/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.imp.builder.MarkerCreator;
import org.eclipse.imp.core.ErrorHandler;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.model.ModelFactory;
import org.eclipse.imp.model.ModelFactory.ModelException;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.preferences.IPreferencesService;
import org.eclipse.imp.prefspecs.PrefspecsPlugin;
import org.eclipse.imp.prefspecs.compiler.codegen.PreferencesFactory;
import org.eclipse.imp.prefspecs.pageinfo.ConcreteBooleanFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.ConcreteDirListFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.ConcreteFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.ConcreteFileFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.ConcreteIntFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.ConcreteStringFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.PreferencesPageInfo;
import org.eclipse.imp.prefspecs.pageinfo.PreferencesTabInfo;
import org.eclipse.imp.prefspecs.pageinfo.VirtualBooleanFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.VirtualDirListFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.VirtualFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.VirtualFileFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.VirtualIntFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.VirtualStringFieldInfo;
import org.eclipse.imp.prefspecs.parser.PrefspecsParseController;
import org.eclipse.imp.prefspecs.parser.Ast.*;
import org.eclipse.imp.prefspecs.parser.PrefspecsParser.SymbolTable;
import org.eclipse.imp.wizards.CodeServiceWizard;
import org.eclipse.imp.wizards.ExtensionPointEnabler;
import org.eclipse.imp.wizards.ExtensionPointWizard;
import org.eclipse.imp.wizards.WizardUtilities;
import org.eclipse.pde.core.plugin.IPluginModel;
import org.eclipse.pde.internal.core.bundle.WorkspaceBundleModel;

public class PrefspecsCompiler
{
	protected IProject fProject = null;
	protected String fProjectName = null;
	protected String fLanguageName = null;

    protected PreferencesPageInfo fPageInfo = null;
    protected IFile fPageInfoFile = null;
    
    // Parameters gathered from the page.genparameters file
	protected String fPagePackageName = null;
	protected String fPageClassNameBase = null;
	protected String fPageName = null;
	protected String fPageId = null;
	protected String fPageMenuItem = null;
	protected String fAlternativeMessage = null;	

	
	
	private static final String sClassNameMacro= "$FILE$";

    private static final String sTemplateHeader= "public class " + sClassNameMacro + " {\n" +
        "\tpublic static void main(String[] args) {\n" +
        "\t\tnew " + sClassNameMacro + "().main();\n" +
        "\t\tSystem.out.println(\"done.\");\n" +
        "\t}\n";

    private static final String sTemplateFooter= "}\n";

    //Stack/*<String>*/ fTranslationStack= new Stack();
    
    //public static final String PROBLEM_MARKER_ID= PrefspecsPlugin.kPluginID + ".$PROBLEM_ID$";
    public static String PROBLEM_MARKER_ID = PrefspecsPlugin.kPluginID + ".$PROBLEM_ID$";
    
    public PrefspecsCompiler(String problem_marker_id) {
    	PROBLEM_MARKER_ID = problem_marker_id;
    }
    
    
 
    public PreferencesPageInfo getPreferencesPageInfo(IFile specFile) {
    	computePreferencesPageInfo(specFile);
    	return fPageInfo;	
    }
    
    
    protected void computePreferencesPageInfo(IFile specFile)
    {
    	if (specFile == null) {
		  System.err.println("PrefspecsCompiler.computePreferencesPageInfo(..):  returning null");
		}
		IProject project= specFile.getProject();
		if (project == null) {
		  System.err.println("PrefspecsCompiler.computePreferencesPageInfo(..):  Project is null; returning null");
		}
		ISourceProject sourceProject = null;
		try {
			sourceProject = ModelFactory.open(project);
		} catch (ModelException me){
		  System.err.println("PrefspecsCompiler.computePreferencesPageInfo(..):  Model exception:\n" + me.getMessage() + "\nReturning null");
		}
		IParseController parseController= new PrefspecsParseController();
		
		// Marker creator handles error messages from the parse controller
		MarkerCreator markerCreator = new MarkerCreator(specFile, parseController, PROBLEM_MARKER_ID);
		
		// If we have a kind of parser that might be receptive, tell it
		// what types of problem marker the builder will create
		parseController.addProblemMarkerType(PROBLEM_MARKER_ID);
		
		parseController.initialize(specFile.getProjectRelativePath(), sourceProject, markerCreator);
		
		parseController.parse(getFileContents(specFile), false, new NullProgressMonitor());
		
		ASTNode currentAst= (ASTNode) parseController.getCurrentAst();
		
		if (currentAst == null) {
				System.err.println("PrefspecsCompiler.computePreferencesPageInfo:  current AST is null (parse errors?); unable to compute page info.");
		}
		
		String fileExten= specFile.getFileExtension();
		String fileBase= specFile.getName().substring(0, specFile.getName().length() - fileExten.length() - 1);
		
		TranslatorVisitor visitor = new TranslatorVisitor();
		
		currentAst.accept(visitor);
    }
    
    
    private final class TranslatorVisitor extends AbstractVisitor {
    	SymbolTable innerScope;

        public void unimplementedVisitor(String s) {
            // System.err.println("Don't know how to translate node type '" + s + "'.");
        }
        
        public boolean visit(pageSpec p) {
        	fPageInfo = new PreferencesPageInfo(p.getidentifier().toString());
        	return true;
        }
        
        public void endVisit(pageSpec p) {
        	fPageInfo.dump();
        }
        
        
        public boolean visit(defaultTabSpec tabSpec) {
        	PreferencesTabInfo tab = new PreferencesTabInfo(fPageInfo, tabSpec.getDEFAULT().toString());
        	generalSpecs propSpecs = tabSpec.getgeneralSpecs();

        	isEditableSpec editableSpec = propSpecs.getisEditableSpec();
        	if (editableSpec != null) {
        		tab.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
        	}
        	isRemovableSpec removableSpec = propSpecs.getisRemovableSpec();
        	if (removableSpec != null) {
        		try {
        			tab.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
        		} catch (IllegalArgumentException e) {
        			System.err.println("PrefspecsCompiler.TranslatorVisitor.visit(defaultTabSpec:  \n" +
        					"\tattempt to set isRemovable to illegal value 'true'; substituting 'false'.");
        			tab.setIsRemovable(false);
        		}
        	}
        	// Should always have an inout spec
        	tab.setIsUsed(tabSpec.getinout().toString().equals("in"));
        	return false;
        }
        
        
        
        public boolean visit(configurationTabSpec tabSpec) {
        	PreferencesTabInfo tab = new PreferencesTabInfo(fPageInfo, tabSpec.getCONFIGURATION
        			().toString());
        	generalSpecs propSpecs = tabSpec.getgeneralSpecs();

        	isEditableSpec editableSpec = propSpecs.getisEditableSpec();
        	if (editableSpec != null) {
        		tab.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
        	}
        	isRemovableSpec removableSpec = propSpecs.getisRemovableSpec();
        	if (removableSpec != null) {
            	tab.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
        	}
        	// Should always have an inout spec
        	tab.setIsUsed(tabSpec.getinout().toString().equals("in"));
        	return false;
        }
        
  
        
        public boolean visit(instanceTabSpec tabSpec) {
        	PreferencesTabInfo tab = new PreferencesTabInfo(fPageInfo, tabSpec.getINSTANCE().toString());
        	generalSpecs propSpecs = tabSpec.getgeneralSpecs();

        	isEditableSpec editableSpec = propSpecs.getisEditableSpec();
        	if (editableSpec != null) {
        		tab.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
        	}
        	isRemovableSpec removableSpec = propSpecs.getisRemovableSpec();
        	if (removableSpec != null) {
            	tab.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
        	}
        	// Should always have an inout spec
        	tab.setIsUsed(tabSpec.getinout().toString().equals("in"));
        	return false;
        }
        

	public boolean visit(projectTabSpec tabSpec) {
        	PreferencesTabInfo tab = new PreferencesTabInfo(fPageInfo, tabSpec.getPROJECT().toString());
        	generalSpecs propSpecs = tabSpec.getgeneralSpecs();
        	
        	isEditableSpec editableSpec = propSpecs.getisEditableSpec();
        	if (editableSpec != null) {
        		tab.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
        	}
        	isRemovableSpec removableSpec = propSpecs.getisRemovableSpec();
        	if (removableSpec != null) {
            	tab.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
        	}
        	// Should always have an inout spec
        	tab.setIsUsed(tabSpec.getinout().toString().equals("in"));
        	return false;
        }
        
        
        
        public boolean visit(booleanFieldSpec boolField) {
        	VirtualBooleanFieldInfo vBool = new VirtualBooleanFieldInfo(fPageInfo, boolField.getidentifier().toString());
        	booleanFieldPropertySpecs propSpecs = boolField.getbooleanFieldPropertySpecs();
        	
        	// Create a virtual field
            isEditableSpec editableSpec = propSpecs.getgeneralSpecs().getisEditableSpec();
        	if (editableSpec != null) {
        		vBool.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
        	}
        	isRemovableSpec removableSpec = propSpecs.getgeneralSpecs().getisRemovableSpec();
        	if (removableSpec != null) {
            	vBool.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
        	}
        	
        	booleanSpecialSpec specialSpec = propSpecs.getbooleanSpecificSpec().getbooleanCustomSpec();
        	// hasSpecial <==> specialSpec != null (i.e., presence of spec indicates true)
        	if (specialSpec != null) {
        		vBool.setHasSpecialValue(true);
            	vBool.setSpecialValue(specialSpec.getbooleanValue().toString().equals("true"));
        	} else {
        		vBool.setHasSpecialValue(false);
        		//vBool.setSpecialValue(false);
        	}

        	booleanDefValueSpec defValueSpec = propSpecs.getbooleanSpecificSpec().getbooleanDefValueSpec();
        	if (defValueSpec != null) {
        		vBool.setDefaultValue(defValueSpec.getbooleanValue().toString().equals("true"));
        	}
        	
        	
        	// Create an instance of a concrete field for each tab on the page
        	Iterator tabs = fPageInfo.getTabInfos();
        	while (tabs.hasNext()) {
        		PreferencesTabInfo tab = (PreferencesTabInfo) tabs.next();
        		if (!tab.getIsUsed())
        			continue;
        		ConcreteBooleanFieldInfo cBool = new ConcreteBooleanFieldInfo(vBool, tab);
        		
        		// Set the attributes of the concrete field:
        		// if set in the virtual field, use that value;
        		// else if set for the tab, use that value;
        		// else rely on the default for the field type
            	if (editableSpec != null) {
            		cBool.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cBool.setIsEditable(tab.getIsEditable());
            	}
            	if (removableSpec != null && !(tab.getName().equals(IPreferencesService.DEFAULT_LEVEL))) {
                	cBool.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cBool.setIsRemovable(tab.getIsRemovable());
            	}
            	if (specialSpec != null) {
            		cBool.setHasSpecialValue(true);
                	cBool.setSpecialValue(specialSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cBool.setHasSpecialValue(false);
            		//cBool.setSpecialValue(false);
            	}
        	}
        	return false;
        }
        
        
        public boolean visit(dirListFieldSpec dirListField) {
        	VirtualDirListFieldInfo vDirList = new VirtualDirListFieldInfo(fPageInfo, dirListField.getidentifier().toString());
        	dirlistFieldPropertySpecs propSpecs = dirListField.getdirlistFieldPropertySpecs();
        	
        	// Create a virtual field
            isEditableSpec editableSpec = propSpecs.getgeneralSpecs().getisEditableSpec();
        	if (editableSpec != null) {
        		vDirList.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
        	}
        	isRemovableSpec removableSpec = propSpecs.getgeneralSpecs().getisRemovableSpec();
        	if (removableSpec != null) {
            	vDirList.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
        	}
        	
        	stringSpecialSpec specialSpec = propSpecs.getstringSpecificSpec().getstringCustomSpec().getstringSpecialSpec();
        	// hasSpecial <==> specialSpec != null (i.e., presence of spec indicates true)
        	if (specialSpec != null) {
        		vDirList.setHasSpecialValue(true);
            	vDirList.setSpecialValue(specialSpec.getstringValue().getSTRING_LITERAL().toString());
        	} else {
        		vDirList.setHasSpecialValue(false);
        		//vString.setSpecialValue(null);
        	}

        	stringDefValueSpec defValueSpec = propSpecs.getstringSpecificSpec().getstringDefValueSpec();
        	if (defValueSpec != null) {
        		vDirList.setDefaultValue(defValueSpec.getstringValue().getSTRING_LITERAL().toString());
        	}
        	
        	IstringEmptySpec emptyValueSpec = propSpecs.getstringSpecificSpec().getstringCustomSpec().getstringEmptySpec();
        	if (emptyValueSpec instanceof stringEmptySpec0) {
        		stringEmptySpec0 ses0 = (stringEmptySpec0) emptyValueSpec;
        		vDirList.setEmptyValueAllowed(false);
        		vDirList.setEmptyValue(null);
        	} else if (emptyValueSpec instanceof stringEmptySpec1) {
           		stringEmptySpec1 ses1 = (stringEmptySpec1) emptyValueSpec;
        		vDirList.setEmptyValueAllowed(ses1.getEMPTYALLOWED().toString().equals("true"));
        		vDirList.setEmptyValue(ses1.getstringValue().getSTRING_LITERAL().toString());
        	}
        	
        	// Create an instance of a concrete field for each tab on the page
        	Iterator tabs = fPageInfo.getTabInfos();
        	while (tabs.hasNext()) {
        		PreferencesTabInfo tab = (PreferencesTabInfo) tabs.next();
        		if (!tab.getIsUsed())
        			continue;
        		ConcreteDirListFieldInfo cDirList = new ConcreteDirListFieldInfo(vDirList, tab);
        		
        		// Set the attributes of the concrete field:
        		// if set in the virtual field, use that value;
        		// else if set for the tab, use that value;
        		// else rely on the default for the field type
            	if (editableSpec != null) {
            		cDirList.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cDirList.setIsEditable(tab.getIsEditable());
            	}
            	if (removableSpec != null && !(tab.getName().equals(IPreferencesService.DEFAULT_LEVEL))) {
                	cDirList.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cDirList.setIsRemovable(tab.getIsRemovable());
            	}
            	if (specialSpec != null) {
            		cDirList.setHasSpecialValue(true);
                	cDirList.setSpecialValue(specialSpec.getstringValue().getSTRING_LITERAL().toString());
            	} else {
            		cDirList.setHasSpecialValue(false);
            		//cString.setSpecialValue(null);
            	}
            	if (emptyValueSpec != null) {
	            	if (emptyValueSpec instanceof stringEmptySpec0) {
	            		stringEmptySpec0 ses0 = (stringEmptySpec0) emptyValueSpec;
	            		cDirList.setEmptyValueAllowed(false);
	            		cDirList.setEmptyValue(null);
	            	} else if (emptyValueSpec instanceof stringEmptySpec1) {
	               		stringEmptySpec1 ses1 = (stringEmptySpec1) emptyValueSpec;
	            		cDirList.setEmptyValueAllowed(true);
	            		cDirList.setEmptyValue(ses1.getstringValue().getSTRING_LITERAL().toString());
	            	}
            	}
        	}
        	return false;
        }
  
        
        public boolean visit(fileFieldSpec fileField) {
        	VirtualFileFieldInfo vFile = new VirtualFileFieldInfo(fPageInfo, fileField.getidentifier().toString());
        	fileFieldPropertySpecs propSpecs = fileField.getfileFieldPropertySpecs();
        	
        	// Create a virtual field
            isEditableSpec editableSpec = propSpecs.getgeneralSpecs().getisEditableSpec();
        	if (editableSpec != null) {
        		vFile.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
        	}
        	isRemovableSpec removableSpec = propSpecs.getgeneralSpecs().getisRemovableSpec();
        	if (removableSpec != null) {
            	vFile.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
        	}
        	
        	stringSpecialSpec specialSpec = propSpecs.getstringSpecificSpec().getstringCustomSpec().getstringSpecialSpec();
        	// hasSpecial <==> specialSpec != null (i.e., presence of spec indicates true)
        	if (specialSpec != null) {
        		vFile.setHasSpecialValue(true);
            	vFile.setSpecialValue(specialSpec.getstringValue().getSTRING_LITERAL().toString());
        	} else {
        		vFile.setHasSpecialValue(false);
        		//vString.setSpecialValue(null);
        	}

        	stringDefValueSpec defValueSpec = propSpecs.getstringSpecificSpec().getstringDefValueSpec();
        	if (defValueSpec != null) {
        		vFile.setDefaultValue(defValueSpec.getstringValue().getSTRING_LITERAL().toString());
        	}
        	
        	IstringEmptySpec emptyValueSpec = propSpecs.getstringSpecificSpec().getstringCustomSpec().getstringEmptySpec();
        	if (emptyValueSpec instanceof stringEmptySpec0) {
        		stringEmptySpec0 ses0 = (stringEmptySpec0) emptyValueSpec;
        		vFile.setEmptyValueAllowed(false);
        		vFile.setEmptyValue(null);
        	} else if (emptyValueSpec instanceof stringEmptySpec1) {
           		stringEmptySpec1 ses1 = (stringEmptySpec1) emptyValueSpec;
        		vFile.setEmptyValueAllowed(ses1.getEMPTYALLOWED().toString().equals("true"));
        		vFile.setEmptyValue(ses1.getstringValue().getSTRING_LITERAL().toString());
        	}
        	
        	// Create an instance of a concrete field for each tab on the page
        	Iterator tabs = fPageInfo.getTabInfos();
        	while (tabs.hasNext()) {
        		PreferencesTabInfo tab = (PreferencesTabInfo) tabs.next();
        		if (!tab.getIsUsed())
        			continue;
        		ConcreteFileFieldInfo cFile = new ConcreteFileFieldInfo(vFile, tab);
        		
        		// Set the attributes of the concrete field:
        		// if set in the virtual field, use that value;
        		// else if set for the tab, use that value;
        		// else rely on the default for the field type
            	if (editableSpec != null) {
            		cFile.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cFile.setIsEditable(tab.getIsEditable());
            	}
            	if (removableSpec != null && !(tab.getName().equals(IPreferencesService.DEFAULT_LEVEL))) {
                	cFile.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cFile.setIsRemovable(tab.getIsRemovable());
            	}
            	if (specialSpec != null) {
            		cFile.setHasSpecialValue(true);
                	cFile.setSpecialValue(specialSpec.getstringValue().getSTRING_LITERAL().toString());
            	} else {
            		cFile.setHasSpecialValue(false);
            		//cString.setSpecialValue(null);
            	}
            	if (emptyValueSpec != null) {
	            	if (emptyValueSpec instanceof stringEmptySpec0) {
	            		stringEmptySpec0 ses0 = (stringEmptySpec0) emptyValueSpec;
	            		cFile.setEmptyValueAllowed(false);
	            		cFile.setEmptyValue(null);
	            	} else if (emptyValueSpec instanceof stringEmptySpec1) {
	               		stringEmptySpec1 ses1 = (stringEmptySpec1) emptyValueSpec;
	            		cFile.setEmptyValueAllowed(true);
	            		cFile.setEmptyValue(ses1.getstringValue().getSTRING_LITERAL().toString());
	            	}
            	}
        	}
        	return false;
        }
        
        
        
        public boolean visit(intFieldSpec intField) {
        	VirtualIntFieldInfo vInt = new VirtualIntFieldInfo(fPageInfo, intField.getidentifier().toString());
        	intFieldPropertySpecs propSpecs = intField.getintFieldPropertySpecs();
        	
        	// Create a virtual field
            isEditableSpec editableSpec = propSpecs.getgeneralSpecs().getisEditableSpec();
        	if (editableSpec != null) {
        		vInt.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
        	}
        	isRemovableSpec removableSpec = propSpecs.getgeneralSpecs().getisRemovableSpec();
        	if (removableSpec != null) {
            	vInt.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
        	}
        	
        	intSpecificSpec specificSpec = propSpecs.getintSpecificSpec();
        	intCustomSpec customSpec = specificSpec.getintCustomSpec();
        	intSpecialSpec specialSpec = customSpec.getintSpecialSpec();
        	intRangeSpec rangeSpec = customSpec.getintRangeSpec();
        	intDefValueSpec defValueSpec = specificSpec.getintDefValueSpec();
        	
        	// hasSpecial <==> specialSpec != null (i.e., presence of spec indicates true)
        	if (specialSpec != null) {
        		vInt.setHasSpecialValue(true);
        		int intValue = 0;
        		IsignedNumber signedNumber = specialSpec.getsignedNumber();
        		if (signedNumber instanceof signedNumber0) {
        			intValue = new Integer(((signedNumber0)signedNumber).getNUMBER().toString()).intValue();
        		} else if (signedNumber instanceof signedNumber1) {
        			intValue = new Integer(((signedNumber1)signedNumber).getNUMBER().toString()).intValue();
        		}
        		vInt.setSpecialValue(intValue);
        	} else {
        		vInt.setHasSpecialValue(false);
        		//vInt.setSpecialValue(null);
        	}

        	if (defValueSpec != null) {
        		int intValue = 0;
        		IsignedNumber signedNumber = defValueSpec.getsignedNumber();
        		if (signedNumber instanceof signedNumber0) {
        			intValue = new Integer(((signedNumber0)signedNumber).getNUMBER().toString()).intValue();
        		} else if (signedNumber instanceof signedNumber1) {
        			intValue = new Integer(((signedNumber1)signedNumber).getNUMBER().toString()).intValue();
        		}
        		vInt.setDefaultValue(intValue);
        	}
        	
        	if (rangeSpec != null) {
        		int intValue = 0;
        		IsignedNumber lower = rangeSpec.getsignedNumber();
        		if (lower instanceof signedNumber0) {
        			intValue = new Integer(((signedNumber0)lower).getNUMBER().toString()).intValue();
        		} else if (lower instanceof signedNumber1) {
        			intValue = new Integer(((signedNumber1)lower).getNUMBER().toString()).intValue();
        		}
        		vInt.setRangeLow(intValue);
        		
        		IsignedNumber upper = rangeSpec.getsignedNumber4();
        		if (upper instanceof signedNumber0) {
        			intValue = new Integer(((signedNumber0)upper).getNUMBER().toString()).intValue();
        		} else if (upper instanceof signedNumber1) {
        			intValue = new Integer(((signedNumber1)upper).getNUMBER().toString()).intValue();
        		}
        		vInt.setRangeHigh(intValue);
        	}
        	
        	
        	// Create an instance of a concrete field for each tab on the page
        	Iterator tabs = fPageInfo.getTabInfos();
        	while (tabs.hasNext()) {
        		PreferencesTabInfo tab = (PreferencesTabInfo) tabs.next();
        		if (!tab.getIsUsed())
        			continue;
        		ConcreteIntFieldInfo cInt = new ConcreteIntFieldInfo(vInt, tab);
        		
        		// Set the attributes of the concrete field:
        		// if set in the virtual field, use that value;
        		// else if set for the tab, use that value;
        		// else rely on the default for the field type
            	if (editableSpec != null) {
            		cInt.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cInt.setIsEditable(tab.getIsEditable());
            	}
            	if (removableSpec != null && !(tab.getName().equals(IPreferencesService.DEFAULT_LEVEL))) {
                	cInt.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cInt.setIsRemovable(tab.getIsRemovable());
            	}
            	if (specialSpec != null) {
            		cInt.setHasSpecialValue(true);
            		int intValue = 0;
            		IsignedNumber signedNumber = specialSpec.getsignedNumber();
            		if (signedNumber instanceof signedNumber0) {
            			intValue = new Integer(((signedNumber0)signedNumber).getNUMBER().toString()).intValue();
            		} else if (signedNumber instanceof signedNumber1) {
            			intValue = new Integer(((signedNumber1)signedNumber).getNUMBER().toString()).intValue();
            		}
            		cInt.setSpecialValue(intValue);
            	} else {
            		cInt.setHasSpecialValue(false);
            		//cString.setSpecialValue(null);
            	}

            	if (rangeSpec != null) {
            		int intValue = 0;
            		IsignedNumber lower = rangeSpec.getsignedNumber();
            		if (lower instanceof signedNumber0) {
            			intValue = new Integer(((signedNumber0)lower).getNUMBER().toString()).intValue();
            		} else if (lower instanceof signedNumber1) {
            			intValue = new Integer(((signedNumber1)lower).getNUMBER().toString()).intValue();
            		}
            		cInt.setRangeLow(intValue);
            		
            		IsignedNumber upper = rangeSpec.getsignedNumber4();
            		if (upper instanceof signedNumber0) {
            			intValue = new Integer(((signedNumber0)upper).getNUMBER().toString()).intValue();
            		} else if (upper instanceof signedNumber1) {
            			intValue = new Integer(((signedNumber1)upper).getNUMBER().toString()).intValue();
            		}
            		cInt.setRangeHigh(intValue);
            	}
            	
        	}
        	return false;
        }
        
        
        
        public boolean visit(stringFieldSpec stringField) {
        	VirtualStringFieldInfo vString = new VirtualStringFieldInfo(fPageInfo, stringField.getidentifier().toString());
        	stringFieldPropertySpecs propSpecs = stringField.getstringFieldPropertySpecs();
        	
        	// Create a virtual field
            isEditableSpec editableSpec = propSpecs.getgeneralSpecs().getisEditableSpec();
        	if (editableSpec != null) {
        		vString.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
        	}
        	isRemovableSpec removableSpec = propSpecs.getgeneralSpecs().getisRemovableSpec();
        	if (removableSpec != null) {
            	vString.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
        	}
        	
        	stringSpecialSpec specialSpec = propSpecs.getstringSpecificSpec().getstringCustomSpec().getstringSpecialSpec();
        	// hasSpecial <==> specialSpec != null (i.e., presence of spec indicates true)
        	if (specialSpec != null) {
        		vString.setHasSpecialValue(true);
            	vString.setSpecialValue(specialSpec.getstringValue().getSTRING_LITERAL().toString());
        	} else {
        		vString.setHasSpecialValue(false);
        		//vString.setSpecialValue(null);
        	}

        	stringDefValueSpec defValueSpec = propSpecs.getstringSpecificSpec().getstringDefValueSpec();
        	if (defValueSpec != null) {
        		vString.setDefaultValue(defValueSpec.getstringValue().getSTRING_LITERAL().toString());
        	}
        	
        	IstringEmptySpec emptyValueSpec = propSpecs.getstringSpecificSpec().getstringCustomSpec().getstringEmptySpec();
        	if (emptyValueSpec instanceof stringEmptySpec0) {
        		stringEmptySpec0 ses0 = (stringEmptySpec0) emptyValueSpec;
        		vString.setEmptyValueAllowed(false);
        		vString.setEmptyValue(null);
        	} else if (emptyValueSpec instanceof stringEmptySpec1) {
           		stringEmptySpec1 ses1 = (stringEmptySpec1) emptyValueSpec;
        		vString.setEmptyValueAllowed(ses1.getEMPTYALLOWED().toString().equals("true"));
        		vString.setEmptyValue(ses1.getstringValue().getSTRING_LITERAL().toString());
        	}
        	
        	// Create an instance of a concrete field for each tab on the page
        	Iterator tabs = fPageInfo.getTabInfos();
        	while (tabs.hasNext()) {
        		PreferencesTabInfo tab = (PreferencesTabInfo) tabs.next();
        		if (!tab.getIsUsed())
        			continue;
        		ConcreteStringFieldInfo cString = new ConcreteStringFieldInfo(vString, tab);
        		
        		// Set the attributes of the concrete field:
        		// if set in the virtual field, use that value;
        		// else if set for the tab, use that value;
        		// else rely on the default for the field type
            	if (editableSpec != null) {
            		cString.setIsEditable(editableSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cString.setIsEditable(tab.getIsEditable());
            	}
            	if (removableSpec != null && !(tab.getName().equals(IPreferencesService.DEFAULT_LEVEL))) {
                	cString.setIsRemovable(removableSpec.getbooleanValue().toString().equals("true"));
            	} else {
            		cString.setIsRemovable(tab.getIsRemovable());
            	}
            	if (specialSpec != null) {
            		cString.setHasSpecialValue(true);
                	cString.setSpecialValue(specialSpec.getstringValue().getSTRING_LITERAL().toString());
            	} else {
            		cString.setHasSpecialValue(false);
            		//cString.setSpecialValue(null);
            	}
            	if (emptyValueSpec != null) {
	            	if (emptyValueSpec instanceof stringEmptySpec0) {
	            		stringEmptySpec0 ses0 = (stringEmptySpec0) emptyValueSpec;
	            		cString.setEmptyValueAllowed(false);
	            		cString.setEmptyValue(null);
	            	} else if (emptyValueSpec instanceof stringEmptySpec1) {
	               		stringEmptySpec1 ses1 = (stringEmptySpec1) emptyValueSpec;
	            		cString.setEmptyValueAllowed(true);
	            		cString.setEmptyValue(ses1.getstringValue().getSTRING_LITERAL().toString());
	            	}
            	}
        	}
        	return false;
        }
        
        
        
        
        /*
         * Context for processing of custom rules 
         */ 
        String customTabName = null;
        PreferencesTabInfo customTabInfo = null;
        String customFieldName = null;
        ConcreteFieldInfo customFieldInfo = null;
        
        
        public boolean visit(customRule custRule)
        {
        	// Set context for custom spec processing
        	customTabName = custRule.gettab().toString();
        	customFieldName = custRule.getidentifier().toString();
        	
        	customTabInfo = fPageInfo.getTabInfo(customTabName);
        	Iterator concreteFields = customTabInfo.getConcreteFields();
        	// Should always have a field with the given name
        	while (concreteFields.hasNext()) {
        		ConcreteFieldInfo next = (ConcreteFieldInfo) concreteFields.next();
        		if (next.getName().equals(customFieldName)) {
        			customFieldInfo = next;
        			break;
        		}	
        	}
        	if (customFieldInfo == null) {
        		// What to do?  Seems that specification is erroneous
        		System.err.println("PrefspecsCompiler.TranslatorVisitor.visit(customRule):\n    found no field info corresponding to field name = '" +
        				customFieldName + "';\n    continuing at your own risk");
        	}
        	
        	return true;
        }
        
        
        public void endVisit(customRule custRule)
        {
        	// Reset context of custom spec processing
        	customTabName = null;
        	customTabInfo = null;
        	customFieldName = null;
        	customFieldInfo = null;	
        }
        

        // Use endVisit(..) methods to get information from specs because when
        // visit(..)ed they don't yet have the info that's needed
        
        
        public void endVisit(generalSpecs genSpecs)
        {
        	// Process general spec as part of a custom rule,
        	// if processing a custom rule
        	if (customTabName != null) {
        		if (genSpecs.getisEditableSpec() != null)
	        		customFieldInfo.setIsEditable(genSpecs.getisEditableSpec().getbooleanValue().toString().equals("true"));
        		try {
        			if (genSpecs.getisRemovableSpec() != null)
        				customFieldInfo.setIsRemovable(genSpecs.getisRemovableSpec().getbooleanValue().toString().equals("true"));
        		} catch (IllegalArgumentException e) {
        			System.err.println("PrefspecsCompiler.TranslatorVisitor.visit(generalSpecs:  \n" +
        					"\tattempt to set isRemovable to illegal value 'true'; substituting 'false'.");
        			customFieldInfo.setIsRemovable(false);
        		}
        	}
        }
        
        
        public void endVisit(booleanSpecialSpec specialSpec)
        {
        	// Process special spec as part of a boolean custom spec,
        	// if processing a custom rule
        	// If the specialSpec is not null, then the field has a special spec
        	// and the value of that spec is the special value for the field
        	if (customTabName != null) {
        		// Don't think the boolean value can be null if we have a specialSpec	
        		((ConcreteBooleanFieldInfo)customFieldInfo).setSpecialValue(specialSpec.getbooleanValue().toString().equals("true"));
        	}
        }
        
        
        public void endVisit(stringSpecialSpec specialSpec)
        {
        	// Process special spec as part of a string custom spec,
        	// if processing a custom rule
        	// If the specialSpec is not null, then the field has a special spec
        	// and the value of that spec is the special value for the field
        	if (customTabName != null) {
        		// Don't think the boolean value can be null if we have a specialSpec	
        		((ConcreteStringFieldInfo)customFieldInfo).setSpecialValue(specialSpec.getstringValue().getSTRING_LITERAL().toString());
        	}
        }
        

        
        /*
         * For processing of conditional rules
         */
        
        /*
         * For "with" condition rules
         */
        public boolean visit(conditionalSpec0 rule) {
        	
        	String conditionalFieldName = rule.getidentifier().toString();
        	String conditionFieldName = rule.getidentifier3().toString();
        	VirtualFieldInfo conditionalFieldInfo = null;
        	VirtualFieldInfo conditionFieldInfo = null;
        	
        	Iterator virtualFieldInfos = fPageInfo.getVirtualFieldInfos();	
        	while (virtualFieldInfos.hasNext()) {
        		VirtualFieldInfo next = (VirtualFieldInfo) virtualFieldInfos.next();
        		String nextName = next.getName();
        		if (nextName.equals(conditionalFieldName)) {
        			conditionalFieldInfo = next;
        		} else if (nextName.equals(conditionFieldName)) {
        			conditionFieldInfo = next;
        		}
        		if (conditionalFieldInfo != null && conditionFieldInfo != null)
        			break;
        	}
        	conditionalFieldInfo.setIsConditional(true);
        	conditionalFieldInfo.setConditionalWith(true);
        	// if we're compiling, then the AST should be correct,
        	// in which case the condition field should always be
        	// a boolean field
        	conditionalFieldInfo.setConditionField((VirtualBooleanFieldInfo)conditionFieldInfo);
        	
        	return false;
        }
        
        
        /*
         * For "against" condition rules
         */
        public boolean visit(conditionalSpec1 rule) {
        	
        	String conditionalFieldName = rule.getidentifier().toString();
        	String conditionFieldName = rule.getidentifier3().toString();
        	VirtualFieldInfo conditionalFieldInfo = null;
        	VirtualFieldInfo conditionFieldInfo = null;
        	
        	Iterator virtualFieldInfos = fPageInfo.getVirtualFieldInfos();	
        	while (virtualFieldInfos.hasNext()) {
        		VirtualFieldInfo next = (VirtualFieldInfo) virtualFieldInfos.next();
        		String nextName = next.getName();
        		if (nextName.equals(conditionalFieldName)) {
        			conditionalFieldInfo = next;
        		} else if (nextName.equals(conditionFieldName)) {
        			conditionFieldInfo = next;
        		}
        		if (conditionalFieldInfo != null && conditionFieldInfo != null)
        			break;
        	}
        	conditionalFieldInfo.setIsConditional(true);
        	conditionalFieldInfo.setConditionalWith(false);
        	// if we're compiling, then the AST should be correct,
        	// in which case the condition field should always be
        	// a boolean field
        	conditionalFieldInfo.setConditionField((VirtualBooleanFieldInfo)conditionFieldInfo);
        	
        	return false;
        }
        
        
        
        public void endVisit(conditionalSpecs0 spec) {
        	propagateConditionsToConcreteSpecs();
        }
        
        
        public void endVisit(conditionalSpecs1 spec) {
        	propagateConditionsToConcreteSpecs();
        }
        
        
        protected void propagateConditionsToConcreteSpecs()
        {
        	Iterator vFieldInfos = fPageInfo.getVirtualFieldInfos();
        	while (vFieldInfos.hasNext()) {
        		VirtualFieldInfo vInfo = (VirtualFieldInfo) vFieldInfos.next();
        		if (vInfo.getIsConditional()) {
        			Iterator cFieldInfos = vInfo.getConcreteFieldInfos();
        			while (cFieldInfos.hasNext()) {
        				ConcreteFieldInfo cInfo = (ConcreteFieldInfo) cFieldInfos.next();
        				cInfo.setIsConditional(true);
        				cInfo.setConditionalWith(vInfo.getConditionalWith());
        				cInfo.setConditionField(vInfo.getConditionField());
        			}
        		}
        	}
        	
        	
        }
        
        
        
        
        
        
    }

    
    
    public PrefspecsCompiler() {
        super();
    }

    public String getFileContents(IFile file) {
        char[] buf= null;
        try {
            File javaFile= new File(file.getLocation().toOSString());
            FileReader fileReader= new FileReader(javaFile);
            int len= (int) javaFile.length();

            buf= new char[len];
            fileReader.read(buf, 0, len);
            return new String(buf);
        } catch(FileNotFoundException fnf) {
            System.err.println(fnf.getMessage());
            return "";
        } catch(IOException io) {
            System.err.println(io.getMessage());
            return "";
        }
    }


    public PreferencesPageInfo compile(IFile file, IProgressMonitor mon) {
       	performGeneration(file, mon);
       	return null;
    }
    

    
	public boolean performGeneration(final IFile specFile, final IProgressMonitor mon)
	{
				IWorkspaceRunnable wsop= new IWorkspaceRunnable() {
				    public void run(IProgressMonitor monitor) throws CoreException {			
							getGenerationParameters(specFile);
							collectCodeParms(specFile);
							
							ExtensionPointEnabler.enable(
								specFile.getProject(), "org.eclipse.ui", "preferencePages", 
								new String[][] {
									{ "page:id", fPageId },
									{ "page:name", fPageName },
									{ "page:class", fPagePackageName + "." + fPageClassNameBase },							// was prefClass
									
									{ "extension:preferencesDialog:language", fLanguageName },
									{ "extension:preferencesDialog:fields", specFile.getLocation().toString() },
									{ "extension:preferencesDialog:class", fPagePackageName + "." + fPageClassNameBase },	// was 	prefClass
									{ "extension:preferencesDialog:category", fPageMenuItem	 },
								},
								true,	// replace previous extension
								getPluginDependencies(),
								monitor);
		
							// SMS 18 Jun 2007:  Duplicative if generateCodeStubs() calls compile?
							//PreferencesPageInfo pageInfo = compile(fProject.getFile(new Path(fieldSpecsRelativeLocation)), monitor);
							generateCodeStubs(specFile, mon);
				    }	
				};
			try {
				wsop.run(new NullProgressMonitor());
			} catch (CoreException e) {
				ErrorHandler.reportError("PrefspecsCompiler.performGeneration:  Core exception:  ", e);
			}

//		try {
//			// Don't have a container (that I know of) if we're not a wizard;
//			// just run it here ...
//		    //getContainer().run(true, false, op);
//		    op.run(mon);
//		} catch (InvocationTargetException e) {
//		    Throwable realException= e.getTargetException();
//		    ErrorHandler.reportError("Error", realException);
//		    return false;
//		} catch (InterruptedException e) {
//		    return false;
//		}
		
		// Don't do this with builder
		//postReminderAboutPreferencesInitializer();
		
		return true;	
	}
	
	
	protected void collectCodeParms(IFile file)
	{	
		fProject = file.getProject();
		fProjectName = fProject.getName();
		
		IPath filePath = file.getLocation();
		IPath projectPath = file.getProject().getLocation();
		IPath packagePath = filePath.removeFirstSegments(projectPath.segmentCount() + getProjectSourceLocationPath().segmentCount());
		packagePath = packagePath.removeLastSegments(1);
		fPagePackageName = packagePath.toString();
		fPagePackageName = fPagePackageName.substring(fPagePackageName.indexOf(':')+1);
		fPagePackageName = fPagePackageName.replace('\\', '.');
		fPagePackageName = fPagePackageName.replace('/', '.');
		
		// Obtain through getGenerationParameters()
		//fPageClassNameBase = getPageInfo(file, new NullProgressMonitor()).getPageName();
		fLanguageName = CodeServiceWizard.discoverProjectLanguage(file.getProject());	
	}
	
	
	protected void getGenerationParameters(IFile file)
	throws CoreException
	{
		// Get the generation-parameters file
		IFile genParamsFile = null;
		IContainer parent = file.getParent();
		IResource[] siblings = parent.members();
		for (int i = 0; i < siblings.length; i++) {
			if (siblings[i].getName().endsWith(".genparams")) {
				genParamsFile = (IFile) siblings[i];
				break;
			}
		}
		if (genParamsFile == null) {
			System.err.println("PrefspecsCompiler.getGenerationParameters:  no parameters file found located with specification file = " + file.getName());
		}

        try {
            File f= new File(genParamsFile.getLocation().toOSString());	
            BufferedReader rdr= new BufferedReader(new FileReader(f));
            String line;

            // In the following, the values given to "startsWith(..)" are
            // those expected to be written by NewPreferencesSpecificationWizard
            while ((line = rdr.readLine()) != null) {
            	if (line.endsWith("\n"))
            		line = line.substring(0, line.length()-1);
            	int valStart = line.indexOf("=") + 1;
            	if (line.startsWith("PageClassNameBase"))
            		fPageClassNameBase 	= line.substring(valStart);
            	else if (line.startsWith("PageName"))
            		fPageName = line.substring(valStart);
            	if (line.startsWith("PageId"))
            		fPageId	= line.substring(valStart);	
            	if (line.startsWith("PageMenuItem")) {
            		fPageMenuItem = line.substring(valStart);
            		if (fPageMenuItem.equals("TOP")) {
            			fPageMenuItem = "";
            		}
            	}
            	if (line.startsWith("AlternativeMessage"))
            		fAlternativeMessage	= line.substring(valStart);
            }
        } catch (FileNotFoundException e) {
            logError(e);
            e.printStackTrace();
        } catch (IOException e) {
            logError(e);
            e.printStackTrace();
        }
	}
	
	
	public void generateCodeStubs(IFile specFile, IProgressMonitor mon) throws CoreException
	{
		IProject fProject = specFile.getProject();
        Map<String,String> subs= getStandardSubstitutions(fProject);

        subs.remove("$PREFS_CLASS_NAME$");
        subs.put("$PREFS_CLASS_NAME$", fPageClassNameBase);
        
        subs.remove("$PREFS_PACKAGE_NAME$");
        subs.put("$PREFS_PACKAGE_NAME$", fPagePackageName);

//            subs.remove("$PREFS_ALTERNATIVE_MESSAGE$");
//            subs.put("$PREFS_ALTERNATIVE_MESSAGE$", fAlternativeMessage);
//            IFile pageSrc = createFileFromTemplate(fClassNamePrefix + ".java", "preferencesPageAlternative.java", fPackageFolder, subs, fProject, mon);
//            editFile(mon, pageSrc);
//            return;
//        }	
        
        // Generating a full tabbed preference page

        PrefspecsCompiler prefspecsCompiler = new PrefspecsCompiler(PROBLEM_MARKER_ID);
        // fFieldSpecs has full absolute path; need to give project relative path
//        String projectLocation = fProject.getLocation().toString();
//        String fieldSpecsLocation = fFieldSpecs;
//        fieldSpecsLocation = fieldSpecsLocation.replace("\\", "/");
//        if (fieldSpecsLocation.startsWith(projectLocation)) {
//        	fieldSpecsLocation = fieldSpecsLocation.substring(projectLocation.length());
//        }
//        IFile fieldSpecsFile = fProject.getFile(fieldSpecsLocation);
        PreferencesPageInfo pageInfo = getPreferencesPageInfo(specFile);	//prefspecsCompiler.compile(specFile, new NullProgressMonitor());
        String constantsClassName = fPageClassNameBase + "Constants";
        String initializerClassName = fPageClassNameBase + "Initializer";

		ISourceProject sourceProject = null;
    	try {
    		sourceProject = ModelFactory.open(fProject);
    	} catch (ModelException me){
            System.err.println("PrefspecsCompiler.generateCodeStubs(..):  Model exception:\n" + me.getMessage() + "\nReturning without parsing");
            return;
    	}

    	
        IFile constantsSrc = PreferencesFactory.generatePreferencesConstants(
        		pageInfo, sourceProject, ExtensionPointWizard.getProjectSourceLocation(), fPagePackageName, constantsClassName,  mon);
        //IFile constantsSrc = createFileFromTemplate(fFullClassName + "Constants.java", "preferencesConstants.java", fPackageFolder, subs, fProject, mon);
        //editFile(mon, constantsSrc);

        
        IFile initializerSrc = PreferencesFactory.generatePreferencesInitializers(
        		pageInfo,
        		getPluginPackageName(fProject, null), getPluginClassName(fProject, null), constantsClassName,	
        		sourceProject, ExtensionPointWizard.getProjectSourceLocation(), fPagePackageName, fPageClassNameBase + "Initializer",  mon);
        //IFile initializerSrc = createFileFromTemplate(fFullClassName + "Initializer.java", "preferencesInitializer.java", fPackageFolder, subs, fProject, mon);
        //editFile(mon, initializerSrc);
        
        //fInitializerFileName = initializerSrc.getName();
        
        
        IFile pageSrc = WizardUtilities.createFileFromTemplate(
        	fPageClassNameBase + ".java", "preferencesPageWithTabs.java", fPagePackageName, WizardUtilities.getProjectSourceLocation(), subs, fProject, mon);
        //editFile(mon, pageSrc);
        

        IFile defaultSrc = PreferencesFactory.generateDefaultTab(
        		pageInfo,
        		getPluginPackageName(fProject, null), getPluginClassName(fProject, null), constantsClassName, initializerClassName,
        		sourceProject, ExtensionPointWizard.getProjectSourceLocation(), fPagePackageName, fPageClassNameBase + "DefaultTab",  mon);
        //editFile(mon, defaultSrc);
        
        IFile configSrc = PreferencesFactory.generateConfigurationTab(
        		pageInfo,
        		getPluginPackageName(fProject, null), getPluginClassName(fProject, null), constantsClassName,
        		sourceProject, ExtensionPointWizard.getProjectSourceLocation(), fPagePackageName, fPageClassNameBase + "ConfigurationTab",  mon);
        //editFile(mon, configSrc);
        

        IFile instanceSrc = PreferencesFactory.generateInstanceTab(
        		pageInfo,
        		getPluginPackageName(fProject, null), getPluginClassName(fProject, null), constantsClassName,
        		sourceProject, ExtensionPointWizard.getProjectSourceLocation(), fPagePackageName, fPageClassNameBase + "InstanceTab",  mon);
        //editFile(mon, instanceSrc);
        
        IFile projectSrc = PreferencesFactory.generateProjectTab(
        		pageInfo,
        		getPluginPackageName(fProject, null), getPluginClassName(fProject, null), constantsClassName,
        		sourceProject, ExtensionPointWizard.getProjectSourceLocation(), fPagePackageName, fPageClassNameBase + "ProjectTab",  mon);
        //editFile(mon, projectSrc);

	}
    


    /**
     * Get the name of the package in which a plugin class is defined
     * for this project, or a default value if there is no such package
     * or if the project is null.  If no default name is provided, then
     * the name of the language is used for a default.
     * 
     * The intention here is to return a the name of the plugin package,
     * if the package exists, or a name that could be used as the name
     * of the plugin package, if the package does not exist.  So this
     * method should not return null and should not be used as a test
     * of whether a given project contains a plugin package or class.
     * 
     * 
     * 
     * SMS 23 Mar 2007
     * 
     * @param project		The project for which the plugin package name is sought;
     * 						may be null
     * @param defaultName	A name to return if the given package lacks a plugin class;
     * 						may be null
     * @return				The name of the package that contains the project's plugin
     * 						class, if there is one, or a name that could be used for the
     * 						plugin package, if there is none.
     */
    public String getPluginPackageName(IProject project, String defaultName)
    {
    	String result = defaultName;
    	if (result == null)
    		result = fLanguageName;
       	if (project != null) {
            String activator = null;
            IPluginModel pm = ExtensionPointEnabler.getPluginModelForProject(project);
            if (pm != null) {
            	WorkspaceBundleModel wbm = new WorkspaceBundleModel(project.getFile("META-INF/MANIFEST.MF")); //$NON-NLS-1$
            	activator = wbm.getBundle().getHeader("Bundle-Activator");
            }

            if (activator != null && !activator.equals("")) {
            	if (activator.lastIndexOf(".") >= 0)
            		result = activator.substring(0, activator.lastIndexOf("."));
            }
    	}
       	return result;
    }
    
    /**
     * Get the name of the plugin class for this project, or a default
     * name if there is no plugin class or if the given project is null.
     * If no default name is provided, then a name based on the name of
     * the language is used for a default.
     * 
     * The intention here is to return a the name of the plugin class,
     * if it exists, or a name that could be used as the name of the
     * plugin class, if it does not exist.  So this method should not
     * return null and should not be used as a test of whether a given
     * project contains a plugin class.
     * 
     * SMS 27 Mar 2007
     * 
     * @param project		The project for which the plugin class name is sought;
     * 						may be null
     * @param defaultName	A name to return if the given package lacks a plugin class;
     * 						may be null
     * @return				The name of the project's plugin class, if there is one,
     * 						or a name that could be used for the plugin class, if there
     * 						is none.
     */
    public String getPluginClassName(IProject project, String defaultName)
    {
    	String result = defaultName;
    	if (result == null)
    		result = fPageClassNameBase + "Plugin";
       	if (project != null) {
            String activator = null;
            IPluginModel pm = ExtensionPointEnabler.getPluginModelForProject(project);
            if (pm != null) {
            	WorkspaceBundleModel wbm = new WorkspaceBundleModel(project.getFile("META-INF/MANIFEST.MF")); //$NON-NLS-1$	
            	activator = wbm.getBundle().getHeader("Bundle-Activator");
            }

            if (activator != null) {
            	result = activator.substring(activator.lastIndexOf(".")+1);
            }
    	}
       	return result;
    }
    
    /**
     * Get the plugin id defined for this project, or a default value if
     * there is no plugin id or if the given project is null.   If no default
     * id is provided, then an id based on the name of the project is used
     * for a default.
     * 
     * The intention here is to return a plugin id, if it exists, or a
     * value that could be used as the id of the plugin, if it does not
     * exist.  So this method should not return null and should not be
     * used as a test of whether a given project has a plugin id.
     * 
     * SMS 27 Mar 2007
     * 
     * @param project		The project for which the plugin id name is sought;
     * 						may be null
     * @param defaultID		A value to return if the given package lacks a plugin id;
     * 						may be null
     * @return				The plugin id of the project, if there is one, or a value
     * 						that could be used as the plugin id, if there is none.
     */
    public String getPluginID(IProject project, String defaultID)
    {
    	String result = defaultID;
    	if (result == null)
    		getPluginPackageName(project, null);
       	if (project != null) {
            result = ExtensionPointEnabler.getPluginIDForProject(project);
    	}
       	return result;
    }
    
    
    // SMS 23 Mar 2007
    // This version takes an IProject and provides mappings
    // related to the project's plugin aspect
    public Map<String,String> getStandardSubstitutions(IProject project)
    {
        Map<String,String> result = new HashMap();
        //result = ExtensionPointUtils.getASTInformation((IPluginModel)pages[0].getPluginModel(), project);
        result.put("$LANG_NAME$", fLanguageName);
        result.put("$CLASS_NAME_PREFIX$", fPageClassNameBase);
        result.put("$PACKAGE_NAME$", fPagePackageName);
        // SMS 22 Mar 2007
        result.put("$PROJECT_NAME$", fProjectName);
        // SMS 23 Mar 2007
        // Not the greatest solution, but if we don't have the
        // project then we may as well assume that $PLUGIN_PACKAGE$
        // has a default value
        result.put("$PLUGIN_PACKAGE$", getPluginPackageName(null, null));
        // SMS 27 Mar 2007:  ditto
        result.put("$PLUGIN_CLASS$", getPluginClassName(null, null));
        result.put("$PLUGIN_ID$", getPluginID(null, null));
    	
    	result.remove("$PLUGIN_PACKAGE$");
        result.put("$PLUGIN_PACKAGE$", getPluginPackageName(project, null));
        // SMS 27 Mar 2007
    	result.remove("$PLUGIN_CLASS$");
        result.put("$PLUGIN_CLASS$", getPluginClassName(project, null));
        result.remove("$PLUGIN_ID$");
        result.put("$PLUGIN_ID$", getPluginID(project, null));
        return result;
    }

    
    public IPath getProjectSourceLocationPath() {
    	return new Path(ExtensionPointWizard.getProjectSourceLocation());
    }
    
	protected List getPluginDependencies() {
	    return Arrays.asList(new String[] { "org.eclipse.core.runtime", "org.eclipse.core.resources",
		    "org.eclipse.imp.runtime" });
	}

    
   private void logError(Exception e) {
        final Status status= new Status(IStatus.ERROR, PrefspecsPlugin.kPluginID, 0, e.getMessage(), e);
        PrefspecsPlugin.getInstance().getLog().log(status);
    }
	
}	


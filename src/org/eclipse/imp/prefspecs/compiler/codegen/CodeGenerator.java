/*******************************************************************************
* Copyright (c) 2007 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
*******************************************************************************/

package org.eclipse.imp.prefspecs.compiler.codegen;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.preferences.IPreferencesService;
import org.eclipse.imp.preferences.PreferencesService;
import org.eclipse.imp.prefspecs.compiler.model.FieldInfo;
import org.eclipse.imp.prefspecs.compiler.model.PreferencesPageInfo;
import org.eclipse.imp.prefspecs.compiler.model.PreferencesTabInfo;
import org.eclipse.ui.console.MessageConsoleStream;

public class CodeGenerator {
	private static final String GEN_FILE_WARNING=
	    "/******************************************/\n" +
	    "/* WARNING: GENERATED FILE - DO NOT EDIT! */\n" +
	    "/******************************************/\n";

	private final MessageConsoleStream fConsoleStream;

    public CodeGenerator(MessageConsoleStream consoleStream) {
        this.fConsoleStream= consoleStream;
    }

	public IFile generatePreferenceConstantsClass(List<PreferencesPageInfo> pageInfos, ISourceProject project,
	                                              String projectSourceLocation, String packageName, String className,
	                                              IProgressMonitor mon)
	{
		StringBuilder srcBuilder= new StringBuilder();

		srcBuilder.append(GEN_FILE_WARNING);
		generateConstantsPartBeforeFields(srcBuilder, packageName, className);
		generateConstantsFields(srcBuilder, pageInfos);
		generateConstantsAfterFields(srcBuilder);

		IFile constantsFile = createFileWithText(srcBuilder.toString(), project, projectSourceLocation, packageName, className, mon);
		return constantsFile;
	}

	public IFile generatePreferenceInitializerClass(List<PreferencesPageInfo> pageInfos, String pluginPkgName,
	                                                String pluginClassName, String constantsClassName,
	                                                ISourceProject project, String projectSourceLocation,
	                                                String packageName, String className, IProgressMonitor mon)
	{
		StringBuilder srcBuilder = new StringBuilder();

		srcBuilder.append(GEN_FILE_WARNING);
		generateInitializersPartBeforeFields(srcBuilder, pluginPkgName, pluginClassName, packageName, className);
		generateInitializersFields(srcBuilder, pageInfos, constantsClassName);
		generateInitializersAfterFields(srcBuilder, pluginClassName);

		IFile initializersFile = createFileWithText(srcBuilder.toString(), project, projectSourceLocation, packageName, className, mon);
		return initializersFile;
		
	}

	public IFile generatePreferencePageClass(PreferencesPageInfo pageInfo, String pluginPkgName, String pluginClassName,
	                                         String constantsClassName, String initializerClassName,
	                                         ISourceProject project, String projectSourceLocation,
	                                         String packageName, String className, IProgressMonitor mon)
	{
	    StringBuilder sb= new StringBuilder();

        sb.append(GEN_FILE_WARNING);
	    generatePageBeforeTabs(sb, pluginPkgName, pluginClassName, packageName, className);
	    generateTabs(sb, pageInfo);
	    generatePageAfterTabs(sb, initializerClassName);

	    IFile prefPageFile = createFileWithText(sb.toString(), project, projectSourceLocation, packageName, className, mon);
	    return prefPageFile;
	}

    protected static void generatePageBeforeTabs(StringBuilder srcText,
            String pluginPackageName, String pluginClassName, String packageName, String className)
    {
        if (className.endsWith(".java")) {
            className = className.substring(0, className.length()-5);
        }

        srcText.append("package " + packageName + ";\n\n");
        srcText.append("import org.eclipse.swt.widgets.TabFolder;");
        srcText.append("import org.eclipse.imp.preferences.IPreferencesService;");
        srcText.append("import org.eclipse.imp.preferences.PreferencesInitializer;");
        srcText.append("import org.eclipse.imp.preferences.PreferencesTab;");
        srcText.append("import org.eclipse.imp.preferences.TabbedPreferencesPage;");
        srcText.append("import " + pluginPackageName + "." + pluginClassName + ";");

        srcText.append("\n\n/**\n");
        srcText.append(" * A preference page class.\n");
        srcText.append(" */\n");
        srcText.append("\n\n");
        srcText.append("public class " + className + " extends TabbedPreferencesPage {\n");

        srcText.append("\tpublic " + className + "() {\n");
        srcText.append("\t\tsuper();\n");
        srcText.append("\t\tprefService = " + pluginClassName + ".getInstance().getPreferencesService();\n");
        srcText.append("\t}\n\n");

        srcText.append("\tprotected PreferencesTab[] createTabs(IPreferencesService prefService,\n");
        srcText.append("\t\tTabbedPreferencesPage page, TabFolder tabFolder) {\n");
    }

    private static void generateTabs(StringBuilder srcText, PreferencesPageInfo pageInfo) {
        int tabCount= 0;

        Iterator<PreferencesTabInfo> tabIter= pageInfo.getTabInfos();
        while (tabIter.hasNext()) {
            PreferencesTabInfo tab= tabIter.next();
            if (tab.getIsUsed()) {
                tabCount++;
            }
        }
        String pageName= pageInfo.getPageName();

        srcText.append("\t\tPreferencesTab[] tabs = new PreferencesTab[" + tabCount + "];\n");
        srcText.append("\n");

        tabIter= pageInfo.getTabInfos();
        int tabIdx= 0;
        while (tabIter.hasNext()) {
            PreferencesTabInfo tab= tabIter.next();
            if (tab.getIsUsed()) {
                String tabName= tab.getName();
                String upperTab= Character.toUpperCase(tabName.charAt(0)) + tabName.substring(1);
                String tabClass= pageName.replaceAll("\\.", "") + upperTab + "Tab";
                String tabVar= tabName + "Tab";
                srcText.append("\t\t" + tabClass + " " + tabVar + " = new " + tabClass + "(prefService);\n");
                srcText.append("\t\t" + tabVar + ".createTabContents(page, tabFolder);\n");
                srcText.append("\t\ttabs[" + tabIdx + "] = " + tabVar + ";\n");
                srcText.append("\n");
                tabIdx++;
            }
        }
    }

    protected static void generatePageAfterTabs(StringBuilder srcText, String initializerClassName) {
        srcText.append("\t\treturn tabs;\n");
        srcText.append("\t}\n");
        srcText.append("\n");
        srcText.append("\tpublic PreferencesInitializer getPreferenceInitializer() {\n");
        srcText.append("\t\treturn new " + initializerClassName + "();\n");
        srcText.append("\t}\n");
        srcText.append("}\n");
    }

    // TODO These methods should take a single Map arg to contain all codegen params, rather than taking each one as an individual arg
    public IFile generateDefaultTabClass(PreferencesPageInfo pageInfo, String pluginPkgName, String pluginClassName,
                                         String constantsClassName, String initializerClassName, ISourceProject project,
                                         String projectSourceLocation, String packageName, String className, IProgressMonitor mon) {
        return generateTabClass(pageInfo, IPreferencesService.DEFAULT_LEVEL, pluginPkgName, pluginClassName,
                constantsClassName, project, projectSourceLocation, packageName, className, initializerClassName, mon);
	}

	public IFile generateConfigurationTabClass(PreferencesPageInfo pageInfo, String pluginPkgName, String pluginClassName,
	                                           String constantsClassName, ISourceProject project, String projectSourceLocation,
	                                           String packageName, String className, IProgressMonitor mon) {
	    return generateTabClass(pageInfo, IPreferencesService.CONFIGURATION_LEVEL, pluginPkgName, pluginClassName,
	                            constantsClassName, project, projectSourceLocation, packageName, className, null, mon);
	}

	public IFile generateInstanceTabClass(PreferencesPageInfo pageInfo, String pluginPkgName, String pluginClassName,
	                                      String constantsClassName, ISourceProject project, String projectSourceLocation,
	                                      String packageName, String className, IProgressMonitor mon) {
        return generateTabClass(pageInfo, IPreferencesService.INSTANCE_LEVEL, pluginPkgName, pluginClassName,
                constantsClassName, project, projectSourceLocation, packageName, className, null, mon);
	}

    private IFile generateTabClass(PreferencesPageInfo pageInfo, String pageLevel, String pluginPkgName, String pluginClassName,
            String constantsClassName, ISourceProject project, String projectSourceLocation,
            String packageName, String className, String initializerClassName, IProgressMonitor mon) {
//        if (pageInfo.getTabInfo(pageLevel) == null) {
//            return null;
//        }

        StringBuilder srcText = new StringBuilder();

        srcText.append(GEN_FILE_WARNING);
        generateTabBeforeFields(srcText, pageInfo, pluginPkgName, pluginClassName, packageName, className, initializerClassName, pageLevel);
        generateTabFields(pageInfo, constantsClassName, srcText, pageLevel);
        generateTabAfterFields(srcText);

        IFile srcFile = createFileWithText(srcText.toString(), project, projectSourceLocation, packageName, className, mon);
        return srcFile;
    }

	public IFile generateProjectTabClass(PreferencesPageInfo pageInfo, String pluginPkgName, String pluginClassName,
	                                     String constantsClassName, ISourceProject project, String projectSourceLocation,
	                                     String packageName, String className, IProgressMonitor mon) {
//	    if (pageInfo.getTabInfo(IPreferencesService.PROJECT_LEVEL) == null) {
//	        return null;
//	    }

	    StringBuilder srcText = new StringBuilder();

	    srcText.append(GEN_FILE_WARNING);
	    generateTabBeforeFields(srcText, pageInfo, pluginPkgName, pluginClassName, packageName, className, null, IPreferencesService.PROJECT_LEVEL);
	    generateTabFields(pageInfo, constantsClassName, srcText, IPreferencesService.PROJECT_LEVEL);
	    generateTabAfterFields(srcText);
	    regenerateEndOfProjectTab(pageInfo, srcText);

	    IFile srcFile = createFileWithText(srcText.toString(), project, projectSourceLocation, packageName, className, mon);
	    return srcFile;
	}

	protected static void generateConstantsPartBeforeFields(StringBuilder srcText, String packageName, String className) {
		if (className.endsWith(".java")) {
			className = className.substring(0, className.length() - 5);
		}

		srcText.append("package " + packageName + ";\n\n");
		srcText.append("/**\n");
		srcText.append(" * Constant definitions for preferences.\n");
		srcText.append(" *\n");
		srcText.append(" * The preferences service uses Strings as keys for preference values,\n");
		srcText.append(" * so Strings defined here are used here to designate preference fields.\n");
		srcText.append(" * These strings are generated automatically from a preferences specification.\n");
		srcText.append(" */\n");
		srcText.append("public class " + className + " {\n");
	}

	protected static void generateConstantsFields(StringBuilder srcText, List<PreferencesPageInfo> pageInfos) {
	    for(PreferencesPageInfo pageInfo: pageInfos) {
	        Iterator<FieldInfo> fields = pageInfo.getFieldInfos();
    		while (fields.hasNext()) {
    			FieldInfo field = fields.next();
    			FieldCodeGenerator fcg = field.getCodeGenerator();

    			srcText.append("\tpublic static final String " + fcg.getPreferenceKey() + " = \"" + field.getName() + "\""+ ";\n");
    		}
	    }
	}

	protected static void generateConstantsAfterFields(StringBuilder srcText) {
		srcText.append("}\n");
	}
	
	
	/*
	 * Subroutines to generate parts of the preferences initializations class
	 */


	protected static void generateInitializersPartBeforeFields(StringBuilder srcText, String pluginPackageName,
	                                                           String pluginClassName, String packageName, String className)
	{
		if (className.endsWith(".java")) {
			className = className.substring(0, className.length()-5);
		}

		srcText.append("package " + packageName + ";\n\n");
		//srcText.append("import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;\n");
		srcText.append("import org.eclipse.imp.preferences.PreferencesInitializer;\n");
		srcText.append("import org.eclipse.imp.preferences.IPreferencesService;\n");
		srcText.append("import " + pluginPackageName + "." + pluginClassName + ";\n\n");
		srcText.append("/**\n");
		srcText.append(" * Initializations of default values for preferences.\n");
		srcText.append(" */\n");
		srcText.append("public class " + className + " extends PreferencesInitializer {\n");
		srcText.append("\t/*\n");
		srcText.append("\t * (non-Javadoc)\n");
		srcText.append("\t * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()\n");
		srcText.append("\t */\n");
		srcText.append("\tpublic void initializeDefaultPreferences() {\n");
		srcText.append("\t\tIPreferencesService service = " + pluginClassName + ".getInstance().getPreferencesService();\n\n");
	}

	protected static void generateInitializersFields(StringBuilder srcText, List<PreferencesPageInfo> pageInfos, String constantsClassName) {
	    for(PreferencesPageInfo pageInfo: pageInfos) {
	        Iterator<FieldInfo> fields = pageInfo.getFieldInfos();
    		while (fields.hasNext()) {
    			FieldInfo vField = fields.next();
    			FieldCodeGenerator fcg = vField.getCodeGenerator();

    			fcg.genPreferenceInitializer(srcText, constantsClassName);
    		}
	    }
	}

	protected static void generateInitializersAfterFields(StringBuilder srcText, String pluginClassName) {
		// Note:  first closing brace in text is for the initializeDefaultPreferences method
	    srcText.append("\t}\n\n");	// closing brace for initializeDefaultPreferences
		
		srcText.append("\t/*\n");
		srcText.append("\t * Clear (remove) any preferences set on the given level.\n");	
		srcText.append("\t */\n");
		srcText.append("\tpublic void clearPreferencesOnLevel(String level) {\n");
		srcText.append("\t\tIPreferencesService service = " + pluginClassName + ".getInstance().getPreferencesService();\n");
		srcText.append("\t\tservice.clearPreferencesAtLevel(level);\n\n");
		srcText.append("\t}\n}\n");
	}

	/*
	 * Subroutines to generate parts of the default tab class
	 */

	protected static void generateTabBeforeFields(StringBuilder srcText,
			PreferencesPageInfo pageInfo, String pluginPackageName, String pluginClassName, String packageName, String className, String initializerClassName, String levelName)
	{
		if (className.endsWith(".java")) {
			className = className.substring(0, className.length()-5);
		}
		levelName = levelName.toLowerCase();
		String levelNameUpperInitial = levelName.substring(0, 1).toUpperCase() + levelName.substring(1, levelName.length());

		srcText.append("package " + packageName + ";\n\n");
		srcText.append("import java.util.List;\n");
		srcText.append("import java.util.ArrayList;\n");
		srcText.append("import org.eclipse.core.runtime.preferences.IEclipsePreferences;\n");
		srcText.append("import org.eclipse.swt.widgets.Composite;\n");
		srcText.append("import org.eclipse.swt.widgets.Link;\n");
		srcText.append("import org.eclipse.imp.preferences.*;\n");
		srcText.append("import org.eclipse.imp.preferences.fields.*;\n");
		srcText.append("import org.osgi.service.prefs.Preferences;\n");

		srcText.append("\n\n/**\n");
		srcText.append(" * The " + levelName + " level preferences tab.\n");
		srcText.append(" */\n");
		srcText.append("public class " + className + " extends " + levelNameUpperInitial + "PreferencesTab {\n\n");
		
		srcText.append("\tpublic " + className + "(IPreferencesService prefService) {\n");
		srcText.append("\t\tsuper(prefService, " + pageInfo.getNoDetails() + ");\n");
		srcText.append("\t}\n\n");
		
		if (initializerClassName != null) {
			srcText.append("\t/**\n");
			srcText.append("\t * Creates a language-specific preferences initializer.\n");
			srcText.append("\t *\n");
			srcText.append("\t * @return    The preference initializer to be used to initialize\n");
			srcText.append("\t *            preferences in this tab\n");
			srcText.append("\t */\n");
			srcText.append("\tpublic AbstractPreferenceInitializer getPreferenceInitializer() {\n");
			srcText.append("\t\t" + initializerClassName +	" preferencesInitializer = new " + initializerClassName + "();\n");
			srcText.append("\t\treturn preferencesInitializer;\n");
			srcText.append("\t}\n\n");
		}
		
		srcText.append("\t/**\n");
		srcText.append("\t * Creates specific preference fields with settings appropriate to\n");
		srcText.append("\t * the " + levelName + " preferences level.\n");
		srcText.append("\t *\n");
		srcText.append("\t * Overrides an unimplemented method in PreferencesTab.\n");
		srcText.append("\t *\n");
		srcText.append("\t * @return    An array that contains the created preference fields\n");
		srcText.append("\t *\n");
		srcText.append("\t */\n");
		srcText.append("\tprotected FieldEditor[] createFields(TabbedPreferencesPage page, Composite parent)\n\t{\n");
		srcText.append("\t\tList<FieldEditor> fields = new ArrayList<FieldEditor>();\n");
	}

	protected static void generateTabFields(PreferencesPageInfo pageInfo, String constantsClassName, StringBuilder srcText, String tabLevel) {
		PreferencesTabInfo tabInfo = pageInfo.getTabInfo(tabLevel);
		Iterator<FieldInfo> fields = pageInfo.getFieldInfos();

		while (fields.hasNext()) {
			FieldInfo fieldInfo = (FieldInfo) fields.next();
			FieldCodeGenerator fcg = fieldInfo.getCodeGenerator();

			fcg.genTextToCreateField(srcText, pageInfo, tabLevel);

			if (fieldInfo.getIsConditional()) {
				generateFieldToggleText(fieldInfo, srcText, tabLevel);
			}
		}
	}	

	protected static void generateFieldToggleText(FieldInfo fieldInfo, StringBuilder srcText, String levelName) {
		boolean onProjectLevel = levelName.equals(PreferencesService.PROJECT_LEVEL);
		String condFieldName = fieldInfo.getConditionField().getName();
		FieldCodeGenerator fcg = fieldInfo.getCodeGenerator();

		srcText.append("\n");
		// Initialize the sense of the toggle-field listener
		srcText.append("\t\tfPrefUtils.createToggleFieldListener(" +
			condFieldName + ", " +  fieldInfo.getName() + ", " +
			(fieldInfo.getConditionalWith() ? "true" : "false") + ");\n");
		
		// Initialize the string that represents the value to which the enabled state
		// of the field is set (given that it is enabled conditionally)
		String enabledValueString = null;
		if (onProjectLevel) {
			enabledValueString = "false;\n";
		} else if (fieldInfo.getConditionalWith()) {
			enabledValueString = condFieldName + ".getBooleanValue();\n";
		} else {
			enabledValueString = "!" + condFieldName + ".getBooleanValue();\n";
		}

		String enabledFieldName = "isEnabled" + fieldInfo.getName();
		
		srcText.append("\t\tboolean " + enabledFieldName + " = " + enabledValueString);

		fcg.genTextToEnableField(srcText, enabledFieldName);

		/*
		 * Example target code:	
		fPrefUtils.createToggleFieldListener(useDefaultGenIncludePathField, includeDirectoriesField, false);
		value = !useDefaultGenIncludePathField.getBooleanValue();
		includeDirectoriesField.getTextControl().setEditable(value);
		includeDirectoriesField.getTextControl().setEnabled(value);
		includeDirectoriesField.setEnabled(value, includeDirectoriesField.getParent());
		*/
	}

    private static String createLabelFor(String name) {
        StringBuilder sb= new StringBuilder();
        int from= 0;
        for(int i= 0; i < name.length(); i++) {
            if (Character.isUpperCase(name.charAt(i))) {
                if (i < name.length() - 1 && Character.isUpperCase(name.charAt(i+1))) {
                    sb.append(name.charAt(from));
                    from= i;
                    continue;
                }
                if (i == from) {
                    continue;
                }
                if (i > 0 && from > 0) {
                    sb.append(' ');
                }
                if (from > 0 && i > from + 1) {
                    appendLowerWord(name, from, i, sb);
                } else {
                    sb.append(name.substring(from, i));
                }
                from= i;
            }
        }
        if (from < name.length()) {
            if (from > 0) {
                sb.append(' ');
                appendLowerWord(name, from, name.length(), sb);
            } else {
                sb.append(name.substring(from, name.length()));
            }
        }
        return sb.toString();
    }

    private static void appendLowerWord(String s, int from, int to, StringBuilder sb) {
        if (from > 0 && to > from + 1) {
            sb.append(Character.toLowerCase(s.charAt(from)));
        } else {
            sb.append(s.charAt(from));
        }
        sb.append(s.substring(from+1, to));
    }

	protected static void generateTabAfterFields(StringBuilder srcText) {
		srcText.append("\t\treturn fields.toArray(new FieldEditor[fields.size()]);\n");
		
		// Note:  first closing brace in text is for the createFields method
		srcText.append("\t}\n}\n");
	}

	
	protected static void regenerateEndOfProjectTab(PreferencesPageInfo pageInfo, StringBuilder srcText) {
		// Assume that the given text represents a complete class, and "erase" the closing brace
		srcText.deleteCharAt(srcText.lastIndexOf("}"));
		srcText.append("\n\n");

		// Generate first field-independent part of the addressProjectSelection method
		srcText.append("\tprotected void addressProjectSelection(IPreferencesService.ProjectSelectionEvent event, Composite composite)\n");
		srcText.append("\t{\n");
		srcText.append("\t\tboolean haveCurrentListeners = false;\n\n");
		srcText.append("\t\tPreferences oldNode = event.getPrevious();\n");
		srcText.append("\t\tPreferences newNode = event.getNew();\n\n");
		
		srcText.append("\t\tif (oldNode == null && newNode == null) {\n");		
		srcText.append("\t\t\t// Happens sometimes when you clear the project selection.\n");
		srcText.append("\t\t\t// Nothing, really, to do in this case ...\n");
		srcText.append("\t\t\treturn;\n");
		srcText.append("\t\t}\n\n");
		
		srcText.append("\t\t// If oldNode is not null, we want to remove any preference-change listeners from it\n");
		srcText.append("\t\tif (oldNode != null && oldNode instanceof IEclipsePreferences && haveCurrentListeners) {\n");
		srcText.append("\t\t\tremoveProjectPreferenceChangeListeners();\n");
		srcText.append("\t\t\thaveCurrentListeners = false;\n");
		srcText.append("\t\t} else {\n");
		srcText.append("\t\t\t// Print an advisory message if you want to\n");
		srcText.append("\t\t}\n\n");
		
		// Generate code to declare a local variable for each field
		// (to simplify subsequent references)
		
		srcText.append("\t\t// Declare local references to the fields\n");
		PreferencesTabInfo tabInfo = pageInfo.getTabInfo(IPreferencesService.PROJECT_LEVEL);
		Iterator fields = pageInfo.getFieldInfos();
		int i = 0;
		while (fields.hasNext()) {
		    FieldInfo fieldInfo = (FieldInfo) fields.next();
		    FieldCodeGenerator fcg = fieldInfo.getCodeGenerator();
			String fieldTypeName = fcg.getFieldEditorTypeName();

			srcText.append("\t\t" + fieldTypeName + " " + fieldInfo.getName() + " = (" + fieldTypeName + ") fFields[" + i + "];\n");
			srcText.append("\t\tLink " +  fieldInfo.getName() + "DetailsLink" + " = (Link) fDetailsLinks.get(" + i + ");\n");
			i++;
		}	
		srcText.append("\n");

		// Generate next block of field-independent text

		srcText.append("\t\t// If we have a new project preferences node, then do various things\n");
		srcText.append("\t\t// to set up the project's preferences\n");
		srcText.append("\t\tif (newNode != null && newNode instanceof IEclipsePreferences) {\n");
	
		srcText.append("\t\t\t// If the containing composite is not disposed, then set field values\n");
		srcText.append("\t\t\t// and make them enabled and editable (as appropriate to the type of field)\n\n");
	
		srcText.append("\t\t\tif (!composite.isDisposed()) {\n");		
		srcText.append("\t\t\t\t// Note:  Where there are toggles between fields, it is a good idea to set the\n");
		srcText.append("\t\t\t\t// properties of the dependent field here according to the values they should have\n");
		srcText.append("\t\t\t\t// based on the independent field.  There should be listeners to take care of \n");
		srcText.append("\t\t\t\t// that sort of adjustment once the tab is established, but when properties are\n");
		srcText.append("\t\t\t\t// first initialized here, the properties may not always be set correctly through\n");
		srcText.append("\t\t\t\t// the toggle.  I'm not entirely sure why that happens, except that there may be\n");
		srcText.append("\t\t\t\t// a race condition between the setting of the dependent values by the listener\n");
		srcText.append("\t\t\t\t// and the setting of those values here.  If the values are set by the listener\n");
		srcText.append("\t\t\t\t// first (which might be surprising, but may be possible) then they will be\n");
		srcText.append("\t\t\t\t// overwritten by values set here--so the values set here should be consistent\n");
		srcText.append("\t\t\t\t// with what the listener would set.\n\n");
		
		// Generate code for the (field-specific) initialization and enabling of each field
		// For conditionally enabled fields, attempts to account for the conditionally enabling
		// field
		tabInfo = pageInfo.getTabInfo(IPreferencesService.PROJECT_LEVEL);
		fields = pageInfo.getFieldInfos();
		while (fields.hasNext()) {
		    FieldInfo fieldInfo = (FieldInfo) fields.next();
		    FieldCodeGenerator fcg = fieldInfo.getCodeGenerator();
			String fieldName = fieldInfo.getName();
			String enablementExpr = null; // source text for a Boolean expr that evaluates to true if the field should be enabled

			if (!fieldInfo.getIsConditional()) {
				// simple case--enabled state is what it is
				enablementExpr = "true"; // RMF 14 May 2010 - RHS was Boolean.toString(cFieldInfo.getIsEditable());
			} else {
				// have to represent the setting with or against the condition field
				enablementExpr = fieldInfo.getConditionField().getName() + ".getBooleanValue()";
				if (!fieldInfo.getConditionalWith())
					enablementExpr = "!" + enablementExpr;
			}

            srcText.append("\t\t\t\tfPrefUtils.setField(" + fieldName    + ", " + fieldName + ".getHolder());\n");

            fcg.genTextToEnableField(srcText, enablementExpr);

            // enable (or not) the details link, regardless of the type of field
			srcText.append("\t\t\t\t" + fieldName + "DetailsLink.setEnabled(selectedProjectCombo.getText().length() > 0);\n\n");
		}	
		srcText.append("\t\t\t\tclearModifiedMarksOnLabels();\n"); 
		srcText.append("\t\t\t}\n\n");	// closes if not disposed ...
		
		
		// Generate code to create a property-change listener for each field

		srcText.append("\t\t\t// Add property change listeners\n");
		tabInfo = pageInfo.getTabInfo(IPreferencesService.PROJECT_LEVEL);
		fields = pageInfo.getFieldInfos();
		while (fields.hasNext()) {
		    FieldInfo fieldInfo = (FieldInfo) fields.next();
			String fieldName = fieldInfo.getName();
            // TODO RMF 5/26/2009 - is it really possible for getHolder() to be null? should it be?
			srcText.append(
				"\t\t\tif (" + fieldName + ".getHolder() != null) addProjectPreferenceChangeListeners(" + 
								fieldName + ", \"" + fieldName + "\", " + fieldName + ".getHolder());\n");
		}
		
		srcText.append("\n\t\t\thaveCurrentListeners = true;\n");
		srcText.append("\t\t}\n\n");

		// Generate field-independent code for disabling fields

		srcText.append("\t\t// Or if we don't have a new project preferences node ...\n");
		srcText.append("\t\tif (newNode == null || !(newNode instanceof IEclipsePreferences)) {\n");
		srcText.append("\t\t\t// May happen when the preferences page is first brought up, or\n");
		srcText.append("\t\t\t// if we allow the project to be deselected\\nn");

		srcText.append("\t\t\t// Clear the preferences from the store\n");
		srcText.append("\t\t\tfPrefService.clearPreferencesAtLevel(IPreferencesService.PROJECT_LEVEL);\n\n");

		srcText.append("\t\t\t// Disable fields and make them non-editable\n");
		srcText.append("\t\t\tif (!composite.isDisposed()) {\n");
					
		// Generate field-dependent code for disabling fields
		tabInfo = pageInfo.getTabInfo(IPreferencesService.PROJECT_LEVEL);
		fields = pageInfo.getFieldInfos();
		while (fields.hasNext()) {
		    FieldInfo fieldInfo = (FieldInfo) fields.next();
		    FieldCodeGenerator fcg = fieldInfo.getCodeGenerator();
			String fieldName = fieldInfo.getName();

			fcg.genTextToEnableField(srcText, "false");
		}
		srcText.append("\t\t\t}\n\n");
		
		
		// Generate code to remove listeners
		srcText.append("\t\t\t// Remove listeners\n");
		srcText.append("\t\t\tremoveProjectPreferenceChangeListeners();\n");
		srcText.append("\t\t\thaveCurrentListeners = false;\n");
		
		// To help assure that field properties are established properly
		srcText.append("\t\t\t// To help assure that field properties are established properly\n");
		srcText.append("\t\t\tperformApply();\n");
		srcText.append("\t\t}\n");	// close for if newnode ==  null ...
		srcText.append("\t}\n\n");		// close for method

		// Close class
		srcText.append("\n}\n");
	}

	protected IFile createFileWithText(
			String srcText, ISourceProject project, String projectSourceLocation, String packageName, String className, IProgressMonitor mon)
	{
		// Find or create the folder to contain the file		
		IFolder packageFolder = null;
		String packageFolderName = packageName.replace(".", "/");
		String createdPath = null;
		String[] pathSegs = (projectSourceLocation + packageFolderName).split("/");

		for (int i = 0; i < pathSegs.length; i++) {
			if (createdPath == null)
				createdPath = pathSegs[i];
			else
				createdPath = createdPath + "/" + pathSegs[i];
			packageFolder = project.getRawProject().getFolder(createdPath);
			try {
				if (!packageFolder.exists()) {
					packageFolder.create(true, true, mon);
					if (!packageFolder.exists()) {	
						fConsoleStream.println("CodeGenerator.createFileWithText(): cannot find or create package folder; returning null" +
								"\tpackage folder = " + packageFolder.getLocation().toString());
						return null;
					}
				}
			} catch (CoreException e) {
				fConsoleStream.println("CodeGenerator.createFileWithText(): CoreException finding or creating package folder; returning null" +
						"\tpackage folder = " + packageFolder.getLocation().toString());
				return null;
			}
			
		}
		
//		IFolder packageFolder = project.getRawProject().getFolder(projectSourceLocation + packageFolderName);
//		try {
//			if (!packageFolder.exists()) {
//				packageFolder.create(true, true, mon);
//				if (!packageFolder.exists()) {	
//					fConsoleStream.println("CodeGenerator.createFileWithText(): cannot find or create package folder; returning null" +
//							"\tpackage folder = " + packageFolder.getLocation().toString());
//					return null;
//				}
//			}
//		} catch (CoreException e) {
//			fConsoleStream.println("CodeGenerator.createFileWithText(): CoreException finding or creating package folder; returning null" +
//					"\tpackage folder = " + packageFolder.getLocation().toString());
//			return null;
//		}
		
		// Find or create the file to contain the text,
		// and put the text into it
		String fileName = className;
		if (!fileName.endsWith(".java"))
			fileName += ".java";
		IFile file = packageFolder.getFile(fileName);
		try {
			if (file.exists()) {
				file.setContents(new ByteArrayInputStream(srcText.getBytes()), true, true, mon);
			} else {
			    file.create(new ByteArrayInputStream(srcText.getBytes()), true, mon);
			}
		} catch (CoreException e) {
			fConsoleStream.println("CodeGenerator.createFileWithText(): CoreException creating file; returning null");
			return null;
		}
		
		return file;
	}
}

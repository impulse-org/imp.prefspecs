package org.eclipse.uide.preferences;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.uide.model.ISourceProject;
import org.eclipse.uide.preferences.fields.SafariBooleanFieldEditor;
import org.eclipse.uide.preferences.fields.SafariFieldEditor;
import org.eclipse.uide.preferences.pageinfo.ConcreteBooleanFieldInfo;
import org.eclipse.uide.preferences.pageinfo.ConcreteFieldInfo;
import org.eclipse.uide.preferences.pageinfo.ConcreteStringFieldInfo;
import org.eclipse.uide.preferences.pageinfo.IPreferencesGeneratorData;
import org.eclipse.uide.preferences.pageinfo.PreferencesPageInfo;
import org.eclipse.uide.preferences.pageinfo.PreferencesTabInfo;
import org.eclipse.uide.preferences.pageinfo.VirtualBooleanFieldInfo;
import org.eclipse.uide.preferences.pageinfo.VirtualFieldInfo;

public class PreferencesFactory implements IPreferencesFactory
{
	protected SafariTabbedPreferencesPage prefsPage;	
	protected SafariPreferencesTab prefsTab;
	protected ISafariPreferencesService prefsService;
	protected String tabLevel;
	protected Composite parent;
	protected SafariPreferencesUtilities prefUtils;
	protected IPreferencesGeneratorData generatorData;
	
	private final String createFieldsErrorPrefix =
		"PreferencesFactory.createFields:  IllegalArgumentException:  ";

	
	public PreferencesFactory(
		SafariTabbedPreferencesPage page,
		SafariPreferencesTab tab,
		ISafariPreferencesService service,
		IPreferencesGeneratorData generatorData)
	{
		if (service == null) {
			throw new IllegalArgumentException("PreferencesFactory(): preferences service is null; not allowed");
		}
		// TODO:  Add checks for other inputs
		this.prefsService = service;
		this.prefsTab = tab;	
		this.prefsPage = page;
		this.generatorData = generatorData;
		
		prefUtils = new SafariPreferencesUtilities(service);
	}
	


	public static IFile generatePreferencesConstants(
		PreferencesPageInfo pageInfo,
		ISourceProject project, String projectSourceLocation, String packageName, String className, IProgressMonitor mon)
	{
		// Generate file text
		String fileText = generateConstantsPartBeforeFields(packageName, className);
		fileText = generateConstantsFields(pageInfo, fileText);
		fileText = generateConstantsAfterFields(fileText);

		IFile constantsFile = createFileWithText(fileText, project, projectSourceLocation, packageName, className, mon);
		return constantsFile;
	}
	
	
	public static IFile generatePreferencesInitializers(
		PreferencesPageInfo pageInfo,
		String pluginProjectName, String pluginClassName, String constantsClassName,
		ISourceProject project, String projectSourceLocation, String packageName, String className, IProgressMonitor mon)
	{
		System.out.println("PreferencesFactory.generatePreferencesInitializers():  generating (insofar as implemented)");
		
		// Generate file text
		String fileText = generateInitializersPartBeforeFields(pluginProjectName, pluginClassName, packageName, className);
		fileText = generateInitializersFields(pageInfo, constantsClassName, fileText);
		fileText = generateInitializersAfterFields(fileText);
		
		IFile initializersFile = createFileWithText(fileText, project, projectSourceLocation, packageName, className, mon);
		return initializersFile;
		
	}

	
	
	public SafariFieldEditor[] createFields(Composite parent, String tabName)
	{
		// Check parameters
		if (parent == null) {
			throw new IllegalArgumentException(createFieldsErrorPrefix + "Composite 'parent' is null; not allowed");
		}
		if (tabName == null) {
			throw new IllegalArgumentException(createFieldsErrorPrefix + "Tab name is null; not allowed");
		}
		if (!prefsService.isaPreferencesLevel(tabName)) {
			throw new IllegalArgumentException(createFieldsErrorPrefix + "tab name is not valid");
		}
		
		tabLevel = tabName;
		this.parent = parent;	
		
		List<SafariFieldEditor> result = new ArrayList();
		SafariFieldEditor[] resultArray = null;
		SafariBooleanFieldEditor boolField = null;
		if (tabName.equals(ISafariPreferencesService.DEFAULT_LEVEL)) {
			resultArray = createFields(ISafariPreferencesService.DEFAULT_LEVEL);
		} else if (tabName.equals(ISafariPreferencesService.CONFIGURATION_LEVEL)) {
			resultArray = createFields(ISafariPreferencesService.CONFIGURATION_LEVEL);
		} else if (tabName.equals(ISafariPreferencesService.INSTANCE_LEVEL)) {
			resultArray = createFields(ISafariPreferencesService.INSTANCE_LEVEL);
		} else if (tabName.equals(ISafariPreferencesService.PROJECT_LEVEL)) {
			resultArray = createFields(ISafariPreferencesService.PROJECT_LEVEL);
		}
		return resultArray;
		
	}

	
	protected SafariFieldEditor[] createFields(String tab)
	{
		// For the final return
		SafariFieldEditor[] resultArray = null;
		// To accumulate incremental results
		List<SafariFieldEditor> resultList = new ArrayList();
		
		// Get info on the fields to construct
		PreferencesPageInfo pageInfo = generatorData.getPageInfo();
		tabLevel = tab;
		PreferencesTabInfo tabInfo = pageInfo.getTabInfo(tabLevel);
		Iterator fieldsIter = tabInfo.getConcreteFields();
		
		while (fieldsIter.hasNext()) {
			SafariFieldEditor field = null;
			ConcreteFieldInfo fieldInfo = (ConcreteFieldInfo) fieldsIter.next();
			if (fieldInfo instanceof ConcreteBooleanFieldInfo) {
				field = createFieldEditor((ConcreteBooleanFieldInfo)fieldInfo); 
			} else {
				System.err.println("PreferencesFieldFactory.createFields(" + tab + "):  got unrecognized field-info kind");
			}
			
			if (field != null) {
				resultList.add(field);
			}
		}
		
		resultArray = new SafariFieldEditor[resultList.size()];
		for (int i = 0; i < resultList.size(); i++) {
			resultArray[i] = resultList.get(i);
		}
		return resultArray;
	}
	
		
	protected SafariFieldEditor createFieldEditor(ConcreteBooleanFieldInfo fieldInfo)				
	{
		SafariBooleanFieldEditor boolField =
			prefUtils.makeNewBooleanField(
			   		prefsPage, prefsTab, prefsService,
					tabLevel, fieldInfo.getName(), fieldInfo.getName(),			// tab level, key, text
					parent,
					fieldInfo.getIsEditable(), fieldInfo.getIsEditable(),		// enabled, editable (treat as same)
					fieldInfo.getHasSpecial(), fieldInfo.getSpecial(),
					false, false,												// empty allowed (always false for boolean), empty (irrelevant)
					fieldInfo.getIsRemovable());								// false for default tab but not necessarily any others
		Link fieldDetailsLink = prefUtils.createDetailsLink(parent, boolField, boolField.getChangeControl().getParent(), "Details ...");
		
		return boolField;
	}


	protected SafariFieldEditor createFieldEditor(ConcreteStringFieldInfo fieldInfo)
	{

		return null;
	}
	
	
	// etc. for other field types
	
		
	// ???
//	protected void addFieldToPage(SafariFieldEditor field) {
//		
//	}
	
	
	/*
	 * Subroutines to generate parts of the preferences constants class
	 */
	
	
	protected static String generateConstantsPartBeforeFields(String packageName, String className)
	{
		if (className.endsWith(".java")) {
			className = className.substring(0, className.length()-5);
		}

		String fileText = "package " + packageName + ";\n\n";
		fileText = fileText + "/**\n";
		fileText = fileText + " * Constant definitions for preferences.\n";
		fileText = fileText + " *\n";
		fileText = fileText + " * The preferences service uses Strings as keys for preference values,\n";
		fileText = fileText + " * so Strings defined here are used here to designate preference fields.\n";
		fileText = fileText + " * These strings are generated automatically from a preferences specification.\n";
		fileText = fileText + " * Other constants may be defined here manually.\n";
		fileText = fileText + " *\n";
		fileText = fileText + " */\n";
		fileText = fileText + "\n\n";
		fileText = fileText + "public class " + className + " {\n";
		
		return fileText;
	}
	
	
	protected static String generateConstantsFields(PreferencesPageInfo pageInfo, String fileText)
	{
		Iterator vFields = pageInfo.getVirtualFieldInfos();
		while (vFields.hasNext()) {
			VirtualFieldInfo vField = (VirtualFieldInfo) vFields.next();
			fileText = fileText + "\n\tpublic static final String P_" +
				vField.getName().toUpperCase() + " = \"" + vField.getName() + "\""+ ";\n";
		}			
		return fileText;		
	}

	
	protected static String generateConstantsAfterFields(String fileText) {
		return fileText + "\n}\n";
	}
	
	
	/*
	 * Subroutines to generate parts of the preferences initializations class
	 */
	
	
	protected static String generateInitializersPartBeforeFields(
			String pluginPackageName, String pluginClassName, String packageName, String className)
	{
		if (className.endsWith(".java")) {
			className = className.substring(0, className.length()-5);
		}

		String fileText = "package " + packageName + ";\n\n";
		fileText = fileText + "import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;\n";
		fileText = fileText + "import org.eclipse.uide.preferences.ISafariPreferencesService;\n";
		fileText = fileText + "import " + pluginPackageName + "." + pluginClassName + ";\n\n";
		fileText = fileText + "/**\n";
		fileText = fileText + " * Initializations of default values for preferences.\n";
		fileText = fileText + " */\n";
		fileText = fileText + "\n\n";
		fileText = fileText + "public class " + className + " extends AbstractPreferenceInitializer {\n";
		fileText = fileText + "\t/*\n";
		fileText = fileText + "\t * (non-Javadoc)\n";
		fileText = fileText + "\t * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()\n";
		fileText = fileText + "\t */\n";
		fileText = fileText + "\tpublic void initializeDefaultPreferences() {\n";
		fileText = fileText + "\t\tISafariPreferencesService service = " + pluginClassName + ".getPreferencesService();\n\n";
		
		return fileText;
	}
	
	
	// Examples:
	//service.setBooleanPreference(ISafariPreferencesService.DEFAULT_LEVEL, PreferenceConstants.P_EMIT_MESSAGES, getDefaultEmitMessages());

	
	protected static String generateInitializersFields(PreferencesPageInfo pageInfo, String constantsClassName, String fileText)
	{
		Iterator vFields = pageInfo.getVirtualFieldInfos();
		while (vFields.hasNext()) {
			VirtualFieldInfo vField = (VirtualFieldInfo) vFields.next();
			if (vField instanceof VirtualBooleanFieldInfo) {
				VirtualBooleanFieldInfo vBool = (VirtualBooleanFieldInfo) vField;
				fileText = fileText + "\t\tservice.setBooleanPreference(ISafariPreferencesService.DEFAULT_LEVEL, " +
										constantsClassName + "." + preferenceConstantForName(vBool.getName()) + ", " +
										vBool.getDefValue() + ");\n";
			} else {
				fileText = fileText + "\t\t//Encountered unimplemented initialization for field = " + vField.getName() + "\n";
			}
		}			
		return fileText;		
	}

	
	protected static String generateInitializersAfterFields(String fileText) {
		// Note:  first closing brace in text is for the initializeDefaultPreferences method
		return fileText + "\t}\n}\n";
	}
	
	
	/*
	 * Utility subroutines
	 */
	
	
	protected static IFile createFileWithText(
			String fileText, ISourceProject project, String projectSourceLocation, String packageName, String className, IProgressMonitor mon)
	{
		// Find or create the folder to contain the file
		String packageFolderName = packageName.replace(".", "/");
		IFolder packageFolder = project.getRawProject().getFolder(projectSourceLocation + packageFolderName);
		if (!packageFolder.exists()) {
			String absoluteFolderLocation = project.getRawProject().getLocation() + "/" + projectSourceLocation + packageFolderName;
			File packageDir = new File(absoluteFolderLocation);
			packageDir.mkdir();
			packageFolder = project.getRawProject().getFolder(packageFolderName);
			if (!packageFolder.exists()) {
				System.err.println("PreferencesFactory.createFileWithText(): cannot find or create package folder; returning null");
				return null;
			}
		}
		
		// Get the file resource
		String fileName = projectSourceLocation + packageFolderName + "/" + className;
		if (!fileName.endsWith(".java"))
			fileName += ".java";
		IFile file = project.getRawProject().getFile(fileName);	

		// Add the file text to the file
		try {
			if (file.exists()) {
				file.setContents(new ByteArrayInputStream(fileText.getBytes()), true, true, mon);
			} else {
			    file.create(new ByteArrayInputStream(fileText.getBytes()), true, mon);
			}
		} catch (CoreException e) {
			System.err.println("PreferencesFactory.createFileWithText(): core exception creating file; returning null");
			return null;
		}
		
		return file;
	}
	
	
	protected static String preferenceConstantForName(String  name) {
		return "P_" + name.toUpperCase();
	}
}

package org.eclipse.imp.prefspecs.compiler.codegen;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.preferences.ISafariPreferencesService;
import org.eclipse.imp.preferences.SafariPreferencesTab;
import org.eclipse.imp.preferences.SafariPreferencesUtilities;
import org.eclipse.imp.preferences.SafariTabbedPreferencesPage;
import org.eclipse.imp.preferences.fields.SafariBooleanFieldEditor;
import org.eclipse.imp.preferences.fields.SafariFieldEditor;
import org.eclipse.imp.prefspecs.pageinfo.ConcreteBooleanFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.ConcreteFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.ConcreteStringFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.IPreferencesGeneratorData;
import org.eclipse.imp.prefspecs.pageinfo.PreferencesPageInfo;
import org.eclipse.imp.prefspecs.pageinfo.PreferencesTabInfo;
import org.eclipse.imp.prefspecs.pageinfo.VirtualBooleanFieldInfo;
import org.eclipse.imp.prefspecs.pageinfo.VirtualFieldInfo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;

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

	
	
	public static IFile generateDefaultTab(
		PreferencesPageInfo pageInfo,
		String pluginProjectName, String pluginClassName, String constantsClassName,
		ISourceProject project, String projectSourceLocation, String packageName, String className, IProgressMonitor mon)
	{
		System.out.println("PreferencesFactory.generateDefaultTab():  generating (insofar as implemented)");
		
		// Generate file text
		String fileText = generateTabBeforeFields(pluginProjectName, pluginClassName, packageName, className, ISafariPreferencesService.DEFAULT_LEVEL);
		fileText = generateTabFields(pageInfo, constantsClassName, fileText, ISafariPreferencesService.DEFAULT_LEVEL);
		fileText = generateTabAfterFields(fileText);
		
		IFile initializersFile = createFileWithText(fileText, project, projectSourceLocation, packageName, className, mon);
		return initializersFile;
	}
	
	
	public static IFile generateConfigurationTab(
			PreferencesPageInfo pageInfo,
			String pluginProjectName, String pluginClassName, String constantsClassName,
			ISourceProject project, String projectSourceLocation, String packageName, String className, IProgressMonitor mon)
	{
		System.out.println("PreferencesFactory.generateConfigurationTab():  generating (insofar as implemented)");
		
		// Generate file text
		String fileText = generateTabBeforeFields(pluginProjectName, pluginClassName, packageName, className, ISafariPreferencesService.CONFIGURATION_LEVEL);
		fileText = generateTabFields(pageInfo, constantsClassName, fileText, ISafariPreferencesService.CONFIGURATION_LEVEL);
		fileText = generateTabAfterFields(fileText);
		
		IFile initializersFile = createFileWithText(fileText, project, projectSourceLocation, packageName, className, mon);
		return initializersFile;
	}
	
	
	
	public static IFile generateInstanceTab(
			PreferencesPageInfo pageInfo,
			String pluginProjectName, String pluginClassName, String constantsClassName,
			ISourceProject project, String projectSourceLocation, String packageName, String className, IProgressMonitor mon)
		{
			System.out.println("PreferencesFactory.generateInstanceTab():  generating (insofar as implemented)");
			
			// Generate file text
			String fileText = generateTabBeforeFields(pluginProjectName, pluginClassName, packageName, className, ISafariPreferencesService.INSTANCE_LEVEL);
			fileText = generateTabFields(pageInfo, constantsClassName, fileText, ISafariPreferencesService.INSTANCE_LEVEL);
			fileText = generateTabAfterFields(fileText);
			
			IFile initializersFile = createFileWithText(fileText, project, projectSourceLocation, packageName, className, mon);
			return initializersFile;
		}
		
		
		public static IFile generateProjectTab(
				PreferencesPageInfo pageInfo,
				String pluginProjectName, String pluginClassName, String constantsClassName,
				ISourceProject project, String projectSourceLocation, String packageName, String className, IProgressMonitor mon)
		{
			System.out.println("PreferencesFactory.generateProjectTab():  generating (insofar as implemented)");
			
			// Generate file text
			String fileText = generateTabBeforeFields(pluginProjectName, pluginClassName, packageName, className, ISafariPreferencesService.PROJECT_LEVEL);
			fileText = generateTabFields(pageInfo, constantsClassName, fileText, ISafariPreferencesService.PROJECT_LEVEL);
			fileText = generateTabAfterFields(fileText);
			fileText = regenerateEndOfProjectTab(pageInfo, fileText);
			
			IFile initializersFile = createFileWithText(fileText, project, projectSourceLocation, packageName, className, mon);
			return initializersFile;
		}
		
	
	
	
	public SafariFieldEditor[] createFields(				//Composite parent, String tabName)
			SafariTabbedPreferencesPage page,
			SafariPreferencesTab tab,
			String level,
			Composite parent,
			ISafariPreferencesService prefsService)
	{
		// Check parameters
		if (parent == null) {
			throw new IllegalArgumentException(createFieldsErrorPrefix + "Composite 'parent' is null; not allowed");
		}
		if (level == null) {
			throw new IllegalArgumentException(createFieldsErrorPrefix + "Tab name is null; not allowed");
		}
		if (!prefsService.isaPreferencesLevel(level)) {
			throw new IllegalArgumentException(createFieldsErrorPrefix + "tab name is not valid");
		}
		
		tabLevel = level;
		this.parent = parent;	
		
		List<SafariFieldEditor> result = new ArrayList();
		SafariFieldEditor[] resultArray = null;
		SafariBooleanFieldEditor boolField = null;
//		if (level.equals(ISafariPreferencesService.DEFAULT_LEVEL)) {
//			resultArray = createFields(page, tab, level, parent, prefsService);
//		} else if (level.equals(ISafariPreferencesService.CONFIGURATION_LEVEL)) {
//			resultArray = createFields(ISafariPreferencesService.CONFIGURATION_LEVEL);
//		} else if (level.equals(ISafariPreferencesService.INSTANCE_LEVEL)) {
//			resultArray = createFields(ISafariPreferencesService.INSTANCE_LEVEL);
//		} else if (level.equals(ISafariPreferencesService.PROJECT_LEVEL)) {
//			resultArray = createFields(ISafariPreferencesService.PROJECT_LEVEL);
//		}
//		return resultArray;
		
		// For the final return
		//SafariFieldEditor[] resultArray = null;
		// To accumulate incremental results
		List<SafariFieldEditor> resultList = new ArrayList();
		
		// Get info on the fields to construct
		PreferencesPageInfo pageInfo = generatorData.getPageInfo();
		//tabLevel = tab;
		PreferencesTabInfo tabInfo = pageInfo.getTabInfo(level);
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

	
//	protected SafariFieldEditor[] createFields(String tab)
//	{
//		// For the final return
//		SafariFieldEditor[] resultArray = null;
//		// To accumulate incremental results
//		List<SafariFieldEditor> resultList = new ArrayList();
//		
//		// Get info on the fields to construct
//		PreferencesPageInfo pageInfo = generatorData.getPageInfo();
//		tabLevel = tab;
//		PreferencesTabInfo tabInfo = pageInfo.getTabInfo(tabLevel);
//		Iterator fieldsIter = tabInfo.getConcreteFields();
//		
//		while (fieldsIter.hasNext()) {
//			SafariFieldEditor field = null;
//			ConcreteFieldInfo fieldInfo = (ConcreteFieldInfo) fieldsIter.next();
//			if (fieldInfo instanceof ConcreteBooleanFieldInfo) {
//				field = createFieldEditor((ConcreteBooleanFieldInfo)fieldInfo); 
//			} else {
//				System.err.println("PreferencesFieldFactory.createFields(" + tab + "):  got unrecognized field-info kind");
//			}
//			
//			if (field != null) {
//				resultList.add(field);
//			}
//		}
//		
//		resultArray = new SafariFieldEditor[resultList.size()];
//		for (int i = 0; i < resultList.size(); i++) {
//			resultArray[i] = resultList.get(i);
//		}
//		return resultArray;
//	}
//	
		
	protected SafariFieldEditor createFieldEditor(ConcreteBooleanFieldInfo fieldInfo)				
	{
		SafariBooleanFieldEditor boolField =
			prefUtils.makeNewBooleanField(
			   		prefsPage, prefsTab, prefsService,
					tabLevel, fieldInfo.getName(), fieldInfo.getName(),			// tab level, key, text
					parent,
					fieldInfo.getIsEditable(), fieldInfo.getIsEditable(),		// enabled, editable (treat as same)
					fieldInfo.getHasSpecialValue(), fieldInfo.getSpecialValue(),
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
										vBool.getDefaultValue() + ");\n";
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
	 * Subroutines to generate parts of the default tab class
	 */
	
	
	protected static String generateTabBeforeFields(
			String pluginPackageName, String pluginClassName, String packageName, String className, String levelName)
	{
		if (className.endsWith(".java")) {
			className = className.substring(0, className.length()-5);
		}
		levelName = levelName.toLowerCase();
		String levelNameUpperInitial = levelName.substring(0, 1).toUpperCase() + levelName.substring(1, levelName.length());

		String fileText = "package " + packageName + ";\n\n";
		fileText = fileText + "import java.util.List;\n";
		fileText = fileText + "import java.util.ArrayList;\n";
		fileText = fileText + "import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;\n";
		fileText = fileText + "import org.eclipse.core.runtime.preferences.IEclipsePreferences;\n";
		fileText = fileText + "import org.eclipse.swt.widgets.Composite;\n";
		fileText = fileText + "import org.eclipse.swt.widgets.Link;\n";
		fileText = fileText + "import org.eclipse.uide.preferences.*;\n";
		fileText = fileText + "import org.eclipse.uide.preferences.fields.*;\n";
		fileText = fileText + "import org.osgi.service.prefs.Preferences;\n";
		fileText = fileText + "\n//		 TODO:  Import additional classes for specific field types from\n";
		fileText = fileText + "//		 org.eclipse.uide.preferences.fields";
		fileText = fileText + "\n\n/**\n";
		fileText = fileText + " * The " + levelName + " level preferences tab.\n";
		fileText = fileText + " */\n";
		fileText = fileText + "\n\n";
		fileText = fileText + "public class " + className + " extends " + levelNameUpperInitial + "PreferencesTab {\n\n";
		
		fileText = fileText + "\tpublic " + className + "(ISafariPreferencesService prefService) {\n";
		fileText = fileText + "\t\tsuper(prefService);\n\t}\n\n";
		
		fileText = fileText + "\t/**\n";
		fileText = fileText + "\t * Creates a language-specific preferences initializer.\n";
		fileText = fileText + "\t *\n";
		fileText = fileText + "\t * @return    The preference initializer to be used to initialize\n";
		fileText = fileText + "\t *            preferences in this tab\n";
		fileText = fileText + "\t */\n";
		fileText = fileText + "\tpublic AbstractPreferenceInitializer getPreferenceInitializer() {\n";
		fileText = fileText + "\t\tLegPreferencesDialogInitializer preferencesInitializer = new LegPreferencesDialogInitializer();\n";
		fileText = fileText + "\t\treturn preferencesInitializer;\n";
		fileText = fileText + "\t}\n\n";
		
		fileText = fileText + "\t/**\n";
		fileText = fileText + "\t * Creates specific preference fields with settings appropriate to\n";
		fileText = fileText + "\t * the " + levelName + " preferences level.\n";
		fileText = fileText + "\t *\n";
		fileText = fileText + "\t * Overrides an unimplemented method in SafariPreferencesTab.\n";
		fileText = fileText + "\t *\n";
		fileText = fileText + "\t * @return    An array that contains the created preference fields\n";
		fileText = fileText + "\t *\n";
		fileText = fileText + "\t */\n";
		fileText = fileText + "\tprotected SafariFieldEditor[] createFields(\n";
		fileText = fileText + "\t\tSafariTabbedPreferencesPage page, SafariPreferencesTab tab, String tabLevel,\n";
		fileText = fileText + "\t\tComposite parent, ISafariPreferencesService prefsService)\n\t{\n";
		fileText = fileText + "\t\tList fields = new ArrayList();\n";

		return fileText;
	}
	

	
	protected static String generateTabFields(PreferencesPageInfo pageInfo, String constantsClassName, String fileText, String tabLevel)
	{
		PreferencesTabInfo tabInfo = pageInfo.getTabInfo(tabLevel);
		Iterator cFields = tabInfo.getConcreteFields();

		while (cFields.hasNext()) {
			ConcreteFieldInfo cFieldInfo = (ConcreteFieldInfo) cFields.next();

			if (cFieldInfo instanceof ConcreteBooleanFieldInfo) {	
				ConcreteBooleanFieldInfo cBoolFieldInfo = (ConcreteBooleanFieldInfo) cFieldInfo;
				fileText = fileText + getTextToCreateBooleanField(pageInfo, cBoolFieldInfo, tabLevel);
			} else {
				fileText = fileText + "\t\t//Encountered unimplemented initialization for field = " + cFieldInfo.getName() + "\n\n";
			}
		}			
		return fileText;		
	}	

	
	
	
	protected static String getTextToCreateBooleanField(
		PreferencesPageInfo pageInfo, ConcreteBooleanFieldInfo fieldInfo, String tabLevel	)
	{
		String result = "\n";
		result = result + "\t\tSafariBooleanFieldEditor " + fieldInfo.getName() + " = prefUtils.makeNewBooleanField(\n";
		result = result + "\t\t\tpage, tab, prefsService,\n";
		result = result + "\t\t\t\"" + tabLevel + "\", \"" + fieldInfo.getName() + "\", \"" + fieldInfo.getName() + "\",\n";	// tab level, key, text\n";
		result = result + "\t\t\tparent,\n";
		result = result + "\t\t\t" + fieldInfo.getIsEditable() + ", " + fieldInfo.getIsEditable() + ",\n";		// enabled, editable (treat as same)\n";
		result = result + "\t\t\t" + fieldInfo.getHasSpecialValue() + ", " + fieldInfo.getSpecialValue() + ",\n";
		result = result + "\t\t\tfalse, false,\n";										// empty allowed (always false for boolean), empty (irrelevant)
		result = result + "\t\t\t" + fieldInfo.getIsRemovable() + ");\n";	// false for default tab but not necessarily any others\n";
		result = result + "\t\t\tLink " + fieldInfo.getName() + "DetailsLink = prefUtils.createDetailsLink(parent, " +
							fieldInfo.getName() + ", " + fieldInfo.getName() + ".getChangeControl().getParent()" + ", \"Details ...\");\n\n";	
		result = result + "\t\tfields.add(" + fieldInfo.getName() + ");\n\n";
		return result;
	}
	

	protected static String generateTabAfterFields(String fileText)
	{
		fileText = fileText + "\t\tSafariFieldEditor[] fieldsArray = new SafariFieldEditor[fields.size()];\n";
		fileText = fileText + "\t\tfor (int i = 0; i < fields.size(); i++) {\n";
		fileText = fileText + "\t\t\tfieldsArray[i] = (SafariFieldEditor) fields.get(i);\n";
		fileText = fileText + "\t\t}\n";
		fileText = fileText + "\t\treturn fieldsArray;\n";
		
		// Note:  first closing brace in text is for the createFields method
		return fileText + "\t}\n}\n";
	}

	
	protected static String regenerateEndOfProjectTab(PreferencesPageInfo pageInfo, String fileText)
	{
		// Assuming that the given text represents a complete class,
		// "erase" the closing brace
		fileText = fileText.substring(0, fileText.lastIndexOf("}")) + "\n\n";

		// Generate first field-independent part of the addressProjectSelection method
		fileText = fileText + "\tprotected void addressProjectSelection(ISafariPreferencesService.ProjectSelectionEvent event, Composite composite)\n";
		fileText = fileText + "\t{\n";
		fileText = fileText + "\t\tboolean haveCurrentListeners = false;\n\n";
		fileText = fileText + "\t\tPreferences oldeNode = event.getPrevious();\n";
		fileText = fileText + "\t\tPreferences newNode = event.getNew();\n\n";
		
		fileText = fileText + "\t\tif (oldeNode == null && newNode == null) {\n";		
		fileText = fileText + "\t\t\t// Happens sometimes when you clear the project selection.\n";
		fileText = fileText + "\t\t\t// Nothing, really, to do in this case ...\n";
		fileText = fileText + "\t\t\treturn;\n";
		fileText = fileText + "\t\t}\n\n";
		
		fileText = fileText + "\t\t// If oldeNode is not null, we want to remove any preference-change listeners from it\n";
		fileText = fileText + "\t\tif (oldeNode != null && oldeNode instanceof IEclipsePreferences && haveCurrentListeners) {\n";
		fileText = fileText + "\t\t\tremoveProjectPreferenceChangeListeners();\n";
		fileText = fileText + "\t\t\thaveCurrentListeners = false;\n";
		fileText = fileText + "\t\t} else {\n";
		fileText = fileText + "\t\t\t// Print an advisory message if you want to\n";
		fileText = fileText + "\t\t}\n\n";
		
		// Generate code to declare a local variable for each field
		// (to simplify subsequent references)
		
		fileText = fileText + "\t\t// Declare local references to the fields\n";
		PreferencesTabInfo tabInfo = pageInfo.getTabInfo(ISafariPreferencesService.PROJECT_LEVEL);
		Iterator cFields = tabInfo.getConcreteFields();
		int i = 0;
		while (cFields.hasNext()) {
			ConcreteFieldInfo cFieldInfo = (ConcreteFieldInfo) cFields.next();
			String fieldTypeName = null;
			if (cFieldInfo instanceof ConcreteBooleanFieldInfo) {
				fieldTypeName = "SafariBooleanFieldEditor";
			} else if (cFieldInfo instanceof ConcreteStringFieldInfo) {
				fieldTypeName = "SafariStringFieldEditor";
			} else {
				fieldTypeName = "UnrecognizedFieldType";
			}
			fileText = fileText + "\t\t" + fieldTypeName + " " + cFieldInfo.getName() + " = (" + fieldTypeName + ") fields[" + i++ + "];\n";
		}	
		fileText += "\n";
		
//		fileText = fileText + "\t\tSafariBooleanFieldEditor useDefaultExecutable = (SafariBooleanFieldEditor) fields[0];
//		fileText = fileText + "\t\tSafariBooleanFieldEditor useDefaultClasspath  = (SafariBooleanFieldEditor) fields[1];
//		SafariBooleanFieldEditor emitDiagnostics      = (SafariBooleanFieldEditor) fields[2];
//		SafariBooleanFieldEditor generateLog          = (SafariBooleanFieldEditor) fields[3];
		
		
		
		
		
		fileText = fileText + "\t\t// Declare a 'holder' for each preference field; not strictly necessary\n";
		fileText = fileText + "\t\t// but helpful in various manipulations of fields and controls to follow\n";
		
		
		// Generate a 'holder' for each field (for ease of expression in later uses of fields)
		
		tabInfo = pageInfo.getTabInfo(ISafariPreferencesService.PROJECT_LEVEL);
		cFields = tabInfo.getConcreteFields();
		while (cFields.hasNext()) {
			ConcreteFieldInfo cFieldInfo = (ConcreteFieldInfo) cFields.next();
			fileText = fileText + "\t\tComposite " + cFieldInfo.getName() + "Holder = null;\n";
		}			

		
		// Generate next block of field-independent text

		fileText = fileText + "\t\t// If we have a new project preferences node, then do various things\n";
		fileText = fileText + "\t\t// to set up the project's preferences\n";
		fileText = fileText + "\t\tif (newNode != null && newNode instanceof IEclipsePreferences) {\n";
		fileText = fileText + "\t\t\t// Set project name in the selected-project field\n";
		fileText = fileText + "\t\t\tselectedProjectName.setStringValue(newNode.name());\n\n";
	
		fileText = fileText + "\t\t\t// If the containing composite is not disposed, then set field values\n";
		fileText = fileText + "\t\t\t// and make them enabled and editable (as appropriate to the type of field)\n\n";
	
		fileText = fileText + "\t\t\tif (!composite.isDisposed()) {\n";		
		fileText = fileText + "\t\t\t\t// Note:  Where there are toggles between fields, it is a good idea to set the\n";
		fileText = fileText + "\t\t\t\t// properties of the dependent field here according to the values they should have\n";
		fileText = fileText + "\t\t\t\t// based on the independent field.  There should be listeners to take care of \n";
		fileText = fileText + "\t\t\t\t// that sort of adjustment once the tab is established, but when properties are\n";
		fileText = fileText + "\t\t\t\t// first initialized here, the properties may not always be set correctly through\n";
		fileText = fileText + "\t\t\t\t// the toggle.  I'm not entirely sure why that happens, except that there may be\n";
		fileText = fileText + "\t\t\t\t// a race condition between the setting of the dependent values by the listener\n";
		fileText = fileText + "\t\t\t\t// and the setting of those values here.  If the values are set by the listener\n";
		fileText = fileText + "\t\t\t\t// first (which might be surprising, but may be possible) then they will be\n";
		fileText = fileText + "\t\t\t\t// overwritten by values set here--so the values set here should be consistent\n";
		fileText = fileText + "\t\t\t\t// with what the listener would set.\n\n";
		
		fileText = fileText + "\t\t\t\t// Used in setting enabled and editable status\n";
		fileText = fileText + "\t\t\t\tboolean enabledState = false;\n\n";

		
		// Generate code for the (field-specific) initialization and enabling of each field
		// NOTE:  Does not currently address the setting of the enabled state for one field
		// based on the value of another field
		tabInfo = pageInfo.getTabInfo(ISafariPreferencesService.PROJECT_LEVEL);
		cFields = tabInfo.getConcreteFields();
		while (cFields.hasNext()) {
			ConcreteFieldInfo cFieldInfo = (ConcreteFieldInfo) cFields.next();
			String fieldName = cFieldInfo.getName();
			String holderName = fieldName + "Holder";
			boolean enabled = cFieldInfo.getIsEditable();
			if (cFieldInfo instanceof ConcreteBooleanFieldInfo) {
				fileText = fileText + "\t\t\t\t" + holderName + " = " + fieldName + ".getChangeControl().getParent();\n";
				fileText = fileText + "\t\t\t\tprefUtils.setField(" + fieldName	 + ", " + holderName + ");\n";
				fileText = fileText + "\t\t\t\t" + fieldName + ".getChangeControl().setEnabled(" + enabled + ");\n\n";
			} else if (cFieldInfo instanceof ConcreteStringFieldInfo) {
				fileText = fileText + "\t\t\t\t" + cFieldInfo.getName() + "Holder" + cFieldInfo.getName() + ".getTextControl().getParent();\n";	
				fileText = fileText + "\t\t\t\tprefUtils.setField(" + fieldName	 + ", " + holderName + ");\n";
				fileText = fileText + "\t\t\t\t" + fieldName + ".getTextControl().setEditable(" + enabled + ");\n";
				fileText = fileText + "\t\t\t\t" + fieldName + ".getTextControl().setEnabled(" + enabled + ");\n";
				fileText = fileText + "\t\t\t\t" + fieldName + ".setEnabled(" + enabled + ", " + fieldName + ".getParent());\n\n";
			} // etc.
		}	
		fileText = fileText + "\t\t\t\tclearModifiedMarksOnLabels();\n"; 
		fileText = fileText + "\t\t\t}\n\n";	// closes if not disposed ...
		
		
		// Generate code to create a property-change listener for each field

		fileText = fileText + "\t\t\t// Add property change listeners\n";
		tabInfo = pageInfo.getTabInfo(ISafariPreferencesService.PROJECT_LEVEL);
		cFields = tabInfo.getConcreteFields();
		while (cFields.hasNext()) {
			ConcreteFieldInfo cFieldInfo = (ConcreteFieldInfo) cFields.next();
			String fieldName = cFieldInfo.getName();
			String holderName = fieldName + "Holder";
			fileText = fileText + 
				"\t\t\tif (" + holderName + " != null) addProjectPreferenceChangeListeners(" + 
								fieldName + ", \"" + fieldName + "\", " + holderName + ");\n";
		}
		
		fileText = fileText + "\n\t\t\thaveCurrentListeners = true;\n";
		fileText = fileText + "\t\t}\n\n";

		

		// Generate field-independent code for disabling fields

		fileText = fileText + "\t\t// Or if we don't have a new project preferences node ...\n";
		fileText = fileText + "\t\tif (newNode == null || !(newNode instanceof IEclipsePreferences)) {\n";
		fileText = fileText + "\t\t\t// May happen when the preferences page is first brought up, or\n";
		fileText = fileText + "\t\t\t// if we allow the project to be deselected\\nn";

		fileText = fileText + "\t\t\t// Unset project name in the tab\n";
		fileText = fileText + "\t\t\tselectedProjectName.setStringValue(\"none selected\");\n\n";

		fileText = fileText + "\t\t\t// Clear the preferences from the store\n";
		fileText = fileText + "\t\t\tprefService.clearPreferencesAtLevel(ISafariPreferencesService.PROJECT_LEVEL);\n\n";

		fileText = fileText + "\t\t\t// Disable fields and make them non-editable\n";
		fileText = fileText + "\t\t\tif (!composite.isDisposed()) {\n";
					
		// Generate field-dependent code for disabling fields
		tabInfo = pageInfo.getTabInfo(ISafariPreferencesService.PROJECT_LEVEL);
		cFields = tabInfo.getConcreteFields();
		while (cFields.hasNext()) {
			ConcreteFieldInfo cFieldInfo = (ConcreteFieldInfo) cFields.next();
			String fieldName = cFieldInfo.getName();
			if (cFieldInfo instanceof ConcreteBooleanFieldInfo) {
				fileText = fileText + "\t\t\t\t" + fieldName + ".getChangeControl().setEnabled(false);\n\n";
			} else if (cFieldInfo instanceof ConcreteStringFieldInfo) {
				fileText = fileText + "\t\t\t\t" + fieldName + ".getTextControl().setEditable(false);\n";
				fileText = fileText + "\t\t\t\t" + fieldName + ".getTextControl().setEnabled(false);\n";
				// I think we want the following also
				fileText = fileText + "\t\t\t\t" + fieldName + ".setEnabled(false, " + fieldName + ".getParent());\n\n";
			} // etc.
		}
		fileText = fileText + "\t\t\t}\n\n";
		
		
		// Generate code to remove listeners
		fileText = fileText + "\t\t\t// Remove listeners\n";
		fileText = fileText + "\t\t\tremoveProjectPreferenceChangeListeners();\n";
		fileText = fileText + "\t\t\thaveCurrentListeners = false;\n";
		fileText = fileText + "\t\t}\n";	// close for if newnode ==  null ...
		fileText = fileText + "\t}\n\n";		// close for method

		// Close class
		fileText = fileText + "\n}\n";
		return fileText;
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

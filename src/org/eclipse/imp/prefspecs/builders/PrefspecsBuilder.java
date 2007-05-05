package prefspecs.safari.builders;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.uide.builder.BuilderUtils;
import org.eclipse.uide.builder.MarkerCreator;
import org.eclipse.uide.core.Language;
import org.eclipse.uide.core.LanguageRegistry;
import org.eclipse.uide.core.SAFARIBuilderBase;
import org.eclipse.uide.parser.IParseController;
import org.eclipse.uide.parser.IParseControllerWithMarkerTypes;
import org.eclipse.uide.runtime.SAFARIPluginBase;

import prefspecs.PrefspecsPlugin;
import prefspecs.safari.compiler.PrefspecsCompiler;
import prefspecs.safari.parser.PrefspecsParseController;

/**
 * @author
 */
public class PrefspecsBuilder extends SAFARIBuilderBase {
    /**
     * Extension ID of the Prefspecs builder. Must match the ID in the corresponding
     * extension definition in plugin.xml.
     * SMS 22 Mar 2007:  If that ID is set through the NewBuilder wizard, then so must this one be.
     */
	// SMS 28 Mar 2007:  Make plugin class name totally parameterized
	public static final String BUILDER_ID= PrefspecsPlugin.kPluginID + ".prefspecs.safari.builder";
	// SMS 28 Mar 2007:  Make problem id parameterized (rather than just ".problem") so that
	// it can be given a builde-specific value (not simply composed here using the builder id
	// because the problem id is also needed in ExtensionPointEnabler for adding the marker
	// extension to the plugin.xml file)
    public static final String PROBLEM_MARKER_ID= PrefspecsPlugin.kPluginID + ".prefspecs.safari.builder.problem";
    
    // SMS 11 May 2006
    public static final String LANGUAGE_NAME = "prefspecs";
    public static final Language LANGUAGE = LanguageRegistry.findLanguage(LANGUAGE_NAME);
    public static final String[] EXTENSIONS = LANGUAGE.getFilenameExtensions();


    protected SAFARIPluginBase getPlugin() {
        //return PrefspecsPlugin.getInstance();
        return PrefspecsPlugin.getInstance();
    }

    protected String getErrorMarkerID() {
        return PROBLEM_MARKER_ID;
    }

    protected String getWarningMarkerID() {
        return PROBLEM_MARKER_ID;
    }

    protected String getInfoMarkerID() {
        return PROBLEM_MARKER_ID;
    }


    // SMS 11 May 2006
    // Incorporated realisitic handling of filename extensions
    // using information recorded in the language registry
    protected boolean isSourceFile(IFile file) {
        IPath path= file.getRawLocation();
        if (path == null) return false;

        String pathString = path.toString();
        if (pathString.indexOf("/bin/") != -1) return false;
        
        for (int i = 0; i < EXTENSIONS.length; i++) {
            if (EXTENSIONS[i].equals(path.getFileExtension())) return true;
        }
        return false;
    }


    /**
     * @return true iff the given file is a source file that this builder should scan
     * for dependencies, but not compile as a top-level compilation unit.<br>
     * <code>isNonRootSourceFile()</code> and <code>isSourceFile()</code> should never
     * return true for the same file.
     */
    protected boolean isNonRootSourceFile(IFile resource)
    {
    	// TODO:  If your language has non-root source files (e.g., header files), then
    	// reimplement this method to test for those
        System.err.println("PrefspecsBuilder.isNonRootSourceFile(..) returning FALSE by default");
        return false;
    }

    /**
     * Collects compilation-unit dependencies for the given file, and records
     * them via calls to <code>fDependency.addDependency()</code>.
     */
    protected void collectDependencies(IFile file)
    {   
    	// TODO:  If your langauge has inter-file dependencies then reimplement
    	// this method to collect those
        System.err.println("PrefspecsBuilder.collectDependencies(..) doing nothing by default");
        return;
    }

    
    protected boolean isOutputFolder(IResource resource) {
        return resource.getFullPath().lastSegment().equals("bin");
    }

    
    protected void compile(final IFile file, IProgressMonitor monitor) {
        try {
            // START_HERE
            System.out.println("Builder.compile with file = " + file.getName());
            PrefspecsCompiler compiler= new PrefspecsCompiler(PROBLEM_MARKER_ID);
            compiler.compile(file, monitor);
            // Here we provide a substitute for the compile method that simply
            // runs the parser in place of the compiler but creates problem
            // markers for errors that will show up in the problems view
            //runParserForCompiler(file, monitor);

            doRefresh(file.getParent());
        } catch (Exception e) {
            getPlugin().writeErrorMsg(e.getMessage());

            e.printStackTrace();
        }
    }

    protected void runParserForCompiler(final IFile file, IProgressMonitor monitor) {
        try {
            // Parse controller is the "compiler" here; parses and reports errors
            IParseController parseController = new PrefspecsParseController();

            // Marker creator handles error messages from the parse controller (and
            // uses the parse controller to get additional information about the errors)
            MarkerCreator markerCreator = new MarkerCreator(file, parseController, 	PROBLEM_MARKER_ID);

            // If we have a kind of parser that might be receptive, tell it
            // what types of problem marker the builder will create
            if (parseController instanceof IParseControllerWithMarkerTypes) {
                ((IParseControllerWithMarkerTypes)parseController).addProblemMarkerType(getErrorMarkerID());
            }
            
            // Need to tell the parse controller which file in which project to parse
            // and also the message handler to which to report errors
            parseController.initialize(file.getProjectRelativePath()/*.toString()*/, file.getProject(), markerCreator);
	
            // Get file contents for parsing
            String contents = BuilderUtils.extractContentsToString(file.getLocation().toString());
        	
            // Finally parse it
            parseController.parse(contents, false, monitor);

            doRefresh(file.getParent());
        } catch (Exception e) {
            getPlugin().writeErrorMsg(e.getMessage());
            e.printStackTrace();
        }
    }

}

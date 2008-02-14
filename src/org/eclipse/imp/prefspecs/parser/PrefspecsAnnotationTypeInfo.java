package org.eclipse.imp.prefspecs.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.imp.services.IAnnotationTypeInfo;

public class PrefspecsAnnotationTypeInfo implements IAnnotationTypeInfo {

    /*
     * For the management of associated problem-marker types
     */
    private static List<String> problemMarkerTypes = new ArrayList<String>();
    
    public List getProblemMarkerTypes() {
        return problemMarkerTypes;
    }
    
    public void addProblemMarkerType(String problemMarkerType) {
        problemMarkerTypes.add(problemMarkerType);
    }
    
    public void removeProblemMarkerType(String problemMarkerType) {
        problemMarkerTypes.remove(problemMarkerType);
    }

}

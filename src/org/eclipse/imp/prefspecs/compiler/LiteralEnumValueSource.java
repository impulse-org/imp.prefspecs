/**
 * 
 */
package org.eclipse.imp.prefspecs.compiler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.imp.prefspecs.parser.Ast.labelledStringValue;
import org.eclipse.imp.prefspecs.parser.Ast.labelledStringValueList;
import org.eclipse.imp.prefspecs.parser.Ast.stringValue;

public class LiteralEnumValueSource implements IEnumValueSource {
    private final List<String> fValues= new ArrayList<String>();
    private final List<String> fLabels= new ArrayList<String>();
    private String fDefaultKey;

    public LiteralEnumValueSource(labelledStringValueList svList) {
        for(int i=0; i < svList.size(); i++) {
            labelledStringValue lsv= svList.getlabelledStringValueAt(i);
            stringValue sv= lsv.getoptLabel();
            fValues.add(lsv.getidentifier().getIDENTIFIER().toString());
            fLabels.add(sv != null ? sv.getSTRING_LITERAL().toString() : null);
        }
    }
    public int size() { return fValues.size(); }
    public String getDefaultKey() { return fDefaultKey; }
    public void setDefaultKey(String defKey) { fDefaultKey= defKey; }
    public String getValue(int i) { return fValues.get(i); }
    public String getLabel(int i) { return fLabels.get(i); }
    public List<String> getValues() { return fValues; }
    public List<String> getLabels() { return fLabels; }
}
package org.eclipse.imp.prefspecs.pageinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;

public class VirtualEnumFieldInfo extends VirtualFieldInfo {
    private static final int DEFAULT_NUM_COLS= 2;

    /**
     * The default value associated with this field
     * (used to set the value of the concrete instance of
     * this field on the default level)
     */
    protected String defaultValue= "";

    protected final List<String> valueList= new ArrayList<String>();

    /**
     * A list of optional labels. If non-null and non-empty, indices correspond
     * to those in the field <code>valueList</code>.
     */
    protected final List<String> labelList= new ArrayList<String>();

    private int numCols;

    private boolean hasNumCols= false;

    public VirtualEnumFieldInfo(PreferencesPageInfo parentPage, String name) {
        super(parentPage, name);
    }

    public VirtualEnumFieldInfo(PreferencesPageInfo parentPage, String name, String defValue) {
        this(parentPage, name);
        this.defaultValue= defValue;
    }

    public void setDefaultValue(String s) {
        defaultValue= s;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setValuesAndLabels(List<String> values, List<String> labels) {
        Assert.isTrue(values.size() == labels.size() || labels == null || labels.size() == 0);
        valueList.clear();
        valueList.addAll(values);
        labelList.clear();
        labelList.addAll(labels);
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(valueList);
    }

    public List<String> getLabels() {
        return Collections.unmodifiableList(labelList);
    }

    /*
     * For reporting on the contents of the virtual field
     */
    public void dump(String prefix) {
        super.dump(prefix);
        String indent = prefix + "  ";
        for(int i=0; i < valueList.size(); i++) {
            System.out.println(indent + "slot[" + i + "] = " + valueList.get(i) + ((labelList != null && labelList.size() > 0) ? (": " + labelList.get(i)) : ""));
        }
        System.out.println(indent + "defaultValue    = " + defaultValue);
    }

    public void setNumColumns(int numCols) {
        this.numCols= numCols;
        this.hasNumCols= true;
    }

    public boolean hasNumColumns() {
        return hasNumCols;
    }

    public int getNumColumns() {
        return hasNumCols ? numCols : DEFAULT_NUM_COLS;
    }
}

package org.eclipse.imp.prefspecs.pageinfo;

import java.util.List;

import org.eclipse.ui.console.MessageConsoleStream;

public class ConcreteEnumFieldInfo extends ConcreteFieldInfo {
    /*
     * Local copy of the virtual field on which this concrete
     * field is based.  The purpose of the copy is to avoid the
     * need to repeatedly typecast the copy of the field contained
     * in the supertype.
     */
    protected VirtualEnumFieldInfo vEnumFieldInfo= null;

    /*
     * Fields that can be set in a specific concrete combo field
     * independently of the corresponding virtual field
     */

    /**
     * Constructor to use for concrete fields that are associated
     * with a virtual, as happens during the processing of the prefspecs
     * AST and the elaboration of field attributes.
     * 
     * @param vField
     * @param parentTab
     */
    public ConcreteEnumFieldInfo(VirtualEnumFieldInfo vEnumFieldInfo, PreferencesTabInfo parentTab) {
        // Super gets page and name from associated virtual field;
        // will serve as a check that given values aren't null
        super(vEnumFieldInfo, parentTab);
        this.vEnumFieldInfo= vEnumFieldInfo;
    }

    public String getDefaultValue() {
        return vEnumFieldInfo.getDefaultValue();
    }

    public String[] getValueList() {
        List<String> values= vEnumFieldInfo.getValues();

        return values.toArray(new String[values.size()]);
    }

    public String[] getLabelList() {
        List<String> labels= vEnumFieldInfo.getLabels();

        return labels.toArray(new String[labels.size()]);
    }

    public int getNumColumns() {
        return vEnumFieldInfo.getNumColumns();
    }

    //
    // For reporting on the contents of the field
    //
    public void dump(String prefix, MessageConsoleStream out) {
        super.dump(prefix, out);
        String indent= prefix + "  ";
        out.println(indent + "default value = " + getDefaultValue());
    }

}

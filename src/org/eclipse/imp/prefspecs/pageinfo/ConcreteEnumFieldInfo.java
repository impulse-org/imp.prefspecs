package org.eclipse.imp.prefspecs.pageinfo;

import org.eclipse.imp.prefspecs.compiler.IEnumValueSource;
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

    public IEnumValueSource getValueSource() {
        return vEnumFieldInfo.getValueSource();
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
        out.println(indent + "value source = " + getValueSource());
    }
}

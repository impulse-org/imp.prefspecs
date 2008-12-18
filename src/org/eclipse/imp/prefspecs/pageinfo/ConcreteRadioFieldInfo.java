package org.eclipse.imp.prefspecs.pageinfo;

public class ConcreteRadioFieldInfo extends ConcreteEnumFieldInfo {
    /**
     * Constructor to use for concrete fields that are associated
     * with a virtual, as happens during the processing of the prefspecs
     * AST and the elaboration of field attributes.
     * 
     * @param vField
     * @param parentTab
     */
    public ConcreteRadioFieldInfo(VirtualRadioFieldInfo vRadioFieldInfo, PreferencesTabInfo parentTab) {
        // Super gets page and name from associated virtual field;
        // will serve as a check that given values aren't null
        super(vRadioFieldInfo, parentTab);
    }
}

package org.eclipse.imp.prefspecs.pageinfo;

public class VirtualComboFieldInfo extends VirtualEnumFieldInfo {
    public VirtualComboFieldInfo(PreferencesPageInfo parentPage, String name) {
        super(parentPage, name);
    }

    public VirtualComboFieldInfo(PreferencesPageInfo parentPage, String name, String defValue) {
        this(parentPage, name);
        this.defaultValue= defValue;
    }
}

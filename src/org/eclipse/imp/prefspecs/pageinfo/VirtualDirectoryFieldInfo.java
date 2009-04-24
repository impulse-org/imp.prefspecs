package org.eclipse.imp.prefspecs.pageinfo;

public class VirtualDirectoryFieldInfo extends VirtualStringFieldInfo {
    public VirtualDirectoryFieldInfo(PreferencesPageInfo parentPage, String name) {
        super(parentPage, name);
    }

    public VirtualDirectoryFieldInfo(PreferencesPageInfo parentPage, String name, String defValue) {
        super(parentPage, name, defValue);
    }

    public VirtualDirectoryFieldInfo(PreferencesPageInfo parentPage, String name, String defValue, boolean hasSpecial, String special) {
        super(parentPage, name, defValue, hasSpecial, special);
    }
}

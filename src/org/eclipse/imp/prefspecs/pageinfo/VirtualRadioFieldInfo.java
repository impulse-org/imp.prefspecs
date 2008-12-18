package org.eclipse.imp.prefspecs.pageinfo;

public class VirtualRadioFieldInfo extends VirtualEnumFieldInfo {
    public VirtualRadioFieldInfo(PreferencesPageInfo parentPage, String name) {
        super(parentPage, name);
    }

    public VirtualRadioFieldInfo(PreferencesPageInfo parentPage, String name, String defValue) {
        this(parentPage, name);
        this.defaultValue= defValue;
    }
}

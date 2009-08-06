package org.eclipse.imp.prefspecs.pageinfo;

import org.eclipse.imp.prefspecs.compiler.IEnumValueSource;

public class VirtualComboFieldInfo extends VirtualEnumFieldInfo {
    public VirtualComboFieldInfo(PreferencesPageInfo parentPage, String name) {
        super(parentPage, name);
    }

    public VirtualComboFieldInfo(PreferencesPageInfo parentPage, String name, IEnumValueSource valueSource) {
        super(parentPage, name, valueSource);
    }
}

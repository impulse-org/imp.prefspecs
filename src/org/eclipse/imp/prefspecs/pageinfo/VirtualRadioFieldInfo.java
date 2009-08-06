package org.eclipse.imp.prefspecs.pageinfo;

import org.eclipse.imp.prefspecs.compiler.IEnumValueSource;

public class VirtualRadioFieldInfo extends VirtualEnumFieldInfo {
    public VirtualRadioFieldInfo(PreferencesPageInfo parentPage, String name) {
        super(parentPage, name);
    }

    public VirtualRadioFieldInfo(PreferencesPageInfo parentPage, String name, IEnumValueSource valueSource) {
        super(parentPage, name, valueSource);
    }
}

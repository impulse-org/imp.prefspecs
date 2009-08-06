package org.eclipse.imp.prefspecs.pageinfo;

import org.eclipse.imp.prefspecs.compiler.IEnumValueSource;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * A class representing an enumerated field, e.g. a combo box or group of radio buttons.
 * @author rfuhrer@watson.ibm.com
 */
public class VirtualEnumFieldInfo extends VirtualFieldInfo {
    private static final int DEFAULT_NUM_COLS= 2;

    private IEnumValueSource fValueSource;

    private int numCols;

    private boolean hasNumCols= false;

    public VirtualEnumFieldInfo(PreferencesPageInfo parentPage, String name) {
        super(parentPage, name);
    }

    public VirtualEnumFieldInfo(PreferencesPageInfo parentPage, String name, IEnumValueSource valueSource) {
        super(parentPage, name);
        fValueSource= valueSource;
    }

    public void setValueSource(IEnumValueSource valueSource) {
        fValueSource= valueSource;
    }

    public IEnumValueSource getValueSource() {
        return fValueSource;
    }

    /*
     * For reporting on the contents of the virtual field
     */
    public void dump(String prefix, MessageConsoleStream out) {
        super.dump(prefix, out);
        out.println(fValueSource.toString());
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

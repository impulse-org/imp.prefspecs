/**
 * 
 */
package org.eclipse.imp.prefspecs.compiler;

public class DynamicEnumValueSource implements IEnumValueSource {
    private final String fQualClassName;

    public DynamicEnumValueSource(String qualClassName) {
        fQualClassName= qualClassName;
    }

    public String getQualClassName() {
        return fQualClassName;
    }
}
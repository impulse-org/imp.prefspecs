package org.eclipse.imp.prefspecs.compiler.codegen;

import java.util.List;

import org.eclipse.imp.prefspecs.compiler.model.FieldInfo;
import org.eclipse.imp.prefspecs.compiler.model.PageInfo;

public abstract class FieldCodeGenerator {
    protected final FieldInfo fFieldInfo;

    public FieldCodeGenerator(FieldInfo fieldInfo) {
        fFieldInfo= fieldInfo;
    }

    /**
     * @return the short (unqualified) name of the field editor class
     */
    public abstract String getFieldEditorTypeName();

    /**
     * Generate the Java source text to initialize the default value of the preference
     * associated with this field.
     * @param srcText the StringBuilder to which to append
     * @param prefKeysClassName the short name of the class that defines the preference key constants
     */
    public abstract void genPreferenceInitializer(StringBuilder srcText, String prefKeysClassName);

    /**
     * Generate the Java source text to create this field.
     * @param srcText the StringBuilder to which to append
     * @param pageInfo the PageInfo for the page to which this field belongs
     * @param tabLevel the preference tab/level (default, instance, workspace, project) in
     * which this field resides
     * @see CodeGenerator#generateTabFields(PageInfo, String, StringBuilder, String)
     */
    public abstract void genTextToCreateField(StringBuilder srcText, PageInfo pageInfo, String tabLevel, String parentComposite);

    /**
     * Generate the Java source text to enable/disable this field, given that this field is
     * conditional on some boolean field.
     * @param srcText the StringBuilder to which to append
     * @param enablementExpr the expression that determines whether the field should be enabled
     * @see CodeGenerator#regenerateEndOfProjectTab(PageInfo, StringBuilder)
     */
    public void genTextToEnableField(StringBuilder srcText, String enablementExpr) {
        String fieldName= fFieldInfo.getName();
        srcText.append("\t\t\t\t" + fieldName + ".getTextControl().setEditable(" + enablementExpr + ");\n");
        srcText.append("\t\t\t\t" + fieldName + ".getTextControl().setEnabled(" + enablementExpr + ");\n");
        srcText.append("\t\t\t\t" + fieldName + ".setEnabled(" + enablementExpr + ", " + fieldName + ".getParent());\n");
    }

    /*
     * Code-gen utility methods for derived classes
     */

    protected String getPreferenceKey() {
        return "P_" + fFieldInfo.getName().toUpperCase();
    }

    protected String toStringArrayLiteral(List<String> strings) {
        StringBuilder sb= new StringBuilder();
        sb.append("new String[] { ");
        for(int i= 0; i < strings.size(); i++) {
            if (i > 0) { sb.append(", "); }
            final String s= strings.get(i);
            if (s != null) {
                appendWithQuotes(s, sb);
            } else {
                sb.append("null");
            }
        }
        sb.append(" }");
        return sb.toString();
    }

    protected String stripQuotes(String s) {
        if (s == null) 
            return null;
        if (s.length() == 0)
            return s;
        if (s.length() == 1) {
            if (s.charAt(0) == '"')
                return "";
            else
                return s;
        }
        
        int newStart, newEnd;
        if (s.charAt(0) == '"')
            newStart = 1;
        else
            newStart = 0;
        
        if (s.charAt(s.length()-1) == '"')
            newEnd = s.length()-1;
        else
            newEnd = s.length();
        
        return s.substring(newStart, newEnd);
    }

    protected void appendWithQuotes(final String s, StringBuilder sb) {
        if (!s.startsWith("\"")) {
            sb.append("\"");
        }
        sb.append(s);
        if (!s.endsWith("\"")) {
            sb.append("\"");
        }
    }

    protected String createLabelFor(String name) {
        StringBuilder sb= new StringBuilder();
        int from= 0;
        for(int i= 0; i < name.length(); i++) {
            if (Character.isUpperCase(name.charAt(i))) {
                if (i < name.length() - 1 && Character.isUpperCase(name.charAt(i+1))) {
                    sb.append(name.charAt(from));
                    from= i;
                    continue;
                }
                if (i == from) {
                    continue;
                }
                if (i > 0 && from > 0) {
                    sb.append(' ');
                }
                if (from > 0 && i > from + 1) {
                    appendLowerWord(name, from, i, sb);
                } else {
                    sb.append(name.substring(from, i));
                }
                from= i;
            }
        }
        if (from < name.length()) {
            if (from > 0) {
                sb.append(' ');
                appendLowerWord(name, from, name.length(), sb);
            } else {
                sb.append(name.substring(from, name.length()));
            }
        }
        return sb.toString();
    }

    protected void appendLowerWord(String s, int from, int to, StringBuilder sb) {
        if (from > 0 && to > from + 1) {
            sb.append(Character.toLowerCase(s.charAt(from)));
        } else {
            sb.append(s.charAt(from));
        }
        sb.append(s.substring(from+1, to));
    }
}

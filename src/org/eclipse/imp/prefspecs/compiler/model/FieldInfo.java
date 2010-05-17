/*******************************************************************************
* Copyright (c) 2007 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
*******************************************************************************/

package org.eclipse.imp.prefspecs.compiler.model;

import org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator;
import org.eclipse.ui.console.MessageConsoleStream;

public abstract class FieldInfo implements IPageMember {
	/**
	 * The preferences page with which this field
	 * is associated
	 */
	protected IPageMemberContainer fParent = null;
	
	/**
	 * The name of the field
	 */
	protected String fName = null;

	/**
	 * If non-null, the text to be displayed as a tooltip for this field
	 */
	protected String fToolTipText = null;

    /**
     * True iff this field has an "is removable" specification; if false,
     * the value of isRemovable is irrelevant.
     */
    protected boolean fHasRemovableSpec = false;

	/**
	 * Whether concrete instances of this field be removed by default
	 */
	protected boolean fIsRemovable = false;

	/**
	 * If non-null, the label to use for the widget corresponding to this field
	 */
	protected String fOptLabel = null;

	/**
	 * Whether this field is enabled conditionally based on another
	 * (boolean) field
	 */
	protected boolean fIsConditional = false;

	/**
	 * Whether this field, if isConditional, is is enabled when the
	 * condition field is true (if false implies that this field is
	 * enabled when the condition field is false)
	 */	
	protected boolean fConditionalWith = true;

	/**
	 * The boolean field based on which this field is enabled (or not),
	 * if isConditional and depending on conditionalWith
	 */
	protected BooleanFieldInfo fConditionField = null;

	public FieldInfo(IPageMemberContainer parent, String name) {
		// All fields must have a parent
		if (parent == null) {
			throw new IllegalArgumentException("FieldInfo(..): parent is null; not allowed");		
		}

		// All fields must have a non-null name
		if (name == null) {
			throw new IllegalArgumentException("FieldInfo(..): name is null; not allowed");		
		}

		// Don't worry about other conditions, such as uniqueness of field
		// name within page; may be checked before or after this point
		
		this.fParent = parent;
		this.fName = name;
		this.fParent.addChild(this);
	}

	public abstract FieldCodeGenerator getCodeGenerator();

	//
	// Name is only defined through the constructor,
	// so only "get" methods are defined for those
	//
	
	public IPageMemberContainer getParent() {
		return fParent;
	}
	
	public String getName() {
		return fName;
	}

	public boolean hasRemovableSpec() {
	    return this.fHasRemovableSpec;
	}

	/**
	 * Whether the values of fields on this tab can, by default, be
	 * removed.  Removal of a field value on one tab triggers inheritance
	 * of the value from the corresponding field on the next higher level.
	 * Individual fields may override this setting, except that it is
	 * always false on the default tab (from which there is no higher tab).
	 * 
	 * @return	False for the default tab; the set value of isUsed otherwise
	 */
	public boolean getIsRemovable() {
		return fIsRemovable;
	}

	/**
	 * @param isRemovable
	 */
	public void setIsRemovable(boolean isRemovable) {
		this.fIsRemovable = isRemovable;
		this.fHasRemovableSpec = true;
	}

	public String getLabel() {
	    return fOptLabel;
	}

	public void setLabel(String newLabel) {
	    this.fOptLabel = newLabel;
	}

	public String getToolTipText() {
        return fToolTipText;
    }

    public void setToolTipText(String toolTipText) {
        this.fToolTipText= toolTipText;
    }

	public boolean getIsConditional() {
		return fIsConditional;
	}
	
	public void setIsConditional(boolean b) {
		fIsConditional = b;
	}

	public boolean getConditionalWith() {
		return fConditionalWith;
	}
	
	public void setConditionalWith(boolean b) {
		fConditionalWith = b;
	}

	public BooleanFieldInfo getConditionField() {
		return fConditionField;
	}
	
	public void setConditionField(BooleanFieldInfo vbf) {
		fConditionField = vbf;
	}

	//
	// For reporting on the contents of the field
	//
	public void dump(String prefix, MessageConsoleStream out) {
		String indent = prefix + "  ";
		out.println(prefix + "Field '" + getName() + "'");
		out.println(indent + "parent = " + getParent().getName());
		out.println(indent + "isRemovable = " + fIsRemovable);
		if (fIsConditional) {
			out.println(indent + "isConditional " + (fConditionalWith ? "with" : "against") + " " + (fConditionField != null ? fConditionField.getName() : "<unknown>"));
		} else {
			out.println(indent + "isConditional  = false");
		}
	}

	@Override
	public String toString() {
	    return "field " + getName();
	}
}

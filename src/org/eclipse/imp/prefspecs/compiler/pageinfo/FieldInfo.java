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

package org.eclipse.imp.prefspecs.compiler.pageinfo;

import org.eclipse.ui.console.MessageConsoleStream;

public class FieldInfo {
	/**
	 * The preferences page with which this field
	 * is associated
	 */
	protected PreferencesPageInfo parentPage = null;
	
	/**
	 * The name of the field
	 */
	protected String name = null;

	/**
	 * If non-null, the text to be displayed as a tooltip for this field
	 */
	protected String toolTipText = null;

    /**
     * True iff this field has an "is removable" specification; if false,
     * the value of isRemovable is irrelevant.
     */
    protected boolean hasRemovableSpec = false;

	/**
	 * Whether concrete instances of this field be removed by default
	 */
	protected boolean isRemovable = false;

	/**
	 * If non-null, the label to use for the widget corresponding to this field
	 */
	protected String optLabel = null;

	/**
	 * Whether this field is enabled conditionally based on another
	 * (boolean) field
	 */
	protected boolean isConditional = false;

	/**
	 * Whether this field, if isConditional, is is enabled when the
	 * condition field is true (if false implies that this field is
	 * enabled when the condition field is false)
	 */	
	protected boolean conditionalWith = true;

	/**
	 * The boolean field based on which this field is enabled (or not),
	 * if isConditional and depending on conditionalWith
	 */
	protected BooleanFieldInfo conditionField = null;

	public FieldInfo(PreferencesPageInfo parentPage, String name) {
		// All fields must have a parent page
		if (parentPage == null) {
			throw new IllegalArgumentException("FieldInfo(..): parent page is null; not allowed");		
		}

		// All fields must have a non-null name
		if (name == null) {
			throw new IllegalArgumentException("FieldInfo(..): name is null; not allowed");		
		}

		// Don't worry about other conditions, such as uniqueness of field
		// name within page; may be checked before or after this point
		
		// Okay
		this.parentPage = parentPage;
		this.name = name;
		parentPage.addVirtualFieldInfo(this);
	}

	//
	// Name is only defined through the constructor,
	// so only "get" methods are defined for those
	//
	
	public PreferencesPageInfo getParentPage() {
		return parentPage;
	}
	
	public String getName() {
		return name;
	}

	public boolean hasRemovableSpec() {
	    return this.hasRemovableSpec;
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
		return isRemovable;
	}

	/**
	 * @param isRemovable
	 */
	public void setIsRemovable(boolean isRemovable) {
		this.isRemovable = isRemovable;
		this.hasRemovableSpec = true;
	}

	public String getLabel() {
	    return optLabel;
	}

	public void setLabel(String newLabel) {
	    this.optLabel = newLabel;
	}

	public String getToolTipText() {
        return toolTipText;
    }

    public void setToolTipText(String toolTipText) {
        this.toolTipText= toolTipText;
    }

	public boolean getIsConditional() {
		return isConditional;
	}
	
	public void setIsConditional(boolean b) {
		isConditional = b;
	}

	public boolean getConditionalWith() {
		return conditionalWith;
	}
	
	public void setConditionalWith(boolean b) {
		conditionalWith = b;
	}

	public BooleanFieldInfo getConditionField() {
		return conditionField;
	}
	
	public void setConditionField(BooleanFieldInfo vbf) {
		conditionField = vbf;
	}

	//
	// For reporting on the contents of the field
	//
	public void dump(String prefix, MessageConsoleStream out) {
		String indent = prefix + "  ";
		out.println(prefix + "Field '" + getName() + "'");
		out.println(indent + "parent page = " + getParentPage().getPageName());
		out.println(indent + "isRemovable = " + isRemovable);
		if (isConditional) {
			out.println(indent + "isConditional " + (conditionalWith ? "with" : "against") + " " + (conditionField != null ? conditionField.getName() : "<unknown>"));
		} else {
			out.println(indent + "isConditional  = false");
		}
	}
}

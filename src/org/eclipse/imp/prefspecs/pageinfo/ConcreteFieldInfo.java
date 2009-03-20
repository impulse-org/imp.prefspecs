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

package org.eclipse.imp.prefspecs.pageinfo;

import org.eclipse.imp.preferences.IPreferencesService;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * Instances of this class contain the info for concrete (per-page) instances
 * of preference fields.
 * @author suttons@us.ibm.com
 */
public class ConcreteFieldInfo
{
	/**
	 * The virtual field for which the concrete field
	 * is a representation
	 */
	protected VirtualFieldInfo vFieldInfo = null;
	
	
	/**
	 * The preferences tab on which this field occurs
	 */
	protected PreferencesTabInfo parentTab = null;
	

	/**
	 * The name of the field
	 */
	protected String name = null;
	
	/**
	 * Whether instances of this field can be edited by default
	 */
	protected boolean isEditable = true;

	/**
	 * Whether instances of this field (that is, their values)
	 * can be removed by default
	 */
	protected boolean isRemovable = false;

    /**
     * The widget label, if explicitly specified in the field properties
     */
    protected String optLabel = null;


	/**
	 * Whether instances of this field have a "special" value
	 */
	protected boolean hasSpecialValue = false;
	
	
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
	protected VirtualBooleanFieldInfo conditionField = null;
	
	
	public ConcreteFieldInfo(VirtualFieldInfo vFieldInfo, PreferencesTabInfo parentTab)
	{
		if (vFieldInfo == null) {
			throw new IllegalArgumentException(
				"ConcreteFieldInfo(..	):  virtual field is null; not allowed");		
		}
		if (parentTab == null) {
			throw new IllegalArgumentException(
				"ConcreteFieldInfo(..):  parent tab is null; not allowed");		
		}
		
		// Don't worry about other conditions, such as uniqueness of field
		// name within page; may be checked before or after this point
		
		// Okay
		this.parentTab = parentTab;
		this.vFieldInfo = vFieldInfo;
		this.name = vFieldInfo.getName();
		
		this.isEditable = vFieldInfo.getIsEditable();
		this.isRemovable = vFieldInfo.getIsRemovable();
		this.optLabel = vFieldInfo.getLabel();

		this.hasSpecialValue = vFieldInfo.getHasSpecialValue();
		
		parentTab.add(this);
		vFieldInfo.addConcreteFieldInfo(this);
	}
	
	
//	public ConcreteFieldInfo(PreferencesTabInfo parentTab, String name) {
//		if (parentTab == null) {
//			throw new IllegalArgumentException(
//				"ConcreteFieldInfo(..):  name is null; not allowed");		
//		}
//		if (parentTab == null) {
//			throw new IllegalArgumentException(
//				"ConcreteFieldInfo(..):  parent tab is null; not allowed");		
//		}
//		
//		// Okay
//		this.parentTab = parentTab;
//		this.name = name;
//		parentTab.add(this);	// why not???
//		
//	}
	
	
	
	// TODO:  Consider adding other constructors to enable attribute values to be set
	
	//
	// Name and parent tab are only defined through the constuctor,
	// so only "get" methods are defined for those
	//
	
	public PreferencesTabInfo getParentTab() {
		return parentTab;
	}
	
	public String getName() {
		return name;
	}
	
	
	
	public boolean getIsEditable() {
		return 	isEditable;
	}

	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}


	
	/**
	 * Whether the values of fields on this tab can, by default, be
	 * removed.  Removal of a field value on one tab triggers inheritance
	 * of the value from the corresponding field on the next higher level.
	 * Individual fields may override this setting, except that it is
	 * always false on the default tab (from which there is no higher tab).
	 * 
	 * @return	False for the default tab; the set value of isRemovable otherwise
	 */
	public boolean getIsRemovable() {
		if (getName().equals(IPreferencesService.DEFAULT_LEVEL))
			return false;
		return isRemovable;
	}
	
	
	/**
	 * Constraint:  For the default tab isRemovable	 must be false
	 * 
	 * @param isRemovable
	 */
	public void setIsRemovable(boolean isRemovable) {
		if (getName().equals(IPreferencesService.DEFAULT_LEVEL))
			if (isRemovable) {
				throw new IllegalArgumentException(
					"ConcreteFieldInfo.setIsRemovable(..):  cannot set isRemovable ");
			}
		this.isRemovable = isRemovable;
	}


	public String getLabel() {
	    return optLabel;
	}

	public void setLabel(String newLabel) {
	    this.optLabel = newLabel;
	}


	public boolean getHasSpecialValue() {
		return 	hasSpecialValue;
	}

	public void setHasSpecialValue(boolean hasSpecialValue) {
		this.hasSpecialValue = hasSpecialValue;
	}


	public String getToolTip() {
	    return this.vFieldInfo.getToolTipText();
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
	
	
	public VirtualBooleanFieldInfo getConditionField() {
		return conditionField;
	}
	
	public void setConditionField(VirtualBooleanFieldInfo vbf) {
		conditionField = vbf;
	}
	
	
	//
	// For reporting on the contents of the field
	//
	public void dump(String prefix, MessageConsoleStream out) {
		String indent = prefix + "  ";
		out.println(prefix + "Field '" + getName() + "'");
		out.println(indent + "parent page = " + getParentTab().getName());
		out.println(indent + "isEditable  = " + isEditable);
		out.println(indent + "isRemovable = " + isRemovable);
		out.println(indent + "hasSpecial = " + hasSpecialValue);
		if (isConditional) {
			out.println(indent + "isConditional " + (conditionalWith ? "with" : "against") + " " + (conditionField != null ? conditionField.getName() : "<unknown>"));
		} else {
			out.println(indent + "isConditional  = false");
		}
	}
}

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.ui.console.MessageConsoleStream;

public class PreferencesPageInfo extends PreferenceTabContainerBase {
	private String name = null;

	private List<FieldInfo> virtualFields = new ArrayList<FieldInfo>();

	private boolean noDetails = false;

	public PreferencesPageInfo(String name) {
		this.name = name;
	}

	public void setPageName(String name) {
		this.name = name;
	}

	public String getPageName() {
		return name;
	}

	public boolean getNoDetails() {
        return noDetails;
    }

    public void setNoDetails(boolean noDetails) {
        this.noDetails= noDetails;
    }

	public void addVirtualFieldInfo(FieldInfo vField) {
		if (vField == null || virtualFields.contains(vField))
			return;
		virtualFields.add(vField);
	}

	public void removeVirtualFieldInfo(FieldInfo vField) {
		if (vField == null)
			return;
		virtualFields.remove(vField);
	}

	public Iterator<FieldInfo> getFieldInfos() {
		return virtualFields.iterator(); 
	}

	public boolean hasVirtualFieldInfo(String name) {
		if (name == null) {
			throw new IllegalArgumentException(
					"PreferencePageInfo.hasVirtualField(String):  given name is null; not allowed");
		}
		for (int i = 0; i < virtualFields.size(); i++) {
			FieldInfo field = virtualFields.get(i);
			if (field == null) continue;
			String fieldName = field.getName();
			if (name.equals(fieldName))
				return true;
		}
		return false;
	}

	public boolean hasVirtualFieldInfo(FieldInfo vField) {
		if (vField == null) {
			throw new IllegalArgumentException(
					"PreferencePageInfo.hasVirtualField(VirtualFieldInfo):  given field is null; not allowed");
		}
		return virtualFields.contains(vField);
	}

	//
	// For reporting on the contents of the page
	//

	public static String INDENT = "";
	
	public void dump(MessageConsoleStream out) {
		String indent = "  ";
		out.println("\n\t%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		out.println("PreferencesPageInfo:  '" + getPageName() + "'");
		
		out.println("Virtual fields:");
		for(FieldInfo vField: virtualFields) {
			vField.dump(indent, out);
		}

		out.println("Tabs:");
		for(PreferencesTabInfo tab: fTabs) {
			tab.dump(indent, out);
		}
		out.println("\t%%%%\t%%%%\t%%%%\n");
	}
}

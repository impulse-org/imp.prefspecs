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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.ui.console.MessageConsoleStream;

public class PageInfo extends TabContainerBase implements IPageMemberContainer {
	private final String name;

	private final List<IPageMember> fMembers = new LinkedList<IPageMember>();

	private boolean noDetails = false;

	public PageInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean getNoDetails() {
        return noDetails;
    }

    public void setNoDetails(boolean noDetails) {
        this.noDetails= noDetails;
    }

	public void addChild(IPageMember m) {
		if (m == null || fMembers.contains(m))
			return;
		fMembers.add(m);
	}

	public List<IPageMember> getChildren() {
		return Collections.unmodifiableList(fMembers);
	}

	//
	// For reporting on the contents of the page
	//
	public void dump(MessageConsoleStream out) {
		String indent = "  ";
		out.println("\n\t%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		out.println("PageInfo: '" + getName() + "'");

		out.println("Members:");
		for(IPageMember m: fMembers) {
			m.dump(indent, out);
		}

		out.println("Tabs:");
		for(TabInfo tab: fTabs) {
			tab.dump(indent, out);
		}
		out.println("\t%%%%\t%%%%\t%%%%\n");
	}

	@Override
	public String toString() {
	    return "page " + getName();
	}
}

/*******************************************************************************
* Copyright (c) 2010 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
*******************************************************************************/

package org.eclipse.imp.prefspecs.compiler.model;

import org.eclipse.ui.console.MessageConsoleStream;

/**
 * Represents the entities that can be a member of a preference page, namely, a
 * field or a group of fields.
 * @author rfuhrer@watson.ibm.com
 */
public interface IPageMember {
    /**
     * @return the parent of this page member, which is either a page or a field group
     */
    public IPageMemberContainer getParent();

    public void dump(String prefix, MessageConsoleStream out);
}

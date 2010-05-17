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

import java.util.List;

/**
 * Represents the entities that can contain preference page members, namely,
 * a page or a field group
 * @author rfuhrer@watson.ibm.com
 */
public interface IPageMemberContainer {
    /**
     * @return the name of this container
     */
    public String getName();

    /**
     * Add a page member to this container
     */
    public void addChild(IPageMember m);

    /**
     * @return the list of direct children of this container
     */
    public List<IPageMember> getChildren();
}

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

import org.eclipse.imp.prefspecs.compiler.codegen.DirListFieldCodeGenerator;
import org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator;

/**
 * @author sutton
 */
public class DirListFieldInfo extends StringFieldInfo {
	public DirListFieldInfo(IPageMemberContainer parentPage, String name) {
		this(parentPage, name, null);
	}
	
	public DirListFieldInfo(IPageMemberContainer parentPage, String name, String defValue) {
		super(parentPage, name);
		this.defaultValue = defValue;
	}

	@Override
	public FieldCodeGenerator getCodeGenerator() {
	    return new DirListFieldCodeGenerator(this);
	}
}

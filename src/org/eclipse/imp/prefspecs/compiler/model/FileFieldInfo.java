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
import org.eclipse.imp.prefspecs.compiler.codegen.FileFieldCodeGenerator;

/**
 * @author sutton
 */
public class FileFieldInfo extends StringFieldInfo {
    public FileFieldInfo(IPageMemberContainer parentPage, String name) {
        this(parentPage, name, null);
    }

    public FileFieldInfo(IPageMemberContainer parentPage, String name, String defValue) {
        super(parentPage, name, defValue);
    }

    @Override
    public FieldCodeGenerator getCodeGenerator() {
        return new FileFieldCodeGenerator(this);
    }
}

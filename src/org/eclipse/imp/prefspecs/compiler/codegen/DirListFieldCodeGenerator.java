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

package org.eclipse.imp.prefspecs.compiler.codegen;

import org.eclipse.imp.prefspecs.compiler.model.DirListFieldInfo;

/**
 * @author rfuhrer@watson.ibm.com
 */
public class DirListFieldCodeGenerator extends StringFieldCodeGenerator {
    private final DirListFieldInfo fDirListFieldInfo;

    /**
     * @param fieldInfo
     */
    public DirListFieldCodeGenerator(DirListFieldInfo fieldInfo) {
        super(fieldInfo);
        fDirListFieldInfo= fieldInfo;
    }

    @Override
    public String getFieldEditorTypeName() {
        return "DirectoryListFieldEditor";
    }

    @Override
    protected String getFactoryMethodName() {
        return "makeNewDirectoryListField";
    }
}

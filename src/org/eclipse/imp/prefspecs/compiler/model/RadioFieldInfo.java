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

import org.eclipse.imp.prefspecs.compiler.IEnumValueSource;
import org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator;
import org.eclipse.imp.prefspecs.compiler.codegen.RadioFieldCodeGenerator;

public class RadioFieldInfo extends EnumFieldInfo {
    public RadioFieldInfo(IPageMemberContainer parentPage, String name) {
        this(parentPage, name, null);
    }

    public RadioFieldInfo(IPageMemberContainer parentPage, String name, IEnumValueSource valueSource) {
        super(parentPage, name, valueSource);
    }

    @Override
    public FieldCodeGenerator getCodeGenerator() {
        return new RadioFieldCodeGenerator(this);
    }
}

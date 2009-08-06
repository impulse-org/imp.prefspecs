
////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2007 IBM Corporation.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
//Contributors:
//    Stan Sutton (suttons@us.ibm.com) - initial API and implementation
//    Robert Fuhrer (rfuhrer@watson.ibm.com)
////////////////////////////////////////////////////////////////////////////////

package org.eclipse.imp.prefspecs.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Hashtable;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public interface Visitor extends IAstVisitor
{
    boolean visit(ASTNode n);
    void endVisit(ASTNode n);

    boolean visit(ASTNodeToken n);
    void endVisit(ASTNodeToken n);

    boolean visit(prefSpecs n);
    void endVisit(prefSpecs n);

    boolean visit(optPackageSpec n);
    void endVisit(optPackageSpec n);

    boolean visit(packageName n);
    void endVisit(packageName n);

    boolean visit(optDetailsSpec n);
    void endVisit(optDetailsSpec n);

    boolean visit(topLevelItemList n);
    void endVisit(topLevelItemList n);

    boolean visit(typeSpec n);
    void endVisit(typeSpec n);

    boolean visit(pageSpec n);
    void endVisit(pageSpec n);

    boolean visit(pageName n);
    void endVisit(pageName n);

    boolean visit(pagePath n);
    void endVisit(pagePath n);

    boolean visit(pageBody n);
    void endVisit(pageBody n);

    boolean visit(optionalSpecs n);
    void endVisit(optionalSpecs n);

    boolean visit(tabsSpec n);
    void endVisit(tabsSpec n);

    boolean visit(tabSpecList n);
    void endVisit(tabSpecList n);

    boolean visit(defaultTabSpec n);
    void endVisit(defaultTabSpec n);

    boolean visit(configurationTabSpec n);
    void endVisit(configurationTabSpec n);

    boolean visit(instanceTabSpec n);
    void endVisit(instanceTabSpec n);

    boolean visit(projectTabSpec n);
    void endVisit(projectTabSpec n);

    boolean visit(fieldsSpec n);
    void endVisit(fieldsSpec n);

    boolean visit(fieldSpecs n);
    void endVisit(fieldSpecs n);

    boolean visit(booleanFieldSpec n);
    void endVisit(booleanFieldSpec n);

    boolean visit(colorFieldSpec n);
    void endVisit(colorFieldSpec n);

    boolean visit(comboFieldSpec n);
    void endVisit(comboFieldSpec n);

    boolean visit(directoryFieldSpec n);
    void endVisit(directoryFieldSpec n);

    boolean visit(dirListFieldSpec n);
    void endVisit(dirListFieldSpec n);

    boolean visit(doubleFieldSpec n);
    void endVisit(doubleFieldSpec n);

    boolean visit(fileFieldSpec n);
    void endVisit(fileFieldSpec n);

    boolean visit(fontFieldSpec n);
    void endVisit(fontFieldSpec n);

    boolean visit(intFieldSpec n);
    void endVisit(intFieldSpec n);

    boolean visit(radioFieldSpec n);
    void endVisit(radioFieldSpec n);

    boolean visit(stringFieldSpec n);
    void endVisit(stringFieldSpec n);

    boolean visit(booleanFieldPropertySpecs n);
    void endVisit(booleanFieldPropertySpecs n);

    boolean visit(colorFieldPropertySpecs n);
    void endVisit(colorFieldPropertySpecs n);

    boolean visit(comboFieldPropertySpecs n);
    void endVisit(comboFieldPropertySpecs n);

    boolean visit(directoryFieldPropertySpecs n);
    void endVisit(directoryFieldPropertySpecs n);

    boolean visit(dirlistFieldPropertySpecs n);
    void endVisit(dirlistFieldPropertySpecs n);

    boolean visit(doubleFieldPropertySpecs n);
    void endVisit(doubleFieldPropertySpecs n);

    boolean visit(fileFieldPropertySpecs n);
    void endVisit(fileFieldPropertySpecs n);

    boolean visit(fontFieldPropertySpecs n);
    void endVisit(fontFieldPropertySpecs n);

    boolean visit(intFieldPropertySpecs n);
    void endVisit(intFieldPropertySpecs n);

    boolean visit(radioFieldPropertySpecs n);
    void endVisit(radioFieldPropertySpecs n);

    boolean visit(stringFieldPropertySpecs n);
    void endVisit(stringFieldPropertySpecs n);

    boolean visit(generalSpecList n);
    void endVisit(generalSpecList n);

    boolean visit(isEditableSpec n);
    void endVisit(isEditableSpec n);

    boolean visit(isRemovableSpec n);
    void endVisit(isRemovableSpec n);

    boolean visit(optLabelSpec n);
    void endVisit(optLabelSpec n);

    boolean visit(optToolTipSpec n);
    void endVisit(optToolTipSpec n);

    boolean visit(booleanSpecificSpecList n);
    void endVisit(booleanSpecificSpecList n);

    boolean visit(booleanSpecialSpec n);
    void endVisit(booleanSpecialSpec n);

    boolean visit(booleanDefValueSpec n);
    void endVisit(booleanDefValueSpec n);

    boolean visit(colorSpecificSpecList n);
    void endVisit(colorSpecificSpecList n);

    boolean visit(colorDefValueSpec n);
    void endVisit(colorDefValueSpec n);

    boolean visit(comboSpecificSpecList n);
    void endVisit(comboSpecificSpecList n);

    boolean visit(enumDefValueSpec n);
    void endVisit(enumDefValueSpec n);

    boolean visit(doubleSpecificSpecList n);
    void endVisit(doubleSpecificSpecList n);

    boolean visit(doubleRangeSpec n);
    void endVisit(doubleRangeSpec n);

    boolean visit(doubleDefValueSpec n);
    void endVisit(doubleDefValueSpec n);

    boolean visit(fontSpecificSpecList n);
    void endVisit(fontSpecificSpecList n);

    boolean visit(fontDefValueSpec n);
    void endVisit(fontDefValueSpec n);

    boolean visit(intSpecificSpecList n);
    void endVisit(intSpecificSpecList n);

    boolean visit(intRangeSpec n);
    void endVisit(intRangeSpec n);

    boolean visit(intSpecialSpec n);
    void endVisit(intSpecialSpec n);

    boolean visit(intDefValueSpec n);
    void endVisit(intDefValueSpec n);

    boolean visit(radioSpecificSpecList n);
    void endVisit(radioSpecificSpecList n);

    boolean visit(valuesSpec n);
    void endVisit(valuesSpec n);

    boolean visit(staticOrDynamicValues n);
    void endVisit(staticOrDynamicValues n);

    boolean visit(columnsSpec n);
    void endVisit(columnsSpec n);

    boolean visit(labelledStringValueList n);
    void endVisit(labelledStringValueList n);

    boolean visit(labelledStringValue n);
    void endVisit(labelledStringValue n);

    boolean visit(stringSpecificSpecList n);
    void endVisit(stringSpecificSpecList n);

    boolean visit(stringSpecialSpec n);
    void endVisit(stringSpecialSpec n);

    boolean visit(stringDefValueSpec n);
    void endVisit(stringDefValueSpec n);

    boolean visit(stringValidatorSpec n);
    void endVisit(stringValidatorSpec n);

    boolean visit(optConditionalSpec n);
    void endVisit(optConditionalSpec n);

    boolean visit(identifier n);
    void endVisit(identifier n);

    boolean visit(stringValue n);
    void endVisit(stringValue n);

    boolean visit(customSpec n);
    void endVisit(customSpec n);

    boolean visit(customRules n);
    void endVisit(customRules n);

    boolean visit(customRule n);
    void endVisit(customRule n);

    boolean visit(newPropertySpecs n);
    void endVisit(newPropertySpecs n);

    boolean visit(conditionalsSpec n);
    void endVisit(conditionalsSpec n);

    boolean visit(onOff0 n);
    void endVisit(onOff0 n);

    boolean visit(onOff1 n);
    void endVisit(onOff1 n);

    boolean visit(inout0 n);
    void endVisit(inout0 n);

    boolean visit(inout1 n);
    void endVisit(inout1 n);

    boolean visit(fontStyle0 n);
    void endVisit(fontStyle0 n);

    boolean visit(fontStyle1 n);
    void endVisit(fontStyle1 n);

    boolean visit(fontStyle2 n);
    void endVisit(fontStyle2 n);

    boolean visit(typeOrValuesSpec0 n);
    void endVisit(typeOrValuesSpec0 n);

    boolean visit(typeOrValuesSpec1 n);
    void endVisit(typeOrValuesSpec1 n);

    boolean visit(stringEmptySpec0 n);
    void endVisit(stringEmptySpec0 n);

    boolean visit(stringEmptySpec1 n);
    void endVisit(stringEmptySpec1 n);

    boolean visit(conditionType0 n);
    void endVisit(conditionType0 n);

    boolean visit(conditionType1 n);
    void endVisit(conditionType1 n);

    boolean visit(booleanValue0 n);
    void endVisit(booleanValue0 n);

    boolean visit(booleanValue1 n);
    void endVisit(booleanValue1 n);

    boolean visit(signedNumber0 n);
    void endVisit(signedNumber0 n);

    boolean visit(signedNumber1 n);
    void endVisit(signedNumber1 n);

    boolean visit(sign0 n);
    void endVisit(sign0 n);

    boolean visit(sign1 n);
    void endVisit(sign1 n);

    boolean visit(tab0 n);
    void endVisit(tab0 n);

    boolean visit(tab1 n);
    void endVisit(tab1 n);

    boolean visit(tab2 n);
    void endVisit(tab2 n);

    boolean visit(tab3 n);
    void endVisit(tab3 n);

    boolean visit(typeCustomSpecs0 n);
    void endVisit(typeCustomSpecs0 n);

    boolean visit(typeCustomSpecs1 n);
    void endVisit(typeCustomSpecs1 n);

    boolean visit(conditionalSpecs0 n);
    void endVisit(conditionalSpecs0 n);

    boolean visit(conditionalSpecs1 n);
    void endVisit(conditionalSpecs1 n);

    boolean visit(conditionalSpec0 n);
    void endVisit(conditionalSpec0 n);

    boolean visit(conditionalSpec1 n);
    void endVisit(conditionalSpec1 n);

}



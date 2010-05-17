
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

    boolean visit(groupSpec n);
    void endVisit(groupSpec n);

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

    boolean visit(conditionalsSpec n);
    void endVisit(conditionalsSpec n);

    boolean visit(onOff__ON n);
    void endVisit(onOff__ON n);

    boolean visit(onOff__OFF n);
    void endVisit(onOff__OFF n);

    boolean visit(inout__IN n);
    void endVisit(inout__IN n);

    boolean visit(inout__OUT n);
    void endVisit(inout__OUT n);

    boolean visit(fontStyle__NORMAL n);
    void endVisit(fontStyle__NORMAL n);

    boolean visit(fontStyle__BOLD n);
    void endVisit(fontStyle__BOLD n);

    boolean visit(fontStyle__ITALIC n);
    void endVisit(fontStyle__ITALIC n);

    boolean visit(typeOrValuesSpec__TYPE_identifier_SEMICOLON n);
    void endVisit(typeOrValuesSpec__TYPE_identifier_SEMICOLON n);

    boolean visit(typeOrValuesSpec__valuesSpec_SEMICOLON n);
    void endVisit(typeOrValuesSpec__valuesSpec_SEMICOLON n);

    boolean visit(stringEmptySpec__EMPTYALLOWED_FALSE_SEMICOLON n);
    void endVisit(stringEmptySpec__EMPTYALLOWED_FALSE_SEMICOLON n);

    boolean visit(stringEmptySpec__EMPTYALLOWED_TRUE_stringValue_SEMICOLON n);
    void endVisit(stringEmptySpec__EMPTYALLOWED_TRUE_stringValue_SEMICOLON n);

    boolean visit(conditionType__IF n);
    void endVisit(conditionType__IF n);

    boolean visit(conditionType__UNLESS n);
    void endVisit(conditionType__UNLESS n);

    boolean visit(booleanValue__TRUE n);
    void endVisit(booleanValue__TRUE n);

    boolean visit(booleanValue__FALSE n);
    void endVisit(booleanValue__FALSE n);

    boolean visit(signedNumber__INTEGER n);
    void endVisit(signedNumber__INTEGER n);

    boolean visit(signedNumber__sign_INTEGER n);
    void endVisit(signedNumber__sign_INTEGER n);

    boolean visit(sign__PLUS n);
    void endVisit(sign__PLUS n);

    boolean visit(sign__MINUS n);
    void endVisit(sign__MINUS n);

    boolean visit(conditionalSpecs__conditionalSpec_SEMICOLON n);
    void endVisit(conditionalSpecs__conditionalSpec_SEMICOLON n);

    boolean visit(conditionalSpecs__conditionalSpecs_conditionalSpec_SEMICOLON n);
    void endVisit(conditionalSpecs__conditionalSpecs_conditionalSpec_SEMICOLON n);

    boolean visit(conditionalSpec__identifier_WITH_identifier n);
    void endVisit(conditionalSpec__identifier_WITH_identifier n);

    boolean visit(conditionalSpec__identifier_AGAINST_identifier n);
    void endVisit(conditionalSpec__identifier_AGAINST_identifier n);

}



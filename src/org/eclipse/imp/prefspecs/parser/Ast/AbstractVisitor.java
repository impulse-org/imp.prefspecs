
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

public abstract class AbstractVisitor implements Visitor
{
    public abstract void unimplementedVisitor(String s);

    public boolean preVisit(IAst element) { return true; }

    public void postVisit(IAst element) {}

    public boolean visit(ASTNodeToken n) { unimplementedVisitor("visit(ASTNodeToken)"); return true; }
    public void endVisit(ASTNodeToken n) { unimplementedVisitor("endVisit(ASTNodeToken)"); }

    public boolean visit(prefSpecs n) { unimplementedVisitor("visit(prefSpecs)"); return true; }
    public void endVisit(prefSpecs n) { unimplementedVisitor("endVisit(prefSpecs)"); }

    public boolean visit(optPackageSpec n) { unimplementedVisitor("visit(optPackageSpec)"); return true; }
    public void endVisit(optPackageSpec n) { unimplementedVisitor("endVisit(optPackageSpec)"); }

    public boolean visit(packageName n) { unimplementedVisitor("visit(packageName)"); return true; }
    public void endVisit(packageName n) { unimplementedVisitor("endVisit(packageName)"); }

    public boolean visit(optDetailsSpec n) { unimplementedVisitor("visit(optDetailsSpec)"); return true; }
    public void endVisit(optDetailsSpec n) { unimplementedVisitor("endVisit(optDetailsSpec)"); }

    public boolean visit(topLevelItemList n) { unimplementedVisitor("visit(topLevelItemList)"); return true; }
    public void endVisit(topLevelItemList n) { unimplementedVisitor("endVisit(topLevelItemList)"); }

    public boolean visit(typeSpec n) { unimplementedVisitor("visit(typeSpec)"); return true; }
    public void endVisit(typeSpec n) { unimplementedVisitor("endVisit(typeSpec)"); }

    public boolean visit(pageSpec n) { unimplementedVisitor("visit(pageSpec)"); return true; }
    public void endVisit(pageSpec n) { unimplementedVisitor("endVisit(pageSpec)"); }

    public boolean visit(pageName n) { unimplementedVisitor("visit(pageName)"); return true; }
    public void endVisit(pageName n) { unimplementedVisitor("endVisit(pageName)"); }

    public boolean visit(pagePath n) { unimplementedVisitor("visit(pagePath)"); return true; }
    public void endVisit(pagePath n) { unimplementedVisitor("endVisit(pagePath)"); }

    public boolean visit(pageBody n) { unimplementedVisitor("visit(pageBody)"); return true; }
    public void endVisit(pageBody n) { unimplementedVisitor("endVisit(pageBody)"); }

    public boolean visit(optionalSpecs n) { unimplementedVisitor("visit(optionalSpecs)"); return true; }
    public void endVisit(optionalSpecs n) { unimplementedVisitor("endVisit(optionalSpecs)"); }

    public boolean visit(tabsSpec n) { unimplementedVisitor("visit(tabsSpec)"); return true; }
    public void endVisit(tabsSpec n) { unimplementedVisitor("endVisit(tabsSpec)"); }

    public boolean visit(tabSpecList n) { unimplementedVisitor("visit(tabSpecList)"); return true; }
    public void endVisit(tabSpecList n) { unimplementedVisitor("endVisit(tabSpecList)"); }

    public boolean visit(defaultTabSpec n) { unimplementedVisitor("visit(defaultTabSpec)"); return true; }
    public void endVisit(defaultTabSpec n) { unimplementedVisitor("endVisit(defaultTabSpec)"); }

    public boolean visit(configurationTabSpec n) { unimplementedVisitor("visit(configurationTabSpec)"); return true; }
    public void endVisit(configurationTabSpec n) { unimplementedVisitor("endVisit(configurationTabSpec)"); }

    public boolean visit(instanceTabSpec n) { unimplementedVisitor("visit(instanceTabSpec)"); return true; }
    public void endVisit(instanceTabSpec n) { unimplementedVisitor("endVisit(instanceTabSpec)"); }

    public boolean visit(projectTabSpec n) { unimplementedVisitor("visit(projectTabSpec)"); return true; }
    public void endVisit(projectTabSpec n) { unimplementedVisitor("endVisit(projectTabSpec)"); }

    public boolean visit(fieldsSpec n) { unimplementedVisitor("visit(fieldsSpec)"); return true; }
    public void endVisit(fieldsSpec n) { unimplementedVisitor("endVisit(fieldsSpec)"); }

    public boolean visit(fieldSpecs n) { unimplementedVisitor("visit(fieldSpecs)"); return true; }
    public void endVisit(fieldSpecs n) { unimplementedVisitor("endVisit(fieldSpecs)"); }

    public boolean visit(booleanFieldSpec n) { unimplementedVisitor("visit(booleanFieldSpec)"); return true; }
    public void endVisit(booleanFieldSpec n) { unimplementedVisitor("endVisit(booleanFieldSpec)"); }

    public boolean visit(colorFieldSpec n) { unimplementedVisitor("visit(colorFieldSpec)"); return true; }
    public void endVisit(colorFieldSpec n) { unimplementedVisitor("endVisit(colorFieldSpec)"); }

    public boolean visit(comboFieldSpec n) { unimplementedVisitor("visit(comboFieldSpec)"); return true; }
    public void endVisit(comboFieldSpec n) { unimplementedVisitor("endVisit(comboFieldSpec)"); }

    public boolean visit(directoryFieldSpec n) { unimplementedVisitor("visit(directoryFieldSpec)"); return true; }
    public void endVisit(directoryFieldSpec n) { unimplementedVisitor("endVisit(directoryFieldSpec)"); }

    public boolean visit(dirListFieldSpec n) { unimplementedVisitor("visit(dirListFieldSpec)"); return true; }
    public void endVisit(dirListFieldSpec n) { unimplementedVisitor("endVisit(dirListFieldSpec)"); }

    public boolean visit(doubleFieldSpec n) { unimplementedVisitor("visit(doubleFieldSpec)"); return true; }
    public void endVisit(doubleFieldSpec n) { unimplementedVisitor("endVisit(doubleFieldSpec)"); }

    public boolean visit(fileFieldSpec n) { unimplementedVisitor("visit(fileFieldSpec)"); return true; }
    public void endVisit(fileFieldSpec n) { unimplementedVisitor("endVisit(fileFieldSpec)"); }

    public boolean visit(fontFieldSpec n) { unimplementedVisitor("visit(fontFieldSpec)"); return true; }
    public void endVisit(fontFieldSpec n) { unimplementedVisitor("endVisit(fontFieldSpec)"); }

    public boolean visit(intFieldSpec n) { unimplementedVisitor("visit(intFieldSpec)"); return true; }
    public void endVisit(intFieldSpec n) { unimplementedVisitor("endVisit(intFieldSpec)"); }

    public boolean visit(radioFieldSpec n) { unimplementedVisitor("visit(radioFieldSpec)"); return true; }
    public void endVisit(radioFieldSpec n) { unimplementedVisitor("endVisit(radioFieldSpec)"); }

    public boolean visit(stringFieldSpec n) { unimplementedVisitor("visit(stringFieldSpec)"); return true; }
    public void endVisit(stringFieldSpec n) { unimplementedVisitor("endVisit(stringFieldSpec)"); }

    public boolean visit(booleanFieldPropertySpecs n) { unimplementedVisitor("visit(booleanFieldPropertySpecs)"); return true; }
    public void endVisit(booleanFieldPropertySpecs n) { unimplementedVisitor("endVisit(booleanFieldPropertySpecs)"); }

    public boolean visit(colorFieldPropertySpecs n) { unimplementedVisitor("visit(colorFieldPropertySpecs)"); return true; }
    public void endVisit(colorFieldPropertySpecs n) { unimplementedVisitor("endVisit(colorFieldPropertySpecs)"); }

    public boolean visit(comboFieldPropertySpecs n) { unimplementedVisitor("visit(comboFieldPropertySpecs)"); return true; }
    public void endVisit(comboFieldPropertySpecs n) { unimplementedVisitor("endVisit(comboFieldPropertySpecs)"); }

    public boolean visit(directoryFieldPropertySpecs n) { unimplementedVisitor("visit(directoryFieldPropertySpecs)"); return true; }
    public void endVisit(directoryFieldPropertySpecs n) { unimplementedVisitor("endVisit(directoryFieldPropertySpecs)"); }

    public boolean visit(dirlistFieldPropertySpecs n) { unimplementedVisitor("visit(dirlistFieldPropertySpecs)"); return true; }
    public void endVisit(dirlistFieldPropertySpecs n) { unimplementedVisitor("endVisit(dirlistFieldPropertySpecs)"); }

    public boolean visit(doubleFieldPropertySpecs n) { unimplementedVisitor("visit(doubleFieldPropertySpecs)"); return true; }
    public void endVisit(doubleFieldPropertySpecs n) { unimplementedVisitor("endVisit(doubleFieldPropertySpecs)"); }

    public boolean visit(fileFieldPropertySpecs n) { unimplementedVisitor("visit(fileFieldPropertySpecs)"); return true; }
    public void endVisit(fileFieldPropertySpecs n) { unimplementedVisitor("endVisit(fileFieldPropertySpecs)"); }

    public boolean visit(fontFieldPropertySpecs n) { unimplementedVisitor("visit(fontFieldPropertySpecs)"); return true; }
    public void endVisit(fontFieldPropertySpecs n) { unimplementedVisitor("endVisit(fontFieldPropertySpecs)"); }

    public boolean visit(intFieldPropertySpecs n) { unimplementedVisitor("visit(intFieldPropertySpecs)"); return true; }
    public void endVisit(intFieldPropertySpecs n) { unimplementedVisitor("endVisit(intFieldPropertySpecs)"); }

    public boolean visit(radioFieldPropertySpecs n) { unimplementedVisitor("visit(radioFieldPropertySpecs)"); return true; }
    public void endVisit(radioFieldPropertySpecs n) { unimplementedVisitor("endVisit(radioFieldPropertySpecs)"); }

    public boolean visit(stringFieldPropertySpecs n) { unimplementedVisitor("visit(stringFieldPropertySpecs)"); return true; }
    public void endVisit(stringFieldPropertySpecs n) { unimplementedVisitor("endVisit(stringFieldPropertySpecs)"); }

    public boolean visit(generalSpecList n) { unimplementedVisitor("visit(generalSpecList)"); return true; }
    public void endVisit(generalSpecList n) { unimplementedVisitor("endVisit(generalSpecList)"); }

    public boolean visit(isEditableSpec n) { unimplementedVisitor("visit(isEditableSpec)"); return true; }
    public void endVisit(isEditableSpec n) { unimplementedVisitor("endVisit(isEditableSpec)"); }

    public boolean visit(isRemovableSpec n) { unimplementedVisitor("visit(isRemovableSpec)"); return true; }
    public void endVisit(isRemovableSpec n) { unimplementedVisitor("endVisit(isRemovableSpec)"); }

    public boolean visit(optLabelSpec n) { unimplementedVisitor("visit(optLabelSpec)"); return true; }
    public void endVisit(optLabelSpec n) { unimplementedVisitor("endVisit(optLabelSpec)"); }

    public boolean visit(optToolTipSpec n) { unimplementedVisitor("visit(optToolTipSpec)"); return true; }
    public void endVisit(optToolTipSpec n) { unimplementedVisitor("endVisit(optToolTipSpec)"); }

    public boolean visit(booleanSpecificSpecList n) { unimplementedVisitor("visit(booleanSpecificSpecList)"); return true; }
    public void endVisit(booleanSpecificSpecList n) { unimplementedVisitor("endVisit(booleanSpecificSpecList)"); }

    public boolean visit(booleanSpecialSpec n) { unimplementedVisitor("visit(booleanSpecialSpec)"); return true; }
    public void endVisit(booleanSpecialSpec n) { unimplementedVisitor("endVisit(booleanSpecialSpec)"); }

    public boolean visit(booleanDefValueSpec n) { unimplementedVisitor("visit(booleanDefValueSpec)"); return true; }
    public void endVisit(booleanDefValueSpec n) { unimplementedVisitor("endVisit(booleanDefValueSpec)"); }

    public boolean visit(colorSpecificSpecList n) { unimplementedVisitor("visit(colorSpecificSpecList)"); return true; }
    public void endVisit(colorSpecificSpecList n) { unimplementedVisitor("endVisit(colorSpecificSpecList)"); }

    public boolean visit(colorDefValueSpec n) { unimplementedVisitor("visit(colorDefValueSpec)"); return true; }
    public void endVisit(colorDefValueSpec n) { unimplementedVisitor("endVisit(colorDefValueSpec)"); }

    public boolean visit(comboSpecificSpecList n) { unimplementedVisitor("visit(comboSpecificSpecList)"); return true; }
    public void endVisit(comboSpecificSpecList n) { unimplementedVisitor("endVisit(comboSpecificSpecList)"); }

    public boolean visit(enumDefValueSpec n) { unimplementedVisitor("visit(enumDefValueSpec)"); return true; }
    public void endVisit(enumDefValueSpec n) { unimplementedVisitor("endVisit(enumDefValueSpec)"); }

    public boolean visit(doubleSpecificSpecList n) { unimplementedVisitor("visit(doubleSpecificSpecList)"); return true; }
    public void endVisit(doubleSpecificSpecList n) { unimplementedVisitor("endVisit(doubleSpecificSpecList)"); }

    public boolean visit(doubleRangeSpec n) { unimplementedVisitor("visit(doubleRangeSpec)"); return true; }
    public void endVisit(doubleRangeSpec n) { unimplementedVisitor("endVisit(doubleRangeSpec)"); }

    public boolean visit(doubleDefValueSpec n) { unimplementedVisitor("visit(doubleDefValueSpec)"); return true; }
    public void endVisit(doubleDefValueSpec n) { unimplementedVisitor("endVisit(doubleDefValueSpec)"); }

    public boolean visit(fontSpecificSpecList n) { unimplementedVisitor("visit(fontSpecificSpecList)"); return true; }
    public void endVisit(fontSpecificSpecList n) { unimplementedVisitor("endVisit(fontSpecificSpecList)"); }

    public boolean visit(fontDefValueSpec n) { unimplementedVisitor("visit(fontDefValueSpec)"); return true; }
    public void endVisit(fontDefValueSpec n) { unimplementedVisitor("endVisit(fontDefValueSpec)"); }

    public boolean visit(intSpecificSpecList n) { unimplementedVisitor("visit(intSpecificSpecList)"); return true; }
    public void endVisit(intSpecificSpecList n) { unimplementedVisitor("endVisit(intSpecificSpecList)"); }

    public boolean visit(intRangeSpec n) { unimplementedVisitor("visit(intRangeSpec)"); return true; }
    public void endVisit(intRangeSpec n) { unimplementedVisitor("endVisit(intRangeSpec)"); }

    public boolean visit(intSpecialSpec n) { unimplementedVisitor("visit(intSpecialSpec)"); return true; }
    public void endVisit(intSpecialSpec n) { unimplementedVisitor("endVisit(intSpecialSpec)"); }

    public boolean visit(intDefValueSpec n) { unimplementedVisitor("visit(intDefValueSpec)"); return true; }
    public void endVisit(intDefValueSpec n) { unimplementedVisitor("endVisit(intDefValueSpec)"); }

    public boolean visit(radioSpecificSpecList n) { unimplementedVisitor("visit(radioSpecificSpecList)"); return true; }
    public void endVisit(radioSpecificSpecList n) { unimplementedVisitor("endVisit(radioSpecificSpecList)"); }

    public boolean visit(valuesSpec n) { unimplementedVisitor("visit(valuesSpec)"); return true; }
    public void endVisit(valuesSpec n) { unimplementedVisitor("endVisit(valuesSpec)"); }

    public boolean visit(staticOrDynamicValues n) { unimplementedVisitor("visit(staticOrDynamicValues)"); return true; }
    public void endVisit(staticOrDynamicValues n) { unimplementedVisitor("endVisit(staticOrDynamicValues)"); }

    public boolean visit(columnsSpec n) { unimplementedVisitor("visit(columnsSpec)"); return true; }
    public void endVisit(columnsSpec n) { unimplementedVisitor("endVisit(columnsSpec)"); }

    public boolean visit(labelledStringValueList n) { unimplementedVisitor("visit(labelledStringValueList)"); return true; }
    public void endVisit(labelledStringValueList n) { unimplementedVisitor("endVisit(labelledStringValueList)"); }

    public boolean visit(labelledStringValue n) { unimplementedVisitor("visit(labelledStringValue)"); return true; }
    public void endVisit(labelledStringValue n) { unimplementedVisitor("endVisit(labelledStringValue)"); }

    public boolean visit(stringSpecificSpecList n) { unimplementedVisitor("visit(stringSpecificSpecList)"); return true; }
    public void endVisit(stringSpecificSpecList n) { unimplementedVisitor("endVisit(stringSpecificSpecList)"); }

    public boolean visit(stringSpecialSpec n) { unimplementedVisitor("visit(stringSpecialSpec)"); return true; }
    public void endVisit(stringSpecialSpec n) { unimplementedVisitor("endVisit(stringSpecialSpec)"); }

    public boolean visit(stringDefValueSpec n) { unimplementedVisitor("visit(stringDefValueSpec)"); return true; }
    public void endVisit(stringDefValueSpec n) { unimplementedVisitor("endVisit(stringDefValueSpec)"); }

    public boolean visit(stringValidatorSpec n) { unimplementedVisitor("visit(stringValidatorSpec)"); return true; }
    public void endVisit(stringValidatorSpec n) { unimplementedVisitor("endVisit(stringValidatorSpec)"); }

    public boolean visit(optConditionalSpec n) { unimplementedVisitor("visit(optConditionalSpec)"); return true; }
    public void endVisit(optConditionalSpec n) { unimplementedVisitor("endVisit(optConditionalSpec)"); }

    public boolean visit(identifier n) { unimplementedVisitor("visit(identifier)"); return true; }
    public void endVisit(identifier n) { unimplementedVisitor("endVisit(identifier)"); }

    public boolean visit(stringValue n) { unimplementedVisitor("visit(stringValue)"); return true; }
    public void endVisit(stringValue n) { unimplementedVisitor("endVisit(stringValue)"); }

    public boolean visit(customSpec n) { unimplementedVisitor("visit(customSpec)"); return true; }
    public void endVisit(customSpec n) { unimplementedVisitor("endVisit(customSpec)"); }

    public boolean visit(customRules n) { unimplementedVisitor("visit(customRules)"); return true; }
    public void endVisit(customRules n) { unimplementedVisitor("endVisit(customRules)"); }

    public boolean visit(customRule n) { unimplementedVisitor("visit(customRule)"); return true; }
    public void endVisit(customRule n) { unimplementedVisitor("endVisit(customRule)"); }

    public boolean visit(newPropertySpecs n) { unimplementedVisitor("visit(newPropertySpecs)"); return true; }
    public void endVisit(newPropertySpecs n) { unimplementedVisitor("endVisit(newPropertySpecs)"); }

    public boolean visit(conditionalsSpec n) { unimplementedVisitor("visit(conditionalsSpec)"); return true; }
    public void endVisit(conditionalsSpec n) { unimplementedVisitor("endVisit(conditionalsSpec)"); }

    public boolean visit(onOff0 n) { unimplementedVisitor("visit(onOff0)"); return true; }
    public void endVisit(onOff0 n) { unimplementedVisitor("endVisit(onOff0)"); }

    public boolean visit(onOff1 n) { unimplementedVisitor("visit(onOff1)"); return true; }
    public void endVisit(onOff1 n) { unimplementedVisitor("endVisit(onOff1)"); }

    public boolean visit(inout0 n) { unimplementedVisitor("visit(inout0)"); return true; }
    public void endVisit(inout0 n) { unimplementedVisitor("endVisit(inout0)"); }

    public boolean visit(inout1 n) { unimplementedVisitor("visit(inout1)"); return true; }
    public void endVisit(inout1 n) { unimplementedVisitor("endVisit(inout1)"); }

    public boolean visit(fontStyle0 n) { unimplementedVisitor("visit(fontStyle0)"); return true; }
    public void endVisit(fontStyle0 n) { unimplementedVisitor("endVisit(fontStyle0)"); }

    public boolean visit(fontStyle1 n) { unimplementedVisitor("visit(fontStyle1)"); return true; }
    public void endVisit(fontStyle1 n) { unimplementedVisitor("endVisit(fontStyle1)"); }

    public boolean visit(fontStyle2 n) { unimplementedVisitor("visit(fontStyle2)"); return true; }
    public void endVisit(fontStyle2 n) { unimplementedVisitor("endVisit(fontStyle2)"); }

    public boolean visit(typeOrValuesSpec0 n) { unimplementedVisitor("visit(typeOrValuesSpec0)"); return true; }
    public void endVisit(typeOrValuesSpec0 n) { unimplementedVisitor("endVisit(typeOrValuesSpec0)"); }

    public boolean visit(typeOrValuesSpec1 n) { unimplementedVisitor("visit(typeOrValuesSpec1)"); return true; }
    public void endVisit(typeOrValuesSpec1 n) { unimplementedVisitor("endVisit(typeOrValuesSpec1)"); }

    public boolean visit(stringEmptySpec0 n) { unimplementedVisitor("visit(stringEmptySpec0)"); return true; }
    public void endVisit(stringEmptySpec0 n) { unimplementedVisitor("endVisit(stringEmptySpec0)"); }

    public boolean visit(stringEmptySpec1 n) { unimplementedVisitor("visit(stringEmptySpec1)"); return true; }
    public void endVisit(stringEmptySpec1 n) { unimplementedVisitor("endVisit(stringEmptySpec1)"); }

    public boolean visit(conditionType0 n) { unimplementedVisitor("visit(conditionType0)"); return true; }
    public void endVisit(conditionType0 n) { unimplementedVisitor("endVisit(conditionType0)"); }

    public boolean visit(conditionType1 n) { unimplementedVisitor("visit(conditionType1)"); return true; }
    public void endVisit(conditionType1 n) { unimplementedVisitor("endVisit(conditionType1)"); }

    public boolean visit(booleanValue0 n) { unimplementedVisitor("visit(booleanValue0)"); return true; }
    public void endVisit(booleanValue0 n) { unimplementedVisitor("endVisit(booleanValue0)"); }

    public boolean visit(booleanValue1 n) { unimplementedVisitor("visit(booleanValue1)"); return true; }
    public void endVisit(booleanValue1 n) { unimplementedVisitor("endVisit(booleanValue1)"); }

    public boolean visit(signedNumber0 n) { unimplementedVisitor("visit(signedNumber0)"); return true; }
    public void endVisit(signedNumber0 n) { unimplementedVisitor("endVisit(signedNumber0)"); }

    public boolean visit(signedNumber1 n) { unimplementedVisitor("visit(signedNumber1)"); return true; }
    public void endVisit(signedNumber1 n) { unimplementedVisitor("endVisit(signedNumber1)"); }

    public boolean visit(sign0 n) { unimplementedVisitor("visit(sign0)"); return true; }
    public void endVisit(sign0 n) { unimplementedVisitor("endVisit(sign0)"); }

    public boolean visit(sign1 n) { unimplementedVisitor("visit(sign1)"); return true; }
    public void endVisit(sign1 n) { unimplementedVisitor("endVisit(sign1)"); }

    public boolean visit(tab0 n) { unimplementedVisitor("visit(tab0)"); return true; }
    public void endVisit(tab0 n) { unimplementedVisitor("endVisit(tab0)"); }

    public boolean visit(tab1 n) { unimplementedVisitor("visit(tab1)"); return true; }
    public void endVisit(tab1 n) { unimplementedVisitor("endVisit(tab1)"); }

    public boolean visit(tab2 n) { unimplementedVisitor("visit(tab2)"); return true; }
    public void endVisit(tab2 n) { unimplementedVisitor("endVisit(tab2)"); }

    public boolean visit(tab3 n) { unimplementedVisitor("visit(tab3)"); return true; }
    public void endVisit(tab3 n) { unimplementedVisitor("endVisit(tab3)"); }

    public boolean visit(typeCustomSpecs0 n) { unimplementedVisitor("visit(typeCustomSpecs0)"); return true; }
    public void endVisit(typeCustomSpecs0 n) { unimplementedVisitor("endVisit(typeCustomSpecs0)"); }

    public boolean visit(typeCustomSpecs1 n) { unimplementedVisitor("visit(typeCustomSpecs1)"); return true; }
    public void endVisit(typeCustomSpecs1 n) { unimplementedVisitor("endVisit(typeCustomSpecs1)"); }

    public boolean visit(conditionalSpecs0 n) { unimplementedVisitor("visit(conditionalSpecs0)"); return true; }
    public void endVisit(conditionalSpecs0 n) { unimplementedVisitor("endVisit(conditionalSpecs0)"); }

    public boolean visit(conditionalSpecs1 n) { unimplementedVisitor("visit(conditionalSpecs1)"); return true; }
    public void endVisit(conditionalSpecs1 n) { unimplementedVisitor("endVisit(conditionalSpecs1)"); }

    public boolean visit(conditionalSpec0 n) { unimplementedVisitor("visit(conditionalSpec0)"); return true; }
    public void endVisit(conditionalSpec0 n) { unimplementedVisitor("endVisit(conditionalSpec0)"); }

    public boolean visit(conditionalSpec1 n) { unimplementedVisitor("visit(conditionalSpec1)"); return true; }
    public void endVisit(conditionalSpec1 n) { unimplementedVisitor("endVisit(conditionalSpec1)"); }


    public boolean visit(ASTNode n)
    {
        if (n instanceof ASTNodeToken) return visit((ASTNodeToken) n);
        else if (n instanceof prefSpecs) return visit((prefSpecs) n);
        else if (n instanceof optPackageSpec) return visit((optPackageSpec) n);
        else if (n instanceof packageName) return visit((packageName) n);
        else if (n instanceof optDetailsSpec) return visit((optDetailsSpec) n);
        else if (n instanceof topLevelItemList) return visit((topLevelItemList) n);
        else if (n instanceof typeSpec) return visit((typeSpec) n);
        else if (n instanceof pageSpec) return visit((pageSpec) n);
        else if (n instanceof pageName) return visit((pageName) n);
        else if (n instanceof pagePath) return visit((pagePath) n);
        else if (n instanceof pageBody) return visit((pageBody) n);
        else if (n instanceof optionalSpecs) return visit((optionalSpecs) n);
        else if (n instanceof tabsSpec) return visit((tabsSpec) n);
        else if (n instanceof tabSpecList) return visit((tabSpecList) n);
        else if (n instanceof defaultTabSpec) return visit((defaultTabSpec) n);
        else if (n instanceof configurationTabSpec) return visit((configurationTabSpec) n);
        else if (n instanceof instanceTabSpec) return visit((instanceTabSpec) n);
        else if (n instanceof projectTabSpec) return visit((projectTabSpec) n);
        else if (n instanceof fieldsSpec) return visit((fieldsSpec) n);
        else if (n instanceof fieldSpecs) return visit((fieldSpecs) n);
        else if (n instanceof booleanFieldSpec) return visit((booleanFieldSpec) n);
        else if (n instanceof colorFieldSpec) return visit((colorFieldSpec) n);
        else if (n instanceof comboFieldSpec) return visit((comboFieldSpec) n);
        else if (n instanceof directoryFieldSpec) return visit((directoryFieldSpec) n);
        else if (n instanceof dirListFieldSpec) return visit((dirListFieldSpec) n);
        else if (n instanceof doubleFieldSpec) return visit((doubleFieldSpec) n);
        else if (n instanceof fileFieldSpec) return visit((fileFieldSpec) n);
        else if (n instanceof fontFieldSpec) return visit((fontFieldSpec) n);
        else if (n instanceof intFieldSpec) return visit((intFieldSpec) n);
        else if (n instanceof radioFieldSpec) return visit((radioFieldSpec) n);
        else if (n instanceof stringFieldSpec) return visit((stringFieldSpec) n);
        else if (n instanceof booleanFieldPropertySpecs) return visit((booleanFieldPropertySpecs) n);
        else if (n instanceof colorFieldPropertySpecs) return visit((colorFieldPropertySpecs) n);
        else if (n instanceof comboFieldPropertySpecs) return visit((comboFieldPropertySpecs) n);
        else if (n instanceof directoryFieldPropertySpecs) return visit((directoryFieldPropertySpecs) n);
        else if (n instanceof dirlistFieldPropertySpecs) return visit((dirlistFieldPropertySpecs) n);
        else if (n instanceof doubleFieldPropertySpecs) return visit((doubleFieldPropertySpecs) n);
        else if (n instanceof fileFieldPropertySpecs) return visit((fileFieldPropertySpecs) n);
        else if (n instanceof fontFieldPropertySpecs) return visit((fontFieldPropertySpecs) n);
        else if (n instanceof intFieldPropertySpecs) return visit((intFieldPropertySpecs) n);
        else if (n instanceof radioFieldPropertySpecs) return visit((radioFieldPropertySpecs) n);
        else if (n instanceof stringFieldPropertySpecs) return visit((stringFieldPropertySpecs) n);
        else if (n instanceof generalSpecList) return visit((generalSpecList) n);
        else if (n instanceof isEditableSpec) return visit((isEditableSpec) n);
        else if (n instanceof isRemovableSpec) return visit((isRemovableSpec) n);
        else if (n instanceof optLabelSpec) return visit((optLabelSpec) n);
        else if (n instanceof optToolTipSpec) return visit((optToolTipSpec) n);
        else if (n instanceof booleanSpecificSpecList) return visit((booleanSpecificSpecList) n);
        else if (n instanceof booleanSpecialSpec) return visit((booleanSpecialSpec) n);
        else if (n instanceof booleanDefValueSpec) return visit((booleanDefValueSpec) n);
        else if (n instanceof colorSpecificSpecList) return visit((colorSpecificSpecList) n);
        else if (n instanceof colorDefValueSpec) return visit((colorDefValueSpec) n);
        else if (n instanceof comboSpecificSpecList) return visit((comboSpecificSpecList) n);
        else if (n instanceof enumDefValueSpec) return visit((enumDefValueSpec) n);
        else if (n instanceof doubleSpecificSpecList) return visit((doubleSpecificSpecList) n);
        else if (n instanceof doubleRangeSpec) return visit((doubleRangeSpec) n);
        else if (n instanceof doubleDefValueSpec) return visit((doubleDefValueSpec) n);
        else if (n instanceof fontSpecificSpecList) return visit((fontSpecificSpecList) n);
        else if (n instanceof fontDefValueSpec) return visit((fontDefValueSpec) n);
        else if (n instanceof intSpecificSpecList) return visit((intSpecificSpecList) n);
        else if (n instanceof intRangeSpec) return visit((intRangeSpec) n);
        else if (n instanceof intSpecialSpec) return visit((intSpecialSpec) n);
        else if (n instanceof intDefValueSpec) return visit((intDefValueSpec) n);
        else if (n instanceof radioSpecificSpecList) return visit((radioSpecificSpecList) n);
        else if (n instanceof valuesSpec) return visit((valuesSpec) n);
        else if (n instanceof staticOrDynamicValues) return visit((staticOrDynamicValues) n);
        else if (n instanceof columnsSpec) return visit((columnsSpec) n);
        else if (n instanceof labelledStringValueList) return visit((labelledStringValueList) n);
        else if (n instanceof labelledStringValue) return visit((labelledStringValue) n);
        else if (n instanceof stringSpecificSpecList) return visit((stringSpecificSpecList) n);
        else if (n instanceof stringSpecialSpec) return visit((stringSpecialSpec) n);
        else if (n instanceof stringDefValueSpec) return visit((stringDefValueSpec) n);
        else if (n instanceof stringValidatorSpec) return visit((stringValidatorSpec) n);
        else if (n instanceof optConditionalSpec) return visit((optConditionalSpec) n);
        else if (n instanceof identifier) return visit((identifier) n);
        else if (n instanceof stringValue) return visit((stringValue) n);
        else if (n instanceof customSpec) return visit((customSpec) n);
        else if (n instanceof customRules) return visit((customRules) n);
        else if (n instanceof customRule) return visit((customRule) n);
        else if (n instanceof newPropertySpecs) return visit((newPropertySpecs) n);
        else if (n instanceof conditionalsSpec) return visit((conditionalsSpec) n);
        else if (n instanceof onOff0) return visit((onOff0) n);
        else if (n instanceof onOff1) return visit((onOff1) n);
        else if (n instanceof inout0) return visit((inout0) n);
        else if (n instanceof inout1) return visit((inout1) n);
        else if (n instanceof fontStyle0) return visit((fontStyle0) n);
        else if (n instanceof fontStyle1) return visit((fontStyle1) n);
        else if (n instanceof fontStyle2) return visit((fontStyle2) n);
        else if (n instanceof typeOrValuesSpec0) return visit((typeOrValuesSpec0) n);
        else if (n instanceof typeOrValuesSpec1) return visit((typeOrValuesSpec1) n);
        else if (n instanceof stringEmptySpec0) return visit((stringEmptySpec0) n);
        else if (n instanceof stringEmptySpec1) return visit((stringEmptySpec1) n);
        else if (n instanceof conditionType0) return visit((conditionType0) n);
        else if (n instanceof conditionType1) return visit((conditionType1) n);
        else if (n instanceof booleanValue0) return visit((booleanValue0) n);
        else if (n instanceof booleanValue1) return visit((booleanValue1) n);
        else if (n instanceof signedNumber0) return visit((signedNumber0) n);
        else if (n instanceof signedNumber1) return visit((signedNumber1) n);
        else if (n instanceof sign0) return visit((sign0) n);
        else if (n instanceof sign1) return visit((sign1) n);
        else if (n instanceof tab0) return visit((tab0) n);
        else if (n instanceof tab1) return visit((tab1) n);
        else if (n instanceof tab2) return visit((tab2) n);
        else if (n instanceof tab3) return visit((tab3) n);
        else if (n instanceof typeCustomSpecs0) return visit((typeCustomSpecs0) n);
        else if (n instanceof typeCustomSpecs1) return visit((typeCustomSpecs1) n);
        else if (n instanceof conditionalSpecs0) return visit((conditionalSpecs0) n);
        else if (n instanceof conditionalSpecs1) return visit((conditionalSpecs1) n);
        else if (n instanceof conditionalSpec0) return visit((conditionalSpec0) n);
        else if (n instanceof conditionalSpec1) return visit((conditionalSpec1) n);
        throw new UnsupportedOperationException("visit(" + n.getClass().toString() + ")");
    }
    public void endVisit(ASTNode n)
    {
        if (n instanceof ASTNodeToken) endVisit((ASTNodeToken) n);
        else if (n instanceof prefSpecs) endVisit((prefSpecs) n);
        else if (n instanceof optPackageSpec) endVisit((optPackageSpec) n);
        else if (n instanceof packageName) endVisit((packageName) n);
        else if (n instanceof optDetailsSpec) endVisit((optDetailsSpec) n);
        else if (n instanceof topLevelItemList) endVisit((topLevelItemList) n);
        else if (n instanceof typeSpec) endVisit((typeSpec) n);
        else if (n instanceof pageSpec) endVisit((pageSpec) n);
        else if (n instanceof pageName) endVisit((pageName) n);
        else if (n instanceof pagePath) endVisit((pagePath) n);
        else if (n instanceof pageBody) endVisit((pageBody) n);
        else if (n instanceof optionalSpecs) endVisit((optionalSpecs) n);
        else if (n instanceof tabsSpec) endVisit((tabsSpec) n);
        else if (n instanceof tabSpecList) endVisit((tabSpecList) n);
        else if (n instanceof defaultTabSpec) endVisit((defaultTabSpec) n);
        else if (n instanceof configurationTabSpec) endVisit((configurationTabSpec) n);
        else if (n instanceof instanceTabSpec) endVisit((instanceTabSpec) n);
        else if (n instanceof projectTabSpec) endVisit((projectTabSpec) n);
        else if (n instanceof fieldsSpec) endVisit((fieldsSpec) n);
        else if (n instanceof fieldSpecs) endVisit((fieldSpecs) n);
        else if (n instanceof booleanFieldSpec) endVisit((booleanFieldSpec) n);
        else if (n instanceof colorFieldSpec) endVisit((colorFieldSpec) n);
        else if (n instanceof comboFieldSpec) endVisit((comboFieldSpec) n);
        else if (n instanceof directoryFieldSpec) endVisit((directoryFieldSpec) n);
        else if (n instanceof dirListFieldSpec) endVisit((dirListFieldSpec) n);
        else if (n instanceof doubleFieldSpec) endVisit((doubleFieldSpec) n);
        else if (n instanceof fileFieldSpec) endVisit((fileFieldSpec) n);
        else if (n instanceof fontFieldSpec) endVisit((fontFieldSpec) n);
        else if (n instanceof intFieldSpec) endVisit((intFieldSpec) n);
        else if (n instanceof radioFieldSpec) endVisit((radioFieldSpec) n);
        else if (n instanceof stringFieldSpec) endVisit((stringFieldSpec) n);
        else if (n instanceof booleanFieldPropertySpecs) endVisit((booleanFieldPropertySpecs) n);
        else if (n instanceof colorFieldPropertySpecs) endVisit((colorFieldPropertySpecs) n);
        else if (n instanceof comboFieldPropertySpecs) endVisit((comboFieldPropertySpecs) n);
        else if (n instanceof directoryFieldPropertySpecs) endVisit((directoryFieldPropertySpecs) n);
        else if (n instanceof dirlistFieldPropertySpecs) endVisit((dirlistFieldPropertySpecs) n);
        else if (n instanceof doubleFieldPropertySpecs) endVisit((doubleFieldPropertySpecs) n);
        else if (n instanceof fileFieldPropertySpecs) endVisit((fileFieldPropertySpecs) n);
        else if (n instanceof fontFieldPropertySpecs) endVisit((fontFieldPropertySpecs) n);
        else if (n instanceof intFieldPropertySpecs) endVisit((intFieldPropertySpecs) n);
        else if (n instanceof radioFieldPropertySpecs) endVisit((radioFieldPropertySpecs) n);
        else if (n instanceof stringFieldPropertySpecs) endVisit((stringFieldPropertySpecs) n);
        else if (n instanceof generalSpecList) endVisit((generalSpecList) n);
        else if (n instanceof isEditableSpec) endVisit((isEditableSpec) n);
        else if (n instanceof isRemovableSpec) endVisit((isRemovableSpec) n);
        else if (n instanceof optLabelSpec) endVisit((optLabelSpec) n);
        else if (n instanceof optToolTipSpec) endVisit((optToolTipSpec) n);
        else if (n instanceof booleanSpecificSpecList) endVisit((booleanSpecificSpecList) n);
        else if (n instanceof booleanSpecialSpec) endVisit((booleanSpecialSpec) n);
        else if (n instanceof booleanDefValueSpec) endVisit((booleanDefValueSpec) n);
        else if (n instanceof colorSpecificSpecList) endVisit((colorSpecificSpecList) n);
        else if (n instanceof colorDefValueSpec) endVisit((colorDefValueSpec) n);
        else if (n instanceof comboSpecificSpecList) endVisit((comboSpecificSpecList) n);
        else if (n instanceof enumDefValueSpec) endVisit((enumDefValueSpec) n);
        else if (n instanceof doubleSpecificSpecList) endVisit((doubleSpecificSpecList) n);
        else if (n instanceof doubleRangeSpec) endVisit((doubleRangeSpec) n);
        else if (n instanceof doubleDefValueSpec) endVisit((doubleDefValueSpec) n);
        else if (n instanceof fontSpecificSpecList) endVisit((fontSpecificSpecList) n);
        else if (n instanceof fontDefValueSpec) endVisit((fontDefValueSpec) n);
        else if (n instanceof intSpecificSpecList) endVisit((intSpecificSpecList) n);
        else if (n instanceof intRangeSpec) endVisit((intRangeSpec) n);
        else if (n instanceof intSpecialSpec) endVisit((intSpecialSpec) n);
        else if (n instanceof intDefValueSpec) endVisit((intDefValueSpec) n);
        else if (n instanceof radioSpecificSpecList) endVisit((radioSpecificSpecList) n);
        else if (n instanceof valuesSpec) endVisit((valuesSpec) n);
        else if (n instanceof staticOrDynamicValues) endVisit((staticOrDynamicValues) n);
        else if (n instanceof columnsSpec) endVisit((columnsSpec) n);
        else if (n instanceof labelledStringValueList) endVisit((labelledStringValueList) n);
        else if (n instanceof labelledStringValue) endVisit((labelledStringValue) n);
        else if (n instanceof stringSpecificSpecList) endVisit((stringSpecificSpecList) n);
        else if (n instanceof stringSpecialSpec) endVisit((stringSpecialSpec) n);
        else if (n instanceof stringDefValueSpec) endVisit((stringDefValueSpec) n);
        else if (n instanceof stringValidatorSpec) endVisit((stringValidatorSpec) n);
        else if (n instanceof optConditionalSpec) endVisit((optConditionalSpec) n);
        else if (n instanceof identifier) endVisit((identifier) n);
        else if (n instanceof stringValue) endVisit((stringValue) n);
        else if (n instanceof customSpec) endVisit((customSpec) n);
        else if (n instanceof customRules) endVisit((customRules) n);
        else if (n instanceof customRule) endVisit((customRule) n);
        else if (n instanceof newPropertySpecs) endVisit((newPropertySpecs) n);
        else if (n instanceof conditionalsSpec) endVisit((conditionalsSpec) n);
        else if (n instanceof onOff0) endVisit((onOff0) n);
        else if (n instanceof onOff1) endVisit((onOff1) n);
        else if (n instanceof inout0) endVisit((inout0) n);
        else if (n instanceof inout1) endVisit((inout1) n);
        else if (n instanceof fontStyle0) endVisit((fontStyle0) n);
        else if (n instanceof fontStyle1) endVisit((fontStyle1) n);
        else if (n instanceof fontStyle2) endVisit((fontStyle2) n);
        else if (n instanceof typeOrValuesSpec0) endVisit((typeOrValuesSpec0) n);
        else if (n instanceof typeOrValuesSpec1) endVisit((typeOrValuesSpec1) n);
        else if (n instanceof stringEmptySpec0) endVisit((stringEmptySpec0) n);
        else if (n instanceof stringEmptySpec1) endVisit((stringEmptySpec1) n);
        else if (n instanceof conditionType0) endVisit((conditionType0) n);
        else if (n instanceof conditionType1) endVisit((conditionType1) n);
        else if (n instanceof booleanValue0) endVisit((booleanValue0) n);
        else if (n instanceof booleanValue1) endVisit((booleanValue1) n);
        else if (n instanceof signedNumber0) endVisit((signedNumber0) n);
        else if (n instanceof signedNumber1) endVisit((signedNumber1) n);
        else if (n instanceof sign0) endVisit((sign0) n);
        else if (n instanceof sign1) endVisit((sign1) n);
        else if (n instanceof tab0) endVisit((tab0) n);
        else if (n instanceof tab1) endVisit((tab1) n);
        else if (n instanceof tab2) endVisit((tab2) n);
        else if (n instanceof tab3) endVisit((tab3) n);
        else if (n instanceof typeCustomSpecs0) endVisit((typeCustomSpecs0) n);
        else if (n instanceof typeCustomSpecs1) endVisit((typeCustomSpecs1) n);
        else if (n instanceof conditionalSpecs0) endVisit((conditionalSpecs0) n);
        else if (n instanceof conditionalSpecs1) endVisit((conditionalSpecs1) n);
        else if (n instanceof conditionalSpec0) endVisit((conditionalSpec0) n);
        else if (n instanceof conditionalSpec1) endVisit((conditionalSpec1) n);
        throw new UnsupportedOperationException("visit(" + n.getClass().toString() + ")");
    }
}


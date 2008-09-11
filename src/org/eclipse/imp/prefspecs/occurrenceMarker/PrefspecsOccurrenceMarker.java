package org.eclipse.imp.prefspecs.occurrenceMarker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lpg.runtime.IAst;

import org.eclipse.imp.language.ILanguageService;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.prefspecs.parser.PrefspecsParsersym;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNodeToken;
import org.eclipse.imp.prefspecs.parser.Ast.AbstractVisitor;
import org.eclipse.imp.prefspecs.parser.Ast.IconditionalSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.IconditionalsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.IcustomSpec;
import org.eclipse.imp.prefspecs.parser.Ast.IfieldSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.IfieldsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.Itab;
import org.eclipse.imp.prefspecs.parser.Ast.ItabSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.ItabsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.booleanValue0;
import org.eclipse.imp.prefspecs.parser.Ast.booleanValue1;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.configurationTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.customRule;
import org.eclipse.imp.prefspecs.parser.Ast.defaultTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.dirListFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fileFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.identifier;
import org.eclipse.imp.prefspecs.parser.Ast.instanceTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.intFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.projectTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.radioFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.signedNumber0;
import org.eclipse.imp.prefspecs.parser.Ast.signedNumber1;
import org.eclipse.imp.prefspecs.parser.Ast.stringFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.stringValue;
import org.eclipse.imp.prefspecs.parser.Ast.tab0;
import org.eclipse.imp.prefspecs.parser.Ast.tab1;
import org.eclipse.imp.prefspecs.parser.Ast.tab2;
import org.eclipse.imp.prefspecs.parser.Ast.tab3;
import org.eclipse.imp.services.IOccurrenceMarker;

public class PrefspecsOccurrenceMarker implements ILanguageService, IOccurrenceMarker {

	private List fOccurrences = Collections.EMPTY_LIST;
	private IAst decl;

	public String getKindName() {
		return "prefspecs Occurence Marker";
	}

	public List<Object> getOccurrencesOf(IParseController parseController,
			Object ast) {

		if (ast == null) {
			return Collections.EMPTY_LIST;
		}

		// Check whether we even have an AST in which to find occurrences
		ASTNode root = (ASTNode) parseController.getCurrentAst();
		if (root == null) {
			return Collections.EMPTY_LIST;
		}

		fOccurrences = new ArrayList();

		// For those selections where only the occurrence of the given
		// instance is to be marked
		if (nodeKindIndicatesAUniqueOccurrence(ast)) {
			fOccurrences.add(ast);
			return fOccurrences;
		}

		// For those selections where occurrences of copies of the
		// given instance are to be marked
		if (nodeKindIndicatesLiteralOccurrences(ast)) {
			root.accept(new LiteralOccurrenceVisitor(ast));
			return fOccurrences;
		}

		// For those selections where the occurrences to be marked are
		// determined by an arbitrary computation based on the given instance
		if (nodeKindIndicatesComputedOccurrences(ast)) {
			root.accept(new ComputedOccurrenceVisitor(ast));
			return fOccurrences;
		}

		// TODO:  Choose an approach to handling given instances that
		// have not been marked in one of the prior sections
		// One option:  return nothing for these
		//fOccurrences = Collections.EMPTY_LIST;
		// Another option:  return just the given item:
		fOccurrences.add(ast);
		return fOccurrences;
	}

	/**
	 * Test whether the given object represents an element in the source
	 * text that should be marked uniquely.
	 * 
	 * @param ast	An instance representing some markable element in the
	 * 				source text
	 * @return		True iff the text should be marked for this instance alone
	 */
	public boolean nodeKindIndicatesAUniqueOccurrence(Object ast) {
		// TODO:  identify AST node types that belong to this category
		// Some examples of syntactic elements that might fall into
		// this category (not an exhaustive list)
		if (ast instanceof ItabSpecs || ast instanceof ItabsSpec ||
				ast instanceof IfieldSpecs || ast instanceof IfieldsSpec ||
				ast instanceof IconditionalSpecs || ast instanceof IconditionalsSpec ||
				ast instanceof IcustomSpec)
		{
			return true;
		}
		return false;
	}

	/**
	 * Test whether the given object represents an element in the source
	 * text for which all copies should be marked.
	 * 
	 * @param ast	An instance representing some markable element in the
	 * 				source text
	 * @return		True iff all literal occurrences of the text represented
	 * 				by the given object should be marked
	 */
	public boolean nodeKindIndicatesLiteralOccurrences(Object ast) {
		// Here allow for miscellaneous ASTNodeTokens
		// BUT exclude any that are to be processed in a different way
		if (ast instanceof ASTNodeToken && 
			!(ast instanceof identifier) &&
			!isTabNodeToken((ASTNodeToken)ast) &&
			!isTabNode(ast))
		{
			return true;
		}
		return false;
	}

	
	public boolean isTabNodeToken(ASTNodeToken nt) {
		if (nt.getIToken().getKind() == PrefspecsParsersym.TK_DEFAULT)
			return true;
		if (nt.getIToken().getKind() == PrefspecsParsersym.TK_CONFIGURATION)
			return true;
		if (nt.getIToken().getKind() == PrefspecsParsersym.TK_INSTANCE)
			return true;
		if (nt.getIToken().getKind() == PrefspecsParsersym.TK_PROJECT)
			return true;
		
		return false;
	}
	
	
	public boolean isTabNode(Object ast) {
		if (ast instanceof identifier ||
				ast instanceof tab0 || ast instanceof tab1 ||
				ast instanceof tab2 || ast instanceof tab3)
		{
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Test whether the given object represents an element in the source
	 * text for which the elements to be marked need to be determined by
	 * some more complicated computation that is not defined a priori
	 * 
	 * @param ast	An instance representing some markable element in the
	 * 				source text
	 * @return		True iff some computation is needed to determine the
	 * 				instances to be marked
	 */
	public boolean nodeKindIndicatesComputedOccurrences(Object ast) {
		if (ast instanceof identifier ||
			(isTabNodeToken((ASTNodeToken)ast)) ||
			isTabNode(ast))
		{
			return true;
		}
		return false;
	}

	/*
	 * A visitor to traverse an AST and search for nodes that match a
	 * given node (that is provided to the constructor).  Matching nodes
	 * are added to a global list fOccurrences.
	 */
	private class LiteralOccurrenceVisitor extends AbstractVisitor {
		// The given node
		Object ast;

		LiteralOccurrenceVisitor(Object ast) {
			super();
			this.ast = ast;
		}

		@Override
		public void unimplementedVisitor(String s) {
		}

		// TODO:  Include a visit method for each AST node type for which
		// nodeKindIndicatesLiteralOccurrence returns true

		// Note:  Depending on the node type, matches between the given
		// node and the visited node may be made based on some value
		// associated with the node (as with numbers) or just based on
		// the type of the node (as with nodes that represent the names
		// of primitive types).

		// Note:  Few (if any) of the node types addressed in this visitor
		// will have children, and for those that do, traversal of the
		// children will probably not contribute to occurrence marking.
		// Therefore, these visit methods will typically return false.
		// Similarly, endVisit(..) methos will typically be unnecessary.

		// Example visit(..) methods:

		public boolean visit(signedNumber0 n) {
			if (ast instanceof signedNumber0
					&& n.getNUMBER().toString().equals(
							((signedNumber0) ast).getNUMBER().toString())) {
				fOccurrences.add(n);
			}
			return false;
		}

		public boolean visit(signedNumber1 n) {
			if (ast instanceof signedNumber1
					&& n.getNUMBER().toString().equals(
							((signedNumber1) ast).getNUMBER().toString())) {
				fOccurrences.add(n);
			}
			return false;
		}

		public boolean visit(booleanValue0 n) {
			if (ast instanceof booleanValue0)
				fOccurrences.add(n);
			return false;
		}

		public boolean visit(booleanValue1 n) {
			if (ast instanceof booleanValue1)
				fOccurrences.add(n);
			return false;
		}

		public boolean visit(stringValue n) {
			if (ast instanceof stringValue
					&& n.getSTRING_LITERAL().toString().equals(
							((stringValue) ast).getSTRING_LITERAL().toString())) {
				fOccurrences.add(n);
			}
			return false;
		}

		// various tokens, including especially operators
		public boolean visit(ASTNodeToken n) {
			if (ast instanceof ASTNodeToken
					&& ((ASTNodeToken) ast).toString().equals(n.toString()))
				fOccurrences.add(n);
			return false;
		}

	}

	/*
	 * A visitor to traverse an AST and search for nodes that should be
	 * marked along with a given node (that is provided to the constructor).
	 * Identified nodes are added to a global list fOccurrences.
	 * 
	 * This visitor may perform relatively complicated computations and
	 * make use of arbitrary ancillary methods in identifying nodes to
	 * return.
	 * 
	 * FOR THE INITIAL EXAMPLE:
	 * 
	 * The purpose of this visitor is to identify 
	 * 
	 */
	private class ComputedOccurrenceVisitor extends AbstractVisitor {
		// The given node
		Object ast;

		ComputedOccurrenceVisitor(Object ast) {
			super();
			this.ast = ast;
		}

		@Override
		public void unimplementedVisitor(String s) {
		}

		// TODO:  Include visit(..) and possibly endVisit(..) methods for each node
		// type that is important in determining the AST nodes that are to be returned
		// by this visitor as targets of occurrence marking.

		// NOTE:  Depending on the type of the given AST node, the information that
		// can be obtained from an instance of that type, and the semantics that govern
		// the selection of nodes in relation to instances of that type, the complexity
		// of the calculation required can vary widely.  For instance, it may be sufficient
		// to visit nodes of just one type or necessary to visit nodes of several type,
		// it may or may not require the use of global data structures, and it may or
		// may not be necessary to couple endVisit(..) methods with visit(..) methods.

		// NOTE:  Some other considerations to keep in mind:
		// - Some AST node kinds may participate in computations for multiple kinds
		//   of given node.
		// - Some "lower level" AST node kinds may occur in the context of several different
		//   types of "higher level" node kinds where these higher-level kinds require
		//   different kinds of processing at the lower level (so some computations
		//   for a given node kind may need to be context sensitive)
		// - This example uses one visitor to capture information related to two different
		//   types of computed occurrence.  Some complexity is introduced into the visitor
		//   by the need to coordinate these two purposes.  As an alternative, different
		//   computations can be split into different visitors, which may keep them simpler.

		/*
		 * Visit methods relating to the selection of identifiers and their declarations.
		 * In this case, only nodes for identifiers and (identifier) declarations need to
		 * be visited.  This is possible because the LPG-generated parser has given each
		 * identifier AST node a reference to its corresponding declaration AST node.
		 */

		// The following implementations are simplified by the fact there must be
		// exactly one declaration of any identifier

		public boolean visit(identifier n) {
			if (ast instanceof identifier) {
				// Don't separately mark identifiers within field specs
				if (n.getParent() instanceof IfieldSpecs)
					return false;
				
				if (n.getIDENTIFIER().toString().equals(((identifier)ast).getIDENTIFIER().toString()))
				fOccurrences.add(n);
			}
			return false;
		}


		public boolean visit(booleanFieldSpec n) {
			String ident = n.getidentifier().toString();
			if (ast instanceof identifier &&
					((identifier)ast).getIDENTIFIER().toString().equals(ident))
			{
				fOccurrences.add(n);
			}
			return true;
		}
		
		public boolean visit(comboFieldSpec n) {
			String ident = n.getidentifier().toString();
			if (ast instanceof identifier &&
					((identifier)ast).getIDENTIFIER().toString().equals(ident))
			{
				fOccurrences.add(n);
			}
			return true;
		}
		
		public boolean visit(dirListFieldSpec n) {
			String ident = n.getidentifier().toString();
			if (ast instanceof identifier &&
					((identifier)ast).getIDENTIFIER().toString().equals(ident))
			{
				fOccurrences.add(n);
			}
			return true;
		}

		public boolean visit(fileFieldSpec n) {
			String ident = n.getidentifier().toString();
			if (ast instanceof identifier &&
					((identifier)ast).getIDENTIFIER().toString().equals(ident))
			{
				fOccurrences.add(n);
			}
			return true;
		}
		
		public boolean visit(intFieldSpec n) {
			String ident = n.getidentifier().toString();
			if (ast instanceof identifier &&
					((identifier)ast).getIDENTIFIER().toString().equals(ident))
			{
				fOccurrences.add(n);
			}
			return true;
		}
		
		public boolean visit(radioFieldSpec n) {
			String ident = n.getidentifier().toString();
			if (ast instanceof identifier &&
					((identifier)ast).getIDENTIFIER().toString().equals(ident))
			{
				fOccurrences.add(n);
			}
			return true;
		}
		
		public boolean visit(stringFieldSpec n) {
			String ident = n.getidentifier().toString();
			if (ast instanceof identifier &&
					((identifier)ast).getIDENTIFIER().toString().equals(ident))
			{
				fOccurrences.add(n);
			}
			return true;
		}

		
		public boolean visit(defaultTabSpec n) {
			// If the ast represents a defaultTabSpec, or the default tab within a default tab spec,
			// or a default tab itself, then mark this default tab spec
			if (ast instanceof defaultTabSpec ||
				((ast instanceof ASTNodeToken) && (isTabNodeToken((ASTNodeToken) ast) && ((ASTNodeToken)ast).getParent() instanceof defaultTabSpec)) ||
				(ast instanceof tab0))
			{
				fOccurrences.add(n);
			}
			return true;
		}

		public boolean visit(configurationTabSpec n) {
			// If the ast represents a configurationTabSpec, or the configuration tab within a configuration tab spec,
			// or a configuration tab itself, then mark this configuration tab spec
			if (ast instanceof configurationTabSpec ||
				((ast instanceof ASTNodeToken) && (isTabNodeToken((ASTNodeToken) ast) && ((ASTNodeToken)ast).getParent() instanceof configurationTabSpec)) ||
				(ast instanceof tab1))
			{
				fOccurrences.add(n);
			}
			return true;
		}
		
		public boolean visit(instanceTabSpec n) {
			// If the ast represents an instanceTabSpec, or the instance tab within an instance tab spec,
			// or an instance tab itself, then mark this instance tab spec
			if (ast instanceof instanceTabSpec ||
				((ast instanceof ASTNodeToken) && (isTabNodeToken((ASTNodeToken) ast) && ((ASTNodeToken)ast).getParent() instanceof instanceTabSpec)) ||
				(ast instanceof tab2))
			{
				fOccurrences.add(n);
			}
			return true;
		}
		
		public boolean visit(projectTabSpec n) {
			// If the ast represents a projectTabSpec, or the project tab within a project tab spec,
			// or a project tab itself, then mark this project tab spec
			if (ast instanceof projectTabSpec ||
				((ast instanceof ASTNodeToken) && (isTabNodeToken((ASTNodeToken) ast) && ((ASTNodeToken)ast).getParent() instanceof projectTabSpec)) ||
				(ast instanceof tab3))
			{
				fOccurrences.add(n);
			}
			return true;
		}
		

		
		
		// Default tab
		public boolean visit(tab0 n) {
			// Record this occurrence if the AST is a default tab spec,
			// a default tab reference within a default tab spec,
			// or a default tab itself
			if ((ast instanceof defaultTabSpec) ||
				((ast instanceof ASTNodeToken) && (((ASTNodeToken)ast).getParent() instanceof defaultTabSpec)) ||
				(ast instanceof tab0))
			{
				fOccurrences.add(n);
			}
			return false;
		}
		
		
		// Configuration tab
		public boolean visit(tab1 n) {
			// Record this occurrence if the AST is a configuration tab spec,
			// a configuration tab reference within a configuration tab spec,
			// or a configuration tab itself
			if ((ast instanceof configurationTabSpec) ||
				((ast instanceof ASTNodeToken) && (((ASTNodeToken)ast).getParent() instanceof configurationTabSpec)) ||
				(ast instanceof tab1))
			{
				fOccurrences.add(n);
			}
			return false;
		}

		
		// Instance tab
		public boolean visit(tab2 n) {
			// Record this occurrence if the AST is an instance tab spec,
			// an instance tab reference within an instance tab spec,
			// or an instance tab itself
			if ((ast instanceof instanceTabSpec) ||
				((ast instanceof ASTNodeToken) && (((ASTNodeToken)ast).getParent() instanceof instanceTabSpec)) ||
				(ast instanceof tab2))
			{
				fOccurrences.add(n);
			}
			return false;
		}
		
		// Project tab
		public boolean visit(tab3 n) {
			// Record this occurrence if the AST is an project tab spec,
			// an project tab reference within an project tab spec,
			// or an project tab itself
			if ((ast instanceof projectTabSpec) ||
				((ast instanceof ASTNodeToken) && (((ASTNodeToken)ast).getParent() instanceof projectTabSpec)) ||
				(ast instanceof tab3))
			{
				fOccurrences.add(n);
			}
			return false;
		}
		
	}
}

/*******************************************************************************
* Copyright (c) 2008 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Stan Sutton (suttons@us.ibm.com) - initial API and implementation

*******************************************************************************/

package org.eclipse.imp.prefspecs.treeModelBuilder;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.imp.editor.ModelTreeNode;
import org.eclipse.imp.prefspecs.IPrefspecsResources;
import org.eclipse.imp.prefspecs.PrefspecsPlugin;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalSpec1;
import org.eclipse.imp.prefspecs.parser.Ast.customRule;
import org.eclipse.imp.services.ILabelProvider;
import org.eclipse.imp.utils.MarkerUtils;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import org.eclipse.imp.prefspecs.parser.Ast.*;


public class PrefspecsLabelProvider implements ILabelProvider {
	private Set<ILabelProviderListener> fListeners = new HashSet<ILabelProviderListener>();

	private static ImageRegistry sImageRegistry = PrefspecsPlugin.getInstance()
			.getImageRegistry();

	private static Image DEFAULT_IMAGE = sImageRegistry
			.get(IPrefspecsResources.PREFSPECS_DEFAULT_IMAGE);
	private static Image FILE_IMAGE = sImageRegistry
			.get(IPrefspecsResources.PREFSPECS_FILE);
	private static Image FILE_WITH_WARNING_IMAGE = sImageRegistry
			.get(IPrefspecsResources.PREFSPECS_FILE_WARNING);
	private static Image FILE_WITH_ERROR_IMAGE = sImageRegistry
			.get(IPrefspecsResources.PREFSPECS_FILE_ERROR);
	
	private static Image BOOLEAN_FIELD_IMAGE =
		sImageRegistry.get(IPrefspecsResources.BOOLEAN_FIELD);
	
	private static Image STRING_FIELD_IMAGE =
		sImageRegistry.get(IPrefspecsResources.STRING_FIELD);
	
	private static Image INT_FIELD_IMAGE =
		sImageRegistry.get(IPrefspecsResources.INT_FIELD);
	
	private static Image DIRLIST_FIELD_IMAGE =
		sImageRegistry.get(IPrefspecsResources.DIRLIST_FIELD);

	public Image getImage(Object element) {
		if (element instanceof IFile) {
			// TODO:  rewrite to provide more appropriate images
			IFile file = (IFile) element;
			int sev = MarkerUtils.getMaxProblemMarkerSeverity(file,
					IResource.DEPTH_ONE);

			switch (sev) {
			case IMarker.SEVERITY_ERROR:
				return FILE_WITH_ERROR_IMAGE;
			case IMarker.SEVERITY_WARNING:
				return FILE_WITH_WARNING_IMAGE;
			default:
				return FILE_IMAGE;
			}
		}
		ASTNode n = (element instanceof ModelTreeNode) ? (ASTNode) ((ModelTreeNode) element)
				.getASTNode()
				: (ASTNode) element;
		return getImageFor(n);
	}

	public static Image getImageFor(ASTNode n) {
		// TODO:  return specific images for specific node
		// types, as images are available and appropriate
		
		if (n instanceof booleanFieldSpec)
			return BOOLEAN_FIELD_IMAGE;
		if (n instanceof stringFieldSpec)
			return STRING_FIELD_IMAGE;
		if (n instanceof intFieldSpec)
			return INT_FIELD_IMAGE;
		if (n instanceof fileFieldSpec)
			return FILE_IMAGE;
		if (n instanceof dirListFieldSpec)
			return DIRLIST_FIELD_IMAGE;
		
		return DEFAULT_IMAGE;
	}

	public String getText(Object element) {
		ASTNode n = (element instanceof ModelTreeNode) ? (ASTNode) ((ModelTreeNode) element)
				.getASTNode()
				: (ASTNode) element;

		return getLabelFor(n);
	}

	public static String getLabelFor(ASTNode n) {

		if (n instanceof pageSpec)
			return "Page " + ((pageSpec)n).getidentifier().toString();
		
		if (n instanceof tabsSpec)
			return "Tabs";
		if (n instanceof defaultTabSpec)
			return "Default tab";
		if (n instanceof configurationTabSpec)
			return "Configuration tab";
		if (n instanceof instanceTabSpec)
			return "Instance tab";
		if (n instanceof projectTabSpec)
			return "Project tab";
		
		if (n instanceof fieldsSpec)
			return "Fields";
		if (n instanceof booleanFieldSpec)
			return ((booleanFieldSpec)n).getidentifier().toString();
		if (n instanceof comboFieldSpec)
			return ((comboFieldSpec)n).getidentifier().toString();
		if (n instanceof dirListFieldSpec)
			return ((dirListFieldSpec)n).getidentifier().toString();
		if (n instanceof fileFieldSpec)
			return ((fileFieldSpec)n).getidentifier().toString();	
		if (n instanceof intFieldSpec)
			return ((intFieldSpec)n).getidentifier().toString();
		if (n instanceof radioFieldSpec)
			return ((radioFieldSpec)n).getidentifier().toString();	
		if (n instanceof stringFieldSpec)
			return ((stringFieldSpec)n).getidentifier().toString();
	
		if (n instanceof customSpec)
			return "Custom";
		if (n instanceof customRule)
			return ((customRule)n).getidentifier().toString();
		
		if (n instanceof conditionalsSpec)
			return "Conditionals";
		if (n instanceof conditionalSpec0)
			return ((conditionalSpec0)n).getidentifier().toString();
		if (n instanceof conditionalSpec1)
			return ((conditionalSpec1)n).getidentifier().toString();
		
		return "<???>";
	}

	public void addListener(ILabelProviderListener listener) {
		fListeners.add(listener);
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		fListeners.remove(listener);
	}
}

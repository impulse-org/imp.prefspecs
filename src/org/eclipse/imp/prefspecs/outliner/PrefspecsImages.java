/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.outliner;

import org.eclipse.imp.services.IOutlineImage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

//public class PrefspecsImages {
	
public class PrefspecsImages implements IOutlineImage
{
	private PrefspecsImages() { }
	
	private static PrefspecsImages image = null;
	
	public static PrefspecsImages getPrefspecsImages() {
		if (image == null) {
			image = new PrefspecsImages();
		}
		return image;
	}

	public static final String IMAGE_ROOT= "icons";

	public static ImageDescriptor OUTLINE_ITEM_DESC= AbstractUIPlugin.imageDescriptorFromPlugin("prefspecs", IMAGE_ROOT + "/outline_item.gif");
	public static Image OUTLINE_ITEM_IMAGE= OUTLINE_ITEM_DESC.createImage();


	public String getImageRoot() {
		return IMAGE_ROOT;
	}

	public ImageDescriptor getOutlineItemDesc() {
		return OUTLINE_ITEM_DESC;
	}

	public Image getOutlineItemImage() {
		return OUTLINE_ITEM_IMAGE;
	}
	
}

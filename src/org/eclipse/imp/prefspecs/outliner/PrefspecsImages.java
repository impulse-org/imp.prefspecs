package prefspecs.safari.outliner;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.uide.defaults.DefaultOutlineImage;
import org.eclipse.uide.editor.IOutlineImage;

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

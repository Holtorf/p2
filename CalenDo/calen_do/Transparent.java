package calen_do;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

/**
 * This class changes normal images to transparent images. It is used to design
 * the rollOver icons of the days buttons
 * 
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
public class Transparent {

	/**
	 * This method changes an image into a bufferedImage
	 * 
	 * @param img
	 * @return
	 */
	public BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	/**
	 * This method makes the bufferImaged transparent
	 * @param image
	 * @param color
	 * @return
	 */
	public BufferedImage makeTransparent(BufferedImage image, Color color) {
		final ImageFilter filter = new RGBImageFilter() {
			private final int ALPHA = 0xFF000000;
			private final int markerRGB = color.getRGB() | ALPHA;

			@Override
			public int filterRGB(int x, int y, int rgb) {
				if ((rgb | ALPHA) == markerRGB) {
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
				} else {
					// nothing to do
					return rgb;
				}
			}
		};

		final ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
		final Image img = Toolkit.getDefaultToolkit().createImage(ip);
		final BufferedImage b = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2 = b.createGraphics();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		return b;
	}
}

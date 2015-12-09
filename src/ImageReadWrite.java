import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageReadWrite {

	public static byte[][] ImageRead(String filename) {
		// -- read input image
		File infile = new File(filename);
		return ImageRead(infile);
	}

	public static byte[][] ImageRead(File infile) {

		try {
			BufferedImage bi = ImageIO.read(infile);

			// -- separate image into RGB components
			byte red[][] = new byte[bi.getHeight()][bi.getWidth()];
			byte grn[][] = new byte[bi.getHeight()][bi.getWidth()];
			byte blu[][] = new byte[bi.getHeight()][bi.getWidth()];
			for (int i = 0; i < red.length; ++i) {
				for (int j = 0; j < red[i].length; ++j) {
					red[i][j] = (byte) (bi.getRGB(j, i) >> 16 & 0xFF);
					grn[i][j] = (byte) (bi.getRGB(j, i) >> 8 & 0xFF);
					blu[i][j] = (byte) (bi.getRGB(j, i) & 0xFF);
				}
			}

			return grn;

		} catch (IOException e) {
			System.out.println("image I/O error");
			return null;
		}
	}

	public static byte[][][] ImageReadC(String filename) {

		try {

			// -- read input image
			File infile = new File(filename);
			BufferedImage bi = ImageIO.read(infile);

			// -- separate image into RGB components
			byte img[][][] = new byte[3][bi.getHeight()][bi.getWidth()];
			for (int i = 0; i < img[0].length; ++i) {
				for (int j = 0; j < img[0][i].length; ++j) {
					img[0][i][j] = (byte) (bi.getRGB(j, i) >> 16 & 0xFF);
					img[1][i][j] = (byte) (bi.getRGB(j, i) >> 8 & 0xFF);
					img[2][i][j] = (byte) (bi.getRGB(j, i) & 0xFF);
				}
			}

			return img;

		} catch (IOException e) {
			System.out.println("image I/O error");
			return null;
		}
	}

	public static void renderImage(byte img[][][], Graphics2D g2d, int x,
			int y, int w, int h) {
		BufferedImage bi = new BufferedImage(img[0][0].length, img[0].length,
				BufferedImage.TYPE_INT_RGB);

		// -- prepare output image
		for (int i = 0; i < bi.getHeight(); ++i) {
			for (int j = 0; j < bi.getWidth(); ++j) {
				int pixel = ((int) img[0][i][j] << 16)
						| ((int) img[1][i][j] << 8) | ((int) img[2][i][j]);
				bi.setRGB(j, i, pixel);
			}
		}
		g2d.drawImage(bi, x, y, Math.min(w, bi.getWidth()),
				Math.min(h, bi.getHeight()), 0, 0, bi.getWidth(),
				bi.getHeight(), null);

	}

	public static void ImageWrite(byte img[][], String filename) {
		try {
			BufferedImage bi = new BufferedImage(img[0].length, img.length,
					BufferedImage.TYPE_INT_RGB);

			// -- prepare output image
			for (int i = 0; i < bi.getHeight(); ++i) {
				for (int j = 0; j < bi.getWidth(); ++j) {
					int val = img[i][j] & 0xff;
					int pixel = (val << 16) | (val << 8) | (val);
					bi.setRGB(j, i, pixel);
				}
			}

			// -- write output image
			File outputfile = new File(filename);
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {

		}
	}

	public static void ImageWrite(byte img[][], File outputfile) {

		try {
			BufferedImage bi = new BufferedImage(img[0].length, img.length,
					BufferedImage.TYPE_INT_RGB);

			// -- prepare output image
			for (int i = 0; i < bi.getHeight(); ++i) {
				for (int j = 0; j < bi.getWidth(); ++j) {
					int val = img[i][j] & 0xff;
					int pixel = (val << 16) | (val << 8) | (val);
					bi.setRGB(j, i, pixel);
				}
			}

			// -- write output image

			ImageIO.write(bi, "png", outputfile);
		} catch (Exception e) {
		}
	}

	public static void ImageWriteC(byte img[][][], String filename) {
		try {
			BufferedImage bi = new BufferedImage(img[0][0].length,
					img[0].length, BufferedImage.TYPE_INT_RGB);

			// -- prepare output image
			for (int i = 0; i < bi.getHeight(); ++i) {
				for (int j = 0; j < bi.getWidth(); ++j) {
					int pixel = ((int) img[0][i][j] << 16)
							| ((int) img[1][i][j] << 8) | ((int) img[2][i][j]);
					bi.setRGB(j, i, pixel);
				}
			}

			// -- write output image
			File outputfile = new File(filename);
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {

		}
	}
}
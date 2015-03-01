package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.IndexColorModel;
import java.util.Random;

import lib.BinaryTable;

// Conway image to illustrate the BinaryTable
public class ConwayImage extends BufferedImage {

	private Color color;
	
	// Constructor
	public ConwayImage(int width, int height) {
		super(width, height, TYPE_INT_RGB);
		sortColor();
	}
	
	public void sortColor() {
		Color old = color;
		Random rand = new Random();
		int r = rand.nextInt(3);
		color = r == 0 ? new Color(255,0,0) : r == 1 ? new Color(0,255,0) : new Color(0,0,255);
		if (color.equals(old)) sortColor();
	}
	
	// Draws the BinaryTable bits with 1 as Red and 0 as Black
	public void Draw(BinaryTable table) {
		Clear();
		for (int y = 0; y < getHeight() && y < table.getHeight(); y++) {
			for (int x = 0; x < getWidth() && x < table.getWidth(); x++) {
				setRGB(x, y, table.Get(x, y) ? color.getRGB() : new Color(0,0,0).getRGB());
			}
		}
	}	
	
	// Clears the image
	public void Clear() {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				setRGB(x, y, 0);
			}
		}
	}
}

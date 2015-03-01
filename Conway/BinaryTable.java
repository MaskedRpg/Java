package lib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

// A table with 2 dimensions containing binary information (0 or 1)
public class BinaryTable {
	
	private Dimension size;
	private boolean[][] table;
	
	// Constructor
	public BinaryTable(int width, int height) throws IllegalArgumentException {
		if (width < 0 || height < 0) throw new IllegalArgumentException();
		this.size = new Dimension(width, height);
		this.table = new boolean[width][height];
	}
	
	// Constructor
	public BinaryTable(Dimension dim) throws IllegalArgumentException {
		this((int)dim.getWidth(), (int)dim.getHeight());
	}
	
	// Gets the table's dimension
	public Dimension getSize() {
		return size;
	}
	
	// Gets the table's width
	public int getWidth() {
		return (int) size.getWidth();
	}
	
	// Gets the table's height
	public int getHeight() {
		return (int) size.getHeight();
	}
	
	// Resizes the table
	public void Resize(int width, int height) {		
		boolean[][] old = this.table.clone();
		this.table = new boolean[width][height];
		for (int x = 0; x < getWidth() && x < width; x++) {
			for (int y = 0; y < getHeight() && y < height; y++) {
				this.table[x][y] = old[x][y];
			}
		}
		this.size = new Dimension(width, height);
	}	
	
	// Gets the bool at the XY coordinates
	public boolean Get(int x, int y) {
		if (x < 0) x = size.width - 1;
		if (y < 0) y = size.height - 1;
		x = x % table.length;
		y = y % table[x].length;
		return table[x][y];
	}
	
	// Sets the bit at the XY coordinates
	public void Set(int x, int y, boolean bool) {
		if (x < 0) x = size.width - 1;
		if (y < 0) y = size.height - 1;
		x = x % table.length;
		y = y % table[x].length;
		table[x][y] = bool;
	}
	
	// Creates a copy of self
	public BinaryTable clone() {
		BinaryTable other = new BinaryTable(getSize());
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				other.Set(x, y, Get(x, y));
			}
		}
		return other;
	}
	
	// Creates a BinaryTable from an BufferedImage
	public static BinaryTable fromImage(BufferedImage image) {
		BinaryTable bintable = new BinaryTable(image.getWidth(null), image.getHeight(null));
		for (int x = 0; x < image.getWidth(null); x++) {
			for (int y = 0; y < image.getHeight(null); y++) {
				bintable.Set(x, y, image.getRGB(x, y) == Color.black.getRGB());
			}
		}
		return bintable;
	}
	
	public BufferedImage toImage() {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < image.getWidth(null); x++) {
			for (int y = 0; y < image.getHeight(null); y++) {
				if (Get(x, y)) image.setRGB(x, y, Color.black.getRGB());
				else image.setRGB(x, y, 0);
			}
		}
		return image;
	}
}

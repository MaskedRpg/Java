package lib;

import java.util.Random;

// Conway algorithm class
public final class ConwayAlgorithm {
	
	// Private variables
	private int moment;
	private boolean started;
	
	// Constructor
	public ConwayAlgorithm() {
		this.moment = 0;
	}
	
	// Gets the current instant in the algorithm
	public int getMoment() {
		return this.moment;
	}
	
	public boolean isStarted() {
		return this.started;
	}
	
	// Starts the game
	public void Start(BinaryTable table) {
		Random rand = new Random();
		for (int x = 0; x < table.getWidth(); x++) {
			for (int y = 0; y < table.getHeight(); y++) {
				table.Set(x, y, rand.nextBoolean());
			}
		}
		this.started = true;
	}
	
	// Advance a moment in the game
	public void Next(BinaryTable table) {
		if (!this.started) return;
		this.moment += 1;
		BinaryTable oldtable = table.clone();
		for (int x = 0; x < oldtable.getWidth(); x++) {
			for (int y = 0; y < oldtable.getHeight(); y++) {
				int n = GetNeighbors(oldtable, x, y);
				if (n < 2 || n > 3) {
					table.Set(x, y, false);
				} else if (n == 3) {
					table.Set(x, y, true);
				}
			}
		}
	}
	
	private int GetNeighbors(BinaryTable table, int x, int y) {
		int n = 0;
		if (table.Get(x+1, y))   n++;
		if (table.Get(x, y+1))   n++;
		if (table.Get(x-1, y))   n++;
		if (table.Get(x, y-1))   n++;
		if (table.Get(x+1, y+1)) n++;
		if (table.Get(x-1, y-1)) n++;
		if (table.Get(x+1, y-1)) n++;
		if (table.Get(x-1, y+1)) n++;
		return n;
	}
}

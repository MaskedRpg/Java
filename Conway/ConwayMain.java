import javax.swing.*;

import visual.ConwayFrame;

public class ConwayMain {

	// Main method
	public static void main(String[] args) {
		ConwayFrame frame = new ConwayFrame(400, 400, "Conway Game of Life");
		while (frame.isVisible()) {
			try {
				frame.Next();
			} catch (Exception e) {
				e.printStackTrace();
				frame.dispose();
				break;
			}
		}
		System.exit(0);
	}

}

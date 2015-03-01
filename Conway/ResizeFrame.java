package visual;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class ResizeFrame extends JDialog {

	JFrame other;

	public ResizeFrame(JFrame other, String title) throws HeadlessException {
		super(other, true);
		setTitle(title);
		setLocationRelativeTo(other);
		this.other = other;
		createContent();
		setResizable(false);
		setSize(248, 96);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void createContent() {
		JPanel panel = new JPanel();
		// panel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		final JSpinner spinX = new JSpinner();
		spinX.setValue(other.getWidth());
		spinX.setModel(new SpinnerNumberModel(other.getWidth(), 134, screen
				.getWidth(), 1));
		spinX.setEditor(new JSpinner.NumberEditor(spinX, "####"));

		final JSpinner spinY = new JSpinner();
		spinY.setValue(other.getHeight());
		spinY.setModel(new SpinnerNumberModel(other.getHeight(), 134, screen
				.getHeight() - 128, 1));
		spinY.setEditor(new JSpinner.NumberEditor(spinY));

		JButton applyButton = new JButton();
		applyButton.setAction(new AbstractAction("Aplicar") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				other.setSize(
						(int) Double.parseDouble(spinX.getValue().toString()),
						(int) Double.parseDouble(spinY.getValue().toString()));
			}

		});

		JButton cancelButton = new JButton();
		cancelButton.setAction(new AbstractAction("Cancelar") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}

		});

		panel.add(new JLabel("X: "));
		panel.add(spinX);
		panel.add(new JLabel("Y: "));
		panel.add(spinY);
		panel.add(cancelButton);
		panel.add(applyButton);
		add(panel);
	}

	protected void exit() {
		this.dispose();
	}

}

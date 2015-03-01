package visual;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;
import javax.swing.plaf.basic.BasicFileChooserUI;

import lib.BinaryTable;
import lib.ConwayAlgorithm;

public class ConwayFrame extends JFrame {

	String title;
	ConwayImage image;
	BinaryTable table = new BinaryTable(1, 1);
	ConwayAlgorithm algorithm = new ConwayAlgorithm();
	JPanel mainPanel;

	public ConwayFrame(int width, int height, String name) {
		super();
		this.title = name;
		setResizable(false);
		createContents(width, height);
		setSize(width, height);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void createContents(int width, int height) {
		createMenu();
		setupImage(width, height);
		setupPanel();
		pack();
	}

	private void setupPanel() {
		mainPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, null);
			}
		};
		add(mainPanel);
	}

	private void setupImage(int width, int height) {
		image = new ConwayImage(width, height);
	}

	public void setSize(int width, int height) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		if (width > screen.getWidth())
			width = (int) screen.getWidth();
		if (height > (screen.getHeight() - 32))
			height = (int) screen.getHeight();
		if (width < 134)
			width = 134;
		if (height < 134)
			height = 134;
		super.setSize(width, height + 32);
		setTitle(title + " (" + width + "x" + height + ")");
		table.Resize(width, height);
		setupImage(width, height);
		setupPanel();
		pack();
		super.setSize(width, height + 32);
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menuFile = new JMenu("Arquivo");

		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image files",
				"png", "bmp", "jpg", "gif"));

		JMenuItem menuFileOpen = new JMenuItem();
		menuFileOpen.setAction(new AbstractAction("Abrir", new ImageIcon(
				"open.png")) {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.showOpenDialog(null);
				if (fileChooser.getSelectedFile() == null)
					return;
				try {
					BufferedImage i = ImageIO.read(fileChooser
							.getSelectedFile());
					setSize(i.getWidth(), i.getHeight());
					table = BinaryTable.fromImage(i);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao carregar arquivo", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		menuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				KeyEvent.CTRL_DOWN_MASK));

		JMenuItem menuFileSave = new JMenuItem();
		menuFileSave.setAction(new AbstractAction("Salvar", new ImageIcon(
				"save.png")) {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.showSaveDialog(null);
				if (fileChooser.getSelectedFile() == null)
					return;
				try {
					ImageIO.write(table.toImage(), "png",
							fileChooser.getSelectedFile());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao salvar arquivo", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		menuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.CTRL_DOWN_MASK));

		menuFile.add(menuFileOpen);
		menuFile.add(menuFileSave);

		JMenu menuGame = new JMenu("Jogo");

		JMenuItem menuGameStart = new JMenuItem();
		menuGameStart.setAction(new AbstractAction("Reiniciar", new ImageIcon(
				"play.png")) {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				algorithm.Start(table);
			}
		});
		menuGameStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				KeyEvent.CTRL_DOWN_MASK));

		JMenuItem menuGameColor = new JMenuItem();
		menuGameColor.setAction(new AbstractAction("Recolorir", new ImageIcon(
				"recolor.png")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				image.sortColor();
			}
		});
		menuGameColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				KeyEvent.ALT_DOWN_MASK));

		JMenuItem menuGameResize = new JMenuItem();
		final JFrame frame = this;
		menuGameResize.setAction(new AbstractAction("Ajustar tamanho",
				new ImageIcon("resize.png")) {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ResizeFrame resFrame = new ResizeFrame(frame, "Redimensionar");
			}
		});
		menuGameResize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				KeyEvent.ALT_DOWN_MASK));

		menuGame.add(menuGameStart);
		menuGame.addSeparator();
		menuGame.add(menuGameColor);
		menuGame.add(menuGameResize);

		menuBar.add(menuFile);
		menuBar.add(menuGame);

		setJMenuBar(menuBar);
	}

	public int getHeight() {
		return super.getHeight() - 32;
	}

	public void Next() {
		if (!this.isVisible())
			return;
		if (!algorithm.isStarted())
			algorithm.Start(table);
		algorithm.Next(table);
		image.Draw(table);
		mainPanel.repaint();
	}

}

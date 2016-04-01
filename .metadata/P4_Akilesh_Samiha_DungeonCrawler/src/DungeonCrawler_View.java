import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DungeonCrawler_View {
	// testing

	BufferedImage[][] gameBoard;
	Boolean[][] playerBoard;

	MyGUI gui;

	public DungeonCrawler_View() {

		// DEBUG
		gameBoard = new BufferedImage[10][10];
		playerBoard = new Boolean[10][10];

		gui = new MyGUI(gameBoard, playerBoard);

		// use this method whenever an updated gameBoard needs to be given.
		gui.drawingPanel.giveOuterArray(gameBoard);
	}

	public BufferedImage[][] getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(BufferedImage[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

}

class MyGUI implements ActionListener {

	// Attributes
	Color color = Color.RED;
	MyDrawingPanel drawingPanel;
	MyPlayerPanel playerPanel;
	JFrame window;
	int count = 0;

	JLabel devs = new JLabel("S.R. / A.P.");

	JLabel ver = new JLabel("0.5");

	/*
	 * 0 - WHITE 1 - RED 2 - GREEN 3 - BLUE
	 */
	Color[][] saveState = new Color[20][20];

	MyGUI(BufferedImage[][] input, Boolean[][] player) {

		// Create Java Window
		window = new JFrame("DungeonCrawler");
		window.setBounds(100, 100, 445, 600);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create GUI elements

		// JPanel to draw in
		drawingPanel = new MyDrawingPanel();
		drawingPanel.localGameBoard = input;

		drawingPanel.setBounds(20, 20, 400, 400);
		drawingPanel.setBorder(BorderFactory.createEtchedBorder());

		playerPanel = new MyPlayerPanel();
		playerPanel.playerBoard = player;
		playerPanel.setBounds(20, 20, 400, 400);

		// JButton
		JButton button = new JButton("Reset");
		button.setBounds(125, 510, 90, 20);
		button.addActionListener(this);

		// JButton (color)
		JButton colorButton = new JButton("Custom Color");
		colorButton.setBounds(230, 510, 90, 20);
		colorButton.addActionListener(this);

		// placeholder for now

		// Add GUI elements to the Java window's ContentPane
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);

		JPanel flavorPanel = new JPanel();
		flavorPanel.setBorder(BorderFactory.createTitledBorder("Developers"));

		flavorPanel.setBounds(80, 425, 100, 70);
		flavorPanel.add(devs);

		JPanel flavorPanel2 = new JPanel();
		flavorPanel2.setBorder(BorderFactory.createTitledBorder("Version"));
		flavorPanel2.setBounds(270, 425, 100, 70);
		flavorPanel2.add(ver);

		// ADD ALL PANELS

		mainPanel.add(drawingPanel);
		mainPanel.add(playerPanel);
		mainPanel.add(flavorPanel);
		mainPanel.add(flavorPanel2);

		window.getContentPane().add(mainPanel);

		// Let there be light
		window.setVisible(true);

	}

	public void printLosingMessage() {
		JOptionPane.showInternalMessageDialog(window, "Sorry, you lost.", "Sorry", JOptionPane.PLAIN_MESSAGE);
	}

	public void actionPerformed(ActionEvent e) {

	}
	
	public void addKeyListener(KeyListener k) {
		drawingPanel.addKeyListener(k);
	}

	public void addMouseListener(MouseListener m) {
		drawingPanel.addMouseListener(m);
	}

	public void clearDraw() {
		for (int row = 0; row < 20; row++) {
			for (int col = 0; col < 20; col++) {
				saveState[row][col] = new Color(255, 255, 255);
			}
		}

		drawingPanel.repaint();
		drawingPanel.paintComponent(drawingPanel.getGraphics());

	}

	class MyDrawingPanel extends JPanel {

		// LOCAL ARRAY OF IMAGES
		BufferedImage[][] localGameBoard;

		// how we get the outside array to the inside
		public void giveOuterArray(BufferedImage[][] input) {
			localGameBoard = input;
		}

		static final long serialVersionUID = 1234567890L;

		public void paintComponent(Graphics g) {

			// This is what repaint calls, so we place the drawImg and stuff
			// here.

			int x = 0;
			int y = 0;

			for (int row = 0; row < 10; row++) {

				for (int col = 0; col < 10; col++) {
					g.drawImage(localGameBoard[row][col], col * 40, row * 40, 40, 40, null);

				}

			}

		}
	}

	class MyPlayerPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// LOCAL ARRAY OF IMAGES
		Boolean[][] playerBoard;
		BufferedImage playerImg;

		public MyPlayerPanel(){
			//setPlayerArray();
		}
		// how we get the outside array to the inside
		public void setPlayerArray(Boolean[][] input) {
			playerBoard = input;
		}

		public void setPlayer(BufferedImage input) {
			playerImg = input;
		}

		public void paintComponent(Graphics g) {

			// This is what repaint calls, so we place the drawImg and stuff
			// here.

			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					if(playerBoard[row][col])
						g.drawImage(playerImg, col * 40, row * 40, 40, 40, null);
				}

			}

		}
	}
}

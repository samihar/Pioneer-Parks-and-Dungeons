import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DungeonCrawler_View {
	// testing

	BufferedImage[][] gameBoard;
	Boolean[][] playerBoard;
	Boolean[][] monsterBoard;

	MyGUI gui;

	public DungeonCrawler_View() {

		// DEBUG
		gameBoard = new BufferedImage[10][10];
		playerBoard = new Boolean[10][10];
		monsterBoard = new Boolean[10][10];

		gui = new MyGUI(gameBoard, playerBoard, monsterBoard);

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
	MyMonsterPanel monsterPanel;
	MyLatePassPanel latepassPanel;
	JFrame window;
	int count = 0;
	int healthCount = 3;

	JLabel devs = new JLabel("S.R. / A.P.");

	JLabel ver = new JLabel("0.5");
	JLabel health = new JLabel("" + healthCount);

	JLabel latepass = new JLabel("0");
	/*
	 * 0 - WHITE 1 - RED 2 - GREEN 3 - BLUE
	 */
	Color[][] saveState = new Color[20][20];

	MyGUI(BufferedImage[][] input, Boolean[][] player, Boolean[][] monster) {

		// Create Java Window
		window = new JFrame("Pioneer Parks and Dungeons");
		window.setBounds(100, 100, 445, 600);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create GUI elements
		JMenuBar menubar = new JMenuBar();

		JMenu gameMenu = new JMenu("Game");
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem exit = new JMenuItem("Exit");
		gameMenu.add(newGame);
		gameMenu.add(exit);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem howToPlay = new JMenuItem("How To Play");
		JMenuItem about = new JMenuItem("About");
		helpMenu.add(howToPlay);
		helpMenu.add(about);

		menubar.add(gameMenu);
		menubar.add(helpMenu);

		// JPanel to draw in
		drawingPanel = new MyDrawingPanel();
		drawingPanel.localGameBoard = input;

		drawingPanel.setBounds(20, 20, 400, 400);
		drawingPanel.setBorder(BorderFactory.createEtchedBorder());

		playerPanel = new MyPlayerPanel();
		playerPanel.playerPath = player;
		playerPanel.setBounds(20, 20, 400, 400);

		monsterPanel = new MyMonsterPanel();
		monsterPanel.monsterPath = monster;
		monsterPanel.setBounds(20, 20, 400, 400);

		latepassPanel = new MyLatePassPanel();
		latepassPanel.setBounds(20, 20, 400, 400);

		// placeholder for now

		// Add GUI elements to the Java window's ContentPane
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);

		JPanel flavorPanel = new JPanel();
		flavorPanel.setBorder(BorderFactory.createTitledBorder("Developers"));
		flavorPanel.setBounds(0, 500, 80, 45);
		flavorPanel.add(devs);

		JPanel flavorPanel2 = new JPanel();
		flavorPanel2.setBorder(BorderFactory.createTitledBorder("Version"));
		flavorPanel2.setBounds(80, 500, 80, 45);
		flavorPanel2.add(ver);

		JPanel healthPanel = new JPanel();

		healthPanel.setBorder(BorderFactory.createTitledBorder("Health"));
		healthPanel.setBounds(100, 425, 75, 50);
		healthPanel.add(health);

		JPanel latepassCounter = new JPanel();

		latepassCounter.setBorder(BorderFactory.createTitledBorder("Late Passes Collected"));
		latepassCounter.setBounds(200, 425, 150, 50);
		latepassCounter.add(latepass);

		// ADD ALL PANELS

		mainPanel.add(drawingPanel);
		mainPanel.add(playerPanel);
		mainPanel.add(monsterPanel);
		mainPanel.add(latepassPanel);
		mainPanel.add(flavorPanel);
		mainPanel.add(flavorPanel2);
		mainPanel.add(healthPanel);

		mainPanel.add(latepassCounter);

		window.setJMenuBar(menubar);

		window.getContentPane().add(mainPanel);

		// Let there be light
		window.setVisible(true);

	}

	public void printLosingMessage() {
		JOptionPane.showMessageDialog(window, "Sorry, you lost. close to continue.", "Sorry",
				JOptionPane.PLAIN_MESSAGE);
	}

	public void printWarningMessage(int health) {
		JOptionPane.showMessageDialog(window, "You ran into the monster. Your health is down to " + health + "!",
				"Caught", JOptionPane.WARNING_MESSAGE);
	}

	public void showHelp() {
		JEditorPane helpContent;
		try {
			helpContent = new JEditorPane(new URL("file:HowToPlay.html"));
			JScrollPane helpPane = new JScrollPane(helpContent);
			helpPane.setPreferredSize(new Dimension(600, 600));
			JOptionPane.showMessageDialog(null, helpPane, "How To Play", JOptionPane.PLAIN_MESSAGE, null);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showAbout() {
		JEditorPane helpContent;
		try {
			helpContent = new JEditorPane(new URL("file:About.html"));
			JScrollPane helpPane = new JScrollPane(helpContent);
			helpPane.setPreferredSize(new Dimension(200, 150));
			JOptionPane.showMessageDialog(null, helpPane, "About", JOptionPane.PLAIN_MESSAGE, null);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean playAgain() {
		int response = JOptionPane.showInternalOptionDialog(window, "Want to  play again?", "Play Again?",
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
		if (response == JOptionPane.YES_OPTION) {
			return true;
		} else
			return false;
	}

	public void actionPerformed(ActionEvent e) {

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
		/**
		 * 
		 */

		// LOCAL ARRAY OF IMAGES
		Boolean[][] playerPath;
		BufferedImage playerImg;

		public MyPlayerPanel() {
			playerPath = new Boolean[10][10];
		}

		// how we get the outside array to the inside
		public void setPlayerArray(Boolean[][] input) {
			playerPath = input;
		}

		public void setPlayer(BufferedImage input) {
			playerImg = input;
		}

		public void paintComponent(Graphics g) {

			// This is what repaint calls, so we place the drawImg and stuff
			// here.

			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					if (playerPath[row][col] != null && playerPath[row][col])
						g.drawImage(playerImg, col * 40, row * 40, 40, 40, null);
				}

			}

		}
	}

	class MyLatePassPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// LOCAL ARRAY OF IMAGES
		Boolean[][] passPanel;
		BufferedImage passImg;

		public MyLatePassPanel() {
			try {
				passImg = ImageIO.read(new File("img/latepass.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			passPanel = new Boolean[10][10];
			for (int i = 0; i < passPanel.length; i++) {
				for (int j = 0; j < passPanel.length; j++) {
					passPanel[i][j] = false;
				}
			}
		}

		private void reset() {
			for (int i = 0; i < passPanel.length; i++) {
				for (int j = 0; j < passPanel.length; j++) {
					passPanel[i][j] = false;
				}
			}
		}

		public void addPasses(ArrayList<int[]> passes) {
			reset();
			while (passes.size() > 0) {
				int[] pos = passes.remove(0);
				passPanel[pos[0]][pos[1]] = true;
			}
		}

		public boolean deletePasses(int i, int j) {
			if (passPanel[i][j]) {
				passPanel[i][j] = false;
				return true;
			}
			return false;
		}

		public void paintComponent(Graphics g) {

			for (int row = 0; row < 10; row++) {

				for (int col = 0; col < 10; col++) {
					if (passPanel[row][col] != null && passPanel[row][col])
						g.drawImage(passImg, col * 40, row * 40, 40, 40, null);

				}

			}

		}
	}

	class MyMonsterPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// LOCAL ARRAY OF IMAGES
		Boolean[][] monsterPath;
		BufferedImage monsterImg;

		public MyMonsterPanel() {
			monsterPath = new Boolean[10][10];
			for (int i = 0; i < monsterPath.length; i++) {
				for (int j = 0; j < monsterPath.length; j++) {
					monsterPath[i][j] = false;
				}
			}
		}

		// how we get the outside array to the inside
		public void setMonsterArray(Boolean[][] input) {
			monsterPath = input;
		}

		public void setMonster(BufferedImage input) {
			monsterImg = input;
		}

		public void paintComponent(Graphics g) {

			// This is what repaint calls, so we place the drawImg and stuff
			// here.

			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					if (monsterPath[row][col] != null && monsterPath[row][col])
						g.drawImage(monsterImg, col * 40, row * 40, 40, 40, null);
				}

			}

		}
	}
}

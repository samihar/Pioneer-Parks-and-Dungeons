import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DungeonCrawler_View {
	// testing
	
	MyGUI gui;
	
	public DungeonCrawler_View() {
		gui = new MyGUI();
	}
}

class MyGUI implements ActionListener {

	// Attributes
	Color color = Color.RED;
	MyDrawingPanel drawingPanel;
	
	int count = 0;

	JLabel devs = new JLabel("S.R. / A.P.");
	
	
	JLabel ver = new JLabel("0.5");

	/*
	 * 0 - WHITE 1 - RED 2 - GREEN 3 - BLUE
	 */
	Color[][] saveState = new Color[20][20];

	MyGUI() {

		// Create Java Window
		JFrame window = new JFrame("DungeonCrawler");
		window.setBounds(100, 100, 445, 600);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create GUI elements
		
		// JPanel to draw in
		drawingPanel = new MyDrawingPanel();
		drawingPanel.setBounds(20, 20, 400, 400);
		drawingPanel.setBorder(BorderFactory.createEtchedBorder());

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

		mainPanel.add(flavorPanel);
		mainPanel.add(flavorPanel2);

		window.getContentPane().add(mainPanel);

		// Let there be light
		window.setVisible(true);

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

		static final long serialVersionUID = 1234567890L;


		public void paintComponent(Graphics g) {

			
			// This is what repaint calls, so we place the drawImg and stuff here.

			int x = 0;
			int y = 0;

			for (int row = 0; row < 10; row++) {

				for (int col = 0; col < 10; col++) {
					// g.setColor(saveState[row][col]);

					// g.drawImage(storage[row][col], col * 40, row * 40, 40, 40, null);
					// System.out.println("X = " + x + ", Y = " + y);

				}

			}

		}
	}
}

import java.awt.image.BufferedImage;

public class DungeonCrawler_Controller {
	DungeonCrawler_Model model;
	DungeonCrawler_View view;
	int numLevel;

	public DungeonCrawler_Controller() {
		model = new DungeonCrawler_Model();
		view = new DungeonCrawler_View();
		numLevel = 0;
	}

	public void play() {
		setup();
		System.out.println("Play Game");
	}

	public void nextLevel() {	
		numLevel++;
		BufferedImage[][] level = model.loadLevel(numLevel);
		view.setGameBoard(level);
		view.gui.drawingPanel.giveOuterArray(view.getGameBoard());
		view.gui.drawingPanel.repaint();
	}

	public void setup() {
		String name = "Sally"; // Replace with user input later
		model.student.setName(name);
		nextLevel();
	}

}

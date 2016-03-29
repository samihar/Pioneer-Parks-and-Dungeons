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
		if (model.student.getMyHealth() == 0) {
			view.gui.printLosingMessage();
		}
	}

	public void nextLevel() {
		numLevel++;
		BufferedImage[][] level = model.loadLevel(numLevel);
		view.setGameBoard(level);
		view.gui.drawingPanel.giveOuterArray(view.getGameBoard());
		view.gui.drawingPanel.repaint();
		view.gui.playerPanel.setPlayer(model.student.getPlayerImg());
		view.gui.playerPanel.repaint();
	}

	public void setup() {
		String name = "Sally"; // Replace with user input later
		model.student.setPlayerImg("player_sprite.png");
		view.gui.playerPanel.setPlayerArray(model.getPlayerPath());
		model.student.setName(name);
		nextLevel();
	}

}

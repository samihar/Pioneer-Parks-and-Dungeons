import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class DungeonCrawler_Controller {
	DungeonCrawler_Model model;
	DungeonCrawler_View view;
	int numLevel;

	public DungeonCrawler_Controller() {
		model = new DungeonCrawler_Model();
		view = new DungeonCrawler_View();
		numLevel = 0;
		view.gui.window.addKeyListener(new MyKeyAdapter());
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
		model.resetPlayerPath();
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

	private class MyKeyAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				nextLevel();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				int[] pos = model.getPlayerPos();
				if (pos[0] + 1 <model.gameBoard.length &&  model.gameBoard[pos[0] + 1][pos[1]].isWalkable()) {
					model.setPlayerPath(pos[0] + 1, pos[1], true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
				view.gui.drawingPanel.repaint();
				view.gui.playerPanel.repaint();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				int[] pos = model.getPlayerPos();
				if (pos[0] - 1 <model.gameBoard.length && model.gameBoard[pos[0] - 1][pos[1]].isWalkable()) {
					model.setPlayerPath(pos[0] - 1, pos[1], true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
				view.gui.drawingPanel.repaint();
				view.gui.playerPanel.repaint();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				int[] pos = model.getPlayerPos();
				if (pos[1] + 1 <model.gameBoard.length &&model.gameBoard[pos[0]][pos[1]+ 1].isWalkable()) {
					model.setPlayerPath(pos[0], pos[1] + 1, true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
				view.gui.drawingPanel.repaint();
				view.gui.playerPanel.repaint();
			} 
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				int[] pos = model.getPlayerPos();
				if (pos[1] - 1 >= 0 && model.gameBoard[pos[0]][pos[1] - 1].isWalkable()) {
					model.setPlayerPath(pos[0], pos[1] - 1, true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
				view.gui.drawingPanel.repaint();
				view.gui.playerPanel.repaint();
			}
			if (model.atStairs()){
				nextLevel();
			}
		}

	}

}

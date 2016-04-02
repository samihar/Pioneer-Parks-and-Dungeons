import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

public class DungeonCrawler_Controller {
	DungeonCrawler_Model model;
	DungeonCrawler_View view;
	int numLevel;
	Timer time;

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
		setInitialPosition();
		model.resetPlayerPath();
		view.setGameBoard(level);
		view.gui.drawingPanel.giveOuterArray(view.getGameBoard());
		view.gui.drawingPanel.repaint();
		view.gui.playerPanel.setPlayer(model.student.getPlayerImg());
		view.gui.playerPanel.repaint();
	}

	public void setInitialPosition(){
		if (numLevel % 2 == 1){
			model.student.setPlayerImg("player_sprite_up.png");
		} else{
			model.student.setPlayerImg("player_sprite_right.png");
		}
	}
	
	public void setup() {
		String name = "Sally"; // Replace with user input later
		model.student.setPlayerImg("player_sprite_up.png");
		view.gui.playerPanel.setPlayerArray(model.getPlayerPath());
		model.student.setName(name);
		nextLevel();
	}

	private class MyKeyAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				model.student.setPlayerImg("player_sprite_down.png");
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				model.student.setPlayerImg("player_sprite_down.png");
				view.gui.playerPanel.setPlayer(model.student.getPlayerImg());
				int[] pos = model.getPlayerPos();
				if (pos[0] + 1 < model.gameBoard.length && model.gameBoard[pos[0] + 1][pos[1]].isWalkable()) {

					model.setPlayerPath(pos[0] + 1, pos[1], true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
				view.gui.drawingPanel.repaint();
				view.gui.playerPanel.repaint();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				model.student.setPlayerImg("player_sprite_up.png");
				view.gui.playerPanel.setPlayer(model.student.getPlayerImg());
				int[] pos = model.getPlayerPos();
				if (pos[0] - 1 < model.gameBoard.length && model.gameBoard[pos[0] - 1][pos[1]].isWalkable()) {
					model.setPlayerPath(pos[0] - 1, pos[1], true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
				view.gui.drawingPanel.repaint();
				view.gui.playerPanel.repaint();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				model.student.setPlayerImg("player_sprite_right.png");
				view.gui.playerPanel.setPlayer(model.student.getPlayerImg());
				int[] pos = model.getPlayerPos();
				if (pos[1] + 1 < model.gameBoard.length && model.gameBoard[pos[0]][pos[1] + 1].isWalkable()) {
					model.setPlayerPath(pos[0], pos[1] + 1, true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
				view.gui.drawingPanel.repaint();
				view.gui.playerPanel.repaint();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				model.student.setPlayerImg("player_sprite_left.png");
				view.gui.playerPanel.setPlayer(model.student.getPlayerImg());
				int[] pos = model.getPlayerPos();
				if (pos[1] - 1 >= 0 && model.gameBoard[pos[0]][pos[1] - 1].isWalkable()) {
					model.setPlayerPath(pos[0], pos[1] - 1, true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
				view.gui.drawingPanel.repaint();
				view.gui.playerPanel.repaint();
			}
			if (model.atStairs()) {
				nextLevel();
			}
		}

	}

}

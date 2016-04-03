import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		setup();
		time = new Timer(500, null);
		time.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				moveMonster();
			}
			
		});
	}

	public void moveMonster(){
		model.enemy.setMonsterImg("enemy_sprite_down.png");
		int[] pos = model.getMonsterPos();
		if (pos[0] + 1 < model.gameBoard.length && model.gameBoard[pos[0] + 1][pos[1]].isWalkable()) {
			model.setMonsterPath(pos[0] + 1, pos[1], true);
			model.setMonsterPath(pos[0], pos[1], false);
		}
		repaint();
	}
	
	public void play() {		
		System.out.println("Play Game");
		if (model.student.getMyHealth() == 0) {
			view.gui.printLosingMessage();
		}
		time.start();
	}
	
	public void repaint(){
		view.gui.drawingPanel.giveOuterArray(view.getGameBoard());
		view.gui.drawingPanel.repaint();
		
		view.gui.playerPanel.setPlayer(model.student.getPlayerImg());
		view.gui.playerPanel.setPlayerArray(model.getPlayerPath());
		view.gui.playerPanel.repaint();

		view.gui.monsterPanel.setMonsterArray(model.getMonsterPath());
		System.out.println(model.enemy.getMonsterImg());
		view.gui.monsterPanel.setMonster(model.enemy.getMonsterImg());
		view.gui.monsterPanel.repaint();	
	}
	
	public void printArray(Boolean [][] b){
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++) {
				System.out.print(b[i][j]+  " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void nextLevel() {
		numLevel++;
		BufferedImage[][] level = model.loadLevel(numLevel);
		setInitialPosition();
		model.resetPlayerPath();
		model.resetMonsterPath();
		
		view.setGameBoard(level);
		
		repaint();			
	}

	
	public void setInitialPosition(){
		if (numLevel % 2 == 1){
			model.student.setPlayerImg("player_sprite_up.png");
			model.enemy.setMonsterImg("enemy_sprite_down.png");
		} else{
			model.student.setPlayerImg("player_sprite_right.png");
			model.enemy.setMonsterImg("enemy_sprite_down.png");
		}
	}
	
	public void setup() {
		String name = "Sally"; // Replace with user input later
		model.student.setPlayerImg("player_sprite_up.png");
		model.enemy.setMonsterImg("enemy_sprite_up.png");
		model.resetPlayerPath();
		model.resetMonsterPath();
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
				int[] pos = model.getPlayerPos();
				if (pos[0] + 1 < model.gameBoard.length && model.gameBoard[pos[0] + 1][pos[1]].isWalkable()) {
					model.setPlayerPath(pos[0] + 1, pos[1], true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				model.student.setPlayerImg("player_sprite_up.png");
				int[] pos = model.getPlayerPos();
				if (pos[0] - 1 < model.gameBoard.length && model.gameBoard[pos[0] - 1][pos[1]].isWalkable()) {
					model.setPlayerPath(pos[0] - 1, pos[1], true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				model.student.setPlayerImg("player_sprite_right.png");
				int[] pos = model.getPlayerPos();
				if (pos[1] + 1 < model.gameBoard.length && model.gameBoard[pos[0]][pos[1] + 1].isWalkable()) {
					model.setPlayerPath(pos[0], pos[1] + 1, true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				model.student.setPlayerImg("player_sprite_left.png");
				int[] pos = model.getPlayerPos();
				if (pos[1] - 1 >= 0 && model.gameBoard[pos[0]][pos[1] - 1].isWalkable()) {
					model.setPlayerPath(pos[0], pos[1] - 1, true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
			}
			if (model.atStairs()) {
				nextLevel();
			}
			repaint();
		}

	}

}

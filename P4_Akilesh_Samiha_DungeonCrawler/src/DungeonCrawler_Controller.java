import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import javax.swing.Timer;

public class DungeonCrawler_Controller {
	DungeonCrawler_Model model;
	DungeonCrawler_View view;
	int numLevel;
	int numLatePasses;
	int initNumPasses;
	Timer time;
	boolean lost;
	static final boolean DEBUG = false;

	public DungeonCrawler_Controller() {
		model = new DungeonCrawler_Model();
		view = new DungeonCrawler_View();
		numLevel = 0;
		numLatePasses = 0;
		initNumPasses = numLatePasses;
		lost = false;
		view.gui.window.addKeyListener(new MyKeyAdapter());
		setup();
		time = new Timer(500, null);
		time.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				moveMonster();
			}

		});
	}

	// debug
	public void pmatrix(Boolean[][] monsterPath) {
		if (DEBUG) {
			for (int i = 0; i < monsterPath.length; i++) {
				for (int j = 0; j < monsterPath[0].length; j++) {
					System.out.print(monsterPath[i][j] + " ");
				}
				System.out.print("\n");
			}
		}
	}

	public void moveMonster() {
		int[] v = { 0, 0, -1, 1 };
		int[] h = { -1, 1, 0, 0 };
		String[] img = { "enemy_sprite_down.png", "enemy_sprite_down.png", "enemy_sprite_up.png",
				"enemy_sprite_down.png" };
		int[] pos = model.getMonsterPos();
		Random r = new Random();
		int index = r.nextInt(v.length);

		while (!canWalk(pos, v[index], h[index])) {
			index = r.nextInt(v.length);
		}
		model.enemy.setMonsterImg(img[index]);
		model.setMonsterPath(pos[0] + v[index], pos[1] + h[index], true);
		model.setMonsterPath(pos[0], pos[1], false);

		repaint();
	}

	public boolean canWalk(int[] pos, int v, int h) {
		int vertical = pos[0] + v;
		int horizontal = pos[1] + h;
		return vertical >= 0 && vertical < model.gameBoard.length && horizontal >= 0
				&& horizontal < model.gameBoard[0].length && model.gameBoard[vertical][horizontal].isWalkable();
	}

	public void repaintMonster() {
		view.gui.monsterPanel.setMonsterArray(model.getMonsterPath());
		view.gui.monsterPanel.setMonster(model.enemy.getMonsterImg());
		view.gui.monsterPanel.repaint();
	}

	public void play() {
		System.out.println("Play Game");
		time.start();
	}

	public void repaint() {
		view.gui.drawingPanel.giveOuterArray(view.getGameBoard());
		view.gui.drawingPanel.repaint();

		view.gui.latepassPanel.repaint();
		view.gui.playerPanel.setPlayer(model.student.getPlayerImg());
		view.gui.playerPanel.setPlayerArray(model.getPlayerPath());
		view.gui.playerPanel.repaint();
		repaintMonster();
		checkDeath();
		checkLatepass();
	}
	
	public void checkLatepass(){
		int pos[] = model.getPlayerPos();
		if (view.gui.latepassPanel.deletePasses(pos[0], pos[1])){
			System.out.println("Pass!");
			numLatePasses++;
			view.gui.latepass.setText(""+numLatePasses);
			repaint();
		}
	}
	
	public void nextLevel() {
		numLevel++;
		numLatePasses  = initNumPasses;
		view.gui.latepass.setText(""+numLatePasses);
		BufferedImage[][] level = model.loadLevel(numLevel);
		setInitialPosition();
		model.resetPlayerPath();
		model.resetMonsterPath();
		view.setGameBoard(level);
		view.gui.latepassPanel.addPasses(model.getPasses(numLevel));
		repaint();
	}

	public void setInitialPosition() {
		if (numLevel % 3 == 1) {
			model.student.setPlayerImg("player_sprite_up.png");
			model.enemy.setMonsterImg("enemy_sprite_down.png");
		} else if (numLevel % 3 == 2){
			model.student.setPlayerImg("player_sprite_right.png");
			model.enemy.setMonsterImg("enemy_sprite_down.png");
		} else if (numLevel % 3 == 0){
			model.student.setPlayerImg("player_sprite_up.png");
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

	// KILLS PLAYER IF PLAYER WALKS ON ENEMY
	public void checkDeath() {
		int row1 = -1;
		int row2 = -2;

		int col1 = -3;
		int col2 = -4;

		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (model.monsterPath[row][col]) {
					row1 = row;
					col1 = col;
				}

			}

		}

		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (model.playerPath[row][col]) {
					row2 = row;
					col2 = col;
				}

			}

		}

		if (row1 == row2 && col1 == col2) {
			model.student.setMyHealth(model.student.getMyHealth() - 1);
			view.gui.health.setText("" + model.student.getMyHealth());
			time.stop();
			checkHealth();
			numLevel--;
			nextLevel();
			time.start();
		}
	}

	public void lose() {
		time.stop();
		view.gui.printLosingMessage();
		if (view.gui.playAgain()) {
			restartGame();
		} else {
			System.exit(0);
		}
	}

	public void checkHealth() {
		if (model.student.getMyHealth() <= 0) {
			lose();
		} else {
			view.gui.printWarningMessage(model.student.getMyHealth());
		}

	}

	public void restartGame() {
		numLevel = 0;
		model.student.setMyHealth(3);
		setup();
		play();
	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() != null) {
				System.out.println("Button Pressed : " + e.getActionCommand());
				if (e.getActionCommand().equals("New Game")) {
					time.stop();
					restartGame();
				}
				if (e.getActionCommand().equals("Exit")) {
					System.exit(0);
				}
				if (e.getActionCommand().equals("How To Play")) {
					view.gui.showHelp();
				}
				if (e.getActionCommand().equals("About")) {
					view.gui.showAbout();
				}
			}
		}

	}

	private class MyKeyAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				model.student.setPlayerImg("player_sprite_down.png");
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				model.student.setPlayerImg("player_sprite_down.png");
				int[] pos = model.getPlayerPos();
				if (canWalk(pos, 1, 0)) {
					model.setPlayerPath(pos[0] + 1, pos[1], true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				model.student.setPlayerImg("player_sprite_up.png");
				int[] pos = model.getPlayerPos();
				if (canWalk(pos, -1, 0)) {
					model.setPlayerPath(pos[0] - 1, pos[1], true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				model.student.setPlayerImg("player_sprite_right.png");
				int[] pos = model.getPlayerPos();
				if (canWalk(pos, 0, 1)) {
					model.setPlayerPath(pos[0], pos[1] + 1, true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				model.student.setPlayerImg("player_sprite_left.png");
				int[] pos = model.getPlayerPos();
				if (canWalk(pos, 0, -1)) {
					model.setPlayerPath(pos[0], pos[1] - 1, true);
					model.setPlayerPath(pos[0], pos[1], false);
				}
			}
			if (model.atStairs(false)) {
				initNumPasses =  numLatePasses;
				nextLevel();
			}

			repaint();
		}

	}

}

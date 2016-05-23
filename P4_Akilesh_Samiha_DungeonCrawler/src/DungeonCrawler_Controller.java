import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.Timer;

public class DungeonCrawler_Controller {
	DungeonCrawler_Model model;
	DungeonCrawler_View view;
	int numLevel;
	int numLatePasses;
	int initNumPasses;
	Timer time;
	Timer level4Timer;
	boolean monsterPresent;
	boolean usbPresent;
	static final boolean DEBUG = false;
	static final boolean DEBUG_LEVELS = true;
	static final int BONUS_TIME = 15;
	static final int NUM_ASSIGNMENTS = 12;

	public DungeonCrawler_Controller() {
		model = new DungeonCrawler_Model();
		view = new DungeonCrawler_View();
		numLevel = 0;
		numLatePasses = 0;
		usbPresent = false;
		initNumPasses = numLatePasses;
		monsterPresent = true;
		view.gui.window.addKeyListener(new MyKeyAdapter());
		view.gui.exit.addActionListener(new MyActionListener());
		view.gui.newGame.addActionListener(new MyActionListener());
		view.gui.howToPlay.addActionListener(new MyActionListener());
		view.gui.about.addActionListener(new MyActionListener());
		setup();
		time = new Timer(500, null);
		time.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (monsterPresent)
					moveMonster();
			}

		});
		level4Timer = new Timer(1000,null);
		level4Timer.addActionListener(new ActionListener() {
			int timeCount = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				timeCount++;
				view.gui.time.setText(""+ (BONUS_TIME - timeCount));
				if (timeCount == 15){
					level4Timer.stop();
					view.gui.bonusTimeUp();
					initNumPasses  = numLatePasses;
					nextLevel();
					timeCount= 0;
					view.gui.time.setText("N/A");
				}
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

	public void play() {
		time.start();
	}

	public boolean checkUSB(){
		int[] player = model.getPlayerPos();
		int[] usb = model.getUSB();
		return usbPresent && player[0] == usb[0] && player[1] == usb[1];
	}
	public void repaint() {
		view.gui.gamepanel.drawingPanel.giveOuterArray(view.getGameBoard());
		view.gui.gamepanel.playerPanel.setPlayer(model.student.getPlayerImg());
		view.gui.gamepanel.playerPanel.setPlayerArray(model.getPlayerPath());
		view.gui.gamepanel.monsterPanel.setMonsterArray(model.getMonsterPath());
		view.gui.gamepanel.monsterPanel.setMonster(model.enemy.getMonsterImg());
		view.gui.gamepanel.setIsUSBPresent(usbPresent);
		view.gui.gamepanel.repaint();
		checkDeath();
		checkLatepass();
	}

	public void checkLatepass() {
		int pos[] = model.getPlayerPos();
		if (view.gui.gamepanel.latepassPanel.deletePasses(pos[0], pos[1])) {
			numLatePasses++;
			view.gui.latepass.setText("" + numLatePasses);
			repaint();
		}
	}

	public void nextLevel() {
		numLevel++;
		if (numLevel > 3){
			monsterPresent = false;
		}
		numLatePasses = initNumPasses;
		view.gui.latepass.setText("" + numLatePasses);
		BufferedImage[][] level = model.loadLevel(numLevel);
		setInitialPosition();
		model.resetPlayerPath();
		model.resetMonsterPath();
		view.setGameBoard(level);
		view.gui.gamepanel.latepassPanel.addPasses(model.getPasses(numLevel));
		if (numLevel ==5){	
			view.gui.gamepanel.usb.addUSB(model.getUSB());
			usbPresent  =  true;
		}
		repaint();
		if (numLevel == 4){
			view.gui.time.setText(""+BONUS_TIME);
			repaint();
			view.gui.postBonus(NUM_ASSIGNMENTS, BONUS_TIME);			
			level4Timer.start();
		}
	}

	public void setInitialPosition() {
		if (numLevel  == 1) {
			model.student.setPlayerImg("player_sprite_up.png");
			model.enemy.setMonsterImg("enemy_sprite_down.png");
		} else if (numLevel == 2) {
			model.student.setPlayerImg("player_sprite_right.png");
			model.enemy.setMonsterImg("enemy_sprite_down.png");
		} else if (numLevel == 3) {
			model.student.setPlayerImg("player_sprite_up.png");
			model.enemy.setMonsterImg("enemy_sprite_down.png");
		} else if (numLevel == 4) {
			model.student.setPlayerImg("player_sprite_left.png");
		} else if (numLevel == 5) {
			model.student.setPlayerImg("player_sprite_right.png");
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
			model.enemy.setMonsterImg("enemy_sprite_up.png");			
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

	public void end() {
		time.stop();
		view.gui.printEndingMessage();
		double test = view.gui.askTest();
		String report = model.generateGradeReport(numLatePasses, NUM_ASSIGNMENTS, test);
		view.gui.printReport(report);
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
		usbPresent = false;
		numLatePasses = 0;
		initNumPasses = 0;
		model.student.setMyHealth(3);
		monsterPresent = true;
		view.gui.health.setText("" + model.student.getMyHealth());
		view.gui.latepass.setText("" + numLatePasses);
		view.gui.time.setText("N/A");
		setup();
		play();
	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() != null) {
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
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (DEBUG_LEVELS) {
					nextLevel();
				}
			}

			repaint();
			if (checkUSB()){
				end();
			}
			if (model.atStairs()) {
				initNumPasses = numLatePasses;
				nextLevel();
				repaint();
			}
		}

	}

}

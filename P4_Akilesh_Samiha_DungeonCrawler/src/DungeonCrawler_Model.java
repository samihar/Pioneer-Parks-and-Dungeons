import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class DungeonCrawler_Model {
	Player student;
	Monster enemy;
	int numLevel;
	Boolean[][] playerPath;
	Boolean[][] monsterPath;
	static final int[] INITIAL_POSITION_LEVEL_1P = { 8, 4 };
	static final int[] INITIAL_POSITION_LEVEL_2P = { 1, 0 };
	static final int[] INITIAL_POSITION_LEVEL_3P = { 9, 4 };
	static final int[] INITIAL_POSITION_LEVEL_4P = { 9, 9 };
	static final int[] INITIAL_POSITION_LEVEL_5P = { 9, 0 };

	static final int[] MONSTER_POSITION_LEVEL_1 = { 2, 4 };
	static final int[] MONSTER_POSITION_LEVEL_2 = { 7, 1 };
	static final int[] MONSTER_POSITION_LEVEL_3 = { 3, 3 };
	static final int[] MONSTER_POSITION_LEVEL_4 = { 3, 3 };

	public Tile[][] gameBoard = new Tile[10][10];

	static final int[] STAIR_POSITION_LEVEL_1_1 = { 0, 4 };
	static final int[] STAIR_POSITION_LEVEL_1_2 = { 0, 5 };
	static final int[] STAIR_POSITION_LEVEL_2 = { 1, 9 };
	static final int[] STAIR_POSITION_LEVEL_3_1 = { 0, 4 };
	static final int[] STAIR_POSITION_LEVEL_3_2 = { 0, 5 };

	static final int[][] LEVEL_1_PASSES = { { 1, 8 }, { 3, 4 }, { 4, 1 } };
	static final int[][] LEVEL_2_PASSES = { { 2, 7 }, { 7, 5 }, { 8, 2 } };
	static final int[][] LEVEL_3_PASSES = { { 1, 8 }, { 4, 4 }, { 3, 6 } };
	static final int[][] LEVEL_4_PASSES = { { 1, 8 }, { 3, 8 }, { 5, 8 }, { 7, 8 }, { 1, 9 }, { 3, 9 }, { 5, 9 },
			{ 7, 9 } };

	static final int[] LEVEL_5_USB = { 4, 4 };

	public DungeonCrawler_Model() {
		numLevel = 0;
		student = new Player();
		playerPath = new Boolean[10][10];
		monsterPath = new Boolean[10][10];
		enemy = new Monster("enemy_sprite_down.png");
		resetPlayerPath();
	}

	public String generateGradeReport(int numLatePasses, int numHW, double test) {
		double testPercent = test + 0.0;
		double hwPercent = 100;
		double percent = 0;
		if (numHW >= numLatePasses) {
			hwPercent *= (double) numLatePasses / (double) numHW;
		} else {
			double multiplier = 1 + ((double) (numLatePasses - numHW)) / ((double) numHW * 10.0);
			hwPercent *= multiplier;
		}
		percent = 0.6 * testPercent + 0.4 * hwPercent;
		String grade = getGrade(percent);
		
		String gradeReport = "Your final grade report:\n" + "Tests/Quizzes (60%): " + Math.round(testPercent)+ "%\n"
				+ "Homework (40%): " + Math.round(hwPercent) + "%\n" + "Score: " + Math.round(percent) + "%\n" + "Grade: " + grade;

		return gradeReport;
	}

	public double round(double n){
		double rounded = 0;
		String roundStr = Double.toString(n);
		int index = roundStr.indexOf(".");
		if (index == -1 || index + 2 <= roundStr.length() -1){
			rounded = n;
		} else{
			String part1 = roundStr.substring(0, index);
			String part2 = roundStr.substring(index + 1, index + 3);
			roundStr = part1 + part2;
			rounded = Double.parseDouble(roundStr);
		}
		return rounded;
	}
	public String getGrade(double percent) {
		if (percent >= 100) {
			return "A+\nWOW! You might just be the next CS Based god o.O";
		} else if (percent >= 90) {
			return "A\nAmazing!!! You were born to program!";
		} else if (percent >= 80) {
			return "B\nNice job! A programmer in the works!";
		} else if (percent >= 70) {
			return "C\nGood job, you passed the class!";
		} else if (percent >= 60) {
			return "D\nI regret to inform you that you did not pass APCS.";
		} else{
			return "F\nYou did not pass APCS.";
		}
	}

	public ArrayList<int[]> getPasses(int level) {
		ArrayList<int[]> passes = new ArrayList<int[]>();
		if (level == 1) {
			for (int[] i : LEVEL_1_PASSES) {
				passes.add(i);
			}
		} else if (level == 2) {
			for (int[] i : LEVEL_2_PASSES) {
				passes.add(i);
			}
		} else if (level == 3) {
			for (int[] i : LEVEL_3_PASSES) {
				passes.add(i);
			}
		} else if (level == 4) {
			for (int[] i : LEVEL_4_PASSES) {
				passes.add(i);
			}
		}
		return passes;
	}

	public int[] getUSB() {
		return LEVEL_5_USB;
	}

	public void removeMonster() {
		for (int i = 0; i < monsterPath.length; i++) {
			for (int j = 0; j < monsterPath.length; j++) {
				monsterPath[i][j] = false;

			}
		}
	}

	public int[] getPlayerPos() {
		for (int i = 0; i < playerPath.length; i++) {
			for (int j = 0; j < playerPath.length; j++) {
				if (playerPath[i][j]) {
					int[] pos = { i, j };
					return pos;
				}
			}
		}
		int[] pos = { -1, -1 };
		return pos;
	}

	public int[] getMonsterPos() {
		for (int i = 0; i < monsterPath.length; i++) {
			for (int j = 0; j < monsterPath.length; j++) {
				if (monsterPath[i][j]) {
					int[] pos = { i, j };
					return pos;
				}
			}
		}
		int[] pos = { -1, -1 };
		return pos;
	}

	public boolean atStairs() {
		int[] pos = getPlayerPos();
		if (numLevel == 1)
			return (pos[0] == STAIR_POSITION_LEVEL_1_1[0] && pos[1] == STAIR_POSITION_LEVEL_1_1[1])
					|| (pos[0] == STAIR_POSITION_LEVEL_1_2[0] && pos[1] == STAIR_POSITION_LEVEL_1_2[1]);
		else if (numLevel == 2) {
			return pos[0] == STAIR_POSITION_LEVEL_2[0] && pos[1] == STAIR_POSITION_LEVEL_2[1];
		} else if (numLevel == 3) {
			return (pos[0] == STAIR_POSITION_LEVEL_3_1[0] && pos[1] == STAIR_POSITION_LEVEL_3_1[1])
					|| (pos[0] == STAIR_POSITION_LEVEL_3_2[0] && pos[1] == STAIR_POSITION_LEVEL_3_2[1]);
		}
		return false;
	}

	public void resetMonsterPath() {
		for (int i = 0; i < monsterPath.length; i++) {
			for (int j = 0; j < monsterPath[0].length; j++) {
				this.monsterPath[i][j] = false;
			}
		}
		if (numLevel == 1)
			setMonsterPath(MONSTER_POSITION_LEVEL_1[0], MONSTER_POSITION_LEVEL_1[1], true);
		else if (numLevel == 2) {
			setMonsterPath(MONSTER_POSITION_LEVEL_2[0], MONSTER_POSITION_LEVEL_2[1], true);
		} else if (numLevel == 3) {
			setMonsterPath(MONSTER_POSITION_LEVEL_3[0], MONSTER_POSITION_LEVEL_3[1], true);
		}

	}

	public void resetPlayerPath() {
		for (int i = 0; i < playerPath.length; i++) {
			for (int j = 0; j < playerPath[0].length; j++) {
				this.playerPath[i][j] = false;
			}
		}
		if (numLevel == 1)
			setPlayerPath(INITIAL_POSITION_LEVEL_1P[0], INITIAL_POSITION_LEVEL_1P[1], true);
		else if (numLevel == 2) {
			setPlayerPath(INITIAL_POSITION_LEVEL_2P[0], INITIAL_POSITION_LEVEL_2P[1], true);
		} else if (numLevel == 3) {
			setPlayerPath(INITIAL_POSITION_LEVEL_3P[0], INITIAL_POSITION_LEVEL_3P[1], true);
		} else if (numLevel == 4) {
			setPlayerPath(INITIAL_POSITION_LEVEL_4P[0], INITIAL_POSITION_LEVEL_4P[1], true);
		} else if (numLevel == 5) {
			setPlayerPath(INITIAL_POSITION_LEVEL_5P[0], INITIAL_POSITION_LEVEL_5P[1], true);
		}

	}

	public void setMonsterPath(int i, int j, boolean hasMonster) {
		this.monsterPath[i][j] = hasMonster;
	}

	public void setPlayerPath(int i, int j, boolean hasPlayer) {
		this.playerPath[i][j] = hasPlayer;
	}

	// MOVING PLAYER (UP/LEFT/DOWN/RIGHT)
	public void moveRight(int currI, int currJ) {
		// Remove player from current spot
		if (gameBoard[currI + 1][currJ].isWalkable()) {

		}
	}

	public Boolean[][] getPlayerPath() {
		return playerPath;
	}

	public Boolean[][] getMonsterPath() {
		return monsterPath;
	}

	@SuppressWarnings("resource")
	public BufferedImage[][] loadLevel(int numLev) {
		numLevel = numLev;
		BufferedImage[][] level = new BufferedImage[10][10];
		ArrayList<BufferedImage> list = new ArrayList<BufferedImage>();

		ArrayList<Tile> tileStorage = new ArrayList<Tile>();
		try {
			Scanner in = null;
			in = new Scanner(new File("levels/level" + (numLevel) + ".txt"));
			while (in != null && in.hasNext()) {
				try {
					String str = in.next().substring(1);
					BufferedImage tileImg = ImageIO.read(new File("img/" + str));
					String str2 = in.next();
					String walk = str2.substring(0, str2.length() - 1);
					boolean walkable = false;
					if (walk.equals("true"))
						walkable = true;

					Tile t = new Tile(tileImg, walkable);
					// Add the tile we created to a tileStorage ArrayList
					tileStorage.add(t);

					list.add(tileImg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ADD IMAGES TO THE BUFFEREDIMAGE ARRAY TO GIVE TO VIEW

		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[0].length; j++) {
				level[i][j] = list.remove(0);
			}
		}
		// ADD TILES TO GAMEBOARD
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[0].length; j++) {
				gameBoard[i][j] = tileStorage.remove(0);
			}
		}
		return level;
	}
}

class Tile {
	BufferedImage tileImage;
	boolean walkable;

	public Tile(BufferedImage img, boolean walk) {
		tileImage = img;
		walkable = walk;
	}

	public BufferedImage getTileImage() {
		return tileImage;
	}

	public void setTileImage(BufferedImage tileImage) {
		this.tileImage = tileImage;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	@Override
	public String toString() {
		return "[ " + walkable + " ]";
	}
}

class Player {
	BufferedImage playerImg;
	int myHealth;
	int myLatePasses;
	String name;

	public Player() {
		myHealth = 3;
		myLatePasses = 0;
		name = "Bob";
		setPlayerImg("player_sprite_up.png");
	}

	public BufferedImage getPlayerImg() {
		return playerImg;
	}

	public void setPlayerImg(String fileName) {
		try {
			this.playerImg = ImageIO.read(new File("img/sprites/" + fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public int getMyHealth() {
		return myHealth;
	}

	public void setMyHealth(int myHealth) {
		this.myHealth = myHealth;
	}

	public int getMyLatePasses() {
		return myLatePasses;
	}

	public void setMyLatePasses(int myLatePasses) {
		this.myLatePasses = myLatePasses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

class Monster {
	BufferedImage monsterImg;
	int health;
	String name;

	public Monster(String fileName) {
		health = 5;
		try {
			monsterImg = ImageIO.read(new File("img/sprites/enemy_sprite_down.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedImage getMonsterImg() {
		return monsterImg;
	}

	public void setMonsterImg(String fileName) {
		try {
			monsterImg = ImageIO.read(new File("img/sprites/" + fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}

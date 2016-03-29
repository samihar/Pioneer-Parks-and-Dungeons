import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class DungeonCrawler_Model {
	Player student;
	Boolean  [][]  playerPath;
	static final int[] INITIAL_POSITION_LEVEL_1 = {8,4};
			
	public DungeonCrawler_Model() {
		student = new Player();
		playerPath = new Boolean[10][10];
		resetPlayerPath();
	}
	
	public void resetPlayerPath(){
		for (int i = 0; i < playerPath.length; i++) {
			for (int j = 0; j < playerPath[0].length; j++) {
				this.playerPath[i][j] = false;
			}
		}
		setPlayerPath(INITIAL_POSITION_LEVEL_1[0], INITIAL_POSITION_LEVEL_1[1], true);
	}
	
	public void setPlayerPath(int  i, int j, boolean hasPlayer) {
		this.playerPath[i][j] = hasPlayer;
	}

	public Boolean[][] getPlayerPath() {
		return playerPath;
	}

	public BufferedImage[][] loadLevel(int numLevel) {
		BufferedImage[][] level = new BufferedImage[10][10];
		ArrayList<BufferedImage> list = new ArrayList<BufferedImage>();
		try {
			Scanner in = null;
			if (numLevel == 1)
				in = new Scanner(new File("level1.txt"));
			else if (numLevel == 2) {
				in = new Scanner(new File("level2.txt"));
			}
			while (in != null && in.hasNext()) {
				try {
					String str = in.next().substring(1);
					BufferedImage tileImg = ImageIO.read(new File(str));
					String str2 = in.next();
					String walk = str2.substring(0, str2.length() - 1);
					boolean walkable = false;
					if (walk.equals("true"))
						walkable = true;
					Tile t = new Tile(tileImg, walkable);
					list.add(tileImg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[0].length; j++) {
				level[i][j] = list.remove(0);
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
}

class Player {
	BufferedImage playerImg;
	int myHealth;
	int myLatePasses;
	String name;

	public Player() {
		myHealth = 10;
		myLatePasses = 0;
		name = "Bob";
		setPlayerImg("player_sprite.png");
	}

	public BufferedImage getPlayerImg() {
		return playerImg;
	}

	public void setPlayerImg(String fileName) {
		try {
			this.playerImg = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	int health;

	public Monster() {
		health = 5;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}

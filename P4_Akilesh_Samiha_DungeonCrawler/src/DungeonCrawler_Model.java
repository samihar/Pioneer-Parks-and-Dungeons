
public class DungeonCrawler_Model {
	Player student;
	public DungeonCrawler_Model(){
		student = new Player();
	}
}

class Player{
	int myHealth;
	int myLatePasses;
	String name;
	
	public Player(){
		myHealth = 10;
		myLatePasses = 0;
		name = "Bob";
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

class Monster{
	int health;
	
	public Monster(){
		health = 5;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
}

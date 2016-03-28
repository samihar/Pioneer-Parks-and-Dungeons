
public class DungeonCrawler_Controller {
	DungeonCrawler_Model model;
	DungeonCrawler_View view;
	
	public DungeonCrawler_Controller(){
		model = new DungeonCrawler_Model();
		view = new DungeonCrawler_View();
	}
	
	public void play(){
		System.out.println("Play Game");
	}
}

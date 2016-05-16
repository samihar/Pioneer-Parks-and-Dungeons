/**
 * 
 * Samiha Rahman 
 * 5/15/2016 
 * Period 4 
 * Time: 3 hours
 * 
 * I spent about 1.5 hours with creating a new level because for some reason
 * there was a out of bounds exception for level3.txt. For a long time, I was
 * confused as it had the same format as the other levels. However, I decided to
 * print the size and noticed the size of the list of tiles was short.
 * Therefore, after noticing that I figured out where the problem was and
 * finally noticed an extra bracket in the the file (probably when changing
 * walkable to false for a tree). Another hour went into figuring out the late
 * pass, which I did using a similar format used for the monster and player. I
 * had to adjust the counter for the pass to reset to the initial count at the
 * beginning of a level every time the player loses helath and restarts the
 * levell. Things I still need to debug involves the window itself as there
 * seems to be a double window/menubar thing going on which is why none of the
 * menu items work.
 *
 */

public class DungeonCrawler_DCGame {
	public static void main(String[] args) {
		DungeonCrawler_Controller controller = new DungeonCrawler_Controller();
		controller.play();
	}
}
/**
 * 
 * Samiha Rahman 5/22/2016 Period 4 Time: 4 hours
 * 
 * Doing it over several days, if I compressed the amount of time spent, it
 * might be about 4-5 hours. I met my goal of finishing my game and calculating
 * the grade. At first I was not sure how to do the timer for the bonus level. I
 * wanted to only show the timer during level 4, but that was causing problems
 * and looked a little odd (cut off), so I just kept it in all levels and put
 * "N/A" to let the player know that there was no time constraint for the other
 * levels Things I might work on in the future might be fixing the time display
 * and working on my round() method in the model class. I ended up using
 * Math.round() because my round() method was causing issues, but I want to get
 * it so that it returns a rounded double to the hundredths place.
 *
 */

public class DungeonCrawler_DCGame {
	public static void main(String[] args) {
		DungeonCrawler_Controller controller = new DungeonCrawler_Controller();
		controller.play();
	}
}
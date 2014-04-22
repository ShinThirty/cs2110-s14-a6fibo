package danaus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.imageio.ImageIO;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/** This class contains the static functions and constants used commonly
 * throughout the danaus package.
 */
public class Common {
    ////////////////////////////////////////////////////////////////////////////
    // Constants
    ////////////////////////////////////////////////////////////////////////////
	static final boolean A3 = false;
	static final boolean A6 = true;
	
	
    /** Slow down and power costs.
     *
     * (*)_SLOW_DOWN is the value added to slowTurns when flying over *.  
     * (*)_POWER_COST is the value subtracted from power when flying over *.  
     */
	static final int CLIFF_SLOW_DOWN= Integer.MAX_VALUE;
	static final int CLIFF_POWER_COST= Integer.MAX_VALUE;
	static final int LAND_SLOW_DOWN= 0;
	static final int LAND_POWER_COST= 0;
	static final int FOREST_SLOW_DOWN= 1;
	static final int FOREST_POWER_COST= 0;
	static final int WATER_SLOW_DOWN= Integer.MAX_VALUE;
	static final int WATER_POWER_COST= Integer.MAX_VALUE;

	/** The power cost of colliding with water. */
	public static final int WATER_COLLISION_POWER_COST= 10;
	/** The slow down of colliding with water. */
	public static final int WATER_COLLISION_SLOW_DOWN= 0;
	
    /** Tunable constants to help construct the minimum aroma intensity. */
	static int MAXIMUM_STEPS= Integer.MAX_VALUE;
	/** The smallest an aroma can be to appear in a tile's list of aromas. */
	public static final double MIN_AROMA_INTENSITY= 
	        Common.AROMA_INTENSITY / (Math.pow(MAXIMUM_STEPS, 2));

	/** The power it costs for a butterfly to get state. */
	static final int REFRESH_STATE_POWER_COST= 5;
	
    /** The power it costs when a butterfly inaccurately collects a flower. */
	static final int WRONG_COLLECT_POWER_COST= 50;
	
    /** The power cost of colliding with a cliff. */
	public static final int CLIFF_COLLISION_POWER_COST = 5;
	/** The slow down of colliding with a cliff. */
	public static final int CLIFF_COLLISION_SLOW_DOWN = 0;
	
    /** Debugging on-off switches. When an enable is set to to true, it's 
	 * corresponding debugging function will produce output. Otherwise, it will
	 * not. For example, if DEBUG_ENABLED is true, the DEBUG method will print
	 * to standard output. If DEBUG_ENABLES is false, DEBUG does nothing. */
	public static boolean DEBUG_ENABLED   = false;
	public static boolean WARNING_ENABLED = false;
	public static boolean ERROR_ENABLED   = true;
	
    /** NULL_CHECK checks for null iff NULL_CHECK_ENABLED is true. */
	public static boolean NULL_CHECK_ENABLED= true;
	static final double AROMA_INTENSITY= 1e6;
	
    /* Both these colors were taken from http://ethanschoonover.com/solarized */
	/** A nice off-white background color for the GUI background */
	public static Color BACKGROUND_COLOR= new Color(253, 246, 227);
	/** A contrasting color that lies between components. */
	public static Color MARGIN_COLOR= new Color(0, 43, 54);
	
    /** The minimum fraction of total flowers that the butterfly must find. */
	public static double MIN_REQUIRED_FLOWERS_FRACTION= 0.25;
	/** The maximum fraction of total flowers that the butterfly must find. */
	public static double MAX_REQUIRED_FLOWERS_FRACTION = 0.75;
	
    /** Highest power possible. */
	public static final int MAX_POWER= 100;
	/** Lowest power possible before a butterfly is forced to land. */
	public static final int MIN_POWER= 0;
    /** The power assigned to a Power object instantiated with the default 
	 * constructor. It is >= MIN_POWER. */
	public static final int DEFAULT_POWER= MAX_POWER;
	
    /** Use a GUI iff HEADLESS is true. */
	public static boolean HEADLESS;
	/** The seed parsed from the command line. */
	public static Integer SEED;
	/** The name of the map file parsed from the command line */
	public static String MAP_FILENAME;
	/** If true, a butterfly has infinite energy. */
	public static boolean INFINITE_ENERGY;
	/** The names of the classes parsed from the command line. */
	public static List<String> CLASS_NAMES= new ArrayList<String>();
	
	/** The root directory of all tile images. */
	public static String TILES_DIR= "res/tiles/";
	
    /** A coefficient used to calculate the power costs associated with wind. 
	 * @see danaus.Map#updateCosts(Position, Speed) */
	public static final int WIND_POWER_COEFFICIENT= 1;
	/** A coefficient used to calculate the slow down associated with wind. 
	 * @see danaus.Map#updateCosts(Position, Speed) */
	public static final int WIND_SLOW_DOWN_COEFFICIENT= 1;
	
    /** The tile text tokens used in map XML files. */
	static final String LAND_TOKEN      = "#";
	static final String WATER_TOKEN     = "~";
	static final String FOREST_TOKEN    = "|";
	static final String CLIFF_TOKEN     = "^";
	static final String BUTTERFLY_TOKEN = "B";
	
    /** When true, an ascii representation of the map is printed to the screen
	 * during generation. This process is known as drawing. */
	static final boolean SKETCH= false;
	/** Time to sleep after every sketch, in milliseconds. */
	static final int SKETCH_TIME= 10;
	/** When true, an ascii representation of the map is printed to the screen
	 * during flight. This process is known as drawing. */
	static final boolean DRAW= false;
	/** Time to sleep after every draw, in milliseconds. */
	static final int DRAW_TIME= 100;
	/** If true, drawing and sketching are instantaneous. */
	static boolean instadraw= false;
	
    /** The inclusive lower bound of a randomly generated map height. */
	static final int MIN_HEIGHT= 20;
	/** The inclusive upper bound of a randomly generated map height. */
	static final int MAX_HEIGHT= 40;
	/** The lower bound on a randomly generated map width. */
	static final int MIN_WIDTH= 20;
	/** The upper bound on a randomly generated map width. */
	static final int MAX_WIDTH= 40;
	/** The desired fraction of map tiles that are not water (i.e. Land, Forest,
	 * or Cliff.). The actual fraction of non-water tiles is not guaranteed to
	 * equal LAND_FRACTION, but is guaranteed to be a close approximation. */
	static final double LAND_FRACTION;
	/** Out of 10, the probability that a frontiersman will be selected from 
	 * the free frontier. For example, a free probability of 8 signifies that
	 * 80% of the time, a tile from the free frontier will be grown.
	 * <br>
	 * The smoothness of a map's border is inversly proportional to
	 * FREE_PROBABILITY. That is, a larger FREE_PROBABILITY will produce a more
	 * jagged border.
	 * 
	 * @see danaus.Map#initRandomTiles() */
	static final int FREE_PROBABILITY= 8;
	/** Out of 1000, the probability that a land tile will be seeded as a 
	 * cliff. As CLIFF_SEED_PROBABILITY grows larger, the number of cliff
	 * ranges on the map increases. The average length of each cliff range also
	 * decreases, as the total number of cliffs is held constant and determined
	 * by CLIFF_FRACTION. */
	static final int CLIFF_SEED_PROBABILITY;
	/** The desired fraction of map tiles that are cliffs. This is
	 * distinct from CLIFF_SEED_PROBABILITY which dealt with mountain seeding,
	 * not total cliff percentage. The actual fraction of tiles that are cliffs
	 * is not guaranteed to match CLIFF_FRACTION. Usually, CLIFF_FRACTION is
	 * larger than the true fraction of cliffs. */ 
	static final double CLIFF_FRACTION= 0.1;
	/** The average length of a cliff range. */
	static int AVERAGE_CLIFF_LENGTH;
	/** The fraction of AVERAGE_CLIFF_LENGTH that the length of a cliff range
	 * varies. For example, a DELTA_CLIFF_LENGTH of 0 signifies that all cliff
	 * ranges should ideally be the same length. In practice, mountain ranges
	 * may be shorter than DELTA_CLIFF_LENGTH. */
	static double DELTA_CLIFF_LENGTH;
	/** The maximum length of a cliff range. This maximum is guaranteed. */
	static int MIN_CLIFF_LENGTH;
	/** The ideal minimum length a cliff range. */
	static int MAX_CLIFF_LENGTH;
	/** Out of (1000-CLIFF_SEED_PROBABILITY), the probability that a land tile
	 * will be seeded as a forest. For example, a forest probability of
	 * 10 + CLIFF_PROBABILITY means that (10/1000) land tiles will be seeded as
	 * a forest. */
	static final int FOREST_SEED_PROBABILITY;
	/** The desired fraction of map tiles that are forest. This is
	 * different than FOREST_SEED_PROBABILITY because FOREST_SEED_PROBABILITY
	 * relates to the probability of a tile being seeded. This relates to the
	 * actual forest fraction after random generation is complete. */
	static final double FOREST_FRACTION= .3;
	/** Out of 1000, the probability that a forest's neighbor will become a 
	 * tree during forest growth. @see danaus.Map#initRandomTiles(). */
	static int FOREST_GROW_PROBABILITY= 625; // 5/8
	/** The maximum number of forests. Once the width and height of a map are 
	 * calculated, this will be equal to width * height * LAND_FRACTION *
	 * FOREST_FRACTION. */
	static int MAX_FORESTS;
	/** The total number of flower images available. */
	static final int NUMBER_FLOWERS= 100;
	/** The list of all available flower image number suffixes. flower images
	 * are saved in the format "flower_<x>.png" where <x> is a number.*/
	static final Integer[] FLOWER_NUMBERS;
	/** Out of 1000, the probability that a land tile will be seeded with a
	 * flower. */
	static int FLOWER_SEED_PROBABILITY;
	
	static {
		/* Flower files range from flower_1 to flower_100 */
		FLOWER_NUMBERS= new Integer[Common.NUMBER_FLOWERS];
		for (int i= 0; i < Common.NUMBER_FLOWERS; i++) {
			Common.FLOWER_NUMBERS[i]= i+1;
		}
		
		if (A3) {
			LAND_FRACTION = 1;
			CLIFF_SEED_PROBABILITY= 100;
			FOREST_SEED_PROBABILITY= 0;
		}
		else {
			LAND_FRACTION= 0.6;
			CLIFF_SEED_PROBABILITY= 20;
			FOREST_SEED_PROBABILITY= 10 + CLIFF_SEED_PROBABILITY;
		}
	}

	/** All possible directions. */
	public static final Direction[] DIRECTIONS = Direction.values();

    ////////////////////////////////////////////////////////////////////////////
    // Functions
    ////////////////////////////////////////////////////////////////////////////
	/** Return a mod n. 
     *
     * Note that -1 mod 5 = 4. For negative a, a mod 5 and a % 5 are different.
	 * 
	 * @param a The 'a' in a mod n.
	 * @param n The 'n' in a mod n.
	 * @return a mod n. 
	 */
	public static int mod(int a, int n) {
		return ((a % n) + n ) % n;
	}
	
	/** @see danaus.Common#mod(int, int)
	 */
	public static float mod(float a, float n) {
		return ((a % n) + n ) % n;
	}
	
	/** Return the number of times c appears in s.
	 * 
	 * @param s A string to search.
	 * @param c A character to look for.
	 * @return The number of occurrences of c in s. 
	 */
	public static int numberOfOccurrences(String s, char c) {
		Debugger.NULL_CHECK(s, "null string in countOccurences");
		
		int n= 0;
		
		for (int i= 0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				n= n + 1;
			}
		}
		
		return n;
	}
	
	/** Return the number of times sub appears in s. 
     *
     * Note that overlapping occurrences are counted, e.g.
     * numberOfOccurrences("eee", String "ee") is 2.
	 * 
	 * @param s A string to search.
	 * @param sub A substring to look for.
	 * @return The number of occurrences of sub in s. 
	 */
	public static int numberOfOccurrences(String s, String sub) {
		Debugger.NULL_CHECK(s, "null string in countOccurences!");
		Debugger.NULL_CHECK(sub, "null substring in countOccurences!");
		
		int n= 0;
		int lookIndex= 0;
		
		while (s.indexOf(sub, lookIndex) != -1) {
			lookIndex= s.indexOf(sub, lookIndex) + 1;
			n++;
		}
		
		return n;
	}
	
	/** Return the text of the first child element with the given tagname or 
	 * null if no such child element exists.
	 * 
	 * @param element The parent element.
	 * @param tagname The tagname of the child element to get text from.
	 * @return The text of the child with the given tagname, or null if no such
	 * child exists.
	 * @throws NullPointerException if either parameter is null.
	 */
	public static String get_text_by_tag_name(Element element, String tagname) {
		// If element is null, likely something is wrong elsewhere
		if (element == null) {
			String tag;
			if (tagname == null) {
				tag= "with null tagname";
			}
			else {
				tag= "with tagname " + tagname;
			}
			
			Debugger.ERROR("null element in getTextByTagName " + tag);
			throw new NullPointerException();
		}
		
		// nodelist that should contain either no elements or one element, the
		// child with the given tagname
		NodeList elements= element.getElementsByTagName(tagname);
		
		if (elements.getLength() == 0) {
			return null;
		}
		
		return elements.item(0).getTextContent();
	}
	
	/** Return 's is not "no" '. 
	 * 
	 * @param s A string.
	 * @return True if s is not "no", false in all other cases.
	 */
	public static boolean isNotNo(String s) {
	    return s == null  ||  !s.toLowerCase().equals("no");
	}
	
	/** If s contains a non-negative integer, return it. <br>
	 * If s contains a negative integer, return 0. <br>
	 * If s is null, return _default. <br>
	 * If s is anything else, display message warning and return _default.
	 * 
	 * @param s The string from which to extract an int.
	 * @param _default The default value to return if an exception occurs.
	 * @param warning The warning message to display if parsing fails.
	 * @return An int extracted from s or a default value if something fails.
	 */
	public static int intValueOf(String s, int _default, String warning) {
		if (s == null) {
			return _default;
		}
		try {
			int i= Math.max(0, Integer.parseInt(s));
			return i;
		}
		catch (RuntimeException e) {
			//Debugger.WARNING(warning);
			return _default;
		}
	}
	
	/** @see danaus.Common#intValueOf(String, int, String)
	 */
	public static double doubleValueOf(String s, double _default, String warning) {
		if (null == s) {
			return _default;
		}
		try {
			double d= Math.max(0, Double.parseDouble(s));
			return d;
		}
		catch (RuntimeException e) {
			Debugger.WARNING(warning);
			return _default;
		}
	}
	
	/** @see danaus.Common#intValueOf(String, int, String)
	 */
	public static Wind windValueOf(String s, Wind _default, String warning) {
		if (null == s) {
			return _default;
		}
		try {
			Wind w= Wind.parseWind(s);
			return w;
		}
		catch (RuntimeException e) {
			//Debugger.WARNING(warning);
			return _default;
		}
	}

	/** Return -1 if i < 0, 0 if i == 0, 1 if i > 0.
	 * 
	 * @param i An int.
	 * @return -1 if i < 0
	 *          0 if i == 0
	 *          1 if i > 0
	 */
	public static int unit_scalar(int i) {
		if (i < 0) {
			return -1;
		}
		if (i > 0) {
			return 1;
		}
		return 0;
	}
	
	/** Return a loaded image whether Danaus is run via Eclipse or via the
     * command line. 
     *
     * All image filenames should be made relative to Danaus' root directory.
     * For example "res/tiles/foo.png" loads "DANAUS_ROOT/res/tiles/foo.png".
	 * 
	 * @param filename The filename of an image relative the Danaus' root 
     *                 directory
	 * @return The loaded image. 
	 */
	public static BufferedImage load_image(String filename) {
		try {
			return ImageIO.read(new File(absolute_path() + "../" + filename));
		}
		catch (IOException e) {
			Debugger.ERROR("Could not load image " + filename);
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}

	/** Acquire semaphore sem, or exit with error 1 if it is interrupted.
	 * 
	 * @param sem A semaphore to acquire.
	 */
	public static void acquire_or_exit(Semaphore sem) {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			Debugger.ERROR("Semaphore acquire interuptted.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**  Return the absolute path of the bin directory of Danaus' .class files.
	 * 
	 * @return Absolute path of Danaus' bin directory.
	 */
	public static String absolute_path() {
		Class<Simulator> c= Simulator.class;
        String s = c.getProtectionDomain().getCodeSource().getLocation().getPath();
        s = s.replaceAll("%20", " ");
        if (System.getProperty("os.name").contains("Windows")) {
                return s.substring(1);
        }
        else {
                return s;
        }
	}
	
	/** Change the brightness of an image. 
     *
     * A scale of 1.0 does not change the brightness of an image. A scale of
     * 0.5 halves the brightness. A scale of 2.0 doubles the brightness. <br>
     * Borrowed verbatim from
     * http://stackoverflow.com/questions/12980780/how-to-change-the-brightness-of-an-image
	 * 
	 * @param img An image.
	 * @param scale A scale, as described above.
	 */
	public static void change_brightness(BufferedImage img, float scale) {
		Graphics g= img.getGraphics();
		int brightness= (int)(256 - 256 * scale);
		g.setColor(new Color(0,0,0,brightness));
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
	}
}

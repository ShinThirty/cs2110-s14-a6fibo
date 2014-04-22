package danaus;

import java.util.List;

/**
 * Class AbstractButterfly contains the infrastructure needed to implement a
 * subclass Butterfly but leaves method run() abstract. Thus, a subclass of 
 * AbstractButterfly must implement method run() to execute a simulation.
 */
public abstract class AbstractButterfly extends Entity {
	/** The name of the butterfly. */
    private static final String BUTTERFLY_NAME= "butterfly";

    /** The power of a butterfly. */
    private Power power;

    /** The map the butterfly is on (null if it is not on a map). */
    private Map map;

    /** A tile state (not necessarily the state of the tile the butterfly is 
     * currently on).
     * @see danaus.AbstractButterfly#refreshState() */
    protected TileState state;
	
    /** Constructor: an abstract butterfly with name "butterfly", location null,
	 * and image file name "butterfly.png". 
	 * <br>
	 * <b>DO NOT FORGET</b> to initialize both the butterfly's position and its 
	 * map.
	 * @see danaus.Entity#Entity(String, Location)
	 */
	public AbstractButterfly() {
		super(BUTTERFLY_NAME, null);
		power = new Power();
	}
	
	/** Return a two-dimensional array of TileStates that represents the map the
	 * butterfly is on.
	 * 
	 * During the learning phase of a simulation, butterflies are tasked with
	 * learning the map in preparation for the running phase of a simulation. 
	 * A butterfly should traverse the entire map and generate a two-dimensional
	 * array of TileStates in which each TileState corresponds to the
	 * appropriate in the map. For example, consider the map with the following
	 * TileStates.
	 * 
	 * <code>
	 * 					 			 -----
	 * 								|a|b|c|
	 *                   			 -----
	 *                  			|d|e|f|
	 *                   			 -----
	 * </code>
	 * A butterfly should return an identical array. The following arrays are
	 * all incorrect.
	 * 
	 * <code>
	 *                               -----
	 * 								|f|e| |
	 *                   			 -----
	 *                  			|a|b|d|
	 *                   			 -----
	 *                                ---
	 * 								 |a|b|
	 * 								  ---
	 *                  			 |d|e|
	 *                         	      ---
	 * </code>
	 *
	 * The returned array is graded based on the percentage of correctly 
	 * identified TileStates. It is recommended that a butterfly save the 
	 * TileState array to use during the running phase of a simulation.
	 *
	 * For more information, refer to Danaus' documentation.
	 * 
	 * @return A two-dimensional array of TileStates that represents the map the
	 * butterfly is on.
	 */
	public abstract TileState[][] learn();
	
    /** Simulate the butterfly's flight.
     * <br>
     * During the transition from the learning phase to the running phase, new 
     * flowers are planted on the map. Everything else remains the same. A
     * butterfly must navigate to and collect all the flowers in 
     * <em>flowerIds<em> in any order to successfully complete a simulation. 
     * You must also not collect extraneous flowers. That is, only collect the 
     * flowers represented by flowerIds.
     * 
     * @param flowers A vector of flowers which the butterfly has to collect
     * @see danaus.AbstractButterfly#collect(Flower)
     */
	public abstract void run(List<Long> flowerIds);
		
    /** @see danaus.Map#fly(Direction, Speed). */
    protected void fly(Direction heading, Speed s) {
        map.fly(heading, s);
    }

    /** @see danaus.Map#land(). */
    protected void land() { 
        map.land();
    }
	
	/** Collect a flower. A butterfly must collect flowers during the running 
	 * phase of a simulation. An attempt to collect a flower during the
	 * learning phase of a simulation throws a PrematureCollectionException. 
	 * 
	 * @param flower The flower to collect.
	 * @throws PrematureCollectionException
	 */
	protected void collect(Flower flower) {
		map.collect(flower);
	}
	
	/** @see danaus.Map#refreshState(). */
    protected void refreshState() {
        map.refreshState();
    }

    /** Add p to the butterfly's power. */
    void addPower(int p) {		
        if (p >= 0) {
            map.park.state.powerConsumed += p;
        }
        else {
            map.park.state.powerSpent += p;
        }

        power.addPower(p);
    }

    /** Subtract p from the butterfly's power. */
    void subtractPower(int p) {
        if (p >= 0) {
            map.park.state.powerSpent += p;
        }
        else {
            map.park.state.powerConsumed += p;
        }

        power.subtractPower(p);
    }

    /** Return the butterfly's power. */
    public Power getPower() {
        return power;
    }

    /** Return the number of columns in the map. */
    protected int getMapWidth() {
        return map.getWidth();
    }

    /** Return the number of rows in the map. */
    protected int getMapHeight() {
        return map.getHeight();
    }

    /** Set the map to m. */
    void setMap(Map m) {
        map= m;
    }
}

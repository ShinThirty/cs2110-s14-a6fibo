package danaus;

/** Thrown when a butterfly attempts to fly over water. 
 */
@SuppressWarnings("serial")
public class WaterCollisionException extends ObstacleCollisionException {
	/** Constructor: an instance with message m*/
    public WaterCollisionException(String m) {
        super(m);
    }

    /** Constructor: an instance with no message */
    public WaterCollisionException() {
        super();
    }
}

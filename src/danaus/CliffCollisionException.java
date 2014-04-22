package danaus;

/** This exception is thrown when a butterfly attempts to fly into a cliff. */
@SuppressWarnings("serial")
public class CliffCollisionException extends ObstacleCollisionException {
	/** Constructor: an instance with message m*/
    public CliffCollisionException(String m) {
        super(m);
    }

    /** Constructor: an instance with no message */
    public CliffCollisionException() {
        super();
    }
}

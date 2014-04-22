package student;
import java.util.List;
import danaus.*;

/** A butterfly that does things randomly. */
public class RandomButterfly extends AbstractButterfly {
    private static Randomer RAND = new Randomer();
    private static Speed[] SPEEDS = Speed.values();
    private static int NUM_TURNS = 1000;
    
    /**  A pedagogical implementation of learn.
     * 
     * The butterfly performs an unspecified number of steps. At each step, it
     * chooses a direction, d, and a speed, s, uniformly at random. It then
     * refreshes its state and flies in direction d at speed s. In the event of
     * exceptional flight, this butterfly does nothing and continues to the
     * next step.
     */
    public @Override TileState[][] learn() {
        for (int i = 0; i < NUM_TURNS; i++) {
            Direction direction = Common.DIRECTIONS[RAND.nextInt(Common.DIRECTIONS.length)];
            Speed speed = SPEEDS[RAND.nextInt(SPEEDS.length)];

            try {
                /* Butterflies interact with a map by calling refreshState().
                 * Say a butterfly is on a tile, t. refreshState() will place
                 * the TileState of t into this butterfly's inherited field,
                 * state. */
                refreshState();
                
                /* fly(d, s) will fly in direction d at speed s. Sometimes
                 * though, flying in direction d is impossible. For example,
                 * there could be a Cliff or Water in direction d. In these
                 * exceptional circumstances, an exception is raised, and the
                 * position of the butterfly is not changed. */
                fly(direction, speed);
                
                /* Typically, you would write code here to store your state or
                 * perform other miscellaneous operations. Here, I access the
                 * row and column of the tile I am currently on. */
                @SuppressWarnings("unused") int row = state.location.row;
                @SuppressWarnings("unused") int col = state.location.col;
            }
            /* In the event that the butterfly tries to fly into a Cliff, a
             * CliffCollisionException is thrown. */
            catch (CliffCollisionException e) {}
            /* In the event that the butterfly tries to fly into Water, a
             * WaterCollisionException is thrown. */
            catch (WaterCollisionException e) {}
            /*
            catch (ObstacleCollisionException e) {
                CliffCollisionException and WaterCollisionException are both
                subclasses of ObstacleCollisionException. If you're agnostic to
                the type of collision, you can catch an
                ObstacleCollisionException.
            } 
            */
        }
        
        /* learn returns a two-dimensional array of TileStates that represents
         * the map. For simplicity, we return null here, but you should not. */
        return null;
    }

    /* Don't worry about these methods until later projects. */
    public @Override void run(List<Long> flowerIds)     {}
}

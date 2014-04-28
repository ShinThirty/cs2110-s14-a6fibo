/** TIME SPENT: # hours and # minutes. */ 

package student;
import java.util.*;
import danaus.*;

public class Butterfly extends AbstractButterfly {
	private TileState[][] mapStates; // TileState[][] array generated by learn()
	private int nFlyable = 0; // Number of flyable tiles in the map.
	
	/** Return a two-dimensional array of TileStates that represents the map the
	 * butterfly is on.
	 * 
	 * During the learning phase of a simulation, butterflies are tasked with
	 * learning the map in preparation for the running phase of a simulation. 
	 * A butterfly will traverse the entire map and generate a two-dimensional
	 * array of TileStates in which each TileState corresponds to the
	 * appropriate in the map.
	 * 
	 * @return A two-dimensional array of TileStates that represents the map the
	 * butterfly is on.
	 */
	public @Override TileState[][] learn() {
		// Get number of rows and columns of the map
		int nRows = getMapHeight();
		int nCols = getMapWidth();
		
		// Declare and initialize the TileState[][] result array
		mapStates = new TileState[nRows][nCols];
		
		// Get the state of the initial tile
		refreshState();
		int curRow = location.row;
		int curCol = location.col;
		mapStates[curRow][curCol] = state;
		nFlyable++;
		
		// Depth First Search
		for (Direction dir : Direction.values()) {
			int nextRow = (curRow + dir.dRow + nRows) % nRows;
			int nextCol = (curCol + dir.dCol + nCols) % nCols;
			if (mapStates[nextRow][nextCol] == null)
				DFS(dir);
		}

		return mapStates;
	}
	
	/** Do the Depth First Search along the Direction dir.
	 * When search() is called, it will first fly the butterfly along the given
	 * direction "dir". Then the state of current tile is fetched and stored in
	 * mapStates array. After that, it will recursively search along the possible
	 * directions.
	 * When the method finishes, the butterfly will return to the original 
	 * location when the method is called.
	 * 
	 * @param dir Direction indicates the current forward direction.
	 */
	private void DFS(Direction dir) {
		// Get number of rows and columns of the map
		int nRows = getMapHeight();
		int nCols = getMapWidth();
		
		try {
			// Fly along the current Direction dir
			fly(dir, Speed.NORMAL);
			
			// Get the state of the current tile
			refreshState();
			int curRow = location.row;
			int curCol = location.col;
			mapStates[curRow][curCol] = state;
			nFlyable++;
			
			// Search the next tile
			for (Direction nextDir : Direction.values()) {
				int nextRow = (curRow + nextDir.dRow + nRows) % nRows;
				int nextCol = (curCol + nextDir.dCol + nCols) % nCols;
				if (mapStates[nextRow][nextCol] == null)
					DFS(nextDir);
			}
			
			// Fly back
			fly(Direction.opposite(dir), Speed.NORMAL);
		}
		catch (ObstacleCollisionException e) {
			// Butterfly hits a cliff or water, sets the corresponding
			// TileState to Nil
			int nextRow = (location.row + dir.dRow + nRows) % nRows;
			int nextCol = (location.col + dir.dCol + nCols) % nCols;
			mapStates[nextRow][nextCol] = TileState.nil;
		}
	}
	
	/** Simulate the butterfly's flight.
	 * During the run(), a shortest-path algorithm will be used to calculate
	 * the shortest path from every flyable tile to the initial tile.
	 * Then the butterfly will use the shortest-paths to collect the old 
	 * flowers. For the new flowers that bloom after learning phase, the 
	 * butterfly will exploit its aroma to find an efficient path to collect
	 * them.
	 * 
	 * @param flowers A vector of flowers which the butterfly has to collect
     * @see danaus.AbstractButterfly#collect(Flower)
	 */
	public @Override void run(List<Long> flowerIds) {
        // Declare the Direction array that stores the back pointers of each 
		// tile along its shortest-path to the initial tile.
		int nRows = getMapHeight();
		int nCols = getMapWidth();
		Direction[][] backPointers = new Direction[nRows][nCols];
		
		// Get the initial location
		refreshState();
		
		// Invoke the shortest-path algorithm to calculate the shortest-paths
		Dijkstra(backPointers, location);
	}
	
	/** The Dijkstra algorithm to calculate the shortest path from each flyable
	 * tile to the initial tile whose location is initLoc.
	 * 
	 * @param backPointers Direction[][] array storing the back-pointers along 
	 * the shortest paths.
	 * @param initLoc Location object contains the location of current processing 
	 * tile.
	 */
	private void Dijkstra(Direction[][] backPointers, Location initLoc) {
		// Declare and initialize the distance array containing the distance from 
		// every flyable tile to the initial tile.
		int nRows = getMapHeight();
		int nCols = getMapWidth();
		int[][] dists = new int[nRows][nCols];
		for (int i = 0; i < nRows; i++)
			for (int j = 0; j < nCols; j++)
				dists[i][j] = Integer.MAX_VALUE;
		dists[initLoc.row][initLoc.col] = 0;
		
		// The remaining flyable tiles in the map
		int nRemFly = nFlyable;
		
		// The location of the tile with the smallest distance and its adjacent tiles
		int curRow = 0;
		int curCol = 0;
		int nextRow = 0;
		int nextCol = 0;
		
		// Alternative distance
		int alt = 0;
		
		// The main loop
		while (nRemFly > 0) {
			// Find the location of the tile with the smallest distance
			for (int i = 0; i < nRows; i++)
				for (int j = 0; j < nCols; j++)
					if (dists[i][j] < dists[curRow][curCol]) {
						curRow = i;
						curCol = j;
					}
			
			// The remaining flyable tiles decreases by 1
			nRemFly--;
			
			// The remaining tiles are unreachable
			if (dists[curRow][curCol] == Integer.MAX_VALUE)
				break;
			
			for (Direction dir: Direction.values()) {
				// Get the coordinates of adjacent tile v
				nextRow = (curRow + dir.dRow + nRows) % nRows;
				nextCol = (curCol + dir.dCol + nCols) % nCols;
				// If v is flyable, update the shortest distance and its back pointer
				if (!mapStates[nextRow][nextCol].equals(TileState.nil)) {
					alt = dists[curRow][curCol] + 1;
					if (alt < dists[nextRow][nextCol]) {
						dists[nextRow][nextCol] = alt;
						backPointers[nextRow][nextCol] = Direction.opposite(dir);
					}
				}
			}
		}
	}
}
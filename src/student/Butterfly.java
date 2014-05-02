/** TIME SPENT: 20 hours and 46 minutes. */ 

package student;
import java.util.*;
import danaus.*;

public class Butterfly extends AbstractButterfly {
	private TileState[][] mapStates; // TileState[][] array generated by learn()
	
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
		// Initialize the TileState[][] result array
		mapStates = new TileState[getMapHeight()][getMapWidth()];
		
		// Depth First Search
		DFS();

		return mapStates;
	}
	
	/** Do the Depth First Search along the Direction dir.
	 * When search() is called, it will first fetch the state of current 
	 * tile and store it in mapStates[][]. After that, it will recursively 
	 * search along the possible directions.
	 * When the method finishes, the butterfly will return to the original 
	 * location when the method is called.
	 */
	private void DFS() {
		// Get the state of the current tile
		refreshState();
		mapStates[location.row][location.col] = state;

		// Search from adjacent tiles
		for (Direction nextDir : Direction.values()) {
			int nextRow = Common.mod(location.row + nextDir.dRow, getMapHeight());
			int nextCol = Common.mod(location.col + nextDir.dCol, getMapWidth());
			if (mapStates[nextRow][nextCol] == null)
				try {
					fly(nextDir, Speed.NORMAL);
					DFS();
					fly(Direction.opposite(nextDir), Speed.NORMAL);
				}
				catch (ObstacleCollisionException e) {
					// Butterfly hits a cliff or water, sets the corresponding
					// TileState to Nil
					mapStates[nextRow][nextCol] = TileState.nil;
				}
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
		Direction backPointer;
		
		// Invoke the shortest-path algorithm to calculate the shortest-paths
		Dijkstra(backPointers, location);
		
		// The remaining flowerId
		List<Long> remainingFlowerIds = new ArrayList<Long>(flowerIds);
		
		// The stack contains the shortest path
		Stack<Direction> path = new Stack<Direction>();
		
		// The location of tiles on the shortest path
		int row = 0;
		int col = 0;
		
		// For the old flowers, use the backPointers to collect them
		for (int i = 0; i < nRows; i++)
			for (int j = 0; j < nCols; j++)
				if (mapStates[i][j] != null && !mapStates[i][j].equals(TileState.nil)) {
					List<Flower> flowers = mapStates[i][j].getFlowers();
					for (Flower flower : flowers) {
						if (flowerIds.contains(flower.getFlowerId())) {
							// Remove the corresponding flowerId from remainingFlowerIds
							remainingFlowerIds.remove(flower.getFlowerId());
							
							// Fly to the tile and collect the flower
							row = i;
							col = j;
							backPointer = backPointers[row][col];
							
							while (backPointer != null) {
								path.push(Direction.opposite(backPointer));
								row = (row + backPointer.dRow + nRows) % nRows;
								col = (col + backPointer.dCol + nCols) % nCols;
								backPointer = backPointers[row][col];
							}
							
							while (path.size() != 0)
								fly(path.pop(), Speed.NORMAL);
							collect(flower);
							
							// Fly back to the initial tile
							row = i;
							col = j;
							while (backPointers[row][col] != null) {
								fly(backPointers[row][col], Speed.NORMAL);
								row = location.row;
								col = location.col;
							}
						}
					}
				}
		
		// For the new flowers, use their aroma to find and collect them
		for (Long newFlowerId : remainingFlowerIds) {
			// Get aromas on current tile
			refreshState();
			List<Aroma> aromas = state.getAromas();
			
			Direction nextDir = null;
			
			// Search for the aroma of the flower whose id is newFlowerId
			do {
				for (Aroma aroma : aromas) {
					if (aroma.getFlowerId() == newFlowerId) {
						// Search and collect the flower
						double maxAromaIntensity = aroma.intensity;
						
						nextDir = null;
						
						for (Direction dir : Direction.values()) {
							int nextRow = (location.row + dir.dRow + nRows) % nRows;
							int nextCol = (location.col + dir.dCol + nCols) % nCols;
							if (mapStates[nextRow][nextCol] != null && !mapStates[nextRow][nextCol].equals(TileState.nil)) {
								fly(dir, Speed.NORMAL);
								refreshState();
								List<Aroma> nextAromas = state.getAromas();
								for (Aroma nextAroma : nextAromas) {
									if (nextAroma.getFlowerId() == newFlowerId) {
										if (nextAroma.intensity > maxAromaIntensity) {
											maxAromaIntensity = nextAroma.intensity;
											nextDir = dir;
										}
										fly(Direction.opposite(dir), Speed.NORMAL);
										break;
									}
								}
							}
						}
						
						// The butterfly still needs to fly
						if (nextDir != null) {
							fly(nextDir, Speed.NORMAL);
							refreshState();
							aromas = state.getAromas();
							break;
						}
					}
				}
			}
			while (nextDir != null);
			
			// The butterfly reaches the tile
			refreshState();
			List<Flower> flowers = state.getFlowers();
			for (Flower flower : flowers) {
				if (flower.getFlowerId() == newFlowerId) {
					collect(flower);
					break;
				}
			}
		}
	}
	
	/** A class contains the information of the row, col and distance from the 
	 * initial tile of one flyable tile.
	 */
	class Distance implements Comparable<Distance> {
		private int row; // Row of the tile.
		private int col; // Column of the tile.
		private int dist = Integer.MAX_VALUE; // Distance from the initial tile.
		
		/** Constructor: an instance with coordinates [row, col] and distance dist */
		public Distance(int row, int col, int dist) {
			this.col = col;
			this.row = row;
			this.dist = dist;
		}
		
		/** Constructor: an instance with equal to d */
		public Distance(Distance d) {
			col = d.col;
			row = d.row;
			dist = d.dist;
		}
		
		/** Return a negative integer, zero, or a positive integer depending on
	     * whether this Distance comes before Distance d. The comparison
	     * is made only on the dist field. */
		public @Override int compareTo(Distance d) {
			return dist - d.dist;
		}
		
		/** Return a string containing the three fields. */
		public @Override String toString() {
			return row + ", " + col + ", " + dist;
		}
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
		// Get number of rows and columns of the map
		int nRows = getMapHeight();
		int nCols = getMapWidth();
		
		// The priority queue that contains TileState of all flyable tiles
		PriorityQueue<Distance> Q = new PriorityQueue<Distance>();
		for (int i = 0; i < nRows; i++)
			for (int j = 0; j < nCols; j++)
				if (mapStates[i][j] != null && !mapStates[i][j].equals(TileState.nil))
					if (i == initLoc.row && j == initLoc.col)
						Q.add(new Distance(i, j, 0));
					else
						Q.add(new Distance(i, j, Integer.MAX_VALUE));
		
		// The location of the tile with the smallest distance and its adjacent tiles
		Distance curLoc;
		int nextRow = 0;
		int nextCol = 0;
		
		// Alternative distance
		int alt = 0;
		
		// The main loop
		while (Q.size() != 0) {
			// Find the location of the tile with the smallest distance
			curLoc = Q.poll();
			
			// The remaining tiles are unreachable
			if (curLoc.dist == Integer.MAX_VALUE)
				break;
			
			for (Direction dir: Direction.values()) {
				// Get the coordinates of adjacent tile v
				nextRow = (curLoc.row + dir.dRow + nRows) % nRows;
				nextCol = (curLoc.col + dir.dCol + nCols) % nCols;
				
				// If v is flyable, update the shortest distance and its back pointer
				if (!mapStates[nextRow][nextCol].equals(TileState.nil)) {
					alt = curLoc.dist + 1;
					
					// Find the appropriate tile in Q
					for (Distance nextLoc : Q) {
						if (nextLoc.row == nextRow && nextLoc.col == nextCol) {
							if (alt < nextLoc.dist) {
								Q.remove(nextLoc);
								Q.add(new Distance(nextRow, nextCol, alt));
								backPointers[nextRow][nextCol] = Direction.opposite(dir);
							}
							break;
						}
					}
				}
			}
		}
	}
}
/** TIME SPENT: # hours and # minutes. */ 

package student;
import java.util.*;
import danaus.*;

public class Butterfly extends AbstractButterfly {
	public @Override TileState[][] learn() {
		// Get number of rows and columns of the map
		int nRows = getMapHeight();
		int nCols = getMapWidth();
		
		// Declare and initialize the TileState[][] result array
		TileState[][] mapStates = new TileState[nRows][nCols];
		
		// Get the state of the initial tile
		refreshState();
		int curRow = state.location.row;
		int curCol = state.location.col;
		mapStates[curRow][curCol] = state;
		
		// Depth First Search
		for (Direction dir : Direction.values()) {
			int nextRow = (curRow + dir.dRow + nRows) % nRows;
			int nextCol = (curCol + dir.dCol + nCols) % nCols;
			if (mapStates[nextRow][nextCol] == null)
				search(mapStates, dir);
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
	 * @param mapStates TileState[][] indicates where to put the TileState info.
	 * @param dir Direction indicates the current forward direction.
	 */
	private void search(TileState[][] mapStates, Direction dir) {
		// Get number of rows and columns of the map
		int nRows = getMapHeight();
		int nCols = getMapWidth();
		
		try {
			// Fly along the current Direction dir
			fly(dir, Speed.NORMAL);
			
			// Get the state of the current tile
			refreshState();
			int curRow = state.location.row;
			int curCol = state.location.col;
			mapStates[curRow][curCol] = state;
			
			// Search the next tile
			for (Direction nextDir : Direction.values()) {
				int nextRow = (curRow + nextDir.dRow + nRows) % nRows;
				int nextCol = (curCol + nextDir.dCol + nCols) % nCols;
				if (mapStates[nextRow][nextCol] == null)
					search(mapStates, nextDir);
			}
			
			// Fly back
			fly(Direction.opposite(dir), Speed.NORMAL);
		}
		catch (ObstacleCollisionException e) {
			// Butterfly hits a cliff or water, sets the corresponding
			// TileState to Nil
			int nextRow = (state.location.row + dir.dRow + nRows) % nRows;
			int nextCol = (state.location.col + dir.dCol + nCols) % nCols;
			mapStates[nextRow][nextCol] = TileState.nil;
		}
	}
	
	public @Override void run(List<Long> flowerIds) {
        // TODO: implement
	}
}
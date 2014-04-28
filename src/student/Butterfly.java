/** TIME SPENT: # hours and # minutes. */ 

package student;
import java.util.*;
import danaus.*;

public class Butterfly extends AbstractButterfly {
	public @Override TileState[][] learn() {
		// Get number of rows and columns of the map
		int nRows = getMapHeight();
		int nCols = getMapWidth();
		
		// Declare and initialize 
		// the TileState[][] result array and visited array
		TileState[][] mapStates = new TileState[nRows][nCols];
		boolean[][] visited = new boolean[nRows][nCols];
		for (int i = 0; i < nRows; i++)
			for (int j = 0; j < nCols; j++)
				visited[i][j] = false;
		
		// Get the state of the initial tile
		refreshState();
		int curRow = state.location.row;
		int curCol = state.location.col;
		mapStates[curRow][curCol] = state;
		visited[curRow][curCol] = true;
		
		// Depth First Search
		for (Direction dir : Direction.values()) {
			int nextRow = (curRow + dir.dRow + nRows) % nRows;
			int nextCol = (curCol + dir.dCol + nCols) % nCols;
			if (!visited[nextRow][nextCol])
				search(mapStates, visited, dir);
		}
			
		
		return mapStates;
	}
	
	/** Do the Depth First Search along the Direction dir.
	 * 
	 * @param mapStates TileState[][] indicates where to put the TileState info.
	 * @param visited boolean[][] indicates whether the corresponding tile is visited before.
	 * @param dir Direction indicates the current forward direction.
	 */
	private void search(TileState[][] mapStates, boolean[][] visited, Direction dir) {
		try {
			// Fly along the current Direction dir
			fly(dir, Speed.NORMAL);
			
			// Get the state of the current tile
			refreshState();
			int curRow = state.location.row;
			int curCol = state.location.col;
			mapStates[curRow][curCol] = state;
			visited[curRow][curCol] = true;
			
			// Get number of rows and columns of the map
			int nRows = getMapHeight();
			int nCols = getMapWidth();
			
			// Search the next tile
			for (Direction nextDir : Direction.values()) {
				int nextRow = (curRow + nextDir.dRow + nRows) % nRows;
				int nextCol = (curCol + nextDir.dCol + nCols) % nCols;
				if (!visited[nextRow][nextCol])
					search(mapStates, visited, nextDir);
			}
			
			// Fly back
			fly(Direction.opposite(dir), Speed.NORMAL);
			
		}
		catch (ObstacleCollisionException e) {
			// Do nothing
		}
	}
	
	public @Override void run(List<Long> flowerIds) {
        // TODO: implement
	}
}
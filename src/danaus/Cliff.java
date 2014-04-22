package danaus;

/** An instance represents an unpassable cliff.
 */
public class Cliff extends Tile {
	/* Tile specific constants. @see main.Tile */
	private static final String NAME= "cliff";
	private static final boolean FLYABLE= false; 
	
	/** Constructor: a instance with name "cliff", skin skin. <br>
     * Its slow-down value is Integer.MAX_VALUE and its power cost is
     * Integer.MAX_VALUE. <br>
     * Its tile state is tileState. <br>
     * Precondition: skin is either "land" or "snow". 
     */
	Cliff(String skin, TileState tileState) {
		super(NAME, skin, FLYABLE, Common.CLIFF_SLOW_DOWN, Common.CLIFF_POWER_COST, tileState, 
				Tile.OBSTACLES_DIR + NAME + "_" + skin + Tile.IMAGE_EXT);
		tileState.type = getType();
	}

	/** Return "^", a Cliff's unique printable character. Each tile has a unique
	 * printable character to print in an ascii map. 
	 *
	 * @return A tile's unique printable character. 
	 */
	public String toStringTile() {
		return "^";
	}
	
	/** Return the tile type */
	public TileType getType() {
		return TileType.CLIFF;
	}
}

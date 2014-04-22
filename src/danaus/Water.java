package danaus;

/** An instance represents a square of water. Butterflies are too afraid to fly
 * over water. 
 */
public class Water extends Tile {
	/* Tile specific constant. @see main.Tile */
	private static final String NAME= "water";
	private static final boolean FLYABLE= false; 
	
	/** Constructor: an instance with skin skin and tile state tileState.
	 * It is flyable, has name "water", and has the highest possible
	 * slow-down and power-cost properties.   */
	Water(String skin, TileState tileState) {
		super(NAME, skin, FLYABLE, Common.WATER_SLOW_DOWN, Common.WATER_POWER_COST, tileState,
				Common.TILES_DIR + NAME + Tile.IMAGE_EXT);
		tileState.type= getType();
	}
	
	/** Return "@", a Water tile's unique printable character. */
	public String toStringTile() {
		return "@";
	}
	
	/** Return tile type */
	public TileType getType() {
		return TileType.WATER;
	}
}

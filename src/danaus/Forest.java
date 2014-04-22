package danaus;

/** An instance represents a thick forest. Forests are difficult to fly over. */
public class Forest extends Tile {
	/* Tile specific constants. @see main.Tile */
	private static final String NAME= "forest";
	private static final boolean FLYABLE= true; 
	
	/** Constructor: a flyable instance with name "forest", skin skin. <br>
	 * Its slow-down value is 1 and its power cost is 0. <br>
	 * Its tile state is tileState. <br>
	 * skin is one of: "land", "snow", "tropical".  */
	Forest(String skin, TileState tileState) {
		super(NAME, skin, FLYABLE, Common.FOREST_SLOW_DOWN, Common.FOREST_POWER_COST, tileState,
				Tile.OBSTACLES_DIR + NAME + "_" + skin + Tile.IMAGE_EXT);
		tileState.type = getType();
	}
	
	/** Return "|", a Forest's unique printable character.  */
	public String toStringTile() {
		return "|";
	}
	
	/** Return tile type */
	public TileType getType() {
		return TileType.FOREST;
	}
}

/**
 * Quiz 3
 * Abanob Tanous (amt812)
 * Samuel Li (zl4088)
 */
public class Tile {
    // 0 means the tile is empty
    public static final int TILE_VAL_EMPTY = 0;
    // 1 means the tile has an X (played by player 1)
    public static final int TILE_VAL_X = 1;
    // 2 means it has an O (played by player 2)
    public static final int TILE_VAL_O = 2;
    // The indices of the four corner tiles
    public static int[] CORNER_TILES = {0, 2, 6, 8};
    // The indices of the four side tiles
    public static int[] SIDE_TILES = {1, 3, 5, 7};
    // The index of the center tile
    public static int CENTER_TILE = 4;
}

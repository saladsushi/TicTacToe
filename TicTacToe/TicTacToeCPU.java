import java.util.Random;

public class TicTacToeCPU {
    // Used to generate random moves
    private static final Random random = new Random();

    /**
     * The CPU makes a move, it follows these rules:
     *     One the first turn, if the player plays in X tile then the CPU will on Y tile:
     *         Center -> only corners are safe
     *         Corner -> only center is safe
     *         Side -> only near corner, center, and opposite side are safe
     *     On subsequent turns, the rules are in order:
     *         Check if there is a way to win and do it
     *         Check if opponent has the win and prevent it
     *         Check if there is a possible double threat and do it
     *         Check if opponent has a possible double threat and prevent it
     *         Otherwise, play a random move
     * @param turnNum the turn number
     * @param tiles the board
     */
    public static void makeAMove(int turnNum, int[] tiles) {
        /* First turn move */
        if (turnNum == 1) {
            if (tiles[Tile.CENTER_TILE] != Tile.TILE_VAL_EMPTY) { // Check if the center tile is where the opp played
                int corner = Tile.CORNER_TILES[random.nextInt(4)];
                if (TicTacToe.play(Tile.TILE_VAL_O, corner, tiles)) {
                    return;
                }
            } else if (TicTacToe.play(Tile.TILE_VAL_O, Tile.CENTER_TILE, tiles)) {// Sides or corners
                return;
            }
        }

        /* Check if there is a way to win and do it */
        if (turnNum > 2) { // Must be at least turn 3 for a win to be possible
            int winPossibility = checkWinPossibility(Tile.TILE_VAL_O, tiles);
            if (winPossibility != -1 && TicTacToe.play(Tile.TILE_VAL_O, winPossibility, tiles)) {
                return;
            }
        }

        /* Check if opponent has the win and prevent it */
        if (turnNum > 1) { // Must be at least turn 2 for an opponent win to be possible
            int winPossibility = checkWinPossibility(Tile.TILE_VAL_X, tiles);
            if (winPossibility != -1 && TicTacToe.play(Tile.TILE_VAL_O, winPossibility, tiles)) {
                return;
            }
        }

        /* Check if there is a possible double threat and do it */
        if (turnNum > 1) { // Must be at least turn 2 for a double threat to be possible
            int doubleThreatPossibility = checkForDoubleThreat(turnNum, tiles);
            if (doubleThreatPossibility != -1 && TicTacToe.play(Tile.TILE_VAL_O, doubleThreatPossibility, tiles)) {
                return;
            }
        }

        /* Check if opponent has a possible double threat and prevent it */
        if (turnNum > 1) { // Must be at least turn 2 for an opponent double threat to be possible
            int oppDoubleThreatPossibility = checkForOppDoubleThreat(tiles);
            if (oppDoubleThreatPossibility != -1 && TicTacToe.play(Tile.TILE_VAL_O, oppDoubleThreatPossibility, tiles)) {
                return;
            }
        }

        /* Otherwise, play a random move */
        for (int i = 0; i < 9; i++) {
            if (TicTacToe.play(Tile.TILE_VAL_O, i, tiles)) {
                return;
            }
        }
    }

    /**
     * Check if the player has a possibility of winning and
     * return the tile where they must play to win
     * @param player the player for whom the win possibility is checked
     * @param tiles the board
     * @return the tile in which the player must play to win, -1 if there is no such tile
     */
    private static int checkWinPossibility(int player, int[] tiles) {
        int opp = player == Tile.TILE_VAL_O ? Tile.TILE_VAL_X : Tile.TILE_VAL_O;

        // Check columns
        int winColumn = checkColumnsForWin(player, opp, tiles);
        if (winColumn != -1) {
            return winColumn;
        }

        // Check rows
        int winRow = checkRowsForWin(player, opp, tiles);
        if (winRow != -1) {
            return winRow;
        }

        // Check diagonals, returns -1 if all are no possibility
        return checkDiagonalsForWin(player, opp, tiles);
    }

    /**
     * Check if the player has a possibility of winning using a column and
     * return the tile where they must play to win
     * @param player the player for whom the win possibility is checked
     * @param tiles the board
     * @return the tile in which the player must play to win, -1 if there is no such tile
     */
    private static int checkColumnsForWin(int player, int opp, int[] tiles) {
        int count; // Count the number of the player's marks are in the current column
        int emptyPos = -1; // The empty tile in the column
        for (int x = 0; x < 3; x++) { // Iterate through the columns
            count = 0; // Reset the count for each column
            for (int y = 0; y < 3; y++) {  // Iterate through each tile in the column
                int val = tiles[3 * y + x]; // Get the value of the tile
                if (val == opp) { // There is an opp mark, column cannot be used for win
                    count = 0; // Reset the count
                    break; // Move on to the next column
                } else if (val == player) {
                    count++; // There is a player mark, increment count
                } else {
                    emptyPos = 3 * y + x; // There is an empty tile
                }
            }
            if (count > 1) {
                // This column has 2 of the player's marks, and one empty tile, so
                // it can be used to win, return the empty tile
                return emptyPos;
            }
        }
        return -1; // No possible column win
    }

    /**
     * Check if the player has a possibility of winning using a row and
     * return the tile where they must play to win
     * @param player the player for whom the win possibility is checked
     * @param tiles the board
     * @return the tile in which the player must play to win, -1 if there is no such tile
     */
    private static int checkRowsForWin(int player, int opp, int[] tiles) {
        int count; // Count the number of the player's marks are in the current row
        int emptyPos = -1; // The empty tile in the row
        for (int y = 0; y < 3; y++) { // Iterate through the rows
            count = 0; // Reset the count for each column
            for (int x = 0; x < 3; x++) {  // Iterate through each tile in the row
                int val = tiles[3 * y + x]; // Get the value of the tile
                if (val == opp) { // There is an opp mark, row cannot be used for win
                    count = 0;  // Reset the count
                    break; // Move on to the next row
                } else if (val == player) {
                    count++; // There is a player mark, increment count
                } else {
                    emptyPos = 3 * y + x; // There is an empty tile
                }
            }
            if (count > 1) {
                // This row has 2 of the player's marks, and one empty tile, so
                // it can be used to win, return the empty tile
                return emptyPos;
            }
        }
        return -1; // No possible row win
    }

    /**
     * Check if the player has a possibility of winning using a diagonal and
     * return the tile where they must play to win
     * @param player the player for whom the win possibility is checked
     * @param tiles the board
     * @return the tile in which the player must play to win, -1 if there is no such tile
     */
    private static int checkDiagonalsForWin(int player, int opp, int[] tiles) {
        int count = 0; // Count the number of the player's marks are in the current row
        int emptyPos = -1; // The empty tile in the diagonal
        // Top left to bot right diagonal
        for (int i = 0; i < 3; i++) { // Iterate through the tiles of the top left to bot right diagonal
            int val = tiles[3 * i + i]; // Get the value of the tile
            if (val == opp) { // There is an opp mark, row cannot be used for win
                count = 0; // Reset the count
                break; // Move on to the other diagonal
            } else if (val == player) {
                count++; // There is a player mark, increment count
            } else {
                emptyPos = 3 * i + i; // There is an empty tile
            }
        }
        if (count == 2) {
            // This diagonal has 2 of the player's marks, and one empty tile, so
            // it can be used to win, return the empty tile
            return emptyPos;
        }

        count = 0;
        // Top right to bot left diagonal
        for (int i = 0; i < 3; i++) { // Iterate through the tiles of the top left to bot right diagonal
            int val = tiles[2 * i + 2]; // Get the value of the tile
            if (val == opp) { // There is an opp mark, row cannot be used for win
                return -1; // Neither diagonal works
            } else if (val == player) {
                count++; // There is a player mark, increment count
            } else {
                emptyPos = 2 * i + 2; // There is an empty tile
            }
        }
        if (count == 2) {
            // This diagonal has 2 of the player's marks, and one empty tile, so
            // it can be used to win, return the empty tile
            return emptyPos;
        }

        return -1; // Neither diagonal works
    }

    /**
     * Check the case in which the cpu may develop a double
     * threat, and return the tile in which the cpu must play
     * to develop the double threat
     * @param turnNum turn number
     * @param tiles the board
     * @return the tile in which the cpu must play
     * to develop the double threat
     */
    private static int checkForDoubleThreat(int turnNum, int[] tiles) {
        int cpu = Tile.TILE_VAL_O;
        int opp = Tile.TILE_VAL_X;
        /* In case of this, a double threat can be established by playing 4 or 6
         1 | x | 3             1 | x | 3             1 | x | o
        ---+---+---           ---+---+---           ---+---+---
         4 | o | 6     -->     4 | o | o     -->     x | o | o
        ---+---+---           ---+---+---           ---+---+---
         7 | x | 9             7 | x | 9             7 | x | 9
         */
        if (turnNum > 2) { // First step in developing the double threat
            if (tiles[Tile.CENTER_TILE] == cpu) {
                if (tiles[Tile.SIDE_TILES[0]] == opp && tiles[Tile.SIDE_TILES[3]] == opp) {
                    if (tiles[Tile.SIDE_TILES[1]] == cpu) {
                        return random.nextBoolean() ? Tile.CORNER_TILES[0] : Tile.CORNER_TILES[2];
                    } else if (tiles[Tile.SIDE_TILES[2]] == cpu) {
                        return random.nextBoolean() ? Tile.CORNER_TILES[1] : Tile.CORNER_TILES[3];
                    }
                } else if (tiles[Tile.SIDE_TILES[1]] == opp && tiles[Tile.SIDE_TILES[2]] == opp) {
                    if (tiles[Tile.SIDE_TILES[0]] == cpu) {
                        return random.nextBoolean() ? Tile.CORNER_TILES[0] : Tile.CORNER_TILES[1];
                    } else if (tiles[Tile.SIDE_TILES[3]] == cpu) {
                        return random.nextBoolean() ? Tile.CORNER_TILES[2] : Tile.CORNER_TILES[3];
                    }
                }
            }

        }
        if (tiles[Tile.CENTER_TILE] == cpu) { // Second step in developing the double threat
            if (tiles[Tile.SIDE_TILES[0]] == opp && tiles[Tile.SIDE_TILES[3]] == opp) {
                return random.nextBoolean() ? Tile.SIDE_TILES[1] : Tile.SIDE_TILES[2];
            } else if (tiles[Tile.SIDE_TILES[1]] == opp && tiles[Tile.SIDE_TILES[2]] == opp) {
                return random.nextBoolean() ? Tile.SIDE_TILES[0] : Tile.SIDE_TILES[3];
            }
        }
        return -1;
    }

    /**
     * Check the possible cases in which the opponent may develop a double
     * threat, and return the tile in which the cpu must play to stop that
     * double threat from happening
     * @param tiles the board
     * @return the tile in which the cpu must play to stop a double threat
     * from developing, -1 if no double threat possible
     */
    private static int checkForOppDoubleThreat(int[] tiles) {
        int cpu = Tile.TILE_VAL_O;
        int opp = Tile.TILE_VAL_X;
        /* In this case, any open corner must be played to stop the double threat
         x | 2 | 3             x | 2 | 3
        ---+---+---           ---+---+---
         4 | x | 6     -->     4 | x | 6
        ---+---+---           ---+---+---
         7 | 8 | o             x | 8 | o
         */
        if (tiles[Tile.CENTER_TILE] == opp) {
            if (tiles[Tile.CORNER_TILES[0]] == opp) {
                return tiles[Tile.CORNER_TILES[1]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[1] :
                        (tiles[Tile.CORNER_TILES[2]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[2] :
                          (tiles[Tile.CORNER_TILES[3]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[3] : -1));
            } else if (tiles[Tile.CORNER_TILES[1]] == opp) {
                return tiles[Tile.CORNER_TILES[0]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[0] :
                        (tiles[Tile.CORNER_TILES[2]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[2] :
                                (tiles[Tile.CORNER_TILES[3]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[3] : -1));
            } else if (tiles[Tile.CORNER_TILES[2]] == opp) {
                return tiles[Tile.CORNER_TILES[1]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[1] :
                        (tiles[Tile.CORNER_TILES[0]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[0] :
                                (tiles[Tile.CORNER_TILES[3]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[3] : -1));
            } else if (tiles[Tile.CORNER_TILES[3]] == opp) {
                return tiles[Tile.CORNER_TILES[1]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[1] :
                        (tiles[Tile.CORNER_TILES[2]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[2] :
                                (tiles[Tile.CORNER_TILES[0]] == Tile.TILE_VAL_EMPTY ? Tile.CORNER_TILES[0] : -1));
            }
        }
        /* In this case, any side must be played to stop the double threat
         x | 2 | 3             x | 2 | o
        ---+---+---           ---+---+---
         4 | o | 6     -->     4 | o | 6
        ---+---+---           ---+---+---
         7 | 8 | x             x | 8 | x
         */
        if (tiles[Tile.CENTER_TILE] == cpu) {
            if ((tiles[Tile.CORNER_TILES[0]] == opp && tiles[Tile.CORNER_TILES[3]] == opp) ||
                    (tiles[Tile.CORNER_TILES[1]] == opp && tiles[Tile.CORNER_TILES[2]] == opp)) {
                return tiles[Tile.SIDE_TILES[1]] == Tile.TILE_VAL_EMPTY ? Tile.SIDE_TILES[1] :
                        (tiles[Tile.SIDE_TILES[2]] == Tile.TILE_VAL_EMPTY ? Tile.SIDE_TILES[2] :
                                (tiles[Tile.SIDE_TILES[3]] == Tile.TILE_VAL_EMPTY ? Tile.SIDE_TILES[3] :
                                        (tiles[Tile.SIDE_TILES[0]] == Tile.TILE_VAL_EMPTY ? Tile.SIDE_TILES[0] : -1)));
            }
        }
        /* In this case, 3 must be played to stop the double threat
         x | 2 | 3             x | 2 | x
        ---+---+---           ---+---+---
         4 | o | x     -->     4 | o | x
        ---+---+---           ---+---+---
         7 | 8 | 9             7 | o | 9
         */
        if (tiles[Tile.CORNER_TILES[0]] == opp && (tiles[Tile.SIDE_TILES[2]] == opp || tiles[Tile.SIDE_TILES[3]] == opp)) {
            return Tile.CORNER_TILES[3];
        } else if (tiles[Tile.CORNER_TILES[1]] == opp && (tiles[Tile.SIDE_TILES[1]] == opp || tiles[Tile.SIDE_TILES[3]] == opp)) {
            return Tile.CORNER_TILES[2];
        } else if (tiles[Tile.CORNER_TILES[2]] == opp && (tiles[Tile.SIDE_TILES[0]] == opp || tiles[Tile.SIDE_TILES[2]] == opp)) {
            return Tile.CORNER_TILES[2];
        } else if (tiles[Tile.CORNER_TILES[3]] == opp && (tiles[Tile.SIDE_TILES[0]] == opp || tiles[Tile.SIDE_TILES[1]] == opp)) {
            return Tile.CORNER_TILES[2];
        }
        /* In this case, 1 must be played to stop the double threat
         1 | x | 3             x | x | 3
        ---+---+---           ---+---+---
         x | o | 6     -->     x | o | 6
        ---+---+---           ---+---+---
         7 | 8 | 9             7 | 8 | o
         */
        if (tiles[Tile.SIDE_TILES[0]] == opp && tiles[Tile.SIDE_TILES[1]] == opp && tiles[Tile.CORNER_TILES[0]] == Tile.TILE_VAL_EMPTY) {
            return Tile.CORNER_TILES[0];
        } else if (tiles[Tile.SIDE_TILES[1]] == opp && tiles[Tile.SIDE_TILES[3]] == opp && tiles[Tile.CORNER_TILES[2]] == Tile.TILE_VAL_EMPTY) {
            return Tile.CORNER_TILES[2];
        } else if (tiles[Tile.SIDE_TILES[3]] == opp && tiles[Tile.SIDE_TILES[2]] == opp && tiles[Tile.CORNER_TILES[3]] == Tile.TILE_VAL_EMPTY) {
            return Tile.CORNER_TILES[3];
        } else if (tiles[Tile.SIDE_TILES[2]] == opp && tiles[Tile.SIDE_TILES[0]] == opp && tiles[Tile.CORNER_TILES[1]] == Tile.TILE_VAL_EMPTY) {
            return Tile.CORNER_TILES[1];
        }

        return -1; // No double threat possible
    }
}

import java.util.Scanner;

/**
 * Quiz 3
 * Abanob Tanous (amt812)
 * Samuel Li (zl4088)
 */
public class TicTacToe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter 1 to start a two player game, 2 to start playing against the CPU, or 0 to quit");
            try {
                int gameMode = Integer.parseInt(scanner.nextLine());
                if (gameMode == 0) {
                    System.exit(0);
                } else if (gameMode == 1) {
                    new TwoPlayerGame(scanner).start();
                    break;
                } else if (gameMode == 2) {
                    new CPUGame(scanner).start();
                    break;
                } else {
                    System.out.println("Invalid selection, try again");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("Invalid selection, try again");
            }
        }
    }

    /**
     * Check the board to see if there is a winner
     * @param tiles the board
     * @return 1 or 2 if player 1 or player 2 won respectively, or -1 if no winner
     */
    public static int checkForWinner(int[] tiles) {
        int winner;
        winner = checkColumnsForWinner(tiles);
        if (winner != -1) {
            return winner;
        }

        winner = checkRowsForWinner(tiles);
        if (winner != -1) {
            return winner;
        }

        return checkDiagonalsForWinner(tiles);
    }

    /**
     * Check the columns to see if there is a winner
     * @param tiles the board
     * @return 1 or 2 if player 1 or player 2 won respectively, or -1 if no winner
     */
    private static int checkColumnsForWinner(int[] tiles) {
        for (int x = 0; x < 3; x++) { // Iterate through all the columns
            int val = tiles[x]; // Get the value of the first tile in the column
            if (val == Tile.TILE_VAL_EMPTY) {
                continue; // Tile is empty so there can be no three in a row in this column, go to the next column
            }
            if (val == tiles[3 + x] && val == tiles[6 + x]) { // Check if all three values in the column are equal
                return val; // If they are, then the player whose mark is in the column is the winner
            }
        }
        return -1; // There is no winner
    }

    /**
     * Check the rows to see if there is a winner
     * @param tiles the board
     * @return 1 or 2 if player 1 or player 2 won respectively, or -1 if no winner
     */
    private static int checkRowsForWinner(int[] tiles) {
        for (int y = 0; y < 3; y++) { // Iterate through all the rows
            int val = tiles[3 * y]; // Get the value of the first tile in the row
            if (val == Tile.TILE_VAL_EMPTY) {
                continue; // Tile is empty so there can be no three in a row in this row, go to the next row
            }
            if (val == tiles[3 * y + 1] && val == tiles[3 * y + 2]) { // Check if all three values in the row are equal
                return val; // If they are, then the player whose mark is in the row is the winner
            }
        }
        return -1; // There is no winner
    }

    /**
     * Check the diagonals to see if there is a winner
     * @param tiles the board
     * @return 1 or 2 if player 1 or player 2 won respectively, or -1 if no winner
     */
    private static int checkDiagonalsForWinner(int[] tiles) {
        int d0 = tiles[0]; // Top left corner
        if (d0 == tiles[4] && d0 == tiles[8]) { // Compare the top left corner with center and bot right corner
            return d0; // If they are equal, then the player whose mark is in the diagonal is the winner
        }
        int d1 = tiles[2]; // Top right corner
        if (d1 == tiles[4] && d1 == tiles[6]) { // Compare the top right corner with center and bot left corner
            return d1; // If they are equal, then the player whose mark is in the diagonal is the winner
        }
        return -1; // There is no winner
    }

    /**
     * Print the board to the console
     * @param tiles the board
     */
    public static void printBoard(int[] tiles) {
        System.out.printf(" %s | %s | %s\n", formatTileVal(0, tiles), formatTileVal(1, tiles), formatTileVal(2, tiles));
        System.out.println("---+---+---");
        System.out.printf(" %s | %s | %s\n", formatTileVal(3, tiles), formatTileVal(4, tiles), formatTileVal(5, tiles));
        System.out.println("---+---+---");
        System.out.printf(" %s | %s | %s\n", formatTileVal(6, tiles), formatTileVal(7, tiles), formatTileVal(8, tiles));
    }

    /**
     * Format the value of the tile into a string to be printed.
     * @param i the index of the tile
     * @param tiles the board
     * @return either "x", "o", or "[i]"
     */
    private static String formatTileVal(int i, int[] tiles) {
        int val = tiles[i];
        return val == 0 ? Integer.toString(i + 1) : (val == 1 ? "x" : "o");
    }

    /**
     * Attempts to play the specified move.
     * @param val the value to place in the specified tile
     * @param tileIndex the specified tile
     * @param tiles the board
     * @return true if the move was played, otherwise false
     */
    public static boolean play(int val, int tileIndex, int[] tiles) {
        if (tiles[tileIndex] == Tile.TILE_VAL_EMPTY) {
            tiles[tileIndex] = val;
            return true;
        }
        return false;
    }
}

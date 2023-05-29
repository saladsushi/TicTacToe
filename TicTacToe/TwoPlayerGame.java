import java.util.Scanner;

/**
 * Quiz 3
 * Abanob Tanous (amt812)
 * Samuel Li (zl4088)
 */
public class TwoPlayerGame {
    private final Scanner scanner;
    private final int[] tiles;
    private int totalInvalidSelections;

    public TwoPlayerGame(Scanner scanner) {
        this.scanner = scanner;
        tiles = new int[9];
        totalInvalidSelections = 0;
    }

    /**
     * Runs the game loop
     */
    public void start() {
        int turnNum = 1; // Tracks the turn number
        while (true) {
            if (turnNum == 5) { // On the fifth turn, there is only one tile left
                TicTacToe.printBoard(tiles);
                System.out.println("\nOnly one possible move left");

                for (int i = 0; i < 9; i++) { // Find the empty tile
                    if (tiles[i] == Tile.TILE_VAL_EMPTY) {
                        tiles[i] = Tile.TILE_VAL_X; // Make the last move in the last empty tile
                        TicTacToe.printBoard(tiles);

                        int winner = TicTacToe.checkForWinner(tiles); // Check if there is a winner
                        if (winner == -1) { // A tie
                            System.out.println("Game has ended in a tie");
                        } else { // Should not be the case, but here just in case
                            System.out.printf("Player %d wins!\n", winner);
                        }
                    }
                }

                System.exit(0);
            }

            doPlayerTurn(1);
            doPlayerTurn(2);

            turnNum++;
        }
    }

    /**
     * Get the player input, validate it, and play the move
     * @param player the player whose turn it is
     */
    private void doPlayerTurn(int player) {
        TicTacToe.printBoard(tiles);
        System.out.printf("\nPlayer %d turn: ", player);
        int input = getInput();
        if (input == -1) { // Player forfeit due to invalid selection limit
            System.out.printf("Player %d has forfeited the game due to reaching the invalid selection limit\n", player);
            System.exit(0);
        } else if (input == 0) { // Player selected to forfeit
            System.out.printf("Player %d has forfeited the game\n", player);
            System.exit(0);
        }

        tiles[input - 1] = player;

        int winner = TicTacToe.checkForWinner(tiles);
        if (winner == player) { // Player won
            TicTacToe.printBoard(tiles);
            System.out.printf("Player %d wins!\n", player);
            System.exit(0);
        }
    }

    /**
     * Get the player input and validate it
     * @return the validated player input
     */
    private int getInput() {
        int consecutiveInvalidSelections = 0;
        while (true) {
            try {
                int selection = Integer.parseInt(scanner.nextLine());
                if (selection == 0) {
                    return selection; // Selected forfeit
                }
                if (selection > 0 && selection <= 9 && tiles[selection - 1] == Tile.TILE_VAL_EMPTY) {
                    return selection; // Selected a valid tile
                }

                totalInvalidSelections++;
                consecutiveInvalidSelections++;
                if (totalInvalidSelections >= 5 || consecutiveInvalidSelections >= 3) {
                    return -1; // Forfeit due to invalid selection limit
                }

                System.out.println("Invalid selection, try again");
            } catch (NumberFormatException ignored) {
                totalInvalidSelections++;
                consecutiveInvalidSelections++;
                if (totalInvalidSelections >= 5 || consecutiveInvalidSelections >= 3) {
                    return -1; // Forfeit due to invalid selection limit
                }

                System.out.println("Invalid selection, try again");
            }
        }
    }
}

import java.util.Scanner;

/**
 * Quiz 3
 * Abanob Tanous (amt812)
 * Samuel Li (zl4088)
 */
public class CPUGame {
    private final Scanner scanner;
    private final int[] tiles;
    private int totalInvalidSelections;

    public CPUGame(Scanner scanner) {
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
                        tiles[i] = Tile.TILE_VAL_X;
                        TicTacToe.printBoard(tiles);

                        int winner = TicTacToe.checkForWinner(tiles);
                        if (winner == -1) { // A tie
                            System.out.println("Game has ended in a tie");
                        } else { // Here just in case
                            System.out.printf("Player %d wins!\n", winner);
                        }
                    }
                }

                System.exit(0);
            }

            doPlayerTurn();
            doCpuTurn(turnNum);

            turnNum++;
        }
    }

    /**
     * Do the CPU turn
     * @param turnNum turn number
     */
    private void doCpuTurn(int turnNum) {
        TicTacToe.printBoard(tiles);
        System.out.println("\nCPU Turn");
        TicTacToeCPU.makeAMove(turnNum, tiles);

        int winner = TicTacToe.checkForWinner(tiles);
        if (winner == 2) {
            TicTacToe.printBoard(tiles);
            System.out.println("CPU Wins!");
            System.exit(0);
        }
    }

    /**
     * Get the player input, validate it, and play the move
     */
    private void doPlayerTurn() {
        TicTacToe.printBoard(tiles);
        System.out.print("\nPlayer 1 turn: ");
        int input = getInput();
        if (input == -1) { // Player forfeit due to invalid selection limit
            System.out.println("Player 1 has forfeited the game due to reaching the invalid selection limit");
            System.exit(0);
        } else if (input == 0) { // Player selected to forfeit
            System.out.println("Player 1 has forfeited the game");
            System.exit(0);
        }

        tiles[input - 1] = Tile.TILE_VAL_X;

        int winner = TicTacToe.checkForWinner(tiles);
        if (winner == Tile.TILE_VAL_X) { // Player won
            TicTacToe.printBoard(tiles);
            System.out.println("Player 1 wins!");
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

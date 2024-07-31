package q2;

public class FourInARowGame {

    private final int PLAYER1 = 1;
    private final int PLAYER2 = 2;
    private final int COLUMNS = 7;
    private final int ROWS = 6;
    private int[][] board;
    private int numOfBttnsPrssd;

    /**
     * Constructor for FourInARowGame. Initializes the game board and sets the button press count to zero.
     */
    public FourInARowGame() {
        board = new int[ROWS][COLUMNS];
        initializeBoard();
        numOfBttnsPrssd = 0;
    }

    /**
     * Initializes the game board by setting all cells to 0.
     */
    public void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = 0;
            }
        }
    }

    /**
     * Adds a player's token to the specified column.
     * 
     * @param index  The column index.
     * @param player The player number (1 or 2).
     * @return The row index where the token was added, or -1 if the column is full.
     */
    public int addItem(int index, int player) {
        int j;
        for (j = ROWS - 1; j >= 0; j--) {
            if (board[j][index] == 0) {
                board[j][index] = player;
                break;
            }
        }
        return j;
    }

    /**
     * Checks if there is a winning column at the specified cell.
     * 
     * @param i The row index.
     * @param j The column index.
     * @return True if there is a winning column, false otherwise.
     */
    public boolean isColumn(int i, int j) {
        int value = board[i][j];
        int counter = 1;
        int iter = i + 1;

        // Count consecutive tokens in the column
        while (iter < ROWS && board[iter][j] == value) {
            counter++;
            iter++;
        }

        return counter >= 4;
    }

    /**
     * Checks if there is a winning row at the specified cell.
     * 
     * @param i The row index.
     * @param j The column index.
     * @return True if there is a winning row, false otherwise.
     */
    public boolean isRow(int i, int j) {
        int value = board[i][j];
        int counter = 1;
        int iter = j + 1;

        // Count consecutive tokens to the right
        while (iter < COLUMNS && board[i][iter] == value) {
            counter++;
            iter++;
        }

        iter = j - 1;
        // Count consecutive tokens to the left
        while (iter >= 0 && board[i][iter] == value) {
            counter++;
            iter--;
        }

        return counter >= 4;
    }

    /**
     * Checks if there is a winning diagonal at the specified cell.
     * 
     * @param i The row index.
     * @param j The column index.
     * @return True if there is a winning diagonal, false otherwise.
     */
    public boolean isDiagonal(int i, int j) {
        int value = board[i][j];
        int[] rowDirections = {1, 1, -1, -1};  // Directions for row changes
        int[] colDirections = {1, -1, 1, -1};  // Directions for column changes

        // Check both main diagonals
        for (int d = 0; d < 2; d++) {
            int counter = 1;

            // Count in the positive direction
            counter += countInDirection(i, j, rowDirections[d], colDirections[d], value);
            // Count in the negative direction
            counter += countInDirection(i, j, -rowDirections[d], -colDirections[d], value);

            if (counter >= 4) {
                return true;
            }
        }

        return false;
    }

    /**
     * Counts the number of consecutive tokens in a specified direction.
     * 
     * @param startRow The starting row index.
     * @param startCol The starting column index.
     * @param rowDir   The direction of row changes.
     * @param colDir   The direction of column changes.
     * @param value    The player number (1 or 2).
     * @return The number of consecutive tokens in the specified direction.
     */
    private int countInDirection(int startRow, int startCol, int rowDir, int colDir, int value) {
        int counter = 0;
        int row = startRow + rowDir;
        int col = startCol + colDir;

        // Count consecutive tokens in the specified direction
        while (isInBounds(row, col) && board[row][col] == value) {
            counter++;
            row += rowDir;
            col += colDir;
        }

        return counter;
    }

    /**
     * Checks if the specified cell is within the bounds of the board.
     * 
     * @param i The row index.
     * @param j The column index.
     * @return True if the cell is within bounds, false otherwise.
     */
    private boolean isInBounds(int i, int j) {
        return i >= 0 && i < ROWS && j >= 0 && j < COLUMNS;
    }

    /**
     * Checks if the game board is full.
     * 
     * @return True if the board is full, false otherwise.
     */
    public boolean isBoardFull() {
        for (int j = 0; j < COLUMNS; j++) {
            if (board[0][j] == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the current player based on the number of button presses.
     * 
     * @return The current player number (1 or 2).
     */
    public int getCurrentPlayer() {
        numOfBttnsPrssd++;
        return (numOfBttnsPrssd % 2 == 0) ? PLAYER2 : PLAYER1;
    }

    /**
     * Gets the current state of the game board.
     * 
     * @return The game board as a 2D array.
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        initializeBoard();
        numOfBttnsPrssd = 0;
    }
}

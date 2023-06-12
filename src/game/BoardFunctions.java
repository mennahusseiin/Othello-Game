//BoardHelper
package game;

import java.awt.*;
import java.util.ArrayList;

public class BoardFunctions {

    /**
     * Checks if the game is finished by determining if both players have no available moves left.
     * Returns true if the game is finished, false otherwise.
     *
     * @param board 2D array representing the game board
     * @return True if the game is finished, false otherwise
     */
    public static boolean BOOL_GameFinished(int[][] board) {
        // The game is finished if neither player has any available moves left
        return !(PlayerHasAnyMoves(board, 1) || PlayerHasAnyMoves(board, 2));
    }

    /**
     * Determines the winner of the game based on the stone count on the game board.
     * <p>
     * Returns 0 for a tie, 1 for player 1, and 2 for player 2.
     *
     * @param board 2D array representing the game board
     * @return The identifier of the winner (0 for tie, 1 for player 1, 2 for player 2)
     */
    public static int getWinner(int[][] board) {
        if (!BOOL_GameFinished(board))
        {
            // Game not finished yet
            return -1;
        }
        else
        {
            //Get total stone count for each player
            int player1Stones = getOnePlayerStoneCount(board, 1);
            int player2Stones = getOnePlayerStoneCount(board, 2);

            if (player1Stones == player2Stones)
            {
                // Tie
                return 0;
            }
            else if (player1Stones > player2Stones)
            {
                // Player 1 wins
                return 1;
            }
            else
            {
                // Player 2 wins
                return 2;
            }
        }
    }


    /**
     * Returns the total count of stones present on the game board.
     *
     * @param board 2D array representing the game board
     * @return The total number of stones on the board
     */
    public static int getTotalBoardStoneCount(int[][] board) {
        // Initialize the counter variable
        int count = 0;

        // Iterate over each row in the board
        for (int[] rows : board) {
            // Iterate over each stone in the row
            for (int TotalStone : rows) {
                // Check if the stone is not empty (represented by 0)
                if (TotalStone != 0) {
                    // Increment the counter if the stone is present
                    count++;
                }
            }
        }
        // Return the final count of stones on the board
        return count;
    }

    /**
     * Returns the count of stones owned by a specific player on the game board.
     *
     * @param board  2D array representing the game board
     * @param player The player identifier (stone) to count
     * @return The number of stones owned by the player
     */
    public static int getOnePlayerStoneCount(int[][] board, int player) {
        // Initialize the counter variable
        int count = 0;

        // Iterate over each row in the board
        for (int[] rows : board) {
            // Iterate over each stone in the row
            for (int TotalPlayerStone : rows) {
                // Check if the stone belongs to the player
                if (TotalPlayerStone == player) {
                    // Increment the counter if the stone is owned by the player
                    count++;
                }
            }
        }
        // Return the final count of stones owned by the player
        return count;
    }

    /**
     * Checks if the specified player has any available moves on the game board.
     * <p>
     * Returns true if there are available moves, false otherwise.
     *
     * @param board  2D array representing the game board
     * @param player The player identifier to check for available moves
     * @return True if the player has available moves, false otherwise
     */
    public static boolean PlayerHasAnyMoves(int[][] board, int player) {
        // Get all possible moves for the player
        ArrayList<Point> Possible_Moves = getAllPossibleMoves(board, player);

        // If the list of possible moves is not empty, there are available moves for the player
        return !Possible_Moves.isEmpty();
    }


    /**
     * Retrieves all possible moves for the specified player on the game board.
     *
     * @param board  2D array representing the game board
     * @param player The player identifier
     * @return List of Point objects representing the coordinates of possible moves
     */
    public static ArrayList<Point> getAllPossibleMoves(int[][] board, int player) {
        ArrayList<Point> Possible_Moves = new ArrayList<>();

        // Iterate over each cell on the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // Check if the player can play at the current cell
                if (ValidPlay(board, player, i, j)) {
                    // Add the current cell as a possible move
                    Possible_Moves.add(new Point(i, j));
                }
            }
        }
        return Possible_Moves;
    }


    /**
     * Retrieves the reverse points for a specific move made by the player on the game board.
     * <p>
     * Reverse points represent the opponent's stones that will be flipped when the move is made.
     *
     * @param board  2D array representing the game board
     * @param player The player identifier
     * @param i      The row index of the move
     * @param j      The column index of the move
     * @return List of Point objects representing the coordinates of the reverse points
     */
    public static ArrayList<Point> getReversePoints(int[][] board, int player, int i, int j) {
        ArrayList<Point> reversePoints = new ArrayList<>();

        // Identify the opponent's player identifier
        int opponent_player = (player == 1) ? 2 : 1;
        // All possible directions to check
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        // Check each direction for possible reverse points
        for (int[] direction : directions)
        {
            int rows = i + direction[0];
            int cols = j + direction[1];
            ArrayList<Point> ReversePoints = new ArrayList<>();

            // Traverse in the current direction until an invalid position or player's stone is encountered
            while (isThePositionValid(rows, cols, board.length, board[0].length) && board[rows][cols] == opponent_player)
            {
                ReversePoints.add(new Point(rows, cols));
                rows += direction[0];
                cols += direction[1];
            }
            // If the traversal ends with the player's stone and there are reverse points in the current direction,
            // add them to the list of reverse points
            if (isThePositionValid(rows, cols, board.length, board[0].length) && board[rows][cols] == player && !ReversePoints.isEmpty())
            {
                reversePoints.addAll(ReversePoints);
            }
        }
        return reversePoints;
    }


    /**
     * Checks if the specified position is valid within the given dimensions of the game board.
     *
     * @param row     The row index of the position
     * @param col     The column index of the position
     * @param NumOfRows The total number of rows on the game board
     * @param NumofCols The total number of columns on the game board
     * @return True if the position is valid, false otherwise
     */
    private static boolean isThePositionValid(int row, int col, int NumOfRows, int NumofCols) {
        // The position is valid if it falls within the boundaries of the game board
        return row >= 0 && row < NumOfRows && col >= 0 && col < NumofCols;
    }


    /**
     * Checks if a player can make a valid move at the specified position on the game board.
     *
     * @param board  2D array representing the game board
     * @param player The player identifier
     * @param i      The row index of the position
     * @param j      The column index of the position
     * @return True if the player can make a valid move, false otherwise
     */
    public static boolean ValidPlay(int[][] board, int player, int i, int j) {
        if (board[i][j] != 0) {
            // The position is already occupied
            return false;
        }
        // Identify the opponent's player identifier
        int opponent_player = (player == 1) ? 2 : 1;
        // All possible directions to check
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        // Traverse in the current direction until an invalid position or player's stone is encountered
        for (int[] direction : directions) {
            int rows = i + direction[0];
            int cols = j + direction[1];
            int count = 0;
            while (isThePositionValid(rows, cols, board.length, board[0].length) && board[rows][cols] == opponent_player) {
                count++;
                rows += direction[0];
                cols += direction[1];
            }
            // If the traversal ends with the player's stone and there are opponent's stones in between,
            // it indicates a valid move
            if (isThePositionValid(rows, cols, board.length, board[0].length) && board[rows][cols] == player && count > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a new game board after a player makes a move at the specified position.
     * <p>
     * The new board includes the player's stone placed at the move position and updates the
     * <p>
     * opponent's stones that are flipped as a result of the move.
     *
     * @param board  2D array representing the current game board
     * @param move   The position where the player wants to make a move
     * @param player The player identifier
     * @return A new 2D array representing the updated game board after the move
     */
    public static int[][] getNewBoardAfterMove(int[][] board, Point move, int player) {
        int[][] newBoard = new int[8][8]; // Create a new game board with the same dimensions

        // Copy the values from the current board to the new board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++)
            {
                newBoard[i][j] = board[i][j];
            }
        }

        // Place the player's stone at the move position
        newBoard[move.x][move.y] = player;

        // Retrieve the reverse points (opponent's stones to be flipped) for the move
        ArrayList<Point> reversePoints = BoardFunctions.getReversePoints(newBoard, player, move.x, move.y);

        // Update the new board by flipping the opponent's stones
        for (Point pt : reversePoints) {
            newBoard[pt.x][pt.y] = player;
        }

        return newBoard;
    }

}
//gameEngine

package game;

/**
 * An interface for the communication between the GUI and the game engine.
 */
interface GameEngine {

    /**
     * Gets the value of a cell on the game board.
     *
     * @param i the row index
     * @param j the column index
     * @return the integer value of the cell
     */
    int getCellValue(int i, int j);

    /**
     * Sets the value of a cell on the game board.
     *
     * @param i the row index
     * @param j the column index
     * @param value the integer value to be set
     */
    void setCellValue(int i, int j, int value);

    /**
     * Handles a click event on a cell of the game board.
     *
     * @param i the row index of the clicked cell
     * @param j the column index of the clicked cell
     */
    void handleClick(int i, int j);
}
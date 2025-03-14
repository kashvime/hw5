package cs3500.pawnsboard.view;

import java.io.IOException;

/**
 * Interface for methods for rendering the board state in text format.
 */
public interface ITextualView {

  /**
   * Renders the current game state to the given appendable.
   *
   * @param append the appendable where the game state is written
   * @throws IOException if appendable fails
   */
  void render(Appendable append) throws IOException;

  /**
   * Returns a string representation of the board.
   * The format includes:
   * - '_' for empty cells
   * - '1', '2', or '3' for the number of pawns in a cell
   * - 'R' for a red player's card
   * - 'B' for a blue player's card
   * - Row scores appended at the end of each row
   *
   * @return a textual representation of the board
   */
  @Override
  String toString();
}

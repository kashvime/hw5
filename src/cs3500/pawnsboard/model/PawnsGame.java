package cs3500.pawnsboard.model;

/**
 * Represents the game logic for the Pawns Board game.
 */
public interface PawnsGame {

  /**
   * Starts the game by initializing players, board, and dealing cards.
   *
   * @param playerRed  The red player.
   * @param playerBlue The blue player.
   * @param handSize   The number of starting cards for each player.
   * @throws IllegalStateException    if the game has already started.
   * @throws IllegalArgumentException if handSize is invalid.
   */
  void startGame(Player playerRed, Player playerBlue, int handSize);

  /**
   * Returns whether the game is over.
   *
   * @return true if both players have passed their turns, otherwise false.
   */
  boolean isGameOver();

  /**
   * Returns the current player whose turn it is.
   *
   * @return the current player.
   */
  IPlayer getCurrentPlayer();

  /**
   * Places a card on the board at the given position, if valid.
   *
   * @param card card to place.
   * @param row  row index.
   * @param col  column index.
   * @throws IllegalArgumentException if the move is invalid.
   * @throws IllegalStateException    if there are not enough pawns.
   */
  void placeCardInPosition(Card card, int row, int col);

  /**
   * Passes the current player's turn.
   */
  void passTurn();

  /**
   * Returns the board state as a 2D array of cells.
   *
   * @return array representing the board state.
   */
  Cell[][] getBoardState();

  /**
   * Returns the score for a given row.
   *
   * @param row The row index.
   * @return scores for the red and blue players.
   * @throws IllegalArgumentException if the row is invalid.
   */
  int[] getRowScore(int row);

  /**
   * Returns the winner of the game, or null if there is a tie.
   *
   * @return the winning player or null if it's a tie.
   */
  IPlayer getWinner();
}

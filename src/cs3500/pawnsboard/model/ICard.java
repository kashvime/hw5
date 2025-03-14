package cs3500.pawnsboard.model;

/**
 * Represents a generic card in the game.
 */

public interface ICard {

  /**
   * Returns the cost of the card in pawns.
   */
  int getCost();

  /**
   * Returns the value score of the card.
   */
  int getValue();

  /**
   * Returns a copy of the influence grid.
   */
  int[][] getInfluenceGrid();

  /**
   * Returns a string representation of the card.
   */
  String toString();
}

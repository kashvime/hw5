package cs3500.pawnsboard.model;

public interface ICell {

  /**
   * Checks if the cell contains a card
   */
  boolean hasCard();

  /**
   * Checks if the cell contains pawns
   */
  boolean hasPawns();

  /**
   * Returns the number of pawns in this cell
   */
  int getPawnCount();

  /**
   * Returns the card in this cell, if any
   */
  Card getCard();

  /**
   * Returns the player who owns the cell or null if unowned
   */
  Player getOwner();

}

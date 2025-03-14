package cs3500.pawnsboard.model;

/**
 * Represents a cell on the Pawns Board.
 * A cell can either be empty, contain a card, or have pawns of a single player.
 */
public class Cell implements ICell {
  private int pawnCount;
  private Player owner;
  private Card card;

  /**
   * Constructs an empty cell with no pawns and no card.
   */
  public Cell() {
    this.pawnCount = 0;
    this.owner = null;
    this.card = null;
  }

  /**
   * Checks if the cell is empty
   */
  protected boolean isEmpty() {
    return card == null && pawnCount == 0;
  }

  @Override
  public boolean hasCard() {
    return card != null;
  }

  @Override
  public boolean hasPawns() {
    return pawnCount > 0;
  }

  @Override
  public int getPawnCount() {
    return pawnCount;
  }

  @Override
  public Card getCard() {
    return card;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  /**
   * Adds a pawn to the cell.
   *
   * @param player The player adding the pawn.
   * @throws IllegalStateException if the cell has a card or exceeds 3 pawns.
   */

  protected void addPawn(Player player) {
    if (hasCard()) {
      throw new IllegalStateException("Cannot add pawns to a cell containing a card.");
    }

    if (isEmpty()) {
      owner = player;
      pawnCount = 1;
      return;
    }

    if (owner.equals(player)) {
      if (pawnCount < 3) {
        pawnCount++;
      }
    } else {
      owner = player;
    }
  }

  /**
   * Places a card in the cell, removing any pawns and setting ownership.
   *
   * @param card   The card to be placed.
   * @param player The player placing the card.
   * @throws IllegalStateException if the cell is not empty.
   */
  protected void placeCard(Card card, Player player) {
    if (hasCard()) {
      throw new IllegalStateException("Can only place a card in an empty cell.");
    }
    this.card = card;
    this.owner = player;
    this.pawnCount = 0;
  }


  /**
   * Removes all pawns from Cell.
   */
  protected void removeAllPawns() {
    this.pawnCount = 0;
  }

  @Override
  public String toString() {
    if (hasCard()) {
      return owner.getColor() == PlayerColor.RED ? "Red" : "Blue";
    } else if (hasPawns()) {
      return String.valueOf(pawnCount);
    } else {
      return "_";
    }
  }

  /**
   * Converts the paewns in cell to the other player
   *
   * @param newOwner is the new owner of the pawns in cell.
   */
  protected void convertPawns(Player newOwner) {
    this.owner = newOwner;
  }

}

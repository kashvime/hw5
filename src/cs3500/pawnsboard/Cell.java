package cs3500.pawnsboard;

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

  @Override
  public boolean isEmpty() {
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
  public void addPawn(Player player) {
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


  public void applyInfluence(Player player) {
    if (hasCard()) {
      return;
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
  public void placeCard(Card card, Player player) {
    if (hasCard()) {
      throw new IllegalStateException("Can only place a card in an empty cell.");
    }
    this.card = card;
    this.owner = player;
    this.pawnCount = 0;
  }

  /**
   * Clears the cell, removing any pawns and cards.
   */
  @Override
  public void clear() {
    this.card = null;
    this.owner = null;
    this.pawnCount = 0;
  }


  public void removeAllPawns() {
    this.pawnCount = 0;
  }

  @Override
  public String toString() {
    if (hasCard()) {
      return owner.getColor() == PlayerColor.RED ? "Red" : "Blue";
    } else if (hasPawns()) {
      return String.valueOf(pawnCount);
    } else {
      return "_"; // Empty cell
    }
  }
}

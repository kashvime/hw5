package cs3500.pawnsboard;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a game card with a name, cost, value, and 5x5 influence grid.
 */
public class Card implements ICard {
  private final String name;
  private final int cost;
  private final int value;
  private final int[][] influenceGrid;

  /**
   * Creates a card with the given name, cost, value, and influence grid.
   *
   * @param name  name of the card.
   * @param cost  cost in pawns.
   * @param value  value score that determines the overall score of the game.
   * @param influenceGrid A 5x5 grid demonstrating the card’s influence on the board.
   * @throws IllegalArgumentException if card name is null or empty.
   * @throws IllegalArgumentException if cost is not 1,2, or 3 pawns.
   * @throws IllegalArgumentException if value score is not positive.
   * @throws IllegalArgumentException grid is not 5x5 array.
   */
  public Card(String name, int cost, int value, int[][] influenceGrid) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Card name cannot be null or empty.");
    }
    if (cost < 1 || cost > 3) {
      throw new IllegalArgumentException("Cost must be 1, 2, or 3 pawns.");
    }
    if (value <= 0) {
      throw new IllegalArgumentException("Value score must be a positive integer.");
    }
    if (influenceGrid == null || influenceGrid.length != 5 || influenceGrid[0].length != 5) {
      throw new IllegalArgumentException("Influence grid must be a 5x5 array.");
    }

    this.name = name;
    this.cost = cost;
    this.value = value;
    this.influenceGrid = new int[5][5];
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getCost() {
    return cost;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public int[][] getInfluenceGrid() {
    return influenceGrid;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Card card = (Card) obj;
    return this.cost == card.cost &&
            this.value == card.value &&
            this.name.equals(card.name) &&
            Arrays.deepEquals(this.influenceGrid, card.influenceGrid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, cost, value, Arrays.deepHashCode(influenceGrid));
  }

  @Override
  public String toString() {
    return name + " (Cost: " + cost + ", Value: " + value + ")";
  }
}
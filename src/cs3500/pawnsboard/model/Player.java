package cs3500.pawnsboard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a player in the game.
 * Each player has a color (RED or BLUE), a hand of cards, and a deck from which they draw cards.
 */
public class Player implements IPlayer {

  private final PlayerColor color;
  private final List<Card> deck;
  private final List<Card> hand;

  /**
   * Constructs a player with a given color and deck of cards.
   *
   * @param color The player's color (RED or BLUE).
   * @param deck  The list of cards that make up the player's deck.
   */
  public Player(PlayerColor color, List<Card> deck) {
    if (deck == null || deck.isEmpty()) {
      throw new IllegalArgumentException("Deck cannot be null or empty.");
    }
    // Check For More Than 2 Duplicates of A Card
    Map<Card, Integer> objectCountMap = new HashMap<>();
    for (Card obj : deck) {
      int count = objectCountMap.getOrDefault(obj, 0);
      count++;
      if (count > 2) {
        throw new IllegalArgumentException("Deck contains more than two of a card.");
      }
      // Update the count in the map
      objectCountMap.put(obj, count);
    }

    this.color = color;
    this.deck = new ArrayList<>(deck);
    this.hand = new ArrayList<>();
  }

  @Override
  public PlayerColor getColor() {
    return color;
  }

  @Override
  public List<Card> getHand() {
    return new ArrayList<>(hand);
  }

  /**
   * Draws a card from the player's deck, adding it to their hand.
   *
   * @return true if a card was successfully drawn, false if the deck is empty.
   */
  protected boolean drawCard() {
    if (deck.isEmpty()) {
      return false;
    }
    Card drawnCard = deck.remove(0);
    hand.add(drawnCard);
    return true;
  }


  /**
   * Removes the specified card from the player's hand.
   *
   * @param card the card to remove.
   * @throws IllegalArgumentException if the card is not in the player's hand.
   */
  protected void removeCardFromHand(Card card) {
    hand.remove(card);
  }

  /**
   * Checks if the player's deck is empty.
   */
  protected boolean isDeckEmpty() {
    return deck.isEmpty();
  }

  /**
   * Gets the remaining deck size.
   *
   * @return The number of cards left in the deck.
   */
  public int getDeckSize() {
    return deck.size();
  }


  @Override
  public String toString() {
    return "Player" + color;
  }
}

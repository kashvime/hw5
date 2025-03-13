package cs3500.pawnsboard;

import java.util.List;

/**
 * Represents a player in the game.
 * Each player has a color , a hand of cards,
 * and a set of pawns.
 */
public interface IPlayer {

  /**
   * Returns the player's color. (RED OR BLUE)
   */
  PlayerColor getColor();

  /**
   * Returns the current hand of the player.
   *
   * @return a copy of the list of cards currently in the player's hand.
   */
  List<Card> getHand();

  /**
   * Draws a card from the player's deck, adding it to their hand.
   *
   * @return true if a card was successfully drawn, false if the deck is empty.
   */

  boolean drawCard();

  /**
   * Removes the specified card from the player's hand.
   *
   * @param card the card to remove.
   * @throws IllegalArgumentException if the card is not in the player's hand.
   */
  void removeCardFromHand(Card card);
}

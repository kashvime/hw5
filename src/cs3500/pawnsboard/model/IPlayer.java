package cs3500.pawnsboard.model;

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

}

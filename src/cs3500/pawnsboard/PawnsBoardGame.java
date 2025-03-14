package cs3500.pawnsboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import cs3500.pawnsboard.model.Card;
import cs3500.pawnsboard.model.DeckConfigReader;
import cs3500.pawnsboard.model.IPlayer;
import cs3500.pawnsboard.model.PawnsBoard;
import cs3500.pawnsboard.model.PawnsGame;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.PlayerColor;
import cs3500.pawnsboard.view.TextualView;

/**
 * Main entry point for the Pawns Board game.
 * Automatically loads deck configuration and starts the game.
 */
public class PawnsBoardGame {
  public static void main(String[] args) {
    try {
      String configFilePath = "docs" + File.separator + "deck.config";
      List<Card> deck = DeckConfigReader.loadDeck(configFilePath);

      Player playerRed = new Player(PlayerColor.RED, deck);
      Player playerBlue = new Player(PlayerColor.BLUE, deck);

      PawnsGame game = new PawnsBoard(3, 5);
      game.startGame(playerRed, playerBlue, 5);

      TextualView view = new TextualView(game);

      view.render(System.out);

      manuallyPlaceCards(game, view);

      view.render(System.out);
      System.out.println("\nGame Over!");
      IPlayer winner = game.getWinner();
      System.out.println(winner == null ? "It's a Tie!" : "Winner: " + winner.getColor());

    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Deck file not found: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error occurred: " + e.getMessage());
    }
  }

  /**
   * Manually places valid cards for both players in a structured manner.
   */
  private static void manuallyPlaceCards(PawnsGame game, TextualView view) {
    int[][] moves = {
            {0, 0, 0}, {1, 2, 4}, {2, 0, 1}, {0, 0, 4},
            {4, 2, 1}, {5, 1, 4}, {0, 1, 0}, {0, 2, 3},
            {0, 1, 1}, {0, 0, 3}, {2, 2, 0}
    };

    for (int[] move : moves) {
      placeAndRender(game, view, move[0], move[1], move[2]);
    }

    game.passTurn();
    game.passTurn();
  }

  /**
   * Helper method to place a card and render the board.
   */
  private static void placeAndRender(PawnsGame game, TextualView view, int cardIndex, int row, int col) {
    try {
      game.placeCardInPosition(game.getCurrentPlayer().getHand().get(cardIndex), row, col);
      view.render(System.out);
    } catch (IllegalArgumentException | IllegalStateException e) {
      System.err.println("Invalid move: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Error rendering board: " + e.getMessage());
    }
  }
}

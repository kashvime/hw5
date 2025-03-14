package cs3500.pawnsboard.view;

import cs3500.pawnsboard.model.*;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * JUnit tests for the TextualView class.
 */
public class TextualViewTest {
  private TextualView view;
  private PawnsBoard game;
  private Player playerRed;
  private Player playerBlue;
  private List<Card> deck;
  private int[][] influenceGrid;

  @Before
  public void setUp() {
    influenceGrid = new int[5][5];

    deck = new ArrayList<>();
    deck.add(new Card("Security", 1, 2, influenceGrid));
    deck.add(new Card("Bee", 1, 1, influenceGrid));
    deck.add(new Card("Riot", 2, 3, influenceGrid));
    deck.add(new Card("Screamer", 1, 3, influenceGrid));
    deck.add(new Card("Devil", 3, 4, influenceGrid));
    deck.add(new Card("NEW", 3, 4, influenceGrid));


    playerRed = new Player(PlayerColor.RED, deck);
    playerBlue = new Player(PlayerColor.BLUE, deck);

    game = new PawnsBoard(3, 5);
    view = new TextualView(game);
  }

  @Test
  public void testFullBoardGameProgression() {
    game.startGame(playerRed, playerBlue, 2);

    String expectedBoard1 =
            "0 1 _ _ _ 1 0\n" +
                    "0 1 _ _ _ 1 0\n" +
                    "0 1 _ _ _ 1 0\n" +
                    "Current player: RED\n" +
                    "Hand: 0: Security (Cost: 1, Value: 2), 1: Bee (Cost: 1, Value: 1), 2: Riot (Cost: 2, Value: 3)\n";

    assertEquals(expectedBoard1, view.toString());

    game.placeCardInPosition(playerRed.getHand().get(0), 0, 0);
    String expectedBoard2 =
            "2 R _ _ _ 1 0\n" +
                    "0 1 _ _ _ 1 0\n" +
                    "0 1 _ _ _ 1 0\n" +
                    "Current player: BLUE\n" +
                    "Hand: 0: Security (Cost: 1, Value: 2), 1: Bee (Cost: 1, Value: 1), 2: Riot (Cost: 2, Value: 3)\n";
    assertEquals(expectedBoard2, view.toString());

    game.placeCardInPosition(playerBlue.getHand().get(0), 2, 4);
    String expectedBoard3 =
            "2 R _ _ _ 1 0\n" +
                    "0 1 _ _ _ 1 0\n" +
                    "0 1 _ _ _ B 2\n" +
                    "Current player: RED\n" +
                    "Hand: 0: Bee (Cost: 1, Value: 1), 1: Riot (Cost: 2, Value: 3), 2: Screamer (Cost: 1, Value: 3)\n";
    assertEquals(expectedBoard3, view.toString());

    game.placeCardInPosition(playerRed.getHand().get(0), 1, 0);
    String expectedBoard4 =
            "2 R _ _ _ 1 0\n" +
                    "1 R _ _ _ 1 0\n" +
                    "0 1 _ _ _ B 2\n" +
                    "Current player: BLUE\n" +
                    "Hand: 0: Bee (Cost: 1, Value: 1), 1: Riot (Cost: 2, Value: 3), 2: Screamer (Cost: 1, Value: 3)\n";
    assertEquals(expectedBoard4, view.toString());

    game.passTurn();
    String expectedBoard5 =
            "2 R _ _ _ 1 0\n" +
                    "1 R _ _ _ 1 0\n" +
                    "0 1 _ _ _ B 2\n" +
                    "Current player: RED\n" +
                    "Hand: 0: Riot (Cost: 2, Value: 3), 1: Screamer (Cost: 1, Value: 3), 2: Devil (Cost: 3, Value: 4)\n";
    assertEquals(expectedBoard5, view.toString());
  }
}

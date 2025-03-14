package cs3500.pawnsboard.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * JUnit 4 tests for the PawnsBoard class.
 */
public class PawnsBoardTest {
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
    deck.add(new Card("ExtraCard", 1, 1, influenceGrid));


    playerRed = new Player(PlayerColor.RED, deck);
    playerBlue = new Player(PlayerColor.BLUE, deck);

    game = new PawnsBoard(3, 5);
  }

  @Test
  public void testCannotPlaceCardOnOccupiedCell() {
    game.startGame(playerRed, playerBlue, 2);
    Card cardToPlace = playerRed.getHand().get(0);
    game.placeCardInPosition(cardToPlace, 0, 0);

    try {
      game.placeCardInPosition(playerRed.getHand().get(1), 0, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid move!", e.getMessage());
    }
  }

  @Test
  public void testGameInitialization() {
    game.startGame(playerRed, playerBlue, 2);

    assertNotNull(game.getCurrentPlayer());
    assertFalse(game.isGameOver());
    assertEquals(playerRed, game.getCurrentPlayer());
  }

  @Test
  public void testStartGameTwice() {
    game.startGame(playerRed, playerBlue, 2);
    try {
      game.startGame(playerRed, playerBlue, 2);
    } catch (IllegalStateException e) {
      assertEquals("Game has already started.", e.getMessage());
    }
  }

  @Test
  public void testStartGameWithLargeHandSize() {
    try {
      game.startGame(playerRed, playerBlue, 4);
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot have a hand size greater than 1/3 deck size.", e.getMessage());
    }
  }

  @Test
  public void testPlaceCardInInvalidPosition() {
    game.startGame(playerRed, playerBlue, 2);
    try {
      game.placeCardInPosition(playerRed.getHand().get(0), -1, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid row or column.", e.getMessage());
    }
  }

  @Test
  public void testPlaceCardWithoutEnoughPawns() {
    game.startGame(playerRed, playerBlue, 2);
    try {
      game.placeCardInPosition(playerRed.getHand().get(0), 0, 2);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid move!", e.getMessage());
    }
  }

  @Test
  public void testPassTurn() {
    game.startGame(playerRed, playerBlue, 2);

    assertEquals(playerRed, game.getCurrentPlayer());
    game.passTurn();
    assertEquals(playerBlue, game.getCurrentPlayer());

    game.passTurn();
    assertEquals(playerRed, game.getCurrentPlayer());
  }

  @Test
  public void testGameOverAfterBothPlayersPass() {
    game.startGame(playerRed, playerBlue, 2);

    game.passTurn();
    game.passTurn();

    assertTrue(game.isGameOver());
  }

  @Test
  public void testApplyingInfluenceToEmptyCell() {
    game.startGame(playerRed, playerBlue, 2);
    Card influenceCard = new Card("Influencer", 1, 2, influenceGrid);

    game.placeCardInPosition(influenceCard, 0, 0);
    assertTrue(game.getBoardState()[0][1].hasPawns() || game.getBoardState()[1][0].hasPawns());
  }

  @Test
  public void testPlaceValidCard() {
    game.startGame(playerRed, playerBlue, 2);

    Card cardToPlace = playerRed.getHand().get(0);
    game.placeCardInPosition(cardToPlace, 0, 0);

    assertEquals(cardToPlace, game.getBoardState()[0][0].getCard());
    assertEquals(playerBlue, game.getCurrentPlayer());
  }

  @Test
  public void testPlaceCardInvalidPosition() {
    game.startGame(playerRed, playerBlue, 2);
    try {
      game.placeCardInPosition(playerRed.getHand().get(0), 3, 5);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid row or column.", e.getMessage());
    }
  }

  @Test
  public void testPlaceCardNotEnoughPawns() {
    game.startGame(playerRed, playerBlue, 2);
    try {
      game.placeCardInPosition(playerRed.getHand().get(1), 0, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough pawns to place this card.", e.getMessage());
    }
  }

  @Test
  public void testGetRowScore() {
    game.startGame(playerRed, playerBlue, 2);

    game.placeCardInPosition(playerRed.getHand().get(0), 0, 0);

    int[] rowScores = game.getRowScore(0);
    assertEquals(2, rowScores[0]);
    assertEquals(0, rowScores[1]);
  }

  @Test
  public void testGetRowScoreInvalidRow() {
    game.startGame(playerRed, playerBlue, 2);
    try {
      game.getRowScore(5);
    } catch (IllegalArgumentException e) {
      assertEquals("no such row.", e.getMessage());
    }
  }

  @Test
  public void testGameEndsInTie() {
    game.startGame(playerRed, playerBlue, 2);

    game.passTurn();
    game.passTurn();

    assertNull(game.getWinner());
  }

  @Test
  public void testApplyCardInfluence() {
    game.startGame(playerRed, playerBlue, 2);

    Card influenceCard = new Card("Influencer", 1, 2, new int[][]{
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
    });

    game.placeCardInPosition(influenceCard, 0, 0);

    assertTrue(game.getBoardState()[0][1].hasPawns() || game.getBoardState()[1][0].hasPawns());
  }

  @Test
  public void testCannotPlaceCardBeforeGameStarts() {
    try {
      game.placeCardInPosition(new Card("Test", 1, 1, influenceGrid), 1, 1);
    } catch (IllegalStateException e) {
      assertEquals("Game has not started. ", e.getMessage());
    }
  }

  @Test
  public void testCannotPassTurnBeforeGameStarts() {
    try {
      game.passTurn();
    } catch (IllegalStateException e) {
      assertEquals("Game has not started. ", e.getMessage());
    }
  }

  @Test
  public void testInfluenceMirroringForBluePlayer() {
    game.startGame(playerRed, playerBlue, 2);
    game.passTurn();

    Card card = playerBlue.getHand().get(0);
    game.placeCardInPosition(card, 2, 4);

    assertTrue(game.getBoardState()[1][4].hasPawns() || game.getBoardState()[2][3].hasPawns());
  }

  @Test
  public void testScoreCalculationAcrossRows() {
    game.startGame(playerRed, playerBlue, 2);
    game.placeCardInPosition(playerRed.getHand().get(0), 0, 0);
    game.placeCardInPosition(playerBlue.getHand().get(0), 2, 4);

    int[] totalScores = game.getTotalScores();
    assertTrue("Total score should be calculated correctly",
            totalScores[0] > 0 || totalScores[1] > 0);
  }

  @Test
  public void testInvalidMoveOnOpponentPawns() {
    game.startGame(playerRed, playerBlue, 2);
    game.passTurn(); // Switch to Blue

    try {
      game.placeCardInPosition(playerBlue.getHand().get(0), 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid move!", e.getMessage());
    }
  }

  @Test
  public void testPassTurnTwiceEndsGame() {
    game.startGame(playerRed, playerBlue, 2);
    game.passTurn();
    game.passTurn();
    assertTrue(game.isGameOver());
  }

  @Test
  public void testTurnOrderEnforcement() {
    game.startGame(playerRed, playerBlue, 2);
    assertEquals(playerRed, game.getCurrentPlayer());

    game.passTurn();
    assertEquals(playerBlue, game.getCurrentPlayer());

    game.passTurn();
    assertEquals(playerRed, game.getCurrentPlayer());
  }

  @Test
  public void testCardPlacementWithExactRequiredPawns() {
    game.startGame(playerRed, playerBlue, 2);

    game.placeCardInPosition(playerRed.getHand().get(1), 0, 0);
    assertEquals(playerBlue, game.getCurrentPlayer());
  }

  @Test
  public void testWinningConditionWithEqualScores() {
    game.startGame(playerRed, playerBlue, 2);
    game.placeCardInPosition(playerRed.getHand().get(0), 0, 0);
    game.placeCardInPosition(playerBlue.getHand().get(0), 2, 4);

    assertNull(game.getWinner());
  }

  @Test
  public void testPlacingMultipleCardsBoardUpdatesCorrectly() {
    game.startGame(playerRed, playerBlue, 2);

    game.placeCardInPosition(playerRed.getHand().get(0), 0, 0);
    game.placeCardInPosition(playerBlue.getHand().get(0), 2, 4);

    assertNotNull(game.getBoardState()[0][0].getCard());
    assertNotNull(game.getBoardState()[2][4].getCard());
  }

}

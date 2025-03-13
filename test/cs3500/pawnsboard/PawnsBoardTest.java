package cs3500.pawnsboard;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * JUnit tests for the PawnsBoard game logic.
 */
public class PawnsBoardTest {

  private PawnsBoard board;
  private Player playerRed;
  private Player playerBlue;
  private Card sampleCard1;
  private Card sampleCard2;

  /**
   * Sets up a new game board before each test.
   */
  @Before
  public void setUp() {
    // Sample Influence Grid (5x5)
    int[][] influenceGrid = {
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0}
    };

    // Sample cards
    sampleCard1 = new Card("Fireball", 1, 3, influenceGrid);
    sampleCard2 = new Card("Ice Shield", 1, 2, influenceGrid);

    // Sample deck
    List<Card> redDeck = Arrays.asList(sampleCard1, sampleCard2);
    List<Card> blueDeck = Arrays.asList(sampleCard1, sampleCard2);

    // Initialize players
    playerRed = new Player(PlayerColor.RED, redDeck);
    playerBlue = new Player(PlayerColor.BLUE, blueDeck);

    // Initialize game board
    board = new PawnsBoard(5, 5);
    board.startGame(playerRed, playerBlue, 2);
  }

  /**
   * Tests that the board initializes correctly.
   */
  @Test
  public void testBoardInitialization() {
    Cell[][] boardState = board.getBoardState();
    assertNotNull(boardState);
    assertEquals(5, boardState.length); // 5 rows
    assertEquals(5, boardState[0].length); // 5 columns

    // Ensure first column has red pawns
    for (int r = 0; r < 5; r++) {
      assertTrue(boardState[r][0].hasPawns());
      assertEquals(playerRed, boardState[r][0].getOwner());
    }

    // Ensure last column has blue pawns
    for (int r = 0; r < 5; r++) {
      assertTrue(boardState[r][4].hasPawns());
      assertEquals(playerBlue, boardState[r][4].getOwner());
    }
  }

  /**
   * Tests player initialization.
   */
  @Test
  public void testPlayerInitialization() {
    assertNotNull(playerRed);
    assertNotNull(playerBlue);
    assertEquals(PlayerColor.RED, playerRed.getColor());
    assertEquals(PlayerColor.BLUE, playerBlue.getColor());

    // Ensure hands are initialized
    assertEquals(2, playerRed.getHand().size());
    assertEquals(2, playerBlue.getHand().size());
  }

  /**
   * Tests turn switching between players.
   */
  @Test
  public void testTurnSwitching() {
    assertEquals(playerRed, board.getCurrentPlayer()); // Red starts

    board.nextTurn();
    assertEquals(playerBlue, board.getCurrentPlayer()); // Blue's turn

    board.nextTurn();
    assertEquals(playerRed, board.getCurrentPlayer()); // Back to Red
  }

  /**
   * Tests placing a card legally.
   */
  @Test
  public void testValidCardPlacement() {
    board.placeCardInPosition(sampleCard1, 2, 0); // Red places card in the first column
    Cell cell = board.getBoardState()[2][0];

    assertTrue(cell.hasCard());
    assertEquals(sampleCard1, cell.getCard());
    assertEquals(playerRed, cell.getOwner());
    assertEquals(0, cell.getPawnCount()); // Pawns should be removed
  }

  /**
   * Tests placing a card illegally (not enough pawns).
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidCardPlacementNotEnoughPawns() {
    board.placeCardInPosition(sampleCard2, 2, 0); // Cost 2, but only 1 pawn in cell
  }

  /**
   * Tests influence application when placing a card.
   */
  @Test
  public void testCardInfluenceApplication() {
    board.placeCardInPosition(sampleCard1, 2, 0); // Place Fireball in middle-left
    Cell[][] boardState = board.getBoardState();

    // Check if the correct cells are influenced
    assertTrue(boardState[1][0].hasPawns()); // Influence should add a pawn
    assertTrue(boardState[3][0].hasPawns());
  }

  /**
   * Tests that passing turns works correctly.
   */
  @Test
  public void testPassingTurns() {
    board.passTurn(); // Red passes
    assertEquals(playerBlue, board.getCurrentPlayer());

    board.passTurn(); // Blue passes
    assertEquals(playerRed, board.getCurrentPlayer());

    board.passTurn(); // Red passes again
    board.passTurn(); // Blue passes

    // Both players passed, game should be over
    assertTrue(board.isGameOver());
  }

  /**
   * Tests game scoring logic.
   */
  @Test
  public void testGameScoring() {
    // Ensure each player has the card in hand
    playerRed.getHand().add(sampleCard1);
    playerBlue.getHand().add(sampleCard2);

    // Red places Fireball in column 0 (valid move)
    board.placeCardInPosition(sampleCard1, 2, 0);

    // Blue places Ice Shield in column 4 (valid move)
    board.placeCardInPosition(sampleCard2, 2, 4);

    // Ensure scoring happens
    int[] scores = board.getTotalScores();
    assertTrue(scores[0] > 0 || scores[1] > 0);
  }


  /**
   * Tests that a player cannot place a card in an occupied cell.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCannotPlaceCardOnOccupiedCell() {
    board.placeCardInPosition(sampleCard1, 2, 0);
    board.placeCardInPosition(sampleCard2, 2, 0); // Should throw an error
  }

  /**
   * Tests getting the game winner.
   */
  @Test
  public void testGetWinner() {
    board.placeCardInPosition(sampleCard1, 2, 0); // Red places Fireball
    board.placeCardInPosition(sampleCard2, 3, 4); // Blue places Ice Shield

    IPlayer winner = board.getWinner();
    assertNotNull(winner); // Someone should be winning
  }
}
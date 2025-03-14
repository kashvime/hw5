package cs3500.pawnsboard.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for Internal Player Card and Cell funcionalities
 */
public class InternalTests {
  private int[][] validGrid;
  private Card card1;
  private Card card2;
  private Card card3;
  private Player playerRed;
  private Player playerBlue;
  private List<Card> validDeck;
  private List<Card> invalidDeck;

  @Before
  public void setUp() {
    validGrid = new int[5][5];

    card1 = new Card("Security", 1, 2, validGrid);
    card2 = new Card("Bee", 2, 3, validGrid);
    card3 = new Card("Riot", 3, 5, validGrid);

    validDeck = Arrays.asList(card1, card1, card2, card2, card3);

    invalidDeck = Arrays.asList(card1, card1, card1, card2, card2, card3);

    playerRed = new Player(PlayerColor.RED, validDeck);
    playerBlue = new Player(PlayerColor.BLUE, validDeck);
  }


  @Test
  public void testCardConstructor() {
    assertEquals("Security", card1.getName());
    assertEquals(1, card1.getCost());
    assertEquals(2, card1.getValue());
    assertArrayEquals(validGrid, card1.getInfluenceGrid());
  }

  @Test
  public void testGetters() {
    assertEquals("Bee", card2.getName());
    assertEquals(2, card2.getCost());
    assertEquals(3, card2.getValue());
    assertArrayEquals(validGrid, card2.getInfluenceGrid());

    assertEquals("Riot", card3.getName());
    assertEquals(3, card3.getCost());
    assertEquals(5, card3.getValue());
    assertArrayEquals(validGrid, card3.getInfluenceGrid());
  }

  @Test
  public void testValidCardCreation() {
    Card testCard = new Card("Knight", 3, 7, validGrid);
    assertNotNull(testCard);
    assertEquals("Knight", testCard.getName());
    assertEquals(3, testCard.getCost());
    assertEquals(7, testCard.getValue());
    assertArrayEquals(validGrid, testCard.getInfluenceGrid());
  }

  @Test
  public void testEqualsAndHashCode() {
    Card duplicateCard = new Card("Security", 1, 2, validGrid);
    assertTrue(card1.equals(duplicateCard));
    assertEquals(card1.hashCode(), duplicateCard.hashCode());

    Card differentCard = new Card("Different", 1, 2, validGrid);
    assertFalse(card1.equals(differentCard));
  }

  @Test
  public void testToString() {
    assertEquals("Security (Cost: 1, Value: 2)", card1.toString());
    assertEquals("Bee (Cost: 2, Value: 3)", card2.toString());
    assertEquals("Riot (Cost: 3, Value: 5)", card3.toString());
  }

  // TESTS FOR INVALID CASES
  @Test
  public void testInvalidCardName() {
    try {
      new Card(null, 1, 2, validGrid);
    } catch (IllegalArgumentException e) {
      assertEquals("Card name cannot be null or empty.", e.getMessage());
    }

    try {
      new Card("", 1, 2, validGrid);
    } catch (IllegalArgumentException e) {
      assertEquals("Card name cannot be null or empty.", e.getMessage());
    }
  }

  @Test
  public void testInvalidCardCost() {
    try {
      new Card("Invalid", 0, 2, validGrid);
    } catch (IllegalArgumentException e) {
      assertEquals("Cost must be 1, 2, or 3 pawns.", e.getMessage());
    }

    try {
      new Card("Invalid", 4, 2, validGrid);
    } catch (IllegalArgumentException e) {
      assertEquals("Cost must be 1, 2, or 3 pawns.", e.getMessage());
    }
  }

  @Test
  public void testInvalidCardValue() {
    try {
      new Card("Invalid", 1, 0, validGrid);
    } catch (IllegalArgumentException e) {
      assertEquals("Value score must be a positive integer.", e.getMessage());
    }
  }

  @Test
  public void testInvalidInfluenceGrid() {
    try {
      new Card("Invalid", 1, 2, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Influence grid must be a 5x5 array.", e.getMessage());
    }

    int[][] smallGrid = new int[4][4]; // Wrong size
    try {
      new Card("Invalid", 1, 2, smallGrid);
    } catch (IllegalArgumentException e) {
      assertEquals("Influence grid must be a 5x5 array.", e.getMessage());
    }
  }

  @Test
  public void testInfluenceGridInvalidValues() {
    int[][] grid = {{0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 9, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}};

    try {
      new Card("InvalidGrid", 2, 5, grid);
    } catch (IllegalArgumentException e) {
      assertEquals("Influence grid contains invalid values.", e.getMessage());
    }
  }

  @Test
  public void testPlayerConstructorValidDeck() {
    assertEquals(PlayerColor.RED, playerRed.getColor());
    assertEquals(5, playerRed.getDeckSize());
    assertTrue(playerRed.getHand().isEmpty());
  }

  @Test
  public void testPlayerConstructorValidDeckBluePlayer() {
    assertEquals(PlayerColor.BLUE, playerBlue.getColor());
    assertEquals(5, playerBlue.getDeckSize());
    assertTrue(playerBlue.getHand().isEmpty());
  }

  @Test
  public void testPlayerConstructorInvalidDeckMoreThanTwoDuplicates() {
    try {
      new Player(PlayerColor.RED, invalidDeck);
    } catch (IllegalArgumentException e) {
      assertEquals("Deck contains more than two of a card.", e.getMessage());
    }
  }

  @Test
  public void testPlayerConstructorWithEmptyDeck() {
    try {
      new Player(PlayerColor.RED, Collections.emptyList());
    } catch (IllegalArgumentException e) {
      assertEquals("Deck cannot be null or empty.", e.getMessage());
    }
  }

  @Test
  public void testPlayerDrawCard() {
    assertTrue(playerRed.drawCard());
    assertEquals(1, playerRed.getHand().size());
    assertEquals(4, playerRed.getDeckSize());
  }

  @Test
  public void testPlayerDrawCardUntilDeckEmpty() {
    for (int i = 0; i < 5; i++) {
      assertTrue(playerRed.drawCard());
    }
    assertFalse(playerRed.drawCard()); // Deck should be empty now
    assertTrue(playerRed.isDeckEmpty());
  }

  @Test
  public void testPlayerRemoveCardFromHand() {
    playerRed.drawCard();
    playerRed.drawCard();
    assertEquals(2, playerRed.getHand().size());

    playerRed.removeCardFromHand(card1);
    assertEquals(1, playerRed.getHand().size());
  }

  @Test
  public void testPlayerRemoveCardNotInHand() {
    try {
      playerRed.removeCardFromHand(card1);
    } catch (IllegalArgumentException e) {
      assertEquals("Attempted to remove a card that is not in hand.", e.getMessage());
    }
  }

  @Test
  public void testPlayerToString() {
    String expected = "Player{color=RED, hand=[], deckSize=5}";
    assertEquals(expected, playerRed.toString());

    playerRed.drawCard();
    assertTrue(playerRed.toString().contains("hand="));
  }
}
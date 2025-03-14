package cs3500.pawnsboard.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for Internal Player Card and Cell funcionalities
 */
public class InternalTests {
  private int[][] validGrid;
  private Card security;
  private Card bee;
  private Card riot;
  private Player playerRed;
  private Player playerBlue;
  private List<Card> validDeck;
  private List<Card> invalidDeck;
  private int[][] influenceGrid;
  private Card card;
  Card card1;
  Card card2;
  Card card3;
  Player player;


  @Before
  public void setUp() {
    validGrid = new int[5][5];
    influenceGrid = new int[5][5];

    security = new Card("Security", 1, 2, validGrid);
    bee = new Card("Bee", 2, 3, validGrid);
    riot = new Card("Riot", 3, 5, validGrid);

    validDeck = Arrays.asList(security, security, bee, bee, riot);

    invalidDeck = Arrays.asList(security, security, security, bee, bee, riot);

    playerRed = new Player(PlayerColor.RED, validDeck);
    playerBlue = new Player(PlayerColor.BLUE, validDeck);

    card1 = new Card("Card1", 1, 10, new int[5][5]);
    card2 = new Card("Card2", 1, 10, new int[5][5]);
    card3 = new Card("Card3", 1, 10, new int[5][5]);
    player = new Player(PlayerColor.RED, Arrays.asList(card1, card2));

  }


  @Test
  public void testPlayerConstructor() {
    Player player2 = new Player(PlayerColor.RED, Arrays.asList(card1, card2));
    assertEquals(PlayerColor.RED, player2.getColor());
    assertEquals(new ArrayList<>(), player2.getHand());
    assertEquals(2, player2.getDeckSize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerConstructorNull() {
    Player player2 = new Player(PlayerColor.RED, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerConstructorEmpty() {
    List<Card> empty = new ArrayList<Card>();
    Player player2 = new Player(PlayerColor.RED, empty);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerConstructorTooManyDupes() {
    Player player2 = new Player(PlayerColor.RED, Arrays.asList(card1, card1, card1));
  }

  @Test
  public void testPlayerGetColor() {
    assertEquals(PlayerColor.RED, player.getColor());
  }

  @Test
  public void testPlayerGetHand() {
    assertEquals(new ArrayList<>(), player.getHand());
    player.drawCard();
    assertEquals(Arrays.asList(card1), player.getHand());
    player.drawCard();
    assertEquals(Arrays.asList(card1, card2), player.getHand());

  }

  @Test
  public void testPlayerDrawCard1() {
    assertTrue(player.drawCard());
    assertEquals(Arrays.asList(card1), player.getHand());
    assertTrue(player.drawCard());
    assertEquals(Arrays.asList(card1, card2), player.getHand());
    assertFalse(player.drawCard());
  }

  @Test
  public void testPlayerRemoveCardFromHand1() {
    player.drawCard();
    player.drawCard();
    assertEquals(Arrays.asList(card1, card2), player.getHand());
    player.removeCardFromHand(card1);
    assertEquals(Arrays.asList(card2), player.getHand());
  }

  @Test
  public void testPlayerIsDeckEmpty() {
    assertFalse(player.isDeckEmpty());
    player.drawCard();
    player.drawCard();
    assertTrue(player.isDeckEmpty());
  }

  @Test
  public void testPlayerGetDeckSize() {
    assertEquals(2, player.getDeckSize());
    player.drawCard();
    assertEquals(1, player.getDeckSize());
    player.drawCard();
    assertEquals(0, player.getDeckSize());
  }


  @Test
  public void testCardConstructor() {
    assertEquals("Security", security.getName());
    assertEquals(1, security.getCost());
    assertEquals(2, security.getValue());
    assertArrayEquals(validGrid, security.getInfluenceGrid());
  }

  @Test
  public void testGetters() {
    assertEquals("Bee", bee.getName());
    assertEquals(2, bee.getCost());
    assertEquals(3, bee.getValue());
    assertArrayEquals(validGrid, bee.getInfluenceGrid());

    assertEquals("Riot", riot.getName());
    assertEquals(3, riot.getCost());
    assertEquals(5, riot.getValue());
    assertArrayEquals(validGrid, riot.getInfluenceGrid());
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
    assertTrue(security.equals(duplicateCard));
    assertEquals(security.hashCode(), duplicateCard.hashCode());

    Card differentCard = new Card("Different", 1, 2, validGrid);
    assertFalse(security.equals(differentCard));
  }

  @Test
  public void testToString() {
    assertEquals("Security (Cost: 1, Value: 2)", security.toString());
    assertEquals("Bee (Cost: 2, Value: 3)", bee.toString());
    assertEquals("Riot (Cost: 3, Value: 5)", riot.toString());
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

    playerRed.removeCardFromHand(security);
    assertEquals(1, playerRed.getHand().size());
  }

  @Test
  public void testPlayerRemoveCardNotInHand() {
    try {
      playerRed.removeCardFromHand(security);
    } catch (IllegalArgumentException e) {
      assertEquals("Attempted to remove a card that is not in hand.", e.getMessage());
    }
  }

  @Test
  public void testPlayerToString() {
    String expected = "PlayerRED";
    assertEquals(expected, playerRed.toString());
  }


  @Test
  public void testInvalidCardNameNull() {
    try {
      new Card(null, 2, 10, influenceGrid);
    } catch (IllegalArgumentException e) {
      assertEquals("Card name cannot be null or empty.", e.getMessage());
    }
  }

  @Test
  public void testInvalidCardNameEmpty() {
    try {
      new Card("", 2, 10, influenceGrid);
    } catch (IllegalArgumentException e) {
      assertEquals("Card name cannot be null or empty.", e.getMessage());
    }
  }

  @Test
  public void testInvalidCardCostLow() {
    try {
      new Card("Card1", 0, 10, influenceGrid);
    } catch (IllegalArgumentException e) {
      assertEquals("Cost must be 1, 2, or 3 pawns.", e.getMessage());
    }
  }

  @Test
  public void testInvalidCardCostHigh() {
    try {
      new Card("Card1", 4, 10, influenceGrid);
    } catch (IllegalArgumentException e) {
      assertEquals("Cost must be 1, 2, or 3 pawns.", e.getMessage());
    }
  }

  @Test
  public void testCardGetName() {
    card = new Card("Card1", 2, 10, influenceGrid);
    assertEquals("Card1", card.getName());
  }

  @Test
  public void testCardGetCost() {
    card = new Card("Card1", 2, 10, influenceGrid);
    assertEquals(2, card.getCost());
  }

  @Test
  public void testCardGetValue() {
    card = new Card("Card1", 2, 10, influenceGrid);
    assertEquals(10, card.getValue());
  }

  @Test
  public void testCardGetInfluenceGrid() {
    card = new Card("Card1", 2, 10, influenceGrid);
    assertArrayEquals(influenceGrid, card.getInfluenceGrid());
  }

  @Test
  public void testCardEquals() {
    card = new Card("Card1", 2, 10, influenceGrid);
    Card card2 = new Card("Card1", 2, 10, influenceGrid);
    Card card3 = new Card("Card2", 3, 15, influenceGrid);

    assertEquals(card, card); // Reflexivity
    assertEquals(card, card2); // Same properties
    assertNotEquals(null, card); // Null comparison
    assertNotEquals(card, influenceGrid); // Different type
    assertNotEquals(card, card3); // Different properties
  }

  @Test
  public void testCardToString() {
    card = new Card("Card1", 2, 10, influenceGrid);
    assertEquals("Card1 (Cost: 2, Value: 10)", card.toString());
  }

  @Test
  public void testPlayerConstructor2() {
    Player player2 = new Player(PlayerColor.RED, Arrays.asList(card1, card2));
    assertEquals(PlayerColor.RED, player2.getColor());
    assertEquals(new ArrayList<>(), player2.getHand());
    assertEquals(2, player2.getDeckSize());
  }
}

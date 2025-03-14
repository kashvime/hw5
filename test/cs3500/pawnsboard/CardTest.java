package cs3500.pawnsboard;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * JUnit tests for the Card class
 */
class CardTest {
  int[][] influenceGrid;
  Card card;
  @Before
  public void setupTest() {

  }

  @Test
  public void testCardConstructor() {
    influenceGrid = new int[5][5];
    card = new Card("Card1", 2, 10, influenceGrid);
    assertEquals("Card1", card.getName());
    assertEquals(2, card.getCost());
    assertEquals(10, card.getValue());
    assertArrayEquals(influenceGrid, card.getInfluenceGrid());
  }
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardNameNull() {
    influenceGrid = new int[5][5];
    card = new Card(null, 2, 10, influenceGrid);
    int[][] influenceGrid = new int[5][5];
    new Card(null, 2, 10, influenceGrid);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardNameEmpty() {
    influenceGrid = new int[5][5];
    card = new Card("", 2, 10, influenceGrid);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardCostLow() {
    int[][] influenceGrid = new int[5][5];
    new Card("Card1", 0, 10, influenceGrid);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardCostHigh() {
    int[][] influenceGrid = new int[5][5];
    new Card("Card1", 4, 10, influenceGrid);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardValue() {
    int[][] influenceGrid = new int[5][5];
    new Card("Card1", 2, 0, influenceGrid);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInfluenceGrid() {
    int[][] influenceGrid = new int[4][4];  // Not 5x5
    new Card("Card1", 2, 10, influenceGrid);
  }

  @Test
  public void testCardGetName() {
    influenceGrid = new int[5][5];
    card = new Card("Card1", 2, 10, influenceGrid);
    assertEquals("Card1", card.getName());
  }

  @Test
  public void testCardGetCost() {
    influenceGrid = new int[5][5];
    card = new Card("Card1", 2, 10, influenceGrid);
    assertEquals(2, card.getCost());
  }

  @Test
  public void testCardGetValue() {
    influenceGrid = new int[5][5];
    card = new Card("Card1", 2, 10, influenceGrid);
    assertEquals(10, card.getValue());
  }

  @Test
  public void testCardGetInfluenceGrid() {
    influenceGrid = new int[5][5];
    card = new Card("Card1", 2, 10, influenceGrid);
    assertEquals(influenceGrid, card.getInfluenceGrid());
  }

  @Test
  public void testCardEquals() {
    influenceGrid = new int[5][5];
    card = new Card("Card1", 2, 10, influenceGrid);
    Card card2 = new Card("Card1", 2, 10, influenceGrid);
    Card card3 = new Card("Card2", 3, 15, influenceGrid);

    assertEquals(true, card.equals(card));
    assertEquals(true, card.equals(card2));
    assertEquals(false, card.equals(null));
    assertEquals(false, card.equals(influenceGrid));
    assertEquals(false, card.equals(card3));
  }

  @Test
  public void testCardToString() {
    influenceGrid = new int[5][5];
    card = new Card("Card1", 2, 10, influenceGrid);
    assertEquals("Card1 (Cost: 2, Value: 10)", card.toString());
  }
}
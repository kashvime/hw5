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
}
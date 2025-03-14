package cs3500.pawnsboard;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 * JUnit tests for the Cell class
 */
class CellTest {
  int[][] influenceGrid;
  Card card;
  Player red;
  Player blue;
  Cell cell;
  List<Card> deck;
  @Before
  public void setupTest() {
    influenceGrid = new int[5][5];
    card = new Card("Card1", 2, 10, influenceGrid);
    deck = new ArrayList<Card>();
    deck.add(card);
    red = new Player(PlayerColor.RED, deck);
    blue = new Player(PlayerColor.BLUE, deck);
  }

  @Test
  public void testCellInitialization() {
    cell = new Cell();
    assertTrue(cell.isEmpty());
    assertFalse(cell.hasCard());
    assertFalse(cell.hasPawns());
    assertEquals(0, cell.getPawnCount());
    assertNull(cell.getCard());
    assertNull(cell.getOwner());
  }

  @Test
  public void testCellIsEmpty() {
    cell = new Cell();
    assertTrue(cell.isEmpty());
  }

  @Test
  public void testCellHasCard() {
    cell = new Cell();

  }

  @Test
  public void testCellHasPawns() {
    cell = new Cell();
    assertFalse(cell.hasPawns());
    cell.addPawn(red);
    assertTrue(cell.hasPawns());
  }

  @Test
  public void testCellGetPawnCount() {
    cell = new Cell();
    assertEquals(0, cell.getPawnCount());
    cell.addPawn(red);
    assertEquals(1, cell.getPawnCount());
    cell.addPawn(red);
    assertEquals(2, cell.getPawnCount());
  }

  @Test
  public void testCellGetCard() {
    cell = new Cell();
    assertEquals(0, cell.getPawnCount());
    cell.addPawn(red);
    assertEquals(1, cell.getPawnCount());
    cell.addPawn(red);
    assertEquals(2, cell.getPawnCount());
  }

  @Test
  public void testCellGetOwner() {
    cell = new Cell();
    assertNull(cell.getOwner());
    cell.addPawn(red);
    assertEquals(red, cell.getOwner());
  }


  @Test
  public void testAddPawn() {
    cell = new Cell();
    cell.addPawn(red);

    assertTrue(cell.hasPawns());
    assertEquals(1, cell.getPawnCount());
    assertEquals(red, cell.getOwner());

    cell.addPawn(red);
    assertEquals(2, cell.getPawnCount());
    cell.addPawn(red);
    assertEquals(3, cell.getPawnCount());
    cell.addPawn(red);
    assertEquals(3, cell.getPawnCount());
    cell.addPawn(blue);
    assertEquals(blue, cell.getOwner());
  }

  @Test(expected = IllegalStateException.class)
  public void testAddPawnToCardCell() {
    cell = new Cell();
    cell.placeCard(card, red);
    cell.addPawn(red);
  }

  @Test
  public void testApplyInfluence() {
    cell = new Cell();
    cell.applyInfluence(red);

    assertTrue(cell.hasPawns());
    assertEquals(1, cell.getPawnCount());
    assertEquals(red, cell.getOwner());

    cell.applyInfluence(red);
    assertEquals(2, cell.getPawnCount());

    cell.applyInfluence(red);
    assertEquals(3, cell.getPawnCount());

    cell.applyInfluence(red);
    assertEquals(3, cell.getPawnCount());

    cell.applyInfluence(blue);
    assertEquals(blue, cell.getOwner());

    cell.placeCard(card, blue);
    assertEquals(blue, cell.getOwner());
    assertEquals(0, cell.getPawnCount());
    cell.applyInfluence(red);
    assertEquals(blue, cell.getOwner());
    assertEquals(0, cell.getPawnCount());
  }

  @Test
  public void testPlaceCard() {
    cell = new Cell();
    cell.placeCard(card, red);

    assertTrue(cell.hasCard());
    assertEquals(card, cell.getCard());
    assertEquals(red, cell.getOwner());
    assertEquals(0, cell.getPawnCount());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardHasCard() {
    cell = new Cell();
    cell.placeCard(card, red);
    cell.placeCard(card, blue);
  }

  @Test
  public void testCellClear() {
    cell = new Cell();
    cell.addPawn(red);
    cell.placeCard(card, red);
    cell.clear();

    assertNull(cell.getCard());
    assertNull(cell.getOwner());
    assertEquals(0, cell.getPawnCount());
  }

  @Test
  public void testRemoveAllPawns() {
    cell = new Cell();
    cell.addPawn(red);
    assertTrue(cell.hasPawns());

    cell.removeAllPawns();
    assertFalse(cell.hasPawns());
    assertEquals(0, cell.getPawnCount());
    assertEquals(red, cell.getOwner());
  }

  @Test
  public void testCellToString() {
    cell = new Cell();
    assertEquals("_", cell.toString());
    cell.addPawn(red);
    assertEquals("1", cell.toString());
    cell.placeCard(card, red);
    assertEquals("Red", cell.toString());
  }
}
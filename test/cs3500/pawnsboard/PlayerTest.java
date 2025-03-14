package cs3500.pawnsboard;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

class PlayerTest {
  Card card1;
  Card card2;
  Card card3;
  Player player;
  @Before
  public void setupTest() {
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
  public void testPlayerDrawCard() {
    assertTrue(player.drawCard());
    assertEquals(Arrays.asList(card1), player.getHand());
    assertTrue(player.drawCard());
    assertEquals(Arrays.asList(card1, card2), player.getHand());
    assertFalse(player.drawCard());
  }

  @Test
  public void testPlayerRemoveCardFromHand() {
    player.drawCard();
    player.drawCard();
    assertEquals(Arrays.asList(card1, card2), player.getHand());
    player.removeCardFromHand(card1);
    assertEquals(Arrays.asList(card2), player.getHand());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerRemoveCardFromHandNoCard() {
    player.drawCard();
    player.drawCard();
    player.removeCardFromHand(card3);
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
  public void testPlayerToString() {
    assertEquals("Player{" + "color=" + PlayerColor.RED + ", hand=" + new ArrayList<>() + ", deckSize=" + 2 + '}', player.toString());
  }
}
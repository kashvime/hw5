package cs3500.pawnsboard.view;

import java.io.IOException;
import java.util.List;

import cs3500.pawnsboard.model.Card;
import cs3500.pawnsboard.model.Cell;
import cs3500.pawnsboard.model.IPlayer;
import cs3500.pawnsboard.model.PawnsGame;
import cs3500.pawnsboard.model.PlayerColor;

/**
 * A textual view for the Pawns Board game.
 * Displays the board state with row scores.
 */
public class TextualView implements ITextualView {
  private final PawnsGame model;

  /**
   * Constructs a textual view of the given PawnsBoard model.
   *
   * @param model the game model to visualize
   */
  public TextualView(PawnsGame model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  @Override
  public void render(Appendable output) throws IOException {
    if (output == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }
    output.append(this.toString()).append("\n");
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    renderBoard(sb);
    return sb.toString();
  }


  /**
   * Renders the board along with row scores.
   */
  private void renderBoard(StringBuilder sb) {
    Cell[][] board = model.getBoardState();

    for (int row = 0; row < board.length; row++) {
      int[] rowScore = model.getRowScore(row);

      sb.append(rowScore[0]).append(" ");

      for (int col = 0; col < board[0].length; col++) {
        sb.append(formatCell(board[row][col])).append(" ");
      }

      sb.append(rowScore[1]).append("\n");
    }

    IPlayer currentPlayer = model.getCurrentPlayer();
    sb.append("Current player: ").append(currentPlayer.getColor()).append("\n");
    sb.append("Hand: ").append(formatHand(currentPlayer.getHand())).append("\n");
  }

  /**
   * Formats the player's hand into a readable string.
   *
   * @param hand the player's hand of cards
   * @return a formatted string of the hand
   */
  private String formatHand(List<Card> hand) {
    if (hand.isEmpty()) {
      return "No cards available.";
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < hand.size(); i++) {
      sb.append(i).append(": ").append(hand.get(i).toString());
      if (i < hand.size() - 1) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  /**
   * Formats a single cell based on game rules.
   *
   * @param cell the cell to format
   * @return a string representation of the cell
   */
  private String formatCell(Cell cell) {
    if (cell.hasCard()) {
      return (cell.getOwner().getColor() == PlayerColor.RED) ? "R" : "B";
    } else if (cell.hasPawns()) {
      return String.valueOf(cell.getPawnCount());
    } else {
      return "_";
    }
  }
}
package cs3500.pawnsboard.model;

/**
 * Implements the game logic for the Pawns Board game.
 * Manages turn order, board state, scoring, and rule enforcement.
 */
public class PawnsBoard implements PawnsGame {
  private final Cell[][] board;
  private Player playerRed;
  private Player playerBlue;
  private Player currentPlayer;
  private boolean redPassed;
  private boolean bluePassed;
  private boolean gameStarted;

  /**
   * Constructs the game with a board of specified dimensions.
   * INVARIANT: rows > 0 && columns > 1 && columns % 2 == 1.
   * Board Dimensions are always ensured to be following these tules.
   *
   * @param rows    The number of rows for the board.
   * @param columns The number of columns for the board (must be odd).
   * @throws IllegalArgumentException If board dimensionas are invalid at consturction.
   */
  public PawnsBoard(int rows, int columns) {
    if (rows <= 0 || columns <= 1 || columns % 2 == 0) {
      throw new IllegalArgumentException("Invalid board dimensions.");
    }
    this.board = new Cell[rows][columns];
    this.gameStarted = false;
  }

  /**
   * Starts the game by initializing the players, board, and dealing cards.
   *
   * @param playerRed  The red player.
   * @param playerBlue The blue player.
   * @param handSize   The number of starting cards for each player.
   */

  // INVARIANT: The game can only be started once (gameStarted == true
  // after this method and can not be switched back until game is over).
  @Override
  public void startGame(Player playerRed, Player playerBlue, int handSize) {
    if (gameStarted) {
      throw new IllegalStateException("Game has already started.");
    }

    this.playerRed = playerRed;
    this.playerBlue = playerBlue;
    this.currentPlayer = playerRed;
    this.redPassed = false;
    this.bluePassed = false;

    // Check if handSize is less than 1/3 of deck size.
    if (handSize > this.playerRed.getDeckSize() / 3 || handSize > this.playerBlue.getDeckSize() / 3) {
      throw new IllegalArgumentException("Cannot have a hand size greater than 1/3 deck size.");
    }

    initializeBoard();
    dealStartingHands(handSize);

    this.gameStarted = true;

    if (!currentPlayer.isDeckEmpty()) {
      currentPlayer.drawCard();
    }
  }

  /**
   * Initializes the board with empty cells and places the starting pawns.
   */

  private void initializeBoard() {
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board[0].length; c++) {
        board[r][c] = new Cell();
      }
    }
    for (int r = 0; r < board.length; r++) {
      board[r][0].addPawn(playerRed);
      board[r][board[0].length - 1].addPawn(playerBlue);
    }
  }

  /**
   * Deals the initial hand of cards to both players.
   */
  private void dealStartingHands(int handSize) {
    for (int i = 0; i < handSize; i++) {
      playerRed.drawCard();
      playerBlue.drawCard();
    }
  }

  @Override
  public boolean isGameOver() {
    return redPassed && bluePassed;
  }

  @Override
  public IPlayer getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Moves to the next player's turn.
   */
  private void nextTurn() {
    currentPlayer = (currentPlayer == playerRed) ? playerBlue : playerRed;
    if (!currentPlayer.isDeckEmpty()) {
      currentPlayer.drawCard();
    }
  }


  @Override
  public void placeCardInPosition(Card card, int row, int col) {
    validateGameStarted();
    if (!isValidPosition(row, col)) {
      throw new IllegalArgumentException("Invalid row or column.");
    }
    Cell cell = board[row][col];

    if (!isValidMove(card, row, col)) {
      throw new IllegalArgumentException("Invalid move!");
    }

    if (cell.getPawnCount() < card.getCost()) {
      throw new IllegalStateException("Not enough pawns to place this card.");
    }

    cell.removeAllPawns();
    cell.placeCard(card, currentPlayer);

    applyCardInfluence(card, row, col);

    currentPlayer.removeCardFromHand(card);

    redPassed = false;
    bluePassed = false;
    nextTurn();
  }

  @Override
  public void passTurn() {
    validateGameStarted();

    if (currentPlayer == playerRed) {
      redPassed = true;
    } else {
      bluePassed = true;
    }
    nextTurn();

  }


  @Override
  public Cell[][] getBoardState() {
    return board;
  }

  @Override
  public int[] getRowScore(int row) {
    validateGameStarted();
    if (!isValidPosition(row, 0)) {
      throw new IllegalArgumentException("no such row.");
    }
    int redScore = 0, blueScore = 0;

    for (int col = 0; col < board[0].length; col++) {
      Card card = board[row][col].getCard();
      if (card != null) {
        Player owner = board[row][col].getOwner();
        if (owner == playerRed) {
          redScore += card.getValue();
        } else if (owner == playerBlue) {
          blueScore += card.getValue();
        }
      }
    }

    return new int[]{redScore, blueScore};
  }


  private int[][] mirrorInfluenceGrid(int[][] grid) {
    int[][] mirroredGrid = new int[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        mirroredGrid[i][4 - j] = grid[i][j]; // Flip horizontally
      }
    }
    return mirroredGrid;
  }

  /**
   * Returns the total scores for both players.
   *
   * @return index 0 is red's score and index 1 is blue's score.
   */
  protected int[] getTotalScores() {
    int redTotal = 0, blueTotal = 0;

    for (int r = 0; r < board.length; r++) {
      int[] rowScores = getRowScore(r);
      int redRowScore = rowScores[0];
      int blueRowScore = rowScores[1];

      if (redRowScore > blueRowScore) {
        redTotal += redRowScore;
      } else if (blueRowScore > redRowScore) {
        blueTotal += blueRowScore;
      }
    }

    return new int[]{redTotal, blueTotal};
  }


  @Override
  public IPlayer getWinner() {
    int[] scores = getTotalScores();
    int redScore = scores[0];
    int blueScore = scores[1];

    if (redScore > blueScore) {
      return playerRed;
    } else if (blueScore > redScore) {
      return playerBlue;
    } else {
      return null;
    }
  }

  /**
   * Applies a card's influence on the board based on its influence grid.
   */
  private void applyCardInfluence(Card card, int row, int col) {
    int[][] influenceGrid = card.getInfluenceGrid();
    if (currentPlayer == playerBlue) {
      influenceGrid = mirrorInfluenceGrid(influenceGrid);
    }

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (influenceGrid[i][j] == 1) {
          int rowOffset = i - 2;
          int colOffset = j - 2;
          int affectedRow = row + rowOffset;
          int affectedCol = col + colOffset;

          if (isValidPosition(affectedRow, affectedCol)) {
            Cell affectedCell = board[affectedRow][affectedCol];

            if (affectedCell.isEmpty()) {
              affectedCell.addPawn(currentPlayer);
            } else if (!affectedCell.getOwner().equals(currentPlayer)) {
              affectedCell.convertPawns(currentPlayer);
            }
          }
        }
      }
    }
  }

  /**
   * Ensures the game has started before performing operations.
   */
  private void validateGameStarted() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started. ");
    }
  }


  /**
   * Checks if the board position is within valid bounds.
   */
  private boolean isValidPosition(int row, int col) {
    return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
  }


  /**
   * Checks if placing a given card at the given position is a valid move.
   *
   * @param card card to be placed.
   * @param row  row index
   * @param col  column index
   * @return true if the move is valid, false otherwise.
   */
  private boolean isValidMove(Card card, int row, int col) {
    Cell cell = board[row][col];

    if (cell.hasCard()) {
      System.out.println("Cell already has a card!");
      return false;
    }
    if (cell.isEmpty()) {
      System.out.println("Cell is empty!");
      return false;
    }
    if (!cell.getOwner().equals(currentPlayer)) {
      System.out.println("Cell is owned by the opponent!");
      return false;
    }
    if (cell.getPawnCount() < card.getCost()) {
      System.out.println("Not enough pawns to place this card!");
      return false;
    }
    return true;
  }
}

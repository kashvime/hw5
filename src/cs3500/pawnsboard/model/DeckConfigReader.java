package cs3500.pawnsboard.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Reads deck configuration files and loads them into a list of cards.
 */
public class DeckConfigReader {

  /**
   * Loads a deck from a configuration file.
   *
   * @param filePath the path to the deck configuration file
   * @return a list of Cards
   * @throws FileNotFoundException    if the file cannot be found
   * @throws IllegalArgumentException if the file format is incorrect
   */
  public static List<Card> loadDeck(String filePath) throws FileNotFoundException {
    File file = new File(filePath);
    Scanner scanner = new Scanner(file);

    List<Card> deck = new ArrayList<>();

    while (scanner.hasNextLine()) {
      // Read the first line (Card Name, Cost, Value)
      if (!scanner.hasNext()) {
        throw new IllegalArgumentException("Invalid card format: Missing card name.");
      }
      String name = scanner.next();

      if (!scanner.hasNextInt()) {
        throw new IllegalArgumentException("Invalid card format: Missing cost.");
      }
      int cost = scanner.nextInt();

      if (!scanner.hasNextInt()) {
        throw new IllegalArgumentException("Invalid card format: Missing value.");
      }
      int value = scanner.nextInt();
      scanner.nextLine(); // Move to next line

      // Validate cost and value
      if (cost < 1 || cost > 3) {
        throw new IllegalArgumentException("Invalid card format: Cost must be 1, 2, or 3.");
      }
      if (value <= 0) {
        throw new IllegalArgumentException("Invalid card format: Value must be a positive integer.");
      }

      // Read the 5x5 influence grid
      int[][] influenceGrid = new int[5][5];
      for (int i = 0; i < 5; i++) {
        if (!scanner.hasNextLine()) {
          throw new IllegalArgumentException("Invalid card format: Missing grid row.");
        }
        String row = scanner.nextLine().trim();

        if (row.length() != 5) {
          throw new IllegalArgumentException("Invalid card format: Grid row must have exactly 5 characters.");
        }

        for (int j = 0; j < 5; j++) {
          char ch = row.charAt(j);
          switch (ch) {
            case 'X':
              influenceGrid[i][j] = 0;
              break;
            case 'I':
              influenceGrid[i][j] = 1;
              break;
            case 'C':
              if (i != 2 || j != 2) {
                throw new IllegalArgumentException("Invalid card format: 'C' must be in the center of the grid.");
              }
              influenceGrid[i][j] = 2;
              break;
            default:
              throw new IllegalArgumentException("Invalid card format: Grid contains invalid character '" + ch + "'.");
          }
        }
      }

      // Add card to the deck
      deck.add(new Card(name, cost, value, influenceGrid));
    }

    scanner.close();
    return deck;
  }
}

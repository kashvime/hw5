# Queen's Blood HW5
This is a project meant to recreate a variant of the game Queen's Blood.  
The Cards in the game along with all of their functions are represented by the ICard interface, and the Card file which implements it. These files allow for the saving and retrieving of various parameters of a card such as cost and value.  
The Board of the game is represented is cells, which we have as the ICell interface and Cell. This represents the state of the game through number of pawns, card placement, and cell ownership.   
The Player and IPlayer files represent the player themselves, saving their color (Shown in PlayerColor.java) along with hand and deck of Cards.  
The aforementioned deck is obtained from an external file, which is read by the DeckConfigReader file, which reads the config file and converts it into a usable list of cards.  
The Gamestate itself is represented by the PawnsGame interface an PawnsBoard.java. These files include all the functionality of the game itself, such as turning the individudal cells into a board, allowing for dealing cards, passing turns, calculating score, and starting/ending the game.  
The game is displayed using the TextualView file and its accompanying interface. This allows for the current state of the game to be represented view text.  
The PawnsBoardGame file on the same level as the model and view packages is to demonstrate the functionality of our code, containing a main method that does the following: Read in a deck config file, start a game with a 3x5 board with players using the read deck, and play the game until no more cards may be placed.
The invariants (rows > 0 && columns > 1 && columns % 2 == 1 and "A cell can never have more than 3 pawns") are properly enforced because the board dimensions are immutable after initialization (getBoardState returns a copy) , while pawn additions are strictly controlled in addPawn().

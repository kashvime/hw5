# hw5
This is a project meant to recreate a variant of the game Queen's Blood. 
The Cards in the game along with all of their functions are represented by the ICard interface, and the Card file which implements it. These files allow for the saving and retrieving of various parameters of a card such as cost and value.
The Board of the game is represented is cells, which we have as the ICell interface and Cell. This represents the state of the game through number of pawns, card placement, and cell ownership. 
The Player and IPlayer files represent the player themselves, saving their color (Shown in PlayerColor.java) along with hand and deck of Cards.
The Gamestate itself is represented by the PawnsGame interface an PawnsBoard.java. These files include all the functionality of the game itself, such as turning the individudal cells into a board, allowing for dealing cards, passing turns, calculating score, and starting/ending the game.

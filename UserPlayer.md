To instantiate a user-player in the future, 
we could add a user-input method for both the player and an algorithm for an AI opponent, 
allowing for selection of cards and placement depending on board state. 
We could implement this as an implementation of the IPlayer interface and integrate
it with PawnsGame so that the various player actions could be done through it. 
The players themselves could be instantiated at the beginning of each game.
Feature: American Checkers

  Background: 
    Given the "American Checkers" game is set up

  Scenario: Start of the Game
    When the players start the game
    Then the player with the dark-colored pieces is given the turn

  Scenario Outline: Valid Regular Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples: 
      | file_name         |
      | validRegularMove1 |
      | validRegularMove2 |
      | validRegularMove3 |
      | validRegularMove4 |
      | validRegularMove5 |
      | validRegularMove6 |

  Scenario Outline: Valid Jump Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "two" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name       | next_turn_player |
      | validJumpMove1  | other            |
      | validJumpMove2  | other            |
      | validJumpMove3  | other            |
      | validJumpMove4  | current          |
      | validJumpMove5  | current          |
      | validJumpMove6  | other            |
      | validJumpMove7  | current          |
      | validJumpMove8  | other            |
      | validJumpMove9  | other            |
      | validJumpMove10 | other            |
      | validJumpMove11 | other            |
      | validJumpMove12 | other            |
      | validJumpMove13 | other            |
      | validJumpMove14 | other            |
      | validJumpMove15 | other            |
      | validJumpMove16 | current          |
      | validJumpMove17 | other            |

  Scenario Outline: Invalid Source Coordinate for Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                      | invalidity_reason                              | error_message                           |
      | invalidSourceCoordinateForMoveOutsideBorders1  | source coordinate is outside of the board      | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveOutsideBorders2  | source coordinate is outside of the board      | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveUnplayableColor1 | source coordinate is not of valid square color | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveUnplayableColor2 | source coordinate is not of valid square color | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveEmpty1           | source coordinate is empty                     | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveEmpty2           | source coordinate is empty                     | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveEmpty3           | source coordinate is empty                     | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveOpponentsPiece1  | source coordinate has opponent's piece         | Piece does not belong to current player |
      | invalidSourceCoordinateForMoveOpponentsPiece2  | source coordinate has opponent's piece         | Piece does not belong to current player |
      | invalidSourceCoordinateForMoveOpponentsPiece3  | source coordinate has opponent's piece         | Piece does not belong to current player |

  Scenario Outline: Invalid Destination Coordinate for Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                                 | invalidity_reason                                                 | error_message                                                               |
      | invalidDestinationCoordinateForMoveOutsideBorders1        | destination coordinate is outside of the board                    | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveOutsideBorders2        | destination coordinate is outside of the board                    | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveUnplayableColor1       | destination coordinate is not of valid square color               | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveUnplayableColor2       | destination coordinate is not of valid square color               | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveOccupied1              | destination coordinate is occupied                                | A piece at destination coordinate                                           |
      | invalidDestinationCoordinateForMoveOccupied2              | destination coordinate is occupied                                | A piece at destination coordinate                                           |
      | invalidDestinationCoordinateForMoveOccupied3              | destination coordinate is occupied                                | A piece at destination coordinate                                           |
      | invalidDestinationCoordinateForMoveUnallowedDirection1    | destination coordinate's direction is not allowed                 | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveUnallowedDirection2    | destination coordinate's direction is not allowed                 | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveUnallowedDirection3    | destination coordinate's direction is not allowed                 | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveTooFarAway1            | destination coordinate is more than two squares away              | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveTooFarAway2            | destination coordinate is more than two squares away              | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves1  | move is not a jump move even though there are possible jump moves | If any opponent's pieces can be captured then it must be captured first!!!! |
      | invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves2  | move is not a jump move even though there are possible jump moves | If any opponent's pieces can be captured then it must be captured first!!!! |
      | invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves3  | move is not a jump move even though there are possible jump moves | If any opponent's pieces can be captured then it must be captured first!!!! |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1     | jumped piece is null                                              | There must be one piece on jump path 0                                      |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | jumped piece is not opponent piece                                | Jumped Piece Must Be Opponent Piece                                         |

  Scenario Outline: Crowning the Eligible Piece
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a pawn piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece at the source coordinate becomes a crowned piece
    And the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples: 
      | file_name                 |
      | crowningTheEligiblePiece1 |
      | crowningTheEligiblePiece2 |
      | crowningTheEligiblePiece3 |
      | crowningTheEligiblePiece4 |
      | crowningTheEligiblePiece5 |
      | crowningTheEligiblePiece6 |
      | crowningTheEligiblePiece7 |
      | crowningTheEligiblePiece8 |

  Scenario Outline: End of the Game
    Given the game is played up to a certain point from file "<file_name>"
    And only one piece of the opponent is present at the game board
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples: 
      | file_name     |
      | endOfTheGame1 |
      | endOfTheGame2 |

  Scenario Outline: End of the Game In Draw
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples: 
      | file_name           | offer_response | result                                     |
      | endOfTheGameInDraw1 | accepts        | the game is ended as a draw                |
      | endOfTheGameInDraw2 | rejects        | the next turn is given to the other player |
      | endOfTheGameInDraw3 | accepts        | the game is ended as a draw                |
      | endOfTheGameInDraw4 | accepts        | the game is ended as a draw                |
      | endOfTheGameInDraw5 | rejects        | the next turn is given to the other player |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece
    Given the game is played up to a certain point from file "<file_name>"
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples: 
      | file_name                                  |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 39
    When the player makes a regular move without promoting
    Then the game is ended as a draw

    Examples: 
      | file_name                               |
      | endOfTheGameInDrawFortyIndecisiveMoves1 |
      | endOfTheGameInDrawFortyIndecisiveMoves2 |
      | endOfTheGameInDrawFortyIndecisiveMoves3 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples: 
      | file_name                               |
      | endOfTheGameOpponentCantMakeAValidMove1 |
      | endOfTheGameOpponentCantMakeAValidMove2 |

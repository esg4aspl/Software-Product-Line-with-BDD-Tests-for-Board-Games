Feature: Chinese Checkers

  #TODO Test Start of the Game
  Scenario Outline: Valid Regular Move
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples: 
      | file_name         | number_of_players | explanation                                                                    |
      | validRegularMove1 |                 2 | f: regular move                                                                |
      | validRegularMove2 |                 2 | f: pawn does not capture even though there is a possibility and goes backwards |
      | validRegularMove3 |                 2 | f: opponent is not blocked, his pawn can jump, game should not end             |

  Scenario Outline: Valid Jump Move
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate that is "two" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name      | number_of_players | next_turn_player | explanation                                                                                     |
      | validJumpMove1 |                 2 | next             | f: end of jump possibilities, no adjacent piece                                                 |
      | validJumpMove2 |                 2 | asked            | f: jumping own piece, another jump possibility                                                  |
      | validJumpMove3 |                 2 | next             | f: end of jump possibilities, piece is not jumpable because destination would be out of borders |
      | validJumpMove4 |                 2 | asked            | s: another jump possibility                                                                     |
      | validJumpMove5 |                 2 | next             | f: this valid jump move proves the case in validRegularMove3                                    |
      | validJumpMove6 |                 2 | next             | f: end of jump possibilities, piece is not jumpable because possible destination is occupied    |

  Scenario Outline: Player Chooses to Continue or Not
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    Given the game is played up to a certain point from file "<file_name>"
    And the player has made jump moves in the current turn
    And the player can continue doing jump moves
    And the player has been asked to continue or not
    When the player chooses to "<decision>"
    Then the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name                     | decision  | next_turn_player |
      | playerChoosesToContinueOrNot1 | continue  | current          |
      | playerChoosesToContinueOrNot2 | stop      | next             |

  Scenario Outline: Invalid Source Coordinate for Move
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
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
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                                 | invalidity_reason                                    | error_message                          |
      | invalidDestinationCoordinateForMoveOutsideBorders1        | destination coordinate is outside of the board       | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveOutsideBorders2        | destination coordinate is outside of the board       | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveUnplayableColor1       | destination coordinate is not of valid square color  | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveUnplayableColor2       | destination coordinate is not of valid square color  | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveOccupied1              | destination coordinate is occupied                   | A piece at destination coordinate      |
      | invalidDestinationCoordinateForMoveOccupied2              | destination coordinate is occupied                   | A piece at destination coordinate      |
      | invalidDestinationCoordinateForMoveOccupied3              | destination coordinate is occupied                   | A piece at destination coordinate      |
      | invalidDestinationCoordinateForMoveUnallowedDirection1    | destination coordinate's direction is not allowed    | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveUnallowedDirection2    | destination coordinate's direction is not allowed    | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveUnallowedDirection3    | destination coordinate's direction is not allowed    | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveTooFarAway1            | destination coordinate is more than two squares away | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveTooFarAway2            | destination coordinate is more than two squares away | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1     | jumped piece is null                                 | There must be one piece on jump path 0 |
      | invalidDestinationCoordinateForMoveCantLeaveGoalTriangle1 | piece can not leave goal triangle                    |                                        |

  Scenario Outline: End of the Game
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    Given the game is played up to a certain point from file "<file_name>"
    And the player has at least one of his pieces in the goal triangle
    And there is only one square is available at the goal triangle
    When the player jumps moves one of his pieces to the last available square in goal triangle
    Then the player wins the game

    Examples: 
      | file_name     |
      | endOfTheGame1 |
      | endOfTheGame2 |

  Scenario Outline: End of the Game In Draw
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
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

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game

    Examples: 
      | file_name                               |
      | endOfTheGameOpponentCantMakeAValidMove1 |
      | endOfTheGameOpponentCantMakeAValidMove2 |

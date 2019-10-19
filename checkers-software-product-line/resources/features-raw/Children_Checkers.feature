Feature: Children Checkers

  Background: 
    Given the "Children Checkers" game is set up

  Scenario: Start of the Game
    When the players start the game
    Then the player with the dark-colored pieces is given the turn

  Scenario Outline: Valid Regular Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples: 
      | file_name         | piece_type |
      | validRegularMove1 | pawn       |
      | validRegularMove2 | pawn       |
      | validRegularMove3 | pawn       |
      | validRegularMove4 | pawn       |
      | validRegularMove5 | pawn       |
      | validRegularMove6 | pawn       |

  Scenario Outline: Valid Jump Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "two" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name       | next_turn_player | piece_type |
      | validJumpMove1  | other            | pawn       |
      | validJumpMove2  | other            | pawn       |
      | validJumpMove3  | other            | pawn       |
      | validJumpMove4  | current          | pawn       |
      | validJumpMove5  | current          | pawn       |
      | validJumpMove6  | other            | pawn       |
      | validJumpMove7  | current          | pawn       |
      | validJumpMove8  | other            | pawn       |
      | validJumpMove9  | other            | pawn       |
      | validJumpMove10 | other            | pawn       |
      | validJumpMove11 | other            | pawn       |
      | validJumpMove12 | other            | pawn       |
      | validJumpMove13 | other            | pawn       |
      | validJumpMove14 | other            | pawn       |
      | validJumpMove15 | other            | pawn       |
      | validJumpMove17 | other            | pawn       |

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
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                                 | piece_type | invalidity_reason                                    | error_message                          |
      | invalidDestinationCoordinateForMoveOutsideBorders1        | pawn       | destination coordinate is outside of the board       | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveOutsideBorders2        | pawn       | destination coordinate is outside of the board       | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveUnplayableColor1       | pawn       | destination coordinate is not of valid square color  | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveUnplayableColor2       | pawn       | destination coordinate is not of valid square color  | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveOccupied1              | pawn       | destination coordinate is occupied                   | A piece at destination coordinate      |
      | invalidDestinationCoordinateForMoveOccupied2              | pawn       | destination coordinate is occupied                   | A piece at destination coordinate      |
      | invalidDestinationCoordinateForMoveOccupied3              | pawn       | destination coordinate is occupied                   | A piece at destination coordinate      |
      | invalidDestinationCoordinateForMoveUnallowedDirection1    | pawn       | destination coordinate's direction is not allowed    | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveUnallowedDirection2    | pawn       | destination coordinate's direction is not allowed    | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveUnallowedDirection3    | pawn       | destination coordinate's direction is not allowed    | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveTooFarAway1            | pawn       | destination coordinate is more than two squares away | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveTooFarAway2            | pawn       | destination coordinate is more than two squares away | Destination Valid? false               |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1     | pawn       | jumped piece is null                                 | There must be one piece on jump path 0 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | pawn       | jumped piece is not opponent piece                   | Jumped Piece Must Be Opponent Piece    |

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

  Scenario Outline: End of the Game - Reaching the Crownhead
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the opponent loses the game
    And the player wins the game

    Examples: 
      | file_name                 | piece_type |
      | crowningTheEligiblePiece1 | pawn       |
      | crowningTheEligiblePiece2 | pawn       |
      | crowningTheEligiblePiece3 | pawn       |
      | crowningTheEligiblePiece4 | pawn       |
      | crowningTheEligiblePiece5 | pawn       |
      | crowningTheEligiblePiece6 | pawn       |
      | crowningTheEligiblePiece7 | pawn       |
      | crowningTheEligiblePiece8 | pawn       |
      | crowningTheEligiblePiece9 | pawn       |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples: 
      | file_name                               |
      | endOfTheGameOpponentCantMakeAValidMove1 |
      | endOfTheGameOpponentCantMakeAValidMove2 |

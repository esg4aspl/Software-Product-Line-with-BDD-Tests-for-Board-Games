Feature: Chinese Checkers

  #TODO Test Start of the Game
  Scenario Outline: Valid Regular Move
  	Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples: 
      | file_name         | number_of_players | explanation                                                                    |
      | validRegularMove1 |                 2 | f: regular move                                                                |
      | validRegularMove2 |                 2 | f: pawn does not capture even though there is a possibility and goes backwards |
      | validRegularMove3 |                 2 | f: opponent is not blocked, his pawn can jump, game should not end             |

  Scenario Outline: Valid Jump Move
  	Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid capture" move
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name      | number_of_players | next_turn_player | explanation                                                                                     |
      | validJumpMove1 |                 2 | next ingame      | f: end of jump possibilities, no adjacent piece                                                 |
      | validJumpMove2 |                 2 | current          | f: jumping own piece, another jump possibility                                                  |
      | validJumpMove3 |                 2 | next ingame      | f: end of jump possibilities, piece is not jumpable because destination would be out of borders |
      | validJumpMove4 |                 2 | current          | s: another jump possibility                                                                     |
      | validJumpMove5 |                 2 | next ingame      | f: this valid jump move proves the case in validRegularMove3                                    |
      | validJumpMove6 |                 2 | next ingame      | f: end of jump possibilities, piece is not jumpable because possible destination is occupied    |

  Scenario Outline: Player Chooses to Continue or Not
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    #the player has made jump moves in the current turn
    #the player can continue doing jump moves
    And the player has been asked to continue or not
    When the player chooses to "<decision>"
    Then the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name                     | number_of_players | decision  | next_turn_player |
      | playerChoosesToContinueOrNot1 |                 2 | continue  | current          |
      | playerChoosesToContinueOrNot2 |                 2 | stop      | next ingame      |

  Scenario Outline: Invalid Move
  	Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player makes a "invalid" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another move

    Examples: 
      | file_name                                                                  | number_of_players | invalidity_reason                                                        | error_message                               |
      | invalidSourceCoordinateForMoveOutsideBorders1                              |                 2 | source coordinate is not of valid square color                           | No piece at source coordinate               |
      | invalidSourceCoordinateForMoveOutsideBorders2                              |                 2 | source coordinate is not of valid square color                           | No piece at source coordinate               |
      | invalidSourceCoordinateForMoveUnplayableColor1                             |                 2 | source coordinate is not of valid square color                           | No piece at source coordinate               |
      | invalidSourceCoordinateForMoveUnplayableColor2                             |                 2 | source coordinate is not of valid square color                           | No piece at source coordinate               |
      | invalidSourceCoordinateForMoveEmpty1                                       |                 2 | source coordinate is empty                                               | No piece at source coordinate               |
      | invalidSourceCoordinateForMoveEmpty2                                       |                 2 | source coordinate is empty                                               | No piece at source coordinate               |
      | invalidSourceCoordinateForMoveEmpty3                                       |                 2 | source coordinate is empty                                               | No piece at source coordinate               |
      | invalidSourceCoordinateForMoveOpponentsPiece1                              |                 2 | source coordinate has opponent's piece                                   | Piece does not belong to current player     |
      | invalidSourceCoordinateForMoveOpponentsPiece2                              |                 2 | source coordinate has opponent's piece                                   | Piece does not belong to current player     |
      | invalidSourceCoordinateForMoveOpponentsPiece3                              |                 2 | source coordinate has opponent's piece                                   | Piece does not belong to current player     |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 |                 2 | source coordinate of move is different than last jump move’s destination | Destination valid? false                    |
      | invalidDestinationCoordinateForMoveOutsideBorders1                         |                 2 | destination coordinate is not of valid square color                      | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveOutsideBorders2                         |                 2 | destination coordinate is not of valid square color                      | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveUnplayableColor1                        |                 2 | destination coordinate is not of valid square color                      | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveUnplayableColor2                        |                 2 | destination coordinate is not of valid square color                      | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveOccupied1                               |                 2 | destination coordinate is occupied                                       | A piece at destination coordinate           |
      | invalidDestinationCoordinateForMoveOccupied2                               |                 2 | destination coordinate is occupied                                       | A piece at destination coordinate           |
      | invalidDestinationCoordinateForMoveOccupied3                               |                 2 | destination coordinate is occupied                                       | A piece at destination coordinate           |
      | invalidDestinationCoordinateForMoveUnallowedDirection1                     |                 2 | destination coordinate's direction is not allowed                        | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveUnallowedDirection2                     |                 2 | destination coordinate's direction is not allowed                        | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveTooFarAway1                             |                 2 | destination coordinate is more than two squares away                     | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveTooFarAway2                             |                 2 | destination coordinate is more than two squares away                     | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1                      |                 2 | jumped piece is null                                                     | There must be only one piece on jump path 0 |
      | invalidDestinationCoordinateForMoveCantLeaveGoalTriangle1                  |                 2 | piece can not leave goal triangle                                        | Piece can not leave goal triangle           |
      | invalidDestinationCoordinateForMoveOppositeDirectionOfLastMove1            |                 2 | move direction is opposite of last jump move's direction                 | Not one of possible jump moves              |

  Scenario Outline: End of the Game
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    #the player has at least one of his pieces in the goal triangle
    #there is only one square available at the goal triangle
    When the player jumps moves one of his pieces to the last available square in goal triangle
    Then the player wins the game

    Examples: 
      | file_name           | number_of_players |
      | endOfTheGame2       |                 2 |
      | endOfTheGame2Accept |                 2 |
      | endOfTheGame3       |                 3 |
      | endOfTheGame3Accept |                 3 |
      | endOfTheGame4       |                 4 |
      | endOfTheGame4Accept |                 4 |
      | endOfTheGame6       |                 6 |
      | endOfTheGame6Accept |                 6 |

  Scenario Outline: Offer Draw Move
    Given the "Chinese Checkers" game is set up for "2" players
    And the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples: 
      | file_name      |
      | offerDrawMove1 |

  Scenario Outline: End of the Game In Draw
    Given the "Chinese Checkers" game is set up for "2" players
    And the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples: 
      | file_name           | offer_response | result                                       |
      | endOfTheGameInDraw1 | accepts        | the game is ended as a draw                  |
      | endOfTheGameInDraw2 | rejects        | the next turn is given to the offerer player |
      | endOfTheGameInDraw3 | accepts        | the game is ended as a draw                  |
      | endOfTheGameInDraw4 | accepts        | the game is ended as a draw                  |
      | endOfTheGameInDraw5 | rejects        | the next turn is given to the offerer player |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game

    Examples: 
      | file_name                               | number_of_players |
      | endOfTheGameOpponentCantMakeAValidMove1 |                 2 |

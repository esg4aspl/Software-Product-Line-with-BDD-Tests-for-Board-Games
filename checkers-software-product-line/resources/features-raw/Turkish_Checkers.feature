Feature: Turkish Checkers

  Background: 
    Given the "Turkish Checkers" game is set up

  Scenario: Start of the Game
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples: 
      | file_name         | piece_type | explanation                                                                 |
      | validRegularMove1 | king       | f: king moves backwards                                                     |
      | validRegularMove2 | pawn       | f: regular move                                                             |
      | validRegularMove3 | pawn       | f: regular move                                                             |
      | validRegularMove4 | pawn       | f: opponent is not blocked, his king can jump backward, game should not end |
      | validRegularMove5 | pawn       | f: opponent is not blocked, his pawn can jump forward, game should not end  |
      | validRegularMove6 | pawn       | f: opponent is not blocked, his pawn can jump forward, game should not end  |
      | validRegularMove7 | king       | f: king can jump multiple squares without capturing                         |
      | validRegularMove8 | king       | f: king can jump multiple squares without capturing                         |

  Scenario Outline: Valid Jump Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name       | next_turn_player | piece_type | explanation                                                                                                                                                               |
      | validJumpMove1  | next ingame      | pawn       | f: end of jump possibilities, own piece is not jumpable                                                                                                                   |
      | validJumpMove2  | next ingame      | king       | f: end of jump possibilities, own piece is not jumpable                                                                                                                   |
      | validJumpMove3  | next ingame      | pawn       | f: end of jump possibilities, no adjacent piece                                                                                                                           |
      | validJumpMove4  | current          | pawn       | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove5)                                                                |
      | validJumpMove5  | current          | pawn       | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove4)                                                                |
      | validJumpMove6  | next ingame      | pawn       | s: end of jump possibilities, opponent is not jumpable because destination would be out of borders                                                                        |
      | validJumpMove7  | current          | king       | f: another jump possibility                                                                                                                                               |
      | validJumpMove8  | current          | king       | f: another jump possibility, opponent piece in distance                                                                                                                   |
      | validJumpMove9  | next ingame      | king       | f: this valid jump move proves the case in validRegularMove4                                                                                                              |
      | validJumpMove10 | next ingame      | pawn       | f: this valid jump move proves the case in validRegularMove6                                                                                                              |
      | validJumpMove11 | next ingame      | king       | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw                                                                         |
      | validJumpMove12 | next ingame      | king       | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw                                                                       |
      | validJumpMove13 | next ingame      | king       | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw                                                                         |
      | validJumpMove14 | next ingame      | king       | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw                                                                       |
      | validJumpMove15 | next ingame      | king       | f: end of jump possibilities, opponent is not jumpable because possible destination is occupied                                                                           |
      | validJumpMove16 | current          | king       | f: another jump possibility, even though the destination is in crownhead, the piece is already king, it can continue jumping, also a similar situation to validJumpMove17 |
      | validJumpMove17 | next ingame      | pawn       | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end in draw                                                          |
      | validJumpMove18 | current          | king       | f: king can jump and capture from distance                                                                                                                                |
      | validJumpMove19 | next ingame      | king       | s: king can jump and capture from distance                                                                                                                                |

  Scenario Outline: Invalid Source Coordinate for Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                                                  | invalidity_reason                                                        | error_message                                      |
      | invalidSourceCoordinateForMoveOutsideBorders1                              | source coordinate is outside of the board                                | No piece at source coordinate                      |
      | invalidSourceCoordinateForMoveOutsideBorders2                              | source coordinate is outside of the board                                | No piece at source coordinate                      |
      | invalidSourceCoordinateForMoveEmpty1                                       | source coordinate is empty                                               | No piece at source coordinate                      |
      | invalidSourceCoordinateForMoveEmpty2                                       | source coordinate is empty                                               | No piece at source coordinate                      |
      | invalidSourceCoordinateForMoveEmpty3                                       | source coordinate is empty                                               | No piece at source coordinate                      |
      | invalidSourceCoordinateForMoveOpponentsPiece1                              | source coordinate has opponent's piece                                   | Piece does not belong to current player            |
      | invalidSourceCoordinateForMoveOpponentsPiece2                              | source coordinate has opponent's piece                                   | Piece does not belong to current player            |
      | invalidSourceCoordinateForMoveOpponentsPiece3                              | source coordinate has opponent's piece                                   | Piece does not belong to current player            |
      | invalidSourceCoordinateForMovePawnInCrownhead1                             | there is a pawn in crownhead but move is not that                        | Pawn in crownhead must capture king to be promoted |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | source coordinate of move is different than last jump move’s destination | Destination valid? false                           |

  Scenario Outline: Invalid Destination Coordinate for Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                                          | piece_type | invalidity_reason                                         | error_message                                                               |
      | invalidDestinationCoordinateForMoveOutsideBorders1                 | king       | destination coordinate is outside of the board            | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveOutsideBorders2                 | king       | destination coordinate is outside of the board            | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveOccupied1                       | king       | destination coordinate is occupied                        | A piece at destination coordinate                                           |
      | invalidDestinationCoordinateForMoveOccupied2                       | king       | destination coordinate is occupied                        | A piece at destination coordinate                                           |
      | invalidDestinationCoordinateForMoveUnallowedDirection1             | pawn       | destination coordinate's direction is not allowed         | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveUnallowedDirection2             | pawn       | destination coordinate's direction is not allowed         | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveUnallowedDirection3             | king       | destination coordinate's direction is not allowed         | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveTooFarAway1                     | pawn       | destination coordinate is more than two squares away      | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1              | pawn       | jumped piece is null                                      | There must be one piece on jump path 0                                      |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1          | king       | jumped piece is not opponent piece                        | Jumped Piece Must Be Opponent Piece                                         |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromSource1 | pawn       | jumped piece is too far away from source coordinate       | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1           | king       | there are more than one pieces in jump path               | There must be only one piece on jump path 2                                 |
      | invalidDestinationCoordinateForMoveNotBestSequence1                | king       | move is not part of the best sequence                     | Not the best move                                                           |
      | invalidDestinationCoordinateForMoveNotBestSequence2                | king       | move is not part of the best sequence                     | Not the best move                                                           |
      | invalidDestinationCoordinateForMovePawnInCrownhead1                | pawn       | the pawn in crownhead did not capture the vulnerable king | Must capture king to be promoted                                            |
      | invalidDestinationCoordinateForMovePawnInCrownhead2                | pawn       | the pawn in crownhead did not capture the vulnerable king | Must capture king to be promoted                                            |
      | invalidDestinationCoordinateForMoveOppositeDirectionOfLastMove1    | king       | move direction is opposite of last jump move's direction  | If any opponent's pieces can be captured then it must be captured first!!!! |

  Scenario Outline: Crowning the Eligible Piece
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece is "<action>" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "<next_turn_player>" player

    #| crowningTheEligiblePiece5   | promoted | next ingame | f: jump move, adjacent vulnerable opponent pawn |
    #| crowningTheEligiblePiece5.1 | promoted | next ingame | f: jump move, far away vulnerable opponent pawn |
    Examples: 
      | file_name                  | action       | next_turn_player | explanation                                                                                              |
      | crowningTheEligiblePiece1  | promoted     | next ingame      | f: regular move, no adjacent pieces                                                                      |
      | crowningTheEligiblePiece2  | promoted     | next ingame      | f: regular move, adjacent vulnerable opponent king                                                       |
      | crowningTheEligiblePiece3  | promoted     | next ingame      | f: regular move, adjacent vulnerable own king                                                            |
      | crowningTheEligiblePiece4  | promoted     | next ingame      | f: jump move, no adjacent pieces                                                                         |
      | crowningTheEligiblePiece5  | promoted     | next ingame      | f: jump move, adjacent vulnerable opponent pawn                                                          |
      | crowningTheEligiblePiece6  | promoted     | next ingame      | f: jump move, adjacent vulnerable own king                                                               |
      | crowningTheEligiblePiece7  | promoted     | next ingame      | f: jump move, adjacent protected opponent king                                                           |
      | crowningTheEligiblePiece8  | promoted     | next ingame      | f: jump move, far away vulnerable opponent king                                                          |
      | crowningTheEligiblePiece9  | not promoted | current          | f: jump move, adjacent vulnerable opponent king, crowning is hold until kings are captured               |
      | crowningTheEligiblePiece10 | not promoted | current          | f: jump move, adjacent vulnerable opponent kings (multiple), crowning is hold until kings are captured   |
      | crowningTheEligiblePiece11 | promoted     | next ingame      | f: no promote is 39, no capture is 39, crowning is a decisive move, game should not end in draw          |
      | crowningTheEligiblePiece12 | promoted     | next ingame      | f: no promote is 45, no capture is 0, crowning is a decisive move, game should not end in draw           |
      | crowningTheEligiblePiece13 | promoted     | next ingame      | f: no promote is 0, no capture is 45, crowning is a decisive move, game should not end in draw           |
      | crowningTheEligiblePiece14 | not promoted | current          | f: pawn in crownhead jumps over adjacent vulnerable king only to see another king                        |
      | crowningTheEligiblePiece15 | not promoted | current          | s: pawn in crownhead jumps over adjacent vulnerable king only to see another king                        |

  #There is an opponent king threatened from the destination coordinate. It must be captured for the crowning to complete.
  #Only if the piece reached the crownhead with a jump move
  Scenario Outline: Crowning The Eligible Piece - Capturing Kings in the Crownhead
    Given the game is played up to a certain point from file "<file_name>"
    And the player has a "pawn" piece in opponent's crownhead
    When the player jumps over all the vulnerable opponent kings in the crownhead
    Then the piece is "promoted" to a crowned piece
    And the next turn is given to the "next ingame" player

    Examples: 
      | file_name                                          | explanation                                                                                                                          |
      | crowningTheEligiblePieceCapturingKingsInCrownhead1 | f: pawn jumps one king, lands at a square where there is no adjacent piece, and becomes king                                         |
      | crowningTheEligiblePieceCapturingKingsInCrownhead2 | f: pawn jumps one king, lands at a square where there is an adjacent vulnerable own king, and becomes king                           |
      | crowningTheEligiblePieceCapturingKingsInCrownhead3 | s: pawn jumps two kings, lands at a square where there is no adjacent piece, and becomes king (finishing crowningTheEligiblePiece14) |

  Scenario Outline: End of the Game
    Given the game is played up to a certain point from file "<file_name>"
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples: 
      | file_name     |
      | endOfTheGame1 |
      | endOfTheGame2 |

  Scenario Outline: Offer Draw Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples: 
      | file_name      |
      | offerDrawMove1 |

  Scenario Outline: End of the Game In Draw
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples: 
      | file_name           | offer_response | result                                           |
      | endOfTheGameInDraw1 | accepts        | the game is ended as a draw                      |
      | endOfTheGameInDraw2 | rejects        | the next turn is given to the next ingame player |
      | endOfTheGameInDraw3 | accepts        | the game is ended as a draw                      |
      | endOfTheGameInDraw4 | accepts        | the game is ended as a draw                      |
      | endOfTheGameInDraw5 | rejects        | the next turn is given to the next ingame player |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece
    Given the game is played up to a certain point from file "<file_name>"
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples: 
      | file_name                                  | explanation |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 | f:          |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 | s:          |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 39
    When the player makes a regular move without promoting
    Then the game is ended as a draw

    Examples: 
      | file_name                               | explanation                                                                 |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | f: no promote is 39, no capture is 39, a regular move ends the game in draw |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | f: no promote is 45, no capture is 39, a regular move ends the game in draw |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | f: no promote is 39, no capture is 45, a regular move ends the game in draw |

  Scenario Outline: End of the Game In Draw - Same Board State Reached for the Third Time
    Given the game is played up to a certain point from file "<file_name>"
    And there are some board states that have been reached two times
    When the player finishes his turn leaving the board in a previously reached state
    Then the game is ended as a draw

    Examples: 
      | file_name                                                          |
      | endOfTheGameInDrawResultingBoardStateWillBeReachedForTheThirdTime1 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples: 
      | file_name                               |
      | endOfTheGameOpponentCantMakeAValidMove1 |
      | endOfTheGameOpponentCantMakeAValidMove2 |

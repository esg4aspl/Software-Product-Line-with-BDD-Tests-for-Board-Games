Feature: Spanish Checkers

  Background: 
    Given the "Spanish Checkers" game is set up

  Scenario: Start of the Game
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples: 
      | file_name         | piece_type | explanation                                                                  |
      | validRegularMove1 | queen      | f: queen moves backwards                                                     |
      | validRegularMove2 | pawn       | f: regular move                                                              |
      | validRegularMove3 | pawn       | f: regular move                                                              |
      | validRegularMove4 | pawn       | f: opponent is not blocked, his queen can jump backward, game should not end |
      | validRegularMove5 | pawn       | f: opponent is not blocked, his pawn can jump forward, game should not end   |
      | validRegularMove6 | pawn       | f: opponent is not blocked, his pawn can jump forward, game should not end   |
      | validRegularMove7 | queen      | f: queen can jump multiple squares without capturing                         |
      | validRegularMove8 | queen      | f: queen can jump multiple squares without capturing                         |

  Scenario Outline: Valid Jump Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name       | next_turn_player | piece_type | explanation                                                                                                                   |
      | validJumpMove1  | other            | pawn       | f: end of jump possibilities, own piece is not jumpable                                                                       |
      | validJumpMove2  | other            | queen      | f: end of jump possibilities, own piece is not jumpable                                                                       |
      | validJumpMove3  | other            | pawn       | f: end of jump possibilities, no adjacent piece                                                                               |
      | validJumpMove4  | current          | queen      | f: another jump possibility                                                                                                   |
      | validJumpMove5  | current          | pawn       | f: another jump possibility                                                                                                   |
      | validJumpMove6  | other            | pawn       | f: end of jump possibilities, opponent is not jumpable because destination would be out of borders                            |
      | validJumpMove7  | current          | queen      | f: another jump possibility                                                                                                   |
      | validJumpMove8  | current          | queen      | f: another jump possiblity, opponent piece in distance                                                                        |
      | validJumpMove9  | other            | queen      | f: this valid jump move proves the case in validRegularMove4                                                                  |
      | validJumpMove10 | other            | pawn       | f: this valid jump move proves the case in validRegularMove6                                                                  |
      | validJumpMove11 | other            | pawn       | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw                             |
      | validJumpMove12 | other            | pawn       | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw                           |
      | validJumpMove13 | other            | pawn       | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw                             |
      | validJumpMove14 | other            | pawn       | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw                           |
      | validJumpMove15 | other            | queen      | f: end of jump possiblities, opponent is not jumpable because possible destination is occupied                                |
      | validJumpMove16 | current          | queen      | f: another jump possibility, even though the destination is in crownhead, the piece is already queen, it can continue jumping |
      | validJumpMove17 | other            | pawn       | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end ind draw             |
      | validJumpMove18 | current          | queen      | f: queen can jump and capture from distance                                                                                   |
      | validJumpMove19 | other            | queen      | s: queen can jump and capture from distance                                                                                   |

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
      | file_name                                                               | piece_type | invalidity_reason                                                     | error_message                               |
      | invalidDestinationCoordinateForMoveOutsideBorders1                      | pawn       | destination coordinate is outside of the board                        | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveOutsideBorders2                      | pawn       | destination coordinate is outside of the board                        | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveUnplayableColor1                     | pawn       | destination coordinate is not of valid square color                   | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveUnplayableColor2                     | pawn       | destination coordinate is not of valid square color                   | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveOccupied1                            | pawn       | destination coordinate is occupied                                    | A piece at destination coordinate           |
      | invalidDestinationCoordinateForMoveOccupied2                            | pawn       | destination coordinate is occupied                                    | A piece at destination coordinate           |
      | invalidDestinationCoordinateForMoveOccupied3                            | pawn       | destination coordinate is occupied                                    | A piece at destination coordinate           |
      | invalidDestinationCoordinateForMoveUnallowedDirection1                  | pawn       | destination coordinate's direction is not allowed                     | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveUnallowedDirection2                  | pawn       | destination coordinate's direction is not allowed                     | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveUnallowedDirection3                  | pawn       | destination coordinate's direction is not allowed                     | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveTooFarAway1                          | pawn       | destination coordinate is more than two squares away                  | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveTooFarAway2                          | pawn       | destination coordinate is more than two squares away                  | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1                   | pawn       | jumped piece is null                                                  | There must be one piece on jump path 0      |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1               | pawn       | jumped piece is not opponent piece                                    | Jumped Piece Must Be Opponent Piece         |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromSource1      | pawn       | jumped piece is too far away from source coordinate                   | Destination Valid? false                    |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromDestination1 | queen      | destination coordinate is more than one square away from jumped piece | Must land just behind jumped piece          |
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1                | queen      | there are more than one pieces in jump path                           | There must be only one piece on jump path 2 |
      | invalidDestinationCoordinateForMoveNotBestSequence1                     | queen      | move is not part of the best sequence                                 | Not the best move                           |
      | invalidDestinationCoordinateForMoveNotBestSequence2                     | queen      | move is not part of the best sequence                                 | Not the best move                           |
      | invalidDestinationCoordinateForMoveNotBestSequence3                     | queen      | move is not part of the best sequence                                 | Not the best move                           |

  Scenario Outline: Crowning the Eligible Piece
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece is "promoted" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples: 
      | file_name                 | explanation                                                                                                            |
      | crowningTheEligiblePiece1 | f:                                                                                                                     |
      | crowningTheEligiblePiece2 | f:                                                                                                                     |
      | crowningTheEligiblePiece3 | f: there are possibilities for a jump move but piece is crowned, so the next turn should be given to the opponent      |
      | crowningTheEligiblePiece4 | f:                                                                                                                     |
      | crowningTheEligiblePiece5 | f: there are possibilities for a jump move but piece is crowned, so the next turn should be given to the opponent      |
      | crowningTheEligiblePiece6 | f: no promote is 39, no capture is 39, crowning is a decisive move, game should not end in draw                        |
      | crowningTheEligiblePiece7 | f: no promote is 45, no capture is 0, crowning is a decisive move, game should not end in draw                         |
      | crowningTheEligiblePiece8 | f: no promote is 0, no capture is 45, crowning is a decisive move, game should not end in draw                         |

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
      | file_name                               | explanation                                                                 |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | f: no promote is 39, no capture is 39, a regular move ends the game in draw |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | f: no promote is 45, no capture is 39, a regular move ends the game in draw |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | f: no promote is 39, no capture is 45, a regular move ends the game in draw |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples: 
      | file_name                               |
      | endOfTheGameOpponentCantMakeAValidMove1 |
      | endOfTheGameOpponentCantMakeAValidMove2 |

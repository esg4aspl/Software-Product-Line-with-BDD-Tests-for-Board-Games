Feature: Chess

  Background: 
    Given the "Chess" game is set up

  Scenario: Start of the Game
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that has no pieces in it
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples: 
      | file_name               | piece_type | explanation                                                                                                  |
      | validRegularMovePawn1   | pawn       |                                                                                                              |
      | validRegularMovePawn2   | pawn       | pawn can move two squares in its starting position                                                           |
      | validRegularMoveRook1   | rook       |                                                                                                              |
      | validRegularMoveRook2   | rook       |                                                                                                              |
      | validRegularMoveKnight1 | knight     |                                                                                                              |
      | validRegularMoveKnight2 | knight     | move is made even though there is piece on path                                                              |
      | validRegularMoveBishop1 | bishop     |                                                                                                              |
      | validRegularMoveBishop2 | bishop     |                                                                                                              |
      | validRegularMoveQueen1  | queen      |                                                                                                              |
      | validRegularMoveQueen2  | queen      | this move proves the case in validRegularMoveNotEnd2                                                         |
      | validRegularMoveKing1   | king       |                                                                                                              |
      | validRegularMoveKing2   | king       | this move proves the case in validRegularMoveNotEnd1                                                         |
      | validRegularMoveNotEnd1 | rook       | opponent is not checkmated, his king can escape, game should not be ended                                    |
      | validRegularMoveNotEnd2 | bishop     | opponent is not checkmated, his king can be protected by another piece by blocking, game should not be ended |
      | validRegularMoveNotEnd3 | rook       | opponent is not checkmated, his king can be protected by another piece by capture, game should not be ended  |
      | validRegularMoveNotEnd4 | pawn       | no pawn move is 99, no capture is 99, pawn move is a decisive move, game should not end in draw              |
      | validRegularMoveNotEnd5 | pawn       | no pawn move is 105, no capture is 0, pawn move is a decisive move, game should not end in draw              |
      | validRegularMoveNotEnd6 | pawn       | no pawn move is 0, no capture is 105, pawn move is a decisive move, game should not end in draw              |

  Scenario Outline: Valid Capture Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that has a capturable opponent piece in it
    Then the piece is moved to the destination coordinate
    And the opponent piece is removed from the board
    And the next turn is given to the "next ingame" player

    Examples: 
      | file_name               | piece_type | explanation                                                                                            |
      | validCaptureMove1       | pawn       | regular capture by pawn                                                                                |
      | validCaptureMove2       | pawn       | regular capture by pawn                                                                                |
      | validCaptureMove3       | pawn       | move en passant by pawn                                                                                |
      | validCaptureMove4       | rook       |                                                                                                        |
      | validCaptureMove5       | knight     |                                                                                                        |
      | validCaptureMove6       | king       | this capture proves the case in validRegularMoveNotEnd3                                                |
      | validCaptureMove7       | queen      |                                                                                                        |
      | validCaptureMove8       | bishop     |                                                                                                        |
      | validCaptureMoveNotEnd1 | pawn       | no pawn move is 99, no capture is 0, a capture move is a decisive move, game should not end in draw    |
      | validCaptureMoveNotEnd2 | pawn       | no pawn move is 0, no capture is 99, this capture move should clear that, game should not end in draw  |
      | validCaptureMoveNotEnd3 | pawn       | no pawn move is 105, no capture is 0, a capture move is a decisive move, game should not end in draw   |
      | validCaptureMoveNotEnd4 | pawn       | no pawn move is 0, no capture is 105, this capture move should clear that, game should not end in draw |

  Scenario Outline: Invalid Source Coordinate for Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                     | invalidity_reason                         | error_message                           |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveEmpty1          | source coordinate is empty                | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveEmpty2          | source coordinate is empty                | No piece at source coordinate           |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece    | Piece does not belong to current player |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece    | Piece does not belong to current player |

  Scenario Outline: Invalid Destination Coordinate for Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                              | piece_type | invalidity_reason                                              | error_message            |
      | invalidDestinationCoordinateForMoveOutsideBorders1     | rook       | destination coordinate is outside of the board                 | Destination Valid? false |
      | invalidDestinationCoordinateForMoveOutsideBorders2     | queen      | destination coordinate is outside of the board                 | Destination Valid? false |
      | invalidDestinationCoordinateForMoveCapturedPieceIsOwn1 | queen      | captured piece is own                                          | Destination Valid? false |
      | invalidDestinationCoordinateForMoveCapturedPieceIsOwn2 | bishop     | captured piece is own                                          | Destination Valid? false |
      | invalidDestinationCoordinateForMoveSameAsSource1       | bishop     | destination coordinate is same as source coordinate            | Destination Valid? false |
      | invalidDestinationCoordinateForMoveSameAsSource2       | pawn       | destination coordinate is same as source coordinate            | Destination Valid? false |
      | invalidDestinationCoordinateForMoveKingNotProtected1   | knight     | threatened king is not protected                               | Destination Valid? false |
      | invalidDestinationCoordinateForMoveKingNotProtected2   | queen      | threatened king is not protected                               | Destination Valid? false |
      | invalidDestinationCoordinateForPawn1                   | pawn       | can not move more than two squares from starting position      |                          |
      | invalidDestinationCoordinateForPawn2                   | pawn       | can not move more than one square if not in starting position  |                          |
      | invalidDestinationCoordinateForPawn3                   | pawn       | can not move in non-forward-vertical directions (cross)        |                          |
      | invalidDestinationCoordinateForPawn4                   | pawn       | can not move in non-forward-vertical directions (sideways)     |                          |
      | invalidDestinationCoordinateForPawn5                   | pawn       | can not move in non-forward-vertical directions (backwards)    |                          |
      | invalidDestinationCoordinateForPawn6                   | pawn       | can not move in non-forward-vertical directions (asymmetrical) |                          |
      | invalidDestinationCoordinateForPawn7                   | pawn       | can not perform capture in a non-cross move                    |                          |
      | invalidDestinationCoordinateForPawn8                   | pawn       | move en passant is not valid                                   |                          |
      | invalidDestinationCoordinateForPawn9                   | pawn       | there is piece on path                                         |                          |
      | invalidDestinationCoordinateForRook1                   | rook       | can not move in non-orthogonal directions (cross)              |                          |
      | invalidDestinationCoordinateForRook2                   | rook       | can not move in non-orthogonal directions (asymmetrical)       |                          |
      | invalidDestinationCoordinateForRook3                   | rook       | there is piece on path                                         |                          |
      | invalidDestinationCoordinateForKnight1                 | knight     | can not move in non-L path                                     |                          |
      | invalidDestinationCoordinateForKnight2                 | knight     | can not move in non-L path                                     |                          |
      | invalidDestinationCoordinateForBishop1                 | bishop     | can not move in non-cross directions (orthogonal)              |                          |
      | invalidDestinationCoordinateForBishop2                 | bishop     | can not move in non-cross directions (assymetrical)            |                          |
      | invalidDestinationCoordinateForBishop3                 | bishop     | there is piece on path                                         |                          |
      | invalidDestinationCoordinateForQueen1                  | queen      | can not move in asymmetrical directions                        |                          |
      | invalidDestinationCoordinateForQueen2                  | queen      | there is piece on path                                         |                          |
      | invalidDestinationCoordinateForKing1                   | king       | king is threatened after move                                  |                          |
      | invalidDestinationCoordinateForKing2                   | king       | cannot castle because king has moved before                    |                          |
      | invalidDestinationCoordinateForKing3                   | king       | cannot castle because rook has moved before                    |                          |
      | invalidDestinationCoordinateForKing4                   | king       | cannot castle because there are pieces in between              |                          |

  Scenario Outline: Crowning the Eligible Piece
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece is "promoted" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples: 
      | file_name                 | explanation |
      | crowningTheEligiblePiece1 |             |
      | crowningTheEligiblePiece2 |             |

  Scenario Outline: End of the Game
    Given the game is played up to a certain point from file "<file_name>"
    And the opponent's king can not be protected if it is checked
    When the player makes a move that threatens the opponent's king's current position
    Then the opponent loses the game
    And the player wins the game

    Examples: 
      | file_name     |
      | endOfTheGame1 |
      | endOfTheGame2 |

  Scenario Outline: End of the Game In Draw - Stalemate
    Given the game is played up to a certain point from file "<file_name>"
    #the opponent's only player on the board is his king
    When the player makes a move that not checks the opponent king but leaves it with no valid move to make
    Then the game is ended as a draw

    Examples: 
      | file_name              |
      | endOfTheGameStalemate1 |
      | endOfTheGameStalemate2 |

  Scenario Outline: End of the Game In Draw - Same Board State Reached for the Third Time
    Given the game is played up to a certain point from file "<file_name>"
    And there are some board states that have been reached two times
    When the player finishes his turn leaving the board in a previously reached state
    Then the game is ended as a draw

    Examples: 
      | file_name                                                          |
      | endOfTheGameInDrawResultingBoardStateWillBeReachedForTheThirdTime1 |

  Scenario Outline: End of the Game In Draw - Hundred Moves Without Moving a Pawn or Capturing
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 99
    When the player moves a non-pawn piece without capturing an opponent piece
    Then the game is ended as a draw

    Examples: 
      | file_name                               | explanation                                                                    |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | f: no pawn move is 99, no capture is 99, a regular move ends the game in draw  |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | f: no pawn move is 105, no capture is 99, a regular move ends the game in draw |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | f: no pawn move is 99, no capture is 105, a regular move ends the game in draw |

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

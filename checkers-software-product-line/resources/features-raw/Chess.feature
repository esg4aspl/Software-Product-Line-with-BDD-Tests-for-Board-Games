Feature: Chess

  Background: 
    Given the "Chess" game is set up

  Scenario: Start of the Game
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
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

  Scenario Outline: Valid Castling Move
    Given the game is played up to a certain point from file "<file_name>"
    #the player can make a castling move
    When the player makes a "valid castling" move
    Then the piece is moved to the destination coordinate
    And the rook is moved to the adjacent coordinate that is towards the center

    Examples: 
      | file_name               |
      | validLeftCastlingMove1  |
      | validRightCastlingMove1 |

  Scenario Outline: Invalid Castling Move
    Given the game is played up to a certain point from file "<file_name>"
    #And the player can not make a castling move
    When the player makes a "invalid castling" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"

    Examples: 
      | file_name            | error_message                                     |
      | invalidCastlingMove1 | cannot castle because king has moved before       |
      | invalidCastlingMove2 | cannot castle because rook has moved before       |
      | invalidCastlingMove3 | cannot castle because there are pieces in between |

  Scenario Outline: Valid Capture Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid capture" move
    Then the piece is moved to the destination coordinate
    And the captured opponent piece is removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name               | explanation                                                                                            |
      | validCaptureMove1       | regular capture by pawn                                                                                |
      | validCaptureMove2       | regular capture by pawn                                                                                |
      | validCaptureMove3       | move en passant by pawn                                                                                |
      | validCaptureMove4       |                                                                                                        |
      | validCaptureMove5       |                                                                                                        |
      | validCaptureMove6       | this capture proves the case in validRegularMoveNotEnd3                                                |
      | validCaptureMove7       |                                                                                                        |
      | validCaptureMove8       |                                                                                                        |
      | validCaptureMoveNotEnd1 | no pawn move is 99, no capture is 0, a capture move is a decisive move, game should not end in draw    |
      | validCaptureMoveNotEnd2 | no pawn move is 0, no capture is 99, this capture move should clear that, game should not end in draw  |
      | validCaptureMoveNotEnd3 | no pawn move is 105, no capture is 0, a capture move is a decisive move, game should not end in draw   |
      | validCaptureMoveNotEnd4 | no pawn move is 0, no capture is 105, this capture move should clear that, game should not end in draw |

  Scenario Outline: Invalid Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "invalid" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another move

    Examples: 
      | file_name                                              | invalidity_reason                                              | error_message                                                             |
      | invalidSourceCoordinateForMoveOutsideBorders1          | source coordinate is outside of the board                      | No piece at source coordinate                                             |
      | invalidSourceCoordinateForMoveOutsideBorders2          | source coordinate is outside of the board                      | No piece at source coordinate                                             |
      | invalidSourceCoordinateForMoveEmpty1                   | source coordinate is empty                                     | No piece at source coordinate                                             |
      | invalidSourceCoordinateForMoveEmpty2                   | source coordinate is empty                                     | No piece at source coordinate                                             |
      | invalidSourceCoordinateForMoveOpponentsPiece1          | source coordinate has opponent's piece                         | Piece does not belong to current player                                   |
      | invalidSourceCoordinateForMoveOpponentsPiece2          | source coordinate has opponent's piece                         | Piece does not belong to current player                                   |
      | invalidDestinationCoordinateForMoveOutsideBorders1     | destination coordinate is outside of the board                 | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForMoveOutsideBorders2     | destination coordinate is outside of the board                 | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForMoveCapturedPieceIsOwn1 | captured piece is own                                          | You can only capture opponent piece!!!!                                   |
      | invalidDestinationCoordinateForMoveCapturedPieceIsOwn2 | captured piece is own                                          | You can only capture opponent piece!!!!                                   |
      | invalidDestinationCoordinateForMoveSameAsSource1       | destination coordinate is same as source coordinate            | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForMoveSameAsSource2       | destination coordinate is same as source coordinate            | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForMoveKingNotProtected1   | threatened king is not protected                               | invalid : King has to be free                                             |
      | invalidDestinationCoordinateForMoveKingNotProtected2   | threatened king is not protected                               | invalid : King has to be free                                             |
      | invalidDestinationCoordinateForPawn1                   | can not move more than two squares from starting position      | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForPawn2                   | can not move more than one square if not in starting position  | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForPawn3                   | can not move in non-forward-vertical directions (cross)        | Pawn cannot move to cross if there is no piece on destination coordinate! |
      | invalidDestinationCoordinateForPawn4                   | can not move in non-forward-vertical directions (sideways)     | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForPawn5                   | can not move in non-forward-vertical directions (backwards)    | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForPawn6                   | can not move in non-forward-vertical directions (asymmetrical) | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForPawn7                   | can not perform capture in a non-cross move                    | Pawn cannot move to straight if there is pawn on destination coordinate   |
      | invalidDestinationCoordinateForPawn8                   | move en passant is not valid                                   | Pawn cannot move to cross if there is no piece on destination coordinate! |
      | invalidDestinationCoordinateForPawn9                   | there is piece on path                                         | There must be no piece on the move path!!!.                               |
      | invalidDestinationCoordinateForRook1                   | can not move in non-orthogonal directions (cross)              | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForRook2                   | can not move in non-orthogonal directions (asymmetrical)       | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForRook3                   | there is piece on path                                         | There must be no piece on the move path!!!.                               |
      | invalidDestinationCoordinateForKnight1                 | can not move in non-L path                                     | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForKnight2                 | can not move in non-L path                                     | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForBishop1                 | can not move in non-cross directions (orthogonal)              | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForBishop2                 | can not move in non-cross directions (assymetrical)            | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForBishop3                 | there is piece on path                                         | There must be no piece on the move path!!!.                               |
      | invalidDestinationCoordinateForQueen1                  | can not move in asymmetrical directions                        | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForQueen2                  | there is piece on path                                         | There must be no piece on the move path!!!.                               |
      | invalidDestinationCoordinateForKing1                   | can not move more than one square                              | Destination Valid? false                                                  |
      | invalidDestinationCoordinateForKing2                   | king is threatened after move                                  | invalid : King has to be free                                             |

  Scenario Outline: Crowning the Eligible Piece
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move to opponent's crownhead with a pawn
    Then the piece is "promoted" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples: 
      | file_name                 |
      | crowningTheEligiblePiece1 |
      | crowningTheEligiblePiece2 |

  Scenario Outline: End of the Game
    Given the game is played up to a certain point from file "<file_name>"
    #the opponent's king can not be protected if it is checked
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
    #there are some board states that have been reached two times
    When the player finishes his turn leaving the board in a previously reached state
    Then the game is ended as a draw

    Examples: 
      | file_name                                                          |
      | endOfTheGameInDrawResultingBoardStateWillBeReachedForTheThirdTime1 |

  Scenario Outline: End of the Game In Draw - Hundred Moves Without Moving a Pawn or Capturing
    Given the game is played up to a certain point from file "<file_name>"
    #the number of consecutive indecisive moves is 99
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

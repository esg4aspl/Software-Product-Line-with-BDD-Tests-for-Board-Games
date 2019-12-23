Feature: Spanish Checkers

  Background: 
    Given the "Spanish Checkers" game is set up

  Scenario: Start of the Game
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

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
    When the player makes a "valid capture" move
    Then the piece is moved to the destination coordinate
    And the captured opponent piece is removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name       | next_turn_player | piece_type | explanation                                                                                                                   |
      | validJumpMove1  | next ingame      | pawn       | f: end of jump possibilities, own piece is not jumpable                                                                       |
      | validJumpMove2  | next ingame      | queen      | f: end of jump possibilities, own piece is not jumpable                                                                       |
      | validJumpMove3  | next ingame      | pawn       | f: end of jump possibilities, no adjacent piece                                                                               |
      | validJumpMove4  | current          | queen      | f: another jump possibility                                                                                                   |
      | validJumpMove5  | current          | pawn       | f: another jump possibility                                                                                                   |
      | validJumpMove6  | next ingame      | pawn       | f: end of jump possibilities, opponent is not jumpable because destination would be out of borders                            |
      | validJumpMove7  | current          | queen      | f: another jump possibility                                                                                                   |
      | validJumpMove8  | current          | queen      | f: another jump possiblity, opponent piece in distance                                                                        |
      | validJumpMove9  | next ingame      | queen      | f: this valid jump move proves the case in validRegularMove4                                                                  |
      | validJumpMove10 | next ingame      | pawn       | f: this valid jump move proves the case in validRegularMove6                                                                  |
      | validJumpMove11 | next ingame      | pawn       | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw                             |
      | validJumpMove12 | next ingame      | pawn       | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw                           |
      | validJumpMove13 | next ingame      | pawn       | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw                             |
      | validJumpMove14 | next ingame      | pawn       | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw                           |
      | validJumpMove15 | next ingame      | queen      | f: end of jump possiblities, opponent is not jumpable because possible destination is occupied                                |
      | validJumpMove16 | current          | queen      | f: another jump possibility, even though the destination is in crownhead, the piece is already queen, it can continue jumping |
      | validJumpMove17 | next ingame      | pawn       | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end ind draw             |
      | validJumpMove18 | current          | queen      | f: queen can jump and capture from distance                                                                                   |
      | validJumpMove19 | next ingame      | queen      | s: queen can jump and capture from distance                                                                                   |
      | validJumpMove20 | current          | pawn       | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove21)                   |
      | validJumpMove21 | current          | pawn       | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove20)                   |

  Scenario Outline: Invalid Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "invalid" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another move

    Examples: 
      | file_name                                                                  | invalidity_reason                                                        | error_message                                                               |
      | invalidSourceCoordinateForMoveOutsideBorders1                              | source coordinate is outside of the board                                | No piece at source coordinate                                               |
      | invalidSourceCoordinateForMoveOutsideBorders2                              | source coordinate is outside of the board                                | No piece at source coordinate                                               |
      | invalidSourceCoordinateForMoveUnplayableColor1                             | source coordinate is not of valid square color                           | No piece at source coordinate                                               |
      | invalidSourceCoordinateForMoveUnplayableColor2                             | source coordinate is not of valid square color                           | No piece at source coordinate                                               |
      | invalidSourceCoordinateForMoveEmpty1                                       | source coordinate is empty                                               | No piece at source coordinate                                               |
      | invalidSourceCoordinateForMoveEmpty2                                       | source coordinate is empty                                               | No piece at source coordinate                                               |
      | invalidSourceCoordinateForMoveEmpty3                                       | source coordinate is empty                                               | No piece at source coordinate                                               |
      | invalidSourceCoordinateForMoveOpponentsPiece1                              | source coordinate has opponent's piece                                   | Piece does not belong to current player                                     |
      | invalidSourceCoordinateForMoveOpponentsPiece2                              | source coordinate has opponent's piece                                   | Piece does not belong to current player                                     |
      | invalidSourceCoordinateForMoveOpponentsPiece3                              | source coordinate has opponent's piece                                   | Piece does not belong to current player                                     |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | source coordinate of move is different than last jump move’s destination | Destination valid? false                                                    |
      | invalidDestinationCoordinateForMoveOutsideBorders1                         | destination coordinate is outside of the board                           | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveOutsideBorders2                         | destination coordinate is outside of the board                           | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveUnplayableColor1                        | destination coordinate is not of valid square color                      | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveUnplayableColor2                        | destination coordinate is not of valid square color                      | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveOccupied1                               | destination coordinate is occupied                                       | A piece at destination coordinate                                           |
      | invalidDestinationCoordinateForMoveOccupied2                               | destination coordinate is occupied                                       | A piece at destination coordinate                                           |
      | invalidDestinationCoordinateForMoveOccupied3                               | destination coordinate is occupied                                       | A piece at destination coordinate                                           |
      | invalidDestinationCoordinateForMoveUnallowedDirection1                     | destination coordinate's direction is not allowed                        | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveUnallowedDirection2                     | destination coordinate's direction is not allowed                        | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveUnallowedDirection3                     | destination coordinate's direction is not allowed                        | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveTooFarAway1                             | destination coordinate is more than two squares away                     | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveTooFarAway2                             | destination coordinate is more than two squares away                     | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1                      | jumped piece is null                                                     | There must be one piece on jump path 0                                      |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1                  | jumped piece is not opponent piece                                       | Jumped Piece Must Be Opponent Piece                                         |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromSource1         | jumped piece is too far away from source coordinate                      | Destination Valid? false                                                    |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromDestination1    | destination coordinate is more than one square away from jumped piece    | Must land just behind jumped piece                                          |
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1                   | there are more than one pieces in jump path                              | There must be only one piece on jump path 2                                 |
      | invalidDestinationCoordinateForMoveNotBestSequence1                        | move is not part of the best sequence                                    | Not the best move                                                           |
      | invalidDestinationCoordinateForMoveNotBestSequence2                        | move is not part of the best sequence                                    | Not the best move                                                           |
      | invalidDestinationCoordinateForMoveNotBestSequence3                        | move is not part of the best sequence                                    | Not the best move                                                           |
      | invalidDestinationCoordinateForMoveOppositeDirectionOfLastMove1            | move direction is opposite of last jump move's direction                 | If any opponent's pieces can be captured then it must be captured first!!!! |
      | invalidDestinationCoordinateForMoveNotBestSequence4                        | move is not part of the best sequence                                    | Not the best move                                                           |

  Scenario Outline: Crowning the Eligible Piece
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move to opponent's crownhead with a pawn
    Then the piece is "promoted" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

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
    #only one piece of the opponent is present at the game board
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
    #the player has only one piece on the game board
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples: 
      | file_name                                  |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping
    Given the game is played up to a certain point from file "<file_name>"
    #the number of consecutive indecisive moves is 39
    When the player makes a "valid regular" move
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

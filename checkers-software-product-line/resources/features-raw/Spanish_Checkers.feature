Feature: Spanish Checkers

  Background: 
    Given the "Spanish Checkers" game is set up

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
      | validRegularMove1 | queen      |
      | validRegularMove2 | pawn       |
      | validRegularMove3 | pawn       |
      | validRegularMove4 | pawn       |
      | validRegularMove5 | pawn       |
      | validRegularMove6 | pawn       |
      | validRegularMove7 | queen      |
      | validRegularMove8 | queen      |

  Scenario Outline: Valid Jump Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name       | next_turn_player | piece_type |
      | validJumpMove1  | other            | pawn       |
      | validJumpMove2  | other            | queen      |
      | validJumpMove3  | other            | pawn       |
      | validJumpMove4  | current          | queen      |
      | validJumpMove5  | current          | pawn       |
      | validJumpMove6  | other            | pawn       |
      | validJumpMove7  | current          | queen      |
      | validJumpMove8  | other            | queen      |
      | validJumpMove9  | other            | queen      |
      | validJumpMove10 | other            | pawn       |
      | validJumpMove11 | other            | pawn       |
      | validJumpMove12 | other            | pawn       |
      | validJumpMove13 | other            | pawn       |
      | validJumpMove14 | other            | pawn       |
      | validJumpMove15 | other            | queen      |
      | validJumpMove16 | current          | queen      |
      | validJumpMove17 | other            | pawn       |
      | validJumpMove18 | current          | queen      |
      | validJumpMove19 | other            | queen      |

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
      | file_name                                                               | piece_type | invalidity_reason                                                     | error_message                                   |  
      | invalidDestinationCoordinateForMoveOutsideBorders1                      | pawn       | destination coordinate is outside of the board                        | Destination Valid? false                        |  
      | invalidDestinationCoordinateForMoveOutsideBorders2                      | pawn       | destination coordinate is outside of the board                        | Destination Valid? false                        |  
      | invalidDestinationCoordinateForMoveUnplayableColor1                     | pawn       | destination coordinate is not of valid square color                   | Destination Valid? false                        |  
      | invalidDestinationCoordinateForMoveUnplayableColor2                     | pawn       | destination coordinate is not of valid square color                   | Destination Valid? false                        |  
      | invalidDestinationCoordinateForMoveOccupied1                            | pawn       | destination coordinate is occupied                                    | A piece at destination coordinate               |  
      | invalidDestinationCoordinateForMoveOccupied2                            | pawn       | destination coordinate is occupied                                    | A piece at destination coordinate               |  
      | invalidDestinationCoordinateForMoveOccupied3                            | pawn       | destination coordinate is occupied                                    | A piece at destination coordinate               |  
      | invalidDestinationCoordinateForMoveUnallowedDirection1                  | pawn       | destination coordinate's direction is not allowed                     | Destination Valid? false                        |  
      | invalidDestinationCoordinateForMoveUnallowedDirection2                  | pawn       | destination coordinate's direction is not allowed                     | Destination Valid? false                        |  
      | invalidDestinationCoordinateForMoveUnallowedDirection3                  | pawn       | destination coordinate's direction is not allowed                     | Destination Valid? false                        |  
      | invalidDestinationCoordinateForMoveTooFarAway1                          | pawn       | destination coordinate is more than two squares away                  | Destination Valid? false                        |  
      | invalidDestinationCoordinateForMoveTooFarAway2                          | pawn       | destination coordinate is more than two squares away                  | Destination Valid? false                        |  
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1                   | pawn       | jumped piece is null                                                  | There must be one piece on jump path 0          |  
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1               | pawn       | jumped piece is not opponent piece                                    | Jumped Piece Must Be Opponent Piece             |  
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromSource1      | pawn       | jumped piece is too far away from source coordinate                   | Destination Valid? false                        |  
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromDestination1 | queen      | destination coordinate is more than one square away from jumped piece | Must land just behind jumped piece              |  
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1                | queen      | there are more than one pieces in jump path                           | There must be only one piece on jump path [num] |  
      | invalidDestinationCoordinateForMoveNotBestSequence1                     | queen      | move is not part of the best sequence                                 | Not the best move                               |  
      | invalidDestinationCoordinateForMoveNotBestSequence2                     | queen      | move is not part of the best sequence                                 | Not the best move                               |  
      | invalidDestinationCoordinateForMoveNotBestSequence3                     | queen      | move is not part of the best sequence                                 | Not the best move                               |  

  Scenario Outline: Crowning the Eligible Piece
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece at the source coordinate becomes a crowned piece
    And the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

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

Feature: Spanish Checkers

  Background: 
    Given the "Spanish Checkers" game is set up

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

  Scenario Outline: Valid Jump Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name      | next_turn_player |
      | validJumpMove1 | other            |

  Scenario Outline: Invalid Source Coordinate for Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                     | invalidity_reason                         | error_message                 |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate |

  Scenario Outline: Invalid Destination Coordinate for Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                                               | invalidity_reason                                                     | error_message                                   |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromSource1      | jumped piece is too far away from source coordinate                   | Destination Valid? false                        |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromDestination1 | destination coordinate is more than one square away from jumped piece | Must land just behind jumped piece              |
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1                | there are more than one pieces in jump path                           | There must be only one piece on jump path [num] |
      | invalidDestinationCoordinateForMoveNotBestSequence                      | move is not part of the best sequence                                 | Not the best move                               |

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

  Scenario Outline: End of the Game
    Given the game is played up to a certain point from file "<file_name>"
    And only one piece of the opponent is present at the game board
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples: 
      | file_name     |
      | endOfTheGame1 |

  Scenario Outline: End of the Game In Draw
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples: 
      | file_name           | offer_response | result                                     |
      | endOfTheGameInDraw1 | accepts        | the game is ended as a draw                |
      | endOfTheGameInDraw2 | rejects        | the next turn is given to the other player |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece
    Given the game is played up to a certain point from file "<file_name>"
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples: 
      | file_name                                  |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 39
    When the player makes a regular move without promoting
    Then the game is ended as a draw

    Examples: 
      | file_name                               |
      | endOfTheGameInDrawFortyIndecisiveMoves1 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples: 
      | file_name                               |
      | endOfTheGameOpponentCantMakeAValidMove1 |

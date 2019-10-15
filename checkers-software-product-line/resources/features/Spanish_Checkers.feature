Feature: Spanish Checkers


  Background:
    Given the "Spanish Checkers" game is set up

  Scenario: Start of the Game (2) (uid:18cb697b-29ea-4331-b8c3-8314afb408e4)
    When the players start the game
    Then the player with the dark-colored pieces is given the turn

  Scenario Outline: Valid Regular Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | validRegularMove1 | uid:76b7eaad-7292-453a-b38a-5979068b7e7a |

  Scenario Outline: Valid Jump Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | hiptest-uid |
      | validJumpMove1 | other | uid:f1e276a7-bcce-495d-8ae1-d06c05c82da3 |

  Scenario Outline: Invalid Source Coordinate for Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:cc161d31-d94f-445f-bc02-9a1d4603097d |

  Scenario Outline: Invalid Destination Coordinate for Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromSource1 | jumped piece is too far away from source coordinate | Destination Valid? false | uid:e4ef7554-32d0-4dc4-a56d-8e95cb31384b |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromDestination1 | destination coordinate is more than one square away from jumped piece | Must land just behind jumped piece | uid:7f489435-4dd3-4df2-9385-49f185196b60 |
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1 | there are more than one pieces in jump path | There must be only one piece on jump path [num] | uid:6478610c-01df-4250-b3b5-44a9b480a03f |
      | invalidDestinationCoordinateForMoveNotBestSequence | move is not part of the best sequence | Not the best move | uid:4259efc8-775a-40ff-b034-87d621d8efa8 |

  Scenario Outline: Crowning the Eligible Piece (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a pawn piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece at the source coordinate becomes a crowned piece
    And the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | crowningTheEligiblePiece1 | uid:301d7970-e5ef-4ec9-9527-c6b7a997ec16 |

  Scenario Outline: End of the Game (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And only one piece of the opponent is present at the game board
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:2f7881c8-fa18-4332-a7e6-99d5a8d69af9 |

  Scenario Outline: End of the Game In Draw (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:5bf90073-bb28-4f38-8b51-589f11628210 |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the other player | uid:13b74a1f-94d4-4f0f-be26-9f77a8bbb800 |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 | uid:87c0231c-2307-4599-a211-893583edf730 |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 39
    When the player makes a regular move without promoting
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | uid:c383e1d4-83d4-42dc-973f-42b5ef5fb1ac |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:69acb856-1454-49df-b510-fcb399dbee2d |

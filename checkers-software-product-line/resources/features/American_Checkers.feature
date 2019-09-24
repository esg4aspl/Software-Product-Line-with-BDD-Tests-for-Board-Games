Feature: American Checkers


  Background:
    Given the "American Checkers" game is set up

  Scenario: Start of the Game (uid:29e7e813-631c-4e8b-b40c-ea1a47a3df45)
    When the players start the game
    Then the player with the dark-colored pieces is given the turn

  Scenario Outline: Valid Regular Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | validRegularMove1 | uid:14cb1355-0af3-49d3-b7a6-51133fd74364 |
      | validRegularMove2 | uid:af9ca253-73e3-4a31-8f9e-f5a6e9605902 |
      | validRegularMove3 | uid:dd894aea-97d9-4ac9-900f-0065aa470adb |
      | validRegularMove4 | uid:e89c658b-f914-45a1-a7c4-b126a9a64453 |
      | validRegularMove5 | uid:716ab29f-e9b7-40fc-a460-d28392f4273f |

  Scenario Outline: Valid Jump Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "two" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | hiptest-uid |
      | validJumpMove1 | other | uid:20888809-bc01-42bf-8820-527bff4ebddd |
      | validJumpMove2 | other | uid:6fbd1fa8-e5ff-49bd-8cb4-3b3281934785 |
      | validJumpMove3 | other | uid:4316cef3-a33b-443c-9cce-10109f4217f0 |
      | validJumpMove4 | current | uid:aea16597-291b-4fdb-aea2-0a9f32b409cc |
      | validJumpMove5 | current | uid:e5e21b57-c813-425a-be74-574d4dfbc7e7 |

  Scenario Outline: Invalid Source Coordinate for Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:a9ec6999-0678-4154-834c-969810b8cba4 |
      | invalidSourceCoordinateForMoveUnplayableColor1 | source coordinate is not of valid square color | No piece at source coordinate | uid:4b018f06-fb90-4d2d-8915-8e93c183f77d |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:5fda3a9a-042c-4d0f-b80e-e84bc5bf7792 |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:6e859ee7-2868-49b3-ada2-d3ba5018eb78 |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:ee334b2b-ec6e-4c77-a55b-35260eeb8045 |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:f7a73b79-f95e-4715-9abf-093d525f98cb |

  Scenario Outline: Invalid Destination Coordinate for Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | destination coordinate is outside of the board | Destination Valid? false | uid:18dbcbef-5cee-4238-9049-1398515b14ca |
      | invalidDestinationCoordinateForMoveUnplayableColor1 | destination coordinate is not of valid square color | Destination Valid? false | uid:1b88c08a-67b5-4158-9bfc-b580c011b5f2 |
      | invalidDestinationCoordinateForMoveOccupied1 | destination coordinate is occupied | A piece at destination coordinate | uid:033130e7-7d0b-4fb5-9740-59c77552024d |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | destination coordinate's direction is not allowed | Destination Valid? false | uid:267ed809-9fa4-4c90-95e7-f9b68e301c94 |
      | invalidDestinationCoordinateForMoveTooFarAway1 | destination coordinate is more than two squares away | Destination Valid? false | uid:886e29a3-b50f-4698-90b2-693d5495da8b |
      | invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves1 | move is not a jump move even though there are possible jump moves | If any opponent's pieces can be captured then it must be captured first!!!! | uid:da00ddda-74da-4d40-b933-7670539f9b07 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | jumped piece is null | There must be one piece on jump path 0 | uid:fa89591d-1911-4c4d-abaf-4ab4e8d583ce |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | jumped piece is not opponent piece | Jumped Piece Must Be Opponent Piece | uid:875b4902-ab32-4b9b-bede-d656722a0708 |

  Scenario Outline: Crowning the Eligible Piece (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a pawn piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece at the source coordinate becomes a king piece
    And the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | crowningTheEligiblePiece1 | uid:49be234c-e7a2-4dcc-8666-709c7fa5c7b9 |
      | crowningTheEligiblePiece2 | uid:718addf2-52c2-4897-8e3a-95dda58bb60d |
      | crowningTheEligiblePiece3 | uid:5efcaa48-92f3-4f6c-a99f-d712a4505ea0 |
      | crowningTheEligiblePiece4 | uid:eafd3c78-e036-48a6-8ca7-ac52b4689fa6 |
      | crowningTheEligiblePiece5 | uid:0f9fe1b9-038b-45dc-b4e1-9cce778db199 |

  Scenario Outline: End of the Game (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And only one piece of the opponent is present at the game board
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:e3cdd31b-df84-40c7-9cf6-b0ee33be2ac4 |
      | endOfTheGame2 | uid:174ca7d0-b756-4333-9e02-98a2d111d982 |
      | endOfTheGame3 | uid:0f84fc41-0adf-41f4-824a-fa6e90719525 |
      | endOfTheGame4 | uid:950581a9-d84f-4ae3-8d5d-95d91b57228e |
      | endOfTheGame5 | uid:fe61c905-94bf-453e-9666-e07ebe8e1e5a |

  Scenario Outline: End of the Game In Draw (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And none of the players can force a win on the other player
    When one player offers the other to end the game in a draw
    And the other player accepts the offer
    Then the game ends in a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDraw1 | uid:a94e962e-29d6-4f74-8b74-26371952511c |
      | endOfTheGameInDraw2 | uid:b818109a-e741-410e-9f3f-312c4aada157 |
      | endOfTheGameInDraw3 | uid:1a8029ee-6e8c-455d-8179-0d248eb8e244 |
      | endOfTheGameInDraw4 | uid:74c6a712-c2f8-4d28-8289-890b9d1c5f54 |
      | endOfTheGameInDraw5 | uid:cb582ac8-ff70-466d-be46-e0cc04900680 |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Becoming King (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player moves a normal piece to a non-crownhead coordinate
    Then the number of moves without upgrade is incremented by 1
    And the game is ended as a draw if the number of moves without upgrade is 40

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawFortyMovesWithoutBecomingKing1 | uid:49fa6e41-6714-4871-a0ae-472eebc4112d |
      | endOfTheGameInDrawFortyMovesWithoutBecomingKing2 | uid:c34d3f05-82d0-4ae9-ab91-52e42b4f9083 |
      | endOfTheGameInDrawFortyMovesWithoutBecomingKing3 | uid:5a2ad867-3cc8-41d4-be22-0752d45ee0db |
      | endOfTheGameInDrawFortyMovesWithoutBecomingKing4 | uid:141baf0b-ab44-4401-8c46-ed2d15835dea |
      | endOfTheGameInDrawFortyMovesWithoutBecomingKing5 | uid:a8254e63-85b3-4059-8304-2abdcdfa3a9d |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece (<hiptest-uid>)
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces of the opponent
    Then the game is ended a draw if the opponent still has one piece on the game board

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 | uid:8160c2ac-c8e8-4c07-9f12-83bdb147c7a2 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 | uid:fc4947e9-33da-4829-b36e-79102b679618 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece3 | uid:d3217d60-b247-41b1-aeec-d473abdd5eb4 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece4 | uid:c8bd5b89-1cfa-4fa5-b5ef-6c45011d2c6e |
      | endOfTheGameInDrawBothPlayersHaveOnePiece5 | uid:d3f63b14-0914-4c5c-b445-5fb72467a0b7 |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Jumps (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player moves a piece without undertaking an opponent piece
    Then the number of moves without undertake is incremented by 1
    And the game is ended as in draw if the number of moves without undertake is 40

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawFortyMovesWithoutJumps1 | uid:81320562-5487-44aa-bc4e-faba2b5a4274 |
      | endOfTheGameInDrawFortyMovesWithoutJumps2 | uid:0da5b531-3274-44b1-adf1-5c9717a6e31b |
      | endOfTheGameInDrawFortyMovesWithoutJumps3 | uid:b3737bad-c6df-429e-913e-291f5b272fd5 |
      | endOfTheGameInDrawFortyMovesWithoutJumps4 | uid:6c2a98ee-ae66-4197-8730-a36bcaf6c0e6 |
      | endOfTheGameInDrawFortyMovesWithoutJumps5 | uid:077ae0f5-eebc-43f7-8faa-6ffa0d73be7d |

  Scenario Outline: End of Game - Opponent Can't Make a Valid Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:596e83da-0e72-4acd-8469-53575343d2f5 |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:6e556aca-4962-4867-8179-8fbbad2c9149 |
      | endOfTheGameOpponentCantMakeAValidMove3 | uid:6f7d14da-aae1-4876-92f6-47c38cc0ef1f |
      | endOfTheGameOpponentCantMakeAValidMove4 | uid:72e5c64c-80b8-43cb-ab10-7d61148f00c2 |
      | endOfTheGameOpponentCantMakeAValidMove5 | uid:94f147bc-c8f1-4aa4-8d60-843ef86e1677 |

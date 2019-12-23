Feature: Spanish Checkers


  Background:
    Given the "Spanish Checkers" game is set up

  Scenario: Start of the Game (2) (uid:897cd45e-f3c6-4491-a146-55a791f4773d)
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | piece_type | explanation | hiptest-uid |
      | validRegularMove1 | queen | f: queen moves backwards | uid:698b4246-7e41-4f9b-9ddb-8f42d22ff60b |
      | validRegularMove2 | pawn | f: regular move | uid:8af677f0-b153-40ce-b502-815cd6afcec5 |
      | validRegularMove3 | pawn | f: regular move | uid:95cc7830-2d40-4d9b-a5ac-29bc87b85c42 |
      | validRegularMove4 | pawn | f: opponent is not blocked, his queen can jump backward, game should not end | uid:9ae49a7a-6a02-4fb9-9f19-e01bab8caba1 |
      | validRegularMove5 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:9e1ac97e-932e-4234-bad1-89c551022508 |
      | validRegularMove6 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:88eb4cf0-5832-4b29-abd5-8b99f1d158b3 |
      | validRegularMove7 | queen | f: queen can jump multiple squares without capturing | uid:a1cd65a9-ce2c-4631-992b-9cd44e94460e |
      | validRegularMove8 | queen | f: queen can jump multiple squares without capturing | uid:2e859856-9d31-48d6-bda6-6921f3bdfb3d |

  Scenario Outline: Valid Jump Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid capture" move
    Then the piece is moved to the destination coordinate
    And the captured opponent piece is removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | piece_type | explanation | hiptest-uid |
      | validJumpMove1 | next ingame | pawn | f: end of jump possibilities, own piece is not jumpable | uid:0536e573-941d-4821-abaf-1bca6d7c148b |
      | validJumpMove2 | next ingame | queen | f: end of jump possibilities, own piece is not jumpable | uid:c73aa553-7a93-4776-ba33-3b97234a17e3 |
      | validJumpMove3 | next ingame | pawn | f: end of jump possibilities, no adjacent piece | uid:293dc4dc-e904-4f98-83fb-2d24555ee4fa |
      | validJumpMove4 | current | queen | f: another jump possibility | uid:5dff4379-7586-4ebe-a3a2-49cd86e9f452 |
      | validJumpMove5 | current | pawn | f: another jump possibility | uid:8db05b71-88b1-4876-880c-00e7e549af73 |
      | validJumpMove6 | next ingame | pawn | f: end of jump possibilities, opponent is not jumpable because destination would be out of borders | uid:00d1ca7b-b69e-4429-a208-212caeba8966 |
      | validJumpMove7 | current | queen | f: another jump possibility | uid:bf01c5fc-5280-4d1a-8598-7d59bf18b342 |
      | validJumpMove8 | current | queen | f: another jump possiblity, opponent piece in distance | uid:441154e0-cb1b-424d-857a-53225732470f |
      | validJumpMove9 | next ingame | queen | f: this valid jump move proves the case in validRegularMove4 | uid:590b0e14-fcf5-4863-b307-613dc8384617 |
      | validJumpMove10 | next ingame | pawn | f: this valid jump move proves the case in validRegularMove6 | uid:42be2fc3-8fc0-4be3-b0ea-edd37fb6baa1 |
      | validJumpMove11 | next ingame | pawn | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:1344a101-a69c-4d46-918f-ab168904a1cf |
      | validJumpMove12 | next ingame | pawn | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw | uid:ebdc437e-ced2-497d-90a9-9b75c40e8936 |
      | validJumpMove13 | next ingame | pawn | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:bdc5fd6e-a872-49d7-90f5-942bd40a77bd |
      | validJumpMove14 | next ingame | pawn | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw | uid:01f6beb1-7adc-430f-91f1-c56943b2cb47 |
      | validJumpMove15 | next ingame | queen | f: end of jump possiblities, opponent is not jumpable because possible destination is occupied | uid:58c2bf66-7579-4b9b-94a9-5518673dc0e8 |
      | validJumpMove16 | current | queen | f: another jump possibility, even though the destination is in crownhead, the piece is already queen, it can continue jumping | uid:92a883fc-4538-462c-a036-bcee03c1acce |
      | validJumpMove17 | next ingame | pawn | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end ind draw | uid:af384fa5-a049-4c99-b6cb-f74edb31c0fe |
      | validJumpMove18 | current | queen | f: queen can jump and capture from distance | uid:4fcf024a-add5-4eda-81d9-5e18211f8a51 |
      | validJumpMove19 | next ingame | queen | s: queen can jump and capture from distance | uid:36b06373-b16d-4a2c-ae09-c40f8acc2da5 |
      | validJumpMove20 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove21) | uid:d0981334-99df-4adf-8e06-7b5331db1331 |
      | validJumpMove21 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove20) | uid:f45a07ad-27da-4d4f-83a7-e981a6882013 |

  Scenario Outline: Crowning the Eligible Piece (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move to opponent's crownhead with a pawn
    Then the piece is "promoted" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | explanation | hiptest-uid |
      | crowningTheEligiblePiece1 | f: | uid:785246c3-fa9c-4545-886c-cba1ec6fb592 |
      | crowningTheEligiblePiece2 | f: | uid:caf0f554-8ada-4d58-9127-4a044b7cba08 |
      | crowningTheEligiblePiece3 | f: there are possibilities for a jump move but piece is crowned, so the next turn should be given to the opponent | uid:0b5330ca-b8b2-4dd7-ba94-e3d649a50a89 |
      | crowningTheEligiblePiece4 | f:          | uid:56ec4270-2652-42fc-a2c3-ed10f7ae0d38 |
      | crowningTheEligiblePiece5 | f: there are possibilities for a jump move but piece is crowned, so the next turn should be given to the opponent      | uid:61658248-e07e-4bd5-9689-651cb647610b |
      | crowningTheEligiblePiece6 | f: no promote is 39, no capture is 39, crowning is a decisive move, game should not end in draw          | uid:ff04cd7a-15aa-4582-8ac2-7e425e6ebe27 |
      | crowningTheEligiblePiece7 | f: no promote is 45, no capture is 0, crowning is a decisive move, game should not end in draw          | uid:77c541f0-0cb8-4e64-8597-bade5f8e1c1b |
      | crowningTheEligiblePiece8 | f: no promote is 0, no capture is 45, crowning is a decisive move, game should not end in draw        | uid:408a318e-9e3d-4760-8309-e8ea71932088 |

  Scenario Outline: End of the Game (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:5d81886f-1099-4fd5-8bd3-970e42f8d01a |
      | endOfTheGame2 | uid:4cb487a2-d2ca-4b50-a793-fd1a124ad655 |

  Scenario Outline: End of the Game In Draw (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:bfc89c86-8ed4-4048-a6c4-221a7da14098 |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the next ingame player | uid:faae0fbb-819b-4a4a-9bf5-d298b1eb444c |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:0c218a3c-43ea-4b01-bad2-fbaef7b4a0fe |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:5f056747-ac12-4019-9da1-628735f5df50 |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the next ingame player | uid:0569899b-cbbf-469f-b265-4fc210a51445 |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 | uid:08162829-32c3-4a4e-aa72-90135d56f67a |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 | uid:becb5044-8ee7-4d03-9cb0-50d618b51b4c |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the game is ended as a draw

    Examples:
      | file_name | explanation | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | f: no promote is 39, no capture is 39, a regular move ends the game in draw | uid:6b041f25-fbc6-41e3-b080-e9deda2a9ef9 |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | f: no promote is 45, no capture is 39, a regular move ends the game in draw | uid:e74624b3-0c0e-4d42-a1ba-ec18c590592d |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | f: no promote is 39, no capture is 45, a regular move ends the game in draw | uid:86cf6883-7ee2-4f3d-805f-d8a144e60e13 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:004aed0d-5cf0-4682-91dc-0095f0c0051c |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:2599c8bb-2aeb-47f3-b182-f9d8c3b6baca |

  Scenario Outline: Offer Draw Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples:
      | file_name | hiptest-uid |
      | offerDrawMove1 | uid:4a954718-908f-44f6-8893-fcc4bf79107f |

  Scenario Outline: Invalid Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "invalid" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another move

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:97c75646-b0f6-4f2e-96de-c32e7abc0ce2 |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate | uid:eb936188-1ec7-495f-9ad5-d5bf278109aa |
      | invalidSourceCoordinateForMoveUnplayableColor1 | source coordinate is not of valid square color | No piece at source coordinate | uid:63c81c0a-3b14-4bf3-a37b-9e67f786b54b |
      | invalidSourceCoordinateForMoveUnplayableColor2 | source coordinate is not of valid square color | No piece at source coordinate | uid:79a0bb42-bc20-4aae-931c-7b55bbadf0dc |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:2d2bb0ca-9918-4056-a16f-a4c1f7c75e05 |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:ed930002-6117-4ac6-a35c-99d362c1fba1 |
      | invalidSourceCoordinateForMoveEmpty3 | source coordinate is empty | No piece at source coordinate | uid:f9bfd510-4584-4f56-beac-81f9e882fe4a |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:4fd99859-c251-4f06-8bd7-c333e27bead3 |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:07aeca31-9e23-48e9-97dc-d0dd1480768a |
      | invalidSourceCoordinateForMoveOpponentsPiece3 | source coordinate has opponent's piece | Piece does not belong to current player | uid:e1d2a670-09ab-4d3c-b377-9f4aec62fb88 |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | source coordinate of move is different than last jump move’s destination | Destination valid? false | uid:16a67ac8-5f09-47c6-a70c-259a279eca09 |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | destination coordinate is outside of the board | Destination Valid? false | uid:3f315aa4-cac7-4237-8956-26fd80606fbe |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | destination coordinate is outside of the board | Destination Valid? false | uid:36b7a4fa-1b1f-49e0-bfbd-84964a68a727 |
      | invalidDestinationCoordinateForMoveUnplayableColor1 | destination coordinate is not of valid square color | Destination Valid? false | uid:bd0df84a-b21e-4644-9574-0226bfbb3a7b |
      | invalidDestinationCoordinateForMoveUnplayableColor2 | destination coordinate is not of valid square color | Destination Valid? false | uid:c5eec932-c3f9-4cda-90dd-9ba437128277 |
      | invalidDestinationCoordinateForMoveOccupied1 | destination coordinate is occupied | A piece at destination coordinate | uid:d50ab779-92da-4191-9b24-ae64a8d3c0ef |
      | invalidDestinationCoordinateForMoveOccupied2 | destination coordinate is occupied | A piece at destination coordinate | uid:9727e05b-1a3f-49e4-8926-bcd2ba2c623c |
      | invalidDestinationCoordinateForMoveOccupied3 | destination coordinate is occupied | A piece at destination coordinate | uid:79747553-b609-4f42-9f37-a395c5dbb9f4 |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | destination coordinate's direction is not allowed | Destination Valid? false | uid:6689fe21-cd67-4367-a9b1-5f314cfac2e3 |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | destination coordinate's direction is not allowed | Destination Valid? false | uid:6071b9af-1302-4b49-b262-4e824d5c519f |
      | invalidDestinationCoordinateForMoveUnallowedDirection3 | destination coordinate's direction is not allowed | Destination Valid? false | uid:286315bb-aaca-459c-aadc-ab1a7de2f360 |
      | invalidDestinationCoordinateForMoveTooFarAway1 | destination coordinate is more than two squares away | Destination Valid? false | uid:cab2dff0-f0fb-4229-9f93-d5676b18b3a3 |
      | invalidDestinationCoordinateForMoveTooFarAway2 | destination coordinate is more than two squares away | Destination Valid? false | uid:90a55471-3bb2-4847-aa06-ccfdc19f2e31 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | jumped piece is null | There must be one piece on jump path 0 | uid:b7224fcf-5d9e-44ea-b82e-69d33f54dd0d |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | jumped piece is not opponent piece | Jumped Piece Must Be Opponent Piece | uid:c5b68e15-29c6-4f12-8c8e-bfd4d5c27d06 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromSource1 | jumped piece is too far away from source coordinate | Destination Valid? false | uid:220071a7-6831-4741-904e-debacb22057b |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromDestination1 | destination coordinate is more than one square away from jumped piece | Must land just behind jumped piece | uid:fab49fab-dfcd-4ae9-9a9d-aae169bddb7c |
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1 | there are more than one pieces in jump path | There must be only one piece on jump path 2 | uid:e3195226-ca6b-4285-9b6b-6a466b178e4c |
      | invalidDestinationCoordinateForMoveNotBestSequence1 | move is not part of the best sequence | Not the best move | uid:ba41f18a-9eb0-4769-9b81-7aee83934e93 |
      | invalidDestinationCoordinateForMoveNotBestSequence2 | move is not part of the best sequence | Not the best move | uid:940b34d4-89b3-4b91-ac39-b9ccc40119f2 |
      | invalidDestinationCoordinateForMoveNotBestSequence3 | move is not part of the best sequence | Not the best move | uid:0fdcf441-2878-444c-a02c-2e273747e61d |
      | invalidDestinationCoordinateForMoveOppositeDirectionOfLastMove1 | move direction is opposite of last jump move's direction | If any opponent's pieces can be captured then it must be captured first!!!! | uid:36278841-6aaa-40fb-b881-753526039907 |
      | invalidDestinationCoordinateForMoveNotBestSequence4 | move is not part of the best sequence | Not the best move | uid:9ce28be9-f90d-40f4-8d71-2eb24d89e149 |

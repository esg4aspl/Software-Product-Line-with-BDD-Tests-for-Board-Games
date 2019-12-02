Feature: Spanish Checkers


  Background:
    Given the "Spanish Checkers" game is set up

  Scenario: Start of the Game (2) (uid:18cb697b-29ea-4331-b8c3-8314afb408e4)
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | piece_type | explanation | hiptest-uid |
      | validRegularMove1 | queen | f: queen moves backwards | uid:917034c8-ccb3-40e7-a798-700cb364209e |
      | validRegularMove2 | pawn | f: regular move | uid:c9f31438-37d6-4f6d-8887-ebe23e9c0cff |
      | validRegularMove3 | pawn | f: regular move | uid:38c5317a-8f94-4d75-9cdf-319c617e48e8 |
      | validRegularMove4 | pawn | f: opponent is not blocked, his queen can jump backward, game should not end | uid:45b9fbdb-b86c-402d-bdb2-d9c33da50873 |
      | validRegularMove5 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:686cab70-654f-44b0-a167-c40af46aabc8 |
      | validRegularMove6 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:900a345c-6d05-4556-bfda-d21ddfacd243 |
      | validRegularMove7 | queen | f: queen can jump multiple squares without capturing | uid:cb4d2565-7935-49c0-a5f1-4e41270c8746 |
      | validRegularMove8 | queen | f: queen can jump multiple squares without capturing | uid:2157eedd-ab5f-4a8c-9a07-d8b70f140dbf |

  Scenario Outline: Valid Jump Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | piece_type | explanation | hiptest-uid |
      | validJumpMove1 | next ingame | pawn | f: end of jump possibilities, own piece is not jumpable | uid:6756d709-b7bf-4b25-b991-fa4e7ddb8282 |
      | validJumpMove2 | next ingame | queen | f: end of jump possibilities, own piece is not jumpable | uid:5b65e672-2791-41b2-b58a-0299c985cc7a |
      | validJumpMove3 | next ingame | pawn | f: end of jump possibilities, no adjacent piece | uid:79a7f46c-1960-48b4-a038-d68aaac6d616 |
      | validJumpMove4 | current | queen | f: another jump possibility | uid:2cf04211-3a4f-48a5-ab51-44a4fbc9bf75 |
      | validJumpMove5 | current | pawn | f: another jump possibility | uid:173a43d3-38e2-4aad-aad9-89df0f2a306c |
      | validJumpMove6 | next ingame | pawn | f: end of jump possibilities, opponent is not jumpable because destination would be out of borders | uid:4b7eacf4-c942-45c8-861d-e5fb3604d092 |
      | validJumpMove7 | current | queen | f: another jump possibility | uid:188158b7-1d2b-4c8e-91aa-268ed021aab8 |
      | validJumpMove8 | current | queen | f: another jump possiblity, opponent piece in distance | uid:997bd2ea-9b74-4840-9c04-56dc4affca8e |
      | validJumpMove9 | next ingame | queen | f: this valid jump move proves the case in validRegularMove4 | uid:f1a19ba8-1cc3-49f2-b67a-12022d8debc7 |
      | validJumpMove10 | next ingame | pawn | f: this valid jump move proves the case in validRegularMove6 | uid:6cdf4487-eaf0-4dfe-b53f-086194fbe687 |
      | validJumpMove11 | next ingame | pawn | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:b3d28a0a-e2e4-4f7b-b8c9-c824378dece5 |
      | validJumpMove12 | next ingame | pawn | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw | uid:c95e3347-f69d-410a-8edf-d3c81f23b443 |
      | validJumpMove13 | next ingame | pawn | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:a0f217de-54c6-488b-92ff-d345c61b3bfb |
      | validJumpMove14 | next ingame | pawn | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw | uid:33ca25ff-e461-4b36-b18b-753cb153e5d0 |
      | validJumpMove15 | next ingame | queen | f: end of jump possiblities, opponent is not jumpable because possible destination is occupied | uid:2d34f719-c9a1-4742-bc2b-5e8f20ee8f78 |
      | validJumpMove16 | current | queen | f: another jump possibility, even though the destination is in crownhead, the piece is already queen, it can continue jumping | uid:8ddc2a0d-9ff1-436d-add8-09d2d019e809 |
      | validJumpMove17 | next ingame | pawn | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end ind draw | uid:9d1172c3-9541-4118-8054-7ad3c2b74422 |
      | validJumpMove18 | current | queen | f: queen can jump and capture from distance | uid:7c1d91ad-28ea-40ae-9a73-acb86443292c |
      | validJumpMove19 | next ingame | queen | s: queen can jump and capture from distance | uid:9d3ea5d1-97d7-4db7-98d8-a74255f05a6a |
      | validJumpMove20 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove21) | uid:90efbd22-b689-4e2c-8804-21eab5bd1d2f |
      | validJumpMove21 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove20) | uid:8c730bed-7819-46a2-b8b4-d04315c76d5a |

  Scenario Outline: Invalid Source Coordinate for Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:cc161d31-d94f-445f-bc02-9a1d4603097d |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate | uid:78be4431-c774-4d65-b6e9-afe5b4ec36b3 |
      | invalidSourceCoordinateForMoveUnplayableColor1 | source coordinate is not of valid square color | No piece at source coordinate | uid:a5ea3767-93e3-498e-a0d2-08534b72adc6 |
      | invalidSourceCoordinateForMoveUnplayableColor2 | source coordinate is not of valid square color | No piece at source coordinate | uid:1507cd7f-69a4-4f1d-9257-f365239c2be5 |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:40cc16c6-0ba6-4f69-ab52-868851489680 |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:cc58a79f-489c-460f-9bdd-a7d7c89165e2 |
      | invalidSourceCoordinateForMoveEmpty3 | source coordinate is empty | No piece at source coordinate | uid:f87ed037-ad51-4d13-9107-fa0a6a7996aa |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:bfe90c14-3020-4bc0-b8ea-2ba26aa82b4f |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:44d092a9-94a5-4c77-ac26-fb4c25871fc5 |
      | invalidSourceCoordinateForMoveOpponentsPiece3 | source coordinate has opponent's piece | Piece does not belong to current player | uid:10ca179a-240e-4eb0-81c7-d6fe18011e0d |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | source coordinate of move is different than last jump move’s destination | Destination valid? false | uid:d14626cd-25b2-486c-a2b2-a4c8cdbf494c |

  Scenario Outline: Invalid Destination Coordinate for Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | piece_type | invalidity_reason | error_message | hiptest-uid |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | pawn | destination coordinate is outside of the board | Destination Valid? false | uid:49042f6c-35f3-4f38-baf2-bfdae8003d82 |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | pawn | destination coordinate is outside of the board | Destination Valid? false | uid:d62f7005-2242-4af0-9541-8a5a6acd57c8 |
      | invalidDestinationCoordinateForMoveUnplayableColor1 | pawn | destination coordinate is not of valid square color | Destination Valid? false | uid:8395e109-06ff-4737-ace7-a8263cc64348 |
      | invalidDestinationCoordinateForMoveUnplayableColor2 | pawn | destination coordinate is not of valid square color | Destination Valid? false | uid:f31628ad-16ca-4d90-9b79-daceb1356dc5 |
      | invalidDestinationCoordinateForMoveOccupied1 | pawn | destination coordinate is occupied | A piece at destination coordinate | uid:26b6de24-8e62-44aa-b05e-a1936e3bb22b |
      | invalidDestinationCoordinateForMoveOccupied2 | pawn | destination coordinate is occupied | A piece at destination coordinate | uid:0ebd2924-f0c2-4393-bff0-99e8bfee0322 |
      | invalidDestinationCoordinateForMoveOccupied3 | pawn | destination coordinate is occupied | A piece at destination coordinate | uid:bae174b2-bc49-4f1a-aeb8-51e1d5a18ae2 |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | pawn | destination coordinate's direction is not allowed | Destination Valid? false | uid:80a99d75-3479-48c3-bf5b-133aad251daf |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | pawn | destination coordinate's direction is not allowed | Destination Valid? false | uid:bed67893-7274-4972-aa8f-b468b3b78b2a |
      | invalidDestinationCoordinateForMoveUnallowedDirection3 | pawn | destination coordinate's direction is not allowed | Destination Valid? false | uid:655b06c8-4381-49dc-8233-e70dcf7359be |
      | invalidDestinationCoordinateForMoveTooFarAway1 | pawn | destination coordinate is more than two squares away | Destination Valid? false | uid:f087375f-3534-4b5e-8c49-283ada14b6ab |
      | invalidDestinationCoordinateForMoveTooFarAway2 | pawn | destination coordinate is more than two squares away | Destination Valid? false | uid:ebe75773-7bb7-4ffc-92c6-31ef5e5eb4f2 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | pawn | jumped piece is null | There must be one piece on jump path 0 | uid:3dc80346-8890-4173-9005-60da97147dd8 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | pawn | jumped piece is not opponent piece | Jumped Piece Must Be Opponent Piece | uid:b718fe28-1b7c-411e-805c-30857992bbac |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromSource1 | pawn | jumped piece is too far away from source coordinate | Destination Valid? false | uid:869d48ff-832e-4ee0-8b6c-57d508bbefa3 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromDestination1 | queen | destination coordinate is more than one square away from jumped piece | Must land just behind jumped piece | uid:55ec7b73-5b16-47d7-8983-31b0721113fd |
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1 | queen | there are more than one pieces in jump path | There must be only one piece on jump path 2 | uid:5d4af1ac-fe97-4ce9-9a27-3a2501df76c2 |
      | invalidDestinationCoordinateForMoveNotBestSequence1 | queen | move is not part of the best sequence | Not the best move | uid:6e0eec37-65b6-473f-bf6f-2edc6abf5ce2 |
      | invalidDestinationCoordinateForMoveNotBestSequence2 | queen | move is not part of the best sequence | Not the best move | uid:72ae11e8-ff3d-415e-a234-3eb8c0c5db7c |
      | invalidDestinationCoordinateForMoveNotBestSequence3 | queen | move is not part of the best sequence | Not the best move | uid:f6c2c203-8871-4d9d-b840-700ef3bd6847 |
      | invalidDestinationCoordinateForMoveOppositeDirectionOfLastMove1 | queen | move direction is opposite of last jump move's direction | If any opponent's pieces can be captured then it must be captured first!!!! | uid:07a3304b-55dc-4e49-bd10-30c54d8cd455 |

  Scenario Outline: Crowning the Eligible Piece (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece is "promoted" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | explanation | hiptest-uid |
      | crowningTheEligiblePiece1 | f: | uid:301d7970-e5ef-4ec9-9527-c6b7a997ec16 |
      | crowningTheEligiblePiece2 | f: | uid:6323d752-ce74-4682-b034-1738296052ca |
      | crowningTheEligiblePiece3 | f: there are possibilities for a jump move but piece is crowned, so the next turn should be given to the opponent | uid:22c130e3-b11e-4643-8fd1-d751dd2cbee4 |
      | crowningTheEligiblePiece4 | f:          | uid:13006862-8489-4373-a111-828f838fa578 |
      | crowningTheEligiblePiece5 | f: there are possibilities for a jump move but piece is crowned, so the next turn should be given to the opponent      | uid:35d245b4-c9af-4285-9759-98ae5ae3007d |
      | crowningTheEligiblePiece6 | f: no promote is 39, no capture is 39, crowning is a decisive move, game should not end in draw          | uid:20679bb1-60a1-4125-aa59-1cdcc63677c2 |
      | crowningTheEligiblePiece7 | f: no promote is 45, no capture is 0, crowning is a decisive move, game should not end in draw          | uid:1b2d2e52-661f-4203-934b-59fd559c9cc5 |
      | crowningTheEligiblePiece8 | f: no promote is 0, no capture is 45, crowning is a decisive move, game should not end in draw        | uid:8cd7de02-605f-4c02-8958-b14e40c4e6b1 |

  Scenario Outline: End of the Game (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And only one piece of the opponent is present at the game board
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:2f7881c8-fa18-4332-a7e6-99d5a8d69af9 |
      | endOfTheGame2 | uid:63ec0119-c358-48f7-b85c-474a7fdf89aa |

  Scenario Outline: End of the Game In Draw (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:5bf90073-bb28-4f38-8b51-589f11628210 |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the next ingame player | uid:e446f94d-6efc-4347-878b-ef72e8fc00af |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:357a9330-e75d-4ac0-a558-147c2757ca2e |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:7123c542-cf09-4b69-80a8-a44ae48a00aa |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the next ingame player | uid:9044ea9b-2104-45ce-8fe2-e9e5ff3ad269 |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 | uid:87c0231c-2307-4599-a211-893583edf730 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 | uid:aaadb2cc-7c09-466a-b512-56b0ff59067a |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 39
    When the player makes a regular move without promoting
    Then the game is ended as a draw

    Examples:
      | file_name | explanation | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | f: no promote is 39, no capture is 39, a regular move ends the game in draw | uid:c383e1d4-83d4-42dc-973f-42b5ef5fb1ac |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | f: no promote is 45, no capture is 39, a regular move ends the game in draw | uid:71cd9231-4ab8-427b-8455-4d9d99c2b9dd |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | f: no promote is 39, no capture is 45, a regular move ends the game in draw | uid:7e9c2d99-fea0-4609-9b48-c99dab823347 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:69acb856-1454-49df-b510-fcb399dbee2d |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:6646321d-3bb4-4a12-b4ab-0a135c99c84f |

  Scenario Outline: Offer Draw Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples:
      | file_name | hiptest-uid |
      | offerDrawMove1 | uid:898107f5-a4f5-41a4-8232-4a5a485755a8 |

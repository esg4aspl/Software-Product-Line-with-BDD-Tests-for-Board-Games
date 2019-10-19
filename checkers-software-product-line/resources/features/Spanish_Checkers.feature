Feature: Spanish Checkers


  Background:
    Given the "Spanish Checkers" game is set up

  Scenario: Start of the Game (2) (uid:18cb697b-29ea-4331-b8c3-8314afb408e4)
    When the players start the game
    Then the player with the dark-colored pieces is given the turn

  Scenario Outline: Valid Regular Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples:
      | file_name | piece_type | hiptest-uid |
      | validRegularMove1 | queen | uid:917034c8-ccb3-40e7-a798-700cb364209e |
      | validRegularMove2 | pawn | uid:c9f31438-37d6-4f6d-8887-ebe23e9c0cff |
      | validRegularMove3 | pawn | uid:38c5317a-8f94-4d75-9cdf-319c617e48e8 |
      | validRegularMove4 | pawn | uid:45b9fbdb-b86c-402d-bdb2-d9c33da50873 |
      | validRegularMove5 | pawn | uid:686cab70-654f-44b0-a167-c40af46aabc8 |
      | validRegularMove6 | pawn | uid:900a345c-6d05-4556-bfda-d21ddfacd243 |
      | validRegularMove7 | queen | uid:cb4d2565-7935-49c0-a5f1-4e41270c8746 |
      | validRegularMove8 | queen | uid:2157eedd-ab5f-4a8c-9a07-d8b70f140dbf |

  Scenario Outline: Valid Jump Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | piece_type | hiptest-uid |
      | validJumpMove1 | other | pawn | uid:f1e276a7-bcce-495d-8ae1-d06c05c82da3 |
      | validJumpMove2 | other | queen | uid:66a1710e-7f83-4fc0-9a5b-aa140d2b990b |
      | validJumpMove3 | other | pawn | uid:7a734891-4740-4e85-b519-90932721af14 |
      | validJumpMove4 | current | queen | uid:2cf04211-3a4f-48a5-ab51-44a4fbc9bf75 |
      | validJumpMove5 | current | pawn | uid:173a43d3-38e2-4aad-aad9-89df0f2a306c |
      | validJumpMove6 | other | pawn | uid:894088e3-f839-47ef-87d2-5a27fba32dad |
      | validJumpMove7 | current | queen | uid:188158b7-1d2b-4c8e-91aa-268ed021aab8 |
      | validJumpMove8 | other | queen | uid:5125ef2c-f3f8-4896-9fd5-06c64262639e |
      | validJumpMove9 | other | queen | uid:1d404770-45fd-4e69-8e61-f399ce22bc87 |
      | validJumpMove10 | other | pawn | uid:e9f9cab4-361e-4d9a-bac0-3002bc05236e |
      | validJumpMove11 | other | pawn | uid:1318dfee-21a7-4c61-a8bb-272e9899cd76 |
      | validJumpMove12 | other | pawn | uid:858d33aa-f4c4-4462-a83d-8eacb81fa7a9 |
      | validJumpMove13 | other | pawn | uid:1af0bbfe-dfff-4443-9ef5-9b26a2b13df8 |
      | validJumpMove14 | other | pawn | uid:e5b79582-0911-4f4e-bea6-142e4262338c |
      | validJumpMove15 | other | queen | uid:535fd921-6dd2-4518-9639-2f8267763b5b |
      | validJumpMove16 | current | queen | uid:8ddc2a0d-9ff1-436d-add8-09d2d019e809 |
      | validJumpMove17 | other | pawn | uid:bb43b844-c98b-4462-8c4c-d7e6ff2a8845 |
      | validJumpMove18 | current | queen | uid:7c1d91ad-28ea-40ae-9a73-acb86443292c |
      | validJumpMove19 | other | queen | uid:08004cef-db70-4a96-86e5-934e983fdefb |

  Scenario Outline: Invalid Source Coordinate for Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
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

  Scenario Outline: Invalid Destination Coordinate for Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
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
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1 | queen | there are more than one pieces in jump path | There must be only one piece on jump path [num] | uid:c2c5a9e2-c6c1-45de-bea8-de9a7d2d35f4 |
      | invalidDestinationCoordinateForMoveNotBestSequence1 | queen | move is not part of the best sequence | Not the best move | uid:6e0eec37-65b6-473f-bf6f-2edc6abf5ce2 |
      | invalidDestinationCoordinateForMoveNotBestSequence2 | queen | move is not part of the best sequence | Not the best move | uid:9f109fbf-a044-4d05-a0ef-3fb36105760f |
      | invalidDestinationCoordinateForMoveNotBestSequence3 | queen | move is not part of the best sequence | Not the best move | uid:f6c2c203-8871-4d9d-b840-700ef3bd6847 |

  Scenario Outline: Crowning the Eligible Piece (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece at the source coordinate becomes a crowned piece
    And the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples:
      | file_name | piece_type | hiptest-uid |
      | crowningTheEligiblePiece1 | pawn | uid:301d7970-e5ef-4ec9-9527-c6b7a997ec16 |
      | crowningTheEligiblePiece2 | pawn | uid:6323d752-ce74-4682-b034-1738296052ca |
      | crowningTheEligiblePiece3 | pawn | uid:22c130e3-b11e-4643-8fd1-d751dd2cbee4 |
      | crowningTheEligiblePiece4 | pawn | uid:13006862-8489-4373-a111-828f838fa578 |
      | crowningTheEligiblePiece5 | pawn | uid:35d245b4-c9af-4285-9759-98ae5ae3007d |
      | crowningTheEligiblePiece6 | pawn | uid:20679bb1-60a1-4125-aa59-1cdcc63677c2 |
      | crowningTheEligiblePiece7 | pawn | uid:1b2d2e52-661f-4203-934b-59fd559c9cc5 |
      | crowningTheEligiblePiece8 | pawn | uid:8cd7de02-605f-4c02-8958-b14e40c4e6b1 |

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
      | endOfTheGameInDraw2 | rejects | the next turn is given to the other player | uid:13b74a1f-94d4-4f0f-be26-9f77a8bbb800 |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:357a9330-e75d-4ac0-a558-147c2757ca2e |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:7123c542-cf09-4b69-80a8-a44ae48a00aa |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the other player | uid:2892285d-a986-44a5-9719-9dbd8fca9b13 |

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
      | file_name | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | uid:c383e1d4-83d4-42dc-973f-42b5ef5fb1ac |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | uid:71cd9231-4ab8-427b-8455-4d9d99c2b9dd |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | uid:7e9c2d99-fea0-4609-9b48-c99dab823347 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:69acb856-1454-49df-b510-fcb399dbee2d |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:6646321d-3bb4-4a12-b4ab-0a135c99c84f |

Feature: Chess


  Background:
    Given the "Chess" game is set up

  Scenario: Start of the Game (4) (uid:7fed4ada-0472-47c2-b849-11d31845dc4a)
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move (5) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | piece_type | explanation | hiptest-uid |
      | validRegularMovePawn1 | pawn |  | uid:49af2692-f38f-4d34-a624-18be0f4c294b |
      | validRegularMovePawn2 | pawn | pawn can move two squares in its starting position | uid:8946d821-f0ac-4314-8d76-5f6fbbb33fb7 |
      | validRegularMoveRook1 | rook |  | uid:074c57dc-5928-4282-9d27-39e74cbc9f66 |
      | validRegularMoveRook2 | rook |  | uid:dcaa980a-87d8-460b-822f-9c225366e13f |
      | validRegularMoveKnight1 | knight |  | uid:094f91a6-a0b2-4957-8ba5-4baaaa8c469e |
      | validRegularMoveKnight2 | knight | move is made even though there is piece on path | uid:066fbb65-1ae7-487c-875d-a0bb8dfcc0be |
      | validRegularMoveBishop1 | bishop |  | uid:029e4306-6290-42ca-bb89-f080cd89fae2 |
      | validRegularMoveBishop2 | bishop |  | uid:ed050121-ecb1-4833-9b62-aad1ebb0a554 |
      | validRegularMoveQueen1 | queen |  | uid:65109849-a908-426b-8707-b3f1ce48300b |
      | validRegularMoveQueen2 | queen | this move proves the case in validRegularMoveNotEnd2 | uid:d51eac5a-33c1-4e79-8f55-d2d1c620dbb7 |
      | validRegularMoveKing1 | king |  | uid:fb435fe5-a9be-4e49-9631-7340a6294cc3 |
      | validRegularMoveKing2 | king | this move proves the case in validRegularMoveNotEnd1 | uid:e61172e7-e699-45ac-b809-ffc73bfde04c |
      | validRegularMoveNotEnd1 | rook | opponent is not checkmated, his king can escape, game should not be ended | uid:b0f5c257-82a5-41be-9bf9-9d454e8335e0 |
      | validRegularMoveNotEnd2 | bishop | opponent is not checkmated, his king can be protected by another piece by blocking, game should not be ended | uid:1f01a247-fda5-45c9-bbf6-4b5374246d12 |
      | validRegularMoveNotEnd3 | rook | opponent is not checkmated, his king can be protected by another piece by capture, game should not be ended | uid:d650116c-187f-4fc2-b173-e2bb5087e95a |
      | validRegularMoveNotEnd4 | pawn | no pawn move is 99, no capture is 99, pawn move is a decisive move, game should not end in draw          | uid:b2ab7cf2-824f-4ebc-b0f4-4c783c8e26b5 |
      | validRegularMoveNotEnd5 | pawn | no pawn move is 105, no capture is 0, pawn move is a decisive move, game should not end in draw          | uid:cdde4130-c544-4ece-a3c9-f4f9e6c506be |
      | validRegularMoveNotEnd6 | pawn | no pawn move is 0, no capture is 105, pawn move is a decisive move, game should not end in draw        | uid:6c5eab8d-8b23-4914-b7bb-aec3738de4ba |

  Scenario Outline: Valid Capture Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid capture" move
    Then the piece is moved to the destination coordinate
    And the captured opponent piece is removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | explanation | hiptest-uid |
      | validCaptureMove1 | regular capture by pawn | uid:ee3c2910-03dd-46f6-a1ea-91a6f62c883c |
      | validCaptureMove2 | regular capture by pawn | uid:258b0ec2-30d6-4f6e-978e-c8d9d03393c2 |
      | validCaptureMove3 | move en passant by pawn | uid:ac856010-19f6-4e05-a9ad-51ef6f55354a |
      | validCaptureMove4 |  | uid:e21dffe6-4449-4c74-abbd-40b2e53bae2f |
      | validCaptureMove5 |  | uid:77b607f2-ca5f-4680-8043-7d4460ac0f7a |
      | validCaptureMove6 | this capture proves the case in validRegularMoveNotEnd3 | uid:5b4c3774-7b58-4631-9377-a2392edab30d |
      | validCaptureMove7 |  | uid:95ed57e5-4465-4cd2-ba9a-d3193d5ae57c |
      | validCaptureMove8 |  | uid:90c5f26f-73b3-4cec-8da1-c6b1b1f42290 |
      | validCaptureMoveNotEnd1 | no pawn move is 99, no capture is 0, a capture move is a decisive move, game should not end in draw | uid:c4549e4e-4713-4aea-a422-71fbc4eb5886 |
      | validCaptureMoveNotEnd2 | no pawn move is 0, no capture is 99, this capture move should clear that, game should not end in draw | uid:3a9d9094-edfa-48f2-a4dd-cac552f3c8ba |
      | validCaptureMoveNotEnd3 | no pawn move is 105, no capture is 0, a capture move is a decisive move, game should not end in draw | uid:e6a1f1ff-631c-4578-b691-73520646b266 |
      | validCaptureMoveNotEnd4 | no pawn move is 0, no capture is 105, this capture move should clear that, game should not end in draw | uid:606bb818-6736-4bae-aa67-e31735f7ff72 |

  Scenario Outline: Crowning the Eligible Piece (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move to opponent's crownhead with a pawn
    Then the piece is "promoted" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | hiptest-uid |
      | crowningTheEligiblePiece1 | uid:e73dbefd-8a65-4a63-accf-f477f90022a6 |
      | crowningTheEligiblePiece2 | uid:1402df8c-0e92-429f-bdd5-64054d1128da |

  Scenario Outline: End of the Game (5) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move that threatens the opponent's king's current position
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:149b5f87-69cf-4ba1-8ffd-50a6726e5f02 |
      | endOfTheGame2 | uid:4346e227-8296-4040-8ab8-c27c329a72ff |

  Scenario Outline: End of the Game In Draw - Stalemate (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move that not checks the opponent king but leaves it with no valid move to make
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameStalemate1 | uid:eb322a7f-1d3e-4e64-93e8-9cb1a5c6bd4d |
      | endOfTheGameStalemate2 | uid:1ca4443a-0f62-48d0-94a8-9f1874e08234 |

  Scenario Outline: End of the Game In Draw - Same Board State Reached for the Third Time (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player finishes his turn leaving the board in a previously reached state
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawResultingBoardStateWillBeReachedForTheThirdTime1 | uid:0a8ae1a7-d0d9-4223-bdce-0f3877fa9e0e |

  Scenario Outline: End of the Game In Draw - Hundred Moves Without Moving a Pawn or Capturing (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player moves a non-pawn piece without capturing an opponent piece
    Then the game is ended as a draw

    Examples:
      | file_name | explanation | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | f: no pawn move is 99, no capture is 99, a regular move ends the game in draw | uid:f1f9d718-1073-4621-b0bd-775210b1725b |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | f: no pawn move is 105, no capture is 99, a regular move ends the game in draw | uid:d5e0c2bf-8cbb-40a3-9a55-c15b1a7463b2 |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | f: no pawn move is 99, no capture is 105, a regular move ends the game in draw | uid:35924768-51f0-42ce-8b59-f997c6281b0b |

  Scenario Outline: Offer Draw Move (4) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples:
      | file_name | hiptest-uid |
      | offerDrawMove1 | uid:aefe949f-fe43-45d7-a050-974085781011 |

  Scenario Outline: End of the Game In Draw (4) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:70a20218-b5b9-42a6-8e38-05cc5905b716 |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the next ingame player | uid:30f6365f-62d1-4dce-8b2c-0ef179b6a795 |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:69ee79b4-2ef2-4dfd-9d5b-f69957d7d422 |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:4633f5e9-25d7-49a7-9041-e5643b92673d |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the next ingame player | uid:ea34e066-f2e2-4289-826c-b02306132adc |

  Scenario Outline: Valid Castling Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid castling" move
    Then the piece is moved to the destination coordinate
    And the rook is moved to the adjacent coordinate that is towards the center

    Examples:
      | file_name | hiptest-uid |
      | validLeftCastlingMove1 | uid:3d9ca8e8-dfb5-452f-9168-a676b74ed433 |
      | validRightCastlingMove1 | uid:1845fb53-0a50-41ed-85b9-987968a5a1ff |

  Scenario Outline: Invalid Castling Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "invalid castling" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"

    Examples:
      | file_name | error_message | hiptest-uid |
      | invalidCastlingMove1 | cannot castle because king has moved before | uid:0cfca75a-e24f-4544-9e7c-48729cd1ffca |
      | invalidCastlingMove2 | cannot castle because rook has moved before | uid:d25aa67c-bfd7-4f58-9857-671fd0ec1472 |
      | invalidCastlingMove3 | cannot castle because there are pieces in between | uid:453faa53-3188-40d0-9820-cd1926309b62 |

  Scenario Outline: Invalid Move (5) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "invalid" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another move

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:d6ef1394-0b35-4d89-a8d3-6eb918f42704 |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate | uid:adf604a3-95b0-4f3b-8156-90012a57b2f9 |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:68e3c684-4330-44de-83ca-6c592ada54da |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:60ff9094-cc1b-4b1f-b00c-fe9a9e5834fa |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:9d225789-65cc-4e23-a82d-84a5e61f2736 |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:0d07ce17-881d-4067-adb1-2dd3c517881c |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | destination coordinate is outside of the board | Destination Valid? false | uid:bbaea260-3a28-4822-87a4-d3998b40dee7 |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | destination coordinate is outside of the board | Destination Valid? false | uid:12b11484-0a5e-4b66-8394-63569fb1bb39 |
      | invalidDestinationCoordinateForMoveCapturedPieceIsOwn1 | captured piece is own | You can only capture opponent piece!!!! | uid:1f7ee1a6-a025-4d45-be74-48d114e34fc9 |
      | invalidDestinationCoordinateForMoveCapturedPieceIsOwn2 | captured piece is own | You can only capture opponent piece!!!! | uid:f4784ef0-0583-4fe8-99a1-1439ade0b5c3 |
      | invalidDestinationCoordinateForMoveSameAsSource1 | destination coordinate is same as source coordinate | Destination Valid? false | uid:ebb4da6e-deaa-4c18-a75a-952ece3011df |
      | invalidDestinationCoordinateForMoveSameAsSource2 | destination coordinate is same as source coordinate | Destination Valid? false | uid:26c15cc4-9e55-4971-8ea1-64f268d8d18d |
      | invalidDestinationCoordinateForMoveKingNotProtected1 | threatened king is not protected | invalid : King has to be free | uid:dd7bdc20-fa40-4d89-abf2-72f5494f1979 |
      | invalidDestinationCoordinateForMoveKingNotProtected2 | threatened king is not protected | invalid : King has to be free | uid:382e139a-54d2-4265-a249-d6713efcfc07 |
      | invalidDestinationCoordinateForPawn1 | can not move more than two squares from starting position | Destination Valid? false | uid:76e02b8f-d405-4f0c-864f-b52b98e2e745 |
      | invalidDestinationCoordinateForPawn2 | can not move more than one square if not in starting position | Destination Valid? false | uid:0fc5acee-fc8f-4a33-b758-d3e03e3a9288 |
      | invalidDestinationCoordinateForPawn3 | can not move in non-forward-vertical directions (cross) | Pawn cannot move to cross if there is no piece on destination coordinate! | uid:9f5cecaf-66bb-4dff-95c9-d569717f8e2d |
      | invalidDestinationCoordinateForPawn4 | can not move in non-forward-vertical directions (sideways) | Destination Valid? false | uid:086b2b69-0d2a-4f05-bed0-3562c14b0b2f |
      | invalidDestinationCoordinateForPawn5 | can not move in non-forward-vertical directions (backwards) | Destination Valid? false | uid:b16b96dd-aeeb-4258-bb78-195993767f6d |
      | invalidDestinationCoordinateForPawn6 | can not move in non-forward-vertical directions (asymmetrical) | Destination Valid? false | uid:504ee270-f40e-4cdd-ab4f-277480d5c751 |
      | invalidDestinationCoordinateForPawn7 | can not perform capture in a non-cross move | Pawn cannot move to straight if there is pawn on destination coordinate | uid:8bcbb603-4c7b-4902-ac82-d30d5277481a |
      | invalidDestinationCoordinateForPawn8 | move en passant is not valid | Pawn cannot move to cross if there is no piece on destination coordinate! | uid:1af49161-38eb-4292-b4cf-b7d603e927c7 |
      | invalidDestinationCoordinateForPawn9 | there is piece on path | There must be no piece on the move path!!!. | uid:6d6d151a-6fc2-42af-abc8-061ad31c3682 |
      | invalidDestinationCoordinateForRook1 | can not move in non-orthogonal directions (cross) | Destination Valid? false | uid:87e92ee1-70b2-4bec-a9f6-511064b37e6d |
      | invalidDestinationCoordinateForRook2 | can not move in non-orthogonal directions (asymmetrical) | Destination Valid? false | uid:cfe018ba-f01d-4d47-874b-8d300284d841 |
      | invalidDestinationCoordinateForRook3 | there is piece on path | There must be no piece on the move path!!!. | uid:33763ef0-a1db-4790-ab99-79b7c77b7db4 |
      | invalidDestinationCoordinateForKnight1 | can not move in non-L path | Destination Valid? false | uid:a2400ddb-201c-490a-87e6-7ab4ede9d167 |
      | invalidDestinationCoordinateForKnight2 | can not move in non-L path | Destination Valid? false | uid:ec4ad24e-b867-4cc8-8a0d-1ddf2025a06a |
      | invalidDestinationCoordinateForBishop1 | can not move in non-cross directions (orthogonal) | Destination Valid? false | uid:27e2ea9a-85cb-483c-abc1-d1ec294fcbc2 |
      | invalidDestinationCoordinateForBishop2 | can not move in non-cross directions (assymetrical) | Destination Valid? false | uid:80cdde49-98a5-49fa-a595-6f76cd3dfd06 |
      | invalidDestinationCoordinateForBishop3 | there is piece on path | There must be no piece on the move path!!!. | uid:cb6648c2-1729-4ca3-8df5-df2e37faabb1 |
      | invalidDestinationCoordinateForQueen1 | can not move in asymmetrical directions | Destination Valid? false | uid:56a52cff-0624-453e-b33f-38c0cf1860d3 |
      | invalidDestinationCoordinateForQueen2 | there is piece on path | There must be no piece on the move path!!!. | uid:311fc657-f56d-4e08-8319-86b1413ceb49 |
      | invalidDestinationCoordinateForKing1 | can not move more than one square | Destination Valid? false | uid:73cf3b0b-6e13-48f1-8935-d4a76bab012f |
      | invalidDestinationCoordinateForKing2 | king is threatened after move | invalid : King has to be free | uid:1d99212b-2a79-4d04-9b7c-f64293eac8f4 |

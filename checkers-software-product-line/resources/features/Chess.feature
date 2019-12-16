Feature: Chess


  Background:
    Given the "Chess" game is set up

  Scenario: Start of the Game (4) (uid:9b4086ea-5211-45b4-ac9f-ea49e80c974c)
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move (5) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that has no pieces in it
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | piece_type | explanation | hiptest-uid |
      | validRegularMovePawn1 | pawn |  | uid:177630f7-7dea-4074-a58f-3ff6fde193e5 |
      | validRegularMovePawn2 | pawn | pawn can move two squares in its starting position | uid:b8fd9112-9c27-4d85-a897-4412da41b168 |
      | validRegularMoveRook1 | rook |  | uid:6d4ce847-586a-4133-b142-ca3587337351 |
      | validRegularMoveRook2 | rook |  | uid:fa2cbddb-a16f-4984-8a00-374e2317533e |
      | validRegularMoveKnight1 | knight |  | uid:da42f26a-61bf-469a-8644-81e83715d857 |
      | validRegularMoveKnight2 | knight | move is made even though there is piece on path | uid:97df7ed4-dd87-49ff-98fb-1037fc01e39e |
      | validRegularMoveBishop1 | bishop |  | uid:21fafd36-bb23-4a7d-9240-c05b818bd3e7 |
      | validRegularMoveBishop2 | bishop |  | uid:376ea1d1-58e6-4f4c-b574-155d1562d60b |
      | validRegularMoveQueen1 | queen |  | uid:0b1538bf-7c94-4698-9901-742077ac4bdc |
      | validRegularMoveQueen2 | queen | this move proves the case in validRegularMoveNotEnd2 | uid:7815f9ca-adfd-4b6d-bf17-5121848f69d0 |
      | validRegularMoveKing1 | king |  | uid:1936a113-9f9e-4ec5-ac01-55f4b8532f67 |
      | validRegularMoveKing2 | king | this move proves the case in validRegularMoveNotEnd1 | uid:0874a665-0f05-4a5c-b2a1-323e9a0e56b5 |
      | validRegularMoveNotEnd1 | rook | opponent is not checkmated, his king can escape, game should not be ended | uid:ab49186c-8b59-439f-acf0-080fb71aee87 |
      | validRegularMoveNotEnd2 | bishop | opponent is not checkmated, his king can be protected by another piece by blocking, game should not be ended | uid:b88e16ef-acb8-4f4b-a954-dc7b03db405e |
      | validRegularMoveNotEnd3 | rook | opponent is not checkmated, his king can be protected by another piece by capture, game should not be ended | uid:a361917a-d4b1-4b36-93a1-7529d4476f6d |
      | validRegularMoveNotEnd4 | pawn | no pawn move is 99, no capture is 99, pawn move is a decisive move, game should not end in draw          | uid:85acadeb-a657-4a28-a74e-df4556124371 |
      | validRegularMoveNotEnd5 | pawn | no pawn move is 105, no capture is 0, pawn move is a decisive move, game should not end in draw          | uid:f15405be-b4fd-4ccb-8259-a0d81342c717 |
      | validRegularMoveNotEnd6 | pawn | no pawn move is 0, no capture is 105, pawn move is a decisive move, game should not end in draw        | uid:1b098158-1855-462b-8617-9c9c0f173a30 |

  Scenario Outline: Valid Capture Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that has a capturable opponent piece in it
    Then the piece is moved to the destination coordinate
    And the opponent piece is removed from the board
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | piece_type | explanation | hiptest-uid |
      | validCaptureMove1 | pawn | regular capture by pawn | uid:506fdc10-481b-4e5f-8fe5-ffdaaa5ecf2c |
      | validCaptureMove2 | pawn | regular capture by pawn | uid:836b81e0-a585-4ec6-bf11-d06bd88df1fd |
      | validCaptureMove3 | pawn | move en passant by pawn | uid:963a98fb-0f24-4180-87b6-c4a52a084bef |
      | validCaptureMove4 | rook |  | uid:22872ac6-ba18-413d-b2b7-3deef79e2710 |
      | validCaptureMove5 | knight |  | uid:3cfe2f4e-c595-4041-b1b5-13d80d10e678 |
      | validCaptureMove6 | king | this capture proves the case in validRegularMoveNotEnd3 | uid:680955ea-1a01-4e5b-865d-d8afe2c8d562 |
      | validCaptureMove7 | queen |  | uid:93f7dbac-07e0-415e-90a1-05f949ab6477 |
      | validCaptureMove8 | bishop |  | uid:b925be8a-d50c-4ed8-b122-3c1b9b465cb8 |
      | validCaptureMoveNotEnd1 | pawn | no pawn move is 99, no capture is 0, a capture move is a decisive move, game should not end in draw | uid:20dc7c38-7679-437e-bbee-f9e19f79c2c0 |
      | validCaptureMoveNotEnd2 | pawn | no pawn move is 0, no capture is 99, this capture move should clear that, game should not end in draw | uid:1e7bbaf6-b452-4a98-8b01-2f0b8eb33e2f |
      | validCaptureMoveNotEnd3 | pawn | no pawn move is 105, no capture is 0, a capture move is a decisive move, game should not end in draw | uid:46abd6bc-91ed-4dab-8eb9-8186956c1e38 |
      | validCaptureMoveNotEnd4 | pawn | no pawn move is 0, no capture is 105, this capture move should clear that, game should not end in draw | uid:ebc264a1-c5c7-4fc7-ba95-3bb0e0037bb9 |

  Scenario Outline: Invalid Source Coordinate for Move (5) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:ab2d4708-5977-4fe6-9358-5dd542094965 |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate | uid:4a95712b-a466-4a2b-a288-8335ec29cb51 |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:c8f20c74-3bad-4870-8e6b-4b5cccf2f1a2 |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:b3a8742c-bdea-4b6e-b4f2-804c1d97295e |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:9761dafa-3781-463f-af70-72da16577ed6 |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:18b5fd0e-e01f-4fa5-a746-069b75584305 |

  Scenario Outline: Invalid Destination Coordinate for Move (5) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | piece_type | invalidity_reason | error_message | hiptest-uid |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | rook | destination coordinate is outside of the board | Destination Valid? false | uid:10ff3beb-35e2-499e-b4af-d8a6f61bed3d |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | queen | destination coordinate is outside of the board | Destination Valid? false | uid:9ca869ab-01ab-4faa-b90a-1b920a8a04af |
      | invalidDestinationCoordinateForMoveCapturedPieceIsOwn1 | queen | captured piece is own | Destination Valid? false | uid:62c5ad61-96c7-42b3-aabd-b5432ddca7c8 |
      | invalidDestinationCoordinateForMoveCapturedPieceIsOwn2 | bishop | captured piece is own | Destination Valid? false | uid:123efa90-da53-4c52-8f37-3604dae1e44b |
      | invalidDestinationCoordinateForMoveSameAsSource1 | bishop | destination coordinate is same as source coordinate | Destination Valid? false | uid:b44db633-123f-4341-a266-83b4f9ea01fb |
      | invalidDestinationCoordinateForMoveSameAsSource2 | pawn | destination coordinate is same as source coordinate | Destination Valid? false | uid:ab03a303-bd1c-49ee-ab2b-8977c0894060 |
      | invalidDestinationCoordinateForMoveKingNotProtected1 | knight | threatened king is not protected | Destination Valid? false | uid:e99aca48-b2a2-4325-84cd-80e17f88b5bd |
      | invalidDestinationCoordinateForMoveKingNotProtected2 | queen | threatened king is not protected | Destination Valid? false | uid:308c3131-1494-4261-bda2-0e9efb4f53a0 |
      | invalidDestinationCoordinateForPawn1 | pawn | can not move more than two squares from starting position |  | uid:65f7b11d-d8b7-4f79-89e0-c7b90f35b87c |
      | invalidDestinationCoordinateForPawn2 | pawn | can not move more than one square if not in starting position |  | uid:35bc7a50-c120-413b-b806-0b4a5f53ad3d |
      | invalidDestinationCoordinateForPawn3 | pawn | can not move in non-forward-vertical directions (cross) |  | uid:1d1bf9b1-fe3a-495d-a232-7297348c38ad |
      | invalidDestinationCoordinateForPawn4 | pawn | can not move in non-forward-vertical directions (sideways) |  | uid:77d99c78-efbb-44cc-b5c5-5d1a0282754f |
      | invalidDestinationCoordinateForPawn5 | pawn | can not move in non-forward-vertical directions (backwards) |  | uid:981a7efb-51e6-42aa-a7c2-b72d3e5cc908 |
      | invalidDestinationCoordinateForPawn6 | pawn | can not move in non-forward-vertical directions (asymmetrical) |  | uid:95d3f37c-e3cf-4485-8399-8a2347c75e50 |
      | invalidDestinationCoordinateForPawn7 | pawn | can not perform capture in a non-cross move |  | uid:5efe381b-be62-4bb4-af13-71a8e5ff1ca2 |
      | invalidDestinationCoordinateForPawn8 | pawn | move en passant is not valid |  | uid:abe9e677-9b3f-4e86-9c00-42266247be6c |
      | invalidDestinationCoordinateForPawn9 | pawn | there is piece on path |  | uid:1fb9e851-d7b2-4363-9bf8-b8529b1a9a44 |
      | invalidDestinationCoordinateForRook1 | rook | can not move in non-orthogonal directions (cross) |  | uid:dd4e05c8-0f1b-418b-9a0c-ee35ee44a8c5 |
      | invalidDestinationCoordinateForRook2 | rook | can not move in non-orthogonal directions (asymmetrical) |  | uid:f14fb513-343e-45e3-a71d-4748bf140dc9 |
      | invalidDestinationCoordinateForRook3 | rook | there is piece on path |  | uid:139d270f-9321-4202-867e-380ce3f48b4d |
      | invalidDestinationCoordinateForKnight1 | knight | can not move in non-L path |  | uid:6c82272f-5960-4bc7-9912-f68773df8c08 |
      | invalidDestinationCoordinateForKnight2 | knight | can not move in non-L path |  | uid:344ea0d7-3bdb-4d7d-baf9-4fad34ade24d |
      | invalidDestinationCoordinateForBishop1 | bishop | can not move in non-cross directions (orthogonal) |  | uid:a1a5c44a-c6d6-4363-a734-8506ebaacd39 |
      | invalidDestinationCoordinateForBishop2 | bishop | can not move in non-cross directions (assymetrical) |  | uid:5d4f99b9-4384-400a-8d34-5f35d17379dc |
      | invalidDestinationCoordinateForBishop3 | bishop | there is piece on path |  | uid:3a1385ec-b56b-4fcc-8fe5-fb3495837862 |
      | invalidDestinationCoordinateForQueen1 | queen | can not move in asymmetrical directions |  | uid:9331f4ca-00cd-4ee9-91c1-d14cbc7f3305 |
      | invalidDestinationCoordinateForQueen2 | queen | there is piece on path |  | uid:46a09c98-5c93-4ed0-b58c-50ea03c7a9e2 |
      | invalidDestinationCoordinateForKing1 | king | king is threatened after move |  | uid:4d792392-f936-4404-8621-d2cc5fd7b82d |
      | invalidDestinationCoordinateForKing2 | king | cannot castle because king has moved before |  | uid:5ed6e429-ca5a-4e8d-a3df-4381c40cebd8 |
      | invalidDestinationCoordinateForKing3 | king | cannot castle because rook has moved before |  | uid:88e5a30b-3a23-4651-8ade-1a5a8526c3d8 |
      | invalidDestinationCoordinateForKing4 | king | cannot castle because there are pieces in between |  | uid:e967376c-e6be-4725-9f26-9e56b6f9d098 |

  Scenario Outline: Crowning the Eligible Piece (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece is "promoted" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | explanation | hiptest-uid |
      | crowningTheEligiblePiece1 |  | uid:dddc8888-d6da-4207-8b0a-6eef83cd1228 |
      | crowningTheEligiblePiece2 |  | uid:9da426d7-f80f-43e6-b6cd-9117f318f05d |

  Scenario Outline: End of the Game (5) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the opponent's king can not be protected if it is checked
    When the player makes a move that threatens the opponent's king's current position
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:85edec90-531b-424a-a38d-6b8c9e677785 |
      | endOfTheGame2 | uid:b8fd60b7-d3cd-4189-b81f-1bc5c0fd0622 |

  Scenario Outline: End of the Game In Draw - Stalemate (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move that not checks the opponent king but leaves it with no valid move to make
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameStalemate1 | uid:0e06cc30-c217-48ce-8b3b-f93684a52f94 |
      | endOfTheGameStalemate2 | uid:e92aeaa5-694e-4bc3-bb77-cbc87e4307c2 |

  Scenario Outline: End of the Game In Draw - Same Board State Reached for the Third Time (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And there are some board states that have been reached two times
    When the player finishes his turn leaving the board in a previously reached state
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawResultingBoardStateWillBeReachedForTheThirdTime1 | uid:6e94b61b-15c2-4931-8161-23d8f0cb8f93 |

  Scenario Outline: End of the Game In Draw - Hundred Moves Without Moving a Pawn or Capturing (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 99
    When the player moves a non-pawn piece without capturing an opponent piece
    Then the game is ended as a draw

    Examples:
      | file_name | explanation | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | f: no pawn move is 99, no capture is 99, a regular move ends the game in draw | uid:47ede132-f947-4139-bbe0-4f16defb8319 |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | f: no pawn move is 105, no capture is 99, a regular move ends the game in draw | uid:a442dd33-5cb4-4e61-9443-9da1ebc3d08b |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | f: no pawn move is 99, no capture is 105, a regular move ends the game in draw | uid:88cf8004-de8d-40e9-8662-9f17d8f7aa3f |

  Scenario Outline: Offer Draw Move (4) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples:
      | file_name | hiptest-uid |
      | offerDrawMove1 | uid:51adbb97-2214-4462-aa1b-d292f70235bd |

  Scenario Outline: End of the Game In Draw (4) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:39b1a085-d3b6-4d2a-9542-335ba461acce |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the next ingame player | uid:a6c43fb8-164f-4069-a024-ace797b5e05e |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:f8aaa447-7fc2-4767-ba80-a96b97029c30 |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:ec70116e-84a6-46ec-918b-4f74f2c8ea22 |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the next ingame player | uid:4b40abd1-63b1-420f-9b10-e437fbf79e45 |

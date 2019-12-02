Feature: Turkish Checkers


  Background:
    Given the "Turkish Checkers" game is set up

  Scenario: Start of the Game (3) (uid:9da3fb36-0a5f-42c8-90f4-8e6f8a422ad3)
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | piece_type | explanation | hiptest-uid |
      | validRegularMove1 | king | f: king moves backwards | uid:92230b9f-4405-4a77-9894-f92c2ddf2b6b |
      | validRegularMove2 | pawn | f: regular move | uid:7783d338-7d06-4a93-930d-a8b0da9d4110 |
      | validRegularMove3 | pawn | f: regular move | uid:65bb3936-767e-4bd0-82ac-c5257ab992dd |
      | validRegularMove4 | pawn | f: opponent is not blocked, his king can jump backward, game should not end | uid:cfb04b99-ab57-4171-9ad8-67623ebfd20d |
      | validRegularMove5 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:46870b71-dc5e-4b5f-ad97-938f97745a5a |
      | validRegularMove6 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:57b1da21-3454-4d82-a0d2-1c5ffc91bfe6 |
      | validRegularMove7 | king | f: king can jump multiple squares without capturing | uid:1a5f0d0a-d761-4562-8bf0-dd96932de7d0 |
      | validRegularMove8 | king | f: king can jump multiple squares without capturing | uid:0258a9c8-498c-4387-b884-dbb1627b4b0a |

  Scenario Outline: Valid Jump Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | piece_type | explanation | hiptest-uid |
      | validJumpMove1 | next ingame | pawn | f: end of jump possibilities, own piece is not jumpable | uid:ec4aa811-95aa-4bc6-82c5-e5fd720c1a04 |
      | validJumpMove2 | next ingame | king | f: end of jump possibilities, own piece is not jumpable | uid:f6661414-f616-404c-90f2-8503b5c3f517 |
      | validJumpMove3 | next ingame | pawn | f: end of jump possibilities, no adjacent piece | uid:432c9d6c-1239-436d-aeda-e80f5b4fac64 |
      | validJumpMove4 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove5) | uid:0d6881c4-fa16-4f46-bfd1-a0f689380c20 |
      | validJumpMove5 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove4) | uid:558b2de6-6e6c-44e1-8ad9-7bc9a72289d2 |
      | validJumpMove6 | next ingame | pawn | s: end of jump possibilities, opponent is not jumpable because destination would be out of borders | uid:f4cedeff-d09d-44fe-995b-c934b7e3fdd2 |
      | validJumpMove7 | current | king | f: another jump possibility | uid:262190ae-5a0b-47a7-a0de-ec261734fcb4 |
      | validJumpMove8 | current | king | f: another jump possibility, opponent piece in distance | uid:fe105c6e-2e96-4891-9b5f-3ad2d9301915 |
      | validJumpMove9 | next ingame | king | f: this valid jump move proves the case in validRegularMove4 | uid:22dbb1ee-ac2b-4dda-84d2-f14b8a4dabda |
      | validJumpMove10 | next ingame | pawn | f: this valid jump move proves the case in validRegularMove6 | uid:f9cc3cf6-60b8-44c6-a800-2d0f16d1950d |
      | validJumpMove11 | next ingame | king | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:e00ba807-477f-4383-b827-d3ac172943ea |
      | validJumpMove12 | next ingame | king | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw | uid:efe6f895-365e-456c-a813-579e4061cff5 |
      | validJumpMove13 | next ingame | king | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:9c1bbe84-b612-4733-ada3-4898a37c4ede |
      | validJumpMove14 | next ingame | king | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw | uid:e88c16c7-bb29-4406-8896-bf66a37df5b0 |
      | validJumpMove15 | next ingame | king | f: end of jump possibilities, opponent is not jumpable because possible destination is occupied | uid:8fd1d1f4-ec05-4ea7-b01b-ba687016aec8 |
      | validJumpMove16 | current | king | f: another jump possibility, even though the destination is in crownhead, the piece is already king, it can continue jumping, also a similar situation to validJumpMove17 | uid:3ad82263-3651-447d-a018-8bc425559bb4 |
      | validJumpMove17 | next ingame | pawn | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end in draw | uid:ecd45a53-e194-4f7b-bed4-0e87715b952b |
      | validJumpMove18 | current | king | f: king can jump and capture from distance | uid:e1d7287e-b9d1-4c22-b24b-97534d182cf3 |
      | validJumpMove19 | next ingame | king | s: king can jump and capture from distance | uid:d23de378-13ea-4568-96c0-b03f7f33e544 |

  Scenario Outline: Invalid Source Coordinate for Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:e4f03640-3f9d-47fc-b348-680021b7b9ab |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate | uid:40265826-1416-4c91-96ea-c33fe7c75078 |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:8dde8251-7b8e-4cb2-9cd6-cc1b8c09867c |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:478fdb7a-7e3a-4b64-96aa-d25a2708d522 |
      | invalidSourceCoordinateForMoveEmpty3 | source coordinate is empty | No piece at source coordinate | uid:e51450f1-26a7-469e-b9ac-ce527a12ca31 |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:5f9daec9-9d8b-4244-bdc5-1f8858176cd8 |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:1f48aa3c-2c7d-4622-bc32-f1a78db83add |
      | invalidSourceCoordinateForMoveOpponentsPiece3 | source coordinate has opponent's piece | Piece does not belong to current player | uid:fa4ab2e6-a439-4b1b-bf94-1fac52a48971 |
      | invalidSourceCoordinateForMovePawnInCrownhead1 | there is a pawn in crownhead but move is not that | Pawn in crownhead must capture king to be promoted | uid:18cc89d2-37fc-4993-bf80-741e9e23a40a |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | source coordinate of move is different than last jump move’s destination | Destination valid? false | uid:7900d53a-4b74-4f3e-8adb-6f85cc95ccca |

  Scenario Outline: Invalid Destination Coordinate for Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | piece_type | invalidity_reason | error_message | hiptest-uid |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | king | destination coordinate is outside of the board | Destination Valid? false | uid:f889468d-e792-4be1-8006-b4ce6de078e0 |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | king | destination coordinate is outside of the board | Destination Valid? false | uid:0ade8c36-9c46-4280-8109-b2b13bd76c49 |
      | invalidDestinationCoordinateForMoveOccupied1 | king | destination coordinate is occupied | A piece at destination coordinate | uid:e9c18a40-7399-4b1f-9a62-35690a13bef9 |
      | invalidDestinationCoordinateForMoveOccupied2 | king | destination coordinate is occupied | A piece at destination coordinate | uid:9407f348-5cc3-4453-bcb4-49291801c898 |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | pawn | destination coordinate's direction is not allowed | Destination Valid? false | uid:17146409-eb11-4324-925c-0bcb28213cd0 |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | pawn | destination coordinate's direction is not allowed | Destination Valid? false | uid:4261d164-9fad-4001-a6b5-09566e159ce8 |
      | invalidDestinationCoordinateForMoveUnallowedDirection3 | king | destination coordinate's direction is not allowed | Destination Valid? false | uid:43df8a15-df39-4760-a181-4ff39fb86d7f |
      | invalidDestinationCoordinateForMoveTooFarAway1 | pawn | destination coordinate is more than two squares away | Destination Valid? false | uid:d91b3f55-db25-4aee-b580-a5a128ee3d5f |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | pawn | jumped piece is null | There must be one piece on jump path 0 | uid:4b8c794f-e571-4ba0-bb2a-12166839cb0b |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | king | jumped piece is not opponent piece | Jumped Piece Must Be Opponent Piece | uid:3e645861-40cd-48af-b116-0c98a7d2c77f |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromSource1 | pawn | jumped piece is too far away from source coordinate | Destination Valid? false | uid:87d1250e-f55b-4c37-9633-7438697145f6 |
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1 | king | there are more than one pieces in jump path | There must be only one piece on jump path 2 | uid:d7fa1604-b076-4f91-9b48-928221536ed4 |
      | invalidDestinationCoordinateForMoveNotBestSequence1 | king | move is not part of the best sequence | Not the best move | uid:64f91e06-50e2-4c5c-bc54-30c6ad6f8a19 |
      | invalidDestinationCoordinateForMoveNotBestSequence2 | king | move is not part of the best sequence | Not the best move | uid:fa364131-36ad-47b5-a397-dac475f88e22 |
      | invalidDestinationCoordinateForMovePawnInCrownhead1 | pawn | the pawn in crownhead did not capture the vulnerable king | Must capture king to be promoted | uid:405bc021-759c-4c8e-9006-e536f278c02b |
      | invalidDestinationCoordinateForMovePawnInCrownhead2 | pawn | the pawn in crownhead did not capture the vulnerable king | Must capture king to be promoted | uid:d33dd013-2cf4-40c7-8ef3-b52b4fc6cd95 |
      | invalidDestinationCoordinateForMoveOppositeDirectionOfLastMove1 | king | move direction is opposite of last jump move's direction | If any opponent's pieces can be captured then it must be captured first!!!! | uid:8e691b34-d708-4de4-8687-c2df69c4a660 |

  Scenario Outline: Crowning the Eligible Piece (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece is "<action>" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | action | next_turn_player | explanation | hiptest-uid |
      | crowningTheEligiblePiece1 | promoted | next ingame | f: regular move, no adjacent pieces | uid:1ba0e318-27a6-494f-8ac0-8ebbb03a8a3b |
      | crowningTheEligiblePiece2 | promoted | next ingame | f: regular move, adjacent vulnerable opponent king | uid:e541d2f3-8a84-4e91-9b52-991f7d9d748e |
      | crowningTheEligiblePiece3 | promoted | next ingame | f: regular move, adjacent vulnerable own king | uid:6a9f1153-3b59-44b8-ae8a-d628e03c9daa |
      | crowningTheEligiblePiece4 | promoted | next ingame | f: jump move, no adjacent pieces | uid:992cf195-95ad-4ab1-a6ea-dde77f77edba |
      | crowningTheEligiblePiece5 | promoted | next ingame | f: jump move, adjacent vulnerable opponent pawn | uid:cd3b88c3-b4f8-4050-a45c-04b15911c58f |
      | crowningTheEligiblePiece6 | promoted | next ingame | f: jump move, adjacent vulnerable own king | uid:4bee8f60-c03f-414f-896b-992f4d773d03 |
      | crowningTheEligiblePiece7 | promoted | next ingame | f: jump move, adjacent protected opponent king | uid:0eab2d64-2b7b-4d4b-9451-a6e923cac0d6 |
      | crowningTheEligiblePiece8 | promoted | next ingame | f: jump move, far away vulnerable opponent king | uid:66d63672-37b7-46ef-9f9c-e340a86a79d2 |
      | crowningTheEligiblePiece9 | not promoted | current | f: jump move, adjacent vulnerable opponent king, crowning is hold until kings are captured | uid:c0e3cf46-ef2f-4a34-aaca-4991e5007d89 |
      | crowningTheEligiblePiece10 | not promoted | current | f: jump move, adjacent vulnerable opponent kings (multiple), crowning is hold until kings are captured | uid:457800f3-1585-4433-893e-b4377bcbce2a |
      | crowningTheEligiblePiece11 | promoted | next ingame | f: no promote is 39, no capture is 39, crowning is a decisive move, game should not end in draw          | uid:fb71b9d8-94d2-481d-820a-b857e2fec82b |
      | crowningTheEligiblePiece12 | promoted | next ingame | f: no promote is 45, no capture is 0, crowning is a decisive move, game should not end in draw          | uid:dab54e2c-b3f0-4e94-be84-11665c088643 |
      | crowningTheEligiblePiece13 | promoted | next ingame | f: no promote is 0, no capture is 45, crowning is a decisive move, game should not end in draw        | uid:51bba853-9261-4518-b2f2-2ca9ad9092a6 |
      | crowningTheEligiblePiece14 | not promoted | current | f: pawn in crownhead jumps over adjacent vulnerable king only to see another king | uid:740f3a60-3853-424d-90d1-7c450e4bc7c6 |
      | crowningTheEligiblePiece15 | not promoted | current | s: pawn in crownhead jumps over adjacent vulnerable king only to see another king | uid:5aa094c9-f07a-44a8-a823-6603a554b7ee |

  Scenario Outline: Crowning The Eligible Piece - Capturing Kings in the Crownhead (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the player has a "pawn" piece in opponent's crownhead
    When the player jumps over all the vulnerable opponent kings in the crownhead
    Then the piece is "promoted" to a crowned piece
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | explanation | hiptest-uid |
      | crowningTheEligiblePieceCapturingKingsInCrownhead1 | f: pawn jumps one king, lands at a square where there is no adjacent piece, and becomes king | uid:431ec4af-f759-4d85-b66c-96128633764d |
      | crowningTheEligiblePieceCapturingKingsInCrownhead2 | f: pawn jumps one king, lands at a square where there is an adjacent vulnerable own king, and becomes king | uid:01c3adb3-d0fb-4da8-9f73-26375b6dfb9b |
      | crowningTheEligiblePieceCapturingKingsInCrownhead3 | s: pawn jumps two kings, lands at a square where there is no adjacent piece, and becomes king (finishing crowningTheEligiblePiece14) | uid:306b04f2-a86a-46f6-87e5-cfb4f05351ae |

  Scenario Outline: End of the Game (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:c7ad6fce-86db-4cde-a1f3-52c7b62828df |
      | endOfTheGame2 | uid:0214bd7b-091a-4002-8dde-5fabd3eb9af8 |

  Scenario Outline: End of the Game In Draw (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:988a1331-f357-4a60-a0ab-5966b2c440fe |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the next ingame player | uid:33fa220c-5a4a-401c-874e-988ae1397c9e |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:8542bdfc-0796-42d9-99db-ae1991bf4046 |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:a7cf2c7c-13e2-4480-a043-0cd0e34f462c |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the next ingame player | uid:1a8072c8-76bc-42b5-bafd-5031b73b2212 |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples:
      | file_name | explanation | hiptest-uid |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 | f: | uid:f461b233-1d9b-4194-8085-0d3b3905a6ce |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 | s: | uid:71d79376-d038-4c13-9ff5-fd9a31df734d |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 39
    When the player makes a regular move without promoting
    Then the game is ended as a draw

    Examples:
      | file_name | explanation | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | f: no promote is 39, no capture is 39, a regular move ends the game in draw | uid:cd42783c-ee11-41ac-ab3b-f8d61c3a267e |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | f: no promote is 45, no capture is 39, a regular move ends the game in draw | uid:916b2d0d-de90-4f2a-b851-bc1ba7ec1567 |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | f: no promote is 39, no capture is 45, a regular move ends the game in draw | uid:7fe9998d-c9f3-496c-b8f3-57471b7d1f92 |

  Scenario Outline: End of the Game In Draw - Same Board State Reached for the Third Time (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And there are some board states that have been reached two times
    When the player finishes his turn leaving the board in a previously reached state
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawResultingBoardStateWillBeReachedForTheThirdTime1 | uid:6af2a3ef-3974-4bbf-b9cd-edf51038db45 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:16dc8d94-f096-4c6a-98e0-fec644fb3d0b |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:e749a2b8-6b5d-4ea2-9dd9-55e7a5a51c7d |

  Scenario Outline: Offer Draw Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples:
      | file_name | hiptest-uid |
      | offerDrawMove1 | uid:fc846757-c2e9-459c-84b8-153b1844bcd3 |

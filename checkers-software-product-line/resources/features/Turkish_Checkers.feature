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
    And the next turn is given to the "other" player

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
      | validJumpMove1 | other | pawn | f: end of jump possibilities, own piece is not jumpable | uid:6aed8a33-b560-4d13-8d2f-7a1aa1b5e474 |
      | validJumpMove2 | other | king | f: end of jump possibilities, own piece is not jumpable | uid:6a3fb173-9a4c-420d-9d07-8a0548496456 |
      | validJumpMove3 | other | pawn | f: end of jump possibilities, no adjacent piece | uid:83033f88-fe7d-4972-9f09-db28fd9e0b56 |
      | validJumpMove4 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove5) | uid:0d6881c4-fa16-4f46-bfd1-a0f689380c20 |
      | validJumpMove5 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove4) | uid:558b2de6-6e6c-44e1-8ad9-7bc9a72289d2 |
      | validJumpMove6 | other | pawn | s: end of jump possibilities, opponent is not jumpable because destination would be out of borders | uid:305699ec-69a2-405e-aff6-c1d9d9b27920 |
      | validJumpMove7 | current | king | f: another jump possibility | uid:262190ae-5a0b-47a7-a0de-ec261734fcb4 |
      | validJumpMove8 | current | king | f: another jump possibility, opponent piece in distance | uid:fe105c6e-2e96-4891-9b5f-3ad2d9301915 |
      | validJumpMove9 | other | king | f: this valid jump move proves the case in validRegularMove4 | uid:afc4a3d2-78fa-46bf-adc8-cc6e905131b7 |
      | validJumpMove10 | other | pawn | f: this valid jump move proves the case in validRegularMove6 | uid:0ad75b67-23e0-4752-81fc-a8c7c9987cc7 |
      | validJumpMove11 | other | king | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:74287f2e-398c-4be7-9f20-4969a610dc4e |
      | validJumpMove12 | other | king | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw | uid:05899f1a-95d7-42c2-a7f9-cda6f583750c |
      | validJumpMove13 | other | king | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:43b2ff9d-c062-4eee-9877-97f8f24ed3c5 |
      | validJumpMove14 | other | king | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw | uid:81ef1942-72a9-468e-8431-ecaf915671c9 |
      | validJumpMove15 | other | king | f: end of jump possibilities, opponent is not jumpable because possible destination is occupied | uid:13e82b8f-b7ab-440d-bcdd-abfd973ffc06 |
      | validJumpMove16 | current | king | f: another jump possibility, even though the destination is in crownhead, the piece is already king, it can continue jumping, also a similar situation to validJumpMove17 | uid:3ad82263-3651-447d-a018-8bc425559bb4 |
      | validJumpMove17 | other | pawn | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end in draw | uid:b83722d3-6ab0-4dff-9766-63fdbc44a044 |
      | validJumpMove18 | current | king | f: king can jump and capture from distance | uid:e1d7287e-b9d1-4c22-b24b-97534d182cf3 |
      | validJumpMove19 | other | king | s: king can jump and capture from distance | uid:a35ada50-2cca-45ff-b6b9-8ba74d10560e |

  Scenario Outline: Invalid Source Coordinate for Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
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

  Scenario Outline: Invalid Destination Coordinate for Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
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

  Scenario Outline: Crowning the Eligible Piece (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece is "<action>" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | action | next_turn_player | explanation | hiptest-uid |
      | crowningTheEligiblePiece1 | promoted | other | f: regular move, no adjacent pieces | uid:b2b20621-d73a-4763-8ad0-10d4255e9db3 |
      | crowningTheEligiblePiece2 | promoted | other | f: regular move, adjacent vulnerable opponent king | uid:3957c669-6d6f-46e7-ab3c-fcb8f607f464 |
      | crowningTheEligiblePiece3 | promoted | other | f: regular move, adjacent vulnerable own king | uid:65796a70-fa85-4166-8b10-f0ae767ae5d3 |
      | crowningTheEligiblePiece4 | promoted | other | f: jump move, no adjacent pieces | uid:dc470c65-003a-4053-a546-fbf77f770157 |
      | crowningTheEligiblePiece6 | promoted | other | f: jump move, adjacent vulnerable own king | uid:2a2f5669-7042-42be-8c07-7de34dc42413 |
      | crowningTheEligiblePiece7 | promoted | other | f: jump move, adjacent protected opponent king | uid:0d68d34e-3dec-44ae-9173-4bccf18c5ba8 |
      | crowningTheEligiblePiece8 | promoted | other | f: jump move, far away vulnerable opponent king | uid:7ea5a56e-e302-4d54-8ce0-803fa361f2f1 |
      | crowningTheEligiblePiece9 | not promoted | current | f: jump move, adjacent vulnerable opponent king, crowning is hold until kings are captured | uid:c0e3cf46-ef2f-4a34-aaca-4991e5007d89 |
      | crowningTheEligiblePiece10 | not promoted | current | f: jump move, adjacent vulnerable opponent kings (multiple), crowning is hold until kings are captured | uid:457800f3-1585-4433-893e-b4377bcbce2a |
      | crowningTheEligiblePiece11 | promoted | other | f: no promote is 39, no capture is 39, crowning is a decisive move, game should not end in draw          | uid:ec1a7aeb-187a-420d-9190-0401238216a4 |
      | crowningTheEligiblePiece12 | promoted | other | f: no promote is 45, no capture is 0, crowning is a decisive move, game should not end in draw          | uid:521162e9-d622-43cc-b282-507b4d56c2c9 |
      | crowningTheEligiblePiece13 | promoted | other | f: no promote is 0, no capture is 45, crowning is a decisive move, game should not end in draw        | uid:4346a31b-1aa7-429d-b294-ad2c66e9204c |
      | crowningTheEligiblePiece14 | not promoted | current | f: pawn in crownhead jumps over adjacent vulnerable king only to see another king | uid:740f3a60-3853-424d-90d1-7c450e4bc7c6 |
      | crowningTheEligiblePiece15 | not promoted | current | s: pawn in crownhead jumps over adjacent vulnerable king only to see another king | uid:5aa094c9-f07a-44a8-a823-6603a554b7ee |

  Scenario Outline: Crowning The Eligible Piece - Capturing Kings in the Crownhead (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the player has a "pawn" piece in opponent's crownhead
    When the player jumps over all the vulnerable opponent kings in the crownhead
    Then the piece is "promoted" to a crowned piece
    And the next turn is given to the "other" player

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
      | endOfTheGameInDraw2 | rejects | the next turn is given to the other player | uid:318d42b7-c5f5-4fd0-865c-6554a25544aa |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:8542bdfc-0796-42d9-99db-ae1991bf4046 |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:a7cf2c7c-13e2-4480-a043-0cd0e34f462c |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the other player | uid:931db9ba-ad5f-4e48-a27c-c41d07029615 |

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

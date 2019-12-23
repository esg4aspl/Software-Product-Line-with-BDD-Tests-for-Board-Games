Feature: Turkish Checkers


  Background:
    Given the "Turkish Checkers" game is set up

  Scenario: Start of the Game (3) (uid:a95491cb-0845-4d1a-9a48-9830d0e560a4)
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | piece_type | explanation | hiptest-uid |
      | validRegularMove1 | king | f: king moves backwards | uid:1cb181c2-52b1-4bba-8a84-2c632d431494 |
      | validRegularMove2 | pawn | f: regular move | uid:9f8a7ba7-c049-4460-aca1-55abe5b0d19c |
      | validRegularMove3 | pawn | f: regular move | uid:8bece884-a103-42d2-8cd8-1eb5a1162006 |
      | validRegularMove4 | pawn | f: opponent is not blocked, his king can jump backward, game should not end | uid:4d902515-1216-4448-82be-94cad0daeb4e |
      | validRegularMove5 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:1f6f95c5-ba2e-423e-980a-02a9cb6a0d65 |
      | validRegularMove6 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:d505b1d2-73ff-4243-8c42-bb9911f6934a |
      | validRegularMove7 | king | f: king can jump multiple squares without capturing | uid:aebef510-5c67-4391-88f1-13e57de8c26c |
      | validRegularMove8 | king | f: king can jump multiple squares without capturing | uid:f4ebff83-e411-4ed8-a9ab-f3a2215d78b0 |

  Scenario Outline: Valid Jump Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid capture" move
    Then the piece is moved to the destination coordinate
    And the captured opponent piece is removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | piece_type | explanation | hiptest-uid |
      | validJumpMove1 | next ingame | pawn | f: end of jump possibilities, own piece is not jumpable | uid:83474bbe-8c6b-4a99-8274-d68f6648ff60 |
      | validJumpMove2 | next ingame | king | f: end of jump possibilities, own piece is not jumpable | uid:9c0c289b-c385-4bb2-ad30-37b0e7995485 |
      | validJumpMove3 | next ingame | pawn | f: end of jump possibilities, no adjacent piece | uid:ee6ea1ae-9969-4828-aa47-4b50b3741bf3 |
      | validJumpMove4 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove5) | uid:b1cdbe65-b285-4243-86ec-dbffe1d51d7b |
      | validJumpMove5 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove4) | uid:e24193b5-e983-4321-8df5-f006edb07c6e |
      | validJumpMove6 | next ingame | pawn | s: end of jump possibilities, opponent is not jumpable because destination would be out of borders | uid:495871d3-56bb-4474-9a8a-86f53c5efabb |
      | validJumpMove7 | current | king | f: another jump possibility | uid:c87277cd-5c22-4b40-90be-651f2d4e39d6 |
      | validJumpMove8 | current | king | f: another jump possibility, opponent piece in distance | uid:6cdf0ffd-4f72-4d66-abf0-f90bff4ead45 |
      | validJumpMove9 | next ingame | king | f: this valid jump move proves the case in validRegularMove4 | uid:09ca6ccd-cbf5-46a8-a3b1-fb22587b52b1 |
      | validJumpMove10 | next ingame | pawn | f: this valid jump move proves the case in validRegularMove6 | uid:b0f69a14-b396-4630-95dc-e4b4dcabffdf |
      | validJumpMove11 | next ingame | king | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:fe883b1c-5d6e-40bf-84db-5c1d2bf86ddc |
      | validJumpMove12 | next ingame | king | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw | uid:dec0285e-7086-4ec1-a10c-8ec647c6cb98 |
      | validJumpMove13 | next ingame | king | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:ca47eacd-c132-43c4-b207-3d09d4df1774 |
      | validJumpMove14 | next ingame | king | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw | uid:9aa63ac4-e149-4727-a2ca-d5ff5fdc68d2 |
      | validJumpMove15 | next ingame | king | f: end of jump possibilities, opponent is not jumpable because possible destination is occupied | uid:7baef18d-605d-4687-b1bf-bff8ab707a22 |
      | validJumpMove16 | current | king | f: another jump possibility, even though the destination is in crownhead, the piece is already king, it can continue jumping, also a similar situation to validJumpMove17 | uid:91d08637-294e-4894-8578-bb37bde53fd7 |
      | validJumpMove17 | next ingame | pawn | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end in draw | uid:3f0f1d0c-ed30-4502-b5f9-d5c0b13c4bf8 |
      | validJumpMove18 | current | king | f: king can jump and capture from distance | uid:82428381-6166-4a91-a67d-12f9b4c393fc |
      | validJumpMove19 | next ingame | king | s: king can jump and capture from distance | uid:b313c0f1-d14d-40d4-80db-88fee91b228a |

  Scenario Outline: Crowning the Eligible Piece (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move to opponent's crownhead with a pawn
    Then the piece is "<action>" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | action | next_turn_player | explanation | hiptest-uid |
      | crowningTheEligiblePiece1 | promoted | next ingame | f: regular move, no adjacent pieces | uid:a132fa67-cf6a-468a-9180-cb34da6619d8 |
      | crowningTheEligiblePiece2 | promoted | next ingame | f: regular move, adjacent vulnerable opponent king | uid:c6a96541-858e-49cf-91d8-460fc7aa8f50 |
      | crowningTheEligiblePiece3 | promoted | next ingame | f: regular move, adjacent vulnerable own king | uid:a179fd10-643e-43c0-ba45-817efe05e4ff |
      | crowningTheEligiblePiece4 | promoted | next ingame | f: jump move, no adjacent pieces | uid:3a4ab01f-0c6d-4b38-9e04-52c604807901 |
      | crowningTheEligiblePiece5 | promoted | next ingame | f: jump move, adjacent vulnerable opponent pawn | uid:8a029f84-29d0-46e1-9968-7feed3bb548f |
      | crowningTheEligiblePiece6 | promoted | next ingame | f: jump move, adjacent vulnerable own king | uid:67240d78-c3b9-4004-9dff-3465abcb169f |
      | crowningTheEligiblePiece7 | promoted | next ingame | f: jump move, adjacent protected opponent king | uid:982bcd8a-e970-4dbf-864b-e204cc35be7d |
      | crowningTheEligiblePiece8 | promoted | next ingame | f: jump move, far away vulnerable opponent king | uid:82fd8aa8-69f4-48eb-aa87-c6006589869d |
      | crowningTheEligiblePiece9 | not promoted | current | f: jump move, adjacent vulnerable opponent king, crowning is hold until kings are captured | uid:29b7e347-1c12-47d8-a64e-2beab5746fab |
      | crowningTheEligiblePiece10 | not promoted | current | f: jump move, adjacent vulnerable opponent kings (multiple), crowning is hold until kings are captured | uid:bf3d152f-4a3a-4281-ac93-a8b5c5d1874d |
      | crowningTheEligiblePiece11 | promoted | next ingame | f: no promote is 39, no capture is 39, crowning is a decisive move, game should not end in draw          | uid:2c5ec60a-d0a2-44b8-b7c5-8c1f1d48ae43 |
      | crowningTheEligiblePiece12 | promoted | next ingame | f: no promote is 45, no capture is 0, crowning is a decisive move, game should not end in draw          | uid:2d104503-f19b-447d-8d27-be19fd16cb40 |
      | crowningTheEligiblePiece13 | promoted | next ingame | f: no promote is 0, no capture is 45, crowning is a decisive move, game should not end in draw        | uid:3e7a5cf2-dd8f-4f7e-bf15-84b1fdc67088 |
      | crowningTheEligiblePiece14 | not promoted | current | f: pawn in crownhead jumps over adjacent vulnerable king only to see another king | uid:e142dea9-ff1f-48be-a441-c4b79f35262a |
      | crowningTheEligiblePiece15 | not promoted | current | s: pawn in crownhead jumps over adjacent vulnerable king only to see another king | uid:cd61d7ac-ce3a-41c3-adea-6a9662c29f41 |

  Scenario Outline: Crowning The Eligible Piece - Capturing Kings in the Crownhead (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player jumps over all the vulnerable opponent kings in the crownhead
    Then the piece is "promoted" to a crowned piece
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | explanation | hiptest-uid |
      | crowningTheEligiblePieceCapturingKingsInCrownhead1 | f: pawn jumps one king, lands at a square where there is no adjacent piece, and becomes king | uid:6d830038-df9b-4292-94c9-f3e8d77e5552 |
      | crowningTheEligiblePieceCapturingKingsInCrownhead2 | f: pawn jumps one king, lands at a square where there is an adjacent vulnerable own king, and becomes king | uid:b3d33df1-9409-4930-8355-63e31706805d |
      | crowningTheEligiblePieceCapturingKingsInCrownhead3 | s: pawn jumps two kings, lands at a square where there is no adjacent piece, and becomes king (finishing crowningTheEligiblePiece14) | uid:ef6aa5c0-b2a0-418b-a488-4d71085583b7 |

  Scenario Outline: End of the Game (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:7c735832-53c6-4125-9bb0-d5b5114b4b1d |
      | endOfTheGame2 | uid:6be56ecf-e554-4196-ad88-70931d41e51c |

  Scenario Outline: End of the Game In Draw (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:99bf6ace-ac88-4ca1-8951-9af917da4335 |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the next ingame player | uid:8e875a42-cae6-416d-883a-0e1a50baa5d1 |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:7c8e7e70-32be-4371-90f3-e0ed1813107c |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:0f835dd2-0301-4677-8c75-0fb5d4875476 |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the next ingame player | uid:a563bcf3-f586-4903-9d47-f022b233fe77 |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples:
      | file_name | explanation | hiptest-uid |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 | f: | uid:c575fe19-9131-44b0-8ea1-9057356d5809 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 | s: | uid:1f742fe2-648a-4a8a-98d9-89cda4a66a85 |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the game is ended as a draw

    Examples:
      | file_name | explanation | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | f: no promote is 39, no capture is 39, a regular move ends the game in draw | uid:0fea0567-5550-489b-beb9-1010e0e4f779 |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | f: no promote is 45, no capture is 39, a regular move ends the game in draw | uid:627a2fb3-a791-4ca9-8108-2a56a01b7e17 |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | f: no promote is 39, no capture is 45, a regular move ends the game in draw | uid:b908ff0f-93e8-4208-8a7c-428ac387121f |

  Scenario Outline: End of the Game In Draw - Same Board State Reached for the Third Time (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player finishes his turn leaving the board in a previously reached state
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawResultingBoardStateWillBeReachedForTheThirdTime1 | uid:4acbbd65-d0e2-4052-9e00-93ab19abf8b6 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:607ccec0-121a-4fcb-a692-56ea90a1f2cb |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:87d61d74-68d4-4341-9984-babf44555150 |

  Scenario Outline: Offer Draw Move (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples:
      | file_name | hiptest-uid |
      | offerDrawMove1 | uid:4ee9bc99-1a01-4468-b726-bdd4cb9ce6c5 |

  Scenario Outline: Invalid Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "invalid" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another move

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:2b5050e6-ad0a-43c9-b692-b530cfaf72aa |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate | uid:e86606c3-8dcb-4960-8c2c-c4dd382dcfb9 |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:c5ce05ee-7d5d-4bac-a33a-d4b54dd0f2ca |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:ffefca9f-83f5-4cc8-bffc-4c1b45ede50c |
      | invalidSourceCoordinateForMoveEmpty3 | source coordinate is empty | No piece at source coordinate | uid:ba834c16-039c-4a18-ac5d-47894000fc70 |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:42c2e850-5420-4f4e-9b0d-7bd6a47f1040 |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:d146425e-3021-46f8-a1be-b5de1245d3f4 |
      | invalidSourceCoordinateForMoveOpponentsPiece3 | source coordinate has opponent's piece | Piece does not belong to current player | uid:f523b1ff-2f61-4540-9e3f-e677df3e2409 |
      | invalidSourceCoordinateForMovePawnInCrownhead1 | there is a pawn in crownhead but move is not that | Pawn in crownhead must capture king to be promoted | uid:d426de30-d114-410a-accf-7e35986db12d |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | source coordinate of move is different than last jump move’s destination | Destination valid? false | uid:03b1d75d-cd7e-4fb1-a1bb-ac92eaff1416 |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | destination coordinate is outside of the board | Destination Valid? false | uid:47eb8fde-2e9d-44e2-bfc1-eaeee36bb532 |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | destination coordinate is outside of the board | Destination Valid? false | uid:cc096d31-c07e-48eb-b9df-2ff252178dc8 |
      | invalidDestinationCoordinateForMoveOccupied1 | destination coordinate is occupied | A piece at destination coordinate | uid:a14ce827-b702-402b-afdd-d8ddcc3f181a |
      | invalidDestinationCoordinateForMoveOccupied2 | destination coordinate is occupied | A piece at destination coordinate | uid:5144ef6c-5a20-4c87-ae9c-8aa8551456a9 |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | destination coordinate's direction is not allowed | Destination Valid? false | uid:ae1391b6-cf6b-4e7f-8ae7-2337a392b035 |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | destination coordinate's direction is not allowed | Destination Valid? false | uid:5926b4e7-d581-4348-a147-a17c75b0ca22 |
      | invalidDestinationCoordinateForMoveUnallowedDirection3 | destination coordinate's direction is not allowed | Destination Valid? false | uid:2f2908d3-d769-4a2b-a4c5-87f93a0940fb |
      | invalidDestinationCoordinateForMoveTooFarAway1 | destination coordinate is more than two squares away | Destination Valid? false | uid:0c000e6d-b716-4300-b061-1e9fc13a8e69 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | jumped piece is null | There must be one piece on jump path 0 | uid:37d3e1c5-47ea-4443-8733-2467f7503272 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | jumped piece is not opponent piece | Jumped Piece Must Be Opponent Piece | uid:b46f4438-c562-4ae3-b501-eead786de47a |
      | invalidDestinationCoordinateForMoveJumpedPieceIsFarAwayFromSource1 | jumped piece is too far away from source coordinate | Destination Valid? false | uid:dbc59754-3c5f-4c56-bcc3-462f30aa49fe |
      | invalidDestinationCoordinateForMoveMultipleJumpedPieces1 | there are more than one pieces in jump path | There must be only one piece on jump path 2 | uid:66a35f71-47a0-4d99-adce-d1b27171f338 |
      | invalidDestinationCoordinateForMoveNotBestSequence1 | move is not part of the best sequence | Not the best move | uid:04674c17-22f9-41d5-b7ea-2dbc656d779e |
      | invalidDestinationCoordinateForMoveNotBestSequence2 | move is not part of the best sequence | Not the best move | uid:a21a6a4d-3b80-4e7a-a37e-1898afd09551 |
      | invalidDestinationCoordinateForMovePawnInCrownhead1 | the pawn in crownhead did not capture the vulnerable king | Must capture king to be promoted | uid:365ff5be-e047-4e84-9629-507468254000 |
      | invalidDestinationCoordinateForMovePawnInCrownhead2 | the pawn in crownhead did not capture the vulnerable king | Must capture king to be promoted | uid:fddeb391-fb9c-413e-a856-7d26ae7d1df4 |
      | invalidDestinationCoordinateForMoveOppositeDirectionOfLastMove1 | move direction is opposite of last jump move's direction | If any opponent's pieces can be captured then it must be captured first!!!! | uid:ac7f2ec7-20b3-4e60-84e2-e83a4d9406ae |

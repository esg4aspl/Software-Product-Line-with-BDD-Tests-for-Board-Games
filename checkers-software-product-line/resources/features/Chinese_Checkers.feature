Feature: Chinese Checkers


  Scenario Outline: Valid Regular Move (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | number_of_players | explanation | hiptest-uid |
      | validRegularMove1 | 2 | f: regular move | uid:73ca1325-21f6-4768-88e6-24777fd4635e |
      | validRegularMove2 | 2 | f: pawn does not capture even though there is a possibility and goes backwards | uid:b773b935-1f5c-4637-bfa4-8d451f3048e7 |
      | validRegularMove3 | 2 | f: opponent is not blocked, his pawn can jump, game should not end | uid:14b8cfcc-e5b6-48d4-b629-84111be21d68 |

  Scenario Outline: Valid Jump Move (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate that is "two" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | number_of_players | next_turn_player | explanation | hiptest-uid |
      | validJumpMove1 | 2 | next ingame | f: end of jump possibilities, no adjacent piece | uid:493a2e8c-76b9-4447-ae55-0ed02442c859 |
      | validJumpMove2 | 2 | current | f: jumping own piece, another jump possibility | uid:74b2d907-5d4a-4f28-a6d2-daf197492f9d |
      | validJumpMove3 | 2 | next ingame | f: end of jump possibilities, piece is not jumpable because destination would be out of borders | uid:b213b194-d118-4ba9-8431-c9c8cbefd3c6 |
      | validJumpMove4 | 2 | current | s: another jump possibility | uid:9eb864b5-8310-4fba-b8fe-a122acbeaf3c |
      | validJumpMove5 | 2 | next ingame | f: this valid jump move proves the case in validRegularMove3 | uid:e18fb05c-58dc-435e-ac8c-c013ebf85c0b |
      | validJumpMove6 | 2 | next ingame | f: end of jump possibilities, piece is not jumpable because possible destination is occupied | uid:cd45e31e-1e3e-49d9-bca3-7b773bcb4673 |

  Scenario Outline: Player Chooses to Continue or Not (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    And the player has made jump moves in the current turn
    And the player can continue doing jump moves
    And the player has been asked to continue or not
    When the player chooses to "<decision>"
    Then the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | number_of_players | decision | next_turn_player | hiptest-uid |
      | playerChoosesToContinueOrNot1 | 2 | continue  | current | uid:7e4ef0bc-5d4f-40c5-95f1-ae9ea4f328b5 |
      | playerChoosesToContinueOrNot2 | 2 | stop  | next ingame | uid:0479f3d3-52ca-4015-95f7-8ee1d4d4eb9c |

  Scenario Outline: Invalid Source Coordinate for Move (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | number_of_players | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | 2 | source coordinate is not of valid square color | No piece at source coordinate | uid:c929c8ee-3c3a-4d63-a0ee-74198f9a3a01 |
      | invalidSourceCoordinateForMoveOutsideBorders2 | 2 | source coordinate is not of valid square color | No piece at source coordinate | uid:26b18823-0ad2-494a-807b-6b1cc7e015f9 |
      | invalidSourceCoordinateForMoveUnplayableColor1 | 2 | source coordinate is not of valid square color | No piece at source coordinate | uid:532ac4f5-b6ff-4afb-bd6f-7738a80b387f |
      | invalidSourceCoordinateForMoveUnplayableColor2 | 2 | source coordinate is not of valid square color | No piece at source coordinate | uid:7fb943a4-ad2e-43bb-baa6-f89a2c7e696f |
      | invalidSourceCoordinateForMoveEmpty1 | 2 | source coordinate is empty | No piece at source coordinate | uid:aba9d667-df3b-4f34-be8d-b5622c57dbe8 |
      | invalidSourceCoordinateForMoveEmpty2 | 2 | source coordinate is empty | No piece at source coordinate | uid:0ba01885-704d-4d27-a22c-d894d97dffb4 |
      | invalidSourceCoordinateForMoveEmpty3 | 2 | source coordinate is empty | No piece at source coordinate | uid:6e2602eb-7087-4c49-ab3c-2a7ab4d647ac |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | 2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:91870d9a-96d1-4f73-a8c2-12faa133ccb4 |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | 2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:d718f000-aa38-45e5-bef2-f190fa406b36 |
      | invalidSourceCoordinateForMoveOpponentsPiece3 | 2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:7d78a4ca-f04a-4a40-a12f-59ac84414949 |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | 2 | source coordinate of move is different than last jump move’s destination | Destination valid? false | uid:c091e7f4-2187-4c8a-ba6a-dda96ec4dd96 |

  Scenario Outline: Invalid Destination Coordinate for Move (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | number_of_players | invalidity_reason | error_message | hiptest-uid |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | 2 | destination coordinate is not of valid square color | Destination Valid? false | uid:988c7e45-2967-44b9-a729-079235207363 |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | 2 | destination coordinate is not of valid square color | Destination Valid? false | uid:8d49863f-cb19-4d12-9b8b-82a2c0b0c129 |
      | invalidDestinationCoordinateForMoveUnplayableColor1 | 2 | destination coordinate is not of valid square color | Destination Valid? false | uid:94d8f4b0-1640-468d-a83f-7349768187f4 |
      | invalidDestinationCoordinateForMoveUnplayableColor2 | 2 | destination coordinate is not of valid square color | Destination Valid? false | uid:00e23da0-54f7-4337-a43a-33956b311c83 |
      | invalidDestinationCoordinateForMoveOccupied1 | 2 | destination coordinate is occupied | A piece at destination coordinate | uid:f4371eb8-68ad-4962-92ae-f8643f1c8880 |
      | invalidDestinationCoordinateForMoveOccupied2 | 2 | destination coordinate is occupied | A piece at destination coordinate | uid:8906c730-8aea-4f5b-840a-23f850fbf606 |
      | invalidDestinationCoordinateForMoveOccupied3 | 2 | destination coordinate is occupied | A piece at destination coordinate | uid:18ef4add-b0c8-461c-bf60-e0f51501e3cb |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | 2 | destination coordinate's direction is not allowed | Destination Valid? false | uid:28314c4e-7f8c-48fb-a2af-c115488d4d8a |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | 2 | destination coordinate's direction is not allowed | Destination Valid? false | uid:458185f4-fb5f-495b-89ea-f26335aaf4cc |
      | invalidDestinationCoordinateForMoveTooFarAway1 | 2 | destination coordinate is more than two squares away | Destination Valid? false | uid:93bd11a4-bc6b-4c4e-a864-270cef4f3c81 |
      | invalidDestinationCoordinateForMoveTooFarAway2 | 2 | destination coordinate is more than two squares away | Destination Valid? false | uid:777bbfc1-4f60-4dd2-abb0-855ad6b1cf3b |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | 2 | jumped piece is null | There must be only one piece on jump path 0 | uid:17a58688-c078-4802-a415-7a258528f635 |
      | invalidDestinationCoordinateForMoveCantLeaveGoalTriangle1 | 2 | piece can not leave goal triangle | Piece can not leave goal triangle | uid:f29f0351-a75c-4fcf-9260-f41f542d81c2 |
      | invalidDestinationCoordinateForMoveOppositeDirectionOfLastMove1 | 2 | move direction is opposite of last jump move's direction | Not one of possible jump moves | uid:baf99a63-e028-4b84-86b8-d6a3e04b5871 |

  Scenario Outline: End of the Game (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    And the player has at least one of his pieces in the goal triangle
    And there is only one square is available at the goal triangle
    When the player jumps moves one of his pieces to the last available square in goal triangle
    Then the player wins the game

    Examples:
      | file_name | number_of_players | hiptest-uid |
      | endOfTheGame2 | 2 | uid:d1d72ee7-2403-4714-9bff-1c0b1b9d5973 |
      | endOfTheGame2Accept | 2 | uid:fba2a27e-7d89-493b-986e-a0ebde585173 |
      | endOfTheGame3 | 3 | uid:3173f4a2-35a0-495f-a19d-d2d0a07a6586 |
      | endOfTheGame3Accept | 3 | uid:871ea697-7d17-4c6e-a7d6-cc8b44d2e522 |
      | endOfTheGame4 | 4 | uid:775d7078-3b89-4c7b-a3b6-986b25b80678 |
      | endOfTheGame4Accept | 4 | uid:1c2fc633-47c9-45b6-b45e-68aa24d19d82 |
      | endOfTheGame6 | 6 | uid:59084ebc-d195-4fc8-8f06-ce8962d9c981 |
      | endOfTheGame6Accept | 6 | uid:fe939f6c-41b4-4b7f-aaeb-37fcc99f9263 |

  Scenario Outline: End of the Game In Draw (3) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "2" players
    And the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:980625e3-42fb-4d35-8b13-e9983d9194a1 |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the offerer player | uid:2ffe4d96-99b1-4268-ad5d-6c93f9cabbe7 |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:a86be045-1780-452d-b148-bf1a62ae7e89 |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:f1b165c4-6e61-463c-8257-878aa0c19f92 |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the offerer player | uid:c5b276d7-dca0-4698-80fc-653acf467381 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game

    Examples:
      | file_name | number_of_players | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | 2 | uid:45476602-71fd-4b70-8a47-dbad4c99f800 |

  Scenario Outline: Offer Draw Move (3) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "2" players
    And the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples:
      | file_name | hiptest-uid |
      | offerDrawMove1 | uid:79f18baa-9bcb-4f44-9a2f-5ae419d064ef |

Feature: Chinese Checkers


  Scenario Outline: Valid Regular Move (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | number_of_players | explanation | hiptest-uid |
      | validRegularMove1 | 2 | f: regular move | uid:745e828c-a25b-4c0b-ad68-dd56b5eb5070 |
      | validRegularMove2 | 2 | f: pawn does not capture even though there is a possibility and goes backwards | uid:48a29981-1356-4e87-b085-fb0657460562 |
      | validRegularMove3 | 2 | f: opponent is not blocked, his pawn can jump, game should not end | uid:79eb0d86-599f-402b-8f81-f1d179b3370e |

  Scenario Outline: Valid Jump Move (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid capture" move
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | number_of_players | next_turn_player | explanation | hiptest-uid |
      | validJumpMove1 | 2 | next ingame | f: end of jump possibilities, no adjacent piece | uid:82d9a6b5-ec04-459f-89a0-affba8552b89 |
      | validJumpMove2 | 2 | current | f: jumping own piece, another jump possibility | uid:c3e23908-5edf-4bcd-a9d1-edcec3a2917b |
      | validJumpMove3 | 2 | next ingame | f: end of jump possibilities, piece is not jumpable because destination would be out of borders | uid:669231f2-f5d8-44c8-a4ad-9d0bef4cb64c |
      | validJumpMove4 | 2 | current | s: another jump possibility | uid:634a95d1-a325-4574-bc23-bee40c45c39f |
      | validJumpMove5 | 2 | next ingame | f: this valid jump move proves the case in validRegularMove3 | uid:5ce645c0-dabc-4711-a06a-adb772a82a86 |
      | validJumpMove6 | 2 | next ingame | f: end of jump possibilities, piece is not jumpable because possible destination is occupied | uid:109cc84c-dbc7-404d-b76c-2dbe26ea04ff |

  Scenario Outline: Player Chooses to Continue or Not (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    And the player has been asked to continue or not
    When the player chooses to "<decision>"
    Then the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | number_of_players | decision | next_turn_player | hiptest-uid |
      | playerChoosesToContinueOrNot1 | 2 | continue  | current | uid:aa8278cd-fd30-4ff9-8c0f-466d9883772c |
      | playerChoosesToContinueOrNot2 | 2 | stop  | next ingame | uid:7941e583-503f-4931-9678-3f1cf1618ea4 |

  Scenario Outline: End of the Game (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player jumps moves one of his pieces to the last available square in goal triangle
    Then the player wins the game

    Examples:
      | file_name | number_of_players | hiptest-uid |
      | endOfTheGame2 | 2 | uid:f1180e12-f9ec-40bf-a281-6a99a6258eb5 |
      | endOfTheGame2Accept | 2 | uid:fa1edcae-a27f-4c20-99a8-71d6edc61e92 |
      | endOfTheGame3 | 3 | uid:41d471c6-8c8e-4b9a-878e-6e2dd2b4a091 |
      | endOfTheGame3Accept | 3 | uid:16edba88-add3-4038-98e0-a2a2effda682 |
      | endOfTheGame4 | 4 | uid:105c593e-b86d-4737-9e1c-3bc0dc8aae12 |
      | endOfTheGame4Accept | 4 | uid:814c4cb6-aceb-4803-b3f0-158825188380 |
      | endOfTheGame6 | 6 | uid:545a6879-c878-4aa0-a24f-f00deb3ac52c |
      | endOfTheGame6Accept | 6 | uid:99f47435-2f6a-481b-89cc-3373f19cde85 |

  Scenario Outline: End of the Game In Draw (3) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "2" players
    And the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:b27ab1ae-62af-4a3f-8b21-c3852904019e |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the offerer player | uid:97af6f24-d0be-4d7e-8fa9-10dc90fe2c4a |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:58f7e5b4-d085-4048-87d3-789dcbc311de |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:fa3e8017-776b-440a-a097-14b18a0dcb55 |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the offerer player | uid:ae9d4cb6-2fe0-4347-84bf-4ddfc78315c3 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game

    Examples:
      | file_name | number_of_players | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | 2 | uid:c050c019-9c8e-4f7d-830a-e28d2aab67cd |

  Scenario Outline: Offer Draw Move (3) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "2" players
    And the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples:
      | file_name | hiptest-uid |
      | offerDrawMove1 | uid:aaf6f2eb-d04b-40a4-8a31-9855a2cd982a |

  Scenario Outline: Invalid Move (4) (<hiptest-uid>)
    Given the "Chinese Checkers" game is set up for "<number_of_players>" players
    And the game is played up to a certain point from file "<file_name>"
    When the player makes a "invalid" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another move

    Examples:
      | file_name | number_of_players | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | 2 | source coordinate is not of valid square color | No piece at source coordinate | uid:5c7fd0cb-97c0-4173-a306-745604e5e1bb |
      | invalidSourceCoordinateForMoveOutsideBorders2 | 2 | source coordinate is not of valid square color | No piece at source coordinate | uid:b067ae71-6a55-4a8c-9c66-c52fdb7d252a |
      | invalidSourceCoordinateForMoveUnplayableColor1 | 2 | source coordinate is not of valid square color | No piece at source coordinate | uid:0f1db4e4-1585-436d-9ff4-51c76d08de2d |
      | invalidSourceCoordinateForMoveUnplayableColor2 | 2 | source coordinate is not of valid square color | No piece at source coordinate | uid:93fc1667-0a8a-4348-86b9-1d7e2fb916fb |
      | invalidSourceCoordinateForMoveEmpty1 | 2 | source coordinate is empty | No piece at source coordinate | uid:484ac40f-366c-403c-a3aa-60aa01f8601a |
      | invalidSourceCoordinateForMoveEmpty2 | 2 | source coordinate is empty | No piece at source coordinate | uid:b4e9486a-fe89-4bc3-b01f-b85843b7e8f7 |
      | invalidSourceCoordinateForMoveEmpty3 | 2 | source coordinate is empty | No piece at source coordinate | uid:952df887-5b55-484b-a62b-1ac90ee8c21f |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | 2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:1ff4ddf9-a191-4c3b-b25e-69b7d7e29339 |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | 2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:e64eeeed-454e-4cdb-9986-7a2f7b76bad3 |
      | invalidSourceCoordinateForMoveOpponentsPiece3 | 2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:0103480a-04a9-449d-a2e7-7e1a38cce602 |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | 2 | source coordinate of move is different than last jump move’s destination | Destination valid? false | uid:6805569b-0b9f-4cb7-8b06-9be55e03950f |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | 2 | destination coordinate is not of valid square color | Destination Valid? false | uid:db202274-370b-4e9d-bc0c-c79d4c383d10 |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | 2 | destination coordinate is not of valid square color | Destination Valid? false | uid:70d43d04-f43b-4170-a2a5-f685f6f0ace7 |
      | invalidDestinationCoordinateForMoveUnplayableColor1 | 2 | destination coordinate is not of valid square color | Destination Valid? false | uid:e07ef7fa-362c-4595-8d71-c1efee4d5763 |
      | invalidDestinationCoordinateForMoveUnplayableColor2 | 2 | destination coordinate is not of valid square color | Destination Valid? false | uid:348e3868-ef84-4901-ac8a-d070b8f7a4f4 |
      | invalidDestinationCoordinateForMoveOccupied1 | 2 | destination coordinate is occupied | A piece at destination coordinate | uid:a302b1d8-c382-436f-8e4f-9dfe4a0089b9 |
      | invalidDestinationCoordinateForMoveOccupied2 | 2 | destination coordinate is occupied | A piece at destination coordinate | uid:32a947ce-5ea5-4559-9530-892d84864a36 |
      | invalidDestinationCoordinateForMoveOccupied3 | 2 | destination coordinate is occupied | A piece at destination coordinate | uid:3d912175-ac2e-4b7e-8956-453ab119837e |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | 2 | destination coordinate's direction is not allowed | Destination Valid? false | uid:3c2b9590-c883-465a-aefd-ec75d56dbbd9 |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | 2 | destination coordinate's direction is not allowed | Destination Valid? false | uid:9f2d72ac-58c4-4df6-a858-5a70fa7d55f1 |
      | invalidDestinationCoordinateForMoveTooFarAway1 | 2 | destination coordinate is more than two squares away | Destination Valid? false | uid:12946af8-f256-481f-9516-64ce076773f9 |
      | invalidDestinationCoordinateForMoveTooFarAway2 | 2 | destination coordinate is more than two squares away | Destination Valid? false | uid:6e6220b2-7210-4f7c-b537-a88114b28347 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | 2 | jumped piece is null | There must be only one piece on jump path 0 | uid:65f88ec2-9fc6-4ce1-8020-ec87c881dbca |
      | invalidDestinationCoordinateForMoveCantLeaveGoalTriangle1 | 2 | piece can not leave goal triangle | Piece can not leave goal triangle | uid:6c202512-7337-4a69-a6c8-5391796fad3f |
      | invalidDestinationCoordinateForMoveOppositeDirectionOfLastMove1 | 2 | move direction is opposite of last jump move's direction | Not one of possible jump moves | uid:9b0df9eb-153e-46a6-9e97-266c8b5df401 |

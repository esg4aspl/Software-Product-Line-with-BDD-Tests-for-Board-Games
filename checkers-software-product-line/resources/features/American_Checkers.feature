Feature: American Checkers


  Background:
    Given the "American Checkers" game is set up

  Scenario: Start of the Game (uid:29e7e813-631c-4e8b-b40c-ea1a47a3df45)
    When the players start the game
    Then the player with the dark-colored pieces is given the turn

  Scenario Outline: Valid Regular Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | validRegularMove1 | uid:14cb1355-0af3-49d3-b7a6-51133fd74364 |
      | validRegularMove2 | uid:af9ca253-73e3-4a31-8f9e-f5a6e9605902 |
      | validRegularMove3 | uid:dd894aea-97d9-4ac9-900f-0065aa470adb |
      | validRegularMove4 | uid:e89c658b-f914-45a1-a7c4-b126a9a64453 |
      | validRegularMove5 | uid:716ab29f-e9b7-40fc-a460-d28392f4273f |
      | validRegularMove6 | uid:3e4a537c-f12a-4b13-9fa1-f88bbc21628c |

  Scenario Outline: Valid Jump Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "two" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | hiptest-uid |
      | validJumpMove1 | other | uid:20888809-bc01-42bf-8820-527bff4ebddd |
      | validJumpMove2 | other | uid:6fbd1fa8-e5ff-49bd-8cb4-3b3281934785 |
      | validJumpMove3 | other | uid:4316cef3-a33b-443c-9cce-10109f4217f0 |
      | validJumpMove4 | current | uid:aea16597-291b-4fdb-aea2-0a9f32b409cc |
      | validJumpMove5 | current | uid:e5e21b57-c813-425a-be74-574d4dfbc7e7 |
      | validJumpMove6 | other | uid:2df0d6f4-8aba-43b2-9efc-43dbec1bab7c |
      | validJumpMove7 | current | uid:091da101-a9c9-4fe8-80ce-7dab53ee08c6 |
      | validJumpMove8 | other | uid:729aa3e6-c3ad-437d-867b-5b6bc250f876 |
      | validJumpMove9 | other | uid:6df2d7d1-407a-4ab0-93e4-ca365123303c |
      | validJumpMove10 | other | uid:cdb9857d-906b-4382-b440-436f63e2e6ae |
      | validJumpMove11 | other | uid:903b026f-2290-4959-b574-e56187d7a760 |
      | validJumpMove12 | other | uid:609b3028-54a3-441e-93f0-d883863d6411 |
      | validJumpMove13 | other | uid:06c5b80f-dccb-47bd-a060-d3e54318aa3d |
      | validJumpMove14 | other | uid:ede75e02-e9a6-40dd-898b-c5aa977afdb4 |

  Scenario Outline: Invalid Source Coordinate for Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:a9ec6999-0678-4154-834c-969810b8cba4 |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate | uid:71ca7ae0-8821-4bc0-9d90-ab5183fdc41c |
      | invalidSourceCoordinateForMoveUnplayableColor1 | source coordinate is not of valid square color | No piece at source coordinate | uid:4b018f06-fb90-4d2d-8915-8e93c183f77d |
      | invalidSourceCoordinateForMoveUnplayableColor2 | source coordinate is not of valid square color | No piece at source coordinate | uid:12b14290-74d8-4597-82f2-f9e0d90d7120 |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:5fda3a9a-042c-4d0f-b80e-e84bc5bf7792 |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:6e859ee7-2868-49b3-ada2-d3ba5018eb78 |
      | invalidSourceCoordinateForMoveEmpty3 | source coordinate is empty | No piece at source coordinate | uid:0f5a7c9d-0d52-43a1-87ac-e5cfa803e350 |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:ee334b2b-ec6e-4c77-a55b-35260eeb8045 |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:f7a73b79-f95e-4715-9abf-093d525f98cb |
      | invalidSourceCoordinateForMoveOpponentsPiece3 | source coordinate has opponent's piece | Piece does not belong to current player | uid:5f61ddb9-3f5e-4f34-b0a4-123e3c30aef9 |

  Scenario Outline: Invalid Destination Coordinate for Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | destination coordinate is outside of the board | Destination Valid? false | uid:18dbcbef-5cee-4238-9049-1398515b14ca |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | destination coordinate is outside of the board | Destination Valid? false | uid:7b7d0929-bc3f-463f-bccb-51ca61c44f17 |
      | invalidDestinationCoordinateForMoveUnplayableColor1 | destination coordinate is not of valid square color | Destination Valid? false | uid:1b88c08a-67b5-4158-9bfc-b580c011b5f2 |
      | invalidDestinationCoordinateForMoveUnplayableColor2 | destination coordinate is not of valid square color | Destination Valid? false | uid:dcd459a2-a574-41fb-98c8-c4640f180329 |
      | invalidDestinationCoordinateForMoveOccupied1 | destination coordinate is occupied | A piece at destination coordinate | uid:033130e7-7d0b-4fb5-9740-59c77552024d |
      | invalidDestinationCoordinateForMoveOccupied2 | destination coordinate is occupied | A piece at destination coordinate | uid:7da5101c-b321-48a0-9338-4f6d851e0db0 |
      | invalidDestinationCoordinateForMoveOccupied3 | destination coordinate is occupied | A piece at destination coordinate | uid:5661d182-0e22-40e9-94c1-8c0508d3e116 |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | destination coordinate's direction is not allowed | Destination Valid? false | uid:267ed809-9fa4-4c90-95e7-f9b68e301c94 |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | destination coordinate's direction is not allowed | Destination Valid? false | uid:3888b0e7-9a73-40be-aa64-cca951509c1c |
      | invalidDestinationCoordinateForMoveUnallowedDirection3 | destination coordinate's direction is not allowed | Destination Valid? false | uid:04199fd8-bee6-41b7-9653-ba642fd36f3f |
      | invalidDestinationCoordinateForMoveTooFarAway1 | destination coordinate is more than two squares away | Destination Valid? false | uid:886e29a3-b50f-4698-90b2-693d5495da8b |
      | invalidDestinationCoordinateForMoveTooFarAway2 | destination coordinate is more than two squares away | Destination Valid? false | uid:4b1d651c-ad3f-4f8e-aec6-b0c82676a8ce |
      | invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves1 | move is not a jump move even though there are possible jump moves | If any opponent's pieces can be captured then it must be captured first!!!! | uid:da00ddda-74da-4d40-b933-7670539f9b07 |
      | invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves2 | move is not a jump move even though there are possible jump moves | If any opponent's pieces can be captured then it must be captured first!!!! | uid:1982fe7f-0089-4704-9da4-279bf5249bf5 |
      | invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves3 | move is not a jump move even though there are possible jump moves | If any opponent's pieces can be captured then it must be captured first!!!! | uid:aaa6e732-0f3c-401f-b4f0-09af2a4f6559 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | jumped piece is null | There must be one piece on jump path 0 | uid:fa89591d-1911-4c4d-abaf-4ab4e8d583ce |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | jumped piece is not opponent piece | Jumped Piece Must Be Opponent Piece | uid:875b4902-ab32-4b9b-bede-d656722a0708 |

  Scenario Outline: Crowning the Eligible Piece (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a pawn piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece at the source coordinate becomes a king piece
    And the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | crowningTheEligiblePiece1 | uid:49be234c-e7a2-4dcc-8666-709c7fa5c7b9 |
      | crowningTheEligiblePiece2 | uid:718addf2-52c2-4897-8e3a-95dda58bb60d |
      | crowningTheEligiblePiece3 | uid:5efcaa48-92f3-4f6c-a99f-d712a4505ea0 |
      | crowningTheEligiblePiece4 | uid:eafd3c78-e036-48a6-8ca7-ac52b4689fa6 |
      | crowningTheEligiblePiece5 | uid:0f9fe1b9-038b-45dc-b4e1-9cce778db199 |
      | crowningTheEligiblePiece6 | uid:8449ff16-b30d-4bbe-9365-78648e3fe5b5 |
      | crowningTheEligiblePiece7 | uid:a63dbeb8-bc55-4733-b4f0-aaff44cdf4ac |
      | crowningTheEligiblePiece8 | uid:a83fc15b-f398-4b3f-9ef8-6d26a8983a0a |

  Scenario Outline: End of the Game (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And only one piece of the opponent is present at the game board
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:e3cdd31b-df84-40c7-9cf6-b0ee33be2ac4 |
      | endOfTheGame2 | uid:174ca7d0-b756-4333-9e02-98a2d111d982 |
      | endOfTheGame3 | uid:0f84fc41-0adf-41f4-824a-fa6e90719525 |
      | endOfTheGame4 | uid:950581a9-d84f-4ae3-8d5d-95d91b57228e |
      | endOfTheGame5 | uid:fe61c905-94bf-453e-9666-e07ebe8e1e5a |

  Scenario Outline: End of the Game In Draw (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And none of the players can force a win on the other player
    When one player offers the other to end the game in a draw
    And the other player accepts the offer
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDraw1 | uid:a94e962e-29d6-4f74-8b74-26371952511c |
      | endOfTheGameInDraw2 | uid:b818109a-e741-410e-9f3f-312c4aada157 |
      | endOfTheGameInDraw3 | uid:1a8029ee-6e8c-455d-8179-0d248eb8e244 |
      | endOfTheGameInDraw4 | uid:74c6a712-c2f8-4d28-8289-890b9d1c5f54 |
      | endOfTheGameInDraw5 | uid:cb582ac8-ff70-466d-be46-e0cc04900680 |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece (<hiptest-uid>)
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces of the opponent
    Then the game is ended a draw if the opponent still has one piece on the game board

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 | uid:8160c2ac-c8e8-4c07-9f12-83bdb147c7a2 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 | uid:fc4947e9-33da-4829-b36e-79102b679618 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece3 | uid:d3217d60-b247-41b1-aeec-d473abdd5eb4 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece4 | uid:c8bd5b89-1cfa-4fa5-b5ef-6c45011d2c6e |
      | endOfTheGameInDrawBothPlayersHaveOnePiece5 | uid:d3f63b14-0914-4c5c-b445-5fb72467a0b7 |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Becoming King and Without Jumping (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 39
    When the player makes a regular move without promoting
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | uid:bba0bb7f-7cf2-4189-ba45-e44ade035d66 |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | uid:4396c38f-a409-48c7-9ae4-b4ff0de4f1d2 |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | uid:1b998bd5-7fe1-4fef-b1b4-2b2cde0421be |
      | endOfTheGameInDrawFortyIndecisiveMoves4 | uid:617db18f-913b-4144-a95b-d2d470aeb2d9 |
      | endOfTheGameInDrawFortyIndecisiveMoves5 | uid:43e9bfc8-5fb6-4f8f-81fc-00f8ca5e3941 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:df5b44aa-ec87-467f-91c7-f379f438830c |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:d4634752-85cb-4747-9eba-88880a66babf |
      | endOfTheGameOpponentCantMakeAValidMove3 | uid:997524f3-122e-42b5-85d2-6bebcf7294e9 |
      | endOfTheGameOpponentCantMakeAValidMove4 | uid:144db367-78f7-4786-bb6c-40c7266cf67b |
      | endOfTheGameOpponentCantMakeAValidMove5 | uid:2f21172e-5d77-4c69-8d5f-87783232210b |

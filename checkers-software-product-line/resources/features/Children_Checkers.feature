Feature: Children Checkers


  Background:
    Given the "Children Checkers" game is set up

  Scenario: Start of the Game (1) (uid:9ecdf0ae-1f63-4f75-95c1-f58d00776fdd)
    When the players start the game
    Then the player with the "dark" colored pieces is given the turn

  Scenario Outline: Valid Regular Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | piece_type | hiptest-uid |
      | validRegularMove1 | pawn | uid:dd8dfa74-65c9-4ebb-9cb9-c0b01004029a |
      | validRegularMove2 | pawn | uid:60a8771f-a5c5-4562-bbe3-c5beb63af7a0 |
      | validRegularMove3 | pawn | uid:8e0f0786-f6ab-4c60-ba8f-d61d0c72e243 |
      | validRegularMove4 | pawn | uid:5047b010-2f07-45de-9509-b35e8ac90158 |
      | validRegularMove5 | pawn | uid:0442e42f-ae6a-43a0-8523-57789d44f717 |
      | validRegularMove6 | pawn | uid:ac304013-5c04-4732-8145-ac66ed0feccb |

  Scenario Outline: Valid Jump Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "two" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | piece_type | hiptest-uid |
      | validJumpMove1 | next ingame | pawn | uid:c4358e31-87be-4b56-8bb9-66640c765a29 |
      | validJumpMove2 | next ingame | pawn | uid:a3e58cb6-076d-43e8-a0d5-5218c3d153ce |
      | validJumpMove3 | next ingame | pawn | uid:0d535887-cc41-44a1-92a9-95664db913c7 |
      | validJumpMove4 | current | pawn | uid:56f59af9-2e29-49d1-9342-bbd820114284 |
      | validJumpMove5 | current | pawn | uid:69b3f0a4-b9af-41e8-bc2a-56d707bc7c29 |
      | validJumpMove6 | next ingame | pawn | uid:28736316-cd27-4718-8579-ef44c5531286 |
      | validJumpMove7 | current | pawn | uid:ede45d4d-d926-4f6b-9a8b-48a287c3c968 |
      | validJumpMove8 | next ingame | pawn | uid:4ea2c56b-1ae5-4f68-a316-3c4170cd986d |
      | validJumpMove9 | next ingame | pawn | uid:393e99fa-372f-47cc-a1c6-c3d9c87e8b1f |
      | validJumpMove10 | next ingame | pawn | uid:879056f7-51cd-4026-9cb2-dc87b378256e |
      | validJumpMove11 | next ingame | pawn | uid:a0111f95-b406-43da-b6e9-bcc95042c9e4 |
      | validJumpMove12 | next ingame | pawn | uid:ad47071a-1d52-47bc-a208-56d4f92ac2a2 |
      | validJumpMove13 | next ingame | pawn | uid:906600bf-75d7-467c-a65f-bd0488c03953 |
      | validJumpMove14 | next ingame | pawn | uid:77227075-d8d9-4109-9aa0-dba8d61ed93c |
      | validJumpMove15 | next ingame | pawn | uid:70e692c4-91f6-4bbd-bbd2-aef309002666 |
      | validJumpMove17 | next ingame | pawn | uid:7c40eacf-0b3f-4c53-a309-f96f621d8c98 |

  Scenario Outline: Invalid Source Coordinate for Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:5e2da906-c4f2-47dd-ade7-2621d0ec04a5 |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate | uid:26e93565-416f-4b6f-8b43-239355937d9b |
      | invalidSourceCoordinateForMoveUnplayableColor1 | source coordinate is not of valid square color | No piece at source coordinate | uid:4d3a6aa9-e567-4aa1-a2ed-b24cad033503 |
      | invalidSourceCoordinateForMoveUnplayableColor2 | source coordinate is not of valid square color | No piece at source coordinate | uid:8a0df765-f0e3-4114-b810-141f3fcf37a8 |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:8a37bc63-83c3-4f4c-85de-c0dfd0e5ca1f |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:ea02bcc4-d2a2-4289-8477-9883c79a37e8 |
      | invalidSourceCoordinateForMoveEmpty3 | source coordinate is empty | No piece at source coordinate | uid:f3655b0a-2713-4c57-8916-4abc4ea6d4e7 |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:f06406c1-cf7d-4d4e-a962-50e98e24b21c |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:cd04fdaa-c983-4b48-ac84-f53d497b4ce8 |
      | invalidSourceCoordinateForMoveOpponentsPiece3 | source coordinate has opponent's piece | Piece does not belong to current player | uid:0d505419-078f-439f-98c6-67b1fd89da55 |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | source coordinate of move is different than last jump move’s destination | Destination valid? false | uid:30f64022-5974-43d6-9358-87abcd3405e6 |

  Scenario Outline: Invalid Destination Coordinate for Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | piece_type | invalidity_reason | error_message | hiptest-uid |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | pawn | destination coordinate is outside of the board | Destination Valid? false | uid:481cea30-f410-4957-b0e6-cd553278e8a5 |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | pawn | destination coordinate is outside of the board | Destination Valid? false | uid:5777f9b5-23ac-4064-a580-da9e3757fd9f |
      | invalidDestinationCoordinateForMoveUnplayableColor1 | pawn | destination coordinate is not of valid square color | Destination Valid? false | uid:c418c205-79c0-4a54-89a8-e1474290dbdb |
      | invalidDestinationCoordinateForMoveUnplayableColor2 | pawn | destination coordinate is not of valid square color | Destination Valid? false | uid:1c21ff59-281f-4b0f-b854-65461749e218 |
      | invalidDestinationCoordinateForMoveOccupied1 | pawn | destination coordinate is occupied | A piece at destination coordinate | uid:d451a9e2-abdb-4aa9-b187-010566bbff43 |
      | invalidDestinationCoordinateForMoveOccupied2 | pawn | destination coordinate is occupied | A piece at destination coordinate | uid:23d532ca-ff30-47a5-ae7a-65145a55dbac |
      | invalidDestinationCoordinateForMoveOccupied3 | pawn | destination coordinate is occupied | A piece at destination coordinate | uid:659bcabc-15a8-48c7-a2e2-02fa2312423c |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | pawn | destination coordinate's direction is not allowed | Destination Valid? false | uid:6ba8145b-31a8-4bd7-9f3a-7caee903e5f0 |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | pawn | destination coordinate's direction is not allowed | Destination Valid? false | uid:507abf70-6b6c-4858-ac96-0c124c2672dc |
      | invalidDestinationCoordinateForMoveUnallowedDirection3 | pawn | destination coordinate's direction is not allowed | Destination Valid? false | uid:4b4c1d28-48f4-42d4-8ca9-0c89d23e81c1 |
      | invalidDestinationCoordinateForMoveTooFarAway1 | pawn | destination coordinate is more than two squares away | Destination Valid? false | uid:9cb7b937-fca2-4c10-ae72-dc7d2714629f |
      | invalidDestinationCoordinateForMoveTooFarAway2 | pawn | destination coordinate is more than two squares away | Destination Valid? false | uid:cd89ee92-d96e-41ed-ad8d-04b7aec0e5dc |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | pawn | jumped piece is null | There must be one piece on jump path 0 | uid:457909a4-e3a6-4b59-bbe8-37cbc854f63b |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | pawn | jumped piece is not opponent piece | Jumped Piece Must Be Opponent Piece | uid:0c63e9e0-7bf2-4444-a94a-81c1d0f8490e |

  Scenario Outline: End of the Game (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And only one piece of the opponent is present at the game board
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:b73467fe-346c-4a99-92c9-61d5d59a0518 |
      | endOfTheGame2 | uid:c2cc7993-7311-4c49-871b-000237826cc9 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:0b43d31c-883e-47a0-a463-d90060190246 |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:896a6f95-8d2f-4461-8d7c-2fecb5dd8899 |

  Scenario Outline: End of the Game - Reaching the Crownhead (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | piece_type | hiptest-uid |
      | crowningTheEligiblePiece1 | pawn | uid:c07317ec-3f97-4497-acc9-f345e242c19f |
      | crowningTheEligiblePiece2 | pawn | uid:0d835f7b-c705-4deb-a2cc-b0792f2e6808 |
      | crowningTheEligiblePiece3 | pawn | uid:7a0ade73-0843-45e2-a8c8-42ba67abbf35 |
      | crowningTheEligiblePiece4 | pawn | uid:9d4b3505-700d-4e01-bc32-acf3827a6f5b |
      | crowningTheEligiblePiece5 | pawn | uid:e6ebad3b-3623-4256-a780-8d295c23bd64 |
      | crowningTheEligiblePiece6 | pawn | uid:1e3b6eb8-84dc-4f95-9772-1f4eb6687bd1 |
      | crowningTheEligiblePiece7 | pawn | uid:5bd3f0cd-27e6-4e5a-b792-0d2d89417415 |
      | crowningTheEligiblePiece8 | pawn | uid:9d6e12ff-ebdb-44e3-b3a7-834e8f9f3ca2 |
      | crowningTheEligiblePiece9 | pawn | uid:c164b56a-c36a-4ebc-bb13-b931641602c8 |

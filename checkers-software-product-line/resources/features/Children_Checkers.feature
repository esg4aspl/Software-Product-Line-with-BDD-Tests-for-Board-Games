Feature: Children Checkers


  Background:
    Given the "Children Checkers" game is set up

  Scenario: Start of the Game (1) (uid:14bafcf9-8687-4180-8132-8bf0c9596b82)
    When the players start the game
    Then the player with the "dark" colored pieces is given the turn

  Scenario Outline: Valid Regular Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | piece_type | hiptest-uid |
      | validRegularMove1 | pawn | uid:71d70e10-1eb7-417b-bfad-b89433c6829e |
      | validRegularMove2 | pawn | uid:f625857c-4223-499b-90dc-3267fe95b201 |
      | validRegularMove3 | pawn | uid:699febcd-0aff-4665-9eeb-751bb051998b |
      | validRegularMove4 | pawn | uid:86694a0c-53f2-4b77-8185-3951dd8ff56f |
      | validRegularMove5 | pawn | uid:2396c08b-0262-4a39-b413-9899fa82d72a |
      | validRegularMove6 | pawn | uid:a42d1be4-557d-4963-83e5-1723e833035b |

  Scenario Outline: Valid Jump Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid capture" move
    Then the piece is moved to the destination coordinate
    And the captured opponent piece is removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | piece_type | hiptest-uid |
      | validJumpMove1 | next ingame | pawn | uid:c8761205-8c15-4039-96de-51dac75306cb |
      | validJumpMove2 | next ingame | pawn | uid:0dcb1154-f0fa-46d4-9093-2dc4164bed02 |
      | validJumpMove3 | next ingame | pawn | uid:f3c2eb57-b1a3-4581-967d-428a9ffe4d52 |
      | validJumpMove4 | current | pawn | uid:57cc5f5d-516d-4db6-b10e-1cc14e6ca0de |
      | validJumpMove5 | current | pawn | uid:ae32a2c9-1bdb-447c-a655-ed3282802abe |
      | validJumpMove6 | next ingame | pawn | uid:19279752-04d2-439d-8821-a62e76a162ca |
      | validJumpMove7 | current | pawn | uid:34c8935c-4674-4070-a3c9-f27436af1784 |
      | validJumpMove8 | next ingame | pawn | uid:8578efbf-3e4f-4b42-b8b1-b68dbbd40e8d |
      | validJumpMove9 | next ingame | pawn | uid:6217cf2a-5738-4b4d-85d1-cca0ec56bf70 |
      | validJumpMove10 | next ingame | pawn | uid:65a614d4-a81d-40f1-91b5-44c23a514cf4 |
      | validJumpMove11 | next ingame | pawn | uid:a22ce309-6080-4dcf-b9bc-23ceeec251ee |
      | validJumpMove12 | next ingame | pawn | uid:b4c6e95e-cb3a-4712-a3c7-f83cb0740445 |
      | validJumpMove13 | next ingame | pawn | uid:b441998d-6943-4a25-8a55-65c977e94019 |
      | validJumpMove14 | next ingame | pawn | uid:64ed787a-e653-42f9-bad2-dcef41e6a219 |
      | validJumpMove15 | next ingame | pawn | uid:26ae9153-74b3-4ccd-9052-af8a8decf1fd |
      | validJumpMove17 | next ingame | pawn | uid:0b4b3b66-cb6e-4eec-b417-6b0fb35a0832 |

  Scenario Outline: End of the Game (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:1056cd97-eb6d-4e22-a332-f0e1e7f71713 |
      | endOfTheGame2 | uid:c462301b-027e-44a5-815a-060a73090999 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:319ecb3e-b7d4-4bdc-9e0d-b63cdc0e79bf |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:95acbd36-7f8c-425d-a772-92a9a6a5f95e |

  Scenario Outline: End of the Game - Reaching the Crownhead (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move to opponent's crownhead with a pawn
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | piece_type | hiptest-uid |
      | crowningTheEligiblePiece1 | pawn | uid:bd01c61f-29af-4ac4-a397-89b173a170a3 |
      | crowningTheEligiblePiece2 | pawn | uid:6b8a1fa0-d259-46c6-a3f9-f89a5aaf00b0 |
      | crowningTheEligiblePiece3 | pawn | uid:133f53ed-d328-4f4d-9a7b-56d8f5ca9f86 |
      | crowningTheEligiblePiece4 | pawn | uid:32910932-0d99-4aa8-b0e8-9ba064194871 |
      | crowningTheEligiblePiece5 | pawn | uid:6f5e038a-d959-4d48-b6d7-c293998612d6 |
      | crowningTheEligiblePiece6 | pawn | uid:38ef69ab-756c-46c0-a2af-9e05eba0703e |
      | crowningTheEligiblePiece7 | pawn | uid:6b542c60-f52c-43f3-abfb-4eb74d0c8c73 |
      | crowningTheEligiblePiece8 | pawn | uid:ecde843d-2915-4da2-9ca5-2a3eab21a8cf |
      | crowningTheEligiblePiece9 | pawn | uid:32a813a1-2cb5-4b60-85fe-09d8e0c01fd9 |

  Scenario Outline: Invalid Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "invalid" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another move

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:73cc2381-bd49-4b85-b71c-0b8293cc0b7e |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate | uid:7915081b-6c17-4416-8e15-edb2d1a96f30 |
      | invalidSourceCoordinateForMoveUnplayableColor1 | source coordinate is not of valid square color | No piece at source coordinate | uid:abcfe229-0d12-44d7-a909-86ea9537becc |
      | invalidSourceCoordinateForMoveUnplayableColor2 | source coordinate is not of valid square color | No piece at source coordinate | uid:0a788b66-73ed-44db-a29b-042519e82592 |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:a9be1c3a-bed3-4287-a376-fd715219e8cb |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:e05e7106-0622-4850-aaad-0faab23ee873 |
      | invalidSourceCoordinateForMoveEmpty3 | source coordinate is empty | No piece at source coordinate | uid:4a458769-c594-480c-8201-24db2f752183 |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:e6b12959-1472-433a-8014-85ebce974bad |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:9d242ad5-ad97-47b8-a772-ffb609036cc0 |
      | invalidSourceCoordinateForMoveOpponentsPiece3 | source coordinate has opponent's piece | Piece does not belong to current player | uid:37551f7b-6d85-4cb6-b094-c818382e0f62 |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | source coordinate of move is different than last jump move’s destination | Destination valid? false | uid:9d6d451f-ccf9-4657-81ea-3cdcb93ef4e0 |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | destination coordinate is outside of the board | Destination Valid? false | uid:d4f2184f-87e8-4a1f-a1bc-e509e6d47971 |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | destination coordinate is outside of the board | Destination Valid? false | uid:262b5be3-3465-4ce9-9f3b-2775e2521f7c |
      | invalidDestinationCoordinateForMoveUnplayableColor1 | destination coordinate is not of valid square color | Destination Valid? false | uid:269b3624-1f54-4d21-a168-a721d7f140fb |
      | invalidDestinationCoordinateForMoveUnplayableColor2 | destination coordinate is not of valid square color | Destination Valid? false | uid:f3f4fc39-6c95-4684-a0a5-6790459acc7c |
      | invalidDestinationCoordinateForMoveOccupied1 | destination coordinate is occupied | A piece at destination coordinate | uid:a96d3ae2-a7e5-46de-a8c4-3d99136a69bf |
      | invalidDestinationCoordinateForMoveOccupied2 | destination coordinate is occupied | A piece at destination coordinate | uid:69c336c9-2403-4729-a01c-ca13b3e35041 |
      | invalidDestinationCoordinateForMoveOccupied3 | destination coordinate is occupied | A piece at destination coordinate | uid:94fc8bec-7681-4510-96e3-32a70fbfe2db |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | destination coordinate's direction is not allowed | Destination Valid? false | uid:d0a7e986-28fc-44ca-8460-3d414f26a709 |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | destination coordinate's direction is not allowed | Destination Valid? false | uid:258cea8c-371f-4118-a212-ccc637c3e0c3 |
      | invalidDestinationCoordinateForMoveUnallowedDirection3 | destination coordinate's direction is not allowed | Destination Valid? false | uid:35274fd4-bc2b-4906-bc26-5e394e787e0c |
      | invalidDestinationCoordinateForMoveTooFarAway1 | destination coordinate is more than two squares away | Destination Valid? false | uid:2f0a4b61-bf46-4db2-9679-f52b90c46c4d |
      | invalidDestinationCoordinateForMoveTooFarAway2 | destination coordinate is more than two squares away | Destination Valid? false | uid:b066c714-2325-455d-94c0-33513ad13dcd |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | jumped piece is null | There must be one piece on jump path 0 | uid:7b24665b-cbe5-459e-8a2d-b2562933d398 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | jumped piece is not opponent piece | Jumped Piece Must Be Opponent Piece | uid:41107501-2d54-4b99-97b2-45e7332e6c65 |

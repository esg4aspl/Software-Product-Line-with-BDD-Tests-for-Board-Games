Feature: Children Checkers


  Background:
    Given the "Children Checkers" game is set up

  Scenario: Start of the Game (1) (uid:9ecdf0ae-1f63-4f75-95c1-f58d00776fdd)
    When the players start the game
    Then the player with the dark-colored pieces is given the turn

  Scenario Outline: Valid Regular Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | validRegularMove1 | uid:dd8dfa74-65c9-4ebb-9cb9-c0b01004029a |
      | validRegularMove2 | uid:60a8771f-a5c5-4562-bbe3-c5beb63af7a0 |
      | validRegularMove3 | uid:8e0f0786-f6ab-4c60-ba8f-d61d0c72e243 |
      | validRegularMove4 | uid:5047b010-2f07-45de-9509-b35e8ac90158 |
      | validRegularMove5 | uid:0442e42f-ae6a-43a0-8523-57789d44f717 |
      | validRegularMove6 | uid:1c082bde-3bb3-4920-b669-f171fc8f61d0 |

  Scenario Outline: Valid Jump Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "two" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | hiptest-uid |
      | validJumpMove1 | other | uid:11bf630a-1690-44b6-8966-03a18abda5c8 |
      | validJumpMove2 | other | uid:c3a7c28d-bcb6-45df-8ae0-31e55508459d |
      | validJumpMove3 | other | uid:fdd171fc-495e-44d1-aff1-76628500287f |
      | validJumpMove4 | current | uid:56f59af9-2e29-49d1-9342-bbd820114284 |
      | validJumpMove5 | current | uid:69b3f0a4-b9af-41e8-bc2a-56d707bc7c29 |
      | validJumpMove6 | other | uid:99631be0-4bd4-420f-ac3e-49e2139ebcd0 |
      | validJumpMove7 | current | uid:ede45d4d-d926-4f6b-9a8b-48a287c3c968 |
      | validJumpMove8 | other | uid:592050b4-b125-4ddd-b48a-b4127efe06ac |
      | validJumpMove9 | other | uid:f771e9bb-9240-484f-9cc6-bc1766ea4f3e |
      | validJumpMove10 | other | uid:6b1be1f2-387a-426b-9f69-6ec9d6f40d7b |
      | validJumpMove11 | other | uid:87155ed7-b84a-449b-9609-0265a8c46f59 |
      | validJumpMove12 | other | uid:c6575d25-5354-4544-8c81-1096ce37877a |
      | validJumpMove13 | other | uid:3a700df8-deb4-406e-ae4c-ae538c21c14f |
      | validJumpMove14 | other | uid:2be630d1-45e9-4d64-863d-a62f5f956767 |
      | validJumpMove15 | other | uid:3f5ecf61-18a9-403b-88ca-a75c236c9a38 |
      | validJumpMove17 | other | uid:d42da3c2-47d3-4c10-81da-74e62741520d |

  Scenario Outline: Invalid Source Coordinate for Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
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

  Scenario Outline: Invalid Destination Coordinate for Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | destination coordinate is outside of the board | Destination Valid? false | uid:481cea30-f410-4957-b0e6-cd553278e8a5 |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | destination coordinate is outside of the board | Destination Valid? false | uid:5777f9b5-23ac-4064-a580-da9e3757fd9f |
      | invalidDestinationCoordinateForMoveUnplayableColor1 | destination coordinate is not of valid square color | Destination Valid? false | uid:c418c205-79c0-4a54-89a8-e1474290dbdb |
      | invalidDestinationCoordinateForMoveUnplayableColor2 | destination coordinate is not of valid square color | Destination Valid? false | uid:1c21ff59-281f-4b0f-b854-65461749e218 |
      | invalidDestinationCoordinateForMoveOccupied1 | destination coordinate is occupied | A piece at destination coordinate | uid:d451a9e2-abdb-4aa9-b187-010566bbff43 |
      | invalidDestinationCoordinateForMoveOccupied2 | destination coordinate is occupied | A piece at destination coordinate | uid:23d532ca-ff30-47a5-ae7a-65145a55dbac |
      | invalidDestinationCoordinateForMoveOccupied3 | destination coordinate is occupied | A piece at destination coordinate | uid:659bcabc-15a8-48c7-a2e2-02fa2312423c |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | destination coordinate's direction is not allowed | Destination Valid? false | uid:6ba8145b-31a8-4bd7-9f3a-7caee903e5f0 |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | destination coordinate's direction is not allowed | Destination Valid? false | uid:507abf70-6b6c-4858-ac96-0c124c2672dc |
      | invalidDestinationCoordinateForMoveUnallowedDirection3 | destination coordinate's direction is not allowed | Destination Valid? false | uid:4b4c1d28-48f4-42d4-8ca9-0c89d23e81c1 |
      | invalidDestinationCoordinateForMoveTooFarAway1 | destination coordinate is more than two squares away | Destination Valid? false | uid:9cb7b937-fca2-4c10-ae72-dc7d2714629f |
      | invalidDestinationCoordinateForMoveTooFarAway2 | destination coordinate is more than two squares away | Destination Valid? false | uid:cd89ee92-d96e-41ed-ad8d-04b7aec0e5dc |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | jumped piece is null | There must be one piece on jump path 0 | uid:457909a4-e3a6-4b59-bbe8-37cbc854f63b |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | jumped piece is not opponent piece | Jumped Piece Must Be Opponent Piece | uid:0c63e9e0-7bf2-4444-a94a-81c1d0f8490e |

  Scenario Outline: End of the Game (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | crowningTheEligiblePiece1 | uid:41afb302-bb4d-44f5-aa6d-d67720f66e1d |
      | crowningTheEligiblePiece2 | uid:aae2cdf1-4fce-4f9a-a7f0-817054cd602e |
      | crowningTheEligiblePiece3 | uid:849c65fb-fc09-4e64-92d0-a5e41a988e3b |
      | crowningTheEligiblePiece4 | uid:a402181b-535e-4584-bf0d-bd8015816c50 |
      | crowningTheEligiblePiece5 | uid:f98e2dbb-25ee-4060-8d1e-b0f7a8df2ed5 |
      | crowningTheEligiblePiece6 | uid:7dfe2252-5ff5-4a0e-aa70-09d4e38e83c6 |
      | crowningTheEligiblePiece7 | uid:99d71eb3-37aa-4f76-a9d5-183f94120722 |
      | crowningTheEligiblePiece8 | uid:6032df81-d991-4ef7-91e6-397bed26f5a3 |
      | crowningTheEligiblePiece9 | uid:ae248848-9060-4f62-b42b-00e0c39a5766 |

  Scenario Outline: End of the Game In Draw (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:e4000d54-e400-47f6-be6d-8b3ef6f448c6 |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the other player | uid:98567fef-2f15-424a-af1e-5f90b3c90ef7 |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:1ae76479-bab8-408d-a245-241b22969d77 |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:cb7cda42-ee24-4321-87b0-3283127acec1 |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the other player | uid:77aeb31d-6b15-45c9-95ce-a860d5161977 |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 | uid:444d9698-5698-4ecf-870b-23dfc9ad1d36 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 | uid:35963059-7d93-4957-802c-a42ee661f6ba |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Becoming King and Without Jumping (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 39
    When the player makes a regular move without promoting
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | uid:e75391cd-4cfc-4910-abfa-d817f7ab5680 |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | uid:8d41c05b-4203-49d9-a0ad-64c3a3b6f3f4 |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | uid:043e8090-7cd3-4920-a092-ce165adbddeb |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (1) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:0b43d31c-883e-47a0-a463-d90060190246 |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:896a6f95-8d2f-4461-8d7c-2fecb5dd8899 |

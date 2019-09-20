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

  Scenario Outline: Valid Jump Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "two" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "current" player

    Examples:
      | file_name | hiptest-uid |
      | validJumpMove1 | uid:9ddd874a-9acd-417b-abe3-65f75dc78081 |
      | validJumpMove2 | uid:e635d03a-2ff9-43d5-8a82-db696f0d3e37 |
      | validJumpMove3 | uid:4316cef3-a33b-443c-9cce-10109f4217f0 |
      | validJumpMove4 | uid:aea16597-291b-4fdb-aea2-0a9f32b409cc |
      | validJumpMove5 | uid:1cff9ebd-d41d-4752-8598-6bbcbc448590 |

  Scenario Outline: Invalid Source Coordinate for Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | hiptest-uid |
      | invalidSourceCoordinateForMove1 | uid:b1caa378-eae4-4cf4-9108-0005c7f45cc1 |
      | invalidSourceCoordinateForMove2 | uid:59f80d22-4c32-4fa8-a301-ba5b1565cb47 |
      | invalidSourceCoordinateForMove3 | uid:c8dc7307-886c-4007-a8df-34ae293c8891 |
      | invalidSourceCoordinateForMove4 | uid:c48db39d-7a52-423f-827f-aa4b7919acf7 |
      | invalidSourceCoordinateForMove5 | uid:05a71dc1-d70e-4c31-9dbc-92ea88361c65 |

  Scenario Outline: Invalid Destination Coordinate for Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "destination" coordinate

    Examples:
      | file_name | hiptest-uid |
      | invalidDestinationCoordinateForMove1 | uid:c140c328-ea82-4e72-a34f-2ae23ef6d802 |
      | invalidDestinationCoordinateForMove2 | uid:9ac2eccb-5ae8-44ad-862c-ba55b0254d3c |
      | invalidDestinationCoordinateForMove3 | uid:32703f43-539e-410b-8183-5446a380eaf6 |
      | invalidDestinationCoordinateForMove4 | uid:d3542a28-3cd9-4d2c-9dfc-4cc7eaacf40b |
      | invalidDestinationCoordinateForMove5 | uid:54b30aac-4708-4dac-b99c-c472fa0a81d1 |

  Scenario Outline: Forcing Jump Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And there is a possibility for the player to make a jump move
    When the player picks a move that is not one of the available jump moves
    Then an error message is shown saying "If any opponent's pieces can be captured then it must be captured first!!!!"
    And the player is asked for another "destination" coordinate

    Examples:
      | file_name | hiptest-uid |
      | forcingJumpMove1 | uid:452b3e08-e593-4907-b13a-06fa11a2857b |
      | forcingJumpMove2 | uid:a77e6d7d-3f2e-4d67-b275-0858019d118c |
      | forcingJumpMove3 | uid:bcdf81ec-9066-4af2-aee7-fc6a8682b982 |
      | forcingJumpMove4 | uid:3d17511c-4f80-4ff3-ad34-331ffacdea53 |
      | forcingJumpMove5 | uid:0a30e32c-bef1-446e-aaa6-56f0fcb245b6 |

  Scenario Outline: Jump Move Series End - No More Possible Jump Moves (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the player has performed one or more jump moves
    When the player picks a valid destination coordinate where no more jump moves will be possible
    Then the move is performed
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | jumpMoveSeriesEndNoMorePossibleJumpMoves1 | uid:a86eeae0-6fac-417d-8f3b-3dfac5594e3a |
      | jumpMoveSeriesEndNoMorePossibleJumpMoves2 | uid:e54e7e0b-054e-4a55-9109-adcc8789e9dc |
      | jumpMoveSeriesEndNoMorePossibleJumpMoves3 | uid:0759cf3c-7188-49df-b64f-b3a2263544b2 |
      | jumpMoveSeriesEndNoMorePossibleJumpMoves4 | uid:d1dd3aa5-b183-40d4-b867-799b68273441 |
      | jumpMoveSeriesEndNoMorePossibleJumpMoves5 | uid:8ff0485b-57d6-4478-82f3-ebd9e52e9461 |

  Scenario Outline: Jump Move Series End - Piece Becomes King (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the player has performed one or more jump moves
    When the player picks a valid destination coordinate where his normal piece will become a king piece
    Then the move is performed
    And the piece transformed to a king piece
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | jumpMoveSeriesEndPieceBecomesKing1 | uid:fa010df3-6ed9-49f4-b53b-0d28be9d30e8 |
      | jumpMoveSeriesEndPieceBecomesKing2 | uid:decfaf06-c938-44cb-88d5-0098ee2668c3 |
      | jumpMoveSeriesEndPieceBecomesKing3 | uid:20c2b290-db05-45fb-b023-ad4ecd5b74f0 |
      | jumpMoveSeriesEndPieceBecomesKing4 | uid:c07ac3d6-12f1-4aca-8016-8141d8574523 |
      | jumpMoveSeriesEndPieceBecomesKing5 | uid:b10c64dd-a9c5-4060-85e1-1465e4b6a90e |

  Scenario Outline: Crowning the Eligible Piece (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a move with a normal piece and a destination coordinate in opponent's crownhead
    Then the move is performed
    And the piece transformed to a king piece

    Examples:
      | file_name | hiptest-uid |
      | crowningTheEligiblePiece1 | uid:49be234c-e7a2-4dcc-8666-709c7fa5c7b9 |
      | crowningTheEligiblePiece2 | uid:718addf2-52c2-4897-8e3a-95dda58bb60d |
      | crowningTheEligiblePiece3 | uid:5efcaa48-92f3-4f6c-a99f-d712a4505ea0 |
      | crowningTheEligiblePiece4 | uid:eafd3c78-e036-48a6-8ca7-ac52b4689fa6 |
      | crowningTheEligiblePiece5 | uid:0f9fe1b9-038b-45dc-b4e1-9cce778db199 |

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
    Then the game ends in a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDraw1 | uid:a94e962e-29d6-4f74-8b74-26371952511c |
      | endOfTheGameInDraw2 | uid:b818109a-e741-410e-9f3f-312c4aada157 |
      | endOfTheGameInDraw3 | uid:1a8029ee-6e8c-455d-8179-0d248eb8e244 |
      | endOfTheGameInDraw4 | uid:74c6a712-c2f8-4d28-8289-890b9d1c5f54 |
      | endOfTheGameInDraw5 | uid:cb582ac8-ff70-466d-be46-e0cc04900680 |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Becoming King (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player moves a normal piece to a non-crownhead coordinate
    Then the number of moves without upgrade is incremented by 1
    And the game is ended as a draw if the number of moves without upgrade is 40

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawFortyMovesWithoutBecomingKing1 | uid:49fa6e41-6714-4871-a0ae-472eebc4112d |
      | endOfTheGameInDrawFortyMovesWithoutBecomingKing2 | uid:c34d3f05-82d0-4ae9-ab91-52e42b4f9083 |
      | endOfTheGameInDrawFortyMovesWithoutBecomingKing3 | uid:5a2ad867-3cc8-41d4-be22-0752d45ee0db |
      | endOfTheGameInDrawFortyMovesWithoutBecomingKing4 | uid:141baf0b-ab44-4401-8c46-ed2d15835dea |
      | endOfTheGameInDrawFortyMovesWithoutBecomingKing5 | uid:a8254e63-85b3-4059-8304-2abdcdfa3a9d |

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

  Scenario Outline: End of the Game In Draw - Forty Moves Without Jumps (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player moves a piece without undertaking an opponent piece
    Then the number of moves without undertake is incremented by 1
    And the game is ended as in draw if the number of moves without undertake is 40

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawFortyMovesWithoutJumps1 | uid:81320562-5487-44aa-bc4e-faba2b5a4274 |
      | endOfTheGameInDrawFortyMovesWithoutJumps2 | uid:0da5b531-3274-44b1-adf1-5c9717a6e31b |
      | endOfTheGameInDrawFortyMovesWithoutJumps3 | uid:b3737bad-c6df-429e-913e-291f5b272fd5 |
      | endOfTheGameInDrawFortyMovesWithoutJumps4 | uid:6c2a98ee-ae66-4197-8730-a36bcaf6c0e6 |
      | endOfTheGameInDrawFortyMovesWithoutJumps5 | uid:077ae0f5-eebc-43f7-8faa-6ffa0d73be7d |

  Scenario Outline: End of Game - Opponent Can't Make a Valid Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:596e83da-0e72-4acd-8469-53575343d2f5 |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:6e556aca-4962-4867-8179-8fbbad2c9149 |
      | endOfTheGameOpponentCantMakeAValidMove3 | uid:6f7d14da-aae1-4876-92f6-47c38cc0ef1f |
      | endOfTheGameOpponentCantMakeAValidMove4 | uid:72e5c64c-80b8-43cb-ab10-7d61148f00c2 |
      | endOfTheGameOpponentCantMakeAValidMove5 | uid:94f147bc-c8f1-4aa4-8d60-843ef86e1677 |

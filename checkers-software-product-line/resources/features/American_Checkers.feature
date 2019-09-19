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
      | moveList | uid:ce4e7320-8f08-46dc-8dae-a3d181ad8372 |

  Scenario Outline: Valid Jump Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks a valid destination coordinate that is "two" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "current" player

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:5e7e4e1b-2319-4e9b-923a-805fad347838 |

  Scenario Outline: Invalid Source Coordinate for Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:bb4b18dd-1ca3-4bb6-86b5-ebc89815a336 |

  Scenario Outline: Invalid Destination Coordinate for Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "destination" coordinate

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:2191db0a-deda-4f75-b22a-f379382537f1 |

  Scenario Outline: Forcing Jump Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And there is a possibility for the player to make a jump move
    When the player picks a move that is not one of the available jump moves
    Then an error message is shown saying "If any opponent's pieces can be captured then it must be captured first!!!!"
    And the player is asked for another "destination" coordinate

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:edd4d7c4-70c8-4553-b33e-1f627dfe3332 |

  Scenario Outline: Jump Move Series End - No More Possible Jump Moves (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the player has performed one or more jump moves
    When the player picks a valid destination coordinate where no more jump moves will be possible
    Then the move is performed
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:c9639926-f5ee-4fe0-ac8f-a65d982f8cfe |

  Scenario Outline: Jump Move Series End - Piece Becomes King (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And the player has performed one or more jump moves
    When the player picks a valid destination coordinate where his normal piece will become a king piece
    Then the move is performed
    And the piece transformed to a king piece
    And the next turn is given to the "other" player

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:6107fd90-3720-4a15-a21a-219945513746 |

  Scenario Outline: Crowning the Eligible Piece (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a move with a normal piece and a destination coordinate in opponent's crownhead
    Then the move is performed
    And the piece transformed to a king piece

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:99ef4b4a-94fc-41ad-9f44-1b0737821b8f |

  Scenario Outline: End of the Game (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And only one piece of the opponent is present at the game board
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:ca4ac8a5-e21e-4d49-a77a-db6f5a4616c6 |

  Scenario Outline: End of the Game In Draw (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And none of the players can force a win on the other player
    When one player offers the other to end the game in a draw
    And the other player accepts the offer
    Then the game ends in a draw

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:b0ae6528-27cc-414f-b087-1965298253e9 |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Becoming King (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player moves a normal piece to a non-crownhead coordinate
    Then the number of moves without upgrade is incremented by 1
    And the game is ended as a draw if the number of moves without upgrade is 40

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:6884cb35-4ddc-44e8-8f0e-4d8a126cfe94 |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece (<hiptest-uid>)
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces of the opponent
    Then the game is ended a draw if the opponent still has one piece on the game board

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:cde881dd-eb50-4f3b-8e20-450878e2fa0b |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Jumps (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player moves a piece without undertaking an opponent piece
    Then the number of moves without undertake is incremented by 1
    And the game is ended as in draw if the number of moves without undertake is 40

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:0c135119-ea1b-4883-aec7-dee687b58a71 |

  Scenario Outline: End of Game - Opponent Can't Make a Valid Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | moveList | uid:d6229798-573b-47a6-acc9-db41f369e93a |

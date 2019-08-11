Feature: American Checkers


  Scenario: Start of the Game (uid:7b1a1513-7283-4b78-972c-3d8259e3a0a0)
    Given the "American Checkers" game is set up
    When the players start the game
    Then the player with the dark-colored pieces is given the turn

  Scenario Outline: Marking Valid Moves (<hiptest-uid>)
    Given the player has the current turn
    When the player selects a "<type>" piece that is his own to move
    Then the empty adjacent squares in "<valid_direction>" are playable
    And the empty squares immediately after the "occupied" adjacent square in "<valid_direction>" are playable
    And the playable squares are visually highlighted

    Examples:
      | type | valid_direction | hiptest-uid |
      | uncrowned | forward_diagonal | uid:808edd2f-a5e5-4478-b99e-33776a052895 |
      | king | any_diagonal | uid:357d1df3-4c06-4347-ad09-8c84fe4d58c9 |

  Scenario Outline: Moving a Piece (<hiptest-uid>)
    Given the player has the current turn
    And the player selected a piece to move
    And there are playable squares on the game board
    When the player selects a playable square that is "<distance>" steps away from the original square
    Then the piece is moved to that square
    And the next turn is given to the "<player_position>" player
    And the opponent piece in between target square and original square is removed from the game board
    And the number of removed opponent pieces in this move is one
    And the number of removed player pieces in this move is zero

    Examples:
      | distance | player_position | hiptest-uid |
      | 2 | current | uid:a5fcc33d-8bd3-4576-816c-a7fbed9f0c81 |
      | 1 | next | uid:5532dea8-9256-4033-bb44-fd05859c208d |

  Scenario: Forcing Undertake (uid:363bdeac-1567-48cf-8b8b-0e92c8295117)
    Given the player has the current turn
    And the player selected a piece to move
    And there are playable squares that are two steps away on the game board
    When the player selects a playable square that is not two steps away
    Then the piece is unselected
    And the player is shown an error message

  Scenario: Repeating Undertakes - No Piece Switch (uid:12b4b235-4774-4b35-ad2a-b36bbb4dcaa9)
    Given the player has previously made a move in the current turn
    When the player selects a piece that is different than the last piece he moved
    Then the piece is unselected
    And the player is shown an error message

  Scenario: Crowning the Eligible Piece (uid:102f7ef0-5985-482c-bb3d-2db318741614)
    Given the player has the current turn
    And the player selected a piece to move
    When the player moves the piece to a square in the opponent's crownhead
    Then the selected piece becomes a king piece

  Scenario: End of the Game (uid:da758048-6f0a-4c6f-849b-6fea2dc163bc)
    Given only one piece of the opponent is present at the game board
    When the player undertakes the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

  Scenario: End of the Game In Draw (uid:2d3b3f91-5a94-438a-b7c9-82ee89e0b070)
    Given that none of the players can force a win on the other player
    When one player offers the other to end the game in a draw
    And the other player accepts the offer
    Then the game ends in a draw

  Scenario: End of the Game In Draw - Forty Moves Without Becoming King (uid:5ff81db3-f547-4441-a44f-47dabf7a18a7)
    Given the player has the current turn
    When the player moves a regular piece to a non-crownhead square
    Then the number of moves without upgrade is incremented by 1
    And the game is ended as in draw if the number of moves without upgrade is 40

  Scenario: End of the Game In Draw - Both Players Have One Piece (uid:fb907e8c-f771-466a-a3b3-5d63098cccde)
    Given the player has only one piece on the game board
    When the player undertakes one or multiple pieces of the opponent
    Then the game is ended in draw if the opponent still has one piece on the game board

  Scenario: End of the Game In Draw - Forty Moves Without Undertake (uid:911b3a69-87b4-4206-b5fc-cfc50725c9c4)
    Given the player has the current turn
    When the player moves a piece without undertaking an opponent piece
    Then the number of moves without undertake is incremented by 1
    And the game is ended as in draw if the number of moves without undertake is 40

  Scenario: End of Game - Opponent Can't Make a Valid Move (uid:55fffa2b-3a06-48dd-8be3-2fc41cc6291b)
    Given the player has the current turn
    When the player makes a moves or multiple moves leaving no playable squares for any of the opponent's pieces
    Then the player wins the game
    And the opponent loses the game

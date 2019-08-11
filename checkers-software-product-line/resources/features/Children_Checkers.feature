Feature: Children Checkers


  Scenario: Start of the Game (2) (uid:4cc6eed2-aced-4563-819f-788a0f237230)
    Given the "American Checkers" game is set up
    When the players start the game
    Then the player with the dark-colored pieces is given the turn

  Scenario Outline: Marking Valid Moves (1) (<hiptest-uid>)
    Given the player has the current turn
    When the player selects a "<type>" piece that is his own to move
    Then the empty adjacent squares in "<valid_direction>" are playable
    And the empty squares immediately after the "occupied" adjacent square in "<valid_direction>" are playable
    And the playable squares are visually highlighted

    Examples:
      | type | valid_direction | hiptest-uid |
      | uncrowned | forward_diagonal | uid:5c5066a9-4309-46d9-8196-75867fbed2ab |
      | king | any_diagonal | uid:80473800-ac8d-4a01-88bc-234c295ea9c5 |

  Scenario Outline: Moving a Piece (2) (<hiptest-uid>)
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
      | 2 | current | uid:4833cf81-1563-4fd3-b68f-b1235b60c586 |
      | 1 | next | uid:d5e1f4af-fbc7-4e37-b3cc-86f2d0ea913e |

  Scenario: Forcing Undertake (1) (uid:3c79140f-1773-4362-870b-1e392fd4b475)
    Given the player has the current turn
    And the player selected a piece to move
    And there are playable squares that are two steps away on the game board
    When the player selects a playable square that is not two steps away
    Then the piece is unselected
    And the player is shown an error message

  Scenario: Repeating Undertakes - No Piece Switch (1) (uid:9edadc6c-5cfb-48ba-8000-eebdf044ae72)
    Given the player has previously made a move in the current turn
    When the player selects a piece that is different than the last piece he moved
    Then the piece is unselected
    And the player is shown an error message

  Scenario: Crowning the Eligible Piece (1) (uid:3e705820-c6b5-4a47-a100-a4a01dd75e4f)
    Given the player has the current turn
    And the player selected a piece to move
    Then the opponent loses the game
    And the player wins the game

  Scenario: End of the Game (2) (uid:d8df547b-8ca6-4483-bb75-340f0dcf38d2)
    Given only one piece of the opponent is present at the game board
    When the player undertakes the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

  Scenario: End of the Game In Draw (1) (uid:ce1c564d-8f55-4966-9921-a5b36b8d41cd)
    Given that none of the players can force a win on the other player
    When one player offers the other to end the game in a draw
    And the other player accepts the offer
    Then the game ends in a draw

  Scenario: End of the Game In Draw - Forty Moves Without Becoming King (1) (uid:6f0e5523-4180-43c9-a317-d3c67959a9ec)
    Given the player has the current turn
    When the player moves a regular piece to a non-crownhead square
    Then the number of moves without upgrade is incremented by 1
    And the game is ended as in draw if the number of moves without upgrade is 40

  Scenario: End of the Game In Draw - Both Players Have One Piece (1) (uid:81bfd0b2-7044-4e68-b054-889b5d271856)
    Given the player has only one piece on the game board
    When the player undertakes one or multiple pieces of the opponent
    Then the game is ended in draw if the opponent still has one piece on the game board

  Scenario: End of the Game In Draw - Forty Moves Without Undertake (1) (uid:700075ec-1ea6-4856-9add-8c2a46a54700)
    Given the player has the current turn
    When the player moves a piece without undertaking an opponent piece
    Then the number of moves without undertake is incremented by 1
    And the game is ended as in draw if the number of moves without undertake is 40

  Scenario: End of Game - Opponent Can't Make a Valid Move (1) (uid:16306cdd-671d-4be4-95a8-feedd5b31a7a)
    Given the player has the current turn
    When the player makes a moves or multiple moves leaving no playable squares for any of the opponent's pieces
    Then the player wins the game
    And the opponent loses the game

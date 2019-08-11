Feature: Turkish Checkers


  Scenario: Start of the Game (4) (uid:fcf52755-4879-4b12-9078-f46e4d082e07)
    Given the "Turkish Checkers" game is set up
    When the players start the game
    Then the player with the light-colored pieces is given the turn

  Scenario: Marking Valid Moves for Uncrowned Pieces (uid:b7823c25-1a5c-4afd-90d1-a959b6ba84ba)
    Given the player has the current turn
    When the player selects an uncrowned piece to move
    Then all the adjacent empty squares in forward and horizontal directions are playable
    And the empty squares that are in the same direction and immediately after the adjacent square in horizontal and forward directions that is occupied by opponent's piece is registered as playable
    And the playable squares are visually highlighted

  Scenario: Marking Valid Moves for King Pieces (uid:0b293cb7-2fdc-415e-a379-bcee36eabb7f)
    Given the player has the current turn
    When the player selects a "king piece" to move
    Then all empty squares up to the first occupied square in vertical and horizontal directions are playable
    And all empty squares after the first occupied square and up to the next occupied square in vertical and horizontal directions are playable
    And the playable squares are visually highlighted

  Scenario Outline: Moving a Piece (4) (<hiptest-uid>)
    Given the player has the current turn
    And the player selected a piece that is his own to move
    And there are playable squares on the game board
    When the player selects a playable square that is "<distance>" steps away from the original square
    Then the piece is moved to that square
    And the next turn is given to the "<player_position>" player
    And the opponent piece in between target square and original square is removed from the game board
    And the number of removed opponent pieces in this move is one
    And the number of removed player pieces in this move is zero

    Examples:
      | distance | player_position | hiptest-uid |
      | 4 | current | uid:c80b87c0-f642-4310-b4bd-c2a2099882b3 |
      | 3 | current | uid:7b09dbf8-15be-43ef-b7f8-7507e2daf723 |
      | 2 | current | uid:e08ab42d-c444-4139-8664-b8b431a09bc2 |
      | 1 | next | uid:419503f6-0620-4200-bbb9-3f60d7fd63e4 |

  Scenario: Repeating Undertakes - No Piece Switch (2) (uid:9fe3403b-d76a-49d2-ab1c-c977c492bd39)
    Given the player has previously made a move in the current turn
    When the player selects a piece that is different than the last piece he moved
    Then the piece is unselected
    And the player is shown an error message

  Scenario: Repeating Undertakes - No Full Turn (uid:d0140214-a0de-4644-beec-aa2df1f8b4c4)
    Given the player has previously made a move in the current turn
    And the player selected a piece to move
    When the player tries to move the piece in the direction that is opposite to the last direction he moved
    Then the piece does not move to the target square
    And the player is shown an error message

  Scenario: Crowning the Eligible Piece (2) (uid:42c8ebe7-69d8-487f-b1cd-805a20c1f3e7)
    Given the player has the current turn
    And the player selected a piece to move
    When the player moves the piece to a square in the opponent's crownhead
    Then the selected piece becomes a king piece

  Scenario: End of the Game (4) (uid:f813ae63-139e-4b25-a5e5-e89f24d4a7f0)
    Given only one piece of the opponent is present at the game board
    When the player undertakes the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

  Scenario: End of the Game - Case Two (uid:1b42dc4f-360c-4208-819a-641b77c52ec2)
    Given at least one king piece of the player is present on the game board
    When the player leaves only one piece of the opponent and it is uncrowned
    Then the opponent loses the game
    And the player wins the game

  Scenario: End of the Game In a Draw (uid:05c8b92c-d43a-4f58-9650-f7bea3118c4b)
    Given that none of the players can force a win on the other player
    When one player offers the other to end the game in a draw
    And the other player accepts the offer
    Then the game ends in a draw

  Scenario: End of the Game In Draw - Forty Moves Without Becoming King (2) (uid:de9f5397-e5fa-44dc-bdd2-4e04e609b353)
    Given the player has the current turn
    When the player moves a regular piece to a non-crownhead square
    Then the number of moves without upgrade is incremented by 1
    And the game is ended as in draw if the number of moves without upgrade is 40

  Scenario: End of the Game In Draw - Both Players Have One Piece (2) (uid:c0b05d18-3902-4b54-b481-51958d5f25f2)
    Given the player has only one piece on the game board
    When the player undertakes one or multiple pieces of the opponent
    Then the game is ended in draw if the opponent still has one piece on the game board

  Scenario: End of the Game In Draw - Forty Moves Without Undertake (2) (uid:28f412c9-97f7-49e7-861b-42c9cd32593a)
    Given the player has the current turn
    When the player moves a piece without undertaking an opponent piece
    Then the number of moves without undertake is incremented by 1
    And the game is ended as in draw if the number of moves without undertake is 40

  Scenario: End of Game - Opponent Can't Make a Valid Move (2) (uid:af1afcf5-c385-409c-acdf-f78d65bc4302)
    Given the player has the current turn
    When the player makes a moves or multiple moves leaving no playable squares for any of the opponent's pieces
    Then the player wins the game
    And the opponent loses the game

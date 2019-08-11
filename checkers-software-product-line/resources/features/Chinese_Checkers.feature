Feature: Chinese Checkers


  Scenario: Start of the Game (3) (uid:ea1d2e5f-16ce-4234-8598-bdb811d84d9f)
    Given the "Chinese Checkers" game is set up
    When the players start the game
    Then a random player is given the turn

  Scenario: Marking Valid Moves (2) (uid:fc10f5e7-5f7c-4089-84ea-2d27c6bc333e)
    Given the player has the current turn
    When the player selects a piece the move
    Then all the adjacent empty places in any direction are playable
    And the empty places immediately after the adjacent "occupied" place in any direction are playable
    And the playable squares are visually highlighted

  Scenario Outline: Moving a Piece (3) (<hiptest-uid>)
    Given the player has the current turn
    And the player selected a piece that is his own to move
    And there are playable places on the game board
    When the player selects a playable place that is "<distance>" steps away from the original place
    Then the piece is moved to that place
    And the next turn is given to the "<player_position>" player

    Examples:
      | distance | player_position | hiptest-uid |
      | 2 | current | uid:9a5ab752-57bb-4adb-a830-a553ed34f7b7 |
      | 1 | next | uid:e30725cd-f977-4029-bac0-8d39ce7c9470 |

  Scenario: Forcing the Same Piece for Repeated Moves (uid:02daa51c-f16c-4cb8-a475-83f0aa7318d5)
    Given the player has moved a piece in the current turn
    When the player selects a "different piece" to move
    Then the piece is unselected
    And the player is shown an error message

  Scenario: End of the Game (3) (uid:306eb8f4-2278-49f3-b7bd-ab8d0fff5786)
    Given there is only one empty place in the opposite triangle
    When the player moves a piece to that place
    Then the player wins the game

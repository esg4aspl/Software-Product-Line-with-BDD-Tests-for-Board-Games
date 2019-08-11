Feature: Chess


  Scenario: Start of the Game (1) (uid:a514e760-5a33-410e-af2b-4b0fa4303091)
    Given the "Chess" game is set up
    When the players start the game
    Then the player with the light-colored pieces is given the turn

  Scenario: Marking Landable Squares (uid:ba67991d-7634-42bd-98ea-f4c0b74b4149)
    Given the player has the current turn
    When the player selects a piece to move
    Then all other squares that are not occupied by one of player's pieces or opponent's king are "landable"

  Scenario Outline: Marking Valid Moves for Pawns (<hiptest-uid>)
    Given the player has the current turn
    When the player selects a previously "<pawn_type>" pawn to move
    Then all the squares in forward direction up to and including the last empty square and that are at max "<step_count>" steps away are playable
    And all the adjacent forward diagonal squares occupied by an opponent piece are playable
    And the playable squares are visually highlighted

    Examples:
      | pawn_type | step_count | hiptest-uid |
      | unmoved | 2 | uid:79c3062a-9069-48ae-974b-5e9ee796ca9d |
      | moved | 1 | uid:06a95033-0e22-4bd6-84a1-66e7afffd0aa |

  Scenario Outline: Marking Valid Moves for Rooks, Bishops & Queen (<hiptest-uid>)
    Given the player has the current turn
    When the player selects a "<piece_type>" to move
    Then all the squares up to and including the last "landable" square in "<valid_direction>" directions are playable
    And the playable squares are visually highlighted

    Examples:
      | piece_type | valid_direction | hiptest-uid |
      | rook | orthogonal | uid:fe077f96-1852-4d8b-bfdf-5e9c5223b03b |
      | bishop | diagonal | uid:1fe841c3-eb41-4715-8541-f384de8aa63f |
      | queen | any | uid:ad06b1c3-1661-422a-8f19-934e8a47a50c |

  Scenario: Marking Valid Moves for King (uid:235f84c9-485e-46d4-bb60-227d6f91b605)
    Given the player has the current turn
    When the player selects the king to move
    Then all the "landable" adjacent squares are playable
    And the playable squares are visually highlighted

  Scenario: Marking Valid Moves for Knight (uid:106c5778-b2f7-447a-8dcf-ebc721ff6d13)
    Given the player has the current turn
    When the player selects a knight to move
    Then all the "landable" squares that are three steps away in X-axis and one square away in Y-axis are playable
    And all the "landable" squares that are three steps away in Y-axis and one square away in X-axis are playable
    And the playable squares are visually highlighted

  Scenario: Moving a Piece (1) (uid:60cc105e-bbba-4af3-b726-eb1a536dca04)
    Given the player has the current turn
    And the player selected a piece that is his own to move
    And there are playable squares on the game board
    When the player selects an empty playable square
    Then the selected piece is moved to that square

  Scenario: Moving a Piece and Making Undertakes (uid:e519f38e-b139-4d6f-ab31-f79fe4c9b811)
    Given the player has the current turn
    And the player selected a piece to move
    And there are playable squares on the game board
    When the player selects an occupied playable square
    Then the selected piece is moved to that square
    And the piece in the target square is removed from the game board

  Scenario: End of the Game (1) (uid:766f5b4b-6455-4d09-a331-fff908b4fdbb)
    Given one of the player's pieces can theoretically move to the square that the opponent's king currently stands
    And player can move one of his pieces to any square that is adjacent to the square that opponent's king currently stands
    When there are no moves that the opponent can make to change the given situation
    Then the player wins the game

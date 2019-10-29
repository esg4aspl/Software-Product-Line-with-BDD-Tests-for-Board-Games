Feature: Turkish Checkers

  Background: 
    Given the "Turkish Checkers" game is set up

  Scenario: Start of the Game
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples: 
      | file_name         | piece_type | explanation                                                                 |
      | validRegularMove1 | king       | f: king moves backwards                                                     |
      | validRegularMove2 | pawn       | f: regular move                                                             |
      | validRegularMove3 | pawn       | f: regular move                                                             |
      | validRegularMove4 | pawn       | f: opponent is not blocked, his king can jump backward, game should not end |
      | validRegularMove5 | pawn       | f: opponent is not blocked, his pawn can jump forward, game should not end  |
      | validRegularMove6 | pawn       | f: opponent is not blocked, his pawn can jump forward, game should not end  |
      | validRegularMove7 | king       | f: king can jump multiple squares without capturing                         |
      | validRegularMove8 | king       | f: king can jump multiple squares without capturing                         |

  Scenario Outline: Valid Jump Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name       | next_turn_player | piece_type | explanation                                                                                                                  |
      | validJumpMove1  | other            | pawn       | f: end of jump possibilities, own piece is not jumpable                                                                      |
      | validJumpMove2  | other            | king       | f: end of jump possibilities, own piece is not jumpable                                                                      |
      | validJumpMove3  | other            | pawn       | f: end of jump possibilities, no adjacent piece                                                                              |
      | validJumpMove4  | current          | pawn       | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove5)                   |
      | validJumpMove5  | current          | pawn       | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove4)                   |
      | validJumpMove6  | other            | pawn       | s: end of jump possibilities, opponent is not jumpable because destination would be out of borders                           |
      | validJumpMove7  | current          | king       | f: another jump possibility                                                                                                  |
      | validJumpMove8  | current          | king       | f: another jump possiblity, opponent piece in distance                                                                       |
      | validJumpMove9  | other            | king       | f: this valid jump move proves the case in validRegularMove4                                                                 |
      | validJumpMove10 | other            | pawn       | f: this valid jump move proves the case in validRegularMove6                                                                 |
      | validJumpMove11 | other            | pawn       | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw                            |
      | validJumpMove12 | other            | pawn       | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw                          |
      | validJumpMove13 | other            | pawn       | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw                            |
      | validJumpMove14 | other            | pawn       | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw                          |
      | validJumpMove15 | other            | king       | f: end of jump possiblities, opponent is not jumpable because possible destination is occupied                               |
      | validJumpMove16 | current          | king       | f: another jump possibility, even though the destination is in crownhead, the piece is already king, it can continue jumping |
      | validJumpMove17 | other            | pawn       | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end ind draw            |
      | validJumpMove18 | current          | king       | f: king can jump and capture from distance                                                                                   |
      | validJumpMove19 | other            | king       | s: king can jump and capture from distance                                                                                   |

  Scenario Outline: Invalid Source Coordinate for Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

  Scenario Outline: Invalid Destination Coordinate for Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples: 
      | file_name                                           | piece_type | invalidity_reason                                         | error_message                    |
      | invalidDestinationCoordinateForMovePawnInCrownhead1 | pawn       | the pawn in crownhead did not capture the vulnerable king | Must capture king to be promoted |

  Scenario Outline: Crowning the Eligible Piece
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece is "<action>" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "<next_turn_player>" player

    Examples: 
      | file_name                 | action       | next_turn_player | explanation                                                                     |
      | crowningTheEligiblePiece1 | promoted     | other            | regular move, immediate crowning                                                |
      | crowningTheEligiblePiece2 | promoted     | other            | regular move, immediate crowning                                                |
      | crowningTheEligiblePiece3 | promoted     | other            | jump move, no adjacent vulnerable kings                                         |
      | crowningTheEligiblePiece4 | promoted     | other            | jump move, no adjacent vulnerable kings                                         |
      | crowningTheEligiblePiece5 | not promoted | current          | jump move, adjacent vulnerable kings, crowning is hold until kings are captured |
      | crowningTheEligiblePiece5 | not promoted | current          | jump move, adjacent vulnerable kings, crowning is hold until kings are captured |

  #There is an opponent king threatened from the destination coordinate. It must be captured for the crowning to complete.
  #Only if the piece reached the crownhead with a jump move
  Scenario Outline: Crowning The Eligible Piece - Capturing Kings in the Crownhead
    Given the game is played up to a certain point from file "<file_name>"
    And the player has a "pawn" piece in opponent's crownhead
    When the player jumps over all the vulnerable opponent kings in the crownhead
    Then the piece is "promoted" to a crowned piece
    And the next turn is given to the "other" player

  Scenario Outline: End of the Game
    Given the game is played up to a certain point from file "<file_name>"
    And only one piece of the opponent is present at the game board
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

  Scenario Outline: End of the Game In Draw
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece
    Given the game is played up to a certain point from file "<file_name>"
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 39
    When the player makes a regular move without promoting
    Then the game is ended as a draw

  Scenario Outline: End of the Game In Draw - Same Board State Reached for the Third Time
    Given the game is played up to a certain point from file "<file_name>"
    And there are some board states that have been reached two times
    When the player finishes his turn leaving the board in a previously reached state
    Then the game is ended as a draw

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

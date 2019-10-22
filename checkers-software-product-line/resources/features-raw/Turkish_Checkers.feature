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
    Then the piece at the source coordinate is moved to the destination coordinate
    And the next turn is given to the "other" player


  Scenario Outline: Valid Jump Move
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece at the source coordinate is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player


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
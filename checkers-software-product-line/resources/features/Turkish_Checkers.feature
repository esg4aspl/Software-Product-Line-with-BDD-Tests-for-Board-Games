Feature: Turkish Checkers


  Background:
    Given the "Turkish Checkers" game is set up

  Scenario: Start of the Game (3) (uid:9da3fb36-0a5f-42c8-90f4-8e6f8a422ad3)
    When the players start the game
    Then the player with the "light" colored pieces is given the turn

  Scenario Outline: Valid Regular Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "one" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "other" player

    Examples:
      | file_name | piece_type | explanation | hiptest-uid |
      | validRegularMove1 | king | f: king moves backwards | uid:92230b9f-4405-4a77-9894-f92c2ddf2b6b |
      | validRegularMove2 | pawn | f: regular move | uid:7783d338-7d06-4a93-930d-a8b0da9d4110 |
      | validRegularMove3 | pawn | f: regular move | uid:65bb3936-767e-4bd0-82ac-c5257ab992dd |
      | validRegularMove4 | pawn | f: opponent is not blocked, his king can jump backward, game should not end | uid:cfb04b99-ab57-4171-9ad8-67623ebfd20d |
      | validRegularMove5 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:46870b71-dc5e-4b5f-ad97-938f97745a5a |
      | validRegularMove6 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:57b1da21-3454-4d82-a0d2-1c5ffc91bfe6 |
      | validRegularMove7 | king | f: king can jump multiple squares without capturing | uid:1a5f0d0a-d761-4562-8bf0-dd96932de7d0 |
      | validRegularMove8 | king | f: king can jump multiple squares without capturing | uid:0258a9c8-498c-4387-b884-dbb1627b4b0a |

  Scenario Outline: Valid Jump Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks a valid destination coordinate that is "multiple" squares away from the source coordinate
    Then the piece is moved to the destination coordinate
    And the opponent piece in between the source and destination coordinates are removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | piece_type | explanation | hiptest-uid |
      | validJumpMove1 | other | pawn | f: end of jump possibilities, own piece is not jumpable | uid:6aed8a33-b560-4d13-8d2f-7a1aa1b5e474 |
      | validJumpMove2 | other | king | f: end of jump possibilities, own piece is not jumpable | uid:6a3fb173-9a4c-420d-9d07-8a0548496456 |
      | validJumpMove3 | other | pawn | f: end of jump possibilities, no adjacent piece | uid:83033f88-fe7d-4972-9f09-db28fd9e0b56 |
      | validJumpMove4 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove5) | uid:0d6881c4-fa16-4f46-bfd1-a0f689380c20 |
      | validJumpMove5 | current | pawn | s: another jump possibility, also shows that any of the best sequences can be chosen (with validJumpMove4) | uid:558b2de6-6e6c-44e1-8ad9-7bc9a72289d2 |
      | validJumpMove6 | other | pawn | s: end of jump possibilities, opponent is not jumpable because destination would be out of borders | uid:305699ec-69a2-405e-aff6-c1d9d9b27920 |
      | validJumpMove7 | current | king | f: another jump possibility | uid:262190ae-5a0b-47a7-a0de-ec261734fcb4 |
      | validJumpMove8 | current | king | f: another jump possiblity, opponent piece in distance | uid:3c4c027a-7f81-4694-b709-57233f7dc08f |
      | validJumpMove9 | other | king | f: this valid jump move proves the case in validRegularMove4 | uid:afc4a3d2-78fa-46bf-adc8-cc6e905131b7 |
      | validJumpMove10 | other | pawn | f: this valid jump move proves the case in validRegularMove6 | uid:0ad75b67-23e0-4752-81fc-a8c7c9987cc7 |
      | validJumpMove11 | other | pawn | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:52c7a9d1-b850-4f7d-98a9-835fd737fc3c |
      | validJumpMove12 | other | pawn | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw | uid:ee702e74-b330-45aa-be23-c2c9d7189b4d |
      | validJumpMove13 | other | pawn | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:a9f5c3dc-24c8-4900-a9bf-e4ec4bcca85a |
      | validJumpMove14 | other | pawn | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw | uid:83fbeb62-84d5-4f3b-b202-f73105ce8f21 |
      | validJumpMove15 | other | king | f: end of jump possiblities, opponent is not jumpable because possible destination is occupied | uid:49b3f27d-abe7-4fc6-b45a-d08b2521f4c3 |
      | validJumpMove16 | current | king | f: another jump possibility, even though the destination is in crownhead, the piece is already king, it can continue jumping | uid:d92f0334-ae53-465f-b41b-ec0d5252f1ca |
      | validJumpMove17 | other | pawn | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end ind draw | uid:385c8a4f-73a6-4822-bc23-060081f3c6e8 |
      | validJumpMove18 | current | king | f: king can jump and capture from distance | uid:e1d7287e-b9d1-4c22-b24b-97534d182cf3 |
      | validJumpMove19 | other | king | s: king can jump and capture from distance | uid:a35ada50-2cca-45ff-b6b9-8ba74d10560e |

  Scenario: Invalid Source Coordinate for Move (3) (uid:32444ded-193a-42d5-931b-9e18e045b4ae)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks an invalid "source" coordinate because "<invalidity_reason>"
    And the player picks any destination coordinate
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

  Scenario Outline: Invalid Destination Coordinate for Move (3) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "<piece_type>" piece in it
    And the player picks an invalid "destination" coordinate because "<invalidity_reason>"
    Then an error message is shown saying "<error_message>"
    And the player is asked for another "source" coordinate

    Examples:
      | file_name | piece_type | invalidity_reason | error_message | hiptest-uid |
      | invalidDestinationCoordinateForMovePawnInCrownhead1 | pawn | the pawn in crownhead did not capture the vulnerable king | Must capture king to be promoted | uid:405bc021-759c-4c8e-9006-e536f278c02b |

  Scenario Outline: Crowning the Eligible Piece (2) (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player picks a valid source coordinate that has a "pawn" piece in it
    And the player picks a valid destination coordinate in opponent's crownhead
    Then the piece is "<action>" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | action | next_turn_player | explanation | hiptest-uid |
      | crowningTheEligiblePiece1 | promoted | other | regular move, immediate crowning | uid:ed8885b8-0498-4639-a504-f669e66d7fb8 |
      | crowningTheEligiblePiece2 | promoted | other | regular move, immediate crowning | uid:9c11af86-ad61-4301-8fe4-227ec735118f |
      | crowningTheEligiblePiece3 | promoted | other | jump move, no adjacent vulnerable kings | uid:b137bf52-c730-4115-adc7-201dcb8765af |
      | crowningTheEligiblePiece4 | promoted | other | jump move, no adjacent vulnerable kings | uid:d9abcc4f-41b0-4983-bcf8-e5635e503f62 |
      | crowningTheEligiblePiece5 | not promoted | current | jump move, adjacent vulnerable kings, crowning is hold until kings are captured | uid:bb595147-4343-436f-8b4b-1f8918690e36 |
      | crowningTheEligiblePiece5 | not promoted | current | jump move, adjacent vulnerable kings, crowning is hold until kings are captured | uid:d072be6b-8992-4dab-88c6-e9e012444a01 |

  Scenario: Crowning The Eligible Piece - Capturing Kings in the Crownhead (uid:e673f9fd-2594-457a-a233-ac9109c3faa4)
    Given the game is played up to a certain point from file "<file_name>"
    And the player has a "pawn" piece in opponent's crownhead
    When the player jumps over all the vulnerable opponent kings in the crownhead
    Then the piece is "promoted" to a crowned piece
    And the next turn is given to the "other" player

  Scenario: End of the Game (3) (uid:e8bde175-5804-416d-a9a4-c3ccfaf77a94)
    Given the game is played up to a certain point from file "<file_name>"
    And only one piece of the opponent is present at the game board
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

  Scenario: End of the Game In Draw (2) (uid:4126edd3-ad00-41ab-880b-cd70ba4b2293)
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

  Scenario: End of the Game In Draw - Both Players Have One Piece (2) (uid:2d18acb1-9ae7-4d7d-92f2-983191881d8c)
    Given the game is played up to a certain point from file "<file_name>"
    Given the player has only one piece on the game board
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

  Scenario: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping (2) (uid:542ea6f5-dd2f-4f64-95a3-48bcea738ab4)
    Given the game is played up to a certain point from file "<file_name>"
    And the number of consecutive indecisive moves is 39
    When the player makes a regular move without promoting
    Then the game is ended as a draw

  Scenario: End of the Game In Draw - Same Board State Reached for the Third Time (uid:2bf06d8f-3433-4aa7-8dd1-4780cc5e8f72)
    Given the game is played up to a certain point from file "<file_name>"
    And there are some board states that have been reached two times
    When the player finishes his turn leaving the board in a previously reached state
    Then the game is ended as a draw

  Scenario: End of the Game - Opponent Can't Make a Valid Move (3) (uid:4d14abc4-c2d4-4a0a-a1b6-82fddd5d776c)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

Feature: American Checkers


  Background:
    Given the "American Checkers" game is set up

  Scenario: Start of the Game (uid:d58bc6f9-adac-4806-99aa-f7ce8c0dd06c)
    When the players start the game
    Then the player with the "dark" colored pieces is given the turn

  Scenario Outline: Valid Regular Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | piece_type | explanation | hiptest-uid |
      | validRegularMove1 | king | f: king moves backwards | uid:0292c46e-1d1f-4fd2-bc72-50fd5a3a7c3f |
      | validRegularMove2 | pawn | f: regular move | uid:da67a276-c6f9-44df-85b9-699875d4860e |
      | validRegularMove3 | pawn | f: regular move | uid:af66f1e8-41d5-459c-bc13-6e9d846a87ec |
      | validRegularMove4 | pawn | f: opponent is not blocked, his king can jump backward, game should not end | uid:0fa50061-8368-4d72-a460-8124e48a1bb0 |
      | validRegularMove5 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:5426b0e6-d325-416f-a9fb-5daa59a9e203 |
      | validRegularMove6 | pawn | f: opponent is not blocked, his pawn can jump forward, game should not end | uid:ecfc0d0e-76db-4eb6-971b-90edd20555f0 |

  Scenario Outline: Valid Jump Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid capture" move
    Then the piece is moved to the destination coordinate
    And the captured opponent piece is removed from the board
    And the next turn is given to the "<next_turn_player>" player

    Examples:
      | file_name | next_turn_player | piece_type | explanation | hiptest-uid |
      | validJumpMove1 | next ingame | pawn | f: end of jump possibilities, own piece is not jumpable | uid:051266be-8b39-42f6-9799-25ed34d35a9e |
      | validJumpMove2 | next ingame | king | f: end of jump possibilities, own piece is not jumpable | uid:bc6d6a3d-235a-487e-bff3-b1bf296cea44 |
      | validJumpMove3 | next ingame | pawn | f: end of jump possibilities, no adjacent piece | uid:c41daf77-1be9-44cd-8d77-1f10457eb2e6 |
      | validJumpMove4 | current | king | f: another jump possibility | uid:b59adc11-1a35-449a-b039-9756c477dd57 |
      | validJumpMove5 | current | pawn | f: another jump possibility | uid:14cb7a12-79c2-4231-9a97-78302d833801 |
      | validJumpMove6 | next ingame | pawn | f: end of jump possibilities, opponent is not jumpable because destination would be out of borders | uid:d3d35df6-aad9-4c8a-8278-39c9c891adb7 |
      | validJumpMove7 | current | king | f: another jump possibility | uid:55c9d6d4-e738-45ad-aa1c-d14ad3c2d349 |
      | validJumpMove8 | next ingame | king | f: end of jump possibilities, own piece is not jumpable | uid:f0d71413-4e89-45ac-ba1c-6c63679094ed |
      | validJumpMove9 | next ingame | king | f: this valid jump move proves the case in validRegularMove4 | uid:25128519-1d83-4d9c-b0a3-812798089418 |
      | validJumpMove10 | next ingame | pawn | f: this valid jump move proves the case in validRegularMove6 | uid:70f1aa3d-f95b-48ee-930f-9d5f73a56955 |
      | validJumpMove11 | next ingame | pawn | f: no promote is 39, no capture is 0, a jump move is a decisive move, game should not end in draw | uid:c1c30acf-b4b5-4dc1-bd30-73de9e5debfb |
      | validJumpMove12 | next ingame | pawn | f: no promote is 0, no capture is 39, this jump move should clear that, game should not end in draw | uid:0b747541-f709-4873-87b7-69e665d3da4d |
      | validJumpMove13 | next ingame | pawn | f: no promote is 45, no capture is 0, a jump move is a decisive move, game should not end in draw  (checked at 45 to see if rule class can keep correct count above 40) | uid:ef7eb9b1-6cfb-434e-bef2-fe8ebdc02647 |
      | validJumpMove14 | next ingame | pawn | f: no promote is 0, no capture is 45, this jump move should clear that, game should not end in draw | uid:be8529fa-72eb-434e-aabd-2725dd883a9c |
      | validJumpMove15 | next ingame | king | f: end of jump possiblities, opponent is not jumpable because possible destination is occupied | uid:e72e5ad0-849c-4d84-9397-39020a8944ee |
      | validJumpMove16 | current | king | f: another jump possibility, even though the destination is in crownhead, the piece is already king, it can continue jumping | uid:80357e1d-adea-42ec-ad0d-6b39084468f8 |
      | validJumpMove17 | next ingame | pawn | f: both players are left with one piece, but one of them is vulnerable to the other, game should not end ind draw | uid:e8974cdd-7f09-4417-9fee-42b0a5cc1a4c |

  Scenario Outline: Crowning the Eligible Piece (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move to opponent's crownhead with a pawn
    Then the piece is "promoted" to a crowned piece
    And the piece is moved to the destination coordinate
    And the next turn is given to the "next ingame" player

    Examples:
      | file_name | explanation | hiptest-uid |
      | crowningTheEligiblePiece1 | f: | uid:317e1082-305b-4835-9df7-5a765067cc0c |
      | crowningTheEligiblePiece2 | f: | uid:e0eac42a-16b8-4553-b2e0-1df4709029e5 |
      | crowningTheEligiblePiece3 | f: there are possibilities for a jump move but piece is crowned, so the next turn should be given to the opponent | uid:b1198060-a4bc-439d-b039-41459a2b379e |
      | crowningTheEligiblePiece4 | f: | uid:937250d4-17c0-48f8-bb6e-539eb544301b |
      | crowningTheEligiblePiece5 | f: there are possibilities for a jump move but piece is crowned, so the next turn should be given to the opponent | uid:fd373fd1-9df2-4782-9bb2-271a5d45572a |
      | crowningTheEligiblePiece6 | f: no promote is 39, no capture is 39, crowning is a decisive move, game should not end in draw | uid:08f088bb-ea5b-4ad5-bc28-8fc28dc64a9d |
      | crowningTheEligiblePiece7 | f: no promote is 45, no capture is 0, crowning is a decisive move, game should not end in draw | uid:25211410-aa2c-4b23-91db-68992c0c93e0 |
      | crowningTheEligiblePiece8 | f: no promote is 0, no capture is 45, crowning is a decisive move, game should not end in draw | uid:dc589572-2c84-470d-919e-f10227b46377 |

  Scenario Outline: End of the Game (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player jumps over the last piece of the opponent
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGame1 | uid:694cdcb8-4f43-4c6f-be5e-1c887f2abc35 |
      | endOfTheGame2 | uid:29b4d41a-9dd0-4467-beb7-09657fedb24f |

  Scenario Outline: End of the Game In Draw (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    And in the previous turn the opponent has offered to end the game in a draw
    When the player "<offer_response>" the offer
    Then "<result>" happens

    Examples:
      | file_name | offer_response | result | hiptest-uid |
      | endOfTheGameInDraw1 | accepts | the game is ended as a draw | uid:b53d37ec-de8e-4b21-a6a0-63b075a0e365 |
      | endOfTheGameInDraw2 | rejects | the next turn is given to the next ingame player | uid:0e950cd4-d1ad-4f56-968e-596a3d593128 |
      | endOfTheGameInDraw3 | accepts | the game is ended as a draw | uid:60804198-a4a4-45e0-90e3-b480e1b1f579 |
      | endOfTheGameInDraw4 | accepts | the game is ended as a draw | uid:d9b8f7c4-e715-4e9f-8989-29ff1b32d323 |
      | endOfTheGameInDraw5 | rejects | the next turn is given to the next ingame player | uid:2eed1778-e083-4a0f-a6cb-4ac16f72b007 |

  Scenario Outline: End of the Game In Draw - Both Players Have One Piece (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move
    Then the game is ended as a draw

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameInDrawBothPlayersHaveOnePiece1 | uid:f1b52603-23e3-42aa-9ca7-11ae7414dcb9 |
      | endOfTheGameInDrawBothPlayersHaveOnePiece2 | uid:e580ae2c-9777-4dfc-8e2c-7802343c7a78 |

  Scenario Outline: End of the Game - Opponent Can't Make a Valid Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a move leaving no valid destination coordinates for any of the opponent's pieces
    Then the opponent loses the game
    And the player wins the game

    Examples:
      | file_name | hiptest-uid |
      | endOfTheGameOpponentCantMakeAValidMove1 | uid:36af85b4-6743-4004-86fa-1a469422f918 |
      | endOfTheGameOpponentCantMakeAValidMove2 | uid:90249256-f831-4272-9701-0a4ca95151fa |

  Scenario Outline: End of the Game In Draw - Forty Moves Without Crowning and Without Jumping (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "valid regular" move
    Then the game is ended as a draw

    Examples:
      | file_name | explanation | hiptest-uid |
      | endOfTheGameInDrawFortyIndecisiveMoves1 | f: no promote is 39, no capture is 39, a regular move ends the game in draw | uid:d9059de0-c0a1-4685-a627-500eda061f7f |
      | endOfTheGameInDrawFortyIndecisiveMoves2 | f: no promote is 45, no capture is 39, a regular move ends the game in draw | uid:57c5acc6-e00c-4357-b0a8-6fa29039bca6 |
      | endOfTheGameInDrawFortyIndecisiveMoves3 | f: no promote is 39, no capture is 45, a regular move ends the game in draw | uid:fa21c68c-20d4-4b96-8962-44de4e86c328 |

  Scenario Outline: Offer Draw Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player offers to end the game in draw
    Then the next turn is given to the "next ingame" player

    Examples:
      | file_name | hiptest-uid |
      | offerDrawMove1 | uid:f46fc834-168a-48f5-bbe3-5822c30bd8ea |

  Scenario Outline: Invalid Move (<hiptest-uid>)
    Given the game is played up to a certain point from file "<file_name>"
    When the player makes a "invalid" move
    Then the piece is not moved
    And an error message is shown saying "<error_message>"
    And the player is asked for another move

    Examples:
      | file_name | invalidity_reason | error_message | hiptest-uid |
      | invalidSourceCoordinateForMoveOutsideBorders1 | source coordinate is outside of the board | No piece at source coordinate | uid:4fbda5f2-f59d-488a-ae26-632fe5040738 |
      | invalidSourceCoordinateForMoveOutsideBorders2 | source coordinate is outside of the board | No piece at source coordinate | uid:69dca382-3e94-4b5e-9dfe-bc8eff12ece8 |
      | invalidSourceCoordinateForMoveUnplayableColor1 | source coordinate is not of valid square color | No piece at source coordinate | uid:47585620-837f-454e-a437-fcfae751e29d |
      | invalidSourceCoordinateForMoveUnplayableColor2 | source coordinate is not of valid square color | No piece at source coordinate | uid:b42b47a1-a329-4a0a-a9c1-1ae7d2870f87 |
      | invalidSourceCoordinateForMoveEmpty1 | source coordinate is empty | No piece at source coordinate | uid:a55bc252-853f-40b7-877c-29ef86c3fd59 |
      | invalidSourceCoordinateForMoveEmpty2 | source coordinate is empty | No piece at source coordinate | uid:d5f2dc3f-35ed-432b-bb2c-2943690929d2 |
      | invalidSourceCoordinateForMoveEmpty3 | source coordinate is empty | No piece at source coordinate | uid:d429acf7-ea85-4296-86bc-6a2b72327e85 |
      | invalidSourceCoordinateForMoveOpponentsPiece1 | source coordinate has opponent's piece | Piece does not belong to current player | uid:a1da2942-f02a-4099-8971-d160299252c2 |
      | invalidSourceCoordinateForMoveOpponentsPiece2 | source coordinate has opponent's piece | Piece does not belong to current player | uid:9205c7c1-b16f-4cf8-844c-181d27579fba |
      | invalidSourceCoordinateForMoveOpponentsPiece3 | source coordinate has opponent's piece | Piece does not belong to current player | uid:1f5b996c-1d9b-463c-9935-9b1b6c5d8df7 |
      | invalidSourceCoordinateForMoveDifferentThanLastMovesDestinationCoordinate1 | source coordinate of move is different than last jump move’s destination | Destination valid? false | uid:111712bc-db06-4f1e-a195-3b8366f97cd2 |
      | invalidDestinationCoordinateForMoveOutsideBorders1 | destination coordinate is outside of the board | Destination Valid? false | uid:40597862-ac0c-4b87-a411-bfab29a2dd0a |
      | invalidDestinationCoordinateForMoveOutsideBorders2 | destination coordinate is outside of the board | Destination Valid? false | uid:beaeddbf-089f-4271-b01d-7f11a17555d6 |
      | invalidDestinationCoordinateForMoveUnplayableColor1 | destination coordinate is not of valid square color | Destination Valid? false | uid:b7710b22-cc28-4d08-8643-a9eb1f23c56b |
      | invalidDestinationCoordinateForMoveUnplayableColor2 | destination coordinate is not of valid square color | Destination Valid? false | uid:fd84aab5-b2bb-4eef-a2c0-7a194db88ecd |
      | invalidDestinationCoordinateForMoveOccupied1 | destination coordinate is occupied | A piece at destination coordinate | uid:879e0bbb-9f9f-4dca-960f-ea6cb598ed39 |
      | invalidDestinationCoordinateForMoveOccupied2 | destination coordinate is occupied | A piece at destination coordinate | uid:a21948b6-a4d8-40ad-9661-94171c9074e3 |
      | invalidDestinationCoordinateForMoveOccupied3 | destination coordinate is occupied | A piece at destination coordinate | uid:9a2f58ca-c5c8-44e3-bde8-db1572b47690 |
      | invalidDestinationCoordinateForMoveUnallowedDirection1 | destination coordinate's direction is not allowed | Destination Valid? false | uid:b56b2955-eeaf-4d6b-a9be-bbf9e9535fbc |
      | invalidDestinationCoordinateForMoveUnallowedDirection2 | destination coordinate's direction is not allowed | Destination Valid? false | uid:7367ef36-8dca-414e-a382-184a0bbd8cd3 |
      | invalidDestinationCoordinateForMoveUnallowedDirection3 | destination coordinate's direction is not allowed | Destination Valid? false | uid:63953512-57cd-4ab9-9221-ff28d8f4c1ca |
      | invalidDestinationCoordinateForMoveTooFarAway1 | destination coordinate is more than two squares away | Destination Valid? false | uid:17d33710-025e-4a17-b4e2-1bd79887dba4 |
      | invalidDestinationCoordinateForMoveTooFarAway2 | destination coordinate is more than two squares away | Destination Valid? false | uid:ffdd8fbd-dc57-491d-b934-be522ab06ef7 |
      | invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves1 | move is not a jump move even though there are possible jump moves | If any opponent's pieces can be captured then it must be captured first!!!! | uid:c5ef6fa5-a887-4db2-bede-fe5dbb505c1d |
      | invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves2 | move is not a jump move even though there are possible jump moves | If any opponent's pieces can be captured then it must be captured first!!!! | uid:f0aca23b-a2f2-48d5-89c3-307a43a710af |
      | invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves3 | move is not a jump move even though there are possible jump moves | If any opponent's pieces can be captured then it must be captured first!!!! | uid:88e2ae1e-a057-4362-8d0f-37cf91ddb38b |
      | invalidDestinationCoordinateForMoveJumpedPieceIsNull1 | jumped piece is null | There must be one piece on jump path 0 | uid:5df7b5f3-e24b-435c-901a-84a5b8608b34 |
      | invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1 | jumped piece is not opponent piece | Jumped Piece Must Be Opponent Piece | uid:e8b55f6e-1fcf-41fc-ad1d-8482ba2a74c0 |
      | invalidDestinationCoordinateForMoveOppositeDirectionOfLastMove1 | move direction is opposite of last jump move's direction | If any opponent's pieces can be captured then it must be captured first!!!! | uid:b9809652-b315-463a-80a4-ac29c3b86c73 |

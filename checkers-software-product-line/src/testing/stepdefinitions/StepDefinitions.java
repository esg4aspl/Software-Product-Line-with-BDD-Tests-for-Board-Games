package testing.stepdefinitions;

import cucumber.api.java.en.*;

public class StepDefinitions {
    public Actionwords actionwords = new Actionwords();

    @Given("^the \"(.*)\" game is set up$")
    public void theP1GameIsSetUp(String p1) {
        actionwords.theP1GameIsSetUp(p1);
    }

    @When("^the players start the game$")
    public void thePlayersStartTheGame() {
        actionwords.thePlayersStartTheGame();
    }

    @Then("^the player with the dark-colored pieces is given the turn$")
    public void thePlayerWithTheDarkcoloredPiecesIsGivenTheTurn() {
        actionwords.thePlayerWithTheDarkcoloredPiecesIsGivenTheTurn();
    }

    @Given("^the player has the current turn$")
    public void thePlayerHasTheCurrentTurn() {
        actionwords.thePlayerHasTheCurrentTurn();
    }

    @When("^the player selects a \"(.*)\" piece that is his own to move$")
    public void thePlayerSelectsAP1PieceThatIsHisOwnToMove(String p1) {
        actionwords.thePlayerSelectsAP1PieceThatIsHisOwnToMove(p1);
    }

    @Then("^the empty adjacent squares in \"(.*)\" are playable$")
    public void theEmptyAdjacentSquaresInP1ArePlayable(String p1) {
        actionwords.theEmptyAdjacentSquaresInP1ArePlayable(p1);
    }

    @Then("^the empty squares immediately after the \"(.*)\" adjacent square in \"(.*)\" are playable$")
    public void theEmptySquaresImmediatelyAfterTheP1AdjacentSquareInP2ArePlayable(String p1, String p2) {
        actionwords.theEmptySquaresImmediatelyAfterTheP1AdjacentSquareInP2ArePlayable(p1, p2);
    }

    @Then("^the playable squares are visually highlighted$")
    public void thePlayableSquaresAreVisuallyHighlighted() {
        actionwords.thePlayableSquaresAreVisuallyHighlighted();
    }

    @Given("^the player selected a piece to move$")
    public void thePlayerSelectedAPieceToMove() {
        actionwords.thePlayerSelectedAPieceToMove();
    }

    @Given("^there are playable squares on the game board$")
    public void thereArePlayableSquaresOnTheGameBoard() {
        actionwords.thereArePlayableSquaresOnTheGameBoard();
    }

    @When("^the player selects a playable square that is \"(.*)\" steps away from the original square$")
    public void thePlayerSelectsAPlayableSquareThatIsP1StepsAwayFromTheOriginalSquare(int p1) {
        actionwords.thePlayerSelectsAPlayableSquareThatIsP1StepsAwayFromTheOriginalSquare(p1);
    }

    @Then("^the piece is moved to that square$")
    public void thePieceIsMovedToThatSquare() {
        actionwords.thePieceIsMovedToThatSquare();
    }

    @Then("^the next turn is given to the \"(.*)\" player$")
    public void theNextTurnIsGivenToTheP1Player(String p1) {
        actionwords.theNextTurnIsGivenToTheP1Player(p1);
    }

    @Then("^the opponent piece in between target square and original square is removed from the game board$")
    public void theOpponentPieceInBetweenTargetSquareAndOriginalSquareIsRemovedFromTheGameBoard() {
        actionwords.theOpponentPieceInBetweenTargetSquareAndOriginalSquareIsRemovedFromTheGameBoard();
    }

    @Then("^the number of removed opponent pieces in this move is one$")
    public void theNumberOfRemovedOpponentPiecesInThisMoveIsOne() {
        actionwords.theNumberOfRemovedOpponentPiecesInThisMoveIsOne();
    }

    @Then("^the number of removed player pieces in this move is zero$")
    public void theNumberOfRemovedPlayerPiecesInThisMoveIsZero() {
        actionwords.theNumberOfRemovedPlayerPiecesInThisMoveIsZero();
    }

    @Given("^there are playable squares that are two steps away on the game board$")
    public void thereArePlayableSquaresThatAreTwoStepsAwayOnTheGameBoard() {
        actionwords.thereArePlayableSquaresThatAreTwoStepsAwayOnTheGameBoard();
    }

    @When("^the player selects a playable square that is not two steps away$")
    public void thePlayerSelectsAPlayableSquareThatIsNotTwoStepsAway() {
        actionwords.thePlayerSelectsAPlayableSquareThatIsNotTwoStepsAway();
    }

    @Then("^the piece is unselected$")
    public void thePieceIsUnselected() {
        actionwords.thePieceIsUnselected();
    }

    @Then("^the player is shown an error message$")
    public void thePlayerIsShownAnErrorMessage() {
        actionwords.thePlayerIsShownAnErrorMessage();
    }

    @Given("^the player has previously made a move in the current turn$")
    public void thePlayerHasPreviouslyMadeAMoveInTheCurrentTurn() {
        actionwords.thePlayerHasPreviouslyMadeAMoveInTheCurrentTurn();
    }

    @When("^the player selects a piece that is different than the last piece he moved$")
    public void thePlayerSelectsAPieceThatIsDifferentThanTheLastPieceHeMoved() {
        actionwords.thePlayerSelectsAPieceThatIsDifferentThanTheLastPieceHeMoved();
    }

    @When("^the player moves the piece to a square in the opponent's crownhead$")
    public void thePlayerMovesThePieceToASquareInTheOpponentsCrownhead() {
        actionwords.thePlayerMovesThePieceToASquareInTheOpponentsCrownhead();
    }

    @Then("^the selected piece becomes a king piece$")
    public void theSelectedPieceBecomesAKingPiece() {
        actionwords.theSelectedPieceBecomesAKingPiece();
    }

    @Given("^only one piece of the opponent is present at the game board$")
    public void onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard() {
        actionwords.onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard();
    }

    @When("^the player undertakes the last piece of the opponent$")
    public void thePlayerUndertakesTheLastPieceOfTheOpponent() {
        actionwords.thePlayerUndertakesTheLastPieceOfTheOpponent();
    }

    @Then("^the opponent loses the game$")
    public void theOpponentLosesTheGame() {
        actionwords.theOpponentLosesTheGame();
    }

    @Then("^the player wins the game$")
    public void thePlayerWinsTheGame() {
        actionwords.thePlayerWinsTheGame();
    }

    @Given("^that none of the players can force a win on the other player$")
    public void thatNoneOfThePlayersCanForceAWinOnTheOtherPlayer() {
        actionwords.thatNoneOfThePlayersCanForceAWinOnTheOtherPlayer();
    }

    @When("^one player offers the other to end the game in a draw$")
    public void onePlayerOffersTheOtherToEndTheGameInADraw() {
        actionwords.onePlayerOffersTheOtherToEndTheGameInADraw();
    }

    @When("^the other player accepts the offer$")
    public void theOtherPlayerAcceptsTheOffer() {
        actionwords.theOtherPlayerAcceptsTheOffer();
    }

    @Then("^the game ends in a draw$")
    public void theGameEndsInADraw() {
        actionwords.theGameEndsInADraw();
    }

    @When("^the player moves a regular piece to a non-crownhead square$")
    public void thePlayerMovesARegularPieceToANoncrownheadSquare() {
        actionwords.thePlayerMovesARegularPieceToANoncrownheadSquare();
    }

    @Then("^the number of moves without upgrade is incremented by 1$")
    public void theNumberOfMovesWithoutUpgradeIsIncrementedBy1() {
        actionwords.theNumberOfMovesWithoutUpgradeIsIncrementedBy1();
    }

    @Then("^the game is ended as in draw if the number of moves without upgrade is 40$")
    public void theGameIsEndedAsInDrawIfTheNumberOfMovesWithoutUpgradeIs40() {
        actionwords.theGameIsEndedAsInDrawIfTheNumberOfMovesWithoutUpgradeIs40();
    }

    @Given("^the player has only one piece on the game board$")
    public void thePlayerHasOnlyOnePieceOnTheGameBoard() {
        actionwords.thePlayerHasOnlyOnePieceOnTheGameBoard();
    }

    @When("^the player undertakes one or multiple pieces of the opponent$")
    public void thePlayerUndertakesOneOrMultiplePiecesOfTheOpponent() {
        actionwords.thePlayerUndertakesOneOrMultiplePiecesOfTheOpponent();
    }

    @Then("^the game is ended in draw if the opponent still has one piece on the game board$")
    public void theGameIsEndedInDrawIfTheOpponentStillHasOnePieceOnTheGameBoard() {
        actionwords.theGameIsEndedInDrawIfTheOpponentStillHasOnePieceOnTheGameBoard();
    }

    @When("^the player moves a piece without undertaking an opponent piece$")
    public void thePlayerMovesAPieceWithoutUndertakingAnOpponentPiece() {
        actionwords.thePlayerMovesAPieceWithoutUndertakingAnOpponentPiece();
    }

    @Then("^the number of moves without undertake is incremented by 1$")
    public void theNumberOfMovesWithoutUndertakeIsIncrementedBy1() {
        actionwords.theNumberOfMovesWithoutUndertakeIsIncrementedBy1();
    }

    @Then("^the game is ended as in draw if the number of moves without undertake is 40$")
    public void theGameIsEndedAsInDrawIfTheNumberOfMovesWithoutUndertakeIs40() {
        actionwords.theGameIsEndedAsInDrawIfTheNumberOfMovesWithoutUndertakeIs40();
    }

    @When("^the player makes a moves or multiple moves leaving no playable squares for any of the opponent's pieces$")
    public void thePlayerMakesAMovesOrMultipleMovesLeavingNoPlayableSquaresForAnyOfTheOpponentsPieces() {
        actionwords.thePlayerMakesAMovesOrMultipleMovesLeavingNoPlayableSquaresForAnyOfTheOpponentsPieces();
    }

    @Then("^the player with the light-colored pieces is given the turn$")
    public void thePlayerWithTheLightcoloredPiecesIsGivenTheTurn() {
        actionwords.thePlayerWithTheLightcoloredPiecesIsGivenTheTurn();
    }

    @When("^the player selects a piece to move$")
    public void thePlayerSelectsAPieceToMove() {
        actionwords.thePlayerSelectsAPieceToMove();
    }

    @Then("^all other squares that are not occupied by one of player's pieces or opponent's king are \"(.*)\"$")
    public void allOtherSquaresThatAreNotOccupiedByOneOfPlayersPiecesOrOpponentsKingAreP1(String p1) {
        actionwords.allOtherSquaresThatAreNotOccupiedByOneOfPlayersPiecesOrOpponentsKingAreP1(p1);
    }

    @When("^the player selects a previously \"(.*)\" pawn to move$")
    public void thePlayerSelectsAPreviouslyP1PawnToMove(String p1) {
        actionwords.thePlayerSelectsAPreviouslyP1PawnToMove(p1);
    }

    @Then("^all the squares in forward direction up to and including the last empty square and that are at max \"(.*)\" steps away are playable$")
    public void allTheSquaresInForwardDirectionUpToAndIncludingTheLastEmptySquareAndThatAreAtMaxP1StepsAwayArePlayable(int p1) {
        actionwords.allTheSquaresInForwardDirectionUpToAndIncludingTheLastEmptySquareAndThatAreAtMaxP1StepsAwayArePlayable(p1);
    }

    @Then("^all the adjacent forward diagonal squares occupied by an opponent piece are playable$")
    public void allTheAdjacentForwardDiagonalSquaresOccupiedByAnOpponentPieceArePlayable() {
        actionwords.allTheAdjacentForwardDiagonalSquaresOccupiedByAnOpponentPieceArePlayable();
    }

    @When("^the player selects a \"(.*)\" to move$")
    public void thePlayerSelectsAP1ToMove(String p1) {
        actionwords.thePlayerSelectsAP1ToMove(p1);
    }

    @Then("^all the squares up to and including the last \"(.*)\" square in \"(.*)\" directions are playable$")
    public void allTheSquaresUpToAndIncludingTheLastP1SquareInP2DirectionsArePlayable(String p1, String p2) {
        actionwords.allTheSquaresUpToAndIncludingTheLastP1SquareInP2DirectionsArePlayable(p1, p2);
    }

    @When("^the player selects the king to move$")
    public void thePlayerSelectsTheKingToMove() {
        actionwords.thePlayerSelectsTheKingToMove();
    }

    @Then("^all the \"(.*)\" adjacent squares are playable$")
    public void allTheP1AdjacentSquaresArePlayable(String p1) {
        actionwords.allTheP1AdjacentSquaresArePlayable(p1);
    }

    @When("^the player selects a knight to move$")
    public void thePlayerSelectsAKnightToMove() {
        actionwords.thePlayerSelectsAKnightToMove();
    }

    @Then("^all the \"(.*)\" squares that are three steps away in X-axis and one square away in Y-axis are playable$")
    public void allTheP1SquaresThatAreThreeStepsAwayInXaxisAndOneSquareAwayInYaxisArePlayable(String p1) {
        actionwords.allTheP1SquaresThatAreThreeStepsAwayInXaxisAndOneSquareAwayInYaxisArePlayable(p1);
    }

    @Then("^all the \"(.*)\" squares that are three steps away in Y-axis and one square away in X-axis are playable$")
    public void allTheP1SquaresThatAreThreeStepsAwayInYaxisAndOneSquareAwayInXaxisArePlayable(String p1) {
        actionwords.allTheP1SquaresThatAreThreeStepsAwayInYaxisAndOneSquareAwayInXaxisArePlayable(p1);
    }

    @Given("^the player selected a piece that is his own to move$")
    public void thePlayerSelectedAPieceThatIsHisOwnToMove() {
        actionwords.thePlayerSelectedAPieceThatIsHisOwnToMove();
    }

    @When("^the player selects an empty playable square$")
    public void thePlayerSelectsAnEmptyPlayableSquare() {
        actionwords.thePlayerSelectsAnEmptyPlayableSquare();
    }

    @Then("^the selected piece is moved to that square$")
    public void theSelectedPieceIsMovedToThatSquare() {
        actionwords.theSelectedPieceIsMovedToThatSquare();
    }

    @When("^the player selects an occupied playable square$")
    public void thePlayerSelectsAnOccupiedPlayableSquare() {
        actionwords.thePlayerSelectsAnOccupiedPlayableSquare();
    }

    @Then("^the piece in the target square is removed from the game board$")
    public void thePieceInTheTargetSquareIsRemovedFromTheGameBoard() {
        actionwords.thePieceInTheTargetSquareIsRemovedFromTheGameBoard();
    }

    @Given("^one of the player's pieces can theoretically move to the square that the opponent's king currently stands$")
    public void oneOfThePlayersPiecesCanTheoreticallyMoveToTheSquareThatTheOpponentsKingCurrentlyStands() {
        actionwords.oneOfThePlayersPiecesCanTheoreticallyMoveToTheSquareThatTheOpponentsKingCurrentlyStands();
    }

    @Given("^player can move one of his pieces to any square that is adjacent to the square that opponent's king currently stands$")
    public void playerCanMoveOneOfHisPiecesToAnySquareThatIsAdjacentToTheSquareThatOpponentsKingCurrentlyStands() {
        actionwords.playerCanMoveOneOfHisPiecesToAnySquareThatIsAdjacentToTheSquareThatOpponentsKingCurrentlyStands();
    }

    @When("^there are no moves that the opponent can make to change the given situation$")
    public void thereAreNoMovesThatTheOpponentCanMakeToChangeTheGivenSituation() {
        actionwords.thereAreNoMovesThatTheOpponentCanMakeToChangeTheGivenSituation();
    }

    @Then("^a random player is given the turn$")
    public void aRandomPlayerIsGivenTheTurn() {
        actionwords.aRandomPlayerIsGivenTheTurn();
    }

    @When("^the player selects a piece the move$")
    public void thePlayerSelectsAPieceTheMove() {
        actionwords.thePlayerSelectsAPieceTheMove();
    }

    @Then("^all the adjacent empty places in any direction are playable$")
    public void allTheAdjacentEmptyPlacesInAnyDirectionArePlayable() {
        actionwords.allTheAdjacentEmptyPlacesInAnyDirectionArePlayable();
    }

    @Then("^the empty places immediately after the adjacent \"(.*)\" place in any direction are playable$")
    public void theEmptyPlacesImmediatelyAfterTheAdjacentP1PlaceInAnyDirectionArePlayable(String p1) {
        actionwords.theEmptyPlacesImmediatelyAfterTheAdjacentP1PlaceInAnyDirectionArePlayable(p1);
    }

    @Given("^there are playable places on the game board$")
    public void thereArePlayablePlacesOnTheGameBoard() {
        actionwords.thereArePlayablePlacesOnTheGameBoard();
    }

    @When("^the player selects a playable place that is \"(.*)\" steps away from the original place$")
    public void thePlayerSelectsAPlayablePlaceThatIsP1StepsAwayFromTheOriginalPlace(int p1) {
        actionwords.thePlayerSelectsAPlayablePlaceThatIsP1StepsAwayFromTheOriginalPlace(p1);
    }

    @Then("^the piece is moved to that place$")
    public void thePieceIsMovedToThatPlace() {
        actionwords.thePieceIsMovedToThatPlace();
    }

    @Given("^the player has moved a piece in the current turn$")
    public void thePlayerHasMovedAPieceInTheCurrentTurn() {
        actionwords.thePlayerHasMovedAPieceInTheCurrentTurn();
    }

    @Given("^there is only one empty place in the opposite triangle$")
    public void thereIsOnlyOneEmptyPlaceInTheOppositeTriangle() {
        actionwords.thereIsOnlyOneEmptyPlaceInTheOppositeTriangle();
    }

    @When("^the player moves a piece to that place$")
    public void thePlayerMovesAPieceToThatPlace() {
        actionwords.thePlayerMovesAPieceToThatPlace();
    }

    @When("^the player selects an uncrowned piece to move$")
    public void thePlayerSelectsAnUncrownedPieceToMove() {
        actionwords.thePlayerSelectsAnUncrownedPieceToMove();
    }

    @Then("^all the adjacent empty squares in forward and horizontal directions are playable$")
    public void allTheAdjacentEmptySquaresInForwardAndHorizontalDirectionsArePlayable() {
        actionwords.allTheAdjacentEmptySquaresInForwardAndHorizontalDirectionsArePlayable();
    }

    @Then("^the empty squares that are in the same direction and immediately after the adjacent square in horizontal and forward directions that is occupied by opponent's piece is registered as playable$")
    public void theEmptySquaresThatAreInTheSameDirectionAndImmediatelyAfterTheAdjacentSquareInHorizontalAndForwardDirectionsThatIsOccupiedByOpponentsPieceIsRegisteredAsPlayable() {
        actionwords.theEmptySquaresThatAreInTheSameDirectionAndImmediatelyAfterTheAdjacentSquareInHorizontalAndForwardDirectionsThatIsOccupiedByOpponentsPieceIsRegisteredAsPlayable();
    }

    @Then("^all empty squares up to the first occupied square in vertical and horizontal directions are playable$")
    public void allEmptySquaresUpToTheFirstOccupiedSquareInVerticalAndHorizontalDirectionsArePlayable() {
        actionwords.allEmptySquaresUpToTheFirstOccupiedSquareInVerticalAndHorizontalDirectionsArePlayable();
    }

    @Then("^all empty squares after the first occupied square and up to the next occupied square in vertical and horizontal directions are playable$")
    public void allEmptySquaresAfterTheFirstOccupiedSquareAndUpToTheNextOccupiedSquareInVerticalAndHorizontalDirectionsArePlayable() {
        actionwords.allEmptySquaresAfterTheFirstOccupiedSquareAndUpToTheNextOccupiedSquareInVerticalAndHorizontalDirectionsArePlayable();
    }

    @When("^the player tries to move the piece in the direction that is opposite to the last direction he moved$")
    public void thePlayerTriesToMoveThePieceInTheDirectionThatIsOppositeToTheLastDirectionHeMoved() {
        actionwords.thePlayerTriesToMoveThePieceInTheDirectionThatIsOppositeToTheLastDirectionHeMoved();
    }

    @Then("^the piece does not move to the target square$")
    public void thePieceDoesNotMoveToTheTargetSquare() {
        actionwords.thePieceDoesNotMoveToTheTargetSquare();
    }

    @Given("^at least one king piece of the player is present on the game board$")
    public void atLeastOneKingPieceOfThePlayerIsPresentOnTheGameBoard() {
        actionwords.atLeastOneKingPieceOfThePlayerIsPresentOnTheGameBoard();
    }

    @When("^the player leaves only one piece of the opponent and it is uncrowned$")
    public void thePlayerLeavesOnlyOnePieceOfTheOpponentAndItIsUncrowned() {
        actionwords.thePlayerLeavesOnlyOnePieceOfTheOpponentAndItIsUncrowned();
    }
}
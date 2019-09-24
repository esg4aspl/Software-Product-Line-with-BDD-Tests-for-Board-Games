package testing.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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

    @Given("^the game is played up to a certain point from file \"(.*)\"$")
    public void theGameIsPlayedUpToACertainPointFromFileP1(String p1) {
        actionwords.theGameIsPlayedUpToACertainPointFromFileP1(p1);
    }

    @When("^the player picks a valid source coordinate$")
    public void thePlayerPicksAValidSourceCoordinate() {
        actionwords.thePlayerPicksAValidSourceCoordinate();
    }

    @Then("^the piece at the source coordinate is moved to the destination coordinate$")
    public void thePieceAtTheSourceCoordinateIsMovedToTheDestinationCoordinate() {
        actionwords.thePieceAtTheSourceCoordinateIsMovedToTheDestinationCoordinate();
    }

    @Then("^the next turn is given to the \"(.*)\" player$")
    public void theNextTurnIsGivenToTheP1Player(String p1) {
        actionwords.theNextTurnIsGivenToTheP1Player(p1);
    }

    @When("^the player picks a valid destination coordinate that is \"(.*)\" squares away from the source coordinate$")
    public void thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(String p1) {
        actionwords.thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(p1);
    }

    @Then("^the opponent piece in between the source and destination coordinates are removed from the board$")
    public void theOpponentPieceInBetweenTheSourceAndDestinationCoordinatesAreRemovedFromTheBoard() {
        actionwords.theOpponentPieceInBetweenTheSourceAndDestinationCoordinatesAreRemovedFromTheBoard();
    }

    @When("^the player picks an invalid \"(.*)\" coordinate because \"(.*)\"$")
    public void thePlayerPicksAnInvalidP1CoordinateBecauseP2(String p1, String p2) {
        actionwords.thePlayerPicksAnInvalidP1CoordinateBecauseP2(p1, p2);
    }

    @When("^the player picks any destination coordinate$")
    public void thePlayerPicksAnyDestinationCoordinate() {
        actionwords.thePlayerPicksAnyDestinationCoordinate();
    }

    @Then("^an error message is shown saying \"(.*)\"$")
    public void anErrorMessageIsShownSayingP1(String p1) {
        actionwords.anErrorMessageIsShownSayingP1(p1);
    }

    @Then("^the player is asked for another \"(.*)\" coordinate$")
    public void thePlayerIsAskedForAnotherP1Coordinate(String p1) {
        actionwords.thePlayerIsAskedForAnotherP1Coordinate(p1);
    }

    @Given("^only one piece of the opponent is present at the game board$")
    public void onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard() {
        actionwords.onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard();
    }

    @When("^the player jumps over the last piece of the opponent$")
    public void thePlayerJumpsOverTheLastPieceOfTheOpponent() {
        actionwords.thePlayerJumpsOverTheLastPieceOfTheOpponent();
    }

    @Then("^the opponent loses the game$")
    public void theOpponentLosesTheGame() {
        actionwords.theOpponentLosesTheGame();
    }

    @Then("^the player wins the game$")
    public void thePlayerWinsTheGame() {
        actionwords.thePlayerWinsTheGame();
    }

    @Given("^none of the players can force a win on the other player$")
    public void noneOfThePlayersCanForceAWinOnTheOtherPlayer() {
        actionwords.noneOfThePlayersCanForceAWinOnTheOtherPlayer();
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

    @When("^the player moves a normal piece to a non-crownhead coordinate$")
    public void thePlayerMovesANormalPieceToANoncrownheadCoordinate() {
        actionwords.thePlayerMovesANormalPieceToANoncrownheadCoordinate();
    }

    @Then("^the number of moves without upgrade is incremented by 1$")
    public void theNumberOfMovesWithoutUpgradeIsIncrementedBy1() {
        actionwords.theNumberOfMovesWithoutUpgradeIsIncrementedBy1();
    }

    @Then("^the game is ended as a draw if the number of moves without upgrade is 40$")
    public void theGameIsEndedAsADrawIfTheNumberOfMovesWithoutUpgradeIs40() {
        actionwords.theGameIsEndedAsADrawIfTheNumberOfMovesWithoutUpgradeIs40();
    }

    @Given("^the player has only one piece on the game board$")
    public void thePlayerHasOnlyOnePieceOnTheGameBoard() {
        actionwords.thePlayerHasOnlyOnePieceOnTheGameBoard();
    }

    @When("^the player jumps over one or multiple pieces of the opponent$")
    public void thePlayerJumpsOverOneOrMultiplePiecesOfTheOpponent() {
        actionwords.thePlayerJumpsOverOneOrMultiplePiecesOfTheOpponent();
    }

    @Then("^the game is ended a draw if the opponent still has one piece on the game board$")
    public void theGameIsEndedADrawIfTheOpponentStillHasOnePieceOnTheGameBoard() {
        actionwords.theGameIsEndedADrawIfTheOpponentStillHasOnePieceOnTheGameBoard();
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

    @When("^the player makes a move leaving no valid destination coordinates for any of the opponent's pieces$")
    public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces() {
        actionwords.thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces();
    }

    @When("^the player picks a valid source coordinate that has a pawn piece in it$")
    public void thePlayerPicksAValidSourceCoordinateThatHasAPawnPieceInIt() {
        actionwords.thePlayerPicksAValidSourceCoordinateThatHasAPawnPieceInIt();
    }

    @When("^the player picks a valid destination coordinate in opponent's crownhead$")
    public void thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead() {
        actionwords.thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead();
    }

    @Then("^the piece at the source coordinate becomes a king piece$")
    public void thePieceAtTheSourceCoordinateBecomesAKingPiece() {
        actionwords.thePieceAtTheSourceCoordinateBecomesAKingPiece();
    }
}
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

    @Given("^the game is played up to a certain point from file \"(.*)\"$")
    public void theGameIsPlayedUpToACertainPointFromFileP1(String p1) {
        actionwords.theGameIsPlayedUpToACertainPointFromFileP1(p1);
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

    @Given("^the player has only one piece on the game board$")
    public void thePlayerHasOnlyOnePieceOnTheGameBoard() {
        actionwords.thePlayerHasOnlyOnePieceOnTheGameBoard();
    }

    @When("^the player makes a move leaving no valid destination coordinates for any of the opponent's pieces$")
    public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces() {
        actionwords.thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces();
    }

    @When("^the player picks a valid destination coordinate in opponent's crownhead$")
    public void thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead() {
        actionwords.thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead();
    }

    @Then("^the game is ended as a draw$")
    public void theGameIsEndedAsADraw() {
        actionwords.theGameIsEndedAsADraw();
    }

    @Given("^the number of consecutive indecisive moves is 39$")
    public void theNumberOfConsecutiveIndecisiveMovesIs39() {
        actionwords.theNumberOfConsecutiveIndecisiveMovesIs39();
    }

    @When("^the player makes a regular move without promoting$")
    public void thePlayerMakesARegularMoveWithoutPromoting() {
        actionwords.thePlayerMakesARegularMoveWithoutPromoting();
    }

    @Given("^in the previous turn the opponent has offered to end the game in a draw$")
    public void inThePreviousTurnTheOpponentHasOfferedToEndTheGameInADraw() {
        actionwords.inThePreviousTurnTheOpponentHasOfferedToEndTheGameInADraw();
    }

    @When("^the player \"(.*)\" the offer$")
    public void thePlayerP1TheOffer(String p1) {
        actionwords.thePlayerP1TheOffer(p1);
    }

    @Then("^\"(.*)\" happens$")
    public void p1Happens(String p1) {
        actionwords.p1Happens(p1);
    }

    @When("^the player jumps over one or multiple pieces leaving the opponent with only one piece that is unable to perform a jump move$")
    public void thePlayerJumpsOverOneOrMultiplePiecesLeavingTheOpponentWithOnlyOnePieceThatIsUnableToPerformAJumpMove() {
        actionwords.thePlayerJumpsOverOneOrMultiplePiecesLeavingTheOpponentWithOnlyOnePieceThatIsUnableToPerformAJumpMove();
    }

    @When("^the player picks a valid source coordinate that has a \"(.*)\" piece in it$")
    public void thePlayerPicksAValidSourceCoordinateThatHasAP1PieceInIt(String p1) {
        actionwords.thePlayerPicksAValidSourceCoordinateThatHasAP1PieceInIt(p1);
    }

    @Then("^the piece is moved to the destination coordinate$")
    public void thePieceIsMovedToTheDestinationCoordinate() {
        actionwords.thePieceIsMovedToTheDestinationCoordinate();
    }

    @Then("^the piece is \"(.*)\" to a crowned piece$")
    public void thePieceIsP1ToACrownedPiece(String p1) {
        actionwords.thePieceIsP1ToACrownedPiece(p1);
    }

    @Then("^the player with the \"(.*)\" colored pieces is given the turn$")
    public void thePlayerWithTheP1ColoredPiecesIsGivenTheTurn(String p1) {
        actionwords.thePlayerWithTheP1ColoredPiecesIsGivenTheTurn(p1);
    }

    @Given("^the player has a \"(.*)\" piece in opponent's crownhead$")
    public void thePlayerHasAP1PieceInOpponentsCrownhead(String p1) {
        actionwords.thePlayerHasAP1PieceInOpponentsCrownhead(p1);
    }

    @When("^the player jumps over all the vulnerable opponent kings in the crownhead$")
    public void thePlayerJumpsOverAllTheVulnerableOpponentKingsInTheCrownhead() {
        actionwords.thePlayerJumpsOverAllTheVulnerableOpponentKingsInTheCrownhead();
    }

    @Given("^there are some board states that have been reached two times$")
    public void thereAreSomeBoardStatesThatHaveBeenReachedTwoTimes() {
        actionwords.thereAreSomeBoardStatesThatHaveBeenReachedTwoTimes();
    }

    @When("^the player finishes his turn leaving the board in a previously reached state$")
    public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState() {
        actionwords.thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState();
    }
}
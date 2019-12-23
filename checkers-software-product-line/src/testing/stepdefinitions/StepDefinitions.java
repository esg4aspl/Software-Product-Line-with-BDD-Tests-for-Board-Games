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

    @Then("^an error message is shown saying \"(.*)\"$")
    public void anErrorMessageIsShownSayingP1(String p1) {
        actionwords.anErrorMessageIsShownSayingP1(p1);
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

    @When("^the player makes a move leaving no valid destination coordinates for any of the opponent's pieces$")
    public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces() {
        actionwords.thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces();
    }

    @Then("^the game is ended as a draw$")
    public void theGameIsEndedAsADraw() {
        actionwords.theGameIsEndedAsADraw();
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

    @When("^the player jumps over all the vulnerable opponent kings in the crownhead$")
    public void thePlayerJumpsOverAllTheVulnerableOpponentKingsInTheCrownhead() {
        actionwords.thePlayerJumpsOverAllTheVulnerableOpponentKingsInTheCrownhead();
    }

    @When("^the player finishes his turn leaving the board in a previously reached state$")
    public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState() {
        actionwords.thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState();
    }

    @Given("^the \"(.*)\" game is set up for \"(.*)\" players$")
    public void theP1GameIsSetUpForP2Players(String p1, String p2) {
        actionwords.theP1GameIsSetUpForP2Players(p1, p2);
    }

    @Given("^the player has been asked to continue or not$")
    public void thePlayerHasBeenAskedToContinueOrNot() {
        actionwords.thePlayerHasBeenAskedToContinueOrNot();
    }

    @When("^the player chooses to \"(.*)\"$")
    public void thePlayerChoosesToP1(String p1) {
        actionwords.thePlayerChoosesToP1(p1);
    }

    @When("^the player jumps moves one of his pieces to the last available square in goal triangle$")
    public void thePlayerJumpsMovesOneOfHisPiecesToTheLastAvailableSquareInGoalTriangle() {
        actionwords.thePlayerJumpsMovesOneOfHisPiecesToTheLastAvailableSquareInGoalTriangle();
    }

    @Then("^the piece is not moved$")
    public void thePieceIsNotMoved() {
        actionwords.thePieceIsNotMoved();
    }

    @When("^the player offers to end the game in draw$")
    public void thePlayerOffersToEndTheGameInDraw() {
        actionwords.thePlayerOffersToEndTheGameInDraw();
    }

    @When("^the player makes a move that threatens the opponent's king's current position$")
    public void thePlayerMakesAMoveThatThreatensTheOpponentsKingsCurrentPosition() {
        actionwords.thePlayerMakesAMoveThatThreatensTheOpponentsKingsCurrentPosition();
    }

    @When("^the player makes a move that not checks the opponent king but leaves it with no valid move to make$")
    public void thePlayerMakesAMoveThatNotChecksTheOpponentKingButLeavesItWithNoValidMoveToMake() {
        actionwords.thePlayerMakesAMoveThatNotChecksTheOpponentKingButLeavesItWithNoValidMoveToMake();
    }

    @When("^the player moves a non-pawn piece without capturing an opponent piece$")
    public void thePlayerMovesANonpawnPieceWithoutCapturingAnOpponentPiece() {
        actionwords.thePlayerMovesANonpawnPieceWithoutCapturingAnOpponentPiece();
    }

    @Then("^the rook is moved to the adjacent coordinate that is towards the center$")
    public void theRookIsMovedToTheAdjacentCoordinateThatIsTowardsTheCenter() {
        actionwords.theRookIsMovedToTheAdjacentCoordinateThatIsTowardsTheCenter();
    }

    @When("^the player makes a \"(.*)\" move$")
    public void thePlayerMakesAP1Move(String p1) {
        actionwords.thePlayerMakesAP1Move(p1);
    }

    @Then("^the captured opponent piece is removed from the board$")
    public void theCapturedOpponentPieceIsRemovedFromTheBoard() {
        actionwords.theCapturedOpponentPieceIsRemovedFromTheBoard();
    }

    @Then("^the player is asked for another move$")
    public void thePlayerIsAskedForAnotherMove() {
        actionwords.thePlayerIsAskedForAnotherMove();
    }

    @When("^the player makes a move to opponent's crownhead with a pawn$")
    public void thePlayerMakesAMoveToOpponentsCrownheadWithAPawn() {
        actionwords.thePlayerMakesAMoveToOpponentsCrownheadWithAPawn();
    }
}
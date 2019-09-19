package testing.stepdefinitions;

import testing.scenariotesters.IScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;

public class Actionwords {
	
	private IScenarioTester scenarioTester;

    public void theP1GameIsSetUp(String p1) {
    	if (p1.equals("American Checkers")) {
    		scenarioTester = new AmericanCheckersScenarioTester();
    	}
    	scenarioTester.theP1GameIsSetUp(p1);
    }

    public void thePlayersStartTheGame() {
    	scenarioTester.thePlayersStartTheGame();
    }

    public void thePlayerWithTheDarkcoloredPiecesIsGivenTheTurn() {
    	
    }

    public void theGameIsPlayedUpToACertainPointFromFileP1(String p1) {
    	scenarioTester.theGameIsPlayedUpToACertainPointFromFileP1(p1);
    }

    public void thePlayerPicksAValidSourceCoordinate() {
    	scenarioTester.thePlayerPicksAValidSourceCoordinate();
    }

    public void thePieceAtTheSourceCoordinateIsMovedToTheDestinationCoordinate() {
    	scenarioTester.thePieceAtTheSourceCoordinateIsMovedToTheDestinationCoordinate();
    }

    public void theNextTurnIsGivenToTheP1Player(String p1) {
    	scenarioTester.theNextTurnIsGivenToTheP1Player(p1);
    }

    public void thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(String p1) {
    	scenarioTester.thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(p1);
    }

    public void theOpponentPieceInBetweenTheSourceAndDestinationCoordinatesAreRemovedFromTheBoard() {
    	scenarioTester.theOpponentPieceInBetweenTheSourceAndDestinationCoordinatesAreRemovedFromTheBoard();
    }

    public void thePlayerPicksAnInvalidP1CoordinateBecauseP2(String p1, String p2) {
    	scenarioTester.thePlayerPicksAnInvalidP1CoordinateBecauseP2(p1, p2);
    }

    public void thePlayerPicksAnyDestinationCoordinate() {
    	scenarioTester.thePlayerPicksAnyDestinationCoordinate();
    }

    public void anErrorMessageIsShownSayingP1(String p1) {
    	scenarioTester.anErrorMessageIsShownSayingP1(p1);
    }

    public void thePlayerIsAskedForAnotherP1Coordinate(String p1) {
    	scenarioTester.thePlayerIsAskedForAnotherP1Coordinate(p1);
    }

    public void thereIsAPossibilityForThePlayerToMakeAJumpMove() {
    	scenarioTester.thereIsAPossibilityForThePlayerToMakeAJumpMove();
    }

    public void thePlayerPicksAMoveThatIsNotOneOfTheAvailableJumpMoves() {
    	scenarioTester.thePlayerPicksAMoveThatIsNotOneOfTheAvailableJumpMoves();
    }

    public void thePlayerHasPerformedOneOrMoreJumpMoves() {
    	scenarioTester.thePlayerHasPerformedOneOrMoreJumpMoves();
    }

    public void thePlayerPicksAValidDestinationCoordinateWhereNoMoreJumpMovesWillBePossible() {
    	scenarioTester.thePlayerPicksAValidDestinationCoordinateWhereNoMoreJumpMovesWillBePossible();
    }

    public void theMoveIsPerformed() {
    	scenarioTester.theMoveIsPerformed();
    }

    public void thePlayerPicksAValidDestinationCoordinateWhereHisNormalPieceWillBecomeAKingPiece() {
    	scenarioTester.thePlayerPicksAValidDestinationCoordinateWhereHisNormalPieceWillBecomeAKingPiece();
    }

    public void thePieceTransformedToAKingPiece() {
    	scenarioTester.thePieceTransformedToAKingPiece();
    }

    public void thePlayerPicksAMoveWithANormalPieceAndADestinationCoordinateInOpponentsCrownhead() {
    	scenarioTester.thePlayerPicksAMoveWithANormalPieceAndADestinationCoordinateInOpponentsCrownhead();
    }

    public void onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard() {
    	scenarioTester.onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard();
    }

    public void thePlayerJumpsOverTheLastPieceOfTheOpponent() {
    	scenarioTester.thePlayerJumpsOverTheLastPieceOfTheOpponent();
    }

    public void theOpponentLosesTheGame() {
    	scenarioTester.theOpponentLosesTheGame();
    }

    public void thePlayerWinsTheGame() {
    	scenarioTester.thePlayerWinsTheGame();
    }

    public void noneOfThePlayersCanForceAWinOnTheOtherPlayer() {
    	scenarioTester.noneOfThePlayersCanForceAWinOnTheOtherPlayer();
    }

    public void onePlayerOffersTheOtherToEndTheGameInADraw() {
    	scenarioTester.onePlayerOffersTheOtherToEndTheGameInADraw();
    }

    public void theOtherPlayerAcceptsTheOffer() {
    	scenarioTester.theOtherPlayerAcceptsTheOffer();
    }

    public void theGameEndsInADraw() {
    	scenarioTester.theGameEndsInADraw();
    }

    public void thePlayerMovesANormalPieceToANoncrownheadCoordinate() {
    	scenarioTester.thePlayerMovesANormalPieceToANoncrownheadCoordinate();
    }

    public void theNumberOfMovesWithoutUpgradeIsIncrementedBy1() {
    	scenarioTester.theNumberOfMovesWithoutUpgradeIsIncrementedBy1();
    }

    public void theGameIsEndedAsADrawIfTheNumberOfMovesWithoutUpgradeIs40() {
    	scenarioTester.theGameIsEndedAsADrawIfTheNumberOfMovesWithoutUpgradeIs40();
    }

    public void thePlayerHasOnlyOnePieceOnTheGameBoard() {
    	scenarioTester.thePlayerHasOnlyOnePieceOnTheGameBoard();
    }

    public void thePlayerJumpsOverOneOrMultiplePiecesOfTheOpponent() {
    	scenarioTester.thePlayerJumpsOverOneOrMultiplePiecesOfTheOpponent();
    }

    public void theGameIsEndedADrawIfTheOpponentStillHasOnePieceOnTheGameBoard() {
    	scenarioTester.theGameIsEndedADrawIfTheOpponentStillHasOnePieceOnTheGameBoard();
    }

    public void thePlayerMovesAPieceWithoutUndertakingAnOpponentPiece() {
    	scenarioTester.thePlayerMovesAPieceWithoutUndertakingAnOpponentPiece();
    }

    public void theNumberOfMovesWithoutUndertakeIsIncrementedBy1() {
    	scenarioTester.theNumberOfMovesWithoutUndertakeIsIncrementedBy1();
    }

    public void theGameIsEndedAsInDrawIfTheNumberOfMovesWithoutUndertakeIs40() {
    	scenarioTester.theGameIsEndedAsInDrawIfTheNumberOfMovesWithoutUndertakeIs40();
    }

	public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces() {
		scenarioTester.thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces();
	}

}
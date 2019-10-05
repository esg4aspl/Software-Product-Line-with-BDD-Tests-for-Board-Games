package testing.stepdefinitions;

import testing.scenariotesters.IScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;
import testing.scenariotesters.checkerschildren.ChildrenCheckersScenarioTester;

public class Actionwords {
	
	private IScenarioTester scenarioTester;

    public void theP1GameIsSetUp(String p1) {
    	if (p1.equals("American Checkers")) {
    		scenarioTester = new AmericanCheckersScenarioTester();
    	} else if (p1.equals("Children Checkers")) {
    		scenarioTester = new ChildrenCheckersScenarioTester();
    	}
    	scenarioTester.theP1GameIsSetUp(p1);
    }

    public void thePlayersStartTheGame() {
    	scenarioTester.thePlayersStartTheGame();
    }

    public void thePlayerWithTheDarkcoloredPiecesIsGivenTheTurn() {
    	scenarioTester.thePlayerWithTheDarkcoloredPiecesIsGivenTheTurn();
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

    public void thePlayerHasOnlyOnePieceOnTheGameBoard() {
    	scenarioTester.thePlayerHasOnlyOnePieceOnTheGameBoard();
    }

	public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces() {
		scenarioTester.thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces();
	}

	public void thePlayerPicksAValidSourceCoordinateThatHasAPawnPieceInIt() {
		scenarioTester.thePlayerPicksAValidSourceCoordinateThatHasAPawnPieceInIt();
	}

	public void thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead() {
		scenarioTester.thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead();
	}

	public void thePieceAtTheSourceCoordinateBecomesAKingPiece() {
		scenarioTester.thePieceAtTheSourceCoordinateBecomesAKingPiece();
	}


	public void theNumberOfConsecutiveIndecisiveMovesIs39() {
		scenarioTester.theNumberOfConsecutiveIndecisiveMovesIs39();
	}

	public void thePlayerMakesARegularMoveWithoutPromoting() {
		scenarioTester.thePlayerMakesARegularMoveWithoutPromoting();
	}

	public void theGameIsEndedAsADraw() {
		scenarioTester.theGameIsEndedAsADraw();
	}

	public void inThePreviousTurnTheOpponentHasOfferedToEndTheGameInADraw() {
		scenarioTester.inThePreviousTurnTheOpponentHasOfferedToEndTheGameInADraw();
	}

	public void thePlayerP1TheOffer(String p1) {
		scenarioTester.thePlayerP1TheOffer(p1);
	}

	public void p1Happens(String p1) {
		scenarioTester.p1Happens(p1);
	}

	public void thePlayerJumpsOverOneOrMultiplePiecesLeavingTheOpponentWithOnlyOnePieceThatIsUnableToPerformAJumpMove() {
		scenarioTester.thePlayerJumpsOverOneOrMultiplePiecesLeavingTheOpponentWithOnlyOnePieceThatIsUnableToPerformAJumpMove();
	}

}
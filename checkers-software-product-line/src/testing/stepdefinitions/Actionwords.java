package testing.stepdefinitions;

import cucumber.api.PendingException;
import testing.scenariotesters.IScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;
import testing.scenariotesters.checkerschildren.ChildrenCheckersScenarioTester;
import testing.scenariotesters.checkerschinese.ChineseCheckersScenarioTester;
import testing.scenariotesters.checkerschinese.IChineseScenarioTester;
import testing.scenariotesters.checkersspanish.SpanishCheckersScenarioTester;
import testing.scenariotesters.checkersturkish.ITurkishScenarioTester;
import testing.scenariotesters.checkersturkish.TurkishCheckersScenarioTester;

public class Actionwords {
	
	private IScenarioTester scenarioTester;

    public void theP1GameIsSetUp(String p1) {
    	if (p1.equals("American Checkers")) {
    		scenarioTester = new AmericanCheckersScenarioTester();
    	} else if (p1.equals("Children Checkers")) {
    		scenarioTester = new ChildrenCheckersScenarioTester();
    	} else if (p1.equals("Spanish Checkers")) {
    		scenarioTester = new SpanishCheckersScenarioTester();
    	} else if (p1.equals("Turkish Checkers")) {
    		scenarioTester = new TurkishCheckersScenarioTester();
    	} else {
    		throw new PendingException("No Such Game");
    	}
    	scenarioTester.theP1GameIsSetUp(p1);
    }

	public void theP1GameIsSetUpForP2Players(String p1, String p2) {
		scenarioTester = new ChineseCheckersScenarioTester();
		((IChineseScenarioTester) scenarioTester).theP1GameIsSetUpForP2Players(p1, p2);
	}
    
    public void thePlayersStartTheGame() {
    	scenarioTester.thePlayersStartTheGame();
    }
    
	public void thePlayerWithTheP1ColoredPiecesIsGivenTheTurn(String p1) {
		scenarioTester.thePlayerWithTheP1ColoredPiecesIsGivenTheTurn(p1);
	}

    public void theGameIsPlayedUpToACertainPointFromFileP1(String p1) {
    	scenarioTester.theGameIsPlayedUpToACertainPointFromFileP1(p1);
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

	public void thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead() {
		scenarioTester.thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead();
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

	public void thePlayerPicksAValidSourceCoordinateThatHasAP1PieceInIt(String p1) {
		scenarioTester.thePlayerPicksAValidSourceCoordinateThatHasAP1PieceInIt(p1);
	}

	public void thePieceIsMovedToTheDestinationCoordinate() {
		scenarioTester.thePieceIsMovedToTheDestinationCoordinate();
	}

	public void thePieceIsP1ToACrownedPiece(String p1) {
		scenarioTester.thePieceIsP1ToACrownedPiece(p1);
	}

	public void thePlayerHasAP1PieceInOpponentsCrownhead(String p1) {
		((ITurkishScenarioTester) scenarioTester).thePlayerHasAP1PieceInOpponentsCrownhead(p1);
	}

	public void thePlayerJumpsOverAllTheVulnerableOpponentKingsInTheCrownhead() {
		((ITurkishScenarioTester) scenarioTester).thePlayerJumpsOverAllTheVulnerableOpponentKingsInTheCrownhead();
	}

	public void thereAreSomeBoardStatesThatHaveBeenReachedTwoTimes() {
		((ITurkishScenarioTester) scenarioTester).thereAreSomeBoardStatesThatHaveBeenReachedTwoTimes();
	}

	public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState() {
		((ITurkishScenarioTester) scenarioTester).thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState();
	}

	public void thePlayerHasMadeJumpMovesInTheCurrentTurn() {
		((IChineseScenarioTester) scenarioTester).thePlayerHasMadeJumpMovesInTheCurrentTurn();
	}

	public void thePlayerCanContinueDoingJumpMoves() {
		((IChineseScenarioTester) scenarioTester).thePlayerCanContinueDoingJumpMoves();
	}

	public void thePlayerHasBeenAskedToContinueOrNot() {
		((IChineseScenarioTester) scenarioTester).thePlayerHasBeenAskedToContinueOrNot();
	}

	public void thePlayerChoosesToP1(String p1) {
		((IChineseScenarioTester) scenarioTester).thePlayerChoosesToP1(p1);
	}

	public void thePlayerHasAtLeastOneOfHisPiecesInTheGoalTriangle() {
		((IChineseScenarioTester) scenarioTester).thePlayerHasAtLeastOneOfHisPiecesInTheGoalTriangle();
	}

	public void thereIsOnlyOneSquareIsAvailableAtTheGoalTriangle() {
		((IChineseScenarioTester) scenarioTester).thereIsOnlyOneSquareIsAvailableAtTheGoalTriangle();
	}

	public void thePlayerJumpsMovesOneOfHisPiecesToTheLastAvailableSquareInGoalTriangle() {
		((IChineseScenarioTester) scenarioTester).thePlayerJumpsMovesOneOfHisPiecesToTheLastAvailableSquareInGoalTriangle();
	}


}
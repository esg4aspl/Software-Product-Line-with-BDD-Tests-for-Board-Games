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
import testing.scenariotesters.chess.ChessScenarioTester;
import testing.scenariotesters.chess.IChessScenarioTester;

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
    	} else if (p1.equals("Chess")) {
    		scenarioTester = new ChessScenarioTester();
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

    public void anErrorMessageIsShownSayingP1(String p1) {
    	scenarioTester.anErrorMessageIsShownSayingP1(p1);
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
    
	public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces() {
		scenarioTester.thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces();
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
		if (scenarioTester instanceof ITurkishScenarioTester) {
			((ITurkishScenarioTester) scenarioTester).thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState();
		} else if (scenarioTester instanceof IChessScenarioTester) {
			((IChessScenarioTester) scenarioTester).thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState();
		} else {
			throw new PendingException();
		}
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

	public void thePieceIsNotMoved() {
		scenarioTester.thePieceIsNotMoved();	
	}

	public void thePlayerOffersToEndTheGameInDraw() {
		this.scenarioTester.thePlayerOffersToEndTheGameInDraw();
	}
	

	public void theOpponentPieceIsRemovedFromTheBoard() {
		((IChessScenarioTester) scenarioTester).theOpponentPieceIsRemovedFromTheBoard();
	}


	public void thePlayerMakesAMoveThatThreatensTheOpponentsKingsCurrentPosition() {
		((IChessScenarioTester) scenarioTester).thePlayerMakesAMoveThatThreatensTheOpponentsKingsCurrentPosition();
	}


	public void thePlayerMovesANonpawnPieceWithoutCapturingAnOpponentPiece() {
		((IChessScenarioTester) scenarioTester).thePlayerMovesANonpawnPieceWithoutCapturingAnOpponentPiece();
	}


	public void theRookIsMovedToTheAdjacentCoordinateThatIsTowardsTheCenter() {
		((IChessScenarioTester) scenarioTester).theRookIsMovedToTheAdjacentCoordinateThatIsTowardsTheCenter();
	}

	public void thePlayerMakesAP1Move(String p1) {
		scenarioTester.thePlayerMakesAP1Move(p1);
	}

	public void theCapturedOpponentPieceIsRemovedFromTheBoard() {
		scenarioTester.theCapturedOpponentPieceIsRemovedFromTheBoard();
	}

	public void thePlayerIsAskedForAnotherMove() {
		scenarioTester.thePlayerIsAskedForAnotherMove();
	}

	public void thePlayerMakesAMoveToOpponentsCrownheadWithAPawn() {
		scenarioTester.thePlayerMakesAMoveToOpponentsCrownheadWithAPawn();
	}

	public void theEnPassantOpponentPieceIsRemovedFromTheBoard() {
		((IChessScenarioTester) scenarioTester).theEnPassantOpponentPieceIsRemovedFromTheBoard();
	}

	
	public void thePlayerMakesAMoveThatNotChecksTheOpponentKingButLeavesItWithNoValidMoveToMake() {
		// TODO Auto-generated method stub
		
	}
	


}
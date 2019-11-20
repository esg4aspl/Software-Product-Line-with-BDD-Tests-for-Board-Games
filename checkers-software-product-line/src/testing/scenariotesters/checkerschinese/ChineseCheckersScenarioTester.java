package testing.scenariotesters.checkerschinese;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import base.Pawn;
import checkersamerican.King;
import checkerschinese.ChinesePawn;
import cucumber.api.PendingException;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.SourceCoordinateValidity;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;

public class ChineseCheckersScenarioTester extends AmericanCheckersScenarioTester implements IChineseScenarioTester {

	
	public ChineseCheckersScenarioTester() {
		try {
			outputter = new PrintWriter(new FileWriter("./src/testing/scenariotesters/checkerschinese/ScenarioTesterOutput.txt", true));
			outputter.println("\nNew Test Run - " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void theP1GameIsSetUpForP2Players(String p1, String p2) {
		int numberOfPlayers = Integer.parseInt(p2);
		referee = new ChineseTesterReferee(new ChineseTestGameConfiguration(numberOfPlayers));
	}

	@Override
	public void thePlayerHasMadeJumpMovesInTheCurrentTurn() {
		assertTrue(info.getPriorMoveList().size() != 0);
	}

	@Override
	public void thePlayerCanContinueDoingJumpMoves() {
		//TODO How to implement this?
	}

	@Override
	public void thePlayerHasBeenAskedToContinueOrNot() {
		assertTrue(((ChineseCheckersTestInfo) info).playerIsAskedToContinue);
	}

	@Override
	public void thePlayerChoosesToP1(String p1) {
		if (p1.equals("continue")) {
			assertTrue(((ChineseCheckersTestInfo) info).playerAnswerToContinuationOfJumpMove);
		} else if (p1.equals("stop")) {
			assertFalse(((ChineseCheckersTestInfo) info).playerAnswerToContinuationOfJumpMove);			
		} else {
			throw new PendingException();
		}
	}

	@Override
	public void thePlayerHasAtLeastOneOfHisPiecesInTheGoalTriangle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void thereIsOnlyOneSquareIsAvailableAtTheGoalTriangle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void thePlayerJumpsMovesOneOfHisPiecesToTheLastAvailableSquareInGoalTriangle() {
		referee.conductGame();
		prepareValidities();
	}
	
	@Override
	public void thePlayerPicksAValidSourceCoordinateThatHasAP1PieceInIt(String p1) {
		referee.conductGame();
		prepareValidities();
		assertEquals(SourceCoordinateValidity.VALID, sourceCoordinateValidityOfPlayerMove);
		if (p1.equals("pawn")) {
			assertTrue(pieceOfPlayerMove instanceof ChinesePawn);
		} else {
			throw new PendingException();
		}
	}
	
	@Override
	public void theOpponentLosesTheGame() {
		//There is no such functionality in the game yet.
		assertTrue(false);
	}

	@Override
	protected boolean invalidDestinationCoordinate(String reason) {
		if (super.invalidDestinationCoordinate(reason)) {
			return true;
		}
		
		if (reason.equals("piece can not leave goal triangle")) {
			//TODO Fill here
		} else {
			return false;
		}
		
		return true;
	}
	
	
	
	

}

package testing.scenariotesters.checkersturkish;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import checkersamerican.King;
import checkersturkish.TurkishGameConfiguration;
import checkersturkish.TurkishKingMoveConstraints;
import checkersturkish.TurkishKingMovePossibilities;
import core.AbstractPiece;
import cucumber.api.PendingException;
import testing.scenariotesters.IScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;

public class TurkishCheckersScenarioTester extends AmericanCheckersScenarioTester implements ITurkishScenarioTester {

	
	/*
	 * TODO
	 * Override methods:
	 * invalid dest. coordinate
	 * 
	
	*/
	
	
	
	/*
	 * Notes: TurkishTestInfo will have a field named "reachedCrownheadWithAJumpMove" as boolean
	 * Then the crowning step will act based on it
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new TurkishTesterReferee(new TurkishGameConfiguration());
	}

	@Override
	public void theGameIsPlayedUpToACertainPointFromFileP1(String p1) {
		referee.setup(p1);
		info = (TurkishCheckersTestInfo) referee.getInfo();
		playerOfPlayerMove = referee.getCurrentPlayer();
		playerMove = referee.getInfo().getPlayerMove();
		output("Testing: " + referee.getInfo().getReader().getSectionName());
		//Set up source coordinate.
		sourceCoordinateOfPlayerMove = playerMove.getSourceCoordinate();
	}

	
	@Override
	public void thePieceIsP1ToACrownedPiece(String p1) {
		AbstractPiece newPiece = getPieceAtCoordinate(destinationCoordinateOfPlayerMove);
		assertTrue(newPiece != null);
		
		if (p1.equals("not promoted")) {
			assertTrue(((TurkishCheckersTestInfo) info).reachedCrownheadWithJumpMove);
			assertEquals(pieceOfPlayerMove, newPiece);
		} else if (p1.equals("promoted")) {
			assertEquals(pieceOfPlayerMove.getId()+2, newPiece.getId());
			if (pieceOfPlayerMove.getIcon().equals("W"))
				assertEquals("Z", newPiece.getIcon());
			else
				assertEquals("A", newPiece.getIcon());
			assertEquals(playerOfPlayerMove, newPiece.getPlayer());
			assertEquals(pieceOfPlayerMove.getGoalDirection(), newPiece.getGoalDirection());
			assertTrue(newPiece.getPieceMovePossibilities() instanceof TurkishKingMovePossibilities);
			assertTrue(newPiece.getPieceMoveConstraints() instanceof TurkishKingMoveConstraints);
			assertTrue(newPiece instanceof King);
			pieceOfPlayerMove = newPiece;
		} else {
			throw new PendingException();
		}
		

	}

	@Override
	public void thePlayerHasAP1PieceInOpponentsCrownhead(String p1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void thePlayerJumpsOverAllTheVulnerableOpponentKingsInTheCrownhead() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void thereAreSomeBoardStatesThatHaveBeenReachedTwoTimes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	

}

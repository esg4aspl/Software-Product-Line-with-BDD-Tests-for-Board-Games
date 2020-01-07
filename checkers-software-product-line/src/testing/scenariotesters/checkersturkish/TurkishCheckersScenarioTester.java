package testing.scenariotesters.checkersturkish;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import checkersamerican.King;
import checkersturkish.TurkishGameConfiguration;
import checkersturkish.TurkishKingMoveConstraints;
import checkersturkish.TurkishKingMovePossibilities;
import core.AbstractPiece;
import cucumber.api.PendingException;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.SourceCoordinateValidity;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;

public class TurkishCheckersScenarioTester extends AmericanCheckersScenarioTester implements ITurkishScenarioTester {

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
	public void thePlayerJumpsOverAllTheVulnerableOpponentKingsInTheCrownhead() {
		referee.conductGame();
		prepareValidities();
	}

	@Override
	public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState() {
		referee.conductGame();
		prepareValidities();
	}

	@Override
	protected boolean invalidDestinationCoordinate(String reason) {
		if (super.invalidDestinationCoordinate(reason)) {
			return true;
		}
		
		if (reason.equals("jumped piece is too far away from source coordinate")) {
			assertEquals(DestinationCoordinateValidity.MORE_THAN_TWO_SQUARES_AWAY_FROM_SOURCE, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("destination coordinate is more than one square away from jumped piece")) {
			assertEquals(DestinationCoordinateValidity.MORE_THAN_ONE_SQUARE_AWAY_FROM_JUMPED_PIECE, destinationCoordinateValidityOfPlayerMove);			
		} else if (reason.equals("there are more than one pieces in jump path")) {
			assertEquals(DestinationCoordinateValidity.MULTIPLE_JUMPED_PIECES, destinationCoordinateValidityOfPlayerMove);			
		} else if (reason.equals("move is not part of the best sequence")) {
			assertEquals(DestinationCoordinateValidity.NOT_THE_BEST_SEQUENCE, destinationCoordinateValidityOfPlayerMove);			
		} else if (reason.equals("the pawn in crownhead did not capture the vulnerable king")) {
			assertEquals(DestinationCoordinateValidity.PAWN_IN_CROWNHEAD_ILLEGAL_MOVE, destinationCoordinateValidityOfPlayerMove);
		} else {
			return false;
		}
		
		return true;
	}

	@Override
	protected boolean invalidSourceCoordinate(String reason) {
		if (super.invalidSourceCoordinate(reason)) {
			return true;
		}
		
		if (reason.equals("there is a pawn in crownhead but move is not that")) {
			assertEquals(SourceCoordinateValidity.NOT_THE_PAWN_IN_CROWNHEAD, this.sourceCoordinateValidityOfPlayerMove);
		} else {
			return false;
		}
		
		return true;
	}

	
	
	
	
	
	

}

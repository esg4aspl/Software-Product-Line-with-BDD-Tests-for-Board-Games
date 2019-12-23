package testing.scenariotesters.checkersspanish;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import base.AmericanGameConfiguration;
import base.Pawn;
import checkersspanish.Queen;
import checkersspanish.QueenMoveConstraints;
import checkersspanish.QueenMovePossibilities;
import core.AbstractPiece;
import cucumber.api.PendingException;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.SourceCoordinateValidity;
import testing.scenariotesters.IScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;

public class SpanishCheckersScenarioTester extends AmericanCheckersScenarioTester implements IScenarioTester {
	

	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new SpanishTesterReferee(new AmericanGameConfiguration());
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
		} else {
			return false;
		}
		
		return true;
	}

	@Override
	public void thePieceIsP1ToACrownedPiece(String p1) {
		if (!p1.equals("promoted"))
			throw new PendingException();
		
		AbstractPiece newPiece = getPieceAtCoordinate(destinationCoordinateOfPlayerMove);
		assertTrue(newPiece != null);
		assertEquals(pieceOfPlayerMove.getId()+2, newPiece.getId());
		if (pieceOfPlayerMove.getIcon().equals("W"))
			assertEquals("Z", newPiece.getIcon());
		else
			assertEquals("A", newPiece.getIcon());
		assertEquals(playerOfPlayerMove, newPiece.getPlayer());
		assertEquals(pieceOfPlayerMove.getGoalDirection(), newPiece.getGoalDirection());
		assertTrue(newPiece.getPieceMovePossibilities() instanceof QueenMovePossibilities);
		assertTrue(newPiece.getPieceMoveConstraints() instanceof QueenMoveConstraints);
		assertTrue(newPiece instanceof Queen);
		pieceOfPlayerMove = newPiece;
	}

	@Override
	public void theGameIsPlayedUpToACertainPointFromFileP1(String p1) {
		referee.setup(p1);
		info = (SpanishCheckersTestInfo) referee.getInfo();
		playerOfPlayerMove = referee.getCurrentPlayer();
		playerMove = referee.getInfo().getPlayerMove();
		output("Testing: " + referee.getInfo().getReader().getSectionName());
		//Set up source coordinate.
		sourceCoordinateOfPlayerMove = playerMove.getSourceCoordinate();
		
	}
	
	
	
	

	

	

}

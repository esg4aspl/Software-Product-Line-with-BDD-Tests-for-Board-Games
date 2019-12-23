package testing.scenariotesters.chess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import base.Pawn;
import chess.Bishop;
import chess.ChessGameConfiguration;
import chess.King;
import chess.Knight;
import chess.Queen;
import chess.QueenMoveConstraints;
import chess.QueenMovePossibilities;
import chess.Rook;
import core.AbstractPiece;
import core.Zone;
import cucumber.api.PendingException;
import testing.helpers.SourceCoordinateValidity;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;

public class ChessScenarioTester extends AmericanCheckersScenarioTester implements IChessScenarioTester {
	
	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new ChessTesterReferee(new ChessGameConfiguration());
	}

	@Override
	public void theOpponentPieceIsRemovedFromTheBoard() {
		assertEquals(Zone.ONSIDE, jumpedPieceOfPlayerMove.getCurrentZone());
		assertEquals(null, jumpedPieceOfPlayerMove.getCurrentCoordinate());
	}

	@Override
	public void thePieceIsP1ToACrownedPiece(String p1) {
		if (!p1.equals("promoted"))
			throw new PendingException();
		
		AbstractPiece newPiece = getPieceAtCoordinate(destinationCoordinateOfPlayerMove);
		assertTrue(newPiece != null);
		assertEquals((playerOfPlayerMove.getId()*7)+playerOfPlayerMove.getId()+6, newPiece.getId());
		assertEquals("Q"+playerOfPlayerMove.getId(), newPiece.getIcon());
		assertEquals(playerOfPlayerMove, newPiece.getPlayer());
		assertEquals(pieceOfPlayerMove.getGoalDirection(), newPiece.getGoalDirection());
		assertTrue(newPiece.getPieceMovePossibilities() instanceof QueenMovePossibilities);
		assertTrue(newPiece.getPieceMoveConstraints() instanceof QueenMoveConstraints);
		assertTrue(newPiece instanceof Queen);
		pieceOfPlayerMove = newPiece;
	}

	@Override
	public void thePlayerMakesAMoveThatThreatensTheOpponentsKingsCurrentPosition() {
		referee.conductGame();
		prepareValidities();
	}

	@Override
	public void thePlayerMovesANonpawnPieceWithoutCapturingAnOpponentPiece() {
		referee.conductGame();
		prepareValidities();
	}

	@Override
	public void theRookIsMovedToTheAdjacentCoordinateThatIsTowardsTheCenter() {
		// TODO Auto-generated method stub
	}

	@Override
	public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState() {
		referee.conductGame();
		prepareValidities();
	}

	@Override
	public void theEnPassantOpponentPieceIsRemovedFromTheBoard() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void thePlayerMakesAMoveThatNotChecksTheOpponentKingButLeavesItWithNoValidMoveToMake() {
		referee.conductGame();
		prepareValidities();
	}

	@Override
	public void theCapturedOpponentPieceIsRemovedFromTheBoard() {
		//TODO Edit for en passant
		assertEquals(pieceOfPlayerMove, getPieceAtCoordinate(jumpedCoordinateOfPlayerMove));
		assertEquals(Zone.ONSIDE, jumpedPieceOfPlayerMove.getCurrentZone());
		assertEquals(null, jumpedPieceOfPlayerMove.getCurrentCoordinate());
	}

	
	
}

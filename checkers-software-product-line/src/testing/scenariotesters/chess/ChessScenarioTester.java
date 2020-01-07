package testing.scenariotesters.chess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import base.Pawn;
import chess.ChessGameConfiguration;
import chess.LimitedPawn;
import chess.Queen;
import chess.QueenMoveConstraints;
import chess.QueenMovePossibilities;
import chess.Rook;
import core.AbstractPiece;
import core.Coordinate;
import core.Zone;
import cucumber.api.PendingException;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;

public class ChessScenarioTester extends AmericanCheckersScenarioTester implements IChessScenarioTester {
	
	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new ChessTesterReferee(new ChessGameConfiguration());
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
		int yCoordinate = playerOfPlayerMove.getId() == 0 ? 0 : 7;
		int xCoordinate = destinationCoordinateOfPlayerMove.getXCoordinate() == 2 ? 3 : 5;
		Coordinate rookCoordinate = new Coordinate(xCoordinate, yCoordinate);
		AbstractPiece pieceAtRookCoordinate = referee.getCoordinatePieceMap().getPieceAtCoordinate(rookCoordinate);
		assertTrue(pieceAtRookCoordinate instanceof Rook);
		assertEquals(playerOfPlayerMove, pieceAtRookCoordinate.getPlayer());
	}

	@Override
	public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState() {
		referee.conductGame();
		prepareValidities();
	}

	@Override
	public void thePlayerMakesAMoveThatNotChecksTheOpponentKingButLeavesItWithNoValidMoveToMake() {
		referee.conductGame();
		prepareValidities();
	}

	@Override
	public void theCapturedOpponentPieceIsRemovedFromTheBoard() {
		if ( (pieceOfPlayerMove instanceof Pawn || pieceOfPlayerMove instanceof LimitedPawn) && !destinationCoordinateOfPlayerMove.equals(jumpedCoordinateOfPlayerMove) ) {
			//En passant
			assertEquals(null, getPieceAtCoordinate(jumpedCoordinateOfPlayerMove));
			assertEquals(Zone.ONSIDE, jumpedPieceOfPlayerMove.getCurrentZone());
			assertEquals(null, jumpedPieceOfPlayerMove.getCurrentCoordinate());
		} else {
			assertEquals(pieceOfPlayerMove, getPieceAtCoordinate(jumpedCoordinateOfPlayerMove));
			assertEquals(Zone.ONSIDE, jumpedPieceOfPlayerMove.getCurrentZone());
			assertEquals(null, jumpedPieceOfPlayerMove.getCurrentCoordinate());
		}
	}

	
	
}

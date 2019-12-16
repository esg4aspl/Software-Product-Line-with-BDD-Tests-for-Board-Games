package testing.scenariotesters.chess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import base.AmericanGameConfiguration;
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
import testing.scenariotesters.checkersamerican.AmericanCheckersTestInfo;
import testing.scenariotesters.checkersamerican.AmericanTesterReferee;

public class ChessScenarioTester extends AmericanCheckersScenarioTester implements IChessScenarioTester {
	
	
	

	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new ChessTesterReferee(new ChessGameConfiguration());
	}

	


	@Override
	public void thePlayerPicksAValidSourceCoordinateThatHasAP1PieceInIt(String p1) {
		referee.conductGame();
		prepareValidities();
		assertEquals(SourceCoordinateValidity.VALID, sourceCoordinateValidityOfPlayerMove);
		switch (p1) {
		case "pawn": assertTrue(pieceOfPlayerMove instanceof Pawn); break;
		case "rook": assertTrue(pieceOfPlayerMove instanceof Rook); break;
		case "knight": assertTrue(pieceOfPlayerMove instanceof Knight); break;
		case "bishop": assertTrue(pieceOfPlayerMove instanceof Bishop); break;
		case "queen": assertTrue(pieceOfPlayerMove instanceof Queen); break;
		case "king": assertTrue(pieceOfPlayerMove instanceof King); break;
		default: throw new PendingException();
		}
	}




	@Override
	protected boolean invalidSourceCoordinate(String reason) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	protected boolean invalidDestinationCoordinate(String reason) {
		// TODO Auto-generated method stub
		return true;
	}
	
	

	@Override
	public void thePlayerPicksAValidDestinationCoordinateThatHasNoPiecesInIt() {
		// TODO Auto-generated method stub
		referee.conductGame();
		prepareValidities();
	}

	@Override
	public void thePlayerPicksAValidDestinationCoordinateThatHasACapturableOpponentPieceInIt() {
		// TODO Auto-generated method stub
		referee.conductGame();
		prepareValidities();
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
	public void theOpponentsKingHasNoValidMoveToMake() {
		// TODO Auto-generated method stub

	}

	@Override
	public void thePlayerMakesAMoveThatThreatensTheOpponentsKingsCurrentPosition() {
		// TODO Auto-generated method stub

	}

	@Override
	public void theOpponentsOnlyPlayerOnTheBoardIsHisKing() {
		// TODO Auto-generated method stub

	}

	@Override
	public void thePlayerMakesAMoveThatNotChecksTheOpponentKingButLeavesItWithNoValidMoveToMake() {
		// TODO Auto-generated method stub

	}

	@Override
	public void theNumberOfConsecutiveIndecisiveMovesIs99() {
		// TODO Auto-generated method stub

	}

	@Override
	public void thePlayerMovesANonpawnPieceWithoutCapturingAnOpponentPiece() {
		// TODO Auto-generated method stub

	}


	
	@Override
	public void theOpponentsKingCanNotBeProtectedIfItIsChecked() {
		// TODO Auto-generated method stub
		
	}

}

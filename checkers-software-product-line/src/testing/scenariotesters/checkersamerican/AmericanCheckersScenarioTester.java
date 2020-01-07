package testing.scenariotesters.checkersamerican;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import base.AmericanGameConfiguration;
import checkersamerican.King;
import checkersamerican.KingMoveConstraints;
import checkersamerican.KingMovePossibilities;
import core.AbstractPiece;
import core.ICoordinate;
import core.IMoveCoordinate;
import core.IPlayer;
import core.Zone;
import cucumber.api.PendingException;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.SourceCoordinateValidity;
import testing.helpers.TestResult;
import testing.scenariotesters.AbstractTestInfo;
import testing.scenariotesters.AbstractTesterReferee;
import testing.scenariotesters.IScenarioTester;

public class AmericanCheckersScenarioTester implements IScenarioTester {

	protected PrintWriter outputter;
	
	protected AbstractTesterReferee referee;
	protected AbstractTestInfo info;
	
	protected IPlayer playerOfPlayerMove; // The player that has the current turn.
	protected AbstractPiece pieceOfPlayerMove; // The piece that is doing the move.
	
	protected IMoveCoordinate playerMove; // The move that is the focus of the currently running test.
	protected ICoordinate sourceCoordinateOfPlayerMove; // Source coordinate of the playerMove.
	protected ICoordinate destinationCoordinateOfPlayerMove; // Destination coordinate of the playerMove.
	protected SourceCoordinateValidity sourceCoordinateValidityOfPlayerMove; // Info about the validity of sourceCoordinate.
	protected DestinationCoordinateValidity destinationCoordinateValidityOfPlayerMove; // Info about the validity of destinationCoordinate.
	
	protected ICoordinate jumpedCoordinateOfPlayerMove; // The coordinate that is being jumped over if the move is a jump move.
	protected AbstractPiece jumpedPieceOfPlayerMove; // The jumped piece if the move is a jump move.
	
	public AmericanCheckersScenarioTester() {
		try {
			outputter = new PrintWriter(new FileWriter("./src/testing/scenariotesters/checkersamerican/ScenarioTesterOutput.txt", true));
			outputter.println("\nNew Test Run - " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new AmericanTesterReferee(new AmericanGameConfiguration());
	}

	@Override
	public void thePlayersStartTheGame() {
		referee.setup();
	}

	@Override
	public void thePlayerWithTheP1ColoredPiecesIsGivenTheTurn(String p1) {
		if (p1.equals("dark"))
			assertEquals(Color.BLACK, referee.getCurrentPlayer().getColor());
		else if (p1.equals("light"))
			assertEquals(Color.WHITE, referee.getCurrentPlayer().getColor());
		else
			throw new PendingException("No such player color for this game.");
	}

	@Override
	public void theGameIsPlayedUpToACertainPointFromFileP1(String p1) {
		referee.setup(p1);
		info = referee.getInfo();
		playerOfPlayerMove = referee.getCurrentPlayer();
		playerMove = referee.getInfo().getPlayerMove();
		output("Testing: " + referee.getInfo().getReader().getSectionName());
		//Set up source coordinate.
		sourceCoordinateOfPlayerMove = playerMove.getSourceCoordinate();
//		this.pieceOfPlayerMove = getPieceAtCoordinate(sourceCoordinateOfPlayerMove);
		
	}
	
	protected void prepareValidities() {

		pieceOfPlayerMove = info.getPieceOfPlayerMove();
		if (info.getPlayerOfPlayerMove() != null)
			playerOfPlayerMove = info.getPlayerOfPlayerMove();
		sourceCoordinateValidityOfPlayerMove = referee.getInfo().getSourceCoordinateValidity();
		//Set up destination coordinate.
		destinationCoordinateOfPlayerMove = playerMove.getDestinationCoordinate();
		destinationCoordinateValidityOfPlayerMove = referee.getInfo().getDestinationCoordinateValidity();
		jumpedCoordinateOfPlayerMove = info.getJumpedCoordinate();
		jumpedPieceOfPlayerMove = info.getJumpedPiece();
		
	}

	
	@Override
	public void theNextTurnIsGivenToTheP1Player(String p1) {
		if (referee.getInfo().isGameEnded()) {
			output("Informers: " + referee.getInfo().getInformers().toString());
		}
		assertFalse(referee.getInfo().isGameEnded());
		if (p1.equals("next ingame")) {
			assertFalse(referee.getInfo().isPlayerWasGoingToMakeAnotherMove());
			assertFalse(referee.getCurrentPlayer().equals(playerOfPlayerMove));
		} else if (p1.equals("current")) {
			assertTrue(referee.getInfo().isPlayerWasGoingToMakeAnotherMove());
			assertTrue(referee.getCurrentPlayer().equals(playerOfPlayerMove));
		}
	}


	//Returns true if reason is matched.
	protected boolean invalidDestinationCoordinate(String reason) {
		if (reason.equals("destination coordinate is outside of the board")) {
			assertEquals(DestinationCoordinateValidity.OUTSIDE_OF_THE_BOARD, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("destination coordinate is not of valid square color")) {
			assertEquals(DestinationCoordinateValidity.NOT_OF_VALID_SQUARE_COLOR, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("destination coordinate is occupied")) {
			//Accept "SAME_AS_SOURCE" error as "OCCUPIED" for now.
			if (destinationCoordinateValidityOfPlayerMove != DestinationCoordinateValidity.SAME_AS_SOURCE)
				assertEquals(DestinationCoordinateValidity.OCCUPIED, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("destination coordinate's direction is not allowed")) {
			//If piece is king, then it can move in any direction. The test fails here. Game set-up (ini file) is not good.
			assertFalse(pieceOfPlayerMove instanceof King);
			assertEquals(DestinationCoordinateValidity.UNALLOWED_DIRECTION, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("destination coordinate is more than two squares away")) {
			assertEquals(DestinationCoordinateValidity.MORE_THAN_TWO_SQUARES_AWAY_FROM_SOURCE, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("move is not a jump move even though there are possible jump moves")) {
			assertEquals(DestinationCoordinateValidity.NOT_ONE_OF_POSSIBLE_JUMP_MOVES, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("jumped piece is null")) {
			assertEquals(DestinationCoordinateValidity.JUMPED_PIECE_IS_NULL, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("jumped piece is not opponent piece")) {
			assertEquals(DestinationCoordinateValidity.JUMPED_PIECE_IS_OWN, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("move direction is opposite of last jump move's direction")) {
			assertEquals(DestinationCoordinateValidity.OPPOSITE_DIRECTION_OF_LAST_JUMP_MOVE, destinationCoordinateValidityOfPlayerMove);
		} else {
			return false;
		}
		return true;
	}	
	
	protected boolean invalidSourceCoordinate(String reason) {
		if (reason.equals("source coordinate is empty")) {
			assertEquals(SourceCoordinateValidity.EMPTY, sourceCoordinateValidityOfPlayerMove);
		} else if (reason.equals("source coordinate has opponent's piece")) {
			assertEquals(SourceCoordinateValidity.OPPONENT_PIECE, sourceCoordinateValidityOfPlayerMove);
		} else if (reason.equals("source coordinate is not of valid square color")) {
			assertEquals(SourceCoordinateValidity.NOT_OF_VALID_SQUARE_COLOR, sourceCoordinateValidityOfPlayerMove);
		} else if (reason.equals("source coordinate is outside of the board")) {
			assertEquals(SourceCoordinateValidity.OUTSIDE_OF_THE_BOARD, sourceCoordinateValidityOfPlayerMove);
		} else if (reason.equals("source coordinate of move is different than last jump move’s destination")) {
			assertEquals(SourceCoordinateValidity.DIFFERENT_THAN_LAST_JUMP_MOVE_DESTINATION, sourceCoordinateValidityOfPlayerMove);
		} else {
			return false;
		}
		
		return true;
	}

	@Override
	public void anErrorMessageIsShownSayingP1(String p1) {
		assertTrue(referee.getInfo().getFinalInformers().size() > 0); //Because there is at least 1 error message and 1 message about the next move.
		assertEquals(p1, referee.getInfo().getFinalInformers().get(0)); //Error message stays in the 0th index.
	}


	@Override
	public void thePlayerJumpsOverTheLastPieceOfTheOpponent() {
		referee.conductGame();
		prepareValidities();
		assertEquals(DestinationCoordinateValidity.VALID_JUMP, this.destinationCoordinateValidityOfPlayerMove);
		assertEquals(0, findOpponentPieceCount());
	}

	@Override
	public void theOpponentLosesTheGame() {
		breakpoint("endOfTheGame1");
		assertEquals(getOtherPlayer(), referee.getInfo().getLoser());
	}

	@Override
	public void thePlayerWinsTheGame() {
		if (info.isGameEnded()) {
			output("game ended");
		} else {
			output("game not ended");
		}
		assertTrue(info.isGameEnded());
		assertFalse(info.isTestAborted());
		assertEquals(playerOfPlayerMove, referee.getInfo().getWinner());
	}

	@Override
	public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces() {
		referee.conductGame();
		prepareValidities();
	}



	@Override
	public void theGameIsEndedAsADraw() {
		assertTrue(referee.getInfo().isGameEnded());
		assertTrue(referee.getInfo().isDraw());
	}

	@Override
	public void inThePreviousTurnTheOpponentHasOfferedToEndTheGameInADraw() {
		assertTrue(referee.getInfo().isDrawOffered());
	}

	@Override
	public void thePlayerP1TheOffer(String p1) {
		referee.conductGame();
		prepareValidities();
		if (p1.equals("accepts"))
			assertTrue(referee.getInfo().isDrawAccepted());
		else if (p1.equals("rejects"))
			assertFalse(referee.getInfo().isDrawAccepted());
		else
			throw new PendingException();
	}

	@Override
	public void p1Happens(String p1) {
		if (p1.equals("the game is ended as a draw")) {
			this.theGameIsEndedAsADraw();
		} else if (p1.equals("the next turn is given to the next ingame player")) {
			this.theNextTurnIsGivenToTheP1Player("other");
		} else if (p1.equals("the next turn is given to the offerer player")) {
			//TODO This waits for an implementation in development.
			throw new PendingException();
		} else {
			throw new PendingException();
		}
	}

	@Override
	public void thePlayerJumpsOverOneOrMultiplePiecesLeavingTheOpponentWithOnlyOnePieceThatIsUnableToPerformAJumpMove() {
		referee.conductGame();
		prepareValidities();
		assertEquals(DestinationCoordinateValidity.VALID_JUMP, destinationCoordinateValidityOfPlayerMove);
		assertEquals(1, findOpponentPieceCount());
	}
	
	@Override
	public void thePieceIsMovedToTheDestinationCoordinate() {
		//Check if destination coordinate now holds the moved piece.
		assertEquals(pieceOfPlayerMove, getPieceAtCoordinate(destinationCoordinateOfPlayerMove));
		//Check if the source coordinate is now empty.
		assertTrue(getPieceAtCoordinate(sourceCoordinateOfPlayerMove) == null);
		//Check if the piece's current coordinate is the same as player move's destination coordinate.
		assertEquals(destinationCoordinateOfPlayerMove, pieceOfPlayerMove.getCurrentCoordinate());
	}

	@Override
	public void thePieceIsP1ToACrownedPiece(String p1) {
		AbstractPiece newPiece = getPieceAtCoordinate(destinationCoordinateOfPlayerMove);
		
		if (p1.equals("not promoted"))
			assertEquals(pieceOfPlayerMove, newPiece);
		
		assertTrue(newPiece != null);
		assertEquals(pieceOfPlayerMove.getId()+2, newPiece.getId());
		if (pieceOfPlayerMove.getIcon().equals("W"))
			assertEquals("Z", newPiece.getIcon());
		else
			assertEquals("A", newPiece.getIcon());
		assertEquals(playerOfPlayerMove, newPiece.getPlayer());
		assertEquals(pieceOfPlayerMove.getGoalDirection(), newPiece.getGoalDirection());
		assertTrue(newPiece.getPieceMovePossibilities() instanceof KingMovePossibilities);
		assertTrue(newPiece.getPieceMoveConstraints() instanceof KingMoveConstraints);
		assertTrue(newPiece instanceof King);
		pieceOfPlayerMove = newPiece;
	}

	protected int findOpponentPieceCount() {
		return findPieceCount(getOtherPlayer());
	}
	
	protected int findPieceCount(IPlayer player) {
		int pieceCount = 0;
		for (AbstractPiece p : player.getPieceList()) {
			if (p.getCurrentZone() == Zone.ONBOARD) {
				pieceCount++;
			}
		}
		return pieceCount;
	}
	
	protected AbstractPiece getPieceAtCoordinate(ICoordinate coordinate) {
		return referee.getCoordinatePieceMap().getPieceAtCoordinate(coordinate);
	}
	
	protected IPlayer getOtherPlayer() {
		int currentPlayerId = this.playerOfPlayerMove.getId();
		int otherPlayerId = currentPlayerId+1;
		if (otherPlayerId >= referee.getNumberOfPlayers()) 
			otherPlayerId = 0;
		return referee.getPlayerbyID(otherPlayerId);
	}

	protected void output(String str) {
		outputter.println(str);
		outputter.flush();
	}
	
	protected void breakpoint(String sectionName) {
		if (referee.getInfo().getReader().getSectionName().equals(sectionName))
			//Have a breakpoint at the following line.
			System.out.println("Breakpoint at " + sectionName);
		
		if (sectionName.equals("any"))
			System.out.println("Mandatory breakpoint");
	}

	@Override
	public void thePieceIsNotMoved() {
		assertEquals(pieceOfPlayerMove, referee.getCoordinatePieceMap().getPieceAtCoordinate(sourceCoordinateOfPlayerMove));
	}
	
	@Override
	public void thePlayerOffersToEndTheGameInDraw() {
		referee.conductGame();
		prepareValidities();
		assertTrue(referee.getInfo().isDrawOffered());
	}

	@Override
	public void thePlayerMakesAP1Move(String p1) {
		referee.conductGame();
		prepareValidities();
	}

	@Override
	public void theCapturedOpponentPieceIsRemovedFromTheBoard() {
		assertEquals(null, getPieceAtCoordinate(jumpedCoordinateOfPlayerMove));
		assertEquals(Zone.ONSIDE, jumpedPieceOfPlayerMove.getCurrentZone());
		assertEquals(null, jumpedPieceOfPlayerMove.getCurrentCoordinate());
	}

	@Override
	public void thePlayerIsAskedForAnotherMove() {
		assertTrue(referee.getInfo().isPlayerWasGoingToMakeAnotherMove());
		assertTrue(referee.getInfo().isPlayerWasGoingToMakeAnotherMove());
		//Following line causes error in invalid source - opponent piece scenarios.
//		assertTrue(referee.getCurrentPlayer().equals(playerOfPlayerMove));
		assertEquals(TestResult.ANOTHER_SOURCE_INVALID.getMessage(), referee.getInfo().getFinalInformers().get(1));
	}

	@Override
	public void thePlayerMakesAMoveToOpponentsCrownheadWithAPawn() {
		thePlayerMakesAP1Move("");
	}

	


	

	

	



	
}

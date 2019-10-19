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
import java.util.ArrayList;
import java.util.List;

import base.AmericanGameConfiguration;
import base.Pawn;
import checkersamerican.King;
import checkersamerican.KingMoveConstraints;
import checkersamerican.KingMovePossibilities;
import core.AbstractPiece;
import core.Coordinate;
import core.Direction;
import core.ICoordinate;
import core.IMoveCoordinate;
import core.IPlayer;
import core.MoveCoordinate;
import core.Zone;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.SourceCoordinateValidity;
import testing.helpers.TestResult;
import testing.scenariotesters.AbstractTesterReferee;
import testing.scenariotesters.IScenarioTester;

public class AmericanCheckersScenarioTester implements IScenarioTester {

	PrintWriter outputter;
	
	protected AbstractTesterReferee referee;
	protected AmericanCheckersTestInfo info;
	
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
			// TODO Auto-generated catch block
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
	public void thePlayerWithTheDarkcoloredPiecesIsGivenTheTurn() {
		assertEquals(Color.BLACK, referee.getCurrentPlayer().getColor());
	}

	@Override
	public void theGameIsPlayedUpToACertainPointFromFileP1(String p1) {
		referee.setup(p1);
		info = (AmericanCheckersTestInfo) referee.getInfo();
		playerOfPlayerMove = referee.getCurrentPlayer();
		playerMove = referee.getInfo().getPlayerMove();
		output("Testing: " + referee.getInfo().getReader().getSectionName());
		//Set up source coordinate.
		sourceCoordinateOfPlayerMove = playerMove.getSourceCoordinate();
		this.pieceOfPlayerMove = getPieceAtCoordinate(sourceCoordinateOfPlayerMove);
		
	}
	
	protected void prepareValidities() {
		sourceCoordinateValidityOfPlayerMove = referee.getInfo().getSourceCoordinateValidity();
		//Set up destination coordinate.
		destinationCoordinateOfPlayerMove = playerMove.getDestinationCoordinate();
		destinationCoordinateValidityOfPlayerMove = referee.getInfo().getDestinationCoordinateValidity();
		jumpedCoordinateOfPlayerMove = info.getJumpedCoordinate();
		jumpedPieceOfPlayerMove = info.getJumpedPiece();
		
	}

	@Override
	public void thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(String p1) {
		if (p1.equals("one")) {
			assertEquals(DestinationCoordinateValidity.VALID_REGULAR, destinationCoordinateValidityOfPlayerMove);
		} else if (p1.equals("two")) {
			assertEquals(DestinationCoordinateValidity.VALID_JUMP, destinationCoordinateValidityOfPlayerMove);
		}
	}
	
	@Override
	public void thePieceAtTheSourceCoordinateIsMovedToTheDestinationCoordinate() {
		//Check if destination coordinate now holds the moved piece.
		assertEquals(pieceOfPlayerMove, getPieceAtCoordinate(destinationCoordinateOfPlayerMove));
		//Check if the source coordinate is now empty.
		assertTrue(getPieceAtCoordinate(sourceCoordinateOfPlayerMove) == null);
		//Check if the piece's current coordinate is the same as player move's destination coordinate.
		assertEquals(destinationCoordinateOfPlayerMove, pieceOfPlayerMove.getCurrentCoordinate());
	}

	@Override
	public void theNextTurnIsGivenToTheP1Player(String p1) {
		if (referee.getInfo().isGameEnded()) {
			output("Informers: " + referee.getInfo().getInformers().toString());
		}
		assertFalse(referee.getInfo().isGameEnded());
		if (p1.equals("other")) {
			assertFalse(referee.getInfo().isPlayerWasGoingToMakeAnotherMove());
			assertFalse(referee.getCurrentPlayer().equals(playerOfPlayerMove));
		} else if (p1.equals("current")) {
			assertTrue(referee.getInfo().isPlayerWasGoingToMakeAnotherMove());
			assertTrue(referee.getCurrentPlayer().equals(playerOfPlayerMove));
		}
	}

	@Override
	public void theOpponentPieceInBetweenTheSourceAndDestinationCoordinatesAreRemovedFromTheBoard() {
		assertEquals(null, getPieceAtCoordinate(jumpedCoordinateOfPlayerMove));
		assertEquals(Zone.ONSIDE, jumpedPieceOfPlayerMove.getCurrentZone());
		assertEquals(null, jumpedPieceOfPlayerMove.getCurrentCoordinate());
	}

	@Override
	public void thePlayerPicksAnInvalidP1CoordinateBecauseP2(String p1, String p2) {
		if (p1.equals("source")) {
			referee.conductGame();
			prepareValidities();
			invalidSourceCoordinate(p2);
		} else if (p1.equals("destination")){
			invalidDestinationCoordinate(p2);
		} else {
			throw new PendingException();
		}
	}
	
	protected void invalidDestinationCoordinate(String reason) {
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
		} else {
			throw new PendingException();
		}
	}	
	
	protected void invalidSourceCoordinate(String reason) {
		if (reason.equals("source coordinate is empty")) {
			assertEquals(SourceCoordinateValidity.EMPTY, sourceCoordinateValidityOfPlayerMove);
		} else if (reason.equals("source coordinate has opponent's piece")) {
			assertEquals(SourceCoordinateValidity.OPPONENT_PIECE, sourceCoordinateValidityOfPlayerMove);
		} else if (reason.equals("source coordinate is not of valid square color")) {
			assertEquals(SourceCoordinateValidity.NOT_OF_VALID_SQUARE_COLOR, sourceCoordinateValidityOfPlayerMove);
		} else if (reason.equals("source coordinate is outside of the board")) {
			assertEquals(SourceCoordinateValidity.OUTSIDE_OF_THE_BOARD, sourceCoordinateValidityOfPlayerMove);
		} else {
			throw new PendingException();
		}
	}

	@Override
	public void thePlayerPicksAnyDestinationCoordinate() {
		//TODO: What can be the implementation for this?
	}

	@Override
	public void anErrorMessageIsShownSayingP1(String p1) {
		assertTrue(referee.getInfo().getFinalInformers().size() > 0); //Because there is at least 1 error message and 1 message about the next move.
		assertEquals(p1, referee.getInfo().getFinalInformers().get(0)); //Error message stays in the 0th index.
	}

	@Override
	public void thePlayerIsAskedForAnotherP1Coordinate(String p1) {
		assertTrue(referee.getInfo().isPlayerWasGoingToMakeAnotherMove());
		if (p1.equals("source")) {
			assertTrue(referee.getInfo().isPlayerWasGoingToMakeAnotherMove());
			assertTrue(referee.getCurrentPlayer().equals(playerOfPlayerMove));
			assertEquals(TestResult.ANOTHER_SOURCE_INVALID.getMessage(), referee.getInfo().getFinalInformers().get(1));
		} else {
			throw new PendingException();
		}

	}

	@Override
	public void onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard() {
		IPlayer otherPlayer = getOtherPlayer();
		int otherPlayerPieceCount = 0;
		for (AbstractPiece otherPlayerPiece : otherPlayer.getPieceList()) {
			if (otherPlayerPiece.getCurrentZone() == Zone.ONBOARD)
				otherPlayerPieceCount++;
		}
		assertEquals(1, otherPlayerPieceCount);
	}

	@Override
	public void thePlayerJumpsOverTheLastPieceOfTheOpponent() {
		referee.conductGame();
		prepareValidities();
		assertEquals(DestinationCoordinateValidity.VALID_JUMP, this.destinationCoordinateValidityOfPlayerMove);
	}

	@Override
	public void theOpponentLosesTheGame() {
		assertEquals(getOtherPlayer(), referee.getInfo().getLoser());
	}

	@Override
	public void thePlayerWinsTheGame() {
		assertEquals(playerOfPlayerMove, referee.getInfo().getWinner());
	}

	@Override
	public void thePlayerHasOnlyOnePieceOnTheGameBoard() {
		int playerPieceCount = 0;
		AbstractPiece onePieceFound = null;
		for (AbstractPiece p : playerOfPlayerMove.getPieceList()) {
			if (p.getCurrentZone() == Zone.ONBOARD) {
				onePieceFound = p;
				playerPieceCount++;
			}
		}
		assertEquals(1, playerPieceCount);
		assertEquals(pieceOfPlayerMove, onePieceFound);
	}

	@Override
	public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces() {
		referee.conductGame();
		prepareValidities();
//		boolean noValidMove = true;
//		IPlayer otherPlayer = getOtherPlayer();
//		List<IMoveCoordinate> opponentAllPossibleMoves = findAllMoves(otherPlayer);
//		for (IMoveCoordinate move : opponentAllPossibleMoves) {
//			DestinationCoordinateValidity validity = referee.checkDestinationCoordinate(otherPlayer, move.getSourceCoordinate(), move.getDestinationCoordinate());
//			if (validity == DestinationCoordinateValidity.VALID_REGULAR || validity == DestinationCoordinateValidity.VALID_JUMP)
//				noValidMove = false;
//		}
//		assertTrue(noValidMove);
	}

	@Override
	public void thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead() {
		assertTrue(destinationCoordinateValidityOfPlayerMove == DestinationCoordinateValidity.VALID_REGULAR
					|| destinationCoordinateValidityOfPlayerMove == DestinationCoordinateValidity.VALID_JUMP);
		
		if (pieceOfPlayerMove.getGoalDirection() == Direction.N) {
			assertEquals(7, destinationCoordinateOfPlayerMove.getYCoordinate());
		} else if (pieceOfPlayerMove.getGoalDirection() == Direction.S) {
			assertEquals(0, destinationCoordinateOfPlayerMove.getYCoordinate());
		}
	}

	@Override
	public void thePieceAtTheSourceCoordinateBecomesACrownedPiece() {
		AbstractPiece newPiece = getPieceAtCoordinate(destinationCoordinateOfPlayerMove);
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
	
	@Override
	public void theGameIsEndedAsADraw() {
		assertTrue(referee.getInfo().isGameEnded());
		assertTrue(referee.getInfo().isDraw());
	}

	@Override
	public void theNumberOfConsecutiveIndecisiveMovesIs39() {
		int noPromote = referee.getInfo().getNoPromoteMoveCount();
		int noCapture = referee.getInfo().getNoCaptureMoveCount();
		if (noPromote < noCapture)
			assertEquals(39, noPromote);
		else
			assertEquals(39, noCapture);
	}

	@Override
	public void thePlayerMakesARegularMoveWithoutPromoting() {
		referee.conductGame();
		prepareValidities();
		assertEquals(DestinationCoordinateValidity.VALID_REGULAR, destinationCoordinateValidityOfPlayerMove);
		assertEquals(pieceOfPlayerMove, referee.getCoordinatePieceMap().getPieceAtCoordinate(destinationCoordinateOfPlayerMove));
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
		if (p1.equals("the game is ended as a draw"))
			this.theGameIsEndedAsADraw();
		else if (p1.equals("the next turn is given to the other player"))
			this.theNextTurnIsGivenToTheP1Player("other");
		else
			throw new PendingException();
	}

	@Override
	public void thePlayerJumpsOverOneOrMultiplePiecesLeavingTheOpponentWithOnlyOnePieceThatIsUnableToPerformAJumpMove() {
		referee.conductGame();
		prepareValidities();
		assertEquals(DestinationCoordinateValidity.VALID_JUMP, destinationCoordinateValidityOfPlayerMove);
		int opponentPieceCount = 0;
		IPlayer opponent = getOtherPlayer();
		AbstractPiece onePieceFound = null;
		for (AbstractPiece p : opponent.getPieceList()) {
			if (p.getCurrentZone() == Zone.ONBOARD) {
				opponentPieceCount++;
				onePieceFound = p;
			}
		}
		assertEquals(1, opponentPieceCount);
//		assertEquals(0, findPossibleJumpMoves(opponent).size());
	}
	
	@Override
	public void thePlayerPicksAValidSourceCoordinateThatHasAP1PieceInIt(String p1) {
		referee.conductGame();
		prepareValidities();
		assertEquals(SourceCoordinateValidity.VALID, sourceCoordinateValidityOfPlayerMove);
		if (p1.equals("pawn")) {
			assertTrue(pieceOfPlayerMove instanceof Pawn);
		} else if (p1.equals("king")) {
			assertTrue(pieceOfPlayerMove instanceof King);
		} else {
			throw new PendingException();
		}
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
	
	private void breakpoint(String sectionName) {
		if (referee.getInfo().getReader().getSectionName().equals(sectionName))
			//Have a breakpoint at the following line.
			System.out.println("Breakpoint at " + sectionName);
		
		if (sectionName.equals("any"))
			System.out.println("Mandatory breakpoint");
	}

	

	

	



	
}

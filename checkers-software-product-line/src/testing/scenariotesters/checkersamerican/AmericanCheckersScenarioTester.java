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
import testing.scenariotesters.AbstractTesterReferee;
import testing.scenariotesters.IScenarioTester;

public class AmericanCheckersScenarioTester implements IScenarioTester {

	PrintWriter outputter;
	
	protected AbstractTesterReferee referee;
	protected IPlayer playerOfPlayerMove; // The player that has the current turn.
	protected AbstractPiece pieceOfPlayerMove; // The piece that is doing the move.
	
	protected IMoveCoordinate playerMove; // The move that is the focus of the currently running test.
	protected ICoordinate sourceCoordinateOfPlayerMove; // Source coordinate of the playerMove.
	protected ICoordinate destinationCoordinateOfPlayerMove; // Destination coordinate of the playerMove.
	protected int sourceXOfPlayerMove, sourceYOfPlayerMove, destinationXOfPlayerMove, destinationYOfPlayerMove; // Explicit coordinates of playerMove.
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
		referee.defaultSetup();
	}

	@Override
	public void thePlayerWithTheDarkcoloredPiecesIsGivenTheTurn() {
		assertEquals(Color.BLACK, referee.getCurrentPlayer().getColor());
	}

	@Override
	public void theGameIsPlayedUpToACertainPointFromFileP1(String p1) {
		referee.setSetUpName(p1);
		referee.setup();
		playerOfPlayerMove = referee.getCurrentPlayer();
		playerMove = referee.readPlayerMove();
		output("Testing: " + referee.getReader().getSectionName());
//		Set up source coordinate.
		sourceCoordinateOfPlayerMove = playerMove.getSourceCoordinate();
		sourceXOfPlayerMove = sourceCoordinateOfPlayerMove.getXCoordinate(); sourceYOfPlayerMove = sourceCoordinateOfPlayerMove.getYCoordinate();
		this.pieceOfPlayerMove = getPieceAtCoordinate(sourceCoordinateOfPlayerMove);
		sourceCoordinateValidityOfPlayerMove = checkSourceCoordinate(playerOfPlayerMove, sourceCoordinateOfPlayerMove);
		//Set up destination coordinate.
		destinationCoordinateOfPlayerMove = playerMove.getDestinationCoordinate();
		destinationXOfPlayerMove = destinationCoordinateOfPlayerMove.getXCoordinate(); destinationYOfPlayerMove = this.destinationCoordinateOfPlayerMove.getYCoordinate();
		destinationCoordinateValidityOfPlayerMove = checkDestinationCoordinate(playerOfPlayerMove, sourceCoordinateOfPlayerMove, destinationCoordinateOfPlayerMove);
		if (destinationCoordinateValidityOfPlayerMove == DestinationCoordinateValidity.JUMPED_PIECE_IS_NULL
				|| destinationCoordinateValidityOfPlayerMove == DestinationCoordinateValidity.JUMPED_PIECE_IS_OWN
				|| destinationCoordinateValidityOfPlayerMove == DestinationCoordinateValidity.VALID_JUMP) {
			jumpedCoordinateOfPlayerMove = new Coordinate(sourceXOfPlayerMove + (destinationXOfPlayerMove-sourceXOfPlayerMove)/2, sourceYOfPlayerMove + (destinationYOfPlayerMove - sourceYOfPlayerMove)/2);
			jumpedPieceOfPlayerMove = getPieceAtCoordinate(jumpedCoordinateOfPlayerMove);
		}
		
		output("SourceCoordinateValidity: " + this.sourceCoordinateValidityOfPlayerMove);
		output("DestinationCoordinateValidity: " + this.destinationCoordinateValidityOfPlayerMove);
	}

	@Override
	public void thePlayerPicksAValidSourceCoordinate() {
		assertEquals(SourceCoordinateValidity.VALID, sourceCoordinateValidityOfPlayerMove);
	}

	@Override
	public void thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(String p1) {
		if (p1.equals("one")) {
			assertEquals(DestinationCoordinateValidity.VALID_REGULAR, destinationCoordinateValidityOfPlayerMove);
		} else if (p1.equals("two")) {
			assertEquals(DestinationCoordinateValidity.VALID_JUMP, destinationCoordinateValidityOfPlayerMove);
		}
		//If there were no errors up to this point, conduct the game.
		referee.conductGame();
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
		if (referee.isGameEnded()) {
			output("Informers: " + referee.getInformers().toString());
		}
		assertFalse(referee.isGameEnded());
		if (p1.equals("other")) {
			assertFalse(referee.isPlayerWasGoingToMakeAnotherMove());
			assertFalse(referee.getCurrentPlayer().equals(playerOfPlayerMove));
		} else if (p1.equals("current")) {
			assertTrue(referee.isPlayerWasGoingToMakeAnotherMove());
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
		playerMove = referee.readPlayerMove();
		if (p1.equals("source")) {
			invalidSourceCoordinate(p2);
		} else if (p1.equals("destination")){
			invalidDestinationCoordinate(p2);
		} else {
			throw new PendingException();
		}
	}
	
	private void invalidDestinationCoordinate(String reason) {
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
			assertEquals(DestinationCoordinateValidity.MORE_THAN_TWO_SQUARES_AWAY, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("move is not a jump move even though there are possible jump moves")) {
			assertEquals(DestinationCoordinateValidity.NOT_ONE_OF_POSSIBLE_JUMP_MOVES, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("jumped piece is null")) {
			assertEquals(DestinationCoordinateValidity.JUMPED_PIECE_IS_NULL, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("jumped piece is not opponent piece")) {
			assertEquals(DestinationCoordinateValidity.JUMPED_PIECE_IS_OWN, destinationCoordinateValidityOfPlayerMove);
		} else {
			throw new PendingException();
		}
		//If there were no errors up to this point, conduct the game.
		referee.conductGame();
	}	
	
	private void invalidSourceCoordinate(String reason) {
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
		referee.conductGame();
	}

	@Override
	public void anErrorMessageIsShownSayingP1(String p1) {
		assertTrue(referee.getInformers().size() > 0); //Because there is at least 1 error message and 1 message about the next move.
		assertEquals(p1, referee.getInformers().get(0)); //Error message stays in the 0th index.
	}

	@Override
	public void thePlayerIsAskedForAnotherP1Coordinate(String p1) {
		assertTrue(referee.isPlayerWasGoingToMakeAnotherMove());
		if (p1.equals("source")) {
			assertEquals("Player will be asked for another source coordinate (previous move was invalid)...", referee.getInformers().get(1));
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
		assertEquals(DestinationCoordinateValidity.VALID_JUMP, this.destinationCoordinateValidityOfPlayerMove);
	}

	@Override
	public void theOpponentLosesTheGame() {
		assertEquals(getOtherPlayer(), referee.getLoser());
	}

	@Override
	public void thePlayerWinsTheGame() {
		assertEquals(playerOfPlayerMove, referee.getWinner());
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
		boolean noValidMove = true;
		IPlayer otherPlayer = getOtherPlayer();
		List<IMoveCoordinate> opponentAllPossibleMoves = findAllMoves(otherPlayer);
		for (IMoveCoordinate move : opponentAllPossibleMoves) {
			DestinationCoordinateValidity validity = checkDestinationCoordinate(otherPlayer, move.getSourceCoordinate(), move.getDestinationCoordinate());
			if (validity == DestinationCoordinateValidity.VALID_REGULAR || validity == DestinationCoordinateValidity.VALID_JUMP)
				noValidMove = false;
		}
		assertTrue(noValidMove);
	}

	@Override
	public void thePlayerPicksAValidSourceCoordinateThatHasAPawnPieceInIt() {
		assertEquals(SourceCoordinateValidity.VALID, sourceCoordinateValidityOfPlayerMove);
		assertTrue(pieceOfPlayerMove instanceof Pawn);
	}

	@Override
	public void thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead() {
		assertTrue(destinationCoordinateValidityOfPlayerMove == DestinationCoordinateValidity.VALID_REGULAR
					|| destinationCoordinateValidityOfPlayerMove == DestinationCoordinateValidity.VALID_JUMP);
		
		if (pieceOfPlayerMove.getGoalDirection() == Direction.N) {
			assertEquals(7, destinationYOfPlayerMove);
		} else if (pieceOfPlayerMove.getGoalDirection() == Direction.S) {
			assertEquals(0, destinationYOfPlayerMove);
		}
		
		referee.conductGame();
	}

	@Override
	public void thePieceAtTheSourceCoordinateBecomesAKingPiece() {
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
		assertTrue(referee.isGameEnded());
		assertTrue(referee.isDraw());
	}

	@Override
	public void theNumberOfConsecutiveIndecisiveMovesIs39() {
		int noPromote = referee.getNoPromoteMoveCount();
		int noCapture = referee.getNoCaptureMoveCount();
		if (noPromote < noCapture)
			assertEquals(39, noPromote);
		else
			assertEquals(39, noCapture);
	}

	@Override
	public void thePlayerMakesARegularMoveWithoutPromoting() {
		breakpoint("endOfTheGameInDrawFortyIndecisiveMoves2");
		assertEquals(DestinationCoordinateValidity.VALID_REGULAR, destinationCoordinateValidityOfPlayerMove);
		referee.conductGame();
		assertEquals(pieceOfPlayerMove, referee.getCoordinatePieceMap().getPieceAtCoordinate(destinationCoordinateOfPlayerMove));
	}
	
	@Override
	public void inThePreviousTurnTheOpponentHasOfferedToEndTheGameInADraw() {
		assertTrue(referee.isDrawOffered());
	}

	@Override
	public void thePlayerP1TheOffer(String p1) {
		referee.conductGame();
		if (p1.equals("accepts"))
			assertTrue(referee.isDrawAccepted());
		else if (p1.equals("rejects"))
			assertFalse(referee.isDrawAccepted());
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
		assertEquals(0, findPossibleJumpMoves(opponent).size());
	}
	
	
	

	//PRIVATE/HELPER METHODS AND CLASSES
	private List<IMoveCoordinate> findPossibleJumpMoves(IPlayer player) {
		List<IMoveCoordinate> possibleJumpMoves = new ArrayList<IMoveCoordinate>();
		for (AbstractPiece anyPlayerPiece : player.getPieceList()) {
			List<ICoordinate> relativeCoordsWithOpponentPiece = new ArrayList<ICoordinate>();
			List<ICoordinate> relativeCoords = new ArrayList<ICoordinate>();
			if (anyPlayerPiece instanceof King) {
				relativeCoords.add(new Coordinate(-1,1)); relativeCoords.add(new Coordinate(1,1));
				relativeCoords.add(new Coordinate(-1,-1)); relativeCoords.add(new Coordinate(1,-1));
			} else if (anyPlayerPiece.getGoalDirection() == Direction.N) {
				relativeCoords.add(new Coordinate(-1,1)); relativeCoords.add(new Coordinate(1,1));
			} else {
				relativeCoords.add(new Coordinate(-1,-1)); relativeCoords.add(new Coordinate(1,-1));
			}
			for (ICoordinate relativeCoord : relativeCoords) {
				AbstractPiece adjacentPiece = getPieceAtCoordinate(new Coordinate(anyPlayerPiece.getCurrentCoordinate().getXCoordinate() + relativeCoord.getXCoordinate(), anyPlayerPiece.getCurrentCoordinate().getYCoordinate() + relativeCoord.getYCoordinate()));
				if (adjacentPiece != null && !adjacentPiece.getPlayer().equals(player))
					relativeCoordsWithOpponentPiece.add(relativeCoord);
			}
			for (ICoordinate relativeCoordWithOpponentPiece : relativeCoordsWithOpponentPiece) {
				ICoordinate possibleJumpMoveDestinationCoordinate = new Coordinate(anyPlayerPiece.getCurrentCoordinate().getXCoordinate() + relativeCoordWithOpponentPiece.getXCoordinate()*2, anyPlayerPiece.getCurrentCoordinate().getYCoordinate() + relativeCoordWithOpponentPiece.getYCoordinate()*2);
				if (possibleJumpMoveDestinationCoordinate.getXCoordinate() >= 0
						&& possibleJumpMoveDestinationCoordinate.getXCoordinate() <= 7
						&& possibleJumpMoveDestinationCoordinate.getYCoordinate() >= 0
						&& possibleJumpMoveDestinationCoordinate.getYCoordinate() <= 7
						&& getPieceAtCoordinate(possibleJumpMoveDestinationCoordinate) == null)
					possibleJumpMoves.add(new MoveCoordinate(anyPlayerPiece.getCurrentCoordinate(), possibleJumpMoveDestinationCoordinate));
			}
		}
		return possibleJumpMoves;
	}

	private List<IMoveCoordinate> findAllMoves(IPlayer player) {
		List<IMoveCoordinate> allPossibleMoves = new ArrayList<IMoveCoordinate>();
		for (AbstractPiece anyPlayerPiece : player.getPieceList()) {
			List<ICoordinate> relativeCoords = new ArrayList<ICoordinate>();
			ICoordinate anyPlayerPieceCoordinate = anyPlayerPiece.getCurrentCoordinate();
			if (anyPlayerPiece instanceof King) {
				relativeCoords.add(new Coordinate(-1,1)); relativeCoords.add(new Coordinate(1,1));
				relativeCoords.add(new Coordinate(-1,-1)); relativeCoords.add(new Coordinate(1,-1));
			} else if (anyPlayerPiece.getGoalDirection() == Direction.N) {
				relativeCoords.add(new Coordinate(-1,1)); relativeCoords.add(new Coordinate(1,1));
			} else {
				relativeCoords.add(new Coordinate(-1,-1)); relativeCoords.add(new Coordinate(1,-1));
			}
			for (ICoordinate relativeCoord : relativeCoords) {
				ICoordinate adjacentSquareCoordinate = new Coordinate(anyPlayerPieceCoordinate.getXCoordinate() + relativeCoord.getXCoordinate(), anyPlayerPieceCoordinate.getYCoordinate() + relativeCoord.getYCoordinate());
				AbstractPiece adjacentPiece = getPieceAtCoordinate(adjacentSquareCoordinate);
				if (adjacentPiece == null)
					allPossibleMoves.add(new MoveCoordinate(anyPlayerPieceCoordinate, adjacentSquareCoordinate));
			}
		}
		List<IMoveCoordinate> possibleJumpMoves = findPossibleJumpMoves(player);
		allPossibleMoves.addAll(possibleJumpMoves);
		return allPossibleMoves;
	}
	
	private boolean isMoveOneOfPossibleJumpMoves() {
		List<IMoveCoordinate> possibleJumpMoves = findPossibleJumpMoves(playerOfPlayerMove);
		if (possibleJumpMoves.size() == 0)
			return true;
		for (IMoveCoordinate move : possibleJumpMoves) {
			if (move.equals(playerMove))
				return true;
		}
		return false;
	}
	
	protected AbstractPiece getPieceAtCoordinate(ICoordinate coordinate) {
		return referee.getCoordinatePieceMap().getPieceAtCoordinate(coordinate);
	}
	
	
	protected enum SourceCoordinateValidity {
		VALID, OUTSIDE_OF_THE_BOARD, NOT_OF_VALID_SQUARE_COLOR, EMPTY, OPPONENT_PIECE
	}
	protected SourceCoordinateValidity checkSourceCoordinate(IPlayer player, ICoordinate sourceCoordinate) {
		//Set-up the source coordinate and piece.
		int xOfSource = sourceCoordinate.getXCoordinate(); int yOfSource = sourceCoordinate.getYCoordinate();
		AbstractPiece piece = getPieceAtCoordinate(sourceCoordinate);
		//Check if the coordinate is on board.
		if (xOfSource < 0 ||  7 < xOfSource || yOfSource < 0 || 7 < yOfSource)
			return SourceCoordinateValidity.OUTSIDE_OF_THE_BOARD;
		//Check if the coordinate is of valid square color.
		if (!referee.getBoard().isPlayableCoordinate(sourceCoordinate))
			return SourceCoordinateValidity.NOT_OF_VALID_SQUARE_COLOR;
		//Check if coordinate is empty
		if (piece == null)
			return SourceCoordinateValidity.EMPTY;
		//Check if coordinate has an opponent's piece
		if (!piece.getPlayer().equals(player))
			return SourceCoordinateValidity.OPPONENT_PIECE;
		
		return SourceCoordinateValidity.VALID;
	}
	
	
	protected enum DestinationCoordinateValidity {
		//Destination coordinate is either valid, or its invalidity reason are one of the below
		VALID_REGULAR, VALID_JUMP, OUTSIDE_OF_THE_BOARD, SAME_AS_SOURCE, NOT_OF_VALID_SQUARE_COLOR, OCCUPIED, UNALLOWED_DIRECTION,
		MORE_THAN_TWO_SQUARES_AWAY, NOT_ONE_OF_POSSIBLE_JUMP_MOVES, JUMPED_PIECE_IS_NULL, JUMPED_PIECE_IS_OWN,
		SOURCE_COORDINATE_PROBLEM, UNKNOWN_ERROR
	}
	protected DestinationCoordinateValidity checkDestinationCoordinate(IPlayer player, ICoordinate sourceCoordinate, ICoordinate destinationCoordinate) {
		SourceCoordinateValidity sourceCoordinateValidity = checkSourceCoordinate(player, sourceCoordinate);
		//If source coordinate is not set up or valid, destination coordinate can't be truly valid.
		if (sourceCoordinateValidity == null || sourceCoordinateValidity != SourceCoordinateValidity.VALID)
			return DestinationCoordinateValidity.SOURCE_COORDINATE_PROBLEM;
		//Set up the destination coordinate.
		int xOfSource = sourceCoordinate.getXCoordinate(); int yOfSource = sourceCoordinate.getYCoordinate();
		AbstractPiece piece = getPieceAtCoordinate(sourceCoordinate);
		int xOfDestination = destinationCoordinate.getXCoordinate(); int yOfDestination = destinationCoordinate.getYCoordinate();
		int xDiff = xOfDestination - xOfSource; int yDiff = yOfDestination - yOfSource;
		//Check if the coordinate is on board.
		if (xOfDestination < 0 ||  7 < xOfDestination || yOfDestination < 0 || 7 < yOfDestination)
			return DestinationCoordinateValidity.OUTSIDE_OF_THE_BOARD;
		//Check if coordinate is the same as source.
		if (destinationCoordinate.equals(sourceCoordinate))
			return DestinationCoordinateValidity.SAME_AS_SOURCE;
		//Check if the coordinate is of valid square color.
		if (!referee.getBoard().isPlayableCoordinate(destinationCoordinate) || Math.abs(xDiff) != Math.abs(yDiff))
			return DestinationCoordinateValidity.NOT_OF_VALID_SQUARE_COLOR;
		//Check if destination coordinate is more than two squares away.
		if (Math.abs(xDiff) > 2 || Math.abs(yDiff) > 2)
			return DestinationCoordinateValidity.MORE_THAN_TWO_SQUARES_AWAY;
		//Check if destination coordinate is occupied.
		if (getPieceAtCoordinate(destinationCoordinate) != null)
			return DestinationCoordinateValidity.OCCUPIED;
		//Check if destination coordinate is not allowed.
		Direction moveDirection = yOfDestination > yOfSource ? Direction.N : Direction.S ;
		if (piece instanceof Pawn && moveDirection != piece.getGoalDirection())
			return DestinationCoordinateValidity.UNALLOWED_DIRECTION;
		//Check if move is not one of possible jump moves.
		if (!isMoveOneOfPossibleJumpMoves())
			return DestinationCoordinateValidity.NOT_ONE_OF_POSSIBLE_JUMP_MOVES;
		//If there are no problems up to this point, return valid if move is a regular move.
		if (Math.abs(xDiff) == 1 && Math.abs(yDiff) == 1)
			return DestinationCoordinateValidity.VALID_REGULAR;
		//Set up jumped piece.
		ICoordinate jumpedCoordinate = new Coordinate(xOfSource + xDiff/2, yOfSource + yDiff/2);
		AbstractPiece jumpedPiece = getPieceAtCoordinate(jumpedCoordinate);
		//Check if jumped piece is null.
		if (jumpedPiece == null)
			return DestinationCoordinateValidity.JUMPED_PIECE_IS_NULL;
		//Check if jumped piece is player's own piece.
		if (jumpedPiece.getPlayer().equals(player))
			return DestinationCoordinateValidity.JUMPED_PIECE_IS_OWN;
		//If everything is okay up to this point, double-check that the xDiff and yDiff are 2 and return valid jump move.
		if (Math.abs(xDiff) == 2 && Math.abs(yDiff) == 2) {
			return DestinationCoordinateValidity.VALID_JUMP;
		}
		
		return DestinationCoordinateValidity.UNKNOWN_ERROR;
	}

	private IPlayer getOtherPlayer() {
		int currentPlayerId = this.playerOfPlayerMove.getId();
		int otherPlayerId = currentPlayerId+1;
		if (otherPlayerId >= referee.getNumberOfPlayers()) 
			otherPlayerId = 0;
		return referee.getPlayerbyID(otherPlayerId);
	}
	
	private void output(String title, List<?> list) {
		output(title);
		for (Object obj : list)
			output(obj.toString());
	}
	
	private void output(String str) {
		outputter.println(str);
		outputter.flush();
	}
	
	private void output() {
		outputter.println();
		outputter.flush();
	}
	
	private void breakpoint(String sectionName) {
		if (referee.getReader().getSectionName().equals(sectionName))
			//Have a breakpoint at the following line.
			System.out.println("Breakpoint at " + sectionName);
		
		if (sectionName.equals("any"))
			System.out.println("Mandatory breakpoint");
	}

	

	



	
}

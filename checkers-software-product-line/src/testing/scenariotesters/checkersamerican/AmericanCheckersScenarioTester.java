package testing.scenariotesters.checkersamerican;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
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
import testing.scenariotesters.IScenarioTester;

public class AmericanCheckersScenarioTester implements IScenarioTester {

	AmericanTesterReferee referee;
	IPlayer player; // The player that has the current turn.
	AbstractPiece piece; // The piece that is doing the move.
	
	IMoveCoordinate playerMove; // The move that is the focus of the currently running test.
	ICoordinate sourceCoordinate; // Source coordinate of the playerMove.
	ICoordinate destinationCoordinate; // Destination coordinate of the playerMove.
	int xOfSource, yOfSource, xOfDestination, yOfDestination; // Explicit coordinates of playerMove.
	private SourceCoordinateValidity sourceCoordinateValidity; // Info about the validity of sourceCoordinate.
	private DestinationCoordinateValidity destinationCoordinateValidity; // Info about the validity of destinationCoordinate.
	
	ICoordinate jumpedCoordinate; // The coordinate that is being jumped over if the move is a jump move.
	AbstractPiece jumpedPiece; // The jumped piece if the move is a jump move.
	List<String> informers; // Informers of the referee after the test run.
	boolean playerWasGoingToMakeAnotherMove; // True if the player was going to make another move after the playerMove is conducted.
	
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
		referee.setGameSetupName(p1);
		referee.setup();
		player = referee.getCurrentPlayer();
		playerMove = referee.readPlayerMove();
		//The following methods not only decide the coordinates' validity, they also set up other playerMove related variables.
		sourceCoordinateValidity = checkSourceCoordinate();
		destinationCoordinateValidity = checkDestinationCoordinate();
	}

	@Override
	public void thePlayerPicksAValidSourceCoordinate() {
		assertEquals(SourceCoordinateValidity.VALID, sourceCoordinateValidity);
	}

	@Override
	public void thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(String p1) {
		if (p1.equals("one")) {
			assertEquals(DestinationCoordinateValidity.VALID_REGULAR, destinationCoordinateValidity);
		} else if (p1.equals("two")) {
			assertEquals(DestinationCoordinateValidity.VALID_JUMP, destinationCoordinateValidity);
		}
		//If there were no errors up to this point, conduct the game.
		referee.conductGame();
		informers = referee.informers;
		playerWasGoingToMakeAnotherMove = referee.playerWasGoingToMakeAnotherMove;
	}
	
	@Override
	public void thePieceAtTheSourceCoordinateIsMovedToTheDestinationCoordinate() {
		//Check if destination coordinate now holds the moved piece.
		assertEquals(piece, getPieceAtCoordinate(destinationCoordinate));
		//Check if the source coordinate is now empty.
		assertTrue(getPieceAtCoordinate(sourceCoordinate) == null);
		//Check if the piece's current coordinate is the same as player move's destination coordinate.
		assertEquals(destinationCoordinate, piece.getCurrentCoordinate());
	}

	@Override
	public void theNextTurnIsGivenToTheP1Player(String p1) {
		if (p1.equals("other")) {
			assertFalse(playerWasGoingToMakeAnotherMove);
			assertFalse(referee.getCurrentPlayer().equals(player));
		} else if (p1.equals("current")) {
			assertTrue(playerWasGoingToMakeAnotherMove);
			assertTrue(referee.getCurrentPlayer().equals(player));
		}
	}

	@Override
	public void theOpponentPieceInBetweenTheSourceAndDestinationCoordinatesAreRemovedFromTheBoard() {
		assertEquals(null, getPieceAtCoordinate(jumpedCoordinate));
		assertEquals(Zone.ONSIDE, jumpedPiece.getCurrentZone());
		assertEquals(null, jumpedPiece.getCurrentCoordinate());
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
			assertEquals(DestinationCoordinateValidity.OUTSIDE_OF_THE_BOARD, destinationCoordinateValidity);
		} else if (reason.equals("destination coordinate is not of valid square color")) {
			assertEquals(DestinationCoordinateValidity.NOT_OF_VALID_SQUARE_COLOR, destinationCoordinateValidity);
		} else if (reason.equals("destination coordinate is occupied")) {
			//Accept "SAME_AS_SOURCE" error as "OCCUPIED" for now.
			if (destinationCoordinateValidity != DestinationCoordinateValidity.SAME_AS_SOURCE)
				assertEquals(DestinationCoordinateValidity.OCCUPIED, destinationCoordinateValidity);
		} else if (reason.equals("destination coordinate's direction is not allowed")) {
			//If piece is king, then it can move in any direction. The test fails here. Game set-up (ini file) is not good.
			assertFalse(piece instanceof King);
			assertEquals(DestinationCoordinateValidity.UNALLOWED_DIRECTION, destinationCoordinateValidity);
		} else if (reason.equals("destination coordinate is more than two squares away")) {
			assertEquals(DestinationCoordinateValidity.MORE_THAN_TWO_SQUARES_AWAY, destinationCoordinateValidity);
		} else if (reason.equals("move is not a jump move even though there are possible jump moves")) {
			assertEquals(DestinationCoordinateValidity.NOT_ONE_OF_POSSIBLE_JUMP_MOVES, destinationCoordinateValidity);
		} else if (reason.equals("jumped piece is null")) {
			assertEquals(DestinationCoordinateValidity.JUMPED_PIECE_IS_NULL, destinationCoordinateValidity);
		} else if (reason.equals("jumped piece is not opponent piece")) {
			assertEquals(DestinationCoordinateValidity.JUMPED_PIECE_IS_OWN, destinationCoordinateValidity);
		} else {
			throw new PendingException();
		}
		//If there were no errors up to this point, conduct the game.
		referee.conductGame();
		informers = referee.informers;
		playerWasGoingToMakeAnotherMove = referee.playerWasGoingToMakeAnotherMove;
	}	
	
	private void invalidSourceCoordinate(String reason) {
		if (reason.equals("source coordinate is empty")) {
			assertEquals(SourceCoordinateValidity.EMPTY, sourceCoordinateValidity);
		} else if (reason.equals("source coordinate has opponent's piece")) {
			assertEquals(SourceCoordinateValidity.OPPONENT_PIECE, sourceCoordinateValidity);
		} else if (reason.equals("source coordinate is not of valid square color")) {
			assertEquals(SourceCoordinateValidity.NOT_OF_VALID_SQUARE_COLOR, sourceCoordinateValidity);
		} else if (reason.equals("source coordinate is outside of the board")) {
			assertEquals(SourceCoordinateValidity.OUTSIDE_OF_THE_BOARD, sourceCoordinateValidity);
		} else {
			throw new PendingException();
		}
	}

	@Override
	public void thePlayerPicksAnyDestinationCoordinate() {
		referee.conductGame();
		informers = referee.informers;
		playerWasGoingToMakeAnotherMove = referee.playerWasGoingToMakeAnotherMove;
	}

	@Override
	public void anErrorMessageIsShownSayingP1(String p1) {
		assertTrue(informers.size() > 0); //Because there is at least 1 error message and 1 message about the next move.
		assertEquals(p1, informers.get(0)); //Error message stays in the 0th index.
	}

	@Override
	public void thePlayerIsAskedForAnotherP1Coordinate(String p1) {
		if (p1.equals("source")) {
			assertEquals("Player will be asked for another source coordinate (previous move was invalid)...", informers.get(1));
		} else {
			throw new PendingException();
		}

	}

	@Override
	public void thereIsAPossibilityForThePlayerToMakeAJumpMove() {
		throw new PendingException();

	}

	@Override
	public void thePlayerPicksAMoveThatIsNotOneOfTheAvailableJumpMoves() {
		throw new PendingException();

	}

	@Override
	public void thePlayerHasPerformedOneOrMoreJumpMoves() {
		throw new PendingException();

	}

	@Override
	public void thePlayerPicksAValidDestinationCoordinateWhereNoMoreJumpMovesWillBePossible() {
		throw new PendingException();

	}

	@Override
	public void theMoveIsPerformed() {
		throw new PendingException();

	}

	@Override
	public void thePlayerPicksAValidDestinationCoordinateWhereHisNormalPieceWillBecomeAKingPiece() {
		throw new PendingException();

	}

	@Override
	public void thePieceTransformedToAKingPiece() {
		throw new PendingException();

	}

	@Override
	public void thePlayerPicksAMoveWithANormalPieceAndADestinationCoordinateInOpponentsCrownhead() {
		throw new PendingException();

	}

	@Override
	public void onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard() {
		throw new PendingException();

	}

	@Override
	public void thePlayerJumpsOverTheLastPieceOfTheOpponent() {
		throw new PendingException();

	}

	@Override
	public void theOpponentLosesTheGame() {
		throw new PendingException();

	}

	@Override
	public void thePlayerWinsTheGame() {
		throw new PendingException();

	}

	@Override
	public void noneOfThePlayersCanForceAWinOnTheOtherPlayer() {
		throw new PendingException();

	}

	@Override
	public void onePlayerOffersTheOtherToEndTheGameInADraw() {
		throw new PendingException();

	}

	@Override
	public void theOtherPlayerAcceptsTheOffer() {
		throw new PendingException();

	}

	@Override
	public void theGameEndsInADraw() {
		throw new PendingException();

	}

	@Override
	public void thePlayerMovesANormalPieceToANoncrownheadCoordinate() {
		throw new PendingException();

	}

	@Override
	public void theNumberOfMovesWithoutUpgradeIsIncrementedBy1() {
		throw new PendingException();

	}

	@Override
	public void theGameIsEndedAsADrawIfTheNumberOfMovesWithoutUpgradeIs40() {
		throw new PendingException();

	}

	@Override
	public void thePlayerHasOnlyOnePieceOnTheGameBoard() {
		throw new PendingException();

	}

	@Override
	public void thePlayerJumpsOverOneOrMultiplePiecesOfTheOpponent() {
		throw new PendingException();

	}

	@Override
	public void theGameIsEndedADrawIfTheOpponentStillHasOnePieceOnTheGameBoard() {
		throw new PendingException();

	}

	@Override
	public void thePlayerMovesAPieceWithoutUndertakingAnOpponentPiece() {
		throw new PendingException();

	}

	@Override
	public void theNumberOfMovesWithoutUndertakeIsIncrementedBy1() {
		throw new PendingException();

	}

	@Override
	public void theGameIsEndedAsInDrawIfTheNumberOfMovesWithoutUndertakeIs40() {
		throw new PendingException();

	}

	@Override
	public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces() {
		throw new PendingException();

	}

	@Override
	public void thePlayerPicksAValidSourceCoordinateThatHasAPawnPieceInIt() {
		assertEquals(SourceCoordinateValidity.VALID, sourceCoordinateValidity);
		assertTrue(piece instanceof Pawn);
	}

	@Override
	public void thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead() {
		assertTrue(destinationCoordinateValidity == DestinationCoordinateValidity.VALID_REGULAR
					|| destinationCoordinateValidity == DestinationCoordinateValidity.VALID_JUMP);
		
		if (piece.getGoalDirection() == Direction.N) {
			assertEquals(7, yOfDestination);
		} else if (piece.getGoalDirection() == Direction.S) {
			assertEquals(0, yOfDestination);
		}
		
		referee.conductGame();
		informers = referee.informers;
		playerWasGoingToMakeAnotherMove = referee.playerWasGoingToMakeAnotherMove;
	}

	@Override
	public void thePieceAtTheSourceCoordinateBecomesAKingPiece() {
		AbstractPiece newPiece = getPieceAtCoordinate(destinationCoordinate);
		assertTrue(newPiece != null);
		assertEquals(piece.getId()+2, newPiece.getId());
		//TODO: Check icon conversion.
		//assertEquals(piece.getIcon(), newPiece.getIcon());
		assertEquals(player, newPiece.getPlayer());
		assertEquals(piece.getGoalDirection(), newPiece.getGoalDirection());
		assertTrue(newPiece.getPieceMovePossibilities() instanceof KingMovePossibilities);
		assertTrue(newPiece.getPieceMoveConstraints() instanceof KingMoveConstraints);
		assertTrue(newPiece instanceof King);
		piece = newPiece;
	}
	
	//PRIVATE/HELPER METHODS AND CLASSES
	private List<IMoveCoordinate> findPossibleJumpMoves() {
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

	private boolean isMoveOneOfPossibleJumpMoves() {
		List<IMoveCoordinate> possibleJumpMoves = findPossibleJumpMoves();
		if (possibleJumpMoves.size() == 0)
			return true;
		for (IMoveCoordinate move : possibleJumpMoves) {
			if (move.equals(playerMove))
				return true;
		}
		return false;
	}
	
	private AbstractPiece getPieceAtCoordinate(ICoordinate coordinate) {
		return referee.getCoordinatePieceMap().getPieceAtCoordinate(coordinate);
	}
	
	private enum SourceCoordinateValidity {
		VALID, OUTSIDE_OF_THE_BOARD, NOT_OF_VALID_SQUARE_COLOR, EMPTY, OPPONENT_PIECE
	}
	private SourceCoordinateValidity checkSourceCoordinate() {
		//Set-up the source coordinate and piece.
		sourceCoordinate = playerMove.getSourceCoordinate();
		xOfSource = sourceCoordinate.getXCoordinate(); yOfSource = sourceCoordinate.getYCoordinate();
		piece = getPieceAtCoordinate(sourceCoordinate);
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
	
	private enum DestinationCoordinateValidity {
		//Destination coordinate is either valid, or its invalidity reason are one of the below
		VALID_REGULAR, VALID_JUMP, OUTSIDE_OF_THE_BOARD, SAME_AS_SOURCE, NOT_OF_VALID_SQUARE_COLOR, OCCUPIED, UNALLOWED_DIRECTION,
		MORE_THAN_TWO_SQUARES_AWAY, NOT_ONE_OF_POSSIBLE_JUMP_MOVES, JUMPED_PIECE_IS_NULL, JUMPED_PIECE_IS_OWN,
		SOURCE_COORDINATE_PROBLEM, UNKNOWN_ERROR
	}
	private DestinationCoordinateValidity checkDestinationCoordinate() {
		//If source coordinate is not set up or valid, destination coordinate can't be truly valid.
		if (sourceCoordinateValidity == null || sourceCoordinateValidity != SourceCoordinateValidity.VALID)
			return DestinationCoordinateValidity.SOURCE_COORDINATE_PROBLEM;
		//Set up the destination coordinate.
		destinationCoordinate = playerMove.getDestinationCoordinate();
		xOfDestination = destinationCoordinate.getXCoordinate(); yOfDestination = this.destinationCoordinate.getYCoordinate();
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
		jumpedCoordinate = new Coordinate(xOfSource + xDiff/2, yOfSource + yDiff/2);
		jumpedPiece = getPieceAtCoordinate(jumpedCoordinate);
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


	
	
	
}

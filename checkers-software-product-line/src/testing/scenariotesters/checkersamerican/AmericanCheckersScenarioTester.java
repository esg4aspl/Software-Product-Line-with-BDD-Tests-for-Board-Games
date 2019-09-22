package testing.scenariotesters.checkersamerican;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.List;

import base.AmericanGameConfiguration;
import base.Pawn;
import core.AbstractPiece;
import core.Coordinate;
import core.Direction;
import core.ICoordinate;
import core.IMoveCoordinate;
import core.IPlayer;
import core.Zone;
import cucumber.api.PendingException;
import testing.scenariotesters.IScenarioTester;

public class AmericanCheckersScenarioTester implements IScenarioTester {

	AmericanTesterReferee referee;
	IMoveCoordinate playerMove;
	ICoordinate sourceCoordinateOfPlayerMove;
	ICoordinate destinationCoordinateOfPlayerMove;
	ICoordinate inbetweenCoordinateOfPlayerJumpMove;
	AbstractPiece inbetweenOpponentPiece;
	IPlayer player;
	AbstractPiece piece;
	List<String> informers;
	boolean playerWasGoingToMakeAnotherMove;
	
	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new AmericanTesterReferee(new AmericanGameConfiguration());
	}

	@Override
	public void thePlayersStartTheGame() {
		// TODO Can there be any implementation for this method?
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
	}

	@Override
	public void thePlayerPicksAValidSourceCoordinate() {
		playerMove = referee.readPlayerMove();
		//Check - coordinate is on board:
		sourceCoordinateOfPlayerMove = playerMove.getSourceCoordinate();
		int xCoordinate = sourceCoordinateOfPlayerMove.getXCoordinate();
		int yCoordinate = sourceCoordinateOfPlayerMove.getYCoordinate();
		assertTrue((xCoordinate >= 0 && xCoordinate <= 7));
		assertTrue((yCoordinate >= 0 && yCoordinate <= 7));
		//Check - coordinate is in valid square color:
		assertTrue(referee.getBoard().isPlayableCoordinate(sourceCoordinateOfPlayerMove));
		//Check - coordinate has a player's piece on it:
		piece = referee.getCoordinatePieceMap().getPieceAtCoordinate(sourceCoordinateOfPlayerMove);
		assertTrue(piece != null);
		assertEquals(player, piece.getPlayer());
	}

	@Override
	public void thePieceAtTheSourceCoordinateIsMovedToTheDestinationCoordinate() {
		//Check if destination coordinate now holds the moved piece.
		assertEquals(piece, referee.getCoordinatePieceMap().getPieceAtCoordinate(destinationCoordinateOfPlayerMove));
		//Check if the source coordinate is now empty.
		assertTrue(referee.getCoordinatePieceMap().getPieceAtCoordinate(sourceCoordinateOfPlayerMove) == null);
		//Check if the piece's current coordinate is the same as player move's destination coordinate.
		assertEquals(destinationCoordinateOfPlayerMove, piece.getCurrentCoordinate());
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
	public void thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(String p1) {
		destinationCoordinateOfPlayerMove = playerMove.getDestinationCoordinate();
		int xOfDestination = destinationCoordinateOfPlayerMove.getXCoordinate();
		int yOfDestination = destinationCoordinateOfPlayerMove.getYCoordinate();
		//Check - coordinate is on board:
		assertTrue((xOfDestination >= 0 && xOfDestination <= 7));
		assertTrue((yOfDestination >= 0 && yOfDestination <= 7));
		//Check - coordinate is empty:
		assertEquals(null, referee.getCoordinatePieceMap().getPieceAtCoordinate(destinationCoordinateOfPlayerMove));

		int xOfSource = sourceCoordinateOfPlayerMove.getXCoordinate();
		int yOfSource = sourceCoordinateOfPlayerMove.getYCoordinate();
		if (p1.equals("one")) {
			//Check - destination coordinate is 1 square away from source coordinate
			assertEquals(1, Math.abs(xOfDestination - xOfSource));
			if (piece instanceof Pawn && piece.getGoalDirection() == Direction.N) {
				assertEquals(1, yOfDestination - yOfSource);
			} else if (piece instanceof Pawn && piece.getGoalDirection() == Direction.S) {
				assertEquals(-1, yOfDestination - yOfSource);
			} else {
				assertEquals(1, Math.abs(yOfDestination - yOfSource));
			}
			assertEquals(1, Math.abs(yOfDestination - yOfSource));
			
		} else if (p1.equals("two")) {
			//Check - destination coordinate is 2 squares away from source coordinate
			assertEquals(2, Math.abs(xOfDestination - xOfSource));
			if (piece instanceof Pawn && piece.getGoalDirection() == Direction.N) {
				assertEquals(2, yOfDestination - yOfSource);
			} else if (piece instanceof Pawn && piece.getGoalDirection() == Direction.S) {
				assertEquals(-2, yOfDestination - yOfSource);
			} else {
				assertEquals(2, Math.abs(yOfDestination - yOfSource));
			}
			//Check - the square in between source and destination has an opponent piece
			int xOfInbetween = ((xOfDestination - xOfSource) < 0) ? xOfSource-1 : xOfSource+1;
			int yOfInbetween = ((yOfDestination - yOfSource) < 0) ? yOfSource-1 : yOfSource+1;
			inbetweenCoordinateOfPlayerJumpMove = new Coordinate(xOfInbetween, yOfInbetween);
			inbetweenOpponentPiece = referee.getCoordinatePieceMap().getPieceAtCoordinate(inbetweenCoordinateOfPlayerJumpMove);
			assertTrue(inbetweenOpponentPiece != null);
		}
		//If there were no errors up to this point, conduct the game.
		referee.conductGame();
		informers = referee.informers;
		playerWasGoingToMakeAnotherMove = referee.playerWasGoingToMakeAnotherMove;
	}

	@Override
	public void theOpponentPieceInBetweenTheSourceAndDestinationCoordinatesAreRemovedFromTheBoard() {
		assertEquals(null, referee.getCoordinatePieceMap().getPieceAtCoordinate(inbetweenCoordinateOfPlayerJumpMove));
		assertEquals(Zone.ONSIDE, inbetweenOpponentPiece.getCurrentZone());
		assertEquals(null, inbetweenOpponentPiece.getCurrentCoordinate());
	}

	@Override
	public void thePlayerPicksAnInvalidP1CoordinateBecauseP2(String p1, String p2) {
		playerMove = referee.readPlayerMove();
		if (p1.equals("source")) {
			invalidSourceCoordinate(p2);
		} else if (p1.equals("destination")){
			throw new PendingException();
		}
	}
	
	private void invalidSourceCoordinate(String reason) {
		sourceCoordinateOfPlayerMove = playerMove.getSourceCoordinate();
		piece = referee.getCoordinatePieceMap().getPieceAtCoordinate(sourceCoordinateOfPlayerMove);
		if (reason.equals("source coordinate is empty")) {
			assertTrue(piece == null);
		} else if (reason.equals("source coordinate has opponent's piece")) {
			assertFalse(player.equals(piece.getPlayer()));
		} else if (reason.equals("source coordinate is not of valid square color")) {
			assertFalse(referee.getBoard().isPlayableCoordinate(sourceCoordinateOfPlayerMove));
		} else if (reason.equals("source coordinate is outside of the board")) {
			int xCoordinate = sourceCoordinateOfPlayerMove.getXCoordinate();
			int yCoordinate = sourceCoordinateOfPlayerMove.getYCoordinate();
			assertFalse(0 <= xCoordinate && xCoordinate <= 7 && 0 <= yCoordinate && yCoordinate <= 7);
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

	
	
}

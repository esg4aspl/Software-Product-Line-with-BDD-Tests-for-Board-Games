package testing.scenariotesters.checkersamerican;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import base.AmericanGameConfiguration;
import core.AbstractPiece;
import core.ICoordinate;
import core.IMoveCoordinate;
import core.IPlayer;
import cucumber.api.PendingException;
import testing.scenariotesters.IScenarioTester;

public class AmericanCheckersScenarioTester implements IScenarioTester {

	AmericanTesterReferee referee;
	IMoveCoordinate playerMove;
	ICoordinate sourceCoordinateOfPlayerMove;
	ICoordinate destinationCoordinateOfPlayerMove;
	IPlayer player;
	AbstractPiece piece;
	
	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new AmericanTesterReferee(new AmericanGameConfiguration());
	}

	@Override
	public void thePlayersStartTheGame() {
		// TODO Auto-generated method stub
		
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
		//Double checking - coordinate is on board:
		sourceCoordinateOfPlayerMove = playerMove.getSourceCoordinate();
		int xCoordinate = sourceCoordinateOfPlayerMove.getXCoordinate();
		int yCoordinate = sourceCoordinateOfPlayerMove.getYCoordinate();
		assertTrue((xCoordinate >= 0 && xCoordinate <= 7));
		assertTrue((yCoordinate >= 0 && yCoordinate <= 7));
		//Double checking - coordinate has a player's piece on it:
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
		//Check if the piece's current coordiante is the same as player move's destination coordinate.
		assertEquals(destinationCoordinateOfPlayerMove, piece.getCurrentCoordinate());
	}

	@Override
	public void theNextTurnIsGivenToTheP1Player(String p1) {
		if (p1.equals("other")) {
			assertFalse(referee.getCurrentPlayer().equals(player));
		} else if (p1.equals("current")) {
			throw new PendingException();
		}
	}

	@Override
	public void thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(String p1) {
		destinationCoordinateOfPlayerMove = playerMove.getDestinationCoordinate();
		int xCoordinate = destinationCoordinateOfPlayerMove.getXCoordinate();
		int yCoordinate = destinationCoordinateOfPlayerMove.getYCoordinate();
		//Check - coordinate is on board:
		assertTrue((xCoordinate >= 0 && xCoordinate <= 7));
		assertTrue((yCoordinate >= 0 && yCoordinate <= 7));
		//Check - coordinate is empty:
		assertEquals(null, referee.getCoordinatePieceMap().getPieceAtCoordinate(destinationCoordinateOfPlayerMove));
		if (p1.equals("one")) {
			//Check - destination coordinate is 1 square away from source coordinate
			int xOfSource = sourceCoordinateOfPlayerMove.getXCoordinate();
			int yOfSource = sourceCoordinateOfPlayerMove.getYCoordinate();
			assertEquals(1, Math.abs(xCoordinate - xOfSource));
			assertEquals(1, Math.abs(yCoordinate - yOfSource));
		} else if (p1.equals("two")) {
			throw new PendingException();
		}
		//If there were no errors up to this point, conduct the game.
		referee.conductGame();
	}

	@Override
	public void theOpponentPieceInBetweenTheSourceAndDestinationCoordinatesAreRemovedFromTheBoard() {
		throw new PendingException();

	}

	@Override
	public void thePlayerPicksAnInvalidP1CoordinateBecauseP2(String p1, String p2) {
		throw new PendingException();

	}

	@Override
	public void thePlayerPicksAnyDestinationCoordinate() {
		throw new PendingException();

	}

	@Override
	public void anErrorMessageIsShownSayingP1(String p1) {
		throw new PendingException();

	}

	@Override
	public void thePlayerIsAskedForAnotherP1Coordinate(String p1) {
		throw new PendingException();

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

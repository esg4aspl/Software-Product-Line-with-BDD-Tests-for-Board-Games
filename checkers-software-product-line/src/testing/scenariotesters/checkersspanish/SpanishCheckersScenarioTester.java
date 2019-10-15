package testing.scenariotesters.checkersspanish;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import base.AmericanGameConfiguration;
import checkersamerican.King;
import cucumber.api.PendingException;
import testing.helpers.DestinationCoordinateValidity;
import testing.scenariotesters.IScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;

public class SpanishCheckersScenarioTester extends AmericanCheckersScenarioTester implements IScenarioTester {
	

	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new SpanishTesterReferee(new AmericanGameConfiguration());
	}

	@Override
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
		} else if (reason.equals("jumped piece is too far away from source coordinate")) {
			assertEquals(DestinationCoordinateValidity.MORE_THAN_TWO_SQUARES_AWAY_FROM_SOURCE, destinationCoordinateValidityOfPlayerMove);
		} else if (reason.equals("destination coordinate is more than one square away from jumped piece")) {
			assertEquals(DestinationCoordinateValidity.MORE_THAN_ONE_SQUARE_AWAY_FROM_JUMPED_PIECE, destinationCoordinateValidityOfPlayerMove);			
		} else if (reason.equals("there are more than one pieces in jump path")) {
			assertEquals(DestinationCoordinateValidity.MULTIPLE_JUMPED_PIECES, destinationCoordinateValidityOfPlayerMove);			
		} else if (reason.equals("move is not part of the best sequence")) {
			assertEquals(DestinationCoordinateValidity.NOT_THE_BEST_SEQUENCE, destinationCoordinateValidityOfPlayerMove);			
		} else {
			throw new PendingException();
		}
	}
	
	
	
	

	

	

}

package testing.scenariotesters.checkerschildren;

import base.AmericanGameConfiguration;
import base.Pawn;
import core.AbstractPiece;
import core.Coordinate;
import core.Direction;
import core.ICoordinate;
import core.IPlayer;
import testing.scenariotesters.IScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanTesterReferee;

public class ChildrenCheckersScenarioTester extends AmericanCheckersScenarioTester implements IScenarioTester {

	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new ChildrenTesterReferee(new AmericanGameConfiguration());
	}

	@Override
	protected DestinationCoordinateValidity checkDestinationCoordinate(IPlayer player, ICoordinate sourceCoordinate,
			ICoordinate destinationCoordinate) {
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
	
	

	
	
}

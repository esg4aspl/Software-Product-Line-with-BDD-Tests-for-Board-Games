package testing.scenariotesters.checkersamerican;

import core.AbstractPiece;
import core.Coordinate;
import core.ICoordinate;
import testing.helpers.DestinationCoordinateValidity;
import testing.scenariotesters.AbstractTestInfo;
import testing.scenariotesters.AbstractTesterReferee;

public class AmericanCheckersTestInfo extends AbstractTestInfo {

	private ICoordinate jumpedCoordinate;
	private AbstractPiece jumpedPiece;

	public AmericanCheckersTestInfo(AbstractTesterReferee referee, String file_path, String file_name) {
		super(referee, file_path, file_name);
	}

	@Override
	public void setupPlayerMoveInfo() {
		ICoordinate src = playerMove.getSourceCoordinate();
		ICoordinate dest = playerMove.getDestinationCoordinate();
		
		pieceOfPlayerMove = referee.getCoordinatePieceMap().getPieceAtCoordinate(src);
		if (pieceOfPlayerMove != null)
			playerOfPlayerMove = pieceOfPlayerMove.getPlayer();
		
		this.sourceCoordinateValidity = referee.checkSourceCoordinate(referee.getCurrentPlayer(),
				this.playerMove.getSourceCoordinate());
		this.destinationCoordinateValidity = referee.checkDestinationCoordinate(referee.getCurrentPlayer(),
				this.playerMove.getSourceCoordinate(), this.playerMove.getDestinationCoordinate());
		
		if (destinationCoordinateValidity == DestinationCoordinateValidity.JUMPED_PIECE_IS_NULL
				|| destinationCoordinateValidity == DestinationCoordinateValidity.JUMPED_PIECE_IS_OWN
				|| destinationCoordinateValidity == DestinationCoordinateValidity.VALID_JUMP) {
			jumpedCoordinate = new Coordinate(src.getXCoordinate() + (dest.getXCoordinate()-src.getXCoordinate())/2, src.getYCoordinate() + (dest.getYCoordinate() - src.getYCoordinate())/2);
			jumpedPiece = referee.getCoordinatePieceMap().getPieceAtCoordinate(jumpedCoordinate);
		}
	}

	public ICoordinate getJumpedCoordinate() {
		return jumpedCoordinate;
	}

	public AbstractPiece getJumpedPiece() {
		return jumpedPiece;
	}
	
	

}

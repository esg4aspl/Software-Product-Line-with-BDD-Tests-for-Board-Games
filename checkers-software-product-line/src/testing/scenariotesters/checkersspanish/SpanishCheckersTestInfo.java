package testing.scenariotesters.checkersspanish;

import java.util.List;

import core.AbstractPiece;
import core.ICoordinate;
import testing.helpers.DestinationCoordinateValidity;
import testing.scenariotesters.AbstractTestInfo;
import testing.scenariotesters.AbstractTesterReferee;

public class SpanishCheckersTestInfo extends AbstractTestInfo {

	private ICoordinate jumpedCoordinate;
	private AbstractPiece jumpedPiece;
	
	public SpanishCheckersTestInfo(AbstractTesterReferee referee, String file_path, String file_name) {
		super(referee, file_path, file_name);
	}

	@Override
	public void setupPlayerMoveInfo() {
		ICoordinate src = playerMove.getSourceCoordinate();
		ICoordinate dest = playerMove.getDestinationCoordinate();
		this.sourceCoordinateValidity = referee.checkSourceCoordinate(referee.getCurrentPlayer(),
				this.playerMove.getSourceCoordinate());
		this.destinationCoordinateValidity = referee.checkDestinationCoordinate(referee.getCurrentPlayer(),
				this.playerMove.getSourceCoordinate(), this.playerMove.getDestinationCoordinate());
		
		if (destinationCoordinateValidity == DestinationCoordinateValidity.JUMPED_PIECE_IS_OWN
				|| destinationCoordinateValidity == DestinationCoordinateValidity.VALID_JUMP) {
			List<ICoordinate> occupiedJumpedCoordinates = ((SpanishTesterReferee) referee).findOccupiedJumpedCoordinates(src, dest);
			jumpedCoordinate = occupiedJumpedCoordinates.get(0);
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

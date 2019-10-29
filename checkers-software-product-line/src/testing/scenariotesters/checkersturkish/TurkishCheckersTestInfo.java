package testing.scenariotesters.checkersturkish;

import java.util.List;

import core.ICoordinate;
import testing.helpers.DestinationCoordinateValidity;
import testing.scenariotesters.AbstractTesterReferee;
import testing.scenariotesters.checkersamerican.AmericanCheckersTestInfo;
import testing.scenariotesters.checkersspanish.SpanishTesterReferee;

public class TurkishCheckersTestInfo extends AmericanCheckersTestInfo {
	
	protected boolean reachedCrownheadWithJumpMove;

	public TurkishCheckersTestInfo(AbstractTesterReferee referee, String file_path, String file_name) {
		super(referee, file_path, file_name);
		reachedCrownheadWithJumpMove = false;
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
		
		if (destinationCoordinateValidity == DestinationCoordinateValidity.JUMPED_PIECE_IS_OWN
				|| destinationCoordinateValidity == DestinationCoordinateValidity.VALID_JUMP) {
			List<ICoordinate> occupiedJumpedCoordinates = ((TurkishTesterReferee) referee).findOccupiedJumpedCoordinates(src, dest);
			jumpedCoordinate = occupiedJumpedCoordinates.get(0);
			jumpedPiece = referee.getCoordinatePieceMap().getPieceAtCoordinate(jumpedCoordinate);
		}
	}

	public boolean isReachedCrownheadWithJumpMove() {
		return reachedCrownheadWithJumpMove;
	}
	
	

}

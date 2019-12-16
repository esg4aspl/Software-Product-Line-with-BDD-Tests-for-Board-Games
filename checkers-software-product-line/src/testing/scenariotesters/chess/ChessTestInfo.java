package testing.scenariotesters.chess;

import core.ICoordinate;
import testing.scenariotesters.AbstractTestInfo;
import testing.scenariotesters.AbstractTesterReferee;
import testing.scenariotesters.checkersamerican.AmericanCheckersTestInfo;

public class ChessTestInfo extends AmericanCheckersTestInfo {

	public ChessTestInfo(AbstractTesterReferee referee, String file_path, String file_name) {
		super(referee, file_path, file_name);
		// TODO Auto-generated constructor stub
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
	}

}

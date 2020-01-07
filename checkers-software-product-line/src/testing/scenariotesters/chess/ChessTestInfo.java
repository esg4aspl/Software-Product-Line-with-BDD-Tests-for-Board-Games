package testing.scenariotesters.chess;

import base.Pawn;
import chess.LimitedPawn;
import core.Coordinate;
import core.ICoordinate;
import testing.scenariotesters.AbstractTesterReferee;
import testing.scenariotesters.checkersamerican.AmericanCheckersTestInfo;

public class ChessTestInfo extends AmericanCheckersTestInfo {

	public ChessTestInfo(AbstractTesterReferee referee, String file_path, String file_name) {
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
		
		jumpedCoordinate = dest;
		jumpedPiece = referee.getCoordinatePieceMap().getPieceAtCoordinate(dest);
		
		if (pieceOfPlayerMove instanceof Pawn || pieceOfPlayerMove instanceof LimitedPawn
				&& dest.getXCoordinate() - src.getXCoordinate() != 0 
				&& referee.getCoordinatePieceMap().getPieceAtCoordinate(dest) == null) {
			jumpedCoordinate = new Coordinate(dest.getXCoordinate(), src.getYCoordinate());
			jumpedPiece = referee.getCoordinatePieceMap().getPieceAtCoordinate(jumpedCoordinate);
		}
	}

}

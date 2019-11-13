package testing.scenariotesters.checkerschinese;

import core.Coordinate;
import core.ICoordinate;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.TestResult;
import testing.scenariotesters.AbstractTestInfo;
import testing.scenariotesters.AbstractTesterReferee;

public class ChineseCheckersTestInfo extends AbstractTestInfo {

	protected boolean playerIsAskedToContinue;
	protected boolean playerAnswerToContinuationOfJumpMove;
	
	public ChineseCheckersTestInfo(AbstractTesterReferee referee, String file_path, String file_name) {
		super(referee, file_path, file_name);
		playerIsAskedToContinue = false;
		this.playerAnswerToContinuationOfJumpMove = false;
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
			jumpedCoordinate = new Coordinate(src.getXCoordinate() + (dest.getXCoordinate() - src.getXCoordinate()) / 2,
					src.getYCoordinate() + (dest.getYCoordinate() - src.getYCoordinate()) / 2);
			jumpedPiece = referee.getCoordinatePieceMap().getPieceAtCoordinate(jumpedCoordinate);
		}
	}
	
	
	@Override
	protected void reset() {
		super.reset();
		playerIsAskedToContinue = false;
	}
	
	
	public boolean getPlayerAnswerToContinuationOfJumpMove() {
		if (!reader.hasExtras()) {
			return false;
		}
		String[] extras = reader.getExtras();
		for (String e : extras) {
			String[] elements = e.split("-");
			if (elements[0].equals("userAnswerToContinuationOfJumpMove") && elements[1].equals("yes")) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void register(TestResult res) {
			informers.add(res.getMessage());
			reset();
			if (res == TestResult.GAME_END_AS_DRAW) {
				 gameEnded = true;
				 isDraw = true;
			} else if (res == TestResult.GAME_END_AS_WIN) {
				gameEnded = true; 
				winner = referee.getCurrentPlayer();
				loser = referee.getNextPlayer();
			} else if (res == TestResult.ANOTHER_SOURCE_INVALID || res == TestResult.ANOTHER_DESTINATION_INVALID || res == TestResult.ANOTHER_MOVE_JUMP_POSSIBILITY) {
				playerWasGoingToMakeAnotherMove = true;
			} else if (res == TestResult.ASKED_TO_CONTINUE) {
				playerIsAskedToContinue = true;
			}
			
			if (endOfMoves)
				finalInformers.add(res.getMessage());
	}

}

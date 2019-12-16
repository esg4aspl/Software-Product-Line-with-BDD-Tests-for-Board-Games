package testing.scenariotesters.chess;

import testing.scenariotesters.IScenarioTester;

public interface IChessScenarioTester extends IScenarioTester {

	public void thePlayerPicksAValidDestinationCoordinateThatHasNoPiecesInIt();
	public void thePlayerPicksAValidDestinationCoordinateThatHasACapturableOpponentPieceInIt();
	public void theOpponentPieceIsRemovedFromTheBoard();
	public void theOpponentsKingHasNoValidMoveToMake();
	public void thePlayerMakesAMoveThatThreatensTheOpponentsKingsCurrentPosition();
	public void theOpponentsOnlyPlayerOnTheBoardIsHisKing();
	public void thePlayerMakesAMoveThatNotChecksTheOpponentKingButLeavesItWithNoValidMoveToMake();
	public void theNumberOfConsecutiveIndecisiveMovesIs99();
	public void thePlayerMovesANonpawnPieceWithoutCapturingAnOpponentPiece();
	public void theOpponentsKingCanNotBeProtectedIfItIsChecked();
	
}

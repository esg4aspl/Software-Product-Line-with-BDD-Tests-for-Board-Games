package testing.scenariotesters.chess;

import testing.scenariotesters.IScenarioTester;

public interface IChessScenarioTester extends IScenarioTester {
	public void theOpponentPieceIsRemovedFromTheBoard();
	public void thePlayerMakesAMoveThatThreatensTheOpponentsKingsCurrentPosition();
	public void thePlayerMovesANonpawnPieceWithoutCapturingAnOpponentPiece();
	public void theRookIsMovedToTheAdjacentCoordinateThatIsTowardsTheCenter();
	public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState();
	public void theEnPassantOpponentPieceIsRemovedFromTheBoard();
	public void thePlayerMakesAMoveThatNotChecksTheOpponentKingButLeavesItWithNoValidMoveToMake();
	
}

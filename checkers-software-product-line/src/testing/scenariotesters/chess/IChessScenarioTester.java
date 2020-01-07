package testing.scenariotesters.chess;

import testing.scenariotesters.IScenarioTester;

public interface IChessScenarioTester extends IScenarioTester {
	public void thePlayerMakesAMoveThatThreatensTheOpponentsKingsCurrentPosition();
	public void thePlayerMovesANonpawnPieceWithoutCapturingAnOpponentPiece();
	public void theRookIsMovedToTheAdjacentCoordinateThatIsTowardsTheCenter();
	public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState();
	public void thePlayerMakesAMoveThatNotChecksTheOpponentKingButLeavesItWithNoValidMoveToMake();
	
}

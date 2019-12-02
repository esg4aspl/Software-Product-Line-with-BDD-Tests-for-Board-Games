package testing.scenariotesters.checkerschinese;

import testing.scenariotesters.IScenarioTester;

public interface IChineseScenarioTester extends IScenarioTester {

	public void theP1GameIsSetUpForP2Players(String p1, String p2);
	public void thePlayerHasMadeJumpMovesInTheCurrentTurn();
	public void thePlayerCanContinueDoingJumpMoves();
	public void thePlayerHasBeenAskedToContinueOrNot();
	public void thePlayerChoosesToP1(String p1);
	public void thePlayerHasAtLeastOneOfHisPiecesInTheGoalTriangle();
	public void thereIsOnlyOneSquareIsAvailableAtTheGoalTriangle();
	public void thePlayerJumpsMovesOneOfHisPiecesToTheLastAvailableSquareInGoalTriangle();

	
}
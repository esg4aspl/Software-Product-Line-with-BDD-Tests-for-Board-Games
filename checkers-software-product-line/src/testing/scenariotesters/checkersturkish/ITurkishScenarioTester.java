package testing.scenariotesters.checkersturkish;

import testing.scenariotesters.IScenarioTester;

public interface ITurkishScenarioTester extends IScenarioTester {

	public void thePlayerHasAP1PieceInOpponentsCrownhead(String p1);
	public void thePlayerJumpsOverAllTheVulnerableOpponentKingsInTheCrownhead();
	public void thereAreSomeBoardStatesThatHaveBeenReachedTwoTimes();
	public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState();
	
	
}

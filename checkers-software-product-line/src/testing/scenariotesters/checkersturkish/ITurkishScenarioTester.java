package testing.scenariotesters.checkersturkish;

import testing.scenariotesters.IScenarioTester;

public interface ITurkishScenarioTester extends IScenarioTester {

	public void thePlayerHasAP1PieceInOpponentsCrownhead(String p1); //Remove
	public void thePlayerJumpsOverAllTheVulnerableOpponentKingsInTheCrownhead(); //Remove the checks
	public void thereAreSomeBoardStatesThatHaveBeenReachedTwoTimes(); //Remove
	public void thePlayerFinishesHisTurnLeavingTheBoardInAPreviouslyReachedState(); //Remove the checks
	
	
}

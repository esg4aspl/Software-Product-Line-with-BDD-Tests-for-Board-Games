package testing.scenariotesters.checkersturkish;

import testing.scenariotesters.IScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;

public class TurkishCheckersScenarioTester extends AmericanCheckersScenarioTester implements IScenarioTester {

	
	/*
	 * Override methods:
	 * theP1Game is set up
	 * up to a certain point
	 * invalid dest. coordinate
	 * the piece is p1 to a crowned piece: check info.reachedCrownheadWAJM" and replace KingMovePos with TurkishKingMovePos.
	 * 
	 * 
	
	*/
	
	/*
	 * Notes: TurkishTestInfo will have a field named "reachedCrownheadWithAJumpMove" as boolean
	 * Then the crowning step will act based on it
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

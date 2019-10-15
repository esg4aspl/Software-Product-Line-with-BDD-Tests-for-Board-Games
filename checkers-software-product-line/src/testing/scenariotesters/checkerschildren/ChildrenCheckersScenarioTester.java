package testing.scenariotesters.checkerschildren;

import base.AmericanGameConfiguration;
import base.Pawn;
import core.AbstractPiece;
import core.Coordinate;
import core.Direction;
import core.ICoordinate;
import core.IPlayer;
import testing.scenariotesters.IScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanCheckersScenarioTester;
import testing.scenariotesters.checkersamerican.AmericanTesterReferee;

public class ChildrenCheckersScenarioTester extends AmericanCheckersScenarioTester implements IScenarioTester {

	@Override
	public void theP1GameIsSetUp(String p1) {
		referee = new ChildrenTesterReferee(new AmericanGameConfiguration());
	}
	
	
}

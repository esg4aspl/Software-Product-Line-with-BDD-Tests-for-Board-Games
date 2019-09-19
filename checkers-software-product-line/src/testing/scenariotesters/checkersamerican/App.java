package testing.scenariotesters.checkersamerican;

import base.AmericanGameConfiguration;
import core.AbstractReferee;

public class App {

	public static void main(String[] args) {
		AmericanTesterReferee referee = new AmericanTesterReferee(new AmericanGameConfiguration());
		referee.setup();
		referee.setMoveFile("moveList");
		referee.playGameUpToACertainPoint();
		referee.conductGame();
	}
	
}

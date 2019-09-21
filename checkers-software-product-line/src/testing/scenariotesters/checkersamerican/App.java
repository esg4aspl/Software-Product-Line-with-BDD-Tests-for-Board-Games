package testing.scenariotesters.checkersamerican;

import base.AmericanGameConfiguration;
import core.AbstractReferee;

public class App {

	public static void main(String[] args) {
		String[] moveArr = {"validJumpMove1", "validJumpMove2", "validJumpMove3"};
		for (String moveID : moveArr) {
			System.out.println("TESTING: " + moveID);
			AmericanTesterReferee referee = new AmericanTesterReferee(new AmericanGameConfiguration());
			referee.setGameSetupName(moveID);
			referee.setup();
			referee.readPlayerMove();
			referee.conductGame();
		}
		
	}
	
}

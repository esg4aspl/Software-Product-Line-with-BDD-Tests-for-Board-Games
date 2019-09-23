package testing.scenariotesters.checkersamerican;

import base.AmericanGameConfiguration;

public class App {

	public static void main(String[] args) {
		//String[] moveArr = {"invalidDestinationCoordinateForMoveJumpedPieceIsNull1", "invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1"};
		//String[] moveArr = {"invalidDestinationCoordinateForMoveOutsideBorders1", "invalidDestinationCoordinateForMoveUnplayableColor1"};
		String[] moveArr = {"validJumpMove1"};
		//String[] moveArr = {"invalidSourceCoordinateForMoveEmpty1", "invalidSourceCoordinateForMoveEmpty2"};
		//String[] moveArr = {"invalidSourceCoordinateForMoveOpponentsPiece1", "invalidSourceCoordinateForMoveOpponentsPiece2"};
 		//String[] moveArr = {"invalidSourceCoordinateForMoveUnplayableColor1"};
		
		for (String moveID : moveArr) {
			System.out.println("\n\n\nTESTING: " + moveID);
			AmericanTesterReferee referee = new AmericanTesterReferee(new AmericanGameConfiguration());
			referee.setGameSetupName(moveID);
			referee.setup();
			referee.readPlayerMove();
			referee.conductGame();
		}
		
	}
	
}

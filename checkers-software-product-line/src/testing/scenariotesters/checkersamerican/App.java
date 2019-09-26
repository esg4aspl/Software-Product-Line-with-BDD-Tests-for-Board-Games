package testing.scenariotesters.checkersamerican;

import base.AmericanGameConfiguration;

public class App {

	public static void main(String[] args) {
		//String[] moveArr = {"invalidDestinationCoordinateForMoveJumpedPieceIsNull1", "invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1"};
		//String[] moveArr = {"invalidDestinationCoordinateForMoveOutsideBorders1", "invalidDestinationCoordinateForMoveUnplayableColor1"};
		//String[] moveArr= {"invalidDestinationCoordinateForMoveOccupied1"};
		//String[] moveArr = {"validRegularMove5", "validJumpMove9", "validRegularMove4"};
		String[] moveArr = {"validRegularMove6"};
		//String[] moveArr = {"crowningTheEligiblePiece1", "crowningTheEligiblePiece2", "crowningTheEligiblePiece3", "crowningTheEligiblePiece4"};
		//String[] moveArr = {"invalidSourceCoordinateForMoveOpponentsPiece1", "invalidSourceCoordinateForMoveOpponentsPiece2"};
 		//String[] moveArr = {"invalidSourceCoordinateForMoveUnplayableColor1"};
		//String[] moveArr = {"invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves1", "invalidDestinationCoordinateForMoveJumpedPieceIsNull1", "invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1"};
		//MoveArr for tests using the "usefulBoard1"
		//String[] moveArr = {"validJumpMove6", "validJumpMove7", "validJumpMove8", "invalidSourceCoordinateForMoveOpponentsPiece3", "invalidSourceCoordinateForMoveEmpty3", "invalidSourceCoordinateForMoveUnplayableColor2", "invalidSourceCoordinateForMoveOutsideBorders2", "invalidDestinationCoordinateForMoveOutsideBorders2", "invalidDestinationCoordinateForMoveUnallowedDirection2", "invalidDestinationCoordinateForMoveUnallowedDirection3", "invalidDestinationCoordinateForMoveOccupied2", "invalidDestinationCoordinateForMoveOccupied3", "invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves2", "invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves3", "invalidDestinationCoordinateForMoveUnplayableColor2", "invalidDestinationCoordinateForMoveTooFarAway2", "crowningTheEligiblePiece5"};                                                    
		
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

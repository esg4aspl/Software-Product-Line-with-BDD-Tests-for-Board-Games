package testing.scenariotesters;

public interface IScenarioTester {
	
	//Start of the Game
	public void theP1GameIsSetUp(String p1);
	public void thePlayersStartTheGame();
	
	//Set-up
	public void thePlayerWithTheP1ColoredPiecesIsGivenTheTurn(String p1);
	public void theGameIsPlayedUpToACertainPointFromFileP1(String p1);
	
	//Move Pick
	public void thePlayerPicksAValidSourceCoordinateThatHasAP1PieceInIt(String p1);
	public void thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(String p1);
	public void thePlayerPicksAnInvalidP1CoordinateBecauseP2(String p1, String p2);
	public void thePlayerPicksAnyDestinationCoordinate();
	
	//After Move
	public void thePieceIsMovedToTheDestinationCoordinate();
	public void theOpponentPieceInBetweenTheSourceAndDestinationCoordinatesAreRemovedFromTheBoard();
	public void theNextTurnIsGivenToTheP1Player(String p1);
	
	//After Invalid Move
	public void anErrorMessageIsShownSayingP1(String p1);
	public void thePlayerIsAskedForAnotherP1Coordinate(String p1);

	//Crowning
	public void thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead();
	public void thePieceIsP1ToACrownedPiece(String p1);
	
	//Decisive Game End
	public void theOpponentLosesTheGame();
	public void thePlayerWinsTheGame();
	
	//American Checkers Natural Game End
	public void onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard();
	public void thePlayerJumpsOverTheLastPieceOfTheOpponent();
	
	//American Checkers Implicit Game End
	public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces();
	
	//American Checkers Agreed Draw
	public void inThePreviousTurnTheOpponentHasOfferedToEndTheGameInADraw();
	public void thePlayerP1TheOffer(String p1);
	public void p1Happens(String p1);
	public void theGameIsEndedAsADraw();
	
	//American Checkers Implicit Draw
	public void theNumberOfConsecutiveIndecisiveMovesIs39();
	public void thePlayerMakesARegularMoveWithoutPromoting();
	
	//American Checkers One Piece Draw
	public void thePlayerHasOnlyOnePieceOnTheGameBoard();
	public void thePlayerJumpsOverOneOrMultiplePiecesLeavingTheOpponentWithOnlyOnePieceThatIsUnableToPerformAJumpMove();
	

	

	
}

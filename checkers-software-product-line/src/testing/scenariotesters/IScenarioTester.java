package testing.scenariotesters;

public interface IScenarioTester {
	
	//Start of the Game
	public void theP1GameIsSetUp(String p1);
	public void thePlayersStartTheGame();
	
	//Set-up
	public void thePlayerWithTheP1ColoredPiecesIsGivenTheTurn(String p1);
	public void theGameIsPlayedUpToACertainPointFromFileP1(String p1);
	
	//Move Pick
	public void thePlayerMakesAP1Move(String p1);
	
	//After Move
	public void thePieceIsMovedToTheDestinationCoordinate();
	public void theCapturedOpponentPieceIsRemovedFromTheBoard();
	public void theNextTurnIsGivenToTheP1Player(String p1);
	
	//After Invalid Move
	public void thePieceIsNotMoved();
	public void anErrorMessageIsShownSayingP1(String p1);
	public void thePlayerIsAskedForAnotherMove();

	//Crowning
	public void thePlayerMakesAMoveToOpponentsCrownheadWithAPawn();
	public void thePieceIsP1ToACrownedPiece(String p1);
	
	//Decisive Game End
	public void theOpponentLosesTheGame();
	public void thePlayerWinsTheGame();
	
	//American Checkers Natural Game End
	public void thePlayerJumpsOverTheLastPieceOfTheOpponent();
	
	//American Checkers Implicit Game End
	public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces();
	
	//American Checkers Agreed Draw
	public void thePlayerOffersToEndTheGameInDraw();
	public void inThePreviousTurnTheOpponentHasOfferedToEndTheGameInADraw();
	public void thePlayerP1TheOffer(String p1);
	public void p1Happens(String p1);
	public void theGameIsEndedAsADraw();
	
	//American Checkers Implicit Draw
	public void thePlayerMakesARegularMoveWithoutPromoting();
	
	//American Checkers One Piece Draw
	public void thePlayerJumpsOverOneOrMultiplePiecesLeavingTheOpponentWithOnlyOnePieceThatIsUnableToPerformAJumpMove();
	

	
}

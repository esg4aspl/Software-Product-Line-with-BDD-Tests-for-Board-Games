package testing.scenariotesters;

public interface IScenarioTester {
	
	public void theP1GameIsSetUp(String p1);
	public void thePlayersStartTheGame();
	public void thePlayerWithTheDarkcoloredPiecesIsGivenTheTurn();
	public void theGameIsPlayedUpToACertainPointFromFileP1(String p1);
	public void thePlayerPicksAValidSourceCoordinate();
	public void thePieceAtTheSourceCoordinateIsMovedToTheDestinationCoordinate();
	public void theNextTurnIsGivenToTheP1Player(String p1);
	public void thePlayerPicksAValidDestinationCoordinateThatIsP1SquaresAwayFromTheSourceCoordinate(String p1);
	public void theOpponentPieceInBetweenTheSourceAndDestinationCoordinatesAreRemovedFromTheBoard();
	public void thePlayerPicksAnInvalidP1CoordinateBecauseP2(String p1, String p2);
	public void thePlayerPicksAnyDestinationCoordinate();
	public void anErrorMessageIsShownSayingP1(String p1);
	public void thePlayerIsAskedForAnotherP1Coordinate(String p1);
	public void thereIsAPossibilityForThePlayerToMakeAJumpMove();
	public void thePlayerPicksAMoveThatIsNotOneOfTheAvailableJumpMoves();
	public void thePlayerHasPerformedOneOrMoreJumpMoves();
	public void thePlayerPicksAValidDestinationCoordinateWhereNoMoreJumpMovesWillBePossible();
	public void theMoveIsPerformed();
	public void thePlayerPicksAValidDestinationCoordinateWhereHisNormalPieceWillBecomeAKingPiece();
	public void thePieceTransformedToAKingPiece();
	public void thePlayerPicksAMoveWithANormalPieceAndADestinationCoordinateInOpponentsCrownhead();
	public void onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard();
	public void thePlayerJumpsOverTheLastPieceOfTheOpponent();
	public void theOpponentLosesTheGame();
	public void thePlayerWinsTheGame();
	public void noneOfThePlayersCanForceAWinOnTheOtherPlayer();
	public void onePlayerOffersTheOtherToEndTheGameInADraw();
	public void theOtherPlayerAcceptsTheOffer();
	public void theGameEndsInADraw();
	public void thePlayerMovesANormalPieceToANoncrownheadCoordinate();
	public void theNumberOfMovesWithoutUpgradeIsIncrementedBy1();
	public void theGameIsEndedAsADrawIfTheNumberOfMovesWithoutUpgradeIs40();
	public void thePlayerHasOnlyOnePieceOnTheGameBoard();
	public void thePlayerJumpsOverOneOrMultiplePiecesOfTheOpponent();
	public void theGameIsEndedADrawIfTheOpponentStillHasOnePieceOnTheGameBoard();
	public void thePlayerMovesAPieceWithoutUndertakingAnOpponentPiece();
	public void theNumberOfMovesWithoutUndertakeIsIncrementedBy1();
	public void theGameIsEndedAsInDrawIfTheNumberOfMovesWithoutUndertakeIs40();
	public void thePlayerMakesAMoveLeavingNoValidDestinationCoordinatesForAnyOfTheOpponentsPieces();
	public void thePlayerPicksAValidSourceCoordinateThatHasAPawnPieceInIt();
	public void thePlayerPicksAValidDestinationCoordinateInOpponentsCrownhead();
	public void thePieceAtTheSourceCoordinateBecomesAKingPiece();
	
}

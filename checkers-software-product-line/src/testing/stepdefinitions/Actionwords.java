package testing.stepdefinitions;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import base.AmericanGameConfiguration;
import core.AbstractReferee;
import core.IGameConfiguration;
import cucumber.api.PendingException;

public class Actionwords {

	IGameConfiguration gameConfiguration;
	AbstractReferee referee;

    public void theP1GameIsSetUp(String p1) {
    	if (p1.equals("American Checkers")) {
    		gameConfiguration = new AmericanGameConfiguration();
    		referee = new checkersamerican.Referee(gameConfiguration);
    	}
    	
    }

    public void thePlayersStartTheGame() {
		referee.setup();
    }

    public void thePlayerWithTheDarkcoloredPiecesIsGivenTheTurn() {
		assertEquals(referee.getCurrentPlayer().getColor(), Color.BLACK);
    }

    public void thePlayerHasTheCurrentTurn() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAP1PieceThatIsHisOwnToMove(String p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theEmptyAdjacentSquaresInP1ArePlayable(String p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theEmptySquaresImmediatelyAfterTheP1AdjacentSquareInP2ArePlayable(String p1, String p2) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayableSquaresAreVisuallyHighlighted() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectedAPieceToMove() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thereArePlayableSquaresOnTheGameBoard() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAPlayableSquareThatIsP1StepsAwayFromTheOriginalSquare(int p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePieceIsMovedToThatSquare() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theNextTurnIsGivenToTheP1Player(String p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theOpponentPieceInBetweenTargetSquareAndOriginalSquareIsRemovedFromTheGameBoard() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theNumberOfRemovedOpponentPiecesInThisMoveIsOne() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theNumberOfRemovedPlayerPiecesInThisMoveIsZero() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thereArePlayableSquaresThatAreTwoStepsAwayOnTheGameBoard() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAPlayableSquareThatIsNotTwoStepsAway() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePieceIsUnselected() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerIsShownAnErrorMessage() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerHasPreviouslyMadeAMoveInTheCurrentTurn() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAPieceThatIsDifferentThanTheLastPieceHeMoved() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerMovesThePieceToASquareInTheOpponentsCrownhead() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theSelectedPieceBecomesAKingPiece() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void onlyOnePieceOfTheOpponentIsPresentAtTheGameBoard() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerUndertakesTheLastPieceOfTheOpponent() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theOpponentLosesTheGame() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerWinsTheGame() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thatNoneOfThePlayersCanForceAWinOnTheOtherPlayer() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void onePlayerOffersTheOtherToEndTheGameInADraw() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theOtherPlayerAcceptsTheOffer() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theGameEndsInADraw() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerMovesARegularPieceToANoncrownheadSquare() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theNumberOfMovesWithoutUpgradeIsIncrementedBy1() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theGameIsEndedAsInDrawIfTheNumberOfMovesWithoutUpgradeIs40() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerHasOnlyOnePieceOnTheGameBoard() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerUndertakesOneOrMultiplePiecesOfTheOpponent() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theGameIsEndedInDrawIfTheOpponentStillHasOnePieceOnTheGameBoard() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerMovesAPieceWithoutUndertakingAnOpponentPiece() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theNumberOfMovesWithoutUndertakeIsIncrementedBy1() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theGameIsEndedAsInDrawIfTheNumberOfMovesWithoutUndertakeIs40() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerMakesAMovesOrMultipleMovesLeavingNoPlayableSquaresForAnyOfTheOpponentsPieces() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerWithTheLightcoloredPiecesIsGivenTheTurn() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAPieceToMove() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allOtherSquaresThatAreNotOccupiedByOneOfPlayersPiecesOrOpponentsKingAreP1(String p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAPreviouslyP1PawnToMove(String p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allTheSquaresInForwardDirectionUpToAndIncludingTheLastEmptySquareAndThatAreAtMaxP1StepsAwayArePlayable(int p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allTheAdjacentForwardDiagonalSquaresOccupiedByAnOpponentPieceArePlayable() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAP1ToMove(String p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allTheSquaresUpToAndIncludingTheLastP1SquareInP2DirectionsArePlayable(String p1, String p2) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsTheKingToMove() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allTheP1AdjacentSquaresArePlayable(String p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAKnightToMove() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allTheP1SquaresThatAreThreeStepsAwayInXaxisAndOneSquareAwayInYaxisArePlayable(String p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allTheP1SquaresThatAreThreeStepsAwayInYaxisAndOneSquareAwayInXaxisArePlayable(String p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectedAPieceThatIsHisOwnToMove() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAnEmptyPlayableSquare() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theSelectedPieceIsMovedToThatSquare() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAnOccupiedPlayableSquare() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePieceInTheTargetSquareIsRemovedFromTheGameBoard() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void oneOfThePlayersPiecesCanTheoreticallyMoveToTheSquareThatTheOpponentsKingCurrentlyStands() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void playerCanMoveOneOfHisPiecesToAnySquareThatIsAdjacentToTheSquareThatOpponentsKingCurrentlyStands() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thereAreNoMovesThatTheOpponentCanMakeToChangeTheGivenSituation() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void aRandomPlayerIsGivenTheTurn() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAPieceTheMove() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allTheAdjacentEmptyPlacesInAnyDirectionArePlayable() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theEmptyPlacesImmediatelyAfterTheAdjacentP1PlaceInAnyDirectionArePlayable(String p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thereArePlayablePlacesOnTheGameBoard() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAPlayablePlaceThatIsP1StepsAwayFromTheOriginalPlace(int p1) {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePieceIsMovedToThatPlace() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerHasMovedAPieceInTheCurrentTurn() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thereIsOnlyOneEmptyPlaceInTheOppositeTriangle() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerMovesAPieceToThatPlace() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerSelectsAnUncrownedPieceToMove() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allTheAdjacentEmptySquaresInForwardAndHorizontalDirectionsArePlayable() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void theEmptySquaresThatAreInTheSameDirectionAndImmediatelyAfterTheAdjacentSquareInHorizontalAndForwardDirectionsThatIsOccupiedByOpponentsPieceIsRegisteredAsPlayable() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allEmptySquaresUpToTheFirstOccupiedSquareInVerticalAndHorizontalDirectionsArePlayable() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void allEmptySquaresAfterTheFirstOccupiedSquareAndUpToTheNextOccupiedSquareInVerticalAndHorizontalDirectionsArePlayable() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerTriesToMoveThePieceInTheDirectionThatIsOppositeToTheLastDirectionHeMoved() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePieceDoesNotMoveToTheTargetSquare() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void atLeastOneKingPieceOfThePlayerIsPresentOnTheGameBoard() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

    public void thePlayerLeavesOnlyOnePieceOfTheOpponentAndItIsUncrowned() {
		//TO-DO: Implement this to pass tests.
		throw new PendingException();

    }

	
}
package testing.scenariotesters;

import java.util.ArrayList;
import java.util.List;

import core.AbstractReferee;
import core.IGameConfiguration;
import core.IMoveCoordinate;
import core.IPlayer;
import testing.helpers.IiniReader;

public abstract class AbstractTesterReferee extends AbstractReferee {
	protected IMoveCoordinate playerMove;
	protected IiniReader reader;
	protected String setUpName;
	protected List<String> informers;
	protected String endTestStatus;
	protected boolean playerWasGoingToMakeAnotherMove;
	protected IPlayer winner;
	protected IPlayer loser;
	protected boolean isDraw;
	protected boolean gameEnded;
	protected int noPromoteMoveCount, noCaptureMoveCount;
	protected boolean drawOffered, drawAccepted;
	
	public abstract void defaultSetup();
	public abstract void setIni(String setUpName);
	
	public IMoveCoordinate readPlayerMove() {
		playerMove = reader.getPlayerMove();
		return playerMove;
	}

	public AbstractTesterReferee(IGameConfiguration gameConfiguration) {
		super(gameConfiguration);
		informers = new ArrayList<String>();
		endTestStatus = "Test running...";
		playerWasGoingToMakeAnotherMove = false;
		winner = null; loser = null;
		isDraw = false; gameEnded = false;
		noPromoteMoveCount = 0; noCaptureMoveCount = 0;
		drawOffered = false; drawAccepted = false;
	}
	
	protected void endTest(String status) {
		endTestStatus = status;
		System.out.println("\n\n-----------------------------------TEST RESULTS--------------------------------------------------------------------------------------------");
		System.out.println("Test: " + setUpName);
		System.out.println("Status: " + endTestStatus);
		System.out.println("Informers: " + informers.toString());
		System.out.println("Was player going to make another move?: " + this.playerWasGoingToMakeAnotherMove);
		System.out.println("End of the game? " + this.gameEnded);
		if (gameEnded)
			System.out.println("isDraw?: " + this.isDraw + ", Winner: " + winner + ", Loser: " + loser);
		System.out.println("Board: ");
		view.drawBoardView();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------\n");
	}
	
	@Override
	public void printMessage(String message) {
		super.printMessage(message);
		informers.add(message);
	}

	
	public IMoveCoordinate getPlayerMove() {
		return playerMove;
	}

	public IiniReader getReader() {
		return reader;
	}

	public String getSetUpName() {
		return setUpName;
	}

	public List<String> getInformers() {
		return informers;
	}

	public String getEndTestStatus() {
		return endTestStatus;
	}

	public boolean isPlayerWasGoingToMakeAnotherMove() {
		return playerWasGoingToMakeAnotherMove;
	}

	public IPlayer getWinner() {
		return winner;
	}

	public IPlayer getLoser() {
		return loser;
	}

	public boolean isDraw() {
		return isDraw;
	}

	public boolean isGameEnded() {
		return gameEnded;
	}

	public int getNoPromoteMoveCount() {
		return noPromoteMoveCount;
	}

	public int getNoCaptureMoveCount() {
		return noCaptureMoveCount;
	}

	public boolean isDrawOffered() {
		return drawOffered;
	}

	public boolean isDrawAccepted() {
		return drawAccepted;
	}

	public void setSetUpName(String setUpName) {
		this.setUpName = setUpName;
	}

	

}

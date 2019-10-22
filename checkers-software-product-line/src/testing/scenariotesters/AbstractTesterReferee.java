package testing.scenariotesters;

import core.AbstractReferee;
import core.ICoordinate;
import core.IGameConfiguration;
import core.IMoveCoordinate;
import core.IPlayer;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.SourceCoordinateValidity;

public abstract class AbstractTesterReferee extends AbstractReferee {
	
	protected AbstractTestInfo info;
	
	public abstract void setup(String fileName);
	public abstract SourceCoordinateValidity checkSourceCoordinate(IPlayer player, ICoordinate sourceCoordinate);
	public abstract DestinationCoordinateValidity checkDestinationCoordinate(IPlayer player, ICoordinate sourceCoordinate, ICoordinate destinationCoordinate);

	public AbstractTesterReferee(IGameConfiguration gameConfiguration) {
		super(gameConfiguration);
	}

	protected IMoveCoordinate getNextMove() {
		return info.getNextMove();
	}

	protected void start() {
		view.printMessage("Testing: " + info.getReader().getSectionName());
		view.printMessage("Player turn: " + info.getReader().getCurrentTurnPlayerIconColor());
		view.printMessage("Player move: " + info.getPlayerMove().toString());
		view.printMessage("No promote count: " + info.getNoPromoteMoveCount());
		view.printMessage("No capture count: " + info.getNoCaptureMoveCount());
//		view.printMessage("B: Pawn of 'black' player");
//		view.printMessage("A: King of 'black' player");
//		view.printMessage("W: Pawn of 'white' player");
//		view.printMessage("Z: King of 'white' player");
	}
	
	protected void abort() {
		info.abort();
		System.out.println("\n\nTEST ABORTED!!!!!");
		System.out.println("Test: " + info.getReader().getSectionName());
		System.out.println("Informers: " + info.getInformers().toString());
		System.out.println("Final Informers: " + info.getFinalInformers().toString());
		System.out.println("Was player going to make another move?: " + info.isPlayerWasGoingToMakeAnotherMove());
		System.out.println("End of the game?: " + info.isGameEnded());
		if (info.isGameEnded())
			System.out.println("isDraw?: " + info.isDraw() + ", Winner: " + info.getWinner() + ", Loser: " + info.getLoser());
		System.out.println("Board: ");
		view.drawBoardView();
	}
	
	protected void end() {
		info.end();
		System.out.println("\n\nTEST RESULTS");
		System.out.println("Test: " + info.getReader().getSectionName());
		System.out.println("Informers: " + info.getInformers().toString());
		System.out.println("Final Informers: " + info.getFinalInformers().toString());
		System.out.println("Was player going to make another move?: " + info.isPlayerWasGoingToMakeAnotherMove());
		System.out.println("End of the game? " + info.isGameEnded());
		if (info.isGameEnded())
			System.out.println("isDraw?: " + info.isDraw() + ", Winner: " + info.getWinner() + ", Loser: " + info.getLoser());
		System.out.println("Board: ");
		view.drawBoardView();
	}
	
	public IPlayer getNextPlayer() {
		int nextPlayerID = currentPlayerID + 1;
		if (nextPlayerID >= numberOfPlayers)
			nextPlayerID = 0;
		return playerList.getPlayer(nextPlayerID);
	}


	
	//Overridden
	@Override
	public void printMessage(String message) {
		super.printMessage(message);
		info.register(message);
	}

	//Getters
	public AbstractTestInfo getInfo() {
		return info;
	}
	
	

}

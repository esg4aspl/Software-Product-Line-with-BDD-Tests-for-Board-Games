package testing.scenariotesters.checkerschinese;

import core.IGameConfiguration;

public class ChineseTestGameConfiguration implements IGameConfiguration {
	
	private int numberOfPlayers;
	
	public ChineseTestGameConfiguration(int numberOfPlayers) {
		super();
		this.numberOfPlayers = numberOfPlayers;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	public int getNumberOfPiecesPerPlayer() {
		return 10;
	}

	@Override
	public boolean getAutomaticGameStatus() {
		// TODO Auto-generated method stub
		return false;
	}

}

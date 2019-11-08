package testing.scenariotesters.checkerschinese;

import core.AbstractReferee;
import core.ICoordinate;
import core.IGameConfiguration;
import core.IPlayer;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.SourceCoordinateValidity;
import testing.scenariotesters.AbstractTesterReferee;

public class ChineseTesterReferee extends AbstractTesterReferee {

	public ChineseTesterReferee(IGameConfiguration gameConfiguration) {
		super(gameConfiguration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setup(String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public SourceCoordinateValidity checkSourceCoordinate(IPlayer player, ICoordinate sourceCoordinate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DestinationCoordinateValidity checkDestinationCoordinate(IPlayer player, ICoordinate sourceCoordinate,
			ICoordinate destinationCoordinate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void conductGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public IPlayer announceWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		IGameConfiguration gameConfiguration = new ChineseTestGameConfiguration(2);
		AbstractReferee referee = new ChineseRefereeCopy(gameConfiguration);
		referee.setup();
		referee.conductGame();
	}
	
	private static class ChineseTestGameConfiguration implements IGameConfiguration {
		
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
	
}

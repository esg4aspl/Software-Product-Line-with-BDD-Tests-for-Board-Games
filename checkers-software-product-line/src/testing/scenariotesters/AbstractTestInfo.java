package testing.scenariotesters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.IMoveCoordinate;
import core.IPlayer;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.IniReaderInterface;
import testing.helpers.IniReader;
import testing.helpers.SourceCoordinateValidity;
import testing.helpers.TestResult;

public abstract class AbstractTestInfo {

	protected boolean testEnded; //Test ends when the game ends or the player changes.
	protected boolean testAborted; //Test abortion happens when the game expects another move from the player but there is no such move specified. It is not necessarily a bad thing.
	protected AbstractTesterReferee referee;
	protected IniReaderInterface reader;
	protected IMoveCoordinate playerMove;
	protected IMoveCoordinate expectedMove;
	protected List<String> informers;
	protected IPlayer winner, loser;
	protected boolean isDraw, gameEnded;
	protected boolean playerWasGoingToMakeAnotherMove;
	protected int noPromoteMoveCount, noCaptureMoveCount;
	protected boolean drawOffered, drawAccepted;
	protected List<IMoveCoordinate> priorMoveList;
	protected Iterator<IMoveCoordinate> priorMoveIterator;
	protected boolean endOfMoves;
	protected SourceCoordinateValidity sourceCoordinateValidity;
	protected DestinationCoordinateValidity destinationCoordinateValidity;
	
	public AbstractTestInfo(AbstractTesterReferee referee, String file_path, String file_name) {
		testEnded = false;
		testAborted = false;
		this.referee = referee;
		reader = new IniReader(file_path, file_name);
		playerMove = reader.getPlayerMove();
		informers = new ArrayList<String>();
		
		reset();
		
		noPromoteMoveCount = getMoveCount("noPromote"); 
		noCaptureMoveCount = getMoveCount("noCapture");
		priorMoveList = reader.getPriorMoveSequence();
		priorMoveIterator = priorMoveList.iterator();
		expectedMove = reader.getExpectedMove();
	}
	
	public abstract void setupPlayerMoveInfo();
	
	public IMoveCoordinate getNextMove() {
		if (priorMoveIterator.hasNext())
			return priorMoveIterator.next();

		if (!endOfMoves) {
			setupPlayerMoveInfo();
			endOfMoves = true;
			return playerMove;
		}
		
		return null;
	}
	
	public void register(TestResult res) {
		informers.add(res.getMessage());
		reset();
		if (res == TestResult.GAME_END_AS_DRAW) {
			 gameEnded = true;
			 isDraw = true;
		} else if (res == TestResult.GAME_END_AS_WIN) {
			gameEnded = true; 
			winner = referee.getCurrentPlayer();
			loser = referee.getNextPlayer();
		} else if (res == TestResult.ANOTHER_SOURCE_INVALID || res == TestResult.ANOTHER_DESTINATION_INVALID || res == TestResult.ANOTHER_MOVE_JUMP_POSSIBILITY) {
			playerWasGoingToMakeAnotherMove = true;
		}
	}
	
	public void register(String message) {
		informers.add(message);
	}
	
	
	
	//Protected Methods
	
	protected int getMoveCount(String extraCandidate) {
		if (!reader.hasExtras())
			return 0;
		
		for (String e : reader.getExtras()) {
			String[] parts = e.split("-");
			if (parts.length != 2)
				continue;
			if (parts[0].equals(extraCandidate))
				return Integer.parseInt(parts[1]);
		}
		
		return 0;
	}
	
	protected void reset() {
		winner = null; loser = null;
		isDraw = false; gameEnded = false;
		playerWasGoingToMakeAnotherMove = false;
		drawOffered = false; drawAccepted = false;
	}
	
	public void end() {
		if (!testAborted)
			testEnded = true;
	}
	
	public void abort() {
		testAborted = true;
	}
	
	
	//ToString
	
	
	//Getters

	public IniReaderInterface getReader() {
		return reader;
	}

	public IMoveCoordinate getPlayerMove() {
		return playerMove;
	}

	public List<String> getInformers() {
		return informers;
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

	public boolean isPlayerWasGoingToMakeAnotherMove() {
		return playerWasGoingToMakeAnotherMove;
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

	public List<IMoveCoordinate> getPriorMoveList() {
		return priorMoveList;
	}

	public Iterator<IMoveCoordinate> getPriorMoveIterator() {
		return priorMoveIterator;
	}

	public IMoveCoordinate getExpectedMove() {
		return expectedMove;
	}

	public SourceCoordinateValidity getSourceCoordinateValidity() {
		return sourceCoordinateValidity;
	}

	public DestinationCoordinateValidity getDestinationCoordinateValidity() {
		return destinationCoordinateValidity;
	}

	
	public boolean isTestEnded() {
		return testEnded;
	}

	public boolean isTestAborted() {
		return testAborted;
	}

	
	
	public boolean isEndOfMoves() {
		return endOfMoves;
	}
	
	
	
	
	
}

package testing.scenariotesters.checkerschildren;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import base.AmericanCheckersBoard;
import base.AmericanCheckersBoardConsoleView;
import base.AmericanGameConfiguration;
import base.Pawn;
import base.PawnMoveConstraints;
import base.PawnMovePossibilities;
import base.Player;
import base.PlayerList;
import checkersamerican.AmericanStartCoordinates;
import core.AbstractPiece;
import core.Direction;
import core.ICoordinate;
import core.IGameConfiguration;
import core.IMoveCoordinate;
import core.IPieceMoveConstraints;
import core.IPieceMovePossibilities;
import core.IPlayer;
import core.MoveOpResult;
import rules.RuleDestinationCoordinateMustBeValidForCurrentPiece;
import rules.RuleEndOfGameChildren;
import rules.RuleIfJumpMoveThenJumpedPieceMustBeOpponentPiece;
import rules.RuleMoveMustMatchPieceMoveConstraints;
import rules.RulePieceAtSourceCoordinateMustBelongToCurrentPlayer;
import rules.RuleThereMustBePieceAtSourceCoordinate;
import rules.RuleThereMustNotBePieceAtDestinationCoordinate;
import testing.helpers.ICoordinatePieceDuo;
import testing.helpers.IniReader;
import testing.scenariotesters.AbstractTesterReferee;
import testing.scenariotesters.checkersamerican.AmericanTesterReferee;

public class ChildrenTesterReferee extends AbstractTesterReferee {

	protected AmericanCheckersBoardConsoleView consoleView;
	
	public ChildrenTesterReferee(IGameConfiguration gameConfiguration) {
		super(gameConfiguration);
		informers = new ArrayList<String>();
		gameEnded = false;
		endTestStatus = "Test running...";
	}

	@Override
	public void defaultSetup() {
		setupPlayers();
		setupBoardMVC();
		setupPiecesOnBoard();
	}
	
	public void setIni(String setUpName) {
		reader = new IniReader("src/testing/scenariotesters/checkerschildren/ChildrenCheckers.ini", setUpName);
	}
	
	@Override
	public void setup() {
		setupPlayers();
		setupBoardMVC();
		view = consoleView;
		setIni(setUpName);
		setupPiecesOnBoard(reader.getCoordinatePieceDuos());
		String currentTurnPlayerIconColor = reader.getCurrentTurnPlayerIconColor();
		if (isReversed())
			currentTurnPlayerIconColor = currentTurnPlayerIconColor.equals("black") ? "white" : "black";
		
		if (currentTurnPlayerIconColor.equals("black")) {
			currentPlayerID = 0;
			currentPlayer = playerList.getPlayer(0);
		} else {
			currentPlayerID = 1;
			currentPlayer = playerList.getPlayer(1);
		}
		noPromoteMoveCount = getMoveCount("noPromote");
		noCaptureMoveCount = getMoveCount("noCapture");
	}

	@Override
	public IPlayer announceWinner() {
		return playerList.getPlayer(currentPlayerID);
	}
	
	
	//GAMEPLAY METHODS
	
	@Override
	public void conductGame() {		
		boolean endOfGame = false;

		if(!endOfGame) {
			view.printMessage("Game begins ...");
			view.printMessage("Reversed: " + isReversed());
			view.printMessage("Testing: " + reader.getSectionName());
			view.printMessage("Player turn: " + reader.getCurrentTurnPlayerIconColor());
			view.printMessage("Player move: " + playerMove.toString());
			view.printMessage("No promote count: " + noPromoteMoveCount);
			view.printMessage("No capture count: " + noCaptureMoveCount);
			view.printMessage("B: Pawn of 'black' player");
			view.printMessage("A: King of 'black' player");
			view.printMessage("W: Pawn of 'white' player");
			view.printMessage("Z: King of 'white' player");
			consoleView.drawBoardView();
		}
		
		while (!endOfGame) {
			currentMoveCoordinate = playerMove;
			while (!conductMove()) {
				playerWasGoingToMakeAnotherMove = true;
				printMessage("Player will be asked for another source coordinate (previous move was invalid)...");
				endTest("Test ended with an invalid player move.");
				return;
			}
			consoleView.drawBoardView();
			endOfGame = isSatisfied(new RuleEndOfGameChildren(), this);
			System.out.println("End Of Game? " + endOfGame);
			if (endOfGame) {
				gameEnded = true;
				break;
			}
			
			if (!playerWasGoingToMakeAnotherMove) {
				currentPlayerID++;
				if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
				currentPlayer = getPlayerbyID(currentPlayerID);
			}
			
			endTest("Test ended with a valid player move.");
			return;
		} 
		winner = currentPlayer;
		loser = getNextPlayer();
		printMessage("WINNER " + announceWinner());
		endTest("Test ended with a valid player move. The game ended.");
		return;
	}

	protected boolean conductMove() {
		ICoordinate sourceCoordinate = currentMoveCoordinate.getSourceCoordinate();
		ICoordinate destinationCoordinate = currentMoveCoordinate.getDestinationCoordinate();
		AbstractPiece piece = coordinatePieceMap.getPieceAtCoordinate(sourceCoordinate);
		if (!checkMove()) return false;
		List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);
		coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
		MoveOpResult moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
		piece = becomeAndOrPutOperation(piece, destinationCoordinate);
		System.out.println("CurrentPlayerTurnAgain? " + moveOpResult.isCurrentPlayerTurnAgain());
		if (moveOpResult.isCurrentPlayerTurnAgain() && !automaticGameOn) 
			conductCurrentPlayerTurnAgain(moveOpResult, piece);
		return true;
	}

	protected boolean conductCurrentPlayerTurnAgain(MoveOpResult moveOpResult, AbstractPiece piece) {
		while (moveOpResult.isCurrentPlayerTurnAgain()) {
			List<ICoordinate> secondJumpList = board.getCBO().findAllowedContinousJumpList(piece);
			board.getCBO().printCoordinateList(secondJumpList, "Second Jump List");
			if (secondJumpList.size() == 0) {
				moveOpResult = new MoveOpResult(false, false);
				printMessage("Player will NOT be asked for another destination coordinate (previous move was a jump move) because there are no more possibilities for a jump move.");
				break;
			}
			playerWasGoingToMakeAnotherMove = true;
			printMessage("Player will be asked for another destination coordinate (previous move was a jump move) (there are still possibilities for a jump move).");
			break;
		}
		return true;
	}

	protected boolean checkMove() {
		return isSatisfied(new RuleThereMustBePieceAtSourceCoordinate(), this)
				&& isSatisfied(new RuleThereMustNotBePieceAtDestinationCoordinate(), this)
				&& isSatisfied(new RulePieceAtSourceCoordinateMustBelongToCurrentPlayer(), this)
				&& isSatisfied(new RuleDestinationCoordinateMustBeValidForCurrentPiece(), this)
				&& isSatisfied(new RuleMoveMustMatchPieceMoveConstraints(), this)
				&& isSatisfied(new RuleIfJumpMoveThenJumpedPieceMustBeOpponentPiece(), this);
	}

	protected MoveOpResult moveInterimOperation(AbstractPiece piece, IMoveCoordinate moveCoordinate, List<ICoordinate> path) {
		IPlayer player = piece.getPlayer();
		if (board.getMBO().isJumpMove(piece, moveCoordinate)) {
			System.out.println("Jump Move");
			ICoordinate pathCoordinate = path.get(1);
			System.out.println("Path Coordinate " + pathCoordinate);
			AbstractPiece pieceAtPath = coordinatePieceMap.getPieceAtCoordinate(pathCoordinate);
			if (!pieceAtPath.getPlayer().equals(player)) {
				// capture piece at path
				coordinatePieceMap.capturePieceAtCoordinate(pieceAtPath, pathCoordinate);
				pieceAtPath.getPlayer().removePiece(pieceAtPath);
				return new MoveOpResult(true, true); // jumped over opponent team
			}
		}
		return new MoveOpResult(true, false);
	}

	protected AbstractPiece becomeAndOrPutOperation(AbstractPiece piece, ICoordinate destinationCoordinate) {
		if ((piece.getGoalDirection() == Direction.N && destinationCoordinate.getYCoordinate() == 7)
				|| (piece.getGoalDirection() == Direction.S && destinationCoordinate.getYCoordinate() == 0)) {
			IPlayer player = piece.getPlayer();
			System.out.println(player + " reached last row");
		}
		coordinatePieceMap.putPieceToCoordinate(piece, destinationCoordinate);
		return piece;
	}
		
	
	
	//PRIVATE METHODS
	
	private void setupPlayers() {
		numberOfPlayers = gameConfiguration.getNumberOfPlayers();
		numberOfPiecesPerPlayer = gameConfiguration.getNumberOfPiecesPerPlayer();
		playerList = new PlayerList();
		IPlayer player0 = new Player(0, Color.BLACK);
		playerList.addPlayer(player0);
		IPlayer player1 = new Player(1, Color.WHITE);
		playerList.addPlayer(player1);
		currentPlayerID = 0;
		currentPlayer = player0;
	}

	private void setupBoardMVC() {
		board = new AmericanCheckersBoard();
		coordinatePieceMap = board.getCoordinatePieceMap();
		consoleView = new AmericanCheckersBoardConsoleView(this);
	}

	private void setupPiecesOnBoard() {
		// create pieces for players and put them on board
		IPlayer player;
		AbstractPiece men;
		AmericanStartCoordinates startCoordinates = new AmericanStartCoordinates();
		IPieceMovePossibilities menMovePossibilities = new PawnMovePossibilities();
		IPieceMoveConstraints menMoveConstraints =  new PawnMoveConstraints();

		for (int i = 0; i < numberOfPlayers; i++) {
			player = playerList.getPlayer(i);
			String icon;
			Direction direction;
			if (i == 0) {
				icon = "B";
				direction = Direction.N;
			}
			else {
				icon = "W";
				direction = Direction.S;
			}
			for (int j = 0; j < numberOfPiecesPerPlayer; j++) {
				men = new Pawn(j, icon, player, direction, menMovePossibilities, menMoveConstraints);
				player.addPiece(men);
				coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());
			}
		}
		
		// coordinatePieceMap.printPieceMap();
		System.out.println(playerList.getPlayerStatus());
	}
	
	private void setupPiecesOnBoard(List<ICoordinatePieceDuo> coordinatePieceDuos) {
		AbstractPiece men;
		IPlayer player;
		String icon;
		Direction direction;
		int playerId;
		int counter = 0;
		for (ICoordinatePieceDuo coordinatePieceDuo : coordinatePieceDuos) {
			//TODO: Check if the coordinate is empty
			if (!board.isPlayableCoordinate(coordinatePieceDuo.getCoordinate())) {
				System.out.println("A piece can not stand there: " + coordinatePieceDuo.getCoordinate().toString());
				System.exit(0);
			}
			String iconName = coordinatePieceDuo.getIconColor();
			if (isReversed())
				iconName = iconName.equals("black") ? "white" : "black";
			
			if (iconName.equals("black")) {
				playerId = 0;
				player = playerList.getPlayer(playerId);
				icon = "B";
				direction = Direction.N;
			} else {
				playerId = 1;
				player = playerList.getPlayer(playerId);
				icon = "W";
				direction = Direction.S;
			}
			IPieceMovePossibilities menMovePossibilities;
			IPieceMoveConstraints menMoveConstraints;
			//First create the piece as a pawn regardless of its real identity:
			menMovePossibilities = new PawnMovePossibilities();
			menMoveConstraints =  new PawnMoveConstraints();
			men = new Pawn(1000+counter, icon, player, direction, menMovePossibilities, menMoveConstraints);
			player.addPiece(men);
			
			coordinatePieceMap.putPieceToCoordinate(men, coordinatePieceDuo.getCoordinate());
			counter++;
		}
		
		
	}
	
	public IPlayer getNextPlayer() {
		int nextPlayerID = currentPlayerID+1;
		if (nextPlayerID >= numberOfPlayers) nextPlayerID = 0;
		return playerList.getPlayer(nextPlayerID);
	}

	private int getMoveCount(String extraCandidate) {
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
	
	private boolean isReversed() {
		if (!reader.hasExtras())
			return false;
		
		for (String e : reader.getExtras()) {
			if (e.equals("reversed"))
				return true;
		}
		
		return false;
	}

	
	public static void main(String[] args) {
		String[] moveArr =  {"endOfTheGame1", "endOfTheGame2"};
		
		for (String moveID : moveArr) {
			System.out.println("\n\n\nTESTING: " + moveID);
			AbstractTesterReferee referee = new ChildrenTesterReferee(new AmericanGameConfiguration());
			referee.setSetUpName(moveID);
			referee.setup();
			referee.readPlayerMove();
			referee.conductGame();
		}
	}
	
}

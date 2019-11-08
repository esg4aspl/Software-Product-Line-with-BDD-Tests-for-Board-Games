package testing.scenariotesters.checkerschinese;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import base.ChineseCheckersBoard;
import base.ChineseCheckersBoardConsoleView;
import base.Player;
import base.PlayerList;
import base.StartCoordinates;
import checkerschinese.ChinesePawn;
import checkerschinese.ChinesePawnMoveConstraints;
import checkerschinese.ChinesePawnMovePossibilities;
import checkerschinese.Referee;
import checkerschinese.StartCoordinateFactory;
import core.AbstractPiece;
import core.AbstractReferee;
import core.Direction;
import core.ICoordinate;
import core.IGameConfiguration;
import core.IMoveCoordinate;
import core.IPieceMoveConstraints;
import core.IPieceMovePossibilities;
import core.IPlayer;
import core.IPlayerList;
import core.MoveOpResult;
import rules.RuleDestinationCoordinateMustBeValidForCurrentPiece;
import rules.RuleEndOfGamePiecesOfPlayerOnFinishCoordinates;
import rules.RuleIfJumpMoveThenJumpedPieceMustBe;
import rules.RuleMoveMustMatchPieceMoveConstraints;
import rules.RulePieceAtSourceCoordinateMustBelongToCurrentPlayer;
import rules.RuleThereMustBePieceAtSourceCoordinate;
import rules.RuleThereMustNotBePieceAtDestinationCoordinate;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.ICoordinatePieceDuo;
import testing.helpers.SourceCoordinateValidity;
import testing.helpers.TestResult;
import testing.scenariotesters.AbstractTesterReferee;
import testing.scenariotesters.checkersamerican.AmericanCheckersTestInfo;

public class ChineseTesterReferee extends AbstractTesterReferee {
	protected ChineseCheckersBoardConsoleView consoleView;
	private String[] icons; 
	private Direction[] directions;

	public ChineseTesterReferee(IGameConfiguration gameConfiguration) {
		super(gameConfiguration);
	}

	
	//TESTER-ONLY METHODS
	
	@Override
	public void setup(String fileName) {
		info = new ChineseCheckersTestInfo(this, "src/testing/scenariotesters/checkerschinese/ChineseCheckers.ini", fileName);
		setupPlayers();
		setupTestBoardMVC();
		view = consoleView;
		setupPiecesOnBoard(info.getReader().getCoordinatePieceDuos());
		switch (info.getReader().getCurrentTurnPlayerIconColor()) {
			case "tag": currentPlayer = playerList.getPlayer(0); currentPlayerID = 0; break;
			case "dol": currentPlayer = playerList.getPlayer(1); currentPlayerID = 0; break;
			case "per": currentPlayer = playerList.getPlayer(2); currentPlayerID = 0; break;
			case "que": currentPlayer = playerList.getPlayer(3); currentPlayerID = 0; break;
			case "eq": currentPlayer = playerList.getPlayer(4); currentPlayerID = 0; break;
			case "at": currentPlayer = playerList.getPlayer(5); currentPlayerID = 0; break;
			default: System.out.println("No such player."); System.exit(0);
		}
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

	private void setupPiecesOnBoard(List<ICoordinatePieceDuo> coordinatePieceDuos) {
		icons = new String[]{"#","$","%","?","=","@"}; //tag, dol, per, que, eq, at
		directions = createDirections(numberOfPlayers);
		StartCoordinateFactory startCoordinateFactory = new StartCoordinateFactory();
		IPlayer player = null;
		String icon = null;
		Direction direction = null;
		int pieceId = 0;
		
		AbstractPiece men;
		StartCoordinates startCoordinates = startCoordinateFactory.getStartCoordinates(numberOfPlayers,numberOfPiecesPerPlayer);
		IPieceMovePossibilities menMovePossibilities = new ChinesePawnMovePossibilities();
		IPieceMoveConstraints menMoveConstraints =  new ChinesePawnMoveConstraints();

		for (ICoordinatePieceDuo coordinatePieceDuo : coordinatePieceDuos) {
			if (!board.isPlayableCoordinate(coordinatePieceDuo.getCoordinate())) {
				System.out.println("A piece can not stand there: " + coordinatePieceDuo.getCoordinate().toString());
				System.exit(0);
			}
			String iconName = coordinatePieceDuo.getIconColor();
			switch (iconName) {
				case "tag": player = playerList.getPlayer(0); icon = icons[0]; direction = directions[0]; pieceId = 1000 + player.getNumberOfPieces() + 1; break;
				case "dol": player = playerList.getPlayer(1); icon = icons[1]; direction = directions[1]; pieceId = 2000 + player.getNumberOfPieces() + 1; break;
				case "per": player = playerList.getPlayer(2); icon = icons[2]; direction = directions[2]; pieceId = 3000 + player.getNumberOfPieces() + 1; break;
				case "que": player = playerList.getPlayer(3); icon = icons[3]; direction = directions[3]; pieceId = 4000 + player.getNumberOfPieces() + 1; break;
				case "eq": player = playerList.getPlayer(4); icon = icons[4]; direction = directions[4]; pieceId = 5000 + player.getNumberOfPieces() + 1; break;
				case "at": player = playerList.getPlayer(5); icon = icons[5]; direction = directions[5]; pieceId = 6000 + player.getNumberOfPieces() + 1; break;
				default: System.out.println("No such player."); System.exit(0);
			}
			
			men = new ChinesePawn(pieceId, icon, player, direction, menMovePossibilities, menMoveConstraints);
			player.addPiece(men);
			coordinatePieceMap.putPieceToCoordinate(men, coordinatePieceDuo.getCoordinate());
		}
		
		
		
	}
	
	//GAMEPLAY METHODS
	
	//TODO: Alter these

	@Override
	public void conductGame() {
		boolean endOfGame = false;
		if (automaticGameOn) {
			conductAutomaticGame();
			endOfGame = (isSatisfied(new RuleEndOfGamePiecesOfPlayerOnFinishCoordinates(), this));
			view.printMessage("End Of Game? " + endOfGame);
		}
		if(!endOfGame) {
			start();
			view.printMessage(playerList.getPlayerStatus());
			view.printMessage("Game begins ...");
			consoleView.drawBoardView();
		}
		while (!endOfGame) {
			currentMoveCoordinate = getNextMove();
			if (currentMoveCoordinate == null) { abort(); return; }
			while (!conductMove()) {
				currentMoveCoordinate = getNextMove();				
				if (currentMoveCoordinate == null) { abort(); return; }
			}
			consoleView.drawBoardView();

			endOfGame = (isSatisfied(new RuleEndOfGamePiecesOfPlayerOnFinishCoordinates(), this));
			
			view.printMessage("End Of Game? " + endOfGame);
			if (endOfGame) break;
			
			if (info.isTestAborted())
				return;
			
			currentPlayerID++;
			if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
			currentPlayer = getPlayerbyID(currentPlayerID);
			if (info.isEndOfMoves()) { end(); return; }
		} 
		//consoleView.drawBoardView();
		info.register(TestResult.GAME_END_AS_WIN);
		view.printMessage("WINNER " + announceWinner());

		end();
		return;
//		consoleView.closeFile();
//		System.exit(0);
	}

	protected boolean conductMove() {
		ICoordinate sourceCoordinate = currentMoveCoordinate.getSourceCoordinate();
		ICoordinate destinationCoordinate = currentMoveCoordinate.getDestinationCoordinate();
		AbstractPiece piece = coordinatePieceMap.getPieceAtCoordinate(sourceCoordinate);
		if (!checkMove()) {
			info.register(TestResult.ANOTHER_SOURCE_INVALID);
			return false;
		}
		view.printMessage(currentMoveCoordinate+" current move corrd");
		List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);
		coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
		MoveOpResult moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
		
		piece = becomeAndOrPutOperation(piece, destinationCoordinate);
		view.printMessage("CurrentPlayerTurnAgain? " + moveOpResult.isCurrentPlayerTurnAgain());
		recordMove(currentMoveCoordinate);
		if (moveOpResult.isCurrentPlayerTurnAgain() && !automaticGameOn) 
			conductCurrentPlayerTurnAgain(moveOpResult, piece);
		return true;
	}

	protected boolean conductCurrentPlayerTurnAgain(MoveOpResult moveOpResult, AbstractPiece piece) {
		AbstractPiece temp  = piece;

		while (moveOpResult.isCurrentPlayerTurnAgain()) {
			List<ICoordinate> secondJumpList = new ArrayList<ICoordinate>();		
			List<ICoordinate> jumpList = board.getCBO().findAllowedContinousJumpList(piece);

			Direction lastMoveDirection = board.getCBO().findDirection(currentMoveCoordinate.getSourceCoordinate(),currentMoveCoordinate.getDestinationCoordinate());

			for(ICoordinate destinationCoordinate : jumpList) {
				Direction newDirection = board.getCBO().findDirection(currentMoveCoordinate.getDestinationCoordinate(), destinationCoordinate);
				if(!lastMoveDirection.getOppositeDirection().equals(newDirection) )
					secondJumpList.add(destinationCoordinate);	
			}

			if (secondJumpList.size() == 0) {
				moveOpResult = new MoveOpResult(false, false);
				info.register(TestResult.MOVE_END_NO_MORE_JUMP_POSSIBILITY);
				break;
			}

			board.getCBO().printCoordinateList(secondJumpList, "Second Jump List");
			currentMoveCoordinate = getNextMove();
			if (currentMoveCoordinate == null) { abort(); return true; }
			ICoordinate sourceCoordinate = currentMoveCoordinate.getSourceCoordinate();
			ICoordinate destinationCoordinate = currentMoveCoordinate.getDestinationCoordinate();

			// if player select same coordinate, turn will be terminated
			if(sourceCoordinate.equals(destinationCoordinate)) 
				moveOpResult = new MoveOpResult(false, false);
			else{
				if (!checkMove()) {
					info.register(TestResult.ANOTHER_DESTINATION_INVALID);
					continue;
				}
				moveOpResult = new MoveOpResult(false, false);
				for(ICoordinate coordinate : secondJumpList) {
					if (coordinate.equals(destinationCoordinate)) {
						List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);
						coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
						moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
						piece = becomeAndOrPutOperation(piece, destinationCoordinate);
						recordMove(currentMoveCoordinate);
						if(!temp.equals(piece)) {
							info.register(TestResult.MOVE_END_CROWNED);
							moveOpResult = new MoveOpResult(true, false);
						}
					}
				}				
			}
		}
		return true;
	}

	private void setupTestBoardMVC() {
		board = new ChineseCheckersBoard();
		coordinatePieceMap = board.getCoordinatePieceMap();
		consoleView = new ChineseCheckersBoardTestConsoleView(this);
		view = consoleView;
	}
	
	//MAIN METHOD
	
	public static void main(String[] args) {
		IGameConfiguration gameConfiguration = new ChineseTestGameConfiguration(3);
		ChineseTesterReferee referee = new ChineseTesterReferee(gameConfiguration);
		referee.setup("cti");
		referee.conductGame();
	}
	
	
	//UNTOUCHED METHODS
	
	@Override
	public void setup() {
		setupPlayers();
		setupBoardMVC();
		setupPiecesOnBoard();
	}

	private void setupPlayers() {
		numberOfPlayers = gameConfiguration.getNumberOfPlayers();
		numberOfPiecesPerPlayer = gameConfiguration.getNumberOfPiecesPerPlayer();
		playerList = new PlayerList();
		for(int i=0; i<numberOfPlayers; i++) {
			playerList.addPlayer(new Player(i, getRandomColor()));
		}
		currentPlayerID = 0;
		currentPlayer = playerList.getPlayer(0);
	}
	
	private Color getRandomColor() {
		Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g, b);
	}

	private void setupBoardMVC() {
		board = new ChineseCheckersBoard();
		coordinatePieceMap = board.getCoordinatePieceMap();
		consoleView = new ChineseCheckersBoardConsoleView(this);
		view = new ChineseCheckersBoardConsoleView(this);
	}

	private void setupPiecesOnBoard() {
		icons = new String[]{"#","$","%","?","=","@"};
		directions = createDirections(numberOfPlayers);
		// create pieces for players and put them on board
		StartCoordinateFactory startCoordinateFactory = new StartCoordinateFactory();
		IPlayer player;
		AbstractPiece men;
		StartCoordinates startCoordinates = startCoordinateFactory.getStartCoordinates(numberOfPlayers,numberOfPiecesPerPlayer);
		IPieceMovePossibilities menMovePossibilities = new ChinesePawnMovePossibilities();
		IPieceMoveConstraints menMoveConstraints =  new ChinesePawnMoveConstraints();

		for (int i = 0; i < numberOfPlayers; i++) {
			player = playerList.getPlayer(i);
			String icon;
			Direction direction;
			icon = icons[i];
			view.printMessage("Player "+i+" "+icon);
			direction = directions[i];
			for (int j = 0; j < numberOfPiecesPerPlayer; j++) {
				men = new ChinesePawn(j, icon, player, direction, menMovePossibilities, menMoveConstraints);
				player.addPiece(men);
				coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());
			}
		}
		
	    //coordinatePieceMap.printPieceMap();

	}
	
	private Direction[] createDirections(int numberOfPlayers) {
		if(numberOfPlayers==2)
			return new Direction[] {Direction.N, Direction.S};
		else if(numberOfPlayers==3)
			return new Direction[] {Direction.S, Direction.NE, Direction.NW};
		else if(numberOfPlayers==4)
			return new Direction[] {Direction.NE, Direction.NW, Direction.SE, Direction.SW};
		else if(numberOfPlayers==6)
			return new Direction[] {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
		return null;
	}
	
	protected boolean checkMove() {
		return  isSatisfied(new RuleThereMustBePieceAtSourceCoordinate(), this)
				&& isSatisfied(new RuleThereMustNotBePieceAtDestinationCoordinate(), this)
				&& isSatisfied(new RulePieceAtSourceCoordinateMustBelongToCurrentPlayer(), this)
				&& isSatisfied(new RuleDestinationCoordinateMustBeValidForCurrentPiece(), this)
				&& isSatisfied(new RuleMoveMustMatchPieceMoveConstraints(), this)
				&& isSatisfied(new RuleIfJumpMoveThenJumpedPieceMustBe(), this);
	}

	protected MoveOpResult moveInterimOperation(AbstractPiece piece, IMoveCoordinate moveCoordinate, List<ICoordinate> path) {
		//IPlayer player = piece.getPlayer();
		if (board.getMBO().isJumpMove(piece, moveCoordinate)) {
			ICoordinate pathCoordinate = path.get(1);
			AbstractPiece pieceAtPath = coordinatePieceMap.getPieceAtCoordinate(pathCoordinate);
			if (pieceAtPath != null) {
				return new MoveOpResult(true, true); // jumped over piece
			}
		}
		return new MoveOpResult(true, false);
	}

	protected AbstractPiece becomeAndOrPutOperation(AbstractPiece piece, ICoordinate destinationCoordinate) {
		coordinatePieceMap.putPieceToCoordinate(piece, destinationCoordinate);
		return piece;
	}
	
	protected AbstractPiece becomeNewPiece(IPlayer player, AbstractPiece piece) {
		return null;
	}
	
	@Override
	public IPlayer announceWinner() {
		return playerList.getPlayer(currentPlayerID);
	}
	
	public IPlayerList announceDraw(){
		return playerList;
	}
	
	public IPlayer getCurrentPlayer() {
		if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
		return playerList.getPlayer(currentPlayerID);
	}

	public IPlayer getNextPlayer() {
		int nextPlayerID = currentPlayerID+1;
		if (nextPlayerID >= numberOfPlayers) nextPlayerID = 0;
		return playerList.getPlayer(nextPlayerID);
	}

	private void conductAutomaticGame() {				
		view.printMessage("Automatic Game begins ...");
		automaticGameOn = true;
		int step = 0;
		consoleView.drawBoardView();
		while (step < consoleView.getSizeOfAutomaticMoveList()) {
			currentMove = consoleView.getNextAutomaticMove(step);
			currentMoveCoordinate = currentMove.getMoveCoordinate();
			currentPlayer = currentMove.getPlayer();
			currentPlayerID = currentPlayer.getId();
			conductMove();
			consoleView.drawBoardView();
			step++;
		}
		automaticGameOn = false;
		view.printMessage("Automatic Game ends ...");
	}
	
	
}

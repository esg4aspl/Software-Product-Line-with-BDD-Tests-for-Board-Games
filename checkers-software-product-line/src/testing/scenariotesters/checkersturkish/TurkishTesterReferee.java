package testing.scenariotesters.checkersturkish;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import base.AmericanCheckersBoardConsoleView;
import base.Pawn;
import base.Player;
import base.PlayerList;
import checkersamerican.King;
import checkersturkish.TurkishCheckerBoard;
import checkersturkish.TurkishCheckersStartCoordinates;
import checkersturkish.TurkishKingMoveConstraints;
import checkersturkish.TurkishKingMovePossibilities;
import checkersturkish.TurkishPawnConstraints;
import checkersturkish.TurkishPawnPossibilities;
import core.AbstractPiece;
import core.Direction;
import core.ICoordinate;
import core.IGameConfiguration;
import core.IMoveCoordinate;
import core.IPieceMoveConstraints;
import core.IPieceMovePossibilities;
import core.IPlayer;
import core.IPlayerList;
import core.IRule;
import core.MoveCoordinate;
import core.MoveOpResult;
import rules.RuleDestinationCoordinateMustBeValidForCurrentPiece;
import rules.RuleDrawIfNoPromoteForFortyTurn;
import rules.RuleEndOfGameGeneral;
import rules.RuleEndOfGameIfEachPlayerHasOnePiece;
import rules.RuleEndOfGameWhenOpponentBlocked;
import rules.RuleIfAnyPieceCanBeCapturedThenMoveMustBeThat;
import rules.RuleIfJumpMoveThenJumpedPieceMustBeOpponentPiece;
import rules.RuleMoveMustMatchPieceMoveConstraints;
import rules.RulePieceAtSourceCoordinateMustBelongToCurrentPlayer;
import rules.RuleThereMustBePieceAtSourceCoordinate;
import rules.RuleThereMustNotBePieceAtDestinationCoordinate;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.SourceCoordinateValidity;
import testing.helpers.TestResult;
import testing.scenariotesters.AbstractTesterReferee;

public class TurkishTesterReferee extends AbstractTesterReferee {
	
	protected AmericanCheckersBoardConsoleView consoleView;
	protected List<IMoveCoordinate> priorMoves;

	public TurkishTesterReferee(IGameConfiguration gameConfiguration) {
		super(gameConfiguration);
		priorMoves = new ArrayList<IMoveCoordinate>();
	}

	
	//GAMEPLAY METHODS

	@Override
	public void conductGame() {
		boolean endOfGame = false;
		boolean endOfGameDraw = false;
		IRule noPromoteRule = new RuleDrawIfNoPromoteForFortyTurn();
		prepareRule(noPromoteRule);
		
//		if (automaticGameOn) {
//			conductAutomaticGame();
//			endOfGame = (isSatisfied(new RuleEndOfGameGeneral(), this) || isSatisfied(new RuleEndOfGameWhenOpponentBlocked(), this));
//			endOfGameDraw = (isSatisfied(noPromoteRule, this)  || 
//					isSatisfied(new RuleEndOfGameIfEachPlayerHasOnePiece(), this));
//			view.printMessage("End Of Game? " + endOfGame);
//		}
		if(!endOfGame) {
			start();
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

			endOfGame = (isSatisfied(new RuleEndOfGameGeneral(), this) || isSatisfied(new RuleEndOfGameWhenOpponentBlocked(), this));
			endOfGameDraw = (isSatisfied(noPromoteRule, this)  || 
					isSatisfied(new RuleEndOfGameIfEachPlayerHasOnePiece(), this));
			
			view.printMessage("End Of Game? " + endOfGame);
			if (endOfGame || endOfGameDraw) break;
			
			if (info.isTestAborted()) { return; }
			
			currentPlayerID++;
			if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
			currentPlayer = getPlayerbyID(currentPlayerID);
			
			if (info.isEndOfMoves()) { end(); return; }
		} 
		//consoleView.drawBoardView();
		
		if (endOfGameDraw) {
			info.register(TestResult.GAME_END_AS_DRAW);
			view.printMessage("DRAW\n" + announceDraw());
		} else {
			info.register(TestResult.GAME_END_AS_WIN);
			view.printMessage("WINNER " + announceWinner());
		}
		end();
		return;
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
		List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);

		coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
		
		MoveOpResult moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
		piece = becomeAndOrPutOperation(piece, destinationCoordinate);
		priorMoves.add(currentMoveCoordinate);
		view.printMessage("CurrentPlayerTurnAgain? " + moveOpResult.isCurrentPlayerTurnAgain());
		if (moveOpResult.isCurrentPlayerTurnAgain() && !automaticGameOn) 
			conductCurrentPlayerTurnAgain(moveOpResult, piece);
		return true;
	}

	protected boolean conductCurrentPlayerTurnAgain(MoveOpResult moveOpResult, AbstractPiece piece) {
		//AbstractPiece temp  = piece;
		boolean flag = false;		
		IMoveCoordinate rollbackTemp;
		while (moveOpResult.isCurrentPlayerTurnAgain()) {
			flag = false;
			List<ICoordinate> secondJumpList = new ArrayList<ICoordinate>();		
			List<ICoordinate> jumpList = findAllowedJumpListWithOpponentPieceOnPath(piece);
			rollbackTemp = currentMoveCoordinate;
			Direction lastMoveDirection = board.getCBO().findDirection(currentMoveCoordinate.getSourceCoordinate(),currentMoveCoordinate.getDestinationCoordinate());
			
			for(ICoordinate destinationCoordinate : jumpList) {
				Direction newDirection = board.getCBO().findDirection(currentMoveCoordinate.getDestinationCoordinate(), destinationCoordinate);
				if(lastMoveDirection.getOppositeDirection()!=newDirection ) {				
					secondJumpList.add(destinationCoordinate);	
				}
			}
			
			if (secondJumpList.size() == 0) {
				moveOpResult = new MoveOpResult(false, false);
				info.register(TestResult.MOVE_END_NO_MORE_JUMP_POSSIBILITY);
				break;
			}
			info.register(TestResult.ANOTHER_MOVE_JUMP_POSSIBILITY);
			board.getCBO().printCoordinateList(secondJumpList, "Second Jump List");
			currentMoveCoordinate = getNextMove();
			if (currentMoveCoordinate == null) { abort(); return true; }
			ICoordinate sourceCoordinate = currentMoveCoordinate.getSourceCoordinate();
			ICoordinate destinationCoordinate = currentMoveCoordinate.getDestinationCoordinate();
			
			for(ICoordinate coord : secondJumpList) {
				if(coord.equals(destinationCoordinate))
					flag=true;
			}
			if(!flag) {
				info.register(TestResult.ANOTHER_DESTINATION_INVALID);
				currentMoveCoordinate = rollbackTemp;
				continue;
			}
			
			if (!checkMove()) { 
				info.register(TestResult.ANOTHER_DESTINATION_INVALID);
				currentMoveCoordinate = rollbackTemp;
				continue;
			}
			
			moveOpResult = new MoveOpResult(false, false);
			for(ICoordinate coordinate : secondJumpList) {
				if (coordinate.equals(destinationCoordinate)) {
					List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);
					coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
					moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
					piece = becomeAndOrPutOperation(piece, destinationCoordinate);
					priorMoves.add(currentMoveCoordinate);
				}
			}
		}
		return true;
	}
	
	
	//TESTER-ONLY METHODS
	
	private void prepareRule(IRule noPromoteRule) {
		//TODO Implement this
	}
	
	@Override
	public void setup(String fileName) {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void start() {
		super.start();
		System.out.println("Best sequence: " + info.getReader().getBestSequence());
		System.out.println("Prior move sequence: " + info.getReader().getPriorMoveSequence());
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

	public List<IMoveCoordinate> getPriorMoves() {
		return priorMoves;
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
		IPlayer player0 = new Player(0, Color.BLACK);
		playerList.addPlayer(player0);
		IPlayer player1 = new Player(1, Color.WHITE);
		playerList.addPlayer(player1);
		currentPlayerID = 0;
		currentPlayer = player0;
	}

	private void setupBoardMVC() {
		board = new TurkishCheckerBoard();
		coordinatePieceMap = board.getCoordinatePieceMap();
		consoleView = new AmericanCheckersBoardConsoleView(this);
		
	}

	private void setupPiecesOnBoard() {
		// create pieces for players and put them on board
		IPlayer player;
		AbstractPiece men;
		TurkishCheckersStartCoordinates startCoordinates = new TurkishCheckersStartCoordinates();
		IPieceMovePossibilities menMovePossibilities = new TurkishPawnPossibilities();
		IPieceMoveConstraints menMoveConstraints =  new TurkishPawnConstraints();

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
				men = new Pawn(1000+i, icon, player, direction, menMovePossibilities, menMoveConstraints);
				player.addPiece(men);
				coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());
			}
		}
	    //coordinatePieceMap.printPieceMap();
	    view.printMessage(playerList.getPlayerStatus());
	}

	protected boolean checkMove() {
		return isSatisfied(new RuleIfAnyPieceCanBeCapturedThenMoveMustBeThat(), this)
				&& isSatisfied(new RuleThereMustBePieceAtSourceCoordinate(), this)
				&& isSatisfied(new RuleThereMustNotBePieceAtDestinationCoordinate(), this)
				&& isSatisfied(new RulePieceAtSourceCoordinateMustBelongToCurrentPlayer(), this)
				&& isSatisfied(new RuleDestinationCoordinateMustBeValidForCurrentPiece(), this)
				&& isSatisfied(new RuleMoveMustMatchPieceMoveConstraints(), this)
				&& isSatisfied(new RuleIfJumpMoveThenJumpedPieceMustBeOpponentPiece(), this);
	}
	
	private List<ICoordinate> piecesOnPath(List<ICoordinate> path){
		List<ICoordinate> coordinatesThatHasAnyPiece = new ArrayList<ICoordinate>();
		for (ICoordinate c: path) {
			if(coordinatePieceMap.getPieceAtCoordinate(c)!=null)
				coordinatesThatHasAnyPiece.add(c);
		}
		return coordinatesThatHasAnyPiece;
	}
	
	protected MoveOpResult moveInterimOperation(AbstractPiece piece, IMoveCoordinate moveCoordinate, List<ICoordinate> path) {
		IPlayer player = piece.getPlayer();
		if (board.getMBO().isJumpMove(piece, moveCoordinate) && piecesOnPath(path).size()==1) {
			ICoordinate pathCoordinate = piecesOnPath(path).get(0);
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
	
	protected AbstractPiece becomeNewPiece(IPlayer player, AbstractPiece piece) {
		int pieceID = piece.getId();
		Direction goalDirection = piece.getGoalDirection();
		player.removePiece(piece);
		IPieceMovePossibilities kingMovePossibilities = new TurkishKingMovePossibilities();
		IPieceMoveConstraints kingMoveConstraints =  new TurkishKingMoveConstraints();
		String icon;
		if (player.getId() == 0) icon = "A";
		else icon = "Z";
		AbstractPiece king = new King(pieceID+2, icon, player, goalDirection, kingMovePossibilities, kingMoveConstraints);
		player.addPiece(king);
		return king;
	}
	
	private List<ICoordinate> findAllowedJumpListWithOpponentPieceOnPath(AbstractPiece piece){
		List<ICoordinate> jumpList = board.getCBO().findAllowedContinousJumpList(piece);
		
		List<ICoordinate> allowedJumpList = new ArrayList<ICoordinate>();
		for(ICoordinate destinationCoordinate : jumpList) {
			IMoveCoordinate moveCoordinate = new MoveCoordinate(piece.getCurrentCoordinate(), destinationCoordinate);
			List<ICoordinate> path = board.getCBO().findPath(piece, moveCoordinate);
			if(!isCurrentPlayersPieceOnPath(currentPlayer, path))
				allowedJumpList.add(destinationCoordinate);	
		}
		return allowedJumpList;
	}
	
	private boolean isCurrentPlayersPieceOnPath(IPlayer player,List<ICoordinate> path) {
		path.remove(0);
		path.remove(path.size()-1);
		for(ICoordinate coordinate:path) {
			AbstractPiece piece = coordinatePieceMap.getPieceAtCoordinate(coordinate);
			if(piece!=null && piece.getPlayer().equals(player))
				return true;
		}
		return false;
	}
	
	protected AbstractPiece becomeAndOrPutOperation(AbstractPiece piece, ICoordinate destinationCoordinate) {
		//check the piece is already king or not
		if(!(piece instanceof King))
			if ((piece.getGoalDirection() == Direction.N && destinationCoordinate.getYCoordinate() == 7 && piece.getIcon().equals("B"))
					|| (piece.getGoalDirection() == Direction.S && destinationCoordinate.getYCoordinate() == 0 && piece.getIcon().equals("W"))){
				IPlayer player = piece.getPlayer();
				piece.setCurrentCoordinate(destinationCoordinate);
				List<ICoordinate> allowedJumpList = findAllowedJumpListWithOpponentPieceOnPath(piece);
				if(allowedJumpList.size() == 0) {
					piece = becomeNewPiece(player, piece);
				}
			}
		
		coordinatePieceMap.putPieceToCoordinate(piece, destinationCoordinate);
		
		return piece;
	}
	
	public IPlayerList announceDraw(){
		return playerList;
	}

	@Override
	public IPlayer announceWinner() {
		return playerList.getPlayer(currentPlayerID);
	}
	

}

package testing.scenariotesters.checkersamerican;

import java.awt.Color;
import java.util.List;

import base.AmericanCheckersBoard;
import base.AmericanCheckersBoardConsoleView;
import base.Pawn;
import base.PawnMoveConstraints;
import base.PawnMovePossibilities;
import base.Player;
import base.PlayerList;
import checkersamerican.King;
import checkersamerican.KingMoveConstraints;
import checkersamerican.KingMovePossibilities;
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
import core.IRule;
import core.MoveOpResult;
import rules.RuleDestinationCoordinateMustBeValidForCurrentPiece;
import rules.RuleDrawIfNoPromoteForFortyTurn;
import rules.RuleEndOfGameGeneral;
import rules.RuleEndOfGameNoPieceCapturedForFortyTurn;
import rules.RuleEndOfGameWhenOpponentBlocked;
import rules.RuleIfAnyPieceCanBeCapturedThenMoveMustBeThat;
import rules.RuleIfJumpMoveThenJumpedPieceMustBeOpponentPiece;
import rules.RuleMoveMustMatchPieceMoveConstraints;
import rules.RulePieceAtSourceCoordinateMustBelongToCurrentPlayer;
import rules.RuleThereMustBePieceAtSourceCoordinate;
import rules.RuleThereMustNotBePieceAtDestinationCoordinate;
import testing.helpers.ICoordinatePieceDuo;
import testing.helpers.IiniReader;
import testing.helpers.IniReader;

public class AmericanTesterReferee extends AbstractReferee {

	IMoveCoordinate playerMove;
	IiniReader reader;
	String setUpName;
	String lastMessagePrinted;
	boolean playerWasGoingToMakeAnotherMove;

	protected AmericanCheckersBoardConsoleView consoleView;
	
	public AmericanTesterReferee(IGameConfiguration checkersGameConfiguration) {
		super(checkersGameConfiguration);
		this.playerWasGoingToMakeAnotherMove = false;
	}
	
	public void setIni(String setUpName) {
		reader = new IniReader("src/testing/scenariotesters/checkersamerican/AmericanCheckers.ini", setUpName);
	}
	
	public void setGameSetupName(String setUpName) {
		this.setUpName = setUpName;
	}
	
	public void setup() {
		setupPlayers();
		setupBoardMVC();
		view = consoleView;
		setIni(setUpName);
		setupPiecesOnBoard(reader.getCoordinatePieceDuos());
		if (reader.getCurrentTurnPlayerIconColor().equals("black")) {
			currentPlayerID = 0;
			currentPlayer = playerList.getPlayer(0);
		} else {
			currentPlayerID = 1;
			currentPlayer = playerList.getPlayer(1);
		}
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
		board = new AmericanCheckersBoard();
		coordinatePieceMap = board.getCoordinatePieceMap();
		consoleView = new AmericanCheckersBoardConsoleView(this);
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
			
			//If the piece was a king piece, then transform it:
			if (coordinatePieceDuo.getPieceType().equals("king")) {
				men = becomeNewPiece(player, men);
			}

			coordinatePieceMap.putPieceToCoordinate(men, coordinatePieceDuo.getCoordinate());
			counter++;
		}
		
		
	}
	
	public IMoveCoordinate readPlayerMove() {
		playerMove = reader.getPlayerMove();
		return playerMove;
	}
	
	public void conductGame() {	
		boolean endOfGame = false;
		boolean endOfGameDraw = false;

		IRule noPromoteRule = new RuleDrawIfNoPromoteForFortyTurn();
		IRule noPieceCapturedForFortyTurn = new RuleEndOfGameNoPieceCapturedForFortyTurn();
//		if (automaticGameOn) {
//			conductAutomaticGame();
//			endOfGame = (isSatisfied(new RuleEndOfGameGeneral(), this) || isSatisfied(new RuleEndOfGameWhenOpponentBlocked(), this));
//			endOfGameDraw = (isSatisfied(noPromoteRule, this) || isSatisfied(noPieceCapturedForFortyTurn, this));
//			printMessage("End Of Game? " + endOfGame);
//		}
		if(!endOfGame) {
			
			printMessage("Game begins ...");
			consoleView.drawBoardView();
		}
		while (!endOfGame) {
			currentMoveCoordinate = playerMove;
			while (!conductMove()) {
				//lastMessagePrinted = "Player picks another move because of invalidity.";
				System.out.println("Test ended with an invalid player move.");
				view.drawBoardView();
				return;
//				playerMove = view.getNextMove(currentPlayer);
//				currentMoveCoordinate = playerMove;			
			}
			consoleView.drawBoardView();

			endOfGame = (isSatisfied(new RuleEndOfGameGeneral(), this) || isSatisfied(new RuleEndOfGameWhenOpponentBlocked(), this));
			endOfGameDraw = (isSatisfied(noPromoteRule, this) || isSatisfied(noPieceCapturedForFortyTurn, this));
			
			view.printMessage("End Of Game? " + endOfGame);
			if (endOfGame || endOfGameDraw) break;
			
			if (!playerWasGoingToMakeAnotherMove) {
				currentPlayerID++;
				if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
				currentPlayer = getPlayerbyID(currentPlayerID);
			}
			
			//ONLY IN TESTER CLASS. END THE GAME AFTER PLAYER'S MOVE THAT THE TEST IS FOCUSING ON.
			System.out.println("Test ended.");
			view.drawBoardView();
			return;
		} 
		//consoleView.drawBoardView();
		
		if(endOfGameDraw)
			printMessage("DRAW\n" + announceDraw());
		else
			printMessage("WINNER " + announceWinner());
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		consoleView.closeFile();
		System.exit(0);
	}
	
	private void conductAutomaticGame() {				
		printMessage("Automatic Game begins ...");
		automaticGameOn = true;
		int step = 0;
		consoleView.drawBoardView();
		while (step < consoleView.getSizeOfAutomaticMoveList()) {
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			currentMove = consoleView.getNextAutomaticMove(step);
			currentMoveCoordinate = currentMove.getMoveCoordinate();
			currentPlayer = currentMove.getPlayer();
			currentPlayerID = currentPlayer.getId();
			conductMove();
			consoleView.drawBoardView();
			step++;
		}
		automaticGameOn = false;
		printMessage("Automatic Game ends ...");
	}
	
	protected boolean conductMove() {
		ICoordinate sourceCoordinate = currentMoveCoordinate.getSourceCoordinate();
		ICoordinate destinationCoordinate = currentMoveCoordinate.getDestinationCoordinate();
		AbstractPiece piece = coordinatePieceMap.getPieceAtCoordinate(sourceCoordinate);
		if (!checkMove()) return false;
		List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);
		coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
		MoveOpResult moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
		//if piece become king then terminate the move
		AbstractPiece  temp  = piece;
		piece = becomeAndOrPutOperation(piece, destinationCoordinate);
		if(!temp.equals(piece))
			moveOpResult = new MoveOpResult(true, false);
		printMessage("CurrentPlayerTurnAgain? " + moveOpResult.isCurrentPlayerTurnAgain());
		if (moveOpResult.isCurrentPlayerTurnAgain() && !automaticGameOn) {
			this.playerWasGoingToMakeAnotherMove = true;
			lastMessagePrinted = "Player picks another move, because of a previous jump move.";
			//conductCurrentPlayerTurnAgain(moveOpResult, piece);
			return true;
		}
		return true;
	}

	protected boolean conductCurrentPlayerTurnAgain(MoveOpResult moveOpResult, AbstractPiece piece) {
		AbstractPiece temp  = piece;
		IMoveCoordinate rollbackTemp;
		while (moveOpResult.isCurrentPlayerTurnAgain()) {
			List<ICoordinate> secondJumpList = board.getCBO().findAllowedContinousJumpList(piece);
			rollbackTemp = currentMoveCoordinate;
			if (secondJumpList.size() == 0) {
				moveOpResult = new MoveOpResult(false, false);
				break;
			}
			playerMove = view.getNextMove(currentPlayer);
			currentMoveCoordinate = playerMove;
			ICoordinate sourceCoordinate = currentMoveCoordinate.getSourceCoordinate();
			ICoordinate destinationCoordinate = currentMoveCoordinate.getDestinationCoordinate();
			
			if (!checkMove()) { 
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
					if(!temp.equals(piece)) 
						moveOpResult = new MoveOpResult(true, false);
				}
			}
			
			
		}
		return true;
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

	protected MoveOpResult moveInterimOperation(AbstractPiece piece, IMoveCoordinate moveCoordinate, List<ICoordinate> path) {
		IPlayer player = piece.getPlayer();
		if (board.getMBO().isJumpMove(piece, moveCoordinate)) {
			ICoordinate pathCoordinate = path.get(1);
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
		//check the piece is already king or not
		if(!(piece instanceof King))
			if ((piece.getGoalDirection() == Direction.N && destinationCoordinate.getYCoordinate() == 7)
					|| (piece.getGoalDirection() == Direction.S && destinationCoordinate.getYCoordinate() == 0)){
				IPlayer player = piece.getPlayer();
				piece = becomeNewPiece(player, piece);
			}
		coordinatePieceMap.putPieceToCoordinate(piece, destinationCoordinate);
		return piece;
	}
	
	protected AbstractPiece becomeNewPiece(IPlayer player, AbstractPiece piece) {
		int pieceID = piece.getId();
		Direction goalDirection = piece.getGoalDirection();
		player.removePiece(piece);
		IPieceMovePossibilities kingMovePossibilities = new KingMovePossibilities();
		IPieceMoveConstraints kingMoveConstraints =  new KingMoveConstraints();
		String icon;
		if (player.getId() == 0) icon = "A";
		else icon = "Z";
		AbstractPiece king = new King(pieceID+2, icon, player, goalDirection, kingMovePossibilities, kingMoveConstraints);
		player.addPiece(king);
		return king;
	}
	
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

	
	@Override
	public void printMessage(String message) {
		super.printMessage(message);
		lastMessagePrinted = message;
	}

	
	
}

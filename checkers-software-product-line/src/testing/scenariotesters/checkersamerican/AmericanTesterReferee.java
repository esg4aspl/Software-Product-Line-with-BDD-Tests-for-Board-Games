package testing.scenariotesters.checkersamerican;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import base.AmericanCheckersBoard;
import base.AmericanCheckersBoardConsoleView;
import base.GUI;
import base.Pawn;
import base.PawnMoveConstraints;
import base.PawnMovePossibilities;
import base.Player;
import base.PlayerList;
import base.StartCoordinates;
import checkersamerican.AmericanStartCoordinates;
import checkersamerican.King;
import checkersamerican.KingMoveConstraints;
import checkersamerican.KingMovePossibilities;
import core.AbstractPiece;
import core.AbstractReferee;
import core.Coordinate;
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
import rules.RuleEndOfGameNoPieceCapturedForFortyTurn;
import rules.RuleEndOfGameWhenOpponentBlocked;
import rules.RuleIfAnyPieceCanBeCapturedThenMoveMustBeThat;
import rules.RuleIfJumpMoveThenJumpedPieceMustBeOpponentPiece;
import rules.RuleMoveMustMatchPieceMoveConstraints;
import rules.RulePieceAtSourceCoordinateMustBelongToCurrentPlayer;
import rules.RuleThereMustBePieceAtSourceCoordinate;
import rules.RuleThereMustNotBePieceAtDestinationCoordinate;

public class AmericanTesterReferee extends AbstractReferee {

	BufferedReader moveReader;
	IMoveCoordinate playerMove;

	protected AmericanCheckersBoardConsoleView consoleView;
	
	public AmericanTesterReferee(IGameConfiguration checkersGameConfiguration) {
		super(checkersGameConfiguration);
	}
	
	public void setMoveFile(String fileName) {
		try {
			moveReader = new BufferedReader(new FileReader("./src/testing/scenariotesters/checkersamerican/" + fileName + ".txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IMoveCoordinate getMoveFromFile() {
		String line;
		try {
			line = moveReader.readLine();
			if (line == null || line.equals("---")) {
				return null;
			}
			String[] coordinates = line.split(">");
			String[] srcCoordinates = coordinates[0].split(",");
			String[] destCoordinates = coordinates[1].split(",");
			ICoordinate src = new Coordinate(Integer.parseInt(srcCoordinates[0]), Integer.parseInt(srcCoordinates[1]));
			ICoordinate dest = new Coordinate(Integer.parseInt(destCoordinates[0]), Integer.parseInt(destCoordinates[1]));
			return new MoveCoordinate(src, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public IMoveCoordinate readPlayerMove() {
		String line;
		try {
			line = moveReader.readLine();
			if (line == null) {
				playerMove = view.getNextMove(currentPlayer);
			} else {
				String[] coordinates = line.split(">");
				String[] srcCoordinates = coordinates[0].split(",");
				String[] destCoordinates = coordinates[1].split(",");
				ICoordinate src = new Coordinate(Integer.parseInt(srcCoordinates[0]), Integer.parseInt(srcCoordinates[1]));
				ICoordinate dest = new Coordinate(Integer.parseInt(destCoordinates[0]), Integer.parseInt(destCoordinates[1]));
				playerMove = new MoveCoordinate(src, dest);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			playerMove = null;
		}
		return playerMove;
	}

	
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
		board = new AmericanCheckersBoard();
		coordinatePieceMap = board.getCoordinatePieceMap();
		consoleView = new AmericanCheckersBoardConsoleView(this);
		view = consoleView;
	}

	private void setupPiecesOnBoard() {
		// create pieces for players and put them on board
		IPlayer player;
		AbstractPiece men;
		StartCoordinates startCoordinates = new AmericanStartCoordinates();
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
				men = new Pawn(1000+i, icon, player, direction, menMovePossibilities, menMoveConstraints);
				player.addPiece(men);
				coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());
			}
		}
		
		//coordinatePieceMap.printPieceMap();
		view.printMessage(playerList.getPlayerStatus());
	}
	
	public void playGameUpToACertainPoint() {
		while ((currentMoveCoordinate = getMoveFromFile()) != null) {
			if (!conductMove()) {
				System.out.println("\nPrepared game is not valid.");
				System.exit(0);
			}
			currentPlayerID++;
			if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
			currentPlayer = getPlayerbyID(currentPlayerID);
		}
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
//			view.printMessage("End Of Game? " + endOfGame);
//		}
		if(!endOfGame) {
			
			view.printMessage("Game begins ...");
			consoleView.drawBoardView();
		}
		while (!endOfGame) {
			currentMoveCoordinate = playerMove;
			while (!conductMove()) {
				playerMove = view.getNextMove(currentPlayer);
				currentMoveCoordinate = playerMove;			
			}
			consoleView.drawBoardView();

			endOfGame = (isSatisfied(new RuleEndOfGameGeneral(), this) || isSatisfied(new RuleEndOfGameWhenOpponentBlocked(), this));
			endOfGameDraw = (isSatisfied(noPromoteRule, this) || isSatisfied(noPieceCapturedForFortyTurn, this));
			
			view.printMessage("End Of Game? " + endOfGame);
			if (endOfGame || endOfGameDraw) break;
			
			currentPlayerID++;
			if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
			currentPlayer = getPlayerbyID(currentPlayerID);
			
			//ONLY IN TESTER CLASS. END THE GAME AFTER PLAYER'S MOVE THAT THE TEST IS FOCUSING ON.
			System.out.println("Test ended.");
			return;
		} 
		//consoleView.drawBoardView();
		
		if(endOfGameDraw)
			view.printMessage("DRAW\n" + announceDraw());
		else
			view.printMessage("WINNER " + announceWinner());
		
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
		view.printMessage("Automatic Game begins ...");
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
		view.printMessage("Automatic Game ends ...");
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
		view.printMessage("CurrentPlayerTurnAgain? " + moveOpResult.isCurrentPlayerTurnAgain());
		if (moveOpResult.isCurrentPlayerTurnAgain() && !automaticGameOn) 
			conductCurrentPlayerTurnAgain(moveOpResult, piece);
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
			currentMoveCoordinate = getMoveFromFile();
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

}

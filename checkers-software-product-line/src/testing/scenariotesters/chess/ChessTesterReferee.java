package testing.scenariotesters.chess;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import base.Pawn;
import base.Player;
import base.PlayerList;
import base.StartCoordinates;
import chess.Bishop;
import chess.BishopMoveConstraints;
import chess.BishopMovePossibilities;
import chess.ChessBoard;
import chess.ChessGameConfiguration;
import chess.ChessStartCoordinates;
import chess.King;
import chess.KingMoveConstraints;
import chess.KingMovePossibilities;
import chess.Knight;
import chess.KnightMoveConstraints;
import chess.KnightMovePossibilities;
import chess.LimitedPawn;
import chess.LimitedPawnMoveConstraints;
import chess.LimitedPawnMovePossibilities;
import chess.PawnMoveConstraints;
import chess.PawnMovePossibilities;
import chess.Queen;
import chess.QueenMoveConstraints;
import chess.QueenMovePossibilities;
import chess.Rook;
import chess.RookMoveConstraints;
import chess.RookMovePossibilities;
import core.AbstractPiece;
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
import rules.RuleEndOfGameChess;
import rules.RuleIfPieceToBeCapturedThenItMustBeBelongsToOpponent;
import rules.RuleIsKingFree;
import rules.RuleMoveMustMatchPieceMoveConstraints;
import rules.RulePawnOfChessCapturePieceInCrossMove;
import rules.RulePawnOfChessNoCapturePieceInStraightMove;
import rules.RulePieceAtSourceCoordinateMustBelongToCurrentPlayer;
import rules.RuleThereMustBeNoPieceOnPath;
import rules.RuleThereMustBePieceAtSourceCoordinate;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.ICoordinatePieceDuo;
import testing.helpers.SourceCoordinateValidity;
import testing.helpers.TestResult;
import testing.scenariotesters.AbstractTesterReferee;

public class ChessTesterReferee extends AbstractTesterReferee {

	protected ChessBoardTestConsoleView consoleView;
	
	public ChessTesterReferee(IGameConfiguration gameConfiguration) {
		super(gameConfiguration);
	}
	
	//TESTER-ONLY METHODS
	
	@Override
	public void setup(String fileName) {
		info = new ChessTestInfo(this, "src/testing/scenariotesters/chess/Chess.ini", fileName);
		setupPlayers();
		setupBoardMVC();
		view = consoleView;
		setupPiecesOnBoard(info.getReader().getCoordinatePieceDuos());
		if (info.getReader().getCurrentTurnPlayerIconColor().equals("white")) {
			currentPlayerID = 0;
			currentPlayer = playerList.getPlayer(0);
		} else {
			currentPlayerID = 1;
			currentPlayer = playerList.getPlayer(1);
		}
	}
	
	private void setupPiecesOnBoard(List<ICoordinatePieceDuo> coordinatePieceDuos) {
		IPlayer player;
		AbstractPiece men;
		Direction direction;
		int playerId;
		int pieceId;
		int p0Counter = 0;
		int p1Counter = 0;
		IPieceMovePossibilities menMovePossibilities = new PawnMovePossibilities();
		IPieceMoveConstraints menMoveConstraints =  new PawnMoveConstraints();
		IPieceMovePossibilities rookMovePossibilities = new RookMovePossibilities();
		IPieceMoveConstraints rookMoveConstraints =  new RookMoveConstraints();
		IPieceMovePossibilities knightMovePossibilities = new KnightMovePossibilities();
		IPieceMoveConstraints knightMoveConstraints =  new KnightMoveConstraints();
		IPieceMovePossibilities bishopMovePossibilities = new BishopMovePossibilities();
		IPieceMoveConstraints bishopMoveConstraints =  new BishopMoveConstraints();
		IPieceMovePossibilities kingMovePossibilities = new KingMovePossibilities();
		IPieceMoveConstraints kingMoveConstraints =  new KingMoveConstraints();
		IPieceMovePossibilities queenMovePossibilities = new QueenMovePossibilities();
		IPieceMoveConstraints queenMoveConstraints =  new QueenMoveConstraints();
		
		for (ICoordinatePieceDuo duo : coordinatePieceDuos) {
			if (duo.getIconColor().equals("white")) {
				playerId = 0;
				player = playerList.getPlayer(playerId);
				p0Counter += 5;
				pieceId = p0Counter + 1000;
				direction = Direction.N;
			} else {
				playerId = 1;
				player = playerList.getPlayer(playerId);
				p1Counter += 5;
				pieceId = p1Counter + 2000;
				direction = Direction.S;
			}
			
			switch(duo.getPieceType()) {
				case "pawn": men = new Pawn(pieceId, "P"+playerId, player, direction, menMovePossibilities, menMoveConstraints); break;
				case "limitedpawn": men = new LimitedPawn(pieceId, "P"+playerId, player, direction, new LimitedPawnMovePossibilities(), new LimitedPawnMoveConstraints()); break;
				case "rook": men = new Rook(pieceId, "R"+playerId, player, direction, rookMovePossibilities, rookMoveConstraints); break;
				case "knight": men = new Knight(pieceId, "k"+playerId, player, direction, knightMovePossibilities, knightMoveConstraints); break;
				case "bishop": men = new Bishop(pieceId, "B"+playerId, player, direction, bishopMovePossibilities, bishopMoveConstraints); break;
				case "queen": men = new Queen(pieceId, "Q"+playerId, player, direction, queenMovePossibilities, queenMoveConstraints); break;
				case "king": men = new King(pieceId, "K"+playerId, player, direction, kingMovePossibilities, kingMoveConstraints); break;
				default: continue;
			}
			
			player.addPiece(men);
			this.coordinatePieceMap.putPieceToCoordinate(men, duo.getCoordinate());
		}
	}

	@Override
	public SourceCoordinateValidity checkSourceCoordinate(IPlayer player, ICoordinate sourceCoordinate) {
		// Set-up the source coordinate and piece.
		int xOfSource = sourceCoordinate.getXCoordinate();
		int yOfSource = sourceCoordinate.getYCoordinate();
		AbstractPiece piece = getCoordinatePieceMap().getPieceAtCoordinate(sourceCoordinate);
		// Check if the coordinate is on board.
		if (xOfSource < 0 || 7 < xOfSource || yOfSource < 0 || 7 < yOfSource)
			return SourceCoordinateValidity.OUTSIDE_OF_THE_BOARD;
		// Check if coordinate is empty
		if (piece == null)
			return SourceCoordinateValidity.EMPTY;
		// Check if coordinate has an opponent's piece
		if (!piece.getPlayer().equals(player))
			return SourceCoordinateValidity.OPPONENT_PIECE;

		return SourceCoordinateValidity.VALID;
	}

	@Override
	public DestinationCoordinateValidity checkDestinationCoordinate(IPlayer player, ICoordinate sourceCoordinate, ICoordinate destinationCoordinate) {
//		int srcX = sourceCoordinate.getXCoordinate(); int srcY = sourceCoordinate.getYCoordinate();
//		int destX = destinationCoordinate.getXCoordinate(); int destY = destinationCoordinate.getYCoordinate();
//		
//		if (destX < 0 || 7 < destX || destY < 0 || 7 < destY)
//			return DestinationCoordinateValidity.OUTSIDE_OF_THE_BOARD;
//		
//		if (sourceCoordinate.equals(destinationCoordinate))
//			return DestinationCoordinateValidity.SAME_AS_SOURCE;
//		
//		AbstractPiece destPiece = this.coordinatePieceMap.getPieceAtCoordinate(destinationCoordinate);
//		if (destPiece.getPlayer().equals(player))
//			return DestinationCoordinateValidity.CAPTURED_PIECE_IS_OWN;
		
		return null;
	}
	
	//GAMEPLAY METHODS
	
	public void conductGame() {		
		boolean endOfGame = false;	
		
//		if (automaticGameOn) {
//			conductAutomaticGame();
//			endOfGame = (isSatisfied(new RuleEndOfGameChess(), this));
//			view.printMessage("End Of Game? " + endOfGame);
//		}
		if(!endOfGame) {
		//	view.printMessage(playerList.getPlayerStatus()+"\n Game begins ...");
			start();
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

			endOfGame = (isSatisfied(new RuleEndOfGameChess(), this));

			view.printMessage("Turn : Player " + currentPlayer.getId());
			if (endOfGame) break;

			if (info.isTestAborted())
				return;
			
			currentPlayerID++;
			if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
			currentPlayer = getPlayerbyID(currentPlayerID);
			if (info.isEndOfMoves()) { end(); return; }
		} 
		info.register(TestResult.GAME_END_AS_WIN);
		view.printMessage("WINNER " + announceWinner());
		end();
		return;
	}

	protected boolean conductMove() {
		ICoordinate sourceCoordinate = currentMoveCoordinate.getSourceCoordinate();
		ICoordinate destinationCoordinate = currentMoveCoordinate.getDestinationCoordinate();
		AbstractPiece piece = coordinatePieceMap.getPieceAtCoordinate(sourceCoordinate);
		if (!checkMove()) {
			info.register(TestResult.ANOTHER_SOURCE_INVALID);
			return false;
		}
		List<ICoordinate> path= new ArrayList<ICoordinate>();
		path=board.getCBO().findPath(piece, currentMoveCoordinate);
		coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);

		MoveOpResult moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);

		//if piece become king then terminate the move
		AbstractPiece  temp  = piece;
		piece = becomeAndOrPutOperation(piece, destinationCoordinate);
		recordMove(currentMoveCoordinate);
		if(!temp.equals(piece)) {
			info.register(TestResult.MOVE_END_CROWNED);
			moveOpResult = new MoveOpResult(true, false);
		}
		view.printMessage("CurrentPlayerTurnAgain? " + moveOpResult.isCurrentPlayerTurnAgain());
		//if (moveOpResult.isCurrentPlayerTurnAgain() && !automaticGameOn) 
		//	conductCurrentPlayerTurnAgain(moveOpResult, piece);
		return true;
	}

	//MAIN METHOD
	
	public static void main(String[] args) {
		String[] iniArr = {"validLeftCastlingMove1", "validRightCastlingMove1"
				};
		for (String ini : iniArr) {
			IGameConfiguration gameConfiguration = new ChessGameConfiguration();
			AbstractTesterReferee referee = new ChessTesterReferee(gameConfiguration);
			referee.setup(ini);
			referee.conductGame();
			System.out.println(referee.getInfo().getSourceCoordinateValidity());
		}
	}

	//UNTOUCHED METHODS
	
	private void conductAutomaticGame() {				
		view.printMessage("Automatic Game begins ...");
		automaticGameOn = true;
		int step = 0;
		consoleView.drawBoardView();
		while (step < consoleView.getSizeOfAutomaticMoveList()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
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

	protected boolean checkMove() {
		return isSatisfied(new RuleThereMustBePieceAtSourceCoordinate(), this)
				&& isSatisfied(new RulePieceAtSourceCoordinateMustBelongToCurrentPlayer(), this)
				&& isSatisfied(new RuleDestinationCoordinateMustBeValidForCurrentPiece(), this)
				&& isSatisfied(new RuleMoveMustMatchPieceMoveConstraints(), this)
				&& isSatisfied(new RulePawnOfChessCapturePieceInCrossMove(), this)
				&& isSatisfied(new RulePawnOfChessNoCapturePieceInStraightMove(), this)
				&& isSatisfied(new RuleIfPieceToBeCapturedThenItMustBeBelongsToOpponent(), this)
				&& isSatisfied(new RuleThereMustBeNoPieceOnPath(), this)
				&& isSatisfied(new RuleIsKingFree(), this);
	}

	protected MoveOpResult moveInterimOperation(AbstractPiece piece, IMoveCoordinate moveCoordinate, List<ICoordinate> path) {
		IPlayer player = piece.getPlayer();
		ICoordinate pathCoordinate = path.get(path.size()-1);
		AbstractPiece pieceAtPath = coordinatePieceMap.getPieceAtCoordinate(pathCoordinate);		
		if (pieceAtPath!=null && !pieceAtPath.getPlayer().equals(player)) {
			// capture piece at path
			coordinatePieceMap.capturePieceAtCoordinate(pieceAtPath, pathCoordinate);
			pieceAtPath.getPlayer().removePiece(pieceAtPath);
			return new MoveOpResult(true, true); // jumped over opponent team
		}
		return new MoveOpResult(true, false);
	}

	protected AbstractPiece becomeAndOrPutOperation(AbstractPiece piece, ICoordinate destinationCoordinate) {
		if(piece.getClass() == Pawn.class) {
			IPlayer player = piece.getPlayer();
			piece = becomeNewPiece(player, piece);				
		}else if(piece instanceof Pawn) {
			if ((piece.getGoalDirection() == Direction.N && destinationCoordinate.getYCoordinate() == 7)
					|| (piece.getGoalDirection() == Direction.S && destinationCoordinate.getYCoordinate() == 0)){
				IPlayer player = piece.getPlayer();
				piece = becomeNewPiece(player, piece);
			}			
		}

		coordinatePieceMap.putPieceToCoordinate(piece, destinationCoordinate);
		return piece;
	}

	protected AbstractPiece becomeNewPiece(IPlayer player, AbstractPiece piece) {
		Direction goalDirection = piece.getGoalDirection();		
		IPieceMovePossibilities pieceMovePossibilities;
		IPieceMoveConstraints pieceMoveConstraints;
		AbstractPiece newPiece = null;		
		
		if(piece.getClass() == Pawn.class) {
			pieceMoveConstraints = new LimitedPawnMoveConstraints();
			pieceMovePossibilities = new LimitedPawnMovePossibilities();
			newPiece = new LimitedPawn(piece.getId(), piece.getIcon(), player, goalDirection, pieceMovePossibilities, pieceMoveConstraints);
		}else {
			pieceMovePossibilities = new QueenMovePossibilities();
			pieceMoveConstraints =  new QueenMoveConstraints();
			String icon = "Q"+player.getId();
			newPiece = new Queen((player.getId()*7)+player.getId()+6, icon, player, goalDirection, pieceMovePossibilities , pieceMoveConstraints);			
		}
			
		player.removePiece(piece);
		player.addPiece(newPiece);
		
		return newPiece;
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

	public void setup() {
		setupPlayers();
		setupBoardMVC();
		setupPiecesOnBoard();
	}

	private void setupPlayers() {
		numberOfPlayers = gameConfiguration.getNumberOfPlayers();
		numberOfPiecesPerPlayer = gameConfiguration.getNumberOfPiecesPerPlayer();
		playerList = new PlayerList();
		IPlayer player0 = new Player(0, Color.WHITE);
		playerList.addPlayer(player0);
		IPlayer player1 = new Player(1, Color.BLACK);
		playerList.addPlayer(player1);
		currentPlayerID = 0;
		currentPlayer = player0;
	}

	private void setupBoardMVC() {
		board = new ChessBoard();
		coordinatePieceMap = board.getCoordinatePieceMap();
		consoleView = new ChessBoardTestConsoleView(this);
		view = consoleView;
	}

	private void setupPiecesOnBoard() {
		// create pieces for players and put them on board
		IPlayer player;
		AbstractPiece men;
		StartCoordinates startCoordinates = new ChessStartCoordinates();
		IPieceMovePossibilities menMovePossibilities = new PawnMovePossibilities();
		IPieceMoveConstraints menMoveConstraints =  new PawnMoveConstraints();
		IPieceMovePossibilities rookMovePossibilities = new RookMovePossibilities();
		IPieceMoveConstraints rookMoveConstraints =  new RookMoveConstraints();
		IPieceMovePossibilities knightMovePossibilities = new KnightMovePossibilities();
		IPieceMoveConstraints knightMoveConstraints =  new KnightMoveConstraints();
		IPieceMovePossibilities bishopMovePossibilities = new BishopMovePossibilities();
		IPieceMoveConstraints bishopMoveConstraints =  new BishopMoveConstraints();
		IPieceMovePossibilities kingMovePossibilities = new KingMovePossibilities();
		IPieceMoveConstraints kingMoveConstraints =  new KingMoveConstraints();
		IPieceMovePossibilities queenMovePossibilities = new QueenMovePossibilities();
		IPieceMoveConstraints queenMoveConstraints =  new QueenMoveConstraints();

		for (int i = 0; i < numberOfPlayers; i++) {
			player = playerList.getPlayer(i);
			String icon;
			Direction direction;
			if (i == 0) {
				icon = "P0";
				direction = Direction.N;
			}
			else {
				icon = "P1";
				direction = Direction.S;
			}
			//------ should be changed ------
			men = new Rook((i*7)+i+2, "R"+i, player, direction, rookMovePossibilities, rookMoveConstraints);
			player.addPiece(men);
			coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());

			men = new Knight((i*7)+i+3, "k"+i, player, direction, knightMovePossibilities, knightMoveConstraints);
			player.addPiece(men);
			coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());

			men = new Bishop((i*7)+i+4, "B"+i, player, direction, bishopMovePossibilities, bishopMoveConstraints);
			player.addPiece(men);
			coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());

			men = new King((i*7)+i+5, "K"+i, player, direction, kingMovePossibilities, kingMoveConstraints);
			player.addPiece(men);
			coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());

			men = new Queen((i*7)+i+6, "Q"+i, player, direction, queenMovePossibilities, queenMoveConstraints);
			player.addPiece(men);
			coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());

			men = new Bishop((i*7)+i+7, "B"+i, player, direction, bishopMovePossibilities, bishopMoveConstraints);
			player.addPiece(men);
			coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());			

			men = new Knight((i*7)+i+8, "k"+i, player, direction, knightMovePossibilities, knightMoveConstraints);
			player.addPiece(men);
			coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());

			men = new Rook((i*7)+i+9, "R"+i, player, direction, rookMovePossibilities, rookMoveConstraints);
			player.addPiece(men);
			coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());

			for (int j = 0; j < numberOfPiecesPerPlayer/2; j++) {
				men = new Pawn(i, icon, player, direction, menMovePossibilities, menMoveConstraints);
				player.addPiece(men);
				coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());
			}

			//-------------------------------
		}

		//coordinatePieceMap.printPieceMap();
		view.drawBoardView();
		//view.printMessage(playerList.getPlayerStatus());
	}

	protected boolean conductCurrentPlayerTurnAgain(MoveOpResult moveOpResult, AbstractPiece piece) {
		AbstractPiece temp  = piece;
		while (moveOpResult.isCurrentPlayerTurnAgain()) {
			List<ICoordinate> secondJumpList = board.getCBO().findAllowedContinousJumpList(piece);
			board.getCBO().printCoordinateList(secondJumpList, "Second Jump List");
			if (secondJumpList.size() == 0) {
				moveOpResult = new MoveOpResult(false, false);
				break;
			}
			currentMoveCoordinate = consoleView.getNextMove(currentPlayer);
			ICoordinate sourceCoordinate = currentMoveCoordinate.getSourceCoordinate();
			ICoordinate destinationCoordinate = currentMoveCoordinate.getDestinationCoordinate();
			if (!checkMove()) continue;
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

	

}

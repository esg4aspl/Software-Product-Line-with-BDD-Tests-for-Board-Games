package testing.scenariotesters.checkersspanish;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import base.AmericanCheckersBoard;
import base.AmericanCheckersBoardConsoleView;
import base.Pawn;
import base.PawnMoveConstraints;
import base.PawnMovePossibilities;
import base.Player;
import base.PlayerList;
import checkersspanish.Queen;
import checkersspanish.QueenMoveConstraints;
import checkersspanish.QueenMovePossibilities;
import checkersspanish.SpanishGameConfiguration;
import checkersspanish.SpanishStartCoordinates;
import core.AbstractPiece;
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
import core.Zone;
import rules.RuleDestinationCoordinateMustBeValidForCurrentPiece;
import rules.RuleDrawIfNoPromoteForFortyTurn;
import rules.RuleEndOfGameGeneral;
import rules.RuleEndOfGameWhenOpponentBlocked;
import rules.RuleIfAnyPieceCanBeCapturedThenMoveMustBeThat;
import rules.RuleIfJumpMoveThenJumpedPieceMustBeOpponentPiece;
import rules.RuleMoveMustMatchPieceMoveConstraints;
import rules.RulePieceAtSourceCoordinateMustBelongToCurrentPlayer;
import rules.RuleThereMustBePieceAtSourceCoordinate;
import rules.RuleThereMustNotBePieceAtDestinationCoordinate;
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.ICoordinatePieceDuo;
import testing.helpers.SourceCoordinateValidity;
import testing.helpers.TestResult;
import testing.scenariotesters.AbstractTesterReferee;

public class SpanishTesterReferee extends AbstractTesterReferee {

	protected AmericanCheckersBoardConsoleView consoleView;
	protected List<IMoveCoordinate> priorMoves;
	
	public SpanishTesterReferee(IGameConfiguration gameConfiguration) {
		super(gameConfiguration);
		priorMoves = new ArrayList<IMoveCoordinate>();
	}

	//GAMEPLAY METHODS
	
	public void conductGame() {		
		boolean endOfGame = false;
		boolean endOfGameDraw = false;
		IRule noPromoteRule = new RuleDrawIfNoPromoteForFortyTurn();
		prepareRule(noPromoteRule);
//		if (automaticGameOn) {
//			conductAutomaticGame();
//			endOfGame = (isSatisfied(new RuleEndOfGameGeneral(), this) || isSatisfied(new RuleEndOfGameWhenOpponentBlocked(), this));
//			endOfGameDraw = (isSatisfied(noPromoteRule, this));
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
			endOfGameDraw = (isSatisfied(noPromoteRule, this) );
			
			view.printMessage("End Of Game? " + endOfGame);
			if (endOfGame || endOfGameDraw) break;
			
			if (info.isTestAborted()) { return; }
			
			currentPlayerID++;
			if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
			currentPlayer = getPlayerbyID(currentPlayerID);
			
			if (info.isEndOfMoves()) { end(); return; }
		} 
		consoleView.drawBoardView();
		
		if(endOfGameDraw) {
			info.register(TestResult.GAME_END_AS_DRAW);
			view.printMessage("DRAW\n" + announceDraw());
		} else {
			info.register(TestResult.GAME_END_AS_WIN);
			view.printMessage("WINNER " + announceWinner());
		}
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
		AbstractPiece temp  = piece;
		boolean flag = false;		
		IMoveCoordinate rollbackTemp;
		while (moveOpResult.isCurrentPlayerTurnAgain()) {
			List<ICoordinate> jumpList = board.getCBO().findAllowedContinousJumpList(piece);
			List<ICoordinate> secondJumpList = new ArrayList<ICoordinate>();
			rollbackTemp = currentMoveCoordinate;
			Direction lastMoveDirection = board.getCBO().findDirection(currentMoveCoordinate.getSourceCoordinate(),currentMoveCoordinate.getDestinationCoordinate());
			
			for(ICoordinate destinationCoordinate : jumpList) {
				IMoveCoordinate moveCoordinate = new MoveCoordinate(piece.getCurrentCoordinate(), destinationCoordinate);
				List<ICoordinate> path = board.getCBO().findPath(piece, moveCoordinate);
				Direction newDirection = board.getCBO().findDirection(currentMoveCoordinate.getDestinationCoordinate(), destinationCoordinate);
				if(!isCurrentPlayersPieceOnPath(currentPlayer, path) && !lastMoveDirection.getOppositeDirection().equals(newDirection))
					secondJumpList.add(destinationCoordinate);	
			}
				
			if (secondJumpList.size() == 0) {
				info.register(TestResult.MOVE_END_NO_MORE_JUMP_POSSIBILITY);
				moveOpResult = new MoveOpResult(false, false);
				break;
			}
			board.getCBO().printCoordinateList(secondJumpList, "Second Jump List");
			info.register(TestResult.ANOTHER_MOVE_JUMP_POSSIBILITY);
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
					if(!temp.equals(piece)) {
						info.register(TestResult.MOVE_END_CROWNED);
						moveOpResult = new MoveOpResult(true, false);
					}
						
				}
			}
		}
		return true;
	}
	
	
	//TESTER-ONLY METHODS
	
	
	
	@Override
	public void setup(String fileName) {
		info = new SpanishCheckersTestInfo(this, "src/testing/scenariotesters/checkersspanish/SpanishCheckers.ini", fileName);
		setupPlayers();
		setupBoardMVC();
		view = consoleView;
		setupPiecesOnBoard(info.getReader().getCoordinatePieceDuos());
		if (info.getReader().getCurrentTurnPlayerIconColor().equals("black")) {
			currentPlayerID = 0;
			currentPlayer = playerList.getPlayer(0);
		} else {
			currentPlayerID = 1;
			currentPlayer = playerList.getPlayer(1);
		}
	}

	private void setupBoardMVC() {
		//board = new SpanishCheckersBoard();
		//This change is for the reusability of the ini file. It doesn't affect the tests' validity.
		board = new AmericanCheckersBoard();
		
		coordinatePieceMap = board.getCoordinatePieceMap();
		consoleView = new AmericanCheckersBoardConsoleView(this);
		
	}
	
	@Override
	protected void start() {
		super.start();
		System.out.println("Best sequence: " + info.getReader().getBestSequence());
		System.out.println("Prior move sequence: " + info.getReader().getPriorMoveSequence());
	}

	private void setupPiecesOnBoard(List<ICoordinatePieceDuo> coordinatePieceDuos) {
		AbstractPiece men;
		IPlayer player;
		String icon;
		Direction direction;
		int playerId;
		int counter = 0;
		for (ICoordinatePieceDuo coordinatePieceDuo : coordinatePieceDuos) {
			// TODO: Check if the coordinate is empty
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
			// First create the piece as a pawn regardless of its real identity:
			menMovePossibilities = new PawnMovePossibilities();
			menMoveConstraints = new PawnMoveConstraints();
			men = new Pawn(1000 + counter, icon, player, direction, menMovePossibilities, menMoveConstraints);
			player.addPiece(men);

			// If the piece was a king piece, then transform it:
			if (coordinatePieceDuo.getPieceType().equals("queen")) {
				men = becomeNewPiece(player, men);
			}

			coordinatePieceMap.putPieceToCoordinate(men, coordinatePieceDuo.getCoordinate());
			counter++;
		}

	}
	
	private boolean isThereAKingOnBoard() {
		for (IPlayer p : playerList.getPlayers()) {
			for (AbstractPiece piece : p.getPieceList()) {
				if ((piece instanceof Queen) && piece.getCurrentZone() == Zone.ONBOARD)
					return true;
			}
		}
		return false;
	}

	private void prepareRule(IRule noPromoteRule) {
		// loop limit is one more to register the current kings, which resets the turn
		// counter. then it will stop when turns are equal to noPromoteMoveCount.
		int loopLimit = isThereAKingOnBoard() ? info.getNoPromoteMoveCount() + 1 : info.getNoPromoteMoveCount();
		for (int i = 0; i < loopLimit; i++)
			isSatisfied(noPromoteRule, this); // Call noPromoteRule.evaluate 39 times for it to think the next move will
												// be the 40th without promoting.
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
		// Check if the coordinate is of valid square color.
		if (!getBoard().isPlayableCoordinate(sourceCoordinate))
			return SourceCoordinateValidity.NOT_OF_VALID_SQUARE_COLOR;
		// Check if coordinate is empty
		if (piece == null)
			return SourceCoordinateValidity.EMPTY;
		// Check if coordinate has an opponent's piece
		if (!piece.getPlayer().equals(player))
			return SourceCoordinateValidity.OPPONENT_PIECE;

		return SourceCoordinateValidity.VALID;
	}

	@Override
	public DestinationCoordinateValidity checkDestinationCoordinate(IPlayer player, ICoordinate sourceCoordinate,
			ICoordinate destinationCoordinate) {
		SourceCoordinateValidity sourceCoordinateValidity = checkSourceCoordinate(player, sourceCoordinate);
		//If source coordinate is not set up or valid, destination coordinate can't be truly valid.
		if (sourceCoordinateValidity == null || sourceCoordinateValidity != SourceCoordinateValidity.VALID)
			return DestinationCoordinateValidity.SOURCE_COORDINATE_PROBLEM;
		//Set up the destination coordinate.
		int xOfSource = sourceCoordinate.getXCoordinate(); int yOfSource = sourceCoordinate.getYCoordinate();
		AbstractPiece piece = getCoordinatePieceMap().getPieceAtCoordinate(sourceCoordinate);
		int xOfDestination = destinationCoordinate.getXCoordinate(); int yOfDestination = destinationCoordinate.getYCoordinate();
		int xDiff = xOfDestination - xOfSource; int yDiff = yOfDestination - yOfSource;
		//Check if the coordinate is on board.
		if (xOfDestination < 0 ||  7 < xOfDestination || yOfDestination < 0 || 7 < yOfDestination)
			return DestinationCoordinateValidity.OUTSIDE_OF_THE_BOARD;
		//Check if coordinate is the same as source.
		if (destinationCoordinate.equals(sourceCoordinate))
			return DestinationCoordinateValidity.SAME_AS_SOURCE;
		//Check if the coordinate is of valid square color.
		if (Math.abs(xDiff) != Math.abs(yDiff) || !getBoard().isPlayableCoordinate(destinationCoordinate))
			return DestinationCoordinateValidity.NOT_OF_VALID_SQUARE_COLOR;
		//Check if destination coordinate is more than two squares away.
		if ((piece instanceof Pawn) && (Math.abs(xDiff) > 2 || Math.abs(yDiff) > 2))
			return DestinationCoordinateValidity.MORE_THAN_TWO_SQUARES_AWAY_FROM_SOURCE;
		//Check if destination coordinate is occupied.
		if (getCoordinatePieceMap().getPieceAtCoordinate(destinationCoordinate) != null)
			return DestinationCoordinateValidity.OCCUPIED;
		//Check if destination coordinate is not allowed.
		Direction moveDirection = yOfDestination > yOfSource ? Direction.N : Direction.S ;
		if (piece instanceof Pawn && moveDirection != piece.getGoalDirection())
			return DestinationCoordinateValidity.UNALLOWED_DIRECTION;
		//Check if move is part of the best sequence.
		if (!isMovePartOfTheBestSequence(new MoveCoordinate(sourceCoordinate, destinationCoordinate)))
			return DestinationCoordinateValidity.NOT_THE_BEST_SEQUENCE;
		//If there are no problems up to this point, return valid if move is a regular move.
		if (piece instanceof Pawn && Math.abs(xDiff) == 1 && Math.abs(yDiff) == 1)
			return DestinationCoordinateValidity.VALID_REGULAR;
		
		List<ICoordinate> occupiedJumpedCoordinates = findOccupiedJumpedCoordinates(sourceCoordinate, destinationCoordinate);
		//Queen can move multiple squares without jumping.
		if (piece instanceof Queen && occupiedJumpedCoordinates.size() == 0)
			return DestinationCoordinateValidity.VALID_REGULAR;
		//Check if pawn tries to move multiple squares without jumping.
		if (piece instanceof Pawn && occupiedJumpedCoordinates.size() == 0)
			return DestinationCoordinateValidity.JUMPED_PIECE_IS_NULL;
		//Check if there are more than one pieces being jumped.
		if (occupiedJumpedCoordinates.size() > 1)
			return DestinationCoordinateValidity.MULTIPLE_JUMPED_PIECES;
		ICoordinate jumpedCoordinate = occupiedJumpedCoordinates.get(0);
		AbstractPiece jumpedPiece = getCoordinatePieceMap().getPieceAtCoordinate(jumpedCoordinate);
		//Check if jumped piece is player's own piece.
		if (jumpedPiece.getPlayer().equals(player))
			return DestinationCoordinateValidity.JUMPED_PIECE_IS_OWN;
		//Check if destination coordinate is too far away form jumped coordinate.
		if (Math.abs(xOfDestination - jumpedCoordinate.getXCoordinate()) != 1 || Math.abs(yOfDestination - jumpedCoordinate.getYCoordinate()) != 1)
			return DestinationCoordinateValidity.MORE_THAN_ONE_SQUARE_AWAY_FROM_JUMPED_PIECE;
		//If everything is okay up to this point, return valid jump move.
		return DestinationCoordinateValidity.VALID_JUMP;
	}

	public List<ICoordinate> findOccupiedJumpedCoordinates(ICoordinate sourceCoordinate, ICoordinate destinationCoordinate) {
		List<ICoordinate> occupiedCoordinates = new ArrayList<ICoordinate>();
		for (ICoordinate c : findPath(sourceCoordinate, destinationCoordinate)) {
			if (!c.equals(sourceCoordinate) && !c.equals(destinationCoordinate) && getCoordinatePieceMap().getPieceAtCoordinate(c) != null) {
				occupiedCoordinates.add(c);
			}
		}
		return occupiedCoordinates;
	}
	
	private List<ICoordinate> findPath(ICoordinate sourceCoordinate, ICoordinate destinationCoordinate) {
		int xSource = sourceCoordinate.getXCoordinate();
		int ySource = sourceCoordinate.getYCoordinate();
		int xDestination = destinationCoordinate.getXCoordinate();
		int yDestination = destinationCoordinate.getYCoordinate();
		int diff = Math.abs(xDestination - xSource);
		int xStep = xDestination - xSource > 0 ? 1 : -1;
		int yStep = yDestination - ySource > 0 ? 1 : -1;
		int xCurrent = xSource; int yCurrent = ySource;
		List<ICoordinate> path = new ArrayList<ICoordinate>();
		for (int i = 0; i <= diff; i++) {
			path.add(new Coordinate(xCurrent, yCurrent));
			xCurrent+=xStep; yCurrent+=yStep;
		}
		return path;
	}
	
	private boolean isMovePartOfTheBestSequence(IMoveCoordinate move) {
		List<IMoveCoordinate> bestSequence = info.getReader().getBestSequence();
		if (bestSequence == null || bestSequence.size() == 0)
			return true;
		
		if (bestSequence.get(priorMoves.size()).equals(move))
			return true;
		
		return false;
	}

	public List<IMoveCoordinate> getPriorMoves() {
		return priorMoves;
	}
	
	public void setPriorMoves(List<IMoveCoordinate> priorMoves) {
		this.priorMoves = priorMoves;
	}
	
	// MAIN METHOD

	public static void main(String[] args) {
		String[] iniArr = {
				"endOfTheGameInDrawFortyIndecisiveMoves2"
		};
		for (String iniName : iniArr ) {
			AbstractTesterReferee ref = new SpanishTesterReferee(new SpanishGameConfiguration());
			ref.setup(iniName);
			ref.conductGame();
			System.out.println(ref.getInfo().getSourceCoordinateValidity());
			System.out.println(ref.getInfo().getDestinationCoordinateValidity());
			System.out.println(ref.getInfo().toString());
		}
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

//	private void setupBoardMVC() {
//		board = new SpanishCheckersBoard();
//		coordinatePieceMap = board.getCoordinatePieceMap();
//		consoleView = new AmericanCheckersBoardConsoleView(this);
//		
//	}

	private void setupPiecesOnBoard() {
		// create pieces for players and put them on board
		IPlayer player;
		AbstractPiece men;
		SpanishStartCoordinates startCoordinates = new SpanishStartCoordinates();
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
//		view.printMessage(playerList.getPlayerStatus());
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
		IPieceMovePossibilities queenMovePossibilities = new QueenMovePossibilities();
		IPieceMoveConstraints queenMoveConstraints =  new QueenMoveConstraints();
		String icon;
		if (player.getId() == 0) icon = "A";
		else icon = "Z";
		AbstractPiece king = new Queen(pieceID+2, icon, player, goalDirection, queenMovePossibilities, queenMoveConstraints);
		player.addPiece(king);
		return king;
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
	
	public IPlayer announceWinner() {
		return playerList.getPlayer(currentPlayerID);
	}
	
	protected AbstractPiece becomeAndOrPutOperation(AbstractPiece piece, ICoordinate destinationCoordinate) {
		//check the piece is already queen or not
		if(!(piece instanceof Queen))
			if ((piece.getGoalDirection() == Direction.N && destinationCoordinate.getYCoordinate() == 7)
					|| (piece.getGoalDirection() == Direction.S && destinationCoordinate.getYCoordinate() == 0)){
				IPlayer player = piece.getPlayer();
				piece = becomeNewPiece(player, piece);
			}
		coordinatePieceMap.putPieceToCoordinate(piece, destinationCoordinate);
		return piece;
	}
	
	public IPlayerList announceDraw(){
		return playerList;
	}

}

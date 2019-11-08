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
import checkersturkish.TurkishGameConfiguration;
import checkersturkish.TurkishKingMoveConstraints;
import checkersturkish.TurkishKingMovePossibilities;
import checkersturkish.TurkishPawnConstraints;
import checkersturkish.TurkishPawnPossibilities;
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
import rules.RuleEndOfGameIfEachPlayerHasOnePiece;
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
import testing.scenariotesters.checkersspanish.SpanishCheckersTestInfo;

public class TurkishTesterReferee extends AbstractTesterReferee {
	
	protected AmericanCheckersBoardConsoleView consoleView;

	public TurkishTesterReferee(IGameConfiguration gameConfiguration) {
		super(gameConfiguration);
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
		recordMove(currentMoveCoordinate);
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
					recordMove(currentMoveCoordinate);
				}
			}
		}
		return true;
	}
	
	
	//TESTER-ONLY METHODS
	
	private void prepareRule(IRule noPromoteRule) {
		// loop limit is one more to register the current kings, which resets the turn
		// counter. then it will stop when turns are equal to noPromoteMoveCount.
		int loopLimit = isThereAKingOnBoard() ? info.getNoPromoteMoveCount() + 1 : info.getNoPromoteMoveCount();
		for (int i = 0; i < loopLimit; i++)
			isSatisfied(noPromoteRule, this); // Call noPromoteRule.evaluate 39 times for it to think the next move will
												// be the 40th without promoting
	}
	
	private boolean isThereAKingOnBoard() {
		for (IPlayer p : playerList.getPlayers()) {
			for (AbstractPiece piece : p.getPieceList()) {
				if ((piece instanceof King) && piece.getCurrentZone() == Zone.ONBOARD)
					return true;
			}
		}
		return false;
	}
	
	@Override
	public void setup(String fileName) {
		info = new TurkishCheckersTestInfo(this, "src/testing/scenariotesters/checkersturkish/TurkishCheckers.ini", fileName);
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
			menMovePossibilities = new TurkishPawnPossibilities();
			menMoveConstraints = new TurkishPawnConstraints();
			men = new Pawn(1000 + counter, icon, player, direction, menMovePossibilities, menMoveConstraints);
			player.addPiece(men);

			// If the piece was a king piece, then transform it:
			if (coordinatePieceDuo.getPieceType().equals("king")) {
				men = becomeNewPiece(player, men);
			}

			coordinatePieceMap.putPieceToCoordinate(men, coordinatePieceDuo.getCoordinate());
			counter++;
		}

	}
	
	@Override
	protected void start() {
		super.start();
		System.out.println("Best sequence: " + info.getReader().getBestSequence());
		System.out.println("Prior move sequence: " + info.getReader().getPriorMoveSequence());
		System.out.println("Resulting Board State Will Be Reached For The Third Time: " + ((TurkishCheckersTestInfo)info).isResultingBoardStateWillBeReachedForTheThirdTime());
	}
	
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
		if (isPieceAtSourceNotPawnInCrownhead(player, sourceCoordinate))
			return SourceCoordinateValidity.NOT_THE_PAWN_IN_CROWNHEAD;
		if (this.isSourceCoordinateDifferentThanLastJumpMoveDestinationCoordinate(sourceCoordinate))
			return SourceCoordinateValidity.DIFFERENT_THAN_LAST_JUMP_MOVE_DESTINATION;

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
		//Check if the move is orthogonal.
		if (xDiff != 0 && yDiff != 0)
			return DestinationCoordinateValidity.UNALLOWED_DIRECTION;
			//return DestinationCoordinateValidity.NOT_ORTHOGONAL;
		//Check if destination coordinate is more than two squares away.
		if ((piece instanceof Pawn) && (Math.abs(xDiff) > 2 || Math.abs(yDiff) > 2))
			return DestinationCoordinateValidity.MORE_THAN_TWO_SQUARES_AWAY_FROM_SOURCE;
		//Check if destination coordinate is occupied.
		if (getCoordinatePieceMap().getPieceAtCoordinate(destinationCoordinate) != null)
			return DestinationCoordinateValidity.OCCUPIED;
		//Check if destination coordinate is not allowed.
		Direction moveDirection = yOfDestination > yOfSource ? Direction.N : Direction.S ;
		if (piece instanceof Pawn && yDiff != 0 && moveDirection != piece.getGoalDirection())
			return DestinationCoordinateValidity.UNALLOWED_DIRECTION;
		if (isPawnInCrownheadIllegalMove(player, new MoveCoordinate(sourceCoordinate, destinationCoordinate)))
			return DestinationCoordinateValidity.PAWN_IN_CROWNHEAD_ILLEGAL_MOVE;
		//Check if move is part of the best sequence.
		if (!isMovePartOfTheBestSequence(new MoveCoordinate(sourceCoordinate, destinationCoordinate)))
			return DestinationCoordinateValidity.NOT_THE_BEST_SEQUENCE;
		//If there are no problems up to this point, return valid if move is a regular move.
		if (piece instanceof Pawn && (Math.abs(xDiff) == 1 || Math.abs(yDiff) == 1))
			return DestinationCoordinateValidity.VALID_REGULAR;
		
		List<ICoordinate> occupiedJumpedCoordinates = findOccupiedJumpedCoordinates(sourceCoordinate, destinationCoordinate);
		//Queen can move multiple squares without jumping.
		if (piece instanceof King && occupiedJumpedCoordinates.size() == 0)
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
		//If everything is okay up to this point, return valid jump move.
		return DestinationCoordinateValidity.VALID_JUMP;
	}
	
	public List<AbstractPiece> findPlayerPawnsInOpponentCrownhead(IPlayer player) {
		List<AbstractPiece> pawnsInCrownhead = new ArrayList<AbstractPiece>();
		int opponentCrownheadY = player.getId() == 0 ? 7 : 0;
		for (int x = 0; x <=7; x++) {
			AbstractPiece p = coordinatePieceMap.getPieceAtCoordinate(new Coordinate(x, opponentCrownheadY));
			if (p != null && p.getPlayer().equals(player) && p instanceof Pawn)
				pawnsInCrownhead.add(p);
		}
		return pawnsInCrownhead;
	}
	
	public boolean isPieceAtSourceNotPawnInCrownhead(IPlayer player, ICoordinate src) {
		AbstractPiece piece = coordinatePieceMap.getPieceAtCoordinate(src);
		
		//Find all pawns in opponent's crownhead. There can be maximum 1 though.
		List<AbstractPiece> pawnsInCrownhead = findPlayerPawnsInOpponentCrownhead(player);
		
		//If player has no pawns in opponent's crownhead, then the move can not be illegal in this aspect.
		if (pawnsInCrownhead.size() == 0)
			return false;
		
		//If player has a pawn in opponent's crownhead, but the chosen piece is not that, then the move is illegal.
		if (pawnsInCrownhead.size() == 1 && piece.equals(pawnsInCrownhead.get(0)))
			return false;
		
		return true;
	}
	
	public boolean isPawnInCrownheadIllegalMove(IPlayer player, IMoveCoordinate move) {
		ICoordinate src = move.getSourceCoordinate();
		
		if (isPieceAtSourceNotPawnInCrownhead(player, src))
			return true;
		
		List<IMoveCoordinate> jumpMovesForPawnInCrownhead = findJumpMovesForPawnInCrownhead(player, src);
		//If the pawn in opponent's crownhead does not have any possible jump moves (there is no vulnerable opponent king near), then the move can not be illegal in this aspect.
		if (jumpMovesForPawnInCrownhead.size() == 0)
			return false;
		//If the move is one of the legal moves, then 
		for (IMoveCoordinate m : jumpMovesForPawnInCrownhead) {
			if (m.equals(move))
				return false;
		}
		
		return true;
		
	}
	
	private List<IMoveCoordinate> findJumpMovesForPawnInCrownhead(IPlayer player, ICoordinate source) {
		List<IMoveCoordinate> result = new ArrayList<IMoveCoordinate>();
		AbstractPiece piece = this.coordinatePieceMap.getPieceAtCoordinate(source);
		if (!(piece instanceof Pawn) || !piece.getPlayer().equals(player))
			return result;
		int srcY = source.getYCoordinate();
		int srcX = source.getXCoordinate();
		for (int i = -1; i <= 1; i+=2) {
			int jumpedX = srcX + i;
			int landedX = srcX + i*2;
			if (jumpedX < 0 || jumpedX > 7 || landedX < 0 || landedX > 7) 
				continue;
			ICoordinate jumpedCoord = new Coordinate(jumpedX, srcY);
			ICoordinate possibleDest = new Coordinate(landedX, srcY);
			AbstractPiece jumpedPiece = coordinatePieceMap.getPieceAtCoordinate(jumpedCoord);
			if (jumpedPiece != null && !jumpedPiece.getPlayer().equals(player)
					&& jumpedPiece instanceof King && coordinatePieceMap.getPieceAtCoordinate(possibleDest) == null)
				result.add(new MoveCoordinate(source, possibleDest));
		}
		return result;
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
		if (diff == 0) { diff = Math.abs(yDestination - ySource); }
		int xStep = xDestination - xSource > 0 ? 1 : -1;
		int yStep = yDestination - ySource > 0 ? 1 : -1;
		if (xDestination == xSource) xStep = 0;
		if (yDestination == ySource) yStep = 0;
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
	
	//MAIN METHOD
	public static void main(String[] args) {
		String[] iniArr = {
				"validJumpMove16",
				"validJumpMove17"
		};
		for (String iniName : iniArr ) {
			AbstractTesterReferee ref = new TurkishTesterReferee(new TurkishGameConfiguration());
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
//	    view.printMessage(playerList.getPlayerStatus());
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

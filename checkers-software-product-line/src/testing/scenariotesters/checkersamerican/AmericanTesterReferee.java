package testing.scenariotesters.checkersamerican;

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
import base.StartCoordinates;
import checkersamerican.AmericanStartCoordinates;
import checkersamerican.King;
import checkersamerican.KingMoveConstraints;
import checkersamerican.KingMovePossibilities;
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
import rules.RuleEndOfGameNoPieceCapturedForFortyTurn;
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

public class AmericanTesterReferee extends AbstractTesterReferee {

	protected AmericanCheckersBoardConsoleView consoleView;

	public AmericanTesterReferee(IGameConfiguration checkersGameConfiguration) {
		super(checkersGameConfiguration);
	}

	//GAMEPLAY METHODS
	
	public void conductGame() {
		boolean endOfGame = false;
		boolean endOfGameDraw = false;

		IRule noPromoteRule = new RuleDrawIfNoPromoteForFortyTurn();
		IRule noPieceCapturedForFortyTurn = new RuleEndOfGameNoPieceCapturedForFortyTurn();
		prepareRules(noPromoteRule, noPieceCapturedForFortyTurn);
//		if (automaticGameOn) {
//			conductAutomaticGame();
//			endOfGame = (isSatisfied(new RuleEndOfGameGeneral(), this) || isSatisfied(new RuleEndOfGameWhenOpponentBlocked(), this));
//			endOfGameDraw = (isSatisfied(noPromoteRule, this) || isSatisfied(noPieceCapturedForFortyTurn, this));
//			view.printMessage("End Of Game? " + endOfGame);
//		}
		if (!endOfGame) {

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

			endOfGame = (isSatisfied(new RuleEndOfGameGeneral(), this)
					|| isSatisfied(new RuleEndOfGameWhenOpponentBlocked(), this));
			endOfGameDraw = (isSatisfied(noPromoteRule, this) || isSatisfied(noPieceCapturedForFortyTurn, this));

			view.printMessage("End Of Game? " + endOfGame);
			if (endOfGame || endOfGameDraw)
				break;

			if (info.isTestAborted())
				return;
			
			currentPlayerID++;
			if (currentPlayerID >= numberOfPlayers)
				currentPlayerID = 0;
			currentPlayer = getPlayerbyID(currentPlayerID);
			if (info.isEndOfMoves()) { end(); return; }
		}
		// consoleView.drawBoardView();

		if (endOfGameDraw) {
			info.register(TestResult.GAME_END_AS_DRAW);
			view.printMessage("DRAW\n" + announceDraw());
		} else {
			info.register(TestResult.GAME_END_AS_WIN);
			view.printMessage("WINNER " + announceWinner());
		}

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
		List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);
		coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
		MoveOpResult moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
		// if piece become king then terminate the move
		AbstractPiece temp = piece;
		piece = becomeAndOrPutOperation(piece, destinationCoordinate);
		recordMove(currentMoveCoordinate);
		if (!temp.equals(piece)) {
			info.register(TestResult.MOVE_END_CROWNED);
			moveOpResult = new MoveOpResult(true, false);
		}
		view.printMessage("CurrentPlayerTurnAgain? " + moveOpResult.isCurrentPlayerTurnAgain());
		if (moveOpResult.isCurrentPlayerTurnAgain() && !automaticGameOn)
			conductCurrentPlayerTurnAgain(moveOpResult, piece);
		return true;
	}

	protected boolean conductCurrentPlayerTurnAgain(MoveOpResult moveOpResult, AbstractPiece piece) {
		AbstractPiece temp = piece;
		IMoveCoordinate rollbackTemp;
		while (moveOpResult.isCurrentPlayerTurnAgain()) {
			List<ICoordinate> secondJumpList = board.getCBO().findAllowedContinousJumpList(piece);
			rollbackTemp = currentMoveCoordinate;
			if (secondJumpList.size() == 0) {
				moveOpResult = new MoveOpResult(false, false);
				info.register(TestResult.MOVE_END_NO_MORE_JUMP_POSSIBILITY);
				break;
			}
			info.register(TestResult.ANOTHER_MOVE_JUMP_POSSIBILITY);
			currentMoveCoordinate = getNextMove();
			if (currentMoveCoordinate == null) { abort(); return true; }
			ICoordinate sourceCoordinate = currentMoveCoordinate.getSourceCoordinate();
			ICoordinate destinationCoordinate = currentMoveCoordinate.getDestinationCoordinate();

			if (!checkMove()) {
				info.register(TestResult.ANOTHER_DESTINATION_INVALID);
				currentMoveCoordinate = rollbackTemp;
				continue;
			}

			moveOpResult = new MoveOpResult(false, false);
			for (ICoordinate coordinate : secondJumpList) {
				if (coordinate.equals(destinationCoordinate)) {
					List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);
					coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
					moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
					piece = becomeAndOrPutOperation(piece, destinationCoordinate);
					recordMove(currentMoveCoordinate);
					if (!temp.equals(piece)) {
						info.register(TestResult.MOVE_END_CROWNED);
						moveOpResult = new MoveOpResult(true, false); 
					}
				}
			}

		}
		return true;
	}

	//TESTER-ONLY METHODS
	
	public void setup(String fileName) {
		info = new AmericanCheckersTestInfo(this, "src/testing/scenariotesters/checkersamerican/AmericanCheckers.ini", fileName);
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
		int pieceId;
		int p0Counter = 0;
		int p1Counter = 0;
		for (ICoordinatePieceDuo coordinatePieceDuo : coordinatePieceDuos) {
			if (coordinatePieceMap.getPieceAtCoordinate(coordinatePieceDuo.getCoordinate()) != null) {
				System.out.println("The coordinate is occupied: " + coordinatePieceDuo.getCoordinate().toString());
				System.exit(0);
			}

			if (!board.isPlayableCoordinate(coordinatePieceDuo.getCoordinate())) {
				System.out.println("A piece can not stand there: " + coordinatePieceDuo.getCoordinate().toString());
				System.exit(0);
			}
			String iconName = coordinatePieceDuo.getIconColor();
			if (iconName.equals("black")) {
				playerId = 0;
				p0Counter += 5;
				pieceId = p0Counter + 1000;
				player = playerList.getPlayer(playerId);
				icon = "B";
				direction = Direction.N;
			} else {
				playerId = 1;
				p1Counter += 5;
				pieceId = p1Counter + 2000;
				player = playerList.getPlayer(playerId);
				icon = "W";
				direction = Direction.S;
			}
			IPieceMovePossibilities menMovePossibilities;
			IPieceMoveConstraints menMoveConstraints;
			// First create the piece as a pawn regardless of its real identity:
			menMovePossibilities = new PawnMovePossibilities();
			menMoveConstraints = new PawnMoveConstraints();
			men = new Pawn(pieceId, icon, player, direction, menMovePossibilities, menMoveConstraints);
			player.addPiece(men);

			// If the piece was a king piece, then transform it:
			if (coordinatePieceDuo.getPieceType().equals("king")) {
				men = becomeNewPiece(player, men);
			}
			coordinatePieceMap.putPieceToCoordinate(men, coordinatePieceDuo.getCoordinate());
		}

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

	private void prepareRules(IRule noPromoteRule, IRule noPieceCapturedForFortyTurn) {
		// loop limit is one more to register the current kings, which resets the turn
		// counter. then it will stop when turns are equal to noPromoteMoveCount.
		int loopLimit = isThereAKingOnBoard() ? info.getNoPromoteMoveCount() + 1 : info.getNoPromoteMoveCount();
		for (int i = 0; i < loopLimit; i++)
			isSatisfied(noPromoteRule, this); // Call noPromoteRule.evaluate 39 times for it to think the next move will
												// be the 40th without promoting.

		for (int i = 0; i < info.getNoCaptureMoveCount() + 1; i++)
			isSatisfied(noPieceCapturedForFortyTurn, this);
	}

	private List<IMoveCoordinate> findPossibleJumpMoves(IPlayer player) {
		List<IMoveCoordinate> possibleJumpMoves = new ArrayList<IMoveCoordinate>();
		for (AbstractPiece anyPlayerPiece : player.getPieceList()) {
			List<ICoordinate> relativeCoordsWithOpponentPiece = new ArrayList<ICoordinate>();
			List<ICoordinate> relativeCoords = new ArrayList<ICoordinate>();
			if (anyPlayerPiece instanceof King) {
				relativeCoords.add(new Coordinate(-1, 1));
				relativeCoords.add(new Coordinate(1, 1));
				relativeCoords.add(new Coordinate(-1, -1));
				relativeCoords.add(new Coordinate(1, -1));
			} else if (anyPlayerPiece.getGoalDirection() == Direction.N) {
				relativeCoords.add(new Coordinate(-1, 1));
				relativeCoords.add(new Coordinate(1, 1));
			} else {
				relativeCoords.add(new Coordinate(-1, -1));
				relativeCoords.add(new Coordinate(1, -1));
			}
			for (ICoordinate relativeCoord : relativeCoords) {
				AbstractPiece adjacentPiece = getCoordinatePieceMap().getPieceAtCoordinate(new Coordinate(
						anyPlayerPiece.getCurrentCoordinate().getXCoordinate() + relativeCoord.getXCoordinate(),
						anyPlayerPiece.getCurrentCoordinate().getYCoordinate() + relativeCoord.getYCoordinate()));
				if (adjacentPiece != null && !adjacentPiece.getPlayer().equals(player))
					relativeCoordsWithOpponentPiece.add(relativeCoord);
			}
			for (ICoordinate relativeCoordWithOpponentPiece : relativeCoordsWithOpponentPiece) {
				ICoordinate possibleJumpMoveDestinationCoordinate = new Coordinate(
						anyPlayerPiece.getCurrentCoordinate().getXCoordinate()
								+ relativeCoordWithOpponentPiece.getXCoordinate() * 2,
						anyPlayerPiece.getCurrentCoordinate().getYCoordinate()
								+ relativeCoordWithOpponentPiece.getYCoordinate() * 2);
				if (possibleJumpMoveDestinationCoordinate.getXCoordinate() >= 0
						&& possibleJumpMoveDestinationCoordinate.getXCoordinate() <= 7
						&& possibleJumpMoveDestinationCoordinate.getYCoordinate() >= 0
						&& possibleJumpMoveDestinationCoordinate.getYCoordinate() <= 7
						&& getCoordinatePieceMap().getPieceAtCoordinate(possibleJumpMoveDestinationCoordinate) == null)
					possibleJumpMoves.add(new MoveCoordinate(anyPlayerPiece.getCurrentCoordinate(),
							possibleJumpMoveDestinationCoordinate));
			}
		}
		return possibleJumpMoves;
	}

	private boolean isMoveOneOfPossibleJumpMoves(IMoveCoordinate move) {
		List<IMoveCoordinate> possibleJumpMoves = findPossibleJumpMoves(currentPlayer);
		if (possibleJumpMoves.size() == 0)
			return true;
		for (IMoveCoordinate m : possibleJumpMoves) {
			if (m.equals(move))
				return true;
		}
		return false;
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
		if (this.isSourceCoordinateDifferentThanLastJumpMoveDestinationCoordinate(sourceCoordinate))
			return SourceCoordinateValidity.DIFFERENT_THAN_LAST_JUMP_MOVE_DESTINATION;

		return SourceCoordinateValidity.VALID;
	}

	@Override
	public DestinationCoordinateValidity checkDestinationCoordinate(IPlayer player, ICoordinate sourceCoordinate,
			ICoordinate destinationCoordinate) {

		SourceCoordinateValidity sourceCoordinateValidity = checkSourceCoordinate(player, sourceCoordinate);
		// If source coordinate is not set up or valid, destination coordinate can't be
		// truly valid.
		if (sourceCoordinateValidity == null || sourceCoordinateValidity != SourceCoordinateValidity.VALID)
			return DestinationCoordinateValidity.SOURCE_COORDINATE_PROBLEM;
		// Set up the destination coordinate.
		int xOfSource = sourceCoordinate.getXCoordinate();
		int yOfSource = sourceCoordinate.getYCoordinate();
		AbstractPiece piece = getCoordinatePieceMap().getPieceAtCoordinate(sourceCoordinate);
		int xOfDestination = destinationCoordinate.getXCoordinate();
		int yOfDestination = destinationCoordinate.getYCoordinate();
		int xDiff = xOfDestination - xOfSource;
		int yDiff = yOfDestination - yOfSource;
		// Check if the coordinate is on board.
		if (xOfDestination < 0 || 7 < xOfDestination || yOfDestination < 0 || 7 < yOfDestination)
			return DestinationCoordinateValidity.OUTSIDE_OF_THE_BOARD;
		// Check if coordinate is the same as source.
		if (destinationCoordinate.equals(sourceCoordinate))
			return DestinationCoordinateValidity.SAME_AS_SOURCE;
		// Check if the coordinate is of valid square color.
		if (!getBoard().isPlayableCoordinate(destinationCoordinate) || Math.abs(xDiff) != Math.abs(yDiff))
			return DestinationCoordinateValidity.NOT_OF_VALID_SQUARE_COLOR;
		// Check if destination coordinate is more than two squares away.
		if (Math.abs(xDiff) > 2 || Math.abs(yDiff) > 2)
			return DestinationCoordinateValidity.MORE_THAN_TWO_SQUARES_AWAY_FROM_SOURCE;
		// Check if destination coordinate is occupied.
		if (this.getCoordinatePieceMap().getPieceAtCoordinate(destinationCoordinate) != null)
			return DestinationCoordinateValidity.OCCUPIED;
		// Check if destination coordinate is not allowed.
		Direction moveDirection = yOfDestination > yOfSource ? Direction.N : Direction.S;
		if (piece instanceof Pawn && moveDirection != piece.getGoalDirection())
			return DestinationCoordinateValidity.UNALLOWED_DIRECTION;
		//Check if opposite of last jump move.
		if (this.isMoveOppositeDirectionOfLastJumpMove(sourceCoordinate, destinationCoordinate))
			return DestinationCoordinateValidity.OPPOSITE_DIRECTION_OF_LAST_JUMP_MOVE;
		// Check if move is not one of possible jump moves.
		if (!isMoveOneOfPossibleJumpMoves(new MoveCoordinate(sourceCoordinate, destinationCoordinate)))
			return DestinationCoordinateValidity.NOT_ONE_OF_POSSIBLE_JUMP_MOVES;
		// If there are no problems up to this point, return valid if move is a regular
		// move.
		if (Math.abs(xDiff) == 1 && Math.abs(yDiff) == 1)
			return DestinationCoordinateValidity.VALID_REGULAR;
		// Set up jumped piece.
		ICoordinate jumpedCoordinate = new Coordinate(xOfSource + xDiff / 2, yOfSource + yDiff / 2);
		AbstractPiece jumpedPiece = getCoordinatePieceMap().getPieceAtCoordinate(jumpedCoordinate);
		// Check if jumped piece is null.
		if (jumpedPiece == null)
			return DestinationCoordinateValidity.JUMPED_PIECE_IS_NULL;
		// Check if jumped piece is player's own piece.
		if (jumpedPiece.getPlayer().equals(player))
			return DestinationCoordinateValidity.JUMPED_PIECE_IS_OWN;
		// If everything is okay up to this point, double-check that the xDiff and yDiff
		// are 2 and return valid jump move.
		if (Math.abs(xDiff) == 2 && Math.abs(yDiff) == 2) {
			return DestinationCoordinateValidity.VALID_JUMP;
		}

		return DestinationCoordinateValidity.UNKNOWN_ERROR;
	}

	// MAIN METHOD
	
	public static void main(String[] args) {

			String[] moveArr = {
					"validJumpMove1",
					};

			for (String moveID : moveArr) {
				System.out.println("\n\n\nTESTING: " + moveID);
				AmericanTesterReferee referee = new AmericanTesterReferee(new AmericanGameConfiguration());
				referee.setup(moveID);
				referee.conductGame();
				System.out.println(referee.getInfo().getSourceCoordinateValidity());
				System.out.println(referee.getInfo().getDestinationCoordinateValidity());
			}

		}

	//UNTOUCHED METHODS
	
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
	}

	private void setupPiecesOnBoard() {
		// create pieces for players and put them on board
		IPlayer player;
		AbstractPiece men;
		StartCoordinates startCoordinates = new AmericanStartCoordinates();
		IPieceMovePossibilities menMovePossibilities = new PawnMovePossibilities();
		IPieceMoveConstraints menMoveConstraints = new PawnMoveConstraints();

		for (int i = 0; i < numberOfPlayers; i++) {
			player = playerList.getPlayer(i);
			String icon;
			Direction direction;
			if (i == 0) {
				icon = "B";
				direction = Direction.N;
			} else {
				icon = "W";
				direction = Direction.S;
			}
			for (int j = 0; j < numberOfPiecesPerPlayer; j++) {
				men = new Pawn(1000 + i, icon, player, direction, menMovePossibilities, menMoveConstraints);
				player.addPiece(men);
				coordinatePieceMap.putPieceToCoordinate(men, startCoordinates.getNextCoordinate());
			}
		}

		// coordinatePieceMap.printPieceMap();
		// view.printMessage(playerList.getPlayerStatus());
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

	protected MoveOpResult moveInterimOperation(AbstractPiece piece, IMoveCoordinate moveCoordinate,
			List<ICoordinate> path) {
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
		// check the piece is already king or not
		if (!(piece instanceof King))
			if ((piece.getGoalDirection() == Direction.N && destinationCoordinate.getYCoordinate() == 7)
					|| (piece.getGoalDirection() == Direction.S && destinationCoordinate.getYCoordinate() == 0)) {
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
		IPieceMoveConstraints kingMoveConstraints = new KingMoveConstraints();
		String icon;
		if (player.getId() == 0)
			icon = "A";
		else
			icon = "Z";
		AbstractPiece king = new King(pieceID + 2, icon, player, goalDirection, kingMovePossibilities,
				kingMoveConstraints);
		player.addPiece(king);
		return king;
	}

	public IPlayer announceWinner() {
		return playerList.getPlayer(currentPlayerID);
	}

	public IPlayerList announceDraw() {
		return playerList;
	}

	public IPlayer getCurrentPlayer() {
		if (currentPlayerID >= numberOfPlayers)
			currentPlayerID = 0;
		return playerList.getPlayer(currentPlayerID);
	}

	public IPlayer getNextPlayer() {
		int nextPlayerID = currentPlayerID + 1;
		if (nextPlayerID >= numberOfPlayers)
			nextPlayerID = 0;
		return playerList.getPlayer(nextPlayerID);
	}

}

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
import core.Coordinate;
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
import testing.helpers.DestinationCoordinateValidity;
import testing.helpers.ICoordinatePieceDuo;
import testing.helpers.IniReader;
import testing.helpers.SourceCoordinateValidity;
import testing.helpers.TestResult;
import testing.scenariotesters.AbstractTesterReferee;
import testing.scenariotesters.checkersamerican.AmericanCheckersTestInfo;
import testing.scenariotesters.checkersamerican.AmericanTesterReferee;

public class ChildrenTesterReferee extends AbstractTesterReferee {

	protected AmericanCheckersBoardConsoleView consoleView;

	public ChildrenTesterReferee(IGameConfiguration gameConfiguration) {
		super(gameConfiguration);
	}

	// GAMEPLAY METHODS

	@Override
	public void conductGame() {
		boolean endOfGame = false;

//		if (automaticGameOn) {
//			conductAutomaticGame();
//			endOfGame = isSatisfied(new RuleEndOfGameChildren(), this);
//			System.out.println("End Of Game? " + endOfGame);
//		}
		if (!endOfGame) {
			start();
			System.out.println(playerList.getPlayerStatus());
			System.out.println("Game begins ...");
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
			//Reset currentMoveCoordinate to the last move (if it is null, playerMove was the last move), rule needs it.
			if (currentMoveCoordinate == null) currentMoveCoordinate = info.getPlayerMove();
			endOfGame = isSatisfied(new RuleEndOfGameChildren(), this);
			System.out.println("End Of Game? " + endOfGame);
			if (endOfGame) {
				info.register(TestResult.GAME_END_AS_WIN);
				break;
			}
			
			if (info.isTestAborted()) { return; }

			currentPlayerID++;
			if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
			currentPlayer = getPlayerbyID(currentPlayerID);
			
			if (info.isEndOfMoves()) { end(); return; }
		}
		end();
		return;
//		announceWinner();
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
				continue;
			}
			moveOpResult = new MoveOpResult(false, false);
			for(ICoordinate coordinate : secondJumpList) {
				if (coordinate.equals(destinationCoordinate)) {
					List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);
					coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
					moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
					piece = becomeAndOrPutOperation(piece, destinationCoordinate);
				}
			}
		}
		return true;
	}

	// TESTER-ONLY METHODS

	@Override
	public void setup(String fileName) {
		info = new AmericanCheckersTestInfo(this, "src/testing/scenariotesters/checkerschildren/ChildrenCheckers.ini",
				fileName);
		setupPlayers();
		setupBoardMVC();
		view = consoleView;
		setupPiecesOnBoard(info.getReader().getCoordinatePieceDuos());
		String currentTurnPlayerIconColor = info.getReader().getCurrentTurnPlayerIconColor();

		if (isReversed())
			currentTurnPlayerIconColor = currentTurnPlayerIconColor.equals("black") ? "white" : "black";

		if (currentTurnPlayerIconColor.equals("black")) {
			currentPlayerID = 0;
			currentPlayer = playerList.getPlayer(0);
		} else {
			currentPlayerID = 1;
			currentPlayer = playerList.getPlayer(1);
		}
	}

	@Override
	public void start() {
		super.start();
		view.printMessage("Reversed: " + isReversed());
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
			// First create the piece as a pawn regardless of its real identity:
			menMovePossibilities = new PawnMovePossibilities();
			menMoveConstraints = new PawnMoveConstraints();
			men = new Pawn(1000 + counter, icon, player, direction, menMovePossibilities, menMoveConstraints);
			player.addPiece(men);

			coordinatePieceMap.putPieceToCoordinate(men, coordinatePieceDuo.getCoordinate());
			counter++;
		}

	}

	private boolean isReversed() {
		if (!info.getReader().hasExtras())
			return false;

		for (String e : info.getReader().getExtras()) {
			if (e.equals("reversed"))
				return true;
		}

		return false;
	}

	// IMPLEMENTED METHODS

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
		// If source coordinate is not set up or valid, destination coordinate can't be
		// truly valid.
		if (sourceCoordinateValidity == null || sourceCoordinateValidity != SourceCoordinateValidity.VALID)
			return DestinationCoordinateValidity.SOURCE_COORDINATE_PROBLEM;
		// Set up the destination coordinate.
		int xOfSource = sourceCoordinate.getXCoordinate();
		int yOfSource = sourceCoordinate.getYCoordinate();
		AbstractPiece piece = coordinatePieceMap.getPieceAtCoordinate(sourceCoordinate);
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
		if (coordinatePieceMap.getPieceAtCoordinate(destinationCoordinate) != null)
			return DestinationCoordinateValidity.OCCUPIED;
		// Check if destination coordinate is not allowed.
		Direction moveDirection = yOfDestination > yOfSource ? Direction.N : Direction.S;
		if (piece instanceof Pawn && moveDirection != piece.getGoalDirection())
			return DestinationCoordinateValidity.UNALLOWED_DIRECTION;
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

	// UNTOUCHED METHODS

	public void setup() {
		setupPlayers();
		setupBoardMVC();
		setupPiecesOnBoard();
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

	protected boolean checkMove() {
		return isSatisfied(new RuleThereMustBePieceAtSourceCoordinate(), this)
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

	public IPlayer announceWinner() {
		return playerList.getPlayer(currentPlayerID);
	}

	public IPlayer getCurrentPlayer() {
		if (currentPlayerID >= numberOfPlayers)
			currentPlayerID = 0;
		return playerList.getPlayer(currentPlayerID);
	}

	// MAIN METHOD

	public static void main(String[] args) {
		String[] moveArr = {
				"validJumpMove4",
				"validJumpMove5",
				"validJumpMove6",
				"validJumpMove7",
				"validJumpMove8",
				"validJumpMove9",
				"validJumpMove10",
				"validJumpMove11",
				"validJumpMove12"
			};
		
//		String[] moveArr = {
//				"crowningTheEligiblePiece3",
//				"crowningTheEligiblePiece4",
//				"crowningTheEligiblePiece5",
//				"crowningTheEligiblePiece6",
//				"crowningTheEligiblePiece7",
//				"crowningTheEligiblePiece8",
//				"crowningTheEligiblePiece9"
//		};

		for (String moveID : moveArr) {
			System.out.println("\n\n\nTESTING: " + moveID);
			AbstractTesterReferee referee = new ChildrenTesterReferee(new AmericanGameConfiguration());
			referee.setup(moveID);
			referee.conductGame();
		}
	}

}

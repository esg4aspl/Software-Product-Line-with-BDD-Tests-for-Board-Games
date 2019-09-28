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
import testing.helpers.ICoordinatePieceDuo;
import testing.helpers.IiniReader;
import testing.helpers.IniReader;

public class AmericanTesterReferee extends AbstractReferee {

	IMoveCoordinate playerMove;
	IiniReader reader;
	String setUpName;
	List<String> informers;
	String endTestStatus;
	boolean playerWasGoingToMakeAnotherMove;
	IPlayer winner;
	IPlayer loser;
	boolean isDraw;
	boolean gameEnded;
	int noPromoteMoveCount, noCaptureMoveCount;

	protected AmericanCheckersBoardConsoleView consoleView;
	
	public AmericanTesterReferee(IGameConfiguration checkersGameConfiguration) {
		super(checkersGameConfiguration);
		this.playerWasGoingToMakeAnotherMove = false;
		informers = new ArrayList<String>();
		gameEnded = false;
		endTestStatus = "Test running...";
	}
	
	public void setIni(String setUpName) {
		reader = new IniReader("src/testing/scenariotesters/checkersamerican/AmericanCheckers.ini", setUpName);
	}
	
	public void setGameSetupName(String setUpName) {
		this.setUpName = setUpName;
	}
	
	public void defaultSetup() {
		setupPlayers();
		setupBoardMVC();
		setupPiecesOnBoard();
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
		noPromoteMoveCount = getMoveCount("noPromote");
		noCaptureMoveCount = getMoveCount("noCapture");
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
		//view.printMessage(playerList.getPlayerStatus());
	}
	
	public IMoveCoordinate readPlayerMove() {
		playerMove = reader.getPlayerMove();
		return playerMove;
	}
	
	private boolean isThereAKingOnBoard() {
		for (IPlayer p : playerList.getPlayers()) {
			for (AbstractPiece piece : p.getPieceList()) {
				if ( (piece instanceof King) && piece.getCurrentZone() == Zone.ONBOARD)
					return true;
			}
		}
		return false;
	}
	
	public void conductGame() {	
		boolean endOfGame = false;
		boolean endOfGameDraw = false;

		IRule noPromoteRule = new RuleDrawIfNoPromoteForFortyTurn();
		IRule noPieceCapturedForFortyTurn = new RuleEndOfGameNoPieceCapturedForFortyTurn();
		
		
		//loop limit is one more to register the current kings, which resets the turn counter. then it will stop when turns are equal to noPromoteMoveCount.
		int loopLimit = isThereAKingOnBoard() ? noPromoteMoveCount+1 : noPromoteMoveCount;
		for (int i = 0; i < loopLimit; i++)
			isSatisfied(noPromoteRule, this); //Call noPromoteRule.evaluate 39 times for it to think the next move will be the 40th without promoting.

		
		for (int i = 0; i < noCaptureMoveCount+1; i++)
			isSatisfied(noPieceCapturedForFortyTurn, this);
		
		
		
//		if (automaticGameOn) {
//			conductAutomaticGame();
//			endOfGame = (isSatisfied(new RuleEndOfGameGeneral(), this) || isSatisfied(new RuleEndOfGameWhenOpponentBlocked(), this));
//			endOfGameDraw = (isSatisfied(noPromoteRule, this) || isSatisfied(noPieceCapturedForFortyTurn, this));
//			printMessage("End Of Game? " + endOfGame);
//		}
		if(!endOfGame) {
			
			view.printMessage("Game begins ...");
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
				this.playerWasGoingToMakeAnotherMove = true;
				printMessage("Player will be asked for another source coordinate (previous move was invalid)...");
				
				
				endTest("Test ended with an invalid player move.");
				return;
//				playerMove = view.getNextMove(currentPlayer);
//				currentMoveCoordinate = playerMove;			
			}
			consoleView.drawBoardView();
			boolean noPromoteDraws = false;
			boolean noCaptureDraws = false;
			endOfGame = (isSatisfied(new RuleEndOfGameGeneral(), this) || isSatisfied(new RuleEndOfGameWhenOpponentBlocked(), this));
			endOfGameDraw = ((noPromoteDraws = isSatisfied(noPromoteRule, this)) || (noCaptureDraws = isSatisfied(noPieceCapturedForFortyTurn, this)));
			
//			view.printMessage("End Of Game? " + endOfGame);
			if (endOfGame || endOfGameDraw) {
				if (noPromoteDraws)
					printMessage("The game ended as a draw because there have been no promoting in the last 40 moves.");
				else if (noCaptureDraws)
					printMessage("The game ended as a draw because there have been no capturing in the last 40 moves.");
				this.gameEnded = true;
				break;
			}
			
			
			
			if (!playerWasGoingToMakeAnotherMove) {
				currentPlayerID++;
				if (currentPlayerID >= numberOfPlayers) currentPlayerID = 0;
				currentPlayer = getPlayerbyID(currentPlayerID);
			}
			
			//ONLY IN TESTER CLASS. END THE GAME AFTER PLAYER'S MOVE THAT THE TEST IS FOCUSING ON.
			endTest("Test ended with a valid player move.");
			return;
		} 
		//consoleView.drawBoardView();
		
		if(endOfGameDraw) {
			isDraw = true;
			winner = null;
			loser = null;
			printMessage("DRAW\n" + announceDraw());
		} else {
			isDraw = false;
			winner = currentPlayer;
			loser = getOtherPlayer();
			printMessage("WINNER " + announceWinner());
		}
		
		endTest("Test ended with a valid player move. The game ended.");
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
		if (!checkMove()) return false;
		List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);
		coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
		MoveOpResult moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
		//if piece become king then terminate the move
		AbstractPiece  temp  = piece;
		piece = becomeAndOrPutOperation(piece, destinationCoordinate);
		if(!temp.equals(piece)) {
			printMessage("The piece has become king.");
			if (moveOpResult.isCurrentPlayerTurnAgain()) {
				printMessage("Player will NOT be asked for another destination coordinate (previous move was a jump move) because the piece has become king.");
			}
			moveOpResult = new MoveOpResult(true, false);
		}
		view.printMessage("CurrentPlayerTurnAgain? " + moveOpResult.isCurrentPlayerTurnAgain());
		if (moveOpResult.isCurrentPlayerTurnAgain() && !automaticGameOn) {
			conductCurrentPlayerTurnAgain(moveOpResult, piece);
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
				//ONLY IN TESTER CLASS
				printMessage("Player will NOT be asked for another destination coordinate (previous move was a jump move) because there are no more possibilities for a jump move.");
				break;
			}
			//ONLY IN TESTER CLASS, RETURNS FROM THIS METHOD AFTER INFORMING
			this.playerWasGoingToMakeAnotherMove = true;
			printMessage("Player will be asked for another destination coordinate (previous move was a jump move) (there are still possibilities for a jump move).");
			break;
//			playerMove = view.getNextMove(currentPlayer,currentMoveCoordinate.getDestinationCoordinate());
//			currentMoveCoordinate = playerMove;
//			ICoordinate sourceCoordinate = currentMoveCoordinate.getSourceCoordinate();
//			ICoordinate destinationCoordinate = currentMoveCoordinate.getDestinationCoordinate();
//			
//			if (!checkMove()) { 
//				currentMoveCoordinate = rollbackTemp;
//				continue;
//			}
//			
//			moveOpResult = new MoveOpResult(false, false);
//			for(ICoordinate coordinate : secondJumpList) {
//				if (coordinate.equals(destinationCoordinate)) {
//					List<ICoordinate> path = board.getCBO().findPath(piece, currentMoveCoordinate);
//					coordinatePieceMap.removePieceFromCoordinate(piece, sourceCoordinate);
//					moveOpResult = moveInterimOperation(piece, currentMoveCoordinate, path);
//					piece = becomeAndOrPutOperation(piece, destinationCoordinate);
//					if(!temp.equals(piece)) 
//						moveOpResult = new MoveOpResult(true, false);
//				}
//			}
//			
//			
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
		informers.add(message);
	}

	public void endTest(String status) {
		endTestStatus = status;
		System.out.println("\n\n-----------------------------------TEST RESULTS--------------------------------------------------------------------------------------------");
		System.out.println("Test: " + setUpName);
		System.out.println("Status: " + endTestStatus);
		System.out.println("Informers: " + informers.toString());
		System.out.println("Was player going to make another move?: " + this.playerWasGoingToMakeAnotherMove);
		System.out.println("End of the game? " + this.gameEnded);
		if (gameEnded)
			System.out.println("isDraw?: " + this.isDraw + ", Winner: " + winner + ", Loser: " + loser);
		System.out.println("Board: ");
		view.drawBoardView();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------\n");
	}
	
	private IPlayer getOtherPlayer() {
		int currentPlayerId = this.currentPlayer.getId();
		int otherPlayerId = currentPlayerId+1;
		if (otherPlayerId >= getNumberOfPlayers()) 
			otherPlayerId = 0;
		return getPlayerbyID(otherPlayerId);
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
	
	
//	private void conductAutomaticGame() {				
//		printMessage("Automatic Game begins ...");
//		automaticGameOn = true;
//		int step = 0;
//		consoleView.drawBoardView();
//		while (step < consoleView.getSizeOfAutomaticMoveList()) {
//			try {
//				Thread.sleep(400);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			currentMove = consoleView.getNextAutomaticMove(step);
//			currentMoveCoordinate = currentMove.getMoveCoordinate();
//			currentPlayer = currentMove.getPlayer();
//			currentPlayerID = currentPlayer.getId();
//			conductMove();
//			consoleView.drawBoardView();
//			step++;
//		}
//		automaticGameOn = false;
//		printMessage("Automatic Game ends ...");
//	}
	
	//FOR MANUAL TESTING
	public static void main(String[] args) {
		//String[] moveArr = {"invalidDestinationCoordinateForMoveJumpedPieceIsNull1", "invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1"};
		//String[] moveArr = {"invalidDestinationCoordinateForMoveOutsideBorders1", "invalidDestinationCoordinateForMoveUnplayableColor1"};
		//String[] moveArr= {"invalidDestinationCoordinateForMoveOccupied1"};
		//String[] moveArr = {"validRegularMove5", "validJumpMove9", "validRegularMove4"};
		
		//String[] moveArr = {"validJumpMove11", "validJumpMove12", "validJumpMove13", "validJumpMove14"};
		//String[] moveArr = {"crowningTheEligiblePiece6", "crowningTheEligiblePiece7", "crowningTheEligiblePiece8"};
		String[] moveArr = {"crowningTheEligiblePiece6"};
		
		//String[] moveArr = {"crowningTheEligiblePiece1", "crowningTheEligiblePiece2", "crowningTheEligiblePiece3", "crowningTheEligiblePiece4"};
		//String[] moveArr = {"invalidSourceCoordinateForMoveOpponentsPiece1", "invalidSourceCoordinateForMoveOpponentsPiece2"};
 		//String[] moveArr = {"invalidSourceCoordinateForMoveUnplayableColor1"};
		//String[] moveArr = {"invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves1", "invalidDestinationCoordinateForMoveJumpedPieceIsNull1", "invalidDestinationCoordinateForMoveJumpedPieceIsOwnPiece1"};
		//MoveArr for tests using the "usefulBoard1"
		//String[] moveArr = {"validJumpMove6", "validJumpMove7", "validJumpMove8", "invalidSourceCoordinateForMoveOpponentsPiece3", "invalidSourceCoordinateForMoveEmpty3", "invalidSourceCoordinateForMoveUnplayableColor2", "invalidSourceCoordinateForMoveOutsideBorders2", "invalidDestinationCoordinateForMoveOutsideBorders2", "invalidDestinationCoordinateForMoveUnallowedDirection2", "invalidDestinationCoordinateForMoveUnallowedDirection3", "invalidDestinationCoordinateForMoveOccupied2", "invalidDestinationCoordinateForMoveOccupied3", "invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves2", "invalidDestinationCoordinateForMoveNotOneOfTheJumpMoves3", "invalidDestinationCoordinateForMoveUnplayableColor2", "invalidDestinationCoordinateForMoveTooFarAway2", "crowningTheEligiblePiece5"};                                                    
		
		for (String moveID : moveArr) {
			System.out.println("\n\n\nTESTING: " + moveID);
			AmericanTesterReferee referee = new AmericanTesterReferee(new AmericanGameConfiguration());
			referee.setGameSetupName(moveID);
			referee.setup();
			referee.readPlayerMove();
			referee.conductGame();
		}
		
	}
	
	
}

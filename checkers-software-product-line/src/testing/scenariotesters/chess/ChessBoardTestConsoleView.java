package testing.scenariotesters.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import base.Move;
import core.AbstractBoard;
import core.AbstractMove;
import core.AbstractPiece;
import core.AbstractReferee;
import core.Coordinate;
import core.CoordinatePieceMap;
import core.ICoordinate;
import core.IMoveCoordinate;
import core.IPlayer;
import core.IView;
import core.MoveCoordinate;
import utils.MoveFileReader;
import utils.MoveFileWriter;

public class ChessBoardTestConsoleView implements IView {

	protected AbstractReferee referee;
	protected AbstractBoard checkersBoard;
	protected CoordinatePieceMap coordinatePieceMap;
	protected String[][] boardMatrixView;
	protected List<AbstractMove> automaticMoveList;
	protected Scanner scanner;
	protected MoveFileWriter moveFileWriter;
	protected MoveFileReader moveFileReader;

	public ChessBoardTestConsoleView(AbstractReferee referee) {
		this.referee = referee;
		checkersBoard = referee.getBoard();
		coordinatePieceMap = checkersBoard.getCoordinatePieceMap();
		scanner = new Scanner(System.in);
		moveFileWriter = new MoveFileWriter();
		moveFileReader = new MoveFileReader();
		readAutomaticMoveList();
		initView();
	}

	private void readAutomaticMoveList() {
		automaticMoveList = new ArrayList<>();
		moveFileReader.readMoveListFromFile(referee, automaticMoveList);
	}

	public int getSizeOfAutomaticMoveList() {
		return automaticMoveList.size();
	}
	
	private void initView() {
		// board lower left has coordinate (0,0)
		boardMatrixView = new String[][]
				{ 
			{"  ","  ","  ","  ","  ","  ","  ","  "},
			{"  ","  ","  ","  ","  ","  ","  ","  "},
			{"  ","  ","  ","  ","  ","  ","  ","  "},
			{"  ","  ","  ","  ","  ","  ","  ","  "},
			{"  ","  ","  ","  ","  ","  ","  ","  "},
			{"  ","  ","  ","  ","  ","  ","  ","  "},
			{"  ","  ","  ","  ","  ","  ","  ","  "},
			{"  ","  ","  ","  ","  ","  ","  ","  "},
				};
	}

	public void drawBoardView() {
		String lineDraw       = "   --- --- --- --- --- --- --- ---";
		String coordinateDraw = "    0   1   2   3   4   5   6   7";
		System.out.println(coordinateDraw);
		System.out.println(lineDraw);
		for (int i = 7; i >= 0; i--) {
			System.out.println(lineBuilder(i));
			System.out.println(lineDraw);
		}
		System.out.println(coordinateDraw);
	}

	private String lineBuilder(int lineNumber) {
		String breaker = " | ";
		String line = lineNumber + " | ";
		for (int i = 0; i < 8; i++)
			line = line + getIcon(i, lineNumber) + breaker;
		line = line + lineNumber;
		return line;
	}

	private String getIcon(int x, int y) {
		ICoordinate coordinate = new Coordinate(x, y);
		AbstractPiece piece = coordinatePieceMap.getPieceAtCoordinate(coordinate);
		if (piece == null) return " ";
		return(iconConverter(piece.getIcon()));
	}
	
	private ICoordinate inputHelper(String message) {
		System.out.println(message);
		String coordinate = scanner.nextLine(); 
		
		//this pattern provide the input (number)(any number of non numerical character)(number)
		Pattern patternForInput = Pattern.compile("\\d+\\D+\\d+");
		//this is used to provide the extract numbers from string
		Pattern extract = Pattern.compile("\\d+");
		
		Matcher matchForInput = patternForInput.matcher(coordinate);
		boolean flagForinput = matchForInput.matches();
		while(!flagForinput) {
			System.out.println("Pattern for input is (Number)(Any number of non-numeric character)(Number)");
			System.out.println(message);
			coordinate = scanner.nextLine(); 
			matchForInput = patternForInput.matcher(coordinate);
			flagForinput = matchForInput.matches();
		}
		
		Matcher matcherNumeric = extract.matcher(coordinate);
		matcherNumeric.find();
		int coordinatex = Integer.parseInt(matcherNumeric.group());
		matcherNumeric.find();
		int coordinatey = Integer.parseInt(matcherNumeric.group());
		return new Coordinate(coordinatex, coordinatey);
	}
	
	public IMoveCoordinate getNextMove(IPlayer currentPlayer, ICoordinate newSourceCoordinate) {
		boolean flag = false;
		IMoveCoordinate moveCoordinate = null;
		while(!flag) {
			flag = true;
			ICoordinate sourceCoordinate = newSourceCoordinate;
			ICoordinate destinationCoordinate = inputHelper("Enter Destination Coordinate x,y for Player " + currentPlayer.getId());
			int scx = sourceCoordinate.getXCoordinate();
			int scy = sourceCoordinate.getYCoordinate();
			int dcx = sourceCoordinate.getXCoordinate();
			int dcy = sourceCoordinate.getYCoordinate();
	
			moveCoordinate = new MoveCoordinate(sourceCoordinate, destinationCoordinate);		
			AbstractMove move = new Move(currentPlayer, moveCoordinate);
			
			if (scx==9 && scy==9 && dcx==9 && dcy==9) {
				moveFileWriter.closeFile();
				System.exit(0);
			}
			else 
			{
				if(checkersBoard.isCoordinateOnBoard(sourceCoordinate) && checkersBoard.isCoordinateOnBoard(destinationCoordinate)) 
					moveFileWriter.writeMoveToFile(move);
				else {
					flag = false;   
					System.out.println("Coordinate must be on the board!");
				}
						
			}
		}
		return moveCoordinate;
	}
	
	
	public IMoveCoordinate getNextMove(IPlayer currentPlayer) {
		boolean flag = false;
		IMoveCoordinate moveCoordinate = null;
		while(!flag) {
			flag = true;
			ICoordinate sourceCoordinate = inputHelper("Enter Source Coordinate x,y for Player " + currentPlayer.getId());
			ICoordinate destinationCoordinate = inputHelper("Enter Destination Coordinate x,y for Player " + currentPlayer.getId());
			int scx = sourceCoordinate.getXCoordinate();
			int scy = sourceCoordinate.getYCoordinate();
			int dcx = sourceCoordinate.getXCoordinate();
			int dcy = sourceCoordinate.getYCoordinate();
	
			moveCoordinate = new MoveCoordinate(sourceCoordinate, destinationCoordinate);		
			AbstractMove move = new Move(currentPlayer, moveCoordinate);
			
			if (scx==9 && scy==9 && dcx==9 && dcy==9) {
				moveFileWriter.closeFile();
				System.exit(0);
			}
			else 
			{
				if(checkersBoard.isCoordinateOnBoard(sourceCoordinate) && checkersBoard.isCoordinateOnBoard(destinationCoordinate)) 
					moveFileWriter.writeMoveToFile(move);
				else {
					flag = false;   
					System.out.println("Coordinate must be on the board!");
				}
						
			}
		}
		return moveCoordinate;
	}

	public AbstractMove getNextAutomaticMove(int step) {
		AbstractMove move = automaticMoveList.get(step);
		moveFileWriter.writeMoveToFile(move);
		return move;
	}

	public void closeFile() {
		moveFileWriter.closeFile();
	}

	
	@Override
	public void printMessage(String message) {
		System.out.println(message);
	}
	
	private String iconConverter(String originalIcon) {
		switch(originalIcon) {
		case "K0": return "♔";
		case "Q0": return "♕";
		case "R0": return "♖";
		case "B0": return "♗";
		case "k0": return "♘";
		case "P0": return "♙";
		case "K1": return "♚";
		case "Q1": return "♛";
		case "R1": return "♜";
		case "B1": return "♝";
		case "k1": return "♞";
		case "P1": return "♟";
		case "W0": return "♙";
		default: return "U";
		}
	}

}

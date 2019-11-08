package testing.scenariotesters.checkerschinese;

import base.ChineseCheckersBoardConsoleView;
import core.AbstractPiece;
import core.AbstractReferee;
import core.Coordinate;
import core.ICoordinate;

public class ChineseCheckersBoardTestConsoleView extends ChineseCheckersBoardConsoleView {

	public ChineseCheckersBoardTestConsoleView(AbstractReferee referee) {
		super(referee);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawBoardView() {
		String lineDraw       = "                                                                ";
		String coordinateDraw = "   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4";
		System.out.println(coordinateDraw);
		System.out.println(lineDraw);
		for (int i = 16; i >= 0; i--) {
			if(i<10)
				System.out.print(" ");
			System.out.println(lineBuilder(i));
			System.out.println(lineDraw);
		}
		System.out.println(coordinateDraw);
	}

	private String lineBuilder(int lineNumber) {
		String breaker = " ";
		String line = lineNumber + " ";
		
		for (int i = 0; i < 25; i++) {
			line = line + getIcon(i, lineNumber) + breaker;
		}
			
		line = line + lineNumber;
		return line;
	}
	
	private String getIcon(int x, int y) {
		ICoordinate coordinate = new Coordinate(x, y);
		AbstractPiece piece = coordinatePieceMap.getPieceAtCoordinate(coordinate);
		if (piece == null)
			if(checkersBoard.isPlayableCoordinate(new Coordinate(x, y)))
				return "○";
			else
				return "·";
		return piece.getIcon();
	}
	
	

}

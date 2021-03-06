package chess;

import java.util.ArrayList;
import java.util.List;

import core.*;

public class QueenMovePossibilities implements IPieceMovePossibilities {

	public List<ICoordinate> getPossibleRelativeDestinationList(ICoordinate sourceCoordinate, Direction direction) {
		List<ICoordinate> possibleDestinationList = new ArrayList<>();
		
		// single step possible moves
		possibleDestinationList.add(new Coordinate(0,1));
		possibleDestinationList.add(new Coordinate(0,2));
		possibleDestinationList.add(new Coordinate(0,3));
		possibleDestinationList.add(new Coordinate(0,4));
		possibleDestinationList.add(new Coordinate(0,5));
		possibleDestinationList.add(new Coordinate(0,6));
		possibleDestinationList.add(new Coordinate(0,7));
		
		possibleDestinationList.add(new Coordinate(0,-1));
		possibleDestinationList.add(new Coordinate(0,-2));
		possibleDestinationList.add(new Coordinate(0,-3));
		possibleDestinationList.add(new Coordinate(0,-4));
		possibleDestinationList.add(new Coordinate(0,-5));
		possibleDestinationList.add(new Coordinate(0,-6));
		possibleDestinationList.add(new Coordinate(0,-7));
		
		possibleDestinationList.add(new Coordinate(1,0));
		possibleDestinationList.add(new Coordinate(2,0));
		possibleDestinationList.add(new Coordinate(3,0));
		possibleDestinationList.add(new Coordinate(4,0));
		possibleDestinationList.add(new Coordinate(5,0));
		possibleDestinationList.add(new Coordinate(6,0));
		possibleDestinationList.add(new Coordinate(7,0));
		
		possibleDestinationList.add(new Coordinate(-1,0));
		possibleDestinationList.add(new Coordinate(-2,0));
		possibleDestinationList.add(new Coordinate(-3,0));
		possibleDestinationList.add(new Coordinate(-4,0));
		possibleDestinationList.add(new Coordinate(-5,0));
		possibleDestinationList.add(new Coordinate(-6,0));
		possibleDestinationList.add(new Coordinate(-7,0));
		
		possibleDestinationList.add(new Coordinate(-1,-1));
		possibleDestinationList.add(new Coordinate(-2,-2));
		possibleDestinationList.add(new Coordinate(-3,-3));
		possibleDestinationList.add(new Coordinate(-4,-4));
		possibleDestinationList.add(new Coordinate(-5,-5));
		possibleDestinationList.add(new Coordinate(-6,-6));
		possibleDestinationList.add(new Coordinate(-7,-7));
		
		possibleDestinationList.add(new Coordinate(1,1));
		possibleDestinationList.add(new Coordinate(2,2));
		possibleDestinationList.add(new Coordinate(3,3));
		possibleDestinationList.add(new Coordinate(4,4));
		possibleDestinationList.add(new Coordinate(5,5));
		possibleDestinationList.add(new Coordinate(6,6));
		possibleDestinationList.add(new Coordinate(7,7));
		
		possibleDestinationList.add(new Coordinate(1,-1));
		possibleDestinationList.add(new Coordinate(2,-2));
		possibleDestinationList.add(new Coordinate(3,-3));
		possibleDestinationList.add(new Coordinate(4,-4));
		possibleDestinationList.add(new Coordinate(5,-5));
		possibleDestinationList.add(new Coordinate(6,-6));
		possibleDestinationList.add(new Coordinate(7,-7));
		
		possibleDestinationList.add(new Coordinate(-1,1));
		possibleDestinationList.add(new Coordinate(-2,2));
		possibleDestinationList.add(new Coordinate(-3,3));
		possibleDestinationList.add(new Coordinate(-4,4));
		possibleDestinationList.add(new Coordinate(-5,5));
		possibleDestinationList.add(new Coordinate(-6,6));
		possibleDestinationList.add(new Coordinate(-7,7));


		return possibleDestinationList;
	}

}


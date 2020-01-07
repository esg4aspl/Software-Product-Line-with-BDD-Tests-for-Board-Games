package testing.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import core.Coordinate;
import core.ICoordinate;
import core.IMoveCoordinate;
import core.MoveCoordinate;
import cucumber.api.PendingException;

public class IniReader implements IniReaderInterface {
	
	private Ini ini;
	private String fileLocation, sectionName;
	private Section section;
	private Section board;
	private IMoveCoordinate playerMove;
	private String currentTurnPlayerIconColor;
	private String[] extras;
	private List<ICoordinatePieceDuo> coordinatePieceDuos;
	private List<IMoveCoordinate> priorMoveSequence;
	private List<IMoveCoordinate> bestSequence;
	private IMoveCoordinate expectedMove;
	
	public IniReader(String fileLocation, String sectionName) {
		this.fileLocation = fileLocation;
		this.sectionName = sectionName;
		try {
			ini = new Ini(new File(fileLocation));
			section = ini.get(sectionName);
			board = ini.get(section.get("boardSetUp"));
			playerMove = parseMove(section.get("playerMove"));
			priorMoveSequence = parseMoveSequence(section.get("priorMoveSequence"));
			bestSequence = parseMoveSequence(section.get("bestSequence"));
			expectedMove = parseMove(section.get("expectedMove"));
			
			currentTurnPlayerIconColor = section.get("turn");
			String extrasString = section.get("extras");
			if (extrasString != null)
				extras = extrasString.split(",");
			coordinatePieceDuos = new ArrayList<ICoordinatePieceDuo>();
			parseCoordinatePieceDuos();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new PendingException("Ini file is not ready for this test.");
		}
	}
	
	private IMoveCoordinate parseMove(String moveStr) {
		if (moveStr == null)
			return null;
		String[] playerMoveCoordinatesString = moveStr.split(">");
		String[] sourceCoordinateString = playerMoveCoordinatesString[0].split(",");
		String[] destinationCoordinateString = playerMoveCoordinatesString[1].split(",");
		ICoordinate source = new Coordinate(Integer.parseInt(sourceCoordinateString[0]), Integer.parseInt(sourceCoordinateString[1]));
		ICoordinate destination = new Coordinate(Integer.parseInt(destinationCoordinateString[0]), Integer.parseInt(destinationCoordinateString[1]));
		return new MoveCoordinate(source, destination);
	}
	
	private List<IMoveCoordinate> parseMoveSequence(String seqStr) {
		List<IMoveCoordinate> sequence = new ArrayList<IMoveCoordinate>();
		if (seqStr == null)
			return sequence;
		String[] moveStrings = seqStr.split(":");
		for (String moveString : moveStrings)
			sequence.add(parseMove(moveString));
		return sequence;
	}

	private void parseCoordinatePieceDuos() {
		for (String coordinateString : board.keySet()) {
			String[] coordinates = coordinateString.split(",");
			ICoordinate coordinate = new Coordinate(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
			String[] values = board.get(coordinateString).split("-");
			String iconColor = values[0];
			String pieceType = values[1];
			coordinatePieceDuos.add(new CoordinatePieceDuo(coordinate, iconColor, pieceType));
		}
	}
	
	@Override
	public List<ICoordinatePieceDuo> getCoordinatePieceDuos() {
		return coordinatePieceDuos;
	}

	@Override
	public IMoveCoordinate getPlayerMove() {
		return playerMove;
	}

	@Override
	public String getCurrentTurnPlayerIconColor() {
		return currentTurnPlayerIconColor;
	}
	
	@Override
	public String getFileLocation() {
		return fileLocation;
	}

	@Override
	public String getSectionName() {
		return sectionName;
	}

	@Override
	public String[] getExtras() {
		return extras;
	}

	@Override
	public boolean hasExtras() {
		if (extras == null)
			return false;
		return true;
	}
	
	public List<IMoveCoordinate> getPriorMoveSequence() {
		return priorMoveSequence;
	}

	public static void main(String[] args) {
		IniReaderInterface reader = new IniReader("src/testing/helpers/ExampleIni.ini", "exampleMove");
		System.out.println(reader.getCoordinatePieceDuos().toString());
		System.out.println(reader.getPlayerMove().toString());
		System.out.println(reader.getPriorMoveSequence().toString());
		System.out.println(reader.getCurrentTurnPlayerIconColor());
		if (reader.hasExtras()) {
			for (String extra : reader.getExtras())
				System.out.println(extra);
		} else {
			System.out.println("extras is null");
		}
	}

	@Override
	public List<IMoveCoordinate> getBestSequence() {
		return bestSequence;
	}

	
	@Override
	public IMoveCoordinate getExpectedMove() {
		return expectedMove;
	}


	

}

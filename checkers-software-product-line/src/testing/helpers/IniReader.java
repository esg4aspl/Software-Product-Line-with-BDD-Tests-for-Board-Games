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

public class IniReader implements IiniReader {
	
	private Ini ini;
	private String fileLocation, sectionName;
	private Section section;
	private Section board;
	private IMoveCoordinate playerMove;
	private String currentTurnPlayerIconColor;
	private List<ICoordinatePieceDuo> coordinatePieceDuos;
	
	public IniReader(String fileLocation, String sectionName) {
		this.fileLocation = fileLocation;
		this.sectionName = sectionName;
		try {
			ini = new Ini(new File(fileLocation));
			section = ini.get(sectionName);
			board = ini.get(section.get("boardSetUp"));
			parsePlayerMove();
			currentTurnPlayerIconColor = section.get("turn");
			coordinatePieceDuos = new ArrayList<ICoordinatePieceDuo>();
			parseCoordinatePieceDuos();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new PendingException("Ini file is not ready for this test.");
		}
	}
	
	private void parsePlayerMove() {
		String playerMoveString = section.get("playerMove");
		String[] playerMoveCoordinatesString = playerMoveString.split(">");
		String[] sourceCoordinateString = playerMoveCoordinatesString[0].split(",");
		String[] destinationCoordinateString = playerMoveCoordinatesString[1].split(",");
		ICoordinate source = new Coordinate(Integer.parseInt(sourceCoordinateString[0]), Integer.parseInt(sourceCoordinateString[1]));
		ICoordinate destination = new Coordinate(Integer.parseInt(destinationCoordinateString[0]), Integer.parseInt(destinationCoordinateString[1]));
		playerMove = new MoveCoordinate(source, destination);
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

	public static void main(String[] args) {
		IiniReader reader = new IniReader("src/testing/helpers/AmericanCheckers.ini", "moveList");
		System.out.println(reader.getCoordinatePieceDuos().toString());
		System.out.println(reader.getPlayerMove().toString());
		System.out.println(reader.getCurrentTurnPlayerIconColor());
	}

}

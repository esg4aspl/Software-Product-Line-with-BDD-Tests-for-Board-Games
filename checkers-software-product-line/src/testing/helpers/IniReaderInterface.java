package testing.helpers;

import java.util.List;

import core.IMoveCoordinate;

public interface IniReaderInterface {
	
	public List<ICoordinatePieceDuo> getCoordinatePieceDuos();
	public IMoveCoordinate getPlayerMove();
	public String getCurrentTurnPlayerIconColor();
	public String getFileLocation();
	public String getSectionName();
	public String[] getExtras();
	public boolean hasExtras();
	public IMoveCoordinate getExpectedMove();
	public List<IMoveCoordinate> getPriorMoveSequence();
	public List<IMoveCoordinate> getBestSequence();
	
}

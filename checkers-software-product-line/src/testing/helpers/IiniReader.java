package testing.helpers;

import java.util.List;

import core.IMoveCoordinate;

public interface IiniReader {
	
	public List<ICoordinatePieceDuo> getCoordinatePieceDuos();
	public IMoveCoordinate getPlayerMove();
	public String getCurrentTurnPlayerIconColor();
	public String getFileLocation();
	public String getSectionName();
	public String[] getExtras();
	public boolean hasExtras();
	
}

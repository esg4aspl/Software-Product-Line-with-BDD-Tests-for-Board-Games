package testing.helpers;

import core.ICoordinate;

public class CoordinatePieceDuo implements ICoordinatePieceDuo {

	private ICoordinate coordinate;
	private String iconColor;
	private String pieceType;
	
	public CoordinatePieceDuo(ICoordinate coordinate, String iconColor, String pieceType) {
		this.coordinate = coordinate;
		this.iconColor = iconColor;
		this.pieceType = pieceType;
	}
	
	@Override
	public ICoordinate getCoordinate() {
		return coordinate;
	}

	@Override
	public String getIconColor() {
		return iconColor;
	}

	@Override
	public String getPieceType() {
		return pieceType;
	}

	@Override
	public String toString() {
		return "CoordinatePieceDuo [coordinate=" + coordinate + ",\n iconColor=" + iconColor + ",\n pieceType=" + pieceType
				+ "]";
	}
	
	

}

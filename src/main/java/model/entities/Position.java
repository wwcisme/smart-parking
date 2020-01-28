package model.entities;

public class Position {

	public String positionId;
	
	public int coordinateX;
	
	public int coordinateY;
	
	public PositionType positionType;
	
	public String parkId;
	
	public int parkFloor;

	@Override
	public boolean equals(Object obj) {
		Position positionObj = (Position) obj;
		if (positionObj == null || positionObj.getClass() != this.getClass())
			return false;
		
		return (positionObj.getCoordinateX() == coordinateX) && (positionObj.getCoordinateY() == coordinateY) &&
				(positionObj.getParkId() == parkId) && (positionObj.getParkFloor() == parkFloor);
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public int getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
	}

	public int getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
	}

	public PositionType getPositionType() {
		return positionType;
	}

	public void setPositionType(PositionType positionType) {
		this.positionType = positionType;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public int getParkFloor() {
		return parkFloor;
	}

	public void setParkFloor(int parkFloor) {
		this.parkFloor = parkFloor;
	}

	@Override
	public String toString() {
		return "Positions [positionId=" + positionId + ", coordinateX=" + coordinateX + ", coordinateY=" + coordinateY
				+ ", positionType=" + positionType + ", parkId=" + parkId + ", parkFloor=" + parkFloor + ", toString()="
				+ super.toString() + "]";
	}
	
}

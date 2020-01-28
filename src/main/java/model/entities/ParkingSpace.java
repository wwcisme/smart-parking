package model.entities;

public class ParkingSpace {

	public String parkingSpaceId;
	
	public String parkId;
	
	public int parkFloor;
	
	public int coordinateX;
	
	public int coordinateY;
	
	public ParkingSpaceStatus parkingSpaceStatus;
	
	public String getParkingSpaceId() {
		return parkingSpaceId;
	}

	public void setParkingSpaceId(String parkingSpaceId) {
		this.parkingSpaceId = parkingSpaceId;
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

	public ParkingSpaceStatus getParkingSpaceStatus() {
		return parkingSpaceStatus;
	}

	public void setParkingSpaceStatus(ParkingSpaceStatus parkingSpaceStatus) {
		this.parkingSpaceStatus = parkingSpaceStatus;
	}
	
	@Override
	public String toString() {
		return "ParkingSpace [parkingSpaceId=" + parkingSpaceId + ", parkId=" + parkId + ", parkFloor=" + parkFloor
				+ ", coordinateX=" + coordinateX + ", coordinateY=" + coordinateY + ", parkingSpaceStatus="
				+ parkingSpaceStatus + ", toString()=" + super.toString() + "]";
	}
}

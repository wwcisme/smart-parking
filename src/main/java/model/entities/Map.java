package model.entities;

public class Map {

	public String parkId;
	
	public int parkFloor;
	
	public int entranceCoordinateX;
	
	public int entranceCoordinateY;
	
	public int exitCoordinateX;
	
	public int exitCoordinateY;
	
	public int stairEntranceCoordinateX;
	
	public int stairEntranceCoordinateY;
	
	public int stairExitCoordinateX;
	
	public int stairExitCoordinateY;
	
	public int getEntranceCoordinateX() {
		return entranceCoordinateX;
	}

	public void setEntranceCoordinateX(int entranceCoordinateX) {
		this.entranceCoordinateX = entranceCoordinateX;
	}

	public int getEntranceCoordinateY() {
		return entranceCoordinateY;
	}

	public void setEntranceCoordinateY(int entranceCoordinateY) {
		this.entranceCoordinateY = entranceCoordinateY;
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

	public int getentranceCoordinateX() {
		return entranceCoordinateX;
	}

	public void setentranceCoordinateX(int entranceCoordinateX) {
		this.entranceCoordinateX = entranceCoordinateX;
	}

	public int getentranceCoordinateY() {
		return entranceCoordinateY;
	}

	public void setentranceCoordinateY(int entranceCoordinateY) {
		this.entranceCoordinateY = entranceCoordinateY;
	}

	public int getExitCoordinateX() {
		return exitCoordinateX;
	}

	public void setExitCoordinateX(int exitCoordinateX) {
		this.exitCoordinateX = exitCoordinateX;
	}

	public int getExitCoordinateY() {
		return exitCoordinateY;
	}

	public void setExitCoordinateY(int exitCoordinateY) {
		this.exitCoordinateY = exitCoordinateY;
	}
	
	
	
	public int getStairEntranceCoordinateX() {
		return stairEntranceCoordinateX;
	}

	public void setStairEntranceCoordinateX(int stairEntranceCoordinateX) {
		this.stairEntranceCoordinateX = stairEntranceCoordinateX;
	}

	public int getStairEntranceCoordinateY() {
		return stairEntranceCoordinateY;
	}

	public void setStairEntranceCoordinateY(int stairEntranceCoordinateY) {
		this.stairEntranceCoordinateY = stairEntranceCoordinateY;
	}

	public int getStairExitCoordinateX() {
		return stairExitCoordinateX;
	}

	public void setStairExitCoordinateX(int stairExitCoordinateX) {
		this.stairExitCoordinateX = stairExitCoordinateX;
	}

	public int getStairExitCoordinateY() {
		return stairExitCoordinateY;
	}

	public void setStairExitCoordinateY(int stairExitCoordinateY) {
		this.stairExitCoordinateY = stairExitCoordinateY;
	}

	@Override
	public String toString() {
		return "Map [parkId=" + parkId + ", parkFloor=" + parkFloor + ", entranceCoordinateX=" + entranceCoordinateX
				+ ", entranceCoordinateY=" + entranceCoordinateY + ", exitCoordinateX=" + exitCoordinateX
				+ ", exitCoordinateY=" + exitCoordinateY + ", toString()=" + super.toString() + "]";
	}
}

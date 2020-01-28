package model.entities;

public class Park {
	
	public String parkId;
	
	public String parkName;
	
	public String parkAddress;

	public int parkCapability;
	
	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getParkAddress() {
		return parkAddress;
	}

	public void setParkAddress(String parkAddress) {
		this.parkAddress = parkAddress;
	}

	public int getParkCapability() {
		return parkCapability;
	}

	public void setParkCapability(int parkCapability) {
		this.parkCapability = parkCapability;
	}

	@Override
	public String toString() {
		return "Park [parkId=" + parkId + ", parkName=" + parkName + ", parkAddress=" + parkAddress
				+ ", parkCapability=" + parkCapability + ", toString()=" + super.toString() + "]";
	}
	
}

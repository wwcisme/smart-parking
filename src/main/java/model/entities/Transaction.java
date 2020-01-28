package model.entities;

import java.time.LocalDateTime;

public class Transaction {
	
	public String transactionId;
	
	public String userId;
	
	public String parkId;
	
	public String parkingSpaceId;
	
	public LocalDateTime startTime;
	
	public TransactionStatus transactionStatus;
	
	
	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getParkingSpaceId() {
		return parkingSpaceId;
	}

	public void setParkingSpaceId(String parkingSpaceId) {
		this.parkingSpaceId = parkingSpaceId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", userId=" + userId + ", parkingSpaceId="
				+ parkingSpaceId + ", startTime=" + startTime + ", transactionStatus=" + transactionStatus
				+ ", toString()=" + super.toString() + "]";
	}

}

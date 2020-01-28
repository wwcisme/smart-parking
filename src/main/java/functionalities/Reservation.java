package functionalities;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import model.dbQuries.ParkingSpaceQuery;
import model.dbQuries.TransactionQuery;
import model.entities.ParkingSpace;
import model.entities.ParkingSpaceStatus;
import model.entities.Transaction;
import model.entities.TransactionStatus;

public class Reservation {
	
	private Connection connect;
	
	private ParkingSpace parkingSpace;
	
	private Transaction transaction;
	
	public Reservation(Connection connect) {
        this.connect = connect;
    }
		

    @Path("/makeReservation")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean makeReservation(@FormParam("userId") String userId, @FormParam("parkId") String parkId) {
		
		ParkingSpaceQuery parkingSpaceQuery = new ParkingSpaceQuery(connect);
		TransactionQuery transactionQuery = new TransactionQuery(connect);

		List<ParkingSpace> availableParkingSpaces = getAvailableParkingSpace(parkId);
		
		if (availableParkingSpaces.size() == 0) {
			System.out.println("Park " + parkId + " is full");
			return false;
		}else {
			parkingSpace = getOptimalParkingSpace(availableParkingSpaces);
			createTransaction(userId, parkingSpace);
			reserveParkingSpace(parkingSpace); 
			System.out.println("Reserved park space " + parkingSpace.getParkingSpaceId() + " in park " + parkingSpace.getParkId() + " for user " + userId);
			return true;
		}		
	}
	public ParkingSpace getParkingSpace() {
		return this.parkingSpace;
	}
	
	public Transaction getTransaction() {
		return this.transaction;
	}
	
	private List<ParkingSpace> getAvailableParkingSpace(String parkId){
		ParkingSpaceQuery parkingSpaceQuery = new ParkingSpaceQuery(connect);
		return parkingSpaceQuery.getParkingSpacesByParkIdAndParkingSpaceStatus(parkId, ParkingSpaceStatus.FREE.toString());
		
	}
	
	private ParkingSpace getOptimalParkingSpace(List<ParkingSpace> parkingSpaces) {
		// todo: calculate the nearest parking space from entrance with Dijkstra Algorithm and generate a ranking list
		return parkingSpaces.get(0);
	}
	
	private void createTransaction(String userId, ParkingSpace parkingSpace) {
		
		TransactionQuery transactionQuery = new TransactionQuery(connect);

		transaction = new Transaction();

		transaction.setTransactionId(UUID.randomUUID().toString());
		transaction.setUserId(userId);
		transaction.setParkId(parkingSpace.getParkId());
		transaction.setParkingSpaceId(parkingSpace.getParkingSpaceId());
		transaction.setTransactionStatus(TransactionStatus.PROCESSING);
		transaction.setStartTime(LocalDateTime.now());	
		
		transactionQuery.save(transaction);
	}
	
	private void reserveParkingSpace(ParkingSpace parkingSpace) {
		ParkingSpaceQuery parkingSpaceQuery = new ParkingSpaceQuery(connect);
	
		parkingSpace.setParkingSpaceStatus(ParkingSpaceStatus.RESERVED);
		
		parkingSpaceQuery.update(parkingSpace);
	}

	
}

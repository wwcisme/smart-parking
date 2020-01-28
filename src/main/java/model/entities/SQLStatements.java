package model.entities;

public class SQLStatements {
	
	
	public static final String CREATE_TABLE_PARK = "CREATE TABLE if not exists smartparking.park ("
			+ "parkId VARCHAR(10) NOT NULL,"
			+ "parkName VARCHAR(30) NOT NULL,"
			+ "parkAddress VARCHAR(100),"
			+ "parkCapability INT NOT NULL,"
			+ "primary key (parkId)"
			+ ");";
	public static final String CREATE_TABLE_MAP = "CREATE TABLE if not exists smartparking.map ("
			+ "parkId VARCHAR(10) NOT NULL,"
			+ "parkFloor INT NOT NULL,"
			+ "entranceCoordinateX INT NOT NULL,"
			+ "entranceCoordinateY INT NOT NULL,"
			+ "exitCoordinateX INT NOT NULL,"
			+ "exitCoordinateY INT NOT NULL,"
			+ "stairEntranceCoordinateX INT NOT NULL,"
			+ "stairEntranceCoordinateY INT NOT NULL,"
			+ "stairExitCoordinateX INT NOT NULL,"
			+ "stairExitCoordinateY INT NOT NULL,"			
			+ "primary key (parkId, parkFloor)"
			+ ");";
	
	public static final String CREATE_TABLE_POSITION = "CREATE TABLE if not exists smartparking.position ("
			+ "parkId VARCHAR(10) NOT NULL,"
			+ "parkFloor INT NOT NULL,"
			+ "positionId VARCHAR(10) NOT NULL,"
			+ "coordinateX INT NOT NULL,"
			+ "coordinateY INT NOT NULL,"
			+ "positionType ENUM('PARKINGSPACE', 'ROUTE','UNKNOWN') NOT NULL,"
			+ "primary key (parkId,parkFloor, positionId)"
			+ ");";
	
	public static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE if not exists smartparking.transaction ("
			+ "transactionId VARCHAR(36) NOT NULL,"
			+ "userId VARCHAR(10) NOT NULL,"
			+ "parkId VARCHAR(10) NOT NULL,"
			+ "parkingSpaceId VARCHAR(10) NOT NULL,"
			+ "startTime TIMESTAMP NOT NULL,"
			+ "transactionStatus ENUM('FINISHED', 'PROCESSING') NOT NULL,"
			+ "primary key (transactionId)"
			+ ");";
	
	public static final String CREATE_TABLE_PARKINGSPACE = "CREATE TABLE if not exists smartparking.parkingspace ("
			+ "parkingSpaceId VARCHAR(10) NOT NULL,"
			+ "parkId VARCHAR(10) NOT NULL,"
			+ "parkFloor INT NOT NULL,"
			+ "coordinateX INT NOT NULL,"
			+ "coordinateY INT NOT NULL,"
			+ "parkingSpaceStatus ENUM('FREE', 'RESERVED', 'OCCUPIED') NOT NULL,"
			+ "primary key (parkId, parkingSpaceId)"
			+ ");";
	
	
}

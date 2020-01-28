package application;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.dbQuries.MapQuery;
import model.entities.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import model.dbQuries.ParkQuery;
import model.dbQuries.ParkingSpaceQuery;
import model.dbQuries.PositionQuery;

public class Initialization {
	
	private Connection connect;
	private PreparedStatement preparedStatement = null;

	public Initialization(Connection connect) {
		this.connect = connect;
		
	}
	

	public void createDatabaseTables() { //每次启动检测表存在？
		
		
		try {
			preparedStatement = connect.prepareStatement(SQLStatements.CREATE_TABLE_MAP);
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement(SQLStatements.CREATE_TABLE_PARK);
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement(SQLStatements.CREATE_TABLE_PARKINGSPACE);
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement(SQLStatements.CREATE_TABLE_POSITION);
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement(SQLStatements.CREATE_TABLE_TRANSACTION);
			preparedStatement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public void importPark() {	//导入
		try {
			
			ParkQuery parkQuery = new ParkQuery(connect);
			ParkingSpaceQuery parkingSpaceQuery = new ParkingSpaceQuery(connect);
			MapQuery mapQuery = new MapQuery(connect);
			PositionQuery positionQuery = new PositionQuery(connect);
			
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream("ParkingSpacesInPark001.xlsx");
			
			Workbook wb = WorkbookFactory.create(is);
			Sheet parkSheet = wb.getSheet("Park");
			Sheet parkingSpaceSheet = wb.getSheet("ParkingSpace");
			Sheet mapSheet = wb.getSheet("Map");
			Sheet positionSheet = wb.getSheet("Position");
			
			// import park
			Row row = parkSheet.getRow(1);
			if (row != null && !parkQuery.getParkByParkId(row.getCell(0).getStringCellValue()).isPresent()) {
				Park park = new Park();
				park.setParkId(row.getCell(0).getStringCellValue());
				park.setParkName(row.getCell(1).getStringCellValue());
				park.setParkAddress(row.getCell(2).getStringCellValue());
				park.setParkCapability((int)(row.getCell(3).getNumericCellValue()));
				parkQuery.save(park);
			}else if (parkQuery.getParkByParkId(row.getCell(0).getStringCellValue()).isPresent()) {
				System.out.println("Skipped loading park from file.");
			}
		
			//Import Parking Spaces
			for (int rowIndex = 1; rowIndex <= parkingSpaceSheet.getLastRowNum(); rowIndex++) {
				row = parkingSpaceSheet.getRow(rowIndex);
				if (row != null ) {
					
					String parkingSpaceId = row.getCell(0).getStringCellValue();
					String parkId = row.getCell(1).getStringCellValue();
					int parkFloor = (int)(row.getCell(2).getNumericCellValue());
					if(!parkingSpaceQuery.getParkByParkIdAndParkFloorAndParkingSpaceId(parkId, parkFloor,parkingSpaceId).isPresent()) {
						ParkingSpace parkingSpace = new ParkingSpace();
						parkingSpace.setParkId(parkId);
						parkingSpace.setParkingSpaceId(parkingSpaceId);
						parkingSpace.setParkFloor(parkFloor);
						parkingSpace.setCoordinateX((int) (row.getCell(3).getNumericCellValue()));
						parkingSpace.setCoordinateY((int) (row.getCell(4).getNumericCellValue()));
						parkingSpace.setParkingSpaceStatus(ParkingSpaceStatus.FREE.valueOf(row.getCell(5).getStringCellValue()));
						parkingSpaceQuery.save(parkingSpace);
					}else{
						System.out.println("Skipped loading parking space from file.");
					}
				}
			}

			//Import Map

			for (int rowIndex = 1; rowIndex <= mapSheet.getLastRowNum(); rowIndex++) {
				row = mapSheet.getRow(rowIndex);
				if (row != null) {
					String parkId = row.getCell(0).getStringCellValue();
					int parkFloor = (int)(row.getCell(1).getNumericCellValue());
					if (!mapQuery.getMapByParkIdAndParkFloor(parkId, parkFloor).isPresent()){

						Map map = new Map();
						map.setParkId(parkId);
						map.setParkFloor(parkFloor);
						map.setEntranceCoordinateX((int)(row.getCell(2).getNumericCellValue()));
						map.setEntranceCoordinateY((int)(row.getCell(3).getNumericCellValue()));
						map.setExitCoordinateX((int)(row.getCell(4).getNumericCellValue()));
						map.setExitCoordinateY((int)(row.getCell(5).getNumericCellValue()));
						if(row.getCell(6) != null)
							map.setStairEntranceCoordinateX((int)(row.getCell(6).getNumericCellValue()));
						if(row.getCell(7) != null)
							map.setStairEntranceCoordinateY((int)(row.getCell(7).getNumericCellValue()));
						if(row.getCell(8) != null)	
							map.setStairExitCoordinateX((int)(row.getCell(8).getNumericCellValue()));
						if(row.getCell(9) != null)
							map.setStairExitCoordinateY((int)(row.getCell(9).getNumericCellValue()));
						mapQuery.save(map);
					}else{
						System.out.println("Skipped loading map from file.");
					}
				}
			}
			
			
			//Import Position(路、车位、位置)
			for (int rowIndex = 1; rowIndex <= positionSheet.getLastRowNum(); rowIndex++) {
				row = positionSheet.getRow(rowIndex);
				if (row != null ) {
					
					String positionId = row.getCell(0).getStringCellValue();
					String parkId = row.getCell(4).getStringCellValue();
					int parkFloor = (int)(row.getCell(5).getNumericCellValue());
					if(!positionQuery.getPositionByParkIdAndParkFloorAndPositionId(parkId, parkFloor, positionId).isPresent()) {
						Position position = new Position();
						position.setCoordinateX((int) (row.getCell(1).getNumericCellValue()));
						position.setCoordinateY((int) (row.getCell(2).getNumericCellValue()));
						position.setParkFloor(parkFloor);
						position.setParkId(parkId);
						position.setPositionId(positionId);
						position.setPositionType(PositionType.valueOf(row.getCell(3).getStringCellValue()));
						positionQuery.save(position);
					}else{
						System.out.println("Skipped loading parking space from file.");
					}
				}
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}		
	}

}

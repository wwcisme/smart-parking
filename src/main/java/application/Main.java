package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import functionalities.Navigation;
import functionalities.Reservation;
import model.dbQuries.MapQuery;
import model.dbQuries.PositionQuery;
import model.entities.Map;
import model.entities.ParkingSpace;
import model.entities.Position;
import model.entities.Transaction;

public class Main {
	
	
    public static void main(String[] args) throws Exception {
        final String mysqlDatabaseName = "SmartParking";
        final String mysqlUserName = "root";
        final String mysqlPassword = "123456";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
    //    Connection connect = DriverManager
     //           .getConnection("jdbc:mysql://localhost:3306/" + mysqlDatabaseName + "?user=" + mysqlUserName + "&password=" + mysqlPassword);
        Connection connect = DriverManager
                   .getConnection("jdbc:mysql://localhost:3306/smartparking?serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true" ,mysqlUserName , mysqlPassword);
       
        Initialization initialization = new Initialization(connect);

        initialization.createDatabaseTables();
        initialization.importPark();
        
        
        Reservation reservation = new Reservation(connect);
        
        Boolean reservationResult = reservation.makeReservation("user001", "P001");
        
        System.out.println(reservationResult);
        
        if(reservationResult) {
        	ParkingSpace parkingSpace = reservation.getParkingSpace();
        	Transaction transaction = reservation.getTransaction();
        	
        	PositionQuery positionQuery = new PositionQuery(connect);

        	MapQuery mapQuery = new MapQuery(connect);
        	
        	Map map = mapQuery.getMapByParkIdAndParkFloor(parkingSpace.getParkId(), parkingSpace.getParkFloor()).get();
    	
            List<Position> positions = positionQuery.getPositionByParkIdAndParkFloor(parkingSpace.getParkId(), parkingSpace.getParkFloor());
        	//жу╣Ц
            Position end =positionQuery.getPositionByParkIdAndParkFloorAndCoordinateXAndCoordinateY(parkingSpace.getParkId(), parkingSpace.getParkFloor(), parkingSpace.getCoordinateX(), parkingSpace.getCoordinateY()).get();
            //Example for floor 1
            Position start = positionQuery.getPositionByParkIdAndParkFloorAndCoordinateXAndCoordinateY(parkingSpace.getParkId(), parkingSpace.getParkFloor(), map.getentranceCoordinateX(), map.getentranceCoordinateY()).get();
            Navigation navigation = new Navigation();
            navigation.getNavigation(positions, start, end);
        }
        
//        Park park = new Park();
//        park.setParkId("123");
//        park.setParkName("TestPark");
//        park.setParkAddress("TestAdd");
//        park.setParkCapability(50);
//        
//        ParkQuery parkQuery = new ParkQuery(connect);
//        parkQuery.save(park);
//
//        System.out.println(parkQuery.getParkByParkId("123").get().toString());
//
//        park.setParkCapability(60);
//        parkQuery.update(park);
//        
//        System.out.println(parkQuery.getParkByParkId("123").get().toString());
//        
//        parkQuery.deleteByParkId("123");
//        
//        System.out.println(parkQuery.getParks().toString());
        
        
        
        connect.close();
    }

}
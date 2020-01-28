package model.dbQuries;

import model.entities.ParkingSpace;
import model.entities.ParkingSpaceStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParkingSpaceQuery {


    private Connection connect;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private final static Logger logger = Logger.getLogger( ParkingSpaceQuery.class.getName() );

    public ParkingSpaceQuery(Connection connect) {
        this.connect = connect;
    }


    public List<ParkingSpace> getParkingSpaces(){
        List<ParkingSpace> parkingSpaces = new ArrayList<ParkingSpace>();
        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.parkingspace;");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ParkingSpace parkingSpace = new ParkingSpace();
                parkingSpace.parkId = resultSet.getString("parkId");
                parkingSpace.parkingSpaceId = resultSet.getString("parkingSpaceId");
                parkingSpace.parkFloor = resultSet.getInt("parkFloor");
                parkingSpace.coordinateX = resultSet.getInt("coordinateX");
                parkingSpace.coordinateY = resultSet.getInt("coordinateY");
                parkingSpace.parkingSpaceStatus = ParkingSpaceStatus.valueOf(resultSet.getString("parkingSpaceStatus"));
                parkingSpaces.add(parkingSpace);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return parkingSpaces;
    }
    public List<ParkingSpace> getParkingSpacesByParkId(String parkId){
        List<ParkingSpace> parkingSpaces = new ArrayList<ParkingSpace>();
        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.parkingspace where parkId=? ;");
            preparedStatement.setString(1, parkId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ParkingSpace parkingSpace = new ParkingSpace();
                parkingSpace.parkId = parkId;
                parkingSpace.parkingSpaceId = resultSet.getString("parkingSpaceId");
                parkingSpace.parkFloor = resultSet.getInt("parkFloor");
                parkingSpace.coordinateX = resultSet.getInt("coordinateX");
                parkingSpace.coordinateY = resultSet.getInt("coordinateY");
                parkingSpace.parkingSpaceStatus = ParkingSpaceStatus.valueOf( resultSet.getString("parkingSpaceStatus"));
                parkingSpaces.add(parkingSpace);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return parkingSpaces;
    }

    public List<ParkingSpace> getParkingSpacesByParkIdAndParkingSpaceStatus(String parkId, String parkingSpaceStatus){
        List<ParkingSpace> parkingSpaces = new ArrayList<ParkingSpace>();
        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.parkingspace where parkId=? and parkingSpaceStatus=?;");
            preparedStatement.setString(1, parkId);
            preparedStatement.setString(2, parkingSpaceStatus);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ParkingSpace parkingSpace = new ParkingSpace();
                parkingSpace.parkId = parkId;
                parkingSpace.parkingSpaceId = resultSet.getString("parkingSpaceId");
                parkingSpace.parkFloor = resultSet.getInt("parkFloor");
                parkingSpace.coordinateX = resultSet.getInt("coordinateX");
                parkingSpace.coordinateY = resultSet.getInt("coordinateY");
                parkingSpace.parkingSpaceStatus = ParkingSpaceStatus.valueOf(parkingSpaceStatus);
                parkingSpaces.add(parkingSpace);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return parkingSpaces;
    }
    


    public Optional<ParkingSpace> getParkByParkIdAndParkFloorAndParkingSpaceId(String parkId, int floor, String parkingSpaceId){

        Optional<ParkingSpace> result = Optional.empty();

        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.parkingspace where parkId= ? and parkFloor= ? and parkingSpaceId= ?;");
            preparedStatement.setString(1, parkId);
            preparedStatement.setInt(2, floor);
            preparedStatement.setString(3, parkingSpaceId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                ParkingSpace parkingSpace = new ParkingSpace();
                parkingSpace.parkId = parkId;
                parkingSpace.parkingSpaceId = parkingSpaceId;
                parkingSpace.parkFloor = floor;
                parkingSpace.coordinateX = resultSet.getInt("coordinateX");
                parkingSpace.coordinateY = resultSet.getInt("coordinateY");
                parkingSpace.parkingSpaceStatus = ParkingSpaceStatus.valueOf(resultSet.getString("parkingSpaceStatus"));
                result = Optional.of(parkingSpace);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void save(ParkingSpace parkingSpace) {

        if(getParkByParkIdAndParkFloorAndParkingSpaceId(parkingSpace.getParkId(),parkingSpace.getParkFloor(), parkingSpace.getParkingSpaceId()).isPresent()){
                String warning = String.format("Parking space with park id {0} floor {1} and parking space id {2} is already exited.", parkingSpace.getParkId(), String.valueOf(parkingSpace.getParkFloor()), parkingSpace.getParkingSpaceId());
                logger.log(Level.WARNING, warning);
        }else {
            String parkId = parkingSpace.getParkId();

            int floor = parkingSpace.getParkFloor();

            String parkingSpaceId = parkingSpace.getParkingSpaceId();

            int coordinateX = parkingSpace.getCoordinateX();

            int coordinateY = parkingSpace.getCoordinateY();

            ParkingSpaceStatus parkingSpaceStatus = parkingSpace.getParkingSpaceStatus();

            try {
                preparedStatement = connect
                        .prepareStatement("insert into  smartparking.parkingspace values (?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, parkingSpaceId);
                preparedStatement.setString(2,parkId);
                preparedStatement.setInt(3, floor);
                preparedStatement.setInt(4, coordinateX);
                preparedStatement.setInt(5, coordinateY);
                preparedStatement.setString(6, parkingSpaceStatus.toString());
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void update(ParkingSpace parkingSpace) {
        if(!getParkByParkIdAndParkFloorAndParkingSpaceId(parkingSpace.getParkId(),parkingSpace.getParkFloor(), parkingSpace.getParkingSpaceId()).isPresent()) {
            String warning = String.format("Cannot find parking space with park id {0} floor {1} and parking space id {2}.", parkingSpace.getParkId(), String.valueOf(parkingSpace.getParkFloor()), parkingSpace.getParkingSpaceId());
            logger.log(Level.WARNING, warning);
        }
        else{
            try{
                preparedStatement = connect
                        .prepareStatement("update smartparking.parkingspace set coordinateX= ?, coordinateY= ?, parkingSpaceStatus= ? where parkId= ? and parkFloor= ? and parkingSpaceId= ?;");
                preparedStatement.setString(6, parkingSpace.getParkingSpaceId());
                preparedStatement.setInt(5, parkingSpace.getParkFloor());
                preparedStatement.setString(4, parkingSpace.getParkId());
                preparedStatement.setInt(1, parkingSpace.getCoordinateX());
                preparedStatement.setInt(2, parkingSpace.getCoordinateY());
                preparedStatement.setString(3, parkingSpace.getParkingSpaceStatus().toString());
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void deleteByParkIdAndParkFloorAndParkingSpaceId(String parkId, int floor, String parkingSpaceId) {
        if(!getParkByParkIdAndParkFloorAndParkingSpaceId(parkId,floor, parkingSpaceId).isPresent()) {
            String warning = String.format("Cannot find parking space with park id {0} floor {1} and parking space id {2}.", parkingSpaceId, String.valueOf(floor), parkingSpaceId);
            logger.log(Level.WARNING, warning);
        }
        else{
            try{
                preparedStatement = connect
                        .prepareStatement("delete from smartparking.parkspace where parkId= ? and parkFloor= ? and parkingSpaceId= ?; ");
                preparedStatement.setString(1, parkId);
                preparedStatement.setInt(2, floor);
                preparedStatement.setString(3, parkingSpaceId);
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}

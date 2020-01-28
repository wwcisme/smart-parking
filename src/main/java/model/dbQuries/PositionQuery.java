package model.dbQuries;

import model.entities.Position;
import model.entities.PositionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PositionQuery {

    private Connection connect;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private final static Logger logger = Logger.getLogger( PositionQuery.class.getName() );

    public PositionQuery(Connection connect) {
        this.connect = connect;
    }


    public List<Position> getPositions(){
        List<Position> parkingSpaces = new ArrayList<Position>();
        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.position;");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Position position = new Position();
                position.parkId = resultSet.getString("parkId");
                position.positionId = resultSet.getString("positionId");
                position.parkFloor = resultSet.getInt("parkFloor");
                position.coordinateX = resultSet.getInt("coordinateX");
                position.coordinateY = resultSet.getInt("coordinateY");
                position.positionType = PositionType.valueOf(resultSet.getString("positionType"));
                parkingSpaces.add(position);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return parkingSpaces;
    }
    
    public List<Position> getPositionByParkIdAndParkFloor(String parkId, int floor){
        List<Position> result = new ArrayList();

        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.position where parkId= ? and parkFloor= ? ;");
            preparedStatement.setString(1, parkId);
            preparedStatement.setInt(2, floor);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Position position = new Position();
                position.parkId = parkId;
                position.parkFloor = floor;
                position.coordinateX = resultSet.getInt("coordinateX");
                position.coordinateY = resultSet.getInt("coordinateY");
                position.positionId = resultSet.getString("positionId");
                position.positionType = PositionType.valueOf(resultSet.getString("positionType"));
                result.add(position);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public Optional<Position> getPositionByParkIdAndParkFloorAndPositionId(String parkId, int floor, String positionId){
        Optional<Position> result = Optional.empty();

        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.position where parkId= ? and parkFloor= ? and positionId= ? ;");
            preparedStatement.setString(1, parkId);
            preparedStatement.setInt(2, floor);
            preparedStatement.setString(3, positionId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Position position = new Position();
                position.parkId = parkId;
                position.parkFloor = floor;
                position.coordinateX = resultSet.getInt("coordinateX");
                position.coordinateY = resultSet.getInt("coordinateY");
                position.positionId = positionId;
                position.positionType = PositionType.valueOf(resultSet.getString("positionType"));
                result = Optional.of(position);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public Optional<Position> getPositionByParkIdAndParkFloorAndCoordinateXAndCoordinateY(String parkId, int floor, int coordinateX, int coordinateY){

        Optional<Position> result = Optional.empty();

        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.position where parkId= ? and parkFloor= ? and coordinateX= ? and coordinateY=? ;");
            preparedStatement.setString(1, parkId);
            preparedStatement.setInt(2, floor);
            preparedStatement.setInt(3, coordinateX);
            preparedStatement.setInt(4, coordinateY);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Position position = new Position();
                position.parkId = parkId;
                position.parkFloor = floor;
                position.coordinateX = coordinateX;
                position.coordinateY = coordinateY;
                position.positionId = resultSet.getString("positionId");
                position.positionType = PositionType.valueOf(resultSet.getString("positionType"));
                result = Optional.of(position);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void save(Position position) {

        if(getPositionByParkIdAndParkFloorAndCoordinateXAndCoordinateY(position.getParkId(),position.getParkFloor(), position.getCoordinateX(),position.getCoordinateY()).isPresent()){
            String warning = String.format("Position with park id %s floor %d coordinateX %d and coordinateY %d is already exited.", position.getParkId(), position.getParkFloor(), position.getCoordinateX(), position.getCoordinateY());
            logger.log(Level.WARNING, warning);
        }else {
            String parkId = position.getParkId();

            int floor = position.getParkFloor();

            String positionId = position.getPositionId();

            int coordinateX = position.getCoordinateX();

            int coordinateY = position.getCoordinateY();

            PositionType positionType = position.getPositionType();

            try {
                preparedStatement = connect
                        .prepareStatement("insert into  smartparking.position  values (?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, parkId);
                preparedStatement.setInt(2, floor);
                preparedStatement.setString(3, positionId);
                preparedStatement.setInt(4, coordinateX);
                preparedStatement.setInt(5, coordinateY);
                preparedStatement.setString(6, positionType.toString());
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void update(Position position) {
        if(!getPositionByParkIdAndParkFloorAndCoordinateXAndCoordinateY(position.getParkId(),position.getParkFloor(), position.getCoordinateX(), position.getCoordinateY()).isPresent()) {
            String warning = String.format("Cannot find position with park id %s floor %d coordinateX %d and coordinateY %d", position.getParkId(), position.getParkFloor(), position.getCoordinateX(), position.getCoordinateY());
            logger.log(Level.WARNING, warning);
        }
        else{
            try{
                preparedStatement = connect
                        .prepareStatement("update smartparking.position set positionId= ? and positionType= ? where parkId= ? and parkFloor= ? and coordinateX= ? and coordinateY= ?;");
                preparedStatement.setString(1, position.getPositionId());
                preparedStatement.setInt(4, position.getParkFloor());
                preparedStatement.setString(3, position.getParkId());
                preparedStatement.setInt(5, position.getCoordinateX());
                preparedStatement.setInt(6, position.getCoordinateY());
                preparedStatement.setString(2, position.getPositionType().toString());
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void deleteByParkIdAndParkFloorAndParkingSpaceId(String parkId, int floor, int coordinateX, int coordinateY) {
        if(!getPositionByParkIdAndParkFloorAndCoordinateXAndCoordinateY(parkId,floor, coordinateX, coordinateY).isPresent()) {
            String warning = String.format("Cannot find position with park id %s floor %d coordinateX %d and coordinateY %ds", parkId,floor, coordinateX, coordinateY);
            logger.log(Level.WARNING, warning);
        }
        else{
            try{
                preparedStatement = connect
                        .prepareStatement("delete from smartparking.position where parkId= ? and parkFloor= ? and coordinateX= ? and coordinateY=?; ");
                preparedStatement.setString(1, parkId);
                preparedStatement.setInt(2, floor);
                preparedStatement.setInt(3, coordinateX);
                preparedStatement.setInt(4, coordinateY);
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}

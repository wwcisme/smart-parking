package model.dbQuries;

import model.entities.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MapQuery {
    private Connection connect;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private final static Logger logger = Logger.getLogger( MapQuery.class.getName() );

    public MapQuery(Connection connect) {
        this.connect = connect;
    }

    public List<Map> getMaps(){
        List<Map> maps = new ArrayList<Map>();
        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.map;");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Map map = new Map();
                map.parkId = resultSet.getString("parkId");
                map.parkFloor = resultSet.getInt("parkFloor");
                map.entranceCoordinateX = resultSet.getInt("entranceCoordinateX");
                map.entranceCoordinateY = resultSet.getInt("entranceCoordinateY");
                map.exitCoordinateX = resultSet.getInt("exitCoordinateX");
                map.exitCoordinateY = resultSet.getInt("exitCoordinateY");
                map.stairEntranceCoordinateX = resultSet.getInt("stairEntranceCoordinateX");
                map.stairEntranceCoordinateY = resultSet.getInt("stairEntranceCoordinateY");
                map.stairExitCoordinateX = resultSet.getInt("stairExitCoordinateX");
                map.stairExitCoordinateY = resultSet.getInt("stairExitCoordinateY");
                maps.add(map);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return maps;
    }


    public Optional<Map> getMapByParkIdAndParkFloor(String parkId, int floor){

        Optional<Map> result = Optional.empty();

        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.map where parkId= ? and parkFloor= ? ;");
            preparedStatement.setString(1, parkId);
            preparedStatement.setInt(2, floor);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Map map = new Map();
                map.parkId = parkId;
                map.parkFloor = floor;
                map.entranceCoordinateX = resultSet.getInt("entranceCoordinateX");
                map.entranceCoordinateY = resultSet.getInt("entranceCoordinateY");
                map.exitCoordinateX = resultSet.getInt("exitCoordinateX");
                map.exitCoordinateY = resultSet.getInt("exitCoordinateY");
                map.stairEntranceCoordinateX = resultSet.getInt("stairEntranceCoordinateX");
                map.stairEntranceCoordinateY = resultSet.getInt("stairEntranceCoordinateY");
                map.stairExitCoordinateX = resultSet.getInt("stairExitCoordinateX");
                map.stairExitCoordinateY = resultSet.getInt("stairExitCoordinateY");
                result = Optional.of(map);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void save(Map map) {

        if(getMapByParkIdAndParkFloor(map.getParkId(), map.getParkFloor()).isPresent()) {
        	String warning = String.format("Map with park id {0} and floor {1} is already existed.", map.getParkId(), String.valueOf(map.getParkFloor()));
            logger.log(Level.WARNING, warning);
        }else {
            String parkId = map.getParkId();

            int parkFloor = map.getParkFloor();

            int entranceCoordinateX = map.getentranceCoordinateX();

            int entranceCoordinateY = map.getentranceCoordinateY();

            int exitCoordinateX = map.getExitCoordinateX();

            int exitCoordinateY = map.getExitCoordinateY();
            
            int stairEntranceCoordinateX = map.getStairEntranceCoordinateX();
            
            int stairEntranceCoordinateY = map.getStairEntranceCoordinateY();
            
            int stairExitCoordinateX = map.getStairExitCoordinateX();
            
            int stairExitCoordinateY = map.getStairExitCoordinateY();

            try {
                preparedStatement = connect
                        .prepareStatement("insert into  smartparking.map values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, parkId);
                preparedStatement.setInt(2, parkFloor);
                preparedStatement.setInt(3, entranceCoordinateX);
                preparedStatement.setInt(4, entranceCoordinateY);
                preparedStatement.setInt(5, exitCoordinateX);
                preparedStatement.setInt(6, exitCoordinateY);
                preparedStatement.setInt(7, stairEntranceCoordinateX);
                preparedStatement.setInt(8, stairEntranceCoordinateY);
                preparedStatement.setInt(9, stairExitCoordinateX);
                preparedStatement.setInt(10, stairExitCoordinateY);
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void update(Map map) {
        if(!getMapByParkIdAndParkFloor(map.getParkId(), map.getParkFloor()).isPresent()) {
        	String warning = String.format("Cannot find map of park id {0} floor {1}.", map.getParkId(), String.valueOf(map.getParkFloor()));
            logger.log(Level.WARNING, warning);
        }else{
            try{
                preparedStatement = connect
                        .prepareStatement("update smartparking.map set entranceCoordinateX= ?, entranceCoordinateY= ?, exitCoordinateX= ? ,exitCoordinateY= ? stairEntranceCoordinateX= ? stairEntranceCoordinateY= ? stairExitCoordinateX= ? stairExitCoordinateY = ? where parkId= ? and parkFloor= ?;");
                preparedStatement.setString(5, map.getParkId());
                preparedStatement.setInt(6, map.getParkFloor());
                preparedStatement.setInt(1, map.getentranceCoordinateX());
                preparedStatement.setInt(2, map.getentranceCoordinateY());
                preparedStatement.setInt(3, map.getExitCoordinateX());
                preparedStatement.setInt(4, map.getExitCoordinateY());
                preparedStatement.setInt(5, map.getStairEntranceCoordinateX());
                preparedStatement.setInt(6, map.getStairEntranceCoordinateY());
                preparedStatement.setInt(7, map.getStairExitCoordinateX());
                preparedStatement.setInt(8, map.getStairExitCoordinateY());
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void deleteByParkIdAndParkFloor(String parkId, int parkFloor) {
        if(!getMapByParkIdAndParkFloor(parkId, parkFloor).isPresent()) {
        	String warning = String.format("Cannot find map of park id {0} floor {1}.", parkId, String.valueOf(parkFloor));
            logger.log(Level.WARNING, warning);
        }else{
            try{
                preparedStatement = connect
                        .prepareStatement("delete from smartparking.map where parkId= ? and parkFloor= ?; ");
                preparedStatement.setString(1, parkId);
                preparedStatement.setInt(2, parkFloor);
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}

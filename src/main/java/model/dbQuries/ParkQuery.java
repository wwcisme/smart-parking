package model.dbQuries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.entities.Park;

public class ParkQuery {

	private Connection connect;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private final static Logger logger = Logger.getLogger( ParkQuery.class.getName() );
	
	public ParkQuery(Connection connect) {
		this.connect = connect;
	}
	
	public List<Park> getParks(){	//从数据库返回
		List<Park> parks = new ArrayList<Park>();
		try {
			preparedStatement = connect.prepareStatement("SELECT * from smartparking.park;");
			resultSet = preparedStatement.executeQuery();
			
	        while(resultSet.next()){
	            Park park = new Park();
	            park.parkId = resultSet.getString("parkId");
	            park.parkName = resultSet.getString("parkName");
	            park.parkAddress = resultSet.getString("parkAddress");
	            park.parkCapability = resultSet.getInt("parkCapability");
	            parks.add(park);
	        }
		}catch(SQLException e) {
			e.printStackTrace();
		}		
		return parks;
	}
	
	
	public Optional<Park> getParkByParkId(String parkId){
		
		Optional<Park> result = Optional.empty();
		
		try {
			preparedStatement = connect.prepareStatement("SELECT * from smartparking.park where parkId= ? ;");
			preparedStatement.setString(1, parkId);
			resultSet = preparedStatement.executeQuery();
			
	        if(resultSet.next()){
	            Park park = new Park();
	            park.parkId = parkId;
	            park.parkName = resultSet.getString("parkName");
	            park.parkAddress = resultSet.getString("parkAddress");
	            park.parkCapability = resultSet.getInt("parkCapability");
	            result = Optional.of(park);
	        }
		}catch(SQLException e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	public void save(Park park) {
		
		if(getParkByParkId(park.getParkId()).isPresent())
			logger.log(Level.WARNING, "Park with park id {0} is already existed.", park.getParkId());
		else {
			String parkId = park.getParkId();
			
			String parkName = park.getParkName();
			
			String parkAddress = park.getParkAddress();
	
			int parkCapability = park.getParkCapability();
			
			try {
				preparedStatement = connect
	                    .prepareStatement("insert into  smartparking.park values (?, ?, ?, ?)");
	            preparedStatement.setString(1, parkId);
	            preparedStatement.setString(2, parkName);
	            preparedStatement.setString(3, parkAddress);
	            preparedStatement.setInt(4, parkCapability);
	            preparedStatement.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
    }
	
	public void update(Park park) {
		if(!getParkByParkId(park.getParkId()).isPresent())
			logger.log(Level.WARNING, "Cannot find park with park id {0}", park.getParkId());
		else{
			try{
				preparedStatement = connect
						.prepareStatement("update smartparking.park set parkName= ?, parkAddress= ?, parkCapability= ? where parkId= ?;");
				preparedStatement.setString(4, park.getParkId());
				preparedStatement.setString(1, park.getParkName());
				preparedStatement.setString(2, park.getParkAddress());
				preparedStatement.setInt(3, park.getParkCapability());
				preparedStatement.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void deleteByParkId(String parkId) {
		if(!getParkByParkId(parkId).isPresent())
			logger.log(Level.WARNING, "Cannot find park with park id {0}", parkId);
		else{
			try{
	            preparedStatement = connect
	                    .prepareStatement("delete from smartparking.park where parkId= ? ; ");
	                    preparedStatement.setString(1, parkId);
	                    preparedStatement.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
}
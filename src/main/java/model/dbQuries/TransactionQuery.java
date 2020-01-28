package model.dbQuries;

import model.entities.Transaction;
import model.entities.TransactionStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionQuery {
    private Connection connect;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private final static Logger logger = Logger.getLogger( TransactionQuery.class.getName() );

    public TransactionQuery(Connection connect) {
        this.connect = connect;
    }

    public List<Transaction> getTransactions(){
        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.transaction;");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.transactionId = resultSet.getString("transactionId");
                transaction.parkId = resultSet.getString("parkId");
                transaction.userId = resultSet.getString("userId");
                transaction.parkingSpaceId = resultSet.getString("parkingSpaceId");
                transaction.startTime = LocalDateTime.parse(resultSet.getString("startTime"));
                transaction.transactionStatus = TransactionStatus.valueOf(resultSet.getString("transactionStatus"));
                transactions.add(transaction);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    public Optional<Transaction> getTransactionByTransactionId(String transactionId){

        Optional<Transaction> result = Optional.empty();

        try {
            preparedStatement = connect.prepareStatement("SELECT * from smartparking.transaction where transactionId= ?;");
            preparedStatement.setString(1, transactionId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.transactionId = transactionId;
                transaction.userId = resultSet.getString("userId");
                transaction.parkId = resultSet.getString("parkId");
                transaction.transactionStatus = TransactionStatus.valueOf(resultSet.getString("transactionStatus"));
                transaction.parkingSpaceId = resultSet.getString("parkingSpaceId");
                transaction.startTime = LocalDateTime.parse(resultSet.getString("startTime"));
                result = Optional.of(transaction);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void save(Transaction transaction) {

        if(getTransactionByTransactionId(transaction.getTransactionId()).isPresent()){
            String warning = String.format("Transaction with transaction id {0} is already exited.", transaction.getTransactionId());
            logger.log(Level.WARNING, warning);
        }else {
            String transactionId = transaction.getTransactionId();

            String userId = transaction.getUserId();
            
            String parkId = transaction.getParkId();

            String parkingSpaceId = transaction.getParkingSpaceId();

            LocalDateTime startTime = transaction.getStartTime();

            TransactionStatus transactionStatus = transaction.getTransactionStatus();

            try {
                preparedStatement = connect
                        .prepareStatement("insert into  smartparking.transaction values (?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, transactionId);
                preparedStatement.setString(2, userId);
                preparedStatement.setString(3, parkId);
                preparedStatement.setString(4, parkingSpaceId);
                preparedStatement.setString(5, startTime.toString());
                preparedStatement.setString(6, transactionStatus.toString());
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void update(Transaction transaction) {
        if(!getTransactionByTransactionId(transaction.getTransactionId()).isPresent()) {
            String warning = String.format("Cannot find transaction with transaction id {0}.", transaction.getTransactionId());
            logger.log(Level.WARNING, warning);
        }
        else{
            try{
                preparedStatement = connect
                        .prepareStatement("update smartparking.transaction set userId= ?, parkId=?, parkingSpaceId= ?, startTime= ?, transactionStatus= ? where parkId= ? and parkFloor= ? and transactionId= ?;");
                preparedStatement.setString(1, transaction.getUserId());
                preparedStatement.setString(2, transaction.getParkId());
                preparedStatement.setString(3, transaction.getParkingSpaceId());
                preparedStatement.setString(4, transaction.getStartTime().toString());
                preparedStatement.setString(5, transaction.getTransactionStatus().toString());
                preparedStatement.setString(6, transaction.getTransactionId());
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void deleteByTransactionId(String transactionId) {
        if(!getTransactionByTransactionId(transactionId).isPresent()) {
            String warning = String.format("Cannot find transaction with transaction id {0}.", transactionId);
            logger.log(Level.WARNING, warning);
        }
        else{
            try{
                preparedStatement = connect
                        .prepareStatement("delete from smartparking.transaction where transactionId= ? ; ");
                preparedStatement.setString(1, transactionId);
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }


}

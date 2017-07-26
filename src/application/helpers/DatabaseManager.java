package application.helpers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    Connection connection;
    Statement statement;

    public DatabaseManager(Connection connection) throws SQLException{
        this.statement = connection.createStatement();
    }

    public Statement getStatement(){
        return statement;
    }

    public void createTransactionsTable() throws SQLException{
        statement.executeUpdate("CREATE TABLE transactions (id INTEGER PRIMARY KEY, type STRING, amount FLOAT)");
    }

    public void dropTransactionsTable() throws SQLException{
        statement.executeUpdate("DROP TABLE IF EXISTS transactions");
    }
}

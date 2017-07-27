package application.models;

import application.helpers.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private int id;
    private String type;
    private double amount;
    private Statement statement;

    public Transaction(String type, double amount, Statement statement){
        this.type = type;
        this.amount = amount;
        this.statement = statement;
    }

    public Transaction(int id, String type, double amount, Statement statement){
        this(type, amount, statement);
        this.id = id;
    }

    public void save() throws SQLException{
        String formattedSQL = String.format("INSERT INTO transactions (type, amount) VALUES ('%s', %s)", type, amount);
        statement.executeUpdate(formattedSQL);
    }

    public static List<Transaction> findAll(DatabaseManager dbm) throws SQLException{
        ResultSet rs = dbm.findAll("transactions");
        List<Transaction> tempCollection = new ArrayList<>();
        Statement tempStatement = dbm.getStatement();
        while (rs.next()){
            String type = rs.getString("type");
            double amount = rs.getInt("amount");
            Transaction tempTransaction = new Transaction(rs.getInt("id"), type, amount, tempStatement);
            tempCollection.add(tempTransaction);
        }
        return tempCollection;
    }

    public static double getBalance(DatabaseManager dbm) throws SQLException{
        double balance = 0;
        List<Transaction> balanceList = Transaction.findAll(dbm);
        for (Transaction transaction : balanceList){
            balance += transaction.amount;
        }
        return balance;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", statement=" + statement +
                '}';
    }
}

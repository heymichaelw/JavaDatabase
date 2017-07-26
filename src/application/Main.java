package application;

import application.helpers.DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:atm.db")) {
            DatabaseManager dbm = new DatabaseManager(connection);


        } catch (SQLException ex) {
            System.out.println("SQL Exception!");
            ex.printStackTrace();
        }
    }
}

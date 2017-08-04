package application;

import application.helpers.DatabaseManager;
import application.models.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:atm.db")) {
            DatabaseManager dbm = new DatabaseManager(connection);
            ATMMenu(dbm);


        } catch (SQLException ex) {
            System.out.println("SQL Exception!");
            ex.printStackTrace();
        }
    }



    public static void ATMMenu(DatabaseManager dbm) throws SQLException{
        boolean isOn = true;
        while (isOn){
            Connection connection = DriverManager.getConnection("jdbc:sqlite:atm.db");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the Automated Teller Machine Machine!");
            System.out.println("What would you like to do?");
            System.out.println("1.  Get Account Balance");
            System.out.println("2.  Make a Deposit");
            System.out.println("3.  Make a Withdrawal");
            System.out.println("4.  Log Out");
            int userChoice = scanner.nextInt();
            switch (userChoice){
                case 1:
                    System.out.println(Transaction.getBalance(dbm));
                    System.out.println("Would you like to do something else? [Y/N]");
                    String choice = scanner.next();
                    switch (choice.toUpperCase()){
                        case "Y":
                            break;
                        case "N":
                           isOn = false;
                           break;
                    }
                    break;
                case 2:
                    System.out.println("How much would you like to deposit?");
                    double depositAmount = scanner.nextDouble();
                    Transaction newDeposit = new Transaction("deposit", depositAmount, dbm.getStatement());
                    newDeposit.save();
                    System.out.println("Would you like to do something else? [Y/N]");
                    String otherChoice = scanner.next();
                    switch (otherChoice.toUpperCase()) {
                        case "Y":
                            break;
                        case "N":
                            isOn = false;
                            break;
                    }
                    break;
                case 3:
                    System.out.println("How much would you like to withdraw?");
                    double withdrawAmount = scanner.nextDouble();
                    if (withdrawAmount < Transaction.getBalance(dbm)){
                        Transaction withdrawal = new Transaction("withdrawal", -withdrawAmount, dbm.getStatement());
                        withdrawal.save();
                        System.out.println("Successfully withdrawn!");
                        System.out.println("Would you like to do something else? [Y/N]");
                        String menuChoice = scanner.next();
                        switch (menuChoice.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                isOn = false;
                                break;
                        }
                    } else {
                        System.out.println("Sorry, you do not have enough in your account!");
                    }
                    break;
                case 4:
                    isOn = false;
                    break;
            }
        }

    }
}

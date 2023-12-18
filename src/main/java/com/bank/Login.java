package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import org.mindrot.jbcrypt.BCrypt;

public class Login {
    public static String user_name;
    String password;
    public static double balance;

    Scanner scanner = new Scanner(System.in);
    Console console = System.console();

    boolean checkUser() throws Exception {
        Connection connection = DatabaseInfo.getConnection();

        System.out.print("Enter your registered user name : ");
        user_name = scanner.next();

        char[] passwordArray = console.readPassword("Enter your password: ");
        password = new String(passwordArray);
        
        String query = "select * from userdetails where user_name = ?";
        String check_password = "select password from userdetails where user_name = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement preparedStatement1 = connection.prepareStatement(check_password);

            preparedStatement.setString(1, user_name);
            preparedStatement1.setString(1, user_name);

            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                if (resultSet1.next()) {
                    if (BCrypt.checkpw(password, resultSet1.getString("password"))) {
                        System.out.println("Logged in Successfully!");
                        balance=resultSet.getDouble("balance");
                        System.out.println("Balance is "+balance);
                        return true;
                    }
                    else
                    {
                        System.out.println("Invalid Username or password");
                        return false;
                    }
                } else {
                    System.out.println("Invalid Username or password");
                    return false;
                }
            } else {
                System.out.println("Invalid Username or password");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}

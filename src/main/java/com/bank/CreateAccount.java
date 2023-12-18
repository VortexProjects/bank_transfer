package com.bank;

import java.sql.*;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class CreateAccount {
    String name;
    String user_name;
    String password;
    double balance = 0;

    Scanner scanner = new Scanner(System.in);

    void createAccountWithInfo() throws Exception {
        Connection connection = DatabaseInfo.getConnection();
    
        System.out.print("Enter your name: ");
        String name = scanner.next();
    
        System.out.print("Enter your user name for Net Banking: ");
        String user_name = scanner.next();
    
        System.out.print("Enter your password: ");
        String password = scanner.next();

        String strong_salt = BCrypt.gensalt(10);

        String encyptedPassword=BCrypt.hashpw(password, strong_salt);

    
        String query = "INSERT INTO userdetails (account_number, user_name, password, balance, name,Created_On) VALUES (UUID_TO_BIN(UUID()), ?, ?, ?, ?,curdate())";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user_name);
            preparedStatement.setString(2, encyptedPassword);
            preparedStatement.setDouble(3, balance);
            preparedStatement.setString(4, name);
    
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account created successfully");
            } else {
                System.out.println("Account not created successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

}

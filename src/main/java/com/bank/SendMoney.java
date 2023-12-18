package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SendMoney {
    double moneyToBeSent;
    String receiverName;
    Scanner scanner = new Scanner(System.in);
    double receiverBalance;

    void sendMoney() throws Exception {
        Connection connection = DatabaseInfo.getConnection();
        if (DatabaseInfo.loginStatus != true) {
            System.out.println("Please Login to send money or Create a new Account!");
        } else {
            System.out.print("Enter the Account Name of the Recipient : ");
            receiverName = scanner.next();

            String moneySendingQuery = "select * from userdetails where user_name=?";

            PreparedStatement preparedStatement = connection.prepareStatement(moneySendingQuery);

            preparedStatement.setString(1, receiverName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Invalid User Name");
            } else {
                receiverBalance = resultSet.getDouble("balance");

                System.out.print("Enter the amount to be sent : ");
                moneyToBeSent = scanner.nextDouble();
                if (moneyToBeSent <= 0) {
                    System.out.println("Please enter the money greater than Zero (0)");
                } else {

                    if (Login.balance < moneyToBeSent) {
                        System.out.println("Insufficient balance");
                    } else {
                        String updateMoney = "update userdetails set balance = ? where user_name = ?";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(updateMoney);

                        // Money sent to the receiver
                        preparedStatement1.setDouble(1, receiverBalance + moneyToBeSent);
                        preparedStatement1.setString(2, receiverName);
                        int linesUpdatedOfReceiver = preparedStatement1.executeUpdate();

                        if (linesUpdatedOfReceiver == 1) {
                            System.out.println("Amount sent successfully!");
                        } else {
                            System.out.println("Amount not sent");
                        }

                        // Money to be deducted from the sender

                        String userName = Login.user_name;

                        String queryToFindSenderName = "select * from userdetails where user_name = ?";

                        PreparedStatement preparedStatement2 = connection.prepareStatement(queryToFindSenderName);

                        preparedStatement2.setString(1, userName);

                        ResultSet resultSet1 = preparedStatement2.executeQuery();

                        resultSet1.next();

                        String sender_name = resultSet1.getString("user_name");
                        double sender_balance = resultSet1.getDouble("balance");

                        String updateMoneyofSender = "update userdetails set balance = ? where user_name = ?";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(updateMoneyofSender);

                        preparedStatement3.setDouble(1, sender_balance - moneyToBeSent);
                        preparedStatement3.setString(2, sender_name);

                        int linesUpdatedforSender = preparedStatement3.executeUpdate();
                        if (linesUpdatedforSender == 1) {
                            System.out.println();
                            System.out.println("Amount Debited from your account!");
                            System.out.println("Balance : "+(Login.balance-moneyToBeSent));
                            System.out.println();
                        } else {
                            System.out.println("Amount not Debited from your account!");
                        }
                    }
                }

            }
        }

    }
}

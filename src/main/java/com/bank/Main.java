package com.bank;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("--------------------------------------------");

            System.out.println("Please select the below options to perform Transaction");
            System.out.println("1 : Create Account");
            System.out.println("2 : Login into your account");
            System.out.println("3 : Transfer Money");                
            if (DatabaseInfo.loginStatus==true) {
                System.out.println("4 : Log Out");
            }

            System.out.println("--------------------------------------------");

            int op = scanner.nextInt();

            switch (op) {
                case 1:
                    CreateAccount ca = new CreateAccount();
                    try {
                        ca.createAccountWithInfo();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    Login log = new Login();
                    try {
                        DatabaseInfo.loginStatus = log.checkUser();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    SendMoney sm = new SendMoney();
                    try {
                        sm.sendMoney();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                default:
                    System.out.println();
                    if (op==4 & DatabaseInfo.loginStatus!=true) {
                        System.out.println("Please select the options from the above list!");
                    }
                    if(op==4 & DatabaseInfo.loginStatus==true)
                    {
                        System.out.println("Logging out!");
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (Exception e) {
                            System.out.println("Exception in Delay Process "+e.getMessage());
                        }
                    }
                    System.out.println();
            }

            if (op==4 & DatabaseInfo.loginStatus==true) {
                break;
            }

        }
        scanner.close();

    }
}

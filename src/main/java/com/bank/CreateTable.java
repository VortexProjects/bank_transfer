package com.bank;

import java.sql.*;;

public class CreateTable 
{
    public static void main( String[] args )
    {
        String databaseurl = "jdbc:mysql://localhost:3306/bank_transfer_db";
        String username="root";
        String password="root";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection(databaseurl, username, password);

            Statement st=connection.createStatement();

            String createTable="create table if not exists userDetails (account_number binary(16) primary key ,name varchar(255),user_name varchar(255), password varchar(255), balance double)";


            boolean res=st.execute(createTable);
            
            if (!res) {
                System.out.println("Table created successfully!");
            }
            else
            {
                System.out.println("Table is not created!");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

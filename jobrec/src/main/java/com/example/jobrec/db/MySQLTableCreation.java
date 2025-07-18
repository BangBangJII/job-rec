package com.example.jobrec.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MySQLTableCreation {
    public static void main(String[] args) {
        try{
            System.out.println("Connecting to "+MySQLDBUtil.URL);
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(MySQLDBUtil.URL);
            if (conn==null){
                return;
            }
            //Step2 Drop tables in case they exist
            Statement statement = conn.createStatement();
            String sql = "DROP TABLE IF EXISTS keywords";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS history";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS items";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(sql);

            //Step3 Create a new table
            sql = "CREATE TABLE items ("
                    + "item_id  VARCHAR(255) NOT NULL,"
                    + "title VARCHAR(255),"
                    + "companyName VARCHAR(255),"
                    + "location VARCHAR(255),"
                    + "via VARCHAR(255),"
                    + "PRIMARY KEY (item_id)"
                    +")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE users ("
                    + "user_id VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "first_name VARCHAR(255),"
                    + "last_name VARCHAR(255),"
                    + "PRIMARY KEY (user_id)"
                    +")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE keywords ("
                    + "item_id VARCHAR(255) NOT NULL,"
                    + "keyword VARCHAR(255) NOT NULL,"
                    + "PRIMARY KEY (item_id, keyword),"
                    + "FOREIGN KEY (item_id) REFERENCES items(item_id)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE history ("
                    + "user_id VARCHAR(255) NOT NULL,"
                    + "item_id VARCHAR(255) NOT NULL,"
                    + "last_favor_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (user_id, item_id),"
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id),"
                    + "FOREIGN KEY (item_id) REFERENCES items(item_id)"
                    +")";
            statement.executeUpdate(sql);

            //Step4 Insert fake user 1111/3229c1097c00d497a0fd282d586be050
            sql = "INSERT INTO users VALUES ('1111','3229c1097c00d497a0fd282d586be050','Qunce','Hua')";
            statement.executeUpdate(sql);
            conn.close();
            System.out.println("Imported successfully");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

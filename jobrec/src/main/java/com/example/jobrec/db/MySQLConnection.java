package com.example.jobrec.db;

import com.example.jobrec.entity.Item;

import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MySQLConnection {
    private Connection conn;
    public MySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(MySQLDBUtil.URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close(){
        if (conn!=null){
            try {
                conn.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void saveItem(Item item){
        if (conn==null){
            System.err.println("DB connection failed");
            return;
        }
        String insertItemSql = "INSERT IGNORE INTO items VALUES (?,?,?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(insertItemSql);
            statement.setString(1,item.getId());
            statement.setString(2,item.getTitle());
            statement.setString(3,item.getCompanyName());
            statement.setString(4,item.getLocation());
            statement.setString(5,item.getVia());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        String insertKeywordSql = "INSERT IGNORE INTO keywords VALUES (?,?)";
        try {
            for (String keyword : item.getKeywords()){
                PreparedStatement statement = conn.prepareStatement(insertKeywordSql);
                statement = conn.prepareStatement(insertKeywordSql);
                statement.setString(1,item.getId());
                statement.setString(2,keyword);
                statement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void setFavoriteItems(String userId, Item item){
        if (conn==null){
            System.err.println("DB connection failed");
            return;
        }
        saveItem(item);
        String sql = "INSERT IGNORE INTO history (user_id,item_id) VALUES (?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,userId);
            statement.setString(2,item.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void removeFavoriteItems(String userId, String itemId){
        if (conn==null){
            System.err.println("DB connection failed");
            return;
        }
        String sql = "DELETE FROM history WHERE user_id=? AND item_id=?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,userId);
            statement.setString(2,itemId);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Set<String> getFavoriteItemIds(String userId) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return new HashSet<>();
        }

        Set<String> favoriteItems = new HashSet<>();

        try {
            String sql = "SELECT item_id FROM history WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String itemId = rs.getString("item_id");
                favoriteItems.add(itemId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favoriteItems;
    }
    public Set<Item> getFavoriteItems(String userId) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return new HashSet<>();
        }
        Set<Item> favoriteItems = new HashSet<>();
        Set<String> favoriteItemIds = getFavoriteItemIds(userId);

        String sql = "SELECT * FROM items WHERE item_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            for (String itemId : favoriteItemIds) {
                statement.setString(1, itemId);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    favoriteItems.add(new Item(rs.getString("item_id")
                            ,rs.getString("title")
                            ,rs.getString("location")
                            ,rs.getString("company_name")
                            ,rs.getString("via")
                            ,null
                            ,null
                            ,null
                            , getKeywords(itemId)
                            ,true));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favoriteItems;
    }
    public Set<String> getKeywords(String itemId) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return Collections.emptySet();
        }
        Set<String> keywords = new HashSet<>();
        String sql = "SELECT keyword from keywords WHERE item_id = ? ";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, itemId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String keyword = rs.getString("keyword");
                keywords.add(keyword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keywords;
    }
    public String getFullname(String userId) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return "";
        }
        //set up an empty name
        String name = "";
        //get first name and last name, and combine them together
        String sql = "SELECT first_name, last_name FROM users WHERE user_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                name = rs.getString("first_name") + " " + rs.getString("last_name");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return name;
    }
    public boolean verifyLogin(String userId, String password) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return false;
        }
        //need to verify both ID and password
        String sql = "SELECT user_id FROM users WHERE user_id = ? AND password = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean addUser(String userId, String password, String firstname, String lastname) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return false;
        }

        String sql = "INSERT IGNORE INTO users VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, password);
            statement.setString(3, firstname);
            statement.setString(4, lastname);

            return statement.executeUpdate() == 1; //1 --> true  0 --> false
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

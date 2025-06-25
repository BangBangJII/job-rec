package com.example.jobrec.db;

public class MySQLDBUtil {
    private static final String INSTANCE = "database-1.c3w88sq62308.ca-central-1.rds.amazonaws.com";
    private static final String PORT_NUM = "3306";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "19951205";
    public static final String DB_NAME = "jobrecdb";
    public static final String URL = "jdbc:mysql://" + INSTANCE + ":" + PORT_NUM + "/" + DB_NAME+"?user="+USERNAME+"&password="+PASSWORD+"&autoReconnect=true&serverTimezone=UTC";

}

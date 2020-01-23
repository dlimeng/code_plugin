package com.code.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;


public class MysqlJdbc {
    private static MysqlJdbc mysqlJdbc = new MysqlJdbc();
    private String className;
    private String jdbcUrl;
    private String user;
    private String password;
    private Connection connection;
    private DatabaseMetaData databaseMetaData;

    private MysqlJdbc() {
        PropertiesUtil properties = PropertiesUtil.getDatabaseProperties();
        this.className = properties.getProperty("className");
        this.jdbcUrl = properties.getProperty("jdbcUrl");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
        try {
            Class.forName(this.className).newInstance();
            String url = String.format(this.jdbcUrl, this.user, this.password);
            this.connection = DriverManager.getConnection(url);
            this.databaseMetaData = this.connection.getMetaData();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MysqlJdbc getMysqlJdbc() {
        return mysqlJdbc;
    }

    public DatabaseMetaData getDatabaseMetaData() {
        return this.databaseMetaData;
    }

    public String jdbcType(String type) {
        if (type.equals("INT")) {
            return "Integer ";
        }
        if (type.equals("VARCHAR")) {
            return "String ";
        }
        if (type.equals("TIMESTAMP")) {
            return "Date ";
        }
        if (type.equals("TEXT")) {
            return "String ";
        }
        if (type.equals("BIGINT")) {
            return "Long ";
        }
        if (type.equals("DATETIME")) {
            return "Date ";
        }
        if (type.equals("DECIMAL")) {
            return "BigDecimal ";
        }
        return null;
    }
}


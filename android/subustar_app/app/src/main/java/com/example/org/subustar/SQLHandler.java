package com.example.org.subustar;

import android.os.StrictMode;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLHandler {
    private String classs, db, username, password, ip, port;
    private Connection conn;

    public Connection getConn() { return conn; }

    public SQLHandler(String db, String un, String pw, String ip, String port) {
        this.classs = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        /* db : databse, username, password, conn : connection */
        this.db = db;
        this.username = un;
        this.password = pw;
        this.ip = ip;
        this.port = port;
        connect();
    }

    public boolean connect() {
        Connection conn = null;
        String connURL = "jdbc:jtds:sqlserver://" + this.ip + ":" + this.port +
                         ";databaseName=" + this.db + ";";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            this.conn = DriverManager.getConnection(connURL, this.username, this.password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            this.conn = null;
            return false;
        }
    }

    // CRUID
    // Create
    // Read
    // Update
    // Insert
    // Delete, Drop

    public void drop(String tablename) {
        String query = "drop table " + tablename;
        System.out.println("쿼리 실행 : "+query);
        try {
            Statement statement = this.conn.createStatement();
            statement.execute(query);
            System.out.println("테이블 삭제 성공 : " + tablename);
        }
        catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }

    public ArrayList<String[]> read(String tablename, String where, String top) {
        String query = "SELECT TOP "+top+" * FROM " + tablename + " WHERE " + where;
        System.out.println("쿼리 실행 : "+query);
        ResultSet rs;
        try {
            Statement statement = this.conn.createStatement();
            rs = statement.executeQuery(query);

            ArrayList<String[]> results = new ArrayList<>();
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String result[] = new String[columnCount];
                for (int i=0; i<columnCount; i++) {
                    result[i] = rs.getString(i+1);
                }
                results.add(result);
            }
            return results;
        }
        catch (SQLException sqle) {
            System.out.println(sqle);
            return null;
        }
    }

    public ArrayList<String[]> read(String tablename, String where) {
        String query = "select * from " + tablename + " WHERE " + where;
        System.out.println("쿼리 실행 : "+query);
        ResultSet rs;
        try {
            Statement statement = this.conn.createStatement();
            rs = statement.executeQuery(query);

            ArrayList<String[]> results = new ArrayList<>();
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String result[] = new String[columnCount];
                for (int i=0; i<columnCount; i++) {
                    result[i] = rs.getString(i+1);
                }
                results.add(result);
            }
            return results;
        }
        catch (SQLException sqle) {
            System.out.println(sqle);
            return null;
        }
    }

    public void update(String tablename, String column, String value, String where) {
        String query = "update " + tablename + " set " + column + " = " + value + where;
        System.out.println("쿼리 실행 : "+query);
        try {
            Statement statement = this.conn.createStatement();
            statement.execute(query);
            System.out.println("테이블 업데이트 성공 : " + tablename);
        }
        catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }

    public void insert(String tablename, String values) {
        String query = "insert into " + tablename + " values (" + values + ")";
        System.out.println("쿼리 실행 : "+query);
        try {
            Statement statement = this.conn.createStatement();
            statement.execute(query);
            System.out.println("테이블 값 추가 성공 : " + tablename);
        }
        catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }

    public void create(String tablename, String[] cnames, String[] dtypes, String[] consts) {
        String query = "";
        for(int i=0; i<cnames.length; i++) {
            if(i == 0) {
                query += "CREATE TABLE " + tablename + " (";
            }
            query += cnames[i] + " " + dtypes[i] + " " + consts[i];
            if(i == cnames.length-1) {
                query += ")";
            }
            else {
                query += ", ";
            }
        }
        System.out.println("쿼리 실행 : "+query);
        try {
            Statement statement = this.conn.createStatement();
            statement.execute(query);
            System.out.println("테이블 생성 성공 : " + tablename);
        }
        catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }
}
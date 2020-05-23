import java.sql.*;

public class SQLHandler {
    String classs, db, username, password, ip, port;
    Connection conn;

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

    public void connect() {
        // connURL : 서버 접속시 필요한 ip주소, db 이름, 사용자 이름, 비밀번호
        String ConnURL = "jdbc:sqlserver://" + this.ip + ":" + this.port + ";" +
                "databaseName=" + this.db + ";";
        try {
            Class.forName(classs);
            DriverManager.setLoginTimeout(1);
            // 연결을 한다
            this.conn = DriverManager.getConnection(ConnURL, this.username, this.password);
            System.out.println("연결 성공!");
        } catch (SQLException sqle) {
            System.out.println("SQLException : " + sqle);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("ClassNotFoundException : " + cnfe);
        }

        catch (Exception e) {
            System.out.println("MSSQLhandler % connect " + e.getMessage() + " Exception");
        }
    }

    //cruid
    // create
    // read
    // update
    // insert
    // delete

    public void drop(String tablename) {
        String query = "drop table if exists " + tablename;
        try {
            Statement statement = this.conn.createStatement();
            statement.execute(query);
            System.out.println("테이블 삭제 성공 : " + tablename);
        }
        catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }

    public String[] read(String tablename, String where) {
        String query = "select * from " + tablename + where;
        ResultSet rs;
        try {
            Statement statement = this.conn.createStatement();
            rs = statement.executeQuery(query);
            String result[] = new String[2];
            while (rs.next()) {
                result[0] = rs.getString(1);
                result[1] = rs.getString(2);
            }
            return result;
        }
        catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public void update(String tablename, String column, String value, String where) {
        String query = "update " + tablename + " set " + column + " = " + value + where;
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
        System.out.println(query);

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
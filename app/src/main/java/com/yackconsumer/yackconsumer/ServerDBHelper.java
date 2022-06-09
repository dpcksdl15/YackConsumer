package com.yackconsumer.yackconsumer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerDBHelper {
    public static Connection dbConn;

    public static Connection getConnection()
    {
        Connection connect = null;
        try {
            String user = "bc_pos";
            String pw = "bc_pos";
            String url = "jdbc:oracle:thin:@eunsung-bc.com:1521:orcl";

            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection(url, user, pw);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("DB 드라이버 로딩 실패 :"+cnfe.toString());
        } catch (SQLException sqle) {
            System.out.println("DB 접속실패 : "+sqle.toString());
        } catch (Exception e) {
            System.out.println("Unkonwn error");
            e.printStackTrace();
        }
        return connect;
    }
}

package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库连接工具类
 */
public class DBconnectUtils {

    private static ComboPooledDataSource dataSource;
    static String urlBefore = "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    /*
    获取连接池连接
     */
    public static Connection getConnect(String driver, String url, String user, String pwd) {
        dataSource = new ComboPooledDataSource();
        Connection connection;
        try {
            dataSource.setDriverClass(driver);
            dataSource.setJdbcUrl(url);
            dataSource.setUser(user);
            dataSource.setPassword(pwd);
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("用户名或密码错误，连接失败！");
            return null;
        }
        return connection;
    }

    /*
    释放连接
     */
    public static void close(Connection conn, PreparedStatement pst, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

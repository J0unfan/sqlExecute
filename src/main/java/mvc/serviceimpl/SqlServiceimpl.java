package mvc.serviceimpl;

import mvc.service.SqlService;
import org.springframework.stereotype.Service;
import utils.DBconnectUtils;

import java.sql.*;
import java.util.*;

@Service
public class SqlServiceimpl implements SqlService {

    Connection connection = null;
    PreparedStatement prests = null;
    ResultSet resultSet = null;

    @Override
    public Map<String, Object> getDBs(String driver, String url,String user,String pwd) {
        Map<String, Object> result = null;
        Set<String> database;
        try {
            database = new LinkedHashSet();
            result = new HashMap<>();
            connection = DBconnectUtils.getConnect(driver, url, user, pwd);
            //判断连接是否创建成功
            if (connection != null) {
                System.out.println("数据库连接成功");
            }else{
                System.out.println("数据库连接失败");
                result.put("err","error");
                return null;
            }
            prests = connection.prepareStatement("show databases");
            resultSet = prests.executeQuery();
            while (resultSet.next()) {
                String re = resultSet.getString(1);
                database.add(re);
            }
            for (String s: database){
                System.out.print(s + " ");
            }
            result.put("succ",database);
            DBconnectUtils.close(connection, prests, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int IDUP(String driver, String url, String user, String pwd, String sql) {
        int re = -1;
        try {
            connection = DBconnectUtils.getConnect(driver, url, user, pwd);

            prests = connection.prepareStatement(sql);
            re = prests.executeUpdate();
            System.out.println("sql语句执行成功" + re);
            DBconnectUtils.close(connection, prests, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    @Override
    public Map<String, Object> select(String driver, String url, String user, String pwd, String sql) {
        Map<String, Object> resSelect = new HashMap<>();
        List<Object> colValues = new ArrayList<>();
        try {
            connection = DBconnectUtils.getConnect(driver, url, user, pwd);
            System.out.println("数据库连接成功");
            prests = connection.prepareStatement(sql);
            resultSet = prests.executeQuery();
            System.out.println("sql语句执行成功");
            ResultSetMetaData md = resultSet.getMetaData(); //获取列集
            int columnCount = md.getColumnCount(); //列的数量
            //将列名放入数组中
            String[] colName = new String[columnCount];
            for (int i = 0; i < columnCount; i++){ //循环列
                colName[i] = md.getColumnName(i + 1);
            }
            while (resultSet.next()){
                List<String> list = new ArrayList();//将每行元素放入List里
                for (String n: colName){
                    list.add(resultSet.getString(n));
                }
                colValues.add(list);//再讲一行元素值存入list的一个元素位置
            }
            resSelect.put("colCount", columnCount);//列数
            resSelect.put("colName", colName);//列名
            resSelect.put("resultSet", colValues);//结果集
            DBconnectUtils.close(connection, prests, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resSelect;
    }

}

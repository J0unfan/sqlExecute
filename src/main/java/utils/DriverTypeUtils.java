package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 确定数据库种类，返回对应数据库的驱动和连接url的工具类
 */
public class DriverTypeUtils {
    /*
    根据数据库软件类型、地址、数据库返回对应的驱动和连接
     */
    public static Map<String,String> chooseDBDriver(String dbType, String hostStr, String db){
        Map result = new HashMap();
        if (dbType.equals("mysql")){
            result.put("driver","com.mysql.jdbc.Driver");
            if (db != null) {
                result.put("url", "jdbc:mysql://" + hostStr + ":3306" + "/" + db);
            }else{
                result.put("url", "jdbc:mysql://" + hostStr + ":3306");
            }
        }else if (dbType.equals("oracle")){
            result.put("driver","oracle.jdbc.driver.OracleDriver");
            if (db != null){
                result.put("url","jdbc:oracle:thin:@"+ hostStr + ":1521" + "/" + db);
            }else{
                result.put("url","jdbc:oracle:thin:@"+ hostStr + ":1521");
            }
        }else if (dbType.equals("sql server")){
            result.put("driver","com.microsoft.sqlserver.jdbc.SQLServerDriver");
            if (db != null){
                result.put("url","jdbc:sqlserver://" + hostStr +":1433;DatabaseName=" + db);
            }else{
                result.put("url","jdbc:sqlserver://" + hostStr +":1433;");
            }
        }
        return result;
    }
}

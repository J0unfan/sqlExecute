package mvc.controller;

import mvc.service.SqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.DriverTypeUtils;

import java.util.Map;

@RestController
public class Controller {
    @Autowired
    private SqlService sqlService;
    Map<String, String> driverAndUrl;

    String driver;
    String url;
    @RequestMapping("/getDBs")
    //连接数据库并获取当前连接所有数据库
    public Map<String, Object> getConn(@RequestParam(value = "DBType") String type, @RequestParam(value = "host") String hostStr,
                                       @RequestParam(value = "user") String user, @RequestParam(value = "pwd") String pwd){
        Map<String, Object> re;

        driverAndUrl = DriverTypeUtils.chooseDBDriver(type, hostStr, null);
        driver = driverAndUrl.get("driver");
        url = driverAndUrl.get("url");
        re = sqlService.getDBs(driver,url, user, pwd);

        return re;
    }

    @RequestMapping("/IDUP")
    //进行数据的增、删、改操作
    public int IDUP(@RequestParam(value = "DBType") String type, @RequestParam(value = "host") String hostStr,
                    @RequestParam(value = "user") String user, @RequestParam(value = "pwd") String pwd,
                    @RequestParam(value = "db") String db, @RequestParam(value = "sql") String sql){
        int res = -1;

        //根据用户选择的数据库类型和输入的主机地址设置对应的驱动和连接url
        driverAndUrl = DriverTypeUtils.chooseDBDriver(type, hostStr, db);
        driver = driverAndUrl.get("driver");
        url = driverAndUrl.get("url");

        res = sqlService.IDUP(driver, url, user, pwd, sql);

        System.out.println(res);
        return res;
    }

    @RequestMapping("/select")
    //执行select查询语句
    public Map<String, Object> select(@RequestParam(value = "DBType") String type, @RequestParam(value = "host") String hostStr,
                       @RequestParam(value = "user") String user, @RequestParam(value = "pwd") String pwd,
                       @RequestParam(value = "db") String db, @RequestParam(value = "sql") String sql){
        Map<String, Object> res;

        driverAndUrl = DriverTypeUtils.chooseDBDriver(type, hostStr, db);
        driver = driverAndUrl.get("driver");
        url = driverAndUrl.get("url");

        res = sqlService.select(driver, url, user, pwd, sql);
        System.out.println(res.get("colCount"));
        String[] colName = (String[]) res.get("colName");
        for (String s : colName){
            System.out.print(s + " ");
        }
        System.out.println(res.get("resultSet"));

        return res;
    }
}

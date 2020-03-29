package mvc.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SqlService {
    //获取数据库连接，并查找连接下所有数据库
    Map getDBs(String driver, String url, String user, String pwd);

    //选择数据库后，执行非select语句
    int IDUP(String driver, String url, String user, String pwd, String sql);

    //执行select查询语句，需要返回结果集
    Map<String, Object> select(String driver, String url, String user, String pwd, String sql);
}

$(function () {
    $('#connWin').window({
        width:500,
        height:400,
        modal:true,
        title:"数据库连接配置",
        collapsible:false,
        minimizable:false,
        maximizable:false,
        closable:false,
        draggable:false,
        resizable:false
    });

    $('#DbsWin').window({
        width:500,
        height:200,
        title:"选择数据库",
        collapsible:false,
        minimizable:false,
        maximizable:false,
        closable:false,
        draggable:false,
        resizable:false
    });

    $('#sqlWin').window({
        width:500,
        height:300,
        title:"SQL语句输入",
        collapsible:false,
        minimizable:false,
        maximizable:false,
        closable:false,
        draggable:false,
        resizable:false
    });

    $('#resultWin').window({
        width:500,
        height:300,
        title:"查询结果返回",
        collapsible:false,
        minimizable:false,
        maximizable:false,
        closable:false,
        draggable:false,
        resizable:false
    });
});

function getConnection() {
    document.getElementById("connection").disabled = true;

    var DBType = $('#DBType').val();
    var host = $('#host').val();
    var user = $('#username').val();
    var pwd = $('#password').val();

    $.ajax({
        type: "post",
        url: "getDBs",
        data: {"DBType":DBType, "host": host, "user": user, "pwd": pwd},
        success: function (data) {
            if (data.succ != null){
                $.messager.alert({
                    title:'连接成功',
                    msg:'数据库连接成功！',
                    icon: "info"
                });
                for (var i = 0; i<data.succ.length; i++){
                    $('#Dbs').append("<option value='"+ data.succ[i] +"'>"+data.succ[i]+"</option>");
                }
                document.getElementById("connection").disabled = false;
            }else{
                $.messager.alert({
                    title:'连接失败',
                    msg:'用户名或密码错误，连接失败！',
                    icon: "error"
                });
                document.getElementById("connection").disabled = false;
            }

        },error: function () {
            console.log("error");
        }
    });
}

function executeSql() {
    var DBType = $('#DBType').val();
    var host = $('#host').val();
    var user = $('#username').val();
    var pwd = $('#password').val();
    var db = $('#Dbs').val();
    var sql1 = $('#sqlTextArea').val();
    var sql = sql1.trim();//去除SQL语句前后的空格
    //判断sql语句是否是select查询语句，indexOf方法返回-1则没有出现
    if (sql.indexOf("select") == -1 && sql.indexOf("show") == -1){//sql语句属于非查询语句时
        $.ajax({
            type: "post",
            url: "IDUP",
            data: {"DBType":DBType, "host":host, "user":user,
            "pwd":pwd, "db":db, "sql":sql},
            success: function (data) {
                //data等于-1则说明语句执行失败，！=-1则说明执行成功
                if (data != -1){
                    $.messager.alert({
                        title:'执行成功',
                        msg:'SQL语句执行成功！',
                        icon: "info"
                    });
                }else {
                    $.messager.alert({
                        title:'执行失败',
                        msg:'SQL语句执行失败，请重试！',
                        icon: "error"
                    });
                }
            }
        });
    }else {//sql语句是属于select查询语句时，访问查询的controller
        $.ajax({
            type: "post",
            url: "select",
            data: {"DBType":DBType, "host":host, "user":user,
                "pwd":pwd, "db":db, "sql":sql},
            success:function (data) {
                //表格标题
                $("#resultTable").append("<tr>");
                for (var i = 0; i < data.colCount; i++){//表的列名
                    $("#resultTable").append("<th>" + data.colName[i] + "</th>")
                }
                $("#resultTable").append("</tr>");
                //表格内容
                for (var i = 0; i < data.resultSet.length; i++){//查询结果的行数
                    $("#resultTable").append("<tr>");
                    for (var j = 0; j < data.colCount; j++){//列数
                        $("#resultTable").append("<td>" + data.resultSet[i][j] + "</td>");
                    }
                    $("#resultTable").append("</tr>");
                }
                console.log(data);
            },
            error:function () {
                $.messager.alert({
                    title:'执行失败',
                    msg:'SQL语句执行失败，可能是该表不存在或主键冲突等异常！',
                    icon: "error"
                });
            }
        })
    }
}
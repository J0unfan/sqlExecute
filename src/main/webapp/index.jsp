<%--
  Created by IntelliJ IDEA.
  User: sofia
  Date: 2020/3/20
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="static/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="static/jquery-easyui/themes/icon.css">
<script src="${pageContext.request.contextPath}/static/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/jquery-easyui/jquery.easyui.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
<html>
<head>
    <title>SQL语句执行程序</title>
    <style type="text/css">
        table{border-right:1px solid grey;border-bottom:1px solid grey;}
        table th{border-right:1px solid grey;border-bottom:1px solid grey;}
        table td{border-left:1px solid grey;border-top:1px solid grey;}
    </style>
</head>
<body>
<%--连接配置窗口--%>
<div id="connWin" style="top: 30px;left: 100px">
    <div style="margin-bottom:20px;padding:10px 70px">
        <select id="DBType" class="easyui-combobox" name="DBtype" style="width:70%" data-options="label:'数据库'">
            <option value="mysql">MySQL</option>
            <option value="oracle">Oracle</option>
            <option value="sql server">SQL server</option>
        </select>
    </div>
    <div style="margin-bottom:20px;padding:10px 70px">
        <input id="host" class="easyui-textbox" name="host" style="width:70%" data-options="label:'主机或IP', required:true">
    </div>
    <div style="margin-bottom:20px;padding:10px 70px">
        <input id="username" class="easyui-textbox" name="subject" style="width:70%" data-options="label:'用户名', required:true">
    </div>
    <div style="margin-bottom:20px;padding:10px 70px">
        <input id="password" class="easyui-textbox" name="password" type="password" style="width:70%" data-options="label:'密码', required:true">
    </div>
    <div style="text-align:center;padding:5px 0">
        <button id="connection" class="easyui-linkbutton" onclick="getConnection()" style="width:80px">连接</button>
    </div>
</div>

<%--数据库选择窗口--%>
<div id="DbsWin" style="top:450px;left: 100px">
    <div style="margin-bottom:20px; padding-top: 50px;padding-left: 50px">
        数据库：
        <select id="Dbs" name="Dbs" style="width: 50%;height: 30px">
        </select>
    </div>
</div>

<%--sql语句输入窗口--%>
<div id="sqlWin" style="top:30px;left: 650px">
    <textarea id="sqlTextArea" rows="4" cols="6" style="height: 220px;width: 480px"></textarea>
    <div style="text-align:center;padding:5px 0">
        <button class="easyui-linkbutton" id="" onclick="executeSql()" style="width:80px">执行</button>
    </div>
</div>

<%--返回结果集--%>
<div id="resultWin" style="top:350px;left: 650px">
    <table id="resultTable"></table>
</div>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">角色管理</a></li>
        <li><a href="#">添加角色</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>角色信息</span></div>
    <form action="${pageContext.request.contextPath}/role/saveOrUpdate" method="post">
        <ul class="forminfo">
            <li><label>角色名称</label><input name="roleName" type="text" class="dfinput" /></li>
            <li><label>角色描述</label><input name="roleDesc" type="text" class="dfinput" /></li>
            <li><label>&nbsp;</label><input  type="submit" class="btn" value="确认保存"/></li>
        </ul>
    </form>
</div>


<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>


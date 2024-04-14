<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %> <!-- 确保EL表达式不被忽略 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            $(".click").click(function(){
                $(".tip").fadeIn(200);
            });

            $(".tiptop a").click(function(){
                $(".tip").fadeOut(200);
            });

            $(".sure").click(function(){
                $(".tip").fadeOut(100);
            });

            $(".cancel").click(function(){
                $(".tip").fadeOut(100);
            });

        });
    </script>


</head>


<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">用户管理</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="tools">

        <ul class="toolbar">
            <li>
                <a href="${pageContext.request.contextPath}/user/userDispatcher">
                    <span>
                        <img src="${pageContext.request.contextPath}/images/t01.png" />
                    </span>添加
                </a>
            </li>
            <li class="click"><span><img src="${pageContext.request.contextPath}/images/t02.png" /></span>修改</li>
            <li><span><img src="${pageContext.request.contextPath}/images/t03.png" /></span>删除</li>
            <li><span><img src="${pageContext.request.contextPath}/images/t04.png" /></span>统计</li>
        </ul>


        <ul class="toolbar1">
            <li><span><img src="${pageContext.request.contextPath}/images/t05.png" /></span>设置</li>
        </ul>

    </div>


    <table class="tablelist">
        <thead>
        <tr>
            <th><input name="" type="checkbox" value="" checked="checked"/></th>
            <th>编号<i class="sort"><img src="${pageContext.request.contextPath}/images/px.gif" /></i></th>
            <th>账号</th>
            <th>名称</th>
            <th>密码</th>
            <th>邮箱</th>
            <th>手机号</th>

            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageInfo.list}" var="user">
            <tr>
                <td><input name="" type="checkbox" value="" /></td>
                <td>${user.userId}</td>
                <td>${user.userName}</td>
                <td>${user.realName}</td>
                <td>${user.password}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>


                <td>
                    <a href="${pageContext.request.contextPath}/user/userDispatcher?userId=${user.userId}" class="tablelink">更新</a>
                    <a href="javascript:void(0)" onclick="deleteRole(${user.userId})" class="tablelink">删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="pagin">
        <jsp:include page="/PageBase.jsp"></jsp:include>
        <form action="${pageContext.request.contextPath}/user/query" method="get" id="pager" >
            <input type="hidden" name="pageSize" id="pageSize" value="${pageInfo.pageSize}">
            <input type="hidden" name="pageNum" id="pageNum" value="${pageInfo.pageNum}">
        </form>
    </div>



    <div class="tip">
        <div class="tiptop"><span>提示信息</span><a></a></div>

        <div class="tipinfo">
            <span><img src="${pageContext.request.contextPath}/images/ticon.png" /></span>
            <div class="tipright">
                <p>是否确认对信息的修改 ？</p>
                <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
            </div>
        </div>

        <div class="tipbtn">
            <input name="" type="button"  class="sure" value="确定" />&nbsp;
            <input name="" type="button"  class="cancel" value="取消" />
        </div>

    </div>




</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
    function deleteRole(id){
        if(window.confirm("是否确定要删除该记录呢?")){
            window.location.href = "${pageContext.request.contextPath}/user/deleteById?id="+id;
        }

    };
</script>

<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>
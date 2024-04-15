<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">用户管理</a></li>
        <li><a href="#">添加用户</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>用户信息</span></div>
    <form action="${pageContext.request.contextPath}/user/userSaveOrUpdate" method="post">
        <ul class="forminfo">
            <c:if test="${user.userId != null}">
                <li><label>编号</label><input name="user.userId" value="${user.userId}" type="text" class="dfinput" readonly /></li>
            </c:if>
            <li>
                <label>账号</label><input name="user.userName" value="${user.userName}" type="text" class="dfinput" id="userName"/>
                <i id="userNameI"></i>
            </li>

            <li><label>名称</label><input name="user.realName" value="${user.realName}" type="text" class="dfinput" /></li>
            <c:if test="${user.userId == null}">
                <li><label>密码</label><input name="user.password" value="${user.password}" type="text" class="dfinput" /></li>
            </c:if>
            <li><label>邮箱</label><input name="user.email" value="${user.email}" type="text" class="dfinput" /></li>
            <li><label>手机号</label><input name="user.phone" value="${user.phone}" type="text" class="dfinput" /></li>
            <li>
                <div style="height: 32px;line-height: 32px;">
                    <label>角色</label>
                    <c:forEach items="${roles}" var="role"><!--外层循环将每个角色信息显示再网页上-->
                    <c:set var="flag" value="${false}"></c:set>
                        <c:forEach items="${ownerRoleIds}" var="ownerRoleId"><!--内层循环判断这个角色是否是当前用户拥有的-->
                            <c:if test="${ownerRoleId eq role.roleId}">
                                <c:set var="flag" value="${true}"></c:set>
                            </c:if>
                        </c:forEach>
                        <input name="roleIds" value="${role.roleId}" type="checkbox" ${flag eq true ?"checked":""}  />${role.roleName}
                    </c:forEach>
                </div>
            </li>

            <li><label>&nbsp;</label><input  type="submit" class="btn" value="确认保存"/></li>
        </ul>
    </form>
</div>

<script>
    $(function(){
        $("#userName").blur(function () {
            if(${user.userId == null }){
                var obj = $(this).val().trim() || '';
                if(obj.length >= 3 && obj.length  <= 10){
                    $.get("${pageContext.request.contextPath}/user/userNameCheck",{userName:obj},function (msg) {
                        if(msg == "1"){
                            $("#userNameI").html("<span style='color:green'>账号正常</span>");
                        }else{
                            //重复的情况下
                            $("#userNameI").html("<span style='color:red'> 账号存在，请重新输入 </span>")
                        }
                    });
                }else{
                    $("#userNameI").html("<span style='color:red'>账号必须为3-10位</span>")
                }
            };
        });
    });
</script>


<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>


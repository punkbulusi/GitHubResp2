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
        <li><a href="#">添加客户</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>客户信息</span></div>
    <form action="${pageContext.request.contextPath}/customer/saveOrUpdate" method="post">
        <input type="hidden" name="customerId" value="${customer.customerId}">
        <ul class="forminfo">
            <li><label>客户名称</label><input name="customerName" value="${customer.customerName}" type="text" class="dfinput" /></li>
            <li><label>客户电话</label><input name="mobilePhone" value="${customer.mobilePhone}" type="text" class="dfinput" /></li>
            <li><label>电子邮箱</label><input name="email" value="${customer.email}" type="text" class="dfinput" /></li>
            <li><label>通讯地址</label><input name="address" value="${customer.address}" type="text" class="dfinput" /></li>
            <li><label>身份证号码</label><input name="idCard" value="${customer.idCard}" type="text" class="dfinput" /></li>

            <li>
                <label>业务员</label>
                <div class="vocation">
                    <select class="select1" name="userId">
                        <c:forEach items="${roleSalemanS}" var="roleSaleman">
                            <option value="${roleSaleman.userId}" ${roleSaleman.userId == customer.userId ? "selected" : ""}>${roleSaleman.realName}</option >
                        </c:forEach>
                    </select>
                </div>
            </li>

            <li>
                <label>常用区间</label>
                <div class="vocation">
                    <select class="select1" name="baseId">
                        <c:forEach items="${intervals}" var="interval">
                            <option value="${interval.baseId}" ${interval.baseId == customer.baseId ? "selected" : ""}>${interval.baseName}</option >
                        </c:forEach>
                    </select>
                </div>
            </li>

            <li>
                <label>备注</label>
                <textarea class="textinput" name="remark" value="${customer.remark}"></textarea>
            </li>

            <li><label>&nbsp;</label><input  type="submit" class="btn" value="确认保存"/></li>
        </ul>
    </form>
</div>


<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>


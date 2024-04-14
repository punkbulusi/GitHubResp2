<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/select-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/editor/kindeditor.js"></script>

<script type="text/javascript">
    KE.show({
        id : 'content7',
        cssPath : './index.css'
    });
</script>

<script type="text/javascript">
    $(document).ready(function(e) {
        $(".select1").uedSelect({
            width : 345
        });
        $(".select2").uedSelect({
            width : 167
        });
        $(".select3").uedSelect({
            width : 100
        });
    });
</script>
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
        <li><a href="#">基础数据管理</a></li>
        <li><a href="#">添加基础数据</a></li>
    </ul>
</div>

<div class="formbody">
    <div class="formtitle"><span>基础数据信息</span></div>
    <form action="${pageContext.request.contextPath}/basicData/saveOrUpdate" method="post">
        <input name="baseId" value="${basicData.baseId}" type="hidden"  />
        <ul class="forminfo">
            <li><label>数据名称</label><input name="baseName" value="${basicData.baseName}" type="text" class="dfinput" /></li>
            <li><label>数据描述</label><input name="baseDesc" value="${basicData.baseDesc}" type="text" class="dfinput" /></li>

            <li><label>大类</label>
                <div class="vocation">
                    <select class="select1" name="parentId">
                        <option value="">-----本身就是大类-----</option >
                        <c:forEach items="${parentBasicDatas}" var="parentBasicData">
                            <option value="${parentBasicData.baseId}" ${parentBasicData.baseId == basicData.parentId ? "selected" : ""}>${parentBasicData.baseName}</option >
                        </c:forEach>
                    </select>
                </div>
            </li>

            <li><label>&nbsp;</label><input  type="submit" class="btn" value="确认保存"/></li>
        </ul>
    </form>
</div>


<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>


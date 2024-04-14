<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="pagin">
    <div class="message">
        共<i class="blue">${pageInfo.total}</i>条记录，
        当前显示第&nbsp;<i class="blue">${pageInfo.pageNum}</i>页，
        每页<select name="pageSize" onchange="changePageSize(this.value)" style="width: 50px;">
                <option value="5" ${pageInfo.pageSize == 5 ? "selected" :""}>5</option>
                <option value="10" ${pageInfo.pageSize == 10 ? "selected" :""}>10</option>
                <option value="30" ${pageInfo.pageSize == 30 ? "selected" :""}>30</option>
                <option value="50" ${pageInfo.pageSize == 50 ? "selected" :""}>50</option>
                <option value="100" ${pageInfo.pageSize == 100 ? "selected" :""}>100</option>
            </select>条
    </div>
    <ul class="paginList">
        <li class="paginItem"><a href="javascript:frontPage()"><span class="pagepre"></span></a></li><!--前一页-->
        <li class="paginItem ${pageInfo.pageNum == 1?"current":""}"><a href="javascript:changePageNum(1);">首页</a></li>
        <c:set var="starPageNum" value="${pageInfo.pageNum-2 < 1 ? 1 : pageInfo.pageNum-2}"></c:set>
        <c:set var="endPageNum" value="${pageInfo.pageNum+2 > pageInfo.pages ? pageInfo.pages : pageInfo.pageNum+2}"></c:set>
        <c:forEach var="i" step="1" begin="${starPageNum}" end="${endPageNum}">
            <li class="paginItem ${pageInfo.pageNum == i?"current":""}"><a href="javascript:changePageNum(${i});">${i}</a></li>
        </c:forEach>
        <li class="paginItem ${pageInfo.pageNum == pageInfo.pages?"current":""}"><a href="javascript:changePageNum(${pageInfo.pages});">尾页</a></li>
        <li class="paginItem"><a href="javascript:nextPage()"><span class="pagenxt"></span></a></li><!--后一页-->
    </ul>
</div>

<script type="text/javascript">

    function frontPage(){
        document.getElementById("pageNum").value=${pageInfo.pageNum<=1? 1 : pageInfo.pageNum-1 };
        document.getElementById("pager").submit();//表单提交
    }
    function nextPage(){
        document.getElementById("pageNum").value=${pageInfo.pageNum>=pageInfo.pages?pageInfo.pages:pageInfo.pageNum+1 };
        document.getElementById("pager").submit();//表单提交
    }
    function changePageNum(cur_pageNum){
        document.getElementById("pageNum").value=cur_pageNum;
        document.getElementById("pager").submit();//表单提交
    }
    function changePageSize(cur_pageSize){
        document.getElementById("pageSize").value=cur_pageSize;
        document.getElementById("pager").submit();//表单提交
    }
</script>

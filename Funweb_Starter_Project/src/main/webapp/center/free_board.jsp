<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<jsp:include page="../inc/top.jsp" />
		
		<div id="sub_img_center"></div>
		
		<jsp:include page ="../inc/left.jsp"/>
		<article>
			<h1>자유게시판</h1>
			<table id = "notice">
				<tr>
					<th class="tno">No.</th>
					<th class="ttitle">Title</th>
					<th class="twrite">Write</th>
					<th class="tdate">Date</th>
					<th class="tread">Read</th>
				</tr>
				<tr onclick="">
				<td class="td_title"></td>
				</tr>
			</table>
			<div id="table_search">
				<input type="button" value="글쓰기" class="btn" onclick="location.href='free_write.jsp'">
			</div>
			<div id="table_search">
				<form action="notice_search.jsp" method="get">
					<input type="text" name="keyword" class="input_box">
					<input type="submit" value="Search" class="btn">
				</form>
			</div>
			
			<div class="clear"></div>
			<div id="page_control">
			</div>
		</article>
	
	<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
	
</body>
</html>
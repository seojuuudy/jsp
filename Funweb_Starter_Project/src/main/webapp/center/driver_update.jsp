<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int idx = Integer.parseInt(request.getParameter("idx"));
String pageNum = request.getParameter("pageNum");

FileBoardDAO dao = new FileBoardDAO();

FileBoardDTO fileboard = dao.selectFileBoard(idx);
%>	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/driver_update.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp" />
		<!-- 헤더 들어가는곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 본문 메인 이미지 -->
		<div id="sub_img_center"></div>
		<!-- 왼쪽 메뉴 -->
		<jsp:include page="../inc/left.jsp" />
		<!-- 본문 내용 -->
		<article>
			<h1>Driver Update</h1>
			<form action="driver_updatePro.jsp" method="post" enctype="multipart/form-data">
				<input type="hidden" name = "idx" value="<%=idx %>">
				<input type="hidden" name = "pageNum" value="<%=pageNum %>">
				<input type="hidden" name = "old_original_file" value="<%=fileboard.getOriginal_file() %>">
				<input type="hidden" name = "old_real_file" value="<%=fileboard.getReal_file() %>">
				
				<table id="notice">
					<tr>
						<td>글쓴이</td>
						<td><input type="text" name="name" value="<%=fileboard.getName() %>" readonly="required"></td>
					</tr>
					<tr>
						<td>패스워드</td>
						<td><input type="password" name="pass" required="required" ></td>
					</tr>
					<tr>
						<td>제목</td>
						<td><input type="text" name="subject"value="<%=fileboard.getSubject() %>"></td>
					</tr>
					<tr>
						<td>내용</td>
						<td><textarea rows="10" cols="20" name="content" required="required"> <%=fileboard.getContent() %></textarea></td>
					</tr>
					<tr>
						<td>파일</td>
						<td>
							<input type="file" name="original_file" ><br>
							(현재 파일 : <%=fileboard.getOriginal_file() %>)
						</td>
					</tr>
				</table>

				<div id="table_search">
					<input type="submit" value="글수정" class="btn">
					<input type="button" value="취소" class="btn" onclick="history.back()">
				</div>
			</form>
			<div class="clear"></div>
		</article>


		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>



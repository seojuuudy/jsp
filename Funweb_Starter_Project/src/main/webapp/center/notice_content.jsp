<%@page import="java.text.SimpleDateFormat"%>
<%@page import="board.BoardDTO"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%
String sId = (String)session.getAttribute("sId");

int idx = Integer.parseInt(request.getParameter("idx"));
String pageNum = "1";
if(request.getParameter("pageNum") != null) {
	pageNum = request.getParameter("pageNum");
}
BoardDAO dao = new BoardDAO();

dao.updateReadcount(idx);

BoardDTO board = dao.selectBoard(idx);
board.setContent(board.getContent().replaceAll(System.getProperty("line.separator"), "<br>"));

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

// ========================================================
pageContext.setAttribute("board", board);

%>	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/notice_content.jsp</title>
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
			<h1>Notice Content</h1>
			<table id="notice">
				<tr>
					<td>글번호</td>
					<td>${board.idx }</td>
					<td>글쓴이</td>
					<td>${board.name }</td>
				</tr>
				<tr>
					<td>작성일</td>
					<td>${board.date }</td>
					<td>조회수</td>
					<td>${board.readcount }</td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3">${board.subject }</td>
				</tr>
				<tr>
					<td height="300">내용</td>
					<td colspan="3">${board.content }</td>
				</tr>
			</table>

			<div id="table_search">
				<c:if test ="${not empty sessionScope.sId and sessionScope.sId eq 'admin'}">
					
					<input type="button" value="글수정" class="btn" onclick="location.href='notice_update.jsp?idx=${param.idx }&pageNum=${param.pageNum}'"> 
					<input type="button" value="글삭제" class="btn" onclick="location.href='notice_delete.jsp?idx=${param.idx }&pageNum=${param.pageNum}'"> 
				</c:if>
					<input type="button" value="글목록" class="btn" onclick="location.href='notice.jsp?pageNum=${param.pageNum}'">
			</div>

			<div class="clear"></div>
			
			<div id="replyArea">
				<!-- 댓글 작성 -->
				<c:if test = "${not empty sessionScope.sId }">	
				<div id="insertForm">
					<form action="content_reply_writePro.jsp" method="post">
						<input type="hidden" name="ref_idx" value="${param.idx }">
						<input type="hidden" name="board_type" value="notice">
						<input type="hidden" name="page_num" value="${param.pageNum}">
						<textarea rows="3" cols="50" name="content"> </textarea>
						<input type="submit" value="등록">
					</form>
				</div>
				</c:if>
				<!-- 댓글 표시 -->
				<div id="replyViewArea">
					안녕하세요. 저에요 저! 	다영이!! 22-11-15 09:17<br>
					안녕하세요. 저에요 저! 	다영이!! 22-11-15 09:17<br>
					안녕하세요. 저에요 저! 	다영이!! 22-11-15 09:17<br>
					
				</div>
			</div>
		</article>

		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>



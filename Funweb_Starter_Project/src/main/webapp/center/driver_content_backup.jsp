<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="board.BoardDTO"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int idx = Integer.parseInt(request.getParameter("idx"));
String pageNum = request.getParameter("pageNum");

FileBoardDAO dao = new FileBoardDAO();

dao.updateReadcount(idx);

FileBoardDTO fileBoard = dao.selectFileBoard(idx);
fileBoard.setContent(fileBoard.getContent().replaceAll(System.getProperty("line.separator"), "<br>"));

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
					<td><%=fileBoard.getIdx() %></td>
					<td>글쓴이</td>
					<td><%=fileBoard.getName() %></td>
				</tr>
				<tr>
					<td>작성일</td>
					<td><%=sdf.format(fileBoard.getDate()) %></td>
					<td>조회수</td>
					<td><%=fileBoard.getReadcount() %></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><%=fileBoard.getSubject() %></td>
				</tr>
				<tr>
					<td>파일</td>
					<td colspan="3">
						<a href="../upload/<%=fileBoard.getReal_file()%>" download = "<%=fileBoard.getOriginal_file() %>"><%=fileBoard.getOriginal_file() %></a></td>
				</tr>
				<tr>
					<td height="300">내용</td>
					<td colspan="3"><%=fileBoard.getContent() %></td>
				</tr>
			</table>

			<div id="table_search">
				<input type="button" value="글수정" class="btn" onclick="location.href='driver_update.jsp?idx=<%=idx%>&pageNum=<%=pageNum%>'"> <input
					type="button" value="글삭제" class="btn" onclick="location.href='driver_delete.jsp?idx=<%=idx%>&pageNum=<%=pageNum%>'"> <input
					type="button" value="글목록" class="btn" onclick="location.href='driver.jsp?pageNum=<%=pageNum%>'">
			</div>

			<div class="clear"></div>
		</article>

		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>



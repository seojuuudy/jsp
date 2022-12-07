<%@page import="board.BoardReplyDTO"%>
<%@page import="java.util.List"%>
<%@page import="board.BoardReplyDAO"%>
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
if(pageNum == null){
	pageNum = "1";
}
FileBoardDAO dao = new FileBoardDAO();

dao.updateReadcount(idx);

FileBoardDTO fileBoard = dao.selectFileBoard(idx);
fileBoard.setContent(fileBoard.getContent().replaceAll(System.getProperty("line.separator"), "<br>"));

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

String board_type = "driver";
%>	
	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/driver_content.jsp</title>
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
			<h1>Driver Content</h1>
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
				<%
			
				if((String)session.getAttribute("sId") != null){
				
				%>
				<input type="button" value="글수정" class="btn" onclick="location.href='driver_update.jsp?idx=<%=idx%>&pageNum=<%=pageNum%>'"> 
				<input type="button" value="글삭제" class="btn" onclick="location.href='driver_delete.jsp?idx=<%=idx%>&pageNum=<%=pageNum%>'"> 
				<%} %>	
				<input type="button" value="글목록" class="btn" onclick="location.href='driver.jsp?pageNum=<%=pageNum%>'">
			</div>

			<div class="clear"></div>
			
			<div id="replyArea">
				<!-- 댓글 작성 -->	
				<%
			
				if((String)session.getAttribute("sId") != null){
				
				%>
				<div id="insertForm">
					<form action="content_reply_writePro.jsp" method="post">
						<input type="hidden" name="ref_idx" value="<%=idx %>">
						<input type="hidden" name="board_type" value="<%=board_type%>">
						<input type="hidden" name="pageNum" value="<%=pageNum %>">
						<textarea rows="3" cols="50" name="content" id = "replyTextarea"> </textarea>
						<input type="submit" value="등록" id = "replySubmit">
					</form>
				</div>
				<%} %>
				<!-- 댓글 표시 -->
				<div id="replyViewArea">
					<%
					// 페이징 처리를 위한 값 설정 생략 => driver.jsp & notice.jsp 와 동일
					// 페이징 처리를 위해 조회 시 필요한 값 임의 설정
					int startRow = 0; // 계산 생략
					int listLimit = 5;
					
					// BoardReplyDAO - selectReplyList() 메서드를 호출하여 댓글 목록 가져오기
					// => 파라미터 : 게시물글번호, 게시판타입, startRow, listLimit 
					//    리턴타입 : List<BoardReplyDTO>(replyList)
					BoardReplyDAO replyDao = new BoardReplyDAO();
					List<BoardReplyDTO> replyList = replyDao.selectReplyList(idx, board_type, startRow, listLimit);
					
					// List 객체 크기만큼 반복
					for(BoardReplyDTO replyBoard : replyList) {
						%>
						<a href=""><img src="../images/center/delete.png" width="10px" height="20px"></a>
						<span id="replyContent"><%=replyBoard.getContent() %></span>
						<span id="replyId"><%=replyBoard.getId() %></span>
						<span id="replyDate"><%=sdf.format(replyBoard.getDate()) %></span><br>
						<%
					}
					%>
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



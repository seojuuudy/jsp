<%@page import="board.BoardReplyDTO"%>
<%@page import="board.BoardReplyDAO"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String id = (String)session.getAttribute("sId");
if(id == null){ %>
	<script>
		alert("잘못된 접근");
		history.back();
	</script>
<% }


BoardReplyDTO boardReply = new BoardReplyDTO();
boardReply.setId(id);
boardReply.setContent(request.getParameter("content"));
boardReply.setRef_idx(Integer.parseInt(request.getParameter("ref_idx")));
boardReply.setBoard_type(request.getParameter("board_type"));
// out.println(boardReply);

BoardReplyDAO dao = new BoardReplyDAO();
int replyInsertCount = dao.insertReplyBoard(boardReply);

if(replyInsertCount > 0){
	String board_type = boardReply.getBoard_type();
	response.sendRedirect(board_type + "_content.jsp?idx=" + boardReply.getRef_idx() + "&pageNum=" + request.getParameter("pageNum"));	
} else { 
%>
	<script>
		alert("댓글 작성 실패!");
		history.back();
	</script>

<%
}
%>
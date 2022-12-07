<%@page import="board.FreeBoardDAO"%>
<%@page import="board.FreeBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");


FreeBoardDTO freeBoard = new FreeBoardDTO();
freeBoard.setName(request.getParameter("name"));
freeBoard.setPass(request.getParameter("pass"));
freeBoard.setSubject(request.getParameter("subject"));
freeBoard.setContent(request.getParameter("content"));
// out.println(freeBoard);

// insert 메서드 부르고! 파라미터,,디티오 주고! 리턴값 인트!!
FreeBoardDAO dao = new FreeBoardDAO();
int insertCount = dao.insertFreeBoard(freeBoard);

if(insertCount > 0){
	 response.sendRedirect("free_board.jsp");
} else {
%>	
	<script>
		alert("글쓰기 실패");
		history.back(;)	
	</script>
	
<%	
}
%>
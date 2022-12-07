<%@page import="board.BoardDTO"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/notice_update.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	<%
	String sId = (String)session.getAttribute("sId");	// 로그인프로가서 값 뭐라고 저장됐는지 찾아와,,
	if(sId == null || !sId.equals("admin")) {
	//		System.out.println("세션아이디 없음!");
		%>
		alert("잘못된 접근입니다!");
		history.back();
		<%
	}
	%>

</script>
</head>
<body>
	<%
	int idx = Integer.parseInt(request.getParameter("idx"));
	String pageNum = request.getParameter("pageNum");
	
	BoardDAO dao = new BoardDAO();
	
	BoardDTO board = dao.selectBoard(idx);
	%>	
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
			<h1>Notice Update</h1>
			<form action="notice_updatePro.jsp" method="post">
				<input type="hidden" name = "idx" value="<%=idx %>">
				<input type="hidden" name = "pageNum" value="<%=pageNum %>">
				<table id="notice">
					<tr>
						<td>글쓴이</td>
						<td><input type="text" name="name" value="<%=board.getName() %>"></td>
					</tr>
					<tr>
						<td>패스워드</td>
						<td><input type="password" name="pass" ></td>
					</tr>
					<tr>
						<td>제목</td>
						<td><input type="text" name="subject"value="<%=board.getSubject() %>"></td>
					</tr>
					<tr>
						<td>내용</td>
						<td><textarea rows="10" cols="20" name="content"> <%=board.getContent() %></textarea></td>
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



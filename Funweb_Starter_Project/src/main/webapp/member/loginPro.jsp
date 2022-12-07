<%@page import="member.MemberDTO"%>
<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	request.setCharacterEncoding("UTF-8");
	
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	
// 	out.println(id + pass);
	MemberDTO member = new MemberDTO();	
	member.setId(id);
	member.setPass(pass);
	MemberDAO dao = new MemberDAO();
	boolean isLogin = dao.isRightUser(member);
	System.out.println(isLogin);
	if(isLogin) {
		session.setAttribute("sId", member.getId());
		response.sendRedirect("../main/main.jsp");
	} else {
	%>
		<script>
			alert("로그인실패!");
			history.back();
		</script>	
	<%  }
	
	
	%>
</body>
</html>
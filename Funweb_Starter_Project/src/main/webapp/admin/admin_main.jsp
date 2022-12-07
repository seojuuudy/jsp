<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="member.MemberDTO"%>
<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 세션아이디가 null 이거나 "admin" 이 아닐 경우 메인페이지로 이동
String sId = (String)session.getAttribute("sId");
if(sId == null || !sId.equals("admin")) {
	response.sendRedirect("../main/main.jsp");
}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function confirmDelete(id) {
		let result = confirm(id + "삭제하시겠습니까?");
		
		if(result){
			location.href = "../member/member_delete.jsp?id=" + id;	
		}
	
	}
	


</script>
</head>
<body>
	<h1>admin_main.jsp - 관리자 페이지</h1>
	<h2>회원 목록</h2>
	<!-- member 테이블의 모든 레코드 조회하여 테이블에 출력 -->
	<table border="1">
	<tr>
		<th width="100">ID</th>
		<th width="100">이름</th>
		<th width="100">이메일</th>
		<th width="100">휴대전화</th>
		<th width="100">가입일</th>
		<th width="150"></th>
	</tr>
	<%
	String id = request.getParameter("id");
	
	MemberDAO dao = new MemberDAO();
	
	List<MemberDTO> memberList = dao.selectMemberList();
	
// 	for(int i = 0; i < memberList.size(); i++) {
// 		MemberDTO member = memberList.get(i);
	for(MemberDTO member : memberList) {
	%>
	

	<tr>
		<td><%=member.getId() %></td>
		<td><%=member.getName() %></td>
		<td><%=member.getEmail() %></td>
		<td><%=member.getMobile() %></td>
		<td><%=member.getDate( )%></td>
	
		<td>
			<input type="button" value="상세정보" onclick="location.href='../member/member_info.jsp?id=<%=member.getId()%>'">
			<input type="button" value="삭제" onclick="confirmDelete('<%=member.getId()%>')">
		</td>
	</tr>
	<%
	}
	%>
</table>
</body>
</html>














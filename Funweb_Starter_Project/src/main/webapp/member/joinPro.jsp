<%@page import="member.MemberDAO"%>
<%@page import="member.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
// String id = request.getParameter("id");
// String pass = request.getParameter("pass");
// String name = request.getParameter("name");
// String email = request.getParameter("email");
// String post_code = request.getParameter("post_code");
// String address1 = request.getParameter("address1");
// String address2 = request.getParameter("address2");
// String phone = request.getParameter("phone");
// String mobile = request.getParameter("mobile");
MemberDTO member = new MemberDTO();
member.setId(request.getParameter("id"));
member.setPass(request.getParameter("pass"));
member.setName(request.getParameter("name"));
member.setEmail(request.getParameter("email"));
member.setMobile(request.getParameter("mobile"));
member.setPost_code(request.getParameter("post_code"));
member.setAddress1(request.getParameter("address1"));
member.setAddress2(request.getParameter("address2"));
member.setPhone(request.getParameter("phone"));
// out.println(member.toString());

MemberDAO dao = new MemberDAO();



//MemberDAO 객체의 insertMember() 메서드를 호출하여 회원가입 작업 수행
//=> 파라미터 : MemberDTO 객체(member)   리턴타입 : int(insertCount)


int count = dao.insertMember(member);
//회원 가입 결과 판별
//실패 시 자바스크립트를 사용하여 "회원 가입 실패!" 출력 후 이전페이지로 돌아가기
//아니면, 메인페이지(main.jsp)로 이동
if(count > 0) {
// 	response.sendRedirect("../main/main.jsp");
	%>
	<script>
		alert("회원 가입 축하합니다! \n3000 포인트가 적립되었습니다!");
		location.href="../main/main.jsp";
	</script>
	<%
} else {
	%>
	<script>
		alert("회원 가입 실패!");
		history.back();
	</script>
	<%
}

%>
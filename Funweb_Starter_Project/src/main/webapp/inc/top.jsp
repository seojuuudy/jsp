<%@page import="member.MemberDTO"%>
<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String sId = (String)session.getAttribute("sId");

%>    
<script>
	function confirm_logout() {
		let result = confirm("로그아웃 할거니?");
		
		if(result) {
			location.href= "../member/logout.jsp"
		}
	}

</script>    
    
<header>
  <!-- login join -->
  	<div id="login">
  		<% 
  		if(sId == null || sId.equals("")) {
  		%>  
  			<a href="../member/login.jsp">login</a> | <a href="../member/join.jsp">join</a> 
  			
  		<% 	
 	 	} else {
  		%>
<%--  	 		<a href = "../member/member_info.jsp?=<%=sId %>"><%=sId %>님 </a> |  --%>
<!--   			<a href = "../member/logout.jsp">logout</a> | -->
			
			<a href = "../member/member_info.jsp?sId=<%=sId %>"><%=sId %>님 </a> | 
  			<a href = "javascript:confirm_logout()">logout</a>
  		<% if(sId.equals("admin"))  {%>
  			| <a href = "../admin/admin_main.jsp">관리자페이지</a>
  		<% 
	  		}
  		}%>
  </div>
  <div class="clear"></div>
  <!-- 로고들어가는 곳 -->
  <div id="logo"><img src="../images/logo.gif" onclick="location.href='../main/main.jsp'"></div>
  <!-- 메뉴들어가는 곳 -->
  <nav id="top_menu">
  	<ul>
  		<li><a href="../main/main.jsp">HOME</a></li>
  		<li><a href="../company/welcome.jsp">COMPANY</a></li>
  		<li><a href="../company/welcome.jsp">SOLUTIONS</a></li>
  		<li><a href="../center/notice.jsp">CUSTOMER CENTER</a></li>
<% if(sId != null){
	

MemberDAO dao = new MemberDAO();

MemberDTO member = dao.selectMember(sId);
String email = member.getEmail();

%>

  		<li><a href="../mail/mailForm.jsp?email=<%=email %>">CONTACT US</a></li>
<% 
} else {
%>	
  		<li><a href="../mail/mailForm.jsp?">CONTACT US</a></li>
<%  		
}
%>  	
	</ul>
  </nav>
</header>
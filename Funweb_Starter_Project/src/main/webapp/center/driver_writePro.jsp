<%@page import="java.io.File"%>
<%@page import="board.FileBoardDAO"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="board.FileBoardDTO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// request.setCharacterEncoding("UTF-8");
// out.println(request.getParameter("name"));

String uploadPath = "/upload";


ServletContext context = request.getServletContext();

String realPath = context.getRealPath(uploadPath);

int fileSize = 1024*1024*10;  // 10MB
// out.println(realPath); 
// 실제 업로드 폴더 위치
// D:\workspace_jsp5\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Funweb_Starter_Project\ upload 

MultipartRequest multi = new MultipartRequest(
		request,
		realPath,
		fileSize,
		"UTF-8",
		new DefaultFileRenamePolicy()
);

FileBoardDTO fileBoard = new FileBoardDTO();
fileBoard.setName(multi.getParameter("name"));
fileBoard.setPass(multi.getParameter("pass"));
fileBoard.setSubject(multi.getParameter("subject"));
fileBoard.setContent(multi.getParameter("content"));
String fileElement = multi.getFileNames().nextElement().toString();
// out.println(fileElement);
fileBoard.setOriginal_file(multi.getOriginalFileName(fileElement));
fileBoard.setReal_file(multi.getFilesystemName(fileElement));

// out.println(fileBoard);

FileBoardDAO dao = new FileBoardDAO();

int insertCount = dao.insertFileBoard(fileBoard);

if(insertCount > 0) {
	response.sendRedirect("driver.jsp");
} else {
	File f = new File(realPath, fileBoard.getReal_file());
	
	if(f.exists()){
		f.delete();
		System.out.println("파일 삭제 결과!");
	}
	%>
	<script>
		alert("글쓰기 실패!");
		history.back();
	</script>
	<%
}

%>
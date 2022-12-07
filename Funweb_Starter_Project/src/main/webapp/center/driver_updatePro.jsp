<%@page import="java.io.File"%>
<%@page import="board.FileBoardDAO"%>
<%@page import="board.FileBoardDTO"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

String uploadPath = "/upload";

String realPath = request.getServletContext().getRealPath(uploadPath);

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

int idx = Integer.parseInt(multi.getParameter("idx"));
fileBoard.setIdx(idx);
String pageNum = multi.getParameter("pageNum");

boolean isNewFile = false; 
if(fileBoard.getOriginal_file() == null) {
	fileBoard.setOriginal_file(multi.getParameter("old_original_file"));
	fileBoard.setReal_file(multi.getParameter("old_real_file"));
} else {
	isNewFile = true;
}
// out.println(fileBoard);
FileBoardDAO dao = new FileBoardDAO();
	
int updateCount = dao.updateFileBoard(fileBoard); // 수정 완

if(updateCount > 0){ // 수정 됐을 때
	if(isNewFile){ // 새파일이니?
		File f = new File(realPath, multi.getParameter("old_real_file"));
// 		File f = new File(realPath, fileBoard.getReal_file());
		
		if(f.exists()){
			f.delete();
			System.out.println("파일 삭제 결과!");
		} // 새파일 지우렴 
	}
	response.sendRedirect("driver_content.jsp?idx=" + idx + "&pageNum=" + pageNum);
} else { 
	if(isNewFile){ // 새파일이니? 
	File f = new File(realPath, fileBoard.getReal_file());
	
	if(f.exists()){
		f.delete();
		System.out.println("파일 삭제 결과!");
	} // 기존 파일 지워 

	%>
	<script>
	alert("글 수정 실패!");
	history.back();
	</script>
	<%	
	}
}
%>
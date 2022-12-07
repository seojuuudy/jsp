package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.BoardWriteProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardWriteProAction");
		ActionForward forward = null;
		
		try {
			String uploadPath = "upload";
			String realPath = request.getServletContext().getRealPath(uploadPath);
			System.out.println("실제 업로드 경로 : " + realPath);
			//  D:\workspace_jsp5\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\MVC_Board\ upload
			int fileSize = 1024 * 1024 * 10;
			// --------------작성자 ip 주소 얻어올때
			String writerIpAddr = request.getRemoteAddr();
			System.out.println("작성자 IP 주소 : " + writerIpAddr);
			// -----------------
			
			MultipartRequest multi = new MultipartRequest(
					request,
					realPath,
					fileSize,
					"UTF-8",
					new DefaultFileRenamePolicy()
			);
			
			// 전달받은 파라미터 데이터를 BoardBean 클래스 인스턴스 생성 후 저장
			BoardBean board = new BoardBean();
			board.setBoard_name(multi.getParameter("board_name"));
			board.setBoard_pass(multi.getParameter("board_pass"));
			board.setBoard_subject(multi.getParameter("board_subject"));
			board.setBoard_content(multi.getParameter("board_content"));
			
			board.setBoard_file(multi.getOriginalFileName("board_file"));
			board.setBoard_real_file(multi.getFilesystemName("board_file"));
//			System.out.println(board);
//			System.out.println(multi.getOriginalFileName("board_file"));
//			System.out.println(multi.getFilesystemName("board_file"));
			
//			String fileElement = multi.
//			System.out.println(multi.getOriginalFileName(multi.getParameter("board_file")));
//			Enumeration e = multi.getFileNames(); // 파일 여러개 관리할때
//			while(e.hasMoreElements()) {
//				String fileElement = e.nextElement().toString();
////				System.out.println(fileElement);
//				System.out.println("원본 파일명 : " + multi.getOriginalFileName(fileElement));
//				System.out.println("실제 파일명 : " + multi.getFilesystemName(fileElement));
//				
//			}
			// -----------------------------------------------------
			BoardWriteProService service = new BoardWriteProService();
			Boolean isWriteSuccess = service.registBoard(board);
			
			if(!isWriteSuccess) { // 실패 시
				File f = new File(realPath, board.getBoard_real_file());
				
				if(f.exists()) {
					f.delete();
				}
				response.setContentType("text/html; charset=UTF-8"); // jsp 첫줄 
				PrintWriter out = response.getWriter(); // html 태그 사용위한 메서드
				out.println("<script>"); // html 태그 등의 문자열 형태로 전달
				out.println("alert('글쓰기 실패!')"); 
				out.println("history.back()"); 
				out.println("</script>"); 				
				
			} else { // 성공 시
				forward = new ActionForward();
				forward.setPath("BoardList.bo");
				forward.setRedirect(true);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return forward;
	}



}

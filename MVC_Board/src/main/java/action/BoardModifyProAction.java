package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDAO;
import db.JdbcUtil;
import svc.BoardDetailService;
import svc.BoardModifyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
//		int board_num = Integer.parseInt(request.getParameter("board_num"));
//		System.out.println(board_num);
//		String board_name = request.getParameter("board_name");
//		String board_pass = request.getParameter("board_pass");
//		String board_subject = request.getParameter("board_subject");
//		String board_content = request.getParameter("board_content");
		
		String realPath = "";
		// 수정 작업 결과에 따라 삭제할 파일이 달라지므로 파일명을 저장할 수 있는 변수 선언
		String deleteFileName =  "";
		
		try {
			String uploadPath = "upload";
			realPath = request.getServletContext().getRealPath(uploadPath);
			System.out.println("실제 업로드 경로 : " + realPath);
			//  D:\workspace_jsp5\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\MVC_Board\ upload
			
			File f = new File(realPath);
			if(!f.exists()) {
				f.mkdir();
			}
					
			
			int fileSize = 1024 * 1024 * 10;
			
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
			board.setBoard_num(Integer.parseInt(multi.getParameter("board_num")));
			board.setBoard_pass(multi.getParameter("board_pass"));
			board.setBoard_subject(multi.getParameter("board_subject"));
			board.setBoard_content(multi.getParameter("board_content"));
			board.setBoard_file(multi.getOriginalFileName("board_file"));
			board.setBoard_real_file(multi.getFilesystemName("board_file"));
			// => 만약, 수정할 파일을 선택하지 않았을 경우 파일명은 null 값
//			System.out.println(board);
			
					
			
			BoardModifyProService service = new BoardModifyProService();
			boolean isBoardWriter = service.isBoardWriter(board);
			if(!isBoardWriter) { // 수정 권한 없음
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정 권한이 없습니다!')");
				out.println("alert('글 수정 실패!!')");
				out.println("history.back()");
				out.println("</script>");
				
				deleteFileName = board.getBoard_real_file();
				
			} else {
				boolean isModifySuccess = service.modifyBoard(board);
				if(!isModifySuccess) {
					response.setContentType("text/html; charset=UTF-8"); // jsp 첫줄 
					PrintWriter out = response.getWriter(); // html 태그 사용위한 메서드
					out.println("<script>"); // html 태그 등의 문자열 형태로 전달
					out.println("alert('수정 실패!!')"); 
					out.println("history.back()"); 
					out.println("</script>"); 		
					
					deleteFileName = board.getBoard_real_file();
					
				} else {
				forward = new ActionForward();
				forward.setPath("BoardDetail.bo?board_num=" + board.getBoard_num()+ "&pageNum=" + multi.getParameter("pageNum"));
				forward.setRedirect(true);
				
				// hidden 속성으로 전달받은 기존 파일 명에 대한 파라미터 사용
				if(board.getBoard_file() != null) {
					deleteFileName = multi.getParameter("board_real_file");
				}
		
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			File f = new File(realPath, deleteFileName);
		
			if(f.exists()) {
				f.delete();
			}
		}
		
		return forward;
	}

}

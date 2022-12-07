package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDeleteProService;
import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardDeleteProAction");
		ActionForward forward = null;
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String board_pass = request.getParameter("board_pass");
		
//		System.out.println(board_num + board_pass);
		
		try {
			BoardDeleteProService service = new BoardDeleteProService();
			boolean isBoardWriter = service.isBoardWriter(board_num, board_pass);
			System.out.println(isBoardWriter);
			
			if(!isBoardWriter) {
				response.setContentType("text/html; charset=UTF-8"); // jsp 첫줄 
				PrintWriter out = response.getWriter(); // html 태그 사용위한 메서드
				out.println("<script>"); // html 태그 등의 문자열 형태로 전달
				out.println("alert('삭제권한없음!')"); 
				out.println("history.back()"); 
				out.println("</script>"); 				
			} else {
				
				BoardDetailService service2 = new BoardDetailService();
				BoardBean board = service2.getBoard(board_num, false);
				// => 레코드 삭제 전 !! 삭제 정보 조회 머저 수행해야함
				boolean isDeleteSuccess = service.removeBoard(board_num);
				if(!isDeleteSuccess) {
					response.setContentType("text/html; charset=UTF-8"); // jsp 첫줄 
					PrintWriter out = response.getWriter(); // html 태그 사용위한 메서드
					out.println("<script>"); // html 태그 등의 문자열 형태로 전달
					out.println("alert('삭제 실패!!')"); 
					out.println("history.back()"); 
					out.println("</script>"); 		
				} else {
					
					String uploadPath = "upload";
					String realPath = request.getServletContext().getRealPath(uploadPath);
					File f = new File(realPath, board.getBoard_real_file());
					
					if(f.exists()) {
						f.delete();
					}
					
					forward = new ActionForward();
					forward.setPath("BoardList.bo?pageNum=" + request.getParameter("pageNum"));
					forward.setRedirect(true);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return forward;
	}
	
	
	
}

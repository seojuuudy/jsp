package action;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardListService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardListAction");
		ActionForward forward = null;
		// service 객체를 통해 게시물 목록 조회 후
		// List 객체에 저장 request 객체를 통해 qna_board_list.jsp에 전달  
		
		int listLimit = 10; // 한 페이지에서 표시할 게시물 목록을 10개로 제한

		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		int startRow = (pageNum - 1) * listLimit; //조회 시작 행번호 계산

		String keyword = request.getParameter("keyword");

		if(keyword == null){
			keyword = "";
		}
		
		// -----------------------------------
		// BoardListService 클래스 인스턴스 생성
		BoardListService service = new BoardListService();
		
		List<BoardBean> boardList = service.getBoardList(keyword, startRow, listLimit);
		
		// -----------------------------------------------------------------------
		int listCount = service.getBoardListCount(keyword);
//		System.out.println("총 게시물 수 : " + listCount);
		
		// 2. 한 페이지에서 표시할 페이지 목록 갯수 설정
		int pageListLimit = 5; // 한 페이지에서 표시할 페이지 목록을 3개로 제한
		
		// 3. 전체 페이지 목록 수 계산
		// => 전체 게시물 수를 페이지 당 게시물 목록 수로 나눈 몫 계산
		int maxPage = listCount / listLimit 
						+ (listCount % listLimit == 0 ? 0 : 1); 
//			System.out.println("전체 페이지 수 : " + maxPage);

		
		// 4. 시작 페이지 번호 계산
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		
		// 5. 끝 페이지 번호 계산
		int endPage = startPage + pageListLimit - 1;
		
		// 6. 만약, 끝 페이지 번호(endPage)가 전체(최대) 페이지 번호(maxPage) 보다
		//    클 경우, 끝 페이지 번호를 최대 페이지 번호로 교체
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// pageinfo 만들어서 위에 있는 인트 녀석들(페이징 처리 정보) 다 저장
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		
		
		
		// ----------------------------------------------------------------------
		// 액션 클래스에 request response 넘어왔고.. 지금 해야할 일은 글목록(list객체)와 페이지 처리 정보를 request에 저장해야함 >> setAttribute 사용
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageInfo", pageInfo);
		
		forward = new ActionForward();
		forward.setPath("board/qna_board_list.jsp");
		forward.setRedirect(false);

		System.out.println("listCount : " + listCount);
		return forward;
	}

}

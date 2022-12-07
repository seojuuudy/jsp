package svc;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardDetailService {

	public BoardBean getBoard(int board_num, boolean isUpdateReadcount) {
		BoardBean board = null;

		Connection con = JdbcUtil.getConnection();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		board = dao.selectBoard(board_num);
		
		if(board != null && isUpdateReadcount) {
			int updateCount = dao.updateReadcount(board_num);
			if(updateCount > 0) {
				JdbcUtil.commit(con);
				board.setBoard_readcount(board.getBoard_readcount() + 1);
			}
			
			
		}
		
		JdbcUtil.close(con);
		
		return board;
	}
	
}
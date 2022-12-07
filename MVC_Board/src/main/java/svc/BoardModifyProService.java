package svc;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardModifyProService {
	
	public boolean isBoardWriter(BoardBean board) {
		boolean isBoardWriter = false;
		Connection con = JdbcUtil.getConnection();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		isBoardWriter = dao.isBoardWriter(board.getBoard_num(), board.getBoard_pass());

		JdbcUtil.close(con);
		return isBoardWriter;
	}
	
//	public boolean modifyBoard(int board_num, String board_pass) {
//		boolean isBoardWriter = false;
//		Connection con = JdbcUtil.getConnection();
//		BoardDAO dao = BoardDAO.getInstance();
//		dao.setConnection(con);
//		isBoardWriter = dao.isBoardWriter(board_num, board_pass);
//		BoardBean board = new BoardBean();
//		int updateCount = dao.updateBoard(board);
//		if(updateCount > 0) {
//			isBoardWriter = true;
//			JdbcUtil.commit(con);
//		} else {
//			JdbcUtil.rollback(con);
//		}
//		JdbcUtil.close(con);
//		return isBoardWriter;
//		
//	}

	public boolean modifyBoard(BoardBean board) {
		boolean isModifySuccess = false;
		Connection con = JdbcUtil.getConnection();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		int updateCount = dao.updateBoard(board);
		
		if(updateCount > 0) {
			JdbcUtil.commit(con);
			isModifySuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con);
		return isModifySuccess;
	}
	
	
	
}

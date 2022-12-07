package svc;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardReplyProService {

	public Boolean registReplyBoard(BoardBean board) {
		Boolean isWriteSuccess = false;
		Connection con = JdbcUtil.getConnection(); 
		
		BoardDAO dao = BoardDAO.getInstance(); 
		
		
		dao.setConnection(con); 
		
		int insertCount = dao.insertReplyBoard(board);
		
		if(insertCount > 0) {// 성공 시
			JdbcUtil.commit(con);
			
			isWriteSuccess = true;
		} else {// 실패 시
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con); 
		return null;
	}

}

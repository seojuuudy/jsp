package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;

public class BoardReplyDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs =null;
	
	public int insertReplyBoard(BoardReplyDTO boardReply) {
		int replyInsertCount = 0;
		
		con = JdbcUtil.getConnection();
		String sql = "INSERT INTO board_reply VALUES(null,?,?,now(),?,?)";
		try {
			pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1, boardReply.getIdx());
			pstmt.setString(1, boardReply.getId());
			pstmt.setString(2, boardReply.getContent());
			pstmt.setInt(3, boardReply.getRef_idx());
			pstmt.setString(4, boardReply.getBoard_type());
			
			replyInsertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql 구문 오류");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		
		return replyInsertCount;
		
	}
	
	public List<BoardReplyDTO> selectReplyList(int ref_idx, String board_type, int startRow, int listLimit){
		List<BoardReplyDTO> replyList = null;
		con = JdbcUtil.getConnection();
		
		String sql = "SELECT * FROM board_reply WHERE board_type=? && ref_idx=? ORDER BY idx DESC LIMIT ?,?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board_type);
			pstmt.setInt(2, ref_idx);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, listLimit);
			rs = pstmt.executeQuery();
			replyList = new ArrayList<BoardReplyDTO>(); // 배열!!!
			while(rs.next()) {
				BoardReplyDTO board = new BoardReplyDTO();
				
				board.setIdx(rs.getInt("idx"));
				board.setId(rs.getString("id"));
				board.setContent(rs.getString("content"));
				board.setDate(rs.getTimestamp("date")); 
				board.setRef_idx(rs.getInt("ref_idx"));
				board.setBoard_type(rs.getString("board_type"));
//				System.out.println(board);
				replyList.add(board);
//				System.out.println(replyList);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return replyList;
	}
	
}

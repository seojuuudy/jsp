package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.JdbcUtil;

public class FreeBoardDAO {
	Connection con = null;
	PreparedStatement pstmt = null, pstmt2 = null;
	ResultSet rs = null;
	
	public int insertFreeBoard(FreeBoardDTO freeBoard) {
		int insertCount = 0;
		
		con = JdbcUtil.getConnection();
		
		try {
			int idx = 1;
			String sql = "SELECT MAX(idx) FROM free_board";
			pstmt2 = con.prepareStatement(sql);
			rs = pstmt2.executeQuery();
			if(rs.next()) {
				idx = rs.getInt(idx) + 1; 
				// MAX(idx) 는 문자열 취급해서 넣는 거 알겠으나,,,
				// idx 변수 설정해줬으니까 idx 바로 대입하면 왜 안되는지?
			}
			System.out.println("새 글 번호 : " + idx);
			sql = "INSERT INTO free_board VALUES(?,?,?,?,?,now(),0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, freeBoard.getName());
			pstmt.setString(3, freeBoard.getPass());
			pstmt.setString(4, freeBoard.getSubject());
			pstmt.setString(5, freeBoard.getContent());
					
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
			JdbcUtil.close(con);
		}
		
		return insertCount;
	}
	
	
}

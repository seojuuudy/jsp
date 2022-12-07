package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;

import db.JdbcUtil;

public class BoardDAO {

	Connection con = null;
	PreparedStatement pstmt = null; 
	PreparedStatement pstmt2 = null;
	ResultSet rs = null;
	public int insertBoard(BoardDTO board) {
		int insertCount = 0;
		
		con = JdbcUtil.getConnection();
		int idx = 1;	
		String sql = "SELECT MAX(idx) FROM board"; 
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				idx = rs.getInt(1) + 1;   
			} 
			System.out.println("새 글 번호 : " + idx);
			
			// ----------------------------------------새글 번호계산
			// 게시물 등록
			sql = "INSERT INTO board VALUES (?,?,?,?,?,now(),0)";
			pstmt2 = con.prepareStatement(sql);
					
			pstmt2.setInt(1, idx);
			pstmt2.setString(2, board.getName());
			pstmt2.setString(3, board.getPass());
			pstmt2.setString(4, board.getSubject());
			pstmt2.setString(5, board.getContent());
			
			insertCount = pstmt2.executeUpdate();
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
	
	public int selectListCount() { 
		int listCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		con = JdbcUtil.getConnection();
		int idx = 1;	
		String sql = "SELECT COUNT(idx) from board";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				listCount = rs.getInt(idx);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류!");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return listCount;
	}
	
	public int selectListCount(String keyword) {
		int listCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		con = JdbcUtil.getConnection();
		int idx = 1;	
		String sql = "SELECT COUNT(idx) from board WHERE subject LIKE ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				listCount = rs.getInt(idx);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류!");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return listCount;
	}
	
	public List<BoardDTO> selectBoardList() {
		List<BoardDTO> boardList = null;
		
		con = JdbcUtil.getConnection();
		
		String sql = "SELECT * FROM board ORDER BY idx  DESC";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			boardList = new ArrayList<BoardDTO>(); // 배열!!!
			while(rs.next()) {
				BoardDTO board = new BoardDTO();
				
				board.setIdx(rs.getInt("idx"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setDate(rs.getTimestamp("date"));
				board.setReadcount(rs.getInt("readcount"));
//				System.out.println(board);
				boardList.add(board);
//				System.out.println(boardList);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		
		
		
		return boardList;
	}
	public List<BoardDTO> selectBoardList(int startRow, int listLimit) {
		List<BoardDTO> boardList = null;
		
		con = JdbcUtil.getConnection();
		
		String sql = "SELECT * FROM board ORDER BY idx DESC LIMIT ?,?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);
			rs = pstmt.executeQuery();
			
			boardList = new ArrayList<BoardDTO>(); // 배열!!!
			while(rs.next()) {
				BoardDTO board = new BoardDTO();
				
				board.setIdx(rs.getInt("idx"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setDate(rs.getTimestamp("date"));
				board.setReadcount(rs.getInt("readcount"));
//				System.out.println(board);
				boardList.add(board);
//				System.out.println(boardList);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return boardList;
	}
	
	// 검색기능 추가
	public List<BoardDTO> selectBoardList(int startRow, int listLimit, String keyword) {
		List<BoardDTO> boardList = null;
		
		con = JdbcUtil.getConnection();
		
		String sql = "SELECT * FROM board WHERE subject LIKE ? ORDER BY idx DESC LIMIT ?,?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			
			rs = pstmt.executeQuery();
			
			boardList = new ArrayList<BoardDTO>(); // 배열!!!
			while(rs.next()) {
				BoardDTO board = new BoardDTO();
				
				board.setIdx(rs.getInt("idx"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setDate(rs.getTimestamp("date"));
				board.setReadcount(rs.getInt("readcount"));
//				System.out.println(board);
				boardList.add(board);
//				System.out.println(boardList);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return boardList;
	}
	
	public BoardDTO selectBoard(int idx) {
		BoardDTO board = new BoardDTO();
			
		con = JdbcUtil.getConnection();
		String sql = "select * from board where idx = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board.setIdx(rs.getInt("idx"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("Subject"));
				board.setContent(rs.getString("content"));
//				board.setContent(rs.getString("content").replaceAll(System.getProperty("line.separator"), "<br>"));
				board.setDate(rs.getTimestamp("date"));
				board.setReadcount(rs.getInt("readcount"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return board;
	}
	
	public void updateReadcount(int idx) {
		con = JdbcUtil.getConnection();
		String sql = "UPDATE board SET readcount=readcount+1 WHERE idx = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
	}
	public int updateBoard(BoardDTO board) {
		int updateCount = 0;
		con = JdbcUtil.getConnection();
		String sql = "UPDATE board SET name=?, subject=?, content=? WHERE idx =? AND pass=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getIdx());
			pstmt.setString(5, board.getPass());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return updateCount;
	}
	
	public int deleteBoard(int idx, String pass) {
		int deleteCount = 0;
		
		con = JdbcUtil.getConnection();
		String sql = "DELETE FROM board WHERE idx = ? AND pass = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, pass);
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return deleteCount;
	}
	
	public List<BoardDTO> selectRecentBoardList() {
		List<BoardDTO> boardList = null;
		con = JdbcUtil.getConnection();
		String sql = "SELECT idx, name, subject, date "
					+ "FROM board ORDER BY idx DESC LIMIT 5";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			boardList = new ArrayList<BoardDTO>();
			while(rs.next()) {
				BoardDTO board = new BoardDTO();
				
				board.setIdx(rs.getInt("idx"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setDate(rs.getTimestamp("date"));
//			System.out.println(board);
				boardList.add(board);
//			System.out.println(boardList);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		return boardList;
	}
}

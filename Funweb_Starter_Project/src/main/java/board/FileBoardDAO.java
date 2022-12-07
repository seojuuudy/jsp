package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;

public class FileBoardDAO {
		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
	public int insertFileBoard(FileBoardDTO fileBoard) {
		int insertCount = 0;
		con = JdbcUtil.getConnection();
		
		int idx = 1;
		String sql = "SELECT MAX(idx) FROM file_board";
		try {
			pstmt2 = con.prepareStatement(sql);
			rs = pstmt2.executeQuery();
			if(rs.next()) {
				idx = rs.getInt(1)+1; // 컬럼인덱스 활용 getInt("컬럼명") 사용시 "idx"가 아닌 "MAX(idx)" 사용해야함
			}
			System.out.println(idx);
			
			sql = "INSERT INTO file_board VALUES(?,?,?,?,?,?,?,now(),0)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, idx);
				pstmt.setString(2, fileBoard.getName());
				pstmt.setString(3, fileBoard.getPass());
				pstmt.setString(4, fileBoard.getSubject());
				pstmt.setString(5, fileBoard.getContent());
				pstmt.setString(6, fileBoard.getOriginal_file());
				pstmt.setString(7, fileBoard.getReal_file());
				insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
			JdbcUtil.close(con);
		
		// 드라이버 목록에 파일명도 보여지게끔 !! 
		return insertCount;
	}
	
	// BoardDAO 객체의 selectBoardList() 메서드를 호출하여 게시물 목록 조회
	// => 파라미터 : 없음, 리턴타입 : List<BoardDTO>(boardList)
	// (단, 임시로 페이징 처리 없이 전체 목록 조회)
	// List<BoardDTO> boardList = dao.selectBoardList();
	
//	public List<FileBoardDTO> selectFileBoardList(int startRow, int listLimit) {
//		List<FileBoardDTO> fileBoardList = null;
//		con = JdbcUtil.getConnection();
//		String sql = "SELECT * FROM file_board ORDER BY idx DESC LIMIT ?,?";
//		try {
//			pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1, startRow);
//			pstmt.setInt(2, listLimit);
//			rs = pstmt.executeQuery();
//			fileBoardList = new ArrayList<FileBoardDTO>();
//			while(rs.next()) {
//				FileBoardDTO board = new FileBoardDTO();
//				board.setIdx(rs.getInt("idx"));
//				board.setName(rs.getString("name"));
//				board.setPass(rs.getString("pass"));
//				board.setSubject(rs.getString("subject"));
//				board.setContent(rs.getString("content"));
//				board.setOriginal_file(rs.getString("original_file"));
//				board.setReal_file(rs.getString("real_file"));
//				board.setDate(rs.getTimestamp("date"));
//				board.setReadcount(rs.getInt("readcount"));
//				fileBoardList.add(board);	
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			JdbcUtil.close(rs);
//			JdbcUtil.close(pstmt);
//			JdbcUtil.close(con);
//		}
//		
//		
//		
//		return fileBoardList;
//	}
	
//	public int selectFileBoardListCount() { 
//		int listCount = 0;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		con = JdbcUtil.getConnection();
//		int idx = 1;	
//		String sql = "SELECT COUNT(idx) from file_board";
//		try {
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				listCount = rs.getInt(idx);
//			}
//		} catch (SQLException e) {
//			System.out.println("SQL 구문 오류!");
//			e.printStackTrace();
//		} finally {
//			JdbcUtil.close(rs);
//			JdbcUtil.close(pstmt);
//			JdbcUtil.close(con);
//		}
//		
//		return listCount;
//	}
	public int selectFileBoardListCount(String keyword) {
		int listCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		con = JdbcUtil.getConnection();
		int idx = 1;	
		String sql = "SELECT COUNT(idx) from file_board WHERE subject LIKE ?";
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
	public void updateReadcount(int idx) {
		con = JdbcUtil.getConnection();
		String sql = "UPDATE file_board SET readcount=readcount+1 WHERE idx = ?";
		
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
	
	public FileBoardDTO selectFileBoard(int idx) {
		FileBoardDTO fileBoard = new FileBoardDTO();
			
		con = JdbcUtil.getConnection();
		String sql = "select * from file_board where idx = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				fileBoard.setIdx(rs.getInt("idx"));
				fileBoard.setName(rs.getString("name"));
				fileBoard.setPass(rs.getString("pass"));
				fileBoard.setSubject(rs.getString("Subject"));
				fileBoard.setContent(rs.getString("content"));
				fileBoard.setOriginal_file(rs.getString("original_file"));
				fileBoard.setReal_file(rs.getString("real_file"));
//				board.setContent(rs.getString("content").replaceAll(System.getProperty("line.separator"), "<br>"));
				fileBoard.setDate(rs.getTimestamp("date"));
				fileBoard.setReadcount(rs.getInt("readcount"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		return fileBoard;
	}
	public String selectRealFile(int idx) {
		String realFile = null;
		con = JdbcUtil.getConnection();
		String sql = "select real_file from file_board WHERE idx=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				realFile = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return realFile;
	}
	public int deleteFileBoard(int idx, String pass) {
		int deleteCount = 0;
		
		con = JdbcUtil.getConnection();
		String sql = "DELETE FROM file_board WHERE idx = ? AND pass = ?";
		
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
	public List<FileBoardDTO> selectFileBoardList(int startRow, int listLimit, String keyword) {
		List<FileBoardDTO> fileBoardList = null;
		
		con = JdbcUtil.getConnection();
		
		String sql = "SELECT * FROM file_board WHERE subject LIKE ? ORDER BY idx DESC LIMIT ?,?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			
			rs = pstmt.executeQuery();
			
			fileBoardList = new ArrayList<FileBoardDTO>(); // 배열!!!
			while(rs.next()) {
				FileBoardDTO fileBoard = new FileBoardDTO();
				
				fileBoard.setIdx(rs.getInt("idx"));
				fileBoard.setName(rs.getString("name"));
				fileBoard.setPass(rs.getString("pass"));
				fileBoard.setSubject(rs.getString("subject"));
				fileBoard.setContent(rs.getString("content"));
				fileBoard.setOriginal_file(rs.getString("original_file"));
				fileBoard.setReal_file(rs.getString("real_file"));
				fileBoard.setDate(rs.getTimestamp("date"));
				fileBoard.setReadcount(rs.getInt("readcount"));
//				System.out.println(board);
				fileBoardList.add(fileBoard);
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
		
		return fileBoardList;
	}
	
	public int selectListCount(String keyword) {
		int listCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		con = JdbcUtil.getConnection();
		int idx = 1;	
		String sql = "SELECT COUNT(idx) from file_board WHERE subject LIKE ?";
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
	public int updateFileBoard(FileBoardDTO fileBoard) {
		int updateCount = 0;
		con = JdbcUtil.getConnection();
		String sql = "UPDATE file_board SET subject=?, content=?, original_file=?, real_file=?  WHERE idx =? AND pass=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fileBoard.getSubject());
			pstmt.setString(2, fileBoard.getContent());
			pstmt.setString(3, fileBoard.getOriginal_file());
			pstmt.setString(4, fileBoard.getReal_file());
			pstmt.setInt(5, fileBoard.getIdx());
			pstmt.setString(6, fileBoard.getPass());
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
}	

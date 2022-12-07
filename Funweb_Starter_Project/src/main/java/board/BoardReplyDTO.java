package board;

import java.sql.Timestamp;

import javax.print.attribute.standard.DateTimeAtCompleted;

// 글쓰기 글목록 복습이라 생각하세요!
/*
  CREATE TABLE board_reply(
  idx INT PRIMARY KEY AUTO_INCREMENT,
  id VARCHAR(16) NOT NULL,
  content VARCHAR(100) NOT NULL,
  date DATETIME NOT NULL,
  ref_idx INT NOT NULL,
  board_type VARCHAR(30) NOT NULL
  );
 */
public class BoardReplyDTO {
	private int idx;
	private String id;
	private String content;
	private Timestamp date;
	private int ref_idx;
	private String board_type;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getRef_idx() {
		return ref_idx;
	}
	public void setRef_idx(int ref_idx) {
		this.ref_idx = ref_idx;
	}
	public String getBoard_type() {
		return board_type;
	}
	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}
	
	@Override
	public String toString() {
		return "BoardReplyDTO [idx=" + idx + ", id=" + id + ", content=" + content + ", date=" + date + ", ref_idx="
				+ ref_idx + ", board_type=" + board_type + "]";
	}
	
	
	
}

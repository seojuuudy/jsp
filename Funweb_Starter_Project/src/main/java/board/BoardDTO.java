package board;

import java.sql.Timestamp;

public class BoardDTO {
/*
  CREATE TABLE board (
  idx INT PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  pass VARCHAR(16) NOT NULL,
  subject VARCHAR(50) NOT NULL,
  content VARCHAR(2000) NOT NULL,
  date TIMESTAMP NOT NULL,
  readcount INT NOT NULL
  );
 */
	private int idx;
	private String name;
	private String pass;
	private String subject;
	private String content;
	private Timestamp date;
	private int readcount;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	@Override
	public String toString() {
		return "BoardDTO [idx=" + idx + ", name=" + name + ", pass=" + pass + ", subject=" + subject + ", content="
				+ content + ", date=" + date + ", readcount=" + readcount + ", toString()=" + super.toString() + "]";
	}
	
	
}

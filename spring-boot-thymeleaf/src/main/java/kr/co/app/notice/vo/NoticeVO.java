package kr.co.app.notice.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeVO {
	private int brdNo;
	private String brdTitle;
	private String brdWriter;
	private String brdMemo;
	private Timestamp brdDate;
}

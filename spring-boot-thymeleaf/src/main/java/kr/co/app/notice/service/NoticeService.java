package kr.co.app.notice.service;

import java.util.List;

import kr.co.app.notice.vo.NoticeVO;

public interface NoticeService {
	public List<NoticeVO> list();
	public NoticeVO read(int brdNo);
	public int insert(NoticeVO boardVO);
	public int update(NoticeVO boardVO);
	public int delete(int brdNo);
}

package kr.co.app.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.app.notice.dao.NoticeDAO;
import kr.co.app.notice.vo.NoticeVO;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Override
	public List<NoticeVO> list() {
		return noticeDAO.list();
	}

	@Override
	public NoticeVO read(int brdNo) {
		return noticeDAO.read(brdNo);
	}

	@Override
	public int insert(NoticeVO noticeVO) {
		return noticeDAO.insert(noticeVO);
	}
	
	@Override
	public int update(NoticeVO noticeVO) {
		return noticeDAO.udate(noticeVO);
	}

	@Override
	public int delete(int brdNo) {
		return noticeDAO.delete(brdNo);
	}
}

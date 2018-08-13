package kr.co.app.notice.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.app.notice.vo.NoticeVO;

@Repository
public class NoticeDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public List<NoticeVO> list() {
		return sqlSession.selectList("notice.list");
	}
	
	public NoticeVO read(int brdNo) {
		return sqlSession.selectOne("notice.read", brdNo);
	}
	
	public int insert(NoticeVO noticeVO) {
		return sqlSession.insert("notice.insert", noticeVO);
	}
	
	public int udate(NoticeVO noticeVO) {
		return sqlSession.update("notice.update", noticeVO);
	}

	public int delete(int brdNo) {
		return sqlSession.delete("notice.delete", brdNo);
	}
}

package com.biz.bbs.dao;

import java.util.List;

import com.biz.bbs.vo.BBsMainVO;

/*
 * interface는 일종의 class의 설계도면
 * method의 구조만 정의 할 수 있다.
 * 혼자서는 아무런 일도 수행 할 수 없고 다른 클래스에서 implement해서 사용
 */

public interface BBsMainDAO {
	
	/*
	 * DAO class에는 CRUD method를 선언
	 */
	
	public void insert(BBsMainVO vo);			// 데이터추가는 한개(1개)씩 하는 것이 원칙
	public List<BBsMainVO> selectAll();			// 조건없이 전체 데이터 조회
	public BBsMainVO findById(long Id);			// Id를 기준으로 한개(1개)의 데이터 조회
	public void update(BBsMainVO vo);			// 데이터 한개(1개)를 기준으로 업데이트
	public void delete(long Id);				// Id를 기준으로 한개(1개)의 데이터만 삭제
	
	
}

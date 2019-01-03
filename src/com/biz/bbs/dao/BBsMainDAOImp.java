package com.biz.bbs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biz.bbs.vo.BBsMainVO;

/*
 * DAOImp는 DAO interface를 반드시 implement해서 구현해야 한다.
 */

public class BBsMainDAOImp implements BBsMainDAO{

	/*
	 * DB연결 설정
	 */

	Connection dbConn;
	
	
	public BBsMainDAOImp() {
		this.dbConnection();
	}
	
	public void dbConnection() {
		//dbConn 멤버변수를  초기화하는 메서드
		//dbConn 멤버변수 : db에 접속하기 위한 통로를 마련하고
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String strurl = "jdbc:oracle:thin:@localhost:1521:xe";
			String struser = "bbsUser";
			String strpassword = "1234";
			
			dbConn = DriverManager.getConnection(strurl, struser, strpassword);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	public void insert(BBsMainVO vo) {
		// TODO vo를 매개변수로 전달받아 DB에 저장하기
		String strDate = vo.getB_date();
		String strAuth = vo.getB_auth();
		String strSubject = vo.getB_subject();
		String strText = vo.getB_text();
		
//		String sql = "INSERT INTO tbl_bbs_main ";
//		sql += " VALUES(SEQ_BBS_MAIN.nextVAL, ";
//		sql += " ' " + strDate + " ' " + " , ";
//		sql += " ' " + strAuth + " ' " + " , ";
//		sql += " ' " + strSubject +" ' " + " , ";
//		sql += " ' " + strText + " ' ";
//		sql += " ) ";
		
		
		String sql = " INSERT INTO tbl_bbs_main ";
		sql += " VALUES( SEQ_BBS_MAIN.nextVAL, ?, ?, ?, ? ) ";
		
		
		PreparedStatement ps ;
		try {
			ps = dbConn.prepareStatement(sql);
			ps.setString(1, strDate);
			ps.setString(2, strAuth);
			ps.setString(3, strSubject);
			ps.setString(4, strText);
			
//			ps.executeQuery();
			
			ps.executeUpdate();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<BBsMainVO> selectAll() {
		// TODO DB로부터 모든 게시판 데이터를 읽어 list로 return
		
		PreparedStatement ps;
		
		//""안에서 앞뒤로 빈칸 한칸씩 추가 !
		String sql = " SELECT * FROM tbl_bbs_main ORDER BY B_ID ";
		
		try {
			ps = dbConn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			List<BBsMainVO> bbsList = new ArrayList();
			
			while(rs.next()) {
				long id = rs.getLong("B_ID");
				String date = rs.getString("B_DATE");
				String auth = rs.getString("B_AUTH");
				String subject = rs.getString("B_SUBJECT");
				String text = rs.getString("B_TEXT");
				
				BBsMainVO vo = new BBsMainVO();
				vo.setB_id(id);
				vo.setB_date(date);
				vo.setB_auth(auth);
				vo.setB_subject(subject);
				vo.setB_text(text);
				
				bbsList.add(vo);
			}
			rs.close();
			return bbsList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BBsMainVO findById(long Id) {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM tbl_bbs_main ";
		sql += " WHERE b_id = ? ";
		
		PreparedStatement ps;
		
		try {
			ps = dbConn.prepareStatement(sql);
			ps.setLong(1, Id);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			BBsMainVO vo = new BBsMainVO();
			vo.setB_id(rs.getLong("b_id"));
			vo.setB_date(rs.getString("b_date"));
			vo.setB_auth(rs.getString("b_auth"));
			vo.setB_subject(rs.getString("b_subject"));
			vo.setB_text(rs.getString("b_text"));
			
			return vo;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(BBsMainVO vo) {
		// TODO Auto-generated method stub
		long longId = vo.getB_id();
		String strAuth = vo.getB_auth();
		String strSubject = vo.getB_subject();
		String strText = vo.getB_text();
		
		String sql = " UPDATE tbl_bbs_main SET "; 
		sql += " B_Auth = ?, ";
		sql += " B_Subject = ?, ";
		sql += " B_Text = ? ";
		sql += " WHERE B_ID = ? ";
		
		PreparedStatement ps;
		
		try {
			ps = dbConn.prepareStatement(sql);
			
			ps.setString(1, strAuth);
			ps.setString(2, strSubject);
			ps.setString(3, strText);
			ps.setLong(4, longId);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(long Id) {
		// TODO id를 매개변수로 받아 해당 레코드를 삭제
		String sql = " DELETE FROM tbl_bbs_main ";
		sql += " WHERE b_id = ? ";
		
		PreparedStatement ps ;
		
		try {
			ps = dbConn.prepareStatement(sql);
			ps.setLong(1, Id);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

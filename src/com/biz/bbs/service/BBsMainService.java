package com.biz.bbs.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.biz.bbs.dao.BBsMainDAO;
import com.biz.bbs.dao.BBsMainDAOImp;
import com.biz.bbs.vo.BBsMainVO;

/*
 * DAO와 연계해서 CRUD에 대한 구체적인 실행을 실시하는 class
 */

public class BBsMainService {
	/*
	 * member 변수들을 생성하는데
	 */
	
	// dao.selectAll();에서 return한 bbsMainVO들을 담을 list
	
	List<BBsMainVO> BBsMainList ;
	// List : ArrayList, LinkedList를 대표하는 interface이다.
	
	//어떤 클래스에 대한 객체를 선언할 때 만약 해당 클래스들을 대표하는 interface가 있으면
	//interface를 자료형으로 하여 선언을 한다.
	BBsMainDAO mainDao;
	
	Scanner scan;
	
	public BBsMainService() {
		BBsMainList = new ArrayList();
		mainDao = new BBsMainDAOImp();
		scan = new Scanner(System.in);
	}
	
	public void bbsMenu() {
		while(true) {
			System.out.println("===============================================================");
			System.out.println("1. 리스트 보기     2. 추가      3. 수정     4. 삭제     5. 원하는내용만보기     0. 종료");
			System.out.println("---------------------------------------------------------------");
			System.out.print("선택 >> ");
			String strM = scan.nextLine();
			
			int intM = Integer.valueOf(strM);
/*
			if(intM == 0) return;
			if(intM == 1) this.viewBBsList();
			if(intM == 2) this.insertBBS(); //데이터추가
			if(intM == 3) {
				// 수정할 데이터를 확인
				this.viewBBsList();
				this.updateBBS(); //데이터수정
			}
			if(intM == 4) {
				//삭제할 데이터를 확인
				this.viewBBsList();
				this.deleteBBS(); //데이터삭제
			}
*/
			if(intM == 0) return;
			else if(intM == 2) this.insertBBS();
			else this.viewBBsList();
			
			if(intM == 3) this.updateBBS();
			if(intM == 4) this.deleteBBS();
			if(intM == 5) this.viewBBsText();
			
		}
	}
	
	
	public void viewBBsText(){
		System.out.print("내용 확인할 ID(Enter:취소)>> ");
		String strId = scan.nextLine();
		if(strId.equals("") ) {
			System.out.println("취소되었습니다.");
			return;
		}
		//if(strId.isEmpty()){} => 스캐너사용시 간혹 조건문을 못잡는 경우 있음(String 변수에 넣어서 해주는경우에는 가능)
		long longId = Long.valueOf(strId);
		
		BBsMainVO vo = mainDao.findById(longId);
		System.out.println("=======================================================");
		System.out.println("작성일자 : " + vo.getB_date());
		System.out.println("작성자 : " + vo.getB_auth());
		System.out.println("제목 : " + vo.getB_subject());
		System.out.println("내용 : " + vo.getB_text());
		System.out.println("=======================================================");
	}
	
	// 게시판 List를 보는 method()를 선언
	public void viewBBsList() {
		
		/*
		 * 아직 selectAll()이 구현이 되어 있지 않지만 service 입장에서는 selectAll()이 게시판 전체리스트를
		 * return해 줄 것이라는 가정을 하고 나머지 코드를 작성 할 수 있다.
		 */
		BBsMainList = mainDao.selectAll();
		
		System.out.println("==========================================================");
		System.out.println("나의 게시판 v1.1");
		System.out.println("==========================================================");
		System.out.printf("%5s  %-10s%17s%16s%19s\n", "NO", "날짜", "작성자", "제목", "내용");
		System.out.println("----------------------------------------------------------");
		if(BBsMainList == null) {
			System.out.println("데이터가 없습니다.");
		} else {
			for(BBsMainVO vo : BBsMainList) {
				System.out.printf("%5d  ", vo.getB_id());
				System.out.printf("%-12s  ", vo.getB_date());
				System.out.printf("%-15s  ", vo.getB_auth());
				System.out.printf("%-12s ", vo.getB_subject());
				System.out.println(vo.getB_text());
			}
		}
		
	}

	/*
	 * 키보드에서 작성자, 제목, 내용을 입력 받고 현재 컴퓨터 날짜를 작성일자로 하여 추가
	 */
	public void insertBBS() {
		
		System.out.print("작성자 >> ");
		String strAuth = scan.nextLine();
		System.out.print("제목 >> ");
		String strSubject = scan.nextLine();
		System.out.print("내용>> ");
		String strText = scan.nextLine();
		
		//구버젼
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Date d = new Date();
		String strDate = sdf.format(d);
		
		//신버젼(1.8이상)
//		LocalDate ld = LocalDate.now();
//		strDate = ld.toString();
		
		BBsMainVO vo = new BBsMainVO();
		vo.setB_date(strDate);
		vo.setB_auth(strAuth);
		vo.setB_subject(strSubject);
		vo.setB_text(strText);
		
		mainDao.insert(vo);
		
	}
	
	public void updateBBS() {
		//TODO 게시판 리스트에서 번호를 선택하면 수정 시작
		System.out.print("수정할 번호(Enter:취소) >> ");
		String strId = scan.nextLine();
		if(strId.equals("") ) {
			System.out.println("수정이 취소되었습니다.");
			return;
		}
		long longId = Long.valueOf(strId);
		
		BBsMainVO vo = mainDao.findById(longId);
		
		System.out.println("수정:내용입력, 수정취소:Enter");
		System.out.println("--------------------------------------------");
		System.out.println("작성자 : " + vo.getB_auth());
		System.out.print("수정 : ");
		String strAuth = scan.nextLine();
		
		System.out.println("제목 : " + vo.getB_subject());
		System.out.print("수정 : ");
		String strSubject = scan.nextLine();
		
		System.out.println("내용 : " + vo.getB_text());
		System.out.print("수정 : ");
		String strText = scan.nextLine();
		
		/*
		 * 만약 내용(작성자,제목,내용)을 입력하지 않고 Enter만 입력했으면 원래 내용을 그대로 유지 하도록 한다. 
		 */
		
		if(strAuth.equals("") == false) {
			vo.setB_auth(strAuth);
		}
		if(!strSubject.equals("")) {
			vo.setB_subject(strSubject);
		}
		if(!strText.equals("")) {
			vo.setB_text(strText);
		}
		
		mainDao.update(vo);
	}
	
	public void deleteBBS() {
		//TODO 게시판 삭제하기
		System.out.print("삭제 할 번호(Enter:취소) >>");
		String strId = scan.nextLine();
		if(strId.equals("") ) {
			System.out.println("삭제가 취소되었습니다.");
			return;
		}
		long longId = Long.valueOf(strId);
		
		//삭제하기 전에 삭제할 데이터를 다시 확인시켜보자
		//Id를 기준으로 해당 데이터 가져오기
		
		BBsMainVO vo = mainDao.findById(longId);
		
		System.out.println("==========================================");
		System.out.println("삭제할 데이터 확인");
		System.out.println("------------------------------------------");
		System.out.println("작성일자 : " + vo.getB_date());
		System.out.println("작성자 : " + vo.getB_auth());
		System.out.println("제목 : " + vo.getB_subject());
		System.out.println("내용 : " + vo.getB_text());
		System.out.println("==========================================");
		System.out.print("위 데이터를 삭제하시겠습니까 ? (YES) >> ");
		String confirm = scan.nextLine();
		
		if(confirm.equals("YES")) {
			mainDao.delete(longId);
			System.out.println("삭제 되었습니다.");
		} else {
			System.out.println("취소 되었습니다.");
		}
		
	}

}

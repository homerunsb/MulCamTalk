package com.mc.mctalk.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mc.mctalk.vo.UserVO;

public class UserDAO {
	private final String TAG = "UserDAO : ";
//	private String loginSQL = "select id from inbody_member where id = ? and pw = ? ";
//	private String joinSQL = "insert into inbody_member values(?, ?, ?, ?, ?, ?, ?, ?, sysdate )";
//	private String checkSQL = "select id from inbody_member where id = ? ";
	private String searchAllFriendsSQL = "select ur.rel_user_id, u.user_name, u.user_pf_img_path, u.user_msg "
												+ "from user_relation ur, users u "
												+ "where ur.rel_user_id = u.user_id "
												+ "and ur.user_id = ? "
												+ "order by user_name";
	private String memberJoinSQL =  "insert into users (user_id,user_pw,user_name,user_sex,user_birthday,user_joindate) "
			+ "values(?,?,?,?,now(),now()) ";
	
	
	public boolean joinMember(MemberInfoVO memberinfoVO){
			boolean insertSucess=false;
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rst = null;
			try{
				conn = JDBCUtil.getConnection();
				stmt = conn.prepareStatement(memberJoinSQL);
				
				stmt.setString(1, memberinfoVO.getMemberId());
				stmt.setString(2, memberinfoVO.getMemberPassword());
				stmt.setString(3, memberinfoVO.getMemberName());
				stmt.setString(4, memberinfoVO.getMemberSex()+"");
				
				int cnt = stmt.executeUpdate();
				if(cnt > 0){
					System.out.println("Insert Success");
					  insertSucess = true;
				}			
				
			}catch(SQLException e){
				System.out.println("Join e : " + e);
				insertSucess =false;
			}finally {
				JDBCUtil.close(rst,stmt, conn);
			} return insertSucess;
		  
	}
//	values('memberId','memberPassword','memberName',0,now()) ";
	
	
//	public String doLogin(String id, String pw){
//		System.out.println("doLogin()");
//		String id_result = null; 
//		
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rst = null;
//		try{
//			conn = JDBCUtil.getConnection();
//			stmt = conn.prepareStatement(loginSQL);
//			stmt.setString(1, id);
//			stmt.setString(2, pw);
//			rst = stmt.executeQuery();
//			
//			//로그인 정보는 1개만 리턴하므로 while문이 필요없음
//			if(rst.next()){
//				id_result = rst.getString(1);
//				System.out.println(id_result);
//			}
//			
//		}catch(SQLException e){
//			System.out.println("login e : " + e);
//		}finally {
//			JDBCUtil.close(rst,stmt, conn);
//		}
//		
//		return id_result;
//	}
	
	public Map<String, UserVO> getAllFriendsMap(String id){
		System.out.println(TAG + "getAllFriendsMap()");
		Map<String, UserVO> friendsMap = new LinkedHashMap<String, UserVO>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		UserVO vo = null;
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(searchAllFriendsSQL);
			stmt.setString(1, id);
			rst = stmt.executeQuery();
			
			while(rst.next()){
				vo = new UserVO();
				vo.setUserID(rst.getString("rel_user_id"));
				vo.setUserName(rst.getString("user_name"));
				vo.setProfileImage(rst.getString("user_pf_img_path"));
				vo.setUserMsg(rst.getString("user_msg"));
				friendsMap.put(vo.getUserID(), vo);
			}
//			System.out.println("searchFriend() : " + friendsList.size());
		}catch(SQLException e){
			System.out.println("getAllFriendsMap e : " + e);
		}finally {
			JDBCUtil.close(rst,stmt, conn);
		}
		return friendsMap;
	}
	
	
//	public void doJoin(JoinVO vo){
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rst = null;
//		try{
//			conn = JDBCUtil.getConnection();
//			stmt = conn.prepareStatement(joinSQL);
//			System.out.println(vo.toString());
//			
//			stmt.setString(1, vo.getId());
//			stmt.setString(2, vo.getPw());
//			stmt.setString(3, vo.getName());
//			stmt.setString(4, vo.getSex());
//			stmt.setString(5, vo.getBirthday());
//			stmt.setString(6, vo.getMail());
//			stmt.setString(7, vo.getPhone());
//			stmt.setString(8, vo.getAddress());
//			
//			int cnt = stmt.executeUpdate();
//			if(cnt > 0){
//				System.out.println("Insert Success");
//			}			
//			
//		}catch(SQLException e){
//			System.out.println("Join e : " + e);
//		}finally {
//			JDBCUtil.close(rst,stmt, conn);
//		}
//	}
	
//	public boolean doCheck(JoinVO vo){
//		boolean result = true;
//		//System.out.println("doCheck vo.getId() : " + vo.getId());
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rst = null;
//		
//		try{
//			conn = JDBCUtil.getConnection();
//			stmt = conn.prepareStatement(checkSQL);
//			stmt.setString(1, vo.getId());
//			rst = stmt.executeQuery();
//			
//			if(rst.next()){
//				result = false;
//			}
//			
//		}catch(SQLException e){
//			System.out.println("login e : " + e);
//		}finally {
//			JDBCUtil.close(rst,stmt, conn);
//		}
//		return result;
//	}
	

}

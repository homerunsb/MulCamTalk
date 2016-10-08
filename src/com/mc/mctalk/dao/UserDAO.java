package com.mc.mctalk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mc.mctalk.chatserver.ChattingClient;
import com.mc.mctalk.vo.UserVO;

public class UserDAO {
	private final String TAG = "UserDAO : ";
	private String loginSQL = "select * from users where user_id = ? and user_pw = ? ";
//	private String checkSQL = "select id from inbody_member where id = ? ";
	private String searchAllFriendsSQL = "select ur.rel_user_id, u.user_name, u.user_pf_img_path, u.user_msg "
												+ "from user_relation ur, users u "
												+ "where ur.rel_user_id = u.user_id "
												+ "and ur.user_id = ? "
												+ "order by user_name";
	private String memberJoinSQL =  "insert into users (user_id,user_pw,user_name,user_sex,user_birthday,user_joindate) "
			+ "values(?,?,?,?,now(),now()) ";
	
	public boolean joinMember(UserVO memberinfoVO){
			boolean insertSucess=false;
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rst = null;
			try{
				conn = JDBCUtil.getConnection();
				stmt = conn.prepareStatement(memberJoinSQL);
				
				stmt.setString(1, memberinfoVO.getUserID());
				stmt.setString(2, memberinfoVO.getUserPassword());
				stmt.setString(3, memberinfoVO.getUserName());
				stmt.setString(4, memberinfoVO.getUserSex()+"");
				
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
	
	public UserVO doLogin(String id, String pw){
		System.out.println("doLogin()");
		String id_result = null; 
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		UserVO vo = new UserVO();
		
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(loginSQL);
			stmt.setString(1, id);
			stmt.setString(2, pw);
			rst = stmt.executeQuery();
			
			//로그인 정보는 1개만 리턴하므로 while문이 필요없음
			if(rst.next()){
				id_result = rst.getString(1);
				vo.setUserID(rst.getString(1));
				vo.setUserPassword(rst.getString(2));
				vo.setUserName(rst.getString(3));
				vo.setUserSex(rst.getInt(4));
				vo.setUserBirth(rst.getString(5));
				vo.setUserMail(rst.getString(6));
				vo.setUserPhone(rst.getString(7));
				vo.setUserAddress(rst.getString(8));
				vo.setUserJoinDate(rst.getString(9));
				vo.setUserImgPath(rst.getString(10));
			}
			
			if(vo != null){
				System.out.println("Login User Info : " + vo.toString());
				new ChattingClient(vo);
			}
			
		}catch(SQLException e){
			System.out.println("login e : " + e);
		}finally {
			JDBCUtil.close(rst,stmt, conn);
		}
		
		return vo;
	}
	
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
				vo.setUserImgPath((rst.getString("user_pf_img_path")));
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

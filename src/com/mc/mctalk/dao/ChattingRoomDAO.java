package com.mc.mctalk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.mc.mctalk.vo.ChattingRoomVO;
import com.mc.mctalk.vo.UserVO;
import com.mysql.jdbc.Statement;

public class ChattingRoomDAO {
	private final String TAG = "ChattingRoomDAO : ";
	//쿼리 수정 필요. 그룹방에 같이 들어있는 경우도 검색됨.
	private String searchLastChatRoomSQL =  "SELECT room_id "
															+ "FROM chat_room_users "
															+ "WHERE room_id in( "
															+ "	select "
															+ "	me.room_id "
															+ "	from( select room_id "
															+ "		from chat_room_users cru "
															+ "		where cru_left_time is null "
															+ "		and user_id = ? "
															+ "		)me, "
															+ "		(select room_id "
															+ "		from chat_room_users cru "
															+ "		where cru_left_time is null "
															+ "		and user_id = ? "
															+ "		)other "
															+ "	where me.room_id = other.room_id ) "
															+ "GROUP BY room_id "
															+ "HAVING count(room_id) = 2 ";
	private String make1on1ChattingRoomSQL = "insert into chat_rooms "
																+ "(room_created_time, room_name) "
																+ "values(now(), ?)";
	private String addUserToChattingRoomSQL = "insert into chat_room_users "
																+ "(room_id, user_id, cru_entered_time) "
																+ "values(?, ?, now())";
	private String searchChatRoomUsersSQL = "select * "
															 + "from users " 
															 + "where user_id in (select user_id " 
															 + "from chat_room_users "
															 + "where room_id = ?) ";	 
								
	
	public String searchLastChatRoom(String loginID, String friendID){
		System.out.println(TAG + "searchLastChatRoom()");
		String roomID = null; 
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(searchLastChatRoomSQL);
			stmt.setString(1, loginID);
			stmt.setString(2, friendID);
			rst = stmt.executeQuery();
			
			//로그인 정보는 1개만 리턴하므로 while문이 필요없음
			if(rst.next()){
				roomID = rst.getString(1);
			}
			
		}catch(SQLException e){
			System.out.println("searchLastChatRoom e : " + e);
		}finally {
			JDBCUtil.close(rst,stmt, conn);
		}
		return roomID;
	}
	
	
	public String make1on1ChattingRoom(String loginID, String friendID){
		System.out.println(TAG + "make1on1ChattingRoom()");
		String roomID = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(make1on1ChattingRoomSQL, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, loginID+"/"+friendID); 
			int cnt = stmt.executeUpdate();

			if(cnt > 0){
				rst = stmt.getGeneratedKeys();
				if(rst.next()){
					System.out.println("Chat Room Insert Success");
					roomID = rst.getString(1);
					System.out.println("만들어진 방 ID : " + roomID);
				}
			}		
			
			
		}catch(SQLException e){
			System.out.println("make1on1ChattingRoom e : " + e);
		}finally {
			JDBCUtil.close(rst,stmt, conn);
		}
		return roomID;
	}
	
	//인원수와 상관없이 방을 만들자.
	// 리스트를 매개변수로 받아서 여러명들을 처리하도록 하겠음. 
	public String makeChattingRoom(String loginID, LinkedHashMap<String, UserVO> lastSelected ){
		System.out.println(TAG + "makeChattingRoom()");
		String roomID = null;
		String friendNames = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(make1on1ChattingRoomSQL, Statement.RETURN_GENERATED_KEYS);
			Iterator<Entry<String, UserVO>> entry = lastSelected.entrySet().iterator();
			for(int i =0 ; i<lastSelected.size(); i++){
				friendNames += "/"+ entry.next().getValue().getUserName();
			}
			
			
			stmt.setString(1, loginID+"/"+friendNames);// 방이름이 될 것이다. 
			int cnt = stmt.executeUpdate();

			if(cnt > 0){
				rst = stmt.getGeneratedKeys();
				if(rst.next()){
					System.out.println("Chat Room Insert Success");
					roomID = rst.getString(1);
					System.out.println("만들어진 방 ID : " + roomID);
				}
			}		
			
			
		}catch(SQLException e){
			System.out.println("make1on1ChattingRoom e : " + e);
		}finally {
			JDBCUtil.close(rst,stmt, conn);
		}
		return roomID;
	}
	
	
	
	//일단은 ID만 받으나, 추후에 친구 초대 기능을 위해 객체로 변경 필요
	public boolean addUserToChattingRoom(String roomID, String userID){
		System.out.println(TAG + "addUserToChattingRoom()");
		boolean isSuceed = false; 
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(addUserToChattingRoomSQL);
			stmt.setString(1, roomID); //roomid
			stmt.setString(2, userID); //user

			int cnt = stmt.executeUpdate();
			if(cnt > 0){
				System.out.println("Chat User Insert Success");
				isSuceed = true;
			}		
			
		}catch(SQLException e){
			System.out.println("addUserToChattingRoom e : " + e);
		}finally {
			JDBCUtil.close(rst,stmt, conn);
		}
		return isSuceed;
	}
	
	//채팅 참여자 유저 정보를 받아서 최종적으로 RoomVO를 만들어 리턴
	//방이름 만들어주는 공통으로 쓸 수 있는 메소드 필요(1:1은 상대방 이름, 여러명은 구분값으로 전부 나열)
	public ChattingRoomVO getChatRoomVO(String roomID){
		System.out.println(TAG + "getChatRoomVO()");
		System.out.println("방ID : " + roomID);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		ChattingRoomVO roomVO = new ChattingRoomVO(); 
		ArrayList<String> listChattingUserIDs = new ArrayList<>();
		String roomName = "";
		
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(searchChatRoomUsersSQL);
			stmt.setString(1, roomID);
			rst = stmt.executeQuery();
			
			while(rst.next()){
				UserVO UserVO = new UserVO();
				UserVO.setUserID(rst.getString(1));
//				UserVO.setUserPassword(rst.getString(2));
				UserVO.setUserName(rst.getString(3));
//				UserVO.setUserSex(rst.getInt(4));
//				UserVO.setUserBirth(rst.getString(5));
//				UserVO.setUserMail(rst.getString(6));
//				UserVO.setUserPhone(rst.getString(7));
//				UserVO.setUserAddress(rst.getString(8));
//				UserVO.setUserJoinDate(rst.getString(9));
//				UserVO.setUserImgPath(rst.getString(10));
				listChattingUserIDs.add(UserVO.getUserID());
				roomName += UserVO.getUserName() + "/";
			}
			
			if(roomName.substring(roomName.length()-1, roomName.length()).equals("/")){
				roomName = roomName.substring(0, roomName.length()-1);
//				System.out.println("방이름 : " + roomName);
			}
			
			roomVO.setChattingRoomID(roomID);
			roomVO.setChattingRoomName(roomName);
			roomVO.setChattingRoomUserIDs(listChattingUserIDs);
		}catch(SQLException e){
			System.out.println("addUserToChattingRoom e : " + e);
		}finally {
			JDBCUtil.close(rst,stmt, conn);
		}
		
		return roomVO;
	}
	
	
}

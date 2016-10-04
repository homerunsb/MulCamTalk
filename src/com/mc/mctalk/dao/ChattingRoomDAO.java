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

import com.mc.mctalk.vo.ChattingRoomVO;
import com.mc.mctalk.vo.FriendsVO;
import com.mc.mctalk.vo.MemberInfoVO;
import com.mysql.jdbc.Statement;

public class ChattingRoomDAO {
	private final String TAG = "ChattingRoomDAO : ";

	private String searchLastChatRoomSQL = "select x.room_id "
															+ "from chat_rooms x, ("
															+ "	select me.room_id "
															+ "	from "
															+ "		(select room_id "
															+ "		from chat_room_users cru "
															+ "		where cru_left_time is null "
															+ "		and user_id = ? "
															+ "		)me, "
															+ "		(select room_id "
															+ "		from chat_room_users cru "
															+ "		where cru_left_time is null "
															+ "		and user_id = ? "
															+ "		)other "
															+ "	where me.room_id = other.room_id )y "
															+ "where x.room_id = y.room_id";	
	private String make1on1ChattingRoomSQL = "insert into chat_rooms "
																+ "(room_created_time, room_name) "
																+ "values(now(), ?)";
	private String addUserToChattingRoomSQL = "insert into chat_room_users "
																+ "(room_id, user_id, cru_entered_time) "
																+ "values(?, ?, now())";
	
	
	public String searchLastChatRoom(String userID, String friendID){
		System.out.println(TAG + "searchLastChatRoom()");
		String roomID = null; 
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(searchLastChatRoomSQL);
			stmt.setString(1, userID);
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
	
	
	public String make1on1ChattingRoom(String userID, String friendID){
		System.out.println(TAG + "make1on1ChattingRoom()");
		String roomID = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(make1on1ChattingRoomSQL, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, userID+"/"+friendID);
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
	
}

package com.mc.mctalk.chatserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.mc.mctalk.vo.ChattingRoomVO;
import com.mc.mctalk.vo.MessageVO;
import com.mc.mctalk.vo.UserVO;

public class ChattingServer {
	final private int SERVER_PORT = 9999;
	private ServerSocket serverSocket;
	private Map<String, ChattingThread> htThreadList; //스레드 맵
	private Map<String, UserVO> htConnectedUsers; //연결 유저 맵
	private Map<String, ChattingRoomVO> htRoomVO; //채팅 방 맵

	Gson gson = new Gson();
	private BufferedReader br;
	private BufferedWriter bw;
	
	//서버 생성자
	public ChattingServer() {
		htThreadList = new Hashtable<>();
		htConnectedUsers = new Hashtable<>();
		htRoomVO = new Hashtable<>();
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			while(true){
				System.out.println("[서버] 클라이언트 연결 대기중...");
				Socket socket = serverSocket.accept();
				System.out.println("[서버] 클라이언트 접속 : " + socket.getRemoteSocketAddress());
				
				//생성된 소켓으로 br, bw 생성
				try {
					br = new BufferedReader(new InputStreamReader(
							socket.getInputStream(),"EUC_KR"));
					bw = new BufferedWriter(new OutputStreamWriter(
							socket.getOutputStream(),"EUC_KR"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//로그인 유저 정보 받기.
				String loginUserInfo = br.readLine();
				UserVO userVO = gson.fromJson(loginUserInfo, UserVO.class);
				htConnectedUsers.put(userVO.getUserID(), userVO);
				
				
				//새로운 클라이언트 접속하면 새로운 스레드 객체를 소켓을 주고 생성하여 리스트에 추가
				ChattingThread t = new ChattingThread(br, bw, userVO);
				htThreadList.put(userVO.getUserID(), t);
				System.out.println("[서버]접속한 사용자 수 : " + htConnectedUsers.size());
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcast(String msg){
		//받은 메시지 JSON을 파싱하여 메시지의 발송 룸번호를 파악
		MessageVO messageVO = gson.fromJson(msg, MessageVO.class);
		ChattingRoomVO roomVO = messageVO.getRoomVO();
		String roomID = roomVO.getChattingRoomID();
		
		//방ㅇ
//		htRoomVO.put(roomID, roomVO);
		
		ArrayList userList = (ArrayList) roomVO.getChattingRoomUserIDs();
		System.out.println("서버 받은 ArrayList : " + userList.size());

		if(userList.size()>0){
			for (int i = 0; i < userList.size(); i++) {
				System.out.println("받은 방 유저 리스트 : " + userList.get(i));
				try{
					htThreadList.get(userList.get(i)).sendToClient(msg);
				}catch (NullPointerException e){
					//접속한 유저가 아닐 경우
					System.out.println("유저접속X DB로 메시지 전송 필요");
					e.printStackTrace();
//					htThreadList.get(userList.get(i)).sendToClient(msg);
				}
			}
		}
	}
	
	//쓰레드 목록에서 특정 쓰레드 삭제하기
	public void removeThread(ChattingThread t){
		htThreadList.remove(t);
	}
	
	//하나의 클라이언트가 접속했을 때 각 클라를 담당할 쓰레드 클래스
	class ChattingThread extends Thread{
		private UserVO userVO;
		private String userName;
		private BufferedReader br;
		private BufferedWriter bw;
		
		ChattingThread(BufferedReader br, BufferedWriter bw, UserVO userVO){
			this.br = br;
			this.bw = bw;
			this.userVO = userVO;
			this.userName = userVO.getUserName();
		}//생성자
		
		@Override
		public void run() {
			try {
//				broadcast("["+userName+"]님이 입장하셨습니다.");
				
				while(true){
					String msg = br.readLine();
					broadcast(msg);
				}
			} catch (IOException e) {
				//담당 클라이언트 퇴장했을 때 리스트 중 해당 스레드 삭제
				removeThread(this);
//				broadcast("["+userName+"]님이 퇴장하셨습니다.");
				e.printStackTrace();
			}
		}//run()
		
		//현재 쓰레드가 담당하는 클라이언트에게 메시지 보내기
		public void sendToClient(String msg){
			try{
				bw.write(msg+"\n");
				bw.flush();
			}catch(IOException e){
				e.printStackTrace();
			}
		}//senToClient()
		
	}//Class chattingThread
	
	
	
	public static void main(String[] args) {
		ChattingServer run = new ChattingServer();
	}
}

package com.mc.mctalk.chatserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.mc.mctalk.dao.ChattingRoomDAO;
import com.mc.mctalk.vo.ChattingRoomVO;
import com.mc.mctalk.vo.MessageVO;
import com.mc.mctalk.vo.UserVO;

public class ChattingServer {
	private final String TAG = "ChattingServer : ";
	final private int SERVER_PORT = 8888;
	private ServerSocket serverSocket;
	private Map<String, ChattingThread> htThreadList; //스레드 맵
	private Map<String, UserVO> htConnectedUsers; //연결 유저 맵
	private Map<String, ChattingRoomVO> htRoomVO; //채팅 방 맵
	private ChattingRoomDAO chatdao = new ChattingRoomDAO();
	private String disconnClient = null;
	private ExecutorService executorService;
	private Gson gson = new Gson();
	private BufferedReader br;
	private BufferedWriter bw;
	
	//서버 생성자
	public ChattingServer() {
//		executorService = Executors.newCachedThreadPool();
		
		htThreadList = new Hashtable<>();
		htConnectedUsers = new Hashtable<>();
		htRoomVO = new Hashtable<>();
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			executorService = Executors.newCachedThreadPool();

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

				System.out.println("[서버] 생성된 스레드 수 : " + htThreadList.size());
//				System.out.println("[서버] 접속한 사용자 수 : " + htConnectedUsers.size());
				executorService.submit(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcast(String msg){
		System.out.println(TAG + "broadcast()");
		//받은 메시지 JSON을 파싱하여 메시지의 발송 룸번호를 파악
		MessageVO messageVO = gson.fromJson(msg, MessageVO.class);
		ChattingRoomVO roomVO = messageVO.getRoomVO();
		String roomID = roomVO.getChattingRoomID();
		// 1.보내기 전에 데이터 베이스에 메세지 정보를 넣는다!!! 메세지 아이디값 반환
		// 2.메세지에 해당 메세지 아이디를 넣고 다시 제이슨으로 조립후 변수에 담음. 
		// 3.메시지 ID가 있을 경우는 반송된 메시지, 없을 경우는 새 메시지.
		try{		
			String msgID = chatdao.insertMessageToDB(messageVO);
			messageVO.setMessageID(msgID);
			msg = gson.toJson(messageVO);
			ArrayList userList = null;
			if(roomVO!=null){
				userList = (ArrayList) roomVO.getChattingRoomUserIDs();
	//			System.out.println("서버 받은 ArrayList : " + userList.size());
			}
			if(userList.size()>0){
				for (int i = 0; i < userList.size(); i++) {
	//				System.out.println("받은 방 유저 리스트 : " + userList.get(i));
					try{
						htThreadList.get(userList.get(i)).sendToClient(msg);
					}catch (NullPointerException e){
						System.out.println("NullPointerException");
						//접속한 유저가 아닐 경우
						disconnClient = (String) userList.get(i);
						chatdao.insertDiconnClient(msgID, disconnClient);
	//					e.printStackTrace();ㄴ
					}
				}
			}
		}catch (SocketException e){
			System.out.println("SocketException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
	
	//쓰레드 목록에서 특정 쓰레드 삭제하기
	public void removeThread(ChattingThread t){
		htThreadList.remove(t);
	}
	
	//하나의 클라이언트가 접속했을 때 각 클라를 담당할 쓰레드 클래스
	class ChattingThread extends Thread{
		private String TAG = "ChattingThread : ";

		private UserVO userVO;
		private String userName;
		private BufferedReader br;
		private BufferedWriter bw;
		
		public ChattingThread(BufferedReader br, BufferedWriter bw, UserVO userVO){
			System.out.println(TAG + "ChattingThread()");
			this.br = br;
			this.bw = bw;
			this.userVO = userVO;
			this.userName = userVO.getUserName();
		}//생성자
		
		@Override
		public void run() {
			System.out.println(TAG + "run()");
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
		public void sendToClient(String msg) throws IOException{
			System.out.println(TAG + "sendToClient()");
			bw.write(msg+"\n");
			bw.flush();
			
		}//senToClient()
	}//Class chattingThread
	
	public static void main(String[] args) {
		ChattingServer run = new ChattingServer();
	}
}

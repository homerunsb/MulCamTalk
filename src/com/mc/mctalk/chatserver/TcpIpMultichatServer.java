package com.mc.mctalk.chatserver;
/**
 * 자바의 정석에서 나온 멀티채팅에  GUI를 입힘.
 * GUI제작 : 아둔한사 (http://adunhansa.tistory.com)
 * 추가로 할일 또는 궁금증 : 쓰레드가 왜 계속 생기는지.. 궁금하고..
 * 또 해볼일은 현재 접속자 명단을 클라이언트 창에서 보이게 하고 싶다.
 * 2014/04/23
 */


import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


class TcpIpMultichatServerUI extends JFrame{
	JTextArea ta;
	JTextField tf;
	
	public TcpIpMultichatServerUI(){
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("채팅방 서버!"); 
		setLayout(new BorderLayout());
		
		JLabel label = new JLabel("This is a server!");
		ta= new JTextArea(25, 40);
		tf = new JTextField(25);
		
		add(label, BorderLayout.NORTH);
		add(ta, BorderLayout.CENTER);
		add(tf, BorderLayout.SOUTH);
		setVisible(true);
	}
}


public class TcpIpMultichatServer {
	String TAG = "TcpIpMultichatServer : ";
	HashMap clients;
	HashMap roomNumber;
	
	TcpIpMultichatServer(){
		System.out.println("1. " + TAG + "TcpIpMultichatServer()");

		clients = new HashMap();
		roomNumber = new HashMap();

		//HashMap은 HashTable, Vector와는 다르게 멀티 쓰레드시 안정성이 보장이 안됨
		//그런 HashMap 명시적으로 동기화 하는 메소드 synchronizedMap()
		Collections.synchronizedMap(clients);
		Collections.synchronizedMap(roomNumber);
		
	}
	
	public void start(){
		System.out.println("2. " + TAG + "start()");

		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try{
			serverSocket = new ServerSocket(7777);
			System.out.println("서버 시작");
			
			while(true){
				socket=serverSocket.accept();
				System.out.println("["+socket.getInetAddress()+"]"
						+ "에서 접속하셨습니다.");
				//서버 리시버 하나 만든다. 쓰레드라는 이름으로~
				//(궁금)근데 같은 이름으로 계속 생성해도 되는 것인가..?반복문인데..?
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
				
				System.out.println("쓰레드 네임 : "+thread.getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}//start
	
	//서버 리시버이다..
	class ServerReceiver extends Thread{
		final String TAG = "ServerReceiver : ";

		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		
		//생성자에서는 서버소켓을 받아서 인풋아웃풋스트림을 하나 만들고 연결한다.
		ServerReceiver(Socket socket){
			this.socket = socket;
			try{
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			}catch(IOException e){
				System.out.println("서버 리시버 소켓 IO 에러");
			}
		}//생성자 끝
		
		public void run(){
			System.out.println("3. " + TAG + "run()");

			String name="";
			String roomNum="";
			
			try{
				name = in.readUTF();
				sendToAll("#"+name+"님이 들어오셨습니다.");
//				printRoomNumber(roomNum);
				
				clients.put(name, out);
//				roomNumber.put(roomNum, out);
				
				System.out.println("현재 접속자 수는 "+clients.size()+"입니다");
				System.out.print("현재 접속자 목록 : ");
				
				while(in!=null){
					sendToAll(in.readUTF());
				}
			}catch(IOException e){
				System.out.println("리시버 도중 io에러");
			}finally{
				sendToAll("#"+name+"님이 나가셨습니다~");
				clients.remove(name);
				System.out.println("["+socket.getInetAddress()+
						":"+socket.getPort()+"] 에서 접속을 종료함");
				System.out.println("현재 접속자 수는 "+clients.size()+"입니다");
				
			}//try
		}//run
	}//ReceiverThread
	
	void sendToAll(String msg){
		System.out.println("4. " + TAG + "sendToAll()");

		//메시지를 받아서 전부 날려주는 소스다.
		Iterator it = clients.keySet().iterator();
		
		while(it.hasNext()){
			try{
				DataOutputStream out = 
						(DataOutputStream)clients.get(it.next());
				out.writeUTF(msg);
			}catch(IOException e){
				System.out.println("sendToall 입출력 에러");
			}
		}//while
	}//sendToAll
	
	void printRoomNumber(String msg){
		System.out.println(TAG + "printRoomNumber()");

		Iterator it = roomNumber.keySet().iterator();
		
		while(it.hasNext()){
			//				DataOutputStream out = 
//						(DataOutputStream)roomNumber.get(it.next());
//				out.writeUTF(msg);
//			System.out.println("룸 넘버 : "  + roomNumber.get(it.next()));
			System.out.println("룸 넘버 : "  + it.next());
			}//while
		}//sendToAll
	
	
	//메인에서는 새로 생성자 만들고 스타트를 실행! 
	public static void main(String args[]){
		TcpIpMultichatServer f = new TcpIpMultichatServer();
		f.start();
		
	}
	
}//Class



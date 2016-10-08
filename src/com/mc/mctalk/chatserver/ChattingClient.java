package com.mc.mctalk.chatserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import com.mc.mctalk.vo.UserVO;

public class ChattingClient {
	private Socket socket = null;
	private BufferedWriter bw = null;
	private BufferedReader br = null;
	private UserVO vo = null;

	public ChattingClient(){};
	public ChattingClient(UserVO vo){
		startClient();
		this.vo = vo;
	}
	
	public void startClient(){
		try {
//			Socket socket = new Socket(InetAddress.getByName("70.12.109.103"),9999);
			Socket socket = new Socket(InetAddress.getByName("172.30.1.27"),9999);

			bw = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(),"EUC_KR"));
			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream(),"EUC_KR"));
			
			//서버 연결 후 user 정보 JSON 전송.
			if(vo != null){
				
			}
			
//			bw.write(nickname+ "\n");
//			bw.flush();
//			
//			//리시버 작동
//			new Reciever().start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

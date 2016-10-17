package com.mc.mctalk.vo;

import java.io.File;

import com.mc.mctalk.chatserver.ChattingClient;

public class FileVO extends File {
	private File file;
	private  String sendUserID;
	public FileVO(String filePath, String sendUserID) {
		super(filePath);
		
		this.file = new File(filePath);
		this.sendUserID = sendUserID;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getSendUserID() {
		return sendUserID;
	}
	public void setSendUserID(String sendUserID) {
		this.sendUserID = sendUserID;
	}
	@Override// 유저 아이디 뱉도록 오버로딩!!! 
	public String toString() {
		
		return sendUserID;
	}
}

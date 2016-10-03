package com.mc.mctalk.vo;

public class ChattingMemberVO {
	private String id, name;
	private int roomID = 0;

	public ChattingMemberVO() {
	}

	public ChattingMemberVO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomNumber() {
		return roomID;
	}
	
	public void setRoomNumber(int roomID) {
		this.roomID = roomID;
	}
}

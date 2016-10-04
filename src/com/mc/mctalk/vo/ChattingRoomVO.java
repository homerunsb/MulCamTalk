package com.mc.mctalk.vo;

public class ChattingRoomVO {
	private int roomID = 0;
	private ChattingUserVO[] member;

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	
	public ChattingUserVO[] getMember() {
		return member;
	}

	public void setMember(ChattingUserVO[] member) {
		this.member = member;
	}
}

package com.mc.mctalk.vo;

public class ChattingRoomVO {
	private int roomID = 0;
	private ChattingMemberVO[] member;

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	
	public ChattingMemberVO[] getMember() {
		return member;
	}

	public void setMember(ChattingMemberVO[] member) {
		this.member = member;
	}
}

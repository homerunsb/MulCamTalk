package com.mc.mctalk.vo;

public class MessageVO {
	ChattingRoomVO roomVO;
	String sendUserID, message, sendTime;

	public ChattingRoomVO getRoomVO() {
		return roomVO;
	}

	public void setRoomVO(ChattingRoomVO roomVO) {
		this.roomVO = roomVO;
	}

	public String getSendUserID() {
		return sendUserID;
	}

	public void setSendUserID(String sendUserID) {
		this.sendUserID = sendUserID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "MessageVO [roomVO=" + roomVO + ", sendUserID=" + sendUserID + ", message=" + message + ", sendTime="
				+ sendTime + "]";
	}
}

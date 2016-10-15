package com.mc.mctalk.vo;

public class MessageVO {
	ChattingRoomVO roomVO;
	String sendUserID, sendUserName, message, sendTime, messageID ;

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

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

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	@Override
	public String toString() {
		return "MessageVO [roomVO=" + roomVO + ", sendUserID=" + sendUserID + ", sendUserName=" + sendUserName
				+ ", message=" + message + ", sendTime=" + sendTime + "]";
	}
}

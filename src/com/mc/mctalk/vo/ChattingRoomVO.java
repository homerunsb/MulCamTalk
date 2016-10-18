package com.mc.mctalk.vo;

import java.util.ArrayList;
import java.util.List;

public class ChattingRoomVO {
	private String chattingRoomID = null, chattingRoomName = null;
	private int userCount = 0;
	private String lastMsgContent = null, lasMsgSendTime = null;
	private int unReadMsgCount = 0;
	private List<String> chattingRoomUserIDs =  new ArrayList<String>();
	private String imgPath = null;
	
	public String getChattingRoomID() {
		return chattingRoomID;
	}
	public void setChattingRoomID(String chattingRoomID) {
		this.chattingRoomID = chattingRoomID;
	}
	public String getChattingRoomName() {
		return chattingRoomName;
	}
	public void setChattingRoomName(String chattingRoomName) {
		this.chattingRoomName = chattingRoomName;
	}
	public List<String> getChattingRoomUserIDs() {
		return chattingRoomUserIDs;
	}
	public void setChattingRoomUserIDs(List<String> chattingRoomUserIDs) {
		this.chattingRoomUserIDs = chattingRoomUserIDs;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public String getLastMsgContent() {
		return lastMsgContent;
	}
	public void setLastMsgContent(String lastMsgContent) {
		this.lastMsgContent = lastMsgContent;
	}
	public String getLasMsgSendTime() {
		return lasMsgSendTime;
	}
	public void setLasMsgSendTime(String lasMsgSendTime) {
		this.lasMsgSendTime = lasMsgSendTime;
	}
	public int getUnReadMsgCount() {
		return unReadMsgCount;
	}
	public void setUnReadMsgCount(int unReadMsgCount) {
		this.unReadMsgCount = unReadMsgCount;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	@Override
	public String toString() {
		return "ChattingRoomVO [chattingRoomID=" + chattingRoomID + ", chattingRoomName=" + chattingRoomName
				+ ", userCount=" + userCount + ", lastMsgContent=" + lastMsgContent + ", lasMsgSendTime="
				+ lasMsgSendTime + ", unReadMsgCount=" + unReadMsgCount + ", chattingRoomUserIDs=" + chattingRoomUserIDs
				+ ", imgPath=" + imgPath + "]";
	}
}

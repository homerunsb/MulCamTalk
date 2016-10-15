package com.mc.mctalk.vo;

import java.util.ArrayList;
import java.util.List;

public class ChattingRoomVO {
	private String chattingRoomID = null, chattingRoomName = null;
	private List<String> chattingRoomUserIDs =  new ArrayList<String>();
	
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
	@Override
	public String toString() {
		return "ChattingRoomVO [chattingRoomID=" + chattingRoomID + ", chattingRoomName=" + chattingRoomName
				+ ", chattingRoomUserIDs=" + chattingRoomUserIDs + "]";
	}
	
}

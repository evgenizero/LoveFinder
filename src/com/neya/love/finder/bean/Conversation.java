package com.neya.love.finder.bean;

public class Conversation {
	private String userName;
	private int userId;
	
	public Conversation() {
		
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public boolean equals(Object obj) {
		Conversation conversation = (Conversation) obj;
		return userId == conversation.getUserId();
	}
}

package com.neya.love.finder.bean;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Message {

	private String message;
	private String date;
	private int messageSenderId;
	private int messageReceiverId;

	public Message(String message, String date, int messageSenderId,
			int messageReceiverId) {
		this.message = message;
		this.messageSenderId = messageSenderId;
		this.messageReceiverId = messageReceiverId;
		this.date = date;
	}

	public Message() {
		
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getMessageSenderId() {
		return messageSenderId;
	}
	
	public void setMessageSenderId(int messageSenderid) {
		this.messageSenderId = messageSenderid;
	}
	
	public int getMessageReceiverId() {
		return messageReceiverId;
	}
	
	public void setMessageReceiverid(int messageReceiverId) {
		this.messageReceiverId = messageReceiverId;
	}
}

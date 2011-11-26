package com.neya.love.finder.bean;

public class Message {

	private String message;
	private Long date;
	private int messageSender;
	private int messageReceiver;

	public Message(String message, Long date, int messageSender,
			int messageReceiver) {
		this.message = message;
		this.messageSender = messageSender;
		this.messageReceiver = messageReceiver;
		this.date = date;
	}

	public String getMessage() {
		return message;
	}
	
	public Long getDate() {
		return date;
	}
	
	public int getMessageSenderId() {
		return messageSender;
	}
	
	public int getMessageReceiverId() {
		return messageReceiver;
	}
}

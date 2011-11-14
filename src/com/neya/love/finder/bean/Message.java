package com.neya.love.finder.bean;

public class Message {

	private String message;
	private String date;
	private CustomerData messageSender;
	private CustomerData messageReceiver;

	public Message(String message, Long date, CustomerData messageSender,
			CustomerData messageReceiver) {
		this.message = message;
		this.messageSender = messageSender;
		this.messageReceiver = messageReceiver;
		this.date = convertDate(date);
	}
	
	private String convertDate(Long date) {
		//TODO convert date into String
		return null;
	}

	public String getMessage() {
		return message;
	}
	
	public String getDate() {
		return date;
	}
	
	public CustomerData getMessageSender() {
		return messageSender;
	}
	
	public CustomerData getMessageReceiver() {
		return messageReceiver;
	}
}

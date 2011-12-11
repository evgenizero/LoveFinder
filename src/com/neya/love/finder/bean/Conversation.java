package com.neya.love.finder.bean;

import java.util.List;

public class Conversation {
	private List<Message> messageList;
	private CustomerData firstUser;
	private CustomerData secondUser;
	private ConversationDBLoader conversationLoader;

	public Conversation(CustomerData firstUser, CustomerData secondUser) {
		conversationLoader = new ConversationDBLoader();

		this.firstUser = firstUser;
		this.secondUser = secondUser;
		this.messageList = conversationLoader.loadMessages(firstUser, secondUser);
	}
	
	public List<Message> getMessageList() {
		return messageList;
	}
	
	public CustomerData getFirstUser() {
		return firstUser;
	}
	
	public CustomerData getSecondUser() {
		return secondUser;
	}
}

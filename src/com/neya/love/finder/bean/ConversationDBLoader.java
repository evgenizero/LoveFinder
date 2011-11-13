package com.neya.love.finder.bean;

import java.util.ArrayList;
import java.util.List;

public class ConversationDBLoader {

	// TODO private fields for the database
	private List<Message> messageList = new ArrayList<Message>();
	private Message message;
	
	public List<Message> loadMessages(CustomerData firstUser,
			CustomerData secondUser) {
		
		//TODO some sql query for getting the messages
		
		//pseudo code
		/*
		 * while(database.hasNextMessage()) {
		 * 		message = new Message(text, sender, receiver);
		 * 		messageList.add(message);
		 * }
		 */
		
		return messageList;
	}
}

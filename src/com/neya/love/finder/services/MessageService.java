/**
 * Interface to the messaging service
 * @author Evgeni Yanev
 * @email evgenizero@gmail.com
 * @date 11 Dec 2011
 * 
 */
package com.neya.love.finder.services;

import java.sql.SQLException;
import java.util.List;

import com.neya.love.finder.bean.Message;

/**
 * @author Evgeni Yanev
 *
 */
public interface MessageService {
	public boolean addMessage(Message message) throws SQLException;
	public List<Message> findMessagesByDate(long date, int customerId) throws SQLException;
	public List<Message> findMessagesBySenderReceiver(int senderId, int receiverId) throws SQLException;
}

/**
 * Tests message persisting
 * @author Evgeni Yanev
 * @email evgenizero@gmail.com
 * @date 11 Dec 2011
 */

package com.neya.love.finder.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
 
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.neya.love.finder.bean.Message;
import com.neya.love.finder.db.MessagePersistor;
import com.neya.love.finder.utils.DBTables;
 
public class MessagePersistorTest {
 
	private final static String addMessageSql = "INSERT INTO " + DBTables.MESSAGE_TABLE
			+ " (message, date, senderId, receiverId) VALUES (?,?,?,?)";
	
	private final static String selectMessageByDateSql = "SELECT message, date, senderId, receiverId FROM "
			+ DBTables.MESSAGE_TABLE + ", " + DBTables.CUSTOMER_TABLE
			+ " WHERE  date = ? AND " + DBTables.CUSTOMER_TABLE
			+ ".id = ?";
	
	private final static String selectMessageBySenderReceiverSql = "SELECT message, date, senderId, receiverId FROM "
			+ DBTables.MESSAGE_TABLE
			+ " WHERE  senderId = ? AND receiverId = ?";
	
	/**
	 * 
	 * @param conn
	 * @param stmt
	 * @param messagePersistor
	 * @return true if the persistor succeeds
	 * @throws SQLException
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 */
	
	public boolean addMessage(Connection conn, PreparedStatement stmt, MessagePersistor messagePersistor) throws SQLException {
    	Message message = new Message("hello", 123l, 5, 6);
	
    	messagePersistor.setConnection(conn);
    	when(conn.prepareStatement(addMessageSql)).thenReturn(stmt);
    	when(stmt.executeUpdate()).thenReturn(0);
    	return messagePersistor.addMessage(message);
	}
	
	/**
	 * JUnit test to check if adding messages into the DB is correct
	 * @throws SQLException
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 */
	
    @Test
    public void testAddMessage() throws SQLException {
    	
    	MessagePersistor messagePersistor = MessagePersistor.getInstance();
    	
    	Connection conn = mock(Connection.class);
    	PreparedStatement stmt = mock(PreparedStatement.class);
    	
    	assertTrue(addMessage(conn, stmt, messagePersistor));
    }
    
    /**
     * JUnit test to check if finding messages from the DB by Date is correct
     * @throws SQLException
     * 
     * @author Evgeni Yanev
     * @email evgenizero@gmail.com
     */
    
    @Test
    public void testFindMessageByDate() throws SQLException {
    	
    	MessagePersistor messagePersistor = MessagePersistor.getInstance();
    	
    	Connection conn = mock(Connection.class);
    	PreparedStatement stmt = mock(PreparedStatement.class);
    	
    	addMessage(conn, stmt, messagePersistor);
    	
    	ResultSet rs = mock(ResultSet.class);
    	
    	when(conn.prepareStatement(selectMessageByDateSql)).thenReturn(stmt);
    	when(stmt.executeQuery()).thenReturn(rs);
    	
    	List<Message> messagesList = messagePersistor.findMessagesByDate(123l, 5);
    	assertEquals(1, messagesList.size());
    }
    
    /**
     * JUnit test to check if finding messages from the DB by sender/receicer is correct
     * @throws SQLException
     * 
     * @author Evgeni Yanev
     * @email evgenizero@gmail.com
     */
    
    @Test
    public void testFindMessageBySenderReceiver() throws SQLException {
    	MessagePersistor messagePersistor = MessagePersistor.getInstance();
    	
    	Connection conn = mock(Connection.class);
    	PreparedStatement stmt = mock(PreparedStatement.class);
    	
    	addMessage(conn, stmt, messagePersistor);
    	
    	ResultSet rs = mock(ResultSet.class);
    	
    	when(conn.prepareStatement(selectMessageBySenderReceiverSql)).thenReturn(stmt);
    	when(stmt.executeQuery()).thenReturn(rs);
    	
    	List<Message> messagesList = messagePersistor.findMessagesBySenderReceiver(5, 6);
    	assertEquals(1, messagesList.size());
    }
    
}

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
	 * JUnit test to check if adding messages into the DB is correct
	 * @throws SQLException
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 */
	
    @Test
    public void testAddMessage() throws SQLException {
    	
    	
    	Connection conn = mock(Connection.class);
    	PreparedStatement stmt = mock(PreparedStatement.class);
    	
    	MessagePersistor messagePersistor = new MessagePersistor(conn);
    	Message message = new Message("hello", "Apr 12", 5, 6);
    	
    	when(conn.prepareStatement(addMessageSql)).thenReturn(stmt);
    	when(stmt.executeUpdate()).thenReturn(1);
    	
    	assertTrue(messagePersistor.addMessage(message));
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
    	
    	Connection conn = mock(Connection.class);
    	PreparedStatement stmt = mock(PreparedStatement.class);
    	
    	MessagePersistor messagePersistor = new MessagePersistor(conn);
    	
    	ResultSet rs = mock(ResultSet.class);
    	
    	when(conn.prepareStatement(selectMessageByDateSql)).thenReturn(stmt);
    	when(stmt.executeQuery()).thenReturn(rs);
    	
    	when(rs.next()).thenReturn(true, false);
    	
    	when(rs.getString(1)).thenReturn("Evgeni");
    	when(rs.getLong(2)).thenReturn(123l);
    	when(rs.getInt(3)).thenReturn(5);
    	when(rs.getInt(4)).thenReturn(6);
    	
    	List<Message> messagesList = messagePersistor.findMessagesByDate("Apr 12", 5);
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
    	
    	Connection conn = mock(Connection.class);
    	PreparedStatement stmt = mock(PreparedStatement.class);
    	
    	MessagePersistor messagePersistor = new MessagePersistor(conn);
    	
    	ResultSet rs = mock(ResultSet.class);
    	
    	when(conn.prepareStatement(selectMessageBySenderReceiverSql)).thenReturn(stmt);
    	when(stmt.executeQuery()).thenReturn(rs);
    	
    	when(rs.next()).thenReturn(true, false);
    	
    	when(rs.getString(1)).thenReturn("Evgeni");
    	when(rs.getLong(2)).thenReturn(123l);
    	when(rs.getInt(3)).thenReturn(5);
    	when(rs.getInt(4)).thenReturn(6);
    	
    	List<Message> messagesList = messagePersistor.findMessagesBySenderReceiver(5, 6);
    	assertEquals(1, messagesList.size());
    }
    
}

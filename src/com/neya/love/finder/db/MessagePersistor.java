/**
 * Persists messages into the DB
 * @author Evgeni Yanev
 * @email evgenizero@gmail.com
 * @date 26 Nov 2011
 */

package com.neya.love.finder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neya.love.finder.bean.Message;
import com.neya.love.finder.services.MessageService;
import com.neya.love.finder.utils.DBTables;

public class MessagePersistor implements MessageService {
	// private final static String TABLE = "message";
	private static MessagePersistor singelton;

	private Connection conn;
	
	/**
	 * Persist a message to the DB
	 * 
	 * @param message
	 * @return true if message persist is successful
	 * @throws SQLException
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 */

	@Override
	public boolean addMessage(Message message) throws SQLException {
		PreparedStatement stmt = null;
		//DBManager dbManager = new DBManager();

		try {
			String sql = "INSERT INTO " + DBTables.MESSAGE_TABLE
					+ " (message, date, senderId, receiverId) VALUES (?,?,?,?)";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, message.getMessage());
			stmt.setLong(2, message.getDate());
			stmt.setInt(3, message.getMessageSenderId());
			stmt.setInt(4, message.getMessageReceiverId());
			
			if (stmt.executeUpdate() == 1) {
				closeConnection(conn, stmt);

				return true;
			} else {
				closeConnection(conn, stmt);

				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, stmt);
		}
		return false;
	}

	/**
	 * Find message by date
	 * 
	 * @param date
	 * @return list of messages of that day
	 * @throws SQLException
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 * 
	 */

	@Override
	public List<Message> findMessagesByDate(long date, int customerId)
			throws SQLException {
		
		PreparedStatement stmt = null;
		Message message = null;

		List<Message> messageList = new ArrayList<Message>();

		try {
			String sql = "SELECT message, date, senderId, receiverId FROM "
					+ DBTables.MESSAGE_TABLE + ", " + DBTables.CUSTOMER_TABLE
					+ " WHERE  date = ? AND " + DBTables.CUSTOMER_TABLE
					+ ".id = ?";

			stmt = conn.prepareStatement(sql);

			stmt.setLong(1, date);
			stmt.setInt(2, customerId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				message = new Message(rs.getString(1), rs.getLong(2),
						rs.getInt(3), rs.getInt(4));
				messageList.add(message);
			}
			return messageList;
		} finally {
			closeConnection(conn, stmt);
		}
	}

	/**
	 * Find message by sender and receiver
	 * 
	 * @param senderId
	 *            , receiverId
	 * @return list of messages between the sender and the receiver
	 * @throws SQLException
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 * 
	 */

	@Override
	public List<Message> findMessagesBySenderReceiver(int senderId,
			int receiverId) throws SQLException {
		
		PreparedStatement stmt = null;
		Message message = null;

		List<Message> messageList = new ArrayList<Message>();

		try {
			String sql = "SELECT message, date, senderId, receiverId FROM "
					+ DBTables.MESSAGE_TABLE
					+ " WHERE  senderId = ? AND receiverId = ?";

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, senderId);
			stmt.setInt(2, receiverId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				message = new Message(rs.getString(1), rs.getLong(2),
						rs.getInt(3), rs.getInt(4));
				messageList.add(message);
			}
			return messageList;
		} finally {
			closeConnection(conn, stmt);
		}
	}

	
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * Default constructor is private so there would be only one instance of
	 * that class
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 */
	private MessagePersistor() {
//		try {
//			this.conn = DBManager.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Create new instance of MessagePersistor if no instance is created
	 * 
	 * @return instance of MessagePersistor
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 */
	public static synchronized MessagePersistor getInstance() {
		if (singelton == null) {
			singelton = new MessagePersistor();
		}

		return singelton;
	}

	/**
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 */
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/**
	 * @param conn
	 * @param stmt
	 * @throws SQLException
	 * 
	 * @author Evgeni Yanev
	 * @email yanev93@gmail.com
	 */
	private static void closeConnection(Connection conn, PreparedStatement stmt)
			throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}
}

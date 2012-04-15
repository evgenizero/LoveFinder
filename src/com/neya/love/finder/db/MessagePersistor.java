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

import com.neya.love.finder.bean.Conversation;
import com.neya.love.finder.bean.Message;
import com.neya.love.finder.services.MessageService;
import com.neya.love.finder.utils.DBTables;
import com.neya.love.finder.utils.net.DBManager;
import com.neya.love.finder.utils.net.Date;

public class MessagePersistor implements MessageService {
	private Connection conn;

	/**
	 * Checks for the available connection
	 * 
	 * @return void
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 * 
	 * @throws SQLException
	 */

	private void checkAvailableConnection() throws SQLException {
		if (conn == null) {
			DBManager manager = new DBManager();
			conn = DBManager.getConnection();
		}
	}

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
	public boolean addMessage(Message message) {
		PreparedStatement stmt = null;

		try {
			checkAvailableConnection();

			String sql = "INSERT INTO " + DBTables.MESSAGE_TABLE
					+ " (message, date, senderId, receiverId) VALUES (?,?,?,?)";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, message.getMessage());
			stmt.setString(2, Date.now());
			stmt.setInt(3, message.getMessageSenderId());
			stmt.setInt(4, message.getMessageReceiverId());

			if (stmt.executeUpdate() == 1) {
				return true;
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
	public List<Message> findMessagesByDate(String date, int customerId) {

		PreparedStatement stmt = null;
		Message message = null;

		List<Message> messageList = new ArrayList<Message>();

		try {
			checkAvailableConnection();

			String sql = "SELECT message, date, senderId, receiverId FROM "
					+ DBTables.MESSAGE_TABLE + ", " + DBTables.CUSTOMER_TABLE
					+ " WHERE  date = ? AND " + DBTables.CUSTOMER_TABLE
					+ ".id = ?";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, date);
			stmt.setInt(2, customerId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				message = new Message(rs.getString(1), rs.getString(2),
						rs.getInt(3), rs.getInt(4));
				messageList.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, stmt);
		}
		return messageList;
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
			int receiverId) {

		PreparedStatement stmt = null;
		Message message = null;

		List<Message> messageList = new ArrayList<Message>();

		try {
			checkAvailableConnection();

			String sql = "SELECT senderId, receiverId, message, date FROM "
					+ DBTables.MESSAGE_TABLE
					+ " WHERE  senderId = ? AND receiverId = ? or senderId=? and receiverId=?";

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, senderId);
			stmt.setInt(2, receiverId);
			stmt.setInt(3, receiverId);
			stmt.setInt(4, senderId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				message = new Message(rs.getString(3), rs.getString(4),
						rs.getInt(1), rs.getInt(2));
				messageList.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, stmt);
		}
		return messageList;
	}

	public List<Conversation> getConversations(int receiverId) {
		PreparedStatement stmt = null;
		Conversation conversation = null;

		CustomerPersistor customerPersistor = new CustomerPersistor(conn);

		List<Conversation> conversationList = new ArrayList<Conversation>();

		try {
			checkAvailableConnection();

			String sql = "SELECT distinct senderId, receiverId FROM "
					+ DBTables.MESSAGE_TABLE
					+ " WHERE  receiverId = ? or senderId = ?";

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, receiverId);
			stmt.setInt(2, receiverId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				conversation = new Conversation();
				if (receiverId == rs.getInt(1)) {
					conversation.setUserId(rs.getInt(2));
					conversation.setUserName(customerPersistor
							.getCustomerName(rs.getInt(2)));
				} else {
					conversation.setUserId(rs.getInt(1));
					conversation.setUserName(customerPersistor
							.getCustomerName(rs.getInt(1)));
				}
				
				if(!conversationList.contains(conversation)) {
					conversationList.add(conversation);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, stmt);
		}

		return conversationList;
	}

	@SuppressWarnings("static-access")
	public MessagePersistor(Connection connection) {
		try {
			if (connection == null) {
				DBManager dbManager = new DBManager();
				this.conn = dbManager.getConnection();
			} else {
				this.conn = connection;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Default constructor is private so there would be only one instance of
	 * that class
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 */
	@SuppressWarnings("static-access")
	public MessagePersistor() {
		DBManager dbManager = new DBManager();
		try {
			this.conn = dbManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param conn
	 * @param stmt
	 * @throws SQLException
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 */
	private static void closeConnection(Connection conn, PreparedStatement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

/**
 * Make DB connection
 * @author Evgeni Yanev
 * @email evgenizero93@gmail.com
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
import com.neya.love.finder.util.net.DBManager;

public class MessagePersistor implements MessageService {
	private final static String TABLE = "message";
	private static MessagePersistor singelton;

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
		Connection conn = null;
		PreparedStatement stmt = null;
		DBManager dbManager = new DBManager();

		try {
			String sql = "INSERT INTO " + TABLE + " (message, date, senderId, receiverId) VALUES (?,?,?,?)";

			conn = dbManager.getConnection();
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
	public List<Message> findMessagesByDate(long date) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		Message message = null;

		List<Message> messageList = new ArrayList<Message>();

		try {
			String sql = "SELECT message, date, senderId, receiverId FROM "
					+ TABLE + " WHERE  date = ?";

			conn = DBManager.getConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setLong(1, date);

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
		Connection conn = null;
		PreparedStatement stmt = null;
		Message message = null;

		List<Message> messageList = new ArrayList<Message>();

		try {
			String sql = "SELECT message, date, senderId, receiverId FROM "
					+ TABLE + " WHERE  senderId = ? AND receiverId = ?";

			conn = DBManager.getConnection();
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

	/**
	 * Default constructor is private so there would be only one instance of
	 * that class
	 * 
	 * @author Evgeni Yanev
	 * @email evgenizero@gmail.com
	 */
	private MessagePersistor() {}

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

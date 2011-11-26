/**
 * Make DB connection
 * @author Nikolay Yanev
 * @email yanev93@gmail.com
 * @date 15 Nov 2011
 */

package com.neya.love.finder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.neya.love.finder.bean.CustomerData;
import com.neya.love.finder.services.CustomerService;
import com.neya.love.finder.util.net.DBManager;
import com.neya.love.finder.utils.StringUtil;

public class CustomerPersistor implements CustomerService {
	private final static String TABLE = "customer";
	private static CustomerPersistor singelton;

	
	/**
	 * Persist customer data to DB
	 * 
	 * @param customer
	 * @return true if customer persist is successful
	 * @throws SQLException
	 * 
	 * @author Nikolay Yanev
	 * @email yanev93@gmail.com
	 */
	@SuppressWarnings("static-access")
	public boolean addCustomer(CustomerData customer)
			throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		DBManager dbManager = new DBManager();

		try {
			String sql = "INSERT INTO (status, username, password, email, age, country, city, is_hidden) "
					+ TABLE + " (?,?,?,?,?,?,?,?)";

			conn = dbManager.getConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, customer.getStatus());
			stmt.setString(2, customer.getUsername());
			stmt.setString(3, StringUtil.md5(customer.getPassword()));
			stmt.setString(4, customer.getEmail());
			stmt.setInt(5, customer.getAge());
			stmt.setInt(6, (short) 1); // TODO use customer.getCountry (should
										// convert to short from String)
			stmt.setInt(7, (short) 1); // TODO use customer.getCity (should
										// convert to short from String)
			stmt.setInt(8, customer.getIsHidden());

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
	 * Find customers
	 * 
	 * @param customerId
	 *            id of the customer
	 * @return the founded customer
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 * @email yanev93@gmail.com
	 */
	public CustomerData findById(int customerId) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		CustomerData customer = null;

		try {
			String sql = "SELECT id, status, username, email, age, country, city, is_hidden FROM "
					+ TABLE + " WHERE  id = ?";

			conn = DBManager.getConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, customerId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				customer = new CustomerData(rs.getInt(1), rs.getInt(2),
						rs.getString(3), null, rs.getString(4), rs.getInt(5),
						String.valueOf(rs.getInt(6)), String.valueOf(rs
								.getInt(7)), 0, rs.getInt(8));
			}

			return customer;
		} finally {
			closeConnection(conn, stmt);
		}
	}
	
	/**
	 * Find customers
	 * 
	 * @param username
	 *            username of the customer
	 * @return the founded customer
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 * @email yanev93@gmail.com
	 */
	public CustomerData findByUsername(String username) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		CustomerData customer = null;

		try {
			String sql = "SELECT id, status, username, email, age, country, city, is_hidden FROM "
					+ TABLE + " WHERE  username = ?";

			conn = DBManager.getConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				customer = new CustomerData(rs.getInt(1), rs.getInt(2),
						rs.getString(3), null, rs.getString(4), rs.getInt(5),
						String.valueOf(rs.getInt(6)), String.valueOf(rs
								.getInt(7)), 0, rs.getInt(8));
			}

			return customer;
		} finally {
			closeConnection(conn, stmt);
		}
	}

	/**
	 * Default constructor
	 * 
	 * @author Nikolay Yanev
	 * @email yanev93@gmail.com
	 */
	private CustomerPersistor() {
		
	}
	
	/**
	 * Create new instance of CustomerPersistor
	 * if no instance is created
	 * @return instance of CustomerPersistor
	 * 
	 * @author Nikolay Yanev
	 * @email yanev93@gmail.com
	 */
	public static synchronized CustomerPersistor getInstance() {
		if(singelton == null) {
			singelton = new CustomerPersistor();
		}
		
		return singelton;
	}
	
	/**
	 * @author Nikolay Yanev
	 * @email yanev93@gmail.com
	 */
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * @param conn
	 * @param stmt
	 * @throws SQLException
	 * 
	 * @author Nikolay Yanev
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

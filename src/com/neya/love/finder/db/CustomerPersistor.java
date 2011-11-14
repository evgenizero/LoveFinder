/**
 * Make connection to DB
 * 
 */
package com.neya.love.finder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.neya.love.finder.bean.CustomerData;
import com.neya.love.finder.util.net.DBManager;
import com.neya.love.finder.utils.StringUtil;

public class CustomerPersistor {
	private final static String TABLE = "customer";
	
	/**
	 * Persist customer data to DB
	 * @param customer
	 * @throws SQLException 
	 */
	@SuppressWarnings("static-access")
	public static void addCustomer(CustomerData customer) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		DBManager dbManager = new DBManager();
		
		try {
			String sql = "INSERT INTO (status, username, password, email, age, country, city, is_hidden) " + TABLE +" (?,?,?,?,?,?,?,?)";
			
			conn = dbManager.getConnection();
			stmt = conn.prepareStatement(sql);
			
			stmt.setShort(1, customer.getStatus());
			stmt.setString(2, customer.getUsername());
			stmt.setString(3, StringUtil.md5(customer.getPassword()));
			stmt.setString(4, customer.getEmail());
			stmt.setShort(5, customer.getAge());
			stmt.setShort(6, (short) 1); //TODO use customer.getCountry (should convert to short from String)
			stmt.setShort(7, (short) 1); //TODO use customer.getCity (should convert to short from String)
			stmt.setShort(8, customer.getIsHidden());
			
			stmt.executeUpdate();
		} finally {
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
	}
}

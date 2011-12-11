/**
 * Tests for customer persistor
 * @author Nikolay Yanev
 * @email yanev93@gmail.com
 * @date 15 Nov 2011
 */
package com.neya.love.finder.db;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.neya.love.finder.bean.CustomerData;
import com.neya.love.finder.utils.DBTables;

/**
 * @author Nikolay Yanev
 * @email yanev93@gmail.com
 * @date 15 Nov 2011
 */
public class CustomerPersistorTest {

	/**
	 * Test add customer to db
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 * @email yanev93@gmail.com
	 * @date 15 Nov 2011
	 */
	@Test
	public void testAddCustomer() throws SQLException {
		String sql = "INSERT INTO "
				+ DBTables.CUSTOMER_TABLE
				+ " (status, username, password, email, age, country, city, is_hidden) "
				+ "VALUES (?,?,?,?,?,?,?,?)";

		CustomerData customer = new CustomerData(0, 1, "liana", "liainiki",
				"liana1533@gmail.com", 18, "1", "1", 0, 1);

		Connection conn = mock(Connection.class);
		PreparedStatement stmt = mock(PreparedStatement.class);

		CustomerPersistor persistor = new CustomerPersistor(conn);

		when(conn.prepareStatement(sql)).thenReturn(stmt);
		when(stmt.executeUpdate()).thenReturn(1);

		assertTrue(persistor.addCustomer(customer));
	}

	/**
	 * Test returned customer is not null
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 * @email yanev93@gmail.com
	 * @date 15 Nov 2011
	 */
	@Test
	public void tesFindByIdIsNotNull() throws SQLException {
		String sql = "SELECT id, status, username, email, age, country, city, is_hidden FROM "
				+ DBTables.CUSTOMER_TABLE + " WHERE  id = ?";

		Connection conn = mock(Connection.class);
		PreparedStatement stmt = mock(PreparedStatement.class);

		CustomerPersistor persistor = new CustomerPersistor(conn);

		ResultSet rs = mock(ResultSet.class);

		when(conn.prepareStatement(sql)).thenReturn(stmt);
		when(stmt.executeQuery()).thenReturn(rs);

		when(rs.next()).thenReturn(true);
		when(rs.getInt(1)).thenReturn(1);
		when(rs.getInt(2)).thenReturn(1);
		when(rs.getString(3)).thenReturn("liana");
		when(rs.getString(4)).thenReturn("liana1533@gmail.com");
		when(rs.getInt(5)).thenReturn(18);
		when(rs.getInt(6)).thenReturn(1);
		when(rs.getInt(7)).thenReturn(1);
		when(rs.getInt(8)).thenReturn(1);

		assertNotNull(persistor.findById(1));
	}

	/**
	 * Test findById() return rightCustomer
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 * @email yanev93@gmail.com
	 * @date 15 Nov 2011
	 */
	@Test
	public void testFindByIdReturnRightCustomer() throws SQLException {
		String sql = "SELECT id, status, username, email, age, country, city, is_hidden FROM "
				+ DBTables.CUSTOMER_TABLE + " WHERE  id = ?";
		
		CustomerData customer = new CustomerData(0, 1, "liana", "liainiki",
				"liana1533@gmail.com", 18, "1", "1", 0, 1);

		Connection conn = mock(Connection.class);
		PreparedStatement stmt = mock(PreparedStatement.class);

		CustomerPersistor persistor = new CustomerPersistor(conn);

		ResultSet rs = mock(ResultSet.class);

		when(conn.prepareStatement(sql)).thenReturn(stmt);
		when(stmt.executeQuery()).thenReturn(rs);

		when(rs.next()).thenReturn(true);
		when(rs.getInt(1)).thenReturn(1);
		when(rs.getInt(2)).thenReturn(1);
		when(rs.getString(3)).thenReturn("liana");
		when(rs.getString(4)).thenReturn("liana1533@gmail.com");
		when(rs.getInt(5)).thenReturn(18);
		when(rs.getInt(6)).thenReturn(1);
		when(rs.getInt(7)).thenReturn(1);
		when(rs.getInt(8)).thenReturn(1);

		assertEquals(customer.getUsername(), persistor.findById(1).getUsername());
	}

	/**
	 * Test returned customer is not null
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 * @email yanev93@gmail.com
	 * @date 15 Nov 2011
	 */
	@Test
	public void tesFindByUsernameIsNotNull() throws SQLException {
		String sql = "SELECT id, status, username, email, age, country, city, is_hidden FROM "
				+ DBTables.CUSTOMER_TABLE + " WHERE  username = ?";

		Connection conn = mock(Connection.class);
		PreparedStatement stmt = mock(PreparedStatement.class);

		CustomerPersistor persistor = new CustomerPersistor(conn);

		ResultSet rs = mock(ResultSet.class);

		when(conn.prepareStatement(sql)).thenReturn(stmt);
		when(stmt.executeQuery()).thenReturn(rs);

		when(rs.next()).thenReturn(true);
		when(rs.getInt(1)).thenReturn(1);
		when(rs.getInt(2)).thenReturn(1);
		when(rs.getString(3)).thenReturn("liana");
		when(rs.getString(4)).thenReturn("liana1533@gmail.com");
		when(rs.getInt(5)).thenReturn(18);
		when(rs.getInt(6)).thenReturn(1);
		when(rs.getInt(7)).thenReturn(1);
		when(rs.getInt(8)).thenReturn(1);

		// System.out.println("test");
		// System.out.println(persistor.findById(1).getUsername());
		assertNotNull(persistor.findByUsername("liana"));
	}
	
	/**
	 * Test findById() return rightCustomer
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 * @email yanev93@gmail.com
	 * @date 15 Nov 2011
	 */
	@Test
	public void testFindByUsernameReturnRightCustomer() throws SQLException {
		String sql = "SELECT id, status, username, email, age, country, city, is_hidden FROM "
				+ DBTables.CUSTOMER_TABLE + " WHERE  username = ?";
		
		CustomerData customer = new CustomerData(0, 1, "liana", "liainiki",
				"liana1533@gmail.com", 18, "1", "1", 0, 1);

		Connection conn = mock(Connection.class);
		PreparedStatement stmt = mock(PreparedStatement.class);

		CustomerPersistor persistor = new CustomerPersistor(conn);

		ResultSet rs = mock(ResultSet.class);

		when(conn.prepareStatement(sql)).thenReturn(stmt);
		when(stmt.executeQuery()).thenReturn(rs);

		when(rs.next()).thenReturn(true);
		when(rs.getInt(1)).thenReturn(1);
		when(rs.getInt(2)).thenReturn(1);
		when(rs.getString(3)).thenReturn("liana");
		when(rs.getString(4)).thenReturn("liana1533@gmail.com");
		when(rs.getInt(5)).thenReturn(18);
		when(rs.getInt(6)).thenReturn(1);
		when(rs.getInt(7)).thenReturn(1);
		when(rs.getInt(8)).thenReturn(1);

		assertEquals(customer.getUsername(), persistor.findByUsername("liana").getUsername());
	}

	/**
	 * Test if the username is free
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 * @email yanev93@gmail.com
	 * @date 15 Nov 2011
	 */
	@Test
	public void tesIsFreeUsername() throws SQLException {
		String sql = "SELECT id FROM " + DBTables.CUSTOMER_TABLE
				+ " WHERE  username = ?";

		Connection conn = mock(Connection.class);
		PreparedStatement stmt = mock(PreparedStatement.class);

		CustomerPersistor persistor = new CustomerPersistor(conn);

		ResultSet rs = mock(ResultSet.class);

		when(conn.prepareStatement(sql)).thenReturn(stmt);
		when(stmt.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(false);

		assertTrue(persistor.isFreeUsername("niki"));
	}

}

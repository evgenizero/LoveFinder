/**
 * Tests for customer persistor
 * @author Nikolay Yanev
 * @email yanev93@gmail.com
 * @date 15 Nov 2011
 */
package com.neya.love.finder.db;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.mockobjects.jms.MockConnection;
import com.neya.love.finder.bean.CustomerData;

/**
 * @author Nikolay Yanev
 * @email yanev93@gmail.com
 * @date 15 Nov 2011
 */
public class CustomerPersistorTest{ 

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
		CustomerPersistor persistor = CustomerPersistor.getInstance();
		
		final MockConnection mock = new MockConnection();
        mock.setExpectedCloseCalls(0);
        //mock.setupAddPreparedStatement(Mock);
		
		//persistor.setConnection(mock);
		CustomerData customer = new CustomerData(0, 1, "liana",
				"liainiki", "liana1533@gmail.com", 18, "1", "1",
				0, 1);
		assertTrue(persistor.addCustomer(customer));
	}
}

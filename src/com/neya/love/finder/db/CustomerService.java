/**
 * 
 */
package com.neya.love.finder.db;

import java.sql.SQLException;

import com.neya.love.finder.bean.CustomerData;

/**
 * @author nyanev
 *
 */
public interface CustomerService {
	public boolean addCustomer(CustomerData customer) throws SQLException;
	public CustomerData findById(int customerId) throws SQLException;
	public CustomerData findByUsername(String username) throws SQLException;
}

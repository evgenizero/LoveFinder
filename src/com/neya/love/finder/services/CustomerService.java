/**
 * 
 */
package com.neya.love.finder.services;

import java.sql.SQLException;

import com.neya.love.finder.bean.CustomerData;

/**
 * @author nyanev
 *
 */
public interface CustomerService extends IService{
	public boolean addCustomer(CustomerData customer) throws SQLException;
	public CustomerData findById(int customerId) throws SQLException;
	public CustomerData findByUsername(String username) throws SQLException;
}

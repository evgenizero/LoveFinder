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
	public int addCustomer(CustomerData customer) throws SQLException;
	public int logIn(String username, String password) throws SQLException;
	public CustomerData findById(int customerId) throws SQLException;
	public CustomerData findByUsername(String username) throws SQLException;
	public boolean isFreeUsername(String username) throws SQLException;
	public boolean chekForUser(CustomerData customer) throws SQLException;
}

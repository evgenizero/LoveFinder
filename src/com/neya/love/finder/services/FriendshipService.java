package com.neya.love.finder.services;

import java.sql.SQLException;

import com.neya.love.finder.bean.CustomerData;

public interface FriendshipService extends IService {
	public boolean addFrirend(CustomerData customerOne, CustomerData customerTwo) throws SQLException;
	public boolean removeFrined(CustomerData customerOne, CustomerData customerTwo) throws SQLException;
}

/**
 * This class represent customer
 * @author Nikolay Yanev
 * @email yanev93@gmail.com
 * @date 15 Nov 2011
 */
package com.neya.love.finder.bean;

import java.security.InvalidParameterException;

/**
 * @author Nikolay Yanev
 * 
 */
public class CustomerData {
	private int customerId;
	private int status;
	private String username;
	private String password;
	private String email;
	private int age;
	private String country;
	private String city;
	private int aboutMe;
	private int isHidden;

	/**
	 * @param customerId
	 * @param status
	 * @param username
	 * @param email
	 * @param age
	 * @param country
	 * @param city
	 * @param aboutMe
	 * @param isHidden
	 */
	public CustomerData(int customerId, int status, String username,
			String password, String email, int age, String country,
			String city, int aboutMe, int isHidden) {
		super();
		this.customerId = customerId;
		this.status = status;
		this.username = username;
		this.password = password;
		this.email = email;
		this.age = age;
		this.country = country;
		this.city = city;
		this.aboutMe = aboutMe;
		this.isHidden = isHidden;
	}

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(int customerId) {
		if (customerId < 1) {
			throw new InvalidParameterException(
					"Customer id must be greater than 0");
		}
		
		this.customerId = customerId;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		if (status < 0) {
			throw new InvalidParameterException("Status must be greater than 0");
		}
		
		this.status = status;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		if(age < 18 && age > 70) {
			throw new InvalidParameterException("Age of the customer must be betwen 18 and 70");
		}
		this.age = age;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the aboutMe
	 */
	public int getAboutMe() {
		return aboutMe;
	}

	/**
	 * @param aboutMe
	 *            the aboutMe to set
	 */
	public void setAboutMe(int aboutMe) {
		this.aboutMe = aboutMe;
	}

	/**
	 * @return the isHidden
	 */
	public int getIsHidden() {
		return isHidden;
	}

	/**
	 * @param isHidden
	 *            the isHidden to set
	 */
	public void setIsHidden(int isHidden) {
		this.isHidden = isHidden;
	}

}

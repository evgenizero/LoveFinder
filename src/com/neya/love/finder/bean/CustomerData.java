/**
 * This class represent Customer
 * 
 */
package com.neya.love.finder.bean;

/**
 * @author nyanev
 * 
 */
public class CustomerData {
	private int customerId;
	private int status;
	private String username;
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
			String email, int age, String country, String city, int aboutMe,
			int isHidden) {
		super();
		this.customerId = customerId;
		this.status = status;
		this.username = username;
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

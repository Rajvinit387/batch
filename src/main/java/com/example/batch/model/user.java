package com.example.batch.model;

public class user {
	
	
	private long userId;
	
	private String firstname;
	
	private String secondname;
	
	
	private String street;
	
	
	
	private String pincode;

	private String city;
	
	
	private String state_code;


	public user(long userId, String firstname, String secondname, String street, String pincode, String city,
			String state_code) {
		super();
		this.userId = userId;
		this.firstname = firstname;
		this.secondname = secondname;
		this.street = street;
		this.pincode = pincode;
		this.city = city;
		this.state_code = state_code;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getSecondname() {
		return secondname;
	}


	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState_code() {
		return state_code;
	}


	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	
	

}

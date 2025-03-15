package com.ctet.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class User extends Base {

	@NotNull
	@Column(length = 2000)
	String email;
	String mobileNumber;
	String firstName;
	String lastName;
	@NotNull
	String password;

	@Transient
	String rePassword;

	String dateOfBirth;

	@Column(length = 1000)
	String permanentAddress;

	String city;

	String zip;

	String imageUrl;

	@Enumerated(EnumType.STRING)
	private UserType userType = UserType.STUDENT;

	@Transient
	private String type;

	public String getType() {
		return getUserType().getType();
	}

	public void setType(String type) {
		this.type = type;
		setUserType(UserType.valueOf(type));
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", mobileNumber=" + mobileNumber + ", firstName=" + firstName + ", lastName="
				+ lastName + ", password=" + password + ", rePassword=" + rePassword + ", dateOfBirth=" + dateOfBirth
				+ ", permanentAddress=" + permanentAddress + ", city=" + city + ", zip=" + zip + ", imageUrl="
				+ imageUrl + ", userType=" + userType + ", type=" + type + "]";
	}

}
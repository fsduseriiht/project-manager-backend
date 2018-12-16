package com.cts.fsd.projectmanager.pojo;

import org.springframework.stereotype.Component;

/**
 * @author Amitabha Das [420652]
 * UsersPOJO Object that interacts with UI and Controller
 */
@Component
public class UserPOJO {
	
	private int userId;

	private String firstName;

	private String lastName;

	private String employeeId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "UserPOJO [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", employeeId="
				+ employeeId + "]";
	}

}

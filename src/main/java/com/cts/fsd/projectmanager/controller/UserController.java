package com.cts.fsd.projectmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fsd.projectmanager.pojo.UserPOJO;
import com.cts.fsd.projectmanager.service.ParentTaskService;
import com.cts.fsd.projectmanager.service.ProjectService;
import com.cts.fsd.projectmanager.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Amitabha Das [420652]
 *
 */
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	ParentTaskService parentTaskService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;

	/**
	 * createProjectDump() is used to create a data dump in the db if the db is empty
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/dump", method = RequestMethod.GET)
	public ResponseEntity<String> createUserDump() {
		
		Gson gson = new Gson();
		String userDump = "[ "
				+ "{\"firstName\":\"test_firstName1\",\"lastName\":\"test_lastName1\",\"employeeId\":\"test_employeeId1\"},"
				+ "{\"firstName\":\"test_firstName2\",\"lastName\":\"test_lastName2\",\"employeeId\":\"test_employeeId2\"},"
				+ "{\"firstName\":\"test_firstName3\",\"lastName\":\"test_lastName3\",\"employeeId\":\"test_employeeId3\"},"
				+ "{\"firstName\":\"test_firstName4\",\"lastName\":\"test_lastName4\",\"employeeId\":\"test_employeeId4\"},"
				+ "{\"firstName\":\"test_firstName5\",\"lastName\":\"test_lastName5\",\"employeeId\":\"test_employeeId5\"},"
				+ "{\"firstName\":\"test_firstName6\",\"lastName\":\"test_lastName6\",\"employeeId\":\"test_employeeId6\"}"
				+ " ]";
		List<UserPOJO> userPOJOList = gson.fromJson(userDump, new TypeToken<List<UserPOJO>>(){}.getType());
		
		// display ParentTask Dump in console
		userPOJOList.forEach((pojo)-> {System.out.println(pojo);});
		System.out.println("creating User dump in db = " + userPOJOList.toString());
		
		List<UserPOJO> dbResponse = userService.createUsers(userPOJOList);
		
		return new ResponseEntity<String>("User Dumps Saved to Database..." + dbResponse , HttpStatus.OK);
	}
	
	
	/**
	 * listUsers() is used to display all the ParentTask Entity values in the db
	 * @return ResponseEntity<List<UserPOJO>>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<UserPOJO>> listUsers() {
		
		System.out.println("getting all the projects from database...");
		
		List<UserPOJO> usersFromDB = userService.getAllUsers();
		
		return new ResponseEntity<List<UserPOJO>>(usersFromDB , HttpStatus.OK);
    }
	
	
	/**
	 * createProject() is used to create a parent task in  the db
	 * @param userPOJO
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> createUser(
							@RequestBody UserPOJO userPOJO	) {
		
		System.out.println("User to be added to DB = " + userPOJO.toString());
		
		List<UserPOJO> userPOJOList = new ArrayList<UserPOJO>();
		userPOJOList.add(userPOJO);
		
		// display User To be Created in console
		userPOJOList.forEach((pojo)-> {System.out.println(pojo);});
		System.out.println("adding new User to db = " + userPOJOList.toString());
		
		List<UserPOJO> dbResponse = userService.createUsers(userPOJOList);
		
		return new ResponseEntity<String>("New User Saved to Database..." + dbResponse , HttpStatus.OK);
	}
	
	
	/**
	 * updateUser() is used to update a project into the db
	 * @param userId
	 * @param userPOJO
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> updateUser( 
			@PathVariable(value = "id") int userId ,
			@RequestBody UserPOJO userPOJO	) {
		
		System.out.println("UserPOJO to be updated which is coming in request = " + userPOJO.toString());
		
		UserPOJO dbResponse = null;
		if(userId == userPOJO.getUserId()) {
			dbResponse = userService.editUserById(userId , userPOJO);
		}
		
		if(dbResponse != null) {
			return new ResponseEntity<String>("User[User_id = "+userId+"] Updated in Database..." + dbResponse , HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User[User_id = "+userId+"] NOT Updated in Database as it does not exist..."  + dbResponse , HttpStatus.OK);
		}
	}
	
	
	/**
	 * deleteUser() is used to delete a project from the db
	 * @param userId
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser( 
			@PathVariable(value = "id") int userId ) {

		boolean dbResponse = userService.removeUserById(userId);
		
		if(dbResponse) {
			return new ResponseEntity<String>("User[User_id = "+userId+"] Deleted from database..." , HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User[User_id = "+userId+"] Not Deleted from database as it does not exist..." , HttpStatus.OK);
		}
	}
	
}

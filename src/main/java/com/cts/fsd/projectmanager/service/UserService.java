package com.cts.fsd.projectmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.fsd.projectmanager.entity.UserEntity;
import com.cts.fsd.projectmanager.exception.ResourceNotFoundException;
import com.cts.fsd.projectmanager.mapper.ApplicationMapperObject;
import com.cts.fsd.projectmanager.pojo.ProjectPOJO;
import com.cts.fsd.projectmanager.pojo.TaskPOJO;
import com.cts.fsd.projectmanager.pojo.UserPOJO;
import com.cts.fsd.projectmanager.repo.TaskRepository;
import com.cts.fsd.projectmanager.repo.UserRepository;

/**
 * @author Amitabha Das [420652]
 * UserService interacts between the controller and the datasources using jpa repository
 */
@Service
public class UserService {
	
	@Autowired
	protected ApplicationMapperObject mapper;
	
	@Autowired
	protected TaskRepository taskRepository;
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected ParentTaskService parentTaskService;
	
	@Autowired
	protected TaskService taskService;
	
	@Autowired
	protected ProjectService projectService;
	
	/**
	 * createUsers() is used to create a task record in the task table sent in the request
	 * @param userPOJOList
	 * @return List<UserPOJO>
	 */
	public List<UserPOJO> createUsers(List<UserPOJO> userPOJOList){
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		List<UserPOJO> returnPojoList = new ArrayList<UserPOJO>();
		
		if (null != userPOJOList && !userPOJOList.isEmpty()) {
			for(UserPOJO userPOJO :  userPOJOList ) {
				
				UserEntity userEntity = mapper.mapUserPojoToEntity(userPOJO);
				
				userEntityList.add(userEntity);
			}
		}
		
		List<UserEntity> dbResponse = userRepository.saveAll(userEntityList);
		
		
		if (null != dbResponse && !dbResponse.isEmpty()) {
			System.out.println("createUsers() dbResponse = " + dbResponse);
			for(UserEntity userEntity :  dbResponse ) {
				UserPOJO userPOJO = mapper.mapUserEntityToPojo(userEntity);
				returnPojoList.add(userPOJO);
			}
		}
		
		return returnPojoList;
	}


	/**
	 * getAllUsers() is used to get all the records in task table 
	 * @return List<UserPOJO>
	 */
	public List<UserPOJO> getAllUsers() {
		
		List<UserEntity> dbResponse = userRepository.findAll();
		System.out.println("getAllUsers() dbResponse = " + dbResponse);
		
		List<UserPOJO> returnPojoList = new ArrayList<UserPOJO>();
		
		if (null != dbResponse && !dbResponse.isEmpty()) {
			for(UserEntity userEntity :  dbResponse ) {
				UserPOJO userPOJO = mapper.mapUserEntityToPojo(userEntity);
				returnPojoList.add(userPOJO);
			}
		}
		return returnPojoList;
	}


	/**
	 * editTaskById() is used to update a task record in db for a particular task id
	 * @param userId
	 * @param userPOJO
	 * @return TaskPOJO
	 */
	public UserPOJO editUserById(int userId, UserPOJO userPOJO) {
		String editResponse = "";
		UserEntity userFromDB = null ;
		UserPOJO returnPOJO = null;
		try {
			userFromDB = getUserById(userId);
			System.out.println("Updating taskFromDB = " + userFromDB.toString());
			
			userFromDB.setFirstName(userPOJO.getFirstName());
			userFromDB.setLastName(userPOJO.getLastName());
			userFromDB.setEmployeeId(userPOJO.getEmployeeId());
			userFromDB =  userRepository.save(userFromDB);
			
			editResponse = "User ID("+userId+") updated, " + userFromDB.toString();
			
			returnPOJO = mapper.mapUserEntityToPojo(userFromDB);
			
		} catch(ResourceNotFoundException e ) {
			System.out.println("ResourceNotFoundException encountered..." + e);
			
			if(e.getResourceName().equals("ParentTaskEntity")) {
				editResponse = "Things are not updated as ParentTaskEntity record does not exist... ";
			} else {
				editResponse = "Things are not updated as record does not exist... ";
			}
			
			userFromDB = null;
			returnPOJO = null;
		} catch(Exception e ) {
			System.out.println("Exception encountered..." + e);
			editResponse = "Things are not updated due to Exception... " + e.getMessage();
			userFromDB = null;
			returnPOJO = null;
		}
		System.out.println("After Task Update :: " + editResponse);
		return returnPOJO;
	}


	/**
	 * getUserById() is used to get a record from the db based on the task id
	 * @param userId
	 * @return UserEntity
	 */
	public UserEntity getUserById(int userId) {
		UserEntity userFromDB = null;
		
		try {
			userFromDB = userRepository.findById(Long.valueOf(userId)).get();
			System.out.println("getUserById successfully returned TaskEntity from DB :: " + userFromDB.toString());
		} catch (NoSuchElementException e) {
			userFromDB = null;
			System.out.println("getParentById NOT successfull...\nNoSuchElementException encountered... ResourceNotFoundException thrown" + e);
			throw new ResourceNotFoundException("TaskEntity" , "taskId" , userId);
		} catch (Exception e ) {
			userFromDB = null;
			System.out.println("Exception encountered..." + e);
		}
		return userFromDB;
	}


	/**
	 * removeUserById() is used to delete a record based on task id from the db
	 * @param userId
	 * @return boolean
	 */
	public boolean removeUserById(int userId) {
		String deleteResponse = "";
		UserEntity userFromDB = null;
		boolean returnResponse = false;
		System.out.println("Before Delete User By Id("+userId+")");
		
		try {
			userFromDB =  getUserById(userId);
			System.out.println("Deleting userFromDB = " + userFromDB.toString());
			
			
			// Update the PROJECT Table With NULL Project ID 
			List<ProjectPOJO> projectPOJOList = projectService.getAllProjects();
			for(ProjectPOJO projectPOJO : projectPOJOList) {
				if(new Long(projectPOJO.getUserId()).equals(Long.valueOf(userFromDB.getUserId()))) {
					projectPOJO.setUserId(-1);
					projectService.editProjectByIdUserDelete(projectPOJO.getProjectId(), projectPOJO);
				}
			}
			
			// Update the TASK Table With NULL Project ID
			List<TaskPOJO> taskFromDBList = taskService.getAllTasks();
			for(TaskPOJO taskPOJO : taskFromDBList) {
				if(new Long(taskPOJO.getUserId()).equals(Long.valueOf(userFromDB.getUserId()))) {
					taskPOJO.setUserId(-1);
					taskService.editTaskByIdUserDelete(taskPOJO.getTaskId(), taskPOJO);
				}
			}
			
			userRepository.deleteUserById(Long.valueOf(userFromDB.getUserId()));
			
			deleteResponse = "User ID("+userId+") Deleted, Record No More exists, corresponding tables are updated...";
			returnResponse = true;
			
		} catch (ResourceNotFoundException e ) {
			System.out.println("ResourceNotFoundException encountered..." + e);
			deleteResponse = "Things are not deleted as record does not exist... ";
			userFromDB = null;
			returnResponse = false;
		} catch (Exception e ) {
			System.out.println("Exception encountered..." + e);
			deleteResponse = "Things are not deleted due to Exception... " + e.getMessage();
			userFromDB = null;
			returnResponse = false;
		}
		
		System.out.println("After Delete :: " + deleteResponse);
		return returnResponse;
	}
}

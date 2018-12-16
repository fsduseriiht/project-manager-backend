package com.cts.fsd.projectmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.fsd.projectmanager.entity.ParentTaskEntity;
import com.cts.fsd.projectmanager.exception.ResourceNotFoundException;
import com.cts.fsd.projectmanager.mapper.ApplicationMapperObject;
import com.cts.fsd.projectmanager.pojo.ParentTaskPOJO;
import com.cts.fsd.projectmanager.pojo.TaskPOJO;
import com.cts.fsd.projectmanager.repo.ParentTaskRepository;

/**
 * @author Amitabha Das [420652]
 * ParentTaskService interacts between the controller and the datasources using jpa repository
 */
@Service
public class ParentTaskService {
	
	@Autowired
	protected ApplicationMapperObject mapper;
	
	@Autowired
	protected ParentTaskRepository parentTaskRepository;
	
	@Autowired
	protected TaskService taskService;
	
	
	/**
	 * createParentTasks() is used to create a parent task in the db that is sent in the request
	 * @param parentTaskPOJOList
	 * @return List<ParentTaskPOJO>
	 */
	public List<ParentTaskPOJO> createParentTasks(List<ParentTaskPOJO> parentTaskPOJOList){
		List<ParentTaskEntity> parentTaskEntityList = new ArrayList<ParentTaskEntity>();
		List<ParentTaskPOJO> returnPojoList = new ArrayList<ParentTaskPOJO>();
		
		if (null != parentTaskPOJOList && !parentTaskPOJOList.isEmpty()) {
			for(ParentTaskPOJO parentTaskPOJO :  parentTaskPOJOList ) {
				ParentTaskEntity parentTaskEntity = mapper.mapParentTaskPojoToEntity(parentTaskPOJO);
				System.out.println("after mapping to entity parentTaskEntity = " + parentTaskEntity.toString());
				parentTaskEntityList.add(parentTaskEntity);
			}
		}
		System.out.println("Before Save parentTaskEntityList = " + parentTaskEntityList.toString());
		List<ParentTaskEntity> dbResponse = parentTaskRepository.saveAll(parentTaskEntityList);
		
		if (null != dbResponse && !dbResponse.isEmpty()) {
			System.out.println("createParentTasks() dbResponse = " + dbResponse.toString());
			for(ParentTaskEntity parentTaskEntity :  dbResponse ) {
				ParentTaskPOJO parentTaskPOJO = mapper.mapParentTaskEntityToPojo(parentTaskEntity);
				returnPojoList.add(parentTaskPOJO);
			}
		}
		
		return returnPojoList;
	}
	
	/**
	 * getAllParentTasks() is used to get all the records in the Parent Task table
	 * @return List<ParentTaskPOJO>
	 */
	public List<ParentTaskPOJO> getAllParentTasks(){
		
		List<ParentTaskEntity> dbResponse = parentTaskRepository.findAll();
		System.out.println("getAllParentTasks() dbResponse = " + dbResponse);
		
		List<ParentTaskPOJO> returnPojoList = new ArrayList<ParentTaskPOJO>();
		
		if (null != dbResponse && !dbResponse.isEmpty()) {
			for(ParentTaskEntity parentTaskEntity :  dbResponse ) {
				ParentTaskPOJO parentTaskPOJO = mapper.mapParentTaskEntityToPojo(parentTaskEntity);
				returnPojoList.add(parentTaskPOJO);
			}
		}
		return returnPojoList;
	}
	
	
	/**
	 * getParentTaskById() is used to get the parent task record from db based on parent id
	 * @param parentId
	 * @return ParentTaskEntity
	 */
	public ParentTaskEntity getParentTaskById(int parentId){
		
		ParentTaskEntity parentTaskFromDB = null;
		
		try {
			parentTaskFromDB = parentTaskRepository.findById(Long.valueOf(parentId)).get();
			System.out.println("getParentTaskById successfully returned ParentTaskEntity from DB :: " + parentTaskFromDB.toString());
		} catch (NoSuchElementException e) {
			parentTaskFromDB = null;
			System.out.println("getParentTaskById NOT successfull...\nNoSuchElementException encountered... ResourceNotFoundException thrown " + e);
			throw new ResourceNotFoundException("ParentTaskEntity" , "parentId" , parentId);
		} catch (Exception e ) {
			parentTaskFromDB = null;
			System.out.println("Exception encountered..." + e);
		}
		return parentTaskFromDB;
	}
	
	
	
	/**
	 * editParentTaskById() is used to update a parent task in db
	 * @param parentId
	 * @param parentTaskPOJO
	 * @return ParentTaskPOJO
	 */
	public ParentTaskPOJO editParentTaskById(int parentId , ParentTaskPOJO parentTaskPOJO){
		String editResponse = "";
		ParentTaskEntity parentTaskFromDB = null ;
		ParentTaskPOJO returnPOJO = null;
		try {
			parentTaskFromDB =  getParentTaskById(parentId);
			System.out.println("Updating parentTaskFromDB = " + parentTaskFromDB.toString());
			parentTaskFromDB.setParentTask(parentTaskPOJO.getParentTask());
			
			parentTaskFromDB =  parentTaskRepository.save(parentTaskFromDB);
			editResponse = "ParentTask ID("+parentId+") updated, " + parentTaskFromDB.toString();
			
			returnPOJO = mapper.mapParentTaskEntityToPojo(parentTaskFromDB);
		} catch(ResourceNotFoundException e ) {
			System.out.println("ResourceNotFoundException encountered..." + e);
			editResponse = "Things are not updated as record does not exist... ";
			parentTaskFromDB = null;
			returnPOJO = null;
		} catch(Exception e ) {
			System.out.println("Exception encountered..." + e);
			editResponse = "Things are not updated due to Exception... " + e.getMessage();
			parentTaskFromDB = null;
			returnPOJO = null;
		}
		System.out.println("After Parent Task Update :: " + editResponse);
		
		return returnPOJO;
	}

	/**
	 * removeParentTaskById() is used to remove a parent task based on parent id
	 * @param parentId
	 * @return boolean
	 */
	public boolean removeParentTaskById(int parentId) {
		String deleteResponse = "";
		ParentTaskEntity parentTaskFromDB = null;
		boolean returnResponse = false;
		System.out.println("Before Delete ParentTask By Id("+parentId+")");
		try {
			parentTaskFromDB =  getParentTaskById(parentId);
			System.out.println("Deleting parentTaskFromDB = " + parentTaskFromDB.toString());
			
			// Update the TASK Table With NULL Project ID 
			List<TaskPOJO> taskPOJOList = taskService.getAllTasks();
			for(TaskPOJO taskPOJO : taskPOJOList) {
				if(new Long(taskPOJO.getParentId()).equals(Long.valueOf(parentTaskFromDB.getParentId()))) {
					taskPOJO.setParentId(-1);
					taskService.editTaskByIdParentTaskDelete(taskPOJO.getTaskId(), taskPOJO);
				}
			}
			
			parentTaskRepository.deleteParentTaskById(Long.valueOf(parentTaskFromDB.getParentId()));
			deleteResponse = "ParentTask ID("+parentId+") Deleted, Record No More exists, corresponding tables are updated...";
			returnResponse = true;
		} catch (ResourceNotFoundException e ) {
			System.out.println("ResourceNotFoundException encountered..." + e);
			deleteResponse = "Things are not deleted as record does not exist... ";
			parentTaskFromDB = null;
			returnResponse = false;
		} catch (Exception e ) {
			System.out.println("Exception encountered..." + e);
			deleteResponse = "Things are not deleted due to Exception... " + e.getMessage();
			parentTaskFromDB = null;
			returnResponse = false;
		}
		
		System.out.println("After Delete :: " + deleteResponse);
		return returnResponse;
	}
	
	
	
	
}

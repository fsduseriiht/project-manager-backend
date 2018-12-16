package com.cts.fsd.projectmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.fsd.projectmanager.entity.ProjectEntity;
import com.cts.fsd.projectmanager.entity.UserEntity;
import com.cts.fsd.projectmanager.exception.ResourceNotFoundException;
import com.cts.fsd.projectmanager.mapper.ApplicationMapperObject;
import com.cts.fsd.projectmanager.pojo.ProjectPOJO;
import com.cts.fsd.projectmanager.pojo.TaskPOJO;
import com.cts.fsd.projectmanager.repo.ParentTaskRepository;
import com.cts.fsd.projectmanager.repo.ProjectRepository;

/**
 * @author Amitabha Das [420652]
 * ProjectService interacts between the controller and the datasources using jpa repository
 */
@Service
public class ProjectService {
	
	@Autowired
	protected ApplicationMapperObject mapper;
	
	@Autowired
	protected ParentTaskRepository parentTaskRepository;
	
	@Autowired
	protected ProjectRepository projectRepository;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected TaskService taskService;
	

	/**
	 * getProjectById() is used to get the project record from db based on project id
	 * @param projectId
	 * @return ProjectEntity
	 */
	public ProjectEntity getProjectById(int projectId) {
		
		ProjectEntity projectEntityFromDB = null;
		
		try {
			projectEntityFromDB = projectRepository.findById(Long.valueOf(projectId)).get();
			System.out.println("getProjectById successfully returned ParentTaskEntity from DB :: " + projectEntityFromDB.toString());
		} catch (NoSuchElementException e) {
			projectEntityFromDB = null;
			System.out.println("getProjectById NOT successfull...\nNoSuchElementException encountered... ResourceNotFoundException thrown " + e);
			throw new ResourceNotFoundException("ProjectEntity" , "projectId" , projectId);
		} catch (Exception e ) {
			projectEntityFromDB = null;
			System.out.println("Exception encountered..." + e);
		}
		return projectEntityFromDB;
	}
	
	
	
	/**
	 * createProjects() is used to create a project in the db that is sent in the request
	 * @param projectPOJOList
	 * @return List<ProjectPOJO>
	 */
	public List<ProjectPOJO> createProjects(List<ProjectPOJO> projectPOJOList) {
		
		List<ProjectEntity> projectEntityList = new ArrayList<ProjectEntity>();
		List<ProjectPOJO> returnPojoList = new ArrayList<ProjectPOJO>();
		
		if (null != projectPOJOList && !projectPOJOList.isEmpty()) {
			for(ProjectPOJO projectPOJO :  projectPOJOList ) {
				
				UserEntity userEntityFromDB = null;
				if ( projectPOJO.getUserId() >= 0 ) {
					userEntityFromDB = userService.getUserById(projectPOJO.getUserId());
					System.out.println("userEntityFromDB = " + userEntityFromDB.toString());
				}
				
				ProjectEntity projectEntity = mapper.mapProjectPojoToEntity(projectPOJO);
				projectEntity.setUserEntity(userEntityFromDB);

				System.out.println("after mapping to entity projectEntity = " + projectEntity.toString());
				projectEntityList.add(projectEntity);
			}
		}
		System.out.println("Before Save projectEntityList = " + projectEntityList.toString());
		List<ProjectEntity> dbResponse = projectRepository.saveAll(projectEntityList);
		
		if (null != dbResponse && !dbResponse.isEmpty()) {
			System.out.println("createProjects() dbResponse = " + dbResponse.toString());
			for(ProjectEntity projectEntity :  dbResponse ) {
				ProjectPOJO projectPOJO = mapper.mapProjectEntityToPojo(projectEntity);
				returnPojoList.add(projectPOJO);
			}
		}
		
		return returnPojoList;
	}
	
	
	
	
	/**
	 * createParentTasks() is used to create a parent task in the db that is sent in the request
	 * @param parentTaskPOJOList
	 * @return List<ParentTaskPOJO>
	 */
	/*public List<ParentTaskPOJO> createParentTasks(List<ParentTaskPOJO> parentTaskPOJOList){
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
		System.out.println("createParentTasks() dbResponse = " + dbResponse.toString());
		
		if (null != dbResponse && !dbResponse.isEmpty()) {
			for(ParentTaskEntity parentTaskEntity :  dbResponse ) {
				ParentTaskPOJO parentTaskPOJO = mapper.mapParentTaskEntityToPojo(parentTaskEntity);
				returnPojoList.add(parentTaskPOJO);
			}
		}
		
		return returnPojoList;
	}*/
	
	/**
	 * getAllProjects() is used to get all the records in the Parent Task table
	 * @return List<ParentTaskPOJO>
	 */
	public List<ProjectPOJO> getAllProjects(){
		
		List<ProjectEntity> dbResponse = projectRepository.findAll();
		System.out.println("getAllProjects() dbResponse = " + dbResponse);
		
		List<ProjectPOJO> returnPojoList = new ArrayList<ProjectPOJO>();
		
		if (null != dbResponse && !dbResponse.isEmpty()) {
			for(ProjectEntity projectEntity :  dbResponse ) {
				ProjectPOJO projectPOJO = mapper.mapProjectEntityToPojo(projectEntity);
				returnPojoList.add(projectPOJO);
			}
		}
		return returnPojoList;
	}
	
	
	/**
	 * editProjectById() is used to update a parent task in db
	 * @param projectId
	 * @param projectPOJO
	 * @return ParentTaskPOJO
	 */
	public ProjectPOJO editProjectById(int projectId , ProjectPOJO projectPOJO){
		String editResponse = "";
		ProjectEntity projectFromDB = null ;
		ProjectPOJO returnPOJO = null;
		try {
			projectFromDB =  getProjectById(projectId);
			System.out.println("Updating projectFromDB = " + projectFromDB.toString());			
			
			projectFromDB.setProject(projectPOJO.getProject());
			projectFromDB.setStartDate(new java.sql.Date(projectPOJO.getStartDate().getTime()));
			projectFromDB.setEndDate(new java.sql.Date(projectPOJO.getEndDate().getTime()));
			projectFromDB.setPriority(projectPOJO.getPriority());
			
			projectFromDB =  projectRepository.save(projectFromDB);
			editResponse = "Project ID("+projectId+") updated, " + projectFromDB.toString();
			
			returnPOJO = mapper.mapProjectEntityToPojo(projectFromDB);
		} catch(ResourceNotFoundException e ) {
			System.out.println("ResourceNotFoundException encountered..." + e);
			editResponse = "Things are not updated as record does not exist... ";
			projectFromDB = null;
			returnPOJO = null;
		} catch(Exception e ) {
			System.out.println("Exception encountered..." + e);
			editResponse = "Things are not updated due to Exception... " + e.getMessage();
			projectFromDB = null;
			returnPOJO = null;
		}
		System.out.println("After Project Update :: " + editResponse);
		
		return returnPOJO;
	}
	/**
	 * editProjectByIdUserDelete() is used to update a parent task in db while user is deleted
	 * @param projectId
	 * @param projectPOJO
	 * @return ParentTaskPOJO
	 */
	public ProjectPOJO editProjectByIdUserDelete(int projectId , ProjectPOJO projectPOJO){
		String editResponse = "";
		ProjectEntity projectFromDB = null ;
		ProjectPOJO returnPOJO = null;
		try {
			projectFromDB =  getProjectById(projectId);
			System.out.println("Updating projectFromDB = " + projectFromDB.toString());			
			
			projectFromDB.setProject(projectPOJO.getProject());
			projectFromDB.setStartDate(new java.sql.Date(projectPOJO.getStartDate().getTime()));
			projectFromDB.setEndDate(new java.sql.Date(projectPOJO.getEndDate().getTime()));
			projectFromDB.setPriority(projectPOJO.getPriority());
			
			projectFromDB.setUserEntity(null);
			
			projectFromDB =  projectRepository.save(projectFromDB);
			editResponse = "Project ID("+projectId+") updated, " + projectFromDB.toString();
			
			returnPOJO = mapper.mapProjectEntityToPojo(projectFromDB);
		} catch(ResourceNotFoundException e ) {
			System.out.println("ResourceNotFoundException encountered..." + e);
			editResponse = "Things are not updated as record does not exist... ";
			projectFromDB = null;
			returnPOJO = null;
		} catch(Exception e ) {
			System.out.println("Exception encountered..." + e);
			editResponse = "Things are not updated due to Exception... " + e.getMessage();
			projectFromDB = null;
			returnPOJO = null;
		}
		System.out.println("After Project Update :: " + editResponse);
		
		return returnPOJO;
	}

	/**
	 * removeProjectById() is used to remove a parent task based on parent id
	 * @param projectId
	 * @return boolean
	 */
	public boolean removeProjectById(int projectId) {
		String deleteResponse = "";
		ProjectEntity projectFromDB = null;
		boolean returnResponse = false;
		System.out.println("Before Delete Project By Id("+projectId+")");
		try {
			projectFromDB =  getProjectById(projectId);
			System.out.println("Deleting projectFromDB = " + projectFromDB.toString());
			
			// Update the TASK Table With NULL Project ID 
			List<TaskPOJO> taskPOJOList = taskService.getAllTasks();
			for(TaskPOJO taskPOJO : taskPOJOList) {
				if(new Long(taskPOJO.getProjectId()).equals(Long.valueOf(projectFromDB.getProjectId()))) {
					taskPOJO.setProjectId(-1);
					taskService.editTaskByIdProjectDelete(taskPOJO.getTaskId(), taskPOJO);
				}
			}
			
			projectRepository.deleteProjectById(Long.valueOf(projectFromDB.getProjectId()));
			deleteResponse = "Project ID("+projectId+") Deleted, Record No More exists,";
			returnResponse = true;
		} catch (ResourceNotFoundException e ) {
			System.out.println("ResourceNotFoundException encountered..." + e);
			deleteResponse = "Things are not deleted as record does not exist... ";
			projectFromDB = null;
			returnResponse = false;
		} catch (Exception e ) {
			System.out.println("Exception encountered..." + e);
			deleteResponse = "Things are not deleted due to Exception... " + e.getMessage();
			projectFromDB = null;
			returnResponse = false;
		}
		
		System.out.println("After Delete :: " + deleteResponse);
		return returnResponse;
	}
	
	
	
	
}

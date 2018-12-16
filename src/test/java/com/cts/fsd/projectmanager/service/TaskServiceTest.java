package com.cts.fsd.projectmanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.fsd.projectmanager.entity.ParentTaskEntity;
import com.cts.fsd.projectmanager.entity.ProjectEntity;
import com.cts.fsd.projectmanager.entity.TaskEntity;
import com.cts.fsd.projectmanager.entity.UserEntity;
import com.cts.fsd.projectmanager.mapper.ApplicationMapperObject;
import com.cts.fsd.projectmanager.pojo.TaskPOJO;
import com.cts.fsd.projectmanager.repo.TaskRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
	
	@MockBean
	protected ApplicationMapperObject mapper;
	
	@MockBean
	protected TaskRepository taskRepository;
	
	@MockBean
	protected ParentTaskService parentTaskService;
	
	@MockBean
	protected ProjectService projectService;
	
	@MockBean
	protected UserService userService;
	
	@Autowired
	protected TaskService taskService;
	
	@Test
	public void testGetTaskById() {
		
		int taskId = 333;
		TaskEntity taskFromDB = new TaskEntity();
		taskFromDB.setTaskId(Long.valueOf(taskId));
		taskFromDB.setTask("fake_task");
		taskFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setPriority(10);
		
		int userId = 1;
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(Long.valueOf(userId));
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		taskFromDB.setUserEntity(userFromDB);
		
		int projectId = 1;
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		taskFromDB.setProjectEntity(projectEntityFromDB);
		
		int parentId = 1;
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		taskFromDB.setParentTaskEntity(parentTaskEntityFromDB);
		
		Optional<TaskEntity> optional = Optional.of(taskFromDB);
		
		Mockito.when(taskRepository.findById(Long.valueOf(taskId))).thenReturn(optional);
		
		TaskEntity result = taskService.getTaskById(taskId);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testCreateTasks() {
		
		List<TaskPOJO> taskPOJOList = new ArrayList<TaskPOJO>();
		
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;

		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		taskPOJOList.add(taskPOJO);
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		Mockito.when(parentTaskService.getParentTaskById(taskPOJO.getParentId())).thenReturn(parentTaskEntityFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(projectService.getProjectById(taskPOJO.getProjectId())).thenReturn(projectEntityFromDB);
		

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");

		Mockito.when(userService.getUserById(taskPOJO.getUserId())).thenReturn(userEntityFromDB);
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);

		Mockito.when(mapper.mapTaskPojoToEntity(Matchers.<TaskPOJO>any())).thenReturn(taskEntity);
		
		List<TaskEntity> dbResponse = new ArrayList<TaskEntity>();
		dbResponse.add(taskEntity);
		
		Mockito.when(taskRepository.saveAll(Matchers.<List<TaskEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		List<TaskPOJO> result = taskService.createTasks(taskPOJOList);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testCreateTasks_taskPOJOList_null() {
		
		List<TaskPOJO> taskPOJOList = new ArrayList<TaskPOJO>();
		
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;

		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		taskPOJOList.add(taskPOJO);
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		Mockito.when(parentTaskService.getParentTaskById(taskPOJO.getParentId())).thenReturn(parentTaskEntityFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(projectService.getProjectById(taskPOJO.getProjectId())).thenReturn(projectEntityFromDB);
		

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");

		Mockito.when(userService.getUserById(taskPOJO.getUserId())).thenReturn(userEntityFromDB);
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);

		Mockito.when(mapper.mapTaskPojoToEntity(Matchers.<TaskPOJO>any())).thenReturn(taskEntity);
		
		List<TaskEntity> dbResponse = new ArrayList<TaskEntity>();
		dbResponse.add(taskEntity);
		
		Mockito.when(taskRepository.saveAll(Matchers.<List<TaskEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		List<TaskPOJO> result = taskService.createTasks(null);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testCreateTasks_taskPOJOList_empty() {
		
		List<TaskPOJO> taskPOJOList = new ArrayList<TaskPOJO>();
		
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;

		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		Mockito.when(parentTaskService.getParentTaskById(taskPOJO.getParentId())).thenReturn(parentTaskEntityFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(projectService.getProjectById(taskPOJO.getProjectId())).thenReturn(projectEntityFromDB);
		

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");

		Mockito.when(userService.getUserById(taskPOJO.getUserId())).thenReturn(userEntityFromDB);
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);

		Mockito.when(mapper.mapTaskPojoToEntity(Matchers.<TaskPOJO>any())).thenReturn(taskEntity);
		
		List<TaskEntity> dbResponse = new ArrayList<TaskEntity>();
		dbResponse.add(taskEntity);
		
		Mockito.when(taskRepository.saveAll(Matchers.<List<TaskEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		List<TaskPOJO> result = taskService.createTasks(taskPOJOList);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testCreateTasks_taskEntity_null() {
		
		List<TaskPOJO> taskPOJOList = new ArrayList<TaskPOJO>();
		
		int parentId = -1;
		int projectId = -1;
		int userId = -1;
		int taskId = 333;

		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		taskPOJOList.add(taskPOJO);
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		Mockito.when(parentTaskService.getParentTaskById(taskPOJO.getParentId())).thenReturn(parentTaskEntityFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(projectService.getProjectById(taskPOJO.getProjectId())).thenReturn(projectEntityFromDB);
		

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");

		Mockito.when(userService.getUserById(taskPOJO.getUserId())).thenReturn(userEntityFromDB);
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);

		Mockito.when(mapper.mapTaskPojoToEntity(Matchers.<TaskPOJO>any())).thenReturn(null);
		
		List<TaskEntity> dbResponse = new ArrayList<TaskEntity>();
		dbResponse.add(taskEntity);
		
		Mockito.when(taskRepository.saveAll(Matchers.<List<TaskEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		List<TaskPOJO> result = taskService.createTasks(taskPOJOList);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testCreateTasks_dbResponse_null() {
		
		List<TaskPOJO> taskPOJOList = new ArrayList<TaskPOJO>();
		
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;

		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		taskPOJOList.add(taskPOJO);
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		Mockito.when(parentTaskService.getParentTaskById(taskPOJO.getParentId())).thenReturn(parentTaskEntityFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(projectService.getProjectById(taskPOJO.getProjectId())).thenReturn(projectEntityFromDB);
		

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");

		Mockito.when(userService.getUserById(taskPOJO.getUserId())).thenReturn(userEntityFromDB);
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);

		Mockito.when(mapper.mapTaskPojoToEntity(Matchers.<TaskPOJO>any())).thenReturn(taskEntity);
		
		List<TaskEntity> dbResponse = null;
		
		Mockito.when(taskRepository.saveAll(Matchers.<List<TaskEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		List<TaskPOJO> result = taskService.createTasks(taskPOJOList);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testCreateTasks_dbResponse_empty() {
		
		List<TaskPOJO> taskPOJOList = new ArrayList<TaskPOJO>();
		
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;

		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		taskPOJOList.add(taskPOJO);
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		Mockito.when(parentTaskService.getParentTaskById(taskPOJO.getParentId())).thenReturn(parentTaskEntityFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(projectService.getProjectById(taskPOJO.getProjectId())).thenReturn(projectEntityFromDB);
		

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");

		Mockito.when(userService.getUserById(taskPOJO.getUserId())).thenReturn(userEntityFromDB);
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);

		Mockito.when(mapper.mapTaskPojoToEntity(Matchers.<TaskPOJO>any())).thenReturn(taskEntity);
		
		List<TaskEntity> dbResponse = new ArrayList<TaskEntity>();
		
		Mockito.when(taskRepository.saveAll(Matchers.<List<TaskEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		List<TaskPOJO> result = taskService.createTasks(taskPOJOList);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testGetAllTasks() {
		
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);
		
		List<TaskEntity> dbResponse = new ArrayList<TaskEntity>();
		dbResponse.add(taskEntity);
		
		Mockito.when(taskRepository.findAll()).thenReturn(dbResponse);
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		
		List<TaskPOJO> result = taskService.getAllTasks();
		Assert.assertNotNull(result);
		
		
	}
	
	
	@Test
	public void testGetAllTasks_dbResponse_empty() {
		
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);
		
		List<TaskEntity> dbResponse = new ArrayList<TaskEntity>();
		
		Mockito.when(taskRepository.findAll()).thenReturn(dbResponse);
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		
		List<TaskPOJO> result = taskService.getAllTasks();
		Assert.assertNotNull(result);
		
		
	}
	
	@Test
	public void testGetAllTasks_dbResponse_null() {
		
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);
		
		List<TaskEntity> dbResponse = null;
		
		Mockito.when(taskRepository.findAll()).thenReturn(dbResponse);
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		
		List<TaskPOJO> result = taskService.getAllTasks();
		Assert.assertNotNull(result);
		
		
	}
	
	@Test
	public void testEditTaskById() {

		int taskId = 333;
		TaskEntity taskFromDB = new TaskEntity();
		taskFromDB.setTaskId(Long.valueOf(taskId));
		taskFromDB.setTask("fake_task");
		taskFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setPriority(10);
		
		int userId = 1;
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(Long.valueOf(userId));
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		taskFromDB.setUserEntity(userFromDB);
		
		int projectId = 1;
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		taskFromDB.setProjectEntity(projectEntityFromDB);
		
		int parentId = 1;
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		taskFromDB.setParentTaskEntity(parentTaskEntityFromDB);
		
		Optional<TaskEntity> optional = Optional.of(taskFromDB);
		
		Mockito.when(taskRepository.findById(Long.valueOf(taskId))).thenReturn(optional);
		
		Mockito.when(taskRepository.save(Matchers.<TaskEntity>any())).thenReturn(taskFromDB);
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		TaskPOJO result = taskService.editTaskById(taskId, taskPOJO);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testEditTaskByIdParentTaskDelete() {

		int taskId = 333;
		TaskEntity taskFromDB = new TaskEntity();
		taskFromDB.setTaskId(Long.valueOf(taskId));
		taskFromDB.setTask("fake_task");
		taskFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setPriority(10);
		
		int userId = 1;
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(Long.valueOf(userId));
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		taskFromDB.setUserEntity(userFromDB);
		
		int projectId = 1;
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		taskFromDB.setProjectEntity(projectEntityFromDB);
		
		int parentId = 1;
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		taskFromDB.setParentTaskEntity(parentTaskEntityFromDB);
		
		Optional<TaskEntity> optional = Optional.of(taskFromDB);
		
		Mockito.when(taskRepository.findById(Long.valueOf(taskId))).thenReturn(optional);
		
		Mockito.when(taskRepository.save(Matchers.<TaskEntity>any())).thenReturn(taskFromDB);
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		TaskPOJO result = taskService.editTaskByIdParentTaskDelete(taskId, taskPOJO);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testEditTaskByIdProjectDelete() {

		int taskId = 333;
		TaskEntity taskFromDB = new TaskEntity();
		taskFromDB.setTaskId(Long.valueOf(taskId));
		taskFromDB.setTask("fake_task");
		taskFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setPriority(10);
		
		int userId = 1;
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(Long.valueOf(userId));
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		taskFromDB.setUserEntity(userFromDB);
		
		int projectId = 1;
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		taskFromDB.setProjectEntity(projectEntityFromDB);
		
		int parentId = 1;
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		taskFromDB.setParentTaskEntity(parentTaskEntityFromDB);
		
		Optional<TaskEntity> optional = Optional.of(taskFromDB);
		
		Mockito.when(taskRepository.findById(Long.valueOf(taskId))).thenReturn(optional);
		
		Mockito.when(taskRepository.save(Matchers.<TaskEntity>any())).thenReturn(taskFromDB);
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		TaskPOJO result = taskService.editTaskByIdProjectDelete(taskId, taskPOJO);
		Assert.assertNotNull(result);
	}

	@Test
	public void testEditTaskByIdUserDelete() {

		int taskId = 333;
		TaskEntity taskFromDB = new TaskEntity();
		taskFromDB.setTaskId(Long.valueOf(taskId));
		taskFromDB.setTask("fake_task");
		taskFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setPriority(10);
		
		int userId = 1;
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(Long.valueOf(userId));
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		taskFromDB.setUserEntity(userFromDB);
		
		int projectId = 1;
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		taskFromDB.setProjectEntity(projectEntityFromDB);
		
		int parentId = 1;
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		taskFromDB.setParentTaskEntity(parentTaskEntityFromDB);
		
		Optional<TaskEntity> optional = Optional.of(taskFromDB);
		
		Mockito.when(taskRepository.findById(Long.valueOf(taskId))).thenReturn(optional);
		
		Mockito.when(taskRepository.save(Matchers.<TaskEntity>any())).thenReturn(taskFromDB);
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		TaskPOJO result = taskService.editTaskByIdUserDelete(taskId, taskPOJO);
		Assert.assertNotNull(result);
	}
	
	
	
	@Test
	public void testRemoveTaskById() {

		int taskId = 333;
		TaskEntity taskFromDB = new TaskEntity();
		taskFromDB.setTaskId(Long.valueOf(taskId));
		taskFromDB.setTask("fake_task");
		taskFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		taskFromDB.setPriority(10);
		
		int userId = 1;
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(Long.valueOf(userId));
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		taskFromDB.setUserEntity(userFromDB);
		
		int projectId = 1;
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		taskFromDB.setProjectEntity(projectEntityFromDB);
		
		int parentId = 1;
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		taskFromDB.setParentTaskEntity(parentTaskEntityFromDB);
		
		Optional<TaskEntity> optional = Optional.of(taskFromDB);
		
		Mockito.when(taskRepository.findById(Long.valueOf(taskId))).thenReturn(optional);
		
		Mockito
			.doNothing()
			.when(taskRepository)
			.deleteTaskById(Matchers.anyLong());
		
		
		boolean result = taskService.removeTaskById(taskId);
		Assert.assertNotNull(Boolean.valueOf(result));
		
	}
	
	@Test
	public void testGetAllTasks_projectId() {
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);
		
		List<TaskEntity> dbResponse = new ArrayList<TaskEntity>();
		dbResponse.add(taskEntity);
		
		Mockito.when(taskRepository.listTaskByProjectId(Matchers.anyLong())).thenReturn(dbResponse);
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		
		List<TaskPOJO> result = taskService.getAllTasks(projectId);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testGetAllTasks_projectId_dbResponse_empty() {
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);
		
		List<TaskEntity> dbResponse = new ArrayList<TaskEntity>();
		
		Mockito.when(taskRepository.listTaskByProjectId(Matchers.anyLong())).thenReturn(dbResponse);
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		
		List<TaskPOJO> result = taskService.getAllTasks(projectId);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testGetAllTasks_projectId_dbResponse_null() {
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;
		
		ParentTaskEntity parentTaskEntityFromDB = new ParentTaskEntity();
		parentTaskEntityFromDB.setParentId(Long.valueOf(parentId));
		parentTaskEntityFromDB.setParentTask("fake_parentTask");
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);

		UserEntity userEntityFromDB = new UserEntity();
		userEntityFromDB.setUserId(Long.valueOf(userId));
		userEntityFromDB.setFirstName("fake_firstName");
		userEntityFromDB.setLastName("fake_lastName");
		userEntityFromDB.setEmployeeId("fake_employeeId");
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);
		
		List<TaskEntity> dbResponse = null;
		
		Mockito.when(taskRepository.listTaskByProjectId(Matchers.anyLong())).thenReturn(dbResponse);
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		Mockito.when(mapper.mapTaskEntityToPojo(Matchers.<TaskEntity>any())).thenReturn(taskPOJO);
		
		
		List<TaskPOJO> result = taskService.getAllTasks(projectId);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testGetTasksPerProject() {
		
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;
		
		List<TaskPOJO> taskPOJOList = new ArrayList<>();
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		taskPOJOList.add(taskPOJO);
		
		Map<Integer , Integer> result = taskService.getTasksPerProject(taskPOJOList);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testGetTasksPerProject_returnMap_not_equal_null() {
		List<TaskPOJO> taskPOJOList = new ArrayList<>();
		
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		int taskId = 333;
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		taskPOJOList.add(taskPOJO);
		
		int parentId2 = 2;
		int projectId2 = 1;
		int userId2 = 1;
		int taskId2 = 334;
		
		TaskPOJO taskPOJO2 = new TaskPOJO();
		taskPOJO2.setTaskId(taskId2);
		taskPOJO2.setTask("fake_task");
		taskPOJO2.setStartDate(new Date());
		taskPOJO2.setEndDate(new Date());
		taskPOJO2.setPriority(10);
		taskPOJO2.setParentId(parentId2);
		taskPOJO2.setProjectId(projectId2);
		taskPOJO2.setUserId(userId2);
		
		taskPOJOList.add(taskPOJO2);
		
		Map<Integer , Integer> result = taskService.getTasksPerProject(taskPOJOList);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testGetTasksPerProject_ProjectId_greater_than_zero() {
		
		int parentId = 1;
		int projectId = -1;
		int userId = 1;
		int taskId = 333;
		
		List<TaskPOJO> taskPOJOList = new ArrayList<>();
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		taskPOJOList.add(taskPOJO);
		
		Map<Integer , Integer> result = taskService.getTasksPerProject(taskPOJOList);
		Assert.assertNotNull(result);
		
	}
	
	

}

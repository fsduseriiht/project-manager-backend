package com.cts.fsd.projectmanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import com.cts.fsd.projectmanager.entity.ProjectEntity;
import com.cts.fsd.projectmanager.entity.UserEntity;
import com.cts.fsd.projectmanager.mapper.ApplicationMapperObject;
import com.cts.fsd.projectmanager.pojo.ProjectPOJO;
import com.cts.fsd.projectmanager.pojo.TaskPOJO;
import com.cts.fsd.projectmanager.repo.ParentTaskRepository;
import com.cts.fsd.projectmanager.repo.ProjectRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {
	
	@MockBean
	protected ApplicationMapperObject mapper;
	
	@MockBean
	protected ParentTaskRepository parentTaskRepository;
	
	@MockBean
	protected ProjectRepository projectRepository;
	
	@MockBean
	protected UserService userService;
	
	@MockBean
	protected TaskService taskService;
	
	@Autowired
	protected ProjectService projectService;
	
	
	@Test
	public void testGetProjectById() {
		
		int projectId = 333;
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(Long.valueOf(1));
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		projectEntityFromDB.setUserEntity(userFromDB);
		
		Optional<ProjectEntity> optional = Optional.of(projectEntityFromDB);
		
		Mockito.when(projectRepository.findById(Long.valueOf(projectId))).thenReturn(optional);
		
		ProjectEntity result = projectService.getProjectById(projectId);
		Assert.assertNotNull(result);
	
	}

	@Test
	public void testCreateProjects() {
		
		List<ProjectPOJO> projectPOJOList = new ArrayList<ProjectPOJO>();
		
		int projectId = 333;
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		projectPOJOList.add(projectPOJO);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(1);
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		Mockito.when(userService.getUserById(Matchers.anyInt())).thenReturn(userFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(mapper.mapProjectPojoToEntity(projectPOJO)).thenReturn(projectEntityFromDB);
		
		List<ProjectEntity> dbResponse = new ArrayList<ProjectEntity>();
		dbResponse.add(projectEntityFromDB);
		
		Mockito.when(projectRepository.saveAll(Matchers.<List<ProjectEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		List<ProjectPOJO> result = projectService.createProjects(projectPOJOList);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testCreateProjects_projectPOJO_userId_less_than_zero() {
		
		List<ProjectPOJO> projectPOJOList = new ArrayList<ProjectPOJO>();
		
		int projectId = 333;
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(-1);
		
		projectPOJOList.add(projectPOJO);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(1);
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		Mockito.when(userService.getUserById(Matchers.anyInt())).thenReturn(userFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(mapper.mapProjectPojoToEntity(projectPOJO)).thenReturn(projectEntityFromDB);
		
		List<ProjectEntity> dbResponse = new ArrayList<ProjectEntity>();
		dbResponse.add(projectEntityFromDB);
		
		Mockito.when(projectRepository.saveAll(Matchers.<List<ProjectEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		List<ProjectPOJO> result = projectService.createProjects(projectPOJOList);
		Assert.assertNotNull(result);
	}
	
	
	@Test
	public void testCreateProjects_dbResponse_empty() {
		
		List<ProjectPOJO> projectPOJOList = new ArrayList<ProjectPOJO>();
		
		int projectId = 333;
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		projectPOJOList.add(projectPOJO);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(1);
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		Mockito.when(userService.getUserById(Matchers.anyInt())).thenReturn(userFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(mapper.mapProjectPojoToEntity(projectPOJO)).thenReturn(projectEntityFromDB);
		
		List<ProjectEntity> dbResponse = new ArrayList<ProjectEntity>();
		
		Mockito.when(projectRepository.saveAll(Matchers.<List<ProjectEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		List<ProjectPOJO> result = projectService.createProjects(projectPOJOList);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testCreateProjects_dbResponse_null() {
		
		List<ProjectPOJO> projectPOJOList = new ArrayList<ProjectPOJO>();
		
		int projectId = 333;
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		projectPOJOList.add(projectPOJO);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(1);
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		Mockito.when(userService.getUserById(Matchers.anyInt())).thenReturn(userFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(mapper.mapProjectPojoToEntity(projectPOJO)).thenReturn(projectEntityFromDB);
		
		List<ProjectEntity> dbResponse = null;
		
		Mockito.when(projectRepository.saveAll(Matchers.<List<ProjectEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		List<ProjectPOJO> result = projectService.createProjects(projectPOJOList);
		Assert.assertNotNull(result);
	}
	
	
	@Test
	public void testCreateProjects_projectPOJOList_empty() {
		
		List<ProjectPOJO> projectPOJOList = new ArrayList<ProjectPOJO>();
		
		int projectId = 333;
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(1);
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		Mockito.when(userService.getUserById(Matchers.anyInt())).thenReturn(userFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(mapper.mapProjectPojoToEntity(projectPOJO)).thenReturn(projectEntityFromDB);
		
		List<ProjectEntity> dbResponse = new ArrayList<ProjectEntity>();
		dbResponse.add(projectEntityFromDB);
		
		Mockito.when(projectRepository.saveAll(Matchers.<List<ProjectEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		List<ProjectPOJO> result = projectService.createProjects(projectPOJOList);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testCreateProjects_projectPOJOList_null() {
		
		List<ProjectPOJO> projectPOJOList = null;
		
		int projectId = 333;
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(1);
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		Mockito.when(userService.getUserById(Matchers.anyInt())).thenReturn(userFromDB);
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		Mockito.when(mapper.mapProjectPojoToEntity(projectPOJO)).thenReturn(projectEntityFromDB);
		
		List<ProjectEntity> dbResponse = new ArrayList<ProjectEntity>();
		dbResponse.add(projectEntityFromDB);
		
		Mockito.when(projectRepository.saveAll(Matchers.<List<ProjectEntity>>any())).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		List<ProjectPOJO> result = projectService.createProjects(projectPOJOList);
		Assert.assertNotNull(result);
	}
	
	
	@Test
	public void testGetAllProjects() {
		
		List<ProjectPOJO> returnPojoList = new ArrayList<ProjectPOJO>();
		
		int projectId = 333;
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		returnPojoList.add(projectPOJO);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(1);
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		projectEntityFromDB.setUserEntity(userFromDB);
		
		List<ProjectEntity> dbResponse = new ArrayList<ProjectEntity>();
		dbResponse.add(projectEntityFromDB);
		
		Mockito.when(projectRepository.findAll()).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		List<ProjectPOJO> result = projectService.getAllProjects();
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testGetAllProjects_dbResponse_empty() {
		
		List<ProjectPOJO> returnPojoList = new ArrayList<ProjectPOJO>();
		
		int projectId = 333;
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		returnPojoList.add(projectPOJO);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(1);
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		projectEntityFromDB.setUserEntity(userFromDB);
		
		List<ProjectEntity> dbResponse = new ArrayList<ProjectEntity>();
		
		Mockito.when(projectRepository.findAll()).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		List<ProjectPOJO> result = projectService.getAllProjects();
		Assert.assertNotNull(result);
	}
	
	
	@Test
	public void testGetAllProjects_dbResponse_null() {
		
		List<ProjectPOJO> returnPojoList = new ArrayList<ProjectPOJO>();
		
		int projectId = 333;
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		returnPojoList.add(projectPOJO);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(1);
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		projectEntityFromDB.setUserEntity(userFromDB);
		
		List<ProjectEntity> dbResponse = null;
		
		Mockito.when(projectRepository.findAll()).thenReturn(dbResponse);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		List<ProjectPOJO> result = projectService.getAllProjects();
		Assert.assertNotNull(result);
	}
	
	
	@Test
	public void testEditProjectById() {
		
		int projectId = 333;
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(Long.valueOf(1));
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		projectEntityFromDB.setUserEntity(userFromDB);
		
		Optional<ProjectEntity> optional = Optional.of(projectEntityFromDB);
		
		Mockito.when(projectRepository.findById(Matchers.anyLong())).thenReturn(optional);
		
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		Mockito.when(projectRepository.save(Matchers.<ProjectEntity>any())).thenReturn(projectEntityFromDB);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		ProjectPOJO result = projectService.editProjectById(projectId , projectPOJO);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testEditProjectByIdUserDelete() {
		
		int projectId = 333;
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(Long.valueOf(1));
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		projectEntityFromDB.setUserEntity(userFromDB);
		
		Optional<ProjectEntity> optional = Optional.of(projectEntityFromDB);
		
		Mockito.when(projectRepository.findById(Matchers.anyLong())).thenReturn(optional);
		
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		Mockito.when(projectRepository.save(Matchers.<ProjectEntity>any())).thenReturn(projectEntityFromDB);
		
		Mockito.when(mapper.mapProjectEntityToPojo(Matchers.<ProjectEntity>any())).thenReturn(projectPOJO);
		
		ProjectPOJO result = projectService.editProjectByIdUserDelete(projectId , projectPOJO);
		Assert.assertNotNull(result);
		
	}
	
	
	@Test
	public void testRemoveProjectById() {
		
		int userId = 1;
		int projectId = 1;
		int parentId = 1;
		int taskId = 1;
		
		ProjectEntity projectEntityFromDB = new ProjectEntity();
		projectEntityFromDB.setProjectId(Long.valueOf(projectId));
		projectEntityFromDB.setProject("fake_project");
		projectEntityFromDB.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntityFromDB.setPriority(10);
		
		UserEntity userFromDB = new UserEntity();
		userFromDB.setUserId(Long.valueOf(1));
		userFromDB.setFirstName("fake_firstName");
		userFromDB.setLastName("fake_lastName");
		userFromDB.setEmployeeId("fake_employeeId");
		
		projectEntityFromDB.setUserEntity(userFromDB);
		
		Optional<ProjectEntity> optional = Optional.of(projectEntityFromDB);
		
		Mockito.when(projectRepository.findById(Matchers.anyLong())).thenReturn(optional);
		
		List<TaskPOJO> taskPOJOList = new ArrayList<TaskPOJO>();
		
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
		
		Mockito.when(taskService.getAllTasks()).thenReturn(taskPOJOList);
		
		Mockito.when(taskService.editTaskByIdProjectDelete(taskPOJO.getTaskId(), taskPOJO)).thenReturn(taskPOJO);
		
		Mockito
			.doNothing()
			.when(projectRepository)
			.deleteProjectById(Matchers.anyLong());
		
		
		boolean result = projectService.removeProjectById(projectId);
		Assert.assertNotNull(Boolean.valueOf(result));
		
	}
}

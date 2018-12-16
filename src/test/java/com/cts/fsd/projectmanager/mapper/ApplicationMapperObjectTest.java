package com.cts.fsd.projectmanager.mapper;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.fsd.projectmanager.entity.ParentTaskEntity;
import com.cts.fsd.projectmanager.entity.ProjectEntity;
import com.cts.fsd.projectmanager.entity.TaskEntity;
import com.cts.fsd.projectmanager.entity.UserEntity;
import com.cts.fsd.projectmanager.pojo.ParentTaskPOJO;
import com.cts.fsd.projectmanager.pojo.ProjectPOJO;
import com.cts.fsd.projectmanager.pojo.TaskPOJO;
import com.cts.fsd.projectmanager.pojo.UserPOJO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationMapperObjectTest {

	@Autowired
	protected ApplicationMapperObject mapper;
	
	@Test
	public void testMapParentTaskPojoToEntity() {
		
		int parentId = 333;
		
		ParentTaskPOJO parentTaskPOJO = new ParentTaskPOJO();
		parentTaskPOJO.setParentId(parentId);
		parentTaskPOJO.setParentTask("fake_parentTask");
		
		ParentTaskEntity result = mapper.mapParentTaskPojoToEntity(parentTaskPOJO);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapParentTaskPojoToEntity_parentTaskPOJO_null() {
		
		ParentTaskPOJO parentTaskPOJO = null;
		
		ParentTaskEntity result = mapper.mapParentTaskPojoToEntity(parentTaskPOJO);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapParentTaskEntityToPojo() {
		
		int parentId = 333;
		
		ParentTaskEntity parentTaskEntity = new ParentTaskEntity();
		parentTaskEntity.setParentId(Long.valueOf(parentId));
		parentTaskEntity.setParentTask("fake_parentTask");
		
		ParentTaskPOJO result = mapper.mapParentTaskEntityToPojo(parentTaskEntity);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapParentTaskEntityToPojo_parentTaskEntity_null() {
		
		ParentTaskEntity parentTaskEntity = null;
		
		ParentTaskPOJO result = mapper.mapParentTaskEntityToPojo(parentTaskEntity);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapTaskPojoToEntity() {

		int taskId = 333;
		int parentId = 1;
		int projectId = 1;
		int userId = 1;
		
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(taskId);
		taskPOJO.setTask("fake_task");
		taskPOJO.setStartDate(new Date());
		taskPOJO.setEndDate(new Date());
		taskPOJO.setPriority(10);
		taskPOJO.setParentId(parentId);
		taskPOJO.setProjectId(projectId);
		taskPOJO.setUserId(userId);
		
		TaskEntity result = mapper.mapTaskPojoToEntity(taskPOJO);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapTaskPojoToEntity_taskPOJO_null() {
		
		TaskPOJO taskPOJO = null;
		
		TaskEntity result = mapper.mapTaskPojoToEntity(taskPOJO);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapUserPojoToEntity() {
		
		UserPOJO userPOJO = new UserPOJO();
		userPOJO.setUserId(1);
		userPOJO.setFirstName("fake_firstName");
		userPOJO.setLastName("fake_lastName");
		userPOJO.setEmployeeId("fake_employeeId");
		
		UserEntity result = mapper.mapUserPojoToEntity(userPOJO);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapUserPojoToEntity_userPOJO_null() {
		
		UserPOJO userPOJO = null;
		
		UserEntity result = mapper.mapUserPojoToEntity(userPOJO);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapProjectPojoToEntity() {
		int projectId = 333;
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(projectId);
		projectPOJO.setProject("fake_project");
		projectPOJO.setStartDate(new Date());
		projectPOJO.setEndDate(new Date());
		projectPOJO.setPriority(10);
		projectPOJO.setUserId(1);
		
		ProjectEntity result = mapper.mapProjectPojoToEntity(projectPOJO);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapProjectPojoToEntity_projectPOJO_null() {
		
		ProjectPOJO projectPOJO = null;
		
		ProjectEntity result = mapper.mapProjectPojoToEntity(projectPOJO);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapTaskEntityToPojo() {
		
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
		taskEntity.setTaskId(Long.valueOf(taskId));
		taskEntity.setTask("fake_task");
		taskEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		taskEntity.setPriority(10);
		taskEntity.setParentTaskEntity(parentTaskEntityFromDB);
		taskEntity.setProjectEntity(projectEntityFromDB);
		taskEntity.setUserEntity(userEntityFromDB);
		
		TaskPOJO result = mapper.mapTaskEntityToPojo(taskEntity);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapTaskEntityToPojo_taskEntity_null() {
		
		TaskEntity taskEntity = null;
		
		TaskPOJO result = mapper.mapTaskEntityToPojo(taskEntity);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapUserEntityToPojo() {
		int userId = 333;
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(Long.valueOf(userId));
		userEntity.setFirstName("firstName");
		userEntity.setLastName("lastName");
		userEntity.setEmployeeId("employeeId");
		
		UserPOJO result = mapper.mapUserEntityToPojo(userEntity);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapUserEntityToPojo_userEntity_null() {

		UserEntity userEntity = null;
		
		UserPOJO result = mapper.mapUserEntityToPojo(userEntity);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testMapProjectEntityToPojo() {
		
		int userId = 333;
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(Long.valueOf(userId));
		userEntity.setFirstName("firstName");
		userEntity.setLastName("lastName");
		userEntity.setEmployeeId("employeeId");
		
		
		int projectId = 333;
		ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setProjectId(Long.valueOf(projectId));
		projectEntity.setProject("fake_project");
		projectEntity.setStartDate(new java.sql.Date(new Date().getTime()));
		projectEntity.setEndDate(new java.sql.Date(new Date().getTime()));
		projectEntity.setPriority(10);
		projectEntity.setUserEntity(userEntity);
		
		ProjectPOJO result = mapper.mapProjectEntityToPojo(projectEntity);
		Assert.assertNotNull(result);
	}

}

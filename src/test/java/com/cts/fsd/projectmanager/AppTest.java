package com.cts.fsd.projectmanager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.cts.fsd.projectmanager.mapper.ApplicationMapperObjectTest;
import com.cts.fsd.projectmanager.repo.ParentTaskRepositoryTest;
import com.cts.fsd.projectmanager.repo.ProjectRepositoryTest;
import com.cts.fsd.projectmanager.repo.TaskRepositoryTest;
import com.cts.fsd.projectmanager.repo.UserRepositoryTest;
import com.cts.fsd.projectmanager.service.ParentTaskServiceTest;
import com.cts.fsd.projectmanager.service.ProjectServiceTest;
import com.cts.fsd.projectmanager.service.TaskServiceTest;
import com.cts.fsd.projectmanager.service.UserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ParentTaskRepositoryTest.class,
	ProjectRepositoryTest.class,
	TaskRepositoryTest.class,
	UserRepositoryTest.class,
	UserServiceTest.class,
	ProjectServiceTest.class,
	TaskServiceTest.class,
	ParentTaskServiceTest.class,
	ApplicationMapperObjectTest.class
})
public class AppTest {



}

package com.cts.fsd.projectmanager.repo;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.fsd.projectmanager.entity.TaskEntity;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
    private TaskRepository taskRepository;


	@Test
	public void testDeleteTaskById() {
		Long taskId = new Long(33333);
		
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskId(taskId);
		entityManager.merge(taskEntity);
		
		taskRepository.deleteTaskById(taskId);
		
		Optional<TaskEntity> result = taskRepository.findById(taskId);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isPresent());
	}

}

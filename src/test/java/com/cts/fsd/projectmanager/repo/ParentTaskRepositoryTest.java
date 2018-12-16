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

import com.cts.fsd.projectmanager.entity.ParentTaskEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class ParentTaskRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
    private ParentTaskRepository parentTaskRepository;

	
	@Test
	public void testDeleteParentTaskById() {
		
		Long parentId = new Long(33333);
		
		ParentTaskEntity parentTaskEntity = new ParentTaskEntity();
		parentTaskEntity.setParentId(parentId);
		entityManager.merge(parentTaskEntity);
		

		parentTaskRepository.deleteParentTaskById(parentId);
		
		Optional<ParentTaskEntity> result = parentTaskRepository.findById(parentId);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isPresent());
	}

}

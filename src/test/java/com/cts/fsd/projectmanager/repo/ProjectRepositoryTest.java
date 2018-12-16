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

import com.cts.fsd.projectmanager.entity.ProjectEntity;



@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class ProjectRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
    private ProjectRepository projectRepository;


	@Test
	public void testDeleteProjectById() {
		Long projectId = new Long(33333);
		
		ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setProjectId(projectId);
		entityManager.merge(projectEntity);
		
		
		projectRepository.deleteProjectById(projectId);
		
		Optional<ProjectEntity> result = projectRepository.findById(projectId);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isPresent());
	}

}

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

import com.cts.fsd.projectmanager.entity.UserEntity;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
    private UserRepository userRepository;


	@Test
	public void testDeleteUserById() {
		Long userId = new Long(33333);
		
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(userId);
		entityManager.merge(userEntity);
		
		userRepository.deleteUserById(userId);
		
		Optional<UserEntity> result = userRepository.findById(userId);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isPresent());
	}

}

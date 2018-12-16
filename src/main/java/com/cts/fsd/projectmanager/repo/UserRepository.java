package com.cts.fsd.projectmanager.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.fsd.projectmanager.entity.UserEntity;


/**
 * @author Amitabha Das [420652]
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	
	/**
	 * Deletes a specific User Record from the Task Table based on a parent id
	 * @param parentId
	 */
	@Transactional
    @Modifying
    @Query("delete from UserEntity p where p.userId=:id")
    public void deleteUserById(@Param("id") Long userId);
}

package com.cts.fsd.projectmanager.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.fsd.projectmanager.entity.ParentTaskEntity;


/**
 * @author Amitabha Das [420652]
 *
 */
@Repository
public interface ParentTaskRepository extends JpaRepository<ParentTaskEntity, Long>{
	
	
	/**
	 * Deletes a specific ParentTask Record from the Task Table based on a parent id
	 * @param parentId
	 */
	@Transactional
    @Modifying
    @Query("delete from ParentTaskEntity p where p.parentId=:id")
    public void deleteParentTaskById(@Param("id") Long parentId);
}

package com.cts.fsd.projectmanager.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.fsd.projectmanager.entity.ProjectEntity;


/**
 * @author Amitabha Das [420652]
 *
 */
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long>{
	
	
	/**
	 * Deletes a specific ParentTask Record from the Task Table based on a parent id
	 * @param parentId
	 */
	@Transactional
    @Modifying
    @Query("delete from ProjectEntity p where p.projectId=:id")
    public void deleteProjectById(@Param("id") Long projectId);
}

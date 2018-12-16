package com.cts.fsd.projectmanager.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.fsd.projectmanager.entity.TaskEntity;


/**
 * @author Amitabha Das [420652]
 *
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long>{

	/**
	 * Deletes a specific Task Record from the Task Table based on a task id
	 * @param taskId
	 */
	@Transactional
    @Modifying
    @Query("delete from TaskEntity t where t.taskId=:id")
    public void deleteTaskById(@Param("id") Long taskId);
	
	/**
	 * Gets List of Task from the Task Table based on a projectId
	 * @param taskId
	 */
//	@Transactional
//    @Modifying
    @Query("select t from TaskEntity t where t.projectEntity.projectId=:id")
    public List<TaskEntity> listTaskByProjectId(@Param("id") Long projectId);
}

package com.cts.fsd.projectmanager.pojo;


import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author Amitabha Das [420652]
 * TaskPOJO Object that interacts with UI and Controller
 */
@Component
public class TaskPOJO {
	
	private int taskId;
	
	private String task;

	private String parentTask;
	
	private Date startDate;
	
	private Date endDate;
	
	private int priority;
	
	private int parentId;
	
	private int projectId;
	
	private int userId;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getParentTask() {
		return parentTask;
	}

	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TaskPOJO [taskId=" + taskId + ", task=" + task + ", parentTask=" + parentTask + ", startDate="
				+ startDate + ", endDate=" + endDate + ", priority=" + priority + ", parentId=" + parentId
				+ ", projectId=" + projectId + ", userId=" + userId + "]";
	}
	
}

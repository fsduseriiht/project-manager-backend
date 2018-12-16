package com.cts.fsd.projectmanager.pojo;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author Amitabha Das [420652]
 * ProjectPOJO Object that interacts with UI and Controller
 */
@Component
public class ProjectPOJO {
	
	private int projectId;

	private String project;

	private Date startDate;
	
	private Date endDate;
	
	private int priority;
	
	private int userId;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ProjectPOJO [projectId=" + projectId + ", project=" + project + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", priority=" + priority + ", userId=" + userId + "]";
	}
}

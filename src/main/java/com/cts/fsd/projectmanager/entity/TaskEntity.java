package com.cts.fsd.projectmanager.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Amitabha Das [420652]
 *
 */
@Entity
@Table(name = "TASK_TABLE")
public class TaskEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "TASK_ID")
	private long taskId;
	
	
	@Column(name = "TASk")
	private String task;
	
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	
	@Column(name = "PRIORITY")
	private int priority;
	
	@OneToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "PARENT_ID" )
	private ParentTaskEntity parentTaskEntity;
	
	
	@OneToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "PROJECT_ID" )
	private ProjectEntity projectEntity;
	
	
	@OneToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "USER_ID" )
	private UserEntity userEntity;


	public long getTaskId() {
		return taskId;
	}


	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}


	public String getTask() {
		return task;
	}


	public void setTask(String task) {
		this.task = task;
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


	public ParentTaskEntity getParentTaskEntity() {
		return parentTaskEntity;
	}


	public void setParentTaskEntity(ParentTaskEntity parentTaskEntity) {
		this.parentTaskEntity = parentTaskEntity;
	}


	public ProjectEntity getProjectEntity() {
		return projectEntity;
	}


	public void setProjectEntity(ProjectEntity projectEntity) {
		this.projectEntity = projectEntity;
	}


	public UserEntity getUserEntity() {
		return userEntity;
	}


	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	@Override
	public String toString() {
		return "TaskEntity [taskId=" + taskId + ", task=" + task + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", priority=" + priority + ", parentTaskEntity=" + parentTaskEntity + ", projectEntity="
				+ projectEntity + ", userEntity=" + userEntity + "]";
	}

}

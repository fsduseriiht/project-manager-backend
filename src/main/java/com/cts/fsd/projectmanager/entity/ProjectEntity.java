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
@Table(name = "PROJECT_TABLE")
public class ProjectEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "PROJECT_ID")
	private long projectId;
	

	@Column(name = "PROJECT")
	private String project;


	@Column(name = "START_DATE")
	private Date startDate;
	

	@Column(name = "END_DATE")
	private Date endDate;
	
	
	@Column(name = "PRIORITY")
	private int priority;
	
	
	@OneToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "USER_ID" )
	private UserEntity userEntity;


	public long getProjectId() {
		return projectId;
	}


	public void setProjectId(long projectId) {
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


	public UserEntity getUserEntity() {
		return userEntity;
	}


	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	@Override
	public String toString() {
		return "ProjectEntity [projectId=" + projectId + ", project=" + project + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", priority=" + priority + ", userEntity=" + userEntity + "]";
	}

}

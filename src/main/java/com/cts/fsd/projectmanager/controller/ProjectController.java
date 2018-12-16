package com.cts.fsd.projectmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fsd.projectmanager.pojo.ProjectPOJO;
import com.cts.fsd.projectmanager.service.ProjectService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Amitabha Das [420652]
 *
 */
@RestController
@RequestMapping("/project")
@CrossOrigin("*")
public class ProjectController {
	
	@Autowired
	ProjectService projectService;

	/**
	 * createProjectDump() is used to create a data dump in the db if the db is empty
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/dump", method = RequestMethod.GET)
	public ResponseEntity<String> createProjectDump() {
		
		Gson gson = new Gson();
		String projectDump = 
					"[ "
				+	"{\"project\":\"project1\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":1,\"userId\":2},"
				+	"{\"project\":\"project2\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":2,\"userId\":2},"
				+ 	"{\"project\":\"project3\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":3,\"userId\":1},"
				+ 	"{\"project\":\"project4\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":3,\"userId\":2},"
				+	"{\"project\":\"project5\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":5,\"userId\":1},"
				+ 	"{\"project\":\"project6\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":10,\"userId\":2}"
				+ 	"]";
		List<ProjectPOJO> projectPOJOList = gson.fromJson(projectDump, new TypeToken<List<ProjectPOJO>>(){}.getType());
		
		// display ParentTask Dump in console
		projectPOJOList.forEach((pojo)-> {System.out.println(pojo);});
		System.out.println("creating Project dump in db = " + projectPOJOList.toString());
		
		List<ProjectPOJO> dbResponse = projectService.createProjects(projectPOJOList);
		
		return new ResponseEntity<String>("Project Dumps Saved to Database..." + dbResponse , HttpStatus.OK);
	}
	
	
	/**
	 * listProjects() is used to display all the ParentTask Entity values in the db
	 * @return ResponseEntity<List<ProjectPOJO>>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProjectPOJO>> listProjects() {
		
		System.out.println("getting all the projects from database...");
		
		List<ProjectPOJO> projectsFromDB = projectService.getAllProjects();
		
		return new ResponseEntity<List<ProjectPOJO>>(projectsFromDB , HttpStatus.OK);
    }
	
	
	/**
	 * createProject() is used to create a parent task in  the db
	 * @param projectPOJO
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> createProject(
							@RequestBody ProjectPOJO projectPOJO	) {
		
		System.out.println("Project to be added to DB = " + projectPOJO.toString());
		
		List<ProjectPOJO> projectPOJOList = new ArrayList<ProjectPOJO>();
		projectPOJOList.add(projectPOJO);
		
		// display Parent Task To be Created in console
		projectPOJOList.forEach((pojo)-> {System.out.println(pojo);});
		System.out.println("adding new Parent Task to db = " + projectPOJOList.toString());
		
		List<ProjectPOJO> dbResponse = projectService.createProjects(projectPOJOList);
		
		return new ResponseEntity<String>("New Project Saved to Database..." + dbResponse , HttpStatus.OK);
	}
	
	
	/**
	 * updateProject() is used to update a project into the db
	 * @param projectId
	 * @param projectPOJO
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> updateProject( 
			@PathVariable(value = "id") int projectId ,
			@RequestBody ProjectPOJO projectPOJO	) {
		
		System.out.println("ProjectPOJO to be updated which is coming in request = " + projectPOJO.toString());
		
		ProjectPOJO dbResponse = null;
		if(projectId == projectPOJO.getProjectId()) {
			dbResponse = projectService.editProjectById(projectId , projectPOJO);
		}
		
		if(dbResponse != null) {
			return new ResponseEntity<String>("Project[project id = "+projectId+"] Updated in Database..." + dbResponse , HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Project[project id = "+projectId+"] NOT Updated in Database as it does not exist..."  + dbResponse , HttpStatus.OK);
		}
	}
	
	
	/**
	 * deleteProject() is used to delete a project from the db
	 * @param projectId
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteProject( 
			@PathVariable(value = "id") int projectId ) {

		boolean dbResponse = projectService.removeProjectById(projectId);
		
		if(dbResponse) {
			return new ResponseEntity<String>("Project[project id = "+projectId+"] Deleted from database..." , HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Project[project id = "+projectId+"] Not Deleted from database as it does not exist..." , HttpStatus.OK);
		}
	}
	
}

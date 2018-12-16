package com.cts.fsd.projectmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fsd.projectmanager.pojo.ParentTaskPOJO;
import com.cts.fsd.projectmanager.pojo.TaskPOJO;
import com.cts.fsd.projectmanager.service.ParentTaskService;
import com.cts.fsd.projectmanager.service.TaskService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Amitabha Das [420652]
 *
 */
@RestController
@RequestMapping("/task")
@CrossOrigin("*")
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	ParentTaskService parentTaskService;
	
	/**
	 * createTaskDump() is used to create database dump for task table if it does not exist in database
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/dump", method = RequestMethod.GET)
	public ResponseEntity<String> createTaskDump() {
		Gson gson = new Gson();
		
		String taskDump = "["
		+ "{\"task\":\"taskName1\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":11,\"parentId\":-1,\"projectId\":1,\"userId\":3},"
		+ "{\"task\":\"taskName2\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":12,\"parentId\":-1,\"projectId\":2,\"userId\":3},"
		+ "{\"task\":\"taskName3\",\"startDate\":\"2018-09-09T18:30:00.000+0000\",\"endDate\":\"2018-09-09T18:30:00.000+0000\",\"priority\":10,\"parentId\":1,\"projectId\":2,\"userId\":3},"
		+ "{\"task\":\"taskName4\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":10,\"parentId\":2,\"projectId\":1,\"userId\":3},"
		+ "{\"task\":\"taskName5\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":1,\"parentId\":5,\"projectId\":4,\"userId\":6},"
		+ "{\"task\":\"taskName6\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":20,\"parentId\":5,\"projectId\":1,\"userId\":6},"
		+ "{\"task\":\"taskName7\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":10,\"parentId\":-1,\"projectId\":2,\"userId\":6},"
		+ "{\"task\":\"taskName8\",\"startDate\":\"2018-09-08T18:30:00.000+0000\",\"endDate\":\"2018-09-08T18:30:00.000+0000\",\"priority\":11,\"parentId\":-1,\"projectId\":-1,\"userId\":6}"
						+ "]";
		List<TaskPOJO> taskPOJOList = gson.fromJson(taskDump, new TypeToken<List<TaskPOJO>>(){}.getType());
		
		// display Task Dump in console
		taskPOJOList.forEach((pojo)-> {System.out.println(pojo);});
		System.out.println("creating Task dump in db = " + taskPOJOList.toString());
		
		List<TaskPOJO> dbResponse = taskService.createTasks(taskPOJOList);

		return new ResponseEntity<String>("Task Dumps Saved to Database..." + dbResponse , HttpStatus.OK);
	}
	
	
	/**
	 * listTasks() is used to display all records in task table db
	 * @return ResponseEntity<List<TaskPOJO>>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<TaskPOJO>> listTasks() {
		
		System.out.println("getting all the tasks from database...");
		
		List<TaskPOJO> tasksFromDB = taskService.getAllTasks();
		
		return new ResponseEntity<List<TaskPOJO>>(tasksFromDB , HttpStatus.OK);
    }
	
	/**
	 * listTaskByProjectID() is used to display all records in task table db for a particular project id
	 * @return ResponseEntity<List<TaskPOJO>>
	 */
	@RequestMapping(value = "/list/{pid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<TaskPOJO>> listTaskByProjectID(
			@PathVariable(value = "pid") int projectId) {
		
		System.out.println("getting all the tasks from database under the project id [" + projectId + "]...");
		
		List<TaskPOJO> tasksFromDB = taskService.getAllTasks(projectId);
		
		return new ResponseEntity<List<TaskPOJO>>(tasksFromDB , HttpStatus.OK);
    }
	
	/**
	 * tasksPerProject() is used to display all records in task table db
	 * @return ResponseEntity<Map<Integer , Integer>>
	 * 
	 */
	@RequestMapping(value = "/countperproject", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Map<Integer , Integer>> tasksPerProject() {
		
		System.out.println("getting count of the tasks per project from database...");
		
		List<TaskPOJO> tasksFromDB = taskService.getAllTasks();
		
		Map<Integer , Integer> tasksPerProjectMap = taskService.getTasksPerProject(tasksFromDB);
		
		return new ResponseEntity<Map<Integer , Integer>>(tasksPerProjectMap , HttpStatus.OK);
    }
	
	/**
	 * createTask() is used to create a single record in task table in db
	 * @param taskPOJO
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> createTask(
							@RequestBody TaskPOJO taskPOJO	) {
		
		System.out.println("Task to be added to DB = " + taskPOJO.toString());
		
		List<TaskPOJO> taskPOJOList = new ArrayList<TaskPOJO>();
		taskPOJOList.add(taskPOJO);
		
		// display Task To be Created in console
		taskPOJOList.forEach((pojo)-> {System.out.println(pojo);});
		
		System.out.println("adding Task to db = " + taskPOJOList.toString());
		
		List<TaskPOJO> dbResponse = taskService.createTasks(taskPOJOList);
		
		return new ResponseEntity<String>("New Task Saved to Database..." + dbResponse , HttpStatus.OK);
	}
	
	/**
	 * updateTask() is used to update the task table single record in db 
	 * @param taskId
	 * @param taskPOJO
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> updateTask(
												@PathVariable(value = "id") int taskId , 
												@RequestBody TaskPOJO taskPOJO			) {
		
		System.out.println("Task to be updated which is coming in request = " + taskPOJO.toString());
		
		TaskPOJO dbResponse = null;
		if (taskId == taskPOJO.getTaskId()) {
			dbResponse = taskService.editTaskById(taskId , taskPOJO);
		}
		
		if(dbResponse != null) {
			return new ResponseEntity<String>("Task[task id = "+taskId+"] Updated in Database..." + dbResponse , HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Task[task id = "+taskId+"] NOT Updated in Database as it does not exist..."  + dbResponse , HttpStatus.OK);
		}
		
	}
	
	
	
	/**
	 * deleteTask() is used to delete record from TaskTable in db
	 * @param taskId
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTask( 
											@PathVariable(value = "id") int taskId ) {

		boolean dbResponse = taskService.removeTaskById(taskId);
		
		if(dbResponse) {
			return new ResponseEntity<String>("ParentTask[parent id = "+taskId+"] Deleted from database..." , HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("ParentTask[parent id = "+taskId+"] Not Deleted from database as it does not exist..." , HttpStatus.OK);
		}
	}
	
	/**
	 * convertTaskToParent() is used to convert Task To Parent Task 
	 * @param taskId
	 * @param taskPOJO
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/convert/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> convertTaskToParent(
												@PathVariable(value = "id") int taskId , 
												@RequestBody TaskPOJO taskPOJO			) {

		System.out.println("Task to be converted to parent task which is coming in request = " + taskPOJO.toString());
		
		boolean dbResponse = false;
		
		ParentTaskPOJO parentTaskPOJO = null;
		
		List<ParentTaskPOJO> dbParentResponse = null;
		
		int parentId = -1;
		
		if (taskId == taskPOJO.getTaskId()) {
			parentTaskPOJO = new ParentTaskPOJO();
			parentTaskPOJO.setParentTask(taskPOJO.getTask());
			
			List<ParentTaskPOJO> parentTaskPOJOList = new ArrayList<ParentTaskPOJO>();
			parentTaskPOJOList.add(parentTaskPOJO);
			System.out.println("adding new Parent Task to db = " + parentTaskPOJOList.toString());
			
			dbParentResponse = parentTaskService.createParentTasks(parentTaskPOJOList);
			if( (dbParentResponse != null && !dbParentResponse.isEmpty())  ) {
				parentId = dbParentResponse.get(0).getParentId();
			}
			if(parentId >= 0  ) {
				dbResponse = taskService.removeTaskById(taskId);
			}
		}
		
		if(dbResponse) {
			return new ResponseEntity<String>("ParentTask[parent id = "+parentId+"] Created for the Old Task with ID - "+taskId+"..." , HttpStatus.OK);
		} else {
			if(parentId < 0 ) {
				return new ResponseEntity<String>("ParentTask Not Created in database..." , HttpStatus.OK);
			}
			if( !dbResponse ) {
				return new ResponseEntity<String>("Task[id = "+taskId+"] Not Deleted from database as it does not exist..." , HttpStatus.OK);
			}
			return new ResponseEntity<String>("Task not converted to ParentTask Properly in database..." , HttpStatus.OK);
		}
		
	}
	
}

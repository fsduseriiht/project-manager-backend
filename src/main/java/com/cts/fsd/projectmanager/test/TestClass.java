/**
 * 
 */
package com.cts.fsd.projectmanager.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cts.fsd.projectmanager.pojo.ParentTaskPOJO;
import com.cts.fsd.projectmanager.pojo.ProjectPOJO;
import com.cts.fsd.projectmanager.pojo.TaskPOJO;
import com.cts.fsd.projectmanager.pojo.UserPOJO;
import com.google.gson.Gson;

/**
 * @author Amitabha Das [420652]
 * TestClass is used to perform POC work
 */
public class TestClass {

	/**
	 * main() method is perform rough work within the application for any POC or Test purpose
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ParseException, IOException {
//		String content = "Hello World !!";
//		Files.write(Paths.get("./src/main/java/com/cts/fsd/tasktracker/test/Test.txt"), content.getBytes());
		
		
		Gson gson = new Gson();
		Date today = new Date();
		
		List<TaskPOJO> taskPOJOList  = new ArrayList<>();
		TaskPOJO taskPOJO = new TaskPOJO();
		taskPOJO.setTaskId(0);
		taskPOJO.setTask("taskName");
		taskPOJO.setParentId(0);
		taskPOJO.setParentTask("parentTask");
		taskPOJO.setProjectId(0);
		taskPOJO.setUserId(-1);
		taskPOJO.setStartDate(today);
		taskPOJO.setEndDate(today);
		taskPOJO.setPriority(0);
		taskPOJOList.add(taskPOJO);
		String taskSampleJson = gson.toJson(taskPOJOList);
		System.out.println("task Sample Json = \n" + taskSampleJson);
		

		List<ParentTaskPOJO> parentTaskPOJOList  = new ArrayList<>();
		ParentTaskPOJO parentTaskPOJO = new ParentTaskPOJO();
		parentTaskPOJO.setParentId(0);
		parentTaskPOJO.setParentTask("parentTask");
		parentTaskPOJOList.add(parentTaskPOJO);
		String parentTaskSampleJson = gson.toJson(parentTaskPOJOList);
		System.out.println("parent Task Sample Json = \n" + parentTaskSampleJson);
		
		List<ProjectPOJO> projectPOJOList  = new ArrayList<>();
		ProjectPOJO projectPOJO = new ProjectPOJO();
		projectPOJO.setProjectId(0);
		projectPOJO.setProject("project");
		projectPOJO.setStartDate(today);
		projectPOJO.setEndDate(today);
		projectPOJO.setPriority(0);
		projectPOJO.setUserId(-1);
		projectPOJOList.add(projectPOJO);
		String projectSampleJson = gson.toJson(projectPOJOList);
		System.out.println("project Sample Json = \n" + projectSampleJson);
		

		List<UserPOJO> usersPOJOList  = new ArrayList<>();
		UserPOJO usersPOJO = new UserPOJO();
		usersPOJO.setUserId(0);
		usersPOJO.setFirstName("firstName");
		usersPOJO.setLastName("lastName");
		usersPOJO.setEmployeeId("employeeId");
		
		usersPOJOList.add(usersPOJO);
		String usersSampleJson = gson.toJson(usersPOJOList);
		System.out.println("users Sample Json = \n" + usersSampleJson);
		
		String userDump = "[{\"firstName\": \"test_firstName1\",\"lastName\": \"test_lastName1\",\"employeeId\": \"test_employeeId1\",\"projectId\": -1,\"taskId\": -1},"
				+ "{\"firstName\": \"test_firstName2\",\"lastName\": \"test_lastName2\",\"employeeId\": \"test_employeeId2\",\"projectId\": -1,\"taskId\": -1},"
				+ "{\"firstName\": \"test_firstName3\",\"lastName\": \"test_lastName3\",\"employeeId\": \"test_employeeId3\",\"projectId\": -1,\"taskId\": -1},"
				+ "{\"firstName\": \"test_firstName4\",\"lastName\": \"test_lastName4\",\"employeeId\": \"test_employeeId4\",\"projectId\": -1,\"taskId\": -1},"
				+ "{\"firstName\": \"test_firstName5\",\"lastName\": \"test_lastName5\",\"employeeId\": \"test_employeeId5\",\"projectId\": -1,\"taskId\": -1},"
				+ "{\"firstName\": \"test_firstName6\",\"lastName\": \"test_lastName6\",\"employeeId\": \"test_employeeId6\",\"projectId\": -1,\"taskId\": -1}]";

		
		String userDump2 = "[{\"firstName\":\"test_firstName1\",\"lastName\":\"test_lastName1\",\"employeeId\":\"test_employeeId1\"},{\"firstName\":\"test_firstName2\",\"lastName\":\"test_lastName2\",\"employeeId\":\"test_employeeId2\"},{\"firstName\":\"test_firstName3\",\"lastName\":\"test_lastName3\",\"employeeId\":\"test_employeeId3\"},{\"firstName\":\"test_firstName4\",\"lastName\":\"test_lastName4\",\"employeeId\":\"test_employeeId4\"},{\"firstName\":\"test_firstName5\",\"lastName\":\"test_lastName5\",\"employeeId\":\"test_employeeId5\"},{\"firstName\":\"test_firstName6\",\"lastName\":\"test_lastName6\",\"employeeId\":\"test_employeeId6\"}]";
		
		System.out.println("userDump = " + userDump );
		
		
		System.out.println("userDump2 = " + userDump2 );
		
		
	}

}

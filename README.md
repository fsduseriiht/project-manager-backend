# project-manager-backend

## Information on the services exposed - 
#### URL 
-----------------------
	{{baseurl}} = the context url with dns and port and application context
	
	Example - 
		Local PC/Docker - http://localhost:8080/projectmanagerbackend/
		Docker Tool Box for older windows - http://192.168.99.100:8080/projectmanagerbackend/


#### IF DATABASE IS EMPTY WE NEED TO hit the following services in the order given
-----------------------
###### The following services can be hit from Browser as well after bringing up the service, since, they are normal GET methods. The POSTMAN Collection can be imported into POSTMAN from the location - 

###### project-manager-backend/other-resources/postman-import-json_for_docker_toolbox/

###### project-manager-backend/other-resources/postman-import-json_for_localhost/

	{{baseurl}}user/dump
	{{baseurl}}parent/dump
	{{baseurl}}project/dump
	{{baseurl}}task/dump
	
	
#### User_TABLE
-----------------------
	GET METHOD - {{baseurl}}user/dump - to pre-populate db with user details dump
	GET METHOD - {{baseurl}}user/list - is used to display all records in user table db
	POST METHOD - {{baseurl}}user/ - is used to create a single user record in user table in db
	PUT METHOD - {{baseurl}}user/{{userId}} - is used to update the user table single record in db 
	DEL METHOD - {{baseurl}}user/{{userId}} - is used to delete single user record from user table in db
#### Parent_TABLE
-----------------------
	GET METHOD - {{baseurl}}parent/dump - to pre-populate db with parent task details dump
	GET METHOD - {{baseurl}}parent/list - is used to display all the parent task values in the db
	POST METHOD - {{baseurl}}parent/ - is used to create a parent task in  the db
	PUT METHOD - {{baseurl}}parent/{{parentId}} - is used to update a parent task in the db
	DEL METHOD - {{baseurl}}parent/{{parentId}} - is used to delete a parent task from the db

#### Project_TABLE 
-----------------------
	GET METHOD - {{baseurl}}project/dump - to pre-populate db with project details dump
	GET METHOD - {{baseurl}}project/list - is used to display all records in project table db
	POST METHOD - {{baseurl}}project/ - is used to create a single project record in project table in db
	PUT METHOD - {{{baseurl}}project/{{projectId}} - is used to update the project table single record in db 
	DEL METHOD - {{baseurl}}project/{{projectId}} - is used to delete single project record from project table in db

#### Task_TABLE 
-----------------------
	GET METHOD - {{baseurl}}task/dump - to pre-populate db with task details dump
	GET METHOD - {{{baseurl}}task/list - is used to display all records in task table db
	GET METHOD - {{baseurl}}task/countperproject - is used to display count of records in task table db for each project
	GET METHOD - {{baseurl}}task/list/{{projectId}} - is used to display records in task table db for particular {{projectId}}
	POST METHOD - {{baseurl}}task/ - is used to create a single task record in task table in db
	PUT METHOD - {{baseurl}}task/{{taskId}} - is used to update the task table single record in db
	PUT METHOD - {{baseurl}}task/convert/{{taskId}} - is used to convert task with {{taskId}} in task table to a parent task, thereby purging the task details in task table
	DEL METHOD - {{baseurl}}task/{{taskId}} - is used to delete record from TaskTable in db



### useful links related to docker:
<br> https://cloud.docker.com/repository/docker/amitabhadockerwork/project-manager-backend

### Docker commands
<br>  docker pull amitabhadockerwork/project-manager-ui
<br>  docker pull amitabhadockerwork/project-manager-backend



Docker for Service Layer - Project Manager Project
-----------------------------------------------------------------------
General Commands to be executed in Docker related to push/build/run- 
=======================================================================
	docker build -t your_image_name your_repo_url
	docker tag your_image_name docker_username/your_image_name
	docker push your_image_name docker_username/your_image_name
	docker run --rm -i -t -d -p 8080:8080/tcp -p 8080:8080/udp --name your_image_name your_image_id_value

Example for push/build/run- 
=======================================================================
	docker build -t project-manager-backend https://github.com/fsduseriiht/project-manager-backend.git
	docker tag project-manager-backend amitabhadockerwork/project-manager-backend
	docker push amitabhadockerwork/project-manager-backend
	docker run --rm -i -t -d -p 8080:8080/tcp -p 8080:8080/udp --name project-manager-backend_running <image_id>
	
	
	
	
	

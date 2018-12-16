# project-manager-backend

## Information on the services exposed - 
#### URL 
-----------------------
	{{baseurl}} = the context url with dns and port and application context
	Example - 
		Local PC/Docker - http://localhost:8080/projectmanagerbackend/
		Docker Tool Box for older windows - http://192.168.99.100:8080/projectmanagerbackend/
#### User module 
-----------------------
	Content to be written yet

#### Project module 
-----------------------
	Content to be written yet

#### Task module 
-----------------------
	GET METHOD - {{baseurl}}task/dump - Used to create the TASK_TABLE database dump with sample values as H2 db is used (only if table does not exist - also first execute dump of PARENT_TASK_TABLE if not exist)
	GET METHOD - {{baseurl}}task/list - is used to display all records in task table db
	POST METHOD - {{baseurl}}task/ - is used to create a single task record in task table in db
	PUT METHOD - {{baseurl}}task/:taskId - is used to update the task table single record in db 
	DEL METHOD - {{baseurl}}task/:taskId - is used to delete record from TaskTable in db
#### Parent module
-----------------------
	GET METHOD - {{baseurl}}parent/dump - Used to create the PARENT_TASK_TABLE database dump with sample values as H2 db is used (only if table does not exist)
	GET METHOD - {{baseurl}}parent/list - is used to display all the ParentTask values in the db
	POST METHOD - {{baseurl}}parent/ - is used to create a parent task in  the db
	PUT METHOD - {{baseurl}}parent/:parentId - is used to update a parent task in the db
	DEL METHOD - {{baseurl}}parent/:parentId - is used to delete a parent task from the db








### useful links related to docker:
<br> https://docs.docker.com/docker-cloud/builds/push-images/
<br> https://docs.docker.com/docker-hub/builds/
<br> https://hub.docker.com/r/amitabhadockerwork/

### Docker commands
<br>  docker pull amitabhadockerwork/project-manager-ui
<br>  docker pull amitabhadockerwork/project-manager-svc



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
	Content to be written yet

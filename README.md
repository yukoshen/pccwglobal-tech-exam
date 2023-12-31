# PCCWGlobal Technical Exam

Springboot Version: 2.7.13 </br>
Java Version: Java 11 </br>
Database: In memory database H2

### Add a new user </br>
Endpoint: http://localhost:8080/v1/user/add </br>
Method: POST </br>
Content-Type: application/json</br>
Request Body:</br>
```json
{
    "firstName": "Kobe",
    "lastName": "Bryant",
    "email": "blackmamba@gmail.com"
}
```

### Get all users </br>
Endpoint: http://localhost:8080/v1/user/get </br>
Method: GET </br>

### Get user by id </br>
Endpoint: http://localhost:8080/v1/user/get/{id} </br>
Method: GET </br>

### Update a user </br>
Endpoint: http://localhost:8080/v1/user/update/{id} </br>
Method: PUT </br>
Content-Type: application/json</br>
Request Body:</br>
```json
{
    "firstName": "Kobe Bean",
    "lastName": "Bryant",
    "email": "blackmamba@gmail.com"
}
```
### Delete a user by id </br>
Endpoint: http://localhost:8080/v1/user/delete/{id} </br>
Method: DELETE </br></br>

### Delete multiple users </br>
Endpoint: http://localhost:8080/v1/user/delete?ids=1,2,3 </br>
Method: DELETE </br>
Request Parameter / Query Params: Key = ids, Value = 1,2,3</br></br></br>
### Running the application via Docker
Visit this link in dockerhub - https://hub.docker.com/repository/docker/kjsiador/pccw-tech-exam/general <br>
1. Pull the image from docker hub by running this command
```Poweshell
docker image pull kjsiador/pccw-tech-exam
```
2. Check if you successfully pulled the docker image by running this command
```Poweshell
docker images
```
3. Run the image in your local powershell by running this command
```Poweshell
docker container run -p 8080:8080 kjsiador/pccw-tech-exam
```



# API MAPPING

**$HOST = 34.101.226.78:5000**

|  Nama API  |   Routes API          |     Request Data         |        Response Data             |
|------------|-----------------------|--------------------------|----------------------------------|
| Test API   | $HOST/                | ANY ->                   | - message: "hello world"         |
|            |                       |                          |                                  |
|            | $HOST/getUsers        | GET -> User              | - status code : 200              |
|            |                       |                          | - message: "Get all data user"   |
|            |                       |                          | - User                           |
|            |                       |                          |                                  |
| API Auth   | $HOST/signup          | POST -> User             | - status code : 201              |
|            |                       | - username : string      | - message : "User created"       |
|            |                       | - password : string      | - User                           |
|            |                       |                          |                                  |
|            | $HOST/login           | POST -> User             | - status code : 200              |
|            |                       | - username : string      | - message: "User has logged in"  |
|            |                       | - password : string      | - User                           |
|            |                       |                          |                                  |
|            | $HOST/logout          | GET -> User              | - status code : 200              |
|            |                       |                          | - message: "User has logged out" |
|            |                       |                          |                                  |
| API User   | $HOST/user/&lt;id&gt; | GET -> User              | - status code : 200              |
|            |                       |                          | - message: "Get user data"       |
|            |                       |                          | - User                           |
|            |                       |                          |                                  |
|            |                       | PUT  -> User             | - status code : 200              |
|            |                       | - username : string      | - message: "User has updated"    |
|            |                       | - password : string      | - User                           |
|            |                       |                          |                                  |
|            |                       | DELETE  -> User          | - status code : 200              |
|            |                       |                          | - message: "User has deleted"    |
|            |                       |                          |                                  |
| API Model  | $HOST/predict         | POST -> Model            | - status code : 200              |
|            |                       | - images : image file    | - message: "prediction success"  |
|            |                       |   or string(base65 url)  | - Model                          |
|            |                       |                          |                                  |

# Database Mapping

| Table |     Column     |
|-------|----------------|
| User  | id             |
|       | username       |
|       | password       |
|       | email          |
|       | profile_image  |
|       |                |
| Model | id             |
|       | name           |
|       | description    |
|       | solution       |
|       |                |
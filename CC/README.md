# API MAPPING

**$HOST = TBA

|  Nama API  |   Routes API   |     Request Data     |        Response Data             |
|------------|----------------|----------------------|----------------------------------|
| Test API   | $HOST/         | ANY ->               | - message: "hello world"         |
|            |----------------|----------------------|----------------------------------|
|            | $HOST/getUsers | GET -> User          | - User.id                        |
|            |                |                      | - User.username                  |
|------------|----------------|----------------------|----------------------------------|
| API Auth   | $HOST/signup   | POST -> User         | - status code : 201              |
|            |                | - username : string  | - message : "User created"       |
|            |                | - password : string  | - User.username                  |
|            |----------------|----------------------|----------------------------------|
|            | $HOST/login    | POST -> User         | - status code : 200              |
|            |                | - username : string  | - message: "User has logged in"  |
|            |                | - password : string  | - User.username                  |
|            |----------------|----------------------|----------------------------------|
|            | $HOST/logout   | GET -> User          | - status code : 200              |
|            |                |                      | - message: "User has logged out" |
|------------|----------------|----------------------|----------------------------------|
| API Model  | $HOST/predict  | POST -> Model        | - status code : 200              |
|            |                | - images : string    | - Model.id                       |
|            |                |   (base65 url)       | - Model.name                     |
|            |                |                      | - Model.description              |
|            |                |                      | - Model.solution                 |
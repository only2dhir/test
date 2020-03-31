# Getting Started

It's a spring boot based project with spring security integrated to provide role based authentication and authorisation.
It uses JWT for token solution.

* There are different packages such as controller, entity, service, dao, etc for each components.
* Individual controller class for User, Role, Auth and Resource entity.
* com.locus.assignment.security has all the security related filters and configurations.
* It has a generic global exception handler.
* A global API response implementation.
* Uses Facade pattern to get logged in user token anywhere in the service class.
* JwtUtil.java has all the util methods for token.
* Passwords in the DB are saved with Bcrypt hashing.
* Different actuators endpoints for application health monitoring.
http://localhost:8080/actuator/health

### To run the application, simply run the main class AssignmentApplication.java as a main class. It will run an in-memory tomcat and H2 DB.

* It uses CommandLineRunner to populate initial admin credentials.
* After running the app, you can try login at http://localhost:8080/auth/login
* {"username": "admin", 
"password": "admin"}
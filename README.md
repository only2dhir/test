# Getting Started

It's a spring boot based project with spring security integrated to provide role based authentication and authorisation.
It uses JWT for token solution.

* There are separate packages such as controller, entity, service, dao, etc for each components.
* Mockito test cases are covered under test packages.
* Individual controller class for User, Role, Auth and Resource entity.
* com.locus.assignment.security has all the security related filters and configurations.
* #####Added custom annotation implementation for method level access control.
* It has a generic global exception handler.
* A global REST API response implementation.
* Uses Facade pattern to get logged in user token anywhere in the service classes.
* JwtUtil.java has all the util methods for token.
* Passwords in the DB are saved with Bcrypt hashing.
* Different actuators endpoints for application health monitoring.
http://localhost:8080/actuator/health

### To run the application, simply run the main class AssignmentApplication.java as a main class. It will run an in-memory tomcat and H2 DB.

* It uses CommandLineRunner to populate initial admin credentials.
* After running the app, you can try login at http://localhost:8080/auth/login
* {"username": "admin", 
"password": "admin"}

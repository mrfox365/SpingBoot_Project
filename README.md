Classroom Management System

This program is a classroom management system designed for efficient management of student and course data.

Installation and Setup Instructions
To ensure proper functioning of the program, please follow these steps:

PostgreSQL Installation
Firstly, you need to install PostgreSQL. You can find the relevant instructions for your operating system on the official PostgreSQL website.

Database Creation
After installing PostgreSQL, create a local database named "classroom".
CREATE DATABASE classroom;

Connect to the Newly Created Database
Using the command line (\c classroom) or programming functionality.

Script Execution
Execute the script from Appendix 1 in the already created "classroom" database. This script contains the necessary table structure and initial data.

Spring Boot Maven Installation
Next, you need to install Spring Boot Maven. You can find documentation and instructions on the official Spring Boot website.

Project Download
Download the project from the GitHub repository. You can do this by cloning the repository or downloading the archive file.

Program Launch
After downloading the project, to run the program, execute the ProgApplication.java file. It is located at src\main\java\com\jamavcode\prog.

Note: Make sure your PostgreSQL database is running before launching the program.

Code Documentation Standards and Rules

To maintain a clean, understandable and consistent code base, each participant must comply with the following **documentation requirements**:

1. Javadoc for Classes and Methods

- Be sure to add 'Javadoc' for **public classes**, **methods**, and **interfaces**.
- Briefly describe the purpose and parameters.
-Example:

```java
/**
* Service for processing user requests.
*/
public class UserService {

/**
* Finds the user by ID.
*
* @param user ID
* @return user object
* @throws UserNotFoundException if no user is found
*/
public User getUserById(Long id) { ... }
}

2. Comments in the code
Comment only on illogical or difficult places in the code.

Avoid excessive comments like // increment.

Validate the token before executing the request
if (!authService.isTokenValid(token)) {
throw new UnauthorizedException();
}

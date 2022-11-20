# EmployeeOnboardingStateMachine
Employee Onboarding with State Machine

## Tools for Development and Execution
- Java 17 (JDK 17)
- Spring Boot
- Spring StateMachine
- Lombok
- JUnit 5

## To Start Docker container

From within the project root directory, issue the following command:
`$ docker-compose -f onboarding.yml up`

## The API Endpoints

- to add an employee
POST http://localhost:8080/eob/add

Request Body (JSON) example:
{"emailAddress":"e004@email.com",
"name":"name",
"contract":"contract",
"age":30,
"state":"ADDED"
}

- to get employee details
GET http://localhost:8080/eob/getByEmailAddress?email=e004@email.com

- to update employee state
PUT http://localhost:8080/eob/update?email=e004@email.com&event=BEGIN_CHECK

## Notes
- A very simple and minimal app has been designed and implemented.
- The app showcases state machine transitions.
- To minimise the effort and time to devote prior to the first showcasing, the following have been parked for future when needed:
  - ResponseEntity in Controller
  - Controller test cases
  - More test cases for Service, especially unhappy scenarios
  - Elaborate OpenAPI documentation

## Contact Points for Feedback and Help
- rishi.raj.88@gmail.com
- https://www.linkedin.com/in/rishirajopenminds
- https://bio.link/rishiraj49de
- https://github.com/rishiraj88?tab=repositories
- https://twitter.com/RishiRajDevOps
- https://hub.docker.com/repository/docker/i50729/employeeonboarding
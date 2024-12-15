# Employee Onboarding State Machine
The smart Human Resource management tool for raising talent building Superteams!!! A must-have accelerator for modern large-scale enterprises.

## Features
- Allows for rapid onboarding of people.
- Reduces error rates (number of data errors per 1000 of persons onboarded).

## Targeted End-Users
This is a great tool for the HR personnel, who are responsible for the following functions:
- Talent Acquisition (Headhunting)
- Hiring (the process of registering and joining)
- Eymployee Onboarding (additional formalities post the joining process, such as orientation)

## Suggested Usage Scenarios in Business Functions
- to manage concurrent hiring (simultaneous) of people,
- concurrent employee onboarding

## Tools for Development and Execution
- Java 17 (JDK 17)
- Spring StateMachine
- Spring Boot
- Lombok
- JUnit 5
- SpringBootTest
- Docker with docker-compose

## To Start Docker container

From within the project root directory, issue the following command (`$ ` is prompt, not to type in!):
<pre>$ docker-compose -f onboarding.yml up</pre>

## The API Endpoints

- to add an employee
<pre>POST http://localhost:8080/eob/add

Request Body (JSON) example:
{"emailAddress":"e004@email.com",
"name":"name",
"contract":"contract",
"age":30,
"state":"ADDED"
}</pre>

- to update employee state
<pre>PUT http://localhost:8080/eob/update?email=e004@email.com&event=BEGIN_CHECK</pre>

- to get employee details
<pre>GET http://localhost:8080/eob/getByEmailAddress?email=e004@email.com</pre>

## Notes
- A very simple and minimal app has been designed and implemented.
- The app showcases state machine transitions.
- To minimise the effort and time to devote prior to the first showcasing, the following have been parked for future when needed:
  - ResponseEntity in Controller
  - Controller test cases
  - More test cases for Service, especially unhappy scenarios
  - Elaborate OpenAPI documentation

## Docker Image
This project is available to pull as a container image as well. Follow this link for exact details:
<pre>https://hub.docker.com/repository/docker/i50729/employeeonboarding</pre>

## Contact Points for Feedback, Queries, Collaboration
- LinkedIn Profile: https://www.linkedin.com/in/rishirajopenminds
- Contact Card: https://bio.link/rishiraj49de
- X: https://twitter.com/RishiRajDevOps
- Other Repositories: https://github.com/rishiraj88?tab=repositories

> **Tip:** Use the dropdown menu next to 'README' above to navigate through different sections of this document. ![Screen Shot](https://github.com/GabrielleYnara/BotaniQ-Backend/blob/main/assets/readme%20dropdown
# BotaniQ Backend
Your virtual companion for nurturing and managing your garden's growth, with a robust BotaniQ Backend.  
This core component handles data management, logic, and server operations, ensuring a seamless user experience as you interact with your virtual garden.
### Project Repositories
- **[BotaniQ Frontend](https://github.com/GabrielleYnara/BotaniQ-Frontend)**: Contains the client-side code for BotaniQ.  
#
> <p align="center">:construction: This project is currently under construction :construction:</p>

### Approach 
- **Initial Planning**: Created a Trello board for project management and drafted an Entity Relation Diagram and User Stories.  
- **Task Breakdown**: Turned User Stories into features on Trello cards.  
- **Repo Setup and Development**: Created a GitHub repository, cloned it locally, wrote test units with Cucumber and Rest Assured following Test Driven Development (TDD), followed by security implementation JWT-based authentication.  
Then implemented the backend feature-based, following Spring MVC architecture and ensuring a functional application.  
Once the backend was done, I moved to the front-end repository and implemented the View feature-based, ensuring a seamless application.  
- **Testing**: After each feature is implemented on the back end, I would refactor the test if necessary. 
  
### User Stories
<details>
<summary>User-related</summary>

1. As a new user, I want to register by providing my email and password, so I can access the application.
    - The user should provide a unique email and a password.
        - Success: If the email is unique, the application registers the user and displays a success message.
        - Error: If the email is not unique, the application displays an error message.
2. As a registered user, I want to log in by providing my email and password, so I can access my personal application. 
    - The user must provide a valid pair of email and password.
        - Success: If the credentials are valid, the application should load the home page.
        - Error: If the credentials are invalid, the application displays an error message.
3. As a logged-in user, I want to add my first name, last name, and bio to my profile, so I can share more about myself with other Virtual Garden users.
    - Success: If the information is saved successfully, a success message is displayed.
    - Error: If there's an error while saving, an error message is displayed.

</details>
<details>
<summary>Garden-related</summary>

1. As a logged-in user, I want to create a garden, so I can have a virtual copy of my actual garden. 
    - I must provide a unique description.
    - I can provide additional notes.
    - Success: If the description is unique it saves the garden.
    - Error: If a garden with that description already exists.
2. As a logged-in user, I want to update my garden's information.
    - I can provide a new description, and/or new notes.
    - Success: If the information is saved successfully, a success message is displayed.
    - Error: If there's an error while saving, an error message is displayed.
3. As a logged-in user, I want to see a list of plants in a garden.
    - I must provide a valid garden.
    - Success: The application displays a list of plants associated with the garden.
    - Error: The application displays an error.

</details>
<details>
<summary>Plant-related</summary>

1. As a logged-in user, I want to add a new plant to my garden. 
    - I must provide a valid garden.
    - I must provide the plant name and type.
    - Success: If the information is saved successfully, a success message is displayed.
    - Error: If there's an error while saving, an error message is displayed.
2. As a logged-in user, I want to view the details of a specific plant, so I can learn more about it.
    - I must choose a valid plant.
    - Success: The application displays the plant information.
    - Error: The application displays an error.
3. As a logged-in user, I want to create descriptive care types and set their frequency, so I can have a personalized care schedule for my plants.
    - The user can create a new care type by providing a description and frequency (daily, weekly, monthly, etc.).
        - Success: The application saves the care type and frequency, and displays a success message.
        - Error: If there's an error while saving, the application displays an error message.
4. As a logged-in user, I want to register when I care for a specific plant, so I can keep track of my plant care routine.
    - The user must select a valid plant and a valid care type.
    - The user can specify the date when the care was provided.
        - Success: The application saves the care event, and displays a success message.
        - Error: If there's an error while saving, the application displays an error message.

</details>

### Endpoints 
| Entity | Request Type | URL | Functionality | Access |
| --- | --- | --- | ---- | --- |
| User | POST | /auth/users/register/ | Register a new user | public |
| User | POST | /auth/users/login/ | Login as a registered user | public |
| User | PUT | /auth/users/profile/ | Update a user's profile | private |
| Garden | POST  | /gardens/ | Create a garden  | private |
| Garden | PUT | /gardens/{gardenId}/ | Update garden information | private |
| Plant | POST | /gardens/{gardenId}/plants/ | Create and add a plant to a garden | private |
| Garden | GET | /gardens/{gardenId}/ | See the garden and its plant list | private |
| Plant | GET | /gardens/{gardenId}/plants/{plantId}/ | View plant details of a given garden | private |
| Care Type | POST | /gardens/{gardenId}/plants/{plantId}/cares/ | Create care types and frequency | private |
| Care Tracker | POST | /gardens/{gardenId}/plants/{plantId}/cares/{careId}/care-tracker/ | Register plant care | private |

### Project Resources
You can find the **Entity Relationship Diagram (ERD)** [here](https://github.com/GabrielleYnara/BotaniQ-Backend/blob/main/assets/BotaniQ%20-%20ERD.png).  
You can view the **Wireframe** [here](https://www.figma.com/file/XPuSCSOH2gortY4YzDjYnY/BotaniQ?type=design&node-id=1%3A153&mode=design&t=sEdhlr8fbDFwkS11-1).  
You can track the project's progress on **Trello** [here](https://trello.com/b/Phjbksmc/capstone).

### Roadblocks
**Cucumber & Rest Assured Confusion**: I found myself questioning every step of the way, doubting the effectiveness of the tests I was writing. I went back to Cucumber's documentation and read it until I felt more confident and finally ready to move on.  
**Time Estimate**: Unfortunately I miscalculated the time necessary for each task and ended up fighting the clock at the end to present basic functionalities. 

### Future Enhancements
**Implement full CRUD**: Enable full Create, Read, Update, and Delete (CRUD)  functionality for managing profile, garden, plants, and care information, so users can have better control over their data.  
**Third-party API for plant care recommendations**: Adding a layer of intelligence, enhancing the virtual companion aspect of BotaniQ.  
**Improve User Interface and Experience**: Adopt mobile-first design to improve accessibility, and enhance user interaction by including error prompts, tooltips, and a more intuitive interface, reducing confusion and providing an overall better user experience.

### Acknowledgments
This project was developed as part of the Software Engineering Immersive program by General Assembly.  
It serves as a capstone project to apply and reinforce skills in Java, Object-Oriented Programming, Spring Boot, Spring Security, and TDD (Test-Driven Development). 

### Feedback
If you have any questions, suggestions, or encounter a bug, feel free to [create an issue ticket](https://github.com/GabrielleYnara/BotaniQ-Backend/issues/new) to let me know.

### Tools and Technologies
- Backend Framework: Spring Boot
- Database: H2 Database
- Server: Tomcat
- Build Tool: Maven
- Version Control: Git
- Testing Frameworks: JUnit, Cucumber
- Security: Spring Security
- Web Framework: Spring Web
- REST Framework: Spring Data REST
- Authentication: JSON Web Tokens (JWT)
- Programming Language: Java
- Project Management: Trello
- Diagramming: Lucid
- Design Tool: Figma

### Installation Instructions

1. **Clone the Repository**  
  Clone the repository to your local machine  
2. **Replace the application.properties file**  
  Open the src/main/resources/application.properties file.
  Replace the placeholder values with your own configurations:
```
server.port= {YOUR_PORT}
spring.datasource.url=jdbc:h2:mem:botaniqdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.error.include-stacktrace=never

jwt-secret="{YOUR_SECRET_KEY}"
jwt-expiration-ms=86400000
```

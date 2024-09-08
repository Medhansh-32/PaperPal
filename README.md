# PaperPal Application 🎓📁

Welcome to the PaperPal application! This project is designed to manage and interact with exam files and user responses. It provides functionalities for uploading, downloading, and managing exam-related files, as well as handling user responses and associated files.

## Table of Contents

- [Project Overview]
- [Controllers]
  - [HomeController]
  - [ExamFileController]
  - [UserResponseController]
- [Running the Application]
- [Contributing]

## Project Overview 🏠

The PaperPal application is built using Spring Boot and provides a web interface for users to upload, manage, and download exam files. It also supports managing user responses and linking exam files to these responses.

## Controllers 🕹️

### HomeController 🏠

The `HomeController` manages the main navigation of the application.

- **GET /home**: Returns the `home` view.
- **GET /upload**: Returns the `upload` view for uploading files.
- **GET /getlinks**: Returns the `getlinks` view for retrieving file links.
- **GET /addfile**: Returns the `addfile` view for adding files.
- **GET /delete**: Returns the `deleteresponse` view for deleting responses.

### ExamFileController 📄

The `ExamFileController` handles operations related to exam files.

- **POST /file**: Uploads an exam file.
  - **Request Body**: `MultipartFile file`
  - **Response**: `ExamFile` object with details of the uploaded file.

- **GET /file/{id}**: Downloads an exam file by its ID.
  - **Path Variable**: `ObjectId id`
  - **Response**: File content as `ByteArrayResource` with `Content-Disposition` header for download.

- **DELETE /file/{id}**: Deletes an exam file by its ID.
  - **Path Variable**: `ObjectId id`
  - **Response**: HTTP Status Code indicating success or failure.

### UserResponseController 🧑‍🎓

The `UserResponseController` manages user responses and associated files.

- **POST /userresponse**: Submits user response data and an exam file.
  - **Request Parameters**: `course`, `branch`, `semester`, `file`
  - **Response**: Redirects to `succesfull` or `unsuccesfull` view based on the operation outcome.

- **GET /userresponse/getlinks**: Retrieves download links for exam files based on user response details.
  - **Request Parameters**: `course`, `branch`, `semester`
  - **Response**: `links-exam` view with file download links.

- **POST /userresponse/addfile**: Adds an additional file to an existing user response.
  - **Request Parameters**: `course`, `branch`, `semester`, `file`
  - **Response**: Redirects to `succesfull` or `unsuccesfull` view based on the operation outcome.

- **POST /userresponse/delete**: Deletes a user response.
  - **Request Parameters**: `course`, `branch`, `semester`
  - **Response**: Redirects to `succesfull` or `unsuccesfull` view based on the operation outcome.

- **GET /userresponse/ai**: Placeholder endpoint for AI-related functionality.
  - **Request Body**: `String ai`
  - **Response**: No specific functionality defined.

## Running the Application 🚀

To run the application locally, follow these steps:

1. Clone the repository:

    ```sh
    git clone https://github.com/yourusername/PaperPal.git
    ```

2. Navigate to the project directory:

    ```sh
    cd PaperPal
    ```

3. Build and run the application using Maven or Gradle:

    ```sh
    ./mvnw spring-boot:run
    ```

    or

    ```sh
    ./gradlew bootRun
    ```

4. Access the application at `http://localhost:8080` for the homepage (`http://localhost:8080/home`).

## Contributing 🤝

If you would like to contribute to this project, please fork the repository and submit a pull request. Make sure to include tests for any new features or bug fixes.

For any issues or feature requests, please create an issue in the GitHub repository.

---

Thank you for using PaperPal! If you have any questions, feel free to reach out or open an issue on GitHub. 😊


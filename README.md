# PaperPal Application 🎓📁

Welcome to the **PaperPal** application! This project is designed to manage and interact with exam files, handle user responses, and assist with posting and resolving doubts. The application also integrates an AI assistant for answering general questions. It provides functionalities for uploading, downloading, and managing exam-related files, posting and replying to doubts, and interacting with an AI assistant.

## Table of Contents

- [Project Overview](#project-overview-)
- [Features](#features-)
- [Controllers](#controllers-)
    - [UserController](#usercontroller-)
    - [UserResponseController](#userresponsecontroller-)
    - [ExamFileController](#examfilecontroller-)
    - [DoubtsController](#doubtscontroller-)
    - [AIController](#aicontroller-)
    - [Healthcheck](#healthcheck-)
- [Running the Application](#running-the-application-)
- [Contributing](#contributing-)

## Project Overview 🏠

The **PaperPal** application is built using **Spring Boot** and provides a web interface for users to upload, manage, and download exam files. It supports managing user responses and linking those responses to exam files. Additionally, users can interact with an AI assistant powered by **Ollama Mistral** via the home page. The application also allows users to post and resolve doubts related to exams, fostering better learning and communication.

## Features 🌟

1. **Question Paper Upload and Download**:
    - Users can upload exam files and download them by accessing the file details through the application.

2. **Ask AI**:
    - The integrated AI assistant allows users to ask questions, similar to ChatGPT, to get instant responses for better learning and problem-solving.

3. **Doubts Postings**:
    - Users can post doubts, respond to others' doubts, and manage their doubts effectively. This feature promotes collaboration and clarity among students.

## Controllers 🕹️

### UserController 🧑‍💻

The `UserController` handles user-related actions such as password management and redirects.

- **POST /user/otp**: Sends an OTP for user verification.
- **POST /user/changePassword**: Changes the user's password.
- **PUT /user/setNewPassword**: Sets a new password after verification.
- **POST /user/redirectHome**: Redirects the user to the home page after login or registration.

### UserResponseController 🧑‍🎓

The `UserResponseController` manages user responses, file uploads, and deletion of responses.

- **POST /userresponse**: Submits user response data and an exam file.
    - **Request Parameters**: `course`, `branch`, `semester`, `file`
    - **Response**: Redirects to `successful` or `unsuccessful` view based on the outcome.

- **GET /userresponse/getlinks**: Retrieves download links for exam files based on user response details.
    - **Request Parameters**: `course`, `branch`, `semester`
    - **Response**: `links-exam` view with file download links.

- **POST /userresponse/addfile**: Adds an additional file to an existing user response.
    - **Request Parameters**: `course`, `branch`, `semester`, `file`
    - **Response**: Redirects to `successful` or `unsuccessful` view based on the operation outcome.

- **POST /userresponse/delete**: Deletes a user response.
    - **Request Parameters**: `course`, `branch`, `semester`
    - **Response**: Redirects to `successful` or `unsuccessful` view based on the operation outcome.

### ExamFileController 📄

The `ExamFileController` handles operations related to uploading, downloading, and deleting exam files.

- **POST /file**: Uploads an exam file.
    - **Request Body**: `MultipartFile file`
    - **Response**: `ExamFile` object with details of the uploaded file.

- **GET /file/{id}**: Downloads an exam file by its ID.
    - **Path Variable**: `ObjectId id`
    - **Response**: File content as `ByteArrayResource` with `Content-Disposition` header for download.

- **DELETE /file/{id}**: Deletes an exam file by its ID.
    - **Path Variable**: `ObjectId id`
    - **Response**: HTTP Status Code indicating success or failure.

### DoubtsController 🤔

The `DoubtsController` manages the posting, replying, and viewing of doubts.

- **POST /doubts/postDoubts**: Allows a user to post a new doubt.
    - **Request Parameters**: `course`, `branch`, `semester`, `question`
    - **Response**: Redirects to the doubt posting status page.

- **PATCH /doubts/addReply**: Adds a reply to an existing doubt.
    - **Request Parameters**: `doubtId`, `reply`
    - **Response**: Redirects to the reply status page.

- **GET /doubts/myDoubts**: Retrieves all doubts posted by the logged-in user.
    - **Response**: List of the user's doubts.

- **GET /doubts/getReply/{id}**: Retrieves all replies for a specific doubt by its ID.
    - **Path Variable**: `id` of the doubt
    - **Response**: List of replies for the given doubt.

- **GET /doubts/allDoubts**: Retrieves all posted doubts.
    - **Response**: List of all doubts in the system.

- **DELETE /doubts/deleteDoubt/{id}**: Deletes a specific doubt.
    - **Path Variable**: `id` of the doubt
    - **Response**: Status of the deletion operation.

### AIController 🤖

The `AIController` allows users to interact with the AI assistant.

- **GET /ai/gen**: Generates a response from the AI based on user input.
    - **Request Parameters**: `query` (the question or prompt)
    - **Response**: The AI-generated response to the query.

### Healthcheck 🔍

The `Healthcheck` endpoint is used to monitor the health status of the application.

- **GET /health**: Returns the health status of the application.
    - **Response**: HTTP Status Code `200 OK` if the application is healthy.

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

4. Access the application(Homepage) at `http://localhost:8080`.

## Contributing 🤝

If you would like to contribute to this project, please fork the repository and submit a pull request. Make sure to include tests for any new features or bug fixes.

For any issues or feature requests, please create an issue in the GitHub repository.

---

Thank you for using **PaperPal**! If you have any questions, feel free to reach out or open an issue on GitHub. 😊

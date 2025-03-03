# PaperPal Application 🎓📁

Welcome to the **PaperPal** application! This project is designed to manage and interact with study material files, handle user responses, and assist with posting and resolving doubts. The application also integrates an AI assistant for answering general questions. It provides functionalities for uploading, downloading, and managing exam-related files, posting and replying to doubts, and interacting with an AI assistant.

## Table of Contents
- [Project Overview](#project-overview-)
- [Features](#features-)
- [API Endpoints](#api-endpoints-)
    - [Authentication API](#authentication-api-)
    - [User API](#user-api-)
    - [File Management API](#file-management-api-)
    - [Doubts API](#doubts-api-)
    - [AI Assistant API](#ai-assistant-api-)
- [Running the Application](#running-the-application-)

## Project Overview 🏠

The **PaperPal** application has been refactored from HTML/CSS/JS to **React** with **TypeScript** for improved type safety and component-based architecture. The application provides a web interface for users to upload, manage, and download exam files. It supports managing user responses and linking those responses to exam files. Additionally, users can interact with an AI assistant powered by **Google Gemini** via the home page. The application also allows users to post and resolve doubts related to exams, fostering better learning and communication.

Moreover, when a PDF is uploaded, it is analyzed by AI to ensure it meets the required guidelines. If the document does not comply, it gets rejected automatically, reducing manual effort in the review process.

## Features 🌟

1. **Question Paper and Notes Upload and Download**:
    - Users can upload exam files and download them by accessing the file details through the application.
    - When a user uploads a PDF, it is analyzed by Google Gemini AI in parallel using multithreading to ensure the file meets guidelines and is study-related.
    - If the file is not related to study materials, it is automatically rejected, reducing manual effort.

2. **Ask AI**:
    - The integrated AI assistant allows users to ask questions, similar to ChatGPT, to get instant responses for better learning and problem-solving.

3. **Doubts Postings**:
    - Users can post doubts, respond to others' doubts, and manage their doubts effectively. This feature promotes collaboration and clarity among students.

## API Endpoints 🔌

### Authentication API 🔑

The Authentication API handles user registration, login, and authentication processes.

- **POST /api/auth/register**: Registers a new user.
  - **Request Body**: `{ username, email, password }`
  - **Response**: `{ message, token }`

- **POST /api/auth/login**: Authenticates a user and provides a JWT token.
  - **Request Body**: `{ email, password }`
  - **Response**: `{ message, token, userData }`

### User API 🧑‍💻

The User API handles user-related actions such as password management and profile updates.

- **POST /api/user/otp**: Sends an OTP for user verification.
  - **Request Body**: `{ email }`
  - **Response**: `{ message, success }`

- **POST /api/user/verify-otp**: Verifies the OTP entered by the user.
  - **Request Body**: `{ email, otp }`
  - **Response**: `{ message, success }`

- **PUT /api/user/change-password**: Changes the user's password.
  - **Request Body**: `{ email, newPassword }`
  - **Response**: `{ message, success }`

- **GET /api/user/profile**: Retrieves the user's profile information.
  - **Headers**: `Authorization: {JWT token}`
  - **Response**: `{ userData }`

- **PUT /api/user/profile**: Updates the user's profile information.
  - **Headers**: `Authorization: {JWT token}`
  - **Request Body**: `{ name, email, ... }`
  - **Response**: `{ message, updatedData }`

### File Management API 📄

The File Management API handles operations related to uploading, downloading, and managing exam files.

- **POST /api/files/upload**: Uploads an exam file with associated metadata.
  - **Headers**: `Authorization: {JWT token}`
  - **Request Body**: `FormData { course, branch, semester, type, file }`
  - **Response**: `{ message, fileData, success }`

- **GET /api/files/list**: Gets a list of files based on filter criteria.
  - **Headers**: `Authorization: {JWT token}`
  - **Query Parameters**: `course, branch, semester`
  - **Response**: `{ files }`

- **GET /api/files/:id**: Downloads a specific file by ID.
  - **Headers**: `Authorization: {JWT token}`
  - **Path Parameter**: `id`
  - **Response**: File content (binary)

- **DELETE /api/files/:id**: Deletes a specific file.
  - **Headers**: `Authorization: {JWT token}`
  - **Path Parameter**: `id`
  - **Response**: `{ message, success }`

- **POST /api/files/validate**: Validates a file using AI to ensure it meets guidelines.
  - **Headers**: `Authorization: {JWT token}`
  - **Request Body**: `FormData { file }`
  - **Response**: `{ valid, message }`

### Doubts API 🤔

The Doubts API manages posting, replying to, and retrieving doubts.

- **POST /api/doubts**: Creates a new doubt.
  - **Headers**: `Authorization: {JWT token}`
  - **Request Body**: `{ title, description }`
  - **Response**: `{ message, doubtData }`

- **GET /api/doubts**: Retrieves all doubts with optional filtering.
  - **Headers**: `Authorization: {JWT token}`
  - **Query Parameters**: `user, solved` (optional)
  - **Response**: `{ doubts }`

- **GET /api/doubts/:id**: Retrieves a specific doubt with its replies.
  - **Headers**: `Authorization: {JWT token}`
  - **Path Parameter**: `id`
  - **Response**: `{ doubt, replies }`

- **POST /api/doubts/:id/reply**: Adds a reply to a doubt.
  - **Headers**: `Authorization: {JWT token}`
  - **Path Parameter**: `id`
  - **Request Body**: `{ content }`
  - **Response**: `{ message, reply }`

- **PUT /api/doubts/:id/status**: Updates the status of a doubt (solved/unsolved).
  - **Headers**: `Authorization: {JWT token}`
  - **Path Parameter**: `id`
  - **Request Body**: `{ solved }`
  - **Response**: `{ message, doubt }`

- **DELETE /api/doubts/:id**: Deletes a specific doubt.
  - **Headers**: `Authorization: {JWT token}`
  - **Path Parameter**: `id`
  - **Response**: `{ message, success }`

### AI Assistant API 🤖

The AI Assistant API provides interaction with the integrated AI assistant.

- **POST /api/ai/ask**: Sends a query to the AI assistant and receives a response.
  - **Headers**: `Authorization: {JWT token}`
  - **Request Body**: `{ query }`
  - **Response**: `{ answer }`

- **GET /api/ai/stream**: Establishes a stream connection for real-time AI responses.
  - **Headers**: `Authorization: {JWT token}`
  - **Query Parameters**: `query`
  - **Response**: Server-Sent Events (SSE) stream with AI responses

## Frontend Components 🖼️

The PaperPal application's frontend is built using React with TypeScript, featuring the following key components:

### Authentication Components

- **RegisterForm**: Handles user registration with form validation
- **LoginForm**: Manages user login and authentication
- **PasswordReset**: Facilitates the password reset flow with OTP verification

### Navigation Components

- **Navbar**: Main navigation with responsive design for both mobile and desktop
- **Sidebar**: Context-aware sidebar that changes based on the current section
- **ThemeToggle**: Switches between light and dark themes

### File Management Components

- **FileUpload**: Multi-step form for uploading exam files with metadata
- **FileList**: Displays filterable list of available files with download options
- **FileViewer**: Preview component for PDF files

### Doubts Components

- **DoubtsList**: Displays all doubts with filtering and sorting options
- **DoubtDetail**: Shows a single doubt with all replies and actions
- **DoubtForm**: Form for creating new doubts
- **ReplyForm**: Component for adding replies to existing doubts

### AI Assistant Components

- **AIChatInterface**: Real-time chat interface for interacting with the AI assistant
- **QueryHistory**: Shows previous AI interactions

### Common Components

- **Button**: Reusable button component with various states
- **Modal**: Reusable modal for confirmations and forms
- **Dropdown**: Custom dropdown component for selections
- **Toast**: Notification system for success/error messages
- **LoadingSpinner**: Visual indicator for loading states

## Running the Application 🚀

To run the application locally, follow these steps:

## 1. Clone the repository:

`git clone https://github.com/Medhansh-32/PaperPal.git`
   
## 2. Navigate to the project directory:

### For frontend
1. `cd Frontend`
2. `npm install`
3. `npm run dev`

### For backend
1. `cd Backend`
2. Add the data required in `application.properties`
3. `mvn clean install`
4. `java -jar target/PaperPal-0.0.1-SNAPSHOT.jar`

## 3. You are now ready to proceed:

- The frontend is available at: [http://localhost:5173](http://localhost:5173)
- The backend is available at: [http://localhost:8080](http://localhost:8080)

## 4. Enjoy using the project?

If you find this project helpful or interesting, don't forget to give it a ⭐ on GitHub! Your support is greatly appreciated and helps the project grow!

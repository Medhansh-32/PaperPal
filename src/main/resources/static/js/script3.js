// JavaScript function to handle form submission
document.getElementById("registerForm").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevent the default form submission

    // Get form field values
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var errorMessage = document.getElementById("error-message");
    var loadingMessage = document.getElementById("loading-message");

    // Clear any previous error messages
    errorMessage.textContent = '';

    // Check if passwords match
    if (password !== confirmPassword) {
        errorMessage.textContent = "Passwords do not match.";
        return;
    }

    // Create an object with form data
    const formData = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password
    };

    // Show the loading message immediately upon clicking the submit button
    loadingMessage.style.display = 'block';

    // Submit the form data via fetch
    try {
        const response = await fetch('/user/redirectHome', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        // Hide the loading message after the request is complete
        loadingMessage.style.display = 'none';

        if (response.ok) {
            // If registration is successful, redirect to the home page
            window.location.href = "/";
        } else {
            // If an error occurred, extract and display the error message from the response
            const errorData = await response.text();
            errorMessage.textContent = errorData; // Display the error message from server
        }
    } catch (error) {
        // Hide loading message if there's an error
        loadingMessage.style.display = 'none';
        errorMessage.textContent = "An error occurred. Please try again.";
    }
});

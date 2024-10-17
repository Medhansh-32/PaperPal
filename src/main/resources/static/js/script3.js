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
document.addEventListener('DOMContentLoaded', function () {
    // Get the form element
    const doubtForm = document.getElementById('doubtForm');

    // Add event listener for form submission
    doubtForm.addEventListener('submit', async function (event) {
        // Prevent the default form submission behavior
        event.preventDefault();

        // Get the values from the form fields
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;

        // Create the data object to send in the POST request
        const postData = {
            title: title,
            description: description
        };

        try {
            // Send a POST request to the /postDoubts endpoint
            const response = await fetch('/postDoubts', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(postData)
            });

            // Check if the response is OK (status 200-299)
            if (response.ok) {
                const result = await response.json();
                alert('Doubt posted successfully!');

                // Optionally, you can clear the form fields after submission
                doubtForm.reset();

                // Reload or update the doubts list on success
                loadDoubts();
            } else {
                alert('Error posting doubt. Please try again.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred while posting the doubt.');
        }
    });

    // Function to load the doubts (optional)
    function loadDoubts() {
        // Add logic here to fetch and display the list of posted doubts
        // You can use another fetch call to get the doubts and update the UI
    }
});

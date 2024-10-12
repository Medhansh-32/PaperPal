// JavaScript function to handle form submission
async function getAiResponse(event) {
    event.preventDefault(); // Prevent default form submission

    const prompt = document.getElementById('prompt').value;
    const loadingMessage = document.getElementById('loading-message');
    const aiResponse = document.getElementById('ai-response');

    // Show the loading message in the aiResponse div and clear the previous response
    aiResponse.textContent = 'Generating...'; // Clear previous response
    // loadingMessage.textContent = 'Generating...'; // Display the loading message

    try {
        const response = await fetch(`/ai/generateStream?prompt=${encodeURIComponent(prompt)}`);
        const data = await response.text(); // Assuming the response is plain text

        // Hide the loading message and display the response
        loadingMessage.textContent = ''; // Clear loading message
        aiResponse.textContent = data;
    } catch (error) {
        console.error('Error fetching AI response:', error);
        loadingMessage.textContent = ''; // Clear loading message

        // Display error message in aiResponse div
        aiResponse.textContent = 'An error occurred while fetching the response.';
    }
}

document.getElementById("registerForm").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevent the default form submission

    // Get form field values
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var errorMessage = document.getElementById("error-message");

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

    // Submit the form data via fetch
    try {
        const response = await fetch('/user/redirectHome', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            // If registration is successful, redirect to the home page
            window.location.href = "/";
        } else {
            // If an error occurred, extract and display the error message from the response
            const errorData = await response.text();
            errorMessage.textContent =errorData; // Display the error message from server
        }
    } catch (error) {
        errorMessage.textContent = "An error occurred. Please try again.";
    }
});







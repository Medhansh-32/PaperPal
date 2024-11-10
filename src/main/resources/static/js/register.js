document.getElementById('registerForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Prevent default form submission

    // Get form values
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    // Reset error message
    const errorMessage = document.getElementById('error-message');
    errorMessage.textContent = '';

    // Check if the password is at least 8 characters long
    if (password.length < 8) {
        errorMessage.textContent = 'Password must be at least 8 characters long.';
        return;
    }

    // Check if passwords match before submitting
    if (password !== confirmPassword) {
        errorMessage.textContent = 'Passwords do not match. Please ensure both passwords are the same.';
        return;
    }

    // Show loading message
    const loadingMessage = document.getElementById('loading-message');
    loadingMessage.style.display = 'block';

    // Prepare the data to send
    const data = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password
    };

    try {
        // Send POST request to /user/redirectHome
        const response = await fetch('/user/redirectHome', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        // Check the response status
        if (response.ok) {
            console.log("Ok");
            // Handle success, e.g., redirect to a success page
            window.location.href = '/'; // Adjust the redirect URL as necessary
        } else if (response.status === 400) {
            // Handle specific case of username or email already registered
            const result = await response.json();
            if (result.message && result.message.includes('already registered')) {
                errorMessage.textContent = 'Either the username or email is already registered. Please try a different one.';
            } else {
                errorMessage.textContent = 'An error occurred during registration. Please try again.';
            }
        } else {
            // Handle other errors (e.g., server error)
            errorMessage.textContent = 'An unexpected error occurred. Please try again.';
        }
    } catch (error) {
        // Handle network or other errors
        errorMessage.textContent = 'Failed to register. Please check your connection and try again.';
    } finally {
        // Hide loading message
        loadingMessage.style.display = 'none';
    }
});

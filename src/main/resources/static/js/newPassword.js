const passwordForm = document.getElementById('passwordForm');
const passwordError = document.getElementById('passwordError');
const emailName=document.getElementById("email-name");

// Function to show error messages
function showError(element, message) {
    element.textContent = message;
    element.classList.remove('hidden');
}

// Function to hide error messages
function hideError(element) {
    element.classList.add('hidden');
    element.textContent = '';
}

// Handle Password Form Submission
passwordForm.addEventListener('submit', function(event) {
    event.preventDefault();

    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    // Clear previous error messages
    hideError(passwordError);

    // Check if passwords match
    if (newPassword !== confirmPassword) {
        showError(passwordError, 'Passwords do not match. Please try again.');
        return;
    }

    // Send POST request with the new password to the backend
    fetch(`/user/setNewPassword`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' // Set content type to application/json
        },
        body: JSON.stringify({ email: emailName.innerText, password: newPassword }) // Create a JSON object with both fields
    })
        .then(response => {
            if (response.ok) {
                console.log("Password updated successfully")
                window.location.href='/'
            } else {
                window.location.href='/user/forgotPassword'
                console.error("Failed to update password");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });

});
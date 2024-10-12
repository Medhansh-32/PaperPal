const emailForm = document.getElementById('emailForm');
const otpSection = document.getElementById('otpSection');
const emailError = document.getElementById('emailError');
const otpError = document.getElementById('otpError');
const timerDisplay = document.getElementById('timer');
let countdown;

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

// Handle Email Form Submission
emailForm.addEventListener('submit', function(event) {
    event.preventDefault();
    emailForm.classList.add('hidden');
    otpSection.classList.remove('hidden');

    // Start countdown timer (2 minutes)
    startTimer(120);
    const email = document.getElementById('email').value;

    // Clear previous error messages
    hideError(emailError);

    // Send POST request to /changePassword with the email
    fetch(`/user/changePassword?email=${encodeURIComponent(email)}`, {
        method: 'POST',
    })
        .then(response => {
            if (response.ok) {
                // If the response is successful (response.ok is true), show OTP input and start the timer
                // Hide email form and show OTP section

            } else {
                // Show invalid email message next to email input
                showError(emailError, 'Invalid email. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showError(emailError, 'There was an error submitting the email.');
        });
});

// Timer function
function startTimer(duration) {
    let timer = duration, minutes, seconds;

    countdown = setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        timerDisplay.textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            clearInterval(countdown);
            timerDisplay.textContent = "Time's up!";
            document.getElementById('otp').disabled = true; // Disable OTP input after timer ends
        }
    }, 1000);
}

// Handle OTP verification button
document.getElementById('verifyOtpBtn').addEventListener('click', function() {
    const otp = document.getElementById('otp').value;
    const email = document.getElementById('email').value;

    // Clear previous error messages
    hideError(otpError);

    clearInterval(countdown); // Stop the timer after OTP submission

    // Send POST request with email and OTP as request parameters
    fetch(`/user/otp?email=${encodeURIComponent(email)}&otp=${encodeURIComponent(otp)}`, {
        method: 'POST',
    })
        .then(response => {
            if (response.ok) {
                // If the response is successful (response.ok is true), show success message
                window.location.href = '/user/newPassword?email='+email;
            } else {
                // Show invalid OTP message next to OTP input
                showError(otpError, 'Invalid OTP. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showError(otpError, 'An error occurred while verifying the OTP.');
        });
});
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
function validatePasswords() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var errorMessage = document.getElementById("error-message");

    if (password !== confirmPassword) {
        errorMessage.textContent = "Passwords do not match.";
        return false;
    } else {
        errorMessage.textContent = "";
        return true;
    }
}
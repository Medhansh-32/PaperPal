document.addEventListener('DOMContentLoaded', function () {
    // Function to handle the AI prompt form submission
    async function getAiResponse(event) {
        event.preventDefault(); // Prevent default form submission

        // Get the prompt value from the form
        const prompt = document.getElementById('prompt').value;
        const loadingMessage = document.getElementById('loading-message');
        const aiResponse = document.getElementById('ai-response');

        // Clear previous AI response and show loading message
        aiResponse.innerHTML = '';
        loadingMessage.textContent = 'Fetching response...';

        try {
            // Send the prompt to the backend
            const response = await fetch('/ai/generateStream?prompt='+prompt, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ prompt: prompt })
            });

            // Hide the loading message
            loadingMessage.textContent = '';

            // Handle the backend response
            if (response.ok) {
                const result = await response.text();
                aiResponse.innerHTML = `<p>${result}</p>`; // Display the AI's response
            } else {
                const errorData = await response.text();
                aiResponse.innerHTML = `<p>Error: ${errorData}</p>`;
            }
        } catch (error) {
            loadingMessage.textContent = '';
            aiResponse.innerHTML = `<p>An error occurred while fetching the AI response.</p>`;
            console.error('Error:', error);
        }
    }

    // Add event listener for the AI form submission
    const aiForm = document.getElementById('ai-form');
    aiForm.addEventListener('submit', getAiResponse);
    // Get the doubt form element
 });

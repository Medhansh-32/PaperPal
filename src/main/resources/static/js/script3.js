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
            const response = await fetch('/askAi', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ prompt: prompt })
            });

            // Hide the loading message
            loadingMessage.textContent = '';

            // Handle the backend response
            if (response.ok) {
                const result = await response.json();
                aiResponse.innerHTML = `<p>${result.answer}</p>`; // Display the AI's response
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
    const doubtForm = document.getElementById('doubtForm');

    // Function to handle the "Post Doubt" form submission
    doubtForm.addEventListener('submit', async function (event) {
        // Prevent the default form submission behavior
        event.preventDefault();
        const posting = document.getElementById("posting");

        // Get the values from the form fields
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;
        posting.innerText = "Posting...";

        // Create the data object to send in the POST request
        const postData = {
            doubtTitle: title,
            doubtDescription: description
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
            console.log(response.status,response.statusText)
            // Check if the response is OK (status 200-299)
            if (response.ok) {
                alert("Doubt Posted")
                posting.innerText = await response.text();

                // Optionally, you can clear the form fields after submission
                doubtForm.reset(); // Clears the form

                // Optionally reload or update the list of doubts
             //   loadDoubts();
            } else {
                const errorData = await response.text();
                posting.innerText = await response.text();
                console.error('Error response:', errorData);
            }
        } catch (error) {
            console.error('Error:', error);
            posting.innerText = "An error occurred while posting the doubt.";
        }
    });

    // Function to load the doubts dynamically (optional)
//     function loadDoubts() {
//         // Use fetch or another method to dynamically load and display the doubts list
//         // For example, you can fetch updated doubts and render them in the #doubtList div
//         fetch('/getDoubts')
//             .then(response => response.json())
//             .then(data => {
//                 const doubtList = document.getElementById('doubtList');
//                 doubtList.innerHTML = ''; // Clear current doubts
//                 data.forEach(doubt => {
//                     doubtList.innerHTML += `<p><strong>${doubt.title}</strong>: ${doubt.description}</p>`;
//                 });
//             })
//             .catch(error => console.error('Error fetching doubts:', error));
//     }
//
//     // Initial load of doubts when the page is ready
//     loadDoubts();
 });

function showReplyBox(button) {
    const replyBox = button.parentElement.querySelector('.reply-box');
    replyBox.style.display = replyBox.style.display === 'none' || replyBox.style.display === '' ? 'block' : 'none';
}

function postReply(button) {
    const cardElement = button.closest('.card');
    const idElement = cardElement.querySelector('#doubt-id'); // Find hidden input
    const replyTextArea = button.previousElementSibling; // Get the textarea
    const replyText = replyTextArea.value.trim(); // Trim the reply text

    // Ensure there's a reply and an ID element
    if (replyText !== "" && idElement) {
        const payload = {
            id: idElement.value.trim(), // Use the value from the hidden input
            message: replyText
        };

        // Send the POST request to add the reply
        fetch('/doubts/addReply', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok.');
                }
                return response.json(); // Assuming your backend returns some response
            })
            .then(data => {
                // Create a new paragraph element for the reply
                const newReply = document.createElement('p');
                newReply.className = 'card-text text-light';
                newReply.textContent = replyText;

                // Find the replies div and append the new reply to it
                const repliesDiv = cardElement.querySelector('.replies'); // Find the correct replies container
                repliesDiv.appendChild(newReply); // Append the new reply to the replies section
                repliesDiv.style.display = 'block'; // Ensure replies are visible after adding

                // Clear the textarea and hide the reply box
                replyTextArea.value = ""; // Clear the textarea
                button.parentElement.style.display = 'none'; // Hide the reply box after posting
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    } else {
        alert('Please enter a reply and ensure the ID is present.');
    }
}

function showReplies(button) {
    const cardElement = button.closest('.card');
    const idElement = cardElement.querySelector('#doubt-id'); // Find hidden input
    const replyId = idElement.value.trim(); // Get the ID value
    const repliesDiv = cardElement.querySelector('.replies'); // Find the correct replies container

    // Check if the replies are currently displayed
    if (repliesDiv.style.display === 'block') {
        // If replies are already visible, hide them
        repliesDiv.style.display = 'none';
    } else {
        // If replies are hidden, fetch them
        if (replyId) {
            // Make a fetch request to the backend
            fetch(`/doubts/getReply/${replyId}`)
                .then(response => {
                    // Check if the response is OK (status in the range 200-299)
                    if (!response.ok) {
                        const replyDiv = document.createElement('div');
                        replyDiv.textContent = "No reply yet...."; // Set the text content to the reply
                        repliesDiv.appendChild(replyDiv);
                        repliesDiv.style.display = 'block';
                        replyDiv.style.fontStyle="italic"
                    }
                    return response.json(); // Parse the JSON data from the response
                })
                .then(data => {
                    // Clear existing replies
                    repliesDiv.innerHTML = ''; // Clear existing replies

                    // Display the replies in the replies div
                    data.forEach(reply => {
                        const replyDiv = document.createElement('div'); // Create a new div for each reply
                        replyDiv.textContent = reply; // Set the text content to the reply
                        repliesDiv.appendChild(replyDiv); // Append the reply div to the replies div
                    });

                    repliesDiv.style.display = 'block'; // Ensure replies are visible after fetching
                })
                .catch(error => {
                    console.error('There was a problem with the fetch operation:', error);
                   // document.getElementById('allReplies').innerText = 'Error fetching replies. Please try again.';
                });
        } else {
            alert('No reply ID found.');
        }
    }
}
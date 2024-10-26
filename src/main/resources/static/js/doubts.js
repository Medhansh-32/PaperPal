function showReplyBox(button) {
    const replyBox = button.parentElement.querySelector('.reply-box');
    replyBox.style.display = replyBox.style.display === 'none' || replyBox.style.display === '' ? 'block' : 'none';
}
const doubtsContainer = document.querySelector('.row');
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
                console.log(response)
                return response.text()// Assuming your backend returns some response
            })
            .then(data => {
                console.log(data)
                const repliesDiv = cardElement.querySelector('.replies');
                // Create a new paragraph element for the reply

                // Find the replies div and append the new reply to it
                const replyDiv = document.createElement('div');
                replyDiv.textContent = data// Set the text content to the reply
                repliesDiv.appendChild(replyDiv);
                repliesDiv.style.display = 'block';

                // Clear the textarea and hide the reply box
                replyTextArea.setAttribute("placeholder","") // Clear the textarea
                replyTextArea.value=""
                replyTextArea.ariaPlaceholder="Enter your reply..."
                // Hide the reply box after posting
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
                    }else {
                        return response.json();
                    }
                     // Parse the JSON data from the response
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
        const response = await fetch('/doubts/postDoubts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postData)
        });
        console.log(response.status,response.statusText)
        // Check if the response is OK (status 200-299)
        if (response.ok) {

            posting.innerText = await response.text();
            setTimeout(() => {
                posting.innerText = "";
            }, 1000);

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
function showMyDoubts(button) {
    const isMyDoubts = button.textContent === 'My Doubts';
    const fetchUrl = isMyDoubts ? '/doubts/myDoubts' : '/doubts/allDoubts';

    fetch(fetchUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // Parse the JSON response
        })
        .then(data => {
            // Clear existing doubts
            noDoubts(data);

            // Create new doubt cards
            data.forEach(doubt => {
                const colDiv = document.createElement('div');
                colDiv.className = 'col-md-4 mb-4';
                colDiv.innerHTML = `
                <div class="card">
                    <div class="card-header">
                        <h5>${doubt.doubtTitle}</h5>
                    </div>
                    <div class="card-body">
                        <input type="hidden" id="doubt-id" value="${doubt.doubtId}" />
                        <h6 class="card-subtitle mb-2 text-muted">Asked by: ${doubt.userName}</h6>
                        <p class="card-text">${doubt.doubtDescription}</p>
                        <p class="card-text">
                            <small class="text-muted">${new Date(doubt.doubtDate).toLocaleString()}</small>
                        </p>
                    </div>
                    <div class="card-footer">
                        <a href="javascript:void(0)" class="btn btn-primary" onclick="showReplyBox(this)">Reply</a>
                        <button class="btn btn-secondary ml-2" onclick="showReplies(this)">Show Replies</button>
                        <div class="reply-box mt-2">
                            <textarea class="form-control mb-2" placeholder="Enter your reply"></textarea>
                            <button class="btn btn-success btn-sm" onclick="postReply(this)">Post</button>
                        </div>
                        <!-- Only show the Clear button for "My Doubts" -->
                        ${isMyDoubts ? '<button onclick="clearDoubt(this)" class="btn btn-primary">Clear</button>' : ''}
                        <div class="replies">
                            <!-- Replies will be shown here dynamically -->
                        </div>
                        <div id="allReplies"></div>
                    </div>
                </div>
            `;
                doubtsContainer.appendChild(colDiv);
            });

            // Update button text after content is loaded
            button.textContent = isMyDoubts ? "All Doubts" : "My Doubts";
        })
        .catch(error => {
            console.error('Error fetching doubts:', error);
        });
}
function clearDoubt(button) {
    // Locate the card that contains the clicked button
    const cardElement = button.closest('.card');
    const idElement = cardElement.querySelector('#doubt-id'); // Find hidden input
    const doubtId = idElement.value.trim(); // Get the doubt ID

    // Confirm with the user before removing the doubt
    const confirmRemoval = confirm('Are you sure you want to remove this doubt?');
    if (confirmRemoval) {
        // Send the DELETE request to the server
        fetch(`/doubts/deleteDoubt/${doubtId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                // Remove the card element from the DOM if the response is successful
                cardElement.remove();
                window.location.reload()
            })
            .catch(error => {
                console.error('Error deleting doubt:', error);
                alert('There was an error removing the doubt. Please try again.');
            });
    }
}
function noDoubts(data){
    const doubtsContainer = document.querySelector('.row');
    doubtsContainer.innerHTML = ''; // Clear existing doubts

    // Check if there are doubts
    if (data.length === 0) {
        const alertDiv = document.createElement('div');
        alertDiv.className = 'alert alert-info';
        alertDiv.role = 'alert';
        alertDiv.innerText = 'No doubts available.';
        doubtsContainer.appendChild(alertDiv);
        return;
    }
}

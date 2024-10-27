// Show the reply box for a particular card
function showReplyBox(button) {
    const replyBox = button.parentElement.querySelector('.reply-box');
    replyBox.style.display = replyBox.style.display === 'none' || replyBox.style.display === '' ? 'block' : 'none';
}

// Function to post a reply
function postReply(button) {
    const cardElement = button.closest('.card');
    const idElement = cardElement.querySelector('#doubt-id'); // Find hidden input
    const replyTextArea = button.previousElementSibling; // Get the textarea
    const replyText = replyTextArea.value.trim(); // Trim the reply text

    if (replyText !== "" && idElement) {
        const payload = {
            id: idElement.value.trim(),
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
                return response.json(); // Expect JSON with reply details
            })
            .then(data => {
                if (data && data.repliedBy && data.message) {
                    showReply(data, button); // Pass the full data object
                } else {
                    console.error('Invalid reply data:', data);
                }

                // Clear the textarea and hide the reply box
                replyTextArea.value = ""; // Clear the textarea
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    } else {
        alert('Please enter a reply and ensure the ID is present.');
    }
}

// Function to fetch and show replies for a particular doubt
function showReplies(button) {
    const cardElement = button.closest('.card');
    const idElement = cardElement.querySelector('#doubt-id'); // Find hidden input
    const replyId = idElement.value.trim();
    const repliesDiv = cardElement.querySelector('.replies');

    if (repliesDiv.style.display === 'block') {
        repliesDiv.style.display = 'none';
    } else {
        if (replyId) {
            fetch(`/doubts/getReply/${replyId}`)
                .then(response => {
                    if (!response.ok) {
                        repliesDiv.innerHTML = "<p><i>No reply yet....</i></p>";
                        repliesDiv.style.display = 'block';
                        throw new Error(`No replies found.`);
                    }
                    return response.json();
                })
                .then(data => {
                    repliesDiv.innerHTML = ''; // Clear existing replies

                    if (Array.isArray(data)) {
                        data.forEach(reply => {
                            if (reply && reply.repliedBy && reply.message) {
                                showReply(reply, button);
                            } else {
                                console.warn('Incomplete reply data:', reply);
                            }
                        });
                    } else {
                        console.error('Expected an array of replies, but got:', data);
                    }

                    repliesDiv.style.display = 'block';
                })
                .catch(error => {
                    console.error('There was a problem with the fetch operation:', error);
                });
        } else {
            alert('No reply ID found.');
        }
    }
}

// Function to display a single reply on the page
function showReply(reply, button) {
    const cardElement = button.closest('.card');
    const repliesDiv = cardElement.querySelector('.replies');

    const replyDiv = document.createElement('div');
    replyDiv.innerHTML = `<p><b>${reply.repliedBy || "Anonymous"} : </b>${reply.message || "No message provided"}</p>`;

    repliesDiv.insertAdjacentElement("afterbegin", replyDiv);
}

// Function to handle the "Post Doubt" form submission
const doubtForm = document.getElementById('doubtForm');
doubtForm.addEventListener('submit', async function (event) {
    event.preventDefault();
    const posting = document.getElementById("posting");

    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;
    posting.innerText = "Posting...";

    const postData = {
        doubtTitle: title,
        doubtDescription: description
    };

    try {
        const response = await fetch('/doubts/postDoubts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postData)
        });

        if (response.ok) {
            posting.innerText = await response.text();
            setTimeout(() => {
                posting.innerText = "";
            }, 1000);

            doubtForm.reset(); // Clear the form
        } else {
            const errorData = await response.text();
            posting.innerText = errorData;
            console.error('Error response:', errorData);
        }
    } catch (error) {
        console.error('Error:', error);
        posting.innerText = "An error occurred while posting the doubt.";
    }
});

// Function to toggle between "My Doubts" and "All Doubts" views
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
                const noDoubt = document.createElement("div");
                noDoubt.style.marginLeft = "1rem";
                noDoubt.innerText = "You have no Doubt....";
                doubtsContainer.textContent = "";
                doubtsContainer.appendChild(noDoubt);
                button.textContent = "All Doubts";
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            noDoubts(data);

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
                        <div class="reply-box mt-2" style="display: none;">
                            <textarea class="form-control mb-2" placeholder="Enter your reply"></textarea>
                            <button class="btn btn-success btn-sm" onclick="postReply(this)">Post</button>
                        </div>
                        ${isMyDoubts ? '<button onclick="clearDoubt(this)" class="btn btn-primary">Clear</button>' : ''}
                        <div class="replies"></div>
                    </div>
                </div>`;
                doubtsContainer.appendChild(colDiv);
            });

            button.textContent = isMyDoubts ? "All Doubts" : "My Doubts";
        })
        .catch(error => {
            console.error('Error fetching doubts:', error);
        });
}

// Function to clear a specific doubt
function clearDoubt(button) {
    const cardElement = button.closest('.card');
    const idElement = cardElement.querySelector('#doubt-id');
    const doubtId = idElement.value.trim();

    const confirmRemoval = confirm('Are you sure you want to remove this doubt?');
    if (confirmRemoval) {
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
                cardElement.remove();
                window.location.reload();
            })
            .catch(error => {
                console.error('Error deleting doubt:', error);
                alert('There was an error removing the doubt. Please try again.');
            });
    }
}

// Helper function to display no doubts message
function noDoubts(data) {
    doubtsContainer.innerHTML = '';
    if (data.length === 0) {
        const alertDiv = document.createElement('div');
        alertDiv.className = 'alert alert-info';
        alertDiv.role = 'alert';
        alertDiv.innerText = 'No doubts available.';
        doubtsContainer.appendChild(alertDiv);
    }
}

// Redirect to the all doubts page
function solveDoubts() {
    window.location.href = "/allDoubts";
}

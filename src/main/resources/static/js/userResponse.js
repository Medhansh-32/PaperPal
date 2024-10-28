document.getElementById('uploadForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Prevent the form from submitting the traditional way

    // Gather form data
    const formData = new FormData(this);
    const statusMessage = document.getElementById('uploadStatus');
    statusMessage.textContent = "uploading file, may take few seconds.....";
    statusMessage.style.color = "white";
    statusMessage.style.fontStyle="italic"
    statusMessage.style.fontSize="1rem"
    try {
        // Send POST request with form data
        const response = await fetch('/userresponse', {
            method: 'POST',
            body: formData
        });

        // Display the response status

        if (response.ok) {
            statusMessage.textContent = "File uploaded successfully!";
            statusMessage.style.color = "green";
            statusMessage.style.fontSize="1rem"
            setTimeout(()=>{
                statusMessage.textContent = "";
            },2000)

        } else {
            statusMessage.textContent = "Failed to upload file. Please try again.";
            statusMessage.style.color = "red";
            statusMessage.style.fontSize="1rem"
            setTimeout(()=>{
                statusMessage.textContent = "";
            },2000)

        }
    } catch (error) {
        const statusMessage = document.getElementById('uploadStatus');
        statusMessage.textContent = "Failed to upload file. Please try again.";
        statusMessage.style.color = "red";
        statusMessage.style.fontSize="1rem"
        setTimeout(()=>{
            statusMessage.textContent = "";
        },2000)

    }
});
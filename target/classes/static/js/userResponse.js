document.getElementById('uploadForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Prevent the form from submitting the traditional way

    // Gather form data
    const formData = new FormData(this);
    const statusMessage = document.getElementById('uploadStatus');

    try {
        statusMessage.textContent = "Please Wait...";
        statusMessage.style.fontSize="1.3rem"
        // Send POST request with form data
        const response = await fetch('/userresponse', {
            method: 'POST',
            body: formData
        });

        // Display the response status

        if (response.ok) {
            statusMessage.textContent = "Your material will be first examined and if successful, then it will be uploaded, Thank You. ";
            statusMessage.style.color = "green";
            statusMessage.style.fontSize="1.3rem"
            setTimeout(()=>{
                statusMessage.textContent = "";
                statusMessage.style.color = "white";
                },4000)

        } else {
            statusMessage.textContent = "Failed to upload file. Please try again.";
            statusMessage.style.color = "red";
            statusMessage.style.fontSize="1.3rem"
            setTimeout(()=>{
                statusMessage.textContent = "";
                statusMessage.style.color = "white";
            },2000)

        }
    } catch (error) {
        const statusMessage = document.getElementById('uploadStatus');
        statusMessage.textContent = "Failed to upload file. Please try again.";
        statusMessage.style.color = "red";
        statusMessage.style.fontSize="1.3rem"
        setTimeout(()=>{
            statusMessage.textContent = "";
            statusMessage.style.color = "white";
        },2000)

    }
});
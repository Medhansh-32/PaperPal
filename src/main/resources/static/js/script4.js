const themeLink = document.getElementById('theme-style');

// Function to switch between light and dark themes
function toggleTheme() {
    const currentTheme = themeLink.getAttribute('href');
    if (currentTheme.includes('dark')) {
        themeLink.setAttribute('href', '/css/style.css');
        localStorage.setItem('theme', 'light');
    }
    else {
        themeLink.setAttribute('href', '/css/style-dark.css');
        localStorage.setItem('theme', 'dark');
    }
}

// Add event listeners for both PC and mobile theme toggles
document.getElementById('theme-toggle').addEventListener('click', toggleTheme);
document.getElementById('theme-toggles').addEventListener('click', toggleTheme);

// Load saved theme from localStorage on page load
window.addEventListener('load', () => {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'light') {
        themeLink.setAttribute('href', '/css/style.css');
    } else {
        themeLink.setAttribute('href', '/css/style-dark.css');
    }
});
function goBack() {
    window.history.back();
    setTimeout(function() {
        window.location.reload(); // Refresh the page after going back
    }, 1000);
}
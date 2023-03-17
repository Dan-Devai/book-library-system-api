// Functions to get and remove JWT tokens from the cookie
function getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

function getToken() {
    return getCookie("jwt");
}

function removeToken() {
    document.cookie = "jwt=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
}

// Function to handle login form submission
function handleLoginFormSubmit(event) {
    event.preventDefault();

    // Get form data
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // Make a request to the /authenticate endpoint with the user's credentials
    fetch("/authenticate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({email, password}),
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Failed to authenticate");
            }
        })
        .then((data) => {
            // Redirect to the home page
            window.location.href = "/home";
        })
        .catch((error) => {
            console.error("Error:", error);
            alert("Authentication failed. Please check your credentials.");
        });
}

// Function to handle logout button click
function handleLogoutButtonClick() {
    console.log('Logging out');
    console.log('Token before logout:', getToken());

    fetch('/logout', {
        method: 'POST',
        credentials: 'same-origin',
    })
        .then((response) => {
            if (response.ok) {
                console.log('Logged out successfully');
            } else {
                throw new Error('Failed to log out');
            }
        })
        .then(() => {
            // Redirect to log in after logging out
            window.location.href = '/login';
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('Logout failed');
        });
}


function attachEventListeners() {
    // Attach the login form submission handler
    const loginForm = document.getElementById("login-form");
    if (loginForm) {
        loginForm.addEventListener("submit", handleLoginFormSubmit);
    }

    // Attach the logout button click handler
    const logoutButton = document.getElementById("logout-button");
    if (logoutButton) {
        logoutButton.addEventListener("click", handleLogoutButtonClick);
    }
}

// Execute the attachEventListeners function when the DOM is fully loaded
document.addEventListener("DOMContentLoaded", attachEventListeners);

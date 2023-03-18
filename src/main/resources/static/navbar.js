function updateNavbar() {
    const token = getToken('jwt');
    console.log("Token After calling:", token);

    const logoutButton = document.getElementById("logout-button");
    const loginButton = document.getElementById("login-button");
    const registerButton = document.getElementById("register-button");

    if (token) {
        if (logoutButton) logoutButton.style.display = "block";
        if (loginButton) loginButton.style.display = "none";
        if (registerButton) registerButton.style.display = "none";
    } else {
        if (logoutButton) logoutButton.style.display = "none";
        if (loginButton) loginButton.style.display = "block";
        if (registerButton) registerButton.style.display = "block";
    }
}

document.addEventListener("DOMContentLoaded", updateNavbar);

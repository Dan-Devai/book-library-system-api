// add validation to check if password and confirmPassword match
const passwordInput = document.getElementById("password");
const confirmPasswordInput = document.getElementById("confirmPassword");
const submitBtn = document.getElementById("submitBtn");

function validatePassword() {
    if (passwordInput.value !== confirmPasswordInput.value) {
        confirmPasswordInput.setCustomValidity("Passwords do not match");
        submitBtn.title = "Passwords do not match";
    } else {
        confirmPasswordInput.setCustomValidity("");
        submitBtn.title = "";
    }
}

function checkEmailExists(email) {
    return fetch(`/emailExists?email=${email}`)
        .then((response) => response.json())
        .then((exists) => exists);
}

passwordInput.addEventListener("change", validatePassword);
confirmPasswordInput.addEventListener("keyup", validatePassword);

// enable/disable submit button based on validation
confirmPasswordInput.addEventListener("input", () => {
    if (passwordInput.value === confirmPasswordInput.value) {
        submitBtn.disabled = false;
    } else {
        submitBtn.disabled = true;
    }
});


document.getElementById("registerForm").addEventListener(
    "submit", async function (event) {
    event.preventDefault();

    const fullName = document.getElementById("fullName").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        alert("Passwords do not match");
        return;
    }

    const emailExists = await checkEmailExists(email);
    if (emailExists) {
        alert("Email is already in use");
        return;
    }

    // Submit the form using the default behavior
    this.submit();
});

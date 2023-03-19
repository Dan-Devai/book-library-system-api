async function validateIsbn(input) {
    const value = input.value;
    const errorElement = document.getElementById('isbnError');
    const existsErrorElement = document.getElementById('isbnExistsError');
    const submitBtn = document.getElementById('submitBtn');

    if (value.length === 10 || value.length === 13) {
        errorElement.style.display = 'none';

        // Check if the ISBN exists
        const isbnExists = await checkIsbnExists(value);
        if (isbnExists) {
            existsErrorElement.style.display = 'block';
            submitBtn.disabled = true;
        } else {
            existsErrorElement.style.display = 'none';
            submitBtn.disabled = false;
        }
    } else {
        errorElement.style.display = 'block';
        submitBtn.disabled = true;
    }
}

function restrictFutureDates(inputId) {
    const today = new Date().toISOString().split('T')[0];
    document.getElementById(inputId).setAttribute('max', today);
}

function addInputListener(inputSelector, buttonId) {
    const inputs = document.querySelectorAll(inputSelector);
    const button = document.getElementById(buttonId);
    let initialFormState = '';

    for (const input of inputs) {
        input.addEventListener('input', () => {
            const currentFormState = getFormState(inputs);
            button.disabled = initialFormState === currentFormState;
            button.title = button.disabled ? 'No changes have been made' : ''; // Add this line
        });
    }

    initialFormState = getFormState(inputs);
    button.disabled = initialFormState === getFormState(inputs);
    button.title = button.disabled ? 'No changes have been made' : ''; // Add this line
}

function getFormState(inputs) {
    let formState = '';
    for (const input of inputs) {
        formState += input.value;
    }
    return formState;
}

async function checkIsbnExists(isbn) {
    try {
        const response = await fetch(`/api/books/check_isbn?isbn=${isbn}`);
        const result = await response.json();
        return result;
    } catch (error) {
        console.error('Error checking ISBN:', error);
        return false;
    }
}
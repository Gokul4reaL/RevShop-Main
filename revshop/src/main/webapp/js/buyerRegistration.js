const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

// Toggle between sign-in and sign-up forms
signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

// Function to handle sign-up form submission
async function handleSignUp(event) {
    event.preventDefault(); // Prevent the default form submission

    // Collect values from the sign-up form
    const firstName = document.querySelector('.sign-up-container input[name="firstName"]').value;
    const lastName = document.querySelector('.sign-up-container input[name="lastName"]').value;
    const emailId = document.querySelector('.sign-up-container input[name="emailId"]').value;
    const phoneNumber = document.querySelector('.sign-up-container input[name="phoneNumber"]').value;
    const dob = document.querySelector('.sign-up-container input[name="dob"]').value;
    const password = document.querySelector('.sign-up-container input[name="password"]').value;

    // Prepare data in x-www-form-urlencoded format
    const data = new URLSearchParams({
        firstName,
        lastName,
        emailId,
        phoneNumber,
        dob,
        password
    });

    try {
        const response = await fetch('http://localhost:8080/revshop/registerUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: data.toString(), // Send the data as a URL-encoded string
        });

        const result = await response.text(); // Use text() to read response for registration

        if (response.ok) {
            alert('Sign up successful!');
            // Optionally, redirect or clear the form here
			window.location.href = '../revshop/buyerRegistration.jsp';
        } else {
            alert(result || 'Sign up failed. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('There was an error. Please try again.');
    }
}

// Function to handle sign-in form submission
async function handleSignIn(event) {
    event.preventDefault(); // Prevent the default form submission

    // Collect values from the sign-in form
    const emailId = document.querySelector('.sign-in-container input[name="emailId"]').value;
    const password = document.querySelector('.sign-in-container input[name="password"]').value;

    // Prepare data in x-www-form-urlencoded format
    const data = new URLSearchParams({
        emailId,
        password
    });

    try {
        const response = await fetch('http://localhost:8080/revshop/loginUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: data.toString(), // Send the data as a URL-encoded string
        });

        const result = await response.json(); // Use text() to read response for login

        if (response.ok) {
            // Save the token to local storage
			const { token, userId, firstName, lastName, emailId, phoneNumber, dob, hashedPassword } = result;

            // Save the JWT token and user details to local storage
            localStorage.setItem('userToken', token);
            localStorage.setItem('userId', userId);
            localStorage.setItem('firstName', firstName);
            localStorage.setItem('lastName', lastName);
            localStorage.setItem('emailId', emailId);
            localStorage.setItem('phoneNumber', phoneNumber);
            localStorage.setItem('dob', dob);
            localStorage.setItem('hashedPassword', hashedPassword);	
			
			console.log("User ID: ", localStorage.getItem("userId"));		
            alert('Sign in successful! Token saved to local storage.');
			window.location.href = '../revshop/userHomeComponent.jsp';
            // Optionally, redirect to a dashboard or home page
        } else {
            alert(result || 'Sign in failed. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('There was an error. Please try again.');
    }
}

// Event listeners for form submissions
document.querySelector('.sign-up-container form').addEventListener('submit', handleSignUp);
document.querySelector('.sign-in-container form').addEventListener('submit', handleSignIn);

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

// Function to handle seller sign-up form submission
async function handleSellerSignUp(event) {
    event.preventDefault(); // Prevent the default form submission

    // Collect values from the seller sign-up form
    const firstName = document.querySelector('.sign-up-container input[name="firstName"]').value;
    const lastName = document.querySelector('.sign-up-container input[name="lastName"]').value;
    const emailId = document.querySelector('.sign-up-container input[name="emailId"]').value;
    const phoneNumber = document.querySelector('.sign-up-container input[name="phoneNumber"]').value;
    const dob = document.querySelector('.sign-up-container input[name="dob"]').value;
    const password = document.querySelector('.sign-up-container input[name="password"]').value;
    const businessName = document.querySelector('.sign-up-container input[name="businessName"]').value;
    const businessAddress = document.querySelector('.sign-up-container input[name="businessAddress"]').value;
    const businessPhone = document.querySelector('.sign-up-container input[name="businessPhone"]').value;
    const gstNumber = document.querySelector('.sign-up-container input[name="gstNumber"]').value;

    // Prepare data in x-www-form-urlencoded format
    const data = new URLSearchParams({
        firstName,
        lastName,
        emailId,
        phoneNumber,
        dob,
        password,
        businessName,
        businessAddress,
        businessPhone,
        gstNumber
    });

    try {
        const response = await fetch('http://localhost:8080/revshop/registerSeller', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: data.toString(), // Send the data as a URL-encoded string
        });

        const result = await response.text(); // Use text() to read response for registration

        if (response.ok) {
            alert('Seller sign up successful!');
            // Optionally, redirect or clear the form here
            window.location.href = '../revshop/sellerRegistration.jsp';
        } else {
            alert(result || 'Seller sign up failed. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('There was an error. Please try again.');
    }
}

// Function to handle seller sign-in form submission
async function handleSellerSignIn(event) {
    event.preventDefault(); // Prevent the default form submission

    // Collect values from the seller sign-in form
    const emailId = document.querySelector('.sign-in-container input[name="emailId"]').value;
    const password = document.querySelector('.sign-in-container input[name="password"]').value;

    // Prepare data in x-www-form-urlencoded format
    const data = new URLSearchParams({
        emailId,
        password
    });

    try {
        const response = await fetch('http://localhost:8080/revshop/loginSeller', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: data.toString(), // Send the data as a URL-encoded string
        });

        const result = await response.json(); // Use text() to read response for login

        if (response.ok) {
            // Save the token to local storage
            localStorage.setItem('sellerToken', result.token); // Store the token with key 'token'
		    // Save seller details to local storage
		    const seller = result.seller;
		    localStorage.setItem('sellerId', result.sellerId);
		    localStorage.setItem('firstName', result.firstName);
		    localStorage.setItem('lastName', result.lastName);
		    localStorage.setItem('emailId', result.emailId);
		    localStorage.setItem('phoneNumber', result.phoneNumber);
		    localStorage.setItem('dob', result.dob);
		    localStorage.setItem('businessName', result.businessName);
		    localStorage.setItem('businessAddress', result.businessAddress);
		    localStorage.setItem('businessPhone', result.businessPhone);
		    localStorage.setItem('gstNumber', result.gstNumber);
            console.log("JWT Token: ", localStorage.getItem('token'));
			console.log("Seller ID: ", localStorage.getItem('sellerId'));
            alert('Seller sign in successful! Token saved to local storage.');
            // Optionally, redirect to seller dashboard or home page
            window.location.href = '../revshop/sellerHomeComponent.jsp';
        } else {
            alert(result || 'Seller sign in failed. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('There was an error. Please try again.');
    }
}

// Event listeners for form submissions
document.querySelector('.sign-up-container form').addEventListener('submit', handleSellerSignUp);
document.querySelector('.sign-in-container form').addEventListener('submit', handleSellerSignIn);

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seller Login/Signup Form</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="css/sellerRegistration.css"> <!-- Link to your CSS file -->
</head>
<body>
    <div class="container" id="container">
        <!-- Seller Registration Form -->
        <div class="form-container sign-up-container">
            <form action="#">
                <h1>Create Seller Account</h1>
                <div class="social-container">
                    <a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
                    <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
                </div>
                <!-- Personal Information -->
                <input type="text" name="firstName" placeholder="First Name" required />
                <input type="text" name="lastName" placeholder="Last Name" required />
                <input type="email" name="emailId" placeholder="Email" required />
                <input type="tel" name="phoneNumber" placeholder="Mobile Number" required />
                <input type="text" id="dob" name="dob" placeholder="Date of Birth" required onfocus="(this.type='date')" onblur="(this.type='text')" />                
                <input type="password" name="password" placeholder="Password" required />
                
                <!-- Business Information -->
                <input type="text" name="businessName" placeholder="Business Name" required />
                <input type="text" name="businessAddress" placeholder="Business Address" required />
                <input type="tel" name="businessPhone" placeholder="Business Phone" required />
                <input type="number" name="gstNumber" placeholder="GST Number" required />

                <button type="submit">Sign Up</button>
            </form>
        </div>

        <!-- Seller Login Form -->
        <div class="form-container sign-in-container">
            <form action="#">
                <h1>Seller Sign in</h1>
                <div class="social-container">
                    <a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
                    <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
                </div>
                <span>or use your account</span>
                <input type="email" name="emailId" placeholder="Email" required />
                <input type="password" name="password" placeholder="Password" required />
                <a href="#">Forgot your password?</a>
                <button type="submit">Sign In</button>
            </form>
        </div>

        <div class="overlay-container">
            <div class="overlay">
                <div class="overlay-panel overlay-left">
                    <h1>Welcome Back!</h1>
                    <p>To manage your business, please login with your seller account</p>
                    <button class="ghost" id="signIn">Sign In</button>
                </div>
                <div class="overlay-panel overlay-right">
                    <h1>Hello, Seller!</h1>
                    <p>Enter your business details and start selling with us</p>
                    <button class="ghost" id="signUp">Sign Up</button>
                </div>
            </div>
        </div>
    </div>

    <!-- JavaScript for toggling between sign in and sign up -->
    <script src="js/sellerRegistration.js"></script> <!-- Link to your JavaScript file -->
</body>
</html>

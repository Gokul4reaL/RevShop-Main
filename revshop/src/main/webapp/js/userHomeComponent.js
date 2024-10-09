let currentPage = 1;
const pageSize = 10; // Number of products per page


function showLoader() {
    console.log("Showing loader");
    document.getElementById('loader-overlay').style.display = 'block';
}

function hideLoader() {
    console.log("Hiding loader");
    document.getElementById('loader-overlay').style.display = 'none';
}

function fetchAllProducts(page) {
	showLoader();
	const data = new URLSearchParams({
	        page,
			pageSize
	    });

	    fetch('/revshop/getProducts', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/x-www-form-urlencoded',
				'Authorization': 'Bearer ' + localStorage.getItem('userToken')
	        },
			body: data.toString()
	    })
        .then(response => response.json())
        .then(data => {
            displayProducts(data);
            updatePageInfo(page);
			hideLoader();
        })
        .catch(error => console.error('Error fetching products:', error));
}

// Function to filter products
function filterProducts() {
    const productName = document.getElementById('productNameFilter').value.trim();
    const description = document.getElementById('descriptionFilter').value.trim();
    
    // Prepare the data only for non-empty filters
    const data = new URLSearchParams({
        page: currentPage,       // Keep page and pageSize to handle pagination
        pageSize
    });

    if (productName) {
        data.append('productName', productName);
    }
    
    if (description) {
        data.append('description', description);
    }

    fetch('/revshop/getProducts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken')
        },
        body: data.toString()
    })
    .then(response => response.json())
    .then(data => {
        displayProducts(data);
        updatePageInfo(currentPage); // Assume currentPage is tracked for pagination
    })
    .catch(error => console.error('Error filtering products:', error));
}

function viewReviews(productId) {
    const data = new URLSearchParams({
        productId: productId
    });
    fetch('/revshop/getProductReviews', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken'),
        },
        body: data.toString()
    })
    .then(response => response.json())
    .then(reviews => {
        displayReviewsModal(reviews, productId);
    })
    .catch(error => console.error('Error fetching reviews:', error));
}

// Function to display the reviews modal
// Function to display the reviews modal
function displayReviewsModal(reviews, productId) {
    let modalContent = `
        <div class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h2>Reviews for Product ID: ${productId}</h2>
    `;

    if (reviews.length === 0) {
        // No reviews case
        modalContent += `
            <p>No reviews yet. Be the first to add a review!</p>
        `;
    } else {
        // Display existing reviews
        reviews.forEach(review => {
            modalContent += `
                <div class="review">
                    <strong>Rating: ${review.rating} Stars</strong>
                    <p>${review.reviewDescription}</p>
                </div>
            `;
        });
    }

    modalContent += `</div></div>`;
    document.body.insertAdjacentHTML('beforeend', modalContent);
}


// Function to close the modal
function closeModal() {
    const modal = document.querySelector('.modal');
    if (modal) {
        modal.remove(); // Remove the modal from the DOM
    }
}


function displayProducts(products) {
    const productsContainer = document.getElementById('productsContainer');
    productsContainer.innerHTML = ''; // Clear previous products

    products.forEach(product => {
        const productCard = document.createElement('div');
        productCard.className = 'product-card';
        productCard.innerHTML = `
            <img src="${product.imageUrl}" alt="Product Image">
            <div class="product-info">
                <h3>${product.productName}</h3>
                <p>Original Price: Rs.${product.price.toFixed(2)}</p>
                <p>Discounted Price: Rs.${product.discountedPrice.toFixed(2)}</p>
                <p>Available Quantity: ${product.quantity}</p>
                <p>${product.description}</p>
				<!-- Rating Section -->
				                <div class="product-rating" data-product-id="${product.productId}">
				                    ${renderStarRating(product.rating, product.productId)} <!-- This function will generate stars based on rating -->
				                    <span class="view-reviews" onclick="viewReviews('${product.productId}')">View Reviews</span>
				                </div>
                <div class="product-actions">
                    <input type="number" class="quantity-input" value="1" min="1" max="${product.quantity}" 
                        oninput="validateQuantity(this, ${product.quantity})"> <!-- Added validation -->
                    <button class="cart-btn" 
                        onclick="addToCart('${product.productId}', ${product.price}, ${product.discountedPrice}, this.parentElement.querySelector('.quantity-input').value)">
                        <i class="fas fa-shopping-cart"></i> Add to Cart
                    </button>
                    <button class="favorite-btn" onclick="toggleFavorite('${product.productId}', this)">
                        <i class="fas fa-heart"></i> Add to Favorites
                    </button>
                </div>
            </div>
        `;
        productsContainer.appendChild(productCard);
    });
}

// Function to toggle favorite status
function toggleFavorite(productId, button) {
    // Use a data attribute to store the favorited state
    const isFavorited = button.dataset.favorited === 'true';

    const data = new URLSearchParams({
        userId: localStorage.getItem("userId"),
        productId
    });

    const url = isFavorited ? '/revshop/removeFromFavorites' : '/revshop/addToFavorites';

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken')
        },
        body: data.toString()
    })
    .then(response => {
        if (response.ok) {
            // Toggle the data attribute
			button.dataset.favorited = isFavorited ? 'false' : 'true';

			            // Ensure the <i> element exists and update it
			            const icon = button.querySelector('i');
			            if (icon) {
			                icon.className = isFavorited ? 'far fa-heart' : 'fas fa-heart'; // Toggle between filled/empty heart
			            } else {
			                console.error("Icon element not found in the button");
			            }

			            // Update the button text
			            button.innerText = isFavorited ? 'Add to Favorites' : 'Remove from Favorites';

			            // Show success alert
			            alert(isFavorited ? 'Product removed from favorites!' : 'Product added to favorites!');
        } else {
            throw new Error('Product is already added to favorites');
        }
    })
    .catch(error => {
        console.error('Error toggling favorite:', error);
        alert('An error occurred: ' + error.message);
    });
}


// Function to validate quantity input
function validateQuantity(input, maxQuantity) {
    if (input.value > maxQuantity) {
        input.value = maxQuantity; // Set the value to max if it exceeds the limit
    } else if (input.value < 1) {
        input.value = 1; // Ensure the value doesn't go below 1
    }
}

// Function to render star rating based on rating value
function renderStarRating(rating, productId) {
    let stars = '';
    for (let i = 1; i <= 5; i++) {
        stars += `
            <i class="fas fa-star ${i <= rating ? 'filled' : ''}" 
               onclick="viewProduct(${productId})" 
               title="${i} Star${i > 1 ? 's' : ''}"></i>`;
    }
    return stars;
}



// Add to cart function
function addToCart(productId, price, discountedPrice, quantity) {
    console.log("Product ID: ", productId);
    const data = new URLSearchParams({
        userId : localStorage.getItem("userId"),
        productId,
        quantity,
        priceAtTime: price,
        discountedPriceAtTime: discountedPrice
    });

	fetch('/revshop/addToCart', {
	    method: 'POST',
	    headers: {
	        'Content-Type': 'application/x-www-form-urlencoded',
	        'Authorization': 'Bearer ' + localStorage.getItem('userToken')
	    },
	    body: data.toString()
	})
	.then(response => {
	    if (response.ok) {
			alert('Product added to cart successfully!'); // Alert after successful parsing of JSON
	        return response.json(); // Return the parsed JSON response only if the response is ok
	    } else {
			alert('Product already added to the cart!'); // Alert after successful parsing of JSON
	        throw new Error('Failed to add product to cart');
	    }
	})
	.catch(error => console.error('Error adding to cart:', error));
}



// Function to change the current page
function changePage(newPage) {
    if (newPage < 1) return; // Prevent going to a negative page
    currentPage = newPage;
    filterProducts(); // Call filterProducts to refresh displayed products based on filters
}

// Function to update the page info
function updatePageInfo(page) {
    document.getElementById('pageInfo').innerText = `Page ${page}`;
}


function handleLogout() {
    // Perform logout logic
    localStorage.removeItem("userId");
    localStorage.removeItem("userToken");
    window.location.href = "/revshop/landingComponent.jsp";
}
// Initial fetch of all products on page load
fetchAllProducts(currentPage);

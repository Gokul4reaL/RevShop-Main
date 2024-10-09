/**
 * 
 */document.addEventListener("DOMContentLoaded", () => {
	showLoader();
    fetchCartItems();
});

function showLoader() {
    console.log("Showing loader");
    document.getElementById('loader-overlay').style.display = 'block';
}

function hideLoader() {
    console.log("Hiding loader");
    document.getElementById('loader-overlay').style.display = 'none';
}


function fetchCartItems() {
	showLoader();  // Call before API call
    const userId = localStorage.getItem("userId");    
    fetch('/revshop/getCartItems', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken')
        },
        body: new URLSearchParams({ userId })
    })
    .then(response => response.json())
    .then(data => {
        displayCartItems(data);
        calculateCartSummary(data);
    })
    .catch(error => console.error('Error fetching cart items:', error))
	.finally(() => {
	        hideLoader(); // Ensure this is called after API completes
	    });
}

function displayCartItems(cartItems) {
    const cartItemsContainer = document.getElementById('cartItemsContainer');
    cartItemsContainer.innerHTML = ''; // Clear previous items

    cartItems.forEach(item => {
        const cartItem = document.createElement('div');
        cartItem.className = 'cart-item';
        cartItem.innerHTML = `
            <img src="${item.imageUrl}" alt="Product Image">
            <div class="item-info">
                <h3>${item.productName}</h3>
                <p>Price: Rs.${item.priceAtTime.toFixed(2)}</p>
                <div class="quantity-controls">
                    <input type="number" value="${item.quantity}" min="1" max="${item.availableQuantity}" onchange="updateQuantity('${item.productId}', this.value)">
                </div>
            </div>
            <button class="remove-btn" onclick="removeFromCart('${item.productId}')">Remove</button>
        `;
        cartItemsContainer.appendChild(cartItem);
    });
}

function updateQuantity(productId, quantity) {
    const userId = localStorage.getItem("userId");

    fetch('/revshop/updateCartQuantity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken')
        },
        body: new URLSearchParams({ userId, productId, quantity })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Quantity updated!');
            fetchCartItems(); // Refresh the cart
        } else {
            alert('Failed to update quantity');
        }
    })
    .catch(error => console.error('Error updating quantity:', error));
}

function removeFromCart(productId) {
    const userId = localStorage.getItem("userId");

    fetch('/revshop/removeFromCart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken')
        },
        body: new URLSearchParams({ userId, productId })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Item removed from cart!');
			window.location.href = window.location.href;
            fetchCartItems(); // Refresh the cart
        } else {
            alert('Failed to remove item');
        }
    })
    .catch(error => console.error('Error removing item:', error));
}

function calculateCartSummary(cartItems) {
    let totalItems = 0;
    let totalPrice = 0;

    cartItems.forEach(item => {
        totalItems += item.quantity;
        totalPrice += item.quantity * item.priceAtTime;
    });

    document.getElementById('totalItems').innerText = totalItems;
    document.getElementById('totalPrice').innerText = totalPrice.toFixed(2);
}

function proceedToCheckout() {
    const userId = localStorage.getItem("userId");

    // Save cart details to session storage before proceeding
    fetch('/revshop/getCartItems', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken')
        },
        body: new URLSearchParams({ userId })
    })
    .then(response => response.json())
    .then(data => {
        sessionStorage.setItem('checkoutCartItems', JSON.stringify(data));
        window.location.href = "/revshop/checkoutConfirmationComponent.jsp";
    })
    .catch(error => console.error('Error fetching cart items for checkout:', error));
}

function handleLogout() {
    // Perform logout logic
    localStorage.removeItem("userId");
    localStorage.removeItem("userToken");
    window.location.href = "/revshop/landingComponent.jsp";
}

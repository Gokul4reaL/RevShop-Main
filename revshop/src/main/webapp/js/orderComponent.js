document.addEventListener("DOMContentLoaded", () => {
	showLoader();
    fetchOrders();
});

function showLoader() {
    console.log("Showing loader");
    document.getElementById('loader-overlay').style.display = 'block';
}

function hideLoader() {
    console.log("Hiding loader");
    document.getElementById('loader-overlay').style.display = 'none';
}

function fetchOrders() {
	showLoader();
    const userId = localStorage.getItem("userId");

    fetch('/revshop/getUserOrders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken')
        },
        body: new URLSearchParams({ userId })
    })
    .then(response => response.json())
    .then(data => {
        displayOrders(data);
		hideLoader();
    })
    .catch(error => console.error('Error fetching orders:', error));
}

function displayOrders(orderItems) {
    const orderItemsContainer = document.getElementById('orderItemsContainer');
    orderItemsContainer.innerHTML = ''; // Clear previous items

    if (orderItems.length === 0) {
        orderItemsContainer.innerHTML = '<p>No orders found.</p>';
    } else {
        orderItems.forEach(order => {
            const orderItem = document.createElement('div');
            orderItem.className = 'order-item';
            orderItem.innerHTML = `
                <div class="order-info">
                    <h3>Order ID: ${order.orderId}</h3>
                    <p>Total Amount: Rs.${order.totalAmount.toFixed(2)}</p>
                    <p>Status: ${order.status}</p>
                </div>
                <div class="order-products">
                    ${order.orderItems.map(product => `
                        <div class="product-item">
                            <img src="${product.imageUrl}" alt="Product Image">
                            <p>Product Name: ${product.productName}</p>
                            <p>Quantity: ${product.quantity}</p>
                            <!-- Add Review Button -->
                            <button class="review-btn" onclick="openReviewModal('${product.productId}')">Add Review</button>
                        </div>
                    `).join('')}
                </div>
            `;
            orderItemsContainer.appendChild(orderItem);
        });
    }
}

function openReviewModal(productId) {
    console.log("Add review button clicked for productId:", productId); // Check if this is logged
    const modalContent = `
        <div class="modal" id="reviewModal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h2>Submit Review for Product ID: ${productId}</h2>
                <label for="reviewRating">Rating (out of 5):</label>
                <input type="number" id="reviewRating" min="1" max="5" required>
                <label for="reviewComment">Review:</label>
                <textarea id="reviewComment" rows="4" cols="50" required></textarea>
                <button onclick="submitReview('${productId}')">Submit Review</button>
            </div>
        </div>
    `;
    document.body.insertAdjacentHTML('beforeend', modalContent);
}

function closeModal() {
    const modal = document.getElementById('reviewModal');
    if (modal) {
        modal.remove(); // Remove modal from DOM
    }
}

function submitReview(productId) {
    const rating = document.getElementById('reviewRating').value;
    const reviewDescription = document.getElementById('reviewComment').value;

    // Check if both fields are filled
    if (!rating || !reviewDescription) {
        alert("Please fill out all fields.");
        return;
    }

    const data = new URLSearchParams({
        productId,
        userId: localStorage.getItem("userId"),
        rating,
        reviewDescription
    });

    fetch('/revshop/addReview', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken')
        },
        body: data.toString()
    })
    .then(response => {
        if (response.ok) {
            alert('Review submitted successfully!');
            closeModal();
        } else {
            throw new Error('Failed to submit review');
        }
    })
    .catch(error => {
        console.error('Error submitting review:', error);
        alert('An error occurred while submitting the review');
    });
}

function handleLogout() {
    // Perform logout logic
    localStorage.removeItem("userId");
    localStorage.removeItem("userToken");
    window.location.href = "/revshop/landingComponent.jsp";
}

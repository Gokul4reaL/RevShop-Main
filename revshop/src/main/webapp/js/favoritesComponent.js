document.addEventListener("DOMContentLoaded", () => {
    fetchFavoriteItems();
	showLoader();
});

function showLoader() {
    console.log("Showing loader");
    document.getElementById('loader-overlay').style.display = 'block';
}

function hideLoader() {
    console.log("Hiding loader");
    document.getElementById('loader-overlay').style.display = 'none';
}

function fetchFavoriteItems() {
	showLoader();
    const userId = localStorage.getItem("userId");

    fetch('/revshop/getFavoriteItems', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken')
        },
        body: new URLSearchParams({ userId })
    })
    .then(response => response.json())
    .then(data => {
        displayFavoriteItems(data);
		hideLoader();
    })
    .catch(error => console.error('Error fetching favorite items:', error));
}

function displayFavoriteItems(favoriteItems) {
    const favoritesItemsContainer = document.getElementById('favoritesItemsContainer');
    favoritesItemsContainer.innerHTML = ''; // Clear previous items

    favoriteItems.forEach(item => {
        const favoriteItem = document.createElement('div');
        favoriteItem.className = 'favorite-item'; // Updated class name to reflect favorites
        favoriteItem.innerHTML = `
            <img src="${item.imageUrl}" alt="Product Image" class="product-image">
            <div class="item-info">
                <h3>${item.productName}</h3>
                <p>Price: Rs.${item.price ? item.price.toFixed(2) : 'N/A'}</p>
                <p>Discounted Price: Rs.${item.discountedPrice ? item.discountedPrice.toFixed(2) : 'N/A'}</p>
            </div>
            <button class="remove-btn" onclick="removeFromFavorites('${item.productId}')">Remove</button>
        `;
        favoritesItemsContainer.appendChild(favoriteItem);
    });
}

function removeFromFavorites(productId) {
    const userId = localStorage.getItem("userId");

    fetch('/revshop/removeFromFavorites', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('userToken')
        },
        body: new URLSearchParams({ userId, productId })
    })
    .then(response => response.json())
    .then(data => {
        if (data.message) {
            alert('Item removed from favorites!');
            fetchFavoriteItems(); // Refresh the favorites list
        } else {
            alert('Failed to remove item');
        }
    })
    .catch(error => console.error('Error removing item:', error));
}

function handleLogout() {
    // Perform logout logic
    localStorage.removeItem("userId");
    localStorage.removeItem("userToken");
    window.location.href = "/revshop/landingComponent.jsp";
}

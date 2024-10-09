document.addEventListener("DOMContentLoaded", function() {
    // Fetch and display products when the page loads
	showLoader()
    fetchProducts();

    // Handle add product form submission
    document.getElementById("add-product-form").addEventListener("submit", function(e) {
        e.preventDefault();
		const fileInput = document.getElementById("imageFile"); // Make sure to have the input with id "imageUrl"
		    const file = fileInput.files[0]; // Get the selected file

		    if (file) {
		        const fileReader = new FileReader();
		        
		        // Set up the onload event handler
		        fileReader.onload = () => {
					console.log("File read successfully");
	                const fileContent = fileReader.result; // Base64 string
	                addProduct(fileContent); // Call function to add product
					fileContent = null;
	                fileInput.value = ""; // Clear input after reading
	            };
	            // Read the file as a data URL
	            fileReader.readAsDataURL(file);
		    } else {
		        console.error("No file selected"); // Handle the case where no file is selected
		    }
    });

    // Handle edit product form submission
    document.getElementById("edit-product-form").addEventListener("submit", function(e) {
        e.preventDefault();
        updateProduct();
    });
});

// Fetch products from the server
function fetchProducts() {
	showLoader()
    fetch(`http://localhost:8080/revshop/getProductsBySellerId?sellerId=${encodeURIComponent(localStorage.getItem('sellerId'))}`, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('sellerToken')
        }
    })
    .then(response => response.json())
    .then(data => {
		hideLoader();
        populateProductTable(data);
    })
    .catch(error => {
        console.error("Error fetching products:", error);
    });
}

// Populate product table with fetched data
// Populate product table with fetched data
function populateProductTable(products) {
    const productList = document.getElementById("product-list");
    productList.innerHTML = ""; // Clear existing rows

    products.forEach(product => {
        const row = `
            <tr>
                <td><img src="${product.imageUrl}" alt="${product.productName}" width="50" height="50"/></td>
				<td>${product.productName}</td>
                <td>${product.description}</td>
                <td>${product.price}</td>
                <td>${product.discountedPrice}</td>
                <td>${product.quantity}</td>
                <td>${product.isPublished ? "Published" : "Unpublished"}</td>
                <td>${new Date(product.createdDate).toLocaleDateString()}</td>
                <td>
                    <button onclick="showEditProductModal('${product.productId}')">Edit</button>
                    <button onclick="deleteProduct('${product.productId}')">Delete</button>
                </td>
            </tr>`;
        productList.innerHTML += row;
    });
}

// Add new product to inventory
// Add new product to inventory
function addProduct(base64String) {
    const productData = new URLSearchParams({
        productName: document.getElementById("productName").value,
        price: document.getElementById("price").value,
        discountedPrice: document.getElementById("discountedPrice").value,
        quantity: document.getElementById("quantity").value,
        description: document.getElementById("description").value,
        imageUrl: base64String,
        sellerId: localStorage.getItem('sellerId'),
    });

    fetch('http://localhost:8080/revshop/addProduct', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + localStorage.getItem('sellerToken')
        },
        body: productData.toString()
    })
    .then(response => {
        if (response.ok) {
            console.log("Product added successfully");
            closeAddProduct(); // Close the modal
            fetchProducts(); // Refresh the product list
        } else {
            response.text().then(text => {
                console.error("Failed to add product:", response.status, text);
            });
        }
    })
    .catch(error => {
        console.error("Error adding product:", error);
    });
}

// Show the Add Product Modal
function showAddProduct() {
    document.getElementById("add-product-modal").style.display = "block";
}

// Close the Add Product Modal
function closeAddProduct() {
    document.getElementById("add-product-modal").style.display = "none";
}

// Show the Edit Product Modal and populate the fields
function showEditProductModal(productId) {
    fetch(`http://localhost:8080/revshop/getProductById?productId=${productId}`, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('sellerToken')
        }
    })
    .then(response => response.json())
    .then(product => {
        document.getElementById("editProductId").value = product.productId;
        document.getElementById("editProductName").value = product.productName;
        document.getElementById("editPrice").value = product.price;
        document.getElementById("editDiscountedPrice").value = product.discountedPrice;
        document.getElementById("editQuantity").value = product.quantity;
        document.getElementById("editDescription").value = product.description;
		document.getElementById("editImage").src = product.imageUrl;
        document.getElementById("edit-product-modal").style.display = "block";
		document.getElementById("editIsPublished").checked = product.isPublished; // Checkbox for isPublished
		document.getElementById("editCreatedDate").value = product.createdDate; // Show the creation date
    });
}

// Close the Edit Product Modal
function closeEditProductModal() {
    document.getElementById("edit-product-modal").style.display = "none";
}

// Update existing product details
function updateProduct() {
    const fileInput = document.getElementById("editImageFile"); // Get the input file element
    const file = fileInput.files[0]; // Get the selected file

    if (file) {
        const fileReader = new FileReader();

        // Set up the onload event handler
        fileReader.onload = () => {
            const fileContent = fileReader.result; // Base64 string

            // Prepare the product data with the Base64 image string
            const productData = new URLSearchParams({
                productId: document.getElementById("editProductId").value,
                sellerId: localStorage.getItem('sellerId'),
                productName: document.getElementById("editProductName").value,
                price: document.getElementById("editPrice").value,
                discountedPrice: document.getElementById("editDiscountedPrice").value,
                quantity: document.getElementById("editQuantity").value,
                description: document.getElementById("editDescription").value,
                isPublished: document.getElementById("editIsPublished").checked,
                createdDate: document.getElementById("editCreatedDate").value,
                imageUrl: fileContent // Use the Base64 string here
            });

            // Make the fetch call to update the product
            fetch('http://localhost:8080/revshop/editProductById', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Authorization': 'Bearer ' + localStorage.getItem('sellerToken')
                },
                body: productData.toString()
            })
            .then(response => {
                if (response.ok) {
                    closeEditProductModal();
                    fetchProducts(); // Refresh the product list
                } else {
                    console.error("Failed to update product");
                }
            });
        };

        // Read the file as a data URL
        fileReader.readAsDataURL(file);
    } else {
        // If no new image file is selected, you can choose to update the product without changing the image
        const productData = new URLSearchParams({
            productId: document.getElementById("editProductId").value,
            sellerId: localStorage.getItem('sellerId'),
            productName: document.getElementById("editProductName").value,
            price: document.getElementById("editPrice").value,
            discountedPrice: document.getElementById("editDiscountedPrice").value,
            quantity: document.getElementById("editQuantity").value,
            description: document.getElementById("editDescription").value,
            isPublished: document.getElementById("editIsPublished").checked,
            createdDate: document.getElementById("editCreatedDate").value,
            imageUrl: document.getElementById("currentImageUrl").value // Assuming you have a hidden input with the current image URL
        });

        // Make the fetch call to update the product
        fetch('http://localhost:8080/revshop/editProductById', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': 'Bearer ' + localStorage.getItem('sellerToken')
            },
            body: productData.toString()
        })
        .then(response => {
            if (response.ok) {
                closeEditProductModal();
                fetchProducts(); // Refresh the product list
            } else {
                console.error("Failed to update product");
            }
        });
    }
}

// Delete a specific product from inventory
function deleteProduct(productId) {
    fetch(`http://localhost:8080/revshop/deleteProductById?productId=${productId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('sellerToken')
        }
    })
    .then(response => {
        if (response.ok) {
            fetchProducts(); // Refresh the product list after deletion
        } else {
            console.error("Failed to delete product");
        }
    });
}

function showLoader() {
    console.log("Showing loader");
    document.getElementById('loader-overlay').style.display = 'block';
}

function hideLoader() {
    console.log("Hiding loader");
    document.getElementById('loader-overlay').style.display = 'none';
}

function logout() {
    // Clear seller token and ID from local storage
    localStorage.removeItem('sellerToken');
    localStorage.removeItem('sellerId');
    
    // Redirect to login page (replace 'login.html' with your actual login page)
    window.location.href = 'landingComponent.jsp';
}

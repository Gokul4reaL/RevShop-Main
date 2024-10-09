<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seller Home</title>
    <link rel="stylesheet" href="css/sellerHomeComponent.css">
</head>
<body>

<div id="loader-overlay">
        <div class="loader"></div>
    </div>
    
    <div class="seller-dashboard">
        <h1>Welcome, Seller!</h1>
        <button class="logout-btn" onclick="logout()">Logout</button>
       

        <!-- Inventory Management Section -->
        <section class="inventory-management">
            <h2>Inventory Management</h2>
            <button class="add-product-btn" onclick="showAddProduct()">Add New Product</button>
            <div class="inventory-table">
                <table>
                    <thead>
                        <tr>
                            <th>Product Image</th>
                            <th>Product Name</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Discounted Price</th>
                            <th>Stock</th>
                            <th>Status</th>
                            <th>Created Date</th>
                            <th>Edit/Delete</th>
                        </tr>
                    </thead>
                    <tbody id="product-list">
                        <!-- Product rows will be dynamically populated -->
                    </tbody>
                </table>
            </div>
        </section>

        <!-- Add Product Modal -->
        <div id="add-product-modal" class="modal" style="display:none;">
            <div class="modal-content">
                <span class="close" onclick="closeAddProduct()">&times;</span>
                <h2>Add Product</h2>
                <form id="add-product-form">
                    <input type="text" id="productName" placeholder="Product Name" required>
                    <input type="number" id="price" placeholder="Price" required>
                    <input type="number" id="discountedPrice" placeholder="Discounted Price">
                    <input type="number" id="quantity" placeholder="Stock Quantity" required>
                    <input type="text" id="description" placeholder="Description" required>
					<input type="file" id="imageFile" accept="image/*" required>
                    <button type="submit">Add Product</button>
                </form>
            </div>
        </div>

        <!-- Edit Product Modal -->
		<div id="edit-product-modal" class="modal" style="display:none;">
		    <div class="modal-content">
		        <span class="close" onclick="closeEditProductModal()">&times;</span>
		        <h2>Edit Product</h2>
		        <form id="edit-product-form">
		            <input type="hidden" id="editProductId">
		            <input type="text" id="editProductName" placeholder="Product Name" required>
		            <input type="number" id="editPrice" placeholder="Price" required>
		            <input type="number" id="editDiscountedPrice" placeholder="Discounted Price">
		            <input type="number" id="editQuantity" placeholder="Stock Quantity" required>
		            <input type="text" id="editDescription" placeholder="Description">
					<img id="editImage" src="" alt="Product Image" />
		            <input type="hidden" id="editIsPublished">
            		<input type="hidden" id="editCreatedDate">
		            <button type="submit">Update Product</button>
		        </form>
		    </div>
		</div>
        

    </div>

    <!-- Link to your JavaScript file -->
    <script src="js/sellerHomeComponent.js"></script> 
</body>
</html>
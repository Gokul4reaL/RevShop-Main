package com.revshop.repository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.revshop.dao.ProductDAO;
import com.revshop.model.Products;
import com.revshop.dto.*;

public class ProductRepository {

    // Add a new product
    public static Products addProductRepo(ProductDTO request) throws SQLException {
        String productId = java.util.UUID.randomUUID().toString(); // Generate a new UUID for productId
        String sellerId = request.getSellerId();
        String productName = request.getProductName();
        double price = request.getPrice();
        String description = request.getDescription();
        int quantity = request.getQuantity();
        double discountedPrice = request.getDiscountedPrice();
        Date createdDate = new Date(System.currentTimeMillis()); // Set current date
        boolean isPublished = request.isPublished();
        String imageUrl = request.getImageUrl();

        // Create a new Products object
        Products newProduct = new Products(productId, sellerId, productName, price, description, quantity, discountedPrice, createdDate, isPublished, imageUrl);

        // Call ProductDAO to add the product to the database
        ProductDAO productDAO = new ProductDAO();
        productDAO.addProduct(newProduct);

        return newProduct; // Return the newly created product object
    }

    // Get a product by ID
    public static Products getProductByIdRepo(String productId) throws SQLException {
        ProductDAO productDAO = new ProductDAO(); // Assuming productDAO is properly instantiated
        return productDAO.getProductById(productId); // Return the product retrieved by its ID
    }

    // Edit a product by ID
    public static Products editProductByIdRepo(EditProductDTO request) throws SQLException {
        // Retrieve the product to be updated
        ProductDAO productDAO = new ProductDAO();
        String productId = request.getProductId();
        Products existingProduct = productDAO.getProductById(productId);

        // Check if the product exists
        if (existingProduct != null) {
            // Update product details
            existingProduct.setProductName(request.getProductName());
            existingProduct.setPrice(request.getPrice());
            existingProduct.setDescription(request.getDescription());
            existingProduct.setQuantity(request.getQuantity());
            existingProduct.setDiscountedPrice(request.getDiscountedPrice());
            existingProduct.setPublished(request.getIsPublished());
            existingProduct.setImageUrl(request.getImageUrl());

            // Call DAO to update the product in the database
            productDAO.editProductById(productId, existingProduct);

            return existingProduct; // Return the updated product object
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    // Delete a product by ID
    public static void deleteProductByIdRepo(String productId) throws SQLException {
        ProductDAO productDAO = new ProductDAO();
        productDAO.deleteProductById(productId); // Call DAO to delete the product from the database
    }
    
    public static List<Products> getProductsBySellerIdRepo(String sellerId) throws SQLException {
        ProductDAO productDAO = new ProductDAO();
        return productDAO.getProductsBySellerId(sellerId); // Call DAO to retrieve products by seller ID
    }
    
 // Retrieve all products from the database
    public static List<Products> getAllProductsRepo(int page, int pageSize) throws SQLException {
        ProductDAO productDAO = new ProductDAO(); // Instantiate ProductDAO
        return productDAO.getAllProducts(page, pageSize); // Call method to retrieve all products
    }

    // Retrieve filtered products based on name, description, or "starts with"
    public static List<Products> getFilteredProductsRepo(String productName, String description, int page, int pageSize) throws SQLException {
        ProductDAO productDAO = new ProductDAO(); // Instantiate ProductDAO
        return productDAO.getFilteredProducts(productName, description, page, pageSize); // Call method to retrieve filtered products
    }
}


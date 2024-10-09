package com.revshop.service;

import com.revshop.dto.*;
import com.revshop.model.Products;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    // Method for adding a new product
    Products addProductService(ProductDTO request) throws SQLException; 

    // Method for getting a product by ID
    Products getProductByIdService(String productId) throws SQLException;

    // Method for editing a product by ID
    Products editProductByIdService(EditProductDTO request) throws SQLException;

    // Method for deleting a product by ID
    void deleteProductByIdService(String productId) throws SQLException;
    
    // Method for getting products by seller ID
    List<Products> getProductsBySellerIdService(String sellerId) throws SQLException;
    
    // Method to retrieve all products
    List<Products> getAllProducts(int page, int pageSize) throws Exception;

    // Method to retrieve filtered products based on name, description, or starting letter
    List<Products> getFilteredProducts(String productNameFilter, String descriptionFilter, int page, int pageSize) throws Exception;
}
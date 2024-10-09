package com.revshop.serviceimpl;

import com.revshop.dto.*;
import com.revshop.model.Products;
import com.revshop.repository.ProductRepository;
import com.revshop.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductImpl implements ProductService {

    @Override
    public Products addProductService(ProductDTO request) throws SQLException {
        // Call the repository to add a new product
        return ProductRepository.addProductRepo(request);
    }

    @Override
    public Products getProductByIdService(String productId) throws SQLException {
        // Call the repository to retrieve a product by ID
        return ProductRepository.getProductByIdRepo(productId);
    }

    @Override
    public Products editProductByIdService(EditProductDTO request) throws SQLException {
        // Call the repository to edit a product by ID
        return ProductRepository.editProductByIdRepo(request);
    }

    @Override
    public void deleteProductByIdService(String productId) throws SQLException {
        // Call the repository to delete a product by ID
        ProductRepository.deleteProductByIdRepo(productId);
    }
    
    @Override
    public List<Products> getProductsBySellerIdService(String sellerId) throws SQLException {
    	return ProductRepository.getProductsBySellerIdRepo(sellerId);
    }
    
    @Override
    public List<Products> getAllProducts(int page, int pageSize) throws SQLException {
        // Call the repository to retrieve all products
        return ProductRepository.getAllProductsRepo(page, pageSize);
    }

    @Override
    public List<Products> getFilteredProducts(String productName, String description, int page, int pageSize) throws SQLException {
        // Call the repository to retrieve filtered products
        return ProductRepository.getFilteredProductsRepo(productName, description, page, pageSize);
    }
}


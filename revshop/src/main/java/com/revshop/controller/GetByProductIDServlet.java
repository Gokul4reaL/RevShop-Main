package com.revshop.controller;

import com.revshop.model.Products;
import com.revshop.service.ProductService;
import com.revshop.serviceimpl.ProductImpl;
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getProductById")
public class GetByProductIDServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductImpl(); // Initialize the service implementation
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	JavaAuthenticationFilter authFilter = new JavaAuthenticationFilter();

        // Use the filter to validate the JWT token
        if (!authFilter.validateToken(request, response)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return; // Exit if the token is invalid
        }
        
        String productId = request.getParameter("productId"); // Get the product ID from the request

        try {
            // Fetch the product details using the service
            Products product = productService.getProductByIdService(productId); // Assuming this method exists in ProductService

            if (product != null) {
                // If product is found, set response status and write product details as JSON
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(productToJson(product)); // Convert product to JSON
            } else {
                // If product not found, send a 404 status
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to retrieve product: " + e.getMessage());
        }
    }

    // Helper method to convert ProductDTO to JSON string
    private String productToJson(Products product) {
        return String.format("{\"productId\":\"%s\", \"sellerId\":\"%s\", \"productName\":\"%s\", " +
                             "\"price\":%.2f, \"description\":\"%s\", \"quantity\":%d, " +
                             "\"discountedPrice\":%.2f, \"createdDate\":\"%s\", \"isPublished\":%b, " +
                             "\"imageUrl\":\"%s\"}",
                             product.getProductId(), product.getSellerId(), product.getProductName(),
                             product.getPrice(), product.getDescription(), product.getQuantity(),
                             product.getDiscountedPrice(), product.getCreatedDate(), product.isPublished(),
                             product.getImageUrl());
    }
}

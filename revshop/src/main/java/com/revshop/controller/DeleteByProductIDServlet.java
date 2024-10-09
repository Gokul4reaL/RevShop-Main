package com.revshop.controller;

import com.revshop.service.ProductService;
import com.revshop.serviceimpl.ProductImpl;
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteProductById")
public class DeleteByProductIDServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductImpl(); // Initialize the service implementation
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	JavaAuthenticationFilter authFilter = new JavaAuthenticationFilter();

        // Use the filter to validate the JWT token
        if (!authFilter.validateToken(request, response)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return; // Exit if the token is invalid
        }
        
        String productId = request.getParameter("productId");

        try {
            // Call the service to delete the product
            productService.deleteProductByIdService(productId); // Assuming this method exists in ProductService

            // Send success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Product deleted successfully");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to delete product: " + e.getMessage());
        }
    }
}


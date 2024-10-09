package com.revshop.controller;

import com.revshop.dto.EditProductDTO;
import com.revshop.service.ProductService;
import com.revshop.serviceimpl.ProductImpl;
import com.revshop.util.GoogleDriveService; // Import the Google Drive service
import com.revshop.util.JavaAuthenticationFilter;
import com.revshop.validator.ValidateProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Base64;

@WebServlet("/editProductById")
public class EditByProductIDServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductImpl(); // Initialize the service implementation
        try {
            GoogleDriveService.initialize(); // Call this to ensure driveService is set up
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JavaAuthenticationFilter authFilter = new JavaAuthenticationFilter();

        // Use the filter to validate the JWT token
        if (!authFilter.validateToken(request, response)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return; // Exit if the token is invalid
        }
        
        String productId = request.getParameter("productId");
        String sellerId = request.getParameter("sellerId");
        String productName = request.getParameter("productName");
        String priceStr = request.getParameter("price"); // Get the price as a string
        String description = request.getParameter("description");
        String quantityStr = request.getParameter("quantity"); // Get the quantity as a string
        String discountedPriceStr = request.getParameter("discountedPrice"); // Get the discounted price as a string
        String isPublishedStr = request.getParameter("isPublished"); // Get the isPublished as a string
        String base64Image = request.getParameter("imageUrl"); // Get the Base64 image string
        String createdDateStr = request.getParameter("createdDate"); // Assuming this is passed as a string

        try {
            // Validate that required fields are not null or empty
            if (productId == null || sellerId == null || productName == null ||
                priceStr == null || description == null || quantityStr == null ||
                discountedPriceStr == null || isPublishedStr == null || createdDateStr == null) {
                throw new IllegalArgumentException("All fields are required.");
            }

            // Parse values safely
            double price = Double.parseDouble(priceStr.trim());
            int quantity = Integer.parseInt(quantityStr.trim());
            double discountedPrice = Double.parseDouble(discountedPriceStr.trim());
            boolean isPublished = Boolean.parseBoolean(isPublishedStr);
            Date createdDate = Date.valueOf(createdDateStr.trim()); // Assuming this is passed as a valid string

            String imageUrl = null;
            // If a new image is uploaded
            if (base64Image != null && !base64Image.isEmpty()) {
                // Decode the Base64 image string
                String imageBase64 = base64Image; // This should contain the entire Base64 string
                if (imageBase64.contains(",")) {
                    imageBase64 = imageBase64.split(",")[1]; // Get only the base64 part
                }

                // Decode the Base64 string to bytes
                byte[] imageBytes = Base64.getDecoder().decode(imageBase64);

                // Create a temporary file to store the image
                File tempFile = File.createTempFile("upload-", ".jpg"); // Adjust the extension as needed
                try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                    outputStream.write(imageBytes);
                }

                // Specify your folder ID in Google Drive
                String folderID = "1tXg71Z10Hhirn0E1arBNHS3CI1ePKVnu"; // Update with your actual folder ID

                // Initialize GoogleDriveService and upload the file
                imageUrl = GoogleDriveService.uploadFile(tempFile, "image/jpeg", folderID);

                // Delete the temporary file
                if (tempFile.delete()) {
                    System.out.println("Temporary file deleted successfully.");
                } else {
                    System.err.println("Failed to delete temporary file.");
                }
            }

            // Create a ProductDTO object
            EditProductDTO productToUpdate = new EditProductDTO(productId, sellerId, productName, price, 
                    description, quantity, discountedPrice, createdDate, isPublished, imageUrl);

            // Validate product details
            ValidateProduct.validateEditProduct(productToUpdate); // Assuming this validation method exists

            // Call the service to update the product
            productService.editProductByIdService(productToUpdate); // Assuming this method exists in ProductService

            // Send success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Product updated successfully");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to update product: " + e.getMessage());
        }
    }
}

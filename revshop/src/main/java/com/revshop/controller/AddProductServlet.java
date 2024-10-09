package com.revshop.controller;

import com.revshop.dto.ProductDTO;
import com.revshop.service.ProductService;
import com.revshop.serviceimpl.ProductImpl;
import com.revshop.validator.ValidateProduct;
import com.revshop.util.GoogleDriveService; // Import the Google Drive service
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {

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
        // Create an instance of the JavaAuthenticationFilter
        JavaAuthenticationFilter authFilter = new JavaAuthenticationFilter();

        // Use the filter to validate the JWT token
        if (!authFilter.validateToken(request, response)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return; // Exit if the token is invalid
        }

        try {
            // Get product details from the request
            String sellerId = request.getParameter("sellerId");
            String productName = request.getParameter("productName");
            String priceStr = request.getParameter("price");
            String description = request.getParameter("description");
            String quantityStr = request.getParameter("quantity");
            String discountedPriceStr = request.getParameter("discountedPrice");
            String base64Image = request.getParameter("imageUrl"); // Get the Base64 image string
            
            System.out.println("base64Image: " + base64Image);

            // Check for null or empty values
            if (sellerId == null || productName == null || priceStr == null ||
                description == null || quantityStr == null || base64Image == null) {
                throw new IllegalArgumentException("All product fields are required.");
            }

            // Parse price, quantity, discounted price, and other values
            double price = Double.parseDouble(priceStr.trim());
            int quantity = Integer.parseInt(quantityStr.trim());
            double discountedPrice = discountedPriceStr != null && !discountedPriceStr.isEmpty() ? Double.parseDouble(discountedPriceStr.trim()) : 0;
            boolean isPublished = true;

         // Decode the Base64 image string
            String imageBase64 = base64Image; // This should contain the entire Base64 string
            if (imageBase64 != null && imageBase64.contains(",")) {
                imageBase64 = imageBase64.split(",")[1]; // Get only the base64 part
            } else {
                throw new IllegalArgumentException("Invalid Base64 image format.");
            }

            // Decode the Base64 string to bytes
            byte[] imageBytes = Base64.getDecoder().decode(imageBase64);

            // Create a temporary file to store the image
            File tempFile = File.createTempFile("upload-", ".jpg"); // Adjust the extension as needed
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                outputStream.write(imageBytes);
            }

            // Specify your folder ID in Google Drive
            String folderID = "1tXg71Z10Hhirn0E1arBNHS3CI1ePKVnu";

            // Initialize GoogleDriveService and upload the file
            String imageUrl = GoogleDriveService.uploadFile(tempFile, "image/jpeg", folderID);

            ProductDTO productRequest = new ProductDTO(sellerId, productName, price, description, quantity,
                    discountedPrice, isPublished, imageUrl);

            // Validate the incoming product request
            ValidateProduct.validateProduct(productRequest); // Pass the request object for validation

            // Add the product via ProductService
            productService.addProductService(productRequest); // Assuming this method exists in ProductService
            
            if (tempFile.delete()) {
                System.out.println("Temporary file deleted successfully.");
            } else {
                System.err.println("Failed to delete temporary file.");
            }

            // Send success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Product added successfully!");

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to add product: " + e.getMessage());
        }
    }
}

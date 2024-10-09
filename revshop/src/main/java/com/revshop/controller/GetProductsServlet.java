package com.revshop.controller;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.client.json.JsonFactory;
import com.revshop.model.Products;
import com.revshop.service.ProductService;
import com.revshop.serviceimpl.ProductImpl;
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Base64;

@WebServlet("/getProducts")
public class GetProductsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductImpl(); // Initialize the service implementation
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JavaAuthenticationFilter authFilter = new JavaAuthenticationFilter();

        // Validate the JWT token
        if (!authFilter.validateToken(request, response)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return;
        }

        // Get optional search filters from request parameters
        String initialPage = request.getParameter("page");
        String initialPageSize = request.getParameter("pageSize");

        String productNameFilter = request.getParameter("productName");
        String descriptionFilter = request.getParameter("description");

        try {
            List<Products> productsList;

            int page = Integer.parseInt(initialPage.trim());
            int pageSize = Integer.parseInt(initialPageSize.trim());

            // Check if filters are applied
            if ((productNameFilter == null || productNameFilter.isEmpty()) &&
                (descriptionFilter == null || descriptionFilter.isEmpty())) {

                // No filters applied, get all products
                productsList = productService.getAllProducts(page, pageSize); // Assuming this method exists
            } else {

                // Filters applied, call a method to fetch filtered products
                productsList = productService.getFilteredProducts(productNameFilter, descriptionFilter, page, pageSize);
            }

            if (productsList != null && !productsList.isEmpty()) {
                // Convert image URLs to Base64 format
                for (Products product : productsList) {
                    String imageUrl = product.getImageUrl(); // Assuming this is the Google Drive URL
                    String fileId = extractFileId(imageUrl); // Extract the file ID from the URL
                    String base64Image = convertImageToBase64(fileId);
                    product.setImageUrl(base64Image); // Update the product's imageUrl with the Base64 string
                }

                // If products are found, write the list as JSON
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(productsListToJson(productsList));
            } else {
                // No products found
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"No products found\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(String.format("{\"error\":\"Failed to retrieve products: %s\"}", e.getMessage()));
        }
    }

    // Helper method to convert a Google Drive file ID to Base64 string
    private String convertImageToBase64(String fileId) {
        String base64Image = "";
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    GetProductsServlet.class.getClassLoader().getResourceAsStream("revshop.json"))
                    .createScoped(Collections.singleton(DriveScopes.DRIVE_READONLY));

            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            Drive driveService = new Drive.Builder(new NetHttpTransport(), jsonFactory, new HttpCredentialsAdapter(credentials))
                    .setApplicationName("RevShop")
                    .build();

            // Download the file as an InputStream
            InputStream inputStream = driveService.files().get(fileId).executeMediaAsInputStream();
            byte[] imageBytes = readInputStreamAsBytes(inputStream);
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "data:image/png;base64," + base64Image; // Change MIME type if necessary
    }

    // Helper method to extract the file ID from Google Drive URL
    private String extractFileId(String url) {
        String[] parts = url.split("id=");
        if (parts.length > 1) {
            return parts[1];
        }
        return null;
    }

    // Helper method to read InputStream as bytes
    private byte[] readInputStreamAsBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }

    // Helper method to convert a list of Products to JSON string
    private String productsListToJson(List<Products> productsList) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("["); // Start JSON array

        for (int i = 0; i < productsList.size(); i++) {
            Products product = productsList.get(i);
            jsonBuilder.append(String.format("{\"productId\":\"%s\", \"sellerId\":\"%s\", \"productName\":\"%s\", " +
                                             "\"price\":%.2f, \"description\":\"%s\", \"quantity\":%d, " +
                                             "\"discountedPrice\":%.2f, \"createdDate\":\"%s\", \"isPublished\":%b, " +
                                             "\"imageUrl\":\"%s\", \"rating\": %.2f}",
                                             product.getProductId(), product.getSellerId(), product.getProductName(),
                                             product.getPrice(), product.getDescription(), product.getQuantity(),
                                             product.getDiscountedPrice(), product.getCreatedDate(), product.isPublished(),
                                             product.getImageUrl(), product.getAvg_rating()));

            if (i < productsList.size() - 1) {
                jsonBuilder.append(","); // Add comma between products
            }
        }

        jsonBuilder.append("]"); // End JSON array
        return jsonBuilder.toString();
    }
}

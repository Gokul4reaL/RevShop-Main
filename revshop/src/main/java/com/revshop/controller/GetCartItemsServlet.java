package com.revshop.controller;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.client.json.JsonFactory;
import com.revshop.model.Cart;
import com.revshop.service.CartService;
import com.revshop.serviceimpl.CartImpl;
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@WebServlet("/getCartItems")
public class GetCartItemsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CartService cartService;

    @Override
    public void init() {
        cartService = new CartImpl(); // Initialize the CartService implementation
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

        String userId = request.getParameter("userId");

        if (userId == null || userId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Missing or empty userId\"}");
            return;
        }

        try {
            // Get all cart items for the user
            List<Cart> cartItems = cartService.getCartItemsByUserId(userId);

            if (cartItems != null && !cartItems.isEmpty()) {
                // Convert product image URLs to Base64 format
                for (Cart cartItem : cartItems) {
                    String imageUrl = cartItem.getImageUrl(); // Assuming this is the Google Drive URL
                    String fileId = extractFileId(imageUrl);  // Extract the file ID from the URL
                    String base64Image = convertImageToBase64(fileId);
                    cartItem.setImageUrl(base64Image);  // Update the cart item with Base64 image
                }

                // Convert cart items list to JSON and send as response
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(cartItemsToJson(cartItems));
            } else {
                // No cart items found for the user
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"No cart items found for the user\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(String.format("{\"error\":\"Failed to retrieve cart items: %s\"}", e.getMessage()));
        }
    }

    // Helper method to convert a Google Drive file ID to Base64 string
    private String convertImageToBase64(String fileId) {
        String base64Image = "";
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    GetCartItemsServlet.class.getClassLoader().getResourceAsStream("revshop.json"))
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

    // Helper method to convert a list of Cart items to JSON string
    private String cartItemsToJson(List<Cart> cartItems) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("["); // Start JSON array

        for (int i = 0; i < cartItems.size(); i++) {
            Cart cartItem = cartItems.get(i);
            jsonBuilder.append(String.format("{\"userId\":\"%s\", \"productId\":\"%s\", \"quantity\":%d, " +
                                             "\"priceAtTime\":%.2f, \"discountedPriceAtTime\":%.2f, \"status\":\"%s\", " +
                                             "\"addedDate\":\"%s\", \"productName\":\"%s\", \"imageUrl\":\"%s\"}",
                                             cartItem.getUserId(), cartItem.getProductId(), cartItem.getQuantity(),
                                             cartItem.getPriceAtTime(), cartItem.getDiscountedPriceAtTime(),
                                             cartItem.getStatus(), cartItem.getAddedDate(),cartItem.getProductName(), cartItem.getImageUrl()));

            if (i < cartItems.size() - 1) {
                jsonBuilder.append(","); // Add comma between cart items
            }
        }

        jsonBuilder.append("]"); // End JSON array
        return jsonBuilder.toString();
    }
}

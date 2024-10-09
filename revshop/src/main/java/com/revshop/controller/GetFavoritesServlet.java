package com.revshop.controller;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.client.json.JsonFactory;
import com.revshop.model.Favorites;
import com.revshop.service.FavoritesService;
import com.revshop.serviceimpl.FavoritesImpl;
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

@WebServlet("/getFavoriteItems")
public class GetFavoritesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private FavoritesService favoritesService;

    @Override
    public void init() {
        // Initialize the FavoritesService implementation
        favoritesService = new FavoritesImpl();
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
            // Get all favorite items for the user
            List<Favorites> favoriteItems = favoritesService.getFavoriteItemsByUserId(userId);

            if (favoriteItems != null && !favoriteItems.isEmpty()) {
                // Convert product image URLs to Base64 format
                for (Favorites favoriteItem : favoriteItems) {
                    String imageUrl = favoriteItem.getImageUrl(); // Assuming this is the Google Drive URL
                    String fileId = extractFileId(imageUrl);  // Extract the file ID from the URL
                    String base64Image = convertImageToBase64(fileId);
                    favoriteItem.setImageUrl(base64Image);  // Update the item with Base64 image
                }

                // Convert the list of favorite items to JSON format and send as response
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(favoriteItemsToJson(favoriteItems));
            } else {
                // No favorite items found for the user
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"No favorite items found for the user\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(String.format("{\"error\":\"Failed to retrieve favorite items: %s\"}", e.getMessage()));
        }
    }

    // Helper method to convert a Google Drive file ID to Base64 string
    private String convertImageToBase64(String fileId) {
        String base64Image = "";
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    GetFavoritesServlet.class.getClassLoader().getResourceAsStream("revshop.json"))
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

    // Helper method to convert a list of Favorites items to JSON string
    private String favoriteItemsToJson(List<Favorites> favoriteItems) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("["); // Start JSON array

        for (int i = 0; i < favoriteItems.size(); i++) {
            Favorites favoriteItem = favoriteItems.get(i);
            jsonBuilder.append(String.format("{\"userId\":\"%s\", \"productId\":\"%s\", \"productName\":\"%s\",\"price\":%.2f,\"discountedPrice\":%.2f, \"imageUrl\":\"%s\"}",
                                             favoriteItem.getUserId(), favoriteItem.getProductId(),
                                             favoriteItem.getProductName(),favoriteItem.getPrice(),favoriteItem.getDiscountedPriceAtTime(), favoriteItem.getImageUrl()));

            if (i < favoriteItems.size() - 1) {
                jsonBuilder.append(","); // Add comma between items
            }
        }

        jsonBuilder.append("]"); // End JSON array
        return jsonBuilder.toString();
    }
}

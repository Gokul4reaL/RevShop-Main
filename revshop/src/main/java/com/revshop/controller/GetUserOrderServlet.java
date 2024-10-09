package com.revshop.controller;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.client.json.JsonFactory;
import com.revshop.model.Orders;
import com.revshop.model.OrderItems;
import com.revshop.service.OrderService;
import com.revshop.service.OrderItemService;
import com.revshop.serviceimpl.OrdersImpl;
import com.revshop.serviceimpl.OrderItemsImpl;
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

@WebServlet("/getUserOrders")
public class GetUserOrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private OrderItemService orderItemService;

    @Override
    public void init() {
        orderService = new OrdersImpl(); // Initialize OrderService implementation
        orderItemService = new OrderItemsImpl(); // Initialize OrderItemService implementation
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
            // Get all orders for the user
            List<Orders> userOrders = orderService.getOrdersByUserId(userId);

            if (userOrders != null && !userOrders.isEmpty()) {
                // Loop through each order and retrieve order items
                for (Orders order : userOrders) {
                    List<OrderItems> orderItems = orderItemService.getOrderItemsByOrderId(order.getOrderId());
                    order.setOrderItems(orderItems); // Assuming Orders model has a list of OrderItems
                    for (OrderItems item : orderItems) {
                        String imageUrl = item.getImageUrl(); // Assuming imageUrl is present in OrderItems
                        String fileId = extractFileId(imageUrl);  // Extract the file ID from the URL
                        String base64Image = convertImageToBase64(fileId);
                        item.setImageUrl(base64Image);  // Update the order item with Base64 image
                    }
                }

                // Convert orders list to JSON and send as response
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(ordersToJson(userOrders));
            } else {
                // No orders found for the user
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"No orders found for the user\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(String.format("{\"error\":\"Failed to retrieve orders: %s\"}", e.getMessage()));
        }
    }

    // Helper method to convert Google Drive file ID to Base64 string
    private String convertImageToBase64(String fileId) {
        String base64Image = "";
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    GetUserOrderServlet.class.getClassLoader().getResourceAsStream("revshop.json"))
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

    // Helper method to convert a list of Orders to JSON string
    private String ordersToJson(List<Orders> userOrders) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("["); // Start JSON array

        for (int i = 0; i < userOrders.size(); i++) {
            Orders order = userOrders.get(i);
            jsonBuilder.append(String.format("{\"orderId\":\"%s\", \"userId\":\"%s\", \"status\":\"%s\", " +
                                             "\"orderDate\":\"%s\", \"totalAmount\":%.2f, \"orderItems\":[",
                                             order.getOrderId(), order.getUserId(), order.getStatus(),
                                             order.getUpdatedAt(), order.getTotalAmount()));

            // Add order items
            List<OrderItems> orderItems = order.getOrderItems();
            for (int j = 0; j < orderItems.size(); j++) {
                OrderItems item = orderItems.get(j);
                jsonBuilder.append(String.format("{\"productId\":\"%s\", \"quantity\":%d, \"price\":%.2f, " +
                                                 "\"discountedPrice\":%.2f, \"imageUrl\":\"%s\", \"productName\": \"%s\"}",
                                                 item.getProductId(), item.getQuantity(), item.getPriceAtTime(),
                                                 item.getDiscountedPriceAtTime(), item.getImageUrl(), item.getProductName()));

                if (j < orderItems.size() - 1) {
                    jsonBuilder.append(","); // Add comma between order items
                }
            }

            jsonBuilder.append("]}"); // End order object

            if (i < userOrders.size() - 1) {
                jsonBuilder.append(","); // Add comma between orders
            }
        }

        jsonBuilder.append("]"); // End JSON array
        return jsonBuilder.toString();
    }
}

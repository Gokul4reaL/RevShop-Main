package com.revshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revshop.dao.ProductDAO;
import com.revshop.dao.ProductDAO.ProductPriceInfo;
import com.revshop.model.OrderItems;
import com.revshop.model.Orders;
import com.revshop.service.OrderItemService;
import com.revshop.service.OrderService;
import com.revshop.serviceimpl.OrderItemsImpl;
import com.revshop.serviceimpl.OrdersImpl;
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/confirmOrder")
public class ConfirmOrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private OrderItemService orderItemService;
    private ProductDAO productDAO;

    @Override
    public void init() {
        orderService = new OrdersImpl(); // Initialize the service implementation
        orderItemService = new OrderItemsImpl();
        productDAO = new ProductDAO(); // Initialize the
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JavaAuthenticationFilter authFilter = new JavaAuthenticationFilter();

        if (!authFilter.validateToken(req, resp)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Unauthorized");
            return;
        }

        try {
            String userId = req.getParameter("userId");
            String amount = req.getParameter("totalAmount");
            String orderAddress = req.getParameter("orderAddress");
            String paymentMethod = req.getParameter("paymentMethod");

            if (userId == null || paymentMethod == null) {
                throw new IllegalArgumentException("User ID and payment method are required.");
            }
            
            double totalAmount = Double.parseDouble(amount.trim());

            String checkoutItemsJson = req.getParameter("checkoutItems");
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> checkoutItems = objectMapper.readValue(checkoutItemsJson, List.class);

            // Create a new order instance
            Orders order = new Orders(userId, totalAmount, orderAddress, paymentMethod);

            // Call createOrder and get the generated orderId
            String orderId = orderService.createOrder(order);

            // Now process each checkout item using the generated orderId
            for (Map<String, Object> item : checkoutItems) {
                String productId = (String) item.get("productId");
                int quantity = (int) item.get("quantity");
                
                System.out.println("Product ID: "+ productId);
                // Fetch price and discounted price from the database
                ProductPriceInfo priceInfo = productDAO.getProductPriceInfo(productId);
                
                if (priceInfo == null) {
                    throw new IllegalArgumentException("Invalid productId: " + productId);
                }

                String sellerId = priceInfo.getSellerId();
                double price = priceInfo.getPrice();
                double discountedPrice = priceInfo.getDiscountedPrice();

                // Create an OrderItem object
                OrderItems orderItem = new OrderItems(orderId, productId, sellerId, quantity, price, discountedPrice);

                // Save each order item in the database
                orderItemService.addOrderItem(orderItem);
            }

            // Send a success response
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"success\": true, \"message\": \"Order confirmed successfully!\"}");

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Failed to confirm the order: " + e.getMessage() + "\"}");
        }
    }
}

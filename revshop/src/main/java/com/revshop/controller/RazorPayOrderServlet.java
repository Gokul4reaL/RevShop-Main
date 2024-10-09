package com.revshop.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/RazorPayCreateOrder")
public class RazorPayOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String apiKey = "rzp_test_37MbLXJMCUd3yL";  // Replace with your Razorpay API Key
            String apiSecret = "FQwGGUeSjZm6VW8IwVeoj2d0";  // Replace with your Razorpay API Secret

            RazorpayClient client = new RazorpayClient(apiKey, apiSecret);

            // Get total price from request and convert to paise
            String totalPriceStr = req.getParameter("totalPrice");
            int amount = Integer.parseInt(totalPriceStr) * 100;  // INR to paise conversion

            // Create a new order
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_11");

            Order order = client.orders.create(orderRequest);

            // Send the order ID back to the frontend
            resp.setContentType("application/json");
            resp.getWriter().write(order.toString());

        } catch (Exception e) {
            Logger.getLogger(RazorPayOrderServlet.class.getName()).log(Level.SEVERE, null, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in creating Razorpay order");
        }
    }
}

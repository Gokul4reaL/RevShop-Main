package com.revshop.controller;

import com.revshop.model.Reviews;
import com.revshop.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.google.gson.Gson;

@WebServlet("/getProductReviews")
public class GetProductReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String productId = request.getParameter("productId");

        // Validate the product ID
        if (productId == null || productId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Product ID is required.\"}");
            return;
        }

        // Fetch reviews from the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM reviews WHERE product_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, productId);
                ResultSet resultSet = statement.executeQuery();

                ArrayList<Reviews> reviews = new ArrayList<>();
                while (resultSet.next()) {
                    Reviews review = new Reviews();
                    review.setUserId(resultSet.getString("user_id"));
                    review.setProductId(resultSet.getString("product_id"));
                    review.setRating(resultSet.getInt("rating"));
                    review.setReviewDescription(resultSet.getString("review_description"));

                    reviews.add(review);
                }

                // Convert reviews to JSON
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(reviews);

                // Return the reviews as a JSON response
                out.print(jsonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Unable to fetch reviews.\"}");
        } finally {
            out.close();
        }
    }
}

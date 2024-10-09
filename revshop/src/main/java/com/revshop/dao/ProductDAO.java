package com.revshop.dao;

import com.revshop.model.Products;
import com.revshop.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    // Existing SQL queries
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products (product_id, seller_id, product_name, price, description, quantity, discounted_price, created_date, is_published, image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_PRODUCT_BY_ID_SQL = "SELECT * FROM products WHERE product_id = ?";
    private static final String UPDATE_PRODUCT_BY_ID_SQL = "UPDATE products SET product_name = ?, price = ?, description = ?, quantity = ?, discounted_price = ?, is_published = ?, image_url = ? WHERE product_id = ?";
    private static final String DELETE_PRODUCT_BY_ID_SQL = "DELETE FROM products WHERE product_id = ?";
    
    // New SQL query to get products by seller ID
    private static final String SELECT_PRODUCTS_BY_SELLER_ID_SQL = "SELECT * FROM products WHERE seller_id = ?";
    
    // Query to get products with pagination and order by quantity (stock) in descending order
    private static final String SELECT_ALL_PRODUCTS_SQL = "SELECT * FROM products ORDER BY quantity DESC LIMIT ? OFFSET ?";


    // Method to add product (existing)
    public void addProduct(Products product) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {

            preparedStatement.setString(1, product.getProductId());
            preparedStatement.setString(2, product.getSellerId());
            preparedStatement.setString(3, product.getProductName());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getQuantity());
            preparedStatement.setDouble(7, product.getDiscountedPrice());
            preparedStatement.setDate(8, product.getCreatedDate());
            preparedStatement.setBoolean(9, product.isPublished());
            preparedStatement.setString(10, product.getImageUrl());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Product added successfully!");
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting product", e);
        }
    }

    // Method to get product by ID (existing)
    public Products getProductById(String productId) throws SQLException {
        Products product = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID_SQL)) {

            preparedStatement.setString(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = new Products(
                    resultSet.getString("product_id"),
                    resultSet.getString("seller_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("price"),
                    resultSet.getString("description"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("discounted_price"),
                    resultSet.getDate("created_date"),
                    resultSet.getBoolean("is_published"),
                    resultSet.getString("image_url")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving product by ID", e);
        }
        return product;
    }

    // Method to edit product by ID (existing)
    public void editProductById(String productId, Products updatedProduct) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_BY_ID_SQL)) {

            preparedStatement.setString(1, updatedProduct.getProductName());
            preparedStatement.setDouble(2, updatedProduct.getPrice());
            preparedStatement.setString(3, updatedProduct.getDescription());
            preparedStatement.setInt(4, updatedProduct.getQuantity());
            preparedStatement.setDouble(5, updatedProduct.getDiscountedPrice());
            preparedStatement.setBoolean(6, updatedProduct.isPublished());
            preparedStatement.setString(7, updatedProduct.getImageUrl());
            preparedStatement.setString(8, productId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("Failed to update product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating product", e);
        }
    }

    // Method to delete product by ID (existing)
    public void deleteProductById(String productId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_BY_ID_SQL)) {

            preparedStatement.setString(1, productId);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Failed to delete product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting product", e);
        }
    }

    // New Method to get products by Seller ID
    public List<Products> getProductsBySellerId(String sellerId) throws SQLException {
        List<Products> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTS_BY_SELLER_ID_SQL)) {

            // Set seller ID in the query
            preparedStatement.setString(1, sellerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set and add each product to the list
            while (resultSet.next()) {
                Products product = new Products(
                    resultSet.getString("product_id"),
                    resultSet.getString("seller_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("price"),
                    resultSet.getString("description"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("discounted_price"),
                    resultSet.getDate("created_date"),
                    resultSet.getBoolean("is_published"),
                    resultSet.getString("image_url")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving products by seller ID", e);
        }
        return products;
    }
    
    public List<Products> getAllProducts(int page, int pageSize) throws SQLException {
        List<Products> productsList = new ArrayList<>();
        int offset = (page - 1) * pageSize; // Calculate the offset based on page number and page size

        String getReviewSQL = "SELECT AVG(rating) as average_rating FROM reviews WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS_SQL)) {

            // Set limit and offset for pagination
            preparedStatement.setInt(1, pageSize);   // Limit to the number of products per page
            preparedStatement.setInt(2, offset);     // Offset for pagination

            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set and add each product to the list
            while (resultSet.next()) {
                String productId = resultSet.getString("product_id");
                double averageRating = 0.0; // Default rating if no reviews are found

                // Fetch the average rating for the product
                try (PreparedStatement getReviewStatement = connection.prepareStatement(getReviewSQL)) {
                    getReviewStatement.setString(1, productId);
                    ResultSet reviewResultSet = getReviewStatement.executeQuery();
                    if (reviewResultSet.next()) {
                        averageRating = reviewResultSet.getDouble("average_rating"); // Get the average rating
                    }
                }

                // Create the product object
                Products product = new Products(
                    productId,
                    resultSet.getString("seller_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("price"),
                    resultSet.getString("description"),
                    resultSet.getInt("quantity"),            // Stock quantity
                    resultSet.getDouble("discounted_price"),
                    resultSet.getDate("created_date"),
                    resultSet.getBoolean("is_published"),
                    resultSet.getString("image_url"),
                    averageRating // Add the average rating to the product object
                );

                productsList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving paginated products", e);
        }
        return productsList;
    }
    
 // Method to get filtered products based on product name or description
    public List<Products> getFilteredProducts(String productName, String description, int page, int pageSize) throws SQLException {
        List<Products> productsList = new ArrayList<>();
        int offset = (page - 1) * pageSize; // Calculate the offset for pagination

        // Build dynamic SQL query with optional filtering conditions
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM products WHERE 1=1");

        // Add filters if provided
        if (productName != null && !productName.trim().isEmpty()) {
            sqlQuery.append(" AND product_name LIKE ?");
        }
        if (description != null && !description.trim().isEmpty()) {
            sqlQuery.append(" AND description LIKE ?");
        }

        // Add sorting and pagination
        sqlQuery.append(" ORDER BY quantity DESC LIMIT ? OFFSET ?");

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.toString())) {

            int paramIndex = 1;

            // Set parameters for the filters
            if (productName != null && !productName.trim().isEmpty()) {
                preparedStatement.setString(paramIndex++, "%" + productName + "%");  // Filter by product name (substring search)
            }
            if (description != null && !description.trim().isEmpty()) {
                preparedStatement.setString(paramIndex++, "%" + description + "%");  // Filter by description (substring search)
            }

            // Set limit and offset for pagination
            preparedStatement.setInt(paramIndex++, pageSize);
            preparedStatement.setInt(paramIndex, offset);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set and add each product to the list
            while (resultSet.next()) {
                Products product = new Products(
                    resultSet.getString("product_id"),
                    resultSet.getString("seller_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("price"),
                    resultSet.getString("description"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("discounted_price"),
                    resultSet.getDate("created_date"),
                    resultSet.getBoolean("is_published"),
                    resultSet.getString("image_url")
                );
                productsList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving filtered products", e);
        }
        return productsList;
    }
    
 // Fetch price and discounted_price based on productId
    public ProductPriceInfo getProductPriceInfo(String productId) throws SQLException {
        String sql = "SELECT seller_id, price, discounted_price FROM products WHERE product_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
        	PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	String sellerId = rs.getString("seller_id");
                    double price = rs.getDouble("price");
                    double discountedPrice = rs.getDouble("discounted_price");
                    return new ProductPriceInfo(sellerId, price, discountedPrice);
                }
            }
        }
        return null;
    }
    
    // Class to hold price information
    public static class ProductPriceInfo {
    	private String sellerId;
        private double price;
        private double discountedPrice;

        public ProductPriceInfo(String sellerId, double price, double discountedPrice) {
        	this.sellerId = sellerId;
            this.price = price;
            this.discountedPrice = discountedPrice;
        }

        public String getSellerId() {
			return sellerId;
		}

		public void setSellerId(String sellerId) {
			this.sellerId = sellerId;
		}

		public double getPrice() {
            return price;
        }

        public double getDiscountedPrice() {
            return discountedPrice;
        }
    }

}

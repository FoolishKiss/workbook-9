package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

    private DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {

        // This is the SQL INSERT statement we will run.
        // We are inserting the film title, rental rate, and language_id.
        String sql = "INSERT INTO products ( ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)";

        // This is a "try-with-resources" block.
        // It ensures that the Connection and PreparedStatement are closed automatically after we are done.
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the second parameter (?) to the film's rental rate.
            stmt.setString(1, product.getName());

            stmt.setInt(2, product.getCategoryId());

            stmt.setDouble(3, product.getPrice());


            // Execute the INSERT statement — this will add the row to the database.
            stmt.executeUpdate();

        } catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> getAll() {
        //
        List<Product> products = new ArrayList<>();

        // This is the SQL SELECT statement we will run.
        String sql = "SELECT ProductID, ProductName, CategoryID, UnitPrice FROM products";

        // This is a "try-with-resources" block.
        // It ensures that the Connection, Statement, and ResultSet are closed automatically after we are done.
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through each row in the ResultSet.
            while (rs.next()) {
                // Create a new product object.
                Product product = new Product();

                // Set the film's ID from the "ProductID" column.
                product.setProductId(rs.getInt("ProductID"));

                // Set the film's title from the "title" column.
                product.setName(rs.getString("ProductName"));

                // Set the film's rental rate from the "rental_rate" column.
                product.setCategoryId(rs.getInt("CategoryID"));

                product.setPrice(rs.getDouble("UnitPrice"));

                // Add the Film object to our list.
                products.add(product);
            }

        } catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

        // Return the list of Film objects.
        return products;

    }


}

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
        // We are inserting the product name, category id , and unit price.
        String sql = "INSERT INTO products ( ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)";

        // This is a "try-with-resources" block.
        // It ensures that the Connection and PreparedStatement are closed automatically after we are done.
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the second parameter (?) to the product's rental rate.
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

                // Set the product's ID from the "ProductID" column.
                product.setProductId(rs.getInt("ProductID"));

                // Set the product's title from the "title" column.
                product.setName(rs.getString("ProductName"));

                // Set the product's rental rate from the "rental_rate" column.
                product.setCategoryId(rs.getInt("CategoryID"));

                product.setPrice(rs.getDouble("UnitPrice"));

                // Add the product object to our list.
                products.add(product);
            }

        } catch (SQLException e) {
            // If something goes wrong (SQL error), print the stack trace to help debug.
            e.printStackTrace();
        }

        // Return the list of Product objects.
        return products;

    }

    @Override
    public void delete(int productId) {
        //
        String sql = """
                DELETE FROM products
                WHERE ProductID = ?
                """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
        //
        String sql = """
                UPDATE products
                SET ProductName = ?, CategoryID = ?, UnitPrice = ?
                WHERE ProductID = ?
                """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getName());
            statement.setInt(2, product.getCategoryId());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getProductId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> searchByKeyword(String keyword) {

        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT ProductID, ProductName, CategoryID, UnitPrice
                FROM products
                WHERE ProductName LIKE ?
                """;
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Product product = new Product();

                product.setProductId(results.getInt("ProductID"));
                product.setName(results.getString("ProductName"));
                product.setCategoryId(results.getInt("CategoryID"));
                product.setPrice(results.getDouble("UnitPrice"));
                products.add(product);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


}

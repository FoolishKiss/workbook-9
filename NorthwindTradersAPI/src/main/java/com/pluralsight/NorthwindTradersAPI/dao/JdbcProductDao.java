package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Category;
import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao{

    private DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {

        List<Product> products = new ArrayList<>();

        String query = """
                SELECT ProductID, ProductName, CategoryID, UnitPrice
                FROM products
                """;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                Product product = new Product();
                product.setProductId(results.getInt("ProductID"));
                product.setProductName(results.getString("ProductName"));
                product.setCategoryId(results.getInt("CategoryID"));
                product.setUnitPrice(results.getDouble("UnitPrice"));

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public void add(Product product) {

        String query = """
                INSERT INTO products (ProductName, CategoryID, UnitPrice)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getProductName());
            statement.setInt(1, product.getCategoryId());
            statement.setDouble(1, product.getUnitPrice());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(Product product) {

        String query = """
                UPDATE products
                SET ProductName = ?, CategoryID = ?, UnitPrice = ?
                WHERE ProductID = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCategoryId());
            statement.setDouble(3, product.getUnitPrice());
            statement.setInt(4, product.getProductId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int productId) {

        String query = """
                DELETE FROM products
                WHERE ProductID = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, productId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

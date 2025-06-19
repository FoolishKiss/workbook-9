package com.pluralsight.NorthwindTradersAPI.dao;



import com.pluralsight.NorthwindTradersAPI.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCategoryDao implements CategoryDao {

    private DataSource dataSource;

    @Autowired
    public JdbcCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAll() {

        List<Category> categories = new ArrayList<>();

        String query = """
                SELECT CategoryID, CategoryName
                FROM categories
                """;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                Category category = new Category();
                category.setCategoryId(results.getInt("CategoryID"));
                category.setName(results.getString("CategoryName"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public void add(Category category) {

        String query = """
                INSERT INTO categories (CategoryName)
                VALUES (?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, category.getName());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



}

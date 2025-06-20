package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Category;
import com.pluralsight.NorthwindTradersAPI.models.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAll();

    List<Product> getByCategory(int categoryId);

    void add(Product product);

    void update(Product product);

    void delete(int product);

}

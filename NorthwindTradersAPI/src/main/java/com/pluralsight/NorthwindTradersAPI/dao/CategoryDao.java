package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> getAll();

    void add(Category category);

    void update(Category category);

    void delete(int category);


}

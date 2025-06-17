package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.models.Product;

import java.util.List;

public interface ProductDao {

    void add(Product product);

    List<Product> getAll();

    void delete(int productId);

    void update(Product product);

    List<Product> searchByKeyword(String keyword);

}

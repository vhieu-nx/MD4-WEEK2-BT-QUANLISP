package com.codegym.repository;

import com.codegym.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends PagingAndSortingRepository<Product, Long> {
    //searchh produect  for category
    @Query(value = "select * from product where product.category_id = ?", nativeQuery = true)
    List<Product> findProductByCategoryName(Long id);

    //search product for name
    @Query(value = "select  * from product where product.name like ?", nativeQuery = true)
    List<Product> findProductByName(String name);

    //top 5 product for price
    @Query(value = "select * from product order by price desc limit ?", nativeQuery = true)
    List<Product> getProductByPriceExp(int limit);

    //repo ho tro
    List<Product> findTop5ByOrderByPriceDesc();

    //top 5 product for time
    List<Product> findTop5ByOrderByDateDesc();

    //Sum
    @Query(value = "select sum(price) from Product ", nativeQuery = false)
    int getSumPrice();
}

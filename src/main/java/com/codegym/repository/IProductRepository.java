package com.codegym.repository;

import com.codegym.model.Category;
import com.codegym.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends PagingAndSortingRepository<Product, Long> {
    //tim kiem san pham theo category
    @Query(value = "select * from product where product.category_id = ?", nativeQuery = true)
    List<Product> findProductByCategoryName(Long id);

    //tim kiem san pham theo ten
    @Query(value = "select  * from product where product.name like ?", nativeQuery = true)
    List<Product> findProductByName(String name);

    //top 5 san pham theo gia
    @Query(value = "select * from product order by price desc limit ?", nativeQuery = true)
    List<Product> getProductByPriceExp(int limit);

    //repo ho tro
    List<Product> findTop5ByOrderByPriceDesc();

    //top 5 san pham theo thoi gian
    List<Product> findTop5ByOrderByDateDesc();

    //tong tien
    @Query(value = "select sum(price) from Product ", nativeQuery = false)
    int getSumPrice();
}

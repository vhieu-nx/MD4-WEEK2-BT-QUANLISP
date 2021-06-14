package com.codegym.service.product;
import com.codegym.model.Product;
import com.codegym.service.IGeneralService;

import java.util.List;

public interface IProductService extends IGeneralService<Product> {
    List<Product> findByProductName(String name);
    List<Product> findByCategoryName(Long id);
    List<Product> findTop5();
    List<Product> findTop5ByDate();
    int getSumPrice();
    }

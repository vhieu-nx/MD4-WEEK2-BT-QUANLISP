package com.codegym.controller;

import com.codegym.exception.NotFoundException;
import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.service.category.ICategoryService;
import com.codegym.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("listCategory")
    public Iterable<Category> categories() {
        return categoryService.findALl();
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("error-404");
    }

    //show list
    @GetMapping("")
    public ModelAndView showList(@PageableDefault(size = 3) Pageable pageable) {
        Page<Product> products = productService.findALl(pageable);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("productList", products);
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    //create
    @GetMapping("/create")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }
    //top 5 product by price
    @GetMapping("/top5")
    public ModelAndView showTop5() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("productList", productService.findTop5());
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }
    //top 5 product by date
    @GetMapping("top5bydate")
    public ModelAndView showTop5ByDate(){
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("productList", productService.findTop5ByDate());
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }
    //tong tien
    @GetMapping("sum")
    public ModelAndView getSumPrice(){
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("sum", productService.getSumPrice());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("create", "product", new Product());
        return modelAndView;
    }

    //edit
    @GetMapping("/edit")
    public ModelAndView showFormEdit(@RequestParam Long id) throws NotFoundException{
        ModelAndView modelAndView = new ModelAndView("edit");
        Product product = productService.findById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView edit(@RequestParam Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.save(product);
        return new ModelAndView("redirect:/products");
    }

    //delete
    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam Long id) {
        productService.deleteById(id);
        return new ModelAndView("redirect:/products");
    }

    //search product
    @PostMapping("/search")
    public ModelAndView searchProductByName(@RequestParam String name) {
        name = "%" + name + "%";
        List<Product> products = productService.findByProductName(name);
        if (products.size() == 0) return new ModelAndView("error-404");
        else return new ModelAndView("home", "productList", products);
    }

    //search category
    @PostMapping("/searchCate")
    public ModelAndView searchProductByCategory(@RequestParam Long id) {
        List<Product> products = productService.findByCategoryName(id);
        if (products.size() == 0) return new ModelAndView("error-404");
        else return new ModelAndView("home", "productList", products);
    }
}

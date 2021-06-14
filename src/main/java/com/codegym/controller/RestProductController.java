package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.service.category.ICategoryService;
import com.codegym.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class RestProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("listCategory")
    public Iterable<Category> categories() {
        return categoryService.findALl();
    }

    //show list
    @GetMapping()
    public ResponseEntity<Iterable<Product>> showList(){
        return new ResponseEntity<>(productService.findALl(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ModelAndView getList(){
        ModelAndView modelAndView = new ModelAndView("ajax");
        modelAndView.addObject("list", productService.findALl());
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    //create
    @PostMapping("/")
    public ResponseEntity<Product> createNewProduct (@RequestBody Product product){
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @RequestBody Product product){
        product.setId(id);
        productService.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delProduct(@PathVariable Long id){
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

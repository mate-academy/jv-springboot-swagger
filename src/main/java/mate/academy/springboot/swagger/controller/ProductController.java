package mate.academy.springboot.swagger.controller;
/*
* create a new Product
get Product by ID
delete Product by ID
update Product
get all products with
* pagination and ability to sort
*  by price or by title in ASC or DESC order
get all products where price is between two
* values received as a RequestParam inputs.
* Add pagination and ability to sort by price or by title in ASC or DESC order.
* */

import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }
}

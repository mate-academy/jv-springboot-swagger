package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    private final ProductService productService;

    public InjectController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ApiOperation(value = "Inject 9 products to db for tests")
    String inject() {
        Product productOne = new Product("Product-One", BigDecimal.valueOf(100));
        productService.create(productOne);
        Product productTwo = new Product("Product-Two", BigDecimal.valueOf(70));
        productService.create(productTwo);
        Product productThree = new Product("Product-Three", BigDecimal.valueOf(300));
        productService.create(productThree);
        Product productFour = new Product("Product-Four", BigDecimal.valueOf(230));
        productService.create(productFour);
        Product productFive = new Product("Product-Five", BigDecimal.valueOf(350));
        productService.create(productFive);
        Product productSix = new Product("Product-Six", BigDecimal.valueOf(180));
        productService.create(productSix);
        Product productSeven = new Product("Product-Seven", BigDecimal.valueOf(70));
        productService.create(productSeven);
        Product productEight = new Product("Product-Eight", BigDecimal.valueOf(150));
        productService.create(productEight);
        Product productNine = new Product("Product-Nine", BigDecimal.valueOf(230));
        productService.create(productNine);
        return "Completed!";
    }
}

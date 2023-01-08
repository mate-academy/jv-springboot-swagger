package mate.academy.springboot.swagger.controller;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

@RestController
@RequestMapping("/init")
public class BootstrapController {
    private final ProductService productService;

    public BootstrapController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public void init() {
        Product product1 = new Product();
        product1.setTitle("bread");
        product1.setPrice(BigDecimal.valueOf(30));
        productService.save(product1);

        Product product2 = new Product();
        product2.setTitle("milk");
        product2.setPrice(BigDecimal.valueOf(40));
        productService.save(product2);

        Product product3 = new Product();
        product3.setTitle("butter");
        product3.setPrice(BigDecimal.valueOf(50));
        productService.save(product3);

        Product product4 = new Product();
        product4.setTitle("oil");
        product4.setPrice(BigDecimal.valueOf(35));
        productService.save(product4);

        Product product5 = new Product();
        product5.setTitle("sugar");
        product5.setPrice(BigDecimal.valueOf(42));
        productService.save(product5);

        Product product6 = new Product();
        product6.setTitle("tea");
        product6.setPrice(BigDecimal.valueOf(47));
        productService.save(product6);

        Product product7 = new Product();
        product7.setTitle("coffee");
        product7.setPrice(BigDecimal.valueOf(65));
        productService.save(product7);

    }
}

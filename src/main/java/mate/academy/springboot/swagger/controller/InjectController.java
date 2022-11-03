package mate.academy.springboot.swagger.controller;

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
    public String inject() {
        Product product = new Product();
        product.setTitle("Iphone 7");
        product.setPrice(BigDecimal.valueOf(250));
        productService.save(product);

        Product product1 = new Product();
        product1.setTitle("Iphone 8");
        product1.setPrice(BigDecimal.valueOf(300));
        productService.save(product1);

        Product product2 = new Product();
        product2.setTitle("Iphone x");
        product2.setPrice(BigDecimal.valueOf(400));
        productService.save(product2);

        Product product3 = new Product();
        product3.setTitle("Iphone 11");
        product3.setPrice(BigDecimal.valueOf(600));
        productService.save(product3);

        return "Done!";
    }
}

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
        Product product1 = new Product();
        product1.setTitle("product1");
        product1.setPrice(BigDecimal.valueOf(100));
        productService.save(product1);

        Product product2 = new Product();
        product2.setTitle("product2");
        product2.setPrice(BigDecimal.valueOf(200));
        productService.save(product2);

        Product product3 = new Product();
        product3.setTitle("product3");
        product3.setPrice(BigDecimal.valueOf(300));
        productService.save(product3);

        Product product4 = new Product();
        product4.setTitle("product4");
        product4.setPrice(BigDecimal.valueOf(400));
        productService.save(product4);

        Product product5 = new Product();
        product5.setTitle("product5");
        product5.setPrice(BigDecimal.valueOf(500));
        productService.save(product5);

        return "Done!";
    }
}

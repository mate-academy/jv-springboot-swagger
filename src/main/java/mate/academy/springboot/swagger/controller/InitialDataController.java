package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.stream.IntStream;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitialDataController {
    private final ProductService productService;

    @Autowired
    public InitialDataController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/inject")
    public String injectInitialData() {
        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    Product product = new Product();
                    product.setPrice(BigDecimal.valueOf(75L * i));
                    product.setTitle("Product #" + i);
                    productService.create(product);
                });
        return "All data are injected!";
    }
}

package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Injector {
    private final ProductService productService;

    public Injector(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/inject")
    public void saveProducts() {
        Product iphone = new Product();
        iphone.setTitle("iphone 14");
        iphone.setPrice(BigDecimal.valueOf(1000));
        productService.save(iphone);

        Product ps4 = new Product();
        ps4.setTitle("Sony Play Station 4");
        ps4.setPrice(BigDecimal.valueOf(500));
        productService.save(ps4);

        Product bmw = new Product();
        bmw.setTitle("BMW i3");
        bmw.setPrice(BigDecimal.valueOf(50000));
        productService.save(bmw);

        int name = 0;
        int price = 100;
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setTitle(String.valueOf(name++));
            product.setPrice(BigDecimal.valueOf(price++));
            productService.save(product);
        }
    }
}

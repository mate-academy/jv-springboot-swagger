package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.Random;
import javax.annotation.PostConstruct;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class DataInitialized {
    private final ProductService productService;

    public DataInitialized(ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    public void inject() {
        String[] names = new String[]{"Apple IPhone", "Samsung Galaxy", "Google Pixel",
                "Huawei Mate 50", "Xiaomi 13 ULTRA"};
        Random random = new Random();
        for (int i = 0; i <= 50; i++) {
            Product product = new Product();
            product.setTitle(names[random.nextInt(names.length)]);
            product.setPrice(BigDecimal.valueOf(random.nextInt(1500)));
            productService.save(product);
        }
    }
}

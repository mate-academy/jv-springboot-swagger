package mate.academy.springboot.swagger;

import java.math.BigDecimal;
import java.util.Random;
import javax.annotation.PostConstruct;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final ProductService productService;

    public DataInitializer(ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        Product product = new Product();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                product.setTitle("Items#" + i);
            } else {
                product.setTitle("Thing#" + i);
            }
            product.setPrice(BigDecimal.valueOf(random.nextInt(100)));
            product.setId((long) (i + 1));
            productService.create(product);
        }
    }
}

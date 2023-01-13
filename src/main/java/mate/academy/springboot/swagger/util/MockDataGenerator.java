package mate.academy.springboot.swagger.util;

import java.math.BigDecimal;
import java.util.Random;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class MockDataGenerator {
    private static final int MIN_PRICE = 200;
    private static final int RANGE = 1800;
    private static final int QUANTITY = 100;
    private final ProductService productService;

    public MockDataGenerator(ProductService productService) {
        this.productService = productService;
    }

    public void injectRandomData() {
        int counter = 0;
        while (counter <= QUANTITY) {
            Random random = new Random();
            Product product = new Product();
            product.setTitle(Title.values()[random.nextInt(Title.values().length)].name());
            product.setPrice(BigDecimal.valueOf((int) (Math.random() * RANGE + MIN_PRICE)));
            productService.save(product);
            counter++;
        }
    }

    private enum Title {
        IPHONE,
        PIXEL,
        SAMSUNG,
        XIAOMI,
        REALME
    }
}

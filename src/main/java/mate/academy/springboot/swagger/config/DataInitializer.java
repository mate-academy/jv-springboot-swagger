package mate.academy.springboot.swagger.config;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final ProductService productService;

    @PostConstruct
    public void init() {
        Random random = new Random();
        List<String> products = List.of("Milk", "Bread", "Eggs", "Cheese",
                "Oil", "Apples", "Bananas", "Meat", "Fish", "Sugar", "Salt", "Cookies",
                "Coffee", "Tea", "Pasta", "Rice", "Soup", "Juice", "Potatoes", "Tomatoes");
        for (int i = 0; i < 50; i++) {
            Product product = new Product();
            product.setTitle(products.get(random.nextInt(products.size())));
            product.setPrice(BigDecimal.valueOf((random.nextInt(50) + 1) * 10));
            productService.create(product);
        }
    }
}

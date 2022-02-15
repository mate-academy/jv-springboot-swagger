package mate.academy.springboot.swagger.controller;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InjectDataController {
    private final ProductService productService;
    private final List<String> NAMES = List.of("Iphone 11", "Iphone 12", "Iphone 13", "Zoppo z9",
            "Samsung S10", "Samsung A20", "Samsung S70", "Samsung A50", "Redmi Note 11", "Redmi 12");

    @PostConstruct
    private void injectData() {
        Random random = new Random();
        for (int i = 1; i < 51; i++) {
            String name = NAMES.get(random.nextInt(NAMES.size() - 1));
            int max = 1399;
            int min = 229;
            BigDecimal price = BigDecimal.valueOf(random.nextInt(max - min) + min);
            productService.save(new Product(name, price));
        }
    }
}

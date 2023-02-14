package mate.academy.springboot.swagger.util.injector;

import java.math.BigDecimal;
import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductDataInjector {
    private static final int COUNT = 150;
    private static final int MAX_PRICE = 500;
    private final ProductService productService;

    @PostConstruct
    public void init() {
        for (int i = 1; i <= COUNT; i++) {
            productService.save(Product.builder()
                    .title("Product#" + i)
                    .price(BigDecimal.valueOf(new Random().nextInt(MAX_PRICE) + 1))
                    .build());
        }
    }
}

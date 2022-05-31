package mate.academy.springboot.swagger.init;

import java.math.BigDecimal;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInit implements ApplicationRunner {
    private final ProductService productService;

    @Override
    public void run(ApplicationArguments args) {
        saveAll();
    }

    private void saveAll() {
        IntStream.range(1, 200)
                .mapToObj(e -> new Product("Phone № " + e,
                        BigDecimal.valueOf(e + 1000)))
                .forEach(productService::save);
    }
}

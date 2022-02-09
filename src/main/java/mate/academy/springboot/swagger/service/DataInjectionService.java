package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class DataInjectionService {
    private final ProductRepository productRepository;

    public DataInjectionService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void injection() {
        Random random = new Random();
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Product product = new Product();
            product.setTitle("iPhone" + random.nextInt(100));
            product.setPrice(new BigDecimal(random.nextInt(1000)));
            list.add(product);
        }
        productRepository.saveAll(list);
    }
}

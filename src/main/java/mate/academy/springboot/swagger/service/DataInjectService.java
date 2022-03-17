package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DataInjectService {
    private final ProductRepository productRepository;

    public DataInjectService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void inject() {
        Random random = new Random();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Product product = new Product();
            product.setTitle("Iphone" + (7 + random.nextInt(7)));
            product.setPrice(BigDecimal.valueOf(600 + random.nextInt(800)));
            products.add(product);
        }
        productRepository.saveAll(products);
    }
}

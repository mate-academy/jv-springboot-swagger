package mate.academy.springboot.swagger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SwaggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }

    // for testing purposes
    @Bean
    public CommandLineRunner commandLineRunner(ProductService productService) {
        return args -> {
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
        };
    }
}

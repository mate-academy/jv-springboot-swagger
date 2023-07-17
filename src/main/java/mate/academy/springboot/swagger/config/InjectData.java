package mate.academy.springboot.swagger.config;

import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class InjectData {
    private final ProductService productService;

    public InjectData(ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    public String injectData() {
        Product nissan = new Product();
        nissan.setTitle("Nissan");
        nissan.setPrice(BigDecimal.valueOf(1000));
        productService.save(nissan);
        Product mazda = new Product();
        mazda.setTitle("Mazda");
        mazda.setPrice(BigDecimal.valueOf(5000));
        productService.save(mazda);
        Product opel = new Product();
        opel.setTitle("Opel");
        opel.setPrice(BigDecimal.valueOf(4000));
        productService.save(opel);
        return "Done";
    }
}

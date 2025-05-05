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
    public String injectProduct() {
        Product iphone1 = new Product();
        iphone1.setTitle("iPhone 1");
        iphone1.setPrice(BigDecimal.valueOf(100));
        productService.save(iphone1);

        Product iphone2 = new Product();
        iphone2.setTitle("iPhone 2");
        iphone2.setPrice(BigDecimal.valueOf(200));
        productService.save(iphone2);

        Product iphone3 = new Product();
        iphone3.setTitle("iPhone 3");
        iphone3.setPrice(BigDecimal.valueOf(300));
        productService.save(iphone3);

        Product iphone4 = new Product();
        iphone4.setTitle("iPhone 4");
        iphone4.setPrice(BigDecimal.valueOf(400));
        productService.save(iphone4);

        return "Done";
    }
}

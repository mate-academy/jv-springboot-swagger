package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InjectController {
    private final ProductService productService;

    public InjectController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/inject")
    public String inject() {
        Product product1 = new Product();
        product1.setPrice(BigDecimal.valueOf(50));
        product1.setTitle("nokia");
        productService.create(product1);

        Product product2 = new Product();
        product2.setTitle("samsung");
        product2.setPrice(BigDecimal.valueOf(100));
        productService.create(product2);

        Product iphone8 = new Product();
        iphone8.setTitle("iphone8");
        iphone8.setPrice(BigDecimal.valueOf(150));
        productService.create(iphone8);

        Product iphone10 = new Product();
        iphone10.setTitle("iphone10");
        iphone10.setPrice(BigDecimal.valueOf(200));
        productService.create(iphone10);

        Product iphone11 = new Product();
        iphone11.setTitle("iphone11");
        iphone11.setPrice(BigDecimal.valueOf(250));
        productService.create(iphone11);

        Product samsung = new Product();
        samsung.setTitle("samsungA50");
        samsung.setPrice(BigDecimal.valueOf(75));
        productService.create(samsung);

        return "Done";
    }
}

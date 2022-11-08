package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InjectorController {
    private final ProductService productService;

    public InjectorController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/inject")
    public void inject() {
        Product phone = new Product();
        phone.setTitle("Xiaomi Note 5");
        phone.setPrice(BigDecimal.valueOf(200));
        productService.save(phone);

        Product laptop = new Product();
        laptop.setTitle("Lenovo 505");
        laptop.setPrice(BigDecimal.valueOf(400));
        productService.save(laptop);
    }
}

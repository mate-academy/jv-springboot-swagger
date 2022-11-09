package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
@RequiredArgsConstructor
public class InjectController {
    private final ProductService productService;

    @GetMapping
    public String inject() {
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setPrice(BigDecimal.valueOf(i));
            product.setTitle(i + "title");
            productService.crete(product);
        }
        return "created products";
    }
}

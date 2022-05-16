package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
@RequiredArgsConstructor
public class InjectController {
    private final ProductService productService;

    @GetMapping
    @ApiOperation(value = "Add fake Products to H2 DB for test purposes")
    public String injectProducts(@RequestParam int count) {
        for (int i = 1; i <= count; i++) {
            Product product = new Product();
            product.setTitle("Product #" + i);
            product.setPrice(BigDecimal.valueOf(Math.random() * 1000 + 1));
            productService.save(product);
        }
        return count + " products have been injected";
    }
}

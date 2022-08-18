package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    private final ProductService productService;

    @Autowired
    public InjectController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ApiOperation(value = "Inject test data in database")
    public String injectData() {
        productService.create(getProduct("Asus Expert Book", BigDecimal.valueOf(1755)));
        productService.create(getProduct("Mac Pro M1", BigDecimal.valueOf(1245)));
        productService.create(getProduct("iPhone 13Pro", BigDecimal.valueOf(1499)));
        productService.create(getProduct("iPhone 11", BigDecimal.valueOf(755)));
        productService.create(getProduct("iPhone 12Max", BigDecimal.valueOf(895)));

        String time = ", end: " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm:ss"));
        return "Successful inject is done" + time;
    }

    private Product getProduct(String title, BigDecimal price) {
        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        return product;
    }
}

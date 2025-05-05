package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.Random;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class DataController {
    private final ProductService productService;
    private Random random = new Random(600);

    public DataController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ApiOperation(value = "put test data to In-Memory database H2")
    public String inject() {

        Product product1 = new Product();
        product1.setTitle("Samsung");
        product1.setPrice(BigDecimal.valueOf(random.nextInt(200)));
        productService.saveOrUpdate(product1);
        Product product11 = new Product();
        product11.setTitle("Samsung");
        product11.setPrice(BigDecimal.valueOf(random.nextInt(200)));
        productService.saveOrUpdate(product11);
        Product product12 = new Product();
        product12.setTitle("Samsung");
        product12.setPrice(BigDecimal.valueOf(random.nextInt(200)));
        productService.saveOrUpdate(product12);
        Product product13 = new Product();
        product13.setTitle("Samsung");
        product13.setPrice(BigDecimal.valueOf(random.nextInt(200)));
        productService.saveOrUpdate(product13);
        Product product14 = new Product();
        product14.setTitle("Samsung");
        product14.setPrice(BigDecimal.valueOf(random.nextInt(200)));
        productService.saveOrUpdate(product14);
        Product product2 = new Product();
        product2.setTitle("Apple");
        product2.setPrice(BigDecimal.valueOf(1000L));
        productService.saveOrUpdate(product2);
        Product product3 = new Product();
        product3.setTitle("Tesla");
        product3.setPrice(BigDecimal.valueOf(34000L));
        productService.saveOrUpdate(product3);
        Product product4 = new Product();
        product4.setTitle("Mustang");
        product4.setPrice(BigDecimal.valueOf(21000L));
        productService.saveOrUpdate(product4);

        return "Test data was injected!";
    }
}

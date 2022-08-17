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

        Product asus = new Product();
        asus.setTitle("Asus Expert Book");
        asus.setPrice(BigDecimal.valueOf(1755));
        productService.create(asus);

        Product mac = new Product();
        mac.setTitle("Mac Pro M1");
        mac.setPrice(BigDecimal.valueOf(1755));
        productService.create(mac);

        Product iphone13 = new Product();
        iphone13.setTitle("iPhone 13Pro");
        iphone13.setPrice(BigDecimal.valueOf(1499));
        productService.create(iphone13);

        Product iphone11 = new Product();
        iphone11.setTitle("iPhone 11");
        iphone11.setPrice(BigDecimal.valueOf(759));
        productService.create(iphone11);

        Product iphone12Max = new Product();
        iphone12Max.setTitle("iPhone 12Max");
        iphone12Max.setPrice(BigDecimal.valueOf(1250));
        productService.create(iphone12Max);

        String time = ", end: " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm:ss"));
        return "Success inject is done" + time;
    }
}

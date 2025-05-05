package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    private final ProductService productService;

    public InjectController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String inject() {
        Product firstProduct = new Product();
        firstProduct.setTitle("Iphone 7");
        firstProduct.setPrice(BigDecimal.valueOf(250));
        productService.save(firstProduct);

        Product secondProduct = new Product();
        secondProduct.setTitle("Iphone 8");
        secondProduct.setPrice(BigDecimal.valueOf(300));
        productService.save(secondProduct);

        Product thirdProduct = new Product();
        thirdProduct.setTitle("Iphone x");
        thirdProduct.setPrice(BigDecimal.valueOf(400));
        productService.save(thirdProduct);

        Product fourthProduct = new Product();
        fourthProduct.setTitle("Iphone 11");
        fourthProduct.setPrice(BigDecimal.valueOf(600));
        productService.save(fourthProduct);

        return "Done!";
    }
}

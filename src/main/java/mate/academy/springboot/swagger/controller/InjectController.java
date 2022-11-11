package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Save predefined list of products")
    public void saveProducts() {
        Product iphone8 = new Product();
        iphone8.setTitle("iPhone 8");
        iphone8.setPrice(BigDecimal.valueOf(699));
        productService.save(iphone8);

        Product iphoneX = new Product();
        iphoneX.setTitle("iPhone X");
        iphoneX.setPrice(BigDecimal.valueOf(1099));
        productService.save(iphoneX);

        Product iphone11 = new Product();
        iphone11.setTitle("iPhone 11");
        iphone11.setPrice(BigDecimal.valueOf(1199));
        productService.save(iphone11);

        Product iphone12 = new Product();
        iphone12.setTitle("iPhone 12");
        iphone12.setPrice(BigDecimal.valueOf(1399));
        productService.save(iphone12);

        Product tvSamsung = new Product();
        tvSamsung.setTitle("Samsung TV 32inch");
        tvSamsung.setPrice(BigDecimal.valueOf(499));
        productService.save(tvSamsung);

        Product tvSony = new Product();
        tvSony.setTitle("Sony TV 56inch");
        tvSony.setPrice(BigDecimal.valueOf(699));
        productService.save(tvSony);

        Product nbDell = new Product();
        nbDell.setTitle("Dell inspire");
        nbDell.setPrice(BigDecimal.valueOf(999));
        productService.save(nbDell);

        Product nbMac = new Product();
        nbMac.setTitle("Mac M1 13inc");
        nbMac.setPrice(BigDecimal.valueOf(1499));
        productService.save(nbMac);
    }

}

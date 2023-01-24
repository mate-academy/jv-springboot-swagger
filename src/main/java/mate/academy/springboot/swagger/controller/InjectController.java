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
        Product milk = new Product();
        milk.setTitle("Milk");
        milk.setPrice(BigDecimal.valueOf(35));
        productService.add(milk);

        Product cheese = new Product();
        cheese.setTitle("Cheese");
        cheese.setPrice(BigDecimal.valueOf(420));
        productService.add(cheese);

        Product yoghurt = new Product();
        yoghurt.setTitle("Yoghurt");
        yoghurt.setPrice(BigDecimal.valueOf(46));
        productService.add(yoghurt);

        Product cola = new Product();
        cola.setTitle("Coca-Cola");
        cola.setPrice(BigDecimal.valueOf(30));
        productService.add(cola);

        Product sprite = new Product();
        sprite.setTitle("Sprite");
        sprite.setPrice(BigDecimal.valueOf(30));
        productService.add(sprite);

        Product fanta = new Product();
        fanta.setTitle("Fanta");
        fanta.setPrice(BigDecimal.valueOf(30));
        productService.add(fanta);

        Product nuts = new Product();
        nuts.setTitle("Nuts");
        nuts.setPrice(BigDecimal.valueOf(24));
        productService.add(nuts);

        Product bounty = new Product();
        bounty.setTitle("Bounty");
        bounty.setPrice(BigDecimal.valueOf(25));
        productService.add(bounty);

        Product kitkat = new Product();
        kitkat.setTitle("KitKat");
        kitkat.setPrice(BigDecimal.valueOf(18));
        productService.add(kitkat);

        return "putin huilo!";
    }
}

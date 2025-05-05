package mate.academy.springboot.swagger.controller;

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
        Product apple = new Product();
        apple.setTitle("Apple");
        apple.setPrice(12L);
        productService.save(apple);

        Product orange = new Product();
        orange.setTitle("Orange");
        orange.setPrice(18L);
        productService.save(orange);

        Product pear = new Product();
        pear.setTitle("Pear");
        pear.setPrice(10L);
        productService.save(pear);

        Product pineapple = new Product();
        pineapple.setTitle("Snickers");
        pineapple.setPrice(21L);
        productService.save(pineapple);

        Product cherry = new Product();
        cherry.setTitle("Cherry");
        cherry.setPrice(13L);
        productService.save(cherry);

        Product potato = new Product();
        potato.setTitle("Potato");
        potato.setPrice(7L);
        productService.save(potato);

        Product tomato = new Product();
        tomato.setTitle("Tomato");
        tomato.setPrice(13L);
        productService.save(tomato);

        Product cucumber = new Product();
        cucumber.setTitle("Cucumber");
        cucumber.setPrice(8L);
        productService.save(cucumber);

        Product carrot = new Product();
        carrot.setTitle("Carrot");
        carrot.setPrice(8L);
        productService.save(carrot);

        Product onion = new Product();
        onion.setTitle("Onion");
        onion.setPrice(5L);
        productService.save(onion);

        Product garlic = new Product();
        garlic.setTitle("Garlic");
        garlic.setPrice(4L);
        productService.save(garlic);

        Product plump = new Product();
        plump.setTitle("Plump");
        plump.setPrice(16L);
        productService.save(plump);

        return "Done!";
    }
}


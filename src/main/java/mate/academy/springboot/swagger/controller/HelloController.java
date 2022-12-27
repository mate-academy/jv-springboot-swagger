package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @ApiOperation(value = "to see Hello)))")
    public String sayHello() {
        return "Hello, mates!";
    }
}

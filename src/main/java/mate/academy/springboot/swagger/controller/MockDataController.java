package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import mate.academy.springboot.swagger.util.MockDataGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class MockDataController {
    private final MockDataGenerator mockDataGenerator;

    public MockDataController(MockDataGenerator mockDataGenerator) {
        this.mockDataGenerator = mockDataGenerator;
    }

    @GetMapping
    @ApiOperation(value = "inject 100 mobile phone with random title and price")
    public void inject() {
        mockDataGenerator.injectRandomData();
    }
}

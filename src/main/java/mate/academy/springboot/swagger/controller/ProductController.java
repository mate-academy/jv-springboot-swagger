package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    @ApiOperation("Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productService.create(productRequestDto);
    }

    @PostMapping("/update")
    @ApiOperation("Update product")
    public void update(ProductResponseDto productResponseDto) {
        productService.update(productResponseDto);
    }

    @GetMapping("/get/{id}")
    @ApiOperation("Get product by Id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("Delete product by Id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    @ApiOperation("Get all product")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "0") Integer page,
                                           @RequestParam (defaultValue = "10") Integer size,
                                           @RequestParam (defaultValue = "ASС") String sortBy) {
        return productService.findAll(page, size, sortBy);
    }

    @GetMapping("/by-price")
    @ApiOperation("Get products between prices")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                     @RequestParam (defaultValue = "0") Integer page,
                                     @RequestParam (defaultValue = "10") Integer size,
                                     @RequestParam (defaultValue = "ASC") String sortBy) {
        return productService.getAllByPriceBetween(from, to, page, size, sortBy);
    }
}

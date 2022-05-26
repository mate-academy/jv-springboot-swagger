package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiOperation;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/inject")
    @ApiOperation(value = "create 100 products")
    public void injectProducts() {
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setPrice(new BigDecimal(100 + i));
            product.setTitle("title: " + i);
            productService.add(product);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @PostMapping
    @ApiOperation(value = "create product")
    public void create(@RequestBody ProductRequestDto dto) {
        productService.add(productMapper.toModel(dto));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product")
    public ProductResponseDto update(@RequestBody ProductRequestDto dto) {
        Product product = productService.add(productMapper.toModel(dto));
        return productMapper.toDto(product);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get product by price")
    public List<ProductResponseDto> getAll(@RequestParam BigDecimal from,
                                           @RequestParam BigDecimal to) {
        List<Product> allByPriceBetween = productService.getAllByPriceBetween(from, to);
        return allByPriceBetween.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20") Integer size,
                                           @RequestParam(defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Product> products = productService.getAll(pageRequest).toList();
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }
}

package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.toDto(productService.create(productMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by 'id'")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by 'id'")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product by 'id'")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.toDto(product);
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "0")
                                          @ApiParam(value = "default value is '0'") Integer page,
                                          @RequestParam(defaultValue = "10")
                                          @ApiParam(value = "default page is '10'") Integer size,
                                          @RequestParam(defaultValue = "id")
                                          @ApiParam(value = "default sort by 'id'") String sortBy) {
        return productService.findAll(page, size, sortBy).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price-between")
    @ApiOperation(value = "Get all products with price between")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam(defaultValue = "0")
                                                          @ApiParam(value = "default value is '0'")
                                                                      Integer page,
                                                          @RequestParam(defaultValue = "10")
                                                          @ApiParam(value = "default page is '10'")
                                                                      Integer size,
                                                          @RequestParam(defaultValue = "id")
                                                          @ApiParam(value = "default sort by 'id'")
                                                                      String sortBy) {
        return productService
                .findAllByPriceBetween(from, to, page, size, sortBy)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
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

    public ProductController(ProductService productService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PutMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper
                .toResponseDto(productService
                .create(productMapper
                .toProduct(productRequestDto)));
    }

    @GetMapping("/{productId}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto get(@PathVariable(name = "product id") Long productId) {
        return productMapper
                .toResponseDto(productService
                        .get(productId));
    }

    @DeleteMapping("/{productId}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable(name = "product id") Long productId) {
        productService.delete(productId);
    }

    @PostMapping
    @ApiOperation(value = "update product")
    public ProductResponseDto update(@RequestBody ProductRequestDto productRequestDto,
                                     @RequestParam(name = "product id") Long productId) {
        return productMapper
                .toResponseDto(productService
                .update(productMapper
                .toProduct(productRequestDto), productId));
    }

    @GetMapping
    @ApiOperation(value = "get products list")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is `20`") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`") Integer page,
            @RequestParam(defaultValue = "id", name = "sort by")
            @ApiParam(value = "default value is `id`") String sortBy) {
        return productService.findAll(count, page, sortBy).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get a list of products between two prises")
    public List<ProductResponseDto> findAllBetween(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is `20`") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`")Integer page,
            @RequestParam(defaultValue = "id", name = "sort by")
            @ApiParam(value = "default value is `id`")String sortBy,
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to) {
        return productService.findAllBetweenPrice(count, page, sortBy, from, to)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

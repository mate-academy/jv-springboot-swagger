package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static final String BEFORE_KEY = "priceBefore";
    public static final String FROM_KEY = "priceFrom";
    private final ProductService productService;
    private final RequestDtoMapper<Product, ProductRequestDto> productRequestMapper;
    private final ResponseDtoMapper<Product, ProductResponseDto> productResponseMapper;

    @Autowired
    public ProductController(ProductService productService,
                             RequestDtoMapper<Product, ProductRequestDto> productRequestMapper,
                             ResponseDtoMapper<Product, ProductResponseDto> productResponseMapper) {
        this.productService = productService;
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
    }

    @ApiOperation(value = "Create a new product")
    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productRequestMapper.toModel(productRequestDto);
        return productResponseMapper.toDto(productService.create(product));
    }

    @ApiOperation(value = "Get product by id")
    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productResponseMapper.toDto(productService.get(id));
    }

    @ApiOperation(value = "Delete product by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @ApiOperation(value = "Update product")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productRequestMapper.toModel(productRequestDto);
        product.setId(id);
        return productResponseMapper.toDto(productService.update(product));
    }

    @ApiOperation(value = "Get product by price filter and with pagination & sort")
    @GetMapping("/filter")
    public List<ProductResponseDto> getAllWithFilter(
            @RequestParam (defaultValue = "999999") String priceBefore,
            @ApiParam(value = "default value is 999999") String sortBy,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is 0") String priceFrom,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is 0") Integer page,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is 20") Integer size) {
        return productService
                .getAll(Map.of(BEFORE_KEY, priceBefore, FROM_KEY, priceFrom), page, size).stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get product with pagination & sort")
    @GetMapping
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is id") String sortBy,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is 20") Integer page,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is 20") Integer size) {
        return productService.getAll(page, size, sortBy).stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}

package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> mapper;

    public ProductController(ProductService productService,
                             DtoMapper<Product, ProductRequestDto, ProductResponseDto> mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto add(@RequestBody @ApiParam(value = "Products details")
                                      ProductRequestDto productRequestDto) {
        return mapper.toDto(productService.create(mapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find a product by id")
    public ProductResponseDto getById(@PathVariable @ApiParam(value = "Id of a product") Long id) {
        return mapper.toDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product by id")
    public ProductResponseDto update(@PathVariable @ApiParam(value = "Id of a product") Long id,
                                     @RequestBody @ApiParam(value = "Products details")
                                     ProductRequestDto requestDto) {
        Product product = mapper.toModel(requestDto);
        product.setId(id);
        return mapper.toDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by id")
    public void delete(@PathVariable @ApiParam(value = "Id of a product") Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Get a list of all products")
    public List<ProductResponseDto> findAll(
            @ApiParam(value = "The page number(default value is 0)")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "The number of products per page(default is 10")
            @RequestParam(defaultValue = "10") Integer count,
            @ApiParam(value = "The field to sort by(default is id)")
            @RequestParam(defaultValue = "id") String sortBy) {
        return productService.findAll(page, count, sortBy).stream().map(mapper::toDto).toList();
    }

    @ApiOperation(value = "Get a list of all products where price is between two values")
    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetween(
            @ApiParam(value = "The minimum price (inclusive)")
            @RequestParam BigDecimal from,
            @ApiParam(value = "The maximum price (inclusive)")
            @RequestParam BigDecimal to,
            @ApiParam(value = "The page number(default value is 0)")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "The number of products per page(default is 10")
            @RequestParam(defaultValue = "10") Integer count,
            @ApiParam(value = "The field to sort by(default is id)")
            @RequestParam(defaultValue = "id") String sortBy) {
        return productService.findAllByPriceBetween(from, to, page, count, sortBy)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}

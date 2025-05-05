package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortParser;
import org.springframework.data.domain.PageRequest;
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
    private final SortParser parser;

    public ProductController(ProductService productService, ProductMapper productMapper,
                             SortParser parser) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.parser = parser;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return productMapper.toDto(productService.save(productMapper.toModel(dto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete a product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update a product")
    public ProductResponseDto update(@RequestBody ProductRequestDto dto, @PathVariable Long id) {
        Product product = productMapper.toModel(dto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "get a list of products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "3")
                                                @ApiParam(value = "default is 3") Integer count,
                                            @RequestParam(defaultValue = "0")
                                                @ApiParam(value = "default is 0")Integer page,
                                            @RequestParam(defaultValue = "id")
                                                @ApiParam(value = "default is id")String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, parser.parse(sortBy));
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price-between")
    @ApiOperation(value = "get a list of products with price between input values")
    public List<ProductResponseDto> findAllByPriceBetween(
                                          @RequestParam BigDecimal from,
                                          @RequestParam BigDecimal to,
                                          @RequestParam(defaultValue = "3")
                                            @ApiParam(value = "default is 3") Integer count,
                                          @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "default is 0") Integer page,
                                          @RequestParam(defaultValue = "id")
                                            @ApiParam(value = "default is id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, parser.parse(sortBy));
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}


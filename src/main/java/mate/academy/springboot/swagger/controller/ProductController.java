package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.SortParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final SortParser sortParser;

    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             SortParser sortParser) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortParser = sortParser;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        Product product = productService.create(productMapper.toModel(dto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.get(id);
        return productMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
        Product product = productMapper.toModel(dto);
        product.setId(id);
        productService.update(product);
        return productMapper.toDto(product);
    }

    @GetMapping
    @ApiOperation(value = "get all products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "10")
                                            @ApiParam(value = "default value is 10")
                                            Integer size,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "default value is 0")
                                            Integer page,
                                            @RequestParam(defaultValue = "id")
                                            @ApiParam(value = "default value is 'id'")
                                            String sortBy) {
        Sort sort = sortParser.sortBy(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get all products with price between")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam(defaultValue = "10")
                                                          @ApiParam(value = "default value is 10")
                                                          Integer size,
                                                          @RequestParam(defaultValue = "0")
                                                          @ApiParam(value = "default value is 0")
                                                          Integer page,
                                                          @RequestParam(defaultValue = "id")
                                                          @ApiParam(value = "default value is 'id'")
                                                          String sortBy,
                                                          @RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to) {
        Sort sort = sortParser.sortBy(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

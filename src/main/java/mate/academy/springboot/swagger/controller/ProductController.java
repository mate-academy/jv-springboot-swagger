package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortService;
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
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final SortService sortService;

    public ProductController(ProductMapper productMapper,
                             ProductService productService,
                             SortService sortService) {
        this.productMapper = productMapper;
        this.productService = productService;
        this.sortService = sortService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        return productMapper.toDto(productService.save(productMapper.fromDto(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by ID")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by ID")
    public String deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return "Successful";
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by ID")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.fromDto(requestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products with pagination")
    public List<ProductResponseDto> findAllWithPagination(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value: 20") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value: 0") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value: ID") String sortBy) {
        Sort sort = Sort.by(sortService.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products between two prices")
    public List<ProductResponseDto> findAllWherePriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value: 20") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value: 0") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value: ID") String sortBy) {
        Sort sort = Sort.by(sortService.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortingOptionsParser;
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
    private ProductService productService;
    private ProductMapper productMapper;
    private SortingOptionsParser sortingOptionsParser;

    public ProductController(ProductService productService, ProductMapper productMapper,
                             SortingOptionsParser sortingOptionsParser) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortingOptionsParser = sortingOptionsParser;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product createdProduct = productService.save(productMapper.toModel(requestDto));
        return productMapper.toResponseDto(createdProduct);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toResponseDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete a product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update a product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "get all products with pagination and sorting")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "20") Integer count,
                                           @RequestParam(defaultValue = "title") String sortBy) {
        Sort sort = sortingOptionsParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "get all products with pagination and sorting by price between 2 values")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam BigDecimal from, @RequestParam BigDecimal to) {
        Sort sort = sortingOptionsParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetweenWithPageable(from, to, pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

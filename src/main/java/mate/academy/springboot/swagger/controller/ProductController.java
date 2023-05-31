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
import mate.academy.springboot.swagger.util.SortingCondition;
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
    private final ProductMapper productMapper;
    private final ProductService productService;

    private final SortingCondition condition;

    public ProductController(ProductMapper productMapper,
                             ProductService productService, SortingCondition condition) {
        this.productMapper = productMapper;
        this.productService = productService;
        this.condition = condition;
    }

    @PostMapping
    @ApiOperation(value = "create and save a new product")
    public Product create(@RequestBody ProductRequestDto requestDto) {
        return productService.save(productMapper.toModel(requestDto));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a product by id")
    public ProductResponseDto getById(@PathVariable
                                      @ApiParam(value = "Insert a product id to get") Long id) {
        return productMapper.toResponseDto(productService.getById(id));

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete a product by id")
    public void deleteById(@PathVariable
                           @ApiParam(value = "insert a product id to delete") Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update a product")
    public ProductResponseDto update(@PathVariable
                                     @ApiParam(value = "insert a product id to update") Long id,
                                     @RequestBody
                                     @ApiParam(value = "new parameters required")
                                     ProductRequestDto requestDto) {
        Product product = productService.getById(id);
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());
        return productMapper.toResponseDto(productService.update(product));
    }

    @GetMapping("/price-between/sort")
    @ApiOperation(value = "get a product in a specific range")
    List<ProductResponseDto> getByPriceBetween(
            @RequestParam(defaultValue = "2")
            @ApiParam(value = "number of products on the page") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "page number") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "a param for sorting") String sortBy,
            @RequestParam
            @ApiParam(value = "insert a price from") BigDecimal from,
            @RequestParam
            @ApiParam(value = "insert a price to") BigDecimal to) {
        PageRequest pageRequest = condition.getSortingCondition(page, count, sortBy);
        return productService.findAllByPriceBetweenWithSort(from, to, pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/sort")
    @ApiOperation(value = "sort a product")
    List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                     @ApiParam(value = "number of products on a page is 20")
                                     Integer count,
                                     @RequestParam(defaultValue = "0")
                                     @ApiParam(value = "page number") Integer page,
                                     @RequestParam(defaultValue = "id")
                                     @ApiParam(value = " insert a param for sorting")
                                     String sortBy) {
        PageRequest pageRequest = condition.getSortingCondition(page, count, sortBy);
        return productService.findAllWithSort(pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

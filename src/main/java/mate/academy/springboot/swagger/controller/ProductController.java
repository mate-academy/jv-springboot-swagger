package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.ProductSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping("/add")
    @ApiOperation(value = "save the new Product to DB")
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper.toModel(productRequestDto));
        return productMapper.toResponseDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "find Product by Id to DB")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productMapper.toResponseDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete Product by ID to DB")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "update Product by ID to DB")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "find all Products to DB, page by page, sorted by title")
    public List<ProductResponseDto> getAll
            (@RequestParam (defaultValue = "5")
             @ApiParam (defaultValue = "value to default is 5") Integer count,
             @RequestParam (defaultValue = "0")
             @ApiParam (defaultValue = "value to default is 0") Integer page,
             @RequestParam (defaultValue = "title")
             @ApiParam (defaultValue = "value to default is title") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return productService.findAll(pageRequest, sortBy).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/findAllByPrice")
    @ApiOperation(value = "find all Products by price between two values, "
            + "page to page, sorted by title")
    public List<ProductResponseDto> getAllByPriceBetween
            (@RequestParam BigDecimal from, @RequestParam BigDecimal to,
             @RequestParam (defaultValue = "5")
             @ApiParam (defaultValue = "value to default is 5") Integer count,
             @RequestParam (defaultValue = "0")
             @ApiParam (defaultValue = "value to default is 0") Integer page,
             @RequestParam (defaultValue = "price")
             @ApiParam (defaultValue = "value to default is price") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return productService.findAllByPriceBetween(from, to, pageRequest, sortBy)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

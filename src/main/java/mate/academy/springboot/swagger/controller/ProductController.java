package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.util.ParserStringToSort;
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

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation("create new product")
    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        productService.create(product);
        return productMapper.toDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation("update product by id")
    public void update(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        productService.create(product);
    }

    @GetMapping("/{id}")
    @ApiOperation("get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation("get all products using pagination and sorting")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                               @ApiParam(value = "defaultValue=20")
                                               Integer count,
                                           @RequestParam(defaultValue = "0")
                                               @ApiParam(value = "defaultValue=0")
                                               Integer page,
                                           @RequestParam(defaultValue = "id")
                                               @ApiParam(value = "defaultValue=id")
                                               String sortBy) {
        Sort sort = ParserStringToSort.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(p -> productMapper.toDto(p))
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation("get all products by price between to parameters using pagination and sorting")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                                         @RequestParam(defaultValue = "20")
                                                             @ApiParam(value = "defaultValue=20")
                                                             Integer count,
                                                         @RequestParam(defaultValue = "0")
                                                             @ApiParam(value = "defaultValue=0")
                                                             Integer page,
                                                         @RequestParam(defaultValue = "id")
                                                             @ApiParam(value = "defaultValue=id")
                                                             String sortBy) {
        Sort sort = ParserStringToSort.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        List<Product> collect = productService.getAllByPriceBetween(from, to, pageRequest);
        return collect.stream()
                .map(p -> productMapper.toDto(p))
                .collect(Collectors.toList());
    }
}

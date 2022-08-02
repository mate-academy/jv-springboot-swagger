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
import mate.academy.springboot.swagger.util.SortUtil;
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
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.toProduct(requestDto));
        return productMapper.toResponseDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by Id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toResponseDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by Id")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by Id")
    public ProductResponseDto updateProduct(@PathVariable Long id,
                                            @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toProduct(requestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.update(product));
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get products list")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = "default sort is by Id") String sortBy) {
        Sort sort = SortUtil.prepareSortParam(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "Get all products where price is between two values")
    public List<ProductResponseDto> getAllProducts(
            @RequestParam(required = false) BigDecimal from,
            @RequestParam(required = false) BigDecimal to,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = "default sort is by Id") String sortBy,
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "default value is 0") Integer page) {
        Sort sort = SortUtil.prepareSortParam(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findProductsByPriceBetween(pageRequest, from, to).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

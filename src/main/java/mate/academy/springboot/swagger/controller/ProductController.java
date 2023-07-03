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
    private final SortUtil sortUtil;

    public ProductController(ProductService productService, ProductMapper productMapper,
                             SortUtil sortUtil) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortUtil = sortUtil;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        productService.save(product);
        return productMapper.toResponseDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.toResponseDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Get all products list")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "5")
                                           @ApiParam(value = "default value is 5")
                                           Integer count,
                                           @RequestParam (defaultValue = "0")
                                           @ApiParam(value = "default value is 0")
                                           Integer page,
                                           @RequestParam (defaultValue = "id")
                                           @ApiParam(value = "default value is id")
                                           String sortBy) {
        Sort sort = sortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.get(id);
        return productMapper.toResponseDto(product);
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get all products by price between values")
    public List<ProductResponseDto> getByPriceBetween(@RequestParam BigDecimal from,
                                                      @RequestParam BigDecimal to,
                                                      @RequestParam (defaultValue = "5")
                                                      @ApiParam(value = "default value is 5")
                                                      Integer count,
                                                      @RequestParam (defaultValue = "0")
                                                      @ApiParam(value = "default value is 0")
                                                      Integer page,
                                                      @RequestParam (defaultValue = "id")
                                                      @ApiParam(value = "default value is id")
                                                      String sortBy) {
        Sort sort = sortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getProductsByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

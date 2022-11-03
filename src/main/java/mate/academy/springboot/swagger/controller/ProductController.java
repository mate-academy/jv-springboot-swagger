package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
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
    @ApiOperation(value = "Create a new product.")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.mapToDto(productService
                .save(productMapper.mapToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id.")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id.")
    public void deleteById(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id.")
    public ProductResponseDto update(@PathVariable Long id, ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products with pagination.")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is '20'") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is 'id'") String sortBy) {
        Sort sort = Sort.by(new SortUtil().sort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products filtered by price with pagination.")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is '20'") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is 'id'") String sortBy) {
        Sort sort = Sort.by(new SortUtil().sort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

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
import mate.academy.springboot.swagger.util.SortOrderUtil;
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
    private final SortOrderUtil sortOrderUtil;

    public ProductController(ProductService productService,
                             ProductMapper productMapper, SortOrderUtil sortOrderUtil) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortOrderUtil = sortOrderUtil;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.add(productMapper.toModel(productRequestDto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product")
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product")
    public ProductResponseDto updateProduct(@PathVariable Long id,
                                            @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get list of products with pagination")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "20") @ApiParam(value = "defaultValue = 20") Integer count,
            @RequestParam(defaultValue = "0") @ApiParam(value = "defaultValue = 0") Integer page,
            @RequestParam(defaultValue = "id") @ApiParam(value = "defaultValue = id") String sortBy
    ) {

        Sort sort = sortOrderUtil.sortBy(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get list of products by price distance")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam @ApiParam(value = "Search from price") BigDecimal from,
            @RequestParam @ApiParam(value = "Search to price") BigDecimal to,
            @RequestParam(defaultValue = "20") @ApiParam(value = "defaultValue = 20") Integer count,
            @RequestParam(defaultValue = "0") @ApiParam(value = "defaultValue = 0") Integer page,
            @RequestParam(defaultValue = "id") @ApiParam(value = "defaultValue = id") String sortBy
    ) {
        Sort sort = sortOrderUtil.sortBy(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

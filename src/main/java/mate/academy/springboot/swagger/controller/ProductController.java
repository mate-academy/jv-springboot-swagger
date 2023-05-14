package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.RequestParamParser;
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
    private static final String DEFAULT_PRODUCTS_ON_PAGE_COUNT = "10";
    private static final String DEFAULT_PAGE_NUMBER = "0";
    private static final String DEFAULT_SORT_BY_FIELD_NAME = "price";
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto dto) {
        Product product = productService.save(productMapper.mapToModel(dto));
        return productMapper.mapToDto(product);
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = DEFAULT_PRODUCTS_ON_PAGE_COUNT)
                @ApiParam(value = "default value is " + DEFAULT_PRODUCTS_ON_PAGE_COUNT)
                Integer count,
            @RequestParam (defaultValue = DEFAULT_PAGE_NUMBER)
                @ApiParam(value = "default value is " + DEFAULT_PAGE_NUMBER)
                Integer page,
            @RequestParam (defaultValue = DEFAULT_SORT_BY_FIELD_NAME)
                @ApiParam(value = "default value is " + DEFAULT_SORT_BY_FIELD_NAME)
                String sortBy) {
        Sort sort = Sort.by(RequestParamParser.toSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by ID")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Find products by price between")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal priceFrom,
            @RequestParam BigDecimal priceTo,
            @RequestParam (defaultValue = DEFAULT_PRODUCTS_ON_PAGE_COUNT)
                @ApiParam(value = "default value is " + DEFAULT_PRODUCTS_ON_PAGE_COUNT)
                Integer count,
            @RequestParam (defaultValue = DEFAULT_PAGE_NUMBER)
                @ApiParam(value = "default value is " + DEFAULT_PAGE_NUMBER)
                Integer page,
            @RequestParam (defaultValue = DEFAULT_SORT_BY_FIELD_NAME)
                @ApiParam(value = "default value is " + DEFAULT_SORT_BY_FIELD_NAME)
                String sortBy) {
        Sort sort = Sort.by(RequestParamParser.toSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(priceFrom, priceTo, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by ID")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by ID")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.update(product));
    }
}

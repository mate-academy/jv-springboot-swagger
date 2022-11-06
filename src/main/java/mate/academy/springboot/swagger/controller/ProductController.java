package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final SortService sortService;

    @Autowired
    public ProductController(
            ProductService productService,
            ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper,
            RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper,
            SortService sortService) {
        this.productService = productService;
        this.responseDtoMapper = responseDtoMapper;
        this.requestDtoMapper = requestDtoMapper;
        this.sortService = sortService;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestProductDto) {
        return responseDtoMapper.toDto(
                productService.save(requestDtoMapper.toModel(requestProductDto)));
    }

    @GetMapping("/{product_id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable(name = "product_id") Long productId) {
        return responseDtoMapper.toDto(productService.getById(productId));
    }

    @DeleteMapping
    @ApiOperation(value = "Delete product by id")
    public void deleteById(@RequestParam(name = "product_id") Long productId) {
        productService.delete(productService.getById(productId));
    }

    @PutMapping
    @ApiOperation(value = "Update info about product")
    public ProductResponseDto update(@RequestParam(name = "product_id") Long productId,
                                     @RequestBody ProductRequestDto requestProductDto) {
        Product product = requestDtoMapper.toModel(requestProductDto);
        product.setId(productId);
        return responseDtoMapper.toDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20") Integer count,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, sortService.getSort(sortBy));
        return productService.findAll(pageRequest).stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get all products with price between two values")
    public List<ProductResponseDto> findAllByPrice(
            @ApiParam(value = "Upper limit") @RequestParam BigDecimal from,
            @ApiParam(value = "Lower limit") @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, count, sortService.getSort(sortBy));
        return productService.findAllByPriceBetween(from, to, pageable).stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}

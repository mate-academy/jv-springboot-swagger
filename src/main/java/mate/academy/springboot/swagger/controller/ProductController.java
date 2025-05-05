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
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.ProductSort;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final ProductSort productSort;

    public ProductController(ProductService productService,
            RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper,
            ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper,
            ProductSort productSort) {
        this.productService = productService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
        this.productSort = productSort;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@RequestParam ProductRequestDto dto) {
        return responseDtoMapper.mapToDto(productService.create(requestDtoMapper.mapToModel(dto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by ID")
    public ProductResponseDto getById(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by ID")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    @ApiOperation(value = "Get all products with paging and sorting")
    public List<ProductResponseDto> findAll(
            @ApiParam(value = "Default value is '3'")
            @RequestParam(defaultValue = "3") Integer count,
            @ApiParam(value = "Default value is '0'")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "Default value is 'price'")
            @RequestParam(defaultValue = "price") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, productSort.productSort(sortBy));
        return productService.findAll(pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products by price with paging and sorting")
    public List<ProductResponseDto> findByPriceSorted(
            @ApiParam(value = "Price from")
            @RequestParam BigDecimal from,
            @ApiParam(value = "Price to")
            @RequestParam BigDecimal to,
            @ApiParam(value = "Default value is '3'")
            @RequestParam(defaultValue = "3") Integer count,
            @ApiParam(value = "Default value is '0'")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "Default value is 'price'")
            @RequestParam(defaultValue = "price") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, productSort.productSort(sortBy));
        return productService.findAllByPrice(from, to, pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

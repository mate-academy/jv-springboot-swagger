package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.dto.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.impl.SortService;
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
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseMapper;
    private final ProductService productService;

    public ProductController(RequestDtoMapper<ProductRequestDto, Product> productRequestMapper,
                             ResponseDtoMapper<ProductResponseDto, Product> productResponseMapper,
                             ProductService productService) {
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
        this.productService = productService;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productRequestMapper.toModel(productRequestDto));
        return productResponseMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productResponseMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productRequestMapper.toModel(productRequestDto);
        product.setId(id);
        Product saved = productService.save(product);
        return productResponseMapper.toDto(saved);
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "15")
                                            @ApiParam(value = "default value is 15") Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "default value is 0") Integer page,
                                            @RequestParam(defaultValue = "title")
                                            @ApiParam(value = "default value is title")
                                            String sortBy) {
        Sort sort = SortService.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products with price between input values")
    public List<ProductResponseDto> findByPrice(@RequestParam BigDecimal from,
                                                @RequestParam BigDecimal to,
                                                @RequestParam(defaultValue = "15")
                                                @ApiParam(value = "default value is 15")
                                                Integer count,
                                                @RequestParam(defaultValue = "0")
                                                @ApiParam(value = "default value is 0")
                                                Integer page,
                                                @RequestParam(defaultValue = "title")
                                                @ApiParam(value = "default value is id")
                                                String sortBy) {
        Sort sort = SortService.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findProductsByPriceBetween(pageRequest, from, to).stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}

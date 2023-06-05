package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import mate.academy.springboot.swagger.util.ParamsParser;
import org.springframework.data.domain.PageRequest;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> productMapper;
    private final ParamsParser paramsParser;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productService.create(productMapper.toModel(requestDto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a product")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update a product information")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid ProductRequestDto requestDto
    ) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete a product")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "get all products with pagination and ability to sort them")
    public List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "0")
                @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam(defaultValue = "20")
                @ApiParam(value = "default value is '20'") Integer count,
            @RequestParam(defaultValue = "title")
                @ApiParam(value = "default sorting is by 'title:asc'") String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, count, paramsParser.getSortingParams(sortBy));
        return productService.getAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get all products by price with pagination and ability to sort them")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "0")
                @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam(defaultValue = "20")
                @ApiParam(value = "default value is '20'") Integer count,
            @RequestParam(defaultValue = "title")
                @ApiParam(value = "default sorting is by 'title:asc'") String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, count, paramsParser.getSortingParams(sortBy));
        return productService.getAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

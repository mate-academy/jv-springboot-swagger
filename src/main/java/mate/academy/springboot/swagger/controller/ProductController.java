package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> mapper;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(ProductRequestDto requestDto) {
        return mapper.toDto(productService.create(mapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return mapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product")
    public ProductResponseDto update(@PathVariable Long id, ProductRequestDto requestDto) {
        return mapper.toDto(productService.update(id, mapper.toModel(requestDto)));
    }

    @GetMapping
    @ApiOperation(value = "get products list")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value 20") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value 0") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value id") String sortBy) {
        Sort sort = Sort.by(SortParser.generateOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get product list by price")
    public List<ProductResponseDto> findAllByPrice(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value 20") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value 0") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value id") String sortBy) {
        Sort sort = Sort.by(SortParser.generateOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPrice(from, to, pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}

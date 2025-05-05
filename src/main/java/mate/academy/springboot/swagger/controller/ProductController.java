package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.RequestParser;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    @ApiOperation(value = "Add new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        Product product = productService.create(productMapper.mapToModel(dto));
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto dto) {
        Product product = productMapper.mapToModel(dto);
        product.setId(id);
        return productMapper.mapToDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Find all product with pagination and sorting")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "5")
            @ApiParam(value = "Limit items on one page") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Number of page") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "Name field to sort") String sortBy) {
        Sort sort = Sort.by(RequestParser.toSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Find all products where price between from and to."
            + " Plus pagination and soring")
    public List<ProductResponseDto> findByPriceBetween(
            @RequestParam
            @ApiParam(value = "Price from") BigDecimal from,
            @RequestParam
            @ApiParam(value = "Price to") BigDecimal to,
            @RequestParam(defaultValue = "5")
            @ApiParam(value = "Limit items on one page") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Number of page") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "Name field to sort") String sortBy) {
        Sort sort = Sort.by(RequestParser.toSortOrders(sortBy));
        Pageable pageable = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageable)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

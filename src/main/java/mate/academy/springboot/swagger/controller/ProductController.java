package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.Sorter;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final Sorter sorter;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "find a product by id")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productMapper.toDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete a product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update a product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "find all products with pagination and sorting")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`") Integer page,
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "default value is `10`") Integer count,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value is `id`") String sortBy) {
        Sort sort = sorter.getSortParams(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "find all products by price between two values"
            + " with pagination and sorting")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`") Integer page,
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "default value is `10`") Integer count,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value is `id`") String sortBy) {
        Sort sort = sorter.getSortParams(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

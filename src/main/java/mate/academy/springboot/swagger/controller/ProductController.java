package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortParser;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortParser sortParser;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.toDto(productService.add(productMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find the product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update the product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        return productMapper.toDto(productService.update(productMapper.toModel(id, requestDto)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete the product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Find all products")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20") @ApiParam(value = "20 by default") Integer size,
            @RequestParam (defaultValue = "0") @ApiParam(value = "0 by default") Integer page,
            @RequestParam (defaultValue = "id") @ApiParam(value = "id by default") String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sortParser.toSort(sortBy));
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Filter products by price between 'from' and 'to'")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "20") @ApiParam(value = "20 by default") Integer size,
            @RequestParam (defaultValue = "0") @ApiParam(value = "0 by default") Integer page,
            @RequestParam (defaultValue = "id") @ApiParam(value = "id by default") String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sortParser.toSort(sortBy));
        return productService
                .findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

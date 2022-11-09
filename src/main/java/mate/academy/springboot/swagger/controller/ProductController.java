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
import mate.academy.springboot.swagger.services.ProductService;
import mate.academy.springboot.swagger.services.mapper.ProductMapper;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    @ApiOperation(value = "create product")
    public ProductResponseDto create(@RequestBody
                                     ProductRequestDto productRequestDto) {
        return productMapper.toDto(
                productService.crete(
                        productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = " delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.crete(product));
    }

    @GetMapping
    @ApiOperation(value = "get all product")
    public List<ProductResponseDto> getAll(@ApiParam(value = "default value is 10")
                                           @RequestParam(defaultValue = "10") Integer count,
                                           @ApiParam(value = "default value is 0")
                                           @RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam String sortBy,
                                           @ApiParam(value = "default value is ASC")
                                           @RequestParam(defaultValue = "ASC") String directional) {
        return productService.getAll(PageRequest.of(page, count, Sort.by(
                        Sort.Direction.valueOf(directional.toUpperCase()), sortBy)))
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "get all product where price between from and to")
    public List<ProductResponseDto> getAllPriceBetween(@RequestParam BigDecimal from,
                                                       @RequestParam BigDecimal to,
                                                       @ApiParam(value = "default value is 10")
                                                       @RequestParam(defaultValue = "10")
                                                       Integer count,
                                                       @ApiParam(value = "default value is 0")
                                                       @RequestParam(defaultValue = "0")
                                                       Integer page,
                                                       @RequestParam String sortBy,
                                                       @ApiParam(defaultValue = "default "
                                                               + "value is ASC")
                                                       @RequestParam(defaultValue = "ASC")
                                                       String directional) {
        return productService.getAllProductWherePriceBetween(
                        from, to, PageRequest.of(page, count, Sort.by(
                                Sort.Direction.valueOf(
                                        directional.toUpperCase()), sortBy)))
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

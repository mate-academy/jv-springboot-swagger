package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortParsing;
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
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<ProductRequestDto, ProductResponseDto, Product> productMapper;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return productMapper.mapToDto(productService.create(productMapper.mapToModel(dto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update operation by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto dto) {
        Product product = productMapper.mapToModel(dto);
        product.setId(id);
        return productMapper.mapToDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get list products. "
            + "You can set count products per page, can set page and can set sort rules")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                               @ApiParam(value = "default value is 20")
                                               Integer count,
                                           @RequestParam(defaultValue = "0")
                                               @ApiParam(value = "default value is 0")
                                               Integer page,
                                           @RequestParam(defaultValue = "id")
                                               @ApiParam(value = "default parameter is ID")
                                               String sort) {
        PageRequest pageRequest = PageRequest.of(page, count, SortParsing.getSort(sort));
        return productService.getAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get list products between price. "
            + "You can set price range, can set count products per page, "
            + "can set page and can set sort rules")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "20") @ApiParam(value = "default value is 20")
            Integer count,
            @RequestParam(defaultValue = "0") @ApiParam(value = "default value is 0")
            Integer page,
            @RequestParam(defaultValue = "id") @ApiParam(value = "default parameter is ID")
            String sort) {
        Pageable pageable = PageRequest.of(page, count, SortParsing.getSort(sort));
        return productService.getAllWherePriceBetween(from, to, pageable).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

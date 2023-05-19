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
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.SortProductUtil;
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

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable @ApiParam(value = "product id") Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return productMapper.mapToDto(productService.add(productMapper.mapToModel(dto)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable @ApiParam(value = "product id") Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@RequestBody ProductRequestDto dto,
                                     @PathVariable @ApiParam(value = "product id") Long id) {
        Product product = productMapper.mapToModel(dto);
        product.setId(id);
        Product updatedProduct = productService.update(product);
        return productMapper.mapToDto(updatedProduct);
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "Products per page") Integer size,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Page number") Integer page,
            @RequestParam(defaultValue = "none")
            @ApiParam(value = "Sorting condition") String sortBy) {
        Sort sort = SortProductUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAll(pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products which price is between two prices")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam
            @ApiParam(value = "Minimum price") BigDecimal from,
            @RequestParam
            @ApiParam(value = "Maximum price") BigDecimal to,
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "Products per page") Integer size,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Page number") Integer page,
            @RequestParam(defaultValue = "none")
            @ApiParam(value = "Sorting condition") String sortBy) {
        Sort sort = SortProductUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

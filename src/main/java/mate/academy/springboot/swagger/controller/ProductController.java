package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortMakerService;
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

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final SortMakerService sortMakerService;
    private final ProductDtoMapper<Product,
            ProductResponseDto, ProductRequestDto> productDtoMapper;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.create(productDtoMapper.toModel(requestDto));
        return productDtoMapper.toResponseDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productDtoMapper.toResponseDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productDtoMapper.toModel(requestDto);
        product.setId(id);
        return productDtoMapper.toResponseDto(productService.update(product));
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all products with sorting and ordering operations")
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "Default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "Default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "Default parameter is PARAMETER:ORDER, "
                    + "default sorting is DESC") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count,
                sortMakerService.sort(sortBy));
        return productService.getAll(pageRequest)
                .stream()
                .map(productDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/all-by-price")
    @ApiOperation(value = "Get all products with sorting and ordering "
            + "operations by price between two values")
    public List<ProductResponseDto> getAllByPrice(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "10")
            @ApiParam(value = "Default value is 10") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "Default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "Default parameter is PARAMETER:ORDER, "
                    + "default sorting is DESC") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, sortMakerService.sort(sortBy));
        return productService.getAllByPrice(from, to, pageRequest)
                .stream()
                .map(productDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product")
    void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}

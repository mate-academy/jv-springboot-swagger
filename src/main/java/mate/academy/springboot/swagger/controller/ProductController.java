package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.mapper.Mapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
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
    private final Mapper<Product, ProductRequestDto, ProductResponseDto> mapper;
    private final ProductService productService;

    @PostMapping
    @ApiOperation(value = "Create a new Product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return mapper.toDto(productService.add(mapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Product by ID")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapper.toDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Product by ID")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update Product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = mapper.toModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return mapper.toDto(product);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products where price is between two values.")
    public List<ProductResponseDto> getByPriceBetween(
            @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "ASC")
            @ApiParam(value = "takes ASC or DESC as input values")
            String sortDirection,
            @RequestParam BigDecimal priceFrom,
            @RequestParam BigDecimal priceTo) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortDirection), "title");
        return productService.findByPriceBetween(
                        priceFrom,
                        priceTo,
                        PageRequest.of(page, count, sort)
                ).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping()
    @ApiOperation(value = "Get all products.")
    public List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "ASC")
            @ApiParam(value = "takes ASC or DESC as input values")
            String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortDirection), "title");
        return productService.findAll(PageRequest.of(page, count, sort))
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}

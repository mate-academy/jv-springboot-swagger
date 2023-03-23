package mate.academy.springboot.swagger.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductController {
    private final ProductService productService;
    private final RequestDtoMapper<Product, ProductRequestDto> productRequestMapper;
    private final ResponseDtoMapper<Product, ProductResponseDto> productResponseMapper;

    @Autowired
    public ProductController(ProductService productService,
                             RequestDtoMapper<Product, ProductRequestDto> productRequestMapper,
                             ResponseDtoMapper<Product, ProductResponseDto> productResponseMapper) {
        this.productService = productService;
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productRequestMapper.toModel(productRequestDto);
        return productResponseMapper.toDto(productService.create(product));
    }

    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productResponseMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productRequestMapper.toModel(productRequestDto);
        product.setId(id);
        return productResponseMapper.toDto(productService.update(product));
    }

    @GetMapping
    public List<ProductResponseDto> getAll(@RequestParam Map<String, String> param,
                                           @RequestParam (defaultValue = "0") Integer page,
                                           @RequestParam (defaultValue = "20") Integer size) {
        return productService.getAll(param, page, size).stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}

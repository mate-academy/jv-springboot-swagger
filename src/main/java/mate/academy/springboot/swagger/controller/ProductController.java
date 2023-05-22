package mate.academy.springboot.swagger.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
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
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> productMapper;

    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper.mapToModel(productRequestDto));
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping("/find-all")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20") int count,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "price") String sortBy) {
        return productService.findAll(count,page, sortBy).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price-between")
    public List<ProductResponseDto> getAllPriceBetween(
            @RequestParam int from,
            @RequestParam int to,
            @RequestParam(defaultValue = "20") int count,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "price") String sortBy) {
        return productService.findAllPriceBetween(from, to, count, page, sortBy).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

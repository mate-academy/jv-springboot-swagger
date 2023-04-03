package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
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
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ProductResponseDto crate(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.create(productMapper.mapToModel(productRequestDto));
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping
    public List<ProductResponseDto> getAllProductByParam(@RequestParam(defaultValue = "10") Integer count,
                                                         @RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "id") String sortBy) {

        return productService.findAllProductWithParam(count, page, sortBy)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-prise")
    public List<ProductResponseDto> getProductByPriseBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "10") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy) {
        return productService.findAllByPriseBetween(from, to, count, page, sortBy)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ParseService;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
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
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> productMapper;
    private final ProductService productService;
    private final ParseService parseService;

    public ProductController(ProductService productService,
            DtoMapper<Product, ProductRequestDto, ProductResponseDto> productMapper,
            ParseService parseService) {
        this.productMapper = productMapper;
        this.productService = productService;
        this.parseService = parseService;
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.toDto(productService.save(productMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
            @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.toDto(product);
    }

    @GetMapping("/all")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count,
                parseService.directionParcing(sortBy));
        return productService.getAll(pageRequest).stream().map(productMapper::toDto).toList();
    }

    @GetMapping("/by-price-range")
    public List<ProductResponseDto> getAll(@RequestParam BigDecimal from,
            @RequestParam BigDecimal to, @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count,
                parseService.directionParcing(sortBy));
        return productService.getAllWherePriceBetween(from, to, pageRequest).stream()
                .map(p -> productMapper.toDto(p)).toList();
    }
}

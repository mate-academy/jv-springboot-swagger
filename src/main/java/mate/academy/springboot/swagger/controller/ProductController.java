package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.util.PageRequestUtil;
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
public class ProductController {
    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;
    private final PageRequestUtil parser;

    public ProductController(ProductService productService,
                             ProductDtoMapper productDtoMapper, PageRequestUtil parser) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
        this.parser = parser;
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productDtoMapper.mapToModel(productRequestDto));
        return productDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productDtoMapper.mapToDto(productService.findById(id));
    }

    @GetMapping
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "10") Integer count,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam String sortBy) {
        PageRequest parse = parser.getPageRequest(count, page, sortBy, "title", "price");
        return productService.findAll(parse)
                .stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @ApiParam("Find between price include pagination and sort")
    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPrice(@RequestParam(defaultValue = "5") Integer count,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam String sortBy,
                                            @RequestParam BigDecimal from,
                                            @RequestParam BigDecimal to) {
        Pageable pageable = parser.getPageRequest(count, page, sortBy, "title", "price");
        return productService.findAllByPriceBetween(pageable, from, to)
                .stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productDtoMapper.mapToModel(requestDto);
        product.setId(id);
        productService.save(product);
        return productDtoMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }
}

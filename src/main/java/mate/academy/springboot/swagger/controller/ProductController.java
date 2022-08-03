package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.ProductSortOrderParser;
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
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductSortOrderParser sortingParser;

    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             ProductSortOrderParser sortingParser) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortingParser = sortingParser;
    }

    @PostMapping
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        return productMapper.mapToDto(productService.save(product));
    }

    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "10")
                                                Integer count,
                                            @RequestParam(defaultValue = "0")
                                                Integer page,
                                            @RequestParam(defaultValue = "id")
                                                String sortBy) {
        Sort sort = Sort.by(sortingParser.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllWithPagination(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam(defaultValue = "10")
                                                              Integer count,
                                                          @RequestParam(defaultValue = "0")
                                                              Integer page,
                                                          @RequestParam(defaultValue = "id")
                                                              String sortBy) {
        Sort sort = Sort.by(sortingParser.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

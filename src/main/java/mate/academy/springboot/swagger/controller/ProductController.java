package mate.academy.springboot.swagger.controller;

import static mate.academy.springboot.swagger.util.SortingUtil.sortBy;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
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
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping()
    @ApiOperation(value = "Create Product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper
                .toResponseDto(productService
                        .save(productMapper
                                .toModel(productRequestDto)));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = sortBy(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService
                .findAll(pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find Product by id")
    public ProductResponseDto findById(@PathVariable Long id) {
        ProductResponseDto productResponseDto;
        Product product = productService.findById(id);
        productResponseDto = productMapper.toResponseDto(product);
        return productResponseDto;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Product by id")
    public void deleteById(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update Product by id")
    public ProductResponseDto updateById(@PathVariable Long id,
                                         @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.update(product));
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products at range of prices")
    public List<ProductResponseDto> getAllPriceBetween(@RequestParam BigDecimal from,
                                                       @RequestParam BigDecimal to,
                                                       @RequestParam (defaultValue = "20")
                                                           Integer count,
                                                       @RequestParam (defaultValue = "0")
                                                           Integer page,
                                                       @RequestParam (defaultValue = "id")
                                                           String sortBy) {
        Sort sort = sortBy(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService
                .findAllByPriceBetween(pageRequest, from, to)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

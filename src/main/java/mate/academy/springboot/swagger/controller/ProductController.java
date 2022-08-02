package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.responce.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
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
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortProductUtil sortProductUtil;

    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             SortProductUtil sortProductUtil) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortProductUtil = sortProductUtil;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        return productMapper.toResponseDto(productService.create(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toResponseDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get product's list")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                            @ApiParam(value = "default value is '20'")
                                                        Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "default value is '0'")
                                                    Integer page,
                                            @RequestParam(defaultValue = "id")
                                                        String sortBy) {
        Sort sort = Sort.by(sortProductUtil.getSortingProduct(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get product's list with 2 limiting parameters prices: from and to")
    public List<ProductResponseDto> findAllByPrice(@RequestParam
                                                   @ApiParam(value = "the lowest price")
                                                           BigDecimal from,
                                                   @RequestParam
                                                   @ApiParam(value = "the biggest price")
                                                           BigDecimal to,
                                                   @RequestParam(defaultValue = "20")
                                                   @ApiParam(value = "default value is '20'")
                                                           Integer count,
                                                   @RequestParam(defaultValue = "0")
                                                   @ApiParam(value = "default value is '0'")
                                                           Integer page,
                                                   @RequestParam(defaultValue = "id")
                                                           String sortBy) {
        Sort sort = Sort.by(sortProductUtil.getSortingProduct(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to,pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

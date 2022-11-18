package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.servise.ProductService;
import mate.academy.springboot.swagger.util.PageRequestUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductMapper productMapper;
    private ProductService productService;

    public ProductController(ProductMapper productMapper,
                             ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PostMapping
    @ApiOperation(value = "this endpoint for create new product")
    public ProductResponseDto create(ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        productService.save(product);
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "this endpoint for get product by id")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productMapper.toDto(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "this endpoint for delete product by id")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "this endpoint for update product")
    public ProductResponseDto update(@PathVariable Long id,
                                     ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        productService.save(product);
        return productMapper.toDto(product);
    }

    @GetMapping
    @ApiOperation(value = "this endpoint for get all products")
    public List<ProductResponseDto> getAllProducts(@RequestParam (defaultValue = "15")
                                                   Integer count,
                                                   @RequestParam (defaultValue = "0")
                                                   Integer page,
                                                   @RequestParam (defaultValue = "id")
                                                   String sortBy) {
        PageRequest pageRequest = PageRequestUtil.getPageRequest(page, count, sortBy);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "this endpoint for get all products by price")
    public List<ProductResponseDto> getAllProductsByPriceBetween(@RequestParam BigDecimal from,
                                                                 @RequestParam BigDecimal to,
                                                                 @RequestParam(defaultValue = "3")
                                                                 Integer count,
                                                                 @RequestParam(defaultValue = "1")
                                                                 Integer page,
                                                                 @RequestParam(defaultValue = "id")
                                                                 String sortBy) {
        PageRequest pageRequest = PageRequestUtil.getPageRequest(page, count, sortBy);
        return productService.findAllByPriceBetween(pageRequest, from, to).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

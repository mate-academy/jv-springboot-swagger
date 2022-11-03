package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.domain.Product;
import mate.academy.springboot.swagger.domain.dto.ProductRequestDto;
import mate.academy.springboot.swagger.domain.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.PageRequestUtil;
import org.springframework.data.domain.PageRequest;
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

    @PostMapping
    @ApiOperation(value = "this endpoint for create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toDomain(requestDto);
        Product savedProduct = productService.save(product);
        return productMapper.toDto(savedProduct);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "this endpoint for get product by id")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productMapper.toDto(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "this endpoint for delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "this endpoint for update product")
    public ProductResponseDto update(@PathVariable Long id,
                       @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toDomain(requestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
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
                                                                  @RequestParam(defaultValue = "5")
                                                                     Integer count,
                                                                  @RequestParam(defaultValue = "0")
                                                                     Integer page,
                                                                  @RequestParam(defaultValue = "id")
                                                                     String sortBy) {
        PageRequest pageRequest = PageRequestUtil.getPageRequest(page, count, sortBy);
        return productService.findAllByPriceBetween(pageRequest, from, to).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
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

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "Add new product")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.toDto(productService.add(productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove product by id")
    public void removeProduct(@PathVariable Long id) {
        productService.remove(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto updateProduct(@PathVariable Long id,
                                            @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all product")
    public List<ProductResponseDto> getAllProducts(@RequestParam(defaultValue = "0")
                                                   @ApiParam(value = "Number of page. " +
                                                           "Default value is `0`")
                                                   int page,
                                                   @RequestParam(defaultValue = "20")
                                                   @ApiParam(value = "Amount of products per page. " +
                                                           "Default value is `20`")
                                                   int count,
                                                   @RequestParam(defaultValue = "id")
                                                   @ApiParam(value = "Sorting products byu parameter. " +
                                                           "Default sorting by `id`. " +
                                                           "Can be sorted by `title` and `price`")
                                                   String sortBy) {
        PageRequest pageRequest = PageRequestUtil.getPageRequest(page, count, sortBy);
        return productService.getAllProducts(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get product by price from to")
    public List<ProductResponseDto> getAllProductsWithPriceBetween(@RequestParam BigDecimal from,
                                                                   @RequestParam BigDecimal to,
                                                                   @RequestParam(defaultValue = "0")
                                                                   @ApiParam(value = "Number of page. " +
                                                                           "Default value is `0`")
                                                                   int page,
                                                                   @RequestParam(defaultValue = "20")
                                                                   @ApiParam(value = "Amount of products per page. " +
                                                                           "Default value is `20`")
                                                                   int count,
                                                                   @RequestParam(defaultValue = "id")
                                                                   @ApiParam(value = "Sorting products byu parameter. " +
                                                                           "Default sorting by `id`. " +
                                                                           "Can be sorted by `title` and `price`")
                                                                   String sortBy) {
        PageRequest pageRequest = PageRequestUtil.getPageRequest(page, count, sortBy);
        return productService.getAllProductsWithPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
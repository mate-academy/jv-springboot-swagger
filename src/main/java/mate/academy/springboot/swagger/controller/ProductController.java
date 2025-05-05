package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductMapper;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ApiOperation(value = "Save a product")
    public ProductResponseDto save(@RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product")
    public ProductResponseDto get(@PathVariable @ApiParam("Product ID") Long id) {
        Product product = productService.get(id);
        return productMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product")
    public ResponseEntity<Object> delete(@PathVariable @ApiParam("Product ID") Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product")
    public ResponseEntity<Object> update(@PathVariable @ApiParam("Product ID") Long id,
                                         @RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        int result = productService.update(product);
        return result > 0 ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "5")
                                               @ApiParam("Count of products. Default is `5`")
                                               Integer count,
                                           @RequestParam(defaultValue = "1")
                                               @ApiParam("Count of pages. Default is `1`")
                                               Integer page,
                                           @RequestParam(defaultValue = "title")
                                               @ApiParam("Field behind which will be sorted. "
                                                       + "Default is `title:ASC`. You can "
                                                       + "choose an order type ASC/DESC")
                                               String sort) {
        return productService.getAll(page - 1, count, sort).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products by price between")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam(defaultValue = "5")
                                                             @ApiParam("Count of products. "
                                                                     + "Default is `5`")
                                                             Integer count,
                                                         @RequestParam(defaultValue = "1")
                                                             @ApiParam("Count of pages. "
                                                                     + "Default is `1`")
                                                             Integer page,
                                                         @RequestParam(defaultValue = "title")
                                                             @ApiParam("Field behind which will "
                                                                     + "be sorted. Default is "
                                                                     + "`title:ASC`. You can "
                                                                     + "choose an order type "
                                                                     + "ASC/DESC")
                                                             String sort,
                                                         @RequestParam
                                                                 @ApiParam("Initial price")
                                                                 BigDecimal priceFrom,
                                                         @RequestParam
                                                                 @ApiParam("Final price")
                                                                 BigDecimal priceTo) {
        return productService.getAllByPriceBetween(priceFrom, priceTo, page - 1, count, sort)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

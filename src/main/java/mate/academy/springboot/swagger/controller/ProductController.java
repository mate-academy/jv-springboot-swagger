package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.RequestMapper;
import mate.academy.springboot.swagger.mapper.ResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.PaginationUtil;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Products resource", tags = {"/products"})
@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final RequestMapper<Product, ProductRequestDto> requestMapper;
    private final ResponseMapper<Product, ProductResponseDto> responseMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Adding new product")
    public ProductResponseDto add(@RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = productService.save(requestMapper.toModel(productRequestDto));
        return responseMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Getting product information by product id")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting product by id")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>("Resource successfully deleted", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updating product by id")
    public ProductResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = requestMapper.toModel(productRequestDto).setId(id);
        return responseMapper.toDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Getting list of products with pagination and sorting")
    List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "amount of products per page", defaultValue = "20")Integer size,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "page number", defaultValue = "0")Integer page,
            @RequestParam(defaultValue = "price")
            @ApiParam(value = "sorting parameters", defaultValue = "price") String sort) {
        Pageable pageable = PaginationUtil.getPageableForParameters(size, page, sort);
        return productService.findAll(pageable).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Getting list of products by price between with pagination and sorting")
    List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam @ApiParam(value = "price from", required = true) BigDecimal from,
            @RequestParam @ApiParam(value = "price to", required = true)BigDecimal to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "amount of products per page", defaultValue = "20")Integer size,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "page number", defaultValue = "0")Integer page,
            @RequestParam(defaultValue = "price")
            @ApiParam(value = "sorting parameters", defaultValue = "price") String sort) {
        Pageable pageable = PaginationUtil.getPageableForParameters(size, page, sort);
        return productService.findAllByPriceBetween(from, to, pageable).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }
}

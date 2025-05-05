package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.mapper.RequestMapper;
import mate.academy.springboot.swagger.mapper.ResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
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
    private final RequestMapper<Product, ProductRequestDto> requestMapper;
    private final ResponseMapper<ProductResponseDto, Product> responseMapper;

    public ProductController(ProductService productService,
                             RequestMapper<Product, ProductRequestDto> requestMapper,
                             ResponseMapper<ProductResponseDto, Product> responseMapper) {
        this.productService = productService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    ProductResponseDto addProduct(@RequestBody ProductRequestDto requestDto) {
        return responseMapper.toDto(productService.save(requestMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    ProductResponseDto getProduct(@PathVariable Long id) {
        return responseMapper.toDto(productService.getById(id));
    }

    @PutMapping
    @ApiOperation(value = "Update product")
    ProductResponseDto updateProduct(@RequestBody ProductRequestDto requestDto) {
        return responseMapper.toDto(productService.update(requestMapper.toModel(requestDto)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return String.format("Product by id: %s was deleted!", id);
    }

    @GetMapping
    @ApiOperation(value = "Get list of all products")
    List<ProductResponseDto> getAllProducts(@RequestParam (defaultValue = "20")
                                            @ApiParam(value = "default value is 20")
                                                     Integer count,
                                            @RequestParam (defaultValue = "0")
                                            @ApiParam(value = "default value is 0")
                                                     Integer page,
                                            @RequestParam (defaultValue = "id")
                                            @ApiParam(value = "default value is id")
                                                     String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return productService.findAll(pageRequest, sortBy).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get list of products specified by price")
    List<ProductResponseDto> getProductsByPriceBetween(@RequestParam BigDecimal from,
                                                       @RequestParam BigDecimal to,
                                                       @RequestParam (defaultValue = "20")
                                                       @ApiParam(value = "default value is 20")
                                                               Integer count,
                                                       @RequestParam (defaultValue = "0")
                                                       @ApiParam(value = "default value is 0")
                                                               Integer page,
                                                       @RequestParam (defaultValue = "id")
                                                       @ApiParam(value = "default value is id")
                                                               String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return productService.findAllByPriceBetween(from, to, pageRequest, sortBy).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }
}

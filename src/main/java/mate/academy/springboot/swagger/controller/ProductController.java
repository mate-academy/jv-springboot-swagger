package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.request.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.Parser;
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

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product with id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product with id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Get all products and sort them for some parameters")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "20")
                                               @ApiParam("default value is 20") Integer count,
                                           @RequestParam (defaultValue = "0")
                                               @ApiParam("default page number 1 (0)") Integer page,
                                           @RequestParam (defaultValue = "title")
                                               @ApiParam("title/price - default value is title")
                                               String sortBy) {
        Sort sort = Sort.by(Parser.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/categories")
    @ApiOperation(value = "Get all products with price between some values")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                         @RequestParam BigDecimal to,
                                         @RequestParam (defaultValue = "20")
                                             @ApiParam("default value is 20") Integer count,
                                         @RequestParam (defaultValue = "0")
                                             @ApiParam("default page number 1 (0)") Integer page,
                                         @RequestParam (defaultValue = "title")
                                             @ApiParam("title/price - default value is title")
                                             String sortBy) {
        Sort sort = Sort.by(Parser.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

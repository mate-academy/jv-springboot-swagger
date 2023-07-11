package mate.academy.springboot.swagger.controller;

import static mate.academy.springboot.swagger.util.ParsingOrder.parsing;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "create and save a new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper.maptoModel(productRequestDto));
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by Id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete product by Id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by Id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody
                                     @ApiParam(value = "paste the product body")
                                     ProductRequestDto productRequestDto) {
        Product product = productMapper.maptoModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                           @ApiParam(value = "defaultValue = 20") Integer count,
                                           @RequestParam(defaultValue = "0")
                                           @ApiParam(value = "defaultValue = 0") Integer page,
                                           @RequestParam(defaultValue = "id")
                                           @ApiParam(value = "defaultValue = sort by id")
                                               String sortBy) {
        List<Sort.Order> parsed = parsing(sortBy);
        Sort sort = Sort.by(parsed);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get-by-price")
    @ApiOperation(value = "Get all products by the price between")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @ApiParam(value = "defaultValue = sort by id")
            @RequestParam(defaultValue = "id") String sortBy,
            @ApiParam(value = "defaultValue = 20")
            @RequestParam(defaultValue = "20") Integer count,
            @ApiParam(value = "defaultValue = 0")
            @RequestParam(defaultValue = "0") Integer page) {
        List<Sort.Order> parsed = parsing(sortBy);
        Sort sort = Sort.by(parsed);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

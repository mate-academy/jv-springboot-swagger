package mate.academy.springboot.swagger.controller;

import static mate.academy.springboot.swagger.util.ParsingOrder.parsing;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.sevice.ProductService;
import mate.academy.springboot.swagger.sevice.mapper.DtoMapper;
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
    private final DtoMapper<Product, ProductResponseDto, ProductRequestDto> productMapper;
    private final ProductService productService;

    public ProductController(DtoMapper<Product, ProductResponseDto,
            ProductRequestDto> productMapper, ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PostMapping
    @ApiOperation(value = "create and save a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.toDto(productService.create(productMapper.toEntity(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by Id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by Id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by Id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody
                                     @ApiParam(value = "paste the product body")
                                     ProductRequestDto requestDto) {
        Product updatedProduct = productMapper.toEntity(requestDto);
        updatedProduct.setId(id);
        return productMapper.toDto(productService.update(updatedProduct));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                           @ApiParam(value = "default value = 20") Integer count,
                                           @RequestParam(defaultValue = "0")
                                           @ApiParam(value = "default value = 0") Integer page,
                                           @RequestParam(defaultValue = "id")
                                           @ApiParam(value = "default sort by id") String sortBy) {
        List<Sort.Order> parsed = parsing(sortBy);
        Sort sort = Sort.by(parsed);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get-by-price")
    @ApiOperation(value = "Get all products by the price between")
    public List<ProductResponseDto> getAllByPrice(@RequestParam(defaultValue = "20")
                                                  @ApiParam(value = "default value = 20")
                                                  Integer count,
                                                  @RequestParam(defaultValue = "0")
                                                  @ApiParam(value = "default value = 0")
                                                  Integer page,
                                                  @RequestParam(defaultValue = "id")
                                                  @ApiParam(value = "default sort by id")
                                                  String sortBy,
                                                  @RequestParam
                                                  @ApiParam(value = "price from")BigDecimal from,
                                                  @RequestParam
                                                  @ApiParam(value = "price to") BigDecimal to) {
        List<Sort.Order> parsed = parsing(sortBy);
        Sort sort = Sort.by(parsed);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

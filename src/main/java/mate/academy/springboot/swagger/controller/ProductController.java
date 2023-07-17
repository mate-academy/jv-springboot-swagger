package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.validation.Valid;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.util.SortRequestParser;
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
    private final RequestDtoMapper<ProductRequestDto, Product> requestMapper;
    private final ResponseDtoMapper<Product, ProductResponseDto> responseMapper;

    public ProductController(ProductService productService,
                             RequestDtoMapper<ProductRequestDto, Product> requestMapper,
                             ResponseDtoMapper<Product, ProductResponseDto> responseMapper) {
        this.productService = productService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping("/create")
    @ApiOperation("Endpoint to create new products")
    public ProductResponseDto create(@RequestBody @Valid ProductRequestDto productRequestDto) {
        return responseMapper.mapToDto(productService.create(
                requestMapper.mapToModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation("Endpoint to get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return responseMapper.mapToDto(productService.get(id)
                .orElseThrow(() -> new NoSuchElementException("Can`t find product with id:"
                        + id)));
    }

    @GetMapping("/all")
    @ApiOperation("Endpoint to get all products with pagination and ability "
            + "to sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> findAllSortByPrice(@ApiParam("default value = 20")
                                                       @RequestParam(defaultValue = "20")
                                                       Integer count,
                                                       @ApiParam("default value = 0")
                                                       @RequestParam(defaultValue = "0")
                                                       Integer page,
                                                       @ApiParam("default sort value = price")
                                                       @RequestParam(defaultValue = "price")
                                                       String sortBy) {
        Sort sort = Sort.by(SortRequestParser.parse(sortBy));
        PageRequest request = PageRequest.of(page, count, sort);
        return productService.getAllProducts(request).stream()
                .map(responseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/all-condition")
    @ApiOperation("Endpoint to get all products where price is between two values received as a "
            + "RequestParam inputs, also with pagination and ability to sort by price or by title "
            + "in ASC or DESC order")
    public List<ProductResponseDto> findAllByPriceBetween(@ApiParam("default value = 0")
                                                          @RequestParam(defaultValue = "0")
                                                          BigDecimal from,
                                                          @ApiParam("default value = 1000")
                                                          @RequestParam(defaultValue = "1000")
                                                          BigDecimal to,
                                                          @ApiParam("default value = 20")
                                                          @RequestParam(defaultValue = "20")
                                                          Integer count,
                                                          @ApiParam("default value = 0")
                                                          @RequestParam(defaultValue = "0")
                                                          Integer page,
                                                          @ApiParam("default sort value = id")
                                                          @RequestParam(defaultValue = "id")
                                                          String sortBy) {
        Sort sort = Sort.by(SortRequestParser.parse(sortBy));
        PageRequest request = PageRequest.of(page, count, sort);
        return productService.getAllProductsByPrice(from, to, request).stream()
                .map(responseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/update")
    @ApiOperation("Endpoint to update product with Id")
    public ProductResponseDto update(@RequestParam Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = requestMapper.mapToModel(productRequestDto);
        product.setId(id);
        return responseMapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Endpoint to delete product by Id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}

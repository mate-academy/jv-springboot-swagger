package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
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
    public ProductResponseDto add(@RequestBody ProductRequestDto dto) {
        return productMapper.mapToDto(productService
                .save(productMapper.mapToModel(dto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product with id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product with id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto dto) {
        Product product = productMapper.mapToModel(dto);
        product.setId(id);
        return productMapper.mapToDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products and sort them by parameters")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "10")
                                            @ApiParam("default value is 10") Integer count,
                                            @RequestParam (defaultValue = "0")
                                            @ApiParam("default page number is 0") Integer page,
                                            @RequestParam (defaultValue = "title")
                                            @ApiParam("default value is title")
                                            String sortBy) {
        Sort sort = Sort.by(Parser.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products with price between 2 values"
            + " and sort them by parameters")
    public List<ProductResponseDto> findAllWherePriceBetween(@RequestParam BigDecimal from,
                             @RequestParam BigDecimal to,
                             @RequestParam (defaultValue = "10")
                             @ApiParam("default value is 10") Integer count,
                             @RequestParam (defaultValue = "0")
                             @ApiParam("default page number is 0") Integer page,
                             @RequestParam (defaultValue = "title")
                             @ApiParam("default value is title")
                             String sortBy) {
        List<Sort.Order> orders = Parser.getSortOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllWherePriceBetween(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

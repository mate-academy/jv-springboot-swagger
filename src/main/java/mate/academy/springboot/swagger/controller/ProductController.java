package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.Parser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseDtoMapper;

    public ProductController(ProductService productService, RequestDtoMapper<ProductRequestDto,
            Product> productRequestDtoMapper, ResponseDtoMapper<ProductResponseDto,
            Product> productResponseDtoMapper) {
        this.productService = productService;
        this.productRequestDtoMapper = productRequestDtoMapper;
        this.productResponseDtoMapper = productResponseDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto dto) {
        return productResponseDtoMapper.mapToDto(productService
                .save(productRequestDtoMapper.mapToModel(dto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product with id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productResponseDtoMapper.mapToDto(productService.get(id));
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
        Product product = productRequestDtoMapper.mapToModel(dto);
        product.setId(id);
        return productResponseDtoMapper.mapToDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all the products and sort them by some parameters")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "10") @ApiParam("default value is 10") Integer count,
            @RequestParam(defaultValue = "0") @ApiParam("default page number 1 (0)") Integer page,
            @RequestParam(defaultValue = "title")
            @ApiParam("title/price - default value is title") String sortBy) {
        Sort sort = Sort.by(Parser.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get all the products those price between some values"
            + " and sort them by some parameters")
    public List<ProductResponseDto> findAllWherePriceBetween(
            @RequestParam BigDecimal priceFrom,
            @RequestParam BigDecimal priceTo,
            @RequestParam @ApiParam("default value is 10") Integer count,
            @RequestParam @ApiParam("default page number 1 (0)") Integer page,
            @RequestParam @ApiParam("title/price - default value is title") String sortBy) {
        List<Order> orders = Parser.getSortOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllWherePriceBetween(priceFrom, priceTo, pageRequest).stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

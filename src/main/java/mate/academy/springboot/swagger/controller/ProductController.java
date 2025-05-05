package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.impl.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.OrderParser;
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
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.toDto(productService.save(productMapper.fromDto(productRequestDto)));
    }

    @GetMapping("/{productId}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long productId) {
        return productMapper.toDto(productService.getById(productId));
    }

    @DeleteMapping("/{productId}")
    @ApiOperation(value = "Delete product by id")
    public void deleteById(@PathVariable Long productId) {
        productService.deleteById(productId);
    }

    @PutMapping("/{productId}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long productId,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product productToUpdate = productService.getById(productId);
        productToUpdate.setPrice(productRequestDto.getPrice());
        productToUpdate.setTitle(productRequestDto.getTitle());
        return productMapper.toDto(productService.update(productToUpdate));
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                            @ApiParam(value = "default value is 20") Integer count,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = OrderParser.parseOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get products by price between bounds")
    public List<ProductResponseDto> findAllByPrice(@RequestParam BigDecimal lowBound,
                                                   @RequestParam BigDecimal highBound,
                                                   @RequestParam(defaultValue = "20")
                                                   @ApiParam(value = "default value is 20")
                                                   Integer count,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "id")
                                                   String sortBy) {
        List<Sort.Order> orders = OrderParser.parseOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getByPriceBetween(lowBound, highBound, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

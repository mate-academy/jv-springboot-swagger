package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.mapper.ProductRequestMapper;
import mate.academy.springboot.swagger.dto.mapper.ProductResponseMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.utils.ParseAndSortByDirections;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;

    public ProductController(ProductService productService,
                             ProductRequestMapper productRequestMapper,
                             ProductResponseMapper productResponseMapper) {
        this.productService = productService;
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        Product product = productService.save(productRequestMapper.mapToModel(dto));
        return productResponseMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.get(id);
        return productResponseMapper.mapToDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto dto) {
        Product product = productRequestMapper.mapToModel(dto);
        product.setId(id);
        return productResponseMapper.mapToDto(productService.save(product));
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(Long id) {
        productService.delete(id);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get products list")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "5")
                                            @ApiParam(value = "default value is 5") Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "default value is 0") Integer page,
                                            @RequestParam(defaultValue = "id")
                                            @ApiParam(value = "default sort by id") String sortBy) {
        Sort sort = ParseAndSortByDirections.sortOrders(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productResponseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products list between two prices")
    public List<ProductResponseDto> findAllBetweenPrices(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                                         @RequestParam(defaultValue = "5")
                                                         @ApiParam(value = "default value is 5")
                                                             Integer count,
                                                         @RequestParam(defaultValue = "0")
                                                         @ApiParam(value = "default value is 0")
                                                             Integer page,
                                                         @RequestParam(defaultValue = "id")
                                                         @ApiParam(value = "default sort by id")
                                                             String sortBy) {
        Sort sort = ParseAndSortByDirections.sortOrders(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllBetweenTwoPrices(from, to, pageRequest).stream()
                .map(productResponseMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

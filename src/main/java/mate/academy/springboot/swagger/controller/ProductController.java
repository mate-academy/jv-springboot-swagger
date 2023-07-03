package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingStrategy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final SortingStrategy sortingStrategy;

    public ProductController(ProductMapper productMapper,
                             ProductService productService,
                             SortingStrategy sortingStrategy) {
        this.productMapper = productMapper;
        this.productService = productService;
        this.sortingStrategy = sortingStrategy;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.toDto(
                productService.save(productMapper.toModel(productRequestDto))
        );
    }

    @GetMapping("/by-id")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto get(@RequestParam Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @GetMapping
    @ApiOperation(value = "get all sorted (ASC (by default), DESC) products by pages")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "0")
                                               @ApiParam("default value '0'") Integer page,
                                           @RequestParam(defaultValue = "20")
                                                @ApiParam("default value '20'") Integer count,
                                           @RequestParam(defaultValue = "title")
                                                @ApiParam("default value 'title'") String sortBy) {
        List<Sort.Order> orders = sortingStrategy.getSortingOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get all where price between")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                                  @RequestParam BigDecimal to,
                                                  @RequestParam(defaultValue = "0")
                                                      @ApiParam("default value '0'") Integer page,
                                                  @RequestParam(defaultValue = "20")
                                                      @ApiParam("default value '20'") Integer count,
                                                  @RequestParam(defaultValue = "title")
                                                      @ApiParam("default value 'title'")
                                                      String sortBy) {
        List<Sort.Order> orders = sortingStrategy.getSortingOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getByPrice(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@RequestParam Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.toDto(product);
    }

    @DeleteMapping
    @ApiOperation(value = "delete product by id")
    public void deleteProduct(@RequestParam Long id) {
        productService.delete(id);
    }
}

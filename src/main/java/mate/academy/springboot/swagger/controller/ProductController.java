package mate.academy.springboot.swagger.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.PaginationUtil;

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
    @ApiOperation(value = "to add Product to DB")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.toDto(productService.save(productMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "to get Product by id from DB")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "to delete Product by id in DB")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "to update Product by id in DB")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.toDto(product);
    }

    @GetMapping("/byPrice")
    @ApiOperation(value = "to get all Products from DB between min "
            + "and max PRICE value with sorting & pagination ability")
    public List<ProductResponseDto> findByPrice(@RequestParam(defaultValue = "5")
                                                @ApiParam(value = "default value is 5")
                                                Integer count,
                                                @RequestParam(defaultValue = "0")
                                                @ApiParam(value = "default value is 0")
                                                Integer page,
                                                @RequestParam(defaultValue = "0.00")
                                                @ApiParam(value = "default value is 0")
                                                BigDecimal from,
                                                @ApiParam(value = "default value is 10000")
                                                @RequestParam(defaultValue = "10000.00")
                                                BigDecimal to,
                                                @RequestParam(defaultValue = "title")
                                                @ApiParam(value = "default sorting - by title")
                                                String sortBy) {
        PageRequest pageRequest = PaginationUtil.getOrders(page, count, sortBy);
        return productService.findAllByPriceBetween(pageRequest, from, to).stream()
                .map(productMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "to get all Products from DB with sorting & pagination ability")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "5")
                                           @ApiParam(value = "default value is 5") Integer count,
                                           @RequestParam (defaultValue = "0")
                                           @ApiParam(value = "default value is 0") Integer page,
                                           @RequestParam (defaultValue = "title")
                                           @ApiParam(value = "default sorting - by title")
                                           String sortBy) {
        PageRequest pageRequest = PaginationUtil.getOrders(page, count, sortBy);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

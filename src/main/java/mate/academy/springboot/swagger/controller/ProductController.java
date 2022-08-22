package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.SortProductUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productDto) {
        Product product = productService.create(productMapper.toModel(productDto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by ID")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by ID")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product")
    public void update(@PathVariable Long id, @RequestBody ProductRequestDto requestDto) {
        productService.update(id, productMapper.toModel(requestDto));
    }

    @GetMapping
    @ApiOperation(value = "get product's list")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                           @ApiParam(value = "default value is 20")
                                           Integer count,
                                           @RequestParam(defaultValue = "0")
                                           @ApiParam(value = "default value is 0")
                                           Integer page,
                                           @RequestParam(defaultValue = "id")
                                           @ApiParam(value = "default value is id")
                                           String sortBy) {
        Sort sort = SortProductUtil.getSortingProduct(sortBy);
        return productService.getAll(PageRequest.of(page, count, sort)).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get product's list between price limits (including)")
    public List<ProductResponseDto> getProductsBetween(@RequestParam
                                                       @ApiParam(value = "the lowest price")
                                                       BigDecimal from,
                                                       @RequestParam
                                                       @ApiParam(value = "the highest price")
                                                       BigDecimal to,
                                                       @RequestParam(defaultValue = "20")
                                                       @ApiParam(value = "default value is 20")
                                                       Integer count,
                                                       @RequestParam(defaultValue = "0")
                                                       @ApiParam(value = "default value is 0")
                                                       Integer page,
                                                       @RequestParam(defaultValue = "id")
                                                       @ApiParam(value = "default value is id")
                                                       String sortBy) {
        Sort sort = SortProductUtil.getSortingProduct(sortBy);
        return productService.getProductsBetween(
                        from,
                        to,
                        PageRequest.of(page, count, sort)).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
